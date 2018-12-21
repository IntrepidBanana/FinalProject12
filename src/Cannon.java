
public class Cannon extends Gun {

	public Cannon() {
		super(1, 1, 80, 300);
		setQuickRelease(30);
		setLength(30);
		setAccuracy(3);
		setReduction(0.005f);
		setAuto(false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fireGun(float theta) {
		theta = (float) (theta + Math.toRadians(Math.random() * getAccuracy() * 2 - getAccuracy()));
		CannonShell b = new CannonShell(WorldMap.getPlayer().x, WorldMap.getPlayer().y, getDamage(), theta, getLength(),
				getReduction());
		fire(theta, b);

	}

}
