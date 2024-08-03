package com.ventooth.worldwide.render.shapes;

import com.ventooth.worldwide.config.LineColor;
import lombok.AllArgsConstructor;
import lombok.val;
import org.joml.Vector3dc;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws a polygon
 *
 * @author yetanotherx
 * @author lahwran
 */
@AllArgsConstructor
public final class Render3DPolygon extends Render {
    private final LineColor color;

    private final Vector3dc[] vertices;

    @Override
    protected void renderImpl(boolean isVisible) {
        glBegin(GL_LINE_LOOP);
        color.setGlColor(isVisible);
        for (val vertex : this.vertices)
            glVertex3d(vertex.x(), vertex.y(), vertex.z());
        glEnd();
    }
}
