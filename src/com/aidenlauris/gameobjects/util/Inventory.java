package com.aidenlauris.gameobjects.util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.items.Item;
import com.aidenlauris.render.menu.Menu;
import com.aidenlauris.render.menu.MenuItemLabel;
import com.aidenlauris.render.menu.MenuLabel;
import com.aidenlauris.render.menu.MenuPointer;

public class Inventory {

	public ArrayList<Item> items = new ArrayList<>();
	int selectedItem = 0;
	String inventoryTitle = "Contents";
	GameObject pointer = null;

	public Inventory() {
		items = new ArrayList<Item>();
	}

	int indexOf(String item, int start) {
		for (int i = start; i < items.size(); i++) {
			Item inventoryItem = items.get(i);
			if (inventoryItem.item().equals(item)) {
				return i;
			}
		}
		return -1;
	}

	public int indexOf(String item) {
		return indexOf(item, 0);
	}

	int indexOfEmpty() {

		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if (item == null) {
				return i;
			}
		}
		return -1;
	}

	public boolean addItem(Item item) {
		int itemCount = item.getCount();
		if (indexOf(item.item()) != -1) {
			int start = indexOf(item.item(), 0);
			Item existingItem = items.get(start);
			while (start != -1) {
				existingItem = items.get(start);
				int leftoverSize = existingItem.getStackSize() - existingItem.getCount();
				if (leftoverSize > 0) {
					if (itemCount >= leftoverSize) {
						itemCount -= leftoverSize;
						existingItem.addToCount(leftoverSize);
					} else {
						existingItem.addToCount(itemCount);
						return true;
					}
				}

				start = indexOf(item.item(), start+1);
			}
		}
		if (itemCount > 0) {
			item.setCount(itemCount);
			items.add(item);
		}

		return true;

	}

	public Item getItem(int index) {
		return items.get(index);
	}

	public void nextItem() {
		selectedItem++;
		if (selectedItem >= items.size()) {
			selectedItem = 0;
		}
	}

	public void previousItem() {
		selectedItem--;
		System.out.println(selectedItem);
		if (selectedItem < 0) {
			selectedItem = items.size() - 1;
		}
	}

	public Item getItem() {
		return items.get(selectedItem);
	}

	public void removeItem() {
		items.remove(selectedItem);
		nextItem();
	}

	public void moveItem(Inventory anotherInventory) {
		if (items.size() == 0) {
			return;
		}
		anotherInventory.addItem(getItem());
		removeItem();
	}

	public Menu getMenu(double xPosition) {
		return getMenu(xPosition, null);
	}

	public Menu getMenu(double xPosition, GameObject point) {
		Menu menu = new Menu();

		Iterator<Item> i = items.iterator();
		int numLabel = 0;
		menu.length = 24 + 24 * items.size();
		menu.y = (WorldMap.camy - menu.length) / 2;
		menu.x = (float) xPosition;

		MenuLabel title = new MenuLabel();
		title.setLabel(inventoryTitle);
		title.x = 16;
		pointer = point;
		menu.add(title);

		while (i.hasNext()) {
			Item item = i.next();

			if (item.getCount() <= 0) {
				i.remove();
				if (selectedItem == numLabel) {
					nextItem();
				}
				continue;

			}

			MenuItemLabel label = new MenuItemLabel();
			label.setItem(item);

			label.x = 16;
			label.y = 24 + 24 * numLabel;
			label.length = 24;
			if (numLabel == selectedItem) {
				label.selected = true;
			} else {
				label.selected = false;
			}

			menu.add(label);
			numLabel++;
		}
		if (pointer != null) {
			MenuPointer arrow = new MenuPointer(pointer);
			arrow.y = -8;
			menu.add(arrow);
		}

		return menu;
	}

}
