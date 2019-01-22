package com.aidenlauris.game;
import java.util.ArrayList;

import com.aidenlauris.render.RenderHandler;
import com.aidenlauris.render.Screen;

/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 * 
 * main game loop of game. handles when to draw, update game and speed of game
 */
public class GameLoop implements Runnable {
	

	private Thread t;
	private String threadName;
	long startTime;

	/**
	 * starts the thread
	 * @param name of thread
	 */
	public GameLoop(String name) {
		threadName = name;
		System.out.println("Creating " + threadName);
	}

	/**
	 * returns the difference between start time and current time.
	 * @return difference
	 */
	private long getTickCount() {
		return (System.currentTimeMillis() - startTime);
	}

	@Override
	public void run() {
		
		//starts the screen and renderhandler
		Screen s = new Screen();
		RenderHandler p = s.getPainter();
		p.repaint();

		// MODIFIED DEWITTERS GAME LOOP
		// http://www.koonsolo.com/news/dewitters-gameloop/
		//
		
		//speed of game, how much to skip, milliseconds per tick, and other values
		//
		final int TICKS_PER_SECOND = 60;
		final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
		final int MAX_FRAMETIME = 1000 / TICKS_PER_SECOND;
		final int MAX_FRAMESKIP = 10;
		long nextGameTick = getTickCount();
		int loops;
		long previousTime = System.currentTimeMillis();
		
		//FPS counter: made of average of all fps for 1 second
		ArrayList<Integer> fps = new ArrayList<>(60);
		for (int i = 0; i < 60; i++) {
			fps.add(0);
		}
		int totalFPS = 0;
		
		
		while (true) {

			//figures out how long the frame was
			long currentTime = System.currentTimeMillis();
			long frameTime = currentTime - previousTime;
			previousTime = currentTime;
			
			//Funky update mechanic to update game at 60 ticks/second
			loops = 0;
			while (getTickCount() > nextGameTick && loops < MAX_FRAMESKIP) {

				GameLogic.update();
				nextGameTick += SKIP_TICKS;
				loops++;
			}
			
			//calculate fps
			int cfps = (int) (Math.max(MAX_FRAMETIME - frameTime, 0) * 2 * 60 / MAX_FRAMETIME);
			totalFPS -= fps.get(0);
			fps.remove(0);
			fps.add(cfps);
			totalFPS += cfps;
			p.fpsTimer = totalFPS / 60;
			
			//render
			p.repaint();

			
			//sleep at variable length to keep frames at 60fps
			try {
				Thread.sleep(Math.max(MAX_FRAMETIME - frameTime, 1));
			} catch (InterruptedException e) {
				System.out.println("Thread.sleep Error in game loop");
			}
 
		}
	}

	/**
	 * starts the thread
	 */
	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			
			t.start();
		}
	}

	/**
	 * starts the main game loop
	 * @param args
	 */
	public static void main(String[] args) {
		GameLoop gl = new GameLoop("GameThread");
		gl.start();
	}
}
