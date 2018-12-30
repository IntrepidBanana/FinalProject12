import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class PaintHelper {
	static Font font;
	
	public static float x(float x) {
		return WorldMap.getCamera().relX(x);
	}

	public static float y(float y) {
		return WorldMap.getCamera().relY(y);
	}

	public static Graphics2D drawCollisionBox(Graphics2D g2d, CollisionBox box) {

		float drawX, drawY;
		drawX = PaintHelper.x(box.getLeft());
		drawY = PaintHelper.y(box.getTop());
		if (box instanceof HurtBox) {
			g2d.setColor(Color.red);
		}
		if (box instanceof HitBox) {
			g2d.setColor(Color.BLACK);
		}
		Shape s = new Rectangle2D.Float(drawX, drawY, box.wid, box.len);
		g2d.draw(s);
		return g2d;
	}

	public static void initFont() {
		try {
		    //create the font to use. Specify the size!
			File[] f = new File("./Press_Start_2P").listFiles();
			System.out.println(Arrays.toString(f));
		    font = Font.createFont(Font.TRUETYPE_FONT, new File(".\\Press_Start_2P\\VT323-Regular.ttf")).deriveFont(24f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(font);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
	}
	
	
}
