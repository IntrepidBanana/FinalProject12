
public class MachineGun extends Gun {

	public MachineGun() {
		super(1, 1, 30, 70);
		setQuickRelease(30);
		setLength(20);
		setAccuracy(10);
		setReduction(0.003f);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fireGun(float theta) {
		theta = (float) (theta + Math.toRadians(Math.random()*getAccuracy()*2-getAccuracy()));
		Bullet b = new Bullet(WorldMap.getPlayer().x, WorldMap.getPlayer().y, theta, getDamage(), getLength(),getReduction());
		fire(theta, b);
		
		
		
	}
}
