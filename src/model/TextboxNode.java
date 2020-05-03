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
	protected double fontValue;
	protected String color;
	
	public TextboxNode(String text, double x, double y, double fontValue, String color) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.fontValue = fontValue;
		this.color = color;
	}
	
	public String getText() { return text; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getFontValue() { return fontValue; }
	public String getColor() { return color; }
	
}
