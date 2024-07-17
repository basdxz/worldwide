package com.mumfrey.worldeditcui.render.shapes;


import static com.mumfrey.worldeditcui.config.WorldEditCUIConfig.General.SHOW_HIDDEN_LINES;
import static org.lwjgl.opengl.GL11.*;

public abstract class Render {
    public final void render() {
        glLineWidth(3F);
        glDepthFunc(GL_LESS);
        renderImpl(true);

        if (SHOW_HIDDEN_LINES) {
            glLineWidth(3F);
            glDepthFunc(GL_GEQUAL);
            renderImpl(false);
        }
    }

    protected abstract void renderImpl(boolean isVisible);
}
