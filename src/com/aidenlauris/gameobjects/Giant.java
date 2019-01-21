package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.HurtBox;

public class Giant extends Enemy {
	private Particle part;

	@Override
	public void kill() {
		part.kill();
		super.kill();
	}
	
	public Giant(float x, float y) {
		super(x, y, 200, 10, 0.5f);
		getCollisionBoxes().clear();
		addCollisionBox(new HitBox(this, 100, 100, true));
		addCollisionBox(new HurtBox(this, 105, 105, 30));
		part = new Particle(x, y);
		part.setSize(100);
		part.setRotationSpeed(3);
		part.setColor(Color.CYAN);
		part.setSizeDecay(100);
		part.setLifeSpan(Integer.MAX_VALUE);
		part.init();
	}

	@Override
	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		if (dist < 300) {
			ForceAnchor f = new ForceAnchor(1.5f, this, Player.getPlayer(), -1);

			f.setId("PlayerFollow");
			f.hasVariableSpeed(false);
			f.setLifeSpan(5);
			if (getForceSet().getForce("PlayerFollow") == null) {
				getForceSet().removeForce("Random");
				getForceSet().addForce(f);
				// System.out.println("Running Attack");
			}

		} else {
			Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
			f.setId("Random");
			f.setLifeSpan(30);
			f.setReduction(0f);
			if (getForceSet().getForce("Random") == null) {
				getForceSet().removeForce("PlayerFollow");
				getForceSet().addForce(f);
			}
		}
	}

	@Override
	public void update() {
		super.update();
		part.x = x;
		part.y = y;
		for (GameObject p : WorldMap.nonStaticObjects) {
			if (p instanceof Projectile) {
				p = (Projectile) p;
				if (Math.hypot(p.x - x, p.y - y) > 600) {
					continue;
				}
				if (p.getForceSet().getForce("GIANTPULL" + this) == null) {
					float magnitude = 30f;
					if (p instanceof MeleeSwing) {
						magnitude = 0;
					}
					ForceAnchor fa = new ForceAnchor(magnitude, this, p, -1);
					fa.setId("GIANTPULL" + this);
					fa.setLifeSpan(1);
					p.addForce(fa);
				}

			}
		}

		float theta = (float) Math.toRadians(Math.random() * 360);
		double offset = 75;
		for (int i = 0; i <= 3; i++) {
			float px = (float) (x + Math.cos(theta) * offset);
			float py = (float) (y + Math.sin(theta) * offset);
			Particle p = new Particle(px, py);
			p.setColor(Color.cyan);
			p.setSize(16);
			p.setSizeDecay(0);
			p.setFadeMinimum(50);
			p.setRotation(3);
			p.setLifeSpan(15);

			ForceAnchor fa = new ForceAnchor(5f, p, this, -1f);
			fa.setOffset(85);
			fa.setLifeSpan(15);
			p.addForce(fa);

			p.init();
		}

	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		return g2d;
	}
}
