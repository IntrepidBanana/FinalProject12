
public class Bullet extends Projectile {

	Bullet(float x, float y, float theta, float damage, float offset, float reduction) {
		super(x, y, 20f, theta, offset,reduction);
		setCollisionBox(new HurtBox(this, -6f, -6f, 12, 12, damage));
	}

	

}
