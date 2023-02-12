package Modelstuff;

import org.joml.Vector3f;

import java.util.*;

public class Model {
    public List<Vector3f> vertices = new ArrayList<Vector3f>();
    //public List<Float> verticesX = new ArrayList<Float>();
    //public List<Float> verticesY = new ArrayList<Float>();
    //public List<Float> verticesZ = new ArrayList<Float>();
    public List<Vector3f> normals = new ArrayList<Vector3f>();
    public List<Vector3f> textureCoordinates = new ArrayList<Vector3f>();
    public List<Face> faces = new ArrayList<Face>();
    public List<Vector3f> texture = new ArrayList<Vector3f>();
    public String texturemap = "";
    public Model(){}
}
