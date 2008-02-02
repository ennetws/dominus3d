package dominus;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

/**
 * This class represent cameras.
 * 
 * @author Ibraheem
 *
 */

public class Camera {
	private float aspectRatio;
	private float fovAngle;
	private float nearZ;
	private float farZ;
	GLU glu;
	
	public Camera(GL gl, GLU glu, int height, int width){
        this.glu = glu;
        
		fovAngle = 50.0f;

        if (height <= 0)
        	height = 1;

        aspectRatio = (float)width / (float)height;
        nearZ = 1.0f;
        farZ = 1000.0f;
        
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        
        //set up a perspective projection matrix
        glu.gluPerspective(fovAngle, aspectRatio , nearZ, farZ);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        
        gl.glLoadIdentity();
	}
	
	public void set(GL gl){
	    gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        
        //set up a perspective projection matrix
        glu.gluPerspective(fovAngle, aspectRatio , nearZ, farZ);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        
        gl.glLoadIdentity();
	}
}
