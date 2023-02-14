package Modelstuff;

import org.joml.Vector3f;

import java.util.*;

public class Model {
    public List<Vector3f> vertices = new ArrayList<>();
    public List<Vector3f> normals = new ArrayList<>();
    public List<Vector3f> textureCoordinates = new ArrayList<>();
    public List<Face> faces = new ArrayList<>();
    public List<Vector3f> texture = new ArrayList<>();
    public String texturemap = "";
    public Model(){}
}
