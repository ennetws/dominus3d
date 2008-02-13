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
	
	
	
	public PhysicsEngine(World world) {
		
		this.world = world;
		gravity = -9.8f;
		friction = 1;
		
	}
	
	public void move() {
	
		Vertex v = new Vertex(0,0,0);
		
		v = world.domCollisionArray[1].getVertexIndex(1);

		// update display to move dominoes
	}
	
	public Element3D boundBox(Element3D e, GL gl) {
		
		Vertex v;
		Element3D bounds = new Element3D("bounds", gl);
		int numVectors = 0;
		
		float minX = 10000;
		float minY = 10000;
		float minZ = 10000; 
		float maxX = -10000;
		float maxY = -10000; 
		float maxZ = -10000;
		float width;
		float height;
		float length;
		int i;
		
		numVectors = e.getNumVertices();
		
		for (i = 0; i < numVectors; i++) {
		
			v = e.getVertexIndex(i);
			
			// get min verts
			minX = getMinVertex(minX, v.x);
			minY = getMinVertex(minY, v.y);
			minZ = getMinVertex(minZ, v.z);
			
			// get max verts
			maxX = getMaxVertex(maxX, v.x);
			maxY = getMaxVertex(maxY, v.y);
			maxZ = getMaxVertex(maxZ, v.z);
			
		}
		
		width = Math.abs(maxX - minX);
		height = Math.abs(maxZ - minZ);
		length = Math.abs(maxY - minY);
		
		bounds = Element3D.createBox("Bounds" + i, width, length, height, gl);
		
		return bounds;
		
	}
	
	public static float getMinVertex(float min, float test) {
		
		if (test < min) {
			min = test;
		}
		
		return min;
	}
	
	public static float getMaxVertex(float max, float test) {
		
		if (test > max) {
			max = test;
		}
		
		return max;
	}
	
	
	public boolean collision(Vertex min1, Vertex max1, Vertex p) {
		
		if ( (isAbovePlane(min1.x, p.x) ) && 
			 (isAbovePlane(min1.y, p.y) ) && 
			 (isAbovePlane(min1.z, p.z) ) && 
			 (isBelowPlane(max1.x, p.x) ) &&
			 (isBelowPlane(max1.y, p.y) ) &&
			 (isBelowPlane(max1.z, p.z) ) ) 
			return true;
		else
			return false;
		
	}
	
	public boolean isAbovePlane(float x1, float x2) {
		
		if (x1 >= x2)
			return true;
		else
			return false;
		
	}
	
public boolean isBelowPlane(float x1, float x2) {
		
		if (x1 <= x2)
			return true;
		else
			return false;
		
	}
	
}
