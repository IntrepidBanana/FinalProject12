
public class Knife extends Gun {

	public Knife() {
		setLength(5);
		setAuto(false);

		setAccuracy(5);
		setAtkSpeed(8);
		setBulletCount(1);
		setDamage(40);
		setAmmoPerUse(0);
	}

	@Override
	public Projectile bulletType() {
		MeleeSwing b = new MeleeSwing(getDamage());
		b.setMoveSpeed(10f);
		b.setReduction(0.3f);
		return b;
	}

}
