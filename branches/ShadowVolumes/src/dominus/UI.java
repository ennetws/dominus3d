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
	private Element2D currentElement;
	private TextRenderer textEngine;
	public int width, height;
	
	private Element2D consoleBox;
	Font consoleFont = new Font("SansSerif", Font.PLAIN, 12);
	private int consoleNumLines = 4;
	public String[] console = new String[consoleNumLines];
	
	// Sample items
	private BufferedImage sampleImage;
	private boolean drawOnce = true;
	
	public UI(int width, int height, GL gl, GLU glu, World world){
		this.gl = gl;
		this.glu = glu;
		this.world = world;
		
		this.width = width;
		this.height = height;
		
		contentPanel = new Element2D("contentPanel", 1, 1, -1, -1, gl);	
		
		// Setup console
		textEngine = new TextRenderer(consoleFont, true, true);
		consoleBox = new Element2D("ConsoleBox", width, 60, 0, height - 60, gl);
		setupConsole(consoleBox);
		clearConsole();

		contentPanel.add(new Element2D("ImageBox", 256, 256, width - 256, 0, gl));
		
		contentPanel.add(new MessageBox("MsgBox1",contentPanel, "Welcome :)",
				"Press 'Space' to start, 'r' to reset", width, height,gl));
		
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
		
		drawConsole();
		
        // SAMPLE CODE ##############################
		
        // PNG Image loading
		Element2D e = get("ImageBox");

		if(drawOnce){
	        Graphics2D g = e.getGraphicsWithAlpha();
	        
	        g.drawImage(sampleImage, 0, 0, 256, 256, null);
	     
	        e.redrawTexture();
	        e.setTransperncy(0.5f);
	        
	        drawOnce = false;
		}
        
		// Draw FPS
		textEngine.beginRendering(width, height);
		textEngine.setColor(0.75f, 0.75f, 0.75f, 1.0f);
		textEngine.draw("FPS: " + world.renderer.fps , 0, height-10);  
		textEngine.endRendering();
        
        // End of SAMPLE CODE ########################
	}
	
	public Element2D get(String id){
		return (Element2D)contentPanel.getChild(id);
	}
	
	public void clearConsole(){
		for (int i = 0; i < consoleNumLines; i++){
			console[i] = "";
		}
	}
	
	public void setupConsole(Element2D con){
		Graphics2D g = con.getGraphicsWithAlpha();
		g.setColor(Color.black);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillRoundRect(0, 0, width, con.height, 20, 20);		
		con.redrawTexture();
	}
	
	public void drawConsole(){
		consoleBox.render();
		
		textEngine.beginRendering(width, height);
		textEngine.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		for(int i = 0; i < consoleNumLines; i++)
			textEngine.draw(console[i], 10, 
					(height-consoleBox.y) - (consoleFont.getSize() * (i) + 15));
		   
		textEngine.endRendering();
	}
	
	public void writeLine(String text){
		for(int i = 0; i < consoleNumLines - 1; i++)
			console[i] = console[i+1];
		
		console[consoleNumLines-1] = text;
	}
	
	public void add(Element2D e){
		contentPanel.add(e);
	}
	
	public void manage(){
		// Optimize
		if (currentElement != null){
			if (currentElement instanceof ElementGUI){
				ElementGUI eleGUI = (ElementGUI)currentElement;
				
				if (!eleGUI.inside(world.input.x, world.input.y+32)){
					eleGUI.setStyle(1);  // Mouse is out of the object
				}
			}
		}
		
		currentElement = (Element2D) contentPanel.isInside(world.input.x, world.input.y+32);
		
		if (currentElement != null){
			writeLine(currentElement.id);
			
			if (currentElement instanceof ElementGUI){
				ElementGUI eleGUI = (ElementGUI)currentElement;

				if(world.input.MouseButtonPressed)
					eleGUI.setStyle(ElementGUI.PRESSED);
				else
					eleGUI.setStyle(ElementGUI.HOVER);
				
				if (world.input.MouseReleased){
					world.input.MouseReleased = false;
					eleGUI.action();
				}
			}
		}		
	}
}
