package com.aidenlauris.gameobjects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.render.PaintHelper;

public class Corpse extends Entity {

	Corpse(float x, float y, Entity e) {
		super(x, y);
		for (CollisionBox c : e.getCollisionBoxes()) {
			CollisionBox box = new HitBox(this, c.x, c.y, c.len, c.wid, true);
			addCollisionBox(box);
		}
//		forces = e.forces;
		Force f = new Force(e.getForceSet().getNetMagnitude(), e.getForceSet().getNetTheta());
		f.setReduction(0.08f);
		getForceSet().addForce(f);
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
		g2d.setColor(Color.red);
		g2d.draw( new Rectangle2D.Float(PaintHelper.x(x-12), PaintHelper.y(y-12), 24, 24));
		return g2d;
	}

}
