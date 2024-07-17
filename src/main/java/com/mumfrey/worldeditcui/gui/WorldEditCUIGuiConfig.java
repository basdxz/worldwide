package com.mumfrey.worldeditcui.gui;

import com.falsepattern.lib.config.ConfigException;
import com.falsepattern.lib.config.SimpleGuiConfig;
import com.mumfrey.worldeditcui.config.LineColor;
import com.mumfrey.worldeditcui.config.WorldEditCUIConfig;
import net.minecraft.client.gui.GuiScreen;

import static com.mumfrey.worldeditcui.Tags.MOD_ID;
import static com.mumfrey.worldeditcui.Tags.MOD_NAME;

public final class WorldEditCUIGuiConfig extends SimpleGuiConfig {
    public WorldEditCUIGuiConfig(GuiScreen parent) throws ConfigException {
        super(parent, MOD_ID, MOD_NAME, WorldEditCUIConfig.configClasses());
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        LineColor.updateAll();
    }
}
