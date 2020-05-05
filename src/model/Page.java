package model;

import java.io.Serializable;

import java.util.List;
import java.util.LinkedList;

/**
 * This class constructs a new Page object
 * @author Trevor Freudig, Alexander Thompson, Michael Tuohy, Scott LaFerriere
 *
 */

public class Page implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String canvasURL;
	protected List<TextboxNode> textboxes;
	
	/**
	 * Constructor for a Page Object
	 * @param canvasURL- snapshot of Canvas
	 */
	
	public Page(String canvasURL) {
		this.canvasURL = canvasURL;
		this.textboxes = new LinkedList<TextboxNode>();
	}
	
	/**
	 * This method returns the URL of the Canvas object of the current Page
	 * @return Canvas URL String name
	 */
	
	public String getCanvasURL() {
		return canvasURL;
	}
	
	
	/**
	 * This method updates the URL String path of the canvas
	 * @param canvasURL
	 */
	public void updateCanvasURL(String canvasURL) {
		this.canvasURL = canvasURL;
	}
	
	/**
	 * This method returns a list of all the Textbox Objects
	 * @return List of all Textbox Object
	 */
	public List<TextboxNode> getTextboxes() {
		return textboxes;
	}
	
}
