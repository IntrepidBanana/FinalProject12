package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.util.Random;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.items.BulletAmmo;
import com.aidenlauris.items.EnergyCell;
import com.aidenlauris.items.ExplosiveAmmo;
import com.aidenlauris.items.ShotgunAmmo;
import com.aidenlauris.render.SoundHelper;

public class Chaser extends Enemy {

	private Particle part;

	public Chaser(float x, float y) {
		super(x, y, 25, 10, 3.5f);
		part = new Particle(x, y);
		part.setColor(new Color(121, 75, 123));
		part.setSize(25);
		part.setSizeDecay(25);
		part.setFadeMinimum(255);
		part.setRotationSpeed(5);
		part.setLifeSpan(Integer.MAX_VALUE);
		part.init();
	}

	@Override
	public void kill() {

		part.kill();
		Random rand = new Random();
		int death = rand.nextInt(2);

		if (death == 0) {
			for (int i = 0; i < 450; i += 90) {
				Bullet b = new Bullet(2);
				b.x = this.x;
				b.y = this.y;
				Player p = Player.getPlayer();
				b.setMoveSpeed(8);
				b.setLifeSpan(60);
				b.setGunOffset(50);
				b.team = team.ENEMY;
				float theta = (float) Math.toRadians(i);
				b.setTheta(theta);
				b.init();

				WorldMap.addGameObject(new Corpse(x, y, this));
				removeSelf();

			}
		} else if (death == 1) {
			for (int i = 45; i < 405; i += 90) {
				Bullet b = new Bullet(2);
				b.x = this.x;
				b.y = this.y;
				Player p = Player.getPlayer();
				b.setMoveSpeed(8);
				b.setLifeSpan(60);
				b.setGunOffset(50);
				b.team = team.ENEMY;
				float theta = (float) Math.toRadians(i);
				b.setTheta(theta);
				b.init();
			}
		}

		SoundHelper.makeSound("pew.wav");
		super.kill();
	}

	@Override
	public void update() {
		part.x = x;
		part.y = y;

		if (time % 5 == 0) {
			Particle p = new Particle(x, y);
			p.setColor(new Color(121, 75, 123));

			p.setSize(16);
			p.setRotationSpeed(3);
			p.setSizeDecay(0);
			p.setFadeMinimum(0);
			p.setLifeSpan(30);
			p.init();
		}
		super.update();
	}

	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		if (dist < 400) {
			ForceAnchor f = new ForceAnchor(3f, this, Player.getPlayer(), -1);

			f.setId("PlayerFollow");
			f.hasVariableSpeed(false);
			if (getForceSet().getForce("PlayerFollow") == null) {
				getForceSet().removeForce("Random");
				getForceSet().addForce(f);
			}
		} else {
			Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
			f.setId("Random");
			f.setLifeSpan(60);
			f.setReduction(0f);
			if (getForceSet().getForce("Random") == null) {
				getForceSet().removeForce("PlayerFollow");
				getForceSet().addForce(f);
			}
		}
	}

}
