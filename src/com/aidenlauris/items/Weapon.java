/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Weapon
 * super class for all types of weapons
 */

package com.aidenlauris.items;

import com.aidenlauris.game.util.Time;

public abstract class Weapon extends Item {

	
	//variables
	private int damage;
	private int atkSpeed;
	
	/**
	 * Initiates a weapon with set values
	 * @param damage damage of weapon
	 * @param atkSpeed attack speed
	 */
	public Weapon(int damage, int atkSpeed) {
		super();
		this.damage = damage;
		this.atkSpeed = atkSpeed;
	}
	
	/**
	 * @return damage of weapon
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * @param damage new damage of weapon
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * @return attack speed
	 */
	public int getAtkSpeed() {
		return atkSpeed;
	}
	
	/**
	 * @param atkSpeed new attack speed
	 */
	public void setAtkSpeed(int atkSpeed) {
		this.atkSpeed = atkSpeed;
	}

	/**
	 * Event that describes instruction for event of attack animation
	 * Meant to be overriden.
	 * @param playerX position of the player x coordinate
	 * @param playerY position of the player y coordinate
	 * @param theta angle the player is facing
	 */
	public void attackAnimation(float playerX, float playerY, float theta) {
	}
}
