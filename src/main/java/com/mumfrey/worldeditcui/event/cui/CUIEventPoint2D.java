package com.mumfrey.worldeditcui.event.cui;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.event.CUIEvent;
import com.mumfrey.worldeditcui.event.CUIEventType;
import lombok.val;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;

/**
 * Called when poly point event is received
 *
 * @author lahwran
 * @author yetanotherx
 */
public final class CUIEventPoint2D extends CUIEvent {
    public CUIEventPoint2D(WorldEditCUIController controller, String... args) {
        super(controller, args);
    }

    @Override
    public CUIEventType getEventType() {
        return CUIEventType.POINT2D;
    }

    @Override
    public String raise() {
        val id = this.getInt(0);
        val x = this.getInt(1);
        val z = this.getInt(2);
        LOG.debug("Setting polygon point: [id={}, x={}, z={}]", id, x, z);
        getSelection().setPolygonPoint(id, x, z);
        return null;
    }
}
