
public class ShotgunAmmo extends Ammo {

	public ShotgunAmmo() {
		setStackSize(24);
	}
	
	ShotgunAmmo(int count){
		this();
		setCount(count);
	}
}
