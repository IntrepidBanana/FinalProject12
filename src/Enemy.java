
public class Enemy extends Entity {

	public Enemy(WorldMap wm, int x, int y, int health, int strength, float speed) {
		super(wm, x, y, 1, 1);
		resistance = 0.9f;

		setCollisionBox(new HitBox(this, -12.5f, -12.5f, 25, 25, false));
		this.health = health;
		moveSpeed = speed;
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
		float dist = (float) Math.sqrt(Math.pow(wm.getPlayer().x - x, 2) + Math.pow(wm.getPlayer().y - y, 2));
		time++;
		move();
		
		if (dist < 20) {
			attack();
		}
		
	}

	public void move() {
		float dist = (float) Math.sqrt(Math.pow(wm.getPlayer().x - x, 2) + Math.pow(wm.getPlayer().y - y, 2));
		if (dist < 200) {
			ForceAnchor f = new ForceAnchor(moveSpeed, this, wm.getPlayer(), -1);
			f.setId("PlayerFollow");
			f.hasVariableSpeed(false);
			if (forces.getForce("PlayerFollow") == null) {
				forces.removeForce("Random");
				forces.addForce(f);
				System.out.println("Running Attack");
				attack();
			}
		} else {
			Force f = new Force(moveSpeed, (float) Math.toRadians(Math.random() * 360));
			f.setId("Random");
			f.setLifeSpan(1000);
			f.setReduction(0f);
			if (forces.getForce("Random") == null) {
				forces.removeForce("PlayerFollow");
				forces.addForce(f);
			}
		}
	}
	
	public void attack(){
		
	}

}
