/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * BulletAmmo
 * type of ammo for pistols
 */

package com.aidenlauris.items;

public class BulletAmmo extends Ammo {
	
	/**
	 * Initates empty bullet ammo
	 */
	public BulletAmmo() {
	}
	/**
	 * initiates bullet with a set count
	 * @param count
	 */
	public BulletAmmo(int count){
		this();
		setCount(count);

	}

}
