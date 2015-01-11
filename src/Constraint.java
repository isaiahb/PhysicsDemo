import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Constraint {
	public static ArrayList<Constraint> all = new ArrayList<Constraint>();
	public static void updateAll() {
		for (int i = 0; i < all.size(); i++) {
			all.get(i).update();
		}
	}
	public static void drawAll(Graphics2D graphics2D) {
		for (int i = 0; i < all.size(); i++) {
			all.get(i).draw(graphics2D);
		}
	}
	
	//Abstract Methods
	public abstract void update();
	public abstract void draw(Graphics2D graphics2D);
	
	//Common Variables and Methods
	public Body a, b;
	public double maxLength;
	public double currentLength() { return Vector2.distance(a.center(), b.center());}
	
	
	  //////////////////////////////
	 //			Constraints		 //
	//////////////////////////////
	
	//Link
	public static class Link extends Constraint {
		double restitution = 0;
		
		public Link(Body a, Body b, int length) {
			this.a = a;
			this.b = b;
			this.maxLength = length;
			all.add(this);
		}
		public void update() {
			double length = currentLength();
			if (length < maxLength) return;
			//System.out.println("wtf");
			Manifold manifold = new Manifold(a,b);
			Vector2 normal = Vector2.sub(a.center(), b.center());
			normal.normalize();
			manifold.normal = normal;
			manifold.penetration = length - maxLength;
			manifold.restitution = restitution;
			manifold.isConstraint = true;
		}
		public void draw(Graphics2D graphics2D) {
			graphics2D.setColor(Color.black);
			int ax = (int) a.center().x;
			int ay = (int) a.center().y;
			int bx = (int) b.center().x;
			int by = (int) b.center().y;
			graphics2D.drawLine(ax, ay, bx, by);
			
			graphics2D.setColor(Color.yellow);
			graphics2D.fillOval(ax-2, ay-2, 5, 5);
			graphics2D.fillOval(bx-2, by-2, 5, 5);
		
		}
	}
	
	//Rod
	public static class Rod extends Constraint{
		public Rod(Body a, Body b, double length) {
			this.a = a;
			this.b = b;
			maxLength = length;
			all.add(this);
		}

		public void update() {
			double length = currentLength();
			if (length == maxLength) return;
			
			//System.out.println(System.nanoTime());
			Manifold manifold = new Manifold(a, b);
			// Calculate the normal.
			Vector2 normal = Vector2.sub(b.center(), a.center());
			normal.normalize();
			
			if (length < maxLength) {
				manifold.normal = normal;
				manifold.penetration = length - maxLength;
			} else {
				manifold.normal = Vector2.mul(normal, -1);
				manifold.penetration = length-maxLength;
			}
			manifold.isConstraint = true;
			manifold.restitution = 0;

		}

		public void draw(Graphics2D graphics2D) {
			graphics2D.setColor(Color.black);
			int ax = (int) a.center().x;
			int ay = (int) a.center().y;
			int bx = (int) b.center().x;
			int by = (int) b.center().y;
			graphics2D.drawLine(ax, ay, bx, by);
			
			graphics2D.setColor(Color.red);
			graphics2D.fillOval(ax-2, ay-2, 5, 5);
			graphics2D.fillOval(bx-2, by-2, 5, 5);
		}
	}
}
