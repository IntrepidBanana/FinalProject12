package com.aidenlauris.render.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.gameobjects.Player;
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

		Shape back = new Rectangle2D.Double(x, y, maxWid, h);
		Shape front = new Rectangle2D.Double(x + wPad, y + hPad, w - 2 * wPad, h - 2 * hPad);

		g2d.setColor(Color.black);
		g2d.fill(back);

		g2d.setColor(Color.red);
		g2d.fill(front);

		Stroke old = g2d.getStroke();
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.white);
		g2d.draw(front);
		g2d.setStroke(old);

		g2d.setColor(Color.WHITE);
		g2d.setFont(PaintHelper.font);
		g2d.drawString(health + " / " + (int) maxHealth, x + wPad + 4, y + h / 2 + 7);
		
		g2d.drawString(p.getInventory().items.toString(), 72, 86);

		return g2d;
	}

}
