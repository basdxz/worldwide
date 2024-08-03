package com.ventooth.worldwide.event;

import com.ventooth.worldwide.event.cui.*;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public enum CUIEventType {
	// @formatter:off
	SELECTION(CUIEventSelection.class, "s"   , 1    ),
	POINT    (CUIEventPoint3D.class  , "p"   , 5,  6),
	POINT2D  (CUIEventPoint2D.class  , "p2"  , 4,  5),
	ELLIPSOID(CUIEventEllipsoid.class, "e"   , 4    ),
	CYLINDER (CUIEventCylinder.class , "cyl" , 5    ),
	MINMAX   (CUIEventBounds.class   , "mm"  , 2    ),
	UPDATE   (CUIEventUpdate.class   , "u"   , 1    ),
	POLYGON  (CUIEventPolygon.class  , "poly", 3, 99),
	// @formatter:on
	;

	private final Class<? extends CUIEvent> eventClass;
	private final String key;
	@Getter(AccessLevel.NONE)
	private final String name;
	private final int minParameters;
	private final int maxParameters;

	CUIEventType(Class<? extends CUIEvent> eventClass, String key, int paramCount) {
		this(eventClass, key, paramCount, paramCount);
	}

	CUIEventType(Class<? extends CUIEvent> eventClass, String key, int minParameters, int MaxParameters) {
		this.eventClass = eventClass;
		this.key = key;
		this.name = eventClass.getSimpleName().substring(8);
		this.minParameters = minParameters;
		this.maxParameters = MaxParameters;
	}


	@Override
	public String toString() {
		return name;
	}
}
