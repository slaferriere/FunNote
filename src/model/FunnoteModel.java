package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class FunnoteModel extends Observable {
	
	private Notebook currNotebook;
	private Section currSection;
	private Page currPage;
	
	private List<Notebook> notebooks = new LinkedList<Notebook>();
	
	public FunnoteModel() {
		
	}
	
	
	public void changePage(String page) {
		
	}
	
	
	public void changeSection(String section) {
		
	}
	
	
	public void changeNotebook(String notebook) {
		
	}
	
	public void save() {
		
	}
}
