package dominus;
/**
 * 
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
	
	public Vertex(Vertex v){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public void set(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void moveX(float offset){
		x += offset;
	}
	
	public void moveY(float offset){
		y += offset;
	}
	
	public void moveZ(float offset){
		z += offset;
	}
	
	public float distanceTo(Vertex v){
		return (float) Math.sqrt(((x - v.x) * (x - v.x))+
								 ((y - v.y) * (y - v.y))+
								 ((z - v.z) * (z - v.z)));
	}
	
	public String toString(){
		return "x: " + x + "\t, y: " + y +  "\t, z: " + z;
	}
}