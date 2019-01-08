package com.aidenlauris.gameobjects;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HitBox;

public class Slug extends Enemy {

	public Slug(int x, int y) {
		super(x, y, 50, 5, 0.5f);
		getCollisionBoxes().clear();
		addCollisionBox(new HitBox(this, 15, 15, true));
	}
	
	
	public void attack() {
		
	}
	
	
	
	

}
