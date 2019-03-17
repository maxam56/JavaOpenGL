package engine.rendering;

import org.lwjgl.opengl.GL15;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;

public class Material {
    private int textureID;
    public Material(String file) {
        try {
            textureID = TextureLoader.getTexture("png", new FileInputStream("res/textures/" + file)).getTextureID();
        } catch (IOException e) {
            System.err.println("Error: Couldn't load texture " + file);
        }
    }

    public void remove() {
        GL15.glDeleteTextures(textureID);
    }

    public int getTextureID() {
        return textureID;
    }
}
