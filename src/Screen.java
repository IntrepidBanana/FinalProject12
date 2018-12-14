import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.JFrame;

public class Screen extends JFrame {

	int cameraSize = 640;
	Camera camera = new Camera(cameraSize);
	IOHandler io = new IOHandler(camera);

	ScreenPanel painter = new ScreenPanel(camera, io);

	public Screen() {
		setTitle("ScreenOverlay");
		setSize(cameraSize, cameraSize);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.BLUE);
		add(painter);
		addKeyListener(painter);
		setVisible(true);
	}

	public static void main(String[] args) {
		Screen s = new Screen();
		int i = 0;
		while (i < 10000) {
			// s.painter.camera.x += 1;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
	}
}
