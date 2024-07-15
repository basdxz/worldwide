package com.mumfrey.worldeditcui.render;

import lombok.AllArgsConstructor;

import static org.lwjgl.opengl.GL11.*;

/**
 * Stores data about a line that can be rendered
 *
 * @author lahwran
 * @author yetanotherx
 */
@AllArgsConstructor
public final class LineStyle {
    private final float lineWidth;
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;
    private final int depthFunc;

    /**
     * Sets the lineWidth and depthFunction based on this colour
     */
    public void prepareRender() {
        glLineWidth(lineWidth);
        glDepthFunc(depthFunc);
    }

    public void prepareColour() {
        glColor4f(red, green, blue, alpha);
    }
}
