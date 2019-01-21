/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Corpse
 * Spawns when an enemy dies
 */

package com.aidenlauris.gameobjects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.CollisionHelper;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.render.PaintHelper;

public class Corpse extends Entity {

	private Entity owner;
	private float wid = 0;
	private float hgt = 0;
	
	
	Corpse(float x, float y, Entity e) {
		super(x, y);
//		forces = e.forces;
		Force f = new Force(e.getForceSet().getNetMagnitude(), e.getForceSet().getNetTheta());
		f.setReduction(0.1f);
		getForceSet().addForce(f);
		owner = e;
		 wid = CollisionHelper.width(owner);
		 hgt = CollisionHelper.length(owner);
		team = Team.ENEMY;
		health = 10;
		invincibility = 10;
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		if (box.isSolid) {
			collide(box, myBox);
		}

	}

	@Override
	public void update() {
		invincibility = 10;
		tickUpdate();
		if(time > 120) {
			kill();
		}
	}
	
	@Override
	public Graphics2D draw(Graphics2D g2d) {

		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);

		
		Shape s = new Rectangle2D.Float(drawX- wid/2, drawY-hgt/2, wid, hgt);
		
		g2d.setColor(Color.darkGray);
		g2d.fill(s);
		
		
		
		return g2d;
	}

}
