package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.render.PaintHelper;

public class SpinnerBlade extends Projectile {

	public Spinner parent = null;
	private float theta = 0;
	private float speed = 0;
	private int offset = 25;

	public SpinnerBlade(Spinner parent, int damage, float angle, float speed, int offset) {
		this.parent = parent;
		team = Team.ENEMY;
		HurtBox hb = new HurtBox(this, 15, 15, damage);
		hb.addHint(Wall.class);

		addCollisionBox(hb);
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
		WorldMap.addGameObject(this);
	}

	@Override
	public void update() {
		if (parent.health <= 0) {
			kill();
		}
		double a = Math.toRadians(theta);

		x = (float) (parent.x + offset * Math.cos(a));
		y = (float) (parent.y + offset * Math.sin(a));

		theta = (theta + speed) % 360;
		setTheta((float) Math.toRadians(theta));

	}

	@Override
	public void damage(HurtBox box) {
	}

	@Override
	public void damage(int damage) {
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);

		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();

		double a = Math.toRadians(theta + speed*5);
		double ox = offset * Math.cos(a);
		double oy = offset * Math.sin(a);

		float chain = 5;
		if (Time.global() % 5 == 0) {
			for (int i = 0; i <= chain; i++) {
				float dx = (float) (ox - (i / chain) * ox);
				float dy = (float) (oy - (i / chain) * oy);

				Particle p = new Particle(parent.x + dx, parent.y + dy);
				p.setFadeMinimum(0);
				p.setSizeDecay(0);
				p.setLifeSpan(60);
				p.setSize(10);
				p.setRotationSpeed(1);
				p.setColor(Color.darkGray);
				p.init();

			}
		}
		long t = System.currentTimeMillis() / 2;
		transform.rotate(Math.toRadians(t), drawX, drawY);
		g2d.setColor(Color.DARK_GRAY);
		g2d.transform(transform);
		g2d.fill(new Rectangle2D.Float(drawX - 12.5f, drawY - 12.5f, 25, 25));
		g2d.setTransform(old);

		return PaintHelper.drawCollisionBox(g2d, this);
	}
}
