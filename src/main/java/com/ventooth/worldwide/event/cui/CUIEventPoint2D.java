package com.ventooth.worldwide.event.cui;

import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.Worldwide;
import com.ventooth.worldwide.event.CUIEvent;
import com.ventooth.worldwide.event.CUIEventType;
import lombok.val;

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
        Worldwide.LOG.debug("Setting polygon point: [id={}, x={}, z={}]", id, x, z);
        selection().setPolygonPoint(id, x, z);
        return null;
    }
}
