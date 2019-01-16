package com.aidenlauris.items;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.aidenlauris.game.IOHandler;
import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.game.util.Mouse;
import com.aidenlauris.gameobjects.Particle;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.Projectile;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.Inventory;
import com.aidenlauris.render.SoundHelper;

public abstract class Gun extends Weapon {

	private boolean auto = true;
	private float length = 15;
	private int quickRelease = getAtkSpeed();
	private int accuracy = 30;
	private long lastFire = 0;
	private long lastRelease = 0;
	private float reduction = 0f;
	private float bulletCount = 1;
	private float spread = 0;
	private String ammoType = "";

	private int ammoPerUse = 1;

	public Gun() {
		super(1, 1, 0, 1);
		setCount(1);
		setStackSize(1);
	}

	public Gun(double weight, int stackSize, int damage, int atkSpeed) {
		super(weight, stackSize, damage, atkSpeed);
		setCount(1);
	}

	public abstract Projectile bulletType();

	public float rollAccuracy() {
		return (float) (Math.toRadians(Math.random() * (2 * accuracy) - accuracy));
	}

	public boolean canFire() {
		boolean canFire = false;
		if (Mouse.getFocus() != null) {
			return false;
		}

		if (isAuto()) {
			if (getLengthSinceFire() > getAtkSpeed() || getLengthSinceRelease() > getQuickRelease()) {
				canFire = true;
			}
		} else {
			if (getLengthSinceFire() > getAtkSpeed() && getLengthSinceRelease() > 1) {
				canFire = true;
			}
		}
		return canFire;
	}

	public void fire() {
		if (canFire()) {

			Player player = WorldMap.getPlayer();
			float theta = Mouse.theta(player.x, player.y);

			float totalSpreadAngle = (float) ((bulletCount - 1) * Math.toRadians(spread));
			float startTheta = theta - (totalSpreadAngle / 2);

			if (!getAmmoType().equals("")) {
				Inventory pInv = player.getInventory();
				int ammoIndex = pInv.indexOf(getAmmoType());

				if (ammoIndex == -1) {
					return;
				}

				if (pInv.getItem(ammoIndex).getCount() < ammoPerUse) {
					return;
				}
				pInv.getItem(ammoIndex).removeFromCount(ammoPerUse);

			}

			for (int i = 0; i < bulletCount; i++) {
				float angle = (float) (startTheta + (Math.toRadians(spread * i)) + rollAccuracy());
				Projectile p = bulletType();
				p.setGunOffset(length);
				p.x = WorldMap.getPlayer().x;
				p.y = WorldMap.getPlayer().y;
				p.setTheta(angle);
				p.init();
				if (!ammoType.equals("")) {

					for (int i1 = 0; i1 < 1; i1++) {
						Particle part = new Particle(p.x, p.y);
						part.setLifeSpan((int) (40 + Math.random() * 30));
						part.setRotationSpeed(30);
						part.setFadeMinimum(0);
						part.setSizeDecay(30);
						part.setSize(10);
						Force f = new Force((float) (3 + Math.random() * 8),
								(float) (theta + Math.toRadians(Math.random() * 30 - 15)));
						f.setReduction(0.098f);
						ForceAnchor fa = new ForceAnchor(1f, player, part, -1);
						part.addForce(f);
						part.getForceSet().addForce(fa, 3);
						part.init();
					}

					// Particle.create(p.x, p.y, (float) (10f + Math.random() * 30), (float) (theta
					// + Math.PI), 10, 3,
					// false, Color.GRAY, (int) (40 + Math.random() * 30), 8);
					Particle.create(p.x, p.y, (float) (3f + Math.random() * 5), (float) (theta + Math.PI + Math.PI / 2),
							i * 2, 1, false, Color.yellow, 10, 6);
					Particle.create(p.x, p.y, (float) (3f + Math.random() * 5), (float) (theta + Math.PI - Math.PI / 2),
							i * 2, 1, false, Color.yellow, 10, 6);
				}
			}
			switch (ammoType) {
			case "BulletAmmo":
				Particle.create(player.x, player.y, 20f, (float) (theta + Math.PI / 1.8), 15, ammoPerUse, false,
						Color.orange, 120, 4);
				break;
			case "ShotgunAmmo":
				Particle.create(player.x, player.y, 20f, (float) (theta + Math.PI / 2), 15, ammoPerUse, false,
						Color.red, 120, 7);
				break;
			case "ExplosiveAmmo":
				Particle.create(player.x, player.y, 20f, (float) (theta + Math.PI / 1.5), 15, ammoPerUse, false,
						Color.green, 120, 12);
				break;
			default:
				break;
			}
			if (!ammoType.equals("")) {
				WorldMap.getCamera().cameraShake(theta, totalSpreadAngle / 2,
						bulletType().getKnockback() * Math.min(getBulletCount(), 5));
			}
			updateFireTime();

		}
		updateReleaseTime();

	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public void updateFireTime() {
		lastFire = Time.global();
	}

	public void updateReleaseTime() {
		lastRelease = Time.global();
	}

	public long getLengthSinceFire() {
		return Time.global() - lastFire;
	}

	public long getLengthSinceRelease() {
		return Time.global() - lastRelease;
	}

	public int getQuickRelease() {
		return quickRelease;
	}

	public void setQuickRelease(int quickRelease) {
		this.quickRelease = quickRelease;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public float getReduction() {
		return reduction;
	}

	public void setReduction(float reduction) {
		this.reduction = reduction;
	}

	public float getBulletCount() {
		return bulletCount;
	}

	public void setBulletCount(float bulletCount) {
		this.bulletCount = bulletCount;
	}

	public float getSpread() {
		return spread;
	}

	public void setSpread(float spread) {
		this.spread = spread;
	}

	public int getAmmoPerUse() {
		return ammoPerUse;
	}

	public void setAmmoPerUse(int ammoPerUse) {
		this.ammoPerUse = ammoPerUse;
	}

	@Override
	public void useItem() {
		fire();
		
		  
		
	}

	public String getAmmoType() {
		return ammoType;
	}

	public void setAmmoType(String ammoType) {
		this.ammoType = ammoType;
	}
}
