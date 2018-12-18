
public class Enemy extends Entity {

	public Enemy(int x, int y, int health, int strength, double speed) {
		super(x, y, 1, 1);
		resistance = 0.9f;
		setCollisionBox(new HitBox(this, -12.5f, -12.5f, 25, 25, true));
		health = 10;
		speed = 0.5;
	}

	@Override
	public void contactReply(CollisionBox box) {
		if (box instanceof HurtBox) {
			damage((HurtBox) box);
		}
		if (box.isSolid) {
			collide(box.getX(), box.getY(), 1f);
		}

	}

	public void update() {
		forceUpdate();
		time++;

		if (time % 500 == 0) {
			move();
		}
	}

	public void move() {
		Force f = new Force(0.45f, (float) Math.toRadians(Math.random() * 360));
		f.setReduction(0.005f);
		forces.addForce(f);
	}

}
