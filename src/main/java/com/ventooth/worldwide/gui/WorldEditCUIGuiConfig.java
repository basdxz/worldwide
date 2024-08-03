package com.ventooth.worldwide.gui;

import com.falsepattern.lib.config.ConfigException;
import com.falsepattern.lib.config.SimpleGuiConfig;
import com.ventooth.worldwide.Tags;
import com.ventooth.worldwide.config.LineColor;
import com.ventooth.worldwide.config.WorldEditCUIConfig;
import net.minecraft.client.gui.GuiScreen;

public final class WorldEditCUIGuiConfig extends SimpleGuiConfig {
    public WorldEditCUIGuiConfig(GuiScreen parent) throws ConfigException {
        super(parent, Tags.MOD_ID, Tags.MOD_NAME, WorldEditCUIConfig.configClasses());
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        LineColor.updateAll();
    }
}
