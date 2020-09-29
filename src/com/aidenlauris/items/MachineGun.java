/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * MachineGun
 * type of gun that uses machine bullet, fully automatic
 */

package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Bullet;
import com.aidenlauris.gameobjects.Projectile;

public class MachineGun extends Gun {

	/**
	 * Creates a machine gun with preset values
	 */
	public MachineGun() {
		setAtkSpeed(8);
		setLength(20);
		setAccuracy(3);
		setDamage(20);
		setAmmoType(new BulletAmmo().item());
		
		setSpawnSound("machine.wav");
	}

	@Override
	public Projectile bulletType() {
		
		//bullet factory
		Bullet b = new Bullet(getDamage());
		b.setMoveSpeed(20f);
		return b;
	}


}
