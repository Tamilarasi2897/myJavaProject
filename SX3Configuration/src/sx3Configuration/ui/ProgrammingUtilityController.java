package sx3Configuration.ui;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sx3Configuration.mergertool.HexConversionErrors;
import sx3Configuration.mergertool.MergeFinalFirmwareArtifacts;
import sx3Configuration.programming.OSValidator;
import sx3Configuration.programming.OSValidator.Level;
import sx3Configuration.programming.SX3ConfigurationProgrammingUtility;

public class ProgrammingUtilityController implements Initializable {

	@FXML
	Label lblProgrammingStatus;

	@FXML
	ProgressBar progressBar;
	
	@FXML
	Button btnProgram, btnRefresh;

	private Stage stage;
	
	public void setStage(Stage stage) {
		this.stage = stage;		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		refreshDevice();
		
		lblProgrammingStatus.setWrapText(true);
		ImageView programImage = new ImageView(getClass().getResource("/resources/program.gif").toString());
		btnProgram.setGraphic(programImage);
		
		ImageView refreshImage = new ImageView(getClass().getResource("/resources/refresh.gif").toString());
		btnRefresh.setGraphic(refreshImage);
	}
	
	@FXML
	public void handleClose() {
		stage.close();
	}

	@SuppressWarnings("rawtypes")
	@FXML
	public void programDevice() {
		progressBar.setProgress(-1);
		Task copyWorker = createWorker();

		progressBar.progressProperty().unbind();
		progressBar.progressProperty().bind(copyWorker.progressProperty());

		copyWorker.messageProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println(observable + " Inside Thread >> " + newValue);
				lblProgrammingStatus.setText(newValue);
				SX3Manager.getInstance().addLog(newValue + "<br>");
				lblProgrammingStatus.setWrapText(true);
			}
		});

		new Thread(copyWorker).start();
	}

	@SuppressWarnings("rawtypes")
	public Task createWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				btnProgram.setDisable(true);
				btnRefresh.setDisable(true);
				// Before programming we need to merge the list of files
				updateMessage("Merging the Artifacts");
				String status = new String();
				try {
					HexConversionErrors result = MergeFinalFirmwareArtifacts.mergeAllFiles();
					switch (result) {
					case BASE_IMAGE_FILE_MISSING:
						status = "Image Creation Failed. Base Image File Missing.";
						break;
					case HEX_CONVERSION_FAILED:
						status ="Image Creation Failed. SX3 Configuration Error.";
						break;
					case HEX_FILE_MISSING:
						status ="Image Creation Failed. Hex File Missing.";
						break;
					case BIT_FILE_MISSING:
						status = "Image Creation Failed. BIT File Missing.";
						break;
					case IMAGE_CONVERSION_FAILED:
						status = "Image Creation Failed. Refer to Error Log.";
						break;

					default:
						status = "Image Creation Successful.";
						break;
					}
					
					if(!result.equals(HexConversionErrors.MERGE_SUCCESS)) {
						btnRefresh.setDisable(false);
						updateMessage(status);
						updateProgress(2, 2);
						return status;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				updateMessage("Programming the Device");
				SX3ConfigurationProgrammingUtility programmingUtility = new SX3ConfigurationProgrammingUtility();
				File sx3MergedHexFile = MergeFinalFirmwareArtifacts.getSX3MergedHexFile();
				String program = programmingUtility
						.program(sx3MergedHexFile.getAbsolutePath());
				updateMessage(program);
				updateProgress(2, 2);
				
				btnRefresh.setDisable(false);
				return program;
			}
		};
	}
	
	@FXML
	public void refreshDevice() {
		progressBar.progressProperty().unbind();
		progressBar.setProgress(0);
		
		SX3ConfigurationProgrammingUtility programmingUtility = new SX3ConfigurationProgrammingUtility();
		List<String> deviceList = programmingUtility.getDeviceList();
		
		if(deviceList.size() > 0 && deviceList.get(0).startsWith("Error :")) {
			SX3Manager.getInstance().addLog(deviceList.get(0) + "<br>");
			lblProgrammingStatus.setText(deviceList.get(0));
			btnProgram.setDisable(true);
			return;
		}
		
		if(deviceList.size() > 1) {
			SX3Manager.getInstance().addLog("Multiple Device Connected. Currently we support 1 Device Programming. <br>");
			lblProgrammingStatus.setText("Multiple Device Connected. Currently we support 1 Device Programming.");
			btnProgram.setDisable(true);
		} else if(deviceList.size() == 1){
			
			OSValidator osValidator = new OSValidator();
			Level os = osValidator.getOS();
			
			switch (os) {
			case WINDOWS:
				
				if(!deviceList.get(0).contains("Found")) {
					lblProgrammingStatus.setText("Ready to Program");
					btnProgram.setDisable(false);
				}
				
				break;

			default:
				lblProgrammingStatus.setText("Ready to Program");
				btnProgram.setDisable(false);
				break;
			}
			
			
			
		} else {
			SX3Manager.getInstance().addLog("No Cypress FX3 USB BootLoader Device Found.<br>");
			lblProgrammingStatus.setText("No Cypress FX3 USB BootLoader Device Found.");
			btnProgram.setDisable(true);
		}
		
	}

}
