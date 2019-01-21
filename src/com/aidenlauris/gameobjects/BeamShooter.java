/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * BeamShooter
 * an enemy that shoots beams
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.items.LaserGun;
import com.aidenlauris.items.Shotgun;
import com.aidenlauris.render.SoundHelper;

public class BeamShooter extends Enemy {

	boolean spinnersCreated = false;
	long moveAlert = 0;
	long prepAttack = Time.alert(180);
	long attack = 0;
	private boolean attacking;

	float px = 0;
	float py = 0;

	public BeamShooter(float x, float y) {
		super(x, y, 200, 10, 1);
	}

	@Override
	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));

		Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
		f.setId("BeamMove");
		f.setLifeSpan(45);
		f.setReduction(0f);
		if (getForceSet().getForce("BeamMove") == null) {
			addForce(f);
		}

	}

	public void update() {
		tickUpdate();
		if (!spinnersCreated) {
			SpinnerBlade a = new SpinnerBlade(this, 20, 0, 1, 50);
			SpinnerBlade b = new SpinnerBlade(this, 20, 120, 1, 50);
			SpinnerBlade c = new SpinnerBlade(this, 20, 240, 1, 50);
			a.color = new Color(255, 0, 125);
			b.color = new Color(255, 0, 125);
			c.color = new Color(255, 0, 125);
			// a.trail = false;
			// b.trail = false;
			// c.trail = false;
			a.chainUpdate = 10;
			a.chains = 3;
			b.chainUpdate = 10;
			b.chains = 3;
			c.chainUpdate = 10;
			c.chains = 3;
			spinnersCreated = true;
		}
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		time++;
		if (isStunned()) {
			return;
		}

		if (dist < 500 && Time.alertPassed(prepAttack)) {
			prepAttack = Time.alert(180);
			moveAlert = Time.alert(75);
			attack = Time.alert(55);
			px = Player.getPlayer().x;
			py = Player.getPlayer().y;
			attacking = true;

			prep();

		}

		if (Time.alertPassed(attack) && attacking) {
			attack();
			attacking = false;
		}
		if (Time.alertPassed(moveAlert)) {
			move();
		} else {

			getForceSet().removeForce("BeamMove");
		}

	}

	private void prep() {

		float theta = (float) Math.atan2(py - this.y, px - this.x);
		Ray r = new Ray(x, y, px, py);

		float fx = r.fx;
		float fy = r.fy;

		double dist = Math.hypot(x - fx, y - fy);

		float particles = 60;
		for (int i = 1; i <= particles; i++) {
			float offset = (float) ((i / particles) * dist);

			float dx = (float) (x + Math.cos(theta) * offset);
			float dy = (float) (y + Math.sin(theta) * offset);

			Particle p = new Particle(dx, dy);
			p.setLifeSpan(50);
			p.setSize(5 - i);
			p.setSizeDecay(15);
			p.setColor(new Color(255, 0, 125, 0));
			p.setRotationSpeed(0);
			p.setRotation((int) Math.toDegrees(theta));

			p.setFadeMinimum(255);
			p.init();

		}

	}

	public void attack() {

		Beam b = new Beam(30, this.x, this.y);
		b.x = this.x;
		b.y = this.y;
		b.setMoveSpeed(80);
		b.setLifeSpan(60);
		b.setGunOffset(50);
		b.team = Team.ENEMY;
		float theta = (float) Math.atan2(py - this.y, px - this.x);
		b.setTheta(theta);
		b.init();

		SoundHelper.makeSound("beam.wav");

	}

	@Override
	public void knockBack(float magnitude, float theta) {
	}

	@Override
	public void kill() {

		super.kill();
	}

}
