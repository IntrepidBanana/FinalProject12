import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScreenPanel extends JPanel implements KeyListener {

	WorldMap wm = new WorldMap();
	int camSize = 720;
	Camera camera;
	IOHandler io;
	double dx;
	double dy;
	boolean keys[] = new boolean[4];
	int timePressed[] = new int[4];
	public ScreenPanel(Camera camera, IOHandler io) {
		this.camera = camera;
		this.io = io;
		addKeyListener(this);
		dx = camera.x;
		dy = camera.y;
	}
	
	
	
	
	double camSpeed = 1;
	private void update() {
		double nudge =0.5;
		if (keys[0]) {
			dy -= nudge;
		}
		if (keys[1]) {
			dy += nudge;
		}
		if (keys[2]) {
			dx -= nudge;
		}
		if (keys[3]) {
			dx += nudge;
		}
		cameraSmooth();
	}

	private void cameraSmooth(){
		double deltaX = dx-camera.x;
		double deltaY = dy-camera.y;
//		System.out.println(deltaX + " " + deltaY);
		int maxDist = 200;
		
		double moveX = deltaX*(camSpeed/100);
		double moveY = deltaY*(camSpeed/100);
		
		
		System.out.println(deltaX + " " + deltaY);
		camera.x+=moveX;
		camera.y+=moveY;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		update();
		Graphics2D g2d = (Graphics2D) g;
		for (Tile t : wm.tiles) {
			g2d.setColor(t.color);
			Shape l = new Rectangle2D.Double(t.x - camera.x, t.y - camera.y, t.size, t.size);
			g2d.draw(l);
		}

		Shape point = new Rectangle2D.Double(dx+camera.getSize()/2-camera.x, dy+camera.getSize()/2-camera.y,5.0,5.0);
		g2d.draw(point);
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			keys[0] = true;
			break;
		case KeyEvent.VK_S:
			keys[1] = true;
			break;
		case KeyEvent.VK_A:
			keys[2] = true;
			break;
		case KeyEvent.VK_D:
			keys[3] = true;
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			keys[0] = false;
			break;
		case KeyEvent.VK_S:
			keys[1] = false;
			break;
		case KeyEvent.VK_A:
			keys[2] = false;
			break;
		case KeyEvent.VK_D:
			keys[3] = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
