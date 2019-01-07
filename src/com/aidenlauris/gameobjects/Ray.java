package com.aidenlauris.gameobjects;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.render.PaintHelper;

public class Ray extends Entity {

	private boolean collided = false;

	public Ray(float x, float y, float destX, float destY) {
		super(x, y);
		float theta = (float) ((float) Math.atan2(destY - y, destX - x) + Math.toRadians((Math.random() * 3 -1.5)));
		Force f = new Force(500, theta);
		f.setReduction(0);
		f.setLifeSpan(0);
		getForceSet().addForce(f);
		addCollisionBox(new HitBox(this, 3, 3, false)); 
		forceAccurate = true;
		health = 10;
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		g2d.fill(new Rectangle2D.Float(PaintHelper.x(x) - 2.5f, PaintHelper.y(y) - 2.5f, 5, 5));
		return g2d;
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		if (box.getOwner() instanceof Wall) {
			getForceSet().getForces().clear();
			collide(box, myBox);
			collided = true;
		}

	}

	@Override
	public void update() {
		time++;
		if (collided != true) {
			forceUpdate();
		}
		if (time > 1) {
			kill();
		}
	}

}
