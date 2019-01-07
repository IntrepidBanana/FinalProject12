package com.aidenlauris.gameobjects;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HitBox;

public class Slug extends Enemy {

	public Slug(int x, int y, int health, int strength, float speed) {
		super(x, y, health, strength, speed);
		health = 50;
		strength = 5;
		setMoveSpeed(0.15f);
		getCollisionBoxes().clear();
		addCollisionBox(new HitBox(this, 15, 15, true));
	}
	
	
	public void attack() {
		
	}
	
	
	
	

}
