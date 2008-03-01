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
	
	public Vertex shiftedX(float offset){
		Vertex v = new Vertex(x,y,z);
		v.x += offset;
		return v;
	}
	
	public Vertex shiftedY(float offset){
		Vertex v = new Vertex(x,y,z);
		v.y += offset;
		return v;
	}
	
	public Vertex shiftedZ(float offset){
		Vertex v = new Vertex(x,y,z);
		v.z += offset;
		return v;
	}
}

class Vertex4 extends Vertex{
	float w = 1;
	public Vertex4(float x, float y, float z, float w){
		super(x,y,z);
		this.w = w;
	}
	
	public Vertex4(Vertex4 src){
		this(src.x,src.y,src.z,src.w);
	}
}

