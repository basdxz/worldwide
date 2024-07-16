package com.mumfrey.worldeditcui.render.region;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.points.PointRectangle;
import com.mumfrey.worldeditcui.render.shapes.Render2DBox;
import com.mumfrey.worldeditcui.render.shapes.Render2DGrid;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

/**
 * Main controller for a polygon-type region
 *
 * @author yetanotherx
 * @author lahwran
 */
public final class PolygonRegion extends BaseRegion {
    private final List<PointRectangle> points = new ArrayList<>();

    private int min;
    private int max;

    public PolygonRegion(WorldEditCUIController controller) {
        super(controller);
    }

    @Override
    public void render() {
        for (val point : points)
            point.render(min, max);

        new Render2DBox(LineStyles.POLYBOX, points, min, max).render();
        new Render2DGrid(LineStyles.POLYGRID, points, min, max).render();
    }

    @Override
    public BaseRegion setMinMax(int min, int max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public BaseRegion setPolygonPoint(int id, int x, int z) {
        val point = new PointRectangle(LineStyles.POLYPOINT, x, z);

        if (id < points.size()) {
            points.set(id, point);
        } else {
            for (int i = 0; i < id - points.size(); i++) {
                points.add(null);
            }
            points.add(point);
        }
        return this;
    }

    @Override
    public RegionType getType() {
        return RegionType.POLYGON;
    }
}
