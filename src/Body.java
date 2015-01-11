import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Body {
	static ArrayList<Body> all = new ArrayList<Body>();
	public static void bodyCreated(Body body) {
		Main.gravity.add(body);
	}
	
	public String type = "Body";
	public Vector2 size;
	public Vector2 position;
	public Vector2 velocity = new Vector2();
	public Vector2 acceleration = new Vector2();
	public Vector2 force = new Vector2();
	
	public double mass;
	public double inverseMass;
	public double restitution; 
	public double damping = 0.998;
	
	//rotation
	public double inertia = 0;
	public double inverseInertia = 0;
	public double torque = 0;
	public double angularAcceleration = 0;
	public double angularVelocity = 0;
	public double orientation = 0;
	
	//Friction 
	public double staticFriction = 1.0;
	public double dynamicFriction = 0.3;
	
	public int radius;
	Color color = new Color(50,150,250);
	
	public abstract void edgeCheck();
	public abstract void draw(Graphics2D graphics2D);
	public abstract double getArea();
	public abstract void setInertia();
	public abstract Vector2 center();	
	
	//Sets Material
	public void setMaterial(Material material) {
		setMass(material.density * getArea());
		this.restitution = material.restitution;
		this.color = material.color;
	}
	
	//Sets Mass
	public void setMass(double m) {
		mass = m;
		setInertia();
		
		if (mass == 0) 
			inverseMass = 0;
		else
			inverseMass = 1/mass;
	}
	
	public void addForce(Vector2 force) {
		this.force.add(force);
	}
	public void addForce(double x, double y) {
		force.add(new Vector2(x, y));
	}
	
	public void addForceAtBodyPoint(Vector2 force, Vector2 point) {
		point = Vector2.toWorldSpace(this, point);
		addForceAtPoint(force, point);
	}
	public void addForceAtPoint(Vector2 force, Vector2 point) {
		point.sub(center());
		addForce(force);
		torque += point.x * force.y - point.y * force.x;
	}
	
	public void update(double delta) {
		//orientation += 1*delta;
		acceleration.setTo(force);
		acceleration.mul(inverseMass);
		velocity.add(Vector2.mul(acceleration, delta));
		position.add(Vector2.mul(velocity, delta));
			
		//velocity.mul(Math.pow(damping, delta));		
		
		angularVelocity += torque * inverseInertia * delta;
		orientation += angularVelocity * delta;
		
		force.reset();
		torque = 0;
		edgeCheck();
	}

	
	
	
	
	public static void updateAll(double delta) {
		for (int i = 0; i < all.size(); i++) {
			all.get(i).update(delta);
		}
	}
	public static void drawAll(Graphics2D graphics2D) {
		for (int i = 0; i < all.size(); i++) {
			all.get(i).draw(graphics2D);
		}
	}


	//Orientation functions 

	//Get Vertices


	public Vector2[] verticesObjectSpace() {
		Vector2[] offset = new Vector2[4];
		offset[0] = new Vector2(-size.x/2, -size.y/2);
		offset[1] = new Vector2(size.x/2, -size.y/2);
		offset[2] = new Vector2(size.x/2, size.y/2);
		offset[3] = new Vector2(-size.x/2, size.y/2);
		
		return offset;
	}
	public Vector2[] verticesWorldSpace() {
		double[][] matrix = Matrix.rZ(Math.toRadians(orientation));
		Vector2[] vertices = verticesObjectSpace();
		Matrix.rotate(matrix, vertices);
		Vector2.add(vertices, center());
		return vertices;
	}
	
	public int[][] verticesInt() {
		Vector2[] vertices = verticesWorldSpace();
		int[][] i = new int[2][4];
		i[0][0] = (int)vertices[0].x;
		i[0][1] = (int)vertices[1].x;
		i[0][2] = (int)vertices[2].x;
		i[0][3] = (int)vertices[3].x;
		i[1][0] = (int)vertices[0].y;
		i[1][1] = (int)vertices[1].y;
		i[1][2] = (int)vertices[2].y;
		i[1][3] = (int)vertices[3].y;		
		
		return i;
	}



}