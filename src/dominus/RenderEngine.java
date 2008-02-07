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
	private float[] lightAmbient = { 0.25f, 0.25f, 1.0f, 1.0f };
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
		fpsEnd = System.currentTimeMillis();
        fpsCounter = 0;
	}
	
	public void display(GLAutoDrawable drawable){
        gl = drawable.getGL();
      
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        
        currentCamera.set(gl);
        currentCamera.lookFrom(new Vertex(10,10,10));
        
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        
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
        
        for(int i = 0 ; i < 10 ; i++){
        	Element3D e = Element3D.createDomino("Domino"+i, gl);
        	e.moveTo(new Vertex(i*1.5f,i*1.5f,0));
        	e.rotateTo(new Vertex(0,0,i*5));
        	e.render();
        }
  
        Element3D e = Element3D.createGrid("Grid", 8, 1.0f, gl);

        e.setTransperncy(0.5f);
        e.renderWireframe();
      
        rotateT+= 0.1f;
        
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
		
		long currTime = System.currentTimeMillis();
		
		if (currTime > fpsEnd){
			fps = fpsCounter;
			fpsEnd = currTime + 1000;
	        fpsCounter = 0;
		}
	}
}
