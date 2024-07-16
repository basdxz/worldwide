package com.mumfrey.worldeditcui.event.listeners;

import com.falsepattern.lib.util.RenderUtil;
import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.render.selection.SelectionBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.val;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;
import static org.lwjgl.opengl.GL11.*;

/**
 * Listener for WorldRenderEvent
 *
 * @author lahwran
 * @author yetanotherx
 */
@RequiredArgsConstructor
public final class CUIListenerWorldRender {
    private final WorldEditCUIController controller;

    @Getter
    @Setter
    private boolean isVisible = true;

    public void toggleVisible() {
        isVisible = !isVisible;
    }

    public void onRender() {
        if (!isVisible)
            return;

        val selection = controller.getSelection();
        if (selection == null)
            return;

        try {
            onRenderImpl(selection);
        } catch (Exception e) {
            LOG.error("Failed to render selection {}", selection, e);
        }
    }

    private static void onRenderImpl(SelectionBase selection) {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        RenderUtil.bindEmptyTexture();

        glPushAttrib(GL_ALL_ATTRIB_BITS);
        glPushMatrix();

        glDisable(GL11.GL_TEXTURE_2D);
        glEnable(GL11.GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL11.GL_LIGHTING);

        RenderUtil.setGLTranslationRelativeToPlayer();

        glColor4f(1F, 1F, 1F, 1F);
        selection.render();

        glPopMatrix();
        glPopAttrib();

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
                                              OpenGlHelper.lastBrightnessX,
                                              OpenGlHelper.lastBrightnessY);
    }
}
