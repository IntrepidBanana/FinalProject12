package com.aidenlauris.render.menu;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * @author Aiden & Lauris
 * Jan 22, 2019
 * 
 * menu layer drawn with final priority in rendering. this is the topmost element
 */
public class MenuLayer {
	
	//all components of this layer
	private ArrayList<MenuObject> components = new ArrayList<>();

	/**
	 * adds the appropriate menu object to the list
	 * @param m Menu object to add
	 */
	public void add(MenuObject m) {
		if (!components.contains(m)) {
			components.add(m);
		}
	}

	/**
	 * Draws all components & validates each element
	 * @param g2d graphics handler
	 * @return updated graphics handler
	 */
	public Graphics2D draw(Graphics2D g2d) {
		for (int i = 0; i < components.size(); i++) {
			MenuObject c = components.get(i);
			if (c == null || c.remove) {
				components.remove(i);
				i--;
				continue;
			}

			g2d = c.draw(g2d);

		}
		return g2d;
	}
}
