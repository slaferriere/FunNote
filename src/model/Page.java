package model;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;

public class Page implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected GraphicsContext gc;
	
	public Page(GraphicsContext gc) {
		this.gc = gc;
	}
	
	public GraphicsContext getGraphicsContext() {
		return gc;
	}
	
	public void updateGraphicsContext(GraphicsContext newGC) {
		gc = newGC;
	}
}
