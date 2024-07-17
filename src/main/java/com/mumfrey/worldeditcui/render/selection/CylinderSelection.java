package com.mumfrey.worldeditcui.render.selection;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.config.LineColor;
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
public final class CylinderSelection extends Selection {
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

        new RenderCylinderCircles(LineColor.CYLINDER_GRID, center, radX, radZ, tMin, tMax).render();
        new RenderCylinderGrid(LineColor.CYLINDER_GRID, center, radX, radZ, tMin, tMax).render();
        new RenderCylinderBox(LineColor.CYLINDER_BOX, center, radX, radZ, tMin, tMax).render();
    }

    @Override
    public Selection setCylinderCenter(int x, int y, int z) {
        center = new PointCube(LineColor.CYLINDER_CENTER, x, y, z);
        return this;
    }

    @Override
    public Selection setCylinderRadius(double x, double z) {
        this.radX = x;
        this.radZ = z;
        return this;
    }

    @Override
    public Selection setMinMax(int min, int max) {
        this.minY = min;
        this.maxY = max;
        return this;
    }
}
