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

        String[] sources = new String[1]; //Hier kommen dann die Sources der verschiedenen Models rein
        sources[0] = "/home/gaxek/Documents/Uni/blender/grafik-project/blender_files/spielbrett.obj";
        //sources[1] = "src/pointpoint2.obj";
        List<Model> allModels = loadAllModels(sources); //Hier wird dann Sources in die Methode loadAllModels reingesteckt
        java.lang.System.out.println("ModelList created");
        List<Mesh> meshes = makedrawList(allModels); //Hier wird dann allModels in die methode makedrawList rengesteckt
        //java.lang.System.out.println(meshes.get(0).getVertexCount());
        java.lang.System.out.println("MeshList created");

        while(!window.shouldClose()) {

            render.cleanup();

            for(Mesh mesh : meshes) {
                render.render(mesh);
                window.update();
            }
        }
    }

    public List<Model> loadAllModels(String[] models) {
        List<Model> modelListe = new ArrayList<Model>();
        for(String source : models) {
            Model m = null;
            try {
                m = ObjectLoader.loadModel(new File(source));  //File
                java.lang.System.out.println("Model loaded");
                modelListe.add(m);
                java.lang.System.out.println("Model added");
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
        java.lang.System.out.println("MeshList instantiated");

        for (Model m : models) {
            java.lang.System.out.println("in for loop");//int count = 0;

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
            int facecount = 0;
            count = 0;
            for(Face face: m.faces) {
                indices[count] =(int) m.faces.get(facecount).vertex.x;
                indices[count+1] =(int) m.faces.get(facecount).vertex.y;
                indices[count+2] =(int) m.faces.get(facecount).vertex.z;
                count = count +3;
                facecount = facecount+1;
            }
            float[] uvs = {0f,1f,
                    1f, 1f,
                    0f,0f,
                    1f,0f};
            Mesh mesh = MeshLoader.createMesh(vertices, uvs, indices);
            meshes.add(mesh);

        }
        java.lang.System.out.println("aus for loop raus");
        return meshes;
    }
    public static void main(String[] args) {
        new Boot().run();
    }
}
