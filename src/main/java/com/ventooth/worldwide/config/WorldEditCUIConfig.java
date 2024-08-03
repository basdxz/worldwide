package com.ventooth.worldwide.config;

import com.falsepattern.lib.config.Config;
import com.falsepattern.lib.config.ConfigurationManager;
import com.ventooth.worldwide.Tags;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

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

    @Config(modid = Tags.MOD_ID)
    public static final class General {
        static {
            poke();
        }

        @Config.Name("showHiddenLines")
        @Config.Comment("Toggles rendering hidden selection lines")
        @Config.LangKey("config.worldwide.general.show_hidden_lines")
        @Config.DefaultBoolean(true)
        public static boolean SHOW_HIDDEN_LINES;
    }

    @Config(modid = Tags.MOD_ID, category = "color")
    public static final class Color {
        private static final String HEX_COL_REGEX = "#[0-9A-F]{8}";

        static {
            poke();
        }

        @Config.Name("cuboidBox")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldwide.color.cuboid_box")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#4F6190CC")
        public static String CUBOID_BOX;
        @Config.Name("cuboidGrid")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldwide.color.cuboid_grid")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#5074CFCC")
        public static String CUBOID_GRID;
        @Config.Name("cuboidPoint1")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldwide.color.cuboid_point_1")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#0CB61ACC")
        public static String CUBOID_POINT_1;
        @Config.Name("cuboidPoint2")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldwide.color.cuboid_point_2")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#E1B61ACC")
        public static String CUBOID_POINT_2;

        @Config.Name("polygonBox")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldwide.color.polygon_box")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#4F6190CC")
        public static String POLYGON_BOX;
        @Config.Name("polygonGrid")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldwide.color.polygon_grid")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#277BDBCC")
        public static String POLYGON_GRID;
        @Config.Name("polygonPoint")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldwide.color.polygon_point")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#0CB61ACC")
        public static String POLYGON_POINT;

        @Config.Name("ellipsoidGrid")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldwide.color.ellipsoid_grid")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#277BDBCC")
        public static String ELLIPSOID_GRID;
        @Config.Name("ellipsoidPoint")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldwide.color.ellipsoid_center")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#0CB61ACC")
        public static String ELLIPSOID_CENTER;

        @Config.Name("cylinderBox")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldwide.color.cylinder_box")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#4F6190CC")
        public static String CYLINDER_BOX;
        @Config.Name("cylinderGrid")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldwide.color.cylinder_grid")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#277BDBCC")
        public static String CYLINDER_GRID;
        @Config.Name("cylinderCenter")
        @Config.Comment("#RRGGBBAA")
        @Config.LangKey("config.worldwide.color.cylinder_center")
        @Config.Pattern(HEX_COL_REGEX)
        @Config.DefaultString("#0CB61ACC")
        public static String CYLINDER_CENTER;
    }
}
