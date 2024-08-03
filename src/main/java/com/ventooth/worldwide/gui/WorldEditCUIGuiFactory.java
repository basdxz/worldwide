package com.ventooth.worldwide.gui;

import com.falsepattern.lib.config.SimpleGuiFactory;
import lombok.NoArgsConstructor;
import net.minecraft.client.gui.GuiScreen;

@NoArgsConstructor
@SuppressWarnings("unused")
public final class WorldEditCUIGuiFactory implements SimpleGuiFactory {
    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return WorldEditCUIGuiConfig.class;
    }
}
