package dominus;

import static javax.media.opengl.GL.GL_FLAT;

import javax.media.opengl.*;
import javax.swing.*;

/**
 * “The most incomprehensible thing about the world is 
 * that it is at all comprehensible.”
 * 
 * This class represent everything including the render 
 * engine and the physics engine.
 * 
 * @author ibraheem
 *
 */

public class World implements Runnable{
	
	public GLCanvas canvas;
	public RenderEngine renderer;
	public InputEngine input;
	private JFrame window;
	
	private boolean running;
	private Element3D superObject;

	private Element3D axis;
	private float rotateT = 0.0f;
	
	private int numOfDominoes = 10;
	
	private Element3D[] domCollisionArray;
	
	public World(JFrame w, int width, int height){
		renderer = new RenderEngine(width, height, this);
		input = new InputEngine(this);
		
		canvas = new GLCanvas();
		
		this.window = w;

		canvas.addGLEventListener(renderer);
		
		canvas.addMouseMotionListener(input.mouse);
		canvas.addMouseListener(input.mouse);
		canvas.addKeyListener(input.keyboard);
		
		window.add(canvas);
		canvas.requestFocus();
		window.setVisible(true);
		
		superObject = new Element3D("SuperObject", null, renderer.gl);
		
		running = true;
	}
	
	public void run(){	
		while(running)
		{
			canvas.display();
		}
	}
	
	public void render(GL gl){
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        
        domCollisionArray = new Element3D[numOfDominoes];
        
        for (int i = 0 ; i < numOfDominoes; i++) {
        	Element3D e = Element3D.createDomino("Domino"+i, gl);
        	e.moveTo(new Vertex(i* 0.5f,0,0));
        	e.rotateTo(new Vertex(0,0,i*10));
        	e.setShadeMode(GL_FLAT);
        	
        	domCollisionArray[i] = Element3D.boundBox(e, gl);
        	
        	e.render();
        }
        
        Element3D e2 = Element3D.createBox("", 6, 6, -1, gl);
        e2.render();
        
        Element3D e = Element3D.createGrid("Grid", 8, 1.0f, gl);

        e.setTransperncy(0.5f);
        e.renderWireframe();
        
        // Create Axis object
        axis = Element3D.createAxis("MainAxis", 3.0f, gl);
        axis.moveTo(new Vertex(-5,-5,0));
        axis.renderAll();
      
        rotateT+= 0.0f;       		
	}
	
	public void add(Element3D e){
		superObject.add(e);
	}
}
