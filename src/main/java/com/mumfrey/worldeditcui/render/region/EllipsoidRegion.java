package com.mumfrey.worldeditcui.render.region;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.points.PointCube;
import com.mumfrey.worldeditcui.render.shapes.RenderEllipsoid;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

/**
 * Main controller for a ellipsoid-type region
 *
 * @author yetanotherx
 * @author lahwran
 */
public final class EllipsoidRegion extends BaseRegion {
    private final Vector3d radii = new Vector3d();

    @Nullable
    private PointCube center;

    public EllipsoidRegion(WorldEditCUIController controller) {
        super(controller);
    }

    @Override
    public RegionType getType() {
        return RegionType.ELLIPSOID;
    }

    @Override
    public void render() {
        if (center != null) {
            center.render();
            new RenderEllipsoid(LineStyles.ELLIPSOIDGRID, center, radii).render();
        }
    }

    @Override
    public BaseRegion setEllipsoidCenter(int x, int y, int z) {
        center = new PointCube(LineStyles.ELLIPSOIDCENTER, x, y, z);
        return this;
    }

    @Override
    public BaseRegion setEllipsoidRadii(double x, double y, double z) {
        radii.set(x, y, z);
        return this;
    }
}
