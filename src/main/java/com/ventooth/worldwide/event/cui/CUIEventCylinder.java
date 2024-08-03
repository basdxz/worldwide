package com.ventooth.worldwide.event.cui;

import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.Worldwide;
import com.ventooth.worldwide.event.CUIEvent;
import com.ventooth.worldwide.event.CUIEventType;
import lombok.val;

/**
 * Called when cylinder event is received
 *
 * @author lahwran
 * @author yetanotherx
 */
public final class CUIEventCylinder extends CUIEvent {
    public CUIEventCylinder(WorldEditCUIController controller, String... args) {
        super(controller, args);
    }

    @Override
    public CUIEventType getEventType() {
        return CUIEventType.CYLINDER;
    }

    @Override
    public String raise() {
        val x = this.getInt(0);
        val y = this.getInt(1);
        val z = this.getInt(2);
        val radiusX = this.getDouble(3);
        val radiusZ = this.getDouble(4);
        Worldwide.LOG.debug("Selecting cylinder: [x={}, y={}, z={}, radiusX={}, radiusZ={}]", x, y, z, radiusX, radiusZ);

        val selection = selection();
        selection.setCylinderCenter(x, y, z);
        selection.setCylinderRadius(radiusX, radiusZ);
        return null;
    }
}
