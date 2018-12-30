import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

public class MenuLabel extends MenuObject {

	private String label = "";
	Color color = Color.gray;
	boolean selected = false;

	@Override
	Graphics2D draw(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.setFont(PaintHelper.font);
		g2d.drawString(getLabel(), x, y);
		if (selected) {
			g2d.setColor(Color.WHITE);
			Stroke old = g2d.getStroke();
			g2d.setStroke(new BasicStroke(4));
			g2d.draw(new Rectangle2D.Float(x-12, y-length+6, getLabel().length()*14, length));
			g2d.setStroke(old);
		}
		return g2d;
	}

	public String getLabel() {
		if(label == null) {
			return "null";
		}
		return label;
	}

	public void setLabel(String label) {
		if(label == null) {
			label = "null";
		}
		this.label = label;
	}

}
