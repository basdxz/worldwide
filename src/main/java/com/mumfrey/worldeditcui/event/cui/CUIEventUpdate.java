package com.mumfrey.worldeditcui.event.cui;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.event.CUIEvent;
import com.mumfrey.worldeditcui.event.CUIEventType;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;

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
		LOG.debug("Received update");
        return null;
    }
}
