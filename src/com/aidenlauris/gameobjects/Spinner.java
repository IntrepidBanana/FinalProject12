package com.aidenlauris.gameobjects;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HurtBox;

public class Spinner extends Enemy {

	private boolean spinnersCreated = false;

	public Spinner(float x, float y) {
		super(x, y, 50, 20, 2);
	}

	public void move() {
		// float dist = (float) Math
		// .sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) +
		// Math.pow(WorldMap.getPlayer().y - y, 2));
		//
		// Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() *
		// 360));
		// f.setId("FourMove");
		// f.setLifeSpan(60);
		// f.setReduction(0);
		// if (getForceSet().getForce("FourMove") == null) {
		// addForce(f);
		// }
	}

	public void update() {
		tickUpdate();
		if (!spinnersCreated) {
			attack();
			spinnersCreated = true;
		}
	}

	public void attack() {

		SpinnerBlade a = new SpinnerBlade(this, 20, 0, 1, 50);
		SpinnerBlade b = new SpinnerBlade(this, 20, 180, 1, 50);

	}

	@Override
	public void knockBack(float magnitude, float theta) {
	}

}