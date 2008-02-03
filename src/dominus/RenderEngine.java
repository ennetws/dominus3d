package dominus;

import java.awt.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;

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
	
	private Color bgColor;
	 
	private float rotateT = 0.0f;
	
	public RenderEngine(int width, int height, World world){
		bgColor = new Color(0.0f, 0.0f, 0.0f);
		
		this.width = width;
		this.height = height;
		
		this.world = world;
	}
	
	public void init(GLAutoDrawable gLDrawable){
		gl = gLDrawable.getGL();
		
        gl.glShadeModel(GL.GL_SMOOTH);
        
        // bgColor contains the background color
        gl.glClearColor(bgColor.getRed()/255, 
        				bgColor.getGreen()/255, 
        				bgColor.getBlue()/255, 0.0f);

        // Set the OpenGL depth functions
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        
        // Set default render settings
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

        // Set the default camera
        currentCamera = new Camera(gl, glu, width, height);
        
        // Create the user interface manager
        ui = new UI (width, height, gl, glu, this.world);
	}
	
	public void display(GLAutoDrawable drawable){
        gl = drawable.getGL();
        
        currentCamera.set(gl);
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -5.0f);
 
        gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
 
        gl.glBegin(GL.GL_TRIANGLES);
 
        // Front
        gl.glColor3f(0.0f, 1.0f, 1.0f); 
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f); 
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glColor3f(0.0f, 0.0f, 0.0f); 
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
 
        // Right Side Facing Front
        gl.glColor3f(0.0f, 1.0f, 1.0f); 
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f); 
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glColor3f(0.0f, 0.0f, 0.0f); 
        gl.glVertex3f(0.0f, -1.0f, -1.0f);
 
        // Left Side Facing Front
        gl.glColor3f(0.0f, 1.0f, 1.0f); 
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f); 
        gl.glVertex3f(0.0f, -1.0f, -1.0f);
        gl.glColor3f(0.0f, 0.0f, 0.0f); 
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
 
        // Bottom
        gl.glColor3f(0.0f, 0.0f, 0.0f); 
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glColor3f(0.1f, 0.1f, 0.1f); 
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glColor3f(0.2f, 0.2f, 0.2f); 
        gl.glVertex3f(0.0f, -1.0f, -1.0f);
 
        gl.glEnd();
        
        rotateT+= 0.05f;
        
        // User interface is rendered last
        ui.render();
	}
	
	public void displayChanged(GLAutoDrawable drawable, 
			boolean modeChanged, boolean deviceChanged){
		
	}
	
	public void reshape(GLAutoDrawable drawable, 
			int x, int y, int width, int height){
	}
}
