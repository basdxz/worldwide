package com.mumfrey.worldeditcui.render.region;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.points.PointCube;
import com.mumfrey.worldeditcui.render.shapes.RenderEllipsoid;
import com.mumfrey.worldeditcui.util.Vector3;

/**
 * Main controller for a ellipsoid-type region
 *
 * @author yetanotherx
 * @author lahwran
 */
public final class EllipsoidRegion extends BaseRegion {
    private PointCube center;
    private Vector3 radii;

    public EllipsoidRegion(WorldEditCUIController controller) {
        super(controller);
    }

    @Override
    public RegionType getType() {
        return RegionType.ELLIPSOID;
    }

    @Override
    public void render() {
        if (center != null && radii != null) {
            center.render();
            new RenderEllipsoid(LineStyles.ELLIPSOIDGRID, center, radii).render();
        } else if (center != null) {
            center.render();
        }
    }

    @Override
    public BaseRegion setEllipsoidCenter(int x, int y, int z) {
        center = new PointCube(x, y, z);
        center.setColour(LineStyles.ELLIPSOIDCENTER);
        return this;
    }

    @Override
    public BaseRegion setEllipsoidRadii(double x, double y, double z) {
        radii = new Vector3(x, y, z);
        return this;
    }
}
