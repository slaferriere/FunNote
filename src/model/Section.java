package model;

import java.util.Map;
import java.util.HashMap;

import java.io.Serializable;

/**
 * This class constructs a new Section Object containing methods to update the Page, add a new Page, or change the current Page
 * @author Trevor Freudig, Alexander Thompson, Michael Tuohy, Scott LaFerriere
 *
 */
public class Section implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Map<String, Page> pages;
	protected Page currPage;
	
	/**
	 * Instantiates the map of Page names with their respective Page object
	 */
	public Section() {
		pages = new HashMap<String, Page>();
	}

	/**
	 * This method changes the current Page
	 * @param page- new Page
	 * @return Page that was switched to
	 */
	public Page changePage(String page) {
		this.currPage = pages.get(page);
		return currPage;
	}
	
	/**
	 * This method returns the Canvas URL of the current Page
	 * @return
	 */
	public String getCanvasURL() {
		return currPage.getCanvasURL();
	}
	
	/**
	 * This method updates the current page using the Canvas URL
	 * @param canvasURL
	 */
	public void updatePage(String canvasURL) {
		this.currPage.updateCanvasURL(canvasURL);
	}
	
	/**
	 * This method adds a new Page to the current Section
	 * @param page- new Page name
	 * @param canvasURL- Canvas URL of current Page
	 */
	public void addPage(String page, String canvasURL) {
		pages.put(page, new Page(canvasURL));
		currPage = pages.get(page);
	}

}
