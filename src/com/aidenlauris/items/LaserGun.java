/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * LaserGun
 * type of gun that shoots laser beams
 */

package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Beam;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.Projectile;

public class LaserGun extends Gun {

	/**
	 * creates a laser gun with preset values
	 */
	public LaserGun() {
		setLength(40);
		setAuto(false);
		
		setAccuracy(1);
		setAtkSpeed(20);
		
		setDamage(60);
		
		setSpread(0);
		setBulletCount(1);
		setAmmoPerUse(1);
		
		setSpawnSound("beam.wav");

		
		setAmmoType(new EnergyCell().item());
	}


	@Override
	public Projectile bulletType() {
		
		
		//factory for beam
		Beam b = new Beam(getDamage(), Player.getPlayer().x, Player.getPlayer().y);
		b.setMoveSpeed(100);
		b.setReduction(0);
		return b;
	}

}
