package com.aidenlauris.render.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.util.Inventory;
import com.aidenlauris.items.Gun;
import com.aidenlauris.render.PaintHelper;

/**
 * @author Aiden & Lauris
 * Jan 22, 2019
 * 
 * Health bar and and weapon display for the game.
 */
public class HealthBar extends MenuObject {

	
	// health stats to display
	private int health = 0;
	private float maxHealth = 100;

	

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		
		//start position of menu
		x = 64;
		y = 32;

		//update stats
		Player p = Player.getPlayer();
		health = p.health;
		maxHealth = p.maxHealth;

		
		//padding
		int wPad = 8;
		int hPad = 8;
		int pad = 6;

		
		//total width
		int maxWid = 216;
		int h = 38;
		
		//width changes based on health
		int w = (int) (maxWid * (health / maxHealth));

		
		//back is background
		Shape back = new Rectangle2D.Double(x, y - pad, maxWid, h + pad);
		
		//front is variable healthbar
		Shape front = new Rectangle2D.Double(x + wPad, y + hPad, w - 2 * wPad, h - 2 * hPad);

		//draw black background
		g2d.setColor(Color.black);
		g2d.fill(back);

		//draw red health bar
		g2d.setColor(Color.red);
		g2d.fill(front);

		//draw new border for health bar
		Stroke old = g2d.getStroke();
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.white);

		g2d.draw(new Rectangle2D.Double(x + wPad, y + hPad - pad, maxWid - 2 * wPad, h - 2 * hPad + pad));
		g2d.setStroke(old);

		
		//create health font
		g2d.setColor(Color.WHITE);
		g2d.setFont(PaintHelper.font);
		g2d.drawString(health + " / " + (int) maxHealth, x + wPad + 4, y + h / 2 + 7);

		
		//INVENTORY STUFF
		
		//gets the inventory of the player
		Inventory inv = p.getInventory();

		
		//display the guns with error checking
		Gun gun1 = inv.getGun();
		int ammo1 = 0;
		if (gun1 != null) {
			ammo1 = inv.getAmmoCount(gun1);
		}
		Gun gun2 = inv.getGuns()[1];
		int ammo2 = 0;
		if (gun2 != null) {
			ammo2 = inv.getAmmoCount(gun2);
		}
		
		//create a string based on gun
		String gunString = gun1 + " " + ammo1 + " " + gun2 + " " + ammo2;

		
		//creates rectangle with a centered alignent of the string
		int strWidth = g2d.getFontMetrics().stringWidth(gunString);
		Shape textBack = new Rectangle2D.Float(72-wPad, 64, strWidth +2*wPad, 32);

		
		//black text background
		g2d.setColor(Color.black);
		g2d.fill(textBack);

		//actual string
		g2d.setColor(Color.WHITE);
		g2d.drawString(gunString, 72, 86);
		return g2d;
	}

}
