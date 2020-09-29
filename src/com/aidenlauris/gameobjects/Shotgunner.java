/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Shotgunner
 * type of enemy that shoots shotguns
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.Time;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.items.Pistol;
import com.aidenlauris.items.Shotgun;
import com.aidenlauris.items.Weapon;
import com.aidenlauris.render.SoundHelper;

public class Shotgunner extends Enemy {
	
	private Particle body;

	/**
	 * initiates enemy
	 * @param x start x
	 * @param y start y
	 */
	public Shotgunner(float x, float y) {
		super(x, y, 50, 10, 3);
		
		//creates body particle
		body = new Particle(x, y);
		body.setColor(Color.red);
		body.setSize(25);
		body.setSizeDecay(25);
		body.setFadeMinimum(255);
		body.setRotationSpeed(5);
		body.setLifeSpan(Integer.MAX_VALUE);
		body.init();
	}

	@Override
	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(GameLogic.getPlayer().x - x, 2) + Math.pow(GameLogic.getPlayer().y - y, 2));

		Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
		f.setId("Shotgun");
		f.setLifeSpan(60);
		f.setReduction(0f);
		if (getForceSet().getForce("Shotgun") == null) {
			addForce(f);
		}

	}

	public void update() {
		body.x = x;
		body.y = y;
		tickUpdate();

		float dist = (float) Math
				.sqrt(Math.pow(GameLogic.getPlayer().x - x, 2) + Math.pow(GameLogic.getPlayer().y - y, 2));
		time++;

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
			b.setLifeSpan(60);
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

		body.kill();
		super.kill();
	}

}
