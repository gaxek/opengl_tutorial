import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    public long window;
    public int width;
    public int height;
    public int aspectRatio;
    private GLFWWindowSizeCallback windowSize;

    public Window(int width, int height, String title){
        this.width = width;
        this.height = height;
        this.aspectRatio = width/height;
        init(width, height, title);
    }

    private void init(int width, int height, String title){
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if(window == NULL) {
            throw new IllegalStateException("Unable to create GLFW Window");
        }

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE){
                glfwSetWindowShouldClose(window, true);
            }
        });

        try(MemoryStack stack = stackPush()){
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(window,(vidmode.width() - pWidth.get(0)) / 2,(vidmode.height() - pHeight.get(0)) / 2);

            glfwMakeContextCurrent(window);
            glfwSwapInterval(1);
            glfwShowWindow(window);
        }

        glfwSetWindowSizeCallback(window, windowSize = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                Boot.window.height = height;
                Boot.window.width = width;
                Boot.window.aspectRatio = width/height;
                glViewport(0, 0, Boot.window.width, Boot.window.height);
            }
        });

        GL.createCapabilities();
    }

    public void update(){
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public boolean shouldClose(){
        return glfwWindowShouldClose(window);
    }

    public void terminate(){
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public Matrix4f getProjectionMatrix(){
        Matrix4f matrix = new Matrix4f().ortho2D(-this.width / 2, this.width / 2, -this.height / 2, this.height / 2);
        return matrix;
    }
}
