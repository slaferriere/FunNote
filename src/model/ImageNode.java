package model;

import java.io.Serializable;

public class ImageNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String imageURL;
	protected double x;
	protected double y;
	protected double width;
	protected double height;
	
	public ImageNode(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
