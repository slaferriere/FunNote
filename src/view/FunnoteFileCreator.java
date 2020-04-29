package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FunnoteFileCreator extends Stage {
	
	private String notebookName;
	private String sectionName;
	private String pageName;
	private boolean choose = false;
	private BorderPane bp;
	
	public FunnoteFileCreator() {
		setTitle("Make New Notebook");
		bp = new BorderPane();
		
		VBox vbox = new VBox();
		
		HBox notebookField = new HBox();
		Label notebook = new Label("Notebook");
		TextField notebookInput = new TextField("Name of Notebook");
		notebookField.getChildren().addAll(notebook, notebookInput);
		notebookField.setAlignment(Pos.CENTER);
		
		HBox sectionField = new HBox();
		Label section = new Label("Section");
		TextField sectionInput = new TextField("Name of Section");
		sectionField.getChildren().addAll(section, sectionInput);
		sectionField.setAlignment(Pos.CENTER);
		
		HBox pageField = new HBox();
		Label page = new Label("Page");
		TextField pageInput = new TextField("Name of Page");
		pageField.getChildren().addAll(page, pageInput);
		pageField.setAlignment(Pos.CENTER);
		
		HBox confirmButtons = new HBox();
		Button ok = new Button("Done");
		Button cancel = new Button("Cancel");
		confirmButtons.getChildren().addAll(ok, cancel);
		confirmButtons.setAlignment(Pos.CENTER);
		
		vbox.getChildren().addAll(notebookField, sectionField, pageField, confirmButtons);
		
		bp.setCenter(vbox);
		
		ok.setOnAction(e -> {
			// Do something
			choose = true;
			close();
		});
		
		cancel.setOnAction(e -> {
			choose = true;
			close();
		});
	}
	
	public BorderPane getBorderPane() {
		return bp;
	}
	
	public FunnoteFileCreator(String notebookName) {
		setTitle("Make New Section");
		
	}
	
	public FunnoteFileCreator(String notebookName, String sectionName) {
		setTitle("Make New Page");
	}
	
	public boolean getChoose() {
		return this.choose;
	}
}
