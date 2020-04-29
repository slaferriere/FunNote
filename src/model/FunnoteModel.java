package model;

import java.util.Map;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;

import javax.imageio.ImageIO;

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
	
	public void createPage(String page) throws IOException {
		File photoLib = new File(currNotebook.location + File.separator + "pageImages");
		if(!photoLib.isDirectory()) throw new IOException();
		File imageWritten = new File(photoLib.getAbsolutePath() + File.separator + "page_" +
							Integer.toString(currNotebook.getNumPages()));
		imageWritten.createNewFile();
		currNotebook.currSection.addPage(page, imageWritten.getAbsolutePath());
	}
	
	public void addCurrentPage(String page, RenderedImage image) throws IOException {
		File photoLib = new File(currNotebook.location + File.separator + "pageImages");
		if(!photoLib.isDirectory()) {
			throw new IOException();
		}
		File imageWritten = new File(photoLib.getAbsolutePath() + File.separator + "page_" +
							Integer.toString(currNotebook.getNumPages()));
		ImageIO.write(image, "png", imageWritten);
		currNotebook.currSection.addPage(page, imageWritten.getAbsolutePath());
		
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
	
	public boolean hasPage() {
		if(currNotebook == null) return false;
		else if(currNotebook.currSection == null) return false;
		else if(currNotebook.currSection.currPage == null) return false;
		return true;
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
	public void save(RenderedImage image) throws IOException {
		File imageToWrite = new File(currNotebook.currSection.currPage.canvasURL);
		boolean written = ImageIO.write(image, "png", imageToWrite);
		if(!written) throw new IOException();
	}
}
