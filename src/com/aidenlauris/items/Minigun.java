package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Bullet;
import com.aidenlauris.gameobjects.Projectile;

public class Minigun extends Gun{

	public Minigun() {
		setAtkSpeed(1);
		setLength(50);
		setAccuracy(20);
		setBulletCount(2);
		setDamage(20);
		setAmmoType(new BulletAmmo().item());
		
		setSpawnSound("machine.wav");
	}
	
	@Override
	public Projectile bulletType() {
		Bullet b = new Bullet(getDamage());
		b.setMoveSpeed(20f);
		return b;
	}

}
