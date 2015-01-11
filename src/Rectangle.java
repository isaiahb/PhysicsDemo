import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics2D;
//import java.awt.*;
import java.util.*;

public class Rectangle extends Body {
	public static ArrayList<Rectangle> all = new ArrayList<Rectangle>();
	public static String type = "Rectangle";

	//Constructor
	public Rectangle(int x, int y, int width, int height) {
		size = new Vector2(width, height);
		position = new Vector2(x, y);
		restitution = 0.5;
		setMaterial(Material.Wood);
		
		all.add(this);
		Body.all.add(this);
		Body.all.get(Body.all.size()-1).type = type;
		Body.bodyCreated(this);
	}
	
	//Get Area
	public double getArea() {
		return (size.x * size.y)/100.;
	}
		
	//Get Center
	public Vector2 center() {
		return new Vector2(position.x + size.x/2, position.y + size.y/2);
	}
	
	public void setInertia() {
		inertia = (1/12) * mass * (Math.pow(size.x, 2) + Math.pow(size.y, 2));
	}



	
	//Edge of screen collision checking
	public void edgeCheck() {
		// If hitting the ground
		if (position.y + size.y > Main.loop.getHeight()) {
			position.y = Main.loop.getHeight() - size.y;
			velocity.y = velocity.y * -restitution;
		}
		
		// If hitting the roof
		if (position.y < 0) {
			position.y = 0;
			velocity.y = velocity.y * -restitution;
		}
		
		//hitting left wall
		if (position.x < 0) {
			position.x = 0;
			velocity.x = velocity.x * -restitution;
		}
		
		//hitting right wall
		if (position.x + size.x > Main.loop.getWidth()) {
			position.x = Main.loop.getWidth() - size.x;
			velocity.x = velocity.x * -restitution;
		}
	}
	

	
	//Draws Rectangle
	public void draw(Graphics2D graphics2D) {
		int[][] vertices = verticesInt();
		int numberOfVertices = vertices[0].length;
	
		graphics2D.setColor(color);
		graphics2D.fillPolygon(vertices[0], vertices[1], numberOfVertices);
		
		graphics2D.setColor(Color.BLACK);
		graphics2D.drawPolygon(vertices[0], vertices[1], numberOfVertices);	

	}
	
	//Updates All Rectangles
	public static void updateAll(double delta) {
		
		for (Rectangle rectangle: all) {
			rectangle.update(delta);
		}
	}
	//Draws All Rectangles
	public static void drawAll(Graphics2D graphics2D) {
		for (Rectangle rectangle: all) {
			rectangle.draw(graphics2D);
		}
	}
}