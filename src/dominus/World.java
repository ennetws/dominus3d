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
	public PhysicsEngine physics;
	public InputEngine input;
	private JFrame window;

	private boolean running;
	private Element3D superObject;
	
	public int numOfDominoes = 2;
	

	
	public World(JFrame w, int width, int height){
		renderer = new RenderEngine(width, height, this);
		input = new InputEngine(this);
		physics = new PhysicsEngine(this);
		
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
			canvas.display();
			physics.run();
	}
	
	public void render(GL gl){
        superObject.renderAll();
	}
	
	public void loadWorld(){
		superObject = new Element3D("SuperObject", null, renderer.gl);

	//## Sample Objects ##########################
		Element3D e;
        
		/*
        e = Element3D.createGrid("Grid", 8, 1.0f, renderer.gl);
        e.setTransperncy(0.5f);
        e.setWireframe(true);
        superObject.add(e);
        */
        
        physics.domCollision = new Element3D[numOfDominoes];
      
        for(int i = 0 ; i < numOfDominoes ; i++){
        	e = Element3D.createDomino("Domino"+i, renderer.gl);
        	e.moveTo(new Vertex(i*2.0f,0,0));
        	// e.rotateTo(new Vertex(0,0,i*10));
        	e.setShadeMode(GL_FLAT);
        	
        	physics.domCollision[i] = physics.boundBox(e, renderer.gl);
        	
        	superObject.add(e);
        }
        
        // Create Axis object
        e = Element3D.createAxis("MainAxis", 3.0f, renderer.gl);
        e.moveTo(new Vertex(-5,-5,0));
        superObject.add(e);
        
        e = Element3D.loadObj("media/objects/teapot.obj", "", "LoadedObj2",0.4f, renderer.gl);
        e.moveTo(new Vertex(-5,5,0));
        superObject.add(e);
        
        e = Element3D.loadObj("media/objects/metal_floor.obj", "media/textures/metal.jpg", "LoadedObj3",5, renderer.gl);
        e.moveTo(new Vertex(0,0,0));
        superObject.add(e);
        
	//#########################################        
	}
	
	public void add(Element3D e){
		superObject.add(e);
	}
	
	public Element3D get(String id){
		return (Element3D)superObject.getChild(id);
	}
	
}
