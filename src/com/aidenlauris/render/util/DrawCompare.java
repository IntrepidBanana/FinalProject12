package com.aidenlauris.render.util;
import java.util.Comparator;

import com.aidenlauris.gameobjects.util.GameObject;

public class DrawCompare implements Comparator<GameObject> {

	@Override
	public int compare(GameObject a, GameObject b) {
//		System.out.println(a +" " + b);
		
		System.out.println(a.z + "  " + a.y);
		System.out.println(b.z + "  " + b.y);
		System.out.println();
		if(a.z == b.z) {
			if( a.y > b.y) {
				return 1;
			}
			else {
				return -1;
			}
		}
		else {
			if( a.z > b.z) {
				return 1;
			}
			else {
				return -1;
			}
		}
	}

}
