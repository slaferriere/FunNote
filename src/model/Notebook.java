package model;

import java.io.File;
import java.io.Serializable;

import java.util.Map;
import java.util.HashMap;

/**
 * This class initializes a Notebook Object which contains information regarding the Notebook's directory,
 * the name of the file, and the name of the Notebook. It also contains a HashMap which maps the name 
 * of the Section to the Section object
 * 
 * @author Michael Tuohy, Trevor Freudig, Alexander Thompson, Scott LaFerriere
 *
 */

public class Notebook implements Serializable {
	

	private static final long serialVersionUID = 1L;
	protected Map<String, Section> sections;
	protected Section currSection;
	protected String location;
	private int numPages = 0;
	protected String fName;
	private String notebookName;
	
	/**
	 * Constructs a new Notebook object
	 * @param location- path to directory
	 * @param fName- Name of File
	 * @param name- Name of Notebook
	 */
	public Notebook(File location, String fName, String name) {
		this.sections = new HashMap<String, Section>();
		this.location = location.getAbsolutePath();
		this.fName = fName;
		this.notebookName = name;
	}
	
	/**
	 * Reurns the HashMap that Maps the Section name to the Section object
	 * @return HashMap
	 */
	public HashMap<String, Section> getSections() {
		return (HashMap<String, Section>) sections;
	}
	
	/**
	 * This method returns the number of pages in the current Section
	 * @return number of Pages
	 */
	public int getNumPages() {
		return numPages++;
	}
	
	/**
	 * This method returns the file path 
	 * @return
	 */
	public String getURL() {
		return fName;
	}
	
	/**
	 * This method returns the current location of the Notebook
	 * @return location
	 */			
	public String getLocation() {
		return location;
	}
	
	/**
	 * This method returns the name of the Notebook
	 * @return
	 */
	public String getNotebookName() {
		return notebookName;
	}
	
	/**
	 * This method adds a new Section to the current Notebook
	 * @param section name of new Section
	 */
	public void addSection(String section) {
		this.sections.put(section, new Section());
		currSection = this.sections.get(section);
	}
	
	
	/**
	 * This method changes the current Section
	 * @param section Section being switched to
	 * @return current Section
	 */
	public Section changeSection(String section) {
		this.currSection = this.sections.get(section);
		return this.currSection;
	}
	
	/**
	 * This method gets the current section
	 * Used for testcase reasons
	 * @return current Section
	 */
	public Section getCurrSection() {
		return currSection;
	}
}
