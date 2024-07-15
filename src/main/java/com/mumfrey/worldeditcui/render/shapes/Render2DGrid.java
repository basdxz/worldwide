package com.mumfrey.worldeditcui.render.shapes;

import java.util.List;

import com.mumfrey.worldeditcui.render.LineColour;
import com.mumfrey.worldeditcui.render.LineInfo;
import com.mumfrey.worldeditcui.render.points.PointRectangle;

import static org.lwjgl.opengl.GL11.*;

/**
 * Draws the grid for a polygon region
 *
 * @author yetanotherx
 * @author lahwran
 */
public class Render2DGrid
{
	protected LineColour colour;
	protected List<PointRectangle> points;
	protected int min;
	protected int max;

	public Render2DGrid(LineColour colour, List<PointRectangle> points, int min, int max)
	{
		this.colour = colour;
		this.points = points;
		this.min = min;
		this.max = max;
	}

	public void render()
	{
		double off = 0.03;
		for (double height = this.min; height <= this.max + 1; height++)
		{
			this.drawPoly(height + off);
		}
	}

	protected void drawPoly(double height)
	{
		for (LineInfo tempColour : this.colour.getColours())
		{
			tempColour.prepareRender();

			glBegin(GL_LINE_LOOP);
			tempColour.prepareColour();
			for (PointRectangle point : this.points)
			{
				if (point != null)
				{
					glVertex3d(point.getPoint().getX() + 0.5, height, point.getPoint().getY() + 0.5);
				}
			}
			glEnd();
		}
	}
}
