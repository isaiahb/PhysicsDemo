public class Vector2 {
	public double x, y;
	//Constructors
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vector2(int x, int y) {
		this.x = (double)x;
		this.y = (double)y;
	}
	public Vector2() {
		x = 0.0;
		y = 0.0;
	}
	
	//Vector Math
	public void add(Vector2 vector) {
		x += vector.x;
		y += vector.y;
	}
	public void add(double x, double y) {
		this.x += x;
		this.y += y;
	}
	public void add(Vector2 vector, double scalar) {
		this.x += vector.x * scalar;
		this.y += vector.y * scalar;
	}

	
	public void sub(Vector2 vector) {
		x -= vector.x;
		y -= vector.y;
	}
	public void sub(double x, double y) {
		this.x -= x;
		this.y -= y;
	}
	public void mul(double scalar) {
		x *= scalar;
		y *= scalar;
	}
	public void div(double scalar) {
		x /= scalar;
		y /= scalar;
	}
	
	//Useful stuff
	public double magnitude() {
		return Math.sqrt(x*x + y*y);
	}
	public double magnitudeSquared() {
		return (x*x + y*y);
	}

	public void normalize() {
		double l = magnitude();
		this.div(l);
	}
	public Vector2 normal() {
		Vector2 normal = this.copy();
		normal.normalize();
		return (normal);
	}
	
	public void invert() {
		x = -x;
		y = -y;
	}
	
	public void reset() {
		x = 0.0;
		y = 0.0;
	}
	public Vector2 copy() {
		return new Vector2(x, y);
	}
	public void setTo(Vector2 vector) {
		x = vector.x;
		y = vector.y;
	}
	public void setTo(double x, double y) {
		this.x = x;
		this.y = y;
	}

	
	//ClASS FUNCTIONS
	public static Vector2 add(Vector2 vector, Vector2 vector2) {
		return new Vector2(vector.x+vector2.x, vector.y+vector2.y);
	}
	public static void add(Vector2[] vectors, Vector2 addVector) {
		for (int i = 0; i < vectors.length; i++) {
			vectors[i].add(addVector);
		}
	}
	
	public static Vector2 sub(Vector2 vector, Vector2 vector2) {
		return new Vector2(vector.x-vector2.x, vector.y-vector2.y);
	}
	public static void sub(Vector2[] vectors, Vector2 subVector) {
		for (int i = 0; i < vectors.length; i++) {
			vectors[i].sub(subVector);
		}
	}
	//Multiplication
	public static Vector2 mul(Vector2 vector, double scalar) {
		return new Vector2(vector.x*scalar, vector.y*scalar);
	}

	//Division
	public static Vector2 div(Vector2 vector, double scalar) {
		return new Vector2(vector.x/scalar, vector.y/scalar);
	}
	
	//Distance
	public static double distance(Vector2 a, Vector2 b) {
		return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
	}
	
	//Dot Product
	public static double dotProduct(Vector2 a, Vector2 b) {
		return a.x * b.x + a.y * b.y;
	}

	
	//Equal to
	public static boolean equals(Vector2 a, Vector2 b) {
		return a.x == b.x && a.y == b.y;
	}
	
	//To ObjectSpace
	public static Vector2 toObjectSpace(Vector2 orgin, Vector2 position) {
		return Vector2.sub(position, orgin);
	}
	//To ObjectSpace
	public static Vector2 toObjectSpace(Body orgin, Vector2 position) {
		Vector2 point = Vector2.sub(position, orgin.center());
		double[][] m = Matrix.rZ(Math.toRadians(-orgin.orientation));
		Matrix.rotate(m, point);
		return point;
	}
	
	//To WorldSpace
	public static Vector2 toWorldSpace(Body orgin, Vector2 position) {
		
		double[][] m = Matrix.rZ(Math.toRadians(orgin.orientation));
		Matrix.rotate(m, position);
		Vector2 point = Vector2.add(position, orgin.center());
		return point;
	}
	public static Vector2[][] verticesToObjectSpace(Body a, Body b) {
		Vector2[] aVertices = a.verticesObjectSpace();
		Vector2[] bVertices = b.verticesWorldSpace();
		double[][] m = Matrix.rZ(Math.toRadians(-a.orientation));
		Vector2.sub(bVertices, a.center());
		Matrix.rotate(m, bVertices);
		Vector2[][] vertices = new Vector2[2][];
		vertices[0] = aVertices;
		vertices[1] = bVertices;
		return vertices;
		
	}
	//To WorldSpace
	public static Vector2 toWorldSpace(Vector2 orgin, Vector2 position) {
		return Vector2.add(position, orgin);
	}

	

}