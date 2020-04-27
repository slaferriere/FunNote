package model;

import java.util.List;
import java.util.LinkedList;
import java.io.Serializable;

public class Section implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected List<Page> pages;
	protected Page currPage;
	
	public Section() {
		pages = new LinkedList<Page>();
	}
	
	public void addPage(String newPage) {
		
	}
	
	public void changePage(String page) {
		
	}
	
}
