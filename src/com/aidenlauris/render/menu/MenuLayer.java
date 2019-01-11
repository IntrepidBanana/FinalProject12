package com.aidenlauris.render.menu;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class MenuLayer {
	ArrayList<MenuObject> components = new ArrayList<>();

	public void add(MenuObject e) {
		if (!components.contains(e)) {
			components.add(e);
		}
		System.out.println(components.size());
	}

	public Graphics2D draw(Graphics2D g2d) {
		for (int i = 0; i < components.size(); i++) {
			MenuObject c = components.get(i);
			if (c == null) {
				i--;
				components.remove(i);
				continue;
			}

			g2d = c.draw(g2d);

		}
		return g2d;
	}
}
