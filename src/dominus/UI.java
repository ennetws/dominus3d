/**
 * 
 */
package dominus;

import javax.media.opengl.GL;
import javax.media.opengl.glu.*;

/**
 * Generates and manages UI elements including message boxes, menus
 * buttons, cursor, labels, etc.
 * 
 * @author ibraheem
 *
 */

public class UI {
	private Element2D contentPanel;
	private GL gl;
	private GLU glu;
	private int width, height;
	
	public UI(int width, int height, GL gl, GLU glu){
		this.gl = gl;
		this.glu = glu;
		
		this.width = width;
		this.height = height;
		
		contentPanel = new Element2D("contentPanel", 0, 0, 0, 0, gl);
		
		contentPanel.add(new Element2D("contentPanel", 50, 50, 100, 100, gl));
		
	}
	
	public void render(){
		// Setup 2D projection
		gl.glMatrixMode (GL.GL_PROJECTION);
        gl.glLoadIdentity ();
        glu.gluOrtho2D(0, width, height, 0);
        gl.glMatrixMode (GL.GL_MODELVIEW);
		
		contentPanel.renderAll();
	}
	
}
