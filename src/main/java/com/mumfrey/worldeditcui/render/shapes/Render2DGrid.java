package com.mumfrey.worldeditcui.render.shapes;

import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.points.PointRectangle;
import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws the grid for a polygon region
 *
 * @author yetanotherx
 * @author lahwran
 */

@AllArgsConstructor
public final class Render2DGrid {
    private final LineStyles lineStyles;
    private final List<PointRectangle> points;
    private final int min;
    private final int max;

    public void render() {
        val eps = 0.03D;
        for (var height = min; height <= max + 1D; height++)
            drawPoly(height + eps);
    }

    private void drawPoly(double height) {
        for (val lineStyle : lineStyles) {
            lineStyle.prepareRender();

            glBegin(GL_LINE_LOOP);
            lineStyle.prepareColour();
            for (val rectPoint : this.points) {
                if (rectPoint != null) {
                    val point = rectPoint.getPoint();
                    glVertex3d(point.getX() + 0.5D,
                               height,
                               point.getY() + 0.5D);
                }
            }
            glEnd();
        }
    }
}
