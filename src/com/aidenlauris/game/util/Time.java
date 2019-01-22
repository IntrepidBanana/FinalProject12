package com.aidenlauris.game.util;

/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 * 
 * Time class, handles global tick and delta time
 */
public class Time {
	private static long globalTick = 0;
	private static double subTick = 0;
	private static double deltaTime = 1.0f;

	/**
	 * validates time to make sure sub tick and global tick match up
	 */
	private static void validate() {
		if (subTick >= 1) {
			globalTick += Math.floor(subTick);
			subTick -= Math.floor(subTick);
		}
	}

	/**
	 * gets the next tick
	 */
	public static void nextTick() {
		subTick += deltaTime;
		validate();
	}

	/**
	 * @return the global time
	 */
	public static long global() {
		return globalTick;
	}

	/**
	 * creates a time stamp in the future based on the current time
	 * @param l ticks ahead in the future
	 * @return long time stamp
	 */
	public static long alert(long l) {
		return globalTick + l;
	}

	/**
	 * sets the delta time value
	 * @param t new delta time
	 */
	public static void setDelta(double t) {
		deltaTime = Math.max(t, 0.1);
	}

	/**
	 * @return delta time
	 */
	public static double delta() {
		return deltaTime;
	}

	/**
	 * check if an alert has passed
	 * @param alert timestamp
	 * @return true if the time has passed
	 */
	public static boolean alertPassed(long alert) {
		if (globalTick >= alert) {
			return true;
		}
		return false;
	}

}
