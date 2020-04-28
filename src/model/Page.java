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
	protected List<ImageNode> images;
	
	public Page(String canvasURL) {
		this.canvasURL = canvasURL;
		this.images = new LinkedList<ImageNode>();
	}
	
	public String getCanvasURL() {
		return canvasURL;
	}
	
	public void updateCanvasURL(String canvasURL) {
		this.canvasURL = canvasURL;
	}
	
}
