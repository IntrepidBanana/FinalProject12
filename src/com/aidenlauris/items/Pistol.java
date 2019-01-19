package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Bullet;
import com.aidenlauris.gameobjects.Projectile;

public class Pistol extends MachineGun{

	public Pistol() {
		super();
		setAuto(true);
		setAtkSpeed(15);
		setQuickRelease(3);
		setDamage(20);
		setLength(10);
		setAccuracy(1);
		
		setSpawnSound("pew.wav");

	}
	
	@Override
	public Projectile bulletType() {
		Bullet b = new Bullet(getDamage());
		b.setMoveSpeed(20);
		return b;
	}
}
