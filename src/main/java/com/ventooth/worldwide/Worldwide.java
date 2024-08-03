package com.ventooth.worldwide;

import com.ventooth.worldwide.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.ventooth.worldwide.Tags.*;

@NoArgsConstructor
@Mod(modid = MOD_ID,
     name = MOD_NAME,
     version = MOD_VERSION,
     dependencies = "required-after:falsepatternlib@[1.4.0,)" +
                    ";required-after:worldedit@[6.3.0,);",
     acceptableRemoteVersions = "*",
     guiFactory = ROOT_PKG + ".gui.WorldEditCUIGuiFactory")
public final class Worldwide {
    public static final int CLIENT_WECUI_API_VERSION = 4;

    public static final Logger LOG = LogManager.getLogger(MOD_NAME);

    @SidedProxy(serverSide = ROOT_PKG + ".proxy.ServerProxy", clientSide = ROOT_PKG + ".proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        proxy.preInit(evt);
    }
}
