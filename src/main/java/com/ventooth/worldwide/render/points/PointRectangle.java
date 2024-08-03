package com.ventooth.worldwide.render.points;

import com.ventooth.worldwide.config.LineColor;
import com.ventooth.worldwide.render.shapes.Render3DBox;
import lombok.AllArgsConstructor;
import lombok.val;
import org.joml.Vector3d;

/**
 * Stores data about a prism surrounding two
 * blocks in the world. Used to store info
 * about the selector blocks for polys. Keeps
 * track of colour, x/y/z values, and rendering.
 *
 * @author yetanotherx
 * @author lahwran
 */
@AllArgsConstructor
public final class PointRectangle {
    private final LineColor color;

    private final double x;
    private final double y;

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public void render(double min, double max) {
        val eps = 0.03D;
        val first = new Vector3d(x, y, min).sub(eps, eps, eps);
        val second = new Vector3d(x, y, max).add(eps + 1D, eps + 1D, eps + 1D);
        new Render3DBox(color, first, second).render();
    }
}
