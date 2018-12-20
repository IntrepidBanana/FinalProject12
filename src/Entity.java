
public abstract class Entity {

	float x;
	float y;
	float moveSpeed;
	int health;
	int strength;
	CollisionBox hitbox;
	ForceSet forces = new ForceSet();
	float resistance = 0f;
	WorldMap wm;
	int time = 0;

	Entity(WorldMap wm, float x, float y, float moveSpeed, int health) {
		this.x = x;
		this.y = y;
		this.moveSpeed = moveSpeed / 10;
		this.health = health;
		this.wm = wm;
	}

	public void setWorldMap(WorldMap wm) {
		this.wm = wm;
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

		System.out.println();
		System.out.println("0 " + (myBox.getBottom() - box.getTop()));
		System.out.println("1 " + (box.getRight() - myBox.getLeft()));
		System.out.println("2 " + (box.getBottom() - myBox.getTop()));
		System.out.println("3 " + (myBox.getRight() - box.getLeft()));
		System.out.println(direction);
		float mag  =0.1f;
		switch (direction) {
		case 0:
			forces.setNetY(mag, -1);
			break;
		case 1:

			forces.setNetX(mag,1);
			break;
		case 2:
			forces.setNetY(mag, 1);
			break;
		case 3:
			forces.setNetX(mag,-1);
			break;
		}

	}

	public void knockBack(float centerX, float centerY, float power) {
		float dx = centerX - x;
		float dy = centerY - y;

		System.out.println(dx / dy);
		float theta = (float) ((float) Math.atan2(dy, dx) + Math.PI);
		Force f = new Force(power, theta);
		forces.addForce(f);

	}

	public void forceUpdate() {
		x += forces.getX();
		y += forces.getY();
		forces.update();
	}

	public void damage(HurtBox box) {
		health -= (box.damage - box.damage * resistance);
		System.out.println(health);
	}

	public abstract void contactReply(CollisionBox box, CollisionBox myBox);

	public abstract void update();

	public ForceSet getForces() {
		return forces;
	}

}
