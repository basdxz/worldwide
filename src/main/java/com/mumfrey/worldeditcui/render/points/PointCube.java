package com.mumfrey.worldeditcui.render.points;

import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.shapes.Render3DBox;
import lombok.AllArgsConstructor;
import lombok.val;
import org.joml.Vector3d;

/**
 * Stores data about a cube surrounding a
 * block in the world. Used to store info
 * about the selector blocks. Keeps track
 * of colour, x/y/z values, and rendering.
 *
 * @author yetanotherx
 * @author lahwran
 */
@AllArgsConstructor
public final class PointCube {
    private final LineStyles lineStyles;

    private final double x;
    private final double y;
    private final double z;

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public void render() {
        val eps = 0.03D;
        val first = new Vector3d(x, y, z).sub(eps, eps, eps);
        val second = new Vector3d(x, y, z).add(eps + 1D, eps + 1D, eps + 1D);
        new Render3DBox(lineStyles, first, second).render();
    }
}
