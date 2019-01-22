package com.aidenlauris.gameobjects.util;

import com.aidenlauris.game.util.Time;

/**
 * @author Lauris & Aiden 
 * Jan 21, 2019
 * 
 *         Force is a class that handles the change in coordinate
 *         position of an object. Forces have a direction, magnitude 
 *         and rate of reduction
 */
/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 */
/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 */
/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 */
/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 */
public class Force {
	
	//dx, dy is the change in x and y position per update
	protected float dx = 0;
	protected float dy = 0;
	
	//angle in radians of direction
	private float theta = 0;
	
	//rate of reduction for this force.
	//ranges from 0-1f
	//represent the percent this force decreases per update
	//e.g. 0.2f means the force will reduce by 20% each update
	private float reduction = 0f;
	
	//strength of force
	//represents the pixel change per update
	private float magnitude = 0;
	
	//checks to see whether or not this force should be terminated or not
	private boolean terminated = false;
	
	//life span of force
	//default: 15 seconds
	protected long lifeSpan = Time.alert(15 * 60);
	
	//if false, this force wont go away unless done manually
	protected boolean hasTimeLimit = true;
	
	//name of this force
	private String id = this.toString();

	/**
	 * initiate this force with an angle and strength
	 * @param magnitude strength of force
	 * @param theta angle of force
	 */
	public Force(float magnitude, float theta) {
		
		//if negative, flip the force around
		//honestly this just works for some reason
		if (magnitude < 0) {
			theta += Math.PI;
			magnitude *= -1;
		}
		
		//set values
		this.setMagnitude(magnitude);
		this.setTheta(theta);
		dx = (float) (magnitude * Math.cos(theta));
		dy = (float) (magnitude * Math.sin(theta));
	}

	/**
	 * initiate this force with a strength and figure out the angle by the ratio of 2 lengths
	 * @param magnitude strength
	 * @param x horizontal length
	 * @param y vertical length
	 */
	public Force(float magnitude, float x, float y) {
		this(magnitude, (float) Math.atan2(y, x));
	}

	/**
	 * update all force information. this include all magnitude and reduction changes
	 */
	public void update() {
		
		//if lifespan is over, terminate this force
		if (Time.alertPassed(lifeSpan) && hasTimeLimit) {
			setTerminated(true);
			dx = 0;
			dy = 0;
			return;
		}

		//update the delta x and y values
		//reduce the magnitude by the reduction value.
		//(all forces are affected by delta time changes
		setMagnitude((float) (getMagnitude() * Math.min((1 - getReduction() * Time.delta()), 1)));
		dx = (float) ((float) (getMagnitude() * Math.cos(getTheta())) * Time.delta());
		dy = (float) ((float) (getMagnitude() * Math.sin(getTheta())) * Time.delta());

		//if the magnitude is stupidly tiny, terminate
		if (magnitude < 0.01f) {
			terminated = true;
			dx = 0;
			dy = 0;
			return;
		}

	}

	/**
	 * @return the delta x value
	 */
	public float getDx() {
		return dx;
	}

	/**
	 * set the delta x value
	 * @param dx new dx
	 */
	public void setDx(float dx) {
		this.dx = dx;
	}

	/**
	 * set the delta y value
	 * @param dy new dy
	 */
	public void setDy(float dy) {
		this.dy = dy;
	}

	/**
	 * @return the delta y value
	 */
	public float getDy() {
		return dy;
	}

	/**
	 * @return get the rate of reduction
	 */
	public float getReduction() {
		return reduction;
	}

	/**
	 * @return get the angle of this force
	 */
	public float getTheta() {
		return theta;
	}

	/**
	 * set the rate of reduction
	 * @param reduction new reduction
	 */
	public void setReduction(float reduction) {
		this.reduction = reduction;
	}

	/**
	 * check if this force is terminated
	 * @return true if terminated
	 */
	public boolean isTerminated() {
		return terminated;
	}

	/**
	 * set if this force is terminated
	 * @param terminated boolean value
	 */
	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	/**
	 * @return the id of this force
	 */
	public String getId() {
		return id;
	}

	/**
	 * sets the id of this force
	 * @param id new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return life span of force
	 */
	public long getLifeSpan() {
		return lifeSpan;
	}

	/**
	 * sets the life span of this force
	 * @param lifespan new life span in ticks
	 */
	public void setLifeSpan(long lifespan) {
		this.lifeSpan = Time.alert(lifespan);
	}

	/**
	 * gets the magnitude of the force
	 * @return magnitude
	 */
	public float getMagnitude() {
		return magnitude;
	}

	/**
	 * sets magnitude
	 * @param magnitude new magnitude
	 */
	public void setMagnitude(float magnitude) {
		this.magnitude = magnitude;
	}

	/**
	 * sets the angle of force (radians)
	 * @param theta new angle
	 */
	public void setTheta(float theta) {
		this.theta = theta;
	}

	/**
	 * checks whether this force has a time limit and can be termintated by time
	 * @return
	 */
	public boolean limitedByTime() {
		return hasTimeLimit;
	}

	/**
	 * sets if this force has a time limit
	 * @param isLimited boolean
	 */
	public void hasTimeLimit(boolean isLimited) {
		this.hasTimeLimit = isLimited;
	}
}
