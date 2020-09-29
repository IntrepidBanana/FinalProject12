/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Projectile
 * the superclass for all types of bullets
 */

package com.aidenlauris.gameobjects;

import java.util.FormatterClosedException;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.Time;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.render.SoundHelper;

public abstract class Projectile extends Entity {

	
	//data for projectile
	private float theta = 0;
	private float gunOffset = 0;
	private long lifeSpan = 0;// TIMER
	private long realLifeSpan = 15*GameLogic.FRAMERATE;
	private float damage = 1;
	private float reduction = 0;
	private float knockback = 1;

	
	
	/**
	 * Creates a projectile
	 */
	public Projectile() {
		super(0, 0);
		team = Team.PLAYER;
		forceAccurate = true;

	}


	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {

		//if there was no high speed access and we are going fast
		if (getForceSet().getNetMagnitude() >= 40 && !highSpeedAccess) {
			return;
		}

		//hurt any entity that it collided with
		// kill the projectile if not
		if (box.getOwner() instanceof Entity && !(box.getOwner() instanceof Projectile) && !(box.getOwner() instanceof Poison)) {
			Entity owner = (Entity) box.getOwner();
			if (owner.team != this.team) {
				owner.damage((HurtBox) myBox);
				owner.knockBack(getKnockback(), getTheta());
				if (health == 1) {
					kill();
				}
				health--;
			}
			if (box.getOwner() instanceof Wall) {
				this.kill();
			}
		}
		//turn off high speed access
		highSpeedAccess = false;

	}

	/**
	 * @return damage
	 */
	public float getDamage() {
		return damage;
	}

	/**
	 * @return lifespan
	 */
	public long getLifeSpan() {
		return lifeSpan;
	}

	/**
	 * @return rate of reduction
	 */
	public float getReduction() {
		return reduction;
	}

	/**
	 * @return angle
	 */
	public float getTheta() {
		return theta;
	}

	@Override
	public void init() {
		
		//creates the bullet with the set values
		x = (float) (x + gunOffset * Math.cos(theta));
		y = (float) (y + gunOffset * Math.sin(theta));
		Force f = new Force(getMoveSpeed(), getTheta());
		f.setReduction(getReduction());
		getForceSet().addForce(f);
		lifeSpan = Time.alert(realLifeSpan);
		GameLogic.addGameObject(this);
	}

	/**
	 * validates the life span of this bullet
	 */
	private void lifeSpanTick() {
		if(Time.alertPassed(getLifeSpan())){
			kill();
		}
	}

	/**
	 * @param damage damage of bullet
	 */
	public void setDamage(float damage) {
		this.damage = damage;
	}

	/**
	 * @param lifeSpan length of bullets life
	 */
	public void setLifeSpan(long lifeSpan) {
		realLifeSpan = lifeSpan;
	}

	/**
	 * @param reduction sets rate of reduction
	 */
	public void setReduction(float reduction) {
		this.reduction = reduction;
	}

	/**
	 * @param theta set angle of bullet
	 */
	public void setTheta(float theta) {
		this.theta = theta;
	}

	@Override
	public void update() {
		
		tickUpdate();
		
		//if low force magnitude or dist to player is far, kill the bullet.
		if (getForceSet().getNetMagnitude() < 1) {
			kill();
		}
		if (distToPlayer() > 3000) {
			kill();
		}
		
		//validate
		lifeSpanTick();
	}

	/**
	 * @return length of the gun
	 */
	public float getGunOffset() {
		return gunOffset;
	}

	/**
	 * @param gunOffset set length of gun
	 */
	public void setGunOffset(float gunOffset) {
		this.gunOffset = gunOffset;
	}

	/**
	 * @return get knockback of the bullet
	 */
	public float getKnockback() {
		return knockback;
	}

	/**
	 * @param knockback sets the knockback
	 */
	public void setKnockback(float knockback) {
		this.knockback = knockback;
	}

	
//	public String getSpawnSound() {
//		return spawnSound;
//	}
//
//	public void setSpawnSound(String spawnSound) {
//		this.spawnSound = spawnSound;
//	}

}
