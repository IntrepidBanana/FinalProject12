package com.aidenlauris.render.menu;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Menu extends MenuObject{
	String title = "";
	
	ArrayList<MenuObject> components = new ArrayList<>();
	
	public void add(MenuObject e) {
		e.x += x;
		e.y += y;
		components.add(e);
	}

	@Override
	public
	Graphics2D draw(Graphics2D g2d) {
		for(MenuObject e : components) {
			g2d = e.draw(g2d);
		}
		return g2d;
	}
	
	
	
	
	
	
	
}
