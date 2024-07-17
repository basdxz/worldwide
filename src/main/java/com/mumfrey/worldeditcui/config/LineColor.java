package com.mumfrey.worldeditcui.config;

import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import org.lwjgl.opengl.GL11;

import java.lang.invoke.MethodHandles;
import java.util.function.Supplier;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;

/**
 * @author Ven
 */
public enum LineColor {
    CUBOID_BOX,
    CUBOID_GRID,
    CUBOID_POINT_1,
    CUBOID_POINT_2,

    POLYGON_BOX,
    POLYGON_GRID,
    POLYGON_POINT,

    ELLIPSOID_GRID,
    ELLIPSOID_CENTER,

    CYLINDER_BOX,
    CYLINDER_GRID,
    CYLINDER_CENTER,
    ;

    private static final LineColor[] VALUES = values();

    private final Supplier<String> getter;

    private float r;
    private float g;
    private float b;
    private float a;

    LineColor() {
        this.getter = createGetter(name());
        update();
    }

    public void setGlColorOld(float alphaMult) {
        GL11.glColor4f(r, g, b, a * alphaMult);
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

    @SneakyThrows
    private static Supplier<String> createGetter(String name) {
        val mh = MethodHandles.publicLookup().findStaticGetter(WorldEditCUIConfig.Color.class, name, String.class);
        return new Supplier<String>() {
            @Override
            @SneakyThrows
            public String get() {
                return (String) mh.invokeExact();
            }
        };
    }
}
