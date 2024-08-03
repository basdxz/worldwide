package com.ventooth.worldwide.proxy;

import com.falsepattern.worldedit.api.WorldEditCUIAPI;
import com.ventooth.worldwide.Tags;
import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.Worldwide;
import com.ventooth.worldwide.event.listeners.CUIListenerChannel;
import com.ventooth.worldwide.event.listeners.CUIListenerWorldRender;
import com.ventooth.worldwide.exceptions.InitializationException;
import com.ventooth.worldwide.render.selection.CuboidSelection;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
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
import org.lwjgl.input.Keyboard;

import java.lang.ref.WeakReference;

@NoArgsConstructor
public final class ClientProxy extends CommonProxy {
    private final WorldEditCUIController controller = new WorldEditCUIController();

    private CUIListenerWorldRender worldRenderListener;
    private CUIListenerChannel channelListener;

    private KeyBinding keyBindToggleUI;
    private KeyBinding keyBindClearSel;
    private KeyBinding keyBindRefresh;

    private WeakReference<WorldClient> lastWorld;
    private WeakReference<EntityPlayerSP> lastPlayer;

    private boolean didHandshake;

    @Override
    public void preInit(FMLPreInitializationEvent evt) {
        try {
            this.controller.initialize();
        } catch (InitializationException e) {
            throw new IllegalStateException("Failed to initialize " + Tags.MOD_NAME, e);
        }

        this.worldRenderListener = new CUIListenerWorldRender(this.controller);
        this.channelListener = new CUIListenerChannel(this.controller);

        this.keyBindToggleUI = registerKeyBind("keys.worldwide.toggle");
        this.keyBindClearSel = registerKeyBind("keys.worldwide.refresh");
        this.keyBindRefresh = registerKeyBind("keys.worldwide.clear");

        this.lastWorld = new WeakReference<>(null);
        this.lastPlayer = new WeakReference<>(null);

        this.didHandshake = false;

        WorldEditCUIAPI.initCUIHandler(Tags.MOD_NAME, this::onCUIHandshake, this::onCUIMessage);

        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
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

            dropHandshake("Not in World");
            return;
        }

        // If the current screen is null, we assume that we are in game with a GUI of some sort open
        if (mc.currentScreen == null)
            handleKeyBinds(currentPlayer);

        // If the player or world are not the same, assume a world change/server join
        if (currentWorld != lastWorld.get() || currentPlayer != lastPlayer.get()) {
            requestHandshake("World Change");

            lastWorld = new WeakReference<>(currentWorld);
            lastPlayer = new WeakReference<>(currentPlayer);
        }
    }

    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent evt) {
        worldRenderListener.onRender();
    }

    private void onCUIHandshake(boolean isValid, int serverAPIVersion) {
        if (isValid) {
            didHandshake = true;
            Worldwide.LOG.debug("CUI handshake success");
            Worldwide.LOG.debug("Requesting CUI Update [Post-Handshake]");
            WorldEditCUIAPI.requestCUIUpdate();
        } else {
            dropHandshake("Server Angy");
            Worldwide.LOG.debug("CUI handshake failed");
        }
        Worldwide.LOG.debug("Client CUI Version: [{}]", Worldwide.CLIENT_WECUI_API_VERSION);
        Worldwide.LOG.debug("Server CUI Version: [{}]", serverAPIVersion);
    }

    private void onCUIMessage(String message) {
        if (!didHandshake) {
            didHandshake = true;
            Worldwide.LOG.debug("CUI Message without handshake? :swagok:");
        }
        channelListener.onMessage(message);
    }

    private void handleKeyBinds(EntityClientPlayerMP player) {
        if (keyBindToggleUI.isPressed())
            worldRenderListener.toggleVisible();
        if (keyBindClearSel.isPressed())
            player.sendChatMessage("//sel");
        if (keyBindRefresh.isPressed())
            requestHandshake("User Request");
    }

    private void requestHandshake(String reason) {
        didHandshake = false;
        Worldwide.LOG.debug("Requesting Handshake [{}]", reason);
        WorldEditCUIAPI.requestCUIHandshake(Worldwide.CLIENT_WECUI_API_VERSION);
    }

    private void dropHandshake(String reason) {
        if (didHandshake) {
            didHandshake = false;
            controller.selection(new CuboidSelection(controller));
            Worldwide.LOG.debug("Dropped Handshake [{}]", reason);
        }
    }

    private static KeyBinding registerKeyBind(String langKey) {
        // All keybinds are unbound by default, and under the `WorldEdit` category
        val keyBind = new KeyBinding(langKey, Keyboard.KEY_NONE, "WorldEdit");
        ClientRegistry.registerKeyBinding(keyBind);
        return keyBind;
    }
}
