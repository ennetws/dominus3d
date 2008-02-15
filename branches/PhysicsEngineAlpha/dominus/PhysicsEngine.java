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
		
		while (world.renderer.fpsCounter < 60) {
			collisionSolver();
		}
	
	}
	
	private void collisionSolver() {
		for (int i = 0; i < world.numOfDominoes; i++) {
			for (int j = 0; j < world.numOfDominoes; j++) {
				
			}
		}
	}
	
	public void move(Element3D e) {
	
		e.moveX(1);
		// update display to move dominoes
	}
	
}
