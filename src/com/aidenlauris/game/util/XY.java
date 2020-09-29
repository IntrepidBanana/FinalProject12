package com.aidenlauris.game.util;

/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 * 
 * simple x,y coordinate handler
 * 
 */
public class XY {
	public int x = 0;
	public int y = 0;

	/**
	 * sets x and y value
	 * @param x
	 * @param y
	 */
	public XY(double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof XY) {
			if (((XY) obj).x == x) {
				if (((XY) obj).y == y) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		
		//overides haschode to provide fast results
		int result = 17;
		result = 31 * result + x;
		result = 31 * result + y;

		return result;
	}

	@Override
	public String toString() {
		String s = "X: " + x + " Y: " + y;
		return s;
	}
}
