/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Shotgun
 * type of gun that shoots shotgun shells
 */
package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Projectile;
import com.aidenlauris.gameobjects.ShotgunShell;

public class Shotgun extends Gun {

	/**
	 * Creates a shotgun with preset values
	 */
	public Shotgun() {
		setLength(25);
		setAuto(false);

		setAccuracy(8);
		setAtkSpeed(30);
		
		setDamage(20);
		
		setSpread(3);
		setBulletCount(15);
		setAmmoPerUse(1);
		
		setAmmoType(new ShotgunAmmo().item());
		
		setSpawnSound("shotgun.wav");
		
		
	}

	@Override
	public Projectile bulletType() {
		
		//shotgun shell factory
		ShotgunShell b = new ShotgunShell(getDamage());
		b.setMoveSpeed(20f);
		b.setReduction(0.05f);
		
		return b;
		
		
	}

}
