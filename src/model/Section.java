package model;

import java.util.Map;
import java.util.HashMap;

import javafx.scene.canvas.GraphicsContext;

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

	public GraphicsContext changePage(String page) {
		this.currPage = pages.get(page);
		return this.currPage.getGraphicsContext();
	}
	
	public void updatePage(GraphicsContext gc) {
		this.currPage.updateGraphicsContext(gc);
	}
	
	public void addPage(String page, GraphicsContext gc) {
		pages.put(page, new Page(gc));
		currPage = pages.get(page);
	}

}
