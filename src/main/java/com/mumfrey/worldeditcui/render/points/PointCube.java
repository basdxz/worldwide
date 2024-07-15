package com.mumfrey.worldeditcui.render.points;

import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.shapes.Render3DBox;
import com.mumfrey.worldeditcui.util.Vector3;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

/**
 * Stores data about a cube surrounding a
 * block in the world. Used to store info
 * about the selector blocks. Keeps track
 * of colour, x/y/z values, and rendering.
 *
 * @author yetanotherx
 * @author lahwran
 */
@Setter
@Getter
public final class PointCube {
    private Vector3 point;
    private LineStyles colour = LineStyles.CUBOIDPOINT1;

    public PointCube(Vector3 point) {
        this.point = point;
    }

    public PointCube(int x, int y, int z) {
        this.point = new Vector3(x, y, z);
    }

    public PointCube(double x, double y, double z) {
        this.point = new Vector3(x, y, z);
    }

    public void render() {
        val eps = 0.03F;
        val minVec = new Vector3(eps, eps, eps);
        val maxVec = new Vector3(eps + 1F, eps + 1F, eps + 1F);

        new Render3DBox(colour, point.subtract(minVec), point.add(maxVec)).render();
    }
}
