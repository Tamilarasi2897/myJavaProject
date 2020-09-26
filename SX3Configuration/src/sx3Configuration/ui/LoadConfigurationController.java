package sx3Configuration.ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadConfigurationController implements Initializable {

	@FXML
	Button loadConfigurationOkBtn, selectJsonFilePathBtn, btnImportFromLocation;

	@FXML
	RadioButton sx3ProjectSelection, userProjectSelection;

	@FXML
	TextField jsonFilePath, sx3ConfigurationToImport;

	private String importToPath;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.importToPath = "";
		jsonFilePath.textProperty().addListener((observable2, oldValue2, newValue2) -> {
			if ((newValue2 != null && !newValue2.equals(""))) {
				loadConfigurationOkBtn.setDisable(false);
			} else {
				loadConfigurationOkBtn.setDisable(true);
			}
		});
		
		sx3ConfigurationToImport.textProperty().addListener((observable2, oldValue2, newValue2) -> {
			if ((newValue2 != null && !newValue2.equals(""))) {
				if (newValue2.toString().substring(newValue2.lastIndexOf(".") + 1).equalsIgnoreCase("zip") && jsonFilePath.getText().isEmpty()) {
					loadConfigurationOkBtn.setDisable(true);
				} else {
					loadConfigurationOkBtn.setDisable(false);
				}
			} else {
				loadConfigurationOkBtn.setDisable(true);
			}
		});
	}

	@FXML
	public void openImportFromChooser() {
		FileChooser fileChooser = new FileChooser();
		if (sx3ProjectSelection.isSelected()) {
			
			File jarPath = SX3Manager.getInstance().getInstallLocation();
			File templatesFolder = new File(jarPath.getParentFile().getAbsolutePath() + File.separator + "templates");
			
			if (templatesFolder.exists()) {
				fileChooser.setInitialDirectory(templatesFolder);
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SX3 Project", "*.zip"));
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText("SX3 Configuration Utility - Error Dialog");
				a.setContentText("No Template Projects Installed.");
				a.show();
				return;
			}
		} else {
			fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SX3 Project", "*.zip"),
					new FileChooser.ExtensionFilter("JSON", "*.json"));
		}

		File selectedFile = fileChooser.showOpenDialog(jsonFilePath.getScene().getWindow());
		if (selectedFile != null) {
			
			if(selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".")+1).equalsIgnoreCase("json")) {
				sx3ConfigurationToImport.setText(selectedFile.getAbsolutePath());
				jsonFilePath.setDisable(true);
				selectJsonFilePathBtn.setDisable(true);
			} else {
				ImportExportUtility importExportUtility = new ImportExportUtility();
				if(importExportUtility.validateZip(selectedFile.getAbsolutePath())) {
					sx3ConfigurationToImport.setText(selectedFile.getAbsolutePath());
					jsonFilePath.setDisable(false);
					selectJsonFilePathBtn.setDisable(false);
				} else {
					Alert a = new Alert(AlertType.ERROR);
					a.setHeaderText(null);
					a.setContentText("Invalid SX3 Project. Please select a Valid SX3 Project.");
					a.show();
				}
				
			}
		}
	}

	@FXML
	public void openImportToChooser() {
		DirectoryChooser fileChooser = new DirectoryChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File selectedFile = fileChooser.showDialog(jsonFilePath.getScene().getWindow());
		if (selectedFile != null) {
			jsonFilePath.setText(selectedFile.getAbsolutePath());
		}
	}

	boolean okClicked = false;

	private String importFromPath;

	@FXML
	public void okClicked() {
		importFromPath = sx3ConfigurationToImport.getText();
		importToPath = jsonFilePath.getText();
		okClicked = true;
		((Stage) jsonFilePath.getScene().getWindow()).close();
	}

	public void handleCancel() {
		((Stage) jsonFilePath.getScene().getWindow()).close();
		okClicked = false;
	}

	public TextField getJsonFilePath() {
		return jsonFilePath;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public String getImportToPath() {
		return importToPath;
	}

	public String getImportFromPath() {
		return importFromPath;
	}

}
