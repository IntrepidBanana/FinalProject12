package com.aidenlauris.gameobjects;
import java.awt.Graphics2D;

import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.render.PaintHelper;

public class Explosion extends Entity {

	float magnitude;
	float size;
	float lifespan = 2;

	Explosion(float x, float y, float size, float magnitude, float damage) {
		super(x, y);
		this.magnitude = magnitude;
		this.size = size;
		team = team.PLAYER;
		addCollisionBox(new HurtBox(this, -size / 2, -size / 2, size, size, damage));
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		if (!(box.getOwner() instanceof Entity)) {
			return;
		}
		if (((Entity)box.getOwner()).team != team.PLAYER) {
			float radius = size / 2;
			float dist = (float) Math.hypot(box.x - myBox.x, box.y - myBox.y);
			float effectiveMagnitude = (float) Math.max(magnitude / (-3 * (size / 2)) * dist + magnitude, 0);
			((Entity) box.getOwner()).damage((HurtBox) myBox);
			((Entity) box.getOwner()).knockBack(effectiveMagnitude, myBox.getX(), myBox.getY(), box.getX(), box.getY());
		}
	}

	@Override
	public void update() {
		if (lifespan <= 0) {
			kill();
		}
		lifespan--;
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		
		
		g2d = PaintHelper.drawCollisionBox(g2d, collisionBoxes);
		return g2d;
	}
}
