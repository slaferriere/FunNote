package model;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import javax.imageio.ImageIO;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.EOFException;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * This is the model of the application. The main method of the model is to create new pages, sections, and notebooks
 * as well as save them to a directory on the user's device.
 * 
 * @author Scott LaFerriere, Trevor Freudig, Alexander Thompson, Michael Tuohy
 *
 */

public class FunnoteModel extends Observable {
	
	private Notebook currNotebook;
	public int sectionCount;
		
	public FunnoteModel() {
		
	}
	
	/**
	 * This method returns the canvas of the current Page, Section, and Notebook
	 * @return URL of JavaFX canvas
	 */
	public String getCurrGC() {
		return currNotebook.currSection.currPage.getCanvasURL();
	}
	
	/**
	 * This method creates a new notebook in the user inputed directory. It throws an IOException if the directory
	 * already exists. If not, it creates a new directory and Notebook
	 * 
	 * 
	 * @param name- name of Notebook
	 * @param dir- directory of where to create new Notebook
	 * @throws IOException
	 */
	public void createNotebook(String name, File dir) throws IOException {
		String path = dir.getAbsolutePath() + File.separator + name;
		File newNotebook = new File(path);
		if(newNotebook.mkdir()) {
			File photoLib = new File(path + File.separator + "pageImages");
			if(!photoLib.mkdir()) {
				throw new IOException();
			}
			File notebookF = new File(path + File.separator + "notebook.funnote");
			if(!notebookF.createNewFile()) {
				throw new IOException();
			}
			currNotebook = new Notebook(newNotebook, name + ".funnote", name);
			try {
				FileOutputStream fileOut = new FileOutputStream(currNotebook.location + File.separator + "notebook.funnote");
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				objectOut.writeObject(currNotebook);
				objectOut.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		} else {
			throw new IOException();
		}
	}
	
	/**
	 * This method creates a new Section and adds that section to the current Notebook
	 * @param sectionName- name of Section
	 */
	public void createSection(String sectionName) {
		currNotebook.addSection(sectionName);
		this.setChanged();
		this.notifyObservers("blank page");
	}
	
	/**
	 * This method creates a new Page in the current Section. It also numbers the page number accordingly based
	 * on how many current Pages there are.
	 * @param page- Name of Page
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
	
	/**
	 * This method takes a previously saved Page and redraws that canvas onto the current Page.
	 * It then adds the Page to the current Section
	 * 
	 * 
	 * @param page- name of saved Page
	 * @param image- image of Canvas
	 * @throws IOException
	 */
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
	
	/**
	 * This method returns whether or not the current Notebook contains a Section
	 * 
	 * @return true if Notebook contains a Section, false otherwise
	 */
	public boolean hasSection() {
		if(currNotebook == null) {
			return false;
		} else if(currNotebook.currSection == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method returns whether or not there is a Notebook currently generated
	 * @return true if there is a Notebook, false otherwise
	 */
	public boolean hasNotebook() {
		return currNotebook != null;
	}
	
	/**
	 * This method returns whether or not the current Notebook has a Section/Page
	 * @return true if Notebook contains a Page, false otherwise
	 */
	public boolean hasPage() {
		if(currNotebook == null) return false;
		else if(currNotebook.currSection == null) return false;
		else if(currNotebook.currSection.currPage == null) return false;
		return true;
	}
	
	/**
	 * This method returns a list of all the Sections in the current Notebook. It uses an iterator to go through all of
	 * the sections in the current Notebook and returns a list of those Sections
	 * 
	 * @return List of Strings of Section names
	 */
	public List<String> getSectionList() {
		List<String> sectionList = new LinkedList<String>();
		Set<String> keySet = currNotebook.sections.keySet();
		Iterator<String> iter = keySet.iterator();
		while(iter.hasNext()) {
			sectionList.add(iter.next());
		}
		return sectionList;
	}
	
	/**
	 * This method returns a list of all the Pages in the current Section. It uses an iterator to go through all of
	 * the Pages in the current Section and returns a list of those Pages
	 * @return List of Strings of Page names
	 */
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
	 * @param dir
	 * This method changes the current Page
	 * @param page- Desired Page to switch to
	 */
	public void changePage(String page) {
		currNotebook.currSection.changePage(page);
		this.setChanged();
		this.notifyObservers(currNotebook.currSection.currPage);
	}
	
	/**
	 * This method changes the current Section. It first determines if the Section is empty or not and then changes to
	 * said Section
	 * @param section- Desired Section to switch to
	 */
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
	 * This method changes the current Notebook. It uses a File and Object Input Stream to change the desired Notebook
	 * @param dir- directory of desired Notebook
	 */
	public void changeNotebook(File dir){
		try {
			FileInputStream input = new FileInputStream(dir.getAbsolutePath() + File.separator +
					"notebook.funnote");
			ObjectInputStream objectIn = new ObjectInputStream(input);
			currNotebook = (Notebook) objectIn.readObject();
			objectIn.close();
			this.setChanged();
			if(this.hasPage()) {
				this.notifyObservers(currNotebook.currSection.currPage);
			} else {
				this.notifyObservers("blank page");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method returns the current Notebook
	 * @return- current Notebook
	 */
	public Notebook getNotebook() {
		return currNotebook;
	}
	
	/**
	 * This method clears all the saved Textboxes on the current Page
	 * 
	 */
	public void clearSavedTextBoxes() {
		currNotebook.currSection.currPage.textboxes.clear();
	}
	
	/**
	 * This method adds a new TextboxNode to the page
	 * @param node
	 */
	public void addTextBox(TextboxNode node) {
		currNotebook.currSection.currPage.textboxes.add(node);
	}
	
	/**
	 * This method saves a snapshot of the canvas to the current Notebook directory. Writes a FileOutputStream
	 * to the user defined directory
	 * @param current state of canvas
	 */
	public void save(RenderedImage image) throws IOException {
		String pathToDir = currNotebook.location;
		String pathToImage = pathToDir + File.separator + "pageImages" + 
			File.separator + currNotebook.currSection.currPage.canvasURL;
		File imageToWrite = new File(pathToImage);
		boolean written = ImageIO.write(image, "png", imageToWrite);
		if(!written) throw new IOException();
		
		try {
			FileOutputStream fileOut = new FileOutputStream(currNotebook.location + File.separator + "notebook.funnote");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(currNotebook);
			objectOut.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * This method returns the path of the current Page
	 * 
	 * @param imageNum- current Page
	 * @return path to current Page
	 */
	public String getPageImageURL(String imageNum) {
		return currNotebook.location + File.separator + "pageImages"
				+ File.separator + imageNum;
	}
	
}
