package com.aidenlauris.gameobjects;

import com.aidenlauris.game.IOHandler;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.game.util.Mouse;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;

public class Camera extends Entity {

	// x and y are the centers
	float mouseOffsetX = 0;
	float mouseOffsetY = 0;
	float destX = 0;
	float destY = 0;
	int sizeX = 720;
	int sizeY = 720;
	Player player = Player.getPlayer();

	public Camera() {
		super(0, 0, 1f, 1);
		this.sizeX = WorldMap.camx;
		this.sizeY = WorldMap.camy;
	}

	public int getSize() {
		return sizeX;
	}

	public int getRadiusX() {
		return sizeX / 2;
	}

	public int getRadiusY() {
		return sizeY / 2;
	}

	@Override
	public void update() {
		mouseOffsetX = Mouse.planeX();
		mouseOffsetY = Mouse.planeY();
		destX = (Mouse.realX() + player.x) / 2;
		destY = (Mouse.realY() + player.y) / 2;
		moveTo(player.x + mouseOffsetX/5, player.y + mouseOffsetY/5);
		 tickUpdate();
	}

	public float camX() {
		return x - getRadiusX();
		// return player.x-getRadiusX() + mouseOffsetX/5;
		// return x - getRadius() + (mouseOffsetX / 20);
	}

	public float camY() {
		return y - getRadiusY();
		// return player.y-getRadiusY() + mouseOffsetY/5;
		// return y - getRadius() + (mouseOffsetY / 20);
	}

	public float relX(double d) {
		return (float) (d - camX());
	}

	public float relY(double y) {
		return (float) (y - camY());
	}

	private void moveTo(float dx, float dy) {
		float distToX = dx - this.x;
		float distToY = dy - this.y;
		this.x += distToX*0.2 ;
		this.y += distToY*0.2;

	}

	public void cameraShake(double theta, float angle, float power) {

		angle = (float) Math.toRadians(Math.random() * angle * 2 - angle);
		Force f = new Force(power*1f, (float) (theta + Math.PI + angle));
		f.setReduction(0.1f);
		getForceSet().addForce(f);

	}

	public void cameraShake(float power, int spawns) {
		ForceAnchor f = new ForceAnchor(5f, this, player, -1f);
		f.setId("cameraAnchor");
		getForceSet().addForce(f);
		for (int i = 0; i < spawns; i++) {
			Force a, b;
			a = new Force(power, (float) (Math.toRadians(Math.random() * 360)));
			b = new Force(power, (float) (Math.toRadians(Math.random() * 360)));

			a.setLifeSpan(30);
			b.setLifeSpan(30);

			getForceSet().addForce(a, 10 * i);
			getForceSet().addForce(b, 10 * i);
		}

		getForceSet().removeForce("cameraAnchor");

	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		// TODO Auto-generated method stub

	}

}
