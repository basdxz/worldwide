package com.mumfrey.worldeditcui.render.shapes;

import java.util.List;

import com.mumfrey.worldeditcui.render.LineColour;
import com.mumfrey.worldeditcui.render.LineInfo;
import com.mumfrey.worldeditcui.render.points.PointRectangle;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws the top and bottom rings of a polygon region
 *
 * @author yetanotherx
 * @author lahwran
 */
public class Render2DBox
{

	protected LineColour colour;
	protected List<PointRectangle> points;
	protected int min;
	protected int max;

	public Render2DBox(LineColour colour, List<PointRectangle> points, int min, int max)
	{
		this.colour = colour;
		this.points = points;
		this.min = min;
		this.max = max;
	}

	public void render()
	{
		double off = 0.03;
		for (LineInfo tempColour : this.colour.getColours())
		{
			tempColour.prepareRender();

			glBegin(GL_LINES);
			tempColour.prepareColour();

			for (PointRectangle point : this.points)
			{
				if (point != null)
				{
					glVertex3d(point.getPoint().getX() + 0.5, this.min + off, point.getPoint().getY() + 0.5);
					glVertex3d(point.getPoint().getX() + 0.5, this.max + 1 + off, point.getPoint().getY() + 0.5);
				}
			}
			GL11.glEnd();
		}
	}
}
