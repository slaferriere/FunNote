package model;

import java.util.Map;
import java.util.HashMap;

import java.io.Serializable;

public class Section implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String, Page> pages;
	protected Page currPage;
	
	public Section() {
		pages = new HashMap<String, Page>();
	}

	public Page changePage(String page) {
		this.currPage = pages.get(page);
		return currPage;
	}
	
	public String getCanvasURL() {
		return currPage.getCanvasURL();
	}
	
	public void updatePage(String canvasURL) {
		this.currPage.updateCanvasURL(canvasURL);
	}
	
	public void addPage(String page) {
		pages.put(page, new Page());
	}
	
	public void addPage(String page, String canvasURL) {
		pages.put(page, new Page(canvasURL));
		currPage = pages.get(page);
	}

}
