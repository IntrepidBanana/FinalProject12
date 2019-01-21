/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Ray
 * 
 */

package com.aidenlauris.gameobjects;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.render.PaintHelper;

public class Ray extends Entity {

	private boolean collided = false;
	public float fx = 0;
	public float fy = 0;

	public Ray(float x, float y, float destX, float destY) {
		super(x, y);
		float theta = (float) ((float) Math.atan2(destY - y, destX - x) + Math.toRadians((Math.random() * 3 - 1.5)));
		Force f = new Force(100, theta);
		f.setReduction(0);
		f.setLifeSpan(15);
		getForceSet().addForce(f);
		addCollisionBox(new HitBox(this, 10, 10, false));
		forceAccurate = true;
		init();
		computeCollision();

	}

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
			collided = true;
			kill();
		}

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
