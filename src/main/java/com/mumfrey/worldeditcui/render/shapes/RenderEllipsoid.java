package com.mumfrey.worldeditcui.render.shapes;

import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.LineStyle;
import com.mumfrey.worldeditcui.render.points.PointCube;
import com.mumfrey.worldeditcui.util.Vector3;
import lombok.val;
import lombok.var;

import static com.mumfrey.worldeditcui.render.RenderUtils.PI_2;
import static org.lwjgl.opengl.GL11.*;

/**
 * Draws an ellipsoid shape around a center point.
 *
 * @author yetanotherx
 */
public final class RenderEllipsoid {
    private final LineStyles lineStyles;
    private final Vector3 radii;
    
    private final double centerX;
    private final double centerY;
    private final double centerZ;

    public RenderEllipsoid(LineStyles lineStyles, PointCube center, Vector3 radii) {
        this.lineStyles = lineStyles;
        this.radii = radii;

        val centerPoint = center.getPoint();
        this.centerX = centerPoint.getX() + 0.5D;
        this.centerY = centerPoint.getY() + 0.5D;
        this.centerZ = centerPoint.getZ() + 0.5D;
    }

    public void render() {
        for (val lineStyle : lineStyles) {
            lineStyle.prepareRender();
            drawXZPlane(lineStyle);
            drawYZPlane(lineStyle);
            drawXYPlane(lineStyle);
        }
    }

    private void drawXZPlane(LineStyle colour) {
        val yRad = (int) Math.floor(radii.getY());
        for (var yBlock = -yRad; yBlock < yRad; yBlock++) {
            glBegin(GL_LINE_LOOP);
            colour.prepareColour();

            for (int i = 0; i <= 40; i++) {
                val tempTheta = i * PI_2 / 40;
                val tempX = radii.getX() * Math.cos(tempTheta) * Math.cos(Math.asin(yBlock / radii.getY()));
                val tempZ = radii.getZ() * Math.sin(tempTheta) * Math.cos(Math.asin(yBlock / radii.getY()));

                glVertex3d(centerX + tempX, centerY + yBlock, centerZ + tempZ);
            }
            glEnd();
        }

        glBegin(GL_LINE_LOOP);
        colour.prepareColour();

        for (var i = 0; i <= 40; i++) {
            val tempTheta = i * PI_2 / 40;
            val tempX = radii.getX() * Math.cos(tempTheta);
            val tempZ = radii.getZ() * Math.sin(tempTheta);

            glVertex3d(centerX + tempX, centerY, centerZ + tempZ);
        }
        glEnd();
    }

    private void drawYZPlane(LineStyle colour) {
        val xRad = (int) Math.floor(radii.getX());
        for (var xBlock = -xRad; xBlock < xRad; xBlock++) {
            glBegin(GL_LINE_LOOP);
            colour.prepareColour();

            for (var i = 0; i <= 40; i++) {
                val tempTheta = i * PI_2 / 40;
                val tempY = radii.getY() * Math.cos(tempTheta) * Math.sin(Math.acos(xBlock / radii.getX()));
                val tempZ = radii.getZ() * Math.sin(tempTheta) * Math.sin(Math.acos(xBlock / radii.getX()));

                glVertex3d(centerX + xBlock, centerY + tempY, centerZ + tempZ);
            }
            glEnd();
        }

        glBegin(GL_LINE_LOOP);
        colour.prepareColour();

        for (var i = 0; i <= 40; i++) {
            val tempTheta = i * PI_2 / 40;
            val tempY = radii.getY() * Math.cos(tempTheta);
            val tempZ = radii.getZ() * Math.sin(tempTheta);

            glVertex3d(centerX, centerY + tempY, centerZ + tempZ);
        }
        glEnd();
    }

    private void drawXYPlane(LineStyle colour) {
        val zRad = (int) Math.floor(radii.getZ());
        for (var zBlock = -zRad; zBlock < zRad; zBlock++) {
            glBegin(GL_LINE_LOOP);
            colour.prepareColour();

            for (var i = 0; i <= 40; i++) {
                val tempTheta = i * PI_2 / 40;
                val tempX = radii.getX() * Math.sin(tempTheta) * Math.sin(Math.acos(zBlock / radii.getZ()));
                val tempY = radii.getY() * Math.cos(tempTheta) * Math.sin(Math.acos(zBlock / radii.getZ()));

                glVertex3d(centerX + tempX, centerY + tempY, centerZ + zBlock);
            }
            glEnd();
        }

        glBegin(GL_LINE_LOOP);
        colour.prepareColour();

        for (var i = 0; i <= 40; i++) {
            val tempTheta = i * PI_2 / 40;
            val tempX = radii.getX() * Math.cos(tempTheta);
            val tempY = radii.getY() * Math.sin(tempTheta);

            glVertex3d(centerX + tempX, centerY + tempY, centerZ);
        }
        glEnd();
    }
}
