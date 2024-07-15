package com.mumfrey.worldeditcui.event.listeners;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.event.CUIEventArgs;
import lombok.RequiredArgsConstructor;
import lombok.val;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;

/**
 * Listener class for incoming plugin channel messages
 *
 * @author lahwran
 * @author yetanotherx
 */
@RequiredArgsConstructor
public final class CUIListenerChannel {
    private final WorldEditCUIController controller;

    public void onMessage(String message) {
        val split = message.split("[|]");
        val type = split[0];
        val args = message.substring(type.length() + 1);

        LOG.debug("Received CUI event from server: [{}]", message);

        try {
            val eventArgs = new CUIEventArgs(type, args.split("[|]"));
            this.controller.getDispatcher().raiseEvent(eventArgs);
        } catch (Exception e) {
            LOG.warn("Failed to process CUI event from server:", e);
        }
    }
}
