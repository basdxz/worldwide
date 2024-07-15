package com.mumfrey.worldeditcui.render.shapes;

import com.mumfrey.worldeditcui.render.LineStyle;
import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.util.Vector3;
import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws the grid for a region between
 * two corners in a cuboid region.
 *
 * @author yetanotherx
 */
@AllArgsConstructor
public final class Render3DGrid {
    private final LineStyles lineStyles;
    private final Vector3 first;
    private final Vector3 second;

    public void render() {
        val offsetSize = 1D;
        val msize = 150;

        val x1 = first.getX();
        val y1 = first.getY();
        val z1 = first.getZ();
        val x2 = second.getX();
        val y2 = second.getY();
        val z2 = second.getZ();

        for (val lineStyle : lineStyles) {
            lineStyle.prepareRender();

            glBegin(GL_LINES);
            lineStyle.prepareColour();

            var x = 0D;
            var y = 0D;
            var z = 0D;

            // Zmax XY plane, y axis
            z = z2;
            y = y1;
            if ((y2 - y / offsetSize) < msize) {
                for (var yoff = 0D; yoff + y <= y2; yoff += offsetSize) {
                    glVertex3d(x1, y + yoff, z);
                    glVertex3d(x2, y + yoff, z);
                }
            }

            // Zmin XY plane, y axis
            z = z1;
            if ((y2 - y / offsetSize) < msize) {
                for (var yoff = 0D; yoff + y <= y2; yoff += offsetSize) {
                    glVertex3d(x1, y + yoff, z);
                    glVertex3d(x2, y + yoff, z);
                }
            }

            // Xmin YZ plane, y axis
            x = x1;
            if ((y2 - y / offsetSize) < msize) {
                for (var yoff = 0D; yoff + y <= y2; yoff += offsetSize) {
                    glVertex3d(x, y + yoff, z1);
                    glVertex3d(x, y + yoff, z2);
                }
            }

            // Xmax YZ plane, y axis
            x = x2;
            if ((y2 - y / offsetSize) < msize) {
                for (var yoff = 0D; yoff + y <= y2; yoff += offsetSize) {
                    glVertex3d(x, y + yoff, z1);
                    glVertex3d(x, y + yoff, z2);
                }
            }

            // Zmin XY plane, x axis
            x = x1;
            z = z1;
            if ((x2 - x / offsetSize) < msize) {
                for (var xoff = 0D; xoff + x <= x2; xoff += offsetSize) {
                    glVertex3d(x + xoff, y1, z);
                    glVertex3d(x + xoff, y2, z);
                }
            }
            // Zmax XY plane, x axis
            z = z2;
            if ((x2 - x / offsetSize) < msize) {
                for (var xoff = 0D; xoff + x <= x2; xoff += offsetSize) {
                    glVertex3d(x + xoff, y1, z);
                    glVertex3d(x + xoff, y2, z);
                }
            }
            // Ymin XZ plane, x axis
            y = y2;
            if ((x2 - x / offsetSize) < msize) {
                for (var xoff = 0D; xoff + x <= x2; xoff += offsetSize) {
                    glVertex3d(x + xoff, y, z1);
                    glVertex3d(x + xoff, y, z2);
                }
            }
            // Ymax XZ plane, x axis
            y = y1;
            if ((x2 - x / offsetSize) < msize) {
                for (var xoff = 0D; xoff + x <= x2; xoff += offsetSize) {
                    glVertex3d(x + xoff, y, z1);
                    glVertex3d(x + xoff, y, z2);
                }
            }

            // Ymin XZ plane, z axis
            z = z1;
            y = y1;
            if ((z2 - z / offsetSize) < msize) {
                for (var zoff = 0D; zoff + z <= z2; zoff += offsetSize) {
                    glVertex3d(x1, y, z + zoff);
                    glVertex3d(x2, y, z + zoff);
                }
            }
            // Ymax XZ plane, z axis
            y = y2;
            if ((z2 - z / offsetSize) < msize) {
                for (var zoff = 0D; zoff + z <= z2; zoff += offsetSize) {
                    glVertex3d(x1, y, z + zoff);
                    glVertex3d(x2, y, z + zoff);
                }
            }
            // Xmin YZ plane, z axis
            x = x2;
            if ((z2 - z / offsetSize) < msize) {
                for (var zoff = 0D; zoff + z <= z2; zoff += offsetSize) {
                    glVertex3d(x, y1, z + zoff);
                    glVertex3d(x, y2, z + zoff);
                }
            }
            // Xmax YZ plane, z axis
            x = x1;
            if ((z2 - z / offsetSize) < msize) {
                for (var zoff = 0D; zoff + z <= z2; zoff += offsetSize) {
                    glVertex3d(x, y1, z + zoff);
                    glVertex3d(x, y2, z + zoff);
                }
            }

            glEnd();
        }
    }
}
