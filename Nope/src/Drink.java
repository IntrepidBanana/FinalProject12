
public class Drink extends Item {

	private int thirst;
	
	public Drink(double weight, int stackSize, int thirst) {
		super(weight, stackSize);
		this.thirst = thirst;
	}
	
	public int getThirst() {
		return thirst;
	}
	
	public void setThirst(int thirst) {
		this.thirst = thirst;
	}

}
