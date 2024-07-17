package com.mumfrey.worldeditcui.config;

import com.mumfrey.worldeditcui.config.WorldEditCUIConfig.Color;
import lombok.val;
import lombok.var;
import org.lwjgl.opengl.GL11;

import java.util.function.Supplier;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;

/**
 * @author Ven
 */
public enum LineColor {
    CUBOID_BOX(() -> Color.CUBOID_BOX),
    CUBOID_GRID(() -> Color.CUBOID_GRID),
    CUBOID_POINT_1(() -> Color.CUBOID_POINT_1),
    CUBOID_POINT_2(() -> Color.CUBOID_POINT_2),

    POLYGON_BOX(() -> Color.POLYGON_BOX),
    POLYGON_GRID(() -> Color.POLYGON_GRID),
    POLYGON_POINT(() -> Color.POLYGON_POINT),

    ELLIPSOID_GRID(() -> Color.ELLIPSOID_GRID),
    ELLIPSOID_CENTER(() -> Color.ELLIPSOID_CENTER),

    CYLINDER_BOX(() -> Color.CYLINDER_BOX),
    CYLINDER_GRID(() -> Color.CYLINDER_GRID),
    CYLINDER_CENTER(() -> Color.CYLINDER_CENTER),
    ;

    private static final LineColor[] VALUES = values();

    private final Supplier<String> getter;

    private float r;
    private float g;
    private float b;
    private float a;

    LineColor(Supplier<String> getter) {
        this.getter = getter;
        update();
    }

    public void setGlColor(boolean isVisible) {
        GL11.glColor4f(r, g, b, isVisible ? a : a * 0.25F);
    }

    public static void updateAll() {
        for (val color : VALUES)
            color.update();
    }

    private void update() {
        var hex = getter.get();
        try {
            r = Integer.valueOf(hex.substring(1, 3), 16) / 255F;
            g = Integer.valueOf(hex.substring(3, 5), 16) / 255F;
            b = Integer.valueOf(hex.substring(5, 7), 16) / 255F;
            a = Integer.valueOf(hex.substring(7, 9), 16) / 255F;
        } catch (Exception e) {
            r = 1F;
            g = 0F;
            b = 0F;
            a = 1F;
            LOG.warn("Failed to update color: [{}] with invalid value: [{}]", name(), hex, e);
        }
    }
}
