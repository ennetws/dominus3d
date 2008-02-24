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
	
	public GL gl;
	private static final GLU glu = new GLU();
	
	private World world;
	private int width;
	private int height;
	
	private Camera currentCamera;
	
	public UI ui;
	
	public int fps;
	public int fpsCounter;
	private long fpsEnd;
	
	float rotateT = 0.0f;
	
	public RenderEngine(int width, int height, World world){
		this.width = width;
		this.height = height;
		
		this.world = world;
	}
	
	public void init(GLAutoDrawable gLDrawable){
		gl = gLDrawable.getGL();
        
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
        
        // Load the world's objects
        world.loadWorld();
	}
	
	public void display(GLAutoDrawable drawable){
        gl = drawable.getGL();
      
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        
        currentCamera.set(gl);
        currentCamera.lookFrom(new Vertex(10,10,10));
        
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        //rotateT+= 0.1f; 
        
        Light point1 = new Light(1);
        point1.pointLight(gl, new Vertex(4,4,4));
        
        // Render all objects
        world.render(gl);
 
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
