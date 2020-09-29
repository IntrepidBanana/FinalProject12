/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Gun
 * super class for all types of guns
 */

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

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.IOHandler;
import com.aidenlauris.game.util.Mouse;
import com.aidenlauris.game.util.Time;
import com.aidenlauris.gameobjects.Particle;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.Projectile;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.Inventory;
import com.aidenlauris.render.SoundHelper;

public abstract class Gun extends Weapon {

	// Modifiers and variables
	private boolean auto = true;
	private int accuracy = 30;

	// Used when the mouse is clicked
	private int quickRelease = getAtkSpeed();
	private long lastFire = 0;
	private long lastRelease = 0;

	// length of gun
	private float length = 15;
	private float spread = 0;
	private String ammoType = "";
	private String spawnSound = "pew.wav";

	private int ammoPerUse = 1;
	private float bulletCount = 1;

	/**
	 * Initiates a gun item
	 */
	public Gun() {
		super(0, 1);
		setCount(1);
	}

	/**
	 * Initiates a gun with a set of items
	 * 
	 * @param weight
	 *            not used
	 * @param stackSize
	 *            not used
	 * @param damage
	 *            damage of weapon
	 * @param atkSpeed
	 *            speed of weapon
	 */
	private Gun(double weight, int stackSize, int damage, int atkSpeed) {
		super(damage, atkSpeed);
		setCount(1);
	}

	/**
	 * Used a factory for creating the bullet used by a gun
	 * 
	 * @return
	 */
	public abstract Projectile bulletType();

	/**
	 * calculate the spread based on accuraccy
	 * 
	 * @return
	 */
	public float rollAccuracy() {
		return (float) (Math.toRadians(Math.random() * (2 * accuracy) - accuracy));
	}

	/**
	 * Checks whether the gun can fire at the current call
	 * 
	 * @return true if gun can fire
	 */
	public boolean canFire() {
		boolean canFire = false;

		// if automatic weapon
		if (isAuto()) {

			// if the length since the last time a bullet fired is greater than
			// the attack speed
			// or
			// the length the fire button was released is greater than the time
			// for quick release
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

	/**
	 * spawns bullets with a spread based on modifiers
	 */
	public void fire() {
		if (canFire()) {

			// calculate angles and spreads
			Player player = GameLogic.getPlayer();
			float theta = Mouse.theta(player.x, player.y);

			float totalSpreadAngle = (float) ((bulletCount - 1) * Math.toRadians(spread));
			float startTheta = theta - (totalSpreadAngle / 2);

			// checking ammo type and consuming ammo
			if (!getAmmoType().equals("")) {
				Inventory pInv = player.getInventory();

				if (pInv.getAmmoCount(this) < ammoPerUse) {
					pInv.getAmmoCount(this);
					return;
				}
				pInv.useAmmo(getAmmoType(), ammoPerUse);

			}

			// generates a projectile for the number of bullets
			for (int i = 0; i < bulletCount; i++) {

				// generates the current angle
				float angle = (float) (startTheta + (Math.toRadians(spread * i)) + rollAccuracy());

				// generates the bullet using the gun bullet factory
				Projectile p = bulletType();
				p.setGunOffset(length);
				p.x = GameLogic.getPlayer().x;
				p.y = GameLogic.getPlayer().y;
				p.setTheta(angle);
				p.init();

				// adding a smoke particle effet
				if (!ammoType.equals("")) {

					// creates a smoke particle
					for (int i1 = 0; i1 < 1; i1++) {
						Particle part = new Particle(p.x, p.y);
						part.setLifeSpan((int) (40 + Math.random() * 30));
						part.setRotationSpeed(30);
						part.setFadeMinimum(0);
						part.setSizeDecay(30);
						part.setSize(10);
						part.setColor(Color.GRAY);
						Force f = new Force((float) (3 + Math.random() * 8),
								(float) (theta + Math.toRadians(Math.random() * 30 - 15)));
						f.setReduction(0.098f);
						ForceAnchor fa = new ForceAnchor(1f, player, part, -1);
						part.addForce(f);
						part.getForceSet().addForce(fa, 3);
						part.init();
					}

					// creates a gun fire affect
					Particle.create(p.x, p.y, (float) (3f + Math.random() * 5), (float) (theta + Math.PI + Math.PI / 2),
							i * 2, 1, false, Color.yellow, 10, 6);
					Particle.create(p.x, p.y, (float) (3f + Math.random() * 5), (float) (theta + Math.PI - Math.PI / 2),
							i * 2, 1, false, Color.yellow, 10, 6);
				}
			}

			// play the appropriate fire sound
			SoundHelper.makeSound(getSpawnSound());

			// plays the appropriate per gun animation
			attackAnimation(player.x, player.y, theta);

			// creates a cool particle that represent the bullet discarding from
			// the gun
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

			// adds camera shake
			if (!ammoType.equals("")) {
				GameLogic.getCamera().cameraShake(theta, totalSpreadAngle / 2,
						bulletType().getKnockback() * Math.min(getBulletCount(), 5));
			}

			// update the time since the last fire
			updateFireTime();

		}

		// update the time since this fire method was released
		updateReleaseTime();

	}

	@Override
	public void useItem() {
		fire();
	}

	/**
	 * @return length of gun
	 */
	public float getLength() {
		return length;
	}

	/**
	 * @param length
	 *            new length of gun
	 */
	public void setLength(float length) {
		this.length = length;
	}

	/**
	 * changes the time since the last fire time to the current global tick
	 */
	public void updateFireTime() {
		lastFire = Time.global();
	}

	/**
	 * changes the time since the fire method has been released to the current
	 * global tick
	 */
	public void updateReleaseTime() {
		lastRelease = Time.global();
	}

	/**
	 * @return the difference between now and the last time this gun was fired
	 */
	public long getLengthSinceFire() {
		return Time.global() - lastFire;
	}

	/**
	 * @return the differnce between now and the last time the fire method was released
	 */
	public long getLengthSinceRelease() {
		return Time.global() - lastRelease;
	}

	/**
	 * @return the duration between quick fires
	 */
	public int getQuickRelease() {
		return quickRelease;
	}

	/**
	 * @param quickRelease new value for length of time for quick fires
	 */
	public void setQuickRelease(int quickRelease) {
		this.quickRelease = quickRelease;
	}

	/**
	 * @return the angle in degrees of the spread of a single bullet
	 */
	public int getAccuracy() {
		return accuracy;
	}

	/**
	 * @param accuracy new accuracy spread of a bullet
	 */
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * @return true if the gun is an automatic weapon
	 */
	public boolean isAuto() {
		return auto;
	}

	/**
	 * @param auto boolean value whether the gun is automatic or not
	 */
	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	/**
	 * @return the number of bullets this gun shoots in one update
	 */
	public float getBulletCount() {
		return bulletCount;
	}

	/**
	 * @param bulletCount sets the number of bullets this gun spawns
	 */
	public void setBulletCount(float bulletCount) {
		this.bulletCount = bulletCount;
	}

	/**
	 * @return the spread between bullets
	 */
	public float getSpread() {
		return spread;
	}

	/**
	 * @param spread new angle between bullets
	 */
	public void setSpread(float spread) {
		this.spread = spread;
	}

	/**
	 * @return ammo used per fire event for this gun
	 */
	public int getAmmoPerUse() {
		return ammoPerUse;
	}

	/**
	 * @param ammoPerUse new value for amount of bullets fired per fire event
	 */
	public void setAmmoPerUse(int ammoPerUse) {
		this.ammoPerUse = ammoPerUse;
	}

	/**
	 * @return type of ammo used
	 */
	public String getAmmoType() {
		return ammoType;
	}

	/**
	 * @param ammoType type of ammo used
	 */
	public void setAmmoType(String ammoType) {
		this.ammoType = ammoType;
	}

	/**
	 * @return sound played 
	 */
	public String getSpawnSound() {
		return spawnSound;
	}

	/**
	 * @param spawnSound new sound
	 */
	public void setSpawnSound(String spawnSound) {
		this.spawnSound = spawnSound;
	}
}
