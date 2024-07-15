package com.mumfrey.worldeditcui;

import com.mumfrey.worldeditcui.event.listeners.CUIListenerChannel;
import com.mumfrey.worldeditcui.event.listeners.CUIListenerWorldRender;
import com.mumfrey.worldeditcui.exceptions.InitializationException;
import com.mumfrey.worldeditcui.render.region.CuboidRegion;
import com.sk89q.worldedit.forge.network.WENetAPI;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import lombok.NoArgsConstructor;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.lang.ref.WeakReference;

import static com.mumfrey.worldeditcui.Tags.*;

@NoArgsConstructor
@Mod(modid = MOD_ID,
     name = MOD_NAME,
     version = MOD_VERSION,
     dependencies = "required-after:worldedit@[6.2.0,);")
public class WorldEditCUI {
    public static final int CLIENT_WECUI_API_VERSION = 4;

    public static final Logger LOG = LogManager.getLogger(MOD_NAME);

    private final WorldEditCUIController controller = new WorldEditCUIController();

    private CUIListenerWorldRender worldRenderListener;
    private CUIListenerChannel channelListener;

    private KeyBinding keyBindToggleUI;
    private KeyBinding keyBindClearSel;
    private KeyBinding keyBindRefresh;

    private WeakReference<WorldClient> lastWorld;
    private WeakReference<EntityPlayerSP> lastPlayer;

    private boolean didHandshake;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        try {
            this.controller.initialize();
        } catch (InitializationException e) {
            throw new IllegalStateException("Failed to initialize " + MOD_NAME, e);
        }

        this.worldRenderListener = new CUIListenerWorldRender(this.controller);
        this.channelListener = new CUIListenerChannel(this.controller);

        this.keyBindToggleUI = registerKeyBind("toggle");
        this.keyBindClearSel = registerKeyBind("clear");
        this.keyBindRefresh = registerKeyBind("refresh");

        this.lastWorld = new WeakReference<>(null);
        this.lastPlayer = new WeakReference<>(null);

        this.didHandshake = false;

        WENetAPI.setupCUIHandler(MOD_NAME, this::onCUIHandshake, this::onCUIMessage);

        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    private void onCUIHandshake(boolean isValid, int serverAPIVersion) {
        didHandshake = isValid;
        if (isValid) {
            LOG.debug("CUI handshake success");
            LOG.debug("Requesting CUI setup [Post-Handshake]");
            WENetAPI.requestCUISetup();
        } else {
            LOG.debug("CUI handshake failed");
        }
        LOG.debug("Client CUI Version: [{}]", CLIENT_WECUI_API_VERSION);
        LOG.debug("Server CUI Version: [{}]", serverAPIVersion);
    }

    private void onCUIMessage(String message) {
        if (!didHandshake) {
            didHandshake = true;
            LOG.debug("CUI Message without handshake? :swagok:");
        }
        channelListener.onMessage(message);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent evt) {
        if (evt.phase != TickEvent.Phase.START)
            return;

        val mc = Minecraft.getMinecraft();

        val currentWorld = mc.theWorld;
        val currentPlayer = mc.thePlayer;

        // If the world or the player are null, we're most likely outside the game.
        if (currentWorld == null || currentPlayer == null) {
            lastWorld = new WeakReference<>(null);
            lastPlayer = new WeakReference<>(null);

            // Reset the controller if a handshake did happen in the past
            if (didHandshake) {
                didHandshake = false;
                controller.setSelection(new CuboidRegion(controller));
            }
            return;
        }

        // If the last world was null (but this one is not) we assume that the player just joined a world.
        if (lastWorld.get() == null) {
            // Request a handshake
            didHandshake = false;
            LOG.debug("Starting CUI handshake [World Join]");
            WENetAPI.requestCUIHandshake(CLIENT_WECUI_API_VERSION);

            // Set our field tracking & return
            lastWorld = new WeakReference<>(currentWorld);
            lastPlayer = new WeakReference<>(currentPlayer);
            return;
        }

        // If the current screen is null, we assume that we are in game with a GUI of some sort open
        if (mc.currentScreen == null)
            handleKeyBinds(currentPlayer);

        // If the player or world are not the same, assume a dimension switch
        if (currentWorld != lastWorld.get() || currentPlayer != lastPlayer.get()) {
            // Request a new CUI setup so we're up-to date in the new world

            LOG.debug("Requesting CUI setup [World Change]");
            WENetAPI.requestCUISetup();

            lastWorld = new WeakReference<>(currentWorld);
            lastPlayer = new WeakReference<>(currentPlayer);
        }
    }

    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent evt) {
        worldRenderListener.onRender();
    }

    private void handleKeyBinds(EntityClientPlayerMP player) {
        if (keyBindToggleUI.isPressed())
            worldRenderListener.toggleVisible();
        if (keyBindClearSel.isPressed())
            player.sendChatMessage("//sel");
        if (keyBindRefresh.isPressed()) {
            LOG.debug("Requesting CUI setup [User Refresh]");
            WENetAPI.requestCUISetup();
        }
    }

	private static KeyBinding registerKeyBind(String name) {
        // All keybinds are unbound by default, and under the `WorldEdit` category
		val keyBind = new KeyBinding(MOD_ID + ".keys." + name, Keyboard.KEY_NONE, "WorldEdit");
        ClientRegistry.registerKeyBinding(keyBind);
        return keyBind;
	}
}
