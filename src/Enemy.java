
public class Enemy extends Entity {

	public Enemy(int x, int y, int health, int strength, float speed) {
		super(x, y, 1, 1);
		resistance = 0.9f;

		setCollisionBox(new HitBox(this, -150, -150, 150, 150, true));

		setCollisionBox(new HitBox(this, -12.5f, -12.5f, 25, 25, true));
		health = 10;
		speed = 0.45f;

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

		if (time % 750 == 0) {
			move();
		}
	}

	public void move() {
		
		Force f = new ForceAnchor(0.15f, this, WorldMap.entities.get(2));
		f.setReduction(1f);
		forces.addForce(f);
//		forces.addForce(new ForceAnchor(0.45f, this, WorldMap.entities.get(2)));
//		forces.get(forces.size() -1).setReduction(0.005f);
	}

}
