package com.aidenlauris.gameobjects;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.gameobjects.util.Team;

public abstract class Projectile extends Entity {

	private float theta = 0;
	private float gunOffset = 0;
	private float lifeSpan = 15 * WorldMap.FRAMERATE;
	private float damage = 1;
	private float reduction = 0;
	private float knockback = 1;

	public Projectile() {
		super(0, 0);
		team = Team.PLAYER;
		forceAccurate = true;

	}

	public Projectile(float x, float y, float moveSpeed, float damage, float theta, float gunOffset, float reduction) {
		super((float) (x + gunOffset * Math.cos(theta)), (float) (y + gunOffset * Math.sin(theta)), moveSpeed, 1);
		this.setDamage(damage);
		this.setTheta(theta);
		addCollisionBox(new HurtBox(this, -2, -2, 4, 4, 0));
		setReduction(reduction);
		// init();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {

		if (getForceSet().getNetMagnitude() >= 40 && !highSpeedAccess) {
			return;
		}

		if (box.getOwner() instanceof Entity) {
			Entity owner = (Entity) box.getOwner();
			if (owner.team != Team.PLAYER) {
				owner.damage((HurtBox) myBox);
				owner.knockBack(getKnockback(), getForceSet().getX(), getForceSet().getY());
				owner.stun(30);
				if (health == 1) {
					kill();
				}
				health--;
			}
			if (box.getOwner() instanceof Wall) {
				kill();
			}
		}
		highSpeedAccess = false;

	}

	public float getDamage() {
		return damage;
	}

	public float getLifeSpan() {
		return lifeSpan;
	}

	public float getReduction() {
		return reduction;
	}

	public float getTheta() {
		return theta;
	}

	public void init() {
		x = (float) (x + gunOffset * Math.cos(theta));
		y = (float) (y + gunOffset * Math.sin(theta));
		Force f = new Force(getMoveSpeed(), getTheta());
		f.setReduction(getReduction());
		getForceSet().addForce(f);
		WorldMap.addGameObject(this);
	}

	private void lifeSpanTick() {
		setLifeSpan(getLifeSpan() - 1);
		if (getLifeSpan() <= 0) {
			kill();
		}
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public void setLifeSpan(float lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	public void setReduction(float reduction) {
		this.reduction = reduction;
	}

	public void setTheta(float theta) {
		this.theta = theta;
	}

	@Override
	public void update() {
		tickUpdate();
		if (getForceSet().getNetMagnitude() < 1) {
			kill();
		}
		if (distToPlayer() > 3000) {
			kill();
		}
		lifeSpanTick();
	}

	public float getGunOffset() {
		return gunOffset;
	}

	public void setGunOffset(float gunOffset) {
		this.gunOffset = gunOffset;
	}

	public float getKnockback() {
		return knockback;
	}

	public void setKnockback(float knockback) {
		this.knockback = knockback;
	}

}
