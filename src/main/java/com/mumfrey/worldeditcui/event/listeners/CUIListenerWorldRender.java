package com.mumfrey.worldeditcui.event.listeners;

import static org.lwjgl.opengl.GL11.*;

import com.falsepattern.lib.util.RenderUtil;
import com.mumfrey.worldeditcui.WorldEditCUIController;

import com.mumfrey.worldeditcui.render.region.BaseRegion;
import net.minecraft.client.renderer.OpenGlHelper;
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

	public CUIListenerWorldRender(WorldEditCUIController controller)
	{
		this.controller = controller;
	}

	public void onRender()
	{
		BaseRegion selection = this.controller.getSelection();
		if (selection == null)
			return;

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		RenderUtil.bindEmptyTexture();

		glPushAttrib(GL_ALL_ATTRIB_BITS);
		glPushMatrix();

		glDisable(GL11.GL_TEXTURE_2D);
		glEnable(GL11.GL_BLEND);
		glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL11.GL_LIGHTING);

		RenderUtil.setGLTranslationRelativeToPlayer();

		try
		{
			glColor3f(1.0f, 1.0f, 1.0f);
			selection.render();
		} catch (Exception ignored) {}

		glPopMatrix();
		glPopAttrib();

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
											  OpenGlHelper.lastBrightnessX,
											  OpenGlHelper.lastBrightnessY);
	}
}
