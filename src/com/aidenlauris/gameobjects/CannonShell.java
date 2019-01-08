package com.aidenlauris.gameobjects;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.HurtBox;

public class CannonShell extends Projectile{

	//dead
	CannonShell(float x, float y,float damage, float theta, float gunOffset, float reduction) {
		super(x, y, 9f,damage, theta, gunOffset, reduction);
		weight = 10;
		addCollisionBox(new HurtBox(this, -4f, -4f, 8, 8, damage));
	}

	
	public CannonShell(int damage) {
//		weight = 10;
		HurtBox box = new HurtBox(this, 8, 8, damage);
		box.addHint(this.getClass());
		addCollisionBox(box);
		setKnockback(10f);
	}

	
	@Override
	public void kill() {
		Particle.create(x, y, 45f, getTheta(), 180, 20);
		WorldMap.addGameObject(new Explosion(x, y, 200, 30f, 150f));
		removeSelf();
	}
}
