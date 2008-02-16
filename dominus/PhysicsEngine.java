package dominus;

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
		p = Element3D.createPlane("Plane", e.length, e.height, world.renderer.gl);
		
		p.center = e.center.shiftedX(e.width/2);
		
		return p;
	}
	
	private boolean intersect(Element3D p1, Element3D p2){
		
		return false;
	}
}
