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
public abstract class Selection implements InitializationFactory {
    protected WorldEditCUIController controller;

    public Selection(WorldEditCUIController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize() {}

    public abstract SelectionType type();

    public abstract void render();

    public Selection setCuboidPoint(int id, double x, double y, double z) {
        throw new InvalidSelectionTypeException(type(), "setCuboidPoint");
    }

    public Selection setPolygonPoint(int id, int x, int z) {
        throw new InvalidSelectionTypeException(type(), "setPolygonPoint");
    }

    public Selection setEllipsoidCenter(int x, int y, int z) {
        throw new InvalidSelectionTypeException(type(), "setEllipsoidCenter");
    }

    public Selection setEllipsoidRadii(double x, double y, double z) {
        throw new InvalidSelectionTypeException(type(), "setEllipsoidRadii");
    }

    public Selection setMinMax(int min, int max) {
        throw new InvalidSelectionTypeException(type(), "setMinMax");
    }

    public Selection setCylinderCenter(int x, int y, int z) {
        throw new InvalidSelectionTypeException(type(), "setCylinderCenter");
    }

    public Selection setCylinderRadius(double x, double z) {
        throw new InvalidSelectionTypeException(type(), "setCylinderRadius");
    }

    public Selection addPolygon(int[] vertexIds) {
        throw new InvalidSelectionTypeException(type(), "addPolygon");
    }
}
