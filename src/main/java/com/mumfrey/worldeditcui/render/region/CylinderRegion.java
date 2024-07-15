package com.mumfrey.worldeditcui.render.region;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.points.PointCube;
import com.mumfrey.worldeditcui.render.shapes.RenderCylinderBox;
import com.mumfrey.worldeditcui.render.shapes.RenderCylinderCircles;
import com.mumfrey.worldeditcui.render.shapes.RenderCylinderGrid;
import lombok.var;

/**
 * Main controller for a cylinder-type region
 *
 * @author yetanotherx
 */
public final class CylinderRegion extends BaseRegion {
    private PointCube center;
    private double radX = 0;
    private double radZ = 0;
    private int minY = 0;
    private int maxY = 0;

    public CylinderRegion(WorldEditCUIController controller) {
        super(controller);
    }
	
	@Override
	public RegionType getType() {
		return RegionType.CYLINDER;
	}
	
    @Override
    public void render() {
        if (center == null)
            return;

        center.render();

        var tMin = minY;
        var tMax = maxY;
        if (minY == 0 || maxY == 0) {
            tMin = (int) center.getPoint().getY();
            tMax = (int) center.getPoint().getY();
        }

        new RenderCylinderCircles(LineStyles.CYLINDERGRID, center, radX, radZ, tMin, tMax).render();
        new RenderCylinderGrid(LineStyles.CYLINDERGRID, center, radX, radZ, tMin, tMax).render();
        new RenderCylinderBox(LineStyles.CYLINDERBOX, center, radX, radZ, tMin, tMax).render();
    }

    @Override
    public BaseRegion setCylinderCenter(int x, int y, int z) {
        center = new PointCube(x, y, z);
        center.setColour(LineStyles.CYLINDERCENTER);
        return this;
    }

    @Override
    public BaseRegion setCylinderRadius(double x, double z) {
        this.radX = x;
        this.radZ = z;
        return this;
    }

    @Override
    public BaseRegion setMinMax(int min, int max) {
        this.minY = min;
        this.maxY = max;
        return this;
    }
}
