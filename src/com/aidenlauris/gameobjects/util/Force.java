package com.aidenlauris.gameobjects.util;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;

public class Force {
	protected float dx = 0;
	protected float dy = 0;
	private float theta = 0;
	private float reduction = 0f;
	private float magnitude = 0;
	private boolean terminated = false;
	protected long lifeSpan = Time.alert(15 * 60);
	protected boolean hasTimeLimit = true;
	private String id = this.toString();

	public Force(float magnitude, float theta) {
		if (magnitude < 0) {
			theta += Math.PI;
			magnitude *= -1;
		}
		this.setMagnitude(magnitude);
		this.setTheta(theta);
		dx = (float) (magnitude * Math.cos(theta));
		dy = (float) (magnitude * Math.sin(theta));
	}

	public Force(float magnitude, float x, float y) {
		this(magnitude, (float) Math.atan2(y, x));
	}

	public void update() {
		if (Time.alertPassed(lifeSpan) && hasTimeLimit) {
			setTerminated(true);
			dx = 0;
			dy = 0;
			return;
		}

		setMagnitude((float) (getMagnitude() * Math.min((1 - getReduction() * Time.delta()), 1)));
		dx = (float) ((float) (getMagnitude() * Math.cos(getTheta())) * Time.delta());
		dy = (float) ((float) (getMagnitude() * Math.sin(getTheta())) * Time.delta());

		System.out.println(magnitude);
		if (magnitude < 0.01f) {
			terminated = true;
			dx = 0;
			dy = 0;
			return;
		}

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

	public long getLifeSpan() {
		return lifeSpan;
	}

	public void setLifeSpan(long l) {
		this.lifeSpan = Time.alert(l);
	}

	public float getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(float magnitude) {
		this.magnitude = magnitude;
	}

	public void setTheta(float theta) {
		this.theta = theta;
	}

	public boolean hasTimeLimit() {
		return hasTimeLimit;
	}

	public void hasTimeLimit(boolean hasTimeLimit) {
		this.hasTimeLimit = hasTimeLimit;
	}
}
