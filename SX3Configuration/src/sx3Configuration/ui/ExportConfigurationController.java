package sx3Configuration.ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ExportConfigurationController implements Initializable {
	
	@FXML
	Button selectExportFilePathBtn, exportConfigurationOkBtn;
	
	@FXML
	TextField exportPath;

	private String exportToPath;

	private boolean okClicked;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		exportPath.textProperty().addListener((observable2, oldValue2, newValue2) -> {
			if ((newValue2 != null && !newValue2.equals(""))) {
				exportConfigurationOkBtn.setDisable(false);
			} else {
				exportConfigurationOkBtn.setDisable(true);
			}
		});
	}
	
	@FXML
	public void okClicked() {
		exportToPath = exportPath.getText();
		okClicked = true;
		((Stage) exportPath.getScene().getWindow()).close();
	}
	
	public String getExportToPath() {
		return exportToPath;
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	public void handleCancel() {
		((Stage) exportPath.getScene().getWindow()).close();
		okClicked = false;
	}

	@FXML
	public void openExportToChooser( ) {
		DirectoryChooser fileChooser = new DirectoryChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File selectedFile = fileChooser.showDialog(exportPath.getScene().getWindow());
		if (selectedFile != null) {
			exportPath.setText(selectedFile.getAbsolutePath());
		}
	}
}
