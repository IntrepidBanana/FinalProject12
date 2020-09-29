/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * FourShooter
 * type of enemy that shoots in four directions
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.Time;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.render.SoundHelper;

public class FourShooter extends Enemy {

	private Particle part;

	public FourShooter(float x, float y) {
		super(x, y, 25, 20, 2);
		part = new Particle(x, y);
		part.setColor(Color.red);
		part.setSize(25);
		part.setSizeDecay(25);
		part.setFadeMinimum(255);
		part.setRotationSpeed(5);
		part.setLifeSpan(Integer.MAX_VALUE);
		part.init();
	}

	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(GameLogic.getPlayer().x - x, 2) + Math.pow(GameLogic.getPlayer().y - y, 2));

		Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
		f.setId("FourMove");
		f.setLifeSpan(60);
		f.setReduction(0.05f);
		if (getForceSet().getForce("FourMove") == null) {
			addForce(f);
		}
	}

	public void update() {
		part.x = x;
		part.y = y;

		tickUpdate();

		float dist = (float) Math
				.sqrt(Math.pow(GameLogic.getPlayer().x - x, 2) + Math.pow(GameLogic.getPlayer().y - y, 2));
		time++;

		move();

		if (Time.alertPassed(alert)) {
			attack();
			alert = Time.alert((long) (60));
		}

	}

	public void attack() {
		for (int i = 0; i < 360; i += 90) {
			Bullet b = new Bullet(20);
			b.x = this.x;
			b.y = this.y;
			Player p = Player.getPlayer();
			b.setMoveSpeed(3);
			b.setLifeSpan(60);
			b.setGunOffset(50);
			b.team = team.ENEMY;
			this.team = team.ENEMY;
			float theta = (float) Math.toRadians(i);
			b.setTheta(theta);
			b.init();
		}
		SoundHelper.makeSound("pew.wav");

	}

	@Override
	public void kill() {

		part.kill();
		super.kill();
	}
}
