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

	private boolean lightEnabled = true; 	// turn light on / off
	
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
	
	public void lightState(boolean state) {
		lightEnabled = !lightEnabled;
	}
	
	public boolean switchLight(Light light) {
		
		lightEnabled = !lightEnabled;
		
		return lightEnabled;
		
	}
	
	public float[] createAmbient(float amRed, float amGreen, float amBlue, float amAlpha) {
		
		lightAmbient[0] = amRed;
		lightAmbient[1] = amGreen;
		lightAmbient[2] = amBlue;
		lightAmbient[3] = amAlpha;
		
		return lightAmbient;
	}
	
	public float[] createDiffuse(float difRed, float difGreen, float difBlue, float difAlpha) {
		
		lightDiffuse[0] = difRed;
		lightDiffuse[1] = difGreen;
		lightDiffuse[2] = difBlue;
		lightDiffuse[3] = difAlpha;
		
		return lightDiffuse;
	}
	
	// float arr version
	public float[] createPosition(float leftX, float rightX, float upY, float downY) {
		
		lightPosition[0] = leftX;
		lightPosition[1] = rightX;
		lightPosition[2] = upY;
		lightPosition[3] = downY;
		
		return lightPosition;
	}
	
	public Vertex createPosition(float x, float y, float z) {
		
		Vertex vertex = new Vertex(x, y, z);
		
		return vertex;
	}
	
}
