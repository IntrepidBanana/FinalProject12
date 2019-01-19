package com.aidenlauris.game.util;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Keys {
	public static Map<Integer, KeyType> keySet = new HashMap<>();

	public Keys() {
		keySet.put(KeyEvent.VK_W, KeyType.RELEASED);
		keySet.put(KeyEvent.VK_A, KeyType.RELEASED);
		keySet.put(KeyEvent.VK_S, KeyType.RELEASED);
		keySet.put(KeyEvent.VK_D, KeyType.RELEASED);
		keySet.put(KeyEvent.VK_SPACE, KeyType.RELEASED);
	}

	public static boolean isKeyHeld(int key) {

		if (keySet.containsKey(key)) {
			if (keySet.get(key) == KeyType.PRESSED || keySet.get(key) == KeyType.HELD) {
				return true;
			}

		}
		return false;

	}

	public static boolean isKeyPressed(int key) {
		if (keySet.containsKey(key)) {
			if (keySet.get(key) == KeyType.PRESSED) {
				keySet.put(key, KeyType.NOT_USED);
				return true;
			}
		}
		return false;

	}
}
