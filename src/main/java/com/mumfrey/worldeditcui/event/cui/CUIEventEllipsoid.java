package com.mumfrey.worldeditcui.event.cui;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.event.CUIEvent;
import com.mumfrey.worldeditcui.event.CUIEventType;
import lombok.val;

import static com.mumfrey.worldeditcui.WorldEditCUI.LOG;

/**
 * Called when ellipsoid event is received
 *
 * @author lahwran
 * @author yetanotherx
 */
public final class CUIEventEllipsoid extends CUIEvent {
    public CUIEventEllipsoid(WorldEditCUIController controller, String... args) {
        super(controller, args);
    }

    @Override
    public CUIEventType getEventType() {
        return CUIEventType.ELLIPSOID;
    }

    @Override
    public String raise() {
        val id = this.getInt(0);
        if (id == 0) {
            setCenter();
        } else if (id == 1) {
            setRadii();
        } else {
            throw new IllegalStateException("Unknown ID: [" + id + "]");
        }
        return null;
    }

    private void setCenter() {
        val x = getInt(1);
        val y = getInt(2);
        val z = getInt(3);
        LOG.debug("Selecting ellipsoid center: [x={}, y={}, z={}]", x, y, z);
        getSelection().setEllipsoidCenter(x, y, z);
    }

    private void setRadii() {
        val radiusX = getDouble(1);
        val radiusY = getDouble(2);
        val radiusZ = getDouble(3);
        LOG.debug("Selecting ellipsoid radii: [radiusX={}, radiusY={}, radiusZ={}]", radiusX, radiusY, radiusZ);
        getSelection().setEllipsoidRadii(radiusX, radiusY, radiusZ);
    }
}
