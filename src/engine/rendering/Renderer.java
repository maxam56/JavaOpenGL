package engine.rendering;

import engine.rendering.models.Model;
import engine.rendering.models.TexturedModel;
import engine.rendering.models.UntexturedModel;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.TextureLoader;

public class Renderer {
    public void renderModel(UntexturedModel model) {
        GL30.glBindVertexArray(model.getVertexArrayID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL40.glBindVertexArray(0);
    }

    public void renderTexturedModel(TexturedModel model) {
        GL30.glBindVertexArray(model.getVertexArrayID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getMaterial().getTextureID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL40.glBindVertexArray(0);
    }
}
