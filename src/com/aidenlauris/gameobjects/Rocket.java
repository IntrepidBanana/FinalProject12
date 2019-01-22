package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.render.PaintHelper;

public class Rocket extends Projectile {

	private GameObject target = this;

	public Rocket(int damage) {
		HurtBox box = new HurtBox(this, 12, 12, damage);
		box.addHint(this.getClass());
		addCollisionBox(box);
		setKnockback(10f);

		ForceAnchor fa = new ForceAnchor(12f, this, this, -1);
		fa.hasVariableSpeed(false);
		fa.setId("target");
		getForceSet().addForce(fa, 30);
	}

	@Override
	public void update() {

		super.update();

		double dist = Integer.MAX_VALUE;

		for (int i = 0; i < GameLogic.enemies.size(); i++) {
			if (i >= GameLogic.enemies.size()) {
				i--;
				continue;
			}
			Enemy e = GameLogic.enemies.get(i);
			if (e == null) {
				continue;
			}
			if (Math.hypot(x - e.x, y - e.y) < dist) {
				target = e;
				dist = Math.hypot(x - e.x, y - e.y);
			}

		}
		if (getForceSet().getForce("target") != null) {
			((ForceAnchor) getForceSet().getForce("target")).setAnchor(target);
		}
	}

	@Override
	public void kill() {
		GameLogic.addGameObject(new Explosion(x, y, 200, 30f, 150f));
		removeSelf();
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {

		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);

		float theta = (float) (getForceSet().getNetTheta() + Math.PI);
		// int trail = (int) (24 * time + 48);
		int trail = 12;
		int width = 8;
		Shape s = new Rectangle2D.Float(drawX, drawY - width / 2, trail, width);
		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(theta, drawX, drawY);
		g2d.transform(transform);
		g2d.setColor(Color.lightGray);
		if (getForceSet().getNetMagnitude() > 1) {
			g2d.fill(s);
		}
		g2d.setTransform(old);

		float tx = target.x;
		float ty = target.y;
		if (time %15 == 0) {
			Particle p = new Particle(tx, ty);
			p.setRotationSpeed(1);
			p.setRotation((int) (System.currentTimeMillis()/10));
			p.setColor(new Color(200, 50, 50, 170));
			p.setFadeMinimum(170);
			p.setSize(30);
			p.setSizeDecay(60);
			p.setLifeSpan(15);
			p.init();
		}

		return g2d;
	}
}
