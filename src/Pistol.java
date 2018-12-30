
public class Pistol extends MachineGun{

	public Pistol() {
		super();
		setAuto(true);
		setAtkSpeed(15);
		setQuickRelease(3);
		setDamage(80);
		setLength(10);
		setAccuracy(1);
	}
	
	@Override
	public Projectile bulletType() {
		Bullet b = new Bullet(getDamage());
		b.setMoveSpeed(60f);
		return b;
	}
}
