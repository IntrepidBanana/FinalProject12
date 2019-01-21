/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Cursor
 * The spot where the mouse is to detect shooting direction
 */

package com.aidenlauris.gameobjects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.util.Mouse;
import com.aidenlauris.gameobjects.util.GameObject;

public class Cursor extends GameObject {

	public Cursor() {
		x = 0;
		y = 0;
//		addCollisionBox(new HitBox(this, 0f, 0f, 32, 32, false));
//		addCollisionBox(new HitBox(this, -1f, -1f, 2, 2, false));
		}	


	@Override
	public void update() {
		this.x = Mouse.getX(); //- rX % 32;
		this.y = Mouse.getY(); // - rY % 32;
	}
	
	@Override
	public Graphics2D draw(Graphics2D g2d) {
		g2d.setColor(Color.BLUE);
		g2d.fill(new Rectangle2D.Float(x-2.5f, y-2.5f, 5, 5));
		return g2d;
	}
}
