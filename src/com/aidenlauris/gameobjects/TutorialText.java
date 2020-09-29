package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;

import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.render.PaintHelper;

/**
 * @author Lauris & Aiden
 * Jan 22, 2019
 *
 * An object that displays text
 */
public class TutorialText extends GameObject {

	
	//String of object
	private String s;
	
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
		
		
		
		//draw the string
		g2d.setFont(PaintHelper.font);
		g2d.setColor(Color.white);
		g2d.drawString(s, PaintHelper.x(x), PaintHelper.y(y));
		
		return g2d;
	}

}
