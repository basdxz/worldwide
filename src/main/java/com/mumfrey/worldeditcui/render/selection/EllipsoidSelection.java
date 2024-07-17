package com.mumfrey.worldeditcui.render.selection;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.config.LineColor;
import com.mumfrey.worldeditcui.render.points.PointCube;
import com.mumfrey.worldeditcui.render.shapes.RenderEllipsoid;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

/**
 * Main controller for a ellipsoid-type selection
 *
 * @author yetanotherx
 * @author lahwran
 */
public final class EllipsoidSelection extends SelectionBase {
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
    public SelectionBase setEllipsoidCenter(int x, int y, int z) {
        center = new PointCube(LineColor.ELLIPSOID_CENTER, x, y, z);
        return this;
    }

    @Override
    public SelectionBase setEllipsoidRadii(double x, double y, double z) {
        radii.set(x, y, z);
        return this;
    }
}
