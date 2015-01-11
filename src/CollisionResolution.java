import java.util.ArrayList;

public abstract class CollisionResolution {
	public static double getSeparatingVelocity(Manifold m) {
		Vector2 relativeVelocity = Vector2.sub(m.b.velocity, m.a.velocity);
		return Vector2.dotProduct(relativeVelocity, m.normal);
	}	
	public static void resolveVelocity(Manifold m) {
		Body a = m.a;
		Body b = m.b;
		
		double separatingVelocity = getSeparatingVelocity(m);
		double totalInverseMass = a.inverseMass + b.inverseMass;
		
		// Do not resolve if velocities are separating
		// Do not resolve if both objects have infinite mass
		if (separatingVelocity > 0 || totalInverseMass <= 0) {
			return; 
		}
		
		double restitution = m.restitution; 
		
		// Calculates the new separating velocity
		double newSeparatingVelocity = -separatingVelocity * restitution;	
		
		// Check velocity build up due to acceleration only
		Vector2 accCausedVelocity = Vector2.add(a.acceleration, b.acceleration);
		double accCausedSepVelocity = Vector2.dotProduct(accCausedVelocity, m.normal);
		accCausedSepVelocity *= Main.delta;

		// If we’ve got a closing velocity due to acceleration buildup,
		// remove it from the new separating velocity.
		if (accCausedSepVelocity < 0) {
			newSeparatingVelocity += restitution * accCausedSepVelocity;
			
			// Make sure we haven’t removed more than was there to remove.
			if (newSeparatingVelocity < 0) 
				newSeparatingVelocity = 0;
		}
		
		double deltaVelocity = separatingVelocity - newSeparatingVelocity;	// Calculates the change in velocity
		double impulse = deltaVelocity / totalInverseMass;					// Calculates the impulse scalar/length
		Vector2 impulsePerMass = Vector2.mul(m.normal, impulse);			// Calculates the impulse vector/direction
		
		// Apply impulse
		a.velocity.add(Vector2.mul(impulsePerMass, a.inverseMass));
		b.velocity.sub(Vector2.mul(impulsePerMass, b.inverseMass));
		
	//////////////////////////////////////////////////////////////
			  //////////////////////////////
			 //       Friction shit      //
			//////////////////////////////
		if (m.isConstraint) return ;
		Vector2 rv = Vector2.sub(b.velocity, a.velocity);
		Vector2 t = new Vector2(rv.x, rv.y);
		
		t.sub(rv, Vector2.mul(m.normal, Vector2.dotProduct(rv, m.normal)) );
		if (t.magnitude() == 0) return;
		t.normalize();
		
		//System.out.println(t.x + " " + t.y);
		
		double jt = -(Vector2.dotProduct(rv, t));
		jt /= totalInverseMass;
		
		if (jt== 0.0 ) return;

		// Coulumb's law
		Vector2 frictionImpulse;
		double sf = a.staticFriction;	//Math.sqrt( a.staticFriction * a.staticFriction );
		double df = b.dynamicFriction;	//Math.sqrt( a.dynamicFriction * a.dynamicFriction );
		if (Math.abs(jt ) < impulse * sf) {
			frictionImpulse = Vector2.mul(t, jt);
		}
		else {
			frictionImpulse = Vector2.mul(Vector2.mul(t, impulse), df); //t.mul( j ).muli( -df );
		}

		// Apply friction impulse
		//System.out.println(frictionImpulse.normal().magnitude());
		a.velocity.add(Vector2.mul(frictionImpulse, -a.inverseMass));
		b.velocity.add(Vector2.mul(frictionImpulse, b.inverseMass));
		
		
	}
	public static void resolvePenetration(Manifold m) {
		Body a = m.a;
		Body b = m.b;

		double totalIMass = a.inverseMass + b.inverseMass;
		
		// Do not resolve if objects are not penetrating
		// Do not resolve if both objects have infinite mass
		if (totalIMass <= 0 || m.penetration <= 0) return;
		
		Vector2 movePerIMass = Vector2.mul(m.normal, m.penetration/totalIMass);	// Calculates translating vector/direction
		
		// Apply translation
		a.position.add(Vector2.mul(movePerIMass, -a.inverseMass)); 
		b.position.add(Vector2.mul(movePerIMass, b.inverseMass));
		
		
		
	}
	public static void resolve(Manifold m) {
		resolvePenetration(m);
		resolveVelocity(m);
	}
	
	

	public static void update(ArrayList<Manifold> manifolds) {
		//Resolve all Velocities
		for (Manifold manifold: manifolds) {
			CollisionResolution.resolve(manifold);
			//CollisionResolution.resolveVelocity(manifold);
		}
		
	}
}
