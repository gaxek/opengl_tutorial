/*

import org.joml.Matrix4f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.concurrent.Callable;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.cooder.tinylog.Logger;

class Window{
    long windowHandle;
    int width;
    int height;

    private Callable<Void> resizeFunc;
    Window(int width, int height, String title, Callable<Void> resizeFunc){
        this.resizeFunc = resizeFunc;

        this.width = width;
        this.height = height;

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);

        // Create the window
        this.windowHandle = glfwCreateWindow(this.width, this.height, title, NULL, NULL);
        if ( this.windowHandle == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(this.windowHandle, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        glfwSetFramebufferSizeCallback(windowHandle, (window, w, h) -> resized(w, h));


        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(this.windowHandle, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    this.windowHandle,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(this.windowHandle);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(this.windowHandle);
    }

    public boolean isKeyPressed(int keyCode){
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    public void cleanup(){
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(this.windowHandle);
        glfwDestroyWindow(this.windowHandle);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    protected void resized(int width, int height) {
        this.width = width;
        this.height = height;
        try {
            resizeFunc.call();
        } catch (Exception excp) {
            Logger.error("Error calling resize callback", excp);
        }
    }

    public void update() {
        glfwSwapBuffers(this.windowHandle);
    }

}

class Renderer {
    public void init() throws Exception{

    }

    public void clear(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}

interface IGameLogic{
    void init() throws Exception;
    void input(Window window);
    void update(float interval);
    void render(Window window);
}

class GameEngine implements Runnable{
    private final Thread gameLoopThread;
    Window window;
    IGameLogic gameLogic
    public GameEngine(String windowTitle, int width, int height, boolean vsSync, IGameLogic gameLogic) throws Exception{
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(width, height, windowTitle, vsSync);
    }

    public void start(){
        gameLoopThread.start();
    }

    @Override
    public void run(){
        try{
            init();
            gameLoop();
        } catch (Exception excp){
            excp.printStackTrace();
        }
    }

    protected void input() {
        gameLogic.input(window);
    }

    protected void update(float interval){
        gameLogic.update(interval);
    }

    protected void render(){
        gameLogic.render(window);
        window.update();
    }
}

public abstract class HelloWorld implements IGameLogic {

    private void resize() {
        // Nothing to be done yet
    }

    // The window handle
    private long window;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        Window window = new Window(300, 300, "Critorial", () -> {
            resize();
            return null;
        });

        //init();
        loop(window);


        window.cleanup();
    }

    private void init() {}

    private void loop(Window window) {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.25f, 0.5f, 0.25f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window.windowHandle) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwSwapBuffers(window.windowHandle); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        try{
            HelloWorld main = new HelloWorld() {
                @Override
                public void init() {

                }

                @Override
                public void input(Window window) {

                }

                @Override
                public void update(float interval) {

                }

                @Override
                public void render(Window window) {

                }
            }
        }
    }

}
*/