package com.mumfrey.worldeditcui.render.shapes;

import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.points.PointCube;
import lombok.val;
import lombok.var;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws the top and bottom circles around a cylindrical region
 *
 * @author yetanotherx
 */
public final class RenderCylinderBox {
    private final LineStyles lineStyles;
    private final double radX;
    private final double radZ;
    private final int minY;
    private final int maxY;
    private final double centerX;
    private final double centerZ;

    public RenderCylinderBox(LineStyles lineStyles, PointCube center, double radX, double radZ, int minY, int maxY) {
        this.lineStyles = lineStyles;
        this.radX = radX;
        this.radZ = radZ;
        this.minY = minY;
        this.maxY = maxY;
        this.centerX = center.x() + 0.5D;
        this.centerZ = center.z() + 0.5D;
    }

    public void render() {
        for (val lineStyle : lineStyles) {
            lineStyle.prepareRender();

            for (var yBlock : new int[]{this.minY, this.maxY + 1}) {
                glBegin(GL_LINE_LOOP);
                lineStyle.prepareColour();

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
}
