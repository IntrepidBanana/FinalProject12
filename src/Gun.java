import java.awt.Color;

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
	private String ammoType = "";

	private int ammoPerUse = 1;

	public Gun() {
		super(1, 1, 0, 1);
		setCount(1);
	}

	public Gun(double weight, int stackSize, int damage, int atkSpeed) {
		super(weight, stackSize, damage, atkSpeed);
		setCount(1);
	}

	public abstract Projectile bulletType();

	public float rollAccuracy() {
		return (float) (Math.toRadians(Math.random() * (2 * accuracy) - accuracy));
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

	public void fire() {
		if (canFire()) {

			Player player = WorldMap.getPlayer();
			Mouse mouse = IOHandler.mouse;
			float theta = mouse.theta(player.x, player.y);

			float totalSpreadAngle = (float) ((bulletCount - 1) * Math.toRadians(spread));
			float startTheta = theta - (totalSpreadAngle / 2);

			if (!getAmmoType().equals("")) {
				Inventory pInv = player.getInventory();
				int ammoIndex = pInv.indexOf(getAmmoType());

				if (ammoIndex == -1) {
					return;
				}

				if (pInv.getItem(ammoIndex).getCount() < ammoPerUse) {
					return;
				}
				pInv.getItem(ammoIndex).removeFromCount(ammoPerUse);

			}

			for (int i = 0; i < bulletCount; i++) {

				float angle = (float) (startTheta + (Math.toRadians(spread * i)) + rollAccuracy());
				Projectile p = bulletType();
				p.setGunOffset(length);
				p.x = WorldMap.getPlayer().x;
				p.y = WorldMap.getPlayer().y;
				p.setTheta(angle);
				p.init();
				if (!ammoType.equals("")) {
					Particle.create(p.x, p.y, (float) (10f+Math.random()*30), (float) (theta + Math.PI), 10, 3, false, Color.GRAY, (int) (40+Math.random()*30), 8);
					Particle.create(p.x, p.y, (float) (3f+Math.random()*5), (float) (theta + Math.PI+Math.PI/2), i*2, 1, false, Color.yellow, 10, 6);
					Particle.create(p.x, p.y, (float) (3f+Math.random()*5), (float) (theta + Math.PI-Math.PI/2), i*2, 1, false, Color.yellow, 10, 6);
				}
			}
			switch (ammoType) {
			case "BulletAmmo":
				Particle.create(player.x, player.y, 20f, (float) (theta + Math.PI / 1.8), 15, ammoPerUse, false,
						Color.orange, 120, 4);
				break;
			case "ShotgunAmmo":
				Particle.create(player.x, player.y, 20f, (float) (theta + Math.PI / 2), 15, ammoPerUse, false,
						Color.red, 120, 7);
				break;
			case "ExplosiveAmmo":
				Particle.create(player.x, player.y, 20f, (float) (theta + Math.PI / 1.5), 15, ammoPerUse, false,
						Color.green, 120, 12);
				break;
			default:
				break;
			}
			if (!ammoType.equals("")) {
				WorldMap.getCamera().cameraShake(theta, totalSpreadAngle / 2,
						bulletType().getKnockback() * Math.min(getBulletCount(), 5));
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

	public int getAmmoPerUse() {
		return ammoPerUse;
	}

	public void setAmmoPerUse(int ammoPerUse) {
		this.ammoPerUse = ammoPerUse;
	}

	@Override
	public void useItem() {
		fire();
	}

	public String getAmmoType() {
		return ammoType;
	}

	public void setAmmoType(String ammoType) {
		this.ammoType = ammoType;
	}
}
