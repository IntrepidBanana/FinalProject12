
public class Slug extends Enemy {

	public Slug(int x, int y, int health, int strength, float speed) {
		super(x, y, health, strength, speed);
		health = 50;
		strength = 5;
		moveSpeed = 0.15f;
		setCollisionBox(new HitBox(this, -12.5f, -12.5f, 15, 15, true));
	}
	
	
	public void attack() {
		ForceAnchor f = new ForceAnchor(-2f, WorldMap.getPlayer(), this, -1);
		ForceAnchor f2 = new ForceAnchor(-1f, this, WorldMap.getPlayer(), -1);
			f.setReduction(0f);
			f2.setReduction(0f);
			f.setId("Knockback");
			f2.setId("Knock");
			//f.setMagnitude(wm.getPlayer().moveSpeed);
			//System.out.println(f.getMagnitude());
			f.setLifeSpan(1000);
			f2.setLifeSpan(500);
			WorldMap.getPlayer().forces.removeForce("Knockback");
			forces.removeForce("Knock");
			forces.addForce(f2);
			WorldMap.getPlayer().forces.addForce(f);
		
	}
	
	
	
	

}
