package com.mumfrey.worldeditcui.event;

import lombok.Getter;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

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

    public CUIEventArgs(String message) {
        val split = StringUtils.split(message, '|');
        this.type = split[0];
        if (split.length == 1) {
            params = new String[0];
        } else {
            params = Arrays.copyOfRange(split, 1, split.length);
        }
    }
}
