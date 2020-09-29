/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Sword
 * type of weapon to slice at enemies in an arc
 */

package com.aidenlauris.items;

import java.awt.Color;

import com.aidenlauris.gameobjects.MeleeSwing;
import com.aidenlauris.gameobjects.Particle;
import com.aidenlauris.gameobjects.Projectile;
import com.aidenlauris.gameobjects.util.Force;

public class Sword extends Gun {

	/**
	 * creates a sword with preset values
	 */
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
		
		//melee swing factory
		MeleeSwing b = new MeleeSwing(getDamage());
		b.setMoveSpeed(20f);
		b.setReduction(0.2f);
		return b;
	}

	@Override
	public void attackAnimation(float playerX, float playerY, float theta) {

		//creates a sword swipe
		
		//angle
		float spread = 120;
		float startAngle = (float) (theta + Math.toRadians(spread / 2));

		int particleCount = 15;

		//create particle
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
		
		
		

	}
}
