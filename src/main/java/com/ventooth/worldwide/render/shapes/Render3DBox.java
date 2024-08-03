package com.ventooth.worldwide.render.shapes;

import com.ventooth.worldwide.config.LineColor;
import lombok.AllArgsConstructor;
import lombok.val;
import org.joml.Vector3dc;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws a rectangular prism around 2 corners
 *
 * @author yetanotherx
 * @author lahwran
 */
@AllArgsConstructor
public final class Render3DBox extends Render {
    private final LineColor color;

    private final Vector3dc first;
    private final Vector3dc second;

    @Override
    protected void renderImpl(boolean isVisible) {
        val x1 = first.x();
        val y1 = first.y();
        val z1 = first.z();
        val x2 = second.x();
        val y2 = second.y();
        val z2 = second.z();

        // Draw bottom face
        glBegin(GL_LINE_LOOP);
        color.setGlColor(isVisible);
        glVertex3d(x1, y1, z1);
        glVertex3d(x2, y1, z1);
        glVertex3d(x2, y1, z2);
        glVertex3d(x1, y1, z2);
        GL11.glEnd();

        // Draw top face
        glBegin(GL_LINE_LOOP);
        color.setGlColor(isVisible);
        glVertex3d(x1, y2, z1);
        glVertex3d(x2, y2, z1);
        glVertex3d(x2, y2, z2);
        glVertex3d(x1, y2, z2);
        GL11.glEnd();

        // Draw join top and bottom faces
        glBegin(GL_LINES);
        color.setGlColor(isVisible);

        glVertex3d(x1, y1, z1);
        glVertex3d(x1, y2, z1);

        glVertex3d(x2, y1, z1);
        glVertex3d(x2, y2, z1);

        glVertex3d(x2, y1, z2);
        glVertex3d(x2, y2, z2);

        glVertex3d(x1, y1, z2);
        glVertex3d(x1, y2, z2);

        glEnd();
    }
}
