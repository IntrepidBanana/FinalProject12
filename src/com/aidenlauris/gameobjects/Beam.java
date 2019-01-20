package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionHelper;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.render.PaintHelper;

public class Beam extends Projectile {

	private float startX;
	private float startY;
	
	public Beam(float damage, float x, float y) {
		setKnockback(0);
		HurtBox box = new HurtBox(this, 20, 20, damage);
		box.addHint(this.getClass());
		addCollisionBox(box);
		health = Integer.MAX_VALUE;
		this.startX = x;
		this.startY = y;
		//setSpawnSound("beam.wav");
		
	}

	public Beam(float x, float y, float moveSpeed, float damage, float theta, float gunOffset, float reduction) {
		super(x, y, 1000f, damage, theta, gunOffset, reduction);
		addCollisionBox(new HurtBox(this, -6f, -6f, 50, 15, damage));
	}
	
	@Override
	public void kill() {
		Particle.create(x, y, 15f, getTheta(), 40, 1);
		super.kill();
	}
	
	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float dist = (float) Math
				.sqrt(Math.pow(startX - x, 2) + Math.pow(startY - y, 2));
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		float theta = (float) (getForceSet().getNetTheta() + Math.PI);
		float trail = dist;
		float width = 20;
		Shape s = new Rectangle2D.Float(drawX, drawY - width/2, trail, width);

		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(theta, drawX, drawY);
		g2d.transform(transform);
		g2d.setColor(Color.RED);
		if (getForceSet().getNetMagnitude() > 1) {
			g2d.fill(s);
		}
		g2d.setTransform(old);
		return g2d;
	}
	
	
	@Override
	public void forceUpdate() {
		double narrowestSide = Math.min(CollisionHelper.width(this), CollisionHelper.length(this));
		if (getForceSet().getNetMagnitude() > narrowestSide && forceAccurate && narrowestSide > 0) {

			double divisions = (getForceSet().getNetMagnitude() / (narrowestSide / 2));

			double dx = getForceSet().getX() / divisions;
			double dy = getForceSet().getY() / divisions;
			for (int i = 0; i < divisions; i++) {
				if (CollisionHelper.chunkCollision(this)) {
				}
				
				Particle p = new Particle(x, y);
				p.setLifeSpan(30);
				p.setSize(20);
				p.setSizeDecay(0);
				p.setColor(new Color(255, 0, 125, 0));
				p.setRotationSpeed(30);
				p.setRotation((int) Math.toDegrees(getForceSet().getNetTheta()));
				
				p.setFadeMinimum(0);
				p.init();
				
				
				
				x += dx;
				y += dy;
			}
		} else {
			x += getForceSet().getX();
			y += getForceSet().getY();
		}

		getForceSet().update();
	
	}

}
