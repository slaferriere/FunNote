package controller;

import model.FunnoteModel;

public class FunnoteController {
	private FunnoteModel model;
	
	public FunnoteController(FunnoteModel model) {
		this.model = model;
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
}
