package com.aidenlauris.gameobjects;

import java.util.Random;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.items.BulletAmmo;
import com.aidenlauris.items.ExplosiveAmmo;
import com.aidenlauris.items.ShotgunAmmo;

public class Chaser extends Enemy {

	public Chaser(float x, float y) {
		super(x, y, 25, 10, 3.5f);
	}

	@Override
	public void kill() {
		Random rand = new Random();
		int death = rand.nextInt((2 - 1) + 1) + 1;
		
		if (death == 1) {
		for (int i = 0; i < 450; i += 90) {
			Bullet b = new Bullet(2);
			b.x = this.x;
			b.y = this.y;
			Player p = Player.getPlayer();
			b.setMoveSpeed(8);
			b.setLifeSpan(60);
			b.setGunOffset(50);
			b.team = team.ENEMY;
			float theta = (float)Math.toRadians(i);
			b.setTheta(theta);
			b.init();
			
			
			ItemDropEntity.drop(x, y, new BulletAmmo(1), 0.2, 4, 10);
			ItemDropEntity.drop(x, y, new ShotgunAmmo(1), 0.05, 2, 3);
			ItemDropEntity.drop(x, y, new ExplosiveAmmo(1), 0.05, 1, 1);
			HealthDropEntity.drop(x, y, 0.15, 1, 3);
			
			WorldMap.addGameObject(new Corpse(x, y, this));
	System.out.println(getForceSet().getNetMagnitude());
			removeSelf();
			
		}
	}else if (death == 2) {
		for (int i = 45; i < 405; i += 90) {
			Bullet b = new Bullet(2);
			b.x = this.x;
			b.y = this.y;
			Player p = Player.getPlayer();
			b.setMoveSpeed(8);
			b.setLifeSpan(60);
			b.setGunOffset(50);
			b.team = team.ENEMY;
			float theta = (float)Math.toRadians(i);
			b.setTheta(theta);
			b.init();
		}
	}
		
		removeSelf();
	}
	
}
