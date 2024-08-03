package com.ventooth.worldwide.proxy;

import com.ventooth.worldwide.config.WorldEditCUIConfig;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public abstract class CommonProxy {
    static {
        WorldEditCUIConfig.poke();
    }

    public void preInit(FMLPreInitializationEvent evt) {}
}
