
public abstract class Projectile extends Entity {

	private float theta = 0;
	private float gunOffset = 0;
	private float lifeSpan = 15 * WorldMap.FRAMERATE;
	private float damage = 1;
	private float reduction = 0;

	public Projectile() {
		super(0, 0);

	}

	public Projectile(float x, float y, float moveSpeed, float damage, float theta, float gunOffset, float reduction) {
		super((float) (x + gunOffset * Math.cos(theta)), (float) (y + gunOffset * Math.sin(theta)), moveSpeed, 1);
		this.setDamage(damage);
		this.setTheta(theta);
		setCollisionBox(new HurtBox(this, -2, -2, 4, 4, 0));
		setReduction(reduction);
//		init();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		if (!(box.getOwner() instanceof Player) && !(box.getOwner() instanceof Projectile)) {
			// System.out.println(this + " collided with: " + box.getOwner());
			kill();
		}

	}

	public float getDamage() {
		return damage;
	}

	public float getLifeSpan() {
		return lifeSpan;
	}

	public float getReduction() {
		return reduction;
	}

	public float getTheta() {
		return theta;
	}

	public void init() {
		x = (float) (x + gunOffset * Math.cos(theta));
		y = (float) (y + gunOffset * Math.sin(theta));
		Force f = new Force(getMoveSpeed(), getTheta());
		f.setReduction(getReduction());
		forces.addForce(f);
		WorldMap.addEntity(this);
//		System.out.println(forces.getX() + " " + forces.getY());
	}

	private void lifeSpanTick() {
		setLifeSpan(getLifeSpan() - 1);
		if (getLifeSpan() <= 0) {
			kill();
		}
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public void setLifeSpan(float lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	public void setReduction(float reduction) {
		this.reduction = reduction;
	}

	public void setTheta(float theta) {
		this.theta = theta;
	}

	@Override
	public void update() {
		forceUpdate();
		if (forces.getNetMagnitude() < 1) {
			kill();
		}
		System.out.println(this + " magnitude: " + forces.getNetMagnitude());
		lifeSpanTick();
	}

	public float getGunOffset() {
		return gunOffset;
	}

	public void setGunOffset(float gunOffset) {
		this.gunOffset = gunOffset;
	}

	
	@Override
	public String toString() {
		String s = "\n" + this.getClass() + "\nSpeed: " + getMoveSpeed() + "\n(x, y): (" + (int)this.x + ", " + (int) this.y +")";
		s += "\nMagnitude:" + this.forces.getNetMagnitude();
		return s;
	}
	
}
