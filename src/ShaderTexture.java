import org.joml.Matrix4f;

public class ShaderTexture extends Shader{
    private int locationProjection;
    private int locationView;

    public ShaderTexture(){
        super("vertexshader.vert", "fragmentshader.frag");
    }

    @Override
    protected void bindAttributes(){
        super.bindAttribute(0, "postion");
        super.bindAttribute(1, "textureCoords");
    }

    public void setProjection(Matrix4f mat){
        this.loadMatrix(locationProjection, mat);
    }

    public void setView(Matrix4f mat){
        this.loadMatrix(locationView, mat);
    }

    @Override
    protected void getAllUniformLocations(){
        this.locationProjection = this.getUniformLocation("projection");
        this.locationView = this.getUniformLocation("view");
    }
}
