
public abstract class Gun extends Weapon {

	private Entity owner = WorldMap.getPlayer();
	private boolean auto = true;
	private float length = 15;
	private int quickRelease = getAtkSpeed();
	private int accuracy = 30;
	private long lastFire = 0;
	private long lastRelease = 0;
	private float reduction = 0.03f;
	
	public Gun(double weight, int stackSize, int damage, int atkSpeed) {
		super(weight, stackSize, damage, atkSpeed);
	}

	public abstract void fireGun(float theta);

	protected void fire(float theta, Projectile p) {

		fire(theta, new Projectile[] { p });
	}

	protected void fire(float theta, Projectile[] set) {
		if (!isAuto()) {
			boolean fired = true;
			if (getLengthSinceFire() > getAtkSpeed() && getLengthSinceRelease() > 3) {
				for (Projectile p : set) {
					float speed = p.moveSpeed;
					WorldMap.addEntity(p);
				}
				WorldMap.getCamera().cameraShake(theta, accuracy, set[0].moveSpeed * (set.length/3));
				WorldMap.getPlayer().knockBack(set[0].moveSpeed * ((set.length + 1)) /20, theta);
				updateFireTime();
			}
			updateReleaseTime();
			return;
		}
		if (getLengthSinceFire() > getAtkSpeed() || getLengthSinceRelease() > getQuickRelease()) {
			for (Projectile p : set) {
				float speed = p.moveSpeed;
				WorldMap.addEntity(p);
			}
			WorldMap.getCamera().cameraShake(theta, accuracy, set[0].moveSpeed * 1f);
			WorldMap.getPlayer().knockBack(set[0].moveSpeed /10, theta);
			updateFireTime();
		}
		updateReleaseTime();
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public void updateFireTime() {
		lastFire = WorldMap.globalTime;
	}

	public void updateReleaseTime() {
		lastRelease = WorldMap.globalTime;
	}

	public long getLengthSinceFire() {
		return WorldMap.globalTime - lastFire;
	}

	public long getLengthSinceRelease() {
		return WorldMap.globalTime - lastRelease;
	}

	public int getQuickRelease() {
		return quickRelease;
	}

	public void setQuickRelease(int quickRelease) {
		this.quickRelease = quickRelease;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public float getReduction() {
		return reduction;
	}

	public void setReduction(float reduction) {
		this.reduction = reduction;
	}

}
