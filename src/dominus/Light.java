package dominus;

import javax.media.opengl.*;
import javax.media.opengl.GL;

/** Lighting class allows a mixture of ambient and diffuse lighting
 * Light position uses Vertex (x, y, z)
 * 
 * @author cherz
 *
 */

public class Light {

	private float[] lightAmbient;	// R G B A float vals for ambient light	
	private float[] lightDiffuse;	// R G B A float vals for diffuse light	
	private float[] lightPosition;	// LeftX RightX UpY DownY
	
	// only one light currently implemented: lightAmbient w/ lightDiffuse
	public Light() {

		lightAmbient = new float[4];
		lightDiffuse = new float[4];
		lightPosition = new float[4];
		
		lightAmbient[0] = 1.0f;
		lightAmbient[1] = 1.0f;
		lightAmbient[2] = 1.0f;
		lightAmbient[3] = 1.0f;
		
		lightDiffuse[0] = 1.0f;
		lightDiffuse[1] = 1.0f;
		lightDiffuse[2] = 1.0f;
		lightDiffuse[3] = 1.0f;
		
		lightPosition[0] = 0.0f;
		lightPosition[1] = 0.0f;
		lightPosition[2] = 2.0f;
		lightPosition[3] = 1.0f;
	
	}

	public void createAmbient(GL gl, int type, float amRed, float amGreen, float amBlue, float amAlpha) {
		
		lightAmbient[0] = amRed;
		lightAmbient[1] = amGreen;
		lightAmbient[2] = amBlue;
		lightAmbient[3] = amAlpha;
		
		gl.glLightfv(type, GL.GL_AMBIENT, lightAmbient, 0);
	  
	}
	
	public void createDiffuse(GL gl, int type, float difRed, float difGreen, float difBlue, float difAlpha) {
		
		lightDiffuse[0] = difRed;
		lightDiffuse[1] = difGreen;
		lightDiffuse[2] = difBlue;
		lightDiffuse[3] = difAlpha;
		
		gl.glLightfv(type, GL.GL_DIFFUSE, lightDiffuse, 0);
		
	}
	
	// float arr version
	public void createPosition(GL gl, int type, float leftX, float rightX, float upY, float downY) {
		
		lightPosition[0] = leftX;
		lightPosition[1] = rightX;
		lightPosition[2] = upY;
		lightPosition[3] = downY;
		
	    gl.glLightfv(type, GL.GL_POSITION, lightPosition, 0);	
	    
	}

	// turn light on or off
	public void lightState(GL gl, int lightType, boolean state) {
		
		if (state == true) {
			 gl.glEnable(lightType);
		}
		else {
			gl.glDisable(lightType);
		}

	}
	
}
