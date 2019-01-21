package com.aidenlauris.gameobjects;

import java.awt.Color;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.items.Pistol;
import com.aidenlauris.items.Shotgun;
import com.aidenlauris.items.Weapon;
import com.aidenlauris.render.SoundHelper;

public class Shotgunner extends Enemy {
	
	private Particle part;

	public Shotgunner(float x, float y) {
		super(x, y, 50, 10, 3);
		part = new Particle(x, y);
		part.setColor(Color.red);
		part.setSize(25);
		part.setSizeDecay(25);
		part.setFadeMinimum(255);
		part.setRotationSpeed(5);
		part.setLifeSpan(Integer.MAX_VALUE);
		part.init();
	}

	@Override
	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));

		Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
		f.setId("Shotgun");
		f.setLifeSpan(60);
		f.setReduction(0f);
		if (getForceSet().getForce("Shotgun") == null) {
			addForce(f);
		}

	}

	public void update() {
		part.x = x;
		part.y = y;
		tickUpdate();

		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		time++;
		if (isStunned()) {
			return;
		}

		move();

		if (dist < 500 && Time.alertPassed(alert)) {
			attack();
			alert = Time.alert((long) (60 + Math.random() * 30));
		}

	}

	public void attack() {
		for (int i = 0; i < 9; i++) {

			Bullet b = new Bullet(10f);
			b.x = this.x;
			b.y = this.y;
			Player p = Player.getPlayer();
			b.setMoveSpeed(10);
			b.setReduction(0.03f);
			b.setLifeSpan(60f);
			b.setGunOffset(50);
			b.team = team.ENEMY;
			b.health = 1;
			float theta = (float) Math.atan2(p.y - this.y - 45 + Math.random() * 90,
					p.x - this.x - 45 + Math.random() * 90);
			b.setTheta(theta);
			b.init();
		}

		SoundHelper.makeSound("shotgun.wav");
	}

	@Override
	public void kill() {

		part.kill();
		super.kill();
	}

}
