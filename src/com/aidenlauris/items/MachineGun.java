package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Bullet;
import com.aidenlauris.gameobjects.Projectile;

public class MachineGun extends Gun {

	public MachineGun() {
		setAtkSpeed(3);
		setQuickRelease(3);
		setLength(20);
		setAccuracy(3);
		setDamage(70);
		setAmmoType(new BulletAmmo().item());
	}

	@Override
	public Projectile bulletType() {
		Bullet b = new Bullet(getDamage());
		b.setMoveSpeed(90f);
		return b;
	}


	@Override
	public String item() {
		return this.toString();
	}
}
