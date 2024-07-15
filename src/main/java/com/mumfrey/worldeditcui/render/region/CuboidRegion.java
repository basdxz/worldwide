package com.mumfrey.worldeditcui.render.region;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.points.PointCube;
import com.mumfrey.worldeditcui.render.shapes.Render3DBox;
import com.mumfrey.worldeditcui.render.shapes.Render3DGrid;
import com.mumfrey.worldeditcui.util.Vector3m;
import lombok.val;

/**
 * Main controller for a cuboid-type region
 *
 * @author yetanotherx
 * @author lahwran
 */
public final class CuboidRegion extends BaseRegion {
    private PointCube firstPoint;
    private PointCube secondPoint;

    public CuboidRegion(WorldEditCUIController controller) {
        super(controller);
    }

    @Override
    public RegionType getType() {
        return RegionType.CUBOID;
    }

    @Override
    public void render() {
        if (firstPoint != null && secondPoint != null) {
            val bounds = calcBounds();
            new Render3DGrid(LineStyles.CUBOIDGRID, bounds[0], bounds[1]).render();
            new Render3DBox(LineStyles.CUBOIDBOX, bounds[0], bounds[1]).render();

            firstPoint.render();
            secondPoint.render();
        } else if (firstPoint != null) {
            firstPoint.render();
        } else if (secondPoint != null) {
            secondPoint.render();
        }
    }

    @Override
    public BaseRegion setCuboidPoint(int id, double x, double y, double z) {
        if (id == 0) {
            firstPoint = new PointCube(x, y, z);
            firstPoint.setColour(LineStyles.CUBOIDPOINT1);
        } else if (id == 1) {
            secondPoint = new PointCube(x, y, z);
            secondPoint.setColour(LineStyles.CUBOIDPOINT2);
        }
        return this;
    }

    /**
     * Calculates the boundary points of the actual box.
     * I have no idea what I'm doing.
     *
     * @return
     */
    private Vector3m[] calcBounds() {
        val off = 0.02F;
        val off1 = 1 + off;

        val out = new Vector3m[]{new Vector3m(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE),
                                 new Vector3m(-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE)};

        for (val point : new PointCube[]{firstPoint, secondPoint}) {
            if (point.getPoint().getX() + off1 > out[1].getX())
                out[1].setX(point.getPoint().getX() + off1);
            if (point.getPoint().getX() - off < out[0].getX())
                out[0].setX(point.getPoint().getX() - off);
            if (point.getPoint().getY() + off1 > out[1].getY())
                out[1].setY(point.getPoint().getY() + off1);
            if (point.getPoint().getY() - off < out[0].getY())
                out[0].setY(point.getPoint().getY() - off);
            if (point.getPoint().getZ() + off1 > out[1].getZ())
                out[1].setZ(point.getPoint().getZ() + off1);
            if (point.getPoint().getZ() - off < out[0].getZ())
                out[0].setZ(point.getPoint().getZ() - off);
        }

        return out;
    }
}
