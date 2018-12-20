
public class ForceAnchor extends Force {

	private Entity owner;
	private Entity anchor;
	private float pull;
	private float offset = 0;
	private float threshold = 1f;
	private boolean inversion = false;
	private boolean isVariable = true;

	public ForceAnchor(float magnitude, Entity owner, Entity anchor, float threshold) {
		super(-magnitude, (float) Math.atan2(anchor.y - owner.y, anchor.x - owner.x));
		this.setPull(pull);
		this.setOwner(owner);
		this.setAnchor(anchor);
		this.setThreshold(threshold);
	}
	
	Entity getAnchor() {
		return anchor;
	}

	float getOffset() {
		return offset;
	}

	Entity getOwner() {
		return owner;
	}

	float getPull() {
		return pull;
	}

	float getThreshold() {
		return threshold;
	}

	public void hasVariableSpeed(boolean b) {
		isVariable = b;
	}

	void setAnchor(Entity anchor) {
		this.anchor = anchor;
	}

	public void setInversion(boolean isInverted) {
		inversion = isInverted;
	}

	void setOffset(float offset) {
		this.offset = offset;
	}

	void setOwner(Entity owner) {
		this.owner = owner;
	}

	void setPull(float pull) {
		this.pull = pull;
	}

	void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	@Override
	public void update() {
		float dist = (float) Math.sqrt(Math.pow(getOwner().x - getAnchor().x, 2) + Math.pow(getOwner().y - getAnchor().y, 2));

		if (dist <= getThreshold()) {
			dx = 0;
			dy = 0;
			setTerminated(true);
			return;
		}

		theta = (float) ((float) Math.atan2(getOwner().y - getAnchor().y, getOwner().x - getAnchor().x) + Math.toRadians(getOffset()));
		// magnitude = 1/dist;
		float multiplier = 1;
		if (isVariable) {
			multiplier = (Math.min(100 / dist, 1));
			if (inversion) {
				multiplier = (dist / 100);
			}
		}

		dx = (float) (getMagnitude() * multiplier * Math.cos(theta));
		dy = (float) (getMagnitude() * multiplier * Math.sin(theta));


	}

}
