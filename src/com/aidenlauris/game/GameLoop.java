package com.aidenlauris.game;
import java.util.ArrayList;
import java.util.Queue;

import com.aidenlauris.render.PainterLoop;
import com.aidenlauris.render.Screen;
import com.aidenlauris.render.SoundHelper;

public class GameLoop implements Runnable {
	private Thread t;
	private String threadName;
	long startTime;

	public GameLoop(String name) {
		threadName = name;
		System.out.println("Creating " + threadName);
	}

	private long getTickCount() {
		return (System.currentTimeMillis() - startTime);
	}

	@Override

	public void run() {
		Screen s = new Screen();
		PainterLoop p = s.getPainter();
		p.repaint();

		// DEWITTERS GAME LOOP
		// http://www.koonsolo.com/news/dewitters-gameloop/
		final int TICKS_PER_SECOND = 60;
		final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
		final int MAX_FRAMETIME = 1000 / TICKS_PER_SECOND;
		final int MAX_FRAMESKIP = 10;
		long nextGameTick = getTickCount();
		int loops;
		long previousTime = System.currentTimeMillis();
		ArrayList<Integer> fps = new ArrayList<>(60);
		for (int i = 0; i < 60; i++) {
			fps.add(0);
		}
		int totalFPS = 0;
		while (true) {

			long currentTime = System.currentTimeMillis();
			long frameTime = currentTime - previousTime;
			previousTime = currentTime;

			loops = 0;
			while (getTickCount() > nextGameTick && loops < MAX_FRAMESKIP) {

				WorldMap.update();
				nextGameTick += SKIP_TICKS;
				loops++;
			}
			int cfps = (int) (Math.max(MAX_FRAMETIME - frameTime, 0) * 2 * 60 / MAX_FRAMETIME);
			totalFPS -= fps.get(0);
			fps.remove(0);
			fps.add(cfps);
			totalFPS += cfps;
			p.fpsTimer = totalFPS / 60;
			p.repaint();

			try {
				Thread.sleep(Math.max(MAX_FRAMETIME - frameTime, 1));
			} catch (InterruptedException e) {
				System.out.println("oops");
			}

			//
			// WorldMap.update();
			// p.repaint();
			// nextGameTick+=SKIP_TICKS;
			// long sleepTime = nextGameTick - getTickCount();
			// if(sleepTime>=0) {
			// try {
			// Thread.sleep(sleepTime);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			//
		}
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

	public static void main(String[] args) {
		GameLoop gl = new GameLoop("yobunga");
		gl.start();
	}
}
