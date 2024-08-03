package com.ventooth.worldwide.event.cui;

import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.Worldwide;
import com.ventooth.worldwide.event.CUIEvent;
import com.ventooth.worldwide.event.CUIEventType;
import lombok.val;

/**
 * Called when point event is received
 *
 * @author lahwran
 * @author yetanotherx
 */
public final class CUIEventPoint3D extends CUIEvent {
    public CUIEventPoint3D(WorldEditCUIController controller, String... args) {
        super(controller, args);
    }

    @Override
    public CUIEventType getEventType() {
        return CUIEventType.POINT;
    }

    @Override
    public String raise() {
        val id = this.getInt(0);
        val x = this.getDouble(1);
        val y = this.getDouble(2);
        val z = this.getDouble(3);
        Worldwide.LOG.debug("Setting cuboid point: [id={}, x={}, y={}, z={}]", id, x, y, z);
        selection().setCuboidPoint(id, x, y, z);
        return null;
    }
}
