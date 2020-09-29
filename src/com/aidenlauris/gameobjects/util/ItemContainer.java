package com.aidenlauris.gameobjects.util;



/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 * 
 * interface for any game object that has an inventory. MOSTLY LEGACY USE
 */
public interface ItemContainer {
	
	/**
	 * gets the inventory of the container
	 * @return inventory
	 */
	public Inventory getInventory();
}
