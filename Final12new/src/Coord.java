
public class Coord {
	private float x;
	private float y;

	public Coord(float x, float y) {
		setX(x);
		setY(y);
	}

	public Coord(double x, double y) {
		setX((float)x);
		setY((float)y);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
