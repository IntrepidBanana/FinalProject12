package com.aidenlauris.items;

import com.aidenlauris.game.Time;

public abstract class Weapon extends Item {

	private int damage;
	private int atkSpeed;
	
	public Weapon(double weight, int stackSize, int damage, int atkSpeed) {
		super(weight, stackSize);
		this.damage = damage;
		this.atkSpeed = atkSpeed;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getAtkSpeed() {
		return (int) (atkSpeed/Time.delta());
	}
	
	public void setAtkSpeed(int atkSpeed) {
		this.atkSpeed = atkSpeed;
	}

}
