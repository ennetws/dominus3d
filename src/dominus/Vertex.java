package dominus;
/**
 * Utiltily class to manage coordinates in 2D and 3D.
 * @author ibraheem
 *
 */
public class Vertex{
	public float x;
	public float y;
	public float z;
	
	public Vertex(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
}