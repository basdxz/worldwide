package com.mumfrey.worldeditcui.render.selection;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The different types of selections and the classes which we use to display them
 *
 * @author Adam Mummery-Smith
 */
@Getter
@AllArgsConstructor
public enum SelectionType {
    // @formatter:off
    CUBOID    ("cuboid"    , "Cuboid"    , CuboidSelection.class    ),
    POLYGON   ("polygon2d" , "2D Polygon", PolygonSelection.class   ),
    ELLIPSOID ("ellipsoid" , "Ellipsoid" , EllipsoidSelection.class ),
    CYLINDER  ("cylinder"  , "Cylinder"  , CylinderSelection.class  ),
    POLYHEDRON("polyhedron", "Polyhedron", PolyhedronSelection.class),
    // @formatter:on
    ;

    private final String key;
    @Getter(AccessLevel.NONE)
    private final String name;
    private final Class<? extends SelectionBase> selectionClass;

    @Override
    public String toString() {
        return name;
    }
}
