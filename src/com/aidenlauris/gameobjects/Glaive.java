package com.aidenlauris.gameobjects;

import com.aidenlauris.gameobjects.util.HurtBox;

public class Glaive extends Projectile {

	private double rotation = Math.random() * 360;
	private Projectile owner;
	private int offset = 50;

	public Glaive(float damage, int offset, Projectile owner) {
		HurtBox box = new HurtBox(this, 12, 12, damage);
		box.addHint(this.getClass());
		addCollisionBox(box);
		health = 100;
		this.offset = offset;
		this.owner = owner;
		team = owner.team;
	}

	@Override
	public void kill() {
		super.kill();
	}

	@Override
	public void init() {
		super.init();
		getForceSet().getForces().clear();
	}

	@Override
	public void update() {
		super.update();
		
		
		double theta = Math.toRadians(rotation + getMoveSpeed());

//		x = (float) (owner.x + Math.cos(theta) * offset);
//		y = (float) (owner.y + Math.sin(theta) * offset);
		
		x= owner.x + 5;
		y = owner.y + 5;
		
		
	}

}
