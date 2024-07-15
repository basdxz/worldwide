package com.mumfrey.worldeditcui.render.shapes;

import com.mumfrey.worldeditcui.render.LineStyle;
import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.util.Vector3;
import lombok.AllArgsConstructor;
import lombok.val;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws a rectangular prism around 2 corners
 *
 * @author yetanotherx
 * @author lahwran
 */
@AllArgsConstructor
public final class Render3DBox {
    private final LineStyles lineStyles;
    private final Vector3 first;
    private final Vector3 second;

    public void render() {
        val x1 = this.first.getX();
        val y1 = this.first.getY();
        val z1 = this.first.getZ();
        val x2 = this.second.getX();
        val y2 = this.second.getY();
        val z2 = this.second.getZ();

        for (val lineStyle : lineStyles) {
            lineStyle.prepareRender();

            // Draw bottom face
            glBegin(GL_LINE_LOOP);
            lineStyle.prepareColour();
            glVertex3d(x1, y1, z1);
            glVertex3d(x2, y1, z1);
            glVertex3d(x2, y1, z2);
            glVertex3d(x1, y1, z2);
            GL11.glEnd();

            // Draw top face
            glBegin(GL_LINE_LOOP);
            lineStyle.prepareColour();
            glVertex3d(x1, y2, z1);
            glVertex3d(x2, y2, z1);
            glVertex3d(x2, y2, z2);
            glVertex3d(x1, y2, z2);
            GL11.glEnd();

            // Draw join top and bottom faces
            glBegin(GL_LINES);
            lineStyle.prepareColour();

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
}
