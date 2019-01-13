package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Beam;
import com.aidenlauris.gameobjects.Projectile;

public class LaserGun extends Gun {

	public LaserGun() {
		setLength(40);
		setAuto(false);
		
		setAccuracy(1);
		setAtkSpeed(10);
		
		setDamage(120);
		
		setSpread(0);
		setBulletCount(1);
		setAmmoPerUse(1);
		
		setAmmoType(new EnergyCell().item());
	}

	public LaserGun(double weight, int stackSize, int damage, int atkSpeed) {
		super(weight, stackSize, damage, atkSpeed);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Projectile bulletType() {
		
		Beam b = new Beam(getDamage());
		b.setMoveSpeed(100);
		b.setReduction(0);
		return b;
	}

}
