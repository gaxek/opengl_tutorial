package Modelstuff;

import org.joml.Vector3f;

import java.io.*;

public class ObjectLoader {
    public static Model loadModel(File f) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        Model m = new Model();
        String line;
        while((line = reader.readLine()) != null) {  //line = reader.readLine ist kein Vergleich, sondern eine Zuweisung. Die wird scheinbar null wenn sie fehlschlaegt
            if(line.startsWith("v ")) {
                float x = Float.valueOf(line.split(" ")[1]); // line.split splittet die line in ein Array, jedes Mal da wo das Leerzeichen vorkommt. Der Index 1 ist dann das zweite Feld, logisch
                float y = Float.valueOf(line.split(" ")[3]); // y und z sind scheinbar geswitched und nicht wie angenommen :/
                float z = Float.valueOf(line.split(" ")[2]);
                m.vertices.add(new Vector3f(x,y,z));
                System.out.println("Vertex loaded");
            }
            else if(line.startsWith("vn ")) {
                float x = Float.valueOf(line.split(" ")[1]);
                float y = Float.valueOf(line.split(" ")[3]);
                float z = Float.valueOf(line.split(" ")[2]);
                m.normals.add(new Vector3f(x,y,z));
                System.out.println("Normal loaded");
            }
            else if(line.startsWith("f ")) {
                Vector3f vertexIndices = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[0])-1,  //die -1 kommt daher, dass .obj files die nummerierung bei 1 beginnen
                Float.valueOf(line.split(" ")[2].split("/")[0])-1,
                Float.valueOf(line.split(" ")[3].split("/")[0])-1);
                Vector3f normalIndices = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[2]),
                        Float.valueOf(line.split(" ")[2].split("/")[2]),
                        Float.valueOf(line.split(" ")[3].split("/")[2]));
                Vector3f textureIndices = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[1]),
                        Float.valueOf(line.split(" ")[2].split("/")[1]),
                        Float.valueOf(line.split(" ")[3].split("/")[1]));
                m.faces.add(new Face(vertexIndices, normalIndices));
                m.texture.add(textureIndices);  //wird in Model gespeichert, wird dann wahrscheinlich in Texture integriert
                System.out.println("Face loaded");
            }
            else if(line.startsWith("vt ")) {
                float x = Float.valueOf(line.split(" ")[1]);
                float y = Float.valueOf(line.split(" ")[2]);
                m.textureCoordinates.add(new Vector3f(x,y,0f));
                System.out.println("UV loaded");
            }
        }
        reader.close();
        return m;
    }
}
