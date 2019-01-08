package com.aidenlauris.gameobjects;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.game.util.Keys;
import com.aidenlauris.game.util.Mouse;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.Interactable;
import com.aidenlauris.gameobjects.util.Inventory;
import com.aidenlauris.gameobjects.util.ItemContainer;
import com.aidenlauris.items.BulletAmmo;
import com.aidenlauris.items.Pistol;
import com.aidenlauris.items.Shotgun;
import com.aidenlauris.items.ShotgunAmmo;
import com.aidenlauris.items.Sword;
import com.aidenlauris.render.PaintHelper;
import com.aidenlauris.render.menu.Menu;

public class InteractableBox extends Entity implements Interactable, ItemContainer {

	boolean popupCreated = false;
	PopIcon icon = new PopIcon(x - 12, y - 48);
	private boolean menuCreated = false;
	private int interactProximity = 128;
	private Inventory contents = new Inventory();
	private Menu menu = null;
	private boolean interacting;

	public InteractableBox(float x, float y) {
		super(x, y);
		addCollisionBox(new HitBox(this, 32, 32, true));
		contents.addItem(new Sword());
		contents.addItem(new Pistol());
		contents.addItem(new BulletAmmo(50));
	}

	public void interactByProximity() {
		Player player = Player.getPlayer();
		if (distToPlayer() < interactProximity) {
			interacting = true;
			player.addInteractable(this);
		} else {
			interacting = false;
			player.removeInteractable(this);
		}

	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		interactByProximity();
		if (menuCreated && interacting) {
			menu = contents.getMenu(WorldMap.camx / 2 + 128, this);
			if(Keys.isKeyPressed(KeyEvent.VK_E)){
				contents.moveItem(Player.getPlayer().getInventory());
			}
			
			
		} else {
			Mouse.setFocus(null);
			menu = null;
			menuCreated = false;
		}

	}

	@Override
	public void interact() {
		System.out.println("INTERACTING " + distToPlayer());
		Mouse.setFocus(this);
		menuCreated = !menuCreated;

	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		if (menu != null) {
			g2d = menu.draw(g2d);
		}
		g2d = PaintHelper.drawCollisionBox(g2d, this);
		return g2d;
	}

	@Override
	public Inventory getInventory() {
		return contents;
	}

}
