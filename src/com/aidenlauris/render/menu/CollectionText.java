package com.aidenlauris.render.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.Time;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.render.PaintHelper;

public class CollectionText extends MenuObject {

	String s = "";
	int move = 0;
	float length = 180f;
	long alert = Time.alert((long) length);
	float reduce = 1 / length;

	public CollectionText(String s) {
		this.s = s;
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		int wid = g2d.getFontMetrics().stringWidth(s);
		int pad = 4;

		float dx = PaintHelper.x(Player.getPlayer().x);
		float dy = PaintHelper.y(Player.getPlayer().y - move);
		move++;

		Shape rect = new Rectangle2D.Float(dx - wid / 2 - pad, dy - 56, wid + 2 * pad, 32);

		g2d.setColor(new Color(0, 0, 0, Math.max(1f - reduce, 0)));
//		g2d.fill(rect);

		g2d.setFont(PaintHelper.font);
		g2d.setColor(new Color(1, 1, 1, Math.max(1f - reduce, 0)));
		g2d.drawString(s, dx - wid / 2, dy - 32);

		reduce += (1 / length);
		if (Time.alertPassed(alert)) {
			remove = true;
		}

		return g2d;
	}

}
