package com.mumfrey.worldeditcui.render.shapes;

import com.mumfrey.worldeditcui.render.LineStyle;
import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.util.Vector3;
import lombok.AllArgsConstructor;
import lombok.val;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws a polygon
 *
 * @author yetanotherx
 * @author lahwran
 */
@AllArgsConstructor
public final class Render3DPolygon {
    private final LineStyles lineStyles;
    private final Vector3[] vertices;

    public void render() {
        for (val lineStyle : lineStyles) {
            lineStyle.prepareRender();

            glBegin(GL_LINE_LOOP);
            lineStyle.prepareColour();
            for (Vector3 vertex : this.vertices) {
                glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
            }
            glEnd();
        }
    }
}
