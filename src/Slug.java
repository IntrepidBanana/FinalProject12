
public class Slug extends Enemy {

	public Slug(WorldMap wm, int x, int y, int health, int strength, float speed) {
		super(wm, x, y, health, strength, speed);
		health = 50;
		strength = 5;
		moveSpeed = 0.15f;
		setCollisionBox(new HitBox(this, -12.5f, -12.5f, 15, 15, false));
	}
	
	
	public void attack() {
		float dist = (float) Math.sqrt(Math.pow(wm.getPlayer().x - x, 2) + Math.pow(wm.getPlayer().y - y, 2));
		Force f = forces.getForce("PlayerFollow");
		System.out.println(dist);
		if (dist<20) {
			System.out.println("Boom!");
			f.setReduction(0f);
			//f.setMagnitude(wm.getPlayer().moveSpeed);
			//System.out.println(f.getMagnitude());
			f.setLifeSpan(500);
			wm.getPlayer().forces.addForce(f);
		}
	}
	
	
	
	

}
