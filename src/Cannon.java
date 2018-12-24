
public class Cannon extends Gun {

	public Cannon() {
		setAtkSpeed(60);
		setLength(30);
		setAccuracy(0);
		setAuto(false);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public Projectile bulletType() {
		CannonShell p = new CannonShell(getDamage());
		p.setMoveSpeed(12f);
		p.setReduction(0.03f);
		return p;
	}

	
}
