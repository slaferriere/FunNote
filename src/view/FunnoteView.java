package view;

import java.awt.image.RenderedImage;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.FunnoteModel;
import model.Page;
import model.Section;
import model.TextboxNode;

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
	private ComboBox<Integer> fontSizesBox;
	private ComboBox<String> fontColorBox;
	private ComboBox<Integer> penSizesBox;
	private ComboBox<String> penColorBox;
	private ComboBox<String> shapesBox;
	private ToolBar textToolBar;
	private ToolBar drawToolBar;
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
	private Text text;
	private FunnoteController controller = new FunnoteController(new FunnoteModel());
	private Label currMode = new Label("Current Mode: DRAW");
	private Label currNotebook = new Label("");
	private HBox hbox = new HBox();
	private BorderPane window = new BorderPane();
	private int count = 0;
	private String currSection = "";
	private String currPage = "";
	private Button boldButton;
	private Button italicizeButton;
	private boolean bold;
	private boolean italic;
	
	/**
	 * This method is called by FunNote.java and initializes 
	 * the main window/canvas. This method "starts" the program.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		controller.getModel().addObserver(this);
		mainStage = primaryStage;
		
		createMenuBar();
		createTextToolBar();
		createDrawToolBar();

		currNotebook.setText("Welcome to FunNote!");
		currNotebook.setFont(Font.font("Arial", FontWeight.BOLD, 25));
		currNotebook.setTextFill(Color.WHITE);
		
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(5, 5, 5, 5));
		hbox.setStyle("-fx-background-color: #800080");
		hbox.getChildren().add(currNotebook);
		
		window.setTop(new VBox(mainMenuBar, hbox, drawToolBar));
			
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
				text.setFont(Font.font("Verdana", currentFontSize));
				if(bold && italic) {
					text.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, currentFontSize));
            	}
				else if(bold) {
					text.setFont(Font.font("Verdana", FontWeight.BOLD, currentFontSize));
            	}
				else if(italic) {
					text.setFont(Font.font("Verdana", FontPosture.ITALIC, currentFontSize));
            	}				
				getTextColor();
				pane.getChildren().add(text);
				pane.requestFocus();
			}
		});
		
		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	getTextColor();
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
		    		textBoxClicked = false;
		    		textBoxButton.setText("Click to Write Text");
		    		window.setTop(new VBox(mainMenuBar, hbox, drawToolBar));
		    		currMode.setText("Current Mode: Draw");
		    		
		    		ObservableList<Node> textboxes = pane.getChildren();
		    		graphicsContext.save();
		    		for(Node box : textboxes) {
		    			if(!(box instanceof Text)) { System.exit(1); }
		    			Text text = (Text) box;
		    			graphicsContext.setFill(Paint.valueOf(text.getFill().toString()));
		    			graphicsContext.setFont(text.getFont());
		    			graphicsContext.fillText(text.getText(), text.getX(), text.getY());
		    		}
		    		graphicsContext.restore();
		    		pane.getChildren().clear();
		    		
		    	} else {
			        textBoxClicked = true;
		    		textBoxButton.setText("Click to Draw");
			        pane.requestFocus();
			        window.setTop(new VBox(mainMenuBar, hbox, textToolBar));
		    		currMode.setText("Current Mode: Text");
		    	}
		    	changeTop();
		    }
		});
		
		boldButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	if(bold) {
		    		bold = false;
		    		boldButton.setEffect(null);	    		
		    	} else {
		    		bold = true;
		    		boldButton.setEffect(new InnerShadow());
		    	}
		    }
		});
		
		italicizeButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	if(italic) {
		    		italic = false;
		    		italicizeButton.setEffect(null);	    		
		    	} else {
		    		italic = true;
		    		italicizeButton.setEffect(new InnerShadow());
		    	}
		    }
		});
		
		stackPane.setStyle("-fx-background-color: white");
		
		canvas.setStyle("-fx-background-color: transparent");
		
		stackPane.getChildren().add(pane);
		stackPane.getChildren().add(canvas);

		createVbox();
		
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
		if(obj instanceof String) {
			String input = (String) obj;
			if(input.compareTo("blank page") == 0) {
				graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				pane.getChildren().clear();
				canvas.setStyle("-fx-background-color: transparent");
			}
		}
		else {
			graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			pane.getChildren().clear();
			canvas.setStyle("-fx-background-color: transparent");
			if(!(obj instanceof Page)) {
				System.out.println("Problem with Page");
				System.exit(1);
			}
			Page page = (Page) obj;
			String imageURL = controller.getPageURL(page.getCanvasURL());
			try {
				Image canvasImage = new Image(new FileInputStream(imageURL));
				graphicsContext.drawImage(canvasImage, 0, 0);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	/**
	 * This method sets the fill and the stroke of the paint object based on what is in the selection box.
	 * 
	 * @param gc Canvas graphicsContext
	 */
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
	}
	

	
	/**
	 * This method sets the color of the text based on the user's selection
     * editingText
	 * 
	 */
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
	 * This method generates the vbox on the left side of the screen that contains 
	 * information regarding the current notebook, sections, and pages. From this 
	 * vbox the user can change the notebook, section, and page.
	 */
	private void createVbox() {
		TitledPane notebookTp = new TitledPane();
		HBox sectionHbox = new HBox();
		HBox pageHbox = new HBox();
		VBox vbox = new VBox();
		Button notebookButton = new Button("Switch Notebook");
		Button sectionButton = new Button("Switch Section");
		Button pageButton = new Button("Switch Page");
		
		notebookTp.setText("Notebooks");
		notebookTp.setContent(notebookButton);
		notebookButton.setOnAction(e -> {
			DirectoryChooser dirChoose = new DirectoryChooser();
			File dir = dirChoose.showDialog(mainStage);
			
			if(dir == null) { 
				showInvalidNotebookFileAlert(); 
			}
			else if(!dir.isDirectory()) { 
				showInvalidNotebookFileAlert(); 
			}
			else {
				String[] ls = dir.list();
				if(ls.length != 2) { 
					showInvalidNotebookFileAlert(); 
					System.out.println("here");
				}
				else {
					if(ls[0].compareTo("notebook.funnote") == 0 &&
							ls[1].compareTo("pageImages") == 0) {
						File imageFile = new File(dir.getAbsolutePath() + File.separator +
								ls[1]);
						if(imageFile.exists()) {
							if(imageFile.isDirectory()) {
								controller.changeNotebook(dir);
							} else {
								showInvalidNotebookFileAlert();
							}
						} else { 
							showInvalidNotebookFileAlert();
						}
					}
				}
			}
			
			if (controller.getModel().getNotebook().getNotebookName() == null) {
				currNotebook.setText("Welcome to FunNote!");
			} else {
				currNotebook.setText(controller.getModel().getNotebook().getNotebookName() + " Notebook");
			}
			currNotebook.setFont(Font.font("Arial", FontWeight.BOLD, 25));
			if (textBoxClicked) {
				window.setTop(new VBox(mainMenuBar, hbox, textToolBar));
			} else {
				window.setTop(new VBox(mainMenuBar, hbox, drawToolBar));
			}		
			
			createVbox();
		});
		notebookTp.setExpanded(false);
		
		// Section list
		TitledPane sectionTp = new TitledPane();
		sectionTp.setText("Sections");
		if (controller.getModel().hasSection()) {
			VBox sectionVbox = new VBox();
			List<String> sections = controller.getSectionList();
			for (int j = 0; j < sections.size(); j++) {
				if (sections.get(j).equals(currSection)) {
					Label label = new Label(sections.get(j));
					label.setStyle("-fx-font-weight: bold");
					sectionVbox.getChildren().add(label);
				} else {
					sectionVbox.getChildren().add(new Label(sections.get(j)));
				}
			}
			sectionTp.setContent(sectionVbox);
		} else {
			sectionTp.setContent(new Label("There are no sections in this notebook."));
		}
		sectionButton.setOnAction(e -> {
			FunnoteFileLoader getSection = new FunnoteFileLoader("section", controller);
			Scene loadScene = new Scene(getSection.getGP());
			getSection.setScene(loadScene);
			getSection.initModality(Modality.APPLICATION_MODAL);
			getSection.showAndWait();
			if(!getSection.getChosen()) {
				
			} else {
				currSection = getSection.partChosen;
				controller.changeSection(getSection.partChosen);
			}
			createVbox();
		});
		sectionTp.setExpanded(false);
		
		// Page list
		TitledPane pageTp = new TitledPane();
		pageTp.setText("Pages");
		if (controller.getModel().hasSection()) {
			VBox pageVbox = new VBox();
			List<String> pages = controller.getPageList();
			for (int j = 0; j < pages.size(); j++) {
				if (pages.get(j).equals(currPage)) {
					Label label = new Label(pages.get(j));
					label.setStyle("-fx-font-weight: bold");
					pageVbox.getChildren().add(label);
				} else {
					pageVbox.getChildren().add(new Label(pages.get(j)));
				}
			}
			pageTp.setContent(pageVbox);
		} else {
			pageTp.setContent(new Label("There are no pages in this section."));
		}
		pageButton.setOnAction(e -> {
			FunnoteFileLoader getPage = new FunnoteFileLoader("page", controller);
			Scene loadScene = new Scene(getPage.getGP());
			getPage.setScene(loadScene);
			getPage.initModality(Modality.APPLICATION_MODAL);
			
			getPage.showAndWait();
			if(!getPage.getChosen()) {
				
			} else {
				currPage = getPage.partChosen;
				controller.changePage(getPage.partChosen);
			}
			createVbox();
		});
		pageTp.setExpanded(false);

		sectionHbox.setSpacing(5);
		sectionHbox.setAlignment(Pos.CENTER);
		pageHbox.setSpacing(5);
		pageHbox.setAlignment(Pos.CENTER);
		sectionHbox.getChildren().addAll(sectionTp, sectionButton);
		pageHbox.getChildren().addAll(pageTp, pageButton);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getChildren().addAll(currMode, textBoxButton, notebookTp, sectionHbox, pageHbox);  
		count++;
		window.setLeft(vbox);
	}
	
	
	/**
	 * This method creates the menu bar for the main stage/scene.
	 */
	private void createMenuBar() {
		// File Menu
		Menu home = new Menu("Home");
		Menu insert = new Menu("Insert");
		Menu create = new Menu("Create");
		
		// Create menu items
		MenuItem newImage = new MenuItem("Image");
		
		MenuItem clearPage = new MenuItem("Clear");
		MenuItem savePage = new MenuItem("Save");
		
		MenuItem createNotebook = new MenuItem("New Notebook");
		MenuItem createSection = new MenuItem("New Section");
		MenuItem createNewPage = new MenuItem("New Page");
		MenuItem addCurrPage = new MenuItem("Add Current Page");
		
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
			if(!controller.hasNotebook()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("MISSING NOTEBOOK:");
				alert.setContentText("Create a Notebook First");
				alert.showAndWait();
			} else if(!controller.hasSection()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("MISSING SECTION:");
				alert.setContentText("Create a Section First");
				alert.showAndWait();
			} else if(!controller.hasPage()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("MISSING SECTION:");
				alert.setContentText("Create a Section First");
				alert.showAndWait();
			} else {
	    		ObservableList<Node> textboxes = pane.getChildren();
	    		graphicsContext.save();
	    		for(Node box : textboxes) {
	    			if(!(box instanceof Text)) { System.exit(1); }
	    			Text text = (Text) box;
	    			graphicsContext.setFill(Paint.valueOf(text.getFill().toString()));
	    			graphicsContext.setFont(text.getFont());
	    			graphicsContext.fillText(text.getText(), text.getX(), text.getY());
	    		}
	    		graphicsContext.restore();
	    		pane.getChildren().clear();
				
				WritableImage image = new WritableImage(
						(int) canvas.getWidth(), (int) canvas.getHeight());
				canvas.snapshot(null, image);
			//	pane.snapshot(null, image);
				RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
				try {
					controller.save(renderedImage);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
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
			
			currNotebook.setText(controller.getModel().getNotebook().getNotebookName() + " Notebook");
			currNotebook.setFont(Font.font("Arial", FontWeight.BOLD, 25));
			if (textBoxClicked) {
				window.setTop(new VBox(mainMenuBar, hbox, textToolBar));
			} else {
				window.setTop(new VBox(mainMenuBar, hbox, drawToolBar));
			}
			
			createVbox();
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
			
			createVbox();
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
					try {
						controller.addNewPage(pageName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
			createVbox();
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
			
			createVbox();
		});
		
		// Add menu items to file dropdown
		create.getItems().addAll(createNotebook, createSection, createNewPage, addCurrPage);
		home.getItems().addAll(savePage, clearPage);
		insert.getItems().addAll(newImage);
		mainMenuBar.getMenus().addAll(home, insert, create);
		mainMenuBar.setStyle("-fx-background-color: #d3d3d3");
	}
	
	/**
	 * This method
	 */
	private void showInvalidNotebookFileAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText("Invalid Notebook File");
		alert.setContentText("Directory is not of specified format. Please "
				+ "look at help page to find the corret file format, or create"
				+ "a new notebook");
		alert.showAndWait();
	}
	
	/**
	 * This method generates the tool bar where the user can change the size of 
	 * the font, the color of the font, the pen size, and the pen color. This
	 * method also handles all of the change listeners associated with each of 
	 * the options.
	 */
	private void createTextToolBar() {
		textToolBar = new ToolBar();
		
		fontSizeLabel.setTextFill(Color.WHITE);
		fontColorLabel.setTextFill(Color.WHITE);
		

		boldButton = new Button("Bold");
		boldButton.setStyle("-fx-font: bold 11pt \"Arial\";");
		
		italicizeButton = new Button("Italic");
		italicizeButton.setStyle("-fx-font: italic 11pt \"Arial\";");


		
		// Font size selector
		fontSizesBox = new ComboBox<Integer>(FXCollections.observableArrayList(fontSizes));
		fontSizesBox.getSelectionModel().select(8);
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
		
		textToolBar.getItems().addAll(fontSizeLabel, fontSizesBox, fontColorLabel, fontColorBox, boldButton, italicizeButton);
		textToolBar.setStyle("-fx-background-color: #800080");
	}
	
	/**
	 * This method generates the tool bar where the user can change the size of 
	 * the font, the color of the font, the pen size, and the pen color. This
	 * method also handles all of the change listeners associated with each of 
	 * the options.
	 */
	private void createDrawToolBar() {
		drawToolBar = new ToolBar();
		penSizeLabel.setTextFill(Color.WHITE);
		penColorLabel.setTextFill(Color.WHITE);
		shapesLabel.setTextFill(Color.WHITE);
		
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
		
		drawToolBar.getItems().addAll(penSizeLabel, penSizesBox, penColorLabel, penColorBox, shapesLabel, shapesBox);
		drawToolBar.setStyle("-fx-background-color: #800080");
	}
}
