/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * ShotgunAmmo
 * type of ammo for shotguns
 */

package com.aidenlauris.items;

public class ShotgunAmmo extends Ammo {

	/**
	 * initiates empty ammo item
	 */
	public ShotgunAmmo() {
	}
	
	/**
	 * initiates ammo item with set count
	 * @param count count of ammunition
	 */
	public ShotgunAmmo(int count){
		this();
		setCount(count);
	}
}
