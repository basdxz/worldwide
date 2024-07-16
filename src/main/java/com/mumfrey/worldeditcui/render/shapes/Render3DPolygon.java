package com.mumfrey.worldeditcui.render.shapes;

import com.mumfrey.worldeditcui.render.LineStyles;
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
public final class Render3DPolygon {
    private final LineStyles lineStyles;
    private final Vector3dc[] vertices;

    public void render() {
        for (val lineStyle : lineStyles) {
            lineStyle.prepareRender();

            glBegin(GL_LINE_LOOP);
            lineStyle.prepareColour();
            for (val vertex : this.vertices)
                glVertex3d(vertex.x(), vertex.y(), vertex.z());
            glEnd();
        }
    }
}
