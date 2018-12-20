
public class Enemy extends Entity {

	public Enemy(WorldMap wm, int x, int y, int health, int strength, float speed) {
		super(wm, x, y, 1, 1);
		resistance = 0.9f;

		setCollisionBox(new HitBox(this, -12.5f, -12.5f, 25, 25, false));
		health = 10;
		speed = 0.45f;
	}

	@Override
	public void contactReply(CollisionBox box) {
		if (box instanceof HurtBox) {
			damage((HurtBox) box);
		}
		if (box.isSolid) {
			collide(box);
		}

	}

	public void update() {
		forceUpdate();
		time++;

		// if (time % 500 == 0) {
		move();
		// }
	}

	public void move() {
		float dist = (float) Math.sqrt(Math.pow(wm.getPlayer().x - x, 2) + Math.pow(wm.getPlayer().y - y, 2));
		if (dist < 300) {
			ForceAnchor f = new ForceAnchor(0.20f, this, wm.getPlayer(), -1);
			f.setId("PlayerFollow");
			f.hasVariableSpeed(false);
			if (forces.getForce("PlayerFollow") == null) {
				forces.addForce(f);
			}
		} else {
			Force f = new Force(0.15f, (float) Math.toRadians(Math.random() * 360));
			f.setId("Random");
			f.setLifeSpan(1000);
			f.setReduction(0f);
			if (forces.getForce("Random") == null) {
				forces.addForce(f);
			}
		}
	}

}
