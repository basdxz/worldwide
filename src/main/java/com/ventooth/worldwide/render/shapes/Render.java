package com.ventooth.worldwide.render.shapes;


import com.ventooth.worldwide.config.WorldEditCUIConfig;

import static org.lwjgl.opengl.GL11.*;

public abstract class Render {
    public final void render() {
        glLineWidth(3F);
        glDepthFunc(GL_LESS);
        renderImpl(true);

        if (WorldEditCUIConfig.General.SHOW_HIDDEN_LINES) {
            glLineWidth(3F);
            glDepthFunc(GL_GEQUAL);
            renderImpl(false);
        }
    }

    protected abstract void renderImpl(boolean isVisible);
}
