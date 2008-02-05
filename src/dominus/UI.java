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
		
		contentPanel.add(new Element2D("ImageBox", 256, 256, 0, 0, gl));
		contentPanel.add(messageBox("Hello, World!", "Title goes here..", "MsgBox1"));
		
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
	       
	        e.redrawTexture();
	        e.setTransperncy(0.5f);
	        
	        drawOnce = false;
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
	
	public Element2D messageBox(String message, String title, String id){
		int mBoxWidth = 250;
		int mBoxHeight = 150;
		
		int shadowHeight = 3;
		
		Element2D mBox = new Element2D(id, mBoxWidth+shadowHeight, mBoxHeight+shadowHeight, 
				(width/2)-(mBoxWidth/2), (mBoxHeight/2)+(mBoxHeight/2), contentPanel.gl);
		
		Graphics2D g = mBox.getGraphicsWithAlpha();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.black);
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
		g.fillRoundRect(shadowHeight, shadowHeight, mBoxWidth, mBoxHeight, 20, 20);		
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
		g.fillRoundRect(0, 0, mBoxWidth, mBoxHeight, 20, 20);		
		g.fillRoundRect(0, 0, mBoxWidth, 30, 20, 20);		
	
		g.setColor(Color.LIGHT_GRAY);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g.drawString(title, 100, 20);
		
		g.setColor(Color.white);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g.drawString(message, 100, 100);
		
		mBox.redrawTexture();
		
		return mBox;
	}
}
