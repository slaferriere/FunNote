package model;

import java.util.Map;
import java.util.HashMap;
import java.util.Observable;

public class FunnoteModel extends Observable {
	
	private Notebook currNotebook;
	
	private Map<String, Notebook> notebooks = new HashMap<String, Notebook>();
	
	public FunnoteModel() {
		
	}
	
	
	public GraphicsContext changePage(String Notebook, String section, String page) {
		return currNotebook.getSection(section).changePage(page);
	}
	
	
	public GraphicsContext changeSection(String section) {
		
	}
	
	
	public GraphicsContext changeNotebook(String notebook) {
		
	}
	
	public void save() {
		
	}
}
