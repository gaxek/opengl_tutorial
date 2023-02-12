import Modelstuff.Face;
import Modelstuff.Model;
import Modelstuff.ObjectLoader;
import org.joml.Vector3f;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Boot {

    public static Window window;

    public void run(){
        window = new Window(640, 480, "Cool Title");
        loop();
        window.terminate();
    }

    public void loop(){
        glEnable(GL_DEPTH_TEST);
        glClearColor(0.00f, 0.60f, 0.10f, 0.0f);

        //Mesh mesh = MeshLoader.createMesh(vertices, uvs, indices).addTexture("kowl.png");
        Render render = new Render();

        String path = "res/models/";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        String[] sources = new String[76]; //Hier kommen dann die Sources der verschiedenen Models rein
        int c = 0;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".obj")) {
                //System.out.println("File " + listOfFiles[i].getName());
                sources[c++] = path + listOfFiles[i].getName();
            }
        }
        System.out.println(sources[0]);

        //sources[0] = "/home/gaxek/Documents/Uni/blender/grafik-project/blender_files/hexagon2.obj";
        //sources[1] = "/home/gaxek/Documents/Uni/blender/grafik-project/blender_files/stadt_wood.obj";
        List<Model> allModels = loadAllModels(sources); //Hier wird dann Sources in die Methode loadAllModels reingesteckt
        java.lang.System.out.println("ModelList created");
        List<Mesh> meshes = makedrawList(allModels); //Hier wird dann allModels in die methode makedrawList rengesteckt
        //java.lang.System.out.println(meshes.get(0).getVertexCount());
        java.lang.System.out.println("MeshList created");

        while(!window.shouldClose()) {

            render.cleanup();

            for(Mesh mesh : meshes) {
                render.render(mesh);
            }
            window.update();
        }
    }

    public List<Model> loadAllModels(String[] models) {
        List<Model> modelListe = new ArrayList<Model>();
        for(String source : models) {
            Model m = null;
            try {
                //System.out.println(source);
                m = ObjectLoader.loadModel(new File(source));  //File
                //java.lang.System.out.println("Model loaded");
                modelListe.add(m);
                //java.lang.System.out.println("Model added");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                //Display.destroy();
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
                //Display.destroy();
                System.exit(1);
            }
        }
        return modelListe;
    }
    public List<Mesh> makedrawList(List<Model> models) { //hoffentlich muss das nur 1x aufgerufen werden? ich versuchs vor die Schleife zu tun
        List<Mesh> meshes = new ArrayList<Mesh>();
        //java.lang.System.out.println("MeshList instantiated");

        for (Model m : models) {
            //java.lang.System.out.println("in for loop");//int count = 0;
            float[] vertices = new float[m.vertices.size()*3];
            int[] indices = new int[m.faces.size()*3];
            int count = 0;
            for(Vector3f vertex: m.vertices) {
                vertices[(count*3)] = vertex.x;
                vertices[(count*3)+1] = vertex.y;
                vertices[(count*3)+2] = vertex.z;
                count = count +1;

                //java.lang.System.out.println(count);

            }

            float[]verticesFixed = new float[m.textureCoordinates.size()*3];
            float[] uvs = new float[m.textureCoordinates.size()*2];
            count = 0;
            //System.out.println(m.textureCoordinates.size());
            for(Vector3f vertex: m.textureCoordinates) {
                //System.out.println((m.textureCoordinates.get(count)));
                verticesFixed[(count*3)] = m.vertices.get((int)(m.textureCoordinates.get(count).z)).x;
                verticesFixed[(count*3)+1] = m.vertices.get((int)(m.textureCoordinates.get(count).z)).y;
                verticesFixed[(count*3)+2] = m.vertices.get((int)(m.textureCoordinates.get(count).z)).z;
                uvs[(count*2)] = m.textureCoordinates.get(count).x;
                uvs[(count*2)+1] = m.textureCoordinates.get(count).y;
                count = count +1;

                //java.lang.System.out.println(count);

            }
            int facecount = 0;
            count = 0;
            for(Vector3f texture: m.texture) {
                indices[count] = (int) m.texture.get(facecount).x-1;
                indices[count + 1] = (int) m.texture.get(facecount).y-1;
                indices[count + 2] = (int) m.texture.get(facecount).z-1;
                count = count + 3;
                facecount = facecount + 1;
            }

            Mesh mesh = MeshLoader.createMesh(verticesFixed, uvs, indices).addTexture(m.texturemap);
            meshes.add(mesh);

        }
        //java.lang.System.out.println("aus for loop raus");
        return meshes;
    }
    public static void main(String[] args) {
        new Boot().run();
    }
}
