
public abstract class Projectile extends Entity {

	private float theta;
	private float lifeSpan = 5000;
	private float damage = 1;
	private float reduction = 0.003f;

	Projectile(float x, float y, float moveSpeed, float damage, float theta, float gunOffset, float reduction) {
		super((float) (x + gunOffset * Math.cos(theta)), (float) (y + gunOffset * Math.sin(theta)), moveSpeed, 1);
		this.damage = damage;
		this.theta = theta;
		setCollisionBox(new HurtBox(this, -2, -2, 4, 4, 0));
		setReduction(reduction);
		init();
		// TODO Auto-generated constructor stub
	}

	private void init() {
		Force f = new Force(moveSpeed, theta);
		f.setReduction(getReduction());
		forces.addForce(f);
	}

	private void lifeSpanTick() {
		lifeSpan--;
		if (lifeSpan <= 0) {
			kill();
		}
	}

	@Override
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		if (!(box.getOwner() instanceof Player) && !(box.getOwner() instanceof Projectile)) {
			System.out.println(this + " collided with: " + box.getOwner());
			kill();
		}

	}

	@Override
	public void update() {
		forceUpdate();
		if (forces.getNetMagnitude() < 0.1) {
			kill();
		}
		lifeSpanTick();
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public float getReduction() {
		return reduction;
	}

	public void setReduction(float reduction) {
		this.reduction = reduction;
	}

}
