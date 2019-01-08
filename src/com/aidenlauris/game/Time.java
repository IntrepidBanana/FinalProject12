package com.aidenlauris.game;

public class Time {
	private static long globalTick = 0;
	private static double subTick = 0;
	private static double deltaTime = 1.0f;

	private static void validate() {
		if (subTick >= 1) {
			globalTick += Math.floor(subTick);
			subTick -= Math.floor(subTick);
		}
	}

	public static void nextTick() {
		subTick += deltaTime;
		validate();
	}

	public static long global() {
		return globalTick;
	}

	public static long alert(long l) {
		return globalTick + l;
	}

	public static void setDelta(double t) {
		deltaTime = Math.max(t, 0.1);
	}

	public static double delta() {
		return deltaTime;
	}

	public static boolean alertPassed(long alert) {
		if (globalTick >= alert) {
			return true;
		}
		return false;
	}

}
