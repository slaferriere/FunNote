package model;

import java.io.Serializable;

import java.util.Map;
import java.util.HashMap;

public class Notebook implements Serializable {
	

	private static final long serialVersionUID = 1L;
	protected Map<String, Section> sections;
	protected Section currSection;
	
	public Notebook() {
		this.sections = new HashMap<String, Section>();
	}
	
	public Page changePage(String section, String page) {
		this.currSection = sections.get(section);
		return this.currSection.changePage(page);
	}
	
	public void addSection(String section) {
		this.sections.put(section, new Section());
	}
	
	public void addPage(String section, String page, String canvasURL) {
		this.sections.get(section).addPage(page, canvasURL);
	}
	
	public Section getSection(String section) {
		this.currSection = this.sections.get(section);
		return this.currSection;
	}
}
