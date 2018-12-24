import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.JFrame;

public class Screen extends JFrame{
	
	IOHandler io = new IOHandler();
	
	PainterLoop painter = new PainterLoop(io);

	
	public Screen() {
		setTitle("ScreenOverlay");
		setSize(WorldMap.camx, WorldMap.camy);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.BLUE);
		add(painter);
		addKeyListener(io);
		addMouseListener(io);
		addMouseMotionListener(io);
		setResizable(false);
		setVisible(true);	
	}
	
	public static void main(String[] args) {
//		Screen s = new Screen();
	}
}
