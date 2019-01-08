package com.aidenlauris.gameobjects;

import com.aidenlauris.gameobjects.util.HitBox;

public class Giant extends Enemy {

	public Giant(float x, float y) {
		super(x, y, 100, 10, 0.5f);
		getCollisionBoxes().clear();
		addCollisionBox(new HitBox(this, 100, 100, true));
		
	}

}
