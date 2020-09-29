package com.aidenlauris.gameobjects.util;


import com.aidenlauris.game.util.Time;

/**
 * @author Lauris & Aiden Jan 21, 2019
 * 
 *         extends force, calculates delta x and y values based on the position
 *         of 2 objects
 */
public class ForceAnchor extends Force {

	// the objects to use in calcuations
	private GameObject owner;
	private GameObject anchor;

	private float offset = 0;
	private float threshold = -1f;
	private boolean inversion = false;
	private boolean isVariable = true;

	/**
	 * initiates force with set parameters
	 * 
	 * @param magnitude
	 *            strength of force
	 * @param owner
	 *            origin of force
	 * @param anchor
	 *            end point of force
	 * @param threshold
	 *            distance at which this anchor terminates
	 */
	public ForceAnchor(float magnitude, GameObject owner, GameObject anchor, float threshold) {
		super(magnitude, (float) ((float) Math.atan2(owner.y - anchor.y, owner.x - anchor.x)));
		this.setOwner(owner);
		this.setAnchor(anchor);
		this.setThreshold(threshold);
	}

	@Override
	public void update() {

		// validates lifespan
		if (Time.alertPassed(lifeSpan) && hasTimeLimit) {
			setTerminated(true);
			dx = 0;
			dy = 0;
			return;
		}

		// finds the distance between the anchor and owner objects
		float dist = (float) Math
				.sqrt(Math.pow(getOwner().x - getAnchor().x, 2) + Math.pow(getOwner().y - getAnchor().y, 2));

		// if distance is less than threshold, terminate
		if (dist <= getThreshold()) {
			dx = 0;
			dy = 0;
			setTerminated(true);
			return;
		}

		// find angle for the force.
		// equal to the angle between the force plus a set angle of offset

		setTheta((float) ((float) Math.atan2(getAnchor().y - getOwner().y, getAnchor().x - getOwner().x)
				+ Math.toRadians(getOffset())));

		// sets the multiplier of the force,
		// if not variable, has a constant speed
		// if inverted, slows down instead of speeding up toward the anchor
		float multiplier = 1;
		if (isVariable) {
			multiplier = (float) (Math.pow(0.99, dist) + 0);
			if (inversion) {
				multiplier = (dist / 100);
			}
		}

		// set magnitude and deltas
		setMagnitude((float) (getMagnitude() * Math.min((1 - getReduction() * Time.delta()), 1)));
		dx = (float) ((getMagnitude() * multiplier * Math.cos(getTheta())) * Time.delta());
		dy = (float) ((getMagnitude() * multiplier * Math.sin(getTheta())) * Time.delta());

	}

	/**
	 * get anchor
	 * 
	 * @return anchor
	 */
	public GameObject getAnchor() {
		return anchor;
	}

	/**
	 * return the angle of offset
	 * 
	 * @return offset
	 */
	public float getOffset() {
		return offset;
	}

	/**
	 * get owner
	 * 
	 * @return owner object
	 */
	public GameObject getOwner() {
		return owner;
	}

	/**
	 * get threshold of distance
	 * 
	 * @return threshold
	 */
	float getThreshold() {
		return threshold;
	}

	/**
	 * sets whether the anchor has variable speed
	 * 
	 * @param b
	 *            true if variable
	 */
	public void hasVariableSpeed(boolean b) {
		isVariable = b;
	}

	/**
	 * sets the object anchor
	 * 
	 * @param anchor
	 *            anchor
	 */
	public void setAnchor(GameObject anchor) {
		this.anchor = anchor;
	}

	/**
	 * if true: force will speed up as it approaches anchor; false: force will slow down
	 * @param isInverted true
	 */
	public void setInversion(boolean isInverted) {
		inversion = isInverted;
	}

	/**
	 * offset will be added to the direction of the force.
	 * e.g. if offset = 90, and angle to anchor = 0;
	 * the angle of the force will be +90 degrees, instead of 0
	 * @param offset offset value
	 */
	public void setOffset(float offset) {
		this.offset = offset;
	}

	/**
	 * sets the owner object
	 * @param owner owner
	 */
	public void setOwner(GameObject owner) {
		this.owner = owner;
	}

	/**
	 * sets the threshold distance when the force will terminate
	 * @param threshold threshold distance
	 */
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

}
