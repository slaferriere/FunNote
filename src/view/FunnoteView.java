package view;

import java.util.Observable;
import java.util.Observer;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FunnoteView extends Application implements Observer {
	
	private MenuBar mainMenuBar = new MenuBar();
	private Canvas canvas = new Canvas(1900, 1000);
	private GraphicsContext graphicsContext;
	private StackPane stackPane = new StackPane();
	private VBox vbox = new VBox();
	private ComboBox<Integer> fontSizesBox;
	private ComboBox<String> fontColorBox;
	private ComboBox<Integer> penSizesBox;
	private ComboBox<String> penColorBox;
	private ToolBar toolBar;
	private Label fontSizeLabel = new Label("Font Size:");
	private Label fontColorLabel = new Label("Font Color:");
	private Label penSizeLabel = new Label("Pen Size:");
	private Label penColorLabel = new Label("Pen Color:");
	private int currentFontSize = 8;
	private String currentFontColor = "Red";
	private int currentPenSize = 5;
	private String currentPenColor = "Red";
	private Integer fontSizes[] = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72};
	private Integer penSizes[] = {5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24};
	private String colors[] = {"Red", "Orange", "Yellow", "Green", "Blue", "Purple", "Pink", "Black"};
	
	/**
	 * This method is called by FunNote.java and initializes 
	 * the main window/canvas. This method "starts" the program.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane window = new BorderPane();
		
		createMenuBar();
		window.setTop(mainMenuBar);
		
		createToolBar();
		window.setBottom(toolBar);
		
		// Canvas setup
		graphicsContext = canvas.getGraphicsContext2D();
		
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				graphicsContext.beginPath();
				graphicsContext.moveTo(e.getX(), e.getY());
				graphicsContext.stroke();
				draw(graphicsContext);
			}
		});
		
		canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				graphicsContext.lineTo(e.getX(), e.getY());
				graphicsContext.stroke();
			}
		});
		
		canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
			}
		});
		
		stackPane.setStyle("-fx-background-color: white");
		stackPane.getChildren().add(canvas);
		
		// Left vbox setup
//		vbox.setPadding(new Insets(5, 5, 5, 5));	
//		window.setLeft(vbox);
//		window.setPadding(new Insets(5, 5, 5, 5));
		
		window.setCenter(canvas);
	
		// Create the primary scene
		Scene scene = new Scene(window, 1500, 1000); 
		// Give stage a title
	    primaryStage.setTitle("FunNote"); 
	    // Add scene to stage
	    primaryStage.setScene(scene); 
	    // Set stage to be full screen
	 	primaryStage.setFullScreen(true);
	    //Display stage
	 	primaryStage.setMaximized(true);
	    primaryStage.show();
	}
	
	/**
	 * Required update method for the observer/observable design pattern
	 */
	public void update(Observable obs, Object obj) {
		// Observable pattern
	}
	
	private void draw(GraphicsContext gc) {
		double width = gc.getCanvas().getWidth();
		double height = gc.getCanvas().getHeight();
		
		// Font color 
		if (currentPenColor.equals("Red")) {
			gc.setFill(Color.RED);
			gc.setStroke(Color.RED);
		} else if (currentPenColor.equals("Orange")) {
			gc.setFill(Color.ORANGE);
			gc.setStroke(Color.ORANGE);
		} else if (currentPenColor.equals("Yellow")) {
			gc.setFill(Color.YELLOW);
			gc.setStroke(Color.YELLOW);
		} else if (currentPenColor.equals("Green")) {
			gc.setFill(Color.GREEN);
			gc.setStroke(Color.GREEN);
		} else if (currentPenColor.equals("Blue")) {
			gc.setFill(Color.BLUE);
			gc.setStroke(Color.BLUE);
		} else if (currentPenColor.equals("Purple")) {
			gc.setFill(Color.PURPLE);
			gc.setStroke(Color.PURPLE);
		} else if (currentPenColor.equals("Pink")) {
			gc.setFill(Color.PINK);
			gc.setStroke(Color.PINK);
		} else if (currentPenColor.equals("Black")) {
			gc.setFill(Color.BLACK);
			gc.setStroke(Color.BLACK);
		}
		
		gc.setLineWidth(currentPenSize);
		gc.fill();
	}
	
	/**
	 * This method creates the menu bar for the main stage/scene.
	 */
	private void createMenuBar() {
		// File Menu
		Menu home = new Menu("Home");
		Menu insert = new Menu("Insert");
		
		// Create menu items
		MenuItem newPage = new MenuItem("New");
		MenuItem clearPage = new MenuItem("Clear");
		MenuItem savePage = new MenuItem("Save");
		
		newPage.setOnAction(e -> {
			// When user selects to create a new page
		});
		
		clearPage.setOnAction(e -> {
			graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		});	
		
		savePage.setOnAction(e -> {
			// When user selects to save their current page
		});	
		
		// Add menu items to file dropdown
		home.getItems().addAll(savePage, clearPage);
		insert.getItems().addAll(newPage);
		mainMenuBar.getMenus().addAll(home, insert);
		mainMenuBar.setStyle("-fx-background-color: #BA55D3");
	}
	
	/**
	 * This method generates the tool bar where the user can change the size of 
	 * the font, the color of the font, the pen size, and the pen color. This
	 * method also handles all of the change listeners associated with each of 
	 * the options.
	 */
	private void createToolBar() {
		toolBar = new ToolBar();
		
		// Font size selector
		fontSizesBox = new ComboBox<Integer>(FXCollections.observableArrayList(fontSizes));
		fontSizesBox.getSelectionModel().selectFirst();
		fontSizesBox.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldVal, Integer newVal) {
				// newVal is the newly selected font size
				currentFontSize = newVal;
			}
		});
		
		// Font color selector
		fontColorBox = new ComboBox<String>(FXCollections.observableArrayList(colors));
		fontColorBox.getSelectionModel().selectFirst();
		fontColorBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// newValue is the newly selected color
			}
		});
		
		// Pen size selector
		penSizesBox = new ComboBox<Integer>(FXCollections.observableArrayList(penSizes));
		penSizesBox.getSelectionModel().selectFirst();
		penSizesBox.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldVal, Integer newVal) {
				// newVal is the newly selected pen size
				currentPenSize = newVal;
			}
		});
		
		// Pen color selector
		penColorBox = new ComboBox<String>(FXCollections.observableArrayList(colors));
		penColorBox.getSelectionModel().selectFirst();
		penColorBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// newValue is the newly selected pen color
				currentPenColor = newValue;
			}
		});
		
		toolBar.getItems().addAll(fontSizeLabel, fontSizesBox, fontColorLabel, fontColorBox, penSizeLabel, penSizesBox, penColorLabel, penColorBox);
		toolBar.setStyle("-fx-background-color: #BA55D3");
	}
}
