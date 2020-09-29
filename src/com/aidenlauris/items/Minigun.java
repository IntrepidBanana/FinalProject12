package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Bullet;
import com.aidenlauris.gameobjects.Projectile;

public class Minigun extends MachineGun{

	/**
	 * creates a minigun with set values
	 */
	public Minigun() {
		setAtkSpeed(1);
		setLength(50);
		setAccuracy(20);
		setDamage(20);
		setAmmoType(new BulletAmmo().item());
		
		setSpawnSound("machine.wav");
	}
	

}
