
public class CollisionHelper {

	public static boolean sendReply(CollisionBox a, CollisionBox b) {
		
		
		if (!(a.getLeft() > b.getRight() || a.getRight() < b.getLeft() || a.getTop() > b.getBottom()
				|| a.getBottom() < b.getTop())) {
			a.notifyOwner(b);
			b.notifyOwner(a);
			return true;
		}
		return false;

	}
}
