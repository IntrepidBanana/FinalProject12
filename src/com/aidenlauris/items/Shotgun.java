package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Projectile;
import com.aidenlauris.gameobjects.ShotgunShell;

public class Shotgun extends Gun {

	public Shotgun() {
//		super(1,1,30,25);
		setLength(25);
		setAuto(false);

		setAccuracy(8);
		setAtkSpeed(30);
		
		setDamage(20);
		
		setSpread(1);
		setBulletCount(8);
		setAmmoPerUse(1);
		
		setAmmoType(new ShotgunAmmo().item());
		
		setSpawnSound("shotgun.wav");
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Projectile bulletType() {
		
		ShotgunShell b = new ShotgunShell(getDamage());
		b.setMoveSpeed(20f);
		b.setReduction(0.05f);
		
		return b;
		
		
	}

	@Override
	public String item() {
		return this.toString();
	}
}
