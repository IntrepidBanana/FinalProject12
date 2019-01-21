/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Sword
 * type of weapon to slice at enemies in an arc
 */

package com.aidenlauris.items;

import java.awt.Color;
import java.awt.Graphics2D;

import com.aidenlauris.gameobjects.MeleeSwing;
import com.aidenlauris.gameobjects.Particle;
import com.aidenlauris.gameobjects.Projectile;
import com.aidenlauris.gameobjects.util.Force;

public class Sword extends Gun {

	public Sword() {

		setLength(20);
		setAuto(true);

		setAccuracy(0);
		setAtkSpeed(20);
		setQuickRelease(3);

		setSpread(8);
		setBulletCount(10);
		setDamage(5);

		setAmmoPerUse(0);

		setSpawnSound("sword.wav");
	}

	@Override
	public Projectile bulletType() {
		MeleeSwing b = new MeleeSwing(getDamage());
		b.setMoveSpeed(20f);
		b.setReduction(0.2f);
		return b;
	}

	@Override
	public Graphics2D attackAnimation(Graphics2D g2d, float playerX, float playerY, float theta) {

		float spread = 120;
		float startAngle = (float) (theta + Math.toRadians(spread / 2));

		int particleCount = 15;

		for (int i = 0; i <= particleCount; i++) {
			float a = (float) (-Math.toRadians(spread) * i / particleCount + startAngle);

			float x = (float) (playerX + Math.cos(a) * 20);
			float y = (float) (playerY + Math.sin(a) * 20);

			Particle p = new Particle(x, y);
			p.setColor(Color.LIGHT_GRAY);
			p.setLifeSpan(12+i*2);
			p.setFadeMinimum(150);
			p.setSize(5);
			p.setSizeDecay((i / (particleCount * 1.0d)) * 30);
			p.setRotation((int) (Math.toDegrees(theta + Math.PI / 2)));
			p.setRotationSpeed(10);
			Force f = new Force(20f, a);
			f.setReduction(0.2f);
			p.addForce(f);

			p.init();

		}
		
		
		

		return super.attackAnimation(g2d, playerX, playerY, theta);
	}
}
