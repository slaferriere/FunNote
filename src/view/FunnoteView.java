package view;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class is the main view class of the application. It is where all the objects
 * are stored and updated when the model tells it to update, as well as where
 * the class itself is setup to be used
 * @author Michael Tuohy, Trevor Freudig, Scott LaFerriere, Alexander Thompson
 *
 */
public class FunnoteView extends Application implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane bp = new BorderPane();
		
	}

}
