/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Cursor
 * The spot where the mouse, mainly graphical component
 */

package com.aidenlauris.gameobjects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.util.Mouse;
import com.aidenlauris.gameobjects.util.GameObject;

public class Cursor extends GameObject {

	/**
	 * initiates cursor
	 */
	public Cursor() {
		x = 0;
		y = 0;
		}	


	@Override
	public void update() {
		//update x and y position 
		this.x = Mouse.getX(); 
		this.y = Mouse.getY();
	}
	
	@Override
	public Graphics2D draw(Graphics2D g2d) {
		
		//draw cursor
		g2d.setColor(Color.WHITE);
		g2d.fill(new Rectangle2D.Float(x-10f, y-2f, 20, 4));
		g2d.fill(new Rectangle2D.Float(x-2f, y-10f, 4, 20));
		return g2d;
	}
}
