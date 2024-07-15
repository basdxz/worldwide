package com.mumfrey.worldeditcui.render.shapes;

import com.mumfrey.worldeditcui.render.LineColour;
import com.mumfrey.worldeditcui.render.LineInfo;
import com.mumfrey.worldeditcui.util.Vector3;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws a polygon
 *
 * @author yetanotherx
 * @author lahwran
 */
public class Render3DPolygon
{

	protected LineColour colour;
	protected Vector3[] vertices;

	public Render3DPolygon(LineColour colour, Vector3... vertices)
	{
		this.colour = colour;
		this.vertices = vertices;
	}

	public void render()
	{
		for (LineInfo tempColour : this.colour.getColours())
		{
			tempColour.prepareRender();

			glBegin(GL_LINE_LOOP);
			tempColour.prepareColour();
			for (Vector3 vertex : this.vertices)
			{
				glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
			}
			glEnd();
		}
	}
}
