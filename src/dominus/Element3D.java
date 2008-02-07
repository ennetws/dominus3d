package dominus;

import javax.media.opengl.GL;
import java.util.*;
import static javax.media.opengl.GL.*;

/**
 * This class represent all visible 3D objects in the world.
 * @author ibraheem
 *
 */
public class Element3D extends Element {
	
	private Vector<Vertex> vertices;
	private Vertex center;
	private Vertex rotate;
	
	public Element3D(String iden, GL gl){
		super(iden, null, gl);
	}
	
	public Element3D(String iden, Element3D parent, GL gl){
		super(iden, parent, gl);
		
		center = new Vertex(0,0,0);
		rotate = new Vertex(0,0,0);
	}
	
	public void placeElement(){
		gl.glTranslatef(center.x, center.y, center.z);
		
		gl.glRotatef(rotate.x, 1, 0, 0);
		gl.glRotatef(rotate.y, 0, 1, 0);
		gl.glRotatef(rotate.z, 0, 0, 1);
	}
	
	public void render(){
		if (vertices == null)
			return;
		
		gl.glPushMatrix();

		placeElement();
		
        gl.glColor4f(1.0f, 1.0f, 1.0f, transperncy); 
        
        gl.glBegin(GL.GL_QUADS);
        renderAllVertices();
		gl.glEnd();
		
		gl.glPopMatrix();
	}
	
	public void renderWireframe(){
		gl.glPushMatrix();

		placeElement();
	
    	gl.glDisable(GL_LIGHTING);
    	gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    	
    	gl.glBegin(GL.GL_QUADS);
    	
        gl.glColor4f(0.75f, 0.75f, 0.75f, transperncy); 
        
    	renderAllVertices();
    	
		gl.glEnd();
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		gl.glEnable(GL_LIGHTING);
		gl.glPopMatrix();
	}
	
	private void renderAllVertices(){
		Iterator<Vertex> i = vertices.iterator();

		while (i.hasNext()){
			Vertex v = i.next();
			gl.glVertex3f(v.x, v.y, v.z);
		}
	}
	
	public void moveTo(Vertex v){
		center = v;
	}
	
	public void rotateTo(Vertex v){
		rotate = v;
	}
	
	public static Element3D createGrid(String id, float length, float spacing, GL gl){
		Element3D e = new Element3D(id, gl);
		
		e.center = new Vertex(0,0,0);
		e.rotate = new Vertex(0,0,0);
		
		e.vertices = new Vector<Vertex>();

		for (float y = 0 ; y < length; y += spacing){
			for (float x = 0; x < length; x += spacing){
				e.vertices.add(new Vertex(-(x*spacing) , -(y*spacing), 0.0f));
				e.vertices.add(new Vertex(-(x*spacing) , (y*spacing) , 0.0f));
				e.vertices.add(new Vertex((x*spacing) , (y*spacing), 0.0f));
				e.vertices.add(new Vertex((x*spacing) , -(y*spacing), 0.0f));
			}
		}
		
		return e;
	}
	
	// TODO: have domino create dots
	public static Element3D createDomino(String id, GL gl) {
		
		Element3D e = new Element3D(id, gl);
		
		e.center = new Vertex(0,0,0);
		e.rotate = new Vertex(0,0,0);
		
		e.vertices = new Vector<Vertex>();
		
		// front
		e.vertices.add(new Vertex(0, 0, 0));
		e.vertices.add(new Vertex(1, 0, 0));
		e.vertices.add(new Vertex(1, 1, 0));
		e.vertices.add(new Vertex(0, 1, 0));
		
		//back
		e.vertices.add(new Vertex(1, 0, 2));
		e.vertices.add(new Vertex(0, 0, 2));
		e.vertices.add(new Vertex(0, 1, 2));
		e.vertices.add(new Vertex(1, 1, 2));
		
		// left
		e.vertices.add(new Vertex(0, 0, 0));
		e.vertices.add(new Vertex(0, 1, 0));
		e.vertices.add(new Vertex(0, 1, 2));
		e.vertices.add(new Vertex(0, 0, 2));
		
		// right
		e.vertices.add(new Vertex(1, 0, 0));
		e.vertices.add(new Vertex(1, 0, 2));
		e.vertices.add(new Vertex(1, 1, 2));
		e.vertices.add(new Vertex(0, 1, 0));
		
		// top
		e.vertices.add(new Vertex(0, 1, 0));
		e.vertices.add(new Vertex(1, 1, 0));
		e.vertices.add(new Vertex(1, 1, 2));
		e.vertices.add(new Vertex(0, 1, 2));
		
		// bottom
		e.vertices.add(new Vertex(0, 0, 0));
		e.vertices.add(new Vertex(1, 0, 0));
		e.vertices.add(new Vertex(1, 0, 2));
		e.vertices.add(new Vertex(0, 0, 2));
		
		return e;
	}
}
