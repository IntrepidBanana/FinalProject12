/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Pistol
 * type of gun that shoots bullets, player starting gun
 */

package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Bullet;
import com.aidenlauris.gameobjects.Projectile;

public class Pistol extends MachineGun{

	/**
	 * creates a pistol with preset values
	 */
	public Pistol() {
		setAuto(false);
		setAtkSpeed(4);
		setDamage(20);
		setLength(10);
		setAccuracy(1);
		
		setSpawnSound("pew.wav");

	}
	
	@Override
	public Projectile bulletType() {
		//bullet factory
		Bullet b = new Bullet(getDamage());
		b.setMoveSpeed(16);
		return b;
	}
}
