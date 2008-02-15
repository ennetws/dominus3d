package dominus;

import javax.swing.*;

public class Driver {

	public static void main(String[] args)
	{
		Window w = new Window();
	
		Thread world = new Thread(new World(w, 640, 480));
		
		world.start();
	}
	
}

@SuppressWarnings("serial")
class Window extends JFrame {
	
	public Window()
	{
		this.setSize(640, 480);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
}
