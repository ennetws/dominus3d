package dominus;

import javax.media.opengl.GL;

import com.sun.opengl.util.j2d.TextRenderer;

import java.awt.Font;
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
	
	private int polyType = GL.GL_QUADS;
	
	public Element3D(String iden, GL gl){
		this(iden, null, gl);
	}
	
	public Element3D(String iden, Element3D parent, GL gl){
		super(iden, parent, gl);
		
		center = new Vertex(0,0,0);
		rotate = new Vertex(0,0,0);
		
		vertices = new Vector<Vertex>();
	}
	
	public void placeElement(){
		gl.glTranslatef(center.x, center.y, center.z);
		
		gl.glRotatef(rotate.x, 1, 0, 0);
		gl.glRotatef(rotate.y, 0, 1, 0);
		gl.glRotatef(rotate.z, 0, 0, 1);
	}
	
	public void render(){
		if (vertices.size() == 0)
			return;
		
		gl.glPushMatrix();

		placeElement();
		
        gl.glColor4f(1.0f, 1.0f, 1.0f, transperncy); 
        
        gl.glBegin(GL_QUADS);
        renderAllVertices();
		gl.glEnd();
		
		gl.glPopMatrix();
	}
	
	public void renderWireframe(){
		gl.glPushMatrix();

		placeElement();
	
    	gl.glDisable(GL_LIGHTING);
    	gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    	
    	gl.glBegin(polyType);
    	
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
		
		e.vertices = Element3D.box(1.0f, 0.5f, 2.5f);
		
		return e;
	}
	
	public static Vector<Vertex> box(float width, float length, float height){
		Vector<Vertex> v = new Vector<Vertex>();
		
		width /= 2;
		length /= 2;
		
		v.add(new Vertex(width, length, 0));	// Bottom
		v.add(new Vertex(-width, length, 0));
		v.add(new Vertex(-width, -length, 0));
		v.add(new Vertex(width, -length, 0));
		
		v.add(new Vertex(width, -length, 0)); // Side1
		v.add(new Vertex(width, -length, height));
		v.add(new Vertex(width, length, height));
		v.add(new Vertex(width, length, 0));
		
		v.add(new Vertex(width, length, height)); // Top
		v.add(new Vertex(-width, length, height));
		v.add(new Vertex(-width, -length, height));
		v.add(new Vertex(width, -length, height));
		
		v.add(new Vertex(width, length, 0)); // Side2
		v.add(new Vertex(width, length, height));
		v.add(new Vertex(-width, length, height));
		v.add(new Vertex(-width, length, 0));

		v.add(new Vertex(-width, -length, 0)); // Side3
		v.add(new Vertex(-width, -length, height));
		v.add(new Vertex(-width, length, height));
		v.add(new Vertex(-width, length, 0));
		
		v.add(new Vertex(width, -length, 0)); // Side4
		v.add(new Vertex(width, -length, height));
		v.add(new Vertex(-width, -length, height));
		v.add(new Vertex(-width, -length, 0));
		
		return v;
	}
	
	public static Element3D createBox(String id, float width, 
			float length, float height, GL gl){
		
		Element3D e = new Element3D(id, gl);
		
		e.vertices = Element3D.box(width, length, height);
		
		return e;
	}
	
	public static Element3D createAxis(String id, float length, GL gl){
		Element3D e = new Element3D(id, gl);

		Element3D x = new Element3D(id, gl);
		Element3D y = new Element3D(id, gl);
		Element3D z = new Element3D(id, gl);

		e.add(x);
		
		
		return e;
	}
	
	public void setPolyType(int pType){
		this.polyType = pType;
	}
}
