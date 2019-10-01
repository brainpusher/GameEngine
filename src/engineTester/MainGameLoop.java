package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) {
        DisplayManager.createDisplay();
        Loader loader = new Loader();

        RawModel model = OBJLoader.loadObjModel("stall", loader);
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));

        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        //TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale
        Entity entity = new Entity(staticModel, new Vector3f(0, -5, -25), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1)); //2-nd argument is a colour

        Camera camera = new Camera();

       // TexturedModel cubeModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
     //   Light cubeLight = new Light(new Vector3f(3000, 2000, 3000), new Vector3f(1, 1, 1));
     //   List<Entity> allCubes = new ArrayList<Entity>();
     //   Random random = new Random();
      /*  for (int i = 0; i < 400; i++) {
            float x = random.nextFloat() * 100 - 50;
            float y = random.nextFloat() * 100 - 50;
            float z = random.nextFloat() * -50;
            allCubes.add(new Entity(cubeModel, new Vector3f(x, y, z), random.nextFloat() * 180f, random.nextFloat() * 180f, 0f, 1f));
        }*/

        MasterRenderer renderer = new MasterRenderer();
        while (!Display.isCloseRequested()) {
          //  entity.increasePosition(0, 0, -0.1f);
            entity.increaseRotation(0,1,0);
            camera.move();
           /* for (Entity cube : allCubes) {
                cube.increaseRotation(1,1,1);
                renderer.processEntity(cube);

            }*/
            renderer.processEntity(entity);
            renderer.render(light,camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

}
