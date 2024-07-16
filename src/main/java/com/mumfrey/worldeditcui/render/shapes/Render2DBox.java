package com.mumfrey.worldeditcui.render.shapes;

import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.points.PointRectangle;
import lombok.AllArgsConstructor;
import lombok.val;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws the top and bottom rings of a polygon region
 *
 * @author yetanotherx
 * @author lahwran
 */
@AllArgsConstructor
public final class Render2DBox {
    private final LineStyles lineStyles;
    private final List<PointRectangle> points;
    private final int min;
    private final int max;

    public void render() {
        val eps = 0.03D;
        for (val lineStyle : lineStyles) {
            lineStyle.prepareRender();

            glBegin(GL_LINES);
            lineStyle.prepareColour();

            for (PointRectangle point : points) {
                if (point != null) {
                    glVertex3d(point.x() + 0.5D, min + eps, point.y() + 0.5D);
                    glVertex3d(point.x() + 0.5D, max + eps + 1D, point.y() + 0.5D);
                }
            }
            GL11.glEnd();
        }
    }
}
