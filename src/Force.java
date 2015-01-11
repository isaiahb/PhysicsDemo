import java.util.ArrayList;

public abstract class Force {
	public static ArrayList<Force> all = new ArrayList<Force>();
	public static void updateAll() {
		for (int i = 0; i < all.size(); i++) {
			all.get(i).update();
		}
	}
	
	public Vector2 force;
	public ArrayList<Body> bodies = new ArrayList<Body>();
	public void add(Body body) {bodies.add(body);}
	public void remove(Body body) {bodies.remove(body);}
	public abstract void updateForce(Body body);
	
	public void update() {
		for (int i = 0; i < bodies.size(); i ++) {
			updateForce(bodies.get(i));
		}
	}
	
	
	  //////////////////////////////////////////////////
	 //				Force Generators				///
	//////////////////////////////////////////////////
	
	//Gravity//
	public static class Gravity extends Force {
		public Gravity (double x, double y) {
			force = new Vector2(x, y);
			
			all.add(this);
		}
		
		public void updateForce(Body body) {
			body.addForce(Vector2.mul(force, body.mass));
		}
		
	}
	
	//Spring//
	public static class Spring extends Force {
		Body other;
		double springConstant, length;
		public Spring (Body other, double springConstant, double length) {
			this.other = other;
			this.springConstant = springConstant;
			this.length = length;
			all.add(this);
		}
		
		public void updateForce(Body body) {
			force = body.position.copy();
			force.sub(other.position);
			double magnitude = force.magnitude();
			magnitude = Math.abs(magnitude - length);
			magnitude *= springConstant;
			force.normalize();
			//force.mul(magnitude);
			body.addForce(Vector2.mul(force,-magnitude));
		}
		
	}
	
}


