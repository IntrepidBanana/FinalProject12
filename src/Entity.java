
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

	public void collide(CollisionBox box) {
		
		

		
		
		float top = box.getTop();
		float bot = box.getBottom();
		
		float left = box.getLeft();
		float right = box.getRight();
		

		// forces.addForce(magnitude, fx, fy);

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

	public abstract void contactReply(CollisionBox box);

	public abstract void update();

	public ForceSet getForces() {
		return forces;
	}

}
