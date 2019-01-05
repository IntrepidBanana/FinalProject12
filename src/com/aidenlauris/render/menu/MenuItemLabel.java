package com.aidenlauris.render.menu;

import com.aidenlauris.items.Item;

public class MenuItemLabel extends MenuLabel {
	private Item item = null;
	
	@Override
	public String getLabel() {
		String s = "null 0";
		if(getItem() != null) {
			s = getItem().toString();
			s += " " + getItem().getCount();
		}
		return s;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}


}
