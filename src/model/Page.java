package model;

import java.io.Serializable;

import java.util.List;
import java.util.LinkedList;

public class Page implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String canvasURL;
	protected List<TextboxNode> textboxes;
	
	public Page() {
		this.textboxes = new LinkedList<TextboxNode>();
	}
	
	public Page(String canvasURL) {
		this.canvasURL = canvasURL;
		this.textboxes = new LinkedList<TextboxNode>();
	}
	
	public String getCanvasURL() {
		return canvasURL;
	}
	
	public void updateCanvasURL(String canvasURL) {
		this.canvasURL = canvasURL;
	}
	
}
