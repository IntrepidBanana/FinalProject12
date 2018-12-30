
public class Shotgun extends Gun {

	public Shotgun() {
//		super(1,1,30,25);
		setLength(25);
		setAuto(false);

		setAccuracy(8);
		setAtkSpeed(30);
		
		setDamage(90);
		
		setSpread(1);
		setBulletCount(24);
		setAmmoPerUse(1);
		
		setAmmoType(new ShotgunAmmo().item());
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Projectile bulletType() {
		
		ShotgunShell b = new ShotgunShell(getDamage());
		b.setMoveSpeed(20f);
		b.setReduction(0.05f);
		
		return b;
		
		
	}

	@Override
	public String item() {
		return this.toString();
	}
}
