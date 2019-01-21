package com;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.render.PaintHelper;
import com.aidenlauris.render.util.SpriteManager;

public class Tile {
	int size = 32;
	BufferedImage sprite;
	float x;
	float y;

	public Tile(float x, float y) {
		this.x = x;
		this.y = y;
		 sprite = SpriteManager.getTile();
	}

	public Graphics2D draw(Graphics2D g2d) {
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		 g2d.drawImage(sprite, null, (int) drawX, (int) drawY);
		return g2d;
	}
}
