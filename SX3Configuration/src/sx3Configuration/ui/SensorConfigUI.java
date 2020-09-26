package sx3Configuration.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sx3Configuration.model.FormatAndResolutions;
import sx3Configuration.model.HDMISourceConfiguration;
import sx3Configuration.model.SensorConfig;
import sx3Configuration.model.UACSetting;
import sx3Configuration.model.VideoSourceConfig;
import sx3Configuration.tablemodel.FormatAndResolutionTableModel;
import sx3Configuration.tablemodel.HDMISourceConfigurationTable;
import sx3Configuration.tablemodel.SensorConfigurationTable;
import sx3Configuration.ui.validations.SX3CommonUIValidations;
import sx3Configuration.util.AddSensorConfigData;

public class SensorConfigUI implements EventHandler<ActionEvent> {

	Button addBtn;
	Button editBtn;
	HDMISourceConfiguration hdmiSourceConfiguration;
	Button sensorConfigEditButton,samplingFrequencySensorConfig;
	HDMISourceConfigurationTable hdmiResourceConfigTable;
	UACSetting uacSetting;
	String frequencyName;
	List<SensorConfig> sensorConfigList;
	FormatAndResolutionTableModel formatAndResolution;
	FormatAndResolutions formatAndResolutionJson;
	TableView<FormatAndResolutionTableModel> formatResolutionTable;
//	Map<String,Boolean> framRateEmptyCheck;
	VideoSourceConfig videoSourceConfig;
	TextField txtFileSizeValue;
	
	

	public SensorConfigUI(Button addBtn, Button editBtn, HDMISourceConfiguration hdmiSourceConfiguration,
			HDMISourceConfigurationTable hdmiResourceConfigTable, Button sensorConfigEditButton) {
		this.addBtn = addBtn;
		this.editBtn = editBtn;
		this.hdmiSourceConfiguration = hdmiSourceConfiguration;
		this.sensorConfigEditButton = sensorConfigEditButton;
		this.hdmiResourceConfigTable = hdmiResourceConfigTable;

	}
	

	public SensorConfigUI(Button samplingFrequencySensorConfig, UACSetting uac_SETTING, String frequencyName) {
		this.samplingFrequencySensorConfig = samplingFrequencySensorConfig;
		this.uacSetting = uac_SETTING;
		this.frequencyName = frequencyName;
	}
	
	public SensorConfigUI(Button addBtn, Button editBtn,FormatAndResolutionTableModel formatAndResolution,FormatAndResolutions formatAndResolutionJson,
			Button sensorConfigEditButton,TableView<FormatAndResolutionTableModel> formatResolutionTable) {//,Map<String,Boolean> framRateEmptyCheck
		this.addBtn = addBtn;
		this.editBtn = editBtn;
		this.formatAndResolution = formatAndResolution;
		this.formatAndResolutionJson = formatAndResolutionJson;
		this.sensorConfigEditButton = sensorConfigEditButton;
		this.formatResolutionTable = formatResolutionTable;
//		this.framRateEmptyCheck = framRateEmptyCheck;
	}

	public SensorConfigUI(VideoSourceConfig videoSourceConfig, Button sensorConfigEditButton,
			TextField txtFileSizeValue) {
		this.videoSourceConfig = videoSourceConfig;
		this.sensorConfigEditButton = sensorConfigEditButton;
		this.txtFileSizeValue = txtFileSizeValue;
	}


	@Override
	public void handle(ActionEvent event) {
		String buttinId = ((Control) event.getSource()).getId();
		
		if(buttinId.startsWith("formatResolutionSensorConfig")) {
			buttinId = "formatResolutionSensorConfig";
		} else if(buttinId.startsWith("hdmiSensorConfigButton")) {
			buttinId = "hdmiSensorConfigButton";
		}

		switch (buttinId) {
		case "hdmiSensorConfigButton":
			if (!hdmiSourceConfiguration.getHDMI_SOURCE_REGISTER_ADDRESS().equals("") && !hdmiSourceConfiguration.getCOMPARE_VALUE().equals("")
					&& !hdmiSourceConfiguration.getMASK_PATTERN().equals("")) {
				openHDMISensorConfigurationDialog();
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText(null);
				a.setContentText("fill all columns");
				a.show();
			}
			break;
			
		case "samplingFrequencySensorConfig":
			openSampleFrequencySensorConfig();
			break;
			
		case "videoSourceSensorConfig":
			openVideoSourceSensorConfig();
			break;
			
		case "formatResolutionSensorConfig":
			if (formatAndResolutionJson.getIMAGE_FORMAT() != null
			&& formatAndResolutionJson.getH_RESOLUTION() != 0
			&& formatAndResolutionJson.getV_RESOLUTION() != 0
			&& (formatAndResolutionJson.getSUPPORTED_IN_FS().equals("Enable") 
					|| formatAndResolutionJson.getSUPPORTED_IN_HS().equals("Enable")
					|| formatAndResolutionJson.getSUPPORTED_IN_SS().equals("Enable"))) {
		openFormatResolutionSensorConfigurationDialog(addBtn, editBtn, formatAndResolution, formatAndResolutionJson,
				formatResolutionTable,sensorConfigEditButton);
			}else {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText(null);
				a.setContentText("fill all columns");
				a.show();
			}
			break;
			
		default:
			break;
		}

	}

	@SuppressWarnings("static-access")
	private void openVideoSourceSensorConfig() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText(null);
		dialog.setGraphic(null);
		dialog.setTitle("Video/Audio Source Configuration");
		dialog.setResizable(false);
		ObservableList<SensorConfigurationTable> sensorConfigTableRows = FXCollections.observableArrayList();
		BorderPane borderPane = new BorderPane();
		AnchorPane anchorPane = new AnchorPane();
		TableView<SensorConfigurationTable> sensorTable = new TableView<>();
		sensorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		sensorTable.setMaxHeight(200);
		anchorPane.setTopAnchor(sensorTable, 0.0);
		anchorPane.setLeftAnchor(sensorTable, 0.0);
		anchorPane.setRightAnchor(sensorTable, 0.0);
		anchorPane.setBottomAnchor(sensorTable, 0.0);
		sensorTable.setLayoutX(5);
		borderPane.setTop(anchorPane);
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(false);
		sensorConfigTableColumn(sensorConfigTableRows, sensorTable);
		
		/** Remove Button **/
		Button removeButton = removeButton();
		
		
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10.0);
		gridPane.add(sensorTable, 0, 0);
		gridPane.add(removeButton, 1, 0);
		anchorPane.getChildren().addAll(gridPane);
		dialog.getDialogPane().setContent(borderPane);
		
		AnchorPane anchorPane2 = new AnchorPane();
		GridPane gridPane1 = new GridPane();
		gridPane1.setHgap(10);
		
		/** Register Address **/
		TextField registerAddress = registerAddress(gridPane1);
		registerAddress.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			try {
				if(!newValue.equals("") && !validateHexString(newValue.substring(2))) {
					registerAddress.setText(oldValue);
					showErrorMessage2();
				}else if(!newValue.equals("") && Long.parseLong(newValue.substring(2),16) > 65535) {
					registerAddress.setText(oldValue);
					showErrorMessage1();
				}
			}catch(Exception e) {
				showErrorMessage2();
			}
		});
		
		/** Register Value **/
		TextField registerValue = registerValue(gridPane1);
		registerValue.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			try {
				if(!newValue.equals("") && !validateHexString1(newValue.substring(2))) {
					registerValue.setText(oldValue);
					showErrorMessage2();
				}else if(!newValue.equals("") && Long.parseLong(newValue.substring(2),16) > 4294967295L) {
					registerValue.setText(oldValue);
					showSizeErrorMessage1();
				}
			}catch(Exception e) {
				showErrorMessage2();
			}
		});
		
		/** Slave Address **/
		TextField slaveAddress = slaveAddress(gridPane1);
		slaveAddress.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			try {
				if(!newValue.equals("") && !validateHexString(newValue.substring(2))) {
					slaveAddress.setText(oldValue);
					showErrorMessage2();
				}else if(!newValue.equals("") && Long.parseLong(newValue.substring(2),16) > 65535) {
					slaveAddress.setText(oldValue);
					showErrorMessage1();
				}
			}catch(Exception e) {
				showErrorMessage2();
			}
		});

		Button editButton = editButton(borderPane, anchorPane2, gridPane1);
		
		AnchorPane anchorPane3 = new AnchorPane();
		GridPane gridPane2 = new GridPane();
		gridPane2.setHgap(10.0);
		TextArea textArea = createTextArea(gridPane2);
		
		GridPane gridPane3 = new GridPane();
		gridPane3.setHgap(10.0);
		gridPane3.setVgap(10.0);
		gridPane2.add(gridPane3, 1, 0);
		gridPane3.setPadding(new Insets(50,0,0,0));
		
		/** Immport Button **/
		Button importButton = importButton(gridPane3);
		
		final Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

		Button button = new Button("...");
		button.setTooltip(new Tooltip("Choose Video Source Configuration File (*.txt)"));
		button.setMaxHeight(15);
		button.setMaxWidth(15);
		button.setOnAction(e -> {
			configureFileChooser(fileChooser);
			File selectedFile = fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				List<SensorConfig> list = AddSensorConfigData.getSensorConfigDatafromFile(selectedFile);
				if(videoSourceConfig.getVIDEO_SOURCE_CONFIG() != null) {
					videoSourceConfig.getVIDEO_SOURCE_CONFIG().clear();
				}
				videoSourceConfig.setVIDEO_SOURCE_CONFIG(list);
				videoSourceConfig.setVIDEO_SOURCE_CONFIGURATION_SIZE(list.size());
				txtFileSizeValue.setText(String.valueOf(list.size()));
				List<SensorConfig> resRegSettings = videoSourceConfig.getVIDEO_SOURCE_CONFIG();
				showSensorConfigDataInTable(dialog, sensorConfigTableRows, resRegSettings);
			}
		});
		
		gridPane3.add(button, 0, 1);
		
		anchorPane3.setTopAnchor(gridPane2, 0.0);
		anchorPane3.setLeftAnchor(gridPane2, 0.0);
		anchorPane3.setRightAnchor(gridPane2, 0.0);
		anchorPane3.setBottomAnchor(gridPane2, 0.0);
		anchorPane3.getChildren().add(gridPane2);
		borderPane.setBottom(anchorPane3);
		
		textArea.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				importButton.setDisable(false);
			}
		});
		
		importButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(videoSourceConfig.getVIDEO_SOURCE_CONFIG() != null) {
					videoSourceConfig.getVIDEO_SOURCE_CONFIG().clear();
				}
				List<SensorConfig> list = AddSensorConfigData.AddSensorConfigDataInTable(textArea,sensorConfigTableRows);
				videoSourceConfig.setVIDEO_SOURCE_CONFIG(list);
				videoSourceConfig.setVIDEO_SOURCE_CONFIGURATION_SIZE(list.size());
				txtFileSizeValue.setText(String.valueOf(list.size()));
					List<SensorConfig> resRegSettings = videoSourceConfig.getVIDEO_SOURCE_CONFIG();
					showSensorConfigDataInTable(dialog, sensorConfigTableRows, resRegSettings);
			}

		});
		
		List<SensorConfig> resRegSettings = videoSourceConfig.getVIDEO_SOURCE_CONFIG();
		if(resRegSettings != null) {
			for (int i = 0; i < resRegSettings.size(); i++) {
				SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
				sensorConfigTable.setRegisterAddress(resRegSettings.get(i).getREGISTER_ADDRESS());
				sensorConfigTable.setRegisterValue(resRegSettings.get(i).getREGISTER_VALUE());
				sensorConfigTable.setSlaveAddress(resRegSettings.get(i).getSLAVE_ADDRESS());
				sensorConfigTableRows.add(sensorConfigTable);
			}
		}
		
		sensorTable.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) {
				SensorConfigurationTable selectedItem = sensorTable.getSelectionModel().getSelectedItem();
				registerAddress.setText(selectedItem.getRegisterAddress());
				registerAddress.setDisable(false);
				registerValue.setText(selectedItem.getRegisterValue());
				registerValue.setDisable(false);
				slaveAddress.setText(selectedItem.getSlaveAddress());
				slaveAddress.setDisable(false);
				}
			});
		
		registerAddress.textProperty().addListener((observable, oldValue, newValue) -> {
			registerValue.textProperty().addListener((observable1, oldValue1, newValue1) -> {
				slaveAddress.textProperty().addListener((observable2, oldValue2, newValue2) -> {
					editButton.setDisable(newValue == null || newValue1 == null || newValue2 == null);
				});
			});
		});
		
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SensorConfigurationTable selectedItem = sensorTable.getSelectionModel().getSelectedItem();
				selectedItem.setRegisterAddress(registerAddress.getText());
				registerAddress.setText("");
				registerAddress.setDisable(true);
				selectedItem.setRegisterValue(registerValue.getText());
				registerValue.setText("");
				registerValue.setDisable(true);
				selectedItem.setSlaveAddress(slaveAddress.getText());
				slaveAddress.setText("");
				slaveAddress.setDisable(true);
				sensorTable.refresh();
				editButton.setDisable(true);
				dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
			}
		});
		
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sensorTable.getItems().clear();
				videoSourceConfig.getVIDEO_SOURCE_CONFIG().clear();
				List<SensorConfig> sensorConfigList = new ArrayList<>();
				SensorConfig sensorConfig = new SensorConfig();
				sensorConfig.setREGISTER_ADDRESS("");
				sensorConfig.setREGISTER_VALUE("");
				sensorConfig.setSLAVE_ADDRESS("");
				sensorConfigList.add(sensorConfig);
				videoSourceConfig.setVIDEO_SOURCE_CONFIG(sensorConfigList);
				txtFileSizeValue.setText(String.valueOf(0));
				dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
				SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
				sensorConfigTable.setRegisterAddress("");
				sensorConfigTable.setRegisterValue("");
				sensorConfigTable.setSlaveAddress("");
				sensorConfigTableRows.add(sensorConfigTable);
			}
		});

		/** OK/Cancel Button **/
		dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
		Optional<String> showAndWait = dialog.showAndWait();
		if (showAndWait.isPresent()) {
			ObservableList<SensorConfigurationTable> items = sensorTable.getItems();
			if(items.size() != 0) {
				for(int i = 0;i < videoSourceConfig.getVIDEO_SOURCE_CONFIG().size();i++) {
					if(items.get(i).getRegisterAddress().equals("") && items.get(i).getRegisterValue().equals("")
							&& items.get(i).getSlaveAddress().equals("")) {
						
					}else {
						videoSourceConfig.getVIDEO_SOURCE_CONFIG().get(i).setREGISTER_ADDRESS(items.get(i).getRegisterAddress());
						videoSourceConfig.getVIDEO_SOURCE_CONFIG().get(i).setREGISTER_VALUE(items.get(i).getRegisterValue());
						videoSourceConfig.getVIDEO_SOURCE_CONFIG().get(i).setSLAVE_ADDRESS(items.get(i).getSlaveAddress());
					}
				}
				if(!videoSourceConfig.getVIDEO_SOURCE_CONFIG().get(0).getREGISTER_ADDRESS().equals("")
					|| !videoSourceConfig.getVIDEO_SOURCE_CONFIG().get(0).getREGISTER_VALUE().equals("")
					|| !videoSourceConfig.getVIDEO_SOURCE_CONFIG().get(0).getSLAVE_ADDRESS().equals("")) {
					sensorConfigEditButton.setStyle("-fx-background-color:green");
				}else {
					sensorConfigEditButton.setStyle("");
				}
			}else {
				sensorConfigEditButton.setStyle("");
			}
		} else {
			if (sensorConfigList!=null && sensorConfigList.size() > 0) {
				if (!sensorConfigList.get(0).getREGISTER_ADDRESS().equals("")
						|| !sensorConfigList.get(0).getREGISTER_VALUE().equals("")
						|| !sensorConfigList.get(0).getSLAVE_ADDRESS().equals("")) {
					samplingFrequencySensorConfig.setStyle("-fx-background-color:green");
				} else {
					samplingFrequencySensorConfig.setStyle("");
				}
			}
		}

	}
	
	private void showSensorConfigDataInTable(TextInputDialog dialog,
			ObservableList<SensorConfigurationTable> sensorConfigTableRows, List<SensorConfig> resRegSettings) {
		for (int i = 0; i < resRegSettings.size(); i++) {
			if (resRegSettings.get(i) != null) {
				if(!resRegSettings.get(i).getREGISTER_ADDRESS().equals("") || !resRegSettings.get(i).getREGISTER_VALUE().equals("")
						|| !resRegSettings.get(i).getSLAVE_ADDRESS().equals("")) {
					SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
					sensorConfigTable.setRegisterAddress(resRegSettings.get(i).getREGISTER_ADDRESS());
					sensorConfigTable.setRegisterValue(resRegSettings.get(i).getREGISTER_VALUE());
					sensorConfigTable.setSlaveAddress(resRegSettings.get(i).getSLAVE_ADDRESS());
					sensorConfigTableRows.add(sensorConfigTable);
				}
			}

		}
		if(sensorConfigTableRows != null && sensorConfigTableRows.size() > 0) {
			if(sensorConfigTableRows.get(0).getRegisterAddress().equals("") && sensorConfigTableRows.get(0).getRegisterValue().equals("")
					&& sensorConfigTableRows.get(0).getSlaveAddress().equals("")) {
				sensorConfigTableRows.remove(0);
			}
			dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
		}
	}


	@SuppressWarnings({ "static-access" })
	private void openFormatResolutionSensorConfigurationDialog(Button addBtn2, Button editBtn2,
			FormatAndResolutionTableModel formatAndResolution2, FormatAndResolutions formatAndResolutionJson2,
			TableView<FormatAndResolutionTableModel> formatResolutionTable2, Button sensorConfigEditButton2) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText(null);
		dialog.setGraphic(null);
		dialog.setTitle("Format And Resolution Configuration");
		dialog.setResizable(false);
		ObservableList<SensorConfigurationTable> sensorConfigTableRows = FXCollections.observableArrayList();
		BorderPane borderPane = new BorderPane();
		AnchorPane anchorPane = new AnchorPane();
		TableView<SensorConfigurationTable> sensorTable = new TableView<>();
		sensorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		sensorTable.setMaxHeight(200);
		anchorPane.setTopAnchor(sensorTable, 0.0);
		anchorPane.setLeftAnchor(sensorTable, 0.0);
		anchorPane.setRightAnchor(sensorTable, 0.0);
		anchorPane.setBottomAnchor(sensorTable, 0.0);
		sensorTable.setLayoutX(5);
		borderPane.setTop(anchorPane);
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(false);
		sensorConfigTableColumn(sensorConfigTableRows, sensorTable);
		
		/** Remove Button **/
		Button removeButton = removeButton();
		
		
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10.0);
		gridPane.add(sensorTable, 0, 0);
		gridPane.add(removeButton, 1, 0);
		anchorPane.getChildren().addAll(gridPane);
		dialog.getDialogPane().setContent(borderPane);
		
		AnchorPane anchorPane2 = new AnchorPane();
		GridPane gridPane1 = new GridPane();
		gridPane1.setHgap(10);
		
		/** Register Address **/
		TextField registerAddress = registerAddress(gridPane1);
		registerAddress.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			try {
				if(!newValue.equals("") && !validateHexString(newValue.substring(2))) {
					registerAddress.setText(oldValue);
					showErrorMessage2();
				}else if(!newValue.equals("") && Long.parseLong(newValue.substring(2),16) > 65535) {
					registerAddress.setText(oldValue);
					showErrorMessage1();
				}
			}catch(Exception e) {
				showErrorMessage2();
			}
		});
		
		/** Register Value **/
		TextField registerValue = registerValue(gridPane1);
		registerValue.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			try {
				if(!newValue.equals("") && !validateHexString1(newValue.substring(2))) {
					registerValue.setText(oldValue);
					showErrorMessage2();
				}else if(!newValue.equals("") && Long.parseLong(newValue.substring(2),16) > 4294967295L) {
					registerValue.setText(oldValue);
					showSizeErrorMessage1();
				}
			}catch(Exception e) {
				showErrorMessage2();
			}
		});
		
		/** Slave Address **/
		TextField slaveAddress = slaveAddress(gridPane1);
		slaveAddress.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			try {
				if(!newValue.equals("") && !validateHexString(newValue.substring(2))) {
					slaveAddress.setText(oldValue);
					showErrorMessage2();
				}else if(!newValue.equals("") && Long.parseLong(newValue.substring(2),16) > 65535) {
					slaveAddress.setText(oldValue);
					showErrorMessage1();
				}
			}catch(Exception e) {
				showErrorMessage2();
			}
		});


		Button editButton = editButton(borderPane, anchorPane2, gridPane1);
		
		AnchorPane anchorPane3 = new AnchorPane();
		GridPane gridPane2 = new GridPane();
		gridPane2.setHgap(10.0);
		TextArea textArea = createTextArea(gridPane2);
		
		GridPane gridPane3 = new GridPane();
		gridPane3.setHgap(10.0);
		gridPane3.setVgap(10.0);
		gridPane2.add(gridPane3, 1, 0);
		gridPane3.setPadding(new Insets(50,0,0,0));
		
		/** Immport Button **/
		Button importButton = importButton(gridPane3);
		
		final Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

		Button button = new Button("...");
		button.setTooltip(new Tooltip("Choose Video Source Configuration File (*.txt)"));
		button.setMaxHeight(15);
		button.setMaxWidth(15);
		button.setOnAction(e -> {
			configureFileChooser(fileChooser);
			File selectedFile = fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				List<SensorConfig> list = AddSensorConfigData.getSensorConfigDatafromFile(selectedFile);
				if (list.size() > 80) {
					Alert a = new Alert(AlertType.ERROR);
					a.setHeaderText(null);
					a.setContentText("Maximum 80 rows allowed");
					a.show();
				} else {
					if(formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING() != null) {
						formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().clear();
					}
					formatAndResolutionJson.setRESOLUTION_REGISTER_SETTING(list);
					formatAndResolutionJson.setRESOLUTION_REGISTER_SETTING_LEN(list.size());
					List<SensorConfig> resRegSettings = formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING();
					showSensorConfigDataInTable(dialog, sensorConfigTableRows, resRegSettings);
				}
			}
		});
		
		gridPane3.add(button, 0, 1);
		
		anchorPane3.setTopAnchor(gridPane2, 0.0);
		anchorPane3.setLeftAnchor(gridPane2, 0.0);
		anchorPane3.setRightAnchor(gridPane2, 0.0);
		anchorPane3.setBottomAnchor(gridPane2, 0.0);
		anchorPane3.getChildren().add(gridPane2);
		borderPane.setBottom(anchorPane3);
		
		textArea.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				importButton.setDisable(false);
			}
		});
		
		importButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				List<SensorConfig> list = AddSensorConfigData.AddSensorConfigDataInTable(textArea,sensorConfigTableRows);
				if (list.size() > 80) {
					Alert a = new Alert(AlertType.ERROR);
					a.setHeaderText(null);
					a.setContentText("Maximum 80 rows allowed");
					a.show();
				} else {
					if(formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING() != null) {
						formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().clear();
					}
					formatAndResolutionJson.setRESOLUTION_REGISTER_SETTING(list);
					formatAndResolutionJson.setRESOLUTION_REGISTER_SETTING_LEN(list.size());
					List<SensorConfig> resRegSettings = formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING();
					for (int i = 0; i < resRegSettings.size(); i++) {
						if (resRegSettings.get(i) != null) {
							SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
							sensorConfigTable.setRegisterAddress(resRegSettings.get(i).getREGISTER_ADDRESS());
							sensorConfigTable.setRegisterValue(resRegSettings.get(i).getREGISTER_VALUE());
							sensorConfigTable.setSlaveAddress(resRegSettings.get(i).getSLAVE_ADDRESS());
							sensorConfigTableRows.add(sensorConfigTable);
						}

					}
					dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				}
			}
		});
		
		List<SensorConfig> resRegSettings = formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING();
		if(resRegSettings != null) {
			for (int i = 0; i < resRegSettings.size(); i++) {
				SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
				sensorConfigTable.setRegisterAddress(resRegSettings.get(i).getREGISTER_ADDRESS());
				sensorConfigTable.setRegisterValue(resRegSettings.get(i).getREGISTER_VALUE());
				sensorConfigTable.setSlaveAddress(resRegSettings.get(i).getSLAVE_ADDRESS());
				sensorConfigTableRows.add(sensorConfigTable);
			}
		}
		
		sensorTable.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) {
				SensorConfigurationTable selectedItem = sensorTable.getSelectionModel().getSelectedItem();
				registerAddress.setText(selectedItem.getRegisterAddress());
				registerAddress.setDisable(false);
				registerValue.setText(selectedItem.getRegisterValue());
				registerValue.setDisable(false);
				slaveAddress.setText(selectedItem.getSlaveAddress());
				slaveAddress.setDisable(false);
				}
			});
		
		registerAddress.textProperty().addListener((observable, oldValue, newValue) -> {
			registerValue.textProperty().addListener((observable1, oldValue1, newValue1) -> {
				slaveAddress.textProperty().addListener((observable2, oldValue2, newValue2) -> {
					editButton.setDisable(newValue == null || newValue1 == null || newValue2 == null);
				});
			});
		});
		
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SensorConfigurationTable selectedItem = sensorTable.getSelectionModel().getSelectedItem();
				selectedItem.setRegisterAddress(registerAddress.getText());
				registerAddress.setText("");
				registerAddress.setDisable(true);
				selectedItem.setRegisterValue(registerValue.getText());
				registerValue.setText("");
				registerValue.setDisable(true);
				selectedItem.setSlaveAddress(slaveAddress.getText());
				slaveAddress.setText("");
				slaveAddress.setDisable(true);
				sensorTable.refresh();
				editButton.setDisable(true);
				dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
			}
		});
		
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sensorTable.getItems().clear();
				formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().clear();
				List<SensorConfig> sensorConfigList = new ArrayList<>();
				SensorConfig sensorConfig = new SensorConfig();
				sensorConfig.setREGISTER_ADDRESS("");
				sensorConfig.setREGISTER_VALUE("");
				sensorConfig.setSLAVE_ADDRESS("");
				sensorConfigList.add(sensorConfig);
				formatAndResolutionJson.setRESOLUTION_REGISTER_SETTING(sensorConfigList);
				dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
				SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
				sensorConfigTable.setRegisterAddress("");
				sensorConfigTable.setRegisterValue("");
				sensorConfigTable.setSlaveAddress("");
				sensorConfigTableRows.add(sensorConfigTable);
			}
		});

		/** OK/Cancel Button **/
		dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
		Optional<String> showAndWait = dialog.showAndWait();
		if (showAndWait.isPresent()) {
			formatAndResolution.getImageFormat().setDisable(true);
			formatAndResolution.getResolutionH().setDisable(true);
			formatAndResolution.getResolutionV().setDisable(true);
			formatAndResolution.getStillCapture().setDisable(true);
			formatAndResolution.getSupportedInFS().setDisable(true);
			formatAndResolution.getSupportedInHS().setDisable(true);
			formatAndResolution.getSupportedInSS().setDisable(true);
			formatAndResolution.getFrameRateInFS().setDisable(true);
			formatAndResolution.getFrameRateInHS().setDisable(true);
			formatAndResolution.getFrameRateInSS().setDisable(true);
			formatAndResolution.getButton().setDisable(true);
			addBtn.setDisable(false);
			editBtn.setDisable(true);
			ObservableList<SensorConfigurationTable> items = sensorTable.getItems();
			for(int i = 0;i < formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().size();i++) {
				formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(i).setREGISTER_ADDRESS(items.get(i).getRegisterAddress());
				formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(i).setREGISTER_VALUE(items.get(i).getRegisterValue());
				formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(i).setSLAVE_ADDRESS(items.get(i).getSlaveAddress());
			}
			if(!formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(0).getREGISTER_ADDRESS().equals("")
				|| !formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(0).getREGISTER_VALUE().equals("")
				|| !formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(0).getSLAVE_ADDRESS().equals("")) {
				sensorConfigEditButton.setStyle("-fx-background-color:green");
			}else {
				sensorConfigEditButton.setStyle("");
				addBtn.setDisable(true);
			}
		}else {
			if(formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().size() > 0 && !formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(0).getREGISTER_ADDRESS().equals("")
					|| !formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(0).getREGISTER_VALUE().equals("")
					|| !formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(0).getSLAVE_ADDRESS().equals("")) {
					sensorConfigEditButton.setStyle("-fx-background-color:green");
				}else {
					sensorConfigEditButton.setStyle("");
				}
		}
		
	}


	private void openSampleFrequencySensorConfig() {
		if(frequencyName.equals("FQ1")) {
			sensorConfigList = uacSetting.getSAMPLING_FREQUENCY_1_SENSOR_CONFIG();
			sensorConfigList = openSampleFrequencySensorConfigurationDialog("FQ1");
			uacSetting.setSAMPLING_FREQUENCY_1_SENSOR_CONFIG(sensorConfigList);
		}
		if(frequencyName.equals("FQ2")) {
			sensorConfigList = uacSetting.getSAMPLING_FREQUENCY_2_SENSOR_CONFIG();
			sensorConfigList = openSampleFrequencySensorConfigurationDialog("FQ2");
			uacSetting.setSAMPLING_FREQUENCY_2_SENSOR_CONFIG(sensorConfigList);
		}
		if(frequencyName.equals("FQ3")) {
			sensorConfigList = uacSetting.getSAMPLING_FREQUENCY_3_SENSOR_CONFIG();
			sensorConfigList = openSampleFrequencySensorConfigurationDialog("FQ3");
			uacSetting.setSAMPLING_FREQUENCY_3_SENSOR_CONFIG(sensorConfigList);
		}
		if(frequencyName.equals("FQ4")) {
			sensorConfigList = uacSetting.getSAMPLING_FREQUENCY_4_SENSOR_CONFIG();
			sensorConfigList = openSampleFrequencySensorConfigurationDialog("FQ4");
			uacSetting.setSAMPLING_FREQUENCY_4_SENSOR_CONFIG(sensorConfigList);
		}
		
	}


	@SuppressWarnings({ "static-access" })
	private List<SensorConfig> openSampleFrequencySensorConfigurationDialog(String sfNo) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText(null);
		dialog.setGraphic(null);
		if(sfNo.equals("FQ1")) {
			dialog.setTitle("Sampling Frequency 1 Config");
		}else if(sfNo.equals("FQ2")) {
			dialog.setTitle("Sampling Frequency 2 Config");
		}else if(sfNo.equals("FQ3")) {
			dialog.setTitle("Sampling Frequency 3 Config");
		}else if(sfNo.equals("FQ4")) {
			dialog.setTitle("Sampling Frequency 4 Config");
		}
		dialog.setResizable(false);
		ObservableList<SensorConfigurationTable> sensorConfigTableRows = FXCollections.observableArrayList();
		BorderPane borderPane = new BorderPane();
		AnchorPane anchorPane = new AnchorPane();
		TableView<SensorConfigurationTable> sensorTable = new TableView<>();
		sensorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		sensorTable.setMaxHeight(200);
		anchorPane.setTopAnchor(sensorTable, 0.0);
		anchorPane.setLeftAnchor(sensorTable, 0.0);
		anchorPane.setRightAnchor(sensorTable, 0.0);
		anchorPane.setBottomAnchor(sensorTable, 0.0);
		sensorTable.setLayoutX(5);
		borderPane.setTop(anchorPane);
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(false);
		sensorConfigTableColumn(sensorConfigTableRows, sensorTable);
		
		/** Remove Button **/
		Button removeButton = removeButton();
		
		
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10.0);
		gridPane.add(sensorTable, 0, 0);
		gridPane.add(removeButton, 1, 0);
		anchorPane.getChildren().addAll(gridPane);
		dialog.getDialogPane().setContent(borderPane);
		
		AnchorPane anchorPane2 = new AnchorPane();
		GridPane gridPane1 = new GridPane();
		gridPane1.setHgap(10);
		
		/** Register Address **/
		TextField registerAddress = registerAddress(gridPane1);
		registerAddress.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			try {
				if(!newValue.equals("") && !validateHexString(newValue.substring(2))) {
					registerAddress.setText(oldValue);
					showErrorMessage2();
				}else if(!newValue.equals("") && Long.parseLong(newValue.substring(2),16) > 65535) {
					registerAddress.setText(oldValue);
					showErrorMessage1();
				}
			}catch(Exception e) {
				showErrorMessage2();
			}
		});
		
		/** Register Value **/
		TextField registerValue = registerValue(gridPane1);
		registerValue.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			try {
				if(!newValue.equals("") && !validateHexString1(newValue.substring(2))) {
					registerValue.setText(oldValue);
					showErrorMessage2();
				}else if(!newValue.equals("") && Long.parseLong(newValue.substring(2),16) > 4294967295L) {
					registerValue.setText(oldValue);
					showSizeErrorMessage1();
				}
			}catch(Exception e) {
				showErrorMessage2();
			}
		});
		
		/** Slave Address **/
		TextField slaveAddress = slaveAddress(gridPane1);
		slaveAddress.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			try {
				if(!newValue.equals("") && !validateHexString(newValue.substring(2))) {
					slaveAddress.setText(oldValue);
					showErrorMessage2();
				}else if(!newValue.equals("") && Long.parseLong(newValue.substring(2),16) > 65535) {
					slaveAddress.setText(oldValue);
					showErrorMessage1();
				}
			}catch(Exception e) {
				showErrorMessage2();
			}
		});

		Button editButton = editButton(borderPane, anchorPane2, gridPane1);
		
		AnchorPane anchorPane3 = new AnchorPane();
		GridPane gridPane2 = new GridPane();
		gridPane2.setHgap(10.0);
		TextArea textArea = createTextArea(gridPane2);
		
		GridPane gridPane3 = new GridPane();
		gridPane3.setHgap(10.0);
		gridPane3.setVgap(10.0);
		gridPane2.add(gridPane3, 1, 0);
		gridPane3.setPadding(new Insets(50,0,0,0));
		
		/** Immport Button **/
		Button importButton = importButton(gridPane3);
		
		final Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

		Button button = new Button("...");
		button.setTooltip(new Tooltip("Load Sensor Config txt file."));
		button.setMaxHeight(15);
		button.setMaxWidth(15);
		button.setOnAction(e -> {
			configureFileChooser(fileChooser);
			File selectedFile = fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				sensorConfigList.clear();
				sensorConfigList = AddSensorConfigData.getSensorConfigDatafromFile(selectedFile);
				if (sensorConfigList.size() > 5) {
					Alert a = new Alert(AlertType.ERROR);
					a.setHeaderText(null);
					a.setContentText("Maximum 5 rows allowed");
					a.show();
				} else {
					showSensorConfigDataInTable(dialog, sensorConfigTableRows, sensorConfigList);
				}
			}
		});
		
		gridPane3.add(button, 0, 1);
		
		
		anchorPane3.setTopAnchor(gridPane2, 0.0);
		anchorPane3.setLeftAnchor(gridPane2, 0.0);
		anchorPane3.setRightAnchor(gridPane2, 0.0);
		anchorPane3.setBottomAnchor(gridPane2, 0.0);
		anchorPane3.getChildren().add(gridPane2);
		borderPane.setBottom(anchorPane3);
		
		textArea.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				importButton.setDisable(false);
			}
		});
		
		importButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sensorConfigList.clear();
				sensorConfigList = AddSensorConfigData.AddSensorConfigDataInTable(textArea,sensorConfigTableRows);
				if (sensorConfigList.size() > 5) {
					Alert a = new Alert(AlertType.ERROR);
					a.setHeaderText(null);
					a.setContentText("Maximum 5 rows allowed");
					a.show();
					sensorConfigList.clear();
				} else {
					for (int i = 0; i < sensorConfigList.size(); i++) {
						if (sensorConfigList.get(i) != null) {
							SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
							sensorConfigTable.setRegisterAddress(sensorConfigList.get(i).getREGISTER_ADDRESS());
							sensorConfigTable.setRegisterValue(sensorConfigList.get(i).getREGISTER_VALUE());
							sensorConfigTable.setSlaveAddress(sensorConfigList.get(i).getSLAVE_ADDRESS());
							sensorConfigTableRows.add(sensorConfigTable);
						}

					}
					dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				}
			}
		});
		
		for (int i = 0; i < sensorConfigList.size(); i++) {
			if (sensorConfigList.get(i) != null) {
				SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
				sensorConfigTable.setRegisterAddress(sensorConfigList.get(i).getREGISTER_ADDRESS());
				sensorConfigTable.setRegisterValue(sensorConfigList.get(i).getREGISTER_VALUE());
				sensorConfigTable.setSlaveAddress(sensorConfigList.get(i).getSLAVE_ADDRESS());
				sensorConfigTableRows.add(sensorConfigTable);
			}
		}
		
		sensorTable.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) {
				SensorConfigurationTable selectedItem = sensorTable.getSelectionModel().getSelectedItem();
				registerAddress.setText(selectedItem.getRegisterAddress());
				registerAddress.setDisable(false);
				registerValue.setText(selectedItem.getRegisterValue());
				registerValue.setDisable(false);
				slaveAddress.setText(selectedItem.getSlaveAddress());
				slaveAddress.setDisable(false);
				}
			});
		
		registerAddress.textProperty().addListener((observable, oldValue, newValue) -> {
			registerValue.textProperty().addListener((observable1, oldValue1, newValue1) -> {
				slaveAddress.textProperty().addListener((observable2, oldValue2, newValue2) -> {
					editButton.setDisable(newValue == null || newValue1 == null || newValue2 == null);
				});
			});
		});
		
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SensorConfigurationTable selectedItem = sensorTable.getSelectionModel().getSelectedItem();
				selectedItem.setRegisterAddress(registerAddress.getText());
				registerAddress.setText("");
				registerAddress.setDisable(true);
				selectedItem.setRegisterValue(registerValue.getText());
				registerValue.setText("");
				registerValue.setDisable(true);
				selectedItem.setSlaveAddress(slaveAddress.getText());
				slaveAddress.setText("");
				slaveAddress.setDisable(true);
				sensorTable.refresh();
				dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				editButton.setDisable(true);
			}
		});
		
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sensorTable.getItems().clear();
				sensorConfigList.clear();
				SensorConfig sensorConfig = new SensorConfig();
				sensorConfig.setREGISTER_ADDRESS("");
				sensorConfig.setREGISTER_VALUE("");
				sensorConfig.setSLAVE_ADDRESS("");
				sensorConfigList.add(sensorConfig);
				dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
				SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
				sensorConfigTable.setRegisterAddress("");
				sensorConfigTable.setRegisterValue("");
				sensorConfigTable.setSlaveAddress("");
				sensorConfigTableRows.add(sensorConfigTable);
			}
		});

		/** OK/Cancel Button **/
		dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
		Optional<String> showAndWait = dialog.showAndWait();
		if(showAndWait.isPresent()) {
			ObservableList<SensorConfigurationTable> items = sensorTable.getItems();
			for(int i = 0;i < sensorConfigList.size();i++) {
				sensorConfigList.get(i).setREGISTER_ADDRESS(items.get(i).getRegisterAddress());
				sensorConfigList.get(i).setREGISTER_VALUE(items.get(i).getRegisterValue());
				sensorConfigList.get(i).setSLAVE_ADDRESS(items.get(i).getSlaveAddress());
			}
			if(!sensorConfigList.get(0).getREGISTER_ADDRESS().equals("")
				|| !sensorConfigList.get(0).getREGISTER_VALUE().equals("")
				|| !sensorConfigList.get(0).getSLAVE_ADDRESS().equals("")) {
				samplingFrequencySensorConfig.setStyle("-fx-background-color:green");
			}else {
				samplingFrequencySensorConfig.setStyle("");
			}
		}else {
			if(!sensorConfigList.get(0).getREGISTER_ADDRESS().equals("")
					|| !sensorConfigList.get(0).getREGISTER_VALUE().equals("")
					|| !sensorConfigList.get(0).getSLAVE_ADDRESS().equals("")) {
					samplingFrequencySensorConfig.setStyle("-fx-background-color:green");
				}else {
					samplingFrequencySensorConfig.setStyle("");
				}
		}
		return sensorConfigList;
		
	}


	@SuppressWarnings({ "static-access" })
	private void openHDMISensorConfigurationDialog() {

		TextInputDialog dialog = new TextInputDialog();
		dialog.setResizable(false);
		dialog.setHeaderText(null);
		dialog.setGraphic(null);
		dialog.setTitle("HDMI Sensor Configuration");
		ObservableList<SensorConfigurationTable> sensorConfigTableRows = FXCollections.observableArrayList();
		BorderPane borderPane = new BorderPane();
		AnchorPane anchorPane = new AnchorPane();
		TableView<SensorConfigurationTable> sensorTable = new TableView<>();
		sensorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		sensorTable.setMaxHeight(200);
		anchorPane.setTopAnchor(sensorTable, 0.0);
		anchorPane.setLeftAnchor(sensorTable, 0.0);
		anchorPane.setRightAnchor(sensorTable, 0.0);
		anchorPane.setBottomAnchor(sensorTable, 0.0);
		sensorTable.setLayoutX(5);
		borderPane.setTop(anchorPane);
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(false);
		sensorConfigTableColumn(sensorConfigTableRows, sensorTable);
		
		/** Remove Button **/
		Button removeButton = removeButton();
		
		
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10.0);
		gridPane.add(sensorTable, 0, 0);
		gridPane.add(removeButton, 1, 0);
		anchorPane.getChildren().addAll(gridPane);
		dialog.getDialogPane().setContent(borderPane);
		
		AnchorPane anchorPane2 = new AnchorPane();
		GridPane gridPane1 = new GridPane();
		gridPane1.setHgap(10);
		
		/** Register Address **/
		TextField registerAddress = registerAddress(gridPane1);
		registerAddress.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			try {
				if(!newValue.equals("") && !validateHexString(newValue.substring(2))) {
					registerAddress.setText(oldValue);
					showErrorMessage2();
				}else if(!newValue.equals("") && Long.parseLong(newValue.substring(2),16) > 65535) {
					registerAddress.setText(oldValue);
					showErrorMessage1();
				}
			}catch(Exception e) {
				showErrorMessage2();
			}
		});
		
		/** Register Value **/
		TextField registerValue = registerValue(gridPane1);
		registerValue.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			try {
				if(!newValue.equals("") && !validateHexString1(newValue.substring(2))) {
					registerValue.setText(oldValue);
					showErrorMessage2();
				}else if(!newValue.equals("") && Long.parseLong(newValue.substring(2),16) > 4294967295L) {
					registerValue.setText(oldValue);
					showSizeErrorMessage1();
				}
			}catch(Exception e) {
				showErrorMessage2();
			}
		});
		
		/** Slave Address **/
		TextField slaveAddress = slaveAddress(gridPane1);
		slaveAddress.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			try {
				if(!newValue.equals("") && !validateHexString(newValue.substring(2))) {
					slaveAddress.setText(oldValue);
					showErrorMessage2();
				}else if(!newValue.equals("") && Long.parseLong(newValue.substring(2),16) > 65535) {
					slaveAddress.setText(oldValue);
					showErrorMessage1();
				}
			}catch(Exception e) {
				showErrorMessage2();
			}
		});

		Button editButton = editButton(borderPane, anchorPane2, gridPane1);
		
		AnchorPane anchorPane3 = new AnchorPane();
		GridPane gridPane2 = new GridPane();
		gridPane2.setHgap(10.0);
		TextArea textArea = createTextArea(gridPane2);
		
		GridPane gridPane3 = new GridPane();
		gridPane3.setHgap(10.0);
		gridPane3.setVgap(10.0);
		gridPane2.add(gridPane3, 1, 0);
		gridPane3.setPadding(new Insets(50,0,0,0));
		
		/** Immport Button **/
		Button importButton = importButton(gridPane3);
		
		final Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

		Button button = new Button("...");
		button.setTooltip(new Tooltip("Load Sensor config from file."));
		button.setMaxHeight(15);
		button.setMaxWidth(15);
		button.setOnAction(e -> {
			configureFileChooser(fileChooser);
			File selectedFile = fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				List<SensorConfig> list = AddSensorConfigData.getSensorConfigDatafromFile(selectedFile);
				if (list.size() > 30) {
					Alert a = new Alert(AlertType.ERROR);
					a.setHeaderText(null);
					a.setContentText("Maximum 30 rows allowed");
					a.show();
				} else {
					if(hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING() != null) {
						hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().clear();
					}
					hdmiSourceConfiguration.setHDMI_SOURCE_CONFIG_NUM_ROWS(list.size());
					hdmiSourceConfiguration.setRESOLUTION_REGISTER_SETTING(list);
					List<SensorConfig> resRegSettings = hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING();
					showSensorConfigDataInTable(dialog, sensorConfigTableRows, resRegSettings);
				}
			}
		});
		
		gridPane3.add(button, 0, 1);
		
		
		anchorPane3.setTopAnchor(gridPane2, 0.0);
		anchorPane3.setLeftAnchor(gridPane2, 0.0);
		anchorPane3.setRightAnchor(gridPane2, 0.0);
		anchorPane3.setBottomAnchor(gridPane2, 0.0);
		anchorPane3.getChildren().add(gridPane2);
		borderPane.setBottom(anchorPane3);
		
		textArea.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				importButton.setDisable(false);
			}
		});
		
		importButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				List<SensorConfig> list = AddSensorConfigData.AddSensorConfigDataInTable(textArea,sensorConfigTableRows);
				if (list.size() > 30) {
					Alert a = new Alert(AlertType.ERROR);
					a.setHeaderText(null);
					a.setContentText("Maximum 30 rows allowed");
					a.show();
				} else if(list.size() != 0){
					if(hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING() != null) {
						hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().clear();
					}
					hdmiSourceConfiguration.setHDMI_SOURCE_CONFIG_NUM_ROWS(list.size());
					hdmiSourceConfiguration.setRESOLUTION_REGISTER_SETTING(list);
					List<SensorConfig> resRegSettings = hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING();
					for (int i = 0; i < resRegSettings.size(); i++) {
						if (resRegSettings.get(i) != null) {
							SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
							sensorConfigTable.setRegisterAddress(resRegSettings.get(i).getREGISTER_ADDRESS());
							sensorConfigTable.setRegisterValue(resRegSettings.get(i).getREGISTER_VALUE());
							sensorConfigTable.setSlaveAddress(resRegSettings.get(i).getSLAVE_ADDRESS());
							sensorConfigTableRows.add(sensorConfigTable);
						}

					}
					dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				}
			}
		});
		
		List<SensorConfig> resRegSettings = hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING();
		if(resRegSettings != null) {
			for (int i = 0; i < resRegSettings.size(); i++) {
				SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
				sensorConfigTable.setRegisterAddress(resRegSettings.get(i).getREGISTER_ADDRESS());
				sensorConfigTable.setRegisterValue(resRegSettings.get(i).getREGISTER_VALUE());
				sensorConfigTable.setSlaveAddress(resRegSettings.get(i).getSLAVE_ADDRESS());
				sensorConfigTableRows.add(sensorConfigTable);
			}
		}
		
		sensorTable.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) {
				SensorConfigurationTable selectedItem = sensorTable.getSelectionModel().getSelectedItem();
				registerAddress.setText(selectedItem.getRegisterAddress());
				registerAddress.setDisable(false);
				registerValue.setText(selectedItem.getRegisterValue());
				registerValue.setDisable(false);
				slaveAddress.setText(selectedItem.getSlaveAddress());
				slaveAddress.setDisable(false);
				}
			});
		
		registerAddress.textProperty().addListener((observable, oldValue, newValue) -> {
			registerValue.textProperty().addListener((observable1, oldValue1, newValue1) -> {
				slaveAddress.textProperty().addListener((observable2, oldValue2, newValue2) -> {
					editButton.setDisable(newValue == null || newValue1 == null || newValue2 == null);
				});
			});
		});
		
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SensorConfigurationTable selectedItem = sensorTable.getSelectionModel().getSelectedItem();
				selectedItem.setRegisterAddress(registerAddress.getText());
				registerAddress.setText("");
				registerAddress.setDisable(true);
				selectedItem.setRegisterValue(registerValue.getText());
				registerValue.setText("");
				registerValue.setDisable(true);
				selectedItem.setSlaveAddress(slaveAddress.getText());
				slaveAddress.setText("");
				slaveAddress.setDisable(true);
				sensorTable.refresh();
				editButton.setDisable(true);
				dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
			}
		});
		
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sensorTable.getItems().clear();
				hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().clear();
				List<SensorConfig> sensorConfigList = new ArrayList<>();
				SensorConfig sensorConfig = new SensorConfig();
				sensorConfig.setREGISTER_ADDRESS("");
				sensorConfig.setREGISTER_VALUE("");
				sensorConfig.setSLAVE_ADDRESS("");
				sensorConfigList.add(sensorConfig);
				hdmiSourceConfiguration.setHDMI_SOURCE_CONFIG_NUM_ROWS(0);
				hdmiSourceConfiguration.setRESOLUTION_REGISTER_SETTING(sensorConfigList);
				dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
				SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
				sensorConfigTable.setRegisterAddress("");
				sensorConfigTable.setRegisterValue("");
				sensorConfigTable.setSlaveAddress("");
				sensorConfigTableRows.add(sensorConfigTable);
			}
		});

		/** OK/Cancel Button **/
		dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
		Optional<String> showAndWait = dialog.showAndWait();
		if (showAndWait.isPresent()) {
			hdmiResourceConfigTable.getHDMISourceRegisterAddress().setDisable(true);
			hdmiResourceConfigTable.getCompareValue().setDisable(true);
			hdmiResourceConfigTable.getMaskValue().setDisable(true);
			addBtn.setDisable(false);
			editBtn.setDisable(true);
			sensorConfigEditButton.setDisable(true);
			ObservableList<SensorConfigurationTable> items = sensorTable.getItems();
			for(int i = 0;i < hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().size();i++) {
				hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().get(i).setREGISTER_ADDRESS(items.get(i).getRegisterAddress());
				hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().get(i).setREGISTER_VALUE(items.get(i).getRegisterValue());
				hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().get(i).setSLAVE_ADDRESS(items.get(i).getSlaveAddress());
			}
			if(!hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().get(0).getREGISTER_ADDRESS().equals("")
				|| !hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().get(0).getREGISTER_VALUE().equals("")
				|| !hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().get(0).getSLAVE_ADDRESS().equals("")) {
				sensorConfigEditButton.setStyle("-fx-background-color:green");
			}else {
				sensorConfigEditButton.setStyle("");
				addBtn.setDisable(true);
			}
		}else {
			if(!hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().get(0).getREGISTER_ADDRESS().equals("")
					|| !hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().get(0).getREGISTER_VALUE().equals("")
					|| !hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().get(0).getSLAVE_ADDRESS().equals("")) {
					sensorConfigEditButton.setStyle("-fx-background-color:green");
				}else {
					sensorConfigEditButton.setStyle("");
//					addBtn.setDisable(true);
				}
//			if(!sensorConfigList.get(0).getREGISTER_ADDRESS().equals("")
//					|| !sensorConfigList.get(0).getREGISTER_VALUE().equals("")
//					|| !sensorConfigList.get(0).getSLAVE_ADDRESS().equals("")) {
//					samplingFrequencySensorConfig.setStyle("-fx-background-color:green");
//				}else {
//					samplingFrequencySensorConfig.setStyle("");
//				}
		}
	}
	
	
	private void configureFileChooser(FileChooser fileChooser) {
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt", "*.txt"));

	}

	private Button importButton(GridPane gridPane2) {
		Button importButton = new Button();
		importButton.setDisable(true);
		importButton.setId("ConvertToJson");
		importButton.setTooltip(new Tooltip("Convert To JSON"));
		ImageView importImage = new ImageView("/resources/import.png");
		importImage.setFitHeight(13);
		importImage.setFitWidth(13);
		importButton.setGraphic(importImage);
		gridPane2.add(importButton, 0, 0);
		return importButton;
	}

	private Button removeButton() {
		Button removeButton = new Button();
		removeButton.setTooltip(new Tooltip("Remove table"));
		ImageView removeImage = new ImageView("/resources/deleteRow.png");
		removeImage.setFitHeight(15);
		removeImage.setFitWidth(15);
		removeButton.setGraphic(removeImage);
		return removeButton;
	}

	private TextField slaveAddress(GridPane gridPane1) {
		TextField slaveAddress = new TextField();
		slaveAddress.setDisable(true);
		slaveAddress.setPrefWidth(160.0);
		slaveAddress.setPromptText("0x1");
		gridPane1.add(slaveAddress, 2, 0);
		return slaveAddress;
	}

	private TextField registerValue(GridPane gridPane1) {
		TextField registerValue = new TextField();
		registerValue.setDisable(true);
		registerValue.setPrefWidth(160.0);
		registerValue.setPromptText("0x1");
		gridPane1.add(registerValue, 1, 0);
		return registerValue;
	}

	private TextField registerAddress(GridPane gridPane1) {
		TextField registerAddress = new TextField();
		registerAddress.setDisable(true);
		registerAddress.setPrefWidth(160.0);
		registerAddress.setPromptText("0x6000");
		gridPane1.add(registerAddress, 0, 0);
		return registerAddress;
	}

	@SuppressWarnings("static-access")
	private Button editButton(BorderPane borderPane, AnchorPane anchorPane2, GridPane gridPane1) {
		/** Edit Button **/
		Button editButton = new Button();
		editButton.setDisable(true);
		editButton.setTooltip(new Tooltip("Update table"));
		ImageView editImage = new ImageView("/resources/editRow.png");
		editImage.setFitHeight(15);
		editImage.setFitWidth(15);
		editButton.setGraphic(editImage);
		
		gridPane1.add(editButton, 3, 0);
		
		anchorPane2.getChildren().addAll(gridPane1);
		anchorPane2.setTopAnchor(gridPane1, 0.0);
		anchorPane2.setLeftAnchor(gridPane1, 0.0);
		anchorPane2.setRightAnchor(gridPane1, 0.0);
		anchorPane2.setBottomAnchor(gridPane1, 0.0);
		borderPane.setCenter(anchorPane2);
		borderPane.setMargin(anchorPane2, new Insets(5,0,5,0));
		return editButton;
	}
	
	private TextArea createTextArea(GridPane gridPane2) {
		TextArea textArea = new TextArea();
		textArea.setId("registerTextArea");
		textArea.setPromptText("{REGISTER ADDRESS, REGISTER VALUE,SLAVE ADDRESS}, \r\n" + 
				"{REGISTER ADDRESS, REGISTER VALUE,SLAVE ADDRESS},");
		textArea.setPrefWidth(500.0);
		gridPane2.add(textArea, 0, 0);
		return textArea;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void sensorConfigTableColumn(ObservableList<SensorConfigurationTable> sensorConfigTableRows,
			TableView<SensorConfigurationTable> sensorTable) {
		/** Register Address **/
		TableColumn firstColumn = new TableColumn("Register Address");
		firstColumn.setCellValueFactory(new PropertyValueFactory<SensorConfigurationTable, String>("registerAddress"));

		/** Register Value **/
		TableColumn secondColumn = new TableColumn("Register Value");
		secondColumn.setCellValueFactory(new PropertyValueFactory<SensorConfigurationTable, String>("registerValue"));

		/** Slave Address **/
		TableColumn threeColumn = new TableColumn("Slave Address");
		threeColumn.setCellValueFactory(new PropertyValueFactory<SensorConfigurationTable, String>("slaveAddress"));
		sensorTable.getColumns().addAll(firstColumn, secondColumn, threeColumn);

		sensorTable.setItems(sensorConfigTableRows);
		sensorTable.setPrefWidth(500.0);
		sensorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	private static List<SensorConfig> showErrorMessage1() {
		Alert a = new Alert(AlertType.ERROR);
		a.setHeaderText(null);
		a.setContentText("Max size 2 bytes");
		a.show();
		List<SensorConfig> list = new ArrayList<>();
		return list;
	}
	
	private static List<SensorConfig> showSizeErrorMessage1() {
		Alert a = new Alert(AlertType.ERROR);
		a.setHeaderText(null);
		a.setContentText("Max size 4 bytes");
		a.show();
		List<SensorConfig> list = new ArrayList<>();
		return list;
	}
	
	private static List<SensorConfig> showErrorMessage2() {
		Alert a = new Alert(AlertType.ERROR);
		a.setHeaderText(null);
		a.setContentText("Please enter Hex value.");
		a.show();
		List<SensorConfig> list = new ArrayList<>();
		return list;
	}
	
	public static boolean validateHexString(String hexVal) {
		if(hexVal == null || hexVal.equals("")) {
			return true;
		}else {
			return SX3CommonUIValidations.isValidHex(hexVal);
		}
	}
	
	private static boolean validateHexString1(String hexVal) {
		if(hexVal == null || hexVal.equals("")) {
			return true;
		}else {
			return SX3CommonUIValidations.isValidHex1(hexVal);
		}
	}

}
