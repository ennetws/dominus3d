package dominus;

import javax.media.opengl.GL;

/**
 * 2D element class for menus, text, object selection, etc.
 * 
 * @author cherz
 *
 */

public class Element2D extends Element {
	
	private int x, y;
	private int width, height, zIndex;
	
	public Element2D(String iden, int width, int height, int x, int y, GL gl){
		this(iden, null, width, height, x, y, gl);
	}
	
	public Element2D(String iden, Element2D parent, int width, int height, int x, int y, GL gl){
		this(iden, parent, gl);
		
		this.width = width;
		this.height = height;
		
		this.x = x;
		this.y = y;
	}

	public Element2D(String iden, Element2D parent, GL gl){
		super(iden, parent, gl);
	}
	
	public void render(){
		
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setWidth(int w){
		width = w;
	}
	
	public void setHeight(int h){
		height = h;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getzIndex(){
		return zIndex;
	}
	public void setzIndex(int z){
		this.zIndex = z;
	}
	
}
