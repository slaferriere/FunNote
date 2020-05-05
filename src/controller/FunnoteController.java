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

public class FunnoteController {
	private FunnoteModel model;
	
	public FunnoteController(FunnoteModel model) {
		this.model = model;
	}
	
	public void addNewNotebook(String notebookName, File dir) throws IOException {
		model.createNotebook(notebookName, dir);
	}
	
	public void addNewSection(String sectionName) {
		model.createSection(sectionName);
	}
	
	public void addNewPage(String pageName) throws IOException {
		model.createPage(pageName);
	}
	
	public void addCurrentPage(String pageName, RenderedImage image) throws IOException {
		model.addCurrentPage(pageName, image);
	}
	
	public boolean hasNotebook() {
		return model.hasNotebook();
	}
	
	public boolean hasSection() {
		return model.hasSection();
	}
	
	public boolean hasPage() {
		return model.hasPage();
	}
	
	public List<String> getPageList() {
		return model.getPageList();
	}
	
	public List<String> getSectionList() {
		return model.getSectionList();
	}
	
	/**
	 * This method will choose which notebook is being observed
	 * currently. This will affect what file is being saved
	 * @param notebook Which notebook to navigate to
	 */
	public void changeNotebook(File dir) {
		model.changeNotebook(dir);
	}
	
	public void changeSection(String section) {
		model.changeSection(section);
	}
	
	/**
	 * This method will change the page to the string passed to it
	 * @param page String of which page is requested
	 */
	public void changePage(String page) {
		model.changePage(page);
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
	
	public FunnoteModel getModel() {
		return this.model;
	}
}
