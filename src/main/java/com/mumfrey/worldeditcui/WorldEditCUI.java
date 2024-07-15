package com.mumfrey.worldeditcui;

import java.nio.charset.StandardCharsets;

import com.sk89q.worldedit.forge.network.WENetAPI;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;

import com.mumfrey.worldeditcui.event.listeners.CUIListenerChannel;
import com.mumfrey.worldeditcui.event.listeners.CUIListenerWorldRender;
import com.mumfrey.worldeditcui.render.region.CuboidRegion;

import static com.mumfrey.worldeditcui.Tags.*;
import static cpw.mods.fml.common.network.FMLNetworkEvent.*;

@Mod(modid = MOD_ID,
	 name = MOD_NAME,
	 version = MOD_VERSION,
	 dependencies = "required-after:worldedit@[6.2.0,);")
public class WorldEditCUI
{
	private static final String CHANNEL_WECUI = "WECUI";

	private WorldEditCUIController controller;
	private WorldClient lastWorld;
	private EntityPlayerSP lastPlayer;

	private KeyBinding keyBindToggleUI;
	private KeyBinding keyBindClearSel;

	private boolean handsShaken = false;

	private boolean visible = true;

	private CUIListenerWorldRender worldRenderListener;
	private CUIListenerChannel channelListener;

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt)
	{
		keyBindToggleUI = new KeyBinding("wecui.keys.toggle", Keyboard.KEY_NONE, "wecui.keys.category");
		keyBindClearSel = new KeyBinding("wecui.keys.clear", Keyboard.KEY_NONE, "wecui.keys.category");

		ClientRegistry.registerKeyBinding(keyBindToggleUI);
		ClientRegistry.registerKeyBinding(keyBindClearSel);


		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);

		WENetAPI.setupCUIHandler(MOD_NAME, (aBoolean, integer) -> {
			handsShaken = aBoolean;
        }, this::onCustomPayload);
	}

	public void onCustomPayload(String payload)
	{
		this.handsShaken = true; // ??

		try
		{
			this.channelListener.onMessage(payload);
		}
		catch (Exception ex) {}
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt)
	{
		this.controller = new WorldEditCUIController();
		this.controller.initialize();

		this.worldRenderListener = new CUIListenerWorldRender(this.controller);
		this.channelListener = new CUIListenerChannel(this.controller);

		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void onJoinGame(EntityJoinWorldEvent evt)
	{
		if (evt.world == null || !evt.world.isRemote)
			return;
		if (!(evt.entity instanceof EntityPlayerSP))
			return;

		this.visible = true;
		this.controller.getDebugger().debug("Joined game, sending initial handshake");
		this.helo();
	}

	/**
	 *
	 */
	private void helo()
	{
		handsShaken = false;
		WENetAPI.sendCUIHandshake(4);
	}

	@SubscribeEvent
	public void onCustomPayload(ClientCustomPacketEvent evt)
	{
		if (!evt.packet.channel().equals(CHANNEL_WECUI))
			return;

		S3FPacketCustomPayload rawPacket = (S3FPacketCustomPayload) evt.packet.toS3FPacket();
		byte[] data = rawPacket.func_149168_d();

		try
		{
			String payload = new String(data, StandardCharsets.UTF_8);
			this.channelListener.onMessage(payload);
		}
		catch (Exception ex) {}
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent evt)
	{
		if (evt.phase != TickEvent.Phase.START)
			return;

		Minecraft mc = Minecraft.getMinecraft();
		boolean inGame = mc.theWorld != null;


		if (inGame && mc.currentScreen == null)
		{
			if (this.keyBindToggleUI.isPressed())
			{
				this.visible = !this.visible;
			}

			if (this.keyBindClearSel.isPressed())
			{
				if (mc.thePlayer != null)
					mc.thePlayer.sendChatMessage("//sel");
			}
		}

		if (inGame && this.controller != null)
		{
			if (mc.theWorld != this.lastWorld || mc.thePlayer != this.lastPlayer)
			{
				this.lastWorld = mc.theWorld;
				this.lastPlayer = mc.thePlayer;

				this.controller.getDebugger().debug("World change detected, sending new handshake");
				this.controller.setSelection(new CuboidRegion(this.controller));
				this.helo();
			}
		}
	}

	@SubscribeEvent
	public void onPostRenderEntities(RenderWorldLastEvent evt)
	{
		if (this.visible && this.handsShaken)
		{
			try
			{
				this.worldRenderListener.onRender();
			}
			catch (Exception ignored) {}
		}
	}
}
