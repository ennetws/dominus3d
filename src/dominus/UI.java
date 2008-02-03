/**
 * 
 */
package dominus;

import java.awt.*;
import java.awt.Graphics2D;
import javax.media.opengl.GL;
import javax.media.opengl.glu.*;
import com.sun.opengl.util.j2d.TextRenderer;

/**
 * Generates and manages UI elements including message boxes, menus
 * buttons, cursor, labels, etc.
 * 
 * @author ibraheem
 *
 */

public class UI {
	private GL gl;
	private GLU glu;
	private World world;
	
	private Element2D contentPanel;
	private TextRenderer textEngine;
	private int width, height;
	
	public UI(int width, int height, GL gl, GLU glu, World world){
		this.gl = gl;
		this.glu = glu;
		this.world = world;
		
		this.width = width;
		this.height = height;
		
		textEngine = new TextRenderer(new Font("SansSerif", Font.BOLD, 36), true);
	    
		contentPanel = new Element2D("contentPanel", 1, 1, -1, -1, gl);
		
		// Sample 2D object
		contentPanel.add(new Element2D("BlueBox", 200, 45, 0, 0, gl));
	}
	
	public void render(){
		// Setup 2D projection
		gl.glMatrixMode (GL.GL_PROJECTION);
        gl.glLoadIdentity ();
        glu.gluOrtho2D(0, width, height, 0);
        gl.glMatrixMode (GL.GL_MODELVIEW);
        
		contentPanel.renderAll();
		
        // SAMPLE CODE
        Element2D e = get("BlueBox");
        
        Graphics2D g = e.getGraphics();
        
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        
        g.setColor(Color.white);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("SansSerif", Font.BOLD, 36));

        if (world.canvas.getMousePosition() != null)
        	g.drawString("x=" + world.canvas.getMousePosition().x+
        				", y=" + world.canvas.getMousePosition().y, 0, 36);
        
        writeLine("FPS: " + world.renderer.fps , 0, 65);
        
        // End of SAMPLE CODE
	}
	
	public Element2D get(String id){
		return (Element2D)contentPanel.getChild(id);
	}
	
	public void writeLine(String text, int x, int y){
		textEngine.beginRendering(width, height);
		   
		textEngine.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		textEngine.draw(text, x, height-y);
		   
		textEngine.endRendering();
	}
}
