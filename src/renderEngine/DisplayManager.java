package renderEngine;

        import org.lwjgl.LWJGLException;
        import org.lwjgl.opengl.*;

public class DisplayManager {
    private static final int WIDTH = 1366;
    private static final int HEIGHT = 768;
    private static final int FPS_CAP = 120;

    public static void createDisplay(){

      //  ContextAttribs contextAttribs = new ContextAttribs(3,1);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat());
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);

    }

    public static void updateDisplay(){
        Display.sync(FPS_CAP);
        Display.update();
    }

    public static void closeDisplay(){
        Display.destroy();
    }
}
