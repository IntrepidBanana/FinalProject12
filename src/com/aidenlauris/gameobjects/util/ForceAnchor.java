package com.aidenlauris.gameobjects.util;

import com.aidenlauris.gameobjects.GameObject;

public class ForceAnchor extends Force {

	private GameObject owner;
	private GameObject anchor;
	private float modifier = 0;
	private float offset = 0;
	private float threshold = 1f;
	private boolean inversion = false;
	private boolean isVariable = true;

	public ForceAnchor(float magnitude, GameObject owner, GameObject anchor, float threshold) {
		super(-magnitude, (float) Math.atan2(anchor.y - owner.y, anchor.x - owner.x));
		this.setModifier(modifier);
		this.setOwner(owner);
		this.setAnchor(anchor);
		this.setThreshold(threshold);
	}

	GameObject getAnchor() {
		return anchor;
	}

	public float getOffset() {
		return offset;
	}

	GameObject getOwner() {
		return owner;
	}

	float getModifier() {
		return modifier;
	}

	float getThreshold() {
		return threshold;
	}

	public void hasVariableSpeed(boolean b) {
		isVariable = b;
	}

	void setAnchor(GameObject anchor2) {
		this.anchor = anchor2;
	}

	public void setInversion(boolean isInverted) {
		inversion = isInverted;
	}

	public void setOffset(float offset) {
		this.offset = offset;
	}

	void setOwner(GameObject owner2) {
		this.owner = owner2;
	}

	void setModifier(float pull) {
		this.modifier = pull;
	}

	void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	@Override
	public void update() {
		if (getLifeSpan() != Integer.MAX_VALUE && getLifeSpan() <= 0) {
			setTerminated(true);
			dx = 0;
			dy = 0;
			return;
		}
		setLifeSpan(getLifeSpan() - 1);
		float dist = (float) Math
				.sqrt(Math.pow(getOwner().x - getAnchor().x, 2) + Math.pow(getOwner().y - getAnchor().y, 2));

		if (dist <= getThreshold()) {
			dx = 0;
			dy = 0;
			setTerminated(true);
			return;
		}

		setTheta((float) ((float) Math.atan2(getOwner().y - getAnchor().y, getOwner().x - getAnchor().x)
				+ Math.toRadians(getOffset())));
		// magnitude = 1/dist;
		float multiplier = 1;
		if (isVariable) {
			multiplier = (float) (Math.pow(0.997, dist) + 0);
			if (inversion) {
				multiplier = (dist / 100);
			}
		}
		
		setMagnitude(getMagnitude() * (1 - getReduction()));
		dx = (float) Math.min((getMagnitude() * multiplier * Math.cos(getTheta())), 20f);
		dy = (float) Math.min((getMagnitude() * multiplier * Math.sin(getTheta())), 20f);
//		if (Math.hypot(dx, dy) < 0.2 && isVariable) {
//			dx = 0;
//			dy = 0;
//		}

	}

}
