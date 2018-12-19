
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

	Entity(float x, float y, float moveSpeed, int health) {
		this.x = x;
		this.y = y;
		this.moveSpeed = moveSpeed / 10;
		this.health = health;
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

	public void collide(float centerX, float centerY, float reduction) {
		float dx = x - centerX;
		float dy = y - centerY;

		float fx = 0;
		float fy = 0;

		if (Math.abs(dx) / Math.abs(dy) > Math.abs(dy) / Math.abs(dx)) {
			fx = (dx > 0) ? 1 : -1;
			forces.setNetX(0.1f, fx);
		} else {
			fy = (dy > 0) ? 1 : -1;
			forces.setNetY(0.1f, fy);
		}

		// forces.addForce(magnitude, fx, fy);

	}

	public void knockBack(float centerX, float centerY, float power) {
		float dx = centerX - x;
		float dy = centerY - y;

		System.out.println(dx / dy);
		float theta = (float) ((float) Math.atan2(dy, dx) + Math.PI);
		forces.addForce(power, theta);

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

	public abstract void contactReply(CollisionBox box);

	public abstract void update();

}
