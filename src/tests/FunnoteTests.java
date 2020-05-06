package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import controller.FunnoteController;
import model.FunnoteModel;

/**
 * This class collects all of the test methods for FunnoteController.java.
 * 
 * In eclipse, run using JUnit 5.
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
	void testAdd() throws IOException {
		/**
		 * Must delete directory after each time running testcase for proper results
		 */
		controller.addNewNotebook("testNotebook", dir);
		controller.addNewSection("testSection");
		controller.addNewSection("testSection1");
		controller.addNewPage("testPage");
		controller.addNewPage("testPage1");
		
		assertTrue(controller.hasNotebook());
		assertTrue(controller.hasSection());
		assertTrue(controller.hasPage());
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
	void testSave() {

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
