package com.mumfrey.worldeditcui.render.shapes;

import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.points.PointCube;
import lombok.val;

import static com.mumfrey.worldeditcui.render.RenderUtils.PI_2;
import static org.lwjgl.opengl.GL11.*;

/**
 * Draws the circles around a cylindrical region
 *
 * @author yetanotherx
 */
public final class RenderCylinderCircles {
    private final LineStyles lineStyles;
    private final double radX;
    private final double radZ;
    private final int minY;
    private final int maxY;

    private final double centerX;
    private final double centerZ;

    public RenderCylinderCircles(LineStyles lineStyles, PointCube center, double radX, double radZ, int minY, int maxY) {
        this.lineStyles = lineStyles;
        this.radX = radX;
        this.radZ = radZ;
        this.minY = minY;
        this.maxY = maxY;

        val centerPoint = center.getPoint();
        this.centerX = centerPoint.getX() + 0.5D;
        this.centerZ = centerPoint.getZ() + 0.5D;
    }

    public void render() {
        for (val lineStyle : lineStyles) {
            lineStyle.prepareRender();

            for (int yBlock = this.minY + 1; yBlock <= this.maxY; yBlock++) {
                glBegin(GL_LINE_LOOP);
                lineStyle.prepareColour();

                for (int i = 0; i <= 75; i++) {
                    double tempTheta = i * PI_2 / 75;
                    double tempX = this.radX * Math.cos(tempTheta);
                    double tempZ = this.radZ * Math.sin(tempTheta);

                    glVertex3d(this.centerX + tempX, yBlock, this.centerZ + tempZ);
                }
                glEnd();
            }
        }
    }
}
