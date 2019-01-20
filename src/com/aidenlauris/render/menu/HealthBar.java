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

public class HealthBar extends MenuObject {

	private int health = 0;
	private float maxHealth = 8;

	public void update(Player player) {
		health = player.health;
		maxHealth = player.maxHealth;

	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		x = 64;
		y = 32;

		Player p = Player.getPlayer();

		int wPad = 8;
		int hPad = 8;

		int maxWid = 216;
		int w = (int) (maxWid * (health / maxHealth));
		int h = 38;

		int pad = 6;
		Shape back = new Rectangle2D.Double(x, y - pad, maxWid, h + pad);
		Shape front = new Rectangle2D.Double(x + wPad, y + hPad, w - 2 * wPad, h - 2 * hPad);

		g2d.setColor(Color.black);
		g2d.fill(back);

		g2d.setColor(Color.red);
		g2d.fill(front);

		Stroke old = g2d.getStroke();
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.white);

		g2d.draw(new Rectangle2D.Double(x + wPad, y + hPad - pad, maxWid - 2 * wPad, h - 2 * hPad + pad));
		g2d.setStroke(old);

		g2d.setColor(Color.WHITE);
		g2d.setFont(PaintHelper.font);
		g2d.drawString(health + " / " + (int) maxHealth, x + wPad + 4, y + h / 2 + 7);

		Inventory inv = p.getInventory();

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
		String gunString = gun1 + " " + ammo1 + " " + gun2 + " " + ammo2;


		int strWidth = g2d.getFontMetrics().stringWidth(gunString);
		Shape textBack = new Rectangle2D.Float(72-wPad, 64, strWidth +2*wPad, 32);
		g2d.setColor(Color.black);
		g2d.fill(textBack);

		g2d.setColor(Color.WHITE);
		g2d.drawString(gunString, 72, 86);
		return g2d;
	}

}
