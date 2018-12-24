
public class Bullet extends Projectile {

	Bullet(float x, float y, float theta, float damage, float offset, float reduction) {
		super(x, y, 10f, damage, theta, offset, reduction);
		setCollisionBox(new HurtBox(this, -6f, -6f, 12, 12, damage));
	}

	Bullet(float damage){

		setCollisionBox(new HurtBox(this, -6f, -6f, 12, 12, damage));
	}
	
}
