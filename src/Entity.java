import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class Entity {

	float weight = 1;
	float x = 0;
	float y = 0;
	private float moveSpeed = 1;
	int health = 1;
	int strength = 0;
	CollisionBox hitbox;
	ForceSet forces = new ForceSet();
	float resistance = 0f;
	int time = 0;
	int invincibilityFrames = 100;
	int invincibility = 0;
	Map<Entity, Integer> invincibleAgainst = new HashMap<>();

	Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}

	Entity(float x, float y, float moveSpeed, int health) {
		this.x = x;
		this.y = y;
		setMoveSpeed(moveSpeed);
		this.health = health;
	}

	public void setCollisionBox(CollisionBox hitbox) {
		this.hitbox = hitbox;
	}

	public CollisionBox getCollisionBox() {
		return hitbox;
	}

	public void collide(CollisionBox box, CollisionBox myBox) {

		// 0 up
		// 1 right
		// 2 down
		// 3 left;
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
		float mag = 3.5f;

		switch (direction) {
		case 0:
			forces.setNetY(mag, -1);
			break;
		case 1:

			forces.setNetX(mag, 1);
			break;
		case 2:
			forces.setNetY(mag, 1);
			break;
		case 3:
			forces.setNetX(mag, -1);
			break;
		}

	}

	public void knockBack(float magnitude, float x, float y) {
		float theta = (float) ((float) Math.atan2(y, x) + Math.PI);
		knockBack(magnitude, theta);
	}

	public void knockBack(float magnitude, float theta) {
		Force f = new Force(magnitude, (float) (theta + Math.PI));
		f.setReduction(0.2f);
		forces.addForce(f);
	}

	public synchronized void forceUpdate() {
		if (health <= 0) {
			kill();
		}
		x += forces.getX();
		y += forces.getY();
		forces.update();
		Iterator<Entity> i = invincibleAgainst.keySet().iterator();
		while (i.hasNext()) {
			Entity e = i.next();
			invincibleAgainst.put(e, invincibleAgainst.get(e) - 1);
			if (invincibleAgainst.get(e) < 0) {
				i.remove();
			}

		}
	}

	public void kill() {
		removeSelf();
	}

	public void removeSelf() {
		WorldMap.removeEntity(this);
	}

	public void damage(HurtBox box) {
		if (!invincibleAgainst.containsKey(box.getOwner())) {
			health -= (box.damage);
			invincibility += invincibilityFrames;
			invincibleAgainst.put(box.getOwner(), invincibilityFrames);
			// System.out.println(health);
		}
	}

	public abstract void contactReply(CollisionBox box, CollisionBox myBox);

	public abstract void update();

	public ForceSet getForces() {
		return forces;
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

}
