package dominus;

import java.awt.*;
import java.awt.Graphics2D;

import javax.media.opengl.GL;
import com.sun.opengl.util.texture.*;
import com.sun.opengl.util.j2d.TextureRenderer;

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
	private TextureRenderer texRenderer;
	
	public Element2D(String iden, int width, int height, int x, int y, GL gl){
		this(iden, null, width, height, x, y, gl);
	}

	public Element2D(String iden, Element2D parent, GL gl){
		super(iden, parent, gl);
	}
	
	public Element2D(String iden, Element2D parent, int width, int height, int x, int y, GL gl){
		this(iden, parent, gl);
		
		this.width = width;
		this.height = height;
		
		this.x = x;
		this.y = y;
		
		// Create a rectangular shaped polygon
		this.corner[0] = new Vertex(0,0,0.0f);
		this.corner[1] = new Vertex(0,height,0.0f);
		this.corner[2] = new Vertex(width,height,0.0f);
		this.corner[3] = new Vertex(width,0,0.0f);
		
        texRenderer = new TextureRenderer(width, height, false);
	}

	public void render(){
        int parentX,parentY,parentZ;
        parentX = parentY = parentZ = 0;
        
        if (parent != null){
        	parentX = ((Element2D)parent).x;
        	parentY = ((Element2D)parent).y;
        	parentZ = ((Element2D)parent).zIndex;
        }
        
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE_MINUS_SRC_ALPHA);
        
		Graphics2D g2d = texRenderer.createGraphics();
		
	    g2d.setColor(Color.yellow);
		g2d.fillRect(0, 0, 100, 100);
		g2d.setColor(Color.red);
		g2d.fillOval(0, 0, 20, 20);
		g2d.drawLine(0, 0, 50, 50);
		g2d.setFont(new Font("Arial", Font.PLAIN, 20)); 
		g2d.drawString("Ibraheem",0, 20);
		g2d.drawString("Ibraheem",0, 40);
		
		 
        Texture tex = texRenderer.getTexture();
        
        tex.bind();
        tex.enable();
        
		gl.glLoadIdentity();
		
        gl.glBegin(GL.GL_QUADS);

        // Bright 2D
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        
        gl.glVertex3f(corner[0].x + x + parentX, corner[0].y + y + parentY, corner[0].z + parentZ);
        gl.glTexCoord2f(0, 100);
        
        gl.glVertex3f(corner[1].x + x + parentX, corner[1].y + y + parentY, corner[1].z + parentZ);
        gl.glTexCoord2f(100, 100);
        
        gl.glVertex3f(corner[2].x + x + parentX, corner[2].y + y + parentY, corner[2].z + parentZ);
        gl.glTexCoord2f(100, 0);

        gl.glVertex3f(corner[3].x + x + parentX, corner[3].y + y + parentY, corner[3].z + parentZ);
        gl.glTexCoord2f(0, 0);
        
        gl.glEnd();
        
        tex.disable();
        gl.glDisable(GL.GL_BLEND);
	}
}
