package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import controller.FunnoteController;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import model.FunnoteModel;
import model.TextboxNode;

/**
 * This class collects all of the test methods for FunnoteController.java.
 * 
 * In eclipse, run using JUnit 5.
 * 
 * NOTE: AFTER EACH TEST, YOU MUST GO TO YOUR DESKTOP AND REMOVE THE CREATED NOTEBOOK
 * DIRECTORIES. 
 * 
 * @author Trevor Freudig, Alexander Thompson, Michael Tuohy, Scott LaFerriere
 * 
 */
public class FunnoteTests {

	private static final String TEMP_DIRECTORY = System.getProperty("user.home") + "/Desktop/";
	FunnoteModel model = new FunnoteModel();
	FunnoteController controller = new FunnoteController(model);
	File dir = new File(TEMP_DIRECTORY);
	
	@Test
	void testNotebook() throws IOException {
		controller.addNewNotebook("testNotebook", dir);
		controller.addNewSection("testSection");
		controller.addNewSection("testSection1");
		controller.addNewPage("testPage");
		controller.addNewPage("testPage1");
		
		assertTrue(controller.hasNotebook());
		assertTrue(controller.hasSection());
		assertTrue(controller.hasPage());
		assertEquals(controller.getModel().getNotebook().getURL(), "testNotebook.funnote");
		assertEquals(controller.getModel().getNotebook().getNotebookName(), "testNotebook");
		
		String temp = System.getProperty("user.home") + "\\Desktop\\testNotebook";
		assertEquals(controller.getModel().getNotebook().getLocation(), temp);
	}
	
	@Test
	void testChange() {
		controller.changeNotebook(new File(dir.getAbsolutePath() + "\\testNotebook"));
		controller.addNewSection("testSection");
		controller.addNewSection("testSection1");
		controller.changeSection("testSection1");
		controller.changePage("testPage1");
		
		assertEquals(model.getNotebook().getCurrSection(), model.getNotebook().getSections().get("testSection1"));
	}
	
	
	@Test
	void testGetPageList() throws IOException {
		controller.addNewNotebook("testNotebook2", dir);
		controller.addNewSection("testSection");
		controller.addNewPage("testPage");
		controller.addNewPage("testPage1");
		controller.changeSection("testSection");
		
		assertEquals(controller.getPageList().get(0), "testPage");
		
		model.getSection().updatePage("testUrl");
		assertEquals(model.getSection().getCanvasURL(), "testUrl");
		assertEquals(model.getCurrGC(), "testUrl");
	}
	
	@Test
	void testGetSectionList() throws IOException {
		controller.addNewNotebook("testNotebook3", dir);
		controller.addNewSection("testSection");
		controller.changeSection("testSection");
		
		assertEquals(controller.getSectionList().get(0), "testSection");
	}
	
	@Test
	void testTextBox() throws IOException {
		TextboxNode tbNode = new TextboxNode("sample text", 50, 50, 15, "blue");
		
		assertEquals(tbNode.getText(), "sample text");
		assertEquals(tbNode.getX(), 50, 1);
		assertEquals(tbNode.getY(), 50, 1);
		assertEquals(tbNode.getFontValue(), 15, 1);
		assertEquals(tbNode.getColor(), "blue");
		
		controller.addNewNotebook("testNotebook1", dir);
		controller.addNewSection("testSection");
		controller.addNewSection("testSection1");
		controller.addNewPage("testPage");
		controller.addNewPage("testPage1");
		controller.changeSection("testSection1");
		controller.changePage("testPage1");	
		controller.getModel().addTextBox(tbNode);
		
		assertEquals(controller.getModel().getTextBox(tbNode), tbNode);
		assertEquals(controller.getModel().getPage().getTextboxes().get(0), tbNode);
		
		controller.getModel().clearSavedTextBoxes();
		
		Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> controller.getModel().getTextBox(tbNode));
		assertEquals("Index: 0, Size: 0", exception.getMessage());
	}
	
	@Test
	void testGetPageUrl() {
		FunnoteModel model = new FunnoteModel();
		FunnoteController controller = new FunnoteController(model);
		
		assertEquals(model, controller.getModel());
	}
	
	@Test
	void getModelTest() {
		FunnoteModel model = new FunnoteModel();
		FunnoteController controller = new FunnoteController(model);
		
		assertEquals(model, controller.getModel());
	}
}
