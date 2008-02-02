package dominus;

import javax.media.opengl.GL;

/**
 * 2D element class for menus, text, object selection, etc.
 * 
 * @author cherz
 *
 */

public class Element2D extends Element {
	
	public int x, y;
	public int width, height, zIndex;
	
	private Vertex[] corner = new Vertex[4];
	
	public Element2D(String iden, int width, int height, int x, int y, GL gl){
		this(iden, null, width, height, x, y, gl);
	}
	
	public Element2D(String iden, Element2D parent, int width, int height, int x, int y, GL gl){
		this(iden, parent, gl);
		
		this.width = width;
		this.height = height;
		
		this.x = x;
		this.y = y;
		
		// Create a box shaped polygon
		this.corner[0] = new Vertex(0,0,0.0f);
		this.corner[1] = new Vertex(0,height,0.0f);
		this.corner[2] = new Vertex(width,0,0.0f);
		this.corner[3] = new Vertex(width,height,0.0f);
	}

	public Element2D(String iden, Element2D parent, GL gl){
		super(iden, parent, gl);
	}
	
	public void render(){
		gl.glLoadIdentity();
        gl.glBegin(GL.GL_TRIANGLE_STRIP);
        
        gl.glColor3f(0.0f, 0.0f, 1.0f); 
        gl.glVertex3f(corner[0].x + x, corner[0].y + y, corner[0].z);
        gl.glVertex3f(corner[1].x + x, corner[1].y + y, corner[1].z);
        gl.glVertex3f(corner[2].x + x, corner[2].y + y, corner[2].z);
        gl.glVertex3f(corner[3].x + x, corner[3].y + y, corner[3].z);
        
        gl.glEnd();
	}
}
