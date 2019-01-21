package com.aidenlauris.game;

import java.awt.Graphics2D;

import com.aidenlauris.gameobjects.Projectile;
import com.aidenlauris.gameobjects.Rocket;
import com.aidenlauris.items.ExplosiveAmmo;
import com.aidenlauris.items.Gun;

public class RocketLauncher extends Gun {

	public RocketLauncher() {
		setAtkSpeed(30);
		setLength(30);
		setAccuracy(0);
		setAuto(false);
		setDamage(120);
		
		setSpawnSound("cannon.wav");

		setAmmoType(new ExplosiveAmmo().item());
	}
	@Override
	public Projectile bulletType() {
		Rocket r = new Rocket(getDamage());
		r.setMoveSpeed(10f);
		r.setReduction(0.01f);
		return r;
	}

	
	@Override
	public Graphics2D attackAnimation(Graphics2D g2d, float playerX, float playerY, float theta) {
		
		
		return super.attackAnimation(g2d, playerX, playerY, theta);
	}
}
