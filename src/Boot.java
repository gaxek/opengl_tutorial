import Modelstuff.Model;
import Modelstuff.ObjectLoader;
import org.joml.Vector3f;

import java.io.File;
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

        Render render = new Render();

        String path = "res/models/";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        String[] sources = new String[77]; //Hier kommen dann die Sources der verschiedenen Models rein
        int c = 0;
        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile() && listOfFile.getName().contains(".obj")) {
                sources[c++] = path + listOfFile.getName();
            }
        }

        List<Model> allModels = loadAllModels(sources); //Hier wird dann Sources in die Methode loadAllModels reingesteckt
        List<Mesh> meshes = makedrawList(allModels); //Hier wird dann allModels in die methode makedrawList reingesteckt

        while(!window.shouldClose()) {

            render.cleanup();

            for (Mesh mesh : meshes) {
                render.render(mesh);
            }
            window.update();
        }
    }

    public List<Model> loadAllModels(String[] models) {
        List<Model> modelListe = new ArrayList<>();
        for(String source : models) {
            Model m;
            try {
                m = ObjectLoader.loadModel(new File(source));
                modelListe.add(m);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return modelListe;
    }
    public List<Mesh> makedrawList(List<Model> models) {
        List<Mesh> meshes = new ArrayList<>();

        for (Model m : models) {
            int[] indices = new int[m.faces.size()*3];

            float[]verticesFixed = new float[m.textureCoordinates.size()*3];
            float[] uvs = new float[m.textureCoordinates.size()*2];
            int count = 0;
            for(Vector3f ignored : m.textureCoordinates) {
                verticesFixed[(count*3)] = m.vertices.get((int)(m.textureCoordinates.get(count).z)).x;
                verticesFixed[(count*3)+1] = m.vertices.get((int)(m.textureCoordinates.get(count).z)).y;
                verticesFixed[(count*3)+2] = m.vertices.get((int)(m.textureCoordinates.get(count).z)).z;
                uvs[(count*2)] = m.textureCoordinates.get(count).x;
                uvs[(count*2)+1] = m.textureCoordinates.get(count).y;
                count = count +1;
            }
            int facecount = 0;
            count = 0;
            for(Vector3f ignored: m.texture) {
                indices[count] = (int) m.texture.get(facecount).x-1;
                indices[count + 1] = (int) m.texture.get(facecount).y-1;
                indices[count + 2] = (int) m.texture.get(facecount).z-1;
                count = count + 3;
                facecount = facecount + 1;
            }

            Mesh mesh = MeshLoader.createMesh(verticesFixed, uvs, indices).addTexture(m.texturemap);
            meshes.add(mesh);

        }
        return meshes;
    }
    public static void main(String[] args) {
        new Boot().run();
    }
}
