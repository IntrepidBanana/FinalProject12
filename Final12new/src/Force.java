
public class Force {
	protected float dx;
	protected float dy;
	protected float theta;
	private float reduction = 0.02f;
	private float magnitude;

	public Force(float magnitude, float theta) {
		this.magnitude = magnitude;
		this.theta = theta;
		dx = (float) (magnitude * Math.cos(theta));
		dy = (float) (magnitude * Math.sin(theta));
		// this.dx = dx/Math.abs(dx);
		// this.dy = dy/Math.abs(dy);
		// this.reduction = reduction;
	}

	public Force(float magnitude, float x, float y) {
		this(magnitude,(float) Math.atan2(y, x));
	}
	

	public boolean update() {
		if (Math.abs(dx) < 0.05f && Math.abs(dy) < 0.05f) {
			return true;
		}

		magnitude *= (1 - reduction);
		dx = (float) (magnitude * Math.cos(theta));
		dy = (float) (magnitude * Math.sin(theta));
		return false;

	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public float getDy() {
		return dy;
	}

	public float getReduction() {
		return reduction;
	}

	public float getTheta() {
		return theta;
	}

	public void setReduction(float reduction) {
		this.reduction = reduction;
	}
}
