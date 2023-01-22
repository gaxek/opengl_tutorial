import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {
    private static  HashMap<String, Integer> idMap = new HashMap<String, Integer>();

    public static int loadTexture(String texture){
        String resourceName = texture;
        if(idMap.containsKey("res/" + resourceName)){
            return idMap.get("res/" + resourceName);
        }
        int width;
        int height;
        ByteBuffer buffer;
        try(MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            URL url = Texture.class.getResource("res/" +  resourceName);
            File file = new File("res/" + resourceName);
            String filePath = file.getAbsolutePath();
            System.out.println(filePath);
            buffer = STBImage.stbi_load(filePath, w, h, channels, 4);
            if(buffer == null){
                throw new Exception("Can't load file " + resourceName + " "+ STBImage.stbi_failure_reason());
            }
            width = w.get();
            height = h.get();

            System.out.println(width + "x" + height);

            int id = glGenTextures();
            idMap.put(resourceName, id);
            glBindTexture(GL_TEXTURE_2D, id);
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            glGenerateMipmap(GL_TEXTURE_2D);
            STBImage.stbi_image_free(buffer);
            return id;
        } catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
