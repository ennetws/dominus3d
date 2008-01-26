package dominus;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;


public class Driver {

	public static void main(String[] args)
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
	
	/**
	 * paint() taken from http://www.ibm.com/developerworks/java/library/j-j2int/
	 * See "An example of Java 2D at work"
	 */
	public void paint(Graphics g) {
        // Obtain a Graphics2D object
        Graphics2D g2 = (Graphics2D)g;
        
        // Set the rendering quality.
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        // define a linear color gradient
        GradientPaint gp = new GradientPaint(0, 60, Color.red,0, 120, Color.yellow);

        Ellipse2D r = new Ellipse2D.Float(30, 60, 160, 60);
        g2.setPaint(gp);
        g2.fill(r);

        

        // hello world
        // set rotation
        g2.transform(AffineTransform.getRotateInstance(Math.PI / 4));
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        g2.setPaint(Color.blue);
        // set compositing rule with transparency
        g2.setComposite(AlphaComposite.getInstance(
                                       AlphaComposite.SRC_OVER, 1.0f));
        
        g2.drawString("Hello World!",100,100);
       

        
    }
	

}
