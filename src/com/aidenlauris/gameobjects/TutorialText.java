package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;

import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.render.PaintHelper;

public class TutorialText extends GameObject {

	String s;
	
	public TutorialText(float x, float y, String s) {
		this.x = x;
		this.y = y;
		this.s = s;
	}
	
	@Override
	public void update() {

	}
	
	@Override
	public Graphics2D draw(Graphics2D g2d) {
		g2d.setFont(PaintHelper.font);
		g2d.setColor(Color.white);
		g2d.drawString(s, PaintHelper.x(x), PaintHelper.y(y));
		
		return g2d;
	}

}
