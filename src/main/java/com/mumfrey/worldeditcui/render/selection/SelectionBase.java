package com.mumfrey.worldeditcui.render.selection;

import com.mumfrey.worldeditcui.InitializationFactory;
import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.exceptions.InvalidSelectionTypeException;

/**
 * Base selection storage class. Provides
 * abstract methods for setting various
 * points in the selection.
 *
 * @author yetanotherx
 * @author lahwran
 */
public abstract class SelectionBase implements InitializationFactory {
    protected WorldEditCUIController controller;

    public SelectionBase(WorldEditCUIController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize() {}

    public abstract SelectionType getType();

    public abstract void render();

    public SelectionBase setCuboidPoint(int id, double x, double y, double z) {
        throw new InvalidSelectionTypeException(getType(), "setCuboidPoint");
    }

    public SelectionBase setPolygonPoint(int id, int x, int z) {
        throw new InvalidSelectionTypeException(getType(), "setPolygonPoint");
    }

    public SelectionBase setEllipsoidCenter(int x, int y, int z) {
        throw new InvalidSelectionTypeException(getType(), "setEllipsoidCenter");
    }

    public SelectionBase setEllipsoidRadii(double x, double y, double z) {
        throw new InvalidSelectionTypeException(getType(), "setEllipsoidRadii");
    }

    public SelectionBase setMinMax(int min, int max) {
        throw new InvalidSelectionTypeException(getType(), "setMinMax");
    }

    public SelectionBase setCylinderCenter(int x, int y, int z) {
        throw new InvalidSelectionTypeException(getType(), "setCylinderCenter");
    }

    public SelectionBase setCylinderRadius(double x, double z) {
        throw new InvalidSelectionTypeException(getType(), "setCylinderRadius");
    }

    public SelectionBase addPolygon(int[] vertexIds) {
        throw new InvalidSelectionTypeException(getType(), "addPolygon");
    }
}
