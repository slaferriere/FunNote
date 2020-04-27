package model;

import java.io.Serializable;

import javafx.scene.canvas.Canvas;

import java.util.List;
import java.util.LinkedList;

public class Page implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Canvas canvas;
	protected List<ImageNode> images;
	
	public Page(Canvas canvas) {
		this.canvas = canvas;
		this.images = new LinkedList<ImageNode>();
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void updateCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	
}
