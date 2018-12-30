import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Inventory {

	ArrayList<Item> items = new ArrayList<>();
	int selectedItem = 0;

	public Inventory() {
		items = new ArrayList<Item>();
	}

	int indexOf(String item) {
		for (int i = 0; i < items.size(); i++) {
			Item inventoryItem = items.get(i);
			if (inventoryItem.item().equals(item)) {
				return i;
			}
		}
		return -1;
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
			items.get(indexOf(item.item())).addToCount(item.getCount());
			return true;
		} else {
			items.add(item);
			return true;
		}

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

	public Item getItem() {
		return items.get(selectedItem);
	}

	public Menu getMenu() {
		Menu menu = new Menu();

		Iterator<Item> i = items.iterator();
		int numLabel = 0;
		menu.length = 24 + 24 * items.size();
		menu.y = (WorldMap.camy - menu.length) / 2;
		while (i.hasNext()) {
			Item item = i.next();

			if (item.getCount() <= 0) {
				i.remove();
				if(selectedItem == numLabel) {
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

		return menu;
	}

}
