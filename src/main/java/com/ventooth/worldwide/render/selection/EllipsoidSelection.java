package com.ventooth.worldwide.render.selection;

import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.config.LineColor;
import com.ventooth.worldwide.render.points.PointCube;
import com.ventooth.worldwide.render.shapes.RenderEllipsoid;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

/**
 * Main controller for a ellipsoid-type selection
 *
 * @author yetanotherx
 * @author lahwran
 */
public final class EllipsoidSelection extends Selection {
    private final Vector3d radii = new Vector3d();

    @Nullable
    private PointCube center;

    public EllipsoidSelection(WorldEditCUIController controller) {
        super(controller);
    }

    @Override
    public SelectionType type() {
        return SelectionType.ELLIPSOID;
    }

    @Override
    public void render() {
        if (center != null) {
            center.render();
            new RenderEllipsoid(LineColor.ELLIPSOID_GRID, center, radii).render();
        }
    }

    @Override
    public Selection setEllipsoidCenter(int x, int y, int z) {
        center = new PointCube(LineColor.ELLIPSOID_CENTER, x, y, z);
        return this;
    }

    @Override
    public Selection setEllipsoidRadii(double x, double y, double z) {
        radii.set(x, y, z);
        return this;
    }
}
