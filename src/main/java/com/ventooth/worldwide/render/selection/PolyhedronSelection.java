package com.ventooth.worldwide.render.selection;

import com.ventooth.worldwide.WorldEditCUIController;
import com.ventooth.worldwide.config.LineColor;
import com.ventooth.worldwide.render.points.PointCube;
import com.ventooth.worldwide.render.shapes.Render3DPolygon;
import lombok.val;
import lombok.var;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.List;

/**
 * Main controller for a polygon-type selection
 *
 * @author TomyLobo
 */
public final class PolyhedronSelection extends Selection {
    private final List<PointCube> vertices = new ArrayList<>();
    private final List<Vector3dc[]> faces = new ArrayList<>();

    public PolyhedronSelection(WorldEditCUIController controller) {
        super(controller);
    }

    @Override
    public SelectionType type() {
        return SelectionType.POLYHEDRON;
    }

    @Override
    public void render() {
        for (val vertex : this.vertices)
            vertex.render();
        for (val face : this.faces)
            new Render3DPolygon(LineColor.POLYGON_BOX, face).render();
    }

    @Override
    public Selection setCuboidPoint(int id, double x, double y, double z) {
        val color = id == 0 ? LineColor.CUBOID_POINT_1 : LineColor.POLYGON_POINT;
        val vertex = new PointCube(color, x, y, z);

        if (id < vertices.size()) {
            vertices.set(id, vertex);
        } else {
            for (var i = 0; i < id - vertices.size(); i++)
                vertices.add(null);
            vertices.add(vertex);
        }
        return this;
    }
    
    @Override
    public Selection addPolygon(int[] vertexIds) {
        val face = new Vector3dc[vertexIds.length];
        for (var i = 0; i < vertexIds.length; ++i) {
            val vertex = vertices.get(vertexIds[i]);
            if (vertex == null) {
                // This should never happen
                return this;
            }

            face[i] = new Vector3d(vertex.x() + 0.5D, vertex.y() + 0.5D, vertex.z() + 0.5D);
        }
        faces.add(face);
        return this;
    }
}
