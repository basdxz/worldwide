package com.mumfrey.worldeditcui.event;

import lombok.Getter;

/**
 * CUI communication event
 * Called when a CUI event is sent from the server.
 *
 * @author lahwran
 * @author yetanotherx
 */

@Getter
public final class CUIEventArgs {
    private final String type;
    private final String[] params;

    public CUIEventArgs(String type, String[] params) {
        this.type = type;

        if (params.length == 1 && params[0].isEmpty())
            params = new String[0];
        this.params = params;
    }
}
