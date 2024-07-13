package com.mumfrey.worldeditcui.event.listeners;

import static org.lwjgl.opengl.GL11.*;

import com.mumfrey.worldeditcui.WorldEditCUIController;

import com.mumfrey.worldeditcui.render.region.BaseRegion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import org.lwjgl.opengl.GL11;

/**
 * Listener for WorldRenderEvent
 *
 * @author lahwran
 * @author yetanotherx
 *
 */
public class CUIListenerWorldRender
{
	private WorldEditCUIController controller;

	private Minecraft minecraft;

	public CUIListenerWorldRender(WorldEditCUIController controller, Minecraft minecraft)
	{
		this.controller = controller;
		this.minecraft = minecraft;
	}

	public void onRender(float partialTicks)
	{
		BaseRegion selection = this.controller.getSelection();
		if (selection == null)
			return;

		glPushAttrib(GL_ALL_ATTRIB_BITS);
		glPushMatrix();

		glDisable(GL11.GL_TEXTURE_2D);
		glEnable(GL11.GL_BLEND);
		glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL11.GL_LIGHTING);

		try
		{
			EntityClientPlayerMP thePlayer = this.minecraft.thePlayer;
			glTranslated(-this.getPlayerXGuess(thePlayer, partialTicks),
						 -this.getPlayerYGuess(thePlayer, partialTicks),
						 -this.getPlayerZGuess(thePlayer, partialTicks));
			glColor3f(1.0f, 1.0f, 1.0f);
			selection.render();
		} catch (Exception ignored) {}

		glPopMatrix();
		glPopAttrib();
	}

	private double getPlayerXGuess(EntityClientPlayerMP thePlayer, float renderTick)
	{
		return thePlayer.prevPosX + ((thePlayer.posX - thePlayer.prevPosX) * renderTick);
	}

	private double getPlayerYGuess(EntityClientPlayerMP thePlayer, float renderTick)
	{
		return thePlayer.prevPosY + ((thePlayer.posY - thePlayer.prevPosY) * renderTick);
	}

	private double getPlayerZGuess(EntityClientPlayerMP thePlayer, float renderTick)
	{
		return thePlayer.prevPosZ + ((thePlayer.posZ - thePlayer.prevPosZ) * renderTick);
	}
}
