package com.mumfrey.worldeditcui.render.selection;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.points.PointCube;
import com.mumfrey.worldeditcui.render.shapes.RenderCylinderBox;
import com.mumfrey.worldeditcui.render.shapes.RenderCylinderCircles;
import com.mumfrey.worldeditcui.render.shapes.RenderCylinderGrid;
import lombok.var;

/**
 * Main controller for a cylinder-type selection
 *
 * @author yetanotherx
 */
public final class CylinderSelection extends SelectionBase {
    private PointCube center;
    private double radX = 0;
    private double radZ = 0;
    private int minY = 0;
    private int maxY = 0;

    public CylinderSelection(WorldEditCUIController controller) {
        super(controller);
    }
	
	@Override
    public SelectionType type() {
        return SelectionType.CYLINDER;
	}
	
    @Override
    public void render() {
        if (center == null)
            return;

        center.render();

        var tMin = minY;
        var tMax = maxY;
        if (minY == 0 || maxY == 0) {
            tMin = (int) center.y();
            tMax = (int) center.y();
        }

        new RenderCylinderCircles(LineStyles.CYLINDERGRID, center, radX, radZ, tMin, tMax).render();
        new RenderCylinderGrid(LineStyles.CYLINDERGRID, center, radX, radZ, tMin, tMax).render();
        new RenderCylinderBox(LineStyles.CYLINDERBOX, center, radX, radZ, tMin, tMax).render();
    }

    @Override
    public SelectionBase setCylinderCenter(int x, int y, int z) {
        center = new PointCube(LineStyles.CYLINDERCENTER, x, y, z);
        return this;
    }

    @Override
    public SelectionBase setCylinderRadius(double x, double z) {
        this.radX = x;
        this.radZ = z;
        return this;
    }

    @Override
    public SelectionBase setMinMax(int min, int max) {
        this.minY = min;
        this.maxY = max;
        return this;
    }
}
