package com.ventooth.worldwide.event.listeners;

import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.Worldwide;
import com.ventooth.worldwide.event.CUIEventArgs;
import lombok.RequiredArgsConstructor;

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
        Worldwide.LOG.debug("Received CUI event: [{}]", message);

        final CUIEventArgs args;
        try {
            args = new CUIEventArgs(message);
        } catch (Exception e) {
            Worldwide.LOG.error("Failed to parse arguments for CUI event: [{}]", message, e);
            return;
        }

        try {
            controller.dispatcher().raiseEvent(args);
        } catch (Exception e) {
            Worldwide.LOG.error("Failed to raise CUI event: [{}]", message, e);
        }
    }
}
