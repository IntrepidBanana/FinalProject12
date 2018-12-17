
public class Bullet extends Entity {

	float lifeSpan = 100f;

	Bullet(float x, float y, float moveSpeed, float theta) {
		super((float) (x + 15 * Math.cos(theta)), (float) (y + 15 * Math.sin(theta)),
				moveSpeed, 1);
		Force f = new Force(moveSpeed, theta);
		f.setReduction(0.01f);
		forces.addForce(f);
		forces.addForce(new ForceAnchor(0.3f, this, WorldMap.entities.get(0)));
		forces.addForce(new ForceAnchor(1f, this, WorldMap.entities.get(0),90));
		setCollisionBox(new HurtBox(this, -2.5f, -2.5f, 5, 5, 1));
	}

	@Override
	public void contactReply(CollisionBox box) {
		if (box.isSolid) {
			health = 0;
		}

	}

	@Override
	public void update() {
		lifeSpan -= 0.1f;
		if (lifeSpan < 0) {
			health = 0;
		}
		
		
		forceUpdate();

	}

}
