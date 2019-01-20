package com.aidenlauris.gameobjects;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.Force;

public class PoisonWalker extends Enemy {

	public PoisonWalker(float x, float y) {
		super(x, y, 50, 10, 2);
	}

	@Override
	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));

		Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
		f.setId("PoisonMove");
		f.setLifeSpan(60);
		f.setReduction(0f);
		if (getForceSet().getForce("PoisonMove") == null) {
			addForce(f);
		}

	}

	public void update() {
		tickUpdate();

		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		time++;
		if (isStunned()) {
			return;
		}

		move();

		if (time % 10 == 0) {

			WorldMap.addGameObject(new Poison(this.x, this.y));
		}
		if (dist < 500 && Time.alertPassed(alert)) {
			// WorldMap.addGameObject(new Poison(this.x, this.y));
			alert = Time.alert((long) (5));
		}

	}

}
