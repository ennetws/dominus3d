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
	}
	
}

class Window extends JFrame {
	
	public Window()
	{
		this.setSize(640, 480);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * paint() taken from http://www.ibm.com/developerworks/java/library/j-j2int/
	 * See "An example of Java 2D at work"  .. and also messing around with other
	 * methods within the Graphics and Graphics2D library
	 */
	public void paint(Graphics g) {
		
        // Obtain a Graphics2D object
        Graphics2D g2 = (Graphics2D)g;

        // dashed rounded rect
        float dash[] = {5.0f};
        BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        g2.setStroke(dashed);
        g2.draw(new RoundRectangle2D.Double(250, 90, 100, 50, 10, 10));
        
        // define a linear color gradient
        GradientPaint gp = new GradientPaint(0, 60, Color.red,0, 120, Color.yellow);

        Ellipse2D r = new Ellipse2D.Float(30, 60, 160, 60);
        g2.setPaint(gp);
        g2.fill(r);
        
        // hello world
        // set rotation
        //g2.transform(AffineTransform.getRotateInstance(Math.PI / 4));
        
        try{
        	g2.setFont(Font.createFont(Font.TRUETYPE_FONT, new File("media/slkscr.ttf")).deriveFont(9.0f));
        }
        catch(Exception e){
        	System.out.println(e.getMessage());
        }
        
        g2.setPaint(Color.blue);
        // set compositing rule with transparency
        g2.setComposite(AlphaComposite.getInstance(
                                       AlphaComposite.SRC_OVER, 1.0f));
        
        g2.drawString("Hello World!",100,100);
 
    }
}
