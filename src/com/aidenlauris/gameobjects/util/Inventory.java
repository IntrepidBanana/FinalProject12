package com.aidenlauris.gameobjects.util;

import com.aidenlauris.items.Ammo;
import com.aidenlauris.items.BulletAmmo;
import com.aidenlauris.items.EnergyCell;
import com.aidenlauris.items.ExplosiveAmmo;
import com.aidenlauris.items.Gun;
import com.aidenlauris.items.ShotgunAmmo;

/**
 * @author Lauris & Aiden 
 * Jan 21, 2019
 * 
 *         Players Inventory, contains space for 2 guns and all types of
 *         bullets. logic for switching, adding and validating guns
 */
public class Inventory {

	
	//guns this inventory has
	private Gun currentGun = null;
	private Gun storedGun = null;

	//ammo this inventory has
	private Ammo[] ammoCount = new Ammo[4];

	
	/**
	 * instantiates all bullet types
	 */
	public Inventory() {
		ammoCount[0] = new BulletAmmo();
		ammoCount[1] = new EnergyCell();
		ammoCount[2] = new ExplosiveAmmo();
		ammoCount[3] = new ShotgunAmmo();

	}

	/**
	 * switches the gun with the stored gun if possible
	 */
	public void swapGun() {
		if (currentGun == null || storedGun == null) {
			return;
		}

		Gun temp = currentGun;
		currentGun = storedGun;
		storedGun = temp;

	}

	/**
	 * switches the current gun or stored gun with another gun.
	 * @param gun from outside source
	 * @return gun that was inside the inventory
	 */
	public Gun addGun(Gun gun) {
		if (currentGun == null) {
			currentGun = gun;
			return null;
		}
		if (storedGun == null) {
			storedGun = gun;
			return null;
		}
		Gun temp = currentGun;
		currentGun = gun;
		return temp;

	}

	/**
	 * add the appropriate ammo to the appropriate counter
	 * @param ammo to add
	 */
	public void addAmmo(Ammo ammo) {
		switch (ammo.item()) {
		case "BulletAmmo":
			ammoCount[0].addToCount(ammo.getCount());
			break;
		case "EnergyCell":
			ammoCount[1].addToCount(ammo.getCount());
			break;
		case "ExplosiveAmmo":
			ammoCount[2].addToCount(ammo.getCount());
			break;
		case "ShotgunAmmo":
			ammoCount[3].addToCount(ammo.getCount());
			break;
		default:
			break;
		}
	}

	/**
	 * @return array of guns in inventory
	 */
	public Gun[] getGuns() {
		return new Gun[] { currentGun, storedGun };
	}

	/**
	 * returns the number of bullets a counter has
	 * @param ammo type of ammo to check for
	 * @return number of bullets
	 */
	public int getAmmoCount(Ammo ammo) {
		return getAmmoCount(ammo.item());
	}
	
	/**
	 * returns the number of bullets a counter has
	 * @param ammo type of ammo to check for
	 * @return number of bullets
	 */
	public int getAmmoCount(String ammo) {
		switch (ammo) {
		case "BulletAmmo":
			return ammoCount[0].getCount();
		case "EnergyCell":
			return ammoCount[1].getCount();
		case "ExplosiveAmmo":
			return ammoCount[2].getCount();
		case "ShotgunAmmo":
			return ammoCount[3].getCount();
		default:
			return 0;
		}
	}

	
	/**
	 * consume a certain number of the specified ammo 
	 * @param ammo ammo to consume
	 * @param count number of bullets consumed
	 */
	public void useAmmo(String ammo, int count) {
		switch (ammo) {
		case "BulletAmmo":
			ammoCount[0].removeFromCount(count);
			break;
		case "EnergyCell":
			ammoCount[1].removeFromCount(count);
			break;
		case "ExplosiveAmmo":
			ammoCount[2].removeFromCount(count);
			break;
		case "ShotgunAmmo":
			ammoCount[3].removeFromCount(count);
			break;
		default:
			break;
		}
	}

	/**
	 * get the number of bullets needed by a gun
	 * @param gun type of gun to check the ammo count for
	 * @return number of bullets
	 */
	public int getAmmoCount(Gun gun) {
		return getAmmoCount(gun.getAmmoType());
	}

	/**
	 * gets the current gun
	 * @return the current gun
	 */
	public Gun getGun() {
		return currentGun;
	}

	/**
	 * DEBUG PURPOSES
	 * @return a string of all ammo counts
	 */
	public String ammoToString() {
		String s = "";
		s += ammoCount[0].item() + " " + ammoCount[0].getCount() + " ";
		s += ammoCount[1].item() + " " + ammoCount[1].getCount() + " ";
		s += ammoCount[2].item() + " " + ammoCount[2].getCount() + " ";
		s += ammoCount[3].item() + " " + ammoCount[3].getCount();
		return s;
	}
}
