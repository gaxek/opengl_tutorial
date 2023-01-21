public class ShaderTexture extends Shader{
    public ShaderTexture(){
        super("vertexshader.vert", "fragmentshader.frag");
    }

    @Override
    protected void bindAttributes(){
        super.bindAttribute(0, "postion");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations(){}
}
