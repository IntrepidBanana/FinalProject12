import java.awt.event.KeyEvent;

public class InteractableBox extends Entity implements Interactable {

	boolean popupCreated = false;
	PopIcon icon = new PopIcon(x-12, y-48);
	private boolean menuCreated = false;

	InteractableBox(float x, float y) {
		super(x, y);
		addCollisionBox(new HitBox(this, 32, 32, true));
		
	}


	@Override
	public void interactByProximity() {
		Player player = Player.getPlayer();
		if(distToPlayer() < 128) {
			player.interactables.add(this);
		}
		else {
			player.interactables.remove(this);
		}

	}

	@Override
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		interactByProximity();
		if(popupCreated && !menuCreated  ) {
			if(IOHandler.isKeyPressed(KeyEvent.VK_E)) {
				menuCreated = true;
			}
		}
		
		
	}


	
	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

}
