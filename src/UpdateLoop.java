import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class UpdateLoop extends JPanel implements Runnable {
	public Color color = Color.BLUE;
	private Thread thread;
	private static boolean running = false;
	private static int fps = 250;

	Vector2 getVectorSize() {
		return new Vector2(getWidth(), getHeight());
	}

	// Constructor
	public UpdateLoop() {
		start();
		setFocusable(true);
		this.requestFocusInWindow();
	}

	// Initiates the thread
	public void start() {
		running = true;
		thread = new Thread(this);
	}

	public static void pause() {
		running = false;
	}

	public static void resume() {
		running = true;

	}

	// Game Loop *from Runnable*
	@Override
	public void run() {
		long last_time = System.nanoTime();
		double delta;
		while (running) {
			double timeTaken = (double)(System.nanoTime() - last_time);
			last_time = System.nanoTime();
			// update and render game here
			delta = timeTaken/1000000000.00 ;
			Main.update(delta);
			repaint();
			try {Thread.sleep(1000/fps);} catch (Exception e) {}	
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.drawRect(0, 0, getWidth(), getHeight());
		Main.draw(graphics2D);
	}

}
