package model;

import java.util.Map;
import java.util.HashMap;
import java.util.Observable;

import javafx.scene.canvas.GraphicsContext;

public class FunnoteModel extends Observable {
	
	private Notebook currNotebook;
	
	private Map<String, Notebook> notebooks = new HashMap<String, Notebook>();
	
	public FunnoteModel() {
		
	}
	
	public GraphicsContext getCurrGC() {
		return currNotebook.currSection.currPage.getGraphicsContext();
	}
	
	public void changePage(String notebook, String section, String page) {
		this.currNotebook = this.notebooks.get(notebook);
		Page newPage = currNotebook.getSection(section).changePage(page);
		this.setChanged();
		this.notifyObservers(newPage);
	}
	
	public void changeNotebook(String notebook) {
		this.currNotebook = this.notebooks.get(notebook);
		this.setChanged();
		this.notifyObservers(notebook);
	}
	
	public void save() {
		
	}
}
