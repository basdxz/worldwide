package com.mumfrey.worldeditcui.render.shapes;

import com.mumfrey.worldeditcui.config.LineColor;
import com.mumfrey.worldeditcui.render.points.PointCube;
import lombok.val;
import lombok.var;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws the grid lines around a cylindrical selection
 *
 * @author yetanotherx
 */
public final class RenderCylinderGrid extends Render {
    private final LineColor color;

    private final double radX;
    private final double radZ;
    private final int minY;
    private final int maxY;
    private final double centerX;
    private final double centerZ;

    public RenderCylinderGrid(LineColor color, PointCube center, double radX, double radZ, int minY, int maxY) {
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
        val tmaxY = maxY + 1;
        val tminY = minY;
        val posRadiusX = (int) Math.ceil(radX);
        val negRadiusX = (int) -Math.ceil(radX);
        val posRadiusZ = (int) Math.ceil(radZ);
        val negRadiusZ = (int) -Math.ceil(radZ);

        for (var tempX = negRadiusX; tempX <= posRadiusX; ++tempX) {
            val tempZ = radZ * Math.cos(Math.asin(tempX / radX));
            glBegin(GL_LINE_LOOP);
            color.setGlColor(isVisible);

            glVertex3d(centerX + tempX, tmaxY, centerZ + tempZ);
            glVertex3d(centerX + tempX, tmaxY, centerZ - tempZ);
            glVertex3d(centerX + tempX, tminY, centerZ - tempZ);
            glVertex3d(centerX + tempX, tminY, centerZ + tempZ);

            glEnd();
        }

        for (var tempZ = negRadiusZ; tempZ <= posRadiusZ; ++tempZ) {
            val tempX = radX * Math.sin(Math.acos(tempZ / radZ));
            glBegin(GL_LINE_LOOP);
            color.setGlColor(isVisible);

            glVertex3d(centerX + tempX, tmaxY, centerZ + tempZ);
            glVertex3d(centerX - tempX, tmaxY, centerZ + tempZ);
            glVertex3d(centerX - tempX, tminY, centerZ + tempZ);
            glVertex3d(centerX + tempX, tminY, centerZ + tempZ);

            glEnd();
        }
    }
}
