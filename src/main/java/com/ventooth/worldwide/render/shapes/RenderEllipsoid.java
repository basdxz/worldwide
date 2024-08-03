package com.ventooth.worldwide.render.shapes;

import com.ventooth.worldwide.config.LineColor;
import com.ventooth.worldwide.render.points.PointCube;
import lombok.val;
import lombok.var;
import org.joml.Vector3dc;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws an ellipsoid shape around a center point.
 *
 * @author yetanotherx
 */
public final class RenderEllipsoid extends Render {
    private final LineColor color;

    private final Vector3dc radii;
    private final double centerX;
    private final double centerY;
    private final double centerZ;

    public RenderEllipsoid(LineColor color, PointCube center, Vector3dc radii) {
        this.color = color;
        
        this.radii = radii;
        this.centerX = center.x() + 0.5D;
        this.centerY = center.y() + 0.5D;
        this.centerZ = center.z() + 0.5D;
    }

    @Override
    protected void renderImpl(boolean isVisible) {
        drawXZPlane(isVisible);
        drawYZPlane(isVisible);
        drawXYPlane(isVisible);
    }

    private void drawXZPlane(boolean isVisible) {
        val yRad = (int) Math.floor(radii.y());
        for (var yBlock = -yRad; yBlock < yRad; yBlock++) {
            glBegin(GL_LINE_LOOP);
            color.setGlColor(isVisible);

            for (var i = 0; i <= 40; i++) {
                val tempTheta = i * (Math.PI * 2 / 40D);
                val tempX = radii.x() * Math.cos(tempTheta) * Math.cos(Math.asin(yBlock / radii.y()));
                val tempZ = radii.z() * Math.sin(tempTheta) * Math.cos(Math.asin(yBlock / radii.y()));
                glVertex3d(centerX + tempX, centerY + yBlock, centerZ + tempZ);
            }
            glEnd();
        }

        glBegin(GL_LINE_LOOP);
        color.setGlColor(isVisible);

        for (var i = 0; i <= 40; i++) {
            val tempTheta = i * (Math.PI * 2 / 40D);
            val tempX = radii.x() * Math.cos(tempTheta);
            val tempZ = radii.z() * Math.sin(tempTheta);

            glVertex3d(centerX + tempX, centerY, centerZ + tempZ);
        }
        glEnd();
    }

    private void drawYZPlane(boolean isVisible) {
        val xRad = (int) Math.floor(radii.x());
        for (var xBlock = -xRad; xBlock < xRad; xBlock++) {
            glBegin(GL_LINE_LOOP);
            color.setGlColor(isVisible);

            for (var i = 0; i <= 40; i++) {
                val tempTheta = (Math.PI * 2 / 40D) * i;
                val tempY = radii.y() * Math.cos(tempTheta) * Math.sin(Math.acos(xBlock / radii.x()));
                val tempZ = radii.z() * Math.sin(tempTheta) * Math.sin(Math.acos(xBlock / radii.x()));

                glVertex3d(centerX + xBlock, centerY + tempY, centerZ + tempZ);
            }
            glEnd();
        }

        glBegin(GL_LINE_LOOP);
        color.setGlColor(isVisible);

        for (var i = 0; i <= 40; i++) {
            val tempTheta = (Math.PI * 2 / 40D) * i;
            val tempY = radii.y() * Math.cos(tempTheta);
            val tempZ = radii.z() * Math.sin(tempTheta);

            glVertex3d(centerX, centerY + tempY, centerZ + tempZ);
        }
        glEnd();
    }

    private void drawXYPlane(boolean isVisible) {
        val zRad = (int) Math.floor(radii.z());
        for (var zBlock = -zRad; zBlock < zRad; zBlock++) {
            glBegin(GL_LINE_LOOP);
            color.setGlColor(isVisible);

            for (var i = 0; i <= 40; i++) {
                val tempTheta = (Math.PI * 2 / 40D) * i;
                val tempX = radii.x() * Math.sin(tempTheta) * Math.sin(Math.acos(zBlock / radii.z()));
                val tempY = radii.y() * Math.cos(tempTheta) * Math.sin(Math.acos(zBlock / radii.z()));

                glVertex3d(centerX + tempX, centerY + tempY, centerZ + zBlock);
            }
            glEnd();
        }

        glBegin(GL_LINE_LOOP);
        color.setGlColor(isVisible);

        for (var i = 0; i <= 40; i++) {
            val tempTheta = (Math.PI * 2 / 40D) * i;
            val tempX = radii.x() * Math.cos(tempTheta);
            val tempY = radii.y() * Math.sin(tempTheta);

            glVertex3d(centerX + tempX, centerY + tempY, centerZ);
        }
        glEnd();
    }
}
