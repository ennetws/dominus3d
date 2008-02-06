package dominus;

import javax.media.opengl.GL;
import java.util.*;
import java.util.Vector;
import static javax.media.opengl.GL.*;

/**
 * This class represent all visible 3D objects in the world.
 * @author ibraheem
 *
 */
public class Element3D extends Element {
	
	private Vector<Vertex> vertices;
	
	public Element3D(String iden, GL gl){
		super(iden, null, gl);
	}
	
	public Element3D(String iden, Element3D parent, GL gl){
		super(iden, parent, gl);
	}
	
	public void render(){
		
		if (vertices == null)
			return;
			
		Iterator<Vertex> i = vertices.iterator();
		
		gl.glBegin(GL_QUADS);
        
        gl.glColor3f(1.0f, 1.0f, 1.0f); 
        
		while (i.hasNext()){
			Vertex v = i.next();
			
			gl.glVertex3f(v.x, v.y, v.z);
		}
		
		gl.glEnd();
	}
	
	public Element3D createGrid(String id, float length, float spacing, GL gl){
		Element3D e = new Element3D(id, gl);
		
		e.vertices = new Vector<Vertex>();
		
		for (int x = 0 ; x < length; x++){
			for (int y = 0; y < length; y++){
				e.vertices.add(new Vertex(x+spacing, y+spacing, 0.0f));
				e.vertices.add(new Vertex(x-spacing, y+spacing, 0.0f));
				e.vertices.add(new Vertex(x-spacing, y-spacing, 0.0f));
				e.vertices.add(new Vertex(x+spacing, y-spacing, 0.0f));
			}
		}
		
		return e;
	}
}
