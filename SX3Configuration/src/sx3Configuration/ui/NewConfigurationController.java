package sx3Configuration.ui;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sx3Configuration.ui.validations.DeviceSettingsValidations;
import sx3Configuration.util.DeviceSettingsConstant;

public class NewConfigurationController implements Initializable {
	
	@FXML
	ComboBox<String> selectDevice;
	
	@FXML
	private Label fileExistError;
	
	@FXML
	TextField configurationFileName, txtFilePath;
	
	@FXML
	Button selectJsonFilePathBtn,createConfigurationOkBtn;

	private Properties tooltipAndErrorProperties;
	private String deviceName;
	private String filePath;
	private String fileName;
	
	public void setToolTipAndErrorProperties(Properties properties) {
		this.tooltipAndErrorProperties = properties;
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectDevice.getItems().addAll(DeviceSettingsConstant.DEVICE_NAME);
		selectDevice.valueProperty().addListener((observable, oldValue, newValue) -> {
		if ((configurationFileName.getText() != null && !configurationFileName.getText().equals(""))
				&& (txtFilePath.getText() != null && !txtFilePath.getText().equals("")) && newValue != null) {
			createConfigurationOkBtn.setDisable(false);
		} else {
			createConfigurationOkBtn.setDisable(true);
		}
	});
		configurationFileName.textProperty().addListener((observable1, oldValue1, newValue1) -> {
		if ((selectDevice.getValue() != null && !selectDevice.getValue().isEmpty())
				&& (txtFilePath.getText() != null && !txtFilePath.getText().isEmpty())) {
			createConfigurationOkBtn.setDisable(false);
		}
		if (newValue1 == null || newValue1.equals("")) {
			createConfigurationOkBtn.setDisable(true);
		}
	});
		txtFilePath.textProperty().addListener((observable2, oldValue2, newValue2) -> {
		if ((selectDevice.getValue() != null && !selectDevice.getValue().isEmpty())
				&& (configurationFileName.getText() != null && !configurationFileName.getText().isEmpty()) && newValue2 != null) {
			createConfigurationOkBtn.setDisable(false);
		}
		if (newValue2 == null || newValue2.equals("")) {
			createConfigurationOkBtn.setDisable(true);
		}
	});
	}
	
	@FXML
	public void validateConfigurationFileName() {
		DeviceSettingsValidations.fileNameValidation(configurationFileName,
				tooltipAndErrorProperties.getProperty("INVALID_FILE_NAME"));
	}
	
	@FXML
	public void openFileChooser() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File selectedDirectory = directoryChooser.showDialog(selectDevice.getScene().getWindow());
		if (selectedDirectory != null) {
			txtFilePath.setText(selectedDirectory.getAbsolutePath());
		}
	}
	
	boolean okClicked = false;
	
	@FXML
	public void okClicked() {
		boolean fileExit = new File(txtFilePath.getText(), configurationFileName.getText()).exists();
		if(fileExit == false) {
			createNewConfiguration();
		}else {
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setTitle("Warning");
			dialog.setResizable(true);
			dialog.setContentText(configurationFileName.getText()+" already exists.\nDo you want to replace it.");
			ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
			ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			dialog.getDialogPane().getButtonTypes().add(buttonCancel);
			Optional<ButtonType> showAndWait = dialog.showAndWait();
			if (showAndWait.get() == buttonTypeOk) {
				createNewConfiguration();
			}
			if (showAndWait.get() == buttonCancel) {
				dialog.close();
			}
		}
	}
	
	private void createNewConfiguration() {
		deviceName = selectDevice.getSelectionModel().getSelectedItem();
		filePath = txtFilePath.getText();
		fileName = configurationFileName.getText();
		okClicked =true;
		((Stage)selectDevice.getScene().getWindow()).close();
	}

	public void handleCancel() {
		((Stage)selectDevice.getScene().getWindow()).close();
		okClicked = false;
	}
	
	public String getDeviceName() {
		return deviceName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}

}
