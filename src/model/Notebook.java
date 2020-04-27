package model;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;

public class Notebook implements Serializable {
	

	private static final long serialVersionUID = 1L;
	protected List<Section> sections;
	protected Section currSection;
	
	public Notebook() {
		this.sections = new LinkedList<Section>();
	}
	
	public void changeSection(String section) {
		
	}
	
	public void addNewPage(String newPage) {
		this.currSection.addPage(newPage);
	}
}
