package dominus;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import static javax.media.opengl.GL.*;

/**
 * The RenderEngine class will render all elements within the World
 * 
 * @author cherz
 *
 */

public class RenderEngine implements GLEventListener{
	
	private GL gl;
	private static final GLU glu = new GLU();
	private World world;
	
	private int width;
	private int height;
	
	private Camera currentCamera;
	private UI ui;
	
	private float rotateT = 0.0f;
	
	public int fps;
	private int fpsCounter;
	private long fpsEnd;

	
	// ax these one the Light class is working properly
	private float[] lightAmbient = { 0.5f, 0.5f, 1.0f, 1.0f };
    private float[] lightDiffuse = { 0.5f, 0.5f, 1.0f, 1.0f };
    private float[] lightPosition = { 0.0f, 0.0f, 1.0f, 1.0f };
	
	
	public RenderEngine(int width, int height, World world){
		this.width = width;
		this.height = height;
		
		this.world = world;
	}
	
	public void init(GLAutoDrawable gLDrawable){
		gl = gLDrawable.getGL();
        gl.glShadeModel(GL_SMOOTH);
        
        // bgColor contains the background color
        gl.glClearColor(0.5f, 0.5f, 0.5f, 0.0f);

        // Set the OpenGL depth functions
        gl.glClearDepth(1.0f);
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        
        // Set default render settings
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        // Set the default camera
        currentCamera = new Camera(gl, glu, width, height);
        
        // Create the user interface manager
        ui = new UI (width, height, gl, glu, this.world);
        
        // Initialize FPS counter
		fpsEnd = System.nanoTime();
        fpsCounter = 0;
	}
	
	public void display(GLAutoDrawable drawable){
        gl = drawable.getGL();
     
        
        currentCamera.set(gl);
        
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        
        gl.glTranslatef(0.0f, 0.0f, -5.0f);

        gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
   
        Element3D domino = new Element3D("Domino", gl);
     
        domino = domino.createDomino("Domino", gl);
        domino.render();
        
        Element3D e = new Element3D("Grid", gl);
        e = e.createGrid("Grid", 1, 1, gl);
        
        e.render();
        
        /*
        Light light = new Light();
        
        lightAmbient = light.createAmbient(1.0f, 0.5f, 0.5f, 1.0f);
        lightDiffuse = light.createDiffuse(1.0f, 1.0f, 1.0f, 1.0f);
        lightPosition = light.createPosition(0.0f, 0.0f, 2.0f, 1.0f);
        */
        
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, lightAmbient, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, lightDiffuse, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPosition, 0);
        gl.glEnable(GL.GL_LIGHT1);
        gl.glEnable(GL.GL_LIGHTING);	
        
        //gl.glDisable(GL_LIGHTING);
        
        rotateT+= 0.5f;
        
        // Calculate Frames Per Second
        calcFPS();
        
        // User interface is rendered last
        ui.render();
	}
	
	public void displayChanged(GLAutoDrawable drawable, 
			boolean modeChanged, boolean deviceChanged){
		
	}
	
	public void reshape(GLAutoDrawable drawable, 
			int x, int y, int width, int height){
	}
	
	public void calcFPS(){
		fpsCounter++;
		
		if (System.nanoTime() > fpsEnd){
			fps = fpsCounter;
			fpsEnd = System.nanoTime() + 1000000000l;
	        fpsCounter = 0;
		}
	}
}
