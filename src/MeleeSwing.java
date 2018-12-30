
public class MeleeSwing extends Projectile {
	
	public MeleeSwing(float damage) {
		setKnockback(2f);
		addCollisionBox(new HurtBox(this, -2f, -2f, 4, 4, damage));
	}
	
	@Override
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		
		if(box.getOwner() instanceof Entity) {
			Entity owner = (Entity) box.getOwner();
			if(owner.team != Team.PLAYER) {
				owner.damage((HurtBox) myBox);
				owner.knockBack(getKnockback(), forces.getX(), forces.getY());
				owner.stun(30);
				
			}
		}

	}
}
