package com.aidenlauris.render.util;
import java.util.Comparator;

import com.aidenlauris.gameobjects.GameObject;

public class DrawCompare implements Comparator<GameObject> {

	@Override
	public int compare(GameObject a, GameObject b) {
		if(a.z == b.z) {
			if( a.y > b.y) {
				return 1;
			}
			else {
				return -1;
			}
		}
		else {
			return a.z - b.z;
		}
	}

}
