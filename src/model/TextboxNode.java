package model;

import java.io.Serializable;

public class TextboxNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String text;
	protected double x;
	protected double y;
	protected double width;
	protected double height;
	
	public TextboxNode(String text, double x, double y, double width, double height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
}
