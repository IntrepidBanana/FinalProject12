package com.aidenlauris.game.util;

public class XY {
	public int x = 0;
	public int y = 0;

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
