
public class Weapon extends Item {

	public Weapon(double weight, int stackSize, int damage, int atkSpeed) {
		super(weight, stackSize);
		this.damage = damage;
		this.atkSpeed = atkSpeed;
	}

}
