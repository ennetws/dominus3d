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

	public Element3D superObject;
	
	public int fpsCap = 80;

	public Vector<Element3D> dominoes = new Vector<Element3D>();
	public static final int NORTH = 0;
	public static final int NW = 45;
	public static final int WEST = 90;
	public static final int SW = 135;
	public static final int SOUTH = 180;
	public static final int SE = 225;
	public static final int EAST = 270;
	public static final int NE = 335;
	
	public int startX = 0;
	public int startY = 0;
	
	public boolean shadowOn = false;
	
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
	    animator.setRunAsFastAsPossible(true);
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
		
		addLineDominoes(5, WEST);
		
        // Create Axis object
        e = Element3D.createAxis("MainAxis", 3.0f, renderer.gl);
        e.moveTo(new Vertex(-10,-10,0));
        superObject.add(e);
        
        e = Element3D.loadObj("media/objects/floor.obj", "media/textures/floor.jpg", "Floor",1, renderer.gl);
        e.moveTo(new Vertex(0,0,0));
        superObject.add(e);
        
        e = Element3D.loadObj("media/objects/rim.obj", "media/textures/rim.jpg", "Rim",1, renderer.gl);
        e.moveTo(new Vertex(0,0,0));
        superObject.add(e);
        
        e = Element3D.loadObj("media/objects/shadowTester.obj", "", "shadowTester",1, renderer.gl);
        e.moveTo(new Vertex(0,0,6));
        e.castShadow = true;
        superObject.add(e);
        
        //Setup shadow option
        superObject.setLightPos(this.renderer.defaultLightPos);
        
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
		
		Vertex v;
		
		int dirFrom;
		
		boolean capFlag;
		
		float x = 0;
		float y = 0;
		
		if (dominoes.size() > 1){
			x = dominoes.get(dominoes.size()-1).center.x;
			y = dominoes.get(dominoes.size()-1).center.y;
			capFlag = true;
		}else{
			x = startX;
			y = startY;
			capFlag = false;
		}
		
		for (int i = 0; i < number; i++){
        	e = Element3D.createDomino("Domino"+dominoes.size(), renderer.gl);
        	
			switch(direction){
				case NORTH:	
					
					e.setDirection(NORTH);
					
					if (capFlag == true) {
						
						capFlag = false;
						dirFrom = dominoes.get(dominoes.size()-1).getDirection();
						
						// dominoes.get(dominoes.size()-1).rotate.z = capDirection(dirFrom, NORTH);
						
						v = addDominoCap(dirFrom, NORTH, x, y);
						
						x = v.x;
						y = v.y;
						
						e.moveTo(new Vertex(x,((i + 1) *-1.5f) + y,0));	
						e.rotate.z = NORTH;
						
					}
					else
					{
					
						e.moveTo(new Vertex(x,((i + 1) *-1.5f) + y,0));	
						e.rotate.z = NORTH;
						
					}
					
		        break;
		        	
				case SOUTH:
					
					e.setDirection(SOUTH);
					
					if (capFlag == true) {
						capFlag = false;
						dirFrom = dominoes.get(dominoes.size()-1).getDirection();
						
						dominoes.get(dominoes.size()-1).rotate.z = capDirection(dirFrom, SOUTH);
						
					}
					
					e.moveTo(new Vertex(x,((i + 1) *1.5f) + y,0));
					e.rotate.z = SOUTH;
					
					
		        break;
				
				case EAST:
					
					e.setDirection(EAST);
					
					if (capFlag == true) {
						capFlag = false;
						dirFrom = dominoes.get(dominoes.size()-1).getDirection();
						
						dominoes.get(dominoes.size()-1).rotate.z = capDirection(dirFrom, EAST);
						
					}
					
					e.moveTo(new Vertex(x + ((i + 1) * -1.5f),y,0));
					e.rotate.z = EAST;
					
					
					
		        break;
		        	
				case WEST:
					
					e.setDirection(WEST);
					
					if (capFlag == true) {
						capFlag = false;
						dirFrom = dominoes.get(dominoes.size()-1).getDirection();
						
						dominoes.get(dominoes.size()-1).rotate.z = capDirection(dirFrom, WEST);
						
					}
					
					e.moveTo(new Vertex(x + ((i + 1) *1.5f),y,0));
					e.rotate.z = WEST;
					
					
		        break;        	
			}
			
			
        	dominoes.add(e);
        	superObject.add(e);
		}	
	}
	
	public int capDirection(int dirFrom, int dirTo) {
		
		if (dirFrom == NORTH && dirTo == WEST) {
			return NW;
		}
		else if (dirFrom == EAST && dirTo == NORTH) {
			return NE;
		}
		else if (dirFrom == NORTH && dirTo == EAST) {
			return NE;
		}	
		else if (dirFrom == WEST && dirTo == NORTH) {
			return NW;
		}
		else if (dirFrom == SOUTH && dirTo == WEST) {
			return SW;
		}
		else if (dirFrom == EAST && dirTo == SOUTH) {
			return SE;
		}
		else if (dirFrom == SOUTH && dirTo == EAST) {
			return SE;
		}
		else if (dirFrom == WEST && dirTo == SOUTH) {
			return SW;
		}
		else if ( (dirFrom == NORTH && dirTo == NORTH) ) {
			return NORTH;
		}
		else if ( (dirFrom == SOUTH && dirTo == SOUTH) ) {
			return SOUTH;
		}
		else if ( (dirFrom == EAST && dirTo == EAST) ) {
			return EAST;
		}
		else {
			return WEST;
		}
			
	}
	
	public Vertex addDominoCap(int dirFrom, int dirTo, float x, float y) {
		
		Element3D e1, e2;
		
		e1 = Element3D.createDomino("Domino"+dominoes.size(), renderer.gl);
		e2 = Element3D.createDomino("Domino"+dominoes.size()+1, renderer.gl);
		
		if (dirFrom == WEST && dirTo == NORTH) {
			e1.moveTo(new Vertex(x + 1.5f, y, 0));
			e1.rotateZ(NW + 15);
			
			e2.moveTo(new Vertex(x + 1.5f, y - 1.5f, 0));
			e2.rotateZ(NW);
		}
		
		dominoes.add(e1);
    	superObject.add(e1);
		
    	dominoes.add(e2);
    	superObject.add(e2);
		
    	return e2.center;
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
