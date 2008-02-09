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
		world.renderer.ui.writeLine("x="+ e.getX() + ", y=" + e.getY());
	}
	
	public void mousePressed(MouseEvent e){
		world.renderer.ui.writeLine("Button pressed="+ e.getButton());
	}
	
}

class Keyboard extends KeyAdapter{
	private World world;
	
	public Keyboard(World world){
		this.world = world;
	}
	
	public void keyPressed(KeyEvent e){
		world.renderer.ui.writeLine("Key pressed="+ e.getKeyChar());
	}
	
}
