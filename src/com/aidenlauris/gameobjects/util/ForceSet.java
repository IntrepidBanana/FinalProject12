package com.aidenlauris.gameobjects.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.aidenlauris.game.util.Time;

/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 * 
 * collection of forces, used to figure out net forces of an object.
 * 
 */
public class ForceSet {

	
	//all forces and forces to be placed in the future
	private ArrayList<Force> forces = new ArrayList<>();
	private Map<Force, Long> forceQueue = new HashMap<>();
	
	//whether the object is either immovable or movable.
	private boolean movable = true;

	/**
	 * @return all forces in force set
	 */
	public ArrayList<Force> getForces() {
		return forces;
	}

	/**
	 * updates all forces and adds necessary queued up forces
	 */
	public void update() {

		// QUEUEING OF FORCES OCCURS HERE
		
		Iterator<Force> fq = forceQueue.keySet().iterator();
		while(fq.hasNext()) {
			Force f = fq.next();
			long timeLeft = forceQueue.get(f);

			if (Time.alertPassed(timeLeft)) {
				forces.add(f);
				fq.remove();
			}
		}

		// UPDATING FORCES HAPPENS HERE
		Iterator<Force> i = forces.iterator();
		while (i.hasNext()) {

			Force next = i.next();
			if (next.isTerminated()) {
				i.remove();
				removeForce(next.getId());
				continue;
			}
			next.update();
		}

	}

	/**
	 * the sum of all delta x's
	 * @return the net change in x
	 */
	public float getDX() {
		float x = 0;
		for (Force f : forces) {
			x += f.getDx();
		}
		return x;
	}

	/**
	 * the sum of all delta y's
	 * @return the net change in y
	 */
	public float getDY() {
		float y = 0;
		for (Force f : forces) {
			y += f.getDy();
		}
		return y;
	}

	/**
	 * sum of all magnitudes
	 * @return the net magnitude of all forces
	 */
	public float getNetMagnitude() {
		return (float) Math.sqrt(Math.pow(getDX(), 2) + Math.pow(getDY(), 2));
	}


	/**
	 * gets sum of all theta
	 * @return net theta
	 */
	public float getNetTheta() {
		return (float) Math.atan2(getDY(), getDX());
	}

	/**
	 * get the force with a certain id
	 * @param id id of force
	 * @return force with id
	 */
	public Force getForce(String id) {
		
		//search list for force with said id
		for (Force f : forces) {

			if (f.getId().equals(id)) {

				return f;
			}
		}
		return null;
	}

	/**
	 * remove force with a set id
	 * @param id id of force
	 * @return true if removed successfully
	 */
	public boolean removeForce(String id) {
		Iterator<Force> i = forces.iterator();
		while (i.hasNext()) {
			Force f = i.next();

			if (f.getId().equals(id)) {
				i.remove();
				return true;
			}
		}
		return false;
	}

	/**
	 * removes the ability to invoke a force on this force set
	 */
	public void setImmovable() {
		movable = false;
	}

	/**
	 * adds a force to this set
	 * @param f force to add
	 */
	public void addForce(Force f) {
		if (movable) {
			forces.add(f);
		}
	}

	/**
	 * adds a force to this set after a certain amount of ticks
	 * @param f force to add
	 * @param ticks in how many ticks the force will be added
	 */
	public void addForce(Force f, int ticks) {
		forceQueue.put(f, Time.alert(ticks));
	}
}
