package com.aidenlauris.gameobjects;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HurtBox;

public class Spinner extends Enemy {

	public Spinner(float x, float y) {
		super(x, y, 50, 20, 2);
		// attack();
	}

	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));

		Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
		f.setId("FourMove");
		f.setLifeSpan(60);
		f.setReduction(0);
		if (getForceSet().getForce("FourMove") == null) {
			addForce(f);
		}
	}

	public void update() {
		tickUpdate();

		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));

		if (Time.alertPassed(alert)) {
			attack();
			alert = Time.alert(3600);
		}

		time++;
		if (isStunned()) {
			return;
		}

	}

	public void attack() {
		for (int i = 0; i < 2; i++) {
			Bullet b = new Bullet(1f);
			ForceAnchor f = new ForceAnchor(5f, b, this, -1f);
			f.hasVariableSpeed(false);
			f.setOffset(87);
			b.getForceSet().addForce(f);
			b.x = (i == 1) ? this.x - 50 : this.x + 50;
			b.y = this.y;
			Player p = Player.getPlayer();
			b.getCollisionBoxes().clear();
			HurtBox hb = new HurtBox(b, 12, 12, b.getDamage());
			hb.addHint(Wall.class);
			b.addCollisionBox(hb);
			b.setMoveSpeed(0.000001f);
			b.setGunOffset(50);
			b.health = Integer.MAX_VALUE;
			b.setLifeSpan(Integer.MAX_VALUE);
			b.team = team.ENEMY;
			float theta = (float) Math.toRadians(90 + i * 180);
			b.setTheta(theta);
			b.init();
		}

	}

}