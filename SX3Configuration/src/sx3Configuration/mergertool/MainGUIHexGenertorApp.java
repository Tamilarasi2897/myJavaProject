package sx3Configuration.mergertool;

import java.io.File;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sx3Configuration.model.SX3Configuration;
import sx3Configuration.ui.SX3ConfigPreference;
import sx3Configuration.ui.SX3Manager;

public class MainGUIHexGenertorApp extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Hex Generator!");

		// Creating a GridPane container
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);

		// Defining the json text field
		final TextField textField = new TextField();
		textField.setPromptText("json file.");
		textField.setPrefColumnCount(10);
		textField.getText();
		GridPane.setConstraints(textField, 0, 0);
		grid.getChildren().add(textField);

		// Defining the Browse button
		Button submit = new Button("Browse Json File");
		GridPane.setConstraints(submit, 1, 0);
		grid.getChildren().add(submit);

		// Adding a Label
		final TextArea label = new TextArea();
		GridPane.setConstraints(label, 0, 3);
		GridPane.setColumnSpan(label, 2);
		GridPane.setRowSpan(label, 3);
		grid.getChildren().add(label);

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Browse Json File");

		// Setting an action for the Submit button
		submit.setOnAction(new EventHandler<ActionEvent>() {
			private File selectedFile;

			@Override
			public void handle(ActionEvent e) {
				textField.clear();
				label.clear();
				selectedFile = fileChooser.showOpenDialog(primaryStage);
				if (selectedFile != null) {

					textField.setText(selectedFile.getAbsolutePath());
				} else {
					label.setText("File selection cancelled.");
				}
			}
		});

		Button btn = new Button("Generate Hex");
		GridPane.setConstraints(btn, 1, 1);
		grid.getChildren().add(btn);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String jsonFilePath;
				try {
					// Gson parser object to parse json file
					Gson gson = new Gson();
					jsonFilePath = textField.getText().trim();
					// Set Config File path to preference
					SX3ConfigPreference.setSx3ConfigFilePathPreference(jsonFilePath);
					File configJsonFile = new File(jsonFilePath);

					if (configJsonFile.exists()) {
						JsonReader reader = new JsonReader(new FileReader(configJsonFile));
						// convert the json string back to object
						SX3Configuration sx3Obj = gson.fromJson(reader, SX3Configuration.class);
						// Set the Sx3 object to the manager
						SX3Manager.getInstance().setSx3Configuration(sx3Obj);
						// Convert config json to hex file
						SX3ConfigHexFileUtil.convertJsonToHexFile();
						label.setText("Successfully generated sx3 config hex file");
					} else {
						label.setText("SX3Configuration Json file not found");
					}
				} catch (Exception e) {
					label.setText(e.getMessage());
					System.exit(1);
				}
			}
		});
		
		final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(grid);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
        
        rootGroup.setOnDragOver(event -> {
			if (event.getDragboard().hasFiles() ) {
				event.acceptTransferModes(TransferMode.ANY);
			}
		});
        
        rootGroup.setOnDragDropped(event -> {
        File selectedFile =	event.getDragboard().getFiles().get(0);
        textField.clear();
		label.clear();
		if (selectedFile != null) {

			textField.setText(selectedFile.getAbsolutePath());
		} else {
			label.setText("File selection cancelled.");
		}
		});;
 
        primaryStage.setScene(new Scene(rootGroup,300,200));
        primaryStage.show();

	}
}