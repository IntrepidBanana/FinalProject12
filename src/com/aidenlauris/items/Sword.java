package com.aidenlauris.items;

import com.aidenlauris.gameobjects.MeleeSwing;
import com.aidenlauris.gameobjects.Projectile;

public class Sword extends Gun {
	
	public Sword() {
		
		setLength(10);
		setAuto(true);
		
		setAccuracy(0);
		setAtkSpeed(20);
		setQuickRelease(3);
		
		setSpread(3);
		setBulletCount(30);
		setDamage(40);
		
		setAmmoPerUse(0);
	}
	
	@Override
	public Projectile bulletType() {
		MeleeSwing b = new MeleeSwing(getDamage());
		b.setMoveSpeed(30f);
		b.setReduction(0.3f);
		return b;
	}

	

}
