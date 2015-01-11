public class Matrix {
	public static void rotate(double[][] m, Vector2 vector) {
		double x = vector.x;
		double y = vector.y;
		vector.x = x*m[0][0] + y*m[0][1];
		vector.y = x*m[1][0] + y*m[1][1];
	}
	public static void rotate(double[][] m, Vector2[] vectors) {
		for(int i = 0; i < vectors.length; i++) {
			Vector2 vector = vectors[i];
			double x = vector.x;
			double y = vector.y;
			vector.x = x*m[0][0] + y*m[0][1];
			vector.y = x*m[1][0] + y*m[1][1];		
		}

	}
	
	
	public static double[][] rX(double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double[][] m = {
			{1	,	0,	0	},
			{0	, cos, -sin	},
			{0	, sin,	cos	}
		};
		return m;
	}
	public static double[][] rY(double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double[][] m = {
			{cos,	0,	sin	},
			{0	,	1,  	0	},
			{-sin,	0,	cos	}
		};
		return m;
	}	
	public static double[][] rZ(double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double[][] m = {
			{cos,  -sin,  0	},
			{sin,	cos,  0	},
			{0	,	0  ,  1	}
		};
		return m;
	}
}
