
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
	public void gameUpdate(PainterLoop p) {
		WorldMap.getPlayer().parseInput(p.io.getKeys(), p.io.getMouse());
		p.camera.update();
		WorldMap.update();
	}
	@Override
	
	public void run() {
		Screen s = new Screen();
		PainterLoop p = s.painter;
		p.repaint();
		startTime = System.currentTimeMillis();
		
		
		//DEWITTERS GAME LOOP 
		//http://www.koonsolo.com/news/dewitters-gameloop/
		final int TICKS_PER_SECOND = 60;
		final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
		long nextGameTick = getTickCount();
		while (true) {
			gameUpdate(p);
			p.repaint();
			nextGameTick+=SKIP_TICKS;
			long sleepTime = nextGameTick - getTickCount();
			if(sleepTime>=0) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
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
