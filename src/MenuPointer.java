import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class MenuPointer extends MenuObject {

	GameObject pointingAt = null;
	float pointX = 0;
	float pointY = 0;

	public MenuPointer(float pointX, float pointY) {
		this.pointX = pointX;
		this.pointY = pointY;
	}

	public MenuPointer(GameObject e) {
		pointingAt = e;
	}

	@Override
	Graphics2D draw(Graphics2D g2d) {
		if (pointingAt != null) {
			pointX = pointingAt.x;
			pointY = pointingAt.y;
		}

		float drawX = PaintHelper.x(pointX);
		float drawY = PaintHelper.y(pointY);

		Line2D horizontal = new Line2D.Float(x, y, drawX, y);
		Line2D vertical = new Line2D.Float(pointX, y, drawX, drawY);

		g2d.setColor(Color.white);
		g2d.draw(horizontal);
		g2d.draw(vertical);

		return g2d;
	}

}
