package com.aidenlauris.render.menu;
import java.awt.Graphics2D;

/**
 * @author Aiden & Lauris
 * Jan 22, 2019
 * 
 * abstract menu object class that displays can be added to the screen
 */
public abstract class MenuObject {
	
	//variables
	public float x = 0;
	public float y = 0;
	
	//if true, the menulayer will try and remove it from the screen
	public boolean remove = false;

	
	
	/**
	 * Handles how this object is drawn
	 * @param g2d graphics unit
	 * @return updated graphics unit
	 */
	public abstract Graphics2D draw(Graphics2D g2d);
	
	
	
}
