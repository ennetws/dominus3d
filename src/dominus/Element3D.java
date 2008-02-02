package dominus;

import javax.media.opengl.GL;

/**
 * This class represent all visible 3D objects in the world.
 * @author ibraheem
 *
 */
public class Element3D extends Element {
	
	public Element3D(String iden, GL gl){
		super(iden, null, gl);
	}
	
	public Element3D(String iden, Element3D parent, GL gl){
		super(iden, parent, gl);
	}
	
	public void render(){
		
	}
}
