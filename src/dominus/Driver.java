package dominus;

import javax.swing.*;


public class Driver {

	public static void main()
	{
		
		Window w = new Window();
		w.setVisible(true);
	}
	
}

class Window extends JFrame {
	
	public Window()
	{
		this.setSize(640, 480);
	
	}

}
