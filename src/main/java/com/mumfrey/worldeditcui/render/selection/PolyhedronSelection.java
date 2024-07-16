package com.mumfrey.worldeditcui.render.selection;

import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.render.LineStyles;
import com.mumfrey.worldeditcui.render.points.PointCube;
import com.mumfrey.worldeditcui.render.shapes.Render3DPolygon;
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
public final class PolyhedronSelection extends SelectionBase {
    private final List<PointCube> vertices = new ArrayList<>();
    private final List<Vector3dc[]> faces = new ArrayList<>();

    public PolyhedronSelection(WorldEditCUIController controller) {
        super(controller);
    }

    @Override
    public SelectionType getType() {
        return SelectionType.POLYHEDRON;
    }

    @Override
    public void render() {
        for (val vertex : this.vertices)
            vertex.render();
        for (val face : this.faces)
            new Render3DPolygon(LineStyles.POLYBOX, face).render();
    }

    @Override
    public SelectionBase setCuboidPoint(int id, double x, double y, double z) {
        val lineStyles = id == 0 ? LineStyles.CUBOIDPOINT1 : LineStyles.POLYPOINT;
        val vertex = new PointCube(lineStyles, x, y, z);

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
    public SelectionBase addPolygon(int[] vertexIds) {
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
