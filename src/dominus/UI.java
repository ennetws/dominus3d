/**
 * 
 */
package dominus;

import javax.media.opengl.GL;

/**
 * Generates and manages UI elements including message boxes, menus
 * buttons, cursor, labels, etc.
 * 
 * @author ibraheem
 *
 */

public class UI {
	private Element2D contentPanel;
	
	public UI(int width, int height, GL gl){
		contentPanel = new Element2D("contentPanel", width, height, 0, 0, gl);
		
		contentPanel.renderAll();
	}
}
