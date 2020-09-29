/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * SpinnerBlade
 * type of projectile that spins around a main entity
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.Time;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.render.PaintHelper;

public class SpinnerBlade extends Projectile {

	// parent the spinner is connected to
	public Entity parent = null;

	// current angle
	private float theta = 0;

	// speed of rotation
	private float speed = 0;

	// dist away from parent
	private int offset = 25;

	// color of spinner
	public Color color = Color.lightGray;
	public boolean trail = true;

	// number of chains
	public int chains = 4;

	// how often the chain updates
	public int chainUpdate = 20;

	/**
	 * Initiates blade data
	 * 
	 * @param parent
	 *            parent object
	 * @param damage
	 *            damage
	 * @param angle
	 *            start angle
	 * @param speed
	 *            speed of rotation
	 * @param offset
	 *            distance away from parent
	 */
	public SpinnerBlade(Entity parent, int damage, float angle, float speed, int offset) {

		HurtBox hb = new HurtBox(this, 15, 15, damage);
		hb.addHint(Wall.class);
		addCollisionBox(hb);

		// initiates values
		this.parent = parent;
		team = Team.ENEMY;
		theta = angle;
		this.speed = speed;
		this.offset = offset;
		health = Integer.MAX_VALUE;
		setKnockback(13);
		init();
	}

	@Override
	public void kill() {
		super.kill();
	}

	@Override
	public void init() {
		GameLogic.addGameObject(this);
	}

	@Override
	public void update() {

		// remove this object if its killed
		if (parent.health <= 0) {
			kill();
		}

		// calculate the angle and position of this object
		double a = Math.toRadians(theta);
		x = (float) (parent.x + offset * Math.cos(a));
		y = (float) (parent.y + offset * Math.sin(a));

		// update theta
		theta = (theta + speed) % 360;
		setTheta((float) Math.toRadians(theta));

	}

	@Override
	public void damage(HurtBox box) {
		// cant be killed
	}

	@Override
	public void damage(float damage) {
		// cant be killed
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		
		
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);


		double a = Math.toRadians(theta + speed * 5);
		
		
		//creating the chain particle
		if (trail) {
			double ox = offset * Math.cos(a);
			double oy = offset * Math.sin(a);

			if (Time.global() % chainUpdate == 0) {
				for (int i = 0; i < chains; i++) {
					float dx = (float) ((i / (chains * 1.0f)) * ox);
					float dy = (float) ((i / (chains * 1.0f)) * oy);

					Particle p = new Particle(parent.x + dx, parent.y + dy);
					p.setFadeMinimum(0);
					p.setSizeDecay(0);
					p.setLifeSpan(chainUpdate * 3);
					p.setSize(10);
					p.setRotationSpeed(1);
					p.setColor(color);
					p.init();

				}
			}
		}
		
		
		//creation of the rotating blade
		long t = System.currentTimeMillis() / 2;
		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(Math.toRadians(t), drawX, drawY);
		g2d.setColor(color);
		g2d.transform(transform);
		g2d.fill(new Rectangle2D.Float(drawX - 12.5f, drawY - 12.5f, 25, 25));
		g2d.setTransform(old);

		return g2d;
	}
}
