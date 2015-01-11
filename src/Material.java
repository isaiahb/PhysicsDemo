import java.awt.Color;

public class Material {
	public double density, restitution;
	public Color color;
	public Material(double density, double restitution, Color color) {
		this.density = density;
		this.restitution = restitution;
		this.color = color;
	}
	
	public static Material Rock = new Material(0.6, 0.1, Color.gray);
	public static Material Wood = new Material(0.3, 0.2, new Color(250, 200,200));
	public static Material Metal = new Material(1.2, 0.05, Color.LIGHT_GRAY);
	public static Material BouncyBall = new Material(0.3, 0.8, Color.blue);
	public static Material SuperBall = new Material(0.3, 0.95, Color.blue);
	public static Material Pillow = new Material(0.6, 0.2, Color.WHITE);
	public static Material Static = new Material(0.0, 0.4, new Color(25,200,25));
}
