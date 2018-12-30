import java.awt.Graphics2D;

public abstract class MenuObject {
	float x = 0;
	float y = 0;
	float length = 0;
	float width = 0;

	float xPadding = 0;
	float yPadding = 0;
	
	abstract Graphics2D draw(Graphics2D g2d);
	
}
