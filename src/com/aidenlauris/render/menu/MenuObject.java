package com.aidenlauris.render.menu;
import java.awt.Graphics2D;

public abstract class MenuObject {
	public float x = 0;
	public float y = 0;
	public float length = 0;
	public float width = 0;

	public float xPadding = 0;
	public float yPadding = 0;
	
	public abstract Graphics2D draw(Graphics2D g2d);
	
}