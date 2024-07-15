package com.mumfrey.worldeditcui.event.listeners;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.event.CUIEventArgs;
import lombok.RequiredArgsConstructor;

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
        LOG.debug("Received CUI event: [{}]", message);

        final CUIEventArgs args;
        try {
            args = new CUIEventArgs(message);
        } catch (Exception e) {
            LOG.error("Failed to parse arguments for CUI event: [{}]", message, e);
            return;
        }

        try {
            controller.getDispatcher().raiseEvent(args);
        } catch (Exception e) {
            LOG.error("Failed to raise CUI event: [{}]", message, e);
        }
    }
}
