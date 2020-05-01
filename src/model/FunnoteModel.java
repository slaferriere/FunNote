package model;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Set;

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
			currNotebook = new Notebook(newNotebook, name + ".funnote");
		} else {
			throw new IOException();
		}
	}
	
	/**
	 * 
	 * @param sectionName
	 */
	public void createSection(String sectionName) {
		currNotebook.addSection(sectionName);
		this.setChanged();
		this.notifyObservers("blank page");
	}
	
	/**
	 * 
	 * @param page
	 * @throws IOException
	 */
	public void createPage(String page) throws IOException {
		File photoLib = new File(currNotebook.location + File.separator + "pageImages");
		if(!photoLib.isDirectory()) throw new IOException();
		String numPage = Integer.toString(currNotebook.getNumPages());
		File imageWritten = new File(photoLib.getAbsolutePath() + File.separator + "page_" +
							numPage + ".png");
		imageWritten.createNewFile();
		currNotebook.currSection.addPage(page, "page_" + numPage + ".png");
		
		this.setChanged();
		this.notifyObservers("blank page");
	}
	
	public void addCurrentPage(String page, RenderedImage image) throws IOException {
		File photoLib = new File(currNotebook.location + File.separator + "pageImages");
		if(!photoLib.isDirectory()) {
			throw new IOException();
		}
		String numPage = Integer.toString(currNotebook.getNumPages());
		File imageWritten = new File(photoLib.getAbsolutePath() + File.separator + "page_" +
							numPage + ".png");
		ImageIO.write(image, "png", imageWritten);
		currNotebook.currSection.addPage(page, "page_" + numPage + ".png");
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
		return currNotebook != null;
	}
	
	public boolean hasPage() {
		if(currNotebook == null) return false;
		else if(currNotebook.currSection == null) return false;
		else if(currNotebook.currSection.currPage == null) return false;
		return true;
	}
	
	public List<String> getSectionList() {
		List<String> sectionList = new LinkedList<String>();
		Set<String> keySet = currNotebook.sections.keySet();
		Iterator<String> iter = keySet.iterator();
		while(iter.hasNext()) {
			sectionList.add(iter.next());
		}
		return sectionList;
	}
	
	public List<String> getPageList() {
		List<String> pageList = new LinkedList<String>();
		Set<String> keySet = currNotebook.currSection.pages.keySet();
		Iterator<String> iter = keySet.iterator();
		while(iter.hasNext()) {
			pageList.add(iter.next());
		}
		return pageList;
	}
	
	/**
	 * 
	 * @param notebook
	 * @param section
	 * @param page
	 */
	public void changePage(String page) {
		currNotebook.currSection.changePage(page);
		this.setChanged();
		this.notifyObservers(currNotebook.currSection.currPage);
	}
	
	public void changeSection(String section) {
		currNotebook.changeSection(section);
		this.setChanged();
		if(currNotebook.currSection.currPage == null) {
			this.notifyObservers("blank page");
		} else {
			this.notifyObservers(currNotebook.currSection.currPage);
		}
	}
	
	/**
	 * 
	 * @param notebook
	 */
	public void changeNotebook(String notebook) {
		// TODO: load notebook
	}
	
	public Notebook getNotebook() {
		return currNotebook;
	}
	
	/**
	 * 
	 * @param canvas
	 */
	public void save(RenderedImage image) throws IOException {
		String pathToDir = currNotebook.location;
		String pathToImage = pathToDir + File.separator + "pageImages" + 
			File.separator + currNotebook.currSection.currPage.canvasURL;
		File imageToWrite = new File(pathToImage);
		boolean written = ImageIO.write(image, "png", imageToWrite);
		if(!written) throw new IOException();
		
		try {
			FileOutputStream fileOut = new FileOutputStream(currNotebook.location + File.separator + currNotebook.fName);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(currNotebook);
			objectOut.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String getPageImageURL(String imageNum) {
		return currNotebook.location + File.separator + "pageImages"
				+ File.separator + imageNum;
	}
	
}
