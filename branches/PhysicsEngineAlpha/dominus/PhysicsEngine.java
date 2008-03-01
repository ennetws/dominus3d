package dominus;

import java.util.*;

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
		Element3D e;
		BoundingBox b1, b2;
		
		for (int i = 0; i < world.numOfDominoes; i++) {
			e = world.get("Domino" + i);
			b1 = new BoundingBox(e);
			b1.render();
			
			for (int j = 0; j < world.numOfDominoes; j++) {
				if (i == j)
					break;
				
				e = world.get("Domino" + j);	
				b2 = new BoundingBox(e);	
				b2.render();
				
				// Find if they collide	
				boolean collide = intersect(b1, b2);
				world.renderer.ui.writeLine(b1 + " + " + b2 + " = " + collide);
			}
		}
	}

	private boolean intersect(BoundingBox b1, BoundingBox b2){
		
		return false;
	}
}

class BoundingBox{
	Element3D face[] = new Element3D[6];
	private String id;
	
	public BoundingBox(Element3D e){
		
		float width = e.width / 2;
		float length = e.length / 2;
		float height = e.height;
		
		// Create all faces and align them
		// Top - Bottom - Front - Back - Left - Right
		
		Element3D top = new Element3D("BBox-TOP-" + e.id, e.gl);
		top.vertices.add(new Vertex(width, length, height)); 
		top.vertices.add(new Vertex(-width, length, height));
		top.vertices.add(new Vertex(-width, -length, height));
		top.vertices.add(new Vertex(width, -length, height));
	
		Element3D bottom = new Element3D("BBox-BOTTOM-" + e.id, e.gl);
		bottom.vertices.add(new Vertex(width, length, 0)); 
		bottom.vertices.add(new Vertex(-width, length, 0));
		bottom.vertices.add(new Vertex(-width, -length, 0));
		bottom.vertices.add(new Vertex(width, -length, 0));
		
		Element3D front = new Element3D("BBox-FRONT-" + e.id, e.gl);
		front.vertices.add(new Vertex(width, -length, 0)); 
		front.vertices.add(new Vertex(width, -length, height));
		front.vertices.add(new Vertex(width, length, height));
		front.vertices.add(new Vertex(width, length, 0));
		
		Element3D back = new Element3D("BBox-BACK-" + e.id, e.gl);
		back.vertices.add(new Vertex(-width, -length, 0)); 
		back.vertices.add(new Vertex(-width, -length, height));
		back.vertices.add(new Vertex(-width, length, height));
		back.vertices.add(new Vertex(-width, length, 0));

		Element3D left = new Element3D("BBox-LEFT-" + e.id, e.gl);
		left.vertices.add(new Vertex(width, length, 0)); 
		left.vertices.add(new Vertex(width, length, height));
		left.vertices.add(new Vertex(-width, length, height));
		left.vertices.add(new Vertex(-width, length, 0));
		
		Element3D right = new Element3D("BBox-RIGHT-" + e.id, e.gl);
		right.vertices.add(new Vertex(width, -length, 0)); 
		right.vertices.add(new Vertex(width, -length, height));
		right.vertices.add(new Vertex(-width, -length, height));
		right.vertices.add(new Vertex(-width, -length, 0));
	
		face[0] = top;
		face[1] = bottom;
		face[2] = front;
		face[3] = back;
		face[4] = left;
		face[5] = right;
		
		alignFaces(e);
		
		id = "BBox-" + e.id;
	}
	
	public String toString(){
		return id;
	}
	
	public void render(){
		for (int i = 0 ; i < 6; i++)
			face[i].renderWireframe();
	}

	public void alignFaces(Element3D e){
		GL gl = e.gl;
		
		float[] m = new float[16];

		for (int f = 0 ; f < 6 ; f++){
			Iterator<Vertex> i = face[f].vertices.iterator();
		
			while (i.hasNext()){
				Vertex v = i.next();
				
				gl.glPushMatrix();
				gl.glLoadIdentity();	
				gl.glTranslatef(e.center.x, e.center.y, e.center.z);
	
				gl.glRotatef(e.rotate.x, 1, 0, 0);
				gl.glRotatef(e.rotate.y, 0, 1, 0);
				gl.glRotatef(e.rotate.z, 0, 0, 1);
				
				gl.glGetFloatv(GL.GL_MODELVIEW_MATRIX, m, 0);
				gl.glPopMatrix();
	
				float X = v.x;
				float Y = v.y;
				float Z = v.z;
				
				v.x = X * m[0] + Y * m[4] + Z * m[8] + m[12];
				v.y = X * m[1] + Y * m[5] + Z * m[9] + m[13];
				v.z = X * m[2] + Y * m[6] + Z * m[10] + m[14];
			}
		}
	}
}
