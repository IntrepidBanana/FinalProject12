
public class Shotgun extends Gun {

	public Shotgun() {
//		super(1,1,30,25);
		setLength(20);
		setAuto(false);

		setAccuracy(6);
		setAtkSpeed(30);
		
		setDamage(30);
		
		setSpread(3);
		setBulletCount(8);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Projectile bulletType() {
		
		ShotgunShell b = new ShotgunShell(getDamage());
		b.setMoveSpeed(20f);
		b.setReduction(0.05f);
		
		return b;
		
		
	}

	

}
