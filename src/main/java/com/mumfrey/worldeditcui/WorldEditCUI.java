package com.mumfrey.worldeditcui;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import com.sk89q.worldedit.forge.WECUIPacketHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import lombok.val;
import net.minecraft.network.play.client.C17PacketCustomPayload;
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

import static cpw.mods.fml.common.network.FMLNetworkEvent.*;

@Mod(modid = Tags.MOD_ID,
	 name = Tags.MOD_NAME,
	 version = Tags.MOD_VERSION,
	 dependencies = "after:worldedit@[6.2.0,);")
public class WorldEditCUI
{
	private static final String CHANNEL_WECUI = "WECUI";

	private WorldEditCUIController controller;
	private WorldClient lastWorld;
	private EntityPlayerSP lastPlayer;

	private KeyBinding keyBindToggleUI = new KeyBinding("wecui.keys.toggle", Keyboard.KEY_NONE, "wecui.keys.category");
	private KeyBinding keyBindClearSel = new KeyBinding("wecui.keys.clear", Keyboard.KEY_NONE, "wecui.keys.category");

	private boolean visible = true;

	private CUIListenerWorldRender worldRenderListener;
	private CUIListenerChannel channelListener;

	private FMLEventChannel WECUI_CHANNEL;

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt)
	{
		this.controller = new WorldEditCUIController();
		this.controller.initialize();

		this.worldRenderListener = new CUIListenerWorldRender(this.controller, Minecraft.getMinecraft());
		this.channelListener = new CUIListenerChannel(this.controller);

		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);

//		{
//			if (!Loader.isModLoaded("worldedit")) {
//				try {
//					Field f = WECUIPacketHandler.class.getDeclaredField("WECUI_CHANNEL")
//				}catch (RuntimeException e) {
//
//				}
//			}
//
//			if (NetworkRegistry.INSTANCE.hasChannel(CHANNEL_WECUI, Side.CLIENT)) {
//
//			}
//		}

//		NetworkRegistry.INSTANCE.getChannel(CHANNEL_WECUI, Side.CLIENT)
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
		byte[] buffer = ("v|" + WorldEditCUIController.protocolVersion).getBytes(StandardCharsets.UTF_8);
		Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C17PacketCustomPayload(CHANNEL_WECUI, buffer));
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
				if (mc.thePlayer != null) mc.thePlayer.sendChatMessage("/we cui"); //Tricks WE to send the current selection
			}
		}
	}

	@SubscribeEvent
	public void onPostRenderEntities(RenderWorldLastEvent evt)
	{
		if (this.visible)
		{
			try
			{
				this.worldRenderListener.onRender(evt.partialTicks);
			}
			catch (Exception ex) {}
		}
	}
}
