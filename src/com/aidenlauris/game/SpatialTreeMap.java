package com.aidenlauris.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.aidenlauris.game.util.XY;
import com.aidenlauris.gameobjects.util.CollisionHelper;
import com.aidenlauris.gameobjects.util.Direction;
import com.aidenlauris.gameobjects.util.GameObject;

/**
 * @author Lauris & Aiden Jan 21, 2019
 * 
 *         Handles all spatial partitioning of gameobjects
 */
public class SpatialTreeMap {

	// map of xy coordinates and a corresponding list of objects
	private Map<XY, ArrayList<GameObject>> map = new HashMap<>();

	// list of all unique objects
	private ArrayList<GameObject> uniqueObjects = new ArrayList<>();

	// size of each partition in pixels
	private int chunkSize = 500;

	/**
	 * creates a spatial map with set chunk size
	 * 
	 * @param chunkSize
	 *            size of partition
	 */
	public SpatialTreeMap(int chunkSize) {

		this.chunkSize = chunkSize;

	}

	/**
	 * adds an object to spatial tree
	 * 
	 * @param obj
	 *            object to add
	 */
	public void add(GameObject obj) {
		if (!getUniqueObjects().contains(obj)) {
			getUniqueObjects().add(obj);
		}
		// if it doesnt have a collision box, no need to care about placing it. add to
		// unique object list
		if (obj.getCollisionBoxes().size() == 0) {
			return;
		}

		// adds the obj to the appropriate locations in the map. also adds to unique
		// object list
		for (XY xy : locateChunkOfObject(obj)) {
			if (!getMap().containsKey(xy)) {
				getMap().put(xy, new ArrayList<GameObject>());
			}
			getMap().get(xy).add(obj);

		}
		
	}

	/**
	 * locates all chunks the object inherits
	 * 
	 * @param obj
	 *            object to check
	 * @return list of all chunk coords
	 */
	public ArrayList<XY> locateChunkOfObject(GameObject obj) {

		// gets the outer bounding box of obj
		double left = Math.floor(CollisionHelper.getColliderBound(obj, Direction.LEFT) / chunkSize);
		double top = Math.floor(CollisionHelper.getColliderBound(obj, Direction.TOP) / chunkSize);
		double right = Math.floor(CollisionHelper.getColliderBound(obj, Direction.RIGHT) / chunkSize);
		double bottom = Math.floor(CollisionHelper.getColliderBound(obj, Direction.BOTTOM) / chunkSize);

		ArrayList<XY> list = new ArrayList<>();

		// adds the intersecting chunks to list
		for (int x = (int) left; x <= right; x++) {
			for (int y = (int) top; y <= bottom; y++) {
				list.add(new XY(x, y));
			}
		}
		return list;
	}

	/**
	 * remove all objects from each chunk
	 */
	public void clear() {
		for (ArrayList<GameObject> obj : getMap().values()) {
			if (obj != null) {
				obj.clear();
			}
		}
	}

	/**
	 * get all chunks
	 * 
	 * @return get collection of all lists of game objects
	 */
	public Collection<ArrayList<GameObject>> getAllChunks() {
		return getMap().values();
	}

	/**
	 * get all unique objects in tree
	 * 
	 * @return list of all unique objects
	 */
	public ArrayList<GameObject> getUniqueObjects() {

		return uniqueObjects;
	}

	/**
	 * clear unique objects
	 */
	public void clearUnique() {
		uniqueObjects.clear();
	}

	/**
	 * get list of all chunk coordinates
	 * 
	 * @return list coordinates
	 */
	public Set<XY> getChunkCoords() {
		return getMap().keySet();
	}

	/**
	 * returns chunksize
	 * 
	 * @return chunksize
	 */
	public int chunkSize() {
		return chunkSize;
	}

	/**
	 * gets the entire map of chunks and game objects
	 * 
	 * @return
	 */
	public Map<XY, ArrayList<GameObject>> getMap() {
		return map;
	}

}
