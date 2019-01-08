package com.aidenlauris.render.menu;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.render.PaintHelper;

public class MenuPointer extends MenuObject {

	GameObject pointingAt = null;
	float pointX = 0;
	float pointY = 0;

	public MenuPointer(float pointX, float pointY) {
		this.pointX = pointX;
		this.pointY = pointY;
	}

	public MenuPointer(GameObject e) {
		pointingAt = e;
	}

	@Override
	public
	Graphics2D draw(Graphics2D g2d) {
		if (pointingAt != null) {
			pointX = pointingAt.x;
			pointY = pointingAt.y;
		}

		float drawX = PaintHelper.x(pointX);
		float drawY = PaintHelper.y(pointY);

		Line2D horizontal = new Line2D.Float(x, y, (drawX + x) / 2, y);
		Line2D vertical = new Line2D.Float((drawX + x) / 2, y, drawX, drawY);
		Stroke old = g2d.getStroke();
		g2d.setColor(Color.gray);
		g2d.setStroke(new BasicStroke(3));
		g2d.draw(horizontal);
		g2d.draw(vertical);
		g2d.setStroke(old);

		return g2d;
	}

}
