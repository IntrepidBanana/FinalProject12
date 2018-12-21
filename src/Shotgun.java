
public class Shotgun extends Gun {

	public Shotgun() {
		super(1,1,30,150);
		setLength(20);
		setAccuracy(10);
		setAuto(false);
		setReduction(0.005f);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fireGun(float theta) {
		ShotgunShell[] shells = new ShotgunShell[6];
		for(int i = 0; i < 6; i++) {
			float angle = (float) ((theta) + Math.toRadians( getAccuracy()*(i-3) + Math.random()*getAccuracy()-getAccuracy()/2));
//			System.out.println(getDamage());
			ShotgunShell b = new ShotgunShell(WorldMap.getPlayer().x, WorldMap.getPlayer().y, angle, getDamage(), getLength(),getReduction());
			shells[i]=b;
		}
		fire(theta, shells);

	}

}
