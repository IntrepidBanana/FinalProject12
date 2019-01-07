package com.aidenlauris.gameobjects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.render.PaintHelper;

public class PopIcon extends GameObject {
	
	float len = 24;
	float wid = 24;
	
	public PopIcon(float x, float y) {
		this.x = x;
		this.y = y;
		
		
	}
	
	
	@Override
	public
	void update() {
		// TODO Auto-generated method stub

	}

	
	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		
		g2d.setColor(Color.DARK_GRAY);
		g2d.fill(new Rectangle2D.Float(drawX, drawY, wid, len));
		
		return g2d;
	}
}
