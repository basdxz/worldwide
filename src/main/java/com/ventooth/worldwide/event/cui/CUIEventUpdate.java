package com.ventooth.worldwide.event.cui;

import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.Worldwide;
import com.ventooth.worldwide.event.CUIEvent;
import com.ventooth.worldwide.event.CUIEventType;

/**
 * Called when update event is received
 *
 * @author lahwran
 * @author yetanotherx
 */
public final class CUIEventUpdate extends CUIEvent {
    public CUIEventUpdate(WorldEditCUIController controller, String... args) {
        super(controller, args);
    }

    @Override
    public CUIEventType getEventType() {
        return CUIEventType.UPDATE;
    }

    @Override
    public String raise() {
        Worldwide.LOG.debug("Received update");
        return null;
    }
}
