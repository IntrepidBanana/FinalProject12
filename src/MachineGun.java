
public class MachineGun extends Gun {

	public MachineGun() {
		setAtkSpeed(4);
		setQuickRelease(3);
		setLength(20);
		setAccuracy(3);
		setDamage(15);
		
	}

	@Override
	public Projectile bulletType() {
		Bullet b = new Bullet(getDamage());
		b.setMoveSpeed(15f);
		b.setReduction(0.01f);
		return b;
	}
}
