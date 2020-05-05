package controller;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.text.Text;

import java.awt.image.RenderedImage;

import model.FunnoteModel;
import model.TextboxNode;

/**
 * 
 * The FunnoteController connects with the model and view and deals with all the logic
 * relating to saving and creating new pages, sections, and notebooks
 * 
 * @author Scott LaFerriere, Trevor Freudig, Michael Tuohy, Alex Thompson
 *
 */

public class FunnoteController {
	private FunnoteModel model;
	
	public FunnoteController(FunnoteModel model) {
		this.model = model;
	}
	
	/**
	 * Calls the model to create a new notebook
	 * 
	 * @param notebookName- notebook name
	 * @param dir- Directory of saved file
	 * @throws IOException
	 */
	public void addNewNotebook(String notebookName, File dir) throws IOException {
		model.createNotebook(notebookName, dir);
	}
	
	/**
	 * 
	 * Calls the model to create a new section
	 * 
	 * @param sectionName name of Section
	 */
	
	public void addNewSection(String sectionName) {
		model.createSection(sectionName);
	}
	
	/**
	 * Calls the model to create a new page
	 * 
	 * @param pageName name of Page
	 * @throws IOException
	 */
	
	public void addNewPage(String pageName) throws IOException {
		model.createPage(pageName);
	}
	
	/**
	 * Calls the model to add the current page to the section
	 * 
	 * @param pageName name of Page
	 * @param image Snapshot of canvas
	 * @throws IOException
	 */
	public void addCurrentPage(String pageName, RenderedImage image) throws IOException {
		model.addCurrentPage(pageName, image);
	}
	
	/**
	 * Returns whether or not the model has a Notebook
	 * 
	 * @return true if model contains a notebook, false otherwise
	 */
	public boolean hasNotebook() {
		return model.hasNotebook();
	}
	
	/**
	 * Returns whether or not the model has a Section
	 * 
	 * @return true if model contains a Section, false otherwise
	 */
	public boolean hasSection() {
		return model.hasSection();
	}
	
	/**
	 * Returns whether or not the model has a Page
	 * 
	 * @return true if model contains a Page, false otherwise
	 */
	public boolean hasPage() {
		return model.hasPage();
	}
	
	/**
	 * Returns a list of all the Pages in the current Section
	 * 
	 * @return List of Pages (Strings)
	 */
	public List<String> getPageList() {
		return model.getPageList();
	}
	
	/**
	 * Returns a list of all the Section in the current Notebook
	 * 
	 * @return List of Sections (Strings)
	 */
	public List<String> getSectionList() {
		return model.getSectionList();
	}
	
	/**
	 * This method will change the page to the string passed to it
	 * @param page String of which page is requested
	 */
	public void changePage(String page) {
		model.changePage(page);
	}
	
	/**
	 * This method will change to a new Section
	 * 
	 * @param section Selection that user wants to change to
	 */
	public void changeSection(String section) {
		model.changeSection(section);
	}
	
	/**
	 * This method will choose which notebook is being observed
	 * currently. This will affect what file is being saved
	 * @param notebook Which notebook to navigate to
	 */
	public void changeNotebook(File dir) {
		model.changeNotebook(dir);
	}
	/**
	 * This method tells the model to save the current notebook
	 */
	public void save(RenderedImage image) throws IOException {
		model.save(image);
	}
	
	/**
	 * Given an image number (should be the page.canvasURL), this method
	 * will return the url of the canvas image
	 * @param imageNum
	 * @return
	 */
	public String getPageURL(String imageNum) {
		return model.getPageImageURL(imageNum);
	}
	
	/**
	 * This method returns the current model to the View
	 * 
	 * @return FunnoteModel
	 */
	
	public FunnoteModel getModel() {
		return this.model;
	}
}
