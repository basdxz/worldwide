package com.ventooth.worldwide.event.cui;

import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.Worldwide;
import com.ventooth.worldwide.event.CUIEvent;
import com.ventooth.worldwide.event.CUIEventType;
import lombok.val;
import lombok.var;

import java.util.Arrays;

/**
 * Called when polygon event is received
 *
 * @author lahwran
 * @author yetanotherx
 */
public final class CUIEventPolygon extends CUIEvent {
    public CUIEventPolygon(WorldEditCUIController controller, String[] args) {
        super(controller, args);
    }

    @Override
    public CUIEventType getEventType() {
        return CUIEventType.POLYGON;
    }

    @Override
    public String raise() {
        val vertexIds = getVertexIds();
        Worldwide.LOG.debug("Adding new polygon: {}", Arrays.toString(vertexIds));
        selection().addPolygon(vertexIds);
        return null;
    }

    private int[] getVertexIds() {
        val length = args.length;
        val vertexIds = new int[length];
        for (var i = 0; i < length; ++i)
            vertexIds[i] = getInt(i);
        return vertexIds;
    }
}
