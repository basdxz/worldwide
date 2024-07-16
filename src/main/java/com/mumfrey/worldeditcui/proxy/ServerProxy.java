package com.mumfrey.worldeditcui.proxy;

import com.mumfrey.worldeditcui.Tags;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lombok.NoArgsConstructor;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;

@NoArgsConstructor
public final class ServerProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent evt) {
        LOG.info(Tags.MOD_NAME + " is a clientside mod, it currently has no effect when present on the server.");
    }
}
