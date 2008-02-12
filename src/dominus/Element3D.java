package dominus;

import javax.media.opengl.GL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.*;

import com.sun.opengl.util.texture.*;
import static javax.media.opengl.GL.*;

/**
 * This class represent all visible 3D objects in the world.
 * @author ibraheem
 *
 */
public class Element3D extends Element {
	
	private Vector<Vertex> vertices = new Vector<Vertex>();
	private Vector<Vertex> texCoordinates = new Vector<Vertex>();
	
	private Vertex center;
	private Vertex rotate;
	private float scale = 1;
	
	// min and max verticies for element's bounding box
	private Vertex min;
	private Vertex max;
	
	private Texture texture = null;
	
	private int polyType = GL_QUADS;
	private int shadeMode = GL_SMOOTH;
	private boolean wireFrame = false;
	
	public Element3D(String iden, GL gl){
		this(iden, null, gl);
	}
	
	public Element3D(String iden, Element3D parent, GL gl){
		super(iden, parent, gl);
		
		center = new Vertex(0,0,0);
		rotate = new Vertex(0,0,0);
	}
	
	public void placeElement(){
		float parentX, parentY, parentZ;
		
        parentX = parentY = parentZ = 0;
        
        if (parent != null){
        	parentX = ((Element3D)parent).center.x;
        	parentY = ((Element3D)parent).center.y;
        	parentZ = ((Element3D)parent).center.z;
        }
        
		gl.glTranslatef(center.x + parentX, center.y + parentY, center.z + parentZ);
		
		gl.glRotatef(rotate.x, 1, 0, 0);
		gl.glRotatef(rotate.y, 0, 1, 0);
		gl.glRotatef(rotate.z, 0, 0, 1);
		
		gl.glScalef(scale, scale, scale);
	}
	
	public void render(){
		if (!visible || vertices.size() == 0)
			return;
		
		if (wireFrame){
			renderWireframe();
			return;
		}
		
        if (texture != null){  	
	    	texture.enable();
	    	texture.bind();
        }
		
		gl.glPushMatrix();
		placeElement();

        gl.glColor4f(1.0f, 1.0f, 1.0f, transperncy); 
        gl.glShadeModel(shadeMode);
        gl.glBegin(GL_QUADS);
	
	    renderAllVertices();
        
		gl.glEnd();
		gl.glPopMatrix();
		
		if (texture != null){
			texture.disable();
		}
	}
	
	private void renderAllVertices(){
		Iterator<Vertex> i = vertices.iterator();
		Iterator<Vertex> texCoord = texCoordinates.iterator();
		
		while (i.hasNext()){
			Vertex v = i.next();
			
			if (texCoord.hasNext() && texture != null){
				Vertex uv = texCoord.next();
				
				gl.glTexCoord2f(uv.x*texture.getWidth(),
								uv.y*texture.getHeight());
			}
			
			gl.glVertex3f(v.x, v.y, v.z);
		}
	}
	
	public void renderWireframe(){
		gl.glPushMatrix();

		placeElement();
	
    	gl.glDisable(GL_LIGHTING);
    	gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    	
    	gl.glBegin(polyType);
    	
        gl.glColor4f(0.75f, 0.75f, 0.75f, transperncy); 
        
        renderAllVertices();
    	
		gl.glEnd();
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		gl.glEnable(GL_LIGHTING);
		gl.glPopMatrix();
	}
	
	public void moveTo(Vertex v){ 
		center = v;
	}
	
	public void rotateTo(Vertex v){
		rotate = v;
	}
	
	public void scaleTo(float s){
		scale = s;
	}
	
	public static Element3D createGrid(String id, float length, float spacing, GL gl){
		Element3D e = new Element3D(id, gl);

		for (float y = 0 ; y < length; y += spacing){
			for (float x = 0; x < length; x += spacing){
				e.vertices.add(new Vertex(-(x*spacing) , -(y*spacing), 0.0f));
				e.vertices.add(new Vertex(-(x*spacing) , (y*spacing) , 0.0f));
				e.vertices.add(new Vertex((x*spacing) , (y*spacing), 0.0f));
				e.vertices.add(new Vertex((x*spacing) , -(y*spacing), 0.0f));
			}
		}
		
		return e;
	}
	
	// TODO: have domino create dots
	public static Element3D createDomino(String id, GL gl) {
		
		Element3D e = new Element3D(id, gl);
		
		e.vertices = Element3D.box(1.0f, 0.5f, 2.5f);
		
		return e;
	}
	
	public static Vector<Vertex> box(float width, float length, float height){
		Vector<Vertex> v = new Vector<Vertex>();
		
		width /= 2;
		length /= 2;
		
		v.add(new Vertex(width, length, 0)); // Bottom
		v.add(new Vertex(-width, length, 0));
		v.add(new Vertex(-width, -length, 0));
		v.add(new Vertex(width, -length, 0));
		
		v.add(new Vertex(width, length, height)); // Top
		v.add(new Vertex(-width, length, height));
		v.add(new Vertex(-width, -length, height));
		v.add(new Vertex(width, -length, height));
		
		v.add(new Vertex(width, -length, 0)); // Side1
		v.add(new Vertex(width, -length, height));
		v.add(new Vertex(width, length, height));
		v.add(new Vertex(width, length, 0));
		
		v.add(new Vertex(width, length, 0)); // Side2
		v.add(new Vertex(width, length, height));
		v.add(new Vertex(-width, length, height));
		v.add(new Vertex(-width, length, 0));

		v.add(new Vertex(-width, -length, 0)); // Side3
		v.add(new Vertex(-width, -length, height));
		v.add(new Vertex(-width, length, height));
		v.add(new Vertex(-width, length, 0));
		
		v.add(new Vertex(width, -length, 0)); // Side4
		v.add(new Vertex(width, -length, height));
		v.add(new Vertex(-width, -length, height));
		v.add(new Vertex(-width, -length, 0));
		
		return v;
	}
	
	public static Element3D createBox(String id, float width, 
			float length, float height, GL gl){
		
		Element3D e = new Element3D(id, gl);
		
		e.vertices = Element3D.box(width, length, height);
		
		return e;
	}
	
	public static Element3D createAxis(String id, float length, GL gl){
		Element3D e = new Element3D(id, gl);

		Element3D x = Element3D.createBox("X-Axis", length, 0.2f, 	0.2f , gl);
		Element3D y = Element3D.createBox("Y-Axis", 0.2f, 	length, 0.2f , gl);
		Element3D z = Element3D.createBox("Z-Axis", 0.2f, 	0.2f, 	length, gl);
		
		x.moveTo(new Vertex(length/2,0,0));
		x.shadeMode = GL_FLAT;
		e.add(x);
		
		y.moveTo(new Vertex(0,length/2,0));
		y.shadeMode = GL_FLAT;
		e.add(y);
		
		z.shadeMode = GL_FLAT;
		e.add(z);
		
		return e;
	}
	
	public static Element3D boundBox(Element3D e, GL gl) {
		
		Vertex v;
		Element3D bounds = new Element3D("bounds", gl);
		int numVectors = 0;
		
		float minX = 10000;
		float minY = 10000;
		float minZ = 10000; 
		float maxX = -10000;
		float maxY = -10000; 
		float maxZ = -10000;
		float width;
		float height;
		float length;
		
		numVectors = e.vertices.size();
		
		for (int i = 0; i < numVectors; i++) {
		
			v = e.vertices.get(i);
			
			// get min verts
			minX = getMinVertex(minX, v.x);
			minY = getMinVertex(minY, v.y);
			minZ = getMinVertex(minZ, v.z);
			
			// get max verts
			maxX = getMaxVertex(maxX, v.x);
			maxY = getMaxVertex(maxY, v.y);
			maxZ = getMaxVertex(maxZ, v.z);
		}
		
		width = Math.abs(maxX - minX);
		height = Math.abs(maxZ - minZ);
		length = Math.abs(maxY - minY);
		
		bounds = createBox("Bounds", width, length, height, gl);
		
		return bounds;
		
	}
	
	public static float getMinVertex(float min, float test) {
		
		if (test < min) {
			min = test;
		}
		
		return min;
	}
	
	public static float getMaxVertex(float max, float test) {
		
		if (test > max) {
			max = test;
		}
		
		return max;
	}
	
	public void setPolyType(int pType){
		polyType = pType;
	}
	
	public void setShadeMode(int mode){
		shadeMode = mode;
	}
	
	public void setWireframe(boolean b){
		wireFrame = b;
	}
	
	public static Element3D loadObj(String fileName, 
			String textureFile, String iden, GL gl){
		return Element3D.loadObj(fileName, textureFile, iden, 1.0f, gl);
	}
	
	public static Element3D loadObj(String fileName, 
			String textureFile, String iden, float size, GL gl){

		Element3D e = loadObjFile(iden, gl, fileName, textureFile, size);
		e.texture = loadTexture(textureFile);
		
		return e;
	}
	
	private static Element3D loadObjFile(String iden, GL gl, String fileName, String textureFile, float size){
		try{
			Element3D e = new Element3D(iden, gl);
			Vector<Vertex> vertices = new Vector<Vertex>();
			Vector<Vertex> texCoord = new Vector<Vertex>();
			Vector<Vertex> finalVertices = new Vector<Vertex>();
			Vector<Vertex> finalTexCoord = new Vector<Vertex>();	
			
			StringTokenizer st;
			String type;
			
			BufferedReader data = new BufferedReader(new FileReader(fileName));

			String line = data.readLine();
			
			while (line != null){
				st = new StringTokenizer(line, " ");
				type = "";	
				
				if (st.hasMoreElements()) 
					type = st.nextToken();

				if (type.equals("v")){
					float x = Float.valueOf(st.nextToken()) * size;
					float y = Float.valueOf(st.nextToken()) * size;
					float z = Float.valueOf(st.nextToken()) * size;
					
					vertices.add(new Vertex(x,z,y));
				}
				
				if (type.equals("vt")){
					texCoord.add(new Vertex(
							Float.valueOf(st.nextToken()),
							Float.valueOf(st.nextToken()),0));
				}
				
				if (type.equals("f")){
					int faceType = st.countTokens();
					
					for(int i = 0 ; i < faceType; i++){
						StringTokenizer num = new StringTokenizer(st.nextToken(), "/");

						finalVertices.add(vertices.get(Integer.valueOf(num.nextToken())-1));
						
						if (texCoord.size() > 0)
							finalTexCoord.add(texCoord.get(Integer.valueOf(num.nextToken())-1));
					}
				}
				
				line = data.readLine();
			}
			
			data.close();
			e.vertices = finalVertices;
			e.texCoordinates = finalTexCoord;
			
			return e;
		}catch(Exception e){
			System.out.println("Cannot load file:" + fileName + "("+e.getMessage()+")");
			return null;
		}
	}
	
	public static Texture loadTexture(String textureFile){
		try{
			if (textureFile.length() == 0)
				return null;
			
			Texture t = TextureIO.newTexture(new File(textureFile), false);
            t.setTexParameteri(GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            t.setTexParameteri(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			return t;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}
}
