package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.util.Keys;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Interactable;
import com.aidenlauris.items.Item;
import com.aidenlauris.render.PaintHelper;

public class ItemDrop extends Entity implements Interactable {
	
	private boolean interacting;
	private Item item;

	public ItemDrop(float x, float y, Item item) {
		super(x, y);
		this.item = item;
	}

	@Override
	public void interact() {
		Player.getPlayer().getInventory().addItem(this.item);
		kill();
	}
	
	
	public void interactByProximity(){
		Player player = Player.getPlayer();
		if(distToPlayer() < 50){
			interacting = true;
			player.addInteractable(this);
		} else {
			interacting = false;
			player.removeInteractable(this);
		}
	}
	
	public void update(){
		interactByProximity();
		
		if(interacting && Keys.isKeyPressed(KeyEvent.VK_E)){
			interact();
		}
		
		
	}

	@Override
	public void collisionOccured(CollisionBox theirBox, CollisionBox myBox) {
		
	}
	
	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		Shape s = new Rectangle2D.Float(drawX - 5, drawY - 5, 10, 10);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fill(s);
		if(interacting){
			g2d.drawString(item.item(), drawX, drawY - 16);
		}
		return g2d;
	}

}
