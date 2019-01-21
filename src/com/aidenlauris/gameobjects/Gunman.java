package com.aidenlauris.gameobjects;

import java.awt.Color;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.items.Pistol;
import com.aidenlauris.render.SoundHelper;

public class Gunman extends Enemy {

	private Particle part;

	public Gunman(float x, float y) {
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
		f.setId("GunMove");
		f.setLifeSpan(60);
		f.setReduction(0f);
		if (getForceSet().getForce("GunMove") == null) {
			addForce(f);
		}

	}

	public void update() {
		tickUpdate();

		part.x = x;
		part.y = y;
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		time++;
		if (isStunned()) {
			return;
		}

		move();

		if (dist < 500 && Time.alertPassed(alert)) {
			attack();
			alert = Time.alert((long) (30 + Math.random() * 30));
		}

	}

	public void attack() {
		Bullet b = new Bullet(10f);
		b.x = this.x;
		b.y = this.y;
		Player p = Player.getPlayer();
		b.setMoveSpeed(6);
		b.setLifeSpan(180f);
		b.setGunOffset(50);
		b.team = team.ENEMY;
		float theta = (float) Math.atan2(p.y - this.y, p.x - this.x);
		b.setTheta(theta);
		b.init();

		SoundHelper.makeSound("pew.wav");
	}

	@Override
	public void kill() {

		part.kill();
		super.kill();
	}

}
