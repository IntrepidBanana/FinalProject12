package com.aidenlauris.gameobjects;

import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.gameobjects.util.HitBox;

public class Portal extends GameObject {

	public Portal() {

	}

	public Portal(Enemy lastEnemy) {

		
		
		addCollisionBox(new HitBox(this, 30, 30, false));
		ForceAnchor fa = new ForceAnchor(10f, Player.getPlayer(), this, -1);
		Player.getPlayer().getForceSet().addForce(fa, 30);
	
		x = lastEnemy.x;
		y = lastEnemy.y;
		
		
	
	}

	@Override
	public void update() {
	}
	
	
	
	
}
