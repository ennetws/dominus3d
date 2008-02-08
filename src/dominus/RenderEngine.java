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
	private Light currentLight;
	private Light light2;
	private UI ui;
	private Element3D axis;
	
	private float rotateT = 0.0f;
	
	public int fps;
	private int fpsCounter;
	private long fpsEnd;
	
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

        currentLight = new Light();
        light2 = new Light();
        
        // Create the user interface manager
        ui = new UI (width, height, gl, glu, this.world);
        
        // Create Axis object   -- commented out my chris so i could commit the light and domino changes
   //     axis = Element3D.createAxis("MainAxis", 2.0f, gl);
        
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
        
        // create Ambient, Diffuse light and also create the light's position
        currentLight.createAmbient(gl, GL.GL_LIGHT1, 1.0f, 0.0f, 0.0f, 1.0f);
        currentLight.createDiffuse(gl, GL.GL_LIGHT1, 1.0f, 1.0f, 1.0f, 1.0f);
        currentLight.createPosition(gl, GL.GL_LIGHT1, 0.0f, 0.0f, 2.0f, 1.0f);
       
        // turn lighting on
        currentLight.lightState(gl, GL.GL_LIGHT1, true);
        currentLight.lightState(gl, GL.GL_LIGHTING, true);
        
        /* turn lighting off
        currentLight.lightState(gl, GL.GL_LIGHT1, true);
        currentLight.lightState(gl, GL.GL_LIGHTING, true);
        */
        
        for(int i = 0 ; i < 10 ; i++){
        	Element3D e = Element3D.createDomino("Domino"+i, gl);
        	e.moveTo(new Vertex(i*1.5f,i*1.5f,0));
        	e.rotateTo(new Vertex(0,0,i*5));
        	e.render();
        }
  
        Element3D e = Element3D.createGrid("Grid", 8, 1.0f, gl);

        e.setTransperncy(0.5f);
        e.renderWireframe();
        
        axis.render();
      
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
