package view;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import controller.FunnoteController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.FunnoteModel;

/**
 * This is the main class that displays everything on the screen. It will update the controller
 * when any changes are made by the user
 * 
 * @author Michael Tuohy, Trevor Freudig, Alexander Thompson, Scott LaFerriere
 *
 */
public class FunnoteView extends Application implements Observer {
	
	private MenuBar mainMenuBar = new MenuBar();
	private Canvas canvas = new Canvas(1900, 1000);
	private GraphicsContext graphicsContext;
	private StackPane stackPane = new StackPane();
	private Pane pane = new Pane();
	private TextArea textArea = new TextArea();
	private VBox vbox = new VBox();
	private ComboBox<Integer> fontSizesBox;
	private ComboBox<String> fontColorBox;
	private ComboBox<Integer> penSizesBox;
	private ComboBox<String> penColorBox;
	private ComboBox<String> shapesBox;
	private ToolBar toolBar;
	private Label fontSizeLabel = new Label("Font Size:");
	private Label fontColorLabel = new Label("Font Color:");
	private Label penSizeLabel = new Label("Pen Size:");
	private Label penColorLabel = new Label("Pen Color:");
	private Label shapesLabel = new Label("Shape:");
	private int currentFontSize = 22;
	private String currentFontColor = "Red";
	private int currentPenSize = 5;
	private String currentPenColor = "Red";
	private String currentShape = "Free Draw";
	private Integer fontSizes[] = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72};
	private Integer penSizes[] = {5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24};
	private String colors[] = {"Red", "Orange", "Yellow", "Green", "Blue", "Purple", "Pink", "Black"};
	private String shapes[] = {"Free Draw", "Oval", "Rectangle"};
	private Button textBoxButton = new Button("Write Text");
	private boolean textBoxClicked;
	private double xCoord;
	private double yCoord;
	private double[] prevShape;
	private Stage mainStage;
	private String imageUrl;
	private boolean insertImage;
	private Image image;
	private ImageView imageView;
	private String currPageFilePath;
	private Text text;
	private FunnoteController controller = new FunnoteController(new FunnoteModel());
	
	/**
	 * This method is called by FunNote.java and initializes 
	 * the main window/canvas. This method "starts" the program.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		controller.getModel().addObserver(this);
		mainStage = primaryStage;
		BorderPane window = new BorderPane();
		
		createMenuBar();
		
		createToolBar();
		
		window.setTop(new VBox(mainMenuBar, toolBar));
		
		
		// Canvas setup
		graphicsContext = canvas.getGraphicsContext2D();
		
		
		pane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				xCoord = e.getX();
				yCoord = e.getY();
				text = new Text();
				text.setX(xCoord);
				text.setY(yCoord);
				text.setFont(Font.font(currentFontSize));
				getTextColor();
				pane.getChildren().add(text);
			}
		});
		
		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	text.setText(text.getText() + event.getText());
            }
        });
		

		
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				prevShape = new double[4];
				xCoord = e.getX();
				yCoord = e.getY();
				if(currentShape.equals("Free Draw") && (insertImage == false)) {
					graphicsContext.beginPath();
					graphicsContext.moveTo(xCoord, yCoord);
					graphicsContext.stroke();					
				} else if (insertImage) {
					// Display image wherever the user clicks.
					try {
						image = new Image(imageUrl);
					} catch (Exception e2) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("WARNING");
						alert.setHeaderText("File Path Error");
						alert.setContentText("Please try again.");
						e2.printStackTrace();
					}
					
					graphicsContext.drawImage(image, e.getX(), e.getY());
					insertImage = false;
				}
				draw(graphicsContext);
				
			}
		});
		
		canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				double newXCoord = e.getX();
				double newYCoord = e.getY();
				if (!currentShape.equals("Free Draw")) {
					drawShape(graphicsContext, xCoord, yCoord, newXCoord, newYCoord);
				}
				else {
					graphicsContext.lineTo(newXCoord, newYCoord);
					graphicsContext.stroke();
				}
			}
		});
		
		canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
			}
		});
		
		textBoxButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	if(textBoxClicked) {
		    		textBoxButton.setEffect(null);
		    		textBoxClicked = false;
		    	} else {
			    	textBoxButton.setEffect(new DropShadow());
			        textBoxClicked = true;
			        pane.requestFocus();
		    	}
		    	changeTop();
		    }
		});
		

		
		stackPane.setStyle("-fx-background-color: white");
		//stackPane.getChildren().add(textArea);
		
		stackPane.getChildren().add(pane);
		stackPane.getChildren().add(canvas);


		
		// Left vbox setup
//		vbox.setPadding(new Insets(5, 5, 5, 5));	
//		window.setLeft(vbox);
//		window.setPadding(new Insets(5, 5, 5, 5));
		
		window.setCenter(stackPane);
	
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
	//	gc.fill();
	}
	
	private void getTextColor() {
		if(currentFontColor.equals("Red")) {
			text.setFill(Color.RED);
		} else if(currentFontColor.equals("Orange")) {
			text.setFill(Color.ORANGE);
		} else if(currentFontColor.equals("Yellow")) {
			text.setFill(Color.YELLOW);
		} else if(currentFontColor.equals("Green")) {
			text.setFill(Color.GREEN);
		} else if(currentFontColor.equals("Blue")) {
			text.setFill(Color.BLUE);
		} else if(currentFontColor.equals("Purple")) {
			text.setFill(Color.PURPLE);
		} else if(currentFontColor.equals("Pink")) {
			text.setFill(Color.PINK);
		} else if(currentFontColor.equals("Black")) {
			text.setFill(Color.BLACK);
		}
		
	}
	
	
	// Swaps the canvas with the pane and vice versa. Is called when "Write Text" button is clicked
	private void changeTop() {
        ObservableList<Node> childs = this.stackPane.getChildren();
 
        if (childs.size() > 1) {
            Node topNode = childs.get(childs.size()-1);
            topNode.toBack();
        }
    }
	

	
	
	// This method draws the specified shape. It clears the previous shape so the user can drag and drop. Only issue is when
	// two shapes overlap it clears the one under.
	private void drawShape(GraphicsContext gc, double startX, double startY, double endX, double endY) {
		
		gc.clearRect(prevShape[0], prevShape[1], prevShape[2], prevShape[3]);
		
		double x = Math.min(startX, endX);
		double y = Math.min(startY, endY);
		double width = Math.abs(startX - endX);
		double height = Math.abs(startY - endY);

		
		if(currentShape.equals("Rectangle")) {
			gc.fillRect(x, y, width, height);
		} else if(currentShape.equals("Oval")) {
			gc.fillOval(x, y, width, height);
		}
		
		prevShape[0] = x;
		prevShape[1] = y;
		prevShape[2] = width;
		prevShape[3] = height;
	}
	
	/**
	 * This method creates the menu bar for the main stage/scene.
	 */
	private void createMenuBar() {
		// File Menu
		Menu home = new Menu("Home");
		Menu insert = new Menu("Insert");
		Menu change = new Menu("Switch");
		Menu create = new Menu("Create");
		
		// Create menu items
		MenuItem changeNotebook = new MenuItem("Notebook");
		MenuItem changeSection = new MenuItem("Section");
		MenuItem changePage = new MenuItem("Page");
		
		MenuItem newPage = new MenuItem("New");
		MenuItem newImage = new MenuItem("Image");
		
		MenuItem clearPage = new MenuItem("Clear");
		MenuItem saveAsPage = new MenuItem("Save As");
		MenuItem savePage = new MenuItem("Save");
		
		MenuItem createNotebook = new MenuItem("New Notebook");
		MenuItem createSection = new MenuItem("New Section");
		MenuItem createNewPage = new MenuItem("New Page");
		MenuItem addCurrPage = new MenuItem("Add Current Page");
		
		// When user selects to create a new page
		newPage.setOnAction(e -> {
			
		});
		
		// When user selects to insert a new image
		newImage.setOnAction(e -> {
			TextInputDialog input = new TextInputDialog("IMAGE URL HERE");
			input.setTitle("Insert Image");
			input.showAndWait();
			
			imageUrl = input.getEditor().getText();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("INFORMATION");
			alert.setHeaderText("NOTE:");
			alert.setContentText("Click anywhere on main screen to display your image.");
			alert.showAndWait();
			
			insertImage = true;
		});
		
		clearPage.setOnAction(e -> {
			graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		});	
		
		savePage.setOnAction(e -> {
			
		});
		
		saveAsPage.setOnAction(e -> {
			// When user selects to save their current page
			FileChooser fileChooser = new FileChooser();
            
            //Set extension filter
            FileChooser.ExtensionFilter extFilter = 
                    new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);
           
            //Show save file dialog
            File file = fileChooser.showSaveDialog(mainStage);
             
            if(file != null){
                try {
                    WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                    canvas.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
		});	
		
		changeNotebook.setOnAction(e -> {
			
		});
		
		changeSection.setOnAction(e-> {
			
		});
		
		changePage.setOnAction(e -> {
			
		});
		
		createNotebook.setOnAction(e -> {
			TextInputDialog notebook = new TextInputDialog("Enter Any Text Here");
			notebook.setHeaderText("Enter Name of Notebook Here");
			notebook.showAndWait();
			String notebookName = notebook.getEditor().getText();
			if(notebookName.compareTo("Enter Any Text Here") == 0) {}
			else {
				DirectoryChooser dirChoose = new DirectoryChooser();
				File dir = dirChoose.showDialog(mainStage);
				
				if(dir != null) {
					try {
						controller.addNewNotebook(notebookName, dir);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		createSection.setOnAction(e -> {
			if(!controller.hasNotebook()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("NOTE:");
				alert.setContentText("Create a Notebook First");
				alert.showAndWait();
			} else {
				TextInputDialog section = new TextInputDialog("Enter Any Text Here");
				section.setHeaderText("Enter Name of Section Here");
				section.showAndWait();
				String sectionName = section.getEditor().getText();
				if(sectionName.compareTo("Enter Any Text Here") == 0) {}
				else {
					controller.addNewSection(sectionName);
				}
			}
		});
		
		createNewPage.setOnAction(e -> {
			if(!controller.hasSection()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("NOTE:");
				alert.setContentText("Create a Section First");
				alert.showAndWait();
			} else {
				TextInputDialog page = new TextInputDialog("Enter Any Text Here");
				page.setHeaderText("Enter Name of Page Here");
				page.showAndWait();
				String pageName = page.getEditor().getText();
				if(pageName.compareTo("Enter Any Text Here") == 0) {}
				else {
					controller.addNewPage(pageName);
				}
			}
			
		});
		
		addCurrPage.setOnAction(e -> {
			if(!controller.hasSection()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("NOTE:");
				alert.setContentText("Create a Section First");
				alert.showAndWait();
			} else {
				TextInputDialog page = new TextInputDialog("Enter Any Text Here");
				page.setHeaderText("Enter Name of Page Here");
				page.showAndWait();
				String pageName = page.getEditor().getText();
				if(pageName.compareTo("Enter Any Text Here") == 0) {}
				else {
					WritableImage image = new WritableImage(
							(int) canvas.getWidth(), (int) canvas.getHeight());
					canvas.snapshot(null, image);
					RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
					try {
						controller.addCurrentPage(pageName, renderedImage);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		// Add menu items to file dropdown
		create.getItems().addAll(createNotebook, createSection, createNewPage, addCurrPage);
		change.getItems().addAll(changeNotebook, changeSection, changePage);
		home.getItems().addAll(savePage, saveAsPage, clearPage);
		insert.getItems().addAll(newPage, newImage);
		mainMenuBar.getMenus().addAll(home, insert, change, create);
		mainMenuBar.setStyle("-fx-background-color: #d3d3d3");
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
				currentFontColor = newValue;
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
		
		shapesBox = new ComboBox<String>(FXCollections.observableArrayList(shapes));
		shapesBox.getSelectionModel().selectFirst();
		shapesBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// newValue is the newly selected pen color
				currentShape = newValue;
			}
		});
		
		toolBar.getItems().addAll(fontSizeLabel, fontSizesBox, fontColorLabel, fontColorBox, penSizeLabel, penSizesBox, penColorLabel, penColorBox, shapesLabel, shapesBox, textBoxButton);
		toolBar.setStyle("-fx-background-color: #BA55D3");
	}
}
