package com.mumfrey.worldeditcui.event.cui;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.event.CUIEvent;
import com.mumfrey.worldeditcui.event.CUIEventType;
import lombok.val;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;

/**
 * Called when resize event is received
 *
 * @author lahwran
 * @author yetanotherx
 */
public final class CUIEventBounds extends CUIEvent {
    public CUIEventBounds(WorldEditCUIController controller, String... args) {
        super(controller, args);
    }

    @Override
    public CUIEventType getEventType() {
        return CUIEventType.MINMAX;
    }

    @Override
    public String raise() {
        val min = getInt(0);
        val max = getInt(1);
        LOG.debug("Resizing selection: [min={}, max={}]", min, max);
        getSelection().setMinMax(min, max);
        return null;
    }
}
