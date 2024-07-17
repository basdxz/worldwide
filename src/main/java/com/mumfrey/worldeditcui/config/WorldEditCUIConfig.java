package com.mumfrey.worldeditcui.config;

import com.falsepattern.lib.config.Config;
import com.falsepattern.lib.config.ConfigurationManager;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import static com.mumfrey.worldeditcui.Tags.MOD_ID;

@UtilityClass
public final class WorldEditCUIConfig {
    // region Init
    public static void poke() {}

    static {
        init();
    }

    public static Class<?>[] configClasses() {
        return new Class[]{General.class, Color.class};
    }

    @SneakyThrows
    private static void init() {
        ConfigurationManager.initialize((a, b) -> {}, configClasses());
    }
    // endregion

    @Config(modid = MOD_ID)
    public static final class General {
        static {
            poke();
        }

        @Config.Name("showHiddenLines")
        @Config.Comment("Toggles rendering hidden selection lines")
        @Config.LangKey("config.worldeditcui.general.show_hidden_lines")
        @Config.DefaultBoolean(true)
        public static boolean SHOW_HIDDEN_LINES;
    }

    @Config(modid = MOD_ID, category = "color")
    public static final class Color {
        private static final String HEX_COL_REGEX = "#[0-9A-F]{8}";

        static {
            poke();
        }

        @Config.Name("cuboidBox")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldeditcui.color.cuboid_box")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#CC3333CC")
        public static String CUBOID_BOX;
        @Config.Name("cuboidGrid")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldeditcui.color.cuboid_grid")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#CC4C4CCC")
        public static String CUBOID_GRID;
        @Config.Name("cuboidPoint1")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldeditcui.color.cuboid_point_1")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#33CC33CC")
        public static String CUBOID_POINT_1;
        @Config.Name("cuboidPoint2")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldeditcui.color.cuboid_point_2")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#3333CCCC")
        public static String CUBOID_POINT_2;

        @Config.Name("polygonBox")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldeditcui.color.polygon_box")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#CC4C4CCC")
        public static String POLYGON_BOX;
        @Config.Name("polygonGrid")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldeditcui.color.polygon_grid")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#CC3333CC")
        public static String POLYGON_GRID;
        @Config.Name("polygonPoint")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldeditcui.color.polygon_point")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#33CCCCCC")
        public static String POLYGON_POINT;

        @Config.Name("ellipsoidGrid")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldeditcui.color.ellipsoid_grid")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#CC4C4CCC")
        public static String ELLIPSOID_GRID;
        @Config.Name("ellipsoidPoint")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldeditcui.color.ellipsoid_center")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#CCCC33CC")
        public static String ELLIPSOID_CENTER;

        @Config.Name("cylinderBox")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldeditcui.color.cylinder_box")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#CC4C4CCC")
        public static String CYLINDER_BOX;
        @Config.Name("cylinderGrid")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldeditcui.color.cylinder_grid")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#CC3333CC")
        public static String CYLINDER_GRID;
        @Config.Name("cylinderCenter")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldeditcui.color.cylinder_center")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#CC33CCCC")
        public static String CYLINDER_CENTER;
    }
}
