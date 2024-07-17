package com.mumfrey.worldeditcui.proxy;

import com.mumfrey.worldeditcui.config.WorldEditCUIConfig;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public abstract class CommonProxy {
    static {
        WorldEditCUIConfig.poke();
    }

    public void preInit(FMLPreInitializationEvent evt) {}
}
