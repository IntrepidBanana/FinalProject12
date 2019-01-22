package com.aidenlauris.render.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.util.Time;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.render.PaintHelper;

/**
 * @author Aiden & Lauris
 * Jan 22, 2019
 *
 * Menu object that appears over players head with a text that slowly fades awway
 */
public class CollectionText extends MenuObject {

	//variables
	
	//String to print to screen
	public String s = "";
	
	//how far the collection text has moved upward
	private int move = 0;
	
	
	
	//length the object will appear for
	private final int timeLength = 180;
	private long lifespan = Time.alert(timeLength);

	
	//level of alpha
	private final float alphaDecay = 1 / (timeLength*1.0f);
	private float alpha = 1;
	
	public CollectionText(String s) {
		this.s = s;
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		int wid = g2d.getFontMetrics().stringWidth(s);
		int pad = 4;

		float dx = PaintHelper.x(Player.getPlayer().x);
		float dy = PaintHelper.y(Player.getPlayer().y - move);
		move++;

		g2d.setFont(PaintHelper.font);
		g2d.setColor(new Color(1, 1, 1, Math.max(alpha, 0)));
		g2d.drawString(s, dx - wid / 2, dy - 32);

		alpha -= alphaDecay;
		
		//remove this menu if the life span has elapsed
		if (Time.alertPassed(lifespan)) {
			remove = true;
		}

		return g2d;
	}

}
