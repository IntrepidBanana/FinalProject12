package com.aidenlauris.gameobjects.util;


import com.aidenlauris.game.util.Time;

/**
 * @author Lauris & Aiden Jan 21, 2019
 * 
 *         Entities are a subset of gameobject that hold health, knockback,
 *         damage and other important data
 */
/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 */
/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 */
public abstract class Entity extends GameObject {

	// classifies whether the entity is an enemy, neutral or on the player team
	public Team team = Team.NONE;

	// data
	public float moveSpeed = 1;
	public int maxHealth = 1;
	public int health = 1;
	protected long time = 0;

	/**
	 * basic constructor
	 * 
	 * @param x
	 *            xcoord
	 * @param y
	 *            ycoord
	 */
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * constructor that plugs in data automatically
	 * 
	 * @param x
	 *            xcoord
	 * @param y
	 *            ycoord
	 * @param moveSpeed
	 *            movespeed
	 * @param health
	 *            health
	 */
	public Entity(float x, float y, float moveSpeed, int health) {
		this.x = x;
		this.y = y;
		this.moveSpeed = moveSpeed;
		this.health = health;
		this.maxHealth = health;
	}

	

	/**
	 * knockback this entity from with a force originating from an xy coordinate.
	 * @param magnitude magnitude of force
	 * @param x xcoord
	 * @param y ycoord
	 */
	public void knockBack(float magnitude, float x, float y) {
		
		//figure out angle
		float theta = (float) ((float) Math.atan2(y, x) + Math.PI);
		knockBack(magnitude, theta);
	}

	/**
	 * knockback this entity from a force with angle theta
	 * @param magnitude magnitude of force
	 * @param theta angle of force
	 */
	public void knockBack(float magnitude, float theta) {

		//initiate a force with magnitude and angle theta
		Force f = new Force(magnitude, (float) (theta));
		f.setReduction(0.3f);
		addForce(f);
	}


	

	/**
	 * updates all intra-tick data, such as health and force updates
	 */
	public void tickUpdate() {
		time = Time.global() - initTime;
		if (health <= 0) {
			kill();
		}
		forceUpdate();

	}

	/**
	 * damage based on a hurtbox
	 * @param box hurtbox
	 */
	public void damage(HurtBox box) {
		damage(box.damage);

	}

	/**
	 * remove health by a float amount
	 * @param damage float damage
	 */
	public void damage(float damage) {
		health -= (damage);

	}

	/* (non-Javadoc)
	 * @see com.aidenlauris.gameobjects.util.GameObject#collisionOccured(com.aidenlauris.gameobjects.util.CollisionBox, com.aidenlauris.gameobjects.util.CollisionBox)
	 */
	public abstract void collisionOccured(CollisionBox theirBox, CollisionBox myBox);

	/* (non-Javadoc)
	 * @see com.aidenlauris.gameobjects.util.GameObject#update()
	 */
	public abstract void update();

	/* (non-Javadoc)
	 * @see com.aidenlauris.gameobjects.util.GameObject#getForceSet()
	 */
	public ForceSet getForceSet() {
		if (moveSpeed == 0) {
			getForceSet().getForces().clear();
		}
		return super.getForceSet();
	}

	/**
	 * @return movespeed
	 */
	public float getMoveSpeed() {
		return moveSpeed;
	}

	/**
	 * set movespeed of entity
	 * @param moveSpeed movespeed of entity
	 */
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

}
