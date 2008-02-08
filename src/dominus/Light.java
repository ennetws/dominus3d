package dominus;

import java.awt.Color;
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
		lightPosition = new float[3];
		
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
	
	}

	public void createAmbient(GL gl, int num, Color difColor, int type) {
		
		lightAmbient[0] = difColor.getRed() / 255;
		lightAmbient[1] = difColor.getGreen() / 255;
		lightAmbient[2] = difColor.getBlue() / 255;
		lightAmbient[3] = 1.0f;
		
		gl.glLightfv(num, GL.GL_AMBIENT, lightAmbient, type);
		
	}
	
	public void createDiffuse(GL gl, int num, Color difColor, int type) {
			
		lightDiffuse[0] = difColor.getRed() / 255;
		lightDiffuse[1] = difColor.getGreen() / 255;
		lightDiffuse[2] = difColor.getBlue() / 255;
		lightDiffuse[3] = 1.0f;
		
		gl.glLightfv(num, GL.GL_DIFFUSE, lightDiffuse, type);
		
	}
	
	// float arr version
	public void createPosition(GL gl, int num, float x, float y, float z) {
		
		lightPosition[0] = x;
		lightPosition[1] = y;
		lightPosition[2] = z;
		
	    gl.glLightfv(num, GL.GL_POSITION, lightPosition, 0);	
	    
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
	
	// world lighting - default using GL_LIGHT0
	public Light createWorldLight(GL gl) {
		
		Light worldLight = new Light();
		Color worldLightColor = new Color(0, 0, 0);
		
		// create Ambient, Diffuse light and also create the light's position
		worldLight.createAmbient(gl, GL.GL_LIGHT0, worldLightColor, 0);
		worldLight.createDiffuse(gl, GL.GL_LIGHT0, worldLightColor, 0);
		worldLight.createPosition(gl, GL.GL_LIGHT0, 500.0f, 500.0f, 500.0f);
       
        // turn lighting on
		worldLight.lightState(gl, GL.GL_LIGHT0, true);
		worldLight.lightState(gl, GL.GL_LIGHTING, true);

		return worldLight;
		
	}
	
}
