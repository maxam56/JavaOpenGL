package main;

import engine.Shaders.BasicShader;
import engine.io.Window;
import engine.rendering.models.Model;
import engine.rendering.Renderer;
import engine.rendering.models.TexturedModel;

public class Main {
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public static int FPS = 60;
    public static BasicShader shader = new BasicShader();


    public static Renderer renderer = new Renderer();

    public static void main(String[] args) {
        Window window = new Window(WIDTH, HEIGHT,"Practice", FPS);
        window.create();
        shader.create();
        window.setBackgroundColor(1.0f,0.0f,0.0f);

        TexturedModel model = new TexturedModel(new float[] {
            -0.5f, 0.5f, 0.0f,  //V0 TOP LEFT
            0.5f, 0.5f, 0.0f,   //V1 TOP RIGHT
            0.5f, -0.5f, 0.0f,  //V2 BOTTOM RIGHT
            -0.5f, -0.5f, 0.0f  //V3 BOTTOM LEFT

        }, new float[] {
            0, 0,
            1, 0,
            1, 1,
            0, 1
        }, new int[] {
            0, 2, 3,
            0, 1, 2
        }, "beautiful.png");

        float scale = 2.0f;
        float factor = 1/60.0f;
        while (!window.close()) {
            if (window.isUpdating()) {
                window.update();
                shader.bind();
                if (scale >= 2) {
                    factor = -1/60.0f;
                } else if (scale <= 0){
                    factor = 1/60.0f;
                }
                scale += factor;
                shader.loadScale(scale);
                renderer.renderTexturedModel(model);
                window.swapBuffers();
            }
        }
        shader.remove();
        model.remove();
        window.stop();
    }
}
