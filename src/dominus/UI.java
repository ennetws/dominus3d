/**
 * 
 */
package dominus;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.*;
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
		contentPanel.add(messageBox("Hello, World!", "Title", "MsgBox1"));
		
		get("MsgBox1").x = 20;
		get("MsgBox1").y = 300;
		
		
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
	
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		
		Rectangle2D textBounds = g.getFontMetrics().getStringBounds(title,g);
		
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(title, (mBoxWidth / 2) - (int)textBounds.getCenterX(), 20);
		
		textBounds = g.getFontMetrics().getStringBounds(message,g);
		g.setColor(Color.white);
		g.drawString(message, (mBoxWidth / 2) - (int)textBounds.getCenterX(), 60);

		drawButton(g, "OK", 60, 30, 110, 110);
		drawButton(g, "Cancel", 60, 30, 180, 110);
		
		mBox.redrawTexture();
		
		return mBox;
	}
	
	public void drawButton(Graphics2D g, String label, int buttonWidth, int buttonHeight, int x, int y){
		g.translate(x, y);
		
		g.setColor(new Color(0.2f,0.2f,0.2f));
		g.fillRoundRect(0, 0, buttonWidth, buttonHeight, 9, 9);
		g.setColor(new Color(0.05f,0.05f,0.05f));
		g.fillRoundRect(1, 1, buttonWidth-2, buttonHeight-2, 9, 9);
		
		GradientPaint gradient = new GradientPaint(0, 0, 
									new Color(0.3f,0.3f,0.3f), 0, buttonHeight, 
									new Color(0.2f,0.2f,0.2f));
		g.setPaint(gradient);
		g.fillRoundRect(2, 2, buttonWidth-4, buttonHeight-4, 9, 9);

		g.setPaint(null);

		g.setColor(Color.white);
		
		Rectangle2D textBounds = g.getFontMetrics().getStringBounds(label,g);
		g.drawString(label, (buttonWidth / 2) - (int)textBounds.getCenterX(), 
				(buttonHeight/2) - (int)(textBounds.getY()/2) - 2);
		
		g.translate(-x, -y);
	}
	
	public void add(Element2D e){
		contentPanel.add(e);
	}
}
