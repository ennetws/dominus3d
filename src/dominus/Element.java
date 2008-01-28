package dominus;

/**
 * Parent class for 2D and 3D elements.
 * 
 * Attributes:
 * 	id			A textual identifier to identify distinct objects
 * 
 * @author cherz
 * @author ibraheem
 */

public class Element {
	
	private String id;
	private boolean visible;
	
	public Element(String iden){
		this.id = iden;
		this.visible = true;
	}
}
