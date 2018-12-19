
public class Enemy extends Entity {

	public Enemy(WorldMap wm, int x, int y, int health, int strength, float speed) {
		super(wm, x, y, 1, 1);
		resistance = 0.9f;

		setCollisionBox(new HitBox(this, -150, -150, 150, 150, true));

		setCollisionBox(new HitBox(this, -12.5f, -12.5f, 25, 25, true));
		health = 10;
		speed = 0.45f;
		move();
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

//		if (time % 750 == 0) {
//			move();
//		}
	}

	public void move() {

		ForceAnchor f = new ForceAnchor(0.45f, this, wm.getPlayer(), -1);
		f.hasVariableSpeed(false);
		forces.addForce(f);

	}

}
