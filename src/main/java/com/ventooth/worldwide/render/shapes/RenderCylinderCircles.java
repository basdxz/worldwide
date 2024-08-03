package com.ventooth.worldwide.render.shapes;

import com.ventooth.worldwide.config.LineColor;
import com.ventooth.worldwide.render.points.PointCube;
import lombok.val;
import lombok.var;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws the circles around a cylindrical selection
 *
 * @author yetanotherx
 */
public final class RenderCylinderCircles extends Render {
    private final LineColor color;

    private final double radX;
    private final double radZ;
    private final int minY;
    private final int maxY;
    private final double centerX;
    private final double centerZ;

    public RenderCylinderCircles(LineColor color, PointCube center, double radX, double radZ, int minY, int maxY) {
        this.color = color;

        this.radX = radX;
        this.radZ = radZ;
        this.minY = minY;
        this.maxY = maxY;
        this.centerX = center.x() + 0.5D;
        this.centerZ = center.z() + 0.5D;
    }

    @Override
    protected void renderImpl(boolean isVisible) {
        for (var yBlock = minY + 1; yBlock <= maxY; yBlock++) {
            glBegin(GL_LINE_LOOP);
            color.setGlColor(isVisible);

            for (var i = 0; i <= 75; i++) {
                val tempTheta = (i * Math.PI * 2) / 75;
                val tempX = radX * Math.cos(tempTheta);
                val tempZ = radZ * Math.sin(tempTheta);

                glVertex3d(centerX + tempX, yBlock, centerZ + tempZ);
            }
            glEnd();
        }
    }
}
