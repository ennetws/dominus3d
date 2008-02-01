package dominus;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.*;

public class Driver {

	public static void main(String[] args)
	{
		Window w = new Window();
		w.setVisible(true);
		
		World world = new World();
		
	}
	
}

class Window extends JFrame {
	
	public Window()
	{
		this.setSize(640, 480);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
}
