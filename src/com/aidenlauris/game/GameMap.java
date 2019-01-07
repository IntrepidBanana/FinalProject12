package com.aidenlauris.game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.aidenlauris.game.util.XY;
import com.aidenlauris.gameobjects.util.CollisionHelper;
import com.aidenlauris.gameobjects.util.Direction;
import com.aidenlauris.gameobjects.util.GameObject;

public class GameMap {
	
	
	private Map<XY, ArrayList<GameObject>> map = new HashMap<>();
	private ArrayList<GameObject> uniqueObjects = new ArrayList<>();

	private int chunkSize = 500;

	public GameMap(int chunkSize) {

		this.chunkSize = chunkSize;

	}

	public void add(GameObject obj) {

		for (XY xy : locateChunkOfObject(obj)) {
			if (!getMap().containsKey(xy)) {
				getMap().put(xy, new ArrayList<GameObject>());
			}
			getMap().get(xy).add(obj);
			if (!getUniqueObjects().contains(obj)) {
				getUniqueObjects().add(obj);
			}
		}

	}

	public ArrayList<XY> locateChunkOfObject(GameObject obj) {
		double left = Math.floor(CollisionHelper.get(obj, Direction.LEFT) / chunkSize);
		double top = Math.floor(CollisionHelper.get(obj, Direction.TOP) / chunkSize);
		double right = Math.floor(CollisionHelper.get(obj, Direction.RIGHT) / chunkSize);
		double bottom = Math.floor(CollisionHelper.get(obj, Direction.BOTTOM) / chunkSize);

		ArrayList<XY> list = new ArrayList<>();

		for (int x = (int) left; x <= right; x++) {
			for (int y = (int) top; y <= bottom; y++) {
				list.add(new XY(x, y));
			}
		}
		return list;
	}

	public void clear() {
		for (ArrayList<GameObject> obj : getMap().values()) {
			if (obj != null) {
				obj.clear();
			}
		}
	}

	public Collection<ArrayList<GameObject>> getAllChunks() {
		return getMap().values();
	}

	public ArrayList<GameObject> getUniqueObjects() {

		return uniqueObjects;
	}

	public void clearUnique() {
		uniqueObjects.clear();
	}

	
	public Set<XY> getChunkCoords() {
		return getMap().keySet();
	}

	public int chunkSize() {
		// TODO Auto-generated method stub
		return chunkSize;
	}

	public Map<XY, ArrayList<GameObject>> getMap() {
		return map;
	}

	public void setMap(Map<XY, ArrayList<GameObject>> map) {
		this.map = map;
	}
}
