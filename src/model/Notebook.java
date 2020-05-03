package model;

import java.io.File;
import java.io.Serializable;

import java.util.Map;
import java.util.HashMap;

public class Notebook implements Serializable {
	

	private static final long serialVersionUID = 1L;
	protected Map<String, Section> sections;
	protected Section currSection;
	protected String location;
	private int numPages = 0;
	protected String fName;
	private String notebookName;
	
	public Notebook(File location, String fName, String name) {
		this.sections = new HashMap<String, Section>();
		this.location = location.getAbsolutePath();
		this.fName = fName;
		this.notebookName = name;
	}
	
	public HashMap<String, Section> getSections() {
		return (HashMap<String, Section>) sections;
	}
	
	public int getNumPages() {
		return numPages++;
	}
	
	public String getURL() {
		return fName;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getNotebookName() {
		return notebookName;
	}
	
	public Page changePage(String section, String page) {
		this.currSection = sections.get(section);
		return this.currSection.changePage(page);
	}
	
	public void addSection(String section) {
		this.sections.put(section, new Section());
		currSection = this.sections.get(section);
	}
	
	public void addPage(String section, String page, String canvasURL) {
		this.sections.get(section).addPage(page, canvasURL);
	}
	
	public Section changeSection(String section) {
		this.currSection = this.sections.get(section);
		return this.currSection;
	}
}
