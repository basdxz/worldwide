package com.ventooth.worldwide.render.selection;

import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.config.LineColor;
import com.ventooth.worldwide.render.points.PointCube;
import com.ventooth.worldwide.render.shapes.Render3DBox;
import com.ventooth.worldwide.render.shapes.Render3DGrid;
import lombok.val;
import org.joml.Vector3d;

/**
 * Main controller for a cuboid-type selection
 *
 * @author yetanotherx
 * @author lahwran
 */
public final class CuboidSelection extends Selection {
    private PointCube firstPoint;
    private PointCube secondPoint;

    public CuboidSelection(WorldEditCUIController controller) {
        super(controller);
    }

    @Override
    public SelectionType type() {
        return SelectionType.CUBOID;
    }

    @Override
    public void render() {
        if (firstPoint != null && secondPoint != null) {
            val bounds = calcBounds();
            new Render3DGrid(LineColor.CUBOID_GRID, bounds[0], bounds[1]).render();
            new Render3DBox(LineColor.CUBOID_BOX, bounds[0], bounds[1]).render();

            firstPoint.render();
            secondPoint.render();
        } else if (firstPoint != null) {
            firstPoint.render();
        } else if (secondPoint != null) {
            secondPoint.render();
        }
    }

    @Override
    public Selection setCuboidPoint(int id, double x, double y, double z) {
        if (id == 0) {
            firstPoint = new PointCube(LineColor.CUBOID_POINT_1, x, y, z);
        } else if (id == 1) {
            secondPoint = new PointCube(LineColor.CUBOID_POINT_2, x, y, z);
        }
        return this;
    }

    /**
     * Calculates the boundary points of the actual box.
     * I have no idea what I'm doing.
     *
     * @return
     */
    private Vector3d[] calcBounds() {
        val min = 0.02D;
        val max = min + 1D;

        val out = new Vector3d[]{new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE),
                                 new Vector3d(-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE)};

        for (val point : new PointCube[]{firstPoint, secondPoint}) {
            if (point.x() + max > out[1].x())
                out[1].x = point.x() + max;
            if (point.x() - min < out[0].x())
                out[0].x = (point.x() - min);
            if (point.y() + max > out[1].y())
                out[1].y = (point.y() + max);
            if (point.y() - min < out[0].y())
                out[0].y = (point.y() - min);
            if (point.z() + max > out[1].z())
                out[1].z = (point.z() + max);
            if (point.z() - min < out[0].z())
                out[0].z = (point.z() - min);
        }

        return out;
    }
}
