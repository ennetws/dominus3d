package dominus;

import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import javax.swing.event.*;

/**
 * This class handles user inputs from both the 
 * mouse and the keyboard.
 * @author ibraheem
 *
 */
public class InputEngine{
	public Mouse mouse;
	public Keyboard keyboard;
	
	public int x = 0;
	public int y = 0;
	
	public boolean MouseButtonPressed;
	public int MouseButtonNumber;
	public boolean MouseDragged;
	
	public InputEngine(World world){
		mouse = new Mouse(world);
		keyboard = new Keyboard(world);
	}
}

class Mouse extends MouseInputAdapter{
	private World world;
	
	public Mouse(World world){
		this.world = world;
	}
	
	public void mouseMoved(MouseEvent e) {
		world.input.x = e.getX();
		world.input.y = e.getY();
	}
	
	public void mousePressed(MouseEvent e){
		world.input.MouseButtonPressed = true;
		world.input.MouseButtonNumber = e.getButton();
		world.renderer.ui.manage();
		
		world.renderer.ui.writeLine("Button pressed="+ e.getButton());
	}
	
	public void mouseReleased(MouseEvent e){
		world.input.MouseButtonPressed = false;
		world.input.MouseDragged = false;
		world.renderer.ui.manage();
		
		world.renderer.ui.writeLine("Button Released.");
	}
	
	public void mouseDragged(MouseEvent e){
		world.input.MouseButtonPressed = true;
		world.input.MouseDragged = true;
		world.renderer.ui.manage();
		
		world.renderer.ui.writeLine("Mouse Dragged.");
	}
}

class Keyboard extends KeyAdapter{
	private World world;
	
	public Keyboard(World world){
		this.world = world;
	}
	
	public void keyPressed(KeyEvent e) {

		world.renderer.ui.writeLine("Key pressed="+ e.getKeyChar());
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			world.get("Domino1").moveX(0.25f);
			break;
			
		case KeyEvent.VK_RIGHT:
			world.get("Domino1").moveX(-0.25f);
            break;
            
		case KeyEvent.VK_UP:
			world.get("Domino1").moveY(-0.25f);
            break;
            
		case KeyEvent.VK_DOWN:
			world.get("Domino1").moveY(0.25f);
            break;
            
		case KeyEvent.VK_HOME:
			world.get("Domino1").moveZ(0.25f);
	        break;  
	        
		case KeyEvent.VK_INSERT:
			world.get("Domino1").moveZ(-0.25f);
	        break;  
	        
		case KeyEvent.VK_PAGE_UP:
			world.get("Domino1").rotateX(5);
            break;   
            
		case KeyEvent.VK_F:
			world.get("Domino0").rotateX(5);
            break;
            
		case KeyEvent.VK_PAGE_DOWN:
			world.get("Domino1").rotateY(5);
            break;  
            
		case KeyEvent.VK_END:
			world.get("Domino1").rotateZ(5);
	        break;    
		}
	}
}
