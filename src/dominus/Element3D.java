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
	
	private int polyType = GL_QUADS;
	private int shadeMode = GL_SMOOTH;
	
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
		float parentX, parentY, parentZ;
		
        parentX = parentY = parentZ = 0;
        
        if (parent != null){
        	parentX = ((Element3D)parent).center.x;
        	parentY = ((Element3D)parent).center.y;
        	parentZ = ((Element3D)parent).center.z;
        }
        
		gl.glTranslatef(center.x + parentX, center.y + parentY, center.z + parentZ);
		
		gl.glRotatef(rotate.x, 1, 0, 0);
		gl.glRotatef(rotate.y, 0, 1, 0);
		gl.glRotatef(rotate.z, 0, 0, 1);
	}
	
	public void render(){
		if (!visible)
			return;
		
		gl.glPushMatrix();

		placeElement();

        gl.glColor4f(1.0f, 1.0f, 1.0f, transperncy); 
        gl.glShadeModel(shadeMode);
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
		
		v.add(new Vertex(width, length, 0)); // Bottom
		v.add(new Vertex(-width, length, 0));
		v.add(new Vertex(-width, -length, 0));
		v.add(new Vertex(width, -length, 0));
		
		v.add(new Vertex(width, length, height)); // Top
		v.add(new Vertex(-width, length, height));
		v.add(new Vertex(-width, -length, height));
		v.add(new Vertex(width, -length, height));
		
		v.add(new Vertex(width, -length, 0)); // Side1
		v.add(new Vertex(width, -length, height));
		v.add(new Vertex(width, length, height));
		v.add(new Vertex(width, length, 0));
		
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

		Element3D x = Element3D.createBox("X-Axis", length, 0.2f, 	0.2f , gl);
		Element3D y = Element3D.createBox("Y-Axis", 0.2f, 	length, 0.2f , gl);
		Element3D z = Element3D.createBox("Z-Axis", 0.2f, 	0.2f, 	length, gl);
		
		x.moveTo(new Vertex(length/2,0,0));
		x.shadeMode = GL_FLAT;
		e.add(x);
		
		y.moveTo(new Vertex(0,length/2,0));
		y.shadeMode = GL_FLAT;
		e.add(y);
		
		z.shadeMode = GL_FLAT;
		e.add(z);
		
		return e;
	}
	
	public void setPolyType(int pType){
		this.polyType = pType;
	}
	
	public void setShadeMode(int mode){
		shadeMode = mode;
	}
}
