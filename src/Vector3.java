public class Vector3 {
	public double x, y, z;
	//Constructors
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	//Vector Math
	
	//Addition
	public void add(Vector3 vector) {
		x += vector.x;
		y += vector.y;
		z += vector.z;
	}
	public void add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	public void add(Vector3 vector, double scalar) {
		this.x += vector.x * scalar;
		this.y += vector.y * scalar;
		this.z += vector.z * scalar;
	}
	
	//Subtraction
	public void sub(Vector3 vector) {
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
	}
	public void sub(double x, double y, double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}
	public void sub(Vector3 vector, double scalar) {
		this.x -= vector.x * scalar;
		this.y -= vector.y * scalar;
		this.z -= vector.z * scalar;
	}
	
	//Multiplication
	public void mul(double scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
	}
	
	//Division
	public void div(double scalar) {
		x /= scalar;
		y /= scalar;
		z /= scalar;
	}
	
	//Useful stuff
	public double magnitude() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	public double magnitudeSquared() {
		return (x*x + y*y + z*z);
	}

	public void normalize() {
		double l = magnitude();
		this.div(l);
	}
	public Vector3 normal() {
		Vector3 normal = this.copy();
		normal.normalize();
		return (normal);
	}
	
	public void invert() {
		x = -x;
		y = -y;
		z = -z;
	}
	
	public void reset() {
		x = 0;
		y = 0;
		z = 0;
	}
	public Vector3 copy() {
		return new Vector3(x, y, z);
	}
	public void setTo(Vector3 vector) {
		x = vector.x;
		y = vector.y;
		z = vector.z;
	}
	public void setTo(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	
	//ClASS FUNCTIONS
	public static Vector3 add(Vector3 a, Vector3 b) {
		return new Vector3(a.x + b.x, a.y + b.y, a.z + b.z);
	}
	public static Vector3 sub(Vector3 a, Vector3 b) {
		return new Vector3(a.x - b.x, a.y - b.y, a.z - b.z);
	}
	
	//Multiplication
	public static Vector3 mul(Vector3 vector, double scalar) {
		return new Vector3(vector.x*scalar, vector.y*scalar, vector.z*scalar);
	}

	//Division
	public static Vector3 div(Vector3 vector, double scalar) {
		return new Vector3(vector.x/scalar, vector.y/scalar, vector.z/scalar);
	}
	
	//Distance
	public static double distance(Vector3 a, Vector3 b) {
		return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y) + (a.z-b.z)*(a.z-b.z));
	}
	public static double distanceSquared(Vector3 a, Vector3 b) {
		return (a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y) + (a.z-b.z)*(a.z-b.z);
	}
	
	//Dot Product
	public static double dotProduct(Vector3 a, Vector3 b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}

}
