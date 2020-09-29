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

	// owner of corpse
	private Entity owner;

	// size of corpse
	private float wid = 0;
	private float hgt = 0;

	/**
	 * initiates corpse with coordinate x, y based on owner
	 * 
	 * @param x
	 *            coord x
	 * @param y
	 *            coord y
	 * @param e
	 *            owner of corpse
	 */
	public Corpse(float x, float y, Entity e) {
		super(x, y);
		owner = e;
		team = Team.ENEMY;
		health = 1;
		// add a force equivalent to the forces taken by the entity at time of death
		Force f = new Force(e.getForceSet().getNetMagnitude(), e.getForceSet().getNetTheta());
		f.setReduction(0.1f);
		getForceSet().addForce(f);

		wid = CollisionHelper.width(owner);
		hgt = CollisionHelper.length(owner);
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {

	}

	@Override
	public void update() {
		tickUpdate();
		
		//kill after 2 seconds
		if (time > 120) {
			kill();
		}
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {

		//draws the corpse 
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);

		
		Shape s = new Rectangle2D.Float(drawX - wid / 2, drawY - hgt / 2, wid, hgt);

		g2d.setColor(Color.darkGray);
		g2d.fill(s);

		return g2d;
	}

}
