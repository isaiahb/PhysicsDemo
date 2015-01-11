/*Game and or Physics Engine
 *Project Started Friday November, 7, 2014
 *By Isaiah Ballah
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;

public class Main {
	public static Window window;	//JFrame
	public static UpdateLoop loop;	//FPanel
	
	//Forces
	public static Force gravity = new Force.Gravity(0, 100);
	
	//Update Data
	public static double fps;
	public static double fpsCounter = 0;
	public static int bodies;
	public static int collisions;
	public static double clock = 0.0;
	public static double delta = 0.0;
	
	// Character 
	//public static Rectangle character;
	public static Circle character;
	public static Vector2 test = new Vector2();
	public static Vector2 movement = new Vector2();
	
	public static void updateData(double delta) {
		clock += delta;
		fpsCounter++;
		bodies = Body.all.size();
		collisions = Manifold.all.size();

		if (clock >= 0.5) {
			fps = fpsCounter * 2;
			clock = 0.0;
			fpsCounter = 0.0;
		}
	}

	// main function
	public static void main(String[] args) {
		window = new Window(800, 600);
		loop = new UpdateLoop();
		//toolBar = new ToolBar();
		//window.add(toolBar);
		window.add(loop);
		window.setVisible(true);
		
		Rectangle ground = new Rectangle(250, 50, 150, 20);
		ground.setMaterial(Material.Static);
		System.out.println(ground.mass);
		//ground.color =  new Color(200,50,150);
		
		//character = new Rectangle(0, 0, 15,15);
		character = new Circle(10,0, 15);
		character.color = Color.RED;
		character.setMaterial(Material.Metal);
		Circle ball = new Circle(10,110,10);
		Circle ball2 = new Circle(20,110,10);
		ball.setMaterial(Material.Metal);
		ball2.setMaterial(Material.Metal);
		Constraint link;
		link = new Constraint.Rod(character, ground, 200);
		link = new Constraint.Rod(character, ball, 80);
		link = new Constraint.Rod(ball2, ball, 50);
		
		
		 ///////////////////////////////////////////
		///		    Action Listeners		    ///
	   ///////////////////////////////////////////
		loop.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				//Circle c2 = new Circle(x, y, 25);
				Rectangle c2 = new Rectangle(x, y, 25,25);
				
				c2.color = new Color(50,100,150);
				c2.setMaterial(Material.Wood);
				c2.addForce(0.0, 100 * c2.mass);
				
				System.out.println(c2.mass);
				
			}

			public void mouseReleased(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
		});
		loop.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {}
			public void mouseMoved(MouseEvent e) {}
		});
		loop.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				char key = e.getKeyChar();
				int keyCode = e.getKeyCode();
				System.out.println("pressed = "+ key + " | " + keyCode);
				if (key == 'd')
					movement.x = 100*character.mass;
				if (key == 'a')
					movement.x = -100*character.mass;
				if (key == 'w')
					movement.y = -100*character.mass;
				if (key == 's')
					movement.y = 100*character.mass;	
				if (keyCode == 10) {
					int x = loop.getWidth()/2;
					int y = loop.getHeight()/2;
					
					Circle c2 = new Circle(x, y, 25);
					c2.addForce(0.0, 100 * c2.mass);
				}
			}
			public void keyReleased(KeyEvent e) {
				char key = e.getKeyChar();

				if (key == 'd')
					movement.x = 0;
				if (key == 'a')
					movement.x = 0;
				if (key == 'w')
					movement.y = 0;
				if (key == 's')
					movement.y = 0;
			}
			public void keyTyped(KeyEvent e) {}
		});
		
		loop.run();
	}

	// update functions
	static void update(double delta) {
		
		character.velocity.add(movement,delta);
		Main.delta = delta;
		
		Force.updateAll();
		Body.updateAll(delta);
		Constraint.updateAll();
		
		
		CollisionDetection.BroadPhase(Body.all);
		CollisionResolution.update(Manifold.all);
		Manifold.clearManifolds();
		
		updateData(delta);
	}

	static void draw(Graphics2D graphics2D) {
		Body.drawAll(graphics2D);
		Constraint.drawAll(graphics2D);
		graphics2D.setColor(Color.black);
		graphics2D.drawRect((int)test.x, (int)test.y, 5, 5);
		graphics2D.drawString("fps: " + (int) fps, 5, 15);
		graphics2D.drawString("bodies: " + bodies, 5, 30);
		graphics2D.drawString("collisions: " + collisions, 5, 45);
		graphics2D.drawString("character velocity: " + (int)character.velocity.magnitude(), 5, 60);
		
	}
}
