package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.render.PaintHelper;

public class Beam extends Projectile {

	public Beam(float damage) {
		setKnockback(0);
		HurtBox box = new HurtBox(this, -6f, -6f, 12, 12, damage);
		box.addHint(this.getClass());
		addCollisionBox(box);
		health = 100;
	}

	public Beam(float x, float y, float moveSpeed, float damage, float theta, float gunOffset, float reduction) {
		super(x, y, 1000f, damage, theta, gunOffset, reduction);
		addCollisionBox(new HurtBox(this, -6f, -6f, 50, 15, damage));
	}
	
	@Override
	public void kill() {
		Particle.create(x, y, 15f, getTheta(), 40, 1);
		System.out.println(this + " " + time + " " + getLifeSpan());
		super.kill();
	}
	
	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		float theta = (float) (getForceSet().getNetTheta() + Math.PI);
		int trail = 1000;

		Shape s = new Rectangle2D.Float(drawX, drawY - 1.5f, trail, 3f);

		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(theta, drawX, drawY);
		g2d.transform(transform);
		g2d.setColor(Color.RED);
		if (getForceSet().getNetMagnitude() > 1) {
			g2d.fill(s);
		}
		g2d.setTransform(old);
		g2d = PaintHelper.drawCollisionBox(g2d, getCollisionBoxes());
		return g2d;
	}
	
	

}
