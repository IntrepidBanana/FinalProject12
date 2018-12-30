import java.util.ArrayList;

public class CollisionHelper {

	public static boolean sendReply(CollisionBox a, CollisionBox b) {

		if (checkCollision(a, b)) {
			a.notifyOwner(b);
			b.notifyOwner(a);
			return true;
		}
		return false;

	}

	public static boolean checkCollision(CollisionBox a, CollisionBox b) {
		if (!(a.getLeft() > b.getRight() || a.getRight() < b.getLeft() || a.getTop() > b.getBottom()
				|| a.getBottom() < b.getTop())) {
			return true;
		}
		return false;
	}

	public static boolean collideSets(ArrayList<CollisionBox> a, ArrayList<CollisionBox> b) {

		for (int i = 0; i < a.size(); i++) {
			CollisionBox box1 = a.get(i);
			for (int j = 0; j < b.size(); j++) {
				CollisionBox box2 = b.get(j);

				if (box1.getOwner() == box2.getOwner()) {
					continue;
				}
				if (checkCollision(box1, box2)) {
					box1.notifyOwner(box2);
					box2.notifyOwner(box1);
					return true;
				}

			}
		}
		return false;
	}
}
