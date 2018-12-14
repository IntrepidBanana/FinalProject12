
public class Character {
	private int health;
	private int strength;
	private double speed;
	
	
	Character(int health, int strength, double speed){
		  this.health = health;
		  this.strength = strength;
		  this.speed = speed;
		}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public void setStrength(int strength) {
		this.strength = strength;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
