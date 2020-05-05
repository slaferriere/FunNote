package model;

import java.io.Serializable;

/**
 * This method constructs a TextboxNode containing information regarding the Textbox location, font size, and font color
 * @author Trevor Freudig, Alexander Thompson, Michael Tuohy, Scott LaFerriere
 *
 */
public class TextboxNode implements Serializable {


	private static final long serialVersionUID = 1L;

	protected String text;
	protected double x;
	protected double y;
	protected double fontValue;
	protected String color;
	
	/**
	 * Constructor method for TextboxNode
	 * @param text String value of text
	 * @param x- Integer value of X-coordinate
	 * @param y- Integer value of Y-coordinate
	 * @param fontValue- Integer value of font size
	 * @param color- Color name
	 */
	
	public TextboxNode(String text, double x, double y, double fontValue, String color) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.fontValue = fontValue;
		this.color = color;
	}
	
	/**
	 * Getter methods for the TextboxNode
	 * @return
	 */
	public String getText() { return text; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getFontValue() { return fontValue; }
	public String getColor() { return color; }
	
}
