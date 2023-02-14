import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {
    private int programID;
    private int vertexID;
    private int fragmentID;
    private FloatBuffer matrix;

    public Shader(String Vert, String Frag){
        vertexID = loadShader(Vert, GL_VERTEX_SHADER);
        fragmentID = loadShader(Frag, GL_FRAGMENT_SHADER);
        programID = glCreateProgram();
        glAttachShader(programID, vertexID);
        glAttachShader(programID, fragmentID);
        bindAttributes();
        glLinkProgram(programID);
        glValidateProgram(programID);
        getAllUniformLocations();
    }

    public void start(){
        glUseProgram(programID);
    }

    public void stop(){
        glUseProgram(0);
    }

    protected abstract void bindAttributes();

    protected abstract void getAllUniformLocations();

    protected int getUniformLocation(String uniformName){
        return glGetUniformLocation(programID, uniformName);
    }

    protected void bindAttribute(int attribute, String variableName){
        glBindAttribLocation(programID, attribute, variableName);
    }

    protected void loadMatrix(int location, Matrix4f value){
        this.matrix = BufferUtils.createFloatBuffer(16);
        value.get(matrix);
        glUniformMatrix4fv(location, false, matrix);
    }

    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("res/shaders/" + file));
            String line;
            while((line = reader.readLine()) !=null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        }catch(IOException e){
            System.err.println("Can't read file");
            e.printStackTrace();
            System.exit(-1);
        }
        int ID = GL20.glCreateShader(type);
        GL20.glShaderSource(ID, shaderSource);
        GL20.glCompileShader(ID);
        if(GL20.glGetShaderi(ID, GL20.GL_COMPILE_STATUS)==GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(ID, 512));
            System.err.println("Couldn't compile the shader");
            System.exit(-1);
        }
        return ID;
    }
}
