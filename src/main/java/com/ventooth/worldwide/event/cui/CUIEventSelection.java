package com.ventooth.worldwide.event.cui;

import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.Worldwide;
import com.ventooth.worldwide.event.CUIEvent;
import com.ventooth.worldwide.event.CUIEventType;
import lombok.val;

/**
 * Called when selection event is received
 *
 * @author lahwran
 * @author yetanotherx
 */
public final class CUIEventSelection extends CUIEvent {
    public CUIEventSelection(WorldEditCUIController controller, String... args) {
        super(controller, args);
    }

    @Override
    public CUIEventType getEventType() {
        return CUIEventType.SELECTION;
    }

    @Override
    public String raise() {
        val key = this.getString(0);
        val selection = controller.selectionProvider().createSelection(key);
        if (selection != null)
            selection.initialize();
        Worldwide.LOG.debug("Setting new selection: [{}]", selection);
        controller.selection(selection);
        return null;
    }
}
