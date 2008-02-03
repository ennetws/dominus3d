/**
 * 
 */
package dominus;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.*;
import javax.imageio.ImageIO;

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
	
	// Sample items
	private BufferedImage sampleImage;
	private boolean drawOnce = true;
	
	public UI(int width, int height, GL gl, GLU glu, World world){
		this.gl = gl;
		this.glu = glu;
		this.world = world;
		
		this.width = width;
		this.height = height;
		
		textEngine = new TextRenderer(new Font("SansSerif", Font.BOLD, 36), true);
	    
		contentPanel = new Element2D("contentPanel", 1, 1, -1, -1, gl);
		
		// Sample 2D objects
		contentPanel.add(new Element2D("ImageBox", 255, 255, 0, 0, gl));

        try{
            sampleImage = ImageIO.read(new File("media/texture.png"));
        }catch(Exception e){
        	System.out.println(e.getMessage());
        }
	}
	
	public void render(){
		// Setup 2D projection
		gl.glMatrixMode (GL.GL_PROJECTION);
        gl.glLoadIdentity ();
        glu.gluOrtho2D(0, width, height, 0);
        gl.glMatrixMode (GL.GL_MODELVIEW);
        
		contentPanel.renderAll();
		
        // SAMPLE CODE ##############################
		
        // PNG Image loading
		Element2D e = get("ImageBox");
		
		if(drawOnce){
			
	        Graphics2D g = e.getGraphicsWithAlpha();
	      
	        g.setFont(new Font("SansSerif", Font.BOLD, 36));
	        g.setColor(Color.red);
	        g.drawImage(sampleImage, 0, 0, 256, 256, null);
	       
	        e.draw();
	        
	        drawOnce = false;
		}
		
        if (world.canvas.getMousePosition() != null){
        	e.x = world.canvas.getMousePosition().x;
        	e.y = world.canvas.getMousePosition().y;
        }
        
        writeLine("FPS: " + world.renderer.fps , 0, 65);
        
        // End of SAMPLE CODE ########################
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
