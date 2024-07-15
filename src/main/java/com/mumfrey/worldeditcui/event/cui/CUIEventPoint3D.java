package com.mumfrey.worldeditcui.event.cui;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.event.CUIEvent;
import com.mumfrey.worldeditcui.event.CUIEventType;
import lombok.val;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;

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
        LOG.debug("Setting cuboid point: [id={}, x={}, y={}, z={}]", id, x, y, z);
        getSelection().setCuboidPoint(id, x, y, z);
        return null;
    }
}
