package com.aidenlauris.gameobjects;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.HurtBox;

public class SuperSlug extends Slug {

	public SuperSlug(float x, float y) {
		super(x, y);
		
		this.health = 75;
		
		getCollisionBoxes().clear();
		addCollisionBox(new HitBox(this, 35, 35, true));
		addCollisionBox(new HurtBox(this, 40, 40, 5));
	}
	
	@Override
	public void kill() {
		
		super.kill();
		for (int i = 0; i < 4; i++){
		WorldMap.addGameObject(new Slug(this.x, this.y));
		}
	}

}
