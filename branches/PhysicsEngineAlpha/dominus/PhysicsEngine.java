package dominus;

import java.util.Iterator;

import javax.media.opengl.GL;

/**
 * PhysicsEngine contains all physic related methods (gravity, collision, projectile motion, etc)
 * and also contains all key events
 * @author cherz
 *
 */

public class PhysicsEngine {

	private World world;
	private float gravity;
	private float friction;
	public Element3D[] domCollision;
	
	public PhysicsEngine(World world) {
		
		this.world = world;
		gravity = -9.8f;
		friction = 1;
		
	}
	
	public void run() {
		collisionSolver();
	}
	
	private void collisionSolver() {
		for (int i = 0; i < world.numOfDominoes; i++) {
			Element3D cur = world.get("Domino" + i);
			
			Element3D plane1 = plane(cur);	
			plane1.renderWireframe();
			
			for (int j = 0; j < world.numOfDominoes; j++) {
				if(i == j)
					break;
					
				Element3D plane2 = plane(cur);
				plane2.renderWireframe();
				
				world.renderer.ui.writeLine("Coll: "+intersect(plane1, plane2));
			}
		}
	}
	
	private Element3D plane(Element3D e){
		Element3D p;
		p = createPlane("Plane", e.length, e.height, world.renderer.gl);
		
		p.center = e.center;

		Iterator<Vertex> i = p.vertices.iterator();
		
		while (i.hasNext()){
			Vertex v = i.next();
			
			Vertex A = new Vertex(0,1,0);
			Vertex4 R = new Vertex4(A.x * sin(e.rotate.x), 
									A.y * sin(e.rotate.x), 
									A.z * sin(e.rotate.x), 
									cos(e.rotate.x));
			Vertex4 Rc = conj(R);
			Vertex4 V = new Vertex4(v.x, v.y, v.z, 1);
			
			Vertex4 W = mult(R, V);
			
			v.x = W.x;
			v.y = W.y;
			v.z = W.z;
		}
		
		return p;
	}
	
	public Vertex4 conj(Vertex4 v){
		Vertex4 result = new Vertex4(-v.x,-v.y,-v.z,-v.w);
		return result;
	}
	
	public float sin(float angle){
		return (float)Math.sin(Math.toRadians(angle));
	}
	public float cos(float angle){
		return (float)Math.cos(Math.toRadians(angle));
	}
	
	public Vertex4 mult(Vertex4 A, Vertex4 B){
		Vertex4 C = new Vertex4(1,1,1,1);
		C.x = A.w*B.x + A.x*B.w + A.y*B.z - A.z*B.y;
		C.y = A.w*B.y - A.x*B.z + A.y*B.w + A.z*B.x;
		C.z = A.w*B.z + A.x*B.y - A.y*B.x + A.z*B.w;
		C.w = A.w*B.w - A.x*B.x - A.y*B.y - A.z*B.z;
		return C;
	}
	
	public Element3D createPlane(String id, float width, float length, GL gl){
		Element3D e = new Element3D(id, gl);

		e.vertices.add(new Vertex(width, length, 0)); 
		e.vertices.add(new Vertex(-width, length, 0));
		e.vertices.add(new Vertex(-width, -length, 0));
		e.vertices.add(new Vertex(width, -length, 0));
		
		return e;
	}
	
	private boolean intersect(Element3D p1, Element3D p2){
		
		return false;
	}
}
