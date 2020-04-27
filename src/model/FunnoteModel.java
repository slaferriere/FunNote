package model;

import java.util.Map;
import java.util.HashMap;
import java.util.Observable;

import javafx.scene.canvas.Canvas;

public class FunnoteModel extends Observable {
	
	private Notebook currNotebook;
	
	private Map<String, Notebook> notebooks = new HashMap<String, Notebook>();
	
	public FunnoteModel() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Canvas getCurrGC() {
		return currNotebook.currSection.currPage.getCanvas();
	}
	
	/**
	 * 
	 * @param notebook
	 * @param section
	 * @param page
	 */
	public void changePage(String notebook, String section, String page) {
		this.currNotebook = this.notebooks.get(notebook);
		Page newPage = currNotebook.getSection(section).changePage(page);
		this.setChanged();
		this.notifyObservers(newPage);
	}
	
	/**
	 * 
	 * @param notebook
	 */
	public void changeNotebook(String notebook) {
		this.currNotebook = this.notebooks.get(notebook);
		this.setChanged();
		this.notifyObservers(notebook);
	}
	
	/**
	 * 
	 * @param canvas
	 */
	public void save(Canvas canvas) {
		currNotebook.currSection.currPage.updateCanvas(canvas);
	}
}
