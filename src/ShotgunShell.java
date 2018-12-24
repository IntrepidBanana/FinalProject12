
public class ShotgunShell extends Projectile {

	public ShotgunShell(float x, float y, float theta, float damage, float offset, float reduction) {
		super(x, y, 12f, damage, theta, offset, reduction);
		setDamage(damage);
		setCollisionBox(new HurtBox(this, -7.5f, -7.5f, 15, 15, damage));
	}

	public ShotgunShell(float damage) {
		setCollisionBox(new HurtBox(this, -7.5f, -7.5f, 15, 15, damage));
	}

}
