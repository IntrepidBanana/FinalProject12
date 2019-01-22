package com.aidenlauris.game.util;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lauris & Aiden
 * 1/20/19
 * Logic behind key pressed and figuring out which key is being used
 *
 */
public class Keys {
	
	//map for current key state for each key
	public static Map<Integer, KeyType> keySet = new HashMap<>();

	/**
	 * Adds some default keys to speed things up
	 */
	public Keys() {
		keySet.put(KeyEvent.VK_W, KeyType.RELEASED);
		keySet.put(KeyEvent.VK_A, KeyType.RELEASED);
		keySet.put(KeyEvent.VK_S, KeyType.RELEASED);
		keySet.put(KeyEvent.VK_D, KeyType.RELEASED);
		keySet.put(KeyEvent.VK_SPACE, KeyType.RELEASED);
		keySet.put(KeyEvent.VK_E, KeyType.RELEASED);
	}

	/**
	 * Checks if key is held
	 * @param key to check
	 * @return true if key held, false if not
	 */
	public static boolean isKeyHeld(int key) {

		//if key was never used dont check
		if (keySet.containsKey(key)) {
			
			
			// if key held of key pressed
			if (keySet.get(key) == KeyType.PRESSED || keySet.get(key) == KeyType.HELD) {
				return true;
			}

		}
		return false;

	}

	/**
	 * checks if key is pressed. i.e. will only return one signal not a continuous signal
	 * @param key to check
	 * @return boolean: true if pressed
	 */
	public static boolean isKeyPressed(int key) {
		
		if (keySet.containsKey(key)) {
			
			//checks if key is pressed, but not held.
			//replace with a not used keys state
			if (keySet.get(key) == KeyType.PRESSED) {
				keySet.put(key, KeyType.NOT_USED);
				return true;
			}
		}
		return false;

	}
}
