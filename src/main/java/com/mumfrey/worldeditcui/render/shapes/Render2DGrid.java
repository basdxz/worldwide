package com.mumfrey.worldeditcui.render.shapes;

import com.mumfrey.worldeditcui.config.LineColor;
import com.mumfrey.worldeditcui.render.points.PointRectangle;
import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws the grid for a polygon selection
 *
 * @author yetanotherx
 * @author lahwran
 */

@AllArgsConstructor
public final class Render2DGrid extends Render {
    private final LineColor color;

    private final List<PointRectangle> points;
    private final int min;
    private final int max;

    @Override
    protected void renderImpl(boolean isVisible) {
        val eps = 0.03D;
        for (var heightOff = min; heightOff <= max + 1D; heightOff++) {
            val height = heightOff + eps;

            glBegin(GL_LINE_LOOP);
            color.setGlColor(isVisible);
            for (val point : this.points) {
                if (point != null)
                    glVertex3d(point.x() + 0.5D, height, point.y() + 0.5D);
            }
            glEnd();
        }
    }
}
