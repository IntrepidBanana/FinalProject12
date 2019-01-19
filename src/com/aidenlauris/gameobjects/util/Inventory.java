package com.aidenlauris.gameobjects.util;

import com.aidenlauris.gameobjects.Ammo;
import com.aidenlauris.items.BulletAmmo;
import com.aidenlauris.items.EnergyCell;
import com.aidenlauris.items.ExplosiveAmmo;
import com.aidenlauris.items.Gun;
import com.aidenlauris.items.ShotgunAmmo;

public class Inventory {

	private Gun currentGun = null;
	private Gun storedGun = null;

	private Ammo[] ammoCount = new Ammo[4];

	public Inventory() {
		ammoCount[0] = new BulletAmmo();
		ammoCount[1] = new EnergyCell();
		ammoCount[2] = new ExplosiveAmmo();
		ammoCount[3] = new ShotgunAmmo();

	}

	public void swapGun() {
		if (currentGun == null || storedGun == null) {
			return;
		}

		Gun temp = currentGun;
		currentGun = storedGun;
		storedGun = temp;

	}

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

	public Gun[] getGuns() {
		return new Gun[] { currentGun, storedGun };
	}

	public int getAmmoCount(Ammo ammo) {
		return getAmmoCount(ammo.item());
	}

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

	public int getAmmoCount(Gun gun) {
		return getAmmoCount(gun.getAmmoType());
	}

	public Gun getGun() {
		return currentGun;
	}

	public String ammoToString() {
		String s = "";
		s += ammoCount[0].item() + " " + ammoCount[0].getCount() + " ";
		s += ammoCount[1].item() + " " + ammoCount[1].getCount() + " ";
		s += ammoCount[2].item() + " " + ammoCount[2].getCount() + " ";
		s += ammoCount[3].item() + " " + ammoCount[3].getCount();
		return s;
	}
}
