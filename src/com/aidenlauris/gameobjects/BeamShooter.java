/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * BeamShooter
 * an enemy that shoots beams
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;

import com.aidenlauris.game.util.Time;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.render.SoundHelper;

public class BeamShooter extends Enemy {

	//whether the spinner blades have been created
	private boolean spinnersCreated = false;
	
	//alert timers
	private long moveAlert = 0;
	private long prepAttack = Time.alert(180);
	private long attack = 0;
	
	
	//whether the shooter is attacking
	private boolean attacking;

	
	//main particle
	private Particle part;
	
	
	//the coordinates of the firing position
	private float firingx = 0;
	private float firingy = 0;

	/**
	 * initiates enemy with coordinate x, y
	 * @param x coord x
	 * @param y coord y
	 */
	public BeamShooter(float x, float y) {
		super(x, y, 200, 10, 1);
		

		//initiates main particle of body
		part = new Particle(x, y);
		part.setRotationSpeed(3);
		part.setSize(64);
		part.setLifeSpan(Integer.MAX_VALUE);
		part.setColor(new Color(255, 0, 125));
		part.init();
		
	}

	@Override
	public void move() {
		float dist = distToPlayer();

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
		
		//updates the particle
		part.x = x;
		part.y = y;
		
		//creates the spinner
		if (!spinnersCreated) {
			SpinnerBlade a = new SpinnerBlade(this, 20, 0, 1, 70);
			SpinnerBlade b = new SpinnerBlade(this, 20, 120, 1, 70);
			SpinnerBlade c = new SpinnerBlade(this, 20, 240, 1, 70);
			a.color = new Color(255, 0, 125);
			b.color = new Color(255, 0, 125);
			c.color = new Color(255, 0, 125);
			a.chainUpdate = 10;
			a.chains = 3;
			b.chainUpdate = 10;
			b.chains = 3;
			c.chainUpdate = 10;
			c.chains = 3;
			spinnersCreated = true;
		}
		
		
		float dist = distToPlayer();
		time++;

		
		//starts the attack cycle
		if (dist < 900 && Time.alertPassed(prepAttack)) {
			prepAttack = Time.alert(180);
			moveAlert = Time.alert(75);
			attack = Time.alert(55);
			firingx = Player.getPlayer().x;
			firingy = Player.getPlayer().y;
			attacking = true;

			prep();

		}

		//attack 
		if (Time.alertPassed(attack) && attacking) {
			attack();
			attacking = false;
		}
		
		
		if (Time.alertPassed(moveAlert)) {
			//able to move again
			move();
		} else {
			//remove all movement
			getForceSet().removeForce("BeamMove");
		}

	}

	/**
	 * starts the particle effect for the charge up of the beam
	 */
	private void prep() {

		
		//sets angle
		float theta = (float) Math.atan2(firingy - this.y, firingx - this.x);

		//fires a ray to figure out end point of prep beam
		Ray r = new Ray(x, y, firingx, firingy);
		float fx = r.fx;
		float fy = r.fy;

		double dist = Math.hypot(x - fx, y - fy);

		
		//creates a particle between here and ray end point
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

		//spawns a beam
		Beam b = new Beam(30, this.x, this.y);
		b.x = this.x;
		b.y = this.y;
		b.setMoveSpeed(80);
		b.setLifeSpan(60);
		b.setGunOffset(50);
		b.team = Team.ENEMY;
		float theta = (float) Math.atan2(firingy - this.y, firingx - this.x);
		b.setTheta(theta);
		b.init();

		SoundHelper.makeSound("beam.wav");

	}

	@Override
	public void knockBack(float magnitude, float theta) {
		//removes ability to get knocked back
	}

	@Override
	public void kill() {
		part.kill();
		super.kill();
	}

	
}
