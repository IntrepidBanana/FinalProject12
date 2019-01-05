package com.aidenlauris.items;

import com.aidenlauris.gameobjects.CannonShell;
import com.aidenlauris.gameobjects.Projectile;

public class Cannon extends Gun {

	public Cannon() {
		setAtkSpeed(40);
		setQuickRelease(40);
		setLength(30);
		setAccuracy(0);
		setAuto(true);
		setDamage(120);
		setAmmoType(new ExplosiveAmmo().item());
	}

	@Override
	public Projectile bulletType() {
		CannonShell p = new CannonShell(getDamage());
		p.setMoveSpeed(30f);
		p.setReduction(0.08f);
		return p;
	}

	@Override
	public String item() {
		return this.toString();
	}
}
