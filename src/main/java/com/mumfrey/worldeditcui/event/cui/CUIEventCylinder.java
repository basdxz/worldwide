package com.mumfrey.worldeditcui.event.cui;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.event.CUIEvent;
import com.mumfrey.worldeditcui.event.CUIEventType;
import lombok.val;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;

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
        LOG.debug("Selecting cylinder: [x={}, y={}, z={}, radiusX={}, radiusZ={}]", x, y, z, radiusX, radiusZ);

        val selection = getSelection();
        selection.setCylinderCenter(x, y, z);
        selection.setCylinderRadius(radiusX, radiusZ);
        return null;
    }
}
