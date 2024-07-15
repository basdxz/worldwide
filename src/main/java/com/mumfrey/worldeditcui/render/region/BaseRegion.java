package com.mumfrey.worldeditcui.render.region;

import com.mumfrey.worldeditcui.InitializationFactory;
import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.exceptions.InvalidSelectionTypeException;

/**
 * Base region storage class. Provides
 * abstract methods for setting various
 * points in the region.
 *
 * @author yetanotherx
 * @author lahwran
 */
public abstract class BaseRegion implements InitializationFactory {
    protected WorldEditCUIController controller;

    public BaseRegion(WorldEditCUIController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize() {}

    public abstract RegionType getType();

    public abstract void render();

    public BaseRegion setCuboidPoint(int id, double x, double y, double z) {
        throw new InvalidSelectionTypeException(getType(), "setCuboidPoint");
    }

    public BaseRegion setPolygonPoint(int id, int x, int z) {
        throw new InvalidSelectionTypeException(getType(), "setPolygonPoint");
    }

    public BaseRegion setEllipsoidCenter(int x, int y, int z) {
        throw new InvalidSelectionTypeException(getType(), "setEllipsoidCenter");
    }

    public BaseRegion setEllipsoidRadii(double x, double y, double z) {
        throw new InvalidSelectionTypeException(getType(), "setEllipsoidRadii");
    }

    public BaseRegion setMinMax(int min, int max) {
        throw new InvalidSelectionTypeException(getType(), "setMinMax");
    }

    public BaseRegion setCylinderCenter(int x, int y, int z) {
        throw new InvalidSelectionTypeException(getType(), "setCylinderCenter");
    }

    public BaseRegion setCylinderRadius(double x, double z) {
        throw new InvalidSelectionTypeException(getType(), "setCylinderRadius");
    }

    public BaseRegion addPolygon(int[] vertexIds) {
        throw new InvalidSelectionTypeException(getType(), "addPolygon");
    }
}
