package com.mumfrey.worldeditcui.render.region;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The different types of regions and the classes which we use to display them
 *
 * @author Adam Mummery-Smith
 */
@Getter
@AllArgsConstructor
public enum RegionType {
    // @formatter:off
    CUBOID    ("cuboid"    , "Cuboid"    , CuboidRegion.class    ),
    POLYGON   ("polygon2d" , "2D Polygon", PolygonRegion.class   ),
    ELLIPSOID ("ellipsoid" , "Ellipsoid" , EllipsoidRegion.class ),
    CYLINDER  ("cylinder"  , "Cylinder"  , CylinderRegion.class  ),
    POLYHEDRON("polyhedron", "Polyhedron", PolyhedronRegion.class),
    // @formatter:on
    ;

    private final String key;
    private final String name;
    private final Class<? extends BaseRegion> regionClass;

    @Override
    public String toString() {
        return name;
    }
}
