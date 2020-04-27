package model;

import java.util.Map;
import java.util.HashMap;

import javafx.scene.canvas.Canvas;

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
	
	public Canvas getGC() {
		return currPage.getCanvas();
	}
	
	public void updatePage(Canvas canvas) {
		this.currPage.updateCanvas(canvas);
	}
	
	public void addPage(String page, Canvas canvas) {
		pages.put(page, new Page(canvas));
		currPage = pages.get(page);
	}

}
