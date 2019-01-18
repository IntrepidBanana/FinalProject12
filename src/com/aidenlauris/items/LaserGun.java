package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Beam;
import com.aidenlauris.gameobjects.Player;
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
		
		setSpawnSound("beam.wav");

		
		setAmmoType(new EnergyCell().item());
	}

	public LaserGun(double weight, int stackSize, int damage, int atkSpeed) {
		super(weight, stackSize, damage, atkSpeed);
	}

	@Override
	public Projectile bulletType() {
		
		Beam b = new Beam(getDamage(), Player.getPlayer().x, Player.getPlayer().y);
		b.setMoveSpeed(100);
		b.setReduction(0);
		return b;
	}

}
