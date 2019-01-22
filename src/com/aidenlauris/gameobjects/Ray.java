/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Fires a ray at a direction to record collision in that direction
 * 
 */

package com.aidenlauris.gameobjects;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.render.PaintHelper;

public class Ray extends Entity {

	
	
	//whether this object has collided or not
	private boolean collided = false;
	
	//final xy position
	public float fx = 0;
	public float fy = 0;

	/**
	 * Fires a ray from a start point to an end point
	 * @param x start x
	 * @param y start y
	 * @param destX end x
 	 * @param destY end y
	 */
	public Ray(float x, float y, float destX, float destY) {
		super(x, y);
		
		//invoke a very fast force
		float theta = (float) ((float) Math.atan2(destY - y, destX - x) + Math.toRadians((Math.random() * 3 - 1.5)));
		Force f = new Force(100, theta);
		f.setReduction(0);
		f.setLifeSpan(15);
		getForceSet().addForce(f);
		addCollisionBox(new HitBox(this, 10, 10, false));
		forceAccurate = true;
		init();
		
		//compute a large amount of collisions until it collides with something
		computeCollision();

	}

	/**
	 * does a large amount of intra tick force updates to check when this ray collides with something
	 */
	private void computeCollision() {
		for (int i = 0; i < 500; i++) {
			forceUpdate();
			if (collided) {
				fx = x;
				fy = y;
				break;
			}
		}
	}

	@Override
	public void collisionOccured(CollisionBox theirBox, CollisionBox myBox) {
		if (theirBox.getOwner() instanceof Wall) {
			
			// when it collides, remove game object and set final coordinates
			collided = true;
			kill();
		}

	}

	@Override
	public void update() {

	}

}
