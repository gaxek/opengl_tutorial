import static org.lwjgl.opengl.GL11.*;

public class Boot {

    public static Window window;

    public void run(){
        window = new Window(640, 480, "Cool Title");
        loop();
        window.terminate();
    }

    public void loop(){
        glEnable(GL_DEPTH_TEST);
        glClearColor(0.00f, 0.60f, 0.10f, 0.0f);

        /*
        float[] vertices = {
                -0.5f, 0.5f, -0.5f,      //0 - l o v
                0.5f, 0.5f, -0.5f,       //1 - r o v
                0.5f, -0.5f, -0.5f,      //2 - r u v
                -0.5f, -0.5f, -0.5f,     //3 - l u v
                0.5f, 0.5f, 0.5f,      //4 - r o h
                0.5f, -0.5f, 0.5f,     //5 - r u h
                -0.5f, 0.5f, 0.5f,     //6 - l o h
                -0.5f, -0.5f, 0.5f,    //7 - l u h
        };
        float[] uvs = {
                //vorderseite
                0.0f,0.0f,  //links oben
                1f, 0.0f,   //rechts oben
                1f, 1f,     //rechts unten
                0f, 1f      //links unten
        };
        int[] indices = {
                //vorderseite
                0, 1, 2, 0, 2, 3,
                //rechte seite
                1, 2, 4, 4, 5, 2,
                //linke seite
                0, 3, 7, 7, 6, 1,
                //obere seite
                0, 1, 4, 4, 6, 0,
                //untere seite
                2, 3, 5, 5, 3, 7,
                //rückseite
                4, 6, 7, 7, 4, 5
        };

         */

        float[] vertices = {-50f,-50f,0f,
                50f, -50f, 0f,
                -50f,50f,0f,
                50f,50f,0f};
        int[] indices = {0,1,2,
                1,2,3};

        float[] uvs = {0f,1f,
                1f, 1f,
                0f,0f,
                1f,0f};

        Mesh mesh = MeshLoader.createMesh(vertices, uvs, indices).addTexture("kowl.png");
        Render render = new Render();

        while(!window.shouldClose()) {

            render.cleanup();
            render.render(mesh);
            window.update();
        }
    }
    public static void main(String[] args) {
        new Boot().run();
    }
}
