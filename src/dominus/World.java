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
	
	private boolean loading = true;
	private boolean running;
	private Element3D superObject;
	
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
		
		running = true;
	}
	
	public void run(){	
		while(running)
		{
			canvas.display();
			
			if (loading){
				loadWorld();
				loading = false;
			}
		}
	}
	
	public void render(GL gl){
		if (loading)
			return;
 	
        superObject.renderAll();
       	
	}
	
	public void loadWorld(){
		superObject = new Element3D("SuperObject", null, renderer.gl);

	//## Sample Objects ##########################
        Element3D e = Element3D.createBox("", 6, 6, -1, renderer.gl);
        e.setShadeMode(GL_FLAT);
        superObject.add(e);
        
        e = Element3D.createGrid("Grid", 8, 1.0f, renderer.gl);
        e.setTransperncy(0.5f);
        e.setWireframe(true);
        superObject.add(e);
        
        domCollisionArray = new Element3D[numOfDominoes];
      
        for(int i = 0 ; i < 10 ; i++){
        	e = Element3D.createDomino("Domino"+i, renderer.gl);
        	e.moveTo(new Vertex(i*0.5f,0,0));
        	e.rotateTo(new Vertex(0,0,i*10));
        	e.setShadeMode(GL_FLAT);
        	
        	//domCollisionArray[i] = Element3D.boundBox(e, gl);
        	
        	superObject.add(e);
        }
        
        // Create Axis object
        e = Element3D.createAxis("MainAxis", 3.0f, renderer.gl);
        e.moveTo(new Vertex(-5,-5,0));
        superObject.add(e);
        
        e = Element3D.loadObj("media/objects/vace.obj", "", "LoadedObj1",0.04f, renderer.gl);
        e.moveTo(new Vertex(5,-5,0));
        superObject.add(e);
        
        e = Element3D.loadObj("media/objects/teapot.obj", "", "LoadedObj2",0.4f, renderer.gl);
        e.moveTo(new Vertex(-5,5,0));
        superObject.add(e);
        
	//#########################################        
	}
	
	public void add(Element3D e){
		superObject.add(e);
	}
}
