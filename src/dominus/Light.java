package dominus;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

/** Lighting class allows a mixture of ambient and diffuse lighting
 * Light position uses Vertex (x, y, z)
 * 
 * @author cherz
 *
 */

public class Light {

	private boolean lightEnabled; 	// turn light on / off
	
	private float[] lightAmbient;	// R G B A float vals for ambient light	
	private float[] lightDiffuse;	// R G B A float vals for diffuse light	
	private float[] lightPosition;	// LeftX RightX UpY DownY
	
	// only one light currently implemented: lightAmbient w/ lightDiffuse
	public Light(GL gl) {
		
		lightAmbient[0] = 0.5f;
		lightAmbient[1] = 0.5f;
		lightAmbient[2] = 0.5f;
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
	
	public boolean switchLight(Light light) {
		
		lightEnabled = !lightEnabled;
		
		return lightEnabled;
		
	}
	
	public void setAmbient(float amRed, float amGreen, float amBlue, float amAlpha) {
		
		lightAmbient[0] = amRed;
		lightAmbient[1] = amGreen;
		lightAmbient[2] = amBlue;
		lightAmbient[3] = amAlpha;
		
	}
	
	public void setDiffuse(float difRed, float difGreen, float difBlue, float difAlpha) {
		
		lightDiffuse[0] = difRed;
		lightDiffuse[1] = difGreen;
		lightDiffuse[2] = difBlue;
		lightDiffuse[3] = difAlpha;
		
	}
	
	public Vertex setPosition(float x, float y, float z) {
		
		Vertex vertex = new Vertex(x, y, z);
		
		return vertex;
	}
	
}
