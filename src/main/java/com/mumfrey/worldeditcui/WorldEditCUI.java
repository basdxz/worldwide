package com.mumfrey.worldeditcui;

import com.mumfrey.worldeditcui.event.listeners.CUIListenerChannel;
import com.mumfrey.worldeditcui.event.listeners.CUIListenerWorldRender;
import com.mumfrey.worldeditcui.render.region.CuboidRegion;
import com.sk89q.worldedit.forge.network.WENetAPI;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.ref.WeakReference;

import static com.mumfrey.worldeditcui.Tags.*;
import static org.lwjgl.input.Keyboard.KEY_NONE;

@Mod(modid = MOD_ID,
     name = MOD_NAME,
     version = MOD_VERSION,
     dependencies = "required-after:worldedit@[6.2.0,);")
public class WorldEditCUI {
    public static final Logger LOG = LogManager.getLogger(MOD_NAME);

    private final WorldEditCUIController controller = new WorldEditCUIController();

    private CUIListenerWorldRender worldRenderListener;
    private CUIListenerChannel channelListener;

    private KeyBinding keyBindToggleUI;
    private KeyBinding keyBindClearSel;

    private WeakReference<WorldClient> lastWorld;
    private WeakReference<EntityPlayerSP> lastPlayer;

    private Status status;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        this.controller.initialize();

        this.worldRenderListener = new CUIListenerWorldRender(this.controller);
        this.channelListener = new CUIListenerChannel(this.controller);

        this.keyBindToggleUI = registerKeyBind("toggle", KEY_NONE);
        this.keyBindClearSel = registerKeyBind("clear", KEY_NONE);

        this.lastWorld = new WeakReference<>(null);
        this.lastPlayer = new WeakReference<>(null);

        this.status = Status.NOT_IN_GAME;

//        WENetAPI.setupCUIHandler(MOD_NAME, (aBoolean, integer) -> {
//            handsShaken = aBoolean;
//        }, this::onCustomPayload);

        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    public void onCustomPayload(String payload) {
//        this.handsShaken = true; // ??

        try {
            this.channelListener.onMessage(payload);
        } catch (Exception ex) {
        }
    }

    @SubscribeEvent
    public void onJoinGame(EntityJoinWorldEvent evt) {
        if (evt.world == null || !evt.world.isRemote)
            return;
        if (!(evt.entity instanceof EntityPlayerSP))
            return;

        LOG.debug("Joined game, sending initial handshake");
        this.helo();
    }

    private void helo() {
//        handsShaken = false;
        WENetAPI.requestCUIHandshake(4);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent evt) {
        if (evt.phase != TickEvent.Phase.START)
            return;

        val mc = Minecraft.getMinecraft();

        val currentWorld = mc.theWorld;
        val currentPlayer = mc.thePlayer;

        if (currentWorld == null || currentPlayer == null) {
            lastWorld = new WeakReference<>(null);
            lastPlayer = new WeakReference<>(null);
            status = Status.NOT_IN_GAME;
            return;
        }

        if (mc.currentScreen == null)
            handleKeyBinds(currentPlayer);

//        if (status == )
//
//        if (inGame && this.controller != null) {
//            if (mc.theWorld != this.lastWorld.get() || mc.thePlayer != this.lastPlayer.get()) {
//                this.lastWorld = new WeakReference<>(mc.theWorld);
//                this.lastPlayer = new WeakReference<>(mc.thePlayer);
//
//                this.controller.getDebugger().debug("World change detected, sending new handshake");
//                this.controller.setSelection(new CuboidRegion(this.controller));
//
//                WENetAPI.requestCUISetup();
//            }
//        }
    }

    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent evt) {
        worldRenderListener.onRender();
    }

    private void handleKeyBinds(EntityClientPlayerMP player) {
        if (keyBindToggleUI.isPressed())
            worldRenderListener.toggleVisible();
        if (this.keyBindClearSel.isPressed())
            player.sendChatMessage("//sel");
    }

	private static KeyBinding registerKeyBind(String name, int defaultKeycode) {
		val keyBind = new KeyBinding(MOD_ID + ".keys." + name, defaultKeycode, MOD_ID + ".keys.category");
        ClientRegistry.registerKeyBinding(keyBind);
        return keyBind;
	}

    private enum Status {
        NOT_IN_GAME,
        IN_GAME_SENT_HANDSHAKE,
        IN_GAME_BAD_HANDSHAKE
    }
}
