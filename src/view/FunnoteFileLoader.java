package view;

import javafx.stage.Stage;

import java.util.List;

import controller.FunnoteController;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class FunnoteFileLoader extends Stage {

	GridPane gp;
	@SuppressWarnings("rawtypes")
	ComboBox box;
	String partChosen;
	boolean chosen = false;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FunnoteFileLoader(String type, FunnoteController controller) {
		gp = new GridPane();
		if(type.compareTo("notebook") == 0) {
			this.setTitle("Choose Notebook");
		} else if(type.compareTo("section") == 0) {
			this.setTitle("Choose Section");
			Label label = new Label("Sections");
			label.setAlignment(Pos.CENTER);
			List<String> sectionNames = controller.getSectionList();
			String[] sections = new String[sectionNames.size()];
			for(int i = 0; i < sections.length; i++) {
				sections[i] = sectionNames.get(i);
			}
			
			box = new ComboBox(FXCollections.observableArrayList(sections));
			EventHandler<ActionEvent> event = 
					new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					partChosen = (String) box.getValue();
					chosen = true;
				}
			};
			
			box.setOnAction(event);
			
			HBox controls = new HBox();
			Button cancel = new Button("Cancel");
			cancel.setCancelButton(true);
			Button done = new Button("Done");
			done.setDefaultButton(true);
			controls.getChildren().addAll(cancel, done);
			controls.setAlignment(Pos.CENTER);
			
			gp.add(label, 1, 0);
			gp.add(box, 1, 1);
			gp.add(controls, 1, 2);
			
			cancel.setOnAction(e -> {
				chosen = false;
				close();
			});
			
			done.setOnAction(e -> {
				if(chosen == false) {}
				else {
					close();
				}
			});
			
		} else if(type.compareTo("page") == 0) {
			this.setTitle("Choose Page");
			Label label = new Label("Page");
			label.setAlignment(Pos.CENTER);
			List<String> pageNames = controller.getPageList();
			String[] pages = new String[pageNames.size()];
			for(int i = 0; i < pages.length; i++) {
				pages[i] = pageNames.get(i);
			}
			
			box = new ComboBox(FXCollections.observableArrayList(pages));
			EventHandler<ActionEvent> event = 
					new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					partChosen = (String) box.getValue();
					chosen = true;
				}
			};
			
			box.setOnAction(event);
			
			HBox controls = new HBox();
			Button cancel = new Button("Cancel");
			cancel.setCancelButton(true);
			Button done = new Button("Done");
			done.setDefaultButton(true);
			controls.getChildren().addAll(cancel, done);
			controls.setAlignment(Pos.CENTER);
			
			gp.add(label, 1, 0);
			gp.add(box, 1, 1);
			gp.add(controls, 1, 2);
			
			cancel.setOnAction(e -> {
				chosen = false;
				close();
			});
			
			done.setOnAction(e -> {
				if(chosen == false) {}
				else {
					close();
				}
			});
		} else {
			
		}
	}
	
	public boolean getChosen() {
		return this.chosen;
	}
	public String getPart() {
		return this.partChosen;
	}
	public GridPane getGP() {
		return this.gp;
	}
	
}
