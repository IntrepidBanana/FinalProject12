
public class Item {
	
	private double weight;
	private int stackSize;
	
	Item(double weight, int stackSize){
	this.weight = weight;
	this.stackSize = stackSize;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public int getStackSize() {
		return stackSize;
	}
	
	public void setStackSize(int stackSize) {
		this.stackSize = stackSize;
	}

}
