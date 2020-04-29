package model;

import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;

public class FunnoteModel extends Observable {
	
	private Notebook currNotebook;
		
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
	
	public void createSection(String sectionName) {
		currNotebook.addSection(sectionName);
	}
	
	public void createPage(String page) {
		currNotebook.currSection.addPage(page);
	}
	
	public void addCurrentPage(String page, String canvasURL) {
		currNotebook.currSection.addPage(page, canvasURL);
	}
	
	public boolean hasSection() {
		if(currNotebook == null) {
			return false;
		} else if(currNotebook.currSection == null) {
			return false;
		}
		return true;
	}
	
	public boolean hasNotebook() {
		System.out.println("here");
		return currNotebook != null;
	}
	
	/**
	 * 
	 * @param notebook
	 * @param section
	 * @param page
	 */
	public void changePage(String notebook, String section, String page) {
		// TODO: load page
	}
	
	/**
	 * 
	 * @param notebook
	 */
	public void changeNotebook(String notebook) {
		// TODO: load notebook
	}
	
	/**
	 * 
	 * @param canvas
	 */
	public void save(String canvasURL) {
		currNotebook.currSection.currPage.updateCanvasURL(canvasURL);
	}
}
