package model;

import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;

public class FunnoteModel extends Observable {
	
	private Notebook currNotebook;
	
	private Map<String, Notebook> notebooks = new HashMap<String, Notebook>();
	
	public FunnoteModel() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCurrGC() {
		return currNotebook.currSection.currPage.getCanvasURL();
	}
	
	public void createNotebook(String name, File dir) throws IOException {
		String path = dir.getAbsolutePath() + File.separator + name;
		File newNotebook = new File(path);
		if(newNotebook.mkdir()) {
			File photoLib = new File(path + File.separator + "pageImages");
			if(!photoLib.mkdir()) {
				throw new IOException();
			}
			File notebookF = new File(path + File.separator + name + ".funnote");
			if(!notebookF.createNewFile()) {
				throw new IOException();
			}
			currNotebook = new Notebook(newNotebook);
		} else {
			throw new IOException();
		}
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
	public void save(String canvasURL) {
		currNotebook.currSection.currPage.updateCanvasURL(canvasURL);
	}
}
