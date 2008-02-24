package dominus;

import static javax.media.opengl.GL.GL_FLAT;

import com.sun.opengl.util.*;

import javax.media.opengl.*;
import javax.swing.*;
import java.util.Vector;

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

	private Element3D superObject;
	
	public int fpsCap = 80;

	public Vector<Element3D> dominoes = new Vector<Element3D>();
	public static final int NORTH = 10;
	public static final int SOUTH = 20;
	public static final int EAST = 30;
	public static final int WEST = 40;
	
	public int startX = 0;
	public int startY = 0;
	
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
	}
	
	public void run(){	
		FPSAnimator animator = new FPSAnimator(canvas, fpsCap);
	    animator.setRunAsFastAsPossible( true );
	    animator.start();
	}
	
	public void render(GL gl){
        superObject.renderAll();
        physics.run();
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
		
		addLineDominoes(5, NORTH);
		addLineDominoes(5, NORTH);
		
		addLineDominoes(5, WEST);
		addLineDominoes(5, WEST);
		addLineDominoes(5, WEST);
		addLineDominoes(5, SOUTH);
		addLineDominoes(5, EAST);
		
		addCurveDominoes(10, 0);
		addLineDominoes(5, SOUTH);
		addCurveDominoes(20, 90);
        
        // Create Axis object
        e = Element3D.createAxis("MainAxis", 3.0f, renderer.gl);
        e.moveTo(new Vertex(-20,-20,0));
        superObject.add(e);
        
        e = Element3D.loadObj("media/objects/teapot.obj", "", "LoadedObj2",0.4f, renderer.gl);
        e.moveTo(new Vertex(-5,5,0));
        //superObject.add(e);
        
        e = Element3D.loadObj("media/objects/metal_floor.obj", "media/textures/metal.jpg", "LoadedObj3",10, renderer.gl);
        e.moveTo(new Vertex(-5,-5,0));
        superObject.add(e);
        
	//#########################################        
	}
	
	public void add(Element3D e){
		superObject.add(e);
	}
	
	public Element3D get(String id){
		return (Element3D)superObject.getChild(id);
	}
	
	public void addLineDominoes(int number, int direction){
		Element3D e;
		
		float x = 0;
		float y = 0;
		
		if (dominoes.size() > 1){
			x = dominoes.get(dominoes.size()-1).center.x;
			y = dominoes.get(dominoes.size()-1).center.y;
		}else{
			x = startX;
			y = startY;
		}
		
		for (int i = 0; i < number; i++){
        	e = Element3D.createDomino("Domino"+dominoes.size(), renderer.gl);
        	
			switch(direction){
				case NORTH:
		        	e.moveTo(new Vertex(x,(i*-1.5f) + y,0));
		        break;
		        	
				case SOUTH:
		        	e.moveTo(new Vertex(x,(i*1.5f) + y,0));
		        	e.rotate.z = 180;
		        break;
				
				case EAST:
		        	e.moveTo(new Vertex(x + (i*1.5f),y,0));
		        	e.rotate.z = 90;
		        break;
		        	
				case WEST:
		        	e.moveTo(new Vertex(x + (i*-1.5f),y,0));
		        	e.rotate.z = -90;
		        break;        	
			}
			
        	dominoes.add(e);
        	superObject.add(e);
		}	
	}
	
	public void addCurveDominoes(float radius, float angle){
		Element3D e;
		
		float x = 0;
		float y = 0;
		
		float angleZ = 90 + angle;
		
		if (dominoes.size() > 1){
			x = dominoes.get(dominoes.size()-1).center.x;
			y = dominoes.get(dominoes.size()-1).center.y;
		}
		
		int number = (int)radius;
		
		float angleStep = (angle + 90) / number;
		
		for (int i = 1; i < number + 1; i++){
			angle += angleStep;
			angleZ += angleStep;
			
        	e = Element3D.createDomino("Domino" + dominoes.size(), renderer.gl);
        	
        	x += Math.cos(Math.toRadians(angle));
        	y += Math.sin(Math.toRadians(angle));
        	
        	e.moveTo(new Vertex(x, y, 0));
	
        	e.rotate.z = angleZ;
        	
        	dominoes.add(e);
        	superObject.add(e);
		}
	}
	
}
