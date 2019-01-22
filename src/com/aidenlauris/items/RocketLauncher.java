package com.aidenlauris.items;

import java.awt.Graphics2D;

import com.aidenlauris.gameobjects.Projectile;
import com.aidenlauris.gameobjects.Rocket;

public class RocketLauncher extends Gun {

	/**
	 * creates a rocket launcher with preset values
	 */
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
		//rocket factory 
		Rocket r = new Rocket(getDamage());
		r.setMoveSpeed(10f);
		r.setReduction(0.01f);
		return r;
	}

	
}
