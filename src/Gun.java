
public abstract class Gun extends Weapon {

	private Entity owner = WorldMap.getPlayer();
	private boolean auto = true;
	private float length = 15;
	private int quickRelease = getAtkSpeed();
	private int accuracy = 30;
	private long lastFire = 0;
	private long lastRelease = 0;
	private float reduction = 0f;
	private float bulletCount = 1;
	private float spread = 0;

	public Gun() {
		super(1, 1, 0, 1);
	}

	public Gun(double weight, int stackSize, int damage, int atkSpeed) {
		super(weight, stackSize, damage, atkSpeed);
	}

	public abstract Projectile bulletType();

	public float accuracyRoll() {
		return (float) (Math.toRadians(Math.random()*(2*accuracy)-accuracy));
	}
	
	public boolean canFire() {
		boolean canFire = false;
		if (isAuto()) {
			if (getLengthSinceFire() > getAtkSpeed() || getLengthSinceRelease() > getQuickRelease()) {
				canFire = true;
			}
		} else {
			if (getLengthSinceFire() > getAtkSpeed() && getLengthSinceRelease() > 1) {
				canFire = true;
			}
		}
		return canFire;
	}

	public void fire(float theta) {
		if (canFire()) {
			float totalSpreadAngle = (float) ((bulletCount - 1) * Math.toRadians(spread));
			float startTheta = theta - (totalSpreadAngle / 2);

			for (int i = 0; i < bulletCount+1; i++) {
				if(i >= bulletCount) {
					continue;
				}
				float angle = (float) (startTheta + (Math.toRadians(spread * i)) );
				Projectile p = bulletType();
				p.setGunOffset(length);
				p.x = WorldMap.getPlayer().x;
				p.y = WorldMap.getPlayer().y;
				p.setTheta(angle);
				p.init();
			}
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

	public float getBulletCount() {
		return bulletCount;
	}

	public void setBulletCount(float bulletCount) {
		this.bulletCount = bulletCount;
	}

	public float getSpread() {
		return spread;
	}

	public void setSpread(float spread) {
		this.spread = spread;
	}

}
