package engine.io;

import main.Main;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.nio.DoubleBuffer;

public class Window {
    private int width, height;
    private String title;
    private long window;
    private boolean keys[];
    private boolean mouseButtons[];

    private double fps_cap;
    private double time;
    private double proccessedTime;

    private Vector3f backgroundColor;

    public Window(int width, int height, String title, int fps) {
        this.width = width;
        this.height = height;
        this.title = title;
        fps_cap = 1.0/fps;
        keys = new boolean[GLFW.GLFW_KEY_LAST];
        mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

        backgroundColor = new Vector3f(0.0f, 0.0f, 0.0f);
    }

    public void create() {
        if (!GLFW.glfwInit()) {
            System.err.println("Error: Failed to initialize glfw;");
            System.exit(-1);
        }

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0) {
            System.err.println("Error: Window couldn't be created");
            System.exit(-1);
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (videoMode.width() - width)/2, (videoMode.height() - height)/2);

        GLFW.glfwShowWindow(window);

        time = getTime();
        proccessedTime = 0;

        //Rendering setup
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }

    public boolean isKeyDown(int keycode) {
        return GLFW.glfwGetKey(window, keycode) == 1;
    }

    public boolean isKeyPressed(int keycode) {
        return isKeyDown(keycode) && !keys[keycode];
    }

    public boolean isKeyReleased(int keycode) {
        return !isKeyDown(keycode) && keys[keycode];
    }

    public boolean isMouseDown(int mouseButton) {
        return GLFW.glfwGetMouseButton(window, mouseButton) == 1;
    }

    public boolean isMousePressed(int mouseButton) {
        return isMouseDown(mouseButton) && !mouseButtons[mouseButton];
    }

    public boolean isMouseReleased(int mouseButton) {
        return !isMouseDown(mouseButton) && mouseButtons[mouseButton];
    }

    public double getMouseX() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(window, buffer, null);
        return buffer.get(0);
    }

    public double getMouseY() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(window, null, buffer);
        return buffer.get(0);
    }

    public double getTime() {
        return (double)System.nanoTime() / 1e9;
    }

    public boolean isUpdating() {
        double nextTime = getTime();
        double pastTime = nextTime - time;
        proccessedTime += pastTime;
        time = nextTime;
        while (proccessedTime > fps_cap) {
            proccessedTime -= fps_cap;
            return true;
        }
        return false;
    }

    public boolean close() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void update() {
        for (int i = 0; i < GLFW.GLFW_KEY_LAST; i++) {
            keys[i] = isKeyDown(i);
        }

        for (int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) {
            mouseButtons[i] = isMouseDown(i);
        }
        GL11.glClearColor(backgroundColor.x,backgroundColor.y,backgroundColor.z,1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GLFW.glfwPollEvents();

    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindow() {
        return window;
    }

    public double getFPS() {
        return fps_cap * Main.FPS;
    }

    public void setBackgroundColor(float r, float g, float b) {
        backgroundColor = new Vector3f(r, g, b);
    }

    public void stop() {
        GLFW.glfwTerminate();
    }
}
