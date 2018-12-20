
public class Force {
	protected float dx = 0;
	protected float dy = 0;
	protected float theta = 0;
	private float reduction = 0.02f;
	private float magnitude = 0;
	private boolean terminated = false;
	private int lifeSpan = Integer.MAX_VALUE;
	private String id = this.toString();

	public Force(float magnitude, float theta) {
		this.setMagnitude(magnitude);
		this.theta = theta;
		dx = (float) (magnitude * Math.cos(theta));
		dy = (float) (magnitude * Math.sin(theta));
	}

	public Force(float magnitude, float x, float y) {
		this(magnitude, (float) Math.atan2(y, x));
	}

	public void update() {
		if (getLifeSpan() != Integer.MAX_VALUE && getLifeSpan() <= 0) {
			terminated = false;
			dx = 0;
			dy = 0;
			return;
		}

		setLifeSpan(getLifeSpan() - 1);

		if (Math.abs(dx) < 0.02f && Math.abs(dy) < 0.02f) {
			terminated = false;
			dx = 0;
			dy = 0;
			return;
		}

		setMagnitude(getMagnitude() * (1 - reduction));
		dx = (float) (getMagnitude() * Math.cos(theta));
		dy = (float) (getMagnitude() * Math.sin(theta));

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

	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private int getLifeSpan() {
		return lifeSpan;
	}

	private void setLifeSpan(int lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	
	public float getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(float magnitude) {
		this.magnitude = magnitude;
	}
}
