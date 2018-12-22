
public class Enemy extends Entity {

	public Enemy(int x, int y, int health, int strength, float speed) {
		super(x, y, 1, 1);
		resistance = 0.9f;

		setCollisionBox(new HitBox(this, -12.5f, -12.5f, 25, 25, false));

		this.health = health;
		moveSpeed = speed;
	}

	@Override
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		if (box instanceof HurtBox) {
			knockBack(box.getOwner().forces.getNetMagnitude()*box.getOwner().weight/3, box.getOwner().forces.getX(),
					box.getOwner().forces.getY());
			damage((HurtBox) box);
			WorldMap.sleep();
		}
		if (box.isSolid) {
			collide(box, myBox);
		}

	}

	public void update() {
		forceUpdate();
		float dist = (float) Math.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		time++;
		move();
		
		if (dist < 20) {
			attack();
		}
		
	}

	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		if (dist < 300) {
			ForceAnchor f = new ForceAnchor(0.20f, this, WorldMap.getPlayer(), -1);

			f.setId("PlayerFollow");
			f.hasVariableSpeed(false);
			if (forces.getForce("PlayerFollow") == null) {
				forces.removeForce("Random");
				forces.addForce(f);
				System.out.println("Running Attack");
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

	@Override
	public void kill() {
			WorldMap.addEntity(new Enemy(300 + 1 * 30, 300 + 1 *30, 200, 0, 1f));
		
		removeSelf();
	}

}
