package Modelstuff;

import org.joml.Vector3f;

public class Face {
    public Vector3f vertex;  //die Indizes der einzelnen Punkte
    public Vector3f normal;
    public Face(Vector3f vertex, Vector3f normal) {
        this.vertex = vertex;
        this.normal = normal;
    }
}
