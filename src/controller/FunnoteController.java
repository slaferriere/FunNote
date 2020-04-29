package controller;

import java.io.File;
import java.io.IOException;

import model.FunnoteModel;

public class FunnoteController {
	private FunnoteModel model;
	
	public FunnoteController(FunnoteModel model) {
		this.model = model;
	}
	
	public void addNewNotebook(String notebookName, File dir) throws IOException{
		model.createNotebook(notebookName, dir);
	}
	
	public void addNewSection(String sectionName) {
		model.createSection(sectionName);
	}
	
	public void addNewPage(String pageName) {
		model.createPage(pageName);
	}
	
	public void addCurrentPage(String pageName, String canvasURL) {
		model.addCurrentPage(pageName, canvasURL);
	}
	
	public boolean hasNotebook() {
		return model.hasNotebook();
	}
	
	public boolean hasSection() {
		return model.hasSection();
	}
	
	/**
	 * This method will change the page to the string passed to it
	 * @param page String of which page is requested
	 */
	public void changePage(String notebook, String section, String page) {
		model.changePage(notebook, section, page);
	}
	
	/**
	 * This method will choose which notebook is being observed
	 * currently. This will affect what file is being saved
	 * @param notebook Which notebook to navigate to
	 */
	public void changeNotebook(String notebook) {
		model.changeNotebook(notebook);
	}
	/**
	 * This method tells the model to save the current notebook
	 */
	public void save(String canvasURL) {
		model.save(canvasURL);
	}
	
	public FunnoteModel getModel() {
		return this.model;
	}
}
