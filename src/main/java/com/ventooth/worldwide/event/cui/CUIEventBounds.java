package com.ventooth.worldwide.event.cui;

import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.Worldwide;
import com.ventooth.worldwide.event.CUIEvent;
import com.ventooth.worldwide.event.CUIEventType;
import lombok.val;

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
        Worldwide.LOG.debug("Resizing selection: [min={}, max={}]", min, max);
        selection().setMinMax(min, max);
        return null;
    }
}
