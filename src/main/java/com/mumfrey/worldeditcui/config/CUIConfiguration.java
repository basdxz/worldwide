package com.mumfrey.worldeditcui.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mumfrey.worldeditcui.InitializationFactory;
import com.mumfrey.worldeditcui.render.LineStyles;
import net.minecraft.launchwrapper.Launch;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Stores and reads WorldEditCUI settings
 *
 * @author yetanotherx
 */
public class CUIConfiguration implements InitializationFactory {
    private static final Path CONFIG_PATH = Paths.get(Launch.minecraftHome.getAbsolutePath(), "config");
    private static final String CONFIG_FILE_NAME = "worldeditcui.config.json";

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private Colour cuboidGridColor = new Colour("#CC3333CC");
    private Colour cuboidEdgeColor = new Colour("#CC4C4CCC");
    private Colour cuboidFirstPointColor = new Colour("#33CC33CC");
    private Colour cuboidSecondPointColor = new Colour("#3333CCCC");
    private Colour polyGridColor = new Colour("#CC3333CC");
    private Colour polyEdgeColor = new Colour("#CC4C4CCC");
    private Colour polyPointColor = new Colour("#33CCCCCC");
    private Colour ellipsoidGridColor = new Colour("#CC4C4CCC");
    private Colour ellipsoidPointColor = new Colour("#CCCC33CC");
    private Colour cylinderGridColor = new Colour("#CC3333CC");
    private Colour cylinderEdgeColor = new Colour("#CC4C4CCC");
    private Colour cylinderPointColor = new Colour("#CC33CCCC");

    /**
     * Copies the default config file to the proper directory if it does not
     * exist. It then reads the file and sets each variable to the proper value.
     */
    @Override
    public void initialize() {
        this.cuboidGridColor = Colour.setDefault(this.cuboidGridColor, "#CC3333CC");
        this.cuboidEdgeColor = Colour.setDefault(this.cuboidEdgeColor, "#CC4C4CCC");
        this.cuboidFirstPointColor = Colour.setDefault(this.cuboidFirstPointColor, "#33CC33CC");
        this.cuboidSecondPointColor = Colour.setDefault(this.cuboidSecondPointColor, "#3333CCCC");
        this.polyGridColor = Colour.setDefault(this.polyGridColor, "#CC3333CC");
        this.polyEdgeColor = Colour.setDefault(this.polyEdgeColor, "#CC4C4CCC");
        this.polyPointColor = Colour.setDefault(this.polyPointColor, "#33CCCCCC");
        this.ellipsoidGridColor = Colour.setDefault(this.ellipsoidGridColor, "#CC4C4CCC");
        this.ellipsoidPointColor = Colour.setDefault(this.ellipsoidPointColor, "#CCCC33CC");
        this.cylinderGridColor = Colour.setDefault(this.cylinderGridColor, "#CC3333CC");
        this.cylinderEdgeColor = Colour.setDefault(this.cylinderEdgeColor, "#CC4C4CCC");
        this.cylinderPointColor = Colour.setDefault(this.cylinderPointColor, "#CC33CCCC");

        LineStyles.CUBOIDBOX.setColour(this.cuboidEdgeColor);
        LineStyles.CUBOIDGRID.setColour(this.cuboidGridColor);
        LineStyles.CUBOIDPOINT1.setColour(this.cuboidFirstPointColor);
        LineStyles.CUBOIDPOINT2.setColour(this.cuboidSecondPointColor);
        LineStyles.POLYGRID.setColour(this.polyGridColor);
        LineStyles.POLYBOX.setColour(this.polyEdgeColor);
        LineStyles.POLYPOINT.setColour(this.polyPointColor);
        LineStyles.ELLIPSOIDGRID.setColour(this.ellipsoidGridColor);
        LineStyles.ELLIPSOIDCENTER.setColour(this.ellipsoidPointColor);
        LineStyles.CYLINDERGRID.setColour(this.cylinderGridColor);
        LineStyles.CYLINDERBOX.setColour(this.cylinderEdgeColor);
        LineStyles.CYLINDERCENTER.setColour(this.cylinderPointColor);

        this.save();
    }

    public static CUIConfiguration create() {
        File jsonFile = CONFIG_PATH.resolve(CUIConfiguration.CONFIG_FILE_NAME).toFile();

        if (jsonFile.exists()) {
            FileReader fileReader = null;

            try {
                fileReader = new FileReader(jsonFile);
                CUIConfiguration config = CUIConfiguration.GSON.fromJson(fileReader, CUIConfiguration.class);
                return config;
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (fileReader != null) fileReader.close();
                } catch (IOException ex) {
                }
            }
        }

        return new CUIConfiguration();
    }

    public void save() {
        File jsonFile = CONFIG_PATH.resolve(CUIConfiguration.CONFIG_FILE_NAME).toFile();

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(jsonFile);
            CUIConfiguration.GSON.toJson(this, fileWriter);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
