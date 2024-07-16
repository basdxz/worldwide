package com.mumfrey.worldeditcui.event.cui;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.event.CUIEvent;
import com.mumfrey.worldeditcui.event.CUIEventType;
import lombok.val;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;

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
        LOG.debug("Setting new selection: [{}]", selection);
        controller.selection(selection);
        return null;
    }
}
