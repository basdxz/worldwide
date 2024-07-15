package com.mumfrey.worldeditcui.render.points;

import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.shapes.Render3DBox;
import com.mumfrey.worldeditcui.util.Vector2;
import lombok.Getter;
import lombok.Setter;

/**
 * Stores data about a prism surrounding two
 * blocks in the world. Used to store info
 * about the selector blocks for polys. Keeps
 * track of colour, x/y/z values, and rendering.
 *
 * @author yetanotherx
 * @author lahwran
 */

@Setter
@Getter
public final class PointRectangle {
    private Vector2 point;
    private LineStyles colour = LineStyles.POLYPOINT;

    public PointRectangle(Vector2 point) {
        this.point = point;
    }

    public PointRectangle(int x, int z) {
        this.point = new Vector2(x, z);
    }

    public void render(int min, int max) {
        float off = 0.03f;
        Vector2 minVec = new Vector2(off, off);
        Vector2 maxVec = new Vector2(off + 1, off + 1);

        new Render3DBox(colour, point.subtract(minVec).toVector3(min - off), point.add(maxVec).toVector3(max + 1 + off)).render();
    }
}
