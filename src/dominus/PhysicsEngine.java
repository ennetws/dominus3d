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
	
	public float[] red = {1.0f,0, 0};
	public float[] green = {0,1.0f, 0};
	public float[] blue = {0,0,1.0f};
	public float[] white = {1,1,1};
	
	public float[] cyan= {0,1,1};
	public float[] magenta = {1,0,1};
	public float[] yellow = {1,1,0};
		
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
			e.setWireframe(true);
			
			b1 = new BoundingBox(e);

			for (int j = 0; j < world.numOfDominoes; j++) {
				if (i == j)
					break;
				
				e = world.get("Domino" + j);	
				b2 = new BoundingBox(e);	
				
				boolean collide = intersect(b2, b1, e.gl);
				world.renderer.ui.writeLine(b1 + " + " + b2 + " = " + collide);
			}
		}
	}
	
	void printMatrix(float[] m){
		int cur = 0;
		
		for (int i = 0 ; i < 4; i++){
			for (int j = 0 ; j < 4; j++)
				System.out.print(m[cur++] + "\t");
			
			System.out.println();
		}
	}
	
	public void drawLine(Vertex v1, Vertex v2, float[] color){
		GL gl = world.renderer.gl;
			
		gl.glLineWidth(1.0f);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glBegin(GL.GL_LINES);
		
		gl.glColor3f(color[0], color[1], color[2]);
		gl.glVertex3d(v1.x, v1.y, v1.z);
		gl.glVertex3d(v2.x, v2.y, v2.z);
		
		gl.glEnd();
	}

	private boolean intersect(BoundingBox a, BoundingBox b, GL gl){
		if (a.center.equals(b.center))
			return true;
		
		float currentDist = 100000000;
		
		Vertex av = a.bound[0];
		Vertex bv = b.bound[0];

		for (int i = 0 ; i < 8; i++){
			for (int j = 0 ; j < 8; j++){
				float distance = a.bound[i].distanceTo(b.bound[j]);
				
				if (distance < currentDist){
					av = a.bound[i];
					bv = b.bound[j];
					
					currentDist = distance;
				}
			}
		}				
	    
		/*for (int i = 0; i < 8 ; i++)
			drawLine(a.bound[i], a.center, red);
		
		for (int i = 0; i < 8 ; i++)
			drawLine(b.bound[i], b.center, red);*/
		
		Vertex aX = new Vertex(a.center);	aX.x = av.x;
		Vertex aY = new Vertex(a.center);	aY.y = av.y;
		Vertex aZ = new Vertex(a.center);	aZ.z = av.z;
		
		Vertex bX = new Vertex(b.center);	bX.x = bv.x;
		Vertex bY = new Vertex(b.center);	bY.y = bv.y;
		Vertex bZ = new Vertex(b.center);	bZ.z = bv.z;

		drawLine(av, bv, white);
		
		drawLine(aX, a.center, green);
		drawLine(aY, a.center, blue);
		drawLine(aZ, a.center, red);
		
		drawLine(bX, b.center, green);
		drawLine(bY, b.center, blue);
		drawLine(bZ, b.center, red);
		
	    drawLine(aX, bX, yellow);
	    drawLine(aY, bY, cyan);
	    drawLine(aZ, bZ, magenta);
		
		float distX = Math.abs(aX.x - a.center.x) + Math.abs(bX.x - b.center.x);
		float distY = Math.abs(aY.y - a.center.y) + Math.abs(bY.y - b.center.y);
		float distZ = Math.abs(aZ.z - a.center.z) + Math.abs(bZ.z - b.center.z);
		
		float distXCenter = Math.abs(a.center.x - b.center.x);
		float distYCenter = Math.abs(a.center.y - b.center.y);
		float distZCenter = Math.abs(a.center.z - b.center.z);
		
		if (distXCenter <= distX
			&& distYCenter <= distY
			&& distZCenter <= distZ)
			return true;

	    return false;
	}
	
	float fabs(float x){
		return Math.abs(x);
	}
}

class BoundingBox{
	private String id;

	public Vertex[] bound = new Vertex[8];
	
	float[] m = new float[16];
	Vertex center = new Vertex(0,0,0);

	public BoundingBox(Element3D e){
		id = "BBox-" + e.id;
	
		float width = e.width / 2;
		float length = e.length / 2;
		
		bound[0] = new Vertex(width,length,0);			// Roof
		bound[1] = new Vertex(width,-length,0);
		bound[2] = new Vertex(-width,length,0);
		bound[3] = new Vertex(-width,-length,0);
		bound[4] = new Vertex(width,length,e.height);	// Floor
		bound[5] = new Vertex(width,-length,e.height);
		bound[6] = new Vertex(-width,length,e.height);
		bound[7] = new Vertex(-width,-length,e.height);
		
		getRotationMatrix(e);
		
		center.moveZ(e.height / 2);
		transformVertex(center);
		
		for (int i = 0; i < 8 ; i++)
			transformVertex(bound[i]);
	}

	public void getRotationMatrix(Element3D e){
		GL gl = e.gl;
		gl.glPushMatrix();
		gl.glLoadIdentity();	
		
		gl.glTranslatef(e.center.x, e.center.y, e.center.z);

		gl.glRotatef(e.rotate.x, 1, 0, 0);
		gl.glRotatef(e.rotate.y, 0, 1, 0);
		gl.glRotatef(e.rotate.z, 0, 0, 1);
		
		gl.glGetFloatv(GL.GL_MODELVIEW_MATRIX, m, 0);
		gl.glPopMatrix();
	}
	
	void transformVertex(Vertex v){
		float X, Y, Z;
		
		X = v.x;	
		Y = v.y;	
		Z = v.z;
		
		v.x = X * m[0] + Y * m[4] + Z * m[8] + m[12];
		v.y = X * m[1] + Y * m[5] + Z * m[9] + m[13];
		v.z = X * m[2] + Y * m[6] + Z * m[10] + m[14];
	}
	
	public String toString(){
		return id;
	}
}
