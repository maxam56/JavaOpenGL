package main;

import engine.io.Window;
import engine.render.Model;
import engine.render.Renderer;
import org.lwjgl.glfw.GLFW;

public class Main {
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public static int FPS = 60;

    public static Renderer renderer = new Renderer();

    public static void main(String[] args) {
        Window window = new Window(WIDTH, HEIGHT,"Practice", FPS);
        window.create();
        window.setBackgroundColor(1.0f,0.0f,0.0f);

        Model model = new Model(new float[] {
            -0.5f, 0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f

        }, new int[] {
                0, 1, 2,
                2, 3, 1
        });

        model.create();


        while (!window.close()) {
            if (window.isUpdating()) {
                window.update();
                renderer.renderModel(model);
                window.swapBuffers();
            }
        }

        model.remove();
        window.stop();
    }
}
