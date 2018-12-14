import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

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
 double moveSpeed = 1;
 //TEMP THINGY TO SIMULATE MOVEMENT
 private void update() {
  double nudge = moveSpeed;
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
 }

 private void cameraSmooth(double x, double y) {
  double deltaX = x - camera.x;
  double deltaY = y - camera.y;

  double moveX = deltaX * (1 / (100-camSpeed*camSpeed));
  double moveY = deltaY * (1 / (100-camSpeed*camSpeed));
  if(Math.abs(moveX) < 0.02) {
   moveX = 0;
  }
  if(Math.abs(moveY) < 0.02) {
   moveY = 0;
  }
  double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

  
  camera.x += moveX;
  camera.y += moveY;
 }

 @Override
 protected void paintComponent(Graphics g) {

  super.paintComponent(g);
  update();
  cameraSmooth(dx, dy);
  Graphics2D g2d = (Graphics2D) g;
  for (Tile t : wm.tiles) {
   g2d.setColor(t.color);
   Shape l = new Rectangle2D.Double(t.x - camera.x, t.y - camera.y, t.size, t.size);
   g2d.draw(l);
  }

  Shape point = new Rectangle2D.Double(dx + camera.getSize() / 2 - camera.x-2.5, dy + camera.getSize() / 2 - camera.y-2.5,
    5.0, 5.0);
  g2d.draw(point);
  
  Shape dist = new Line2D.Double(dx-camera.x +camera.getSize()/2, (dy + (camera.getSize() / 2) - camera.y),
                                                   camera.getSize()/2, camera.getSize()/2);
  
  g2d.draw(dist);
                                 
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
  case KeyEvent.VK_SPACE:
   dx+= new Random().nextInt(1000)-500;
   dy+= new Random().nextInt(1000)-500;
   break;
    case KeyEvent.VK_UP:
      moveSpeed+=.5;
      break;
  case KeyEvent.VK_DOWN:
      moveSpeed = Math.max(moveSpeed-.1, 0);
      break;
  }
 }

 @Override
 public void keyTyped(KeyEvent e) {
  // TODO Auto-generated method stub

 }
}
