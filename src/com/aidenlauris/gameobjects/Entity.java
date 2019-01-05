package com.aidenlauris.gameobjects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceSet;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.gameobjects.util.Team;

public abstract class Entity extends GameObject {
	Team team = Team.NONE;
	float weight = 1;
	private float moveSpeed = 1;
	int maxHealth = 1;
	int health = 1;
	int time = 0;
	final int invincibilityFrames = 10;
	int invincibility = 0;
	Map<GameObject, Integer> invincibleAgainst = new HashMap<>();
	int stunTimer = 0;
	

	Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}

	Entity(float x, float y, float moveSpeed, int health) {
		this.x = x;
		this.y = y;
		setMoveSpeed(moveSpeed);
		this.health = health;
		this.maxHealth = health;
	}

	public void collide(CollisionBox box, CollisionBox myBox) {

		// 0 mybox is above
		// 1 mybox is to the right
		// 2 mybox is below
		// 3 mybox is to the left
		int direction = 0;
		float smallestDifference = myBox.getBottom() - box.getTop();
		if (box.getRight() - myBox.getLeft() < smallestDifference) {
			smallestDifference = box.getRight() - myBox.getLeft();
			direction = 1;
		}

		if (box.getBottom() - myBox.getTop() < smallestDifference) {
			smallestDifference = box.getBottom() - myBox.getTop();
			direction = 2;
		}

		if (myBox.getRight() - box.getLeft() < smallestDifference) {
			smallestDifference = myBox.getRight() - box.getLeft();
			direction = 3;
		}
		//
		// System.out.println();
		// System.out.println("0 " + (myBox.getBottom() - box.getTop()));
		// System.out.println("1 " + (box.getRight() - myBox.getLeft()));
		// System.out.println("2 " + (box.getBottom() - myBox.getTop()));
		// System.out.println("3 " + (myBox.getRight() - box.getLeft()));
		// System.out.println(direction);
		float mag = ((Entity) myBox.getOwner()).moveSpeed;
		// mag = 1f;

		Entity owner = (Entity) myBox.getOwner();
		float netX = Math.abs(owner.getForceSet().getX());
		float netY = Math.abs(owner.getForceSet().getY());

		switch (direction) {
		case 0:
			owner.y = -2 + box.getTop() - myBox.len / 2 + (myBox.getY() - owner.y);

			break;
		case 1:

			owner.x = 2 + box.getRight() + myBox.wid / 2 + (myBox.getX() - owner.x);
			break;
		case 2:
			owner.y = 2 + box.getBottom() + myBox.len / 2 + (myBox.getY() - owner.y);
			break;
		case 3:

			owner.x = -2 + box.getLeft() - myBox.wid / 2 + (myBox.getX() - owner.x);
			break;
		}

		// ((Entity) myBox.getOwner()).stun(7);

	}

	public void knockBack(float magnitude, float x, float y) {
		float theta = (float) ((float) Math.atan2(y, x) + Math.PI);
		knockBack(magnitude, theta);
	}

	public void knockBack(float magnitude, float theta) {

		Force f = new Force(magnitude, (float) (theta + Math.PI));
		f.setReduction(0.09f);
		getForceSet().addForce(f);
	}

	public void knockBack(float magnitude, float originX, float originY, float pointX, float pointY) {
		float dx = pointX - originX;
		float dy = pointY - originY;

		knockBack(magnitude, dx, dy);

	}

	public void printAllForces() {
		System.out.println("\n Total Forces: " + getForceSet().getForces().size());
		for(Force f : getForceSet().getForces()) {
			System.out.println(f.getId() + ": " + f.getMagnitude() + "/tick,  " + (-f.getReduction()) + "/tick/tick");
		}
	}

	public synchronized void tickUpdate() {
		time++;
		if (health <= 0) {
			kill();
		}
		forceUpdate();
		if (invincibility > 0) {
			invincibility--;
		}
		if (stunTimer > 0) {
			stunTimer--;
		}
		
	}

	public void damage(HurtBox box) {
		if (invincibility <= 0) {
			health -= (box.damage);
		}

	}

	public void damage(int damage) {
		if (invincibility <= 0) {
			health -= (damage);
		}

	}

	public abstract void collisionOccured(CollisionBox box, CollisionBox myBox);

	public abstract void update();

	public ForceSet getForceSet() {
		if(moveSpeed == 0) {
			getForceSet().getForces().clear();
		}
		return super.getForceSet();
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public void startIFrames() {
		invincibility = invincibilityFrames;
	}

	public void stun(int stun) {
		stunTimer = Math.max(stunTimer, stun);
	}

	public boolean isStunned() {
		if (stunTimer > 0) {
			return true;
		}
		return false;

	}
}
