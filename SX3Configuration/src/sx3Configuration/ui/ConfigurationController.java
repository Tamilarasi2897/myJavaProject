package sx3Configuration.ui;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import org.configureme.util.UnicodeReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sx3Configuration.mergertool.BytesStreamsAndHexFileUtil;
import sx3Configuration.mergertool.SX3ConfigHexFileUtil;
import sx3Configuration.mergertool.SX3PropertiesConstants;
import sx3Configuration.model.AuxilliaryInterface;
import sx3Configuration.model.CameraControl;
import sx3Configuration.model.ColorMatching;
import sx3Configuration.model.ConfigTableGeneral;
import sx3Configuration.model.ConfigTableOffSetTable;
import sx3Configuration.model.DebugLevel;
import sx3Configuration.model.DeviceSettings;
import sx3Configuration.model.EndpointSettings;
import sx3Configuration.model.ExtensionUnitControl;
import sx3Configuration.model.FifoMasterConfig;
import sx3Configuration.model.FormatAndResolution;
import sx3Configuration.model.FormatAndResolutions;
import sx3Configuration.model.GPIOs;
import sx3Configuration.model.HDMISourceConfiguration;
import sx3Configuration.model.ProcessingUnitControl;
import sx3Configuration.model.SX3Configuration;
import sx3Configuration.model.SensorConfig;
import sx3Configuration.model.SlaveFIFOSettings;
import sx3Configuration.model.UACSetting;
import sx3Configuration.model.UACSettings;
import sx3Configuration.model.USBSettings;
import sx3Configuration.model.UVCSettings;
import sx3Configuration.model.VideoSourceConfig;
import sx3Configuration.tablemodel.CameraAndProcessingUnitControlsTableModel;
import sx3Configuration.tablemodel.FormatAndResolutionTableModel;
import sx3Configuration.tablemodel.HDMISourceConfigurationTable;
import sx3Configuration.ui.validations.DeviceSettingsValidations;
import sx3Configuration.ui.validations.SX3CommonUIValidations;
import sx3Configuration.ui.validations.SlaveFifoSettingValidation;
import sx3Configuration.ui.validations.UVCSettingsValidation;
import sx3Configuration.ui.validations.VideoSourceConfigValidation;
import sx3Configuration.util.DeviceSettingsConstant;
import sx3Configuration.util.OnMouseClickHandler;
import sx3Configuration.util.SX3ConfigCommonUtil;
import sx3Configuration.util.SX3ConfiguartionHelp;
import sx3Configuration.util.UACSettingFieldConstants;
import sx3Configuration.util.UVCSettingsConstants;
import sx3Configuration.webView.RegisterHyperlinkHandler;

public class ConfigurationController implements Initializable {

	private static final String CYPRESS_EZ_USB_SX3_CONFIGURATION_UTILITY_TITLE = "Cypress EZ-USB SX3 Configuration Utility";
	private static final String RELEASE_DATE = "07092020";
	private String urlDarkTheme = getClass().getResource("fxmls/dark-theme.css").toExternalForm();
	private String urlDefaultTheme = getClass().getResource("fxmls/application.css").toExternalForm();

	@FXML
	private ComboBox<String> debugValue, fpgaFamily, powerConfiguration, fifoBusWidth, gpio1, gpio2, gpio3, gpio4,
			gpio5, gpio6, gpio7, serialNumberIncrement, uvcVersion, uvcHeaderAddition, deviceSttingI2CFrequency;

	@FXML
	private SplitPane root;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private ChoiceBox<String> Endpoint_1, Endpoint_2, interFaceType;// , noOfEndpoint

	@FXML
	private CheckBox enableDebugLevel, enableFPGA, enableRemoteWakeup, autoGenerateSerialNumber, deviceSttingFirmWare;

	@FXML
	private Label deviceName, logFileName, versionId;

	@FXML
	private AnchorPane welcomeScreen, logHelpSplitpane;

	@FXML
	private Button newConfig, openConfig, saveConfigFromToolBar, undo, redo, programConfigFromToolBar, browseBitFile,
			saveLog, clearLog, exportConfiguration;

	@FXML
	private MenuItem saveConfigFromMenu, programConfigFromMenuBar, saveAsConfigFromMenu;

	@FXML
	private TextField chooseBitFile, i2cSlaveAddress, vendorId, productId, manufacture, productString, serialNumber,
			fifoClockFrequency, bitFileSize;

	@FXML
	private TabPane videoSourceConfigEndpoint, uvcuacTabpane, videoSourceConfigTabpane, slaveFifoSettings,
			slaveFifoSettingsTabpane;

	@FXML
	private Tab helpTab, deviceSettingsTab, uvcuacSettingsTab, videoSourceConfigTab, fifoMasterConfigTab,
			slaveFifoSettingsTab, logTab;

	@FXML
	private WebView logDetails1, helpContent;

	@FXML
	private WebView startScreen;

	@FXML
	private GridPane endpointsGridPane;

	@FXML
	private TabPane deviceSettingsTabSplitpane;

	private String sx3ConfigurationFilePath;

	private SX3Configuration sx3Configuration;

	private List<UVCSettings> uvcSettingList = new ArrayList<>(2);

	private List<VideoSourceConfig> videoSourceConfigList = new ArrayList<VideoSourceConfig>(2);

	private List<UACSettings> uacSettingList = new ArrayList<>(2);

	private boolean performLoadAction = false;

	private List<SlaveFIFOSettings> slaveFifoSettingList = new ArrayList<>();

	private SX3ConfiguartionHelp sx3ConfigurationHelp = new SX3ConfiguartionHelp();

	private List<String> loadEndpoints = new ArrayList<String>();

	private Map<String, Boolean> uvcuacTabErrorList = new HashMap<>();

	private Map<String, Boolean> deviceSettingTabErrorList = new HashMap<>();

	private Map<String, Boolean> fifoMasterTabErrorList = new HashMap<>();

	private Map<String, Boolean> videoSourceConfigTabErrorList = new HashMap<>();

	private Map<String, Boolean> endpointSettingsTabErrorList = new HashMap<>();

	private boolean endpointOneChanges = false;
	private boolean endpointTwoChanges = false;
	private Map<String, String> endpointValues = new HashMap<>();
	private boolean endpointOneActionPerformed = false;
	private boolean endpointTwoActionPerformed = false;
	final ContextMenu numericValidator = new ContextMenu();
	private StringBuffer logDetails = new StringBuffer();
	private TextArea logDetails2 = new TextArea();
	private TextArea logDetails3 = new TextArea();
	private boolean checkFieldEditOrNot = false;
	private Properties tooltipAndErrorProperties = new Properties();
	private Map<String, Boolean> errorListOnSave = new HashMap<>();
	private boolean performSaveAction = false;
	private List<String> s1 = new ArrayList<>();
	private Map<String, String> videoSourceType = new HashMap<>();
	private boolean interfaceChange = false;
	private boolean slaveChange = false;
	private boolean imageSensorChangeChange = false;

	private Main application;
	private WebEngine webEngine;

	public ConfigurationController(Main main) {
		this.application = main;
	}

	/**
	 * Init method of controller. Enable/Disable tab when application loaded. Set
	 * tooltip on toolbar icon. Log device setting field value. Load welcome page.
	 * Load Help content
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		programConfigFromMenuBar.setDisable(true);
		programConfigFromToolBar.setDisable(true);
		logHelpSplitpane.setMaxHeight(200.0);
		checkFieldEditOrNot = false;
		endpointOneChanges = false;
		endpointTwoChanges = false;
		deviceSettingsTabSplitpane.setVisible(false);
		welcomeScreen.setVisible(true);

		versionId.setText("Version  1.0_" + RELEASE_DATE);

		uvcuacSettingsTab.setDisable(true);
		videoSourceConfigTab.setDisable(true);
		logDetails = new StringBuffer();
		SX3Manager.getInstance().setLogDetails(logDetails);
		logDetails.append(new Date() + " EZ-USB SX3 Configuration Utility Launched.<br>");
		logDetails.append("Create new EZ-USB SX3 Configuration : Click File &gt; New Configuration.<br>");
		logDetails.append("Load existing EZ-USB SX3 Configuration : Click File &gt;Load Configuration.<br>");
		logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
		logDetails1.getEngine().loadContent(logDetails.toString());
		SX3Manager.getInstance().setLogView(logDetails1);
		logDetails1.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
			@Override
			public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue,
					Worker.State newValue) {
				if (newValue == Worker.State.SUCCEEDED)
					logDetails1.getEngine().executeScript("window.scrollTo(0, document.body.scrollHeight);");
			}
		});

		helpContent.getEngine().getLoadWorker().stateProperty()
				.addListener(new RegisterHyperlinkHandler(helpContent.getEngine(), application.getHostServices()));
		SX3Manager.getInstance().setHelpView(helpContent);

		/** ------ Show tooltip on new configuration icon --------------- **/
		Tooltip newConfigToolTip = new Tooltip(DeviceSettingsConstant.NEW_COFIGURATION_TOOLTIP);
		newConfig.setTooltip(newConfigToolTip);
		newConfig.getTooltip().setOnShowing(s -> {
			Bounds bounds = newConfig.localToScreen(newConfig.getBoundsInLocal());
			newConfig.getTooltip().setX(bounds.getMaxX());
		});

		/** ------ Show tooltip on open configuration icon --------------- **/
		Tooltip openConfigToolTip = new Tooltip(DeviceSettingsConstant.OPEN_COFIGURATION_TOOLTIP);
		openConfig.setTooltip(openConfigToolTip);
		openConfig.getTooltip().setOnShowing(s -> {
			Bounds bounds = openConfig.localToScreen(openConfig.getBoundsInLocal());
			openConfig.getTooltip().setX(bounds.getMaxX());
		});
		
		Tooltip exportTooltip = new Tooltip(DeviceSettingsConstant.EXPORT_COFIGURATION_TOOLTIP);
		exportConfiguration.setTooltip(exportTooltip);
		exportConfiguration.getTooltip().setOnShowing(s -> {
			Bounds bounds = exportConfiguration.localToScreen(exportConfiguration.getBoundsInLocal());
			exportConfiguration.getTooltip().setX(bounds.getMaxX());
		});

		/** ------ Show tooltip on save configuration icon --------------- **/
		Tooltip saveConfigToolTip = new Tooltip(DeviceSettingsConstant.SAVE_COFIGURATION_TOOLTIP);
		saveConfigFromToolBar.setTooltip(saveConfigToolTip);
		saveConfigFromToolBar.getTooltip().setOnShowing(s -> {
			Bounds bounds = saveConfigFromToolBar.localToScreen(saveConfigFromToolBar.getBoundsInLocal());
			saveConfigFromToolBar.getTooltip().setX(bounds.getMaxX());
		});

		/** ------ Show tooltip on undo icon --------------- **/
		Tooltip undoToolTip = new Tooltip(DeviceSettingsConstant.UNDO);
		undo.setTooltip(undoToolTip);
		undo.getTooltip().setOnShowing(s -> {
			Bounds bounds = undo.localToScreen(undo.getBoundsInLocal());
			undo.getTooltip().setX(bounds.getMaxX());
		});

		/** ------ Show tooltip on redo icon --------------- **/
		Tooltip redoToolTip = new Tooltip(DeviceSettingsConstant.REDO);
		redo.setTooltip(redoToolTip);
		redo.getTooltip().setOnShowing(s -> {
			Bounds bounds = redo.localToScreen(redo.getBoundsInLocal());
			redo.getTooltip().setX(bounds.getMaxX());
		});

		/** ------ Show tooltip on program utility icon --------------- **/
		Tooltip programConfigToolTip = new Tooltip(DeviceSettingsConstant.PROGRAM_COFIGURATION_TOOLTIP);
		programConfigFromToolBar.setTooltip(programConfigToolTip);
		programConfigFromToolBar.getTooltip().setOnShowing(s -> {
			Bounds bounds = programConfigFromToolBar.localToScreen(programConfigFromToolBar.getBoundsInLocal());
			programConfigFromToolBar.getTooltip().setX(bounds.getMaxX());
		});

		/** ------ Show tooltip on save log button --------------- **/
		Tooltip saveLogToolTip = new Tooltip(DeviceSettingsConstant.SAVE_LOG);
		saveLog.setTooltip(saveLogToolTip);
		saveLog.getTooltip().setOnShowing(s -> {
			Bounds bounds = saveLog.localToScreen(saveLog.getBoundsInLocal());
			saveLog.getTooltip().setX(bounds.getMaxX());
		});

		ImageView saveImage = new ImageView(getClass().getResource("/resources/saveRow.png").toString());
		saveImage.setFitHeight(15);
		saveImage.setFitWidth(15);
		saveLog.setGraphic(saveImage);

		/** ------ Show tooltip on cancel log button --------------- **/
		Tooltip clearLogToolTip = new Tooltip(DeviceSettingsConstant.CLEAR_LOG);
		clearLog.setTooltip(clearLogToolTip);
		clearLog.getTooltip().setOnShowing(s -> {
			Bounds bounds = clearLog.localToScreen(clearLog.getBoundsInLocal());
			clearLog.getTooltip().setX(bounds.getMaxX());
		});
		ImageView clearImage = new ImageView(getClass().getResource("/resources/deleteRow.png").toString());
		clearImage.setFitHeight(15);
		clearImage.setFitWidth(15);
		clearLog.setGraphic(clearImage);

		/** --------- Log device settings tab text field ---------- **/
		logDeviceSettingTabField();

		/** ----- Load welcome page html file ------- **/
		loadWelcomeContent();

		/** ------ Select Endpoint one ------- **/
		selectEndpointOne();

		/** ------ Select Endpoint Two ------- **/
		selectEndpointTwo();

		/** ------ Load Help content ------- **/
		loadHelpContent();

		/**
		 * ------------- Load Tooltip and error properties file --------------
		 **/
		tooltipAndErrorProperties = SX3ConfigurationTooltipController.getConfigProperties();

	}

	/**
	 * Log device settings tab text field
	 */
	private void logDeviceSettingTabField() {

		DeviceSettingsController.logVendorID(vendorId);
		DeviceSettingsController.logProductID(productId);
		DeviceSettingsController.logManufacture(manufacture);
		DeviceSettingsController.logProductString(productString);
		DeviceSettingsController.logSerialNumber(serialNumber);
		DeviceSettingsController.logFifoClockFrequency(fifoClockFrequency);
		DeviceSettingsController.logFPGAI2CSlaveAddress(i2cSlaveAddress);
		DeviceSettingsController.logDeviceSettingFirmware(deviceSttingFirmWare);
		DeviceSettingsController.logDeviceSettingI2CFrequency(deviceSttingI2CFrequency);
	}

	/**
	 * Vendor ID validation
	 **/
	@FXML
	public void vendorIdValidation() {
		validateVendorIdField(false);
	}

	public void validateVendorIdField(boolean performLoadAction) {
		/** Vendor ID **/
		if (vendorId.getText() != null && !vendorId.getText().isEmpty()) {
			checkFieldEditOrNot = true;
			DeviceSettingsValidations.setupValidationForTextField(deviceSettingTabErrorList, deviceSettingsTab,
					vendorId, tooltipAndErrorProperties.getProperty("INVALID_HEX_ERROR_MESSAGE"), 65535, "VendorID",
					performLoadAction);
		}
	}

	/**
	 * Product ID validation
	 */
	@FXML
	public void productIdValidation() {
		validateProductIdField(false);
	}

	public void validateProductIdField(boolean performLoadAction) {
		if (productId.getText() != null && !productId.getText().isEmpty()) {
			checkFieldEditOrNot = true;
		}
		DeviceSettingsValidations.setupValidationForTextField(deviceSettingTabErrorList, deviceSettingsTab, productId,
				tooltipAndErrorProperties.getProperty("INVALID_HEX_ERROR_MESSAGE"), 65535, "ProductID",
				performLoadAction);
	}

	/**
	 * Manufecture validation
	 */
	@FXML
	public void manufactureValidation() {
		validateManufactureField(false);
	}

	public void validateManufactureField(boolean performLoadAction) {
		checkFieldEditOrNot = true;
		/** Manufacture **/
		DeviceSettingsValidations.setupValidationForAlphNumericTextField(deviceSettingTabErrorList, deviceSettingsTab,
				manufacture, tooltipAndErrorProperties.getProperty("INVALID_ALPHANUMERIC_ERROR_MESSAGE"), "Manufacture",
				performLoadAction, 16);
	}

	/**
	 * Product String validation
	 */
	@FXML
	public void productStringValidation() {
		validateProductStringField(false);
	}

	public void validateProductStringField(boolean performLoadAction) {
		checkFieldEditOrNot = true;
		/** Product String **/
		DeviceSettingsValidations.setupValidationForAlphNumericTextField(deviceSettingTabErrorList, deviceSettingsTab,
				productString, tooltipAndErrorProperties.getProperty("INVALID_ALPHANUMERIC_ERROR_MESSAGE"),
				"ProductString", performLoadAction, 16);
	}

	/**
	 * Serial Number validation
	 */
	@FXML
	public void serialNumberValidation() {
		validateSerialNumberField(false);
	}

	public void validateSerialNumberField(boolean performLoadAction) {
		checkFieldEditOrNot = true;
		/** Serial Number **/
		DeviceSettingsValidations.validateSerialNumberTextField(numericValidator, deviceSettingTabErrorList,
				deviceSettingsTab, serialNumber,
				tooltipAndErrorProperties.getProperty("INVALID_ALPHANUMERIC_ERROR_MESSAGE"), "SerialNumber", 16,
				performLoadAction);
	}

	/**
	 * FIFO Clock Frequency validation
	 */
	@FXML
	public void fifoClockFrequencyValidation() {
		validateFifoClockFrequencyField(false);
	}

	public void validateFifoClockFrequencyField(boolean performLoadAction) {
		checkFieldEditOrNot = true;
		/** FIFO Clock Frequency **/
		DeviceSettingsValidations.validateFifoClockFrequencyTextField(fifoClockFrequency,
				tooltipAndErrorProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), 100);
	}

	/**
	 * FIFO Master Slave Address validation
	 */
	@FXML
	public void fifoMasterSlaveAddressValidation() {
		validateFifoMasterSlaveAddress(false);
	}

	public void validateFifoMasterSlaveAddress(boolean b) {
		checkFieldEditOrNot = true;
		/** I2C Slave Address **/
		DeviceSettingsValidations.setupValidationForTextField(fifoMasterTabErrorList, fifoMasterConfigTab,
				i2cSlaveAddress, tooltipAndErrorProperties.getProperty("INVALID_HEX_ERROR_MESSAGE"), 255,
				"I2CSlaveAddress", performLoadAction);
	}

	/**
	 * Set Tooltip of device settings tab all fields removed noOfEndpoint,
	 */
	private void setAllToolTip() {
		DeviceSettingsController.setAllToolTip(tooltipAndErrorProperties, deviceName.getText(), vendorId, productId,
				manufacture, productString, autoGenerateSerialNumber, serialNumber, serialNumberIncrement,
				enableRemoteWakeup, powerConfiguration, Endpoint_1, fifoBusWidth, fifoClockFrequency, enableDebugLevel,
				debugValue, gpio1, gpio2, gpio3, gpio4, gpio5, gpio6, gpio7, interFaceType, uvcVersion,
				uvcHeaderAddition, enableFPGA, fpgaFamily, browseBitFile, i2cSlaveAddress, deviceSttingFirmWare,
				deviceSttingI2CFrequency);
	}

	/** Enable Disable Remote Wakeup **/
	@FXML
	public void logRemoteWakeup() {
		checkFieldEditOrNot = true;
		if (enableRemoteWakeup.isSelected()) {
			SX3Manager.getInstance().addLog("Enable Remote Wakeup : Enable.<br>");
		} else {
			SX3Manager.getInstance().addLog("Enable Remote Wakeup : Disable.<br>");
		}
	}

	/** Enable Disable Debug Level **/
	@FXML
	public void enableDebugLevel() {
		checkFieldEditOrNot = true;
		if (enableDebugLevel.isSelected()) {
			debugValue.setDisable(false);
			debugValue.setValue("0");
			SX3Manager.getInstance().addLog("Debug Level : Enable.<br>");
		} else {
			debugValue.setDisable(true);
			debugValue.setValue("");
			SX3Manager.getInstance().addLog("Debug Level : Disable.<br>");
		}
	}

	/**
	 * Log Auto generate serial number disable serial number field disable increment
	 * serial number field
	 */
	@FXML
	public void logAutoGenerateSerialNo() {
		checkFieldEditOrNot = true;
		if (autoGenerateSerialNumber.isSelected()) {
			SX3Manager.getInstance().addLog("Auto-Generate Serial Number : Enable.<br>");
			serialNumber.setDisable(true);
			serialNumber.setText("");
			serialNumber.setStyle("");
			serialNumberIncrement.setDisable(true);
			serialNumberIncrement.setValue(String.valueOf(0));
		} else {
			SX3Manager.getInstance().addLog("Auto-Generate Serial Number : Disable.<br>");
			serialNumber.setDisable(false);
			serialNumberIncrement.setDisable(false);
		}
	}

	/** Select Device settings tab **/

	@FXML
	public void selectDeviceSettingsTab() {
		if (deviceSettingsTab.isSelected() && !welcomeScreen.isVisible()) {
			logHelpSplitpane.setMaxHeight(470.0);
		}
	}

	/** Select UVC/UAC tab **/
	@FXML
	public void selectUVCUACTab() {
		if (uvcuacSettingsTab.isSelected()) {
			ObservableList<Tab> tabs = uvcuacTabpane.getTabs();
			checkFieldEditOrNot = true;
			logHelpSplitpane.setMaxHeight(600.0);
			Tab uvcOneTab = tabs.get(0);
			if (Endpoint_1.getValue().equals("UVC") && !videoSourceType.isEmpty()
					&& videoSourceType.get("Endpoint 1").equals("HDMI Source")) {
				/** Disable Endpoint 1 Camera and PU control tab **/
				TabPane uvcOneTabpane = (TabPane) uvcOneTab.getContent();
				uvcOneTabpane.getTabs().get(2).setDisable(true);
				uvcOneTabpane.getTabs().get(3).setDisable(true);
				clearCameraAndPUControlObject(0);
			} else if (Endpoint_1.getValue().equals("UVC") && !videoSourceType.isEmpty()
					&& !videoSourceType.get("Endpoint 1").equals("HDMI Source")) {
				/** Enable Endpoint 1 Camera and PU control tab **/
				TabPane uvcOneTabpane = (TabPane) uvcOneTab.getContent();
				uvcOneTabpane.getTabs().get(2).setDisable(false);
				uvcOneTabpane.getTabs().get(3).setDisable(false);
			}
			endpointOneChanges = true;
			endpointValues.put("Endpoint1", Endpoint_1.getValue());
			if (Endpoint_2.getValue().equals("UAC")) {
				endpointValues.put("Endpoint2", Endpoint_2.getValue());
				endpointTwoChanges = true;
			}
			TabPane tabpane = (TabPane) uvcuacTabpane.getTabs().get(0).getContent();
			ObservableList<Tab> tabs1 = tabpane.getTabs();
			AnchorPane content1 = (AnchorPane) tabs1.get(0).getContent();
			AnchorPane node1 = (AnchorPane) content1.getChildren().get(0);
			GridPane node2 = (GridPane) node1.getChildren().get(0);
			if (Endpoint_1.getValue().equals("UVC") && Endpoint_2.getValue().equals("UAC")) {
				TabPane tabpane1 = (TabPane) uvcuacTabpane.getTabs().get(1).getContent();
				ObservableList<Tab> tabs2 = tabpane1.getTabs();
				AnchorPane content2 = (AnchorPane) tabs2.get(0).getContent();
				AnchorPane node3 = (AnchorPane) content2.getChildren().get(0);
				GridPane node4 = (GridPane) node3.getChildren().get(0);
				TextField bufferSpaceTextField = new TextField();
				bufferSpaceTextField.setDisable(true);
				bufferSpaceTextField.setAlignment(Pos.CENTER_RIGHT);
				bufferSpaceTextField.setMaxWidth(60);
				int total = (((sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getBUFFER_SIZE()
						* sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getBUFFER_COUNT())
						+ (sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getBUFFER_SIZE()
								* sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getBUFFER_COUNT()))
						* 2);
				sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().setUSED_BUFFER_SPACE(total);
				sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().setUSED_BUFFER_SPACE(total);
				bufferSpaceTextField.setText(String.valueOf(total));
				setEndpointBufferSpace(node2, bufferSpaceTextField, "UVC");
				TextField bufferSpaceTextField1 = new TextField();
				bufferSpaceTextField1.setDisable(true);
				bufferSpaceTextField1.setAlignment(Pos.CENTER_RIGHT);
				bufferSpaceTextField1.setMaxWidth(60);
				setEndpointBufferSpace(node4, bufferSpaceTextField1, "UAC");
			} else if (Endpoint_1.getValue().equals("UAC") && Endpoint_2.getValue().equals("Not Enabled")) {
				TextField bufferSpaceTextField = new TextField();
				bufferSpaceTextField.setDisable(true);
				bufferSpaceTextField.setAlignment(Pos.CENTER_RIGHT);
				bufferSpaceTextField.setMaxWidth(60);
				int total = (((sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getBUFFER_SIZE()
						* sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getBUFFER_COUNT())) * 2);
				sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().setUSED_BUFFER_SPACE(total);
				bufferSpaceTextField.setText(String.valueOf(total));
				setEndpointBufferSpace(node2, bufferSpaceTextField, "UAC");
			} else if (Endpoint_1.getValue().equals("UVC") && Endpoint_2.getValue().equals("Not Enabled")) {
				TextField bufferSpaceTextField = new TextField();
				bufferSpaceTextField.setDisable(true);
				bufferSpaceTextField.setAlignment(Pos.CENTER_RIGHT);
				bufferSpaceTextField.setMaxWidth(60);
				int total = (((sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getBUFFER_SIZE()
						* sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getBUFFER_COUNT())) * 2);
				sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().setUSED_BUFFER_SPACE(total);
				bufferSpaceTextField.setText(String.valueOf(total));
				setEndpointBufferSpace(node2, bufferSpaceTextField, "UVC");
			}
		}
	}

	private void clearCameraAndPUControlObject(int i) {
		CameraControl cameraControl = new CameraControl();
		List<LinkedHashMap<String, LinkedHashMap<String, Object>>> cameraControlList = loadCameraControl1();
		cameraControl.setCAMERA_CONTROLS(cameraControlList);
		SX3Manager.getInstance().getSx3Configuration().getUVC_SETTINGS().get(i).setCAMERA_CONTROL(cameraControl);
		ProcessingUnitControl processingUnitControl = new ProcessingUnitControl();
		List<LinkedHashMap<String, LinkedHashMap<String, Object>>> processingUnitControlList = loadProcessingUnitControl();
		processingUnitControl.setPROCESSING_UNIT_CONTROLS(processingUnitControlList);
		SX3Manager.getInstance().getSx3Configuration().getUVC_SETTINGS().get(i)
				.setPROCESSING_UNIT_CONTROL(processingUnitControl);
	}

	/** Select slave FIFO settings **/
	@SuppressWarnings("static-access")
	@FXML
	public void selectSlaveFifoSettingsTab() {
		checkFieldEditOrNot = true;
		logHelpSplitpane.setMaxHeight(600.0);
		if (slaveFifoSettingsTab.isSelected()) {
			endpointOneChanges = true;
			endpointValues.put("Endpoint1", Endpoint_1.getValue());
			if (!Endpoint_2.getValue().equals("Not Enabled")) {
				endpointValues.put("Endpoint2", Endpoint_2.getValue());
				endpointTwoChanges = true;
			}
			TextField bufferSpaceTextField = new TextField();
			bufferSpaceTextField.setDisable(true);
			bufferSpaceTextField.setAlignment(Pos.CENTER_RIGHT);
			bufferSpaceTextField.setMaxWidth(60);
			AnchorPane content1 = (AnchorPane) slaveFifoSettingsTabpane.getTabs().get(0).getContent();
			GridPane node2 = (GridPane) content1.getChildren().get(0);
			ObservableList<javafx.scene.Node> children2 = node2.getChildren();
			if (Endpoint_2.getValue().equals("Not Enabled")) {
				int total = (sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_COUNT()
						* sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_SIZE()) * 2;
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_SPACE(total);
				bufferSpaceTextField.setText(String.valueOf(total));
			} else {
				int total = ((sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_COUNT()
						* sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_SIZE())
						+ (sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_COUNT()
								* sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_SIZE()))
						* 2;
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_SPACE(total);
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).setBUFFER_SPACE(total);
				bufferSpaceTextField.setText(String.valueOf(total));
			}
			for (javafx.scene.Node node : children2) {
				if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 4) {
					node2.getChildren().remove(node);
					node2.add(bufferSpaceTextField, 1, 4);
					node2.setMargin(bufferSpaceTextField, new Insets(0, 5, 0, 0));
					break;
				}
			}
		}
	}

	/**
	 * Change endpoint value check any changes in old value related tab show warning
	 * message
	 **/

	public void selectEndpointOne() {
		ChangeListener<String> changeListener = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (Endpoint_1.getValue() != null) {
					if (endpointOneChanges && !endpointValues.get("Endpoint1").equals(Endpoint_1.getValue())) {
						Dialog<ButtonType> dialog = new Dialog<>();
						dialog.setTitle("Warning");
						dialog.setResizable(true);
						dialog.setContentText("Data entered in Endpoint 1 - " + oldValue
								+ " tab will be lost. Do you want continue?");
						ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
						dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
						ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
						dialog.getDialogPane().getButtonTypes().add(buttonCancel);
						Optional<ButtonType> showAndWait = dialog.showAndWait();
						if (showAndWait.get() == buttonTypeOk) {
							loadNewEndPoint1(newValue);
							endpointOneChanges = false;
							if (Endpoint_1.getValue().equals("OUT")) {
								Endpoint_2.setValue("Not Enabled");
								Endpoint_2.getItems().removeAll("OUT");
//								if (Endpoint_2.getValue() != null && Endpoint_2.getValue().equals("IN")) {
//									Endpoint_2.setValue("Not Enabled");
//									Endpoint_2.getItems().remove("IN");
//								} else {
//									Endpoint_2.getItems().remove("IN");
//								}
							} else if(Endpoint_1.getValue().equals("IN")) {
									Endpoint_2.setValue("Not Enabled");
									if(!Endpoint_2.getItems().contains("OUT")) {
										Endpoint_2.getItems().add("OUT");
									}
							}
//							else if (Endpoint_2.getValue() != null && Endpoint_1.getValue().equals("IN")
//									&& Endpoint_2.getValue().equals("Not Enabled")) {
//								if (!Endpoint_2.getItems().contains("IN")) {
//									Endpoint_2.getItems().addAll("IN");
//								}
//								Endpoint_2.setValue("Not Enabled");
//							}
							if (Endpoint_1.getValue().equals("UAC")) {
								if (Endpoint_2.getValue() != null && Endpoint_2.getValue().equals("UAC")) {
									Endpoint_2.setValue("Not Enabled");
									Endpoint_2.getItems().remove("UAC");
								} else {
									Endpoint_2.getItems().remove("UAC");
								}
							} else if (Endpoint_2.getValue() != null && Endpoint_1.getValue().equals("UVC")
									&& Endpoint_2.getValue().equals("Not Enabled")) {
								if (!Endpoint_2.getItems().contains("UAC")) {
									Endpoint_2.getItems().addAll("UAC");
								}
								Endpoint_2.setValue("Not Enabled");
							}
						}
						if (showAndWait.get() == buttonCancel) {
							dialog.close();
							endpointOneActionPerformed = true;
							Endpoint_1.setValue(oldValue);
						}
					} else {
						if (!endpointOneActionPerformed) {
							if (!performLoadAction && Endpoint_1.getValue() != null
									&& Endpoint_1.getValue().equals("OUT")) {
								Endpoint_2.setValue("Not Enabled");
								Endpoint_2.getItems().removeAll("OUT");
//								if (Endpoint_2.getValue() != null && Endpoint_2.getValue().equals("IN")) {
//									Endpoint_2.setValue("Not Enabled");
//									Endpoint_2.getItems().remove("IN");
//								} else {
//									Endpoint_2.getItems().remove("IN");
//								}
							} else if (!performLoadAction && Endpoint_1.getValue() != null
									&& Endpoint_1.getValue().equals("IN")) {
//								if (!Endpoint_2.getItems().contains("IN")) {
//									Endpoint_2.getItems().addAll("IN");
//								}
								Endpoint_2.setValue("Not Enabled");
								if(!Endpoint_2.getItems().contains("OUT")) {
									Endpoint_2.getItems().add("OUT");
								}
							}
							if (!performLoadAction && Endpoint_1.getValue() != null
									&& Endpoint_1.getValue().equals("UAC")) {
								if (Endpoint_2.getValue() != null && Endpoint_2.getValue().equals("UAC")) {
									Endpoint_2.setValue("Not Enabled");
									Endpoint_2.getItems().remove("UAC");
								} else {
									Endpoint_2.getItems().remove("UAC");
								}
							} else if (!performLoadAction && Endpoint_1.getValue() != null
									&& Endpoint_1.getValue().equals("UVC") && Endpoint_2.getValue() != null
									&& Endpoint_2.getValue().equals("Not Enabled")) {
								if (!Endpoint_2.getItems().contains("UAC")) {
									Endpoint_2.getItems().addAll("UAC");
								}
								Endpoint_2.setValue("Not Enabled");
							}
							loadNewEndPoint1(newValue);
						}
					}
				}
			}
		};
		Endpoint_1.getSelectionModel().selectedItemProperty().addListener(changeListener);
	}

	/**
	 * change old and create new endpoint one value related UI remove old value data
	 * from tab and list
	 * 
	 * @param oldValue
	 **/
	private void loadNewEndPoint1(String newValue) {
		if (loadEndpoints != null && !loadEndpoints.isEmpty() && !loadEndpoints.get(0).equals(newValue)) {
			performLoadAction = false;
			if (loadEndpoints.get(0).equals("UVC")) {
				sx3Configuration.getUVC_SETTINGS().remove(0);
				sx3Configuration.getVIDEO_SOURCE_CONFIG().remove(0);
				loadEndpoints.remove(0);
			} else if (loadEndpoints.get(0).equals("UAC")) {
				sx3Configuration.getUAC_SETTINGS().remove(0);
				loadEndpoints.remove(0);
				sx3Configuration.getVIDEO_SOURCE_CONFIG().remove(0);
			} else if (loadEndpoints.get(0).equals("IN")) {
				sx3Configuration.getSLAVE_FIFO_SETTINGS().remove(0);
				loadEndpoints.remove(0);
			} else if (loadEndpoints.get(0).equals("OUT")) {
				sx3Configuration.getSLAVE_FIFO_SETTINGS().remove(0);
				loadEndpoints.remove(0);
			}
		}
		checkFieldEditOrNot = true;
		SX3Manager.getInstance().addLog("Endpoint 1 : " + Endpoint_1.getValue() + ".<br>");
		if (deviceName.getText().equals("SX3 UVC (CYUSB3017)")) {
			uvcuacSettingsTab.setDisable(false);
			videoSourceConfigTab.setDisable(false);
			Tab tabOne = new Tab();
			tabOne.setClosable(false);
			tabOne.setText("Endpoint 1 -" + Endpoint_1.getValue());
			tabOne.setOnSelectionChanged(event -> {
				if (tabOne.isSelected() && uvcuacSettingsTab.isSelected()) {
					setEndpointOneBufferSpace(tabOne);
				}
			});
			if (!uvcuacTabpane.getTabs().isEmpty()) {
				uvcuacTabpane.getTabs().set(0, tabOne);
			} else {
				uvcuacTabpane.getTabs().add(tabOne);
			}
			if (!videoSourceConfigTabpane.getTabs().isEmpty()) {
				videoSourceConfigTabpane.getTabs().remove(0);
			}
			updateEndpointOneVideoSourceConfigUI();
			if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals(DeviceSettingsConstant.UVC)) {
				createUVCUI(tabOne, uvcSettingList, 0, "endpoint1");
				uvcVersion.setDisable(false);
				uvcVersion.setValue("1.5");
				uvcHeaderAddition.setDisable(false);
				uvcHeaderAddition.setValue("UVC HEADER BY FIFO MASTER");
				if (!performLoadAction && uacSettingList != null && uacSettingList.size() != 0) {
					uacSettingList.remove(0);
				}
			} else if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals(DeviceSettingsConstant.UAC)) {
				videoSourceType.put("Endpoint 1", "Image Sensor");
				createUACUI(tabOne, uacSettingList, 0, "endpoint1");
				uvcVersion.setValue(null);
				uvcVersion.setDisable(true);
				uvcHeaderAddition.setDisable(true);
				uvcHeaderAddition.setValue(null);
			}
		} else {
			Tab tabOne = new Tab();
			tabOne.setClosable(false);
			tabOne.setText("Endpoint 1 -" + Endpoint_1.getValue());
			tabOne.setOnSelectionChanged(event -> {
				if (tabOne.isSelected() && slaveFifoSettingsTab.isSelected()) {
					setEndpointOneBufferSpace(tabOne);
				}
			});
			slaveFifoSettingsTab.setDisable(false);
			if (performLoadAction == false) {
				SlaveFIFOSettings slaveFifo = loadSlaveFifoSetting();
				if (!slaveFifoSettingsTabpane.getTabs().isEmpty()) {
					slaveFifoSettingsTabpane.getTabs().set(0, tabOne);
				} else {
					slaveFifoSettingsTabpane.getTabs().add(tabOne);
				}
				if (slaveFifoSettingList.size() != 0) {
					slaveFifoSettingList.set(0, slaveFifo);
				} else {
					slaveFifoSettingList.add(slaveFifo);
				}
				sx3Configuration.setSLAVE_FIFO_SETTINGS(slaveFifoSettingList);
				createSlaveFIFOSettingsUI(slaveFifoSettingList.get(0), tabOne);
			} else {
				slaveFifoSettingsTabpane.getTabs().add(tabOne);
				slaveFifoSettingList = sx3Configuration.getSLAVE_FIFO_SETTINGS();
				createSlaveFIFOSettingsUI(slaveFifoSettingList.get(0), tabOne);
			}
		}

	}

	private void updateEndpointOneVideoSourceConfigUI() {
		Tab videoConfigTab = new Tab();
		videoConfigTab.setText("Endpoint 1 -" + Endpoint_1.getValue());
		if (!videoSourceConfigTabpane.getTabs().isEmpty()) {
			Tab tab = videoSourceConfigTabpane.getTabs().get(0);
			videoSourceConfigTabpane.getTabs().set(0, videoConfigTab);
			videoSourceConfigTabpane.getTabs().add(tab);
		} else {
			videoSourceConfigTabpane.getTabs().add(videoConfigTab);
		}
		createVideoSourceConfig(videoConfigTab, videoSourceConfigList, 0, 1);
	}

	/**
	 * @param tabOne Endpoint 1 Endpoint settings tab calculate buffer size and
	 *               buffer count value and show in total used buffer space value on
	 *               tab change
	 */
	@SuppressWarnings("static-access")
	private void setEndpointOneBufferSpace(Tab tabOne) {
		TextField bufferSpaceTextField = new TextField();
		bufferSpaceTextField.setDisable(true);
		bufferSpaceTextField.setAlignment(Pos.CENTER_RIGHT);
		bufferSpaceTextField.setMaxWidth(60);
		if (deviceName.getText().equals("SX3 UVC (CYUSB3017)")) {
			TabPane tabpane = (TabPane) tabOne.getContent();
			ObservableList<Tab> tabs = tabpane.getTabs();
			AnchorPane content1 = (AnchorPane) tabs.get(0).getContent();
			AnchorPane node1 = (AnchorPane) content1.getChildren().get(0);
			GridPane node2 = (GridPane) node1.getChildren().get(0);
			if (tabs.get(0).getText().equals("Endpoint Settings") && tabOne.getText().contains("UVC")) {
				if (Endpoint_2.getValue() != null && Endpoint_2.getValue().equals("UAC")) {
					int total = (((sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getBUFFER_SIZE()
							* sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getBUFFER_COUNT())
							+ (sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getBUFFER_SIZE()
									* sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings()
											.getBUFFER_COUNT()))
							* 2);
					sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().setUSED_BUFFER_SPACE(total);
					sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().setUSED_BUFFER_SPACE(total);
					bufferSpaceTextField.setText(String.valueOf(total));
				} else if (Endpoint_2.getValue().equals("Not Enabled")) {
					int total = (((sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getBUFFER_SIZE()
							* sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getBUFFER_COUNT())) * 2);
					sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().setUSED_BUFFER_SPACE(total);
				}
				setEndpointBufferSpace(node2, bufferSpaceTextField, "UVC");
			} else if (tabs.get(0).getText().equals("Endpoint Settings") && tabOne.getText().contains("UAC")) {
				int total = (((sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getBUFFER_SIZE()
						* sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getBUFFER_COUNT())) * 2);
				bufferSpaceTextField.setText(String.valueOf(total));
				setEndpointBufferSpace(node2, bufferSpaceTextField, "UAC");
			}
		} else {
			AnchorPane content1 = (AnchorPane) tabOne.getContent();
			GridPane node2 = (GridPane) content1.getChildren().get(0);
			if (sx3Configuration.getSLAVE_FIFO_SETTINGS().size() > 1) {
				int total = ((sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_COUNT()
						* sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_SIZE())
						+ (sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_COUNT()
								* sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_SIZE()))
						* 2;
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_SPACE(total);
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).setBUFFER_SPACE(total);
				bufferSpaceTextField.setText(String.valueOf(total));
				ObservableList<javafx.scene.Node> children2 = node2.getChildren();
				for (javafx.scene.Node node : children2) {
					if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 4) {
						node2.getChildren().remove(node);
						node2.add(bufferSpaceTextField, 1, 4);
						node2.setMargin(bufferSpaceTextField, new Insets(0, 5, 0, 0));
						break;
					}
				}
			} else {
				int total = (sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_COUNT()
						* sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_SIZE()) * 2;
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_SPACE(total);
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).setBUFFER_SPACE(total);
				bufferSpaceTextField.setText(String.valueOf(total));
				ObservableList<javafx.scene.Node> children2 = node2.getChildren();
				for (javafx.scene.Node node : children2) {
					if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 4) {
						node2.getChildren().remove(node);
						node2.add(bufferSpaceTextField, 1, 4);
						node2.setMargin(bufferSpaceTextField, new Insets(0, 5, 0, 0));
						break;
					}
				}
			}
		}

	}

	/**
	 * Change endpoint value check any changes in old value related tab show warning
	 * message
	 **/
	public void selectEndpointTwo() {
		ChangeListener<String> changeListener = new ChangeListener<String>() {
			@SuppressWarnings("unlikely-arg-type")
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (Endpoint_2.getValue() != null) {
					if (endpointTwoChanges && !endpointValues.get("Endpoint2").equals(Endpoint_2.getValue())) {
						Dialog<ButtonType> dialog = new Dialog<>();
						dialog.setTitle("Warning");
						dialog.setResizable(true);
						dialog.setContentText("Data entered in Endpoint 2 - " + oldValue
								+ " tab will be lost. Do you want continue?");
						ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
						dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
						ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
						if (Endpoint_1.getValue().equals("UVC")) {
							dialog.getDialogPane().getButtonTypes().add(buttonCancel);
						} else if ((deviceName.equals("SX3 Data-16 bit(CYUSB3015)")
								|| deviceName.equals("SX3-Data-32 bit(CYUSB3016)"))
								&& (!Endpoint_1.getValue().equals("OUT") && !Endpoint_2.getValue().equals("IN"))) {
							dialog.getDialogPane().getButtonTypes().add(buttonCancel);
						}
						Optional<ButtonType> showAndWait = dialog.showAndWait();
						if (showAndWait.get() == buttonTypeOk) {
							loadNewEndPoint2(newValue);
							endpointTwoChanges = false;
//							if (sx3Configuration.getUAC_SETTINGS() != null && sx3Configuration.getUAC_SETTINGS().size() > 1) {
//								sx3Configuration.getUAC_SETTINGS().remove(1);
//							}else if(sx3Configuration.getUAC_SETTINGS() != null && sx3Configuration.getUAC_SETTINGS().size() == 1){
//								sx3Configuration.getUAC_SETTINGS().remove(0);
//							}
						}
						if (showAndWait.get() == buttonCancel) {
							endpointTwoActionPerformed = true;
							Endpoint_2.setValue(oldValue);
						}
					} else {
						if (!endpointTwoActionPerformed) {
							loadNewEndPoint2(newValue);
						}
					}
				}
			}
		};
		Endpoint_2.getSelectionModel().selectedItemProperty().addListener(changeListener);
	}

	/**
	 * change old and create new endpoint two value related UI remove old value data
	 * from tab and list
	 * 
	 * @param newValue
	 **/
	private void loadNewEndPoint2(String newValue) {
		if (loadEndpoints != null && loadEndpoints.size() > 1 && !loadEndpoints.get(1).equals(newValue)) {
			performLoadAction = false;
			if (loadEndpoints.get(1).equals("UAC")) {
				loadEndpoints.remove(1);
				if (sx3Configuration.getUAC_SETTINGS() != null && sx3Configuration.getUAC_SETTINGS().size() > 1) {
					sx3Configuration.getUAC_SETTINGS().remove(1);
				} else {
					sx3Configuration.getUAC_SETTINGS().remove(0);
				}
				sx3Configuration.getVIDEO_SOURCE_CONFIG().remove(1);
			} else if (loadEndpoints.get(1).equals("IN")) {
				loadEndpoints.remove(1);
				if (sx3Configuration.getSLAVE_FIFO_SETTINGS() != null
						&& sx3Configuration.getSLAVE_FIFO_SETTINGS().size() > 1) {
					sx3Configuration.getSLAVE_FIFO_SETTINGS().remove(1);
				} else if (sx3Configuration.getSLAVE_FIFO_SETTINGS() != null
						&& sx3Configuration.getSLAVE_FIFO_SETTINGS().size() == 1) {
					sx3Configuration.getSLAVE_FIFO_SETTINGS().remove(0);
				}
			} else if (loadEndpoints.get(1).equals("OUT")) {
				loadEndpoints.remove(1);
				if (sx3Configuration.getSLAVE_FIFO_SETTINGS() != null
						&& sx3Configuration.getSLAVE_FIFO_SETTINGS().size() > 1) {
					sx3Configuration.getSLAVE_FIFO_SETTINGS().remove(1);
				} else if (sx3Configuration.getSLAVE_FIFO_SETTINGS() != null
						&& sx3Configuration.getSLAVE_FIFO_SETTINGS().size() == 1) {
					sx3Configuration.getSLAVE_FIFO_SETTINGS().remove(0);
				}
			}
		} else if (loadEndpoints != null && loadEndpoints.size() == 1 && !newValue.equals("Not Enabled")) {
			performLoadAction = false;
		}
		checkFieldEditOrNot = true;
		SX3Manager.getInstance().addLog("Endpoint 2 : " + Endpoint_2.getValue() + ".<br>");
		logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
		if (deviceName.getText().equals("SX3 UVC (CYUSB3017)")) {
			if (Endpoint_2.getValue() != null && Endpoint_2.getValue().equals("UAC")) {
				uvcuacSettingsTab.setDisable(false);
				videoSourceConfigTab.setDisable(false);
				Tab tabTwo = new Tab();
				tabTwo.setClosable(false);
				tabTwo.setText("Endpoint 2 -" + Endpoint_2.getValue());
				tabTwo.setOnSelectionChanged(event -> {
					if (tabTwo.isSelected() && uvcuacSettingsTab.isSelected()) {
						setEndpointTwoBufferSpace(tabTwo);
					}
				});
				Tab videoConfigTab = new Tab();
				if (uvcuacTabpane.getTabs().size() > 1) {
					uvcuacTabpane.getTabs().set(1, tabTwo);
				} else {
					uvcuacTabpane.getTabs().add(tabTwo);
				}
				if (videoSourceConfigTabpane.getTabs().size() > 1) {
					videoSourceConfigTabpane.getTabs().remove(1);
				}
				updateEndpointTwoVideoSourceConfigUI(videoConfigTab);
				videoSourceType.put("Endpoint 2", "Image Sensor");
				createUACUI(tabTwo, uacSettingList, 1, "endpoint2");
			} else {
				if (uvcuacTabpane.getTabs().size() > 1) {
					uvcuacTabpane.getTabs().remove(1);
					videoSourceConfigTabpane.getTabs().remove(1);
				}
				if (sx3Configuration.getVIDEO_SOURCE_CONFIG().size() > 1) {
					sx3Configuration.getVIDEO_SOURCE_CONFIG().remove(1);
				}
				if (sx3Configuration.getUAC_SETTINGS() != null && sx3Configuration.getUAC_SETTINGS().size() > 1) {
					sx3Configuration.getUAC_SETTINGS().remove(1);
				} else if (Endpoint_1.getValue().equals("UVC") && sx3Configuration.getUAC_SETTINGS() != null
						&& sx3Configuration.getUAC_SETTINGS().size() == 1) {
					sx3Configuration.getUAC_SETTINGS().remove(0);
				}
			}
		} else {
			if (!Endpoint_2.getValue().equals("Not Enabled")) {
				loadEndpoints.add(Endpoint_2.getValue());
				slaveFifoSettingsTab.setDisable(false);
				Tab tabTwo = new Tab();
				tabTwo.setClosable(false);
				tabTwo.setText("Endpoint 2 -" + Endpoint_2.getValue());
				tabTwo.setOnSelectionChanged(event -> {
					if (tabTwo.isSelected() && slaveFifoSettingsTab.isSelected()) {
						setEndpointTwoBufferSpace(tabTwo);
					}
				});
				if (performLoadAction == false) {
					SlaveFIFOSettings slaveFifo = loadSlaveFifoSetting();
					if (slaveFifoSettingsTabpane.getTabs().size() > 1) {
						slaveFifoSettingsTabpane.getTabs().set(1, tabTwo);
					} else {
						slaveFifoSettingsTabpane.getTabs().add(tabTwo);
					}
					if (slaveFifoSettingList.size() > 1) {
						slaveFifoSettingList.set(1, slaveFifo);
					} else {
						slaveFifoSettingList.add(slaveFifo);
					}
					sx3Configuration.setSLAVE_FIFO_SETTINGS(slaveFifoSettingList);
					createSlaveFIFOSettingsUI(slaveFifoSettingList.get(1), tabTwo);
				} else {
					slaveFifoSettingsTabpane.getTabs().add(tabTwo);
					slaveFifoSettingList = sx3Configuration.getSLAVE_FIFO_SETTINGS();
					createSlaveFIFOSettingsUI(slaveFifoSettingList.get(1), tabTwo);
				}
			} else {
				if (slaveFifoSettingsTabpane.getTabs().size() > 1) {
					slaveFifoSettingsTabpane.getTabs().remove(1);
					slaveFifoSettingList.remove(1);
				}
			}
		}
	}

	private void updateEndpointTwoVideoSourceConfigUI(Tab videoConfigTab) {
		videoConfigTab.setText("Endpoint 2 -" + Endpoint_2.getValue());
		videoSourceConfigTabpane.getTabs().add(videoConfigTab);
		createVideoSourceConfig(videoConfigTab, videoSourceConfigList, 1, 2);
	}

	/**
	 * @param tabOne Endpoint 2 Endpoint settings tab calculate buffer size and
	 *               buffer count value and show in total used buffer space value on
	 *               tab change
	 */
	@SuppressWarnings("static-access")
	private void setEndpointTwoBufferSpace(Tab tabTwo) {
		TextField bufferSpaceTextField = new TextField();
		bufferSpaceTextField.setDisable(true);
		bufferSpaceTextField.setAlignment(Pos.CENTER_RIGHT);
		bufferSpaceTextField.setMaxWidth(60);
		if (deviceName.getText().equals("SX3 UVC (CYUSB3017)")) {
			TabPane tabpane = (TabPane) tabTwo.getContent();
			ObservableList<Tab> tabs = tabpane.getTabs();
			AnchorPane content1 = (AnchorPane) tabs.get(0).getContent();
			AnchorPane node1 = (AnchorPane) content1.getChildren().get(0);
			GridPane node2 = (GridPane) node1.getChildren().get(0);
			ObservableList<javafx.scene.Node> children2 = node2.getChildren();
			if (tabs.get(0).getText().equals("Endpoint Settings") && tabTwo.getText().contains("UAC")) {
				int total = (((sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getBUFFER_SIZE()
						* sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getBUFFER_COUNT())
						+ (sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getBUFFER_SIZE()
								* sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getBUFFER_COUNT()))
						* 2);
				bufferSpaceTextField.setText(String.valueOf(total));
				for (javafx.scene.Node node : children2) {
					if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 4) {
						node2.getChildren().remove(node);
						node2.add(bufferSpaceTextField, 1, 4);
						node2.setMargin(bufferSpaceTextField, new Insets(0, 5, 0, 0));
						break;
					}
				}
			}
		} else {
			AnchorPane content1 = (AnchorPane) tabTwo.getContent();
			GridPane node2 = (GridPane) content1.getChildren().get(0);
			if (sx3Configuration.getSLAVE_FIFO_SETTINGS().size() > 1) {
				int total = ((sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_COUNT()
						* sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_SIZE())
						+ (sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_COUNT()
								* sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_SIZE()))
						* 2;
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_SPACE(total);
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).setBUFFER_SPACE(total);
				bufferSpaceTextField.setText(String.valueOf(total));
				ObservableList<javafx.scene.Node> children2 = node2.getChildren();
				for (javafx.scene.Node node : children2) {
					if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 4) {
						node2.getChildren().remove(node);
						node2.add(bufferSpaceTextField, 1, 4);
						node2.setMargin(bufferSpaceTextField, new Insets(0, 5, 0, 0));
						break;
					}
				}
			}

		}

	}

	/**
	 * @param Tab
	 * @param     List<UVCSettings> Endpoint value endpoint name create all UVC tab
	 *            related UI
	 **/
	private void createUVCUI(Tab tab, List<UVCSettings> uvcSettingList, int i, String endpointName) {
		TabPane tabPane = new TabPane();
		tab.setContent(tabPane);
		UVCSettings uvcSettings = new UVCSettings();
		FormatAndResolution formatAndreolution = new FormatAndResolution();
		List<FormatAndResolutions> formatResolutionJsonList = new ArrayList<>();
		if (performLoadAction == false) {
			EndpointSettings endpointSettings = loadEndpoint("UVC");
			uvcSettings.setENDPOINT_SETTINGS(endpointSettings);
			formatResolutionJsonList.add(loadFormatResolutions());
			formatAndreolution.setFORMAT_RESOLUTIONS(formatResolutionJsonList);
			uvcSettings.setFORMAT_RESOLUTION(formatAndreolution);
			ColorMatching colorMatching = loadColorMatching();
			uvcSettings.setCOLOR_MATCHING(colorMatching);
			CameraControl cameraControl = new CameraControl();
			List<LinkedHashMap<String, LinkedHashMap<String, Object>>> cameraControlList = loadCameraControl1();
			cameraControl.setCAMERA_CONTROLS(cameraControlList);
			uvcSettings.setCAMERA_CONTROL(cameraControl);
			ProcessingUnitControl processingUnnitControl = new ProcessingUnitControl();
			List<LinkedHashMap<String, LinkedHashMap<String, Object>>> processingUnitControlJson = loadProcessingUnitControl();
			processingUnnitControl.setPROCESSING_UNIT_CONTROLS(processingUnitControlJson);
			uvcSettings.setPROCESSING_UNIT_CONTROL(processingUnnitControl);
			ExtensionUnitControl extensionUnitControl = loadExtensionUnitControl();
			uvcSettings.setEXTENSION_UNIT_CONTROL(extensionUnitControl);
			uvcSettings.setRESERVED("FF");
			if (endpointName.equals("endpoint1") && uvcSettingList != null && uvcSettingList.size() == 1) {
				UVCSettings uvcSettings2 = uvcSettingList.get(0);
				uvcSettingList.set(0, uvcSettings);
				uvcSettingList.add(uvcSettings2);
			} else {
				uvcSettingList.add(uvcSettings);
			}
			sx3Configuration.setUVC_SETTINGS(uvcSettingList);
		} else {
			if (tab.getText().contains("Endpoint 2") && sx3Configuration.getUVC_SETTINGS().size() == 1) {
				uvcSettings = sx3Configuration.getUVC_SETTINGS().get(i - 1);
			} else {
				uvcSettings = sx3Configuration.getUVC_SETTINGS().get(i);
			}
		}

		/** UVC Endpoint Settings **/
		Tab endpointTab = new Tab();
		endpointTab.setClosable(false);
		endpointTab.setText("Endpoint Settings");
		createEndpointSettingsUI(tab, uvcSettings.getENDPOINT_SETTINGS(), endpointTab, tab.getText(), "UVC");
		endpointTab.setOnSelectionChanged(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				logHelpSplitpane.setMaxHeight(600.0);
			}
		});

		/** UVC Format And Resolution **/
		Tab formationAndResolutionTab = new Tab();
		formationAndResolutionTab.setClosable(false);
		formationAndResolutionTab.setText("Format And Resolution");
		createFormatAndResolution(tab, formationAndResolutionTab, uvcSettings, uvcSettings.getCOLOR_MATCHING());
		formationAndResolutionTab.setOnSelectionChanged(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				logHelpSplitpane.setMaxHeight(300.0);
			}
		});
		formationAndResolutionTab.setOnSelectionChanged(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				ScrollPane scrollPane = (ScrollPane) formationAndResolutionTab.getContent();
				BorderPane borderPane = (BorderPane) scrollPane.getContent();
				AnchorPane anchorPane = (AnchorPane) borderPane.getTop();
				@SuppressWarnings("unchecked")
				TableView<FormatAndResolutionTableModel> tableView = (TableView<FormatAndResolutionTableModel>) anchorPane.getChildren().get(0);
				FormatAndResolutionTableModel formatAndResolutionTableModel = tableView.getItems().get(0);
				if(SX3Manager.getInstance().getSx3Configuration().getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getENDPOINT_TRANSFER_TYPE().equals("Isochronous")) {
					formatAndResolutionTableModel.getSupportedInHS().setDisable(true);
					formatAndResolutionTableModel.getSupportedInHS().setSelected(false);
					formatAndResolutionTableModel.getFrameRateInHS().setDisable(true);
					formatAndResolutionTableModel.getFrameRateInHS().setText("0");
				}else {
					formatAndResolutionTableModel.getSupportedInHS().setDisable(false);
				}
			}
		});

		/** UVC Camera Control **/
		Tab cameraControlTab = new Tab();
		cameraControlTab.setClosable(false);
		cameraControlTab.setText("Camera Control");
		createCameraControl(tab, cameraControlTab, uvcSettings.getCAMERA_CONTROL().getCAMERA_CONTROLS());
		cameraControlTab.setOnSelectionChanged(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				logHelpSplitpane.setMaxHeight(200.0);
			}
		});

		/** UVC Processing Unit Control **/
		Tab processingUnitControlTab = new Tab();
		processingUnitControlTab.setClosable(false);
		processingUnitControlTab.setText("Processing Unit Control");
		createProcessingUnitControl(tab, processingUnitControlTab,
				uvcSettings.getPROCESSING_UNIT_CONTROL().getPROCESSING_UNIT_CONTROLS());
		processingUnitControlTab.setOnSelectionChanged(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				logHelpSplitpane.setMaxHeight(200.0);
			}
		});

		/** UVC Processing Unit Control **/
		Tab extensionUnitControlTab = new Tab();
		extensionUnitControlTab.setClosable(false);
		extensionUnitControlTab.setText("Extension Unit Controls");
		createExtensionUnitControl(extensionUnitControlTab, uvcSettings.getEXTENSION_UNIT_CONTROL());
		processingUnitControlTab.setOnSelectionChanged(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				logHelpSplitpane.setMaxHeight(600.0);
			}
		});

		tabPane.getTabs().addAll(endpointTab, formationAndResolutionTab, cameraControlTab, processingUnitControlTab,
				extensionUnitControlTab);
	}

	/**
	 * @param subTab
	 * @param subSubTab
	 * @param uvcSettings
	 * @param colorMatching create format and resolution tab and functionality
	 *                      create color matching part
	 */
	@SuppressWarnings("static-access")
	private void createFormatAndResolution(Tab subTab, Tab subSubTab, UVCSettings uvcSettings,
			ColorMatching colorMatching) {
		TableView<FormatAndResolutionTableModel> formatResolutionTable = new TableView<>();
		formatResolutionTable.setId("formatResolutionTable");
		ObservableList<FormatAndResolutionTableModel> formateAndResolutionData = FXCollections.observableArrayList();
		List<FormatAndResolutions> formatAndResolutionList = uvcSettings.getFORMAT_RESOLUTION().getFORMAT_RESOLUTIONS();
		ScrollPane rootScrollPane = new ScrollPane();
		rootScrollPane.getStyleClass().add("scrollpane");
		AnchorPane topAnchor = new AnchorPane();
		topAnchor.setLeftAnchor(rootScrollPane, 0.0);
		topAnchor.setRightAnchor(rootScrollPane, 0.0);
		topAnchor.setTopAnchor(rootScrollPane, 0.0);
		topAnchor.setBottomAnchor(rootScrollPane, 0.0);
		formatResolutionTable.setMaxHeight(550);
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(topAnchor);
		formatResolutionTable.setPrefWidth(1100);
		rootScrollPane.setContent(borderPane);
		subSubTab.setContent(rootScrollPane);
		UVCUACSettingsController.createTable(formatResolutionTable);

		/** Create Color Matching Descriptor in Format And Resolution Tab **/
		GridPane gridPane1 = new GridPane();
		gridPane1 = UVCUACSettingsController.createColorMatchingDescriptor(gridPane1, colorMatching,
				tooltipAndErrorProperties);
		borderPane.setBottom(gridPane1);
		borderPane.setMargin(gridPane1, new Insets(5));

		/** Separator **/
		final Separator sepHor = new Separator();
		sepHor.setPadding(new Insets(0));
		sepHor.setLayoutY(410);
		sepHor.setPrefWidth(1060);

		/** Add Row Button **/
		Button addBtn = new Button();
		addBtn.setId("formatResolutionAddBtn");
		addBtn.setDisable(true);
		addBtn.setLayoutY(4);
		addBtn.setLayoutX(1110);
		addBtn.setTooltip(new Tooltip("Add Row"));
		addBtn.getTooltip().setOnShowing(s -> {
			Bounds bounds = addBtn.localToScreen(addBtn.getBoundsInLocal());
			addBtn.getTooltip().setX(bounds.getMaxX());
		});
		ImageView addImage = new ImageView(getClass().getResource("/resources/add.png").toString());
		addImage.setFitHeight(15);
		addImage.setFitWidth(15);
		addBtn.setGraphic(addImage);

		/** Edit Row Button **/
		Button editBtn = new Button();
		editBtn.setId("formatResolutionSaveBtn");
		editBtn.setLayoutY(40);
		editBtn.setDisable(true);
		editBtn.setLayoutX(1110);
		editBtn.setTooltip(new Tooltip("Save Row"));
		editBtn.getTooltip().setOnShowing(s -> {
			Bounds bounds = editBtn.localToScreen(editBtn.getBoundsInLocal());
			editBtn.getTooltip().setX(bounds.getMaxX());
		});
		ImageView editImage = new ImageView(getClass().getResource("/resources/saveRow.png").toString());
		editImage.setFitHeight(15);
		editImage.setFitWidth(15);
		editBtn.setGraphic(editImage);

		/** Remove Row Button **/
		Button removeBtn = new Button();
		removeBtn.setLayoutY(76);
		removeBtn.setDisable(true);
		removeBtn.setLayoutX(1110);
		removeBtn.setTooltip(new Tooltip("Remove Row"));
		removeBtn.getTooltip().setOnShowing(s -> {
			Bounds bounds = removeBtn.localToScreen(removeBtn.getBoundsInLocal());
			removeBtn.getTooltip().setX(bounds.getMaxX());
		});
		ImageView removeImage = new ImageView(getClass().getResource("/resources/deleteRow.png").toString());
		removeImage.setFitHeight(15);
		removeImage.setFitWidth(15);
		removeBtn.setGraphic(removeImage);
		for (int i = 0; i < formatAndResolutionList.size(); i++) {
			UVCUACSettingsController.createAndAddRowInTable(addBtn, editBtn, formatAndResolutionList.get(i),
					formateAndResolutionData, formatResolutionTable, i + 1, uvcuacTabErrorList, subTab, subSubTab,
					uvcuacSettingsTab, tooltipAndErrorProperties, performLoadAction,
					Integer.parseInt(fifoBusWidth.getValue()), errorListOnSave);
		}

		/** Add Row in Table **/
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FormatAndResolutions formatResolutionJson = loadFormatResolutions();
				uvcSettings.getFORMAT_RESOLUTION().getFORMAT_RESOLUTIONS().add(formatResolutionJson);
				UVCUACSettingsController.createAndAddRowInTable(addBtn, editBtn, formatResolutionJson,
						formateAndResolutionData, formatResolutionTable, formateAndResolutionData.size() + 1,
						uvcuacTabErrorList, subTab, subSubTab, uvcuacSettingsTab, tooltipAndErrorProperties,
						performLoadAction, Integer.parseInt(fifoBusWidth.getValue()), errorListOnSave);
				addBtn.setDisable(true);
				editBtn.setDisable(true);
				formatResolutionTable.requestFocus();
			}

		});

		editBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FormatAndResolutionTableModel editedRow = formatResolutionTable.getSelectionModel().getSelectedItem();
				if (editedRow != null && editedRow.getImageFormat().getValue() != null
						&& !editedRow.getResolutionH().getText().equals("")
						&& !editedRow.getResolutionV().getText().equals("")
						&& ((editedRow.getSupportedInFS().isSelected()
								&& !editedRow.getFrameRateInFS().getText().equals(""))
								|| (editedRow.getSupportedInHS().isSelected()
										&& !editedRow.getFrameRateInHS().getText().equals(""))
								|| (editedRow.getSupportedInSS().isSelected()
										&& !editedRow.getFrameRateInSS().getText().equals("")))) {
					editedRow.getImageFormat().setDisable(true);
					editedRow.getResolutionH().setDisable(true);
					editedRow.getResolutionV().setDisable(true);
					editedRow.getStillCapture().setDisable(true);
					editedRow.getSupportedInFS().setDisable(true);
					editedRow.getSupportedInHS().setDisable(true);
					editedRow.getSupportedInSS().setDisable(true);
					editedRow.getFrameRateInFS().setDisable(true);
					editedRow.getFrameRateInHS().setDisable(true);
					editedRow.getFrameRateInSS().setDisable(true);
					editedRow.getButton().setDisable(true);
					if (!formateAndResolutionData.get(formateAndResolutionData.size() - 1).getImageFormat().getValue()
							.equals("")
							&& !formateAndResolutionData.get(formateAndResolutionData.size() - 1).getResolutionH()
									.getText().equals("")
							&& !formateAndResolutionData.get(formateAndResolutionData.size() - 1).getResolutionV()
									.getText().equals("")
							&& ((formateAndResolutionData.get(formateAndResolutionData.size() - 1).getSupportedInFS()
									.isSelected() && !editedRow.getFrameRateInFS().getText().equals(""))
									|| (formateAndResolutionData.get(formateAndResolutionData.size() - 1)
											.getSupportedInHS().isSelected()
											&& !editedRow.getFrameRateInHS().getText().equals(""))
									|| (formateAndResolutionData.get(formateAndResolutionData.size() - 1)
											.getSupportedInSS().isSelected()
											&& !editedRow.getFrameRateInSS().getText().equals("")))) {
						addBtn.setDisable(false);
						formatResolutionTable.requestFocus();
					}
					editBtn.setDisable(true);
					formatResolutionTable.requestFocus();
				} else {
					showAlertMsg("Fill all columns");
				}
			}
		});

		removeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FormatAndResolutionTableModel editedRow = formatResolutionTable.getSelectionModel().getSelectedItem();
				formateAndResolutionData.remove(editedRow);
				int index = Integer.parseInt(editedRow.getSno().getText());
				formatAndResolutionList.remove(index - 1);
				for (int i = 0; i < formateAndResolutionData.size(); i++) {
					Label sno = new Label(String.valueOf(i + 1));
					formateAndResolutionData.get(i).setSno(sno);
					if (formateAndResolutionData.get(i).getImageFormat().getValue()
							.equals(formatAndResolutionList.get(i).getIMAGE_FORMAT())) {
						formatAndResolutionList.get(i).setS_NO(Integer.parseInt(sno.getText()));
					}
				}
				uvcSettings.getFORMAT_RESOLUTION().setFORMAT_RESOLUTIONS(formatAndResolutionList);
				removeBtn.setDisable(true);
				addBtn.setDisable(false);
				formatResolutionTable.requestFocus();
			}

		});

		formatResolutionTable.setStyle("-fx-cell-alignment: center;");
		formatResolutionTable.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
				addBtn.setDisable(true);
				removeBtn.setDisable(true);
				FormatAndResolutionTableModel selectedRow = formatResolutionTable.getSelectionModel().getSelectedItem();
				for (FormatAndResolutionTableModel formatResolution : formateAndResolutionData) {
					if (selectedRow.getSno() == formatResolution.getSno()) {
						formatResolution.getImageFormat().setDisable(false);
						formatResolution.getResolutionH().setDisable(false);
						formatResolution.getResolutionV().setDisable(false);
						formatResolution.getStillCapture().setDisable(false);
						if(SX3Manager.getInstance().getSx3Configuration().getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS()
								.getENDPOINT_TRANSFER_TYPE().equals("Bulk")) {
							formatResolution.getSupportedInHS().setDisable(false);
						}else {
							formatResolution.getSupportedInHS().setDisable(true);
						}
						formatResolution.getSupportedInSS().setDisable(false);
						if (formatResolution.getSupportedInFS().isSelected()) {
							formatResolution.getFrameRateInFS().setDisable(false);
						} else {
							formatResolution.getFrameRateInFS().setDisable(true);
						}
						if (formatResolution.getSupportedInHS().isSelected()) {
							formatResolution.getFrameRateInHS().setDisable(false);
						} else {
							formatResolution.getFrameRateInHS().setDisable(true);
						}
						if (formatResolution.getSupportedInSS().isSelected()) {
							formatResolution.getFrameRateInSS().setDisable(false);
						} else {
							formatResolution.getFrameRateInSS().setDisable(true);
						}
						formatResolution.getButton().setDisable(false);
						if (formatResolution.getImageFormat().getValue() != null
								&& !formatResolution.getResolutionH().getText().equals("")
								&& !formatResolution.getResolutionV().getText().equals("")
								&& (formatResolution.getSupportedInFS().isSelected()
										|| formatResolution.getSupportedInHS().isSelected()
										|| formatResolution.getSupportedInSS().isSelected())) {
							editBtn.setDisable(false);
						} else {
							editBtn.setDisable(true);
						}
					} else {
						formatResolution.getImageFormat().setDisable(true);
						formatResolution.getResolutionH().setDisable(true);
						formatResolution.getResolutionV().setDisable(true);
						formatResolution.getStillCapture().setDisable(true);
						formatResolution.getSupportedInFS().setDisable(true);
						formatResolution.getSupportedInHS().setDisable(true);
						formatResolution.getSupportedInSS().setDisable(true);
						formatResolution.getFrameRateInFS().setDisable(true);
						formatResolution.getFrameRateInHS().setDisable(true);
						formatResolution.getFrameRateInSS().setDisable(true);
						formatResolution.getButton().setDisable(true);
					}
				}
			} else {
				if (formateAndResolutionData.size() > 1) {
					removeBtn.setDisable(false);
				}
			}
		});
		topAnchor.getChildren().addAll(formatResolutionTable, addBtn, editBtn, removeBtn, sepHor);

	}

	/**
	 * @param rootTab
	 * @param cameraControlTab
	 * @param cameraControlList create camera control tab UI
	 */
	private void createCameraControl(Tab rootTab, Tab cameraControlTab,
			List<LinkedHashMap<String, LinkedHashMap<String, Object>>> cameraControlList) {
		ScrollPane rootScrollPane = new ScrollPane();
		rootScrollPane.getStyleClass().add("scrollpane");
		BorderPane borderPane = new BorderPane();
		rootScrollPane.setContent(borderPane);
		cameraControlTab.setContent(rootScrollPane);

		TableView<CameraAndProcessingUnitControlsTableModel> cameraControlTableView = new TableView<>();
		cameraControlTableView.setId("cameraControlTableView");
		ObservableList<CameraAndProcessingUnitControlsTableModel> cameraControlsList = FXCollections
				.observableArrayList();
		AnchorPane anchorPane = new AnchorPane();
		AnchorPane anchorPane1 = new AnchorPane();
		anchorPane1.setLayoutX(30);
		anchorPane1.setLayoutY(20);
		anchorPane1.setPrefWidth(1000);
		anchorPane1.setStyle("-fx-background-color: #D0D3D4; -fx-border-insets: 4 1 1 1; -fx-border-color: black;");
		Label cameraControlLabel = new Label("Camera Controls");
		cameraControlLabel.setLayoutX(6);
		cameraControlLabel.setLayoutY(-5);
		cameraControlLabel.setStyle("-fx-background-color: inherit;");
		cameraControlTableView.setPrefWidth(995);
		cameraControlTableView.setLayoutX(5);
		cameraControlTableView.setLayoutY(10);

		cameraControlTableView.setPrefHeight(600.0);

		/** Create Camera Control Table **/
		cameraControlTableView = UVCUACSettingsController
				.createCameraAndProcessingControlTableUI(cameraControlTableView);

		/** Add Rows in Camera Control Table **/
		for (int i = 0; i < cameraControlList.size(); i++) {
			UVCUACSettingsController.addRowInCameraContolTable(uvcuacTabErrorList, uvcuacSettingsTab, rootTab,
					cameraControlTab, UVCSettingsConstants.CAMERA_CONTROL_LABEL[i], cameraControlsList,
					cameraControlList.get(i), logDetails2, performLoadAction, tooltipAndErrorProperties);
		}
		cameraControlTableView.setItems(cameraControlsList);

		anchorPane1.getChildren().addAll(cameraControlLabel, cameraControlTableView);
		anchorPane.getChildren().add(anchorPane1);
		rootScrollPane.setContent(anchorPane);

	}

	/**
	 * @param rootTab
	 * @param processingUnitControlTab
	 * @param processingUnitControlList create processing unit control UI
	 */
	private void createProcessingUnitControl(Tab rootTab, Tab processingUnitControlTab,
			List<LinkedHashMap<String, LinkedHashMap<String, Object>>> processingUnitControlList) {
		ScrollPane rootScrollPane = new ScrollPane();
		rootScrollPane.getStyleClass().add("scrollpane");
		BorderPane borderPane = new BorderPane();
		rootScrollPane.setContent(borderPane);
		processingUnitControlTab.setContent(rootScrollPane);

		TableView<CameraAndProcessingUnitControlsTableModel> processingUnitControlTableView = new TableView<>();

		ObservableList<CameraAndProcessingUnitControlsTableModel> processingUnitControlsList = FXCollections
				.observableArrayList();

		AnchorPane anchorPane = new AnchorPane();
		AnchorPane anchorPane1 = new AnchorPane();
		anchorPane1.setLayoutX(30);
		anchorPane1.setLayoutY(20);
		anchorPane1.setPrefWidth(1000);
		anchorPane1.setStyle("-fx-background-color: #D0D3D4; -fx-border-insets: 4 1 1 1; -fx-border-color: black;");
		Label processingUnitControlLabel = new Label("Processing Unit Controls");
		processingUnitControlLabel.setLayoutX(6);
		processingUnitControlLabel.setLayoutY(-5);
		processingUnitControlLabel.setStyle("-fx-background-color: inherit;");

		/** Crate Processing Unit Control Table **/
		processingUnitControlTableView = new TableView<>();
		processingUnitControlTableView.setPrefHeight(600.0);
		processingUnitControlTableView.setPrefWidth(995);
		processingUnitControlTableView.setLayoutX(5);
		processingUnitControlTableView.setLayoutY(10);
		processingUnitControlTableView = UVCUACSettingsController
				.createCameraAndProcessingControlTableUI(processingUnitControlTableView);

		/** Add Rows in Processing Unit Control Table **/
		/** Add Rows in Camera Control Table **/
		for (int i = 0; i < processingUnitControlList.size(); i++) {
			UVCUACSettingsController.addRowsInProcessingUnitControlTable(uvcuacTabErrorList, uvcuacSettingsTab, rootTab,
					processingUnitControlTab, UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL[i],
					processingUnitControlsList, processingUnitControlList.get(i), logDetails3, performLoadAction,
					tooltipAndErrorProperties);
		}

		processingUnitControlTableView.setItems(processingUnitControlsList);
		anchorPane1.getChildren().addAll(processingUnitControlLabel, processingUnitControlTableView);
		anchorPane.getChildren().add(anchorPane1);
		rootScrollPane.setContent(anchorPane);

	}

	private void createExtensionUnitControl(Tab extensionUnitControlTab, ExtensionUnitControl extensionUnitControl) {
		AnchorPane anchorPane = new AnchorPane();
		anchorPane = UVCUACSettingsController.createExtensionUnitControlUI(anchorPane, extensionUnitControl,
				tooltipAndErrorProperties);

		extensionUnitControlTab.setContent(anchorPane);
	}

	/**
	 * @param tab
	 * @param uacSettingList
	 * @param number
	 * @param endpointName   create UAC tab UI
	 */
	private void createUACUI(Tab tab, List<UACSettings> uacSettingList, int number, String endpointName) {
		TabPane tabPane = new TabPane();
		tab.setContent(tabPane);
		UACSettings uacSettings = new UACSettings();
		uacSettings.setRESERVED("FF");
		if (performLoadAction == false) {
			int samplingFrequenciesCount = 1;
			EndpointSettings endpointSettings = loadEndpoint("UAC");
			uacSettings.setEndpointSettings(endpointSettings);
			LinkedHashMap<String, String> chennalConfig = loadChannelConfig();
			UACSetting uacSetting = new UACSetting();
			uacSetting.setTERMINAL_TYPE(UACSettingFieldConstants.TERMINAL_TYPE[0]);
			uacSetting.setNUMBER_OF_CHANNELS(1);
			chennalConfig.put("D0: Left Front (L)", "Enable");
			uacSetting.setCHANNEL_CONFIGURATION(chennalConfig);
			uacSetting.setNUMBER_OF_SAMPLING_FREQUENCIES(samplingFrequenciesCount);
			uacSetting.setRESERVED("FF");
			uacSetting.setSAMPLING_FREQUENCY_1_SENSOR_CONFIG(loadSampleFreqSensorConfig());
			uacSetting.setSAMPLING_FREQUENCY_2_SENSOR_CONFIG(loadSampleFreqSensorConfig());
			uacSetting.setSAMPLING_FREQUENCY_3_SENSOR_CONFIG(loadSampleFreqSensorConfig());
			uacSetting.setSAMPLING_FREQUENCY_4_SENSOR_CONFIG(loadSampleFreqSensorConfig());
			LinkedHashMap<String, String> featureUnitControl = loadFeatureUnitControl();
			uacSetting.setFEATURE_UNIT_CONTROLS(featureUnitControl);
			uacSetting.setBIT_RESOLUTION("16 bit");
			uacSetting.setAUDIO_FORMAT("PCM");
			uacSettings.setUAC_SETTING(uacSetting);
			if (endpointName.equals("endpoint1") && uacSettingList != null && uacSettingList.size() == 1) {
				UACSettings uacSettings2 = uacSettingList.get(0);
				uacSettingList.set(0, uacSettings);
				uacSettingList.add(uacSettings2);
			} else {
				uacSettingList.add(uacSettings);
			}
			sx3Configuration.setUAC_SETTINGS(uacSettingList);
		} else {
			if (tab.getText().contains("Endpoint 2") && sx3Configuration.getUAC_SETTINGS().size() == 1) {
				uacSettings = sx3Configuration.getUAC_SETTINGS().get(number - 1);
			} else {
				uacSettings = sx3Configuration.getUAC_SETTINGS().get(number);
			}
		}

		/** Endpoint Settings **/
		Tab endpointTab = new Tab();
		endpointTab.setText("Endpoint Settings");
		endpointTab.setClosable(false);
		createEndpointSettingsUI(tab, uacSettings.getEndpointSettings(), endpointTab, tab.getText(), "UAC");

		/** UAC Settings Tab **/
		Tab uacSettingTab = new Tab();
		uacSettingTab.setText("UAC Setting");
		uacSettingTab.setClosable(false);
		createUACSettingsUI(uacSettingTab, uacSettings);
		tabPane.getTabs().addAll(endpointTab, uacSettingTab);

	}

	private List<SensorConfig> loadSampleFreqSensorConfig() {
		List<SensorConfig> sensorConfigList = new ArrayList<>();
		SensorConfig sensorConfig = new SensorConfig("", "", "");
		sensorConfigList.add(sensorConfig);
		return sensorConfigList;
	}

	@SuppressWarnings("static-access")
	private void createUACSettingsUI(Tab uacSettingTab, UACSettings uacSettings) {
		ScrollPane rootScrollPane = new ScrollPane();
		rootScrollPane.getStyleClass().add("scrollpane");
		AnchorPane topAnchor = new AnchorPane();
		topAnchor.setLeftAnchor(topAnchor, 0.0);
		topAnchor.setRightAnchor(topAnchor, 0.0);
		topAnchor.setTopAnchor(topAnchor, 0.0);
		topAnchor.setBottomAnchor(topAnchor, 0.0);
		rootScrollPane.setContent(topAnchor);

		/** Create UAC Settings Tab UI **/

		UVCUACSettingsController.createUACSettingsTabUI(topAnchor, uacSettings, tooltipAndErrorProperties);

		uacSettingTab.setContent(rootScrollPane);

	}

	/**
	 * ------------------------------- Endpoint Setting Tab For UVC And UAC
	 * ----------------------------------------
	 * 
	 * @param rootTab
	 * @param tabType
	 * @param total
	 * @param endpointOneChanges2
	 **/

	@SuppressWarnings("static-access")
	private void createEndpointSettingsUI(Tab rootTab, EndpointSettings endpointSettingsJson, Tab endpointTab,
			String tabName, String tabType) {
		String endpointName = tabName;
		if (tabName.contains("Endpoint 1")) {
			tabName = "Endpoint 1";
		} else {
			tabName = "Endpoint 2";
		}
		AnchorPane anchorPane = new AnchorPane();
		AnchorPane anchorPane1 = new AnchorPane();
		anchorPane1.setLayoutX(50);
		anchorPane1.setLayoutY(20);
		anchorPane1.setStyle("-fx-background-color: #D0D3D4; -fx-border-insets: 4 1 1 1; -fx-border-color: black;");
		Label endpointLabel = new Label("Endpoint Settings");
		endpointLabel.setLayoutX(6);
		endpointLabel.setLayoutY(-5);
		endpointLabel.setStyle("-fx-background-color: inherit;");
		GridPane leftAnchorGridPane = new GridPane();
		leftAnchorGridPane.setLayoutX(3);
		leftAnchorGridPane.setLayoutY(20);
		leftAnchorGridPane.setVgap(5.0);

		if (tabType.equals("UVC")) {
			/** Transfer Type **/
			Label end1TransferTypeLabel = new Label(tabName + " Transfer Type : ");
			leftAnchorGridPane.setMargin(end1TransferTypeLabel, new Insets(0, 0, 0, 5));
			leftAnchorGridPane.add(end1TransferTypeLabel, 0, 1);
			ComboBox<String> end1TransferTypeValue = new ComboBox<>();
			end1TransferTypeValue.setId("endpointTransferType");
			end1TransferTypeValue.getItems().addAll("Bulk", "Isochronous");
			end1TransferTypeValue.setTooltip(
					new Tooltip(tooltipAndErrorProperties.getProperty("Endpoint_Settings.TOOLTIP_TRANSFER_TYPE")));
			end1TransferTypeValue.getTooltip().setOnShowing(s -> {
				Bounds bounds = end1TransferTypeValue.localToScreen(end1TransferTypeValue.getBoundsInLocal());
				end1TransferTypeValue.getTooltip().setX(bounds.getMaxX());
			});
			end1TransferTypeValue.setValue(endpointSettingsJson.getENDPOINT_TRANSFER_TYPE());
			leftAnchorGridPane.add(end1TransferTypeValue, 1, 1);
			end1TransferTypeValue.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					endpointSettingsJson.setENDPOINT_TRANSFER_TYPE(end1TransferTypeValue.getValue());
					SX3Manager.getInstance()
							.addLog(endpointName + " Transfer Type : " + end1TransferTypeValue.getValue() + ".<br>");
					logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

				}

			});
			end1TransferTypeValue.setOnMouseClicked(new OnMouseClickHandler());
		}

		/** Brust Length **/
		Label end1BurstLengthLabel = new Label(tabName + " Burst Length : ");
		leftAnchorGridPane.setMargin(end1BurstLengthLabel, new Insets(0, 0, 0, 5));
		if (tabType.equals("UVC")) {
			leftAnchorGridPane.add(end1BurstLengthLabel, 0, 2);
		} else {
			leftAnchorGridPane.add(end1BurstLengthLabel, 0, 1);
		}
		TextField end1BurstLengthValue = new TextField();
		end1BurstLengthValue.setAlignment(Pos.CENTER_RIGHT);
		end1BurstLengthValue.setId("end" + tabType + "BurstLengthValue");
		end1BurstLengthValue.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("Endpoint_Settings.TOOLTIP_BURST_LENGTH")));
		end1BurstLengthValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = end1BurstLengthValue.localToScreen(end1BurstLengthValue.getBoundsInLocal());
			end1BurstLengthValue.getTooltip().setX(bounds.getMaxX());
		});
		end1BurstLengthValue.setText(String.valueOf(endpointSettingsJson.getBURST_LENGTH()));
		end1BurstLengthValue.setMaxWidth(40);
		UVCSettingsValidation.setupEndpointValidationForNumeric(uvcuacSettingsTab, rootTab, endpointTab,
				end1BurstLengthValue, performLoadAction, endpointSettingsTabErrorList,
				tooltipAndErrorProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), 16, "Burst Length");
		end1BurstLengthValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (end1BurstLengthValue.getText().equals("") || end1BurstLengthValue.getText().equals("0")) {
					end1BurstLengthValue.setText("1");
				}
				endpointSettingsJson.setBURST_LENGTH(Integer.parseInt(end1BurstLengthValue.getText()));
				SX3Manager.getInstance()
						.addLog(endpointName + " Burst Length : " + end1BurstLengthValue.getText() + ".<br>");
				logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

			}
		});
		if (tabType.equals("UVC")) {
			leftAnchorGridPane.add(end1BurstLengthValue, 1, 2);
		} else {
			leftAnchorGridPane.add(end1BurstLengthValue, 1, 1);
		}
		end1BurstLengthValue.setOnMouseClicked(new OnMouseClickHandler());

		/** Buffer Size **/
		Label end1BufferSizeLabel = new Label(tabName + " Buffer Size (Bytes) : ");
		leftAnchorGridPane.setMargin(end1BufferSizeLabel, new Insets(0, 0, 0, 5));
		if (tabType.equals("UVC")) {
			leftAnchorGridPane.add(end1BufferSizeLabel, 0, 3);
		} else {
			leftAnchorGridPane.add(end1BufferSizeLabel, 0, 2);
		}
		TextField end1BufferSizeValue = new TextField();
		leftAnchorGridPane.setMargin(end1BufferSizeValue, new Insets(0, 5, 0, 0));
		end1BufferSizeValue.setAlignment(Pos.CENTER_RIGHT);
		end1BufferSizeValue.setId("end" + tabType + "BufferSizeValue");
		end1BufferSizeValue.setMaxWidth(60);
		end1BufferSizeValue.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("Endpoint_Settings.TOOLTIP_BUFFER_SIZE")));
		end1BufferSizeValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = end1BufferSizeValue.localToScreen(end1BufferSizeValue.getBoundsInLocal());
			end1BufferSizeValue.getTooltip().setX(bounds.getMaxX());
		});
		end1BufferSizeValue.setText(String.valueOf(endpointSettingsJson.getBUFFER_SIZE()));
		UVCSettingsValidation.setupEndpointValidationForNumeric(uvcuacSettingsTab, rootTab, endpointTab,
				end1BufferSizeValue, performLoadAction, endpointSettingsTabErrorList,
				tooltipAndErrorProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), 65535, "Buffer Size");
		end1BufferSizeValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (end1BufferSizeValue.getText().equals("") || end1BufferSizeValue.getText().equals("0")) {
					end1BufferSizeValue.setText("16");
				}
				int bufferSize = Integer.parseInt(end1BufferSizeValue.getText());
				if (bufferSize % 16 != 0) {
					errorListOnSave.put(endpointName + " Buffer Size", true);
					SX3Manager.getInstance().addLog(
							"<span style='color:red;'>Choose the size (in bytes) for each buffer (should be a multiple of 16).</span><br>");
					logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				} else {
					errorListOnSave.put(endpointName + " Buffer Size", false);
					SX3Manager.getInstance()
							.addLog(endpointName + " Buffer Size (Bytes) : " + end1BufferSizeValue.getText() + ".<br>");
					logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				}
			}
		});
		if (tabType.equals("UVC")) {
			leftAnchorGridPane.add(end1BufferSizeValue, 1, 3);
		} else {
			leftAnchorGridPane.add(end1BufferSizeValue, 1, 2);
		}
		end1BufferSizeValue.setOnMouseClicked(new OnMouseClickHandler());

		/** Buffer Count **/
		Label end1BufferCountLabel = new Label(tabName + "  Buffer Count : ");
		leftAnchorGridPane.setMargin(end1BufferCountLabel, new Insets(0, 0, 0, 5));
		if (tabType.equals("UVC")) {
			leftAnchorGridPane.add(end1BufferCountLabel, 0, 4);
		} else {
			leftAnchorGridPane.add(end1BufferCountLabel, 0, 3);
		}
		TextField end1BufferCountValue = new TextField();
		end1BufferCountValue.setAlignment(Pos.CENTER_RIGHT);
		end1BufferCountValue.setId("end" + tabType + "BufferCountValue");
		end1BufferCountValue.setMaxWidth(40);
		end1BufferCountValue.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("Endpoint_Settings.TOOLTIP_BUFFER_COUNT")));
		end1BufferCountValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = end1BufferCountValue.localToScreen(end1BufferCountValue.getBoundsInLocal());
			end1BufferCountValue.getTooltip().setX(bounds.getMaxX());
		});
		end1BufferCountValue.setText(String.valueOf(endpointSettingsJson.getBUFFER_COUNT()));
		UVCSettingsValidation.setupEndpointValidationForNumeric(uvcuacSettingsTab, rootTab, endpointTab,
				end1BufferCountValue, performLoadAction, endpointSettingsTabErrorList,
				tooltipAndErrorProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), 255, "Buffer Count");
		end1BufferCountValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (end1BufferCountValue.getText().equals("") || end1BufferCountValue.getText().equals("0")) {
					end1BufferCountValue.setText("1");
				}
				SX3Manager.getInstance()
						.addLog(endpointName + " Buffer Count : " + end1BufferCountValue.getText() + ".<br>");
				logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

			}
		});
		if (tabType.equals("UVC")) {
			leftAnchorGridPane.add(end1BufferCountValue, 1, 4);
		} else {
			leftAnchorGridPane.add(end1BufferCountValue, 1, 3);
		}
		end1BufferCountValue.setOnMouseClicked(new OnMouseClickHandler());

		/** Total Used Buffer Space **/
		Label totalUseBufferSpaceLabel = new Label(tabName + " Total used Buffer Space (KB) : ");
		leftAnchorGridPane.setMargin(totalUseBufferSpaceLabel, new Insets(0, 0, 0, 5));
		if (tabType.equals("UVC")) {
			leftAnchorGridPane.add(totalUseBufferSpaceLabel, 0, 5);
		} else {
			leftAnchorGridPane.add(totalUseBufferSpaceLabel, 0, 4);
		}
		TextField totalUseBufferSpaceValue = new TextField();
		leftAnchorGridPane.setMargin(totalUseBufferSpaceValue, new Insets(0, 5, 0, 0));
		totalUseBufferSpaceValue.setAlignment(Pos.CENTER_RIGHT);
		totalUseBufferSpaceValue.setId("totalUseBufferSpaceValue");
		totalUseBufferSpaceValue.setMaxWidth(60);
		totalUseBufferSpaceValue.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("Endpoint_Settings.TOOLTIP_BUFFER_SPACE")));
		totalUseBufferSpaceValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = totalUseBufferSpaceValue.localToScreen(totalUseBufferSpaceValue.getBoundsInLocal());
			totalUseBufferSpaceValue.getTooltip().setX(bounds.getMaxX());
		});
		totalUseBufferSpaceValue.setText(String.valueOf(endpointSettingsJson.getUSED_BUFFER_SPACE()));
		end1BufferCountValue.setOnMouseClicked(new OnMouseClickHandler());
		endpointSettingsJson.setUSED_BUFFER_SPACE(Integer.parseInt(totalUseBufferSpaceValue.getText()));
		totalUseBufferSpaceValue.setDisable(true);

		end1BufferSizeValue.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if (newValue.equals("")) {
				newValue = "0";
			}
			if (end1BufferSizeLabel.getText().contains("Endpoint 1")) {
				if (Endpoint_1.getValue().equals("UVC") && Endpoint_2.getValue().equals("UAC")) {
					int bufferSize = sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getBUFFER_SIZE();
					int bufferCount = sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getBUFFER_COUNT();
					sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().setUSED_BUFFER_SPACE(
							((Integer.parseInt(newValue) * Integer.parseInt(end1BufferCountValue.getText()))
									+ (bufferSize * bufferCount)) * 2);
					sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().setUSED_BUFFER_SPACE(
							((Integer.parseInt(newValue) * Integer.parseInt(end1BufferCountValue.getText()))
									+ (bufferSize * bufferCount)) * 2);
					sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS()
							.setBUFFER_SIZE(Integer.parseInt(newValue));
					totalUseBufferSpaceValue.setText(String.valueOf(
							sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getUSED_BUFFER_SPACE()));
					setEndpointBufferSpace(leftAnchorGridPane, totalUseBufferSpaceValue, "UVC");
				} else if (Endpoint_1.getValue().equals("UAC") && Endpoint_2.getValue().equals("Not Enabled")) {
					sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().setUSED_BUFFER_SPACE(
							((Integer.parseInt(newValue) * Integer.parseInt(end1BufferCountValue.getText()))) * 2);
					sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings()
							.setBUFFER_SIZE(Integer.parseInt(newValue));
					totalUseBufferSpaceValue.setText(String.valueOf(
							sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getUSED_BUFFER_SPACE()));
					setEndpointBufferSpace(leftAnchorGridPane, totalUseBufferSpaceValue, "UAC");
				} else if (Endpoint_1.getValue().equals("UVC") && Endpoint_2.getValue().equals("Not Enabled")) {
					sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().setUSED_BUFFER_SPACE(
							((Integer.parseInt(newValue) * Integer.parseInt(end1BufferCountValue.getText()))) * 2);
					sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS()
							.setBUFFER_SIZE(Integer.parseInt(newValue));
					totalUseBufferSpaceValue.setText(String.valueOf(
							sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getUSED_BUFFER_SPACE()));
					setEndpointBufferSpace(leftAnchorGridPane, totalUseBufferSpaceValue, "UVC");
				}
			} else {
				if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UVC")) {
					int bufferSize = sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getBUFFER_SIZE();
					int bufferCount = sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS()
							.getBUFFER_COUNT();
					sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().setUSED_BUFFER_SPACE(
							((Integer.parseInt(newValue) * Integer.parseInt(end1BufferCountValue.getText()))
									+ (bufferSize * bufferCount)) * 2);
					sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().setUSED_BUFFER_SPACE(
							((Integer.parseInt(newValue) * Integer.parseInt(end1BufferCountValue.getText()))
									+ (bufferSize * bufferCount)) * 2);
					sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings()
							.setBUFFER_SIZE(Integer.parseInt(newValue));
					totalUseBufferSpaceValue.setText(String.valueOf(
							sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getUSED_BUFFER_SPACE()));
					setEndpointBufferSpace(leftAnchorGridPane, totalUseBufferSpaceValue, "UAC");
				}
			}
		});

		end1BufferCountValue.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if (newValue.equals("")) {
				newValue = "0";
			}
			if (end1BufferCountLabel.getText().contains("Endpoint 1")) {
				if (Endpoint_1.getValue().equals("UVC") && Endpoint_2.getValue().equals("UAC")) {
					int bufferSize = sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getBUFFER_SIZE();
					int bufferCount = sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getBUFFER_COUNT();
					sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().setUSED_BUFFER_SPACE(
							((Integer.parseInt(end1BufferSizeValue.getText()) * Integer.parseInt(newValue))
									+ (bufferSize * bufferCount)) * 2);
					sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().setUSED_BUFFER_SPACE(
							((Integer.parseInt(end1BufferSizeValue.getText()) * Integer.parseInt(newValue))
									+ (bufferSize * bufferCount)) * 2);
					sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS()
							.setBUFFER_COUNT(Integer.parseInt(newValue));
					totalUseBufferSpaceValue.setText(String.valueOf(
							sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getUSED_BUFFER_SPACE()));
					setEndpointBufferSpace(leftAnchorGridPane, totalUseBufferSpaceValue, "UVC");
				} else if (Endpoint_1.getValue().equals("UAC") && Endpoint_2.getValue().equals("Not Enabled")) {
					sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().setUSED_BUFFER_SPACE(
							((Integer.parseInt(end1BufferSizeValue.getText()) * Integer.parseInt(newValue))) * 2);
					sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings()
							.setBUFFER_COUNT(Integer.parseInt(newValue));
					totalUseBufferSpaceValue.setText(String.valueOf(
							sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getUSED_BUFFER_SPACE()));
					setEndpointBufferSpace(leftAnchorGridPane, totalUseBufferSpaceValue, "UAC");
				} else if (Endpoint_1.getValue().equals("UVC") && Endpoint_2.getValue().equals("Not Enabled")) {
					sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().setUSED_BUFFER_SPACE(
							((Integer.parseInt(end1BufferSizeValue.getText()) * Integer.parseInt(newValue))) * 2);
					sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS()
							.setBUFFER_COUNT(Integer.parseInt(newValue));
					totalUseBufferSpaceValue.setText(String.valueOf(
							sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getUSED_BUFFER_SPACE()));
					setEndpointBufferSpace(leftAnchorGridPane, totalUseBufferSpaceValue, "UVC");
				}
			} else {
				int bufferSize = sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getBUFFER_SIZE();
				int bufferCount = sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getBUFFER_COUNT();
				sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().setUSED_BUFFER_SPACE(
						((Integer.parseInt(end1BufferSizeValue.getText()) * Integer.parseInt(newValue))
								+ (bufferSize * bufferCount)) * 2);
				sx3Configuration.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().setUSED_BUFFER_SPACE(
						((Integer.parseInt(end1BufferSizeValue.getText()) * Integer.parseInt(newValue))
								+ (bufferSize * bufferCount)) * 2);
				sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings()
						.setBUFFER_COUNT(Integer.parseInt(newValue));
				totalUseBufferSpaceValue.setText(String.valueOf(
						sx3Configuration.getUAC_SETTINGS().get(0).getEndpointSettings().getUSED_BUFFER_SPACE()));
				setEndpointBufferSpace(leftAnchorGridPane, totalUseBufferSpaceValue, "UAC");
			}
		});

		if (tabType.equals("UVC")) {
			leftAnchorGridPane.add(totalUseBufferSpaceValue, 1, 5);
		} else {
			leftAnchorGridPane.add(totalUseBufferSpaceValue, 1, 4);
		}
		anchorPane1.getChildren().add(leftAnchorGridPane);
		anchorPane1.getChildren().add(endpointLabel);
		anchorPane.getChildren().add(anchorPane1);
		endpointTab.setContent(anchorPane);
	}

	private void setEndpointBufferSpace(GridPane leftAnchorGridPane, TextField totalUseBufferSpaceValue,
			String tabName) {
		ObservableList<javafx.scene.Node> children2 = leftAnchorGridPane.getChildren();
		for (javafx.scene.Node node : children2) {
			if (tabName.equals("UVC")) {
				if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 5) {
					leftAnchorGridPane.getChildren().remove(node);
					leftAnchorGridPane.add(totalUseBufferSpaceValue, 1, 5);
					break;
				}
			} else {
				if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 4) {
					leftAnchorGridPane.getChildren().remove(node);
					leftAnchorGridPane.add(totalUseBufferSpaceValue, 1, 4);
					break;
				}
			}
		}
	}

	/**
	 * ------------------------------------ FIFO Master Config Tab
	 * ------------------------------------------------
	 **/

	@FXML
	private void enableFPGA() {
		checkFieldEditOrNot = true;
		if (enableFPGA.isSelected()) {
			fpgaFamily.setDisable(false);
			browseBitFile.setDisable(false);
			SX3Manager.getInstance().addLog("Enable FIFO Master Configuration Download : " + true + ".<br>");
			logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

		} else {
			fpgaFamily.setDisable(true);
			browseBitFile.setDisable(true);
			chooseBitFile.setText("");
			bitFileSize.setText("0");
			SX3Manager.getInstance().addLog("Enable FIFO Master Configuration Download : " + false + ".<br>");
			logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

		}
	}

	@FXML
	private void selectFPGAFamily() {
		checkFieldEditOrNot = true;
		SX3Manager.getInstance().addLog("FPGA Family : " + fpgaFamily.getValue() + ".<br>");
		logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

	}

	@FXML
	private void selectBitFile() {
		checkFieldEditOrNot = true;
		Stage primaryStage = (Stage) deviceName.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		configureBitFileChooser(fileChooser);
		if (!chooseBitFile.getText().isEmpty()) {
			String bitFilePath = BytesStreamsAndHexFileUtil.getConfigJsonFile().getParentFile().getAbsolutePath() + "/"
					+ chooseBitFile.getText();
			fileChooser.setInitialDirectory(new File(bitFilePath).getParentFile());
		}
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		if (selectedFile != null) {
			String fileName = selectedFile.getName();
			if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
//				String fileExtention = fileName.substring(fileName.lastIndexOf(".") + 1);
//				if (fileExtention.equals("bit")) {

				String configContainerFolder = BytesStreamsAndHexFileUtil.getConfigJsonFile().getParent();

				try {
					Files.copy(new FileInputStream(selectedFile), Paths.get(configContainerFolder + "/" + fileName),
							StandardCopyOption.REPLACE_EXISTING);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				chooseBitFile.setText(fileName);
				bitFileSize.setText(String.valueOf(selectedFile.length()));
				SX3Manager.getInstance().addLog("Bit File Path Copied from " + selectedFile.getAbsolutePath().toString()
						+ " to " + Paths.get(configContainerFolder + "/" + fileName) + ".<br>");
				logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
//				} else {
//					SX3Manager.getInstance().addLog(
//							"Invalid Bit File Path Selected : " + selectedFile.getAbsolutePath().toString() + ".<br>");
//				}
			}
		}

	}

	protected void configureBitFileChooser(FileChooser fileChooser) {
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BIT", "*.bit"),
				new FileChooser.ExtensionFilter("All", "*.*"));

	}

	@FXML
	public void selectFifoMasterTab() {
		logHelpSplitpane.setMaxHeight(600.0);
		if (fifoMasterConfigTab.isSelected()) {
			if (enableFPGA.isSelected()) {
				fpgaFamily.setDisable(false);
				browseBitFile.setDisable(false);
			} else {
				fpgaFamily.setDisable(true);
				browseBitFile.setDisable(true);
			}
		}
	}

	@FXML
	private void showChooseBitFile() {
		if (sx3ConfigurationHelp.getFIFO_MASTER_CONFIG() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine().loadContent(sx3ConfigurationHelp.getFIFO_MASTER_CONFIG().getBIT_FILE_PATH());
		}
	}

	@FXML
	private void showEnableFPGAHelp() {
		if (sx3ConfigurationHelp.getFIFO_MASTER_CONFIG() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine().loadContent(
					sx3ConfigurationHelp.getFIFO_MASTER_CONFIG().getFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN());
		}
	}

	@FXML
	private void showFPGAFamilyHelp() {
		if (sx3ConfigurationHelp.getFIFO_MASTER_CONFIG() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine().loadContent(sx3ConfigurationHelp.getFIFO_MASTER_CONFIG().getFPGA_FAMILY());
		}
	}

	@FXML
	private void showFPGASlaveAddressHelp() {
		if (sx3ConfigurationHelp.getFIFO_MASTER_CONFIG() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine().loadContent(sx3ConfigurationHelp.getFIFO_MASTER_CONFIG().getI2C_SLAVE_ADDRESS());
		}
	}

	@FXML
	private void showFPGAI2CFrequency() {
		if (sx3ConfigurationHelp.getFIFO_MASTER_CONFIG() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine().loadContent(sx3ConfigurationHelp.getFIFO_MASTER_CONFIG().getI2C_FREQUENCY());
		}
	}

	/**
	 * ----------------------------------------- Video Source Config
	 * -----------------------------
	 * 
	 * @param endpointNumber
	 **/
	private void createVideoSourceConfig(Tab tab, List<VideoSourceConfig> videoSourceConfigList, int i,
			int endpointNumber) {
		if (performLoadAction == false) {
			VideoSourceConfig videoSourceConfig = loadVideoSourceConfig();
			if (tab.getText().contains("Endpoint 1") && videoSourceConfigList != null
					&& videoSourceConfigList.size() == 1) {
//				VideoSourceConfig videoSourceConfig1 = videoSourceConfigList.get(0);
				videoSourceConfigList.set(0, videoSourceConfig);
			} else {
				videoSourceConfigList.add(videoSourceConfig);
			}
			sx3Configuration.setVIDEO_SOURCE_CONFIG(videoSourceConfigList);
			if (tab.getText().contains("Endpoint 2") && videoSourceConfigList != null
					&& videoSourceConfigList.size() > 1) {
				createVideoSourceConfigUI(tab, sx3Configuration.getVIDEO_SOURCE_CONFIG().get(i), endpointNumber);
			} else if (tab.getText().contains("Endpoint 2") && videoSourceConfigList != null
					&& videoSourceConfigList.size() == 1) {
				createVideoSourceConfigUI(tab, sx3Configuration.getVIDEO_SOURCE_CONFIG().get(i - 1), endpointNumber);
			} else {
				createVideoSourceConfigUI(tab, sx3Configuration.getVIDEO_SOURCE_CONFIG().get(i), endpointNumber);
			}

		} else {
			if (sx3Configuration.getVIDEO_SOURCE_CONFIG().size() == 1) {
				createVideoSourceConfigUI(tab, sx3Configuration.getVIDEO_SOURCE_CONFIG().get(i), endpointNumber);
			} else {
				createVideoSourceConfigUI(tab, sx3Configuration.getVIDEO_SOURCE_CONFIG().get(i), endpointNumber);
			}
		}
	}

	@SuppressWarnings("static-access")
	private void createVideoSourceConfigUI(Tab tab, VideoSourceConfig videoSourceConfig, int endpointNumber) {
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setStyle("-fx-background : #D0D3D4");
		AnchorPane anchorPane = new AnchorPane();
		AnchorPane anchorPane1 = new AnchorPane();
		anchorPane1.setLayoutX(50);
		anchorPane1.setLayoutY(20);
		anchorPane1.setStyle("-fx-background-color: #D0D3D4; -fx-border-insets: 4 1 1 1; -fx-border-color: black;");
		AnchorPane anchorPane3 = new AnchorPane();
		anchorPane3.setLayoutX(50);
		anchorPane3.setLayoutY(250);
		anchorPane3.setStyle("-fx-background-color: #D0D3D4; -fx-border-insets: 4 1 1 1; -fx-border-color: black;");
		Label configDownloadLabel = new Label("Configuration Download");
		configDownloadLabel.setLayoutX(6);
		configDownloadLabel.setLayoutY(-5);
		configDownloadLabel.setStyle("-fx-background-color: inherit;");
		Label videoSrcSetting = new Label("Video Source Settings");
		videoSrcSetting.setLayoutX(6);
		videoSrcSetting.setLayoutY(-5);
		videoSrcSetting.setStyle("-fx-background-color: inherit;");
		GridPane leftAnchorGridPane = new GridPane();
		leftAnchorGridPane.setLayoutX(5);
		leftAnchorGridPane.setLayoutY(20);
		leftAnchorGridPane.setVgap(5.0);
		GridPane leftAnchorGridPane1 = new GridPane();
		leftAnchorGridPane1.setLayoutX(5);
		leftAnchorGridPane1.setLayoutY(20);
		leftAnchorGridPane1.setVgap(5.0);
		anchorPane1.getChildren().add(leftAnchorGridPane);
		anchorPane3.getChildren().addAll(videoSrcSetting, leftAnchorGridPane1);
		anchorPane1.getChildren().add(configDownloadLabel);
		anchorPane.getChildren().addAll(anchorPane1, anchorPane3);
		scrollPane.setContent(anchorPane);
		tab.setContent(scrollPane);
		AnchorPane anchorPane2 = new AnchorPane();
		anchorPane2.setLayoutX(600);
		anchorPane2.setLayoutY(20);
		anchorPane2.setStyle("-fx-background-color: #D0D3D4;");
		anchorPane.getChildren().add(anchorPane2);

		/** Set Endpoint Number parameter **/
		videoSourceConfig.setENDPOINT(endpointNumber);

		/** Enable Video Source Configuration Download **/
		Label enableVideoSourceLabel = new Label("Enable Video/Audio Source Configuration Download : ");
		leftAnchorGridPane.setMargin(enableVideoSourceLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(enableVideoSourceLabel, 0, 0);
		CheckBox enableVideoSourceCheckBox = new CheckBox();
		enableVideoSourceCheckBox.setId("enableVideoSourceConfig");
		enableVideoSourceCheckBox.setTooltip(new Tooltip(tooltipAndErrorProperties
				.getProperty("VIDEO_SOURCE_CONFIG.TOOLTIP_ENABLE_VIDEO_SOURCE_CONFIGURATION")));
		enableVideoSourceCheckBox.getTooltip().setOnShowing(s -> {
			Bounds bounds = enableVideoSourceCheckBox.localToScreen(enableVideoSourceCheckBox.getBoundsInLocal());
			enableVideoSourceCheckBox.getTooltip().setX(bounds.getMaxX());
		});
		if (videoSourceConfig.getENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD().equals("Enable")) {
			enableVideoSourceCheckBox.setSelected(true);
		} else {
			enableVideoSourceCheckBox.setSelected(false);
		}
		enableVideoSourceCheckBox.setOnMouseEntered(new OnMouseClickHandler());
		leftAnchorGridPane.setMargin(enableVideoSourceCheckBox, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(enableVideoSourceCheckBox, 2, 0);

		/** Video Source Type **/
		Label videoSorceTypeLabel = new Label("Video/Audio Source Type : ");
		leftAnchorGridPane.setMargin(videoSorceTypeLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(videoSorceTypeLabel, 0, 1);
		ComboBox<String> videoSorceTypeValue = new ComboBox<>();
		videoSorceTypeValue.setId("videoSorceTypeValue");
		videoSorceTypeValue.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("VIDEO_SOURCE_CONFIG.TOOLTIP_VIDEO_SOURCE_TYPE")));
		videoSorceTypeValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = videoSorceTypeValue.localToScreen(videoSorceTypeValue.getBoundsInLocal());
			videoSorceTypeValue.getTooltip().setX(bounds.getMaxX());
		});
		videoSorceTypeValue.setDisable(true);
		videoSorceTypeValue.getItems().addAll("Image Sensor", "HDMI Source");
		if (videoSourceConfig.getVIDEO_SOURCE_TYPE().equals("HDMI Source")) {
			videoSourceType.put("Endpoint " + endpointNumber, "HDMI Source");
		}
		videoSorceTypeValue.setValue(videoSourceConfig.getVIDEO_SOURCE_TYPE());
		leftAnchorGridPane.setMargin(videoSorceTypeValue, new Insets(0, 5, 0, 0));
		leftAnchorGridPane.add(videoSorceTypeValue, 2, 1);
		videoSorceTypeValue.setOnMouseClicked(new OnMouseClickHandler());

		/** Video Source Sub Type **/
		Label videoSorceSubTypeLabel = new Label("Video/Audio Source Subtype : ");
		leftAnchorGridPane.setMargin(videoSorceSubTypeLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(videoSorceSubTypeLabel, 0, 2);
		ComboBox<String> videoSorceSubTypeValue = new ComboBox<>();
		videoSorceSubTypeValue.setId("videoSorceSubTypeValue");
		videoSorceSubTypeValue.setTooltip(new Tooltip(
				tooltipAndErrorProperties.getProperty("VIDEO_SOURCE_CONFIG.TOOLTIP_VIDEO_SOURCE_SUB_TYPE")));
		videoSorceSubTypeValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = videoSorceSubTypeValue.localToScreen(videoSorceSubTypeValue.getBoundsInLocal());
			videoSorceSubTypeValue.getTooltip().setX(bounds.getMaxX());
		});
		videoSorceSubTypeValue.setDisable(true);
		videoSorceSubTypeValue.getItems().addAll("HDMI RX - ITE6801", "HDMI RX - Generic");
		videoSorceSubTypeValue.setValue(videoSourceConfig.getVIDEO_SOURCE_SUBTYPE());
		if (!videoSorceSubTypeValue.getValue().equals("")
				&& videoSorceSubTypeValue.getValue().equals("HDMI RX - Generic")) {
			createHDMISourceUI(tab, anchorPane2, videoSourceConfig.getHDMI_SOURCE_CONFIGURATION());
		}
		leftAnchorGridPane.setMargin(videoSorceSubTypeValue, new Insets(0, 5, 0, 0));
		leftAnchorGridPane.add(videoSorceSubTypeValue, 2, 2);
		videoSorceSubTypeValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				videoSourceConfig.setVIDEO_SOURCE_SUBTYPE(videoSorceSubTypeValue.getValue());
				SX3Manager.getInstance()
						.addLog("Video/Audio source sub type : " + videoSorceSubTypeValue.getValue() + ".<br>");
				logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

				if (videoSorceSubTypeValue.getValue().equals("HDMI RX - Generic")) {
					LinkedList<HDMISourceConfiguration> hdmiSourceConfigList = new LinkedList<HDMISourceConfiguration>();
					HDMISourceConfiguration hdmiSourceConfig = loadHDMISourceConfig();
					hdmiSourceConfigList.add(hdmiSourceConfig);
					videoSourceConfig.setHDMI_SOURCE_CONFIGURATION(hdmiSourceConfigList);
					createHDMISourceUI(tab, anchorPane2, videoSourceConfig.getHDMI_SOURCE_CONFIGURATION());
				} else {
					anchorPane2.getChildren().clear();
					videoSourceConfig.setHDMI_SOURCE_CONFIGURATION(null);
				}
			}
		});
		videoSorceSubTypeValue.setOnMouseClicked(new OnMouseClickHandler());
		videoSorceTypeValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				videoSourceConfig.setVIDEO_SOURCE_TYPE(videoSorceTypeValue.getValue());
				SX3Manager.getInstance().addLog("Video/Audio source type : " + videoSorceTypeValue.getValue() + ".<br>");
				logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				if (videoSourceConfig.getVIDEO_SOURCE_TYPE().equals("HDMI Source")) {
					videoSourceType.put("Endpoint " + endpointNumber, "HDMI Source");
				} else {
					videoSourceType.put("Endpoint " + endpointNumber, "Image Sensor");
				}
				if (videoSorceTypeValue.getValue().equals("HDMI Source")) {
					videoSorceSubTypeValue.setDisable(false);
				} else {
					videoSorceSubTypeValue.setValue("");
					videoSorceSubTypeValue.setDisable(true);
				}
			}
		});

		/** Choose Video Source Config File **/
		Label videoSourceConfifFilePath = new Label("Video/Audio Source Configuration : ");
		videoSourceConfifFilePath.setLayoutX(5);
		leftAnchorGridPane.setMargin(videoSourceConfifFilePath, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(videoSourceConfifFilePath, 0, 3);

		Button chooseJsonFileButton = new Button();
		chooseJsonFileButton.setId("videoSourceSensorConfig");
		chooseJsonFileButton.setTooltip(new Tooltip("Video/Audio Source Sensor Config"));
		ImageView sensorConfigImage = new ImageView(getClass().getResource("/resources/sensorConfig.png").toString());
		sensorConfigImage.setFitHeight(15);
		sensorConfigImage.setFitWidth(15);
		chooseJsonFileButton.setGraphic(sensorConfigImage);
		leftAnchorGridPane.setMargin(chooseJsonFileButton, new Insets(0, 5, 0, 0));
		leftAnchorGridPane.add(chooseJsonFileButton, 2, 3);

		Label txtFileSizeLabel = new Label("Video/Audio Source Configuration Length : ");
		leftAnchorGridPane.setMargin(txtFileSizeLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(txtFileSizeLabel, 0, 4);
		TextField txtFileSizeValue = new TextField();
		txtFileSizeValue.setText(String.valueOf(videoSourceConfig.getVIDEO_SOURCE_CONFIGURATION_SIZE()));
		leftAnchorGridPane.setMargin(txtFileSizeValue, new Insets(0, 5, 0, 0));
		txtFileSizeValue.setPrefWidth(40.0);
		txtFileSizeValue.setDisable(true);
		leftAnchorGridPane.add(txtFileSizeValue, 2, 4);
		if (videoSourceConfig.getVIDEO_SOURCE_CONFIGURATION_SIZE() != 0) {
			chooseJsonFileButton.setStyle("-fx-background-color:green");
		} else {
			chooseJsonFileButton.setStyle("");
		}

		chooseJsonFileButton.setOnAction(new SensorConfigUI(videoSourceConfig, chooseJsonFileButton, txtFileSizeValue));

		/** I2C Slave address (Video Source) **/
		Label videoSourceI2CSlaveAddressLabel = new Label("I2C Slave address (Video/Audio Source)* : ");
		leftAnchorGridPane1.setMargin(videoSourceI2CSlaveAddressLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane1.add(videoSourceI2CSlaveAddressLabel, 0, 1);
		Label videoSourceI2CSlaveAddressLabel1 = new Label("0x");
		leftAnchorGridPane1.add(videoSourceI2CSlaveAddressLabel1, 1, 1);
		TextField videoSourceI2CSlaveAddressValue = new TextField();
		videoSourceI2CSlaveAddressValue.setAlignment(Pos.CENTER_RIGHT);
		videoSourceI2CSlaveAddressValue.setId("videoSourceI2CSlaveAddressValue");
		videoSourceI2CSlaveAddressValue.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("VIDEO_SOURCE_CONFIG.TOOLTIP_I2C_SLAVE_ADDRESS")));
		videoSourceI2CSlaveAddressValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = videoSourceI2CSlaveAddressValue
					.localToScreen(videoSourceI2CSlaveAddressValue.getBoundsInLocal());
			videoSourceI2CSlaveAddressValue.getTooltip().setX(bounds.getMaxX());
		});
		videoSourceI2CSlaveAddressValue.setMaxWidth(40);
		videoSourceI2CSlaveAddressValue.setText(!videoSourceConfig.getI2C_SLAVE_ADDRESS().isEmpty()
				? videoSourceConfig.getI2C_SLAVE_ADDRESS().substring(2)
				: "");
		leftAnchorGridPane1.setMargin(videoSourceI2CSlaveAddressValue, new Insets(0, 5, 0, 0));
		leftAnchorGridPane1.add(videoSourceI2CSlaveAddressValue, 2, 1);
		VideoSourceConfigValidation.setupValidationForHexTextField(videoSourceConfigTab, tab,
				videoSourceConfigTabErrorList, performLoadAction, videoSourceI2CSlaveAddressValue,
				tooltipAndErrorProperties.getProperty("INVALID_HEX_ERROR_MESSAGE"), videoSourceConfig, 255,
				"Slave_Address");
		videoSourceI2CSlaveAddressValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				SX3Manager.getInstance()
						.addLog("I2C Slave Address : " + videoSourceI2CSlaveAddressValue.getText() + ".<br>");
				logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

			}
		});
		videoSourceI2CSlaveAddressValue.setOnMouseClicked(new OnMouseClickHandler());

		/** I2C Slave Data size **/
		Label videoSourceI2CSlaveDataSizeLabel = new Label("I2C Slave Data size (Bytes) : ");
		leftAnchorGridPane1.setMargin(videoSourceI2CSlaveDataSizeLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane1.add(videoSourceI2CSlaveDataSizeLabel, 0, 2);
		TextField videoSourceI2CSlaveDataSizeValue = new TextField();
		videoSourceI2CSlaveDataSizeValue.setId("videoSourceI2CSlaveDataSize");
		videoSourceI2CSlaveDataSizeValue.setAlignment(Pos.CENTER_RIGHT);
		videoSourceI2CSlaveDataSizeValue.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("VIDEO_SOURCE_CONFIG.TOOLTIP_I2C_SLAVE_DATA_SIZE")));
		videoSourceI2CSlaveDataSizeValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = videoSourceI2CSlaveDataSizeValue
					.localToScreen(videoSourceI2CSlaveDataSizeValue.getBoundsInLocal());
			videoSourceI2CSlaveDataSizeValue.getTooltip().setX(bounds.getMaxX());
		});
		videoSourceI2CSlaveDataSizeValue.setMaxWidth(40);
		videoSourceI2CSlaveDataSizeValue.setText(String.valueOf(videoSourceConfig.getI2C_SLAVE_DATA_SIZE()));
		leftAnchorGridPane1.setMargin(videoSourceI2CSlaveDataSizeValue, new Insets(0, 5, 0, 0));
		leftAnchorGridPane1.add(videoSourceI2CSlaveDataSizeValue, 2, 2);
		VideoSourceConfigValidation.setupNumericFieldValidation(videoSourceI2CSlaveDataSizeValue,
				tooltipAndErrorProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), videoSourceConfig, 2,
				"DataSize");
		videoSourceI2CSlaveDataSizeValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (videoSourceI2CSlaveDataSizeValue.getText().equals("")
						|| Integer.parseInt(videoSourceI2CSlaveDataSizeValue.getText()) == 0) {
					videoSourceI2CSlaveDataSizeValue.setText("1");
				}
				SX3Manager.getInstance().addLog(
						"I2C Slave Data size (Bytes) : " + videoSourceI2CSlaveDataSizeValue.getText() + ".<br>");
				logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

			}
		});
		videoSourceI2CSlaveDataSizeValue.setOnMouseClicked(new OnMouseClickHandler());

		/** I2C Slave register size **/
		Label videoSourceI2CSlaveregisterSizeLabel = new Label("I2C Slave register address size (Bytes) : ");
		leftAnchorGridPane1.setMargin(videoSourceI2CSlaveregisterSizeLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane1.add(videoSourceI2CSlaveregisterSizeLabel, 0, 3);
		TextField videoSourceI2CSlaveregisterSizeValue = new TextField();
		videoSourceI2CSlaveregisterSizeValue.setId("videoSourceI2CSlaveRegisterSize");
		videoSourceI2CSlaveregisterSizeValue.setAlignment(Pos.CENTER_RIGHT);
		videoSourceI2CSlaveregisterSizeValue.setTooltip(new Tooltip(
				tooltipAndErrorProperties.getProperty("VIDEO_SOURCE_CONFIG.TOOLTIP_I2C_SLAVE_REGISTER_SIZE")));
		videoSourceI2CSlaveregisterSizeValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = videoSourceI2CSlaveregisterSizeValue
					.localToScreen(videoSourceI2CSlaveregisterSizeValue.getBoundsInLocal());
			videoSourceI2CSlaveregisterSizeValue.getTooltip().setX(bounds.getMaxX());
		});
		VideoSourceConfigValidation.setupNumericFieldValidation(videoSourceI2CSlaveregisterSizeValue,
				tooltipAndErrorProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), videoSourceConfig, 2,
				"RegisterSize");
		videoSourceI2CSlaveregisterSizeValue.setMaxWidth(40);
		leftAnchorGridPane1.setMargin(videoSourceI2CSlaveregisterSizeValue, new Insets(0, 5, 0, 0));
		leftAnchorGridPane1.add(videoSourceI2CSlaveregisterSizeValue, 2, 3);
		videoSourceI2CSlaveregisterSizeValue.setText(String.valueOf(videoSourceConfig.getI2C_SLAVE_REGISTER_SIZE()));
		videoSourceI2CSlaveregisterSizeValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (videoSourceI2CSlaveregisterSizeValue.getText().equals("")
						|| Integer.parseInt(videoSourceI2CSlaveregisterSizeValue.getText()) == 0) {
					videoSourceI2CSlaveregisterSizeValue.setText("1");
				}
				SX3Manager.getInstance().addLog(
						"I2C Slave Address size (Bytes) : " + videoSourceI2CSlaveregisterSizeValue.getText() + ".<br>");
				logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

			}
		});
		videoSourceI2CSlaveregisterSizeValue.setOnMouseClicked(new OnMouseClickHandler());

		if (enableVideoSourceCheckBox.isSelected()) {
			videoSorceTypeValue.setDisable(false);
			chooseJsonFileButton.setDisable(false);
			videoSourceConfig.setENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD("Enable");
			if (videoSorceTypeValue.getValue().equals("HDMI Source")) {
				videoSorceSubTypeValue.setDisable(false);
			}
		} else {
			videoSorceTypeValue.setDisable(true);
			chooseJsonFileButton.setDisable(true);
			videoSourceConfig.setENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD("Disable");
		}

		enableVideoSourceCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (enableVideoSourceCheckBox.isSelected()) {
					videoSorceTypeValue.setDisable(false);
					videoSourceConfig.setVIDEO_SOURCE_TYPE(videoSorceTypeValue.getValue());
					videoSourceConfifFilePath.setDisable(false);
					chooseJsonFileButton.setDisable(false);
					videoSourceConfig.setENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD("Enable");
					SX3Manager.getInstance().addLog("Enable Video Source Configuration Download : " + true + ".<br>");
					logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

				} else {
					videoSorceTypeValue.setDisable(true);
					videoSorceTypeValue.setValue("Image Sensor");
					if (videoSourceConfig.getVIDEO_SOURCE_CONFIG() != null) {
						chooseJsonFileButton.setStyle("");
						videoSourceConfig.getVIDEO_SOURCE_CONFIG().clear();
						txtFileSizeValue.setText(String.valueOf(0));
					}
					chooseJsonFileButton.setDisable(true);
					videoSourceConfig.setENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD("Disable");
					// txtFileSizeValue.setText("");
					SX3Manager.getInstance().addLog("Enable Video Source Configuration Download : " + false + ".<br>");
					logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

				}
			}

		});
	}

	protected void configureTextFileChooser(FileChooser fileChooser) {
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt", "*.txt"));

	}

	private void createHDMISourceUI(Tab subTab, AnchorPane anchorPane1,
			LinkedList<HDMISourceConfiguration> hdmiConfigList) {

		Collections.sort(hdmiConfigList, HDMISourceConfiguration.serialNoComparator);

		/** Add Row Button **/
		Button addBtn = new Button();
		addBtn.setId("hdmiConfigAddBtn");
		addBtn.setDisable(true);
		addBtn.setLayoutY(10);
		addBtn.setLayoutX(660);
		addBtn.setTooltip(new Tooltip("Add Row"));
		addBtn.getTooltip().setOnShowing(s -> {
			Bounds bounds = addBtn.localToScreen(addBtn.getBoundsInLocal());
			addBtn.getTooltip().setX(bounds.getMaxX());
		});
		ImageView addImage = new ImageView(getClass().getResource("/resources/add.png").toString());
		addImage.setFitHeight(15);
		addImage.setFitWidth(15);
		addBtn.setGraphic(addImage);

		/** Edit Row Button **/
		Button editBtn = new Button();
		editBtn.setId("hdmiConfigSaveBtn");
		editBtn.setDisable(true);
		editBtn.setLayoutY(40);
		editBtn.setLayoutX(660);
		editBtn.setTooltip(new Tooltip("Save Row"));
		editBtn.getTooltip().setOnShowing(s -> {
			Bounds bounds = editBtn.localToScreen(editBtn.getBoundsInLocal());
			editBtn.getTooltip().setX(bounds.getMaxX());
		});
		ImageView editImage = new ImageView(getClass().getResource("/resources/saveRow.png").toString());
		editImage.setFitHeight(15);
		editImage.setFitWidth(15);
		editBtn.setGraphic(editImage);

		/** Remove Row Button **/
		Button removeBtn = new Button();
		removeBtn.setLayoutY(76);
		removeBtn.setDisable(true);
		removeBtn.setLayoutX(660);
		removeBtn.setTooltip(new Tooltip("Remove Row"));
		removeBtn.getTooltip().setOnShowing(s -> {
			Bounds bounds = removeBtn.localToScreen(removeBtn.getBoundsInLocal());
			removeBtn.getTooltip().setX(bounds.getMaxX());
		});
		ImageView removeImage = new ImageView(getClass().getResource("/resources/deleteRow.png").toString());
		removeImage.setFitHeight(15);
		removeImage.setFitWidth(15);
		removeBtn.setGraphic(removeImage);
		anchorPane1.getChildren().addAll(addBtn, removeBtn, editBtn);
		TableView<HDMISourceConfigurationTable> hdmiSourceConfigurationTable = new TableView<>();
		ObservableList<HDMISourceConfigurationTable> hdmiSourceConfigurationData = FXCollections.observableArrayList();
		VideoSourceConfigController.createHDMITable(anchorPane1, hdmiSourceConfigurationTable);
		for (int i = 0; i < hdmiConfigList.size(); i++) {
			VideoSourceConfigController.createAndAddRowInHdmiSensorConfigTable(videoSourceConfigTab, subTab,
					videoSourceConfigTabErrorList, performLoadAction, addBtn, editBtn, hdmiConfigList.get(i),
					hdmiSourceConfigurationData, hdmiSourceConfigurationTable, i + 1, tooltipAndErrorProperties, s1);
		}

		/** Add Row in Table **/
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				HDMISourceConfiguration hdmiSourceConfigJson = loadHDMISourceConfig();
				hdmiConfigList.add(hdmiSourceConfigJson);
				VideoSourceConfigController.createAndAddRowInHdmiSensorConfigTable(videoSourceConfigTab, subTab,
						videoSourceConfigTabErrorList, performLoadAction, addBtn, editBtn, hdmiSourceConfigJson,
						hdmiSourceConfigurationData, hdmiSourceConfigurationTable, hdmiConfigList.size(),
						tooltipAndErrorProperties, s1);
				addBtn.setDisable(true);
			}
		});

		editBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				HDMISourceConfigurationTable editedRow = hdmiSourceConfigurationTable.getSelectionModel()
						.getSelectedItem();
				if (editedRow != null && !editedRow.getCompareValue().getText().equals("")
						&& !editedRow.getHDMISourceRegisterAddress().getText().equals("")
						&& !editedRow.getMaskValue().getText().equals("")) {
					editedRow.getHDMISourceRegisterAddress().setDisable(true);
					editedRow.getCompareValue().setDisable(true);
					editedRow.getMaskValue().setDisable(true);
					editedRow.getButton().setDisable(true);
					if (!hdmiSourceConfigurationData.get(hdmiSourceConfigurationData.size() - 1)
							.getHDMISourceRegisterAddress().getText().equals("")
							&& !hdmiSourceConfigurationData.get(hdmiSourceConfigurationData.size() - 1).getMaskValue()
									.getText().equals("")
							&& !hdmiSourceConfigurationData.get(hdmiSourceConfigurationData.size() - 1)
									.getCompareValue().getText().equals("")) {
						addBtn.setDisable(false);
					}
					editBtn.setDisable(true);
				} else {
					showAlertMsg("Fill all columns");
				}
			}
		});

		removeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				HDMISourceConfigurationTable editedRow = hdmiSourceConfigurationTable.getSelectionModel()
						.getSelectedItem();
				hdmiSourceConfigurationData.remove(editedRow);
				for (int i = 0; i < hdmiConfigList.size(); i++) {
					if (hdmiConfigList.get(i).getS_NO() == Integer.parseInt(editedRow.getSno().getText())) {
						hdmiConfigList.remove(i);
					}
				}
				for (int i = 0; i < hdmiSourceConfigurationData.size(); i++) {
					Label sno = new Label(String.valueOf(i + 1));
					hdmiSourceConfigurationData.get(i).setSno(sno);
					hdmiConfigList.get(i).setS_NO(Integer.parseInt(sno.getText()));
//					if(hdmiSourceConfigurationData.get(i).getHDMISourceRegisterAddress().getText()
//							.equals(hdmiConfigList.get(i).getHDMI_SOURCE_REGISTER_ADDRESS())) {
//						
//					}
				}
				removeBtn.setDisable(true);
			}

		});

		hdmiSourceConfigurationTable.setStyle("-fx-cell-alignment: center;");
		hdmiSourceConfigurationTable.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
				addBtn.setDisable(true);
				HDMISourceConfigurationTable selectedRow = hdmiSourceConfigurationTable.getSelectionModel()
						.getSelectedItem();
				for (HDMISourceConfigurationTable hdmiConfiguration : hdmiSourceConfigurationData) {
					if (selectedRow.getSno() == hdmiConfiguration.getSno()) {
						hdmiConfiguration.getHDMISourceRegisterAddress().setDisable(false);
						hdmiConfiguration.getCompareValue().setDisable(false);
						hdmiConfiguration.getMaskValue().setDisable(false);
						hdmiConfiguration.getButton().setDisable(false);
						editBtn.setDisable(false);
					} else {
						hdmiConfiguration.getCompareValue().setDisable(true);
						hdmiConfiguration.getHDMISourceRegisterAddress().setDisable(true);
						hdmiConfiguration.getMaskValue().setDisable(true);
					}
				}
			} else {
				if (hdmiSourceConfigurationData.size() > 1) {
					removeBtn.setDisable(false);
				}
			}
		});

	}

	@FXML
	public void selectvideoSourceConfigTab() {
		checkFieldEditOrNot = true;
		logHelpSplitpane.setMaxHeight(500.0);
	}

	/**
	 * ----------------------------------------------- Log Data
	 * --------------------------------------------------------------
	 **/

	/** Log Serial Number increment **/
	@FXML
	private void logSerialNumberIncrement() {
		checkFieldEditOrNot = true;
		SX3Manager.getInstance().addLog("Serial Number Increment : " + serialNumberIncrement.getValue() + ".<br>");
		logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

	}

	/** Log Power Configuration **/
	@FXML
	private void logPowerConfiguration() {
		checkFieldEditOrNot = true;
		SX3Manager.getInstance().addLog("Power Configuration : " + powerConfiguration.getValue() + ".<br>");
		logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

	}

	/** Log FIFO BusWidth **/
	@FXML
	public void logFifoBusWidth() {
		checkFieldEditOrNot = true;
		SX3Manager.getInstance().addLog("FIFO Bus Width : " + fifoBusWidth.getValue() + ".<br>");
		logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

	}

	/** Log Interface Type **/
	@FXML
	public void logInterfaceType() {
		ChangeListener<String> changeListener = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && newValue.equals("Image Sensor Parallel Interface")
						&& !Endpoint_2.getValue().equals("Not Enabled")) {
					Dialog<ButtonType> dialog = new Dialog<>();
					dialog.setTitle("Warning");
					dialog.setResizable(true);
					dialog.setContentText("Data entered in Endpoint 2 - " + Endpoint_2.getValue()
							+ " tab will be lost. Do you want continue?");
					ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
					dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
					ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
					dialog.getDialogPane().getButtonTypes().add(buttonCancel);
					Optional<ButtonType> showAndWait = dialog.showAndWait();
					if (showAndWait.get() == buttonTypeOk) {
						checkFieldEditOrNot = true;
						Endpoint_2.getItems().remove("UAC");
						Endpoint_2.setValue("Not Enabled");
						interFaceType.setValue(newValue);
						if (!imageSensorChangeChange && newValue != null && newValue.equals("Image Sensor Parallel Interface")) {
							fifoBusWidth.getItems().clear();
							fifoBusWidth.getItems().addAll("8");
							fifoBusWidth.setValue("8");
							slaveChange = false;
							imageSensorChangeChange = true;
							SX3Manager.getInstance()
									.addLog("FIFO Interface Type : " + interFaceType.getValue() + ".<br>");
							logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
						}
					}
					if (showAndWait.get() == buttonCancel) {
						interfaceChange = true;
						fifoBusWidth.getItems().clear();
						fifoBusWidth.getItems().addAll("8", "16", "24", "32");
						fifoBusWidth.setValue("32");
						interFaceType.setValue(oldValue);
						dialog.close();
					}
				} else {
					if (!interfaceChange) {
						checkFieldEditOrNot = true;
						interFaceType.setValue(newValue);
						if (!slaveChange && newValue != null && newValue.equals("Slave FIFO Interface")) {
							fifoBusWidth.getItems().clear();
							fifoBusWidth.getItems().addAll("8", "16", "24", "32");
							fifoBusWidth.setValue("32");
							;
							Endpoint_2.getItems().add("UAC");
							slaveChange = true;
							imageSensorChangeChange = false;
							SX3Manager.getInstance()
									.addLog("FIFO Interface Type : " + interFaceType.getValue() + ".<br>");
							logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
						} else if (!imageSensorChangeChange && newValue != null && newValue.equals("Image Sensor Parallel Interface")) {
							fifoBusWidth.getItems().clear();
							fifoBusWidth.getItems().addAll("8");
							fifoBusWidth.setValue("8");
							Endpoint_2.getItems().remove("UAC");
							Endpoint_2.setValue("Not Enabled");
							slaveChange = false;
							imageSensorChangeChange = true;
							SX3Manager.getInstance()
									.addLog("FIFO Interface Type : " + interFaceType.getValue() + ".<br>");
							logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
						}
					}
				}
			}
		};
		interFaceType.getSelectionModel().selectedItemProperty().addListener(changeListener);
	}

	/** Log GPIO 1 **/
	@FXML
	public void logGpio1() {
		if (gpio1.getValue() != null) {
			checkFieldEditOrNot = true;
			SX3Manager.getInstance().addLog("GPIO 0 :" + gpio1.getValue() + ".<br>");
			logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
		}
	}

	@FXML
	public void loadGpio1List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_1_SETTING());
		}
		ObservableList<String> items = gpio1.getItems();
		if (deviceName.getText().equals("SX3 UVC (CYUSB3017)")) {

			if (!gpio2.getValue().equals("Not Used")) {
				items.remove(gpio2.getValue());
			}

			if (!gpio3.getValue().equals("Not Used")) {
				items.remove(gpio3.getValue());
			}
			if (!gpio4.getValue().equals("Not Used")) {
				items.remove(gpio4.getValue());
			}
			if (!gpio5.getValue().equals("Not Used")) {
				items.remove(gpio5.getValue());
			}
			if (!gpio6.getValue().equals("Not Used")) {
				items.remove(gpio6.getValue());
			}
			if (!gpio7.getValue().equals("Not Used")) {
				items.remove(gpio7.getValue());
			}
			for (int j = 0; j < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; j++) {
				if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals("Not Used")
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio1.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio2.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio3.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio4.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio5.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio6.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio7.getValue())
						&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j])) {
					gpio1.getItems().add(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j]);
				}
			}
		} else {
			for (int i = 0; i < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA.length; i++) {
				if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i].equals("Not Used")
						&& (gpio2.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i])
								|| gpio3.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i]))) {
					gpio1.getItems().remove(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i]);
				} else if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i].equals("Not Used")
						&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i])) {
					gpio1.getItems().add(i, DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i]);
				}
			}
		}
	}

	/** Log GPIO 2 **/
	@FXML
	public void logGpio2() {
		if (gpio2.getValue() != null) {
			checkFieldEditOrNot = true;
			SX3Manager.getInstance().addLog("GPIO 1 :" + gpio2.getValue() + ".<br>");
			logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

		}
	}

	@FXML
	public void loadGpio2List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_2_SETTING());
		}
		ObservableList<String> items = gpio2.getItems();
		if (deviceName.getText().equals("SX3 UVC (CYUSB3017)")) {
			if (!gpio1.getValue().equals("Not Used")) {
				items.remove(gpio1.getValue());
			}
			if (!gpio3.getValue().equals("Not Used")) {
				items.remove(gpio3.getValue());
			}
			if (!gpio4.getValue().equals("Not Used")) {
				items.remove(gpio4.getValue());
			}
			if (!gpio5.getValue().equals("Not Used")) {
				items.remove(gpio5.getValue());
			}
			if (!gpio6.getValue().equals("Not Used")) {
				items.remove(gpio6.getValue());
			}
			if (!gpio7.getValue().equals("Not Used")) {
				items.remove(gpio7.getValue());
			}
			for (int j = 0; j < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; j++) {
				if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals("Not Used")
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio1.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio3.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio4.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio5.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio6.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio7.getValue())
						&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j])) {
					gpio2.getItems().add(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j]);
				}
			}
		} else {
			for (int i = 0; i < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA.length; i++) {
				if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i].equals("Not Used")
						&& (gpio1.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i])
								|| gpio3.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i]))) {
					gpio2.getItems().remove(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i]);
				} else if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i].equals("Not Used")
						&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i])) {
					gpio2.getItems().add(i, DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i]);
				}
			}
		}
	}

	/** Log GPIO 3 **/
	@FXML
	public void logGpio3() {
		if (gpio3.getValue() != null) {
			checkFieldEditOrNot = true;
			SX3Manager.getInstance().addLog("GPIO 2 :" + gpio3.getValue() + ".<br>");
			logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

			logDetails1.getEngine().setJavaScriptEnabled(true);
		}
	}

	@FXML
	public void loadGpio3List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_3_SETTING());
		}
		ObservableList<String> items = gpio3.getItems();
		if (deviceName.getText().equals("SX3 UVC (CYUSB3017)")) {
			if (!gpio1.getValue().equals("Not Used") && !gpio1.getValue().equals("LED Brightness Control")) {
				items.remove(gpio1.getValue());
			}
			if (!gpio2.getValue().equals("Not Used") && !gpio2.getValue().equals("LED Brightness Control")) {
				items.remove(gpio2.getValue());
			}
			if (!gpio4.getValue().equals("Not Used")) {
				items.remove(gpio4.getValue());
			}
			if (!gpio5.getValue().equals("Not Used")) {
				items.remove(gpio5.getValue());
			}
			if (!gpio6.getValue().equals("Not Used")) {
				items.remove(gpio6.getValue());
			}
			if (!gpio7.getValue().equals("Not Used")) {
				items.remove(gpio7.getValue());
			}
			for (int j = 0; j < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; j++) {
				if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals("Not Used")
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals("LED Brightness Control")
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio2.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio1.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio4.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio5.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio6.getValue())
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio7.getValue())
						&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j])) {
					gpio3.getItems().add(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j]);
				}
			}
		} else {
			for (int i = 0; i < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA.length; i++) {
				if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i].equals("Not Used")
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i].equals("LED Brightness Control")
						&& (gpio1.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i])
								|| gpio2.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i]))) {
					gpio3.getItems().remove(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i]);
				} else if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i].equals("Not Used")
						&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i])
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i].equals("LED Brightness Control")) {
					gpio3.getItems().add(i, DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[i]);
				}
			}
		}
	}

	/** Log GPIO 4 **/
	@FXML
	public void logGpio4() {
		if (gpio4.getValue() != null) {
			checkFieldEditOrNot = true;
			SX3Manager.getInstance().addLog("GPIO 3 :" + gpio4.getValue() + ".<br>");
			logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

			logDetails1.getEngine().executeScript("window.scrollTo(0, document.body.scrollHeight);");
		}
	}

	@FXML
	public void loadGpio4List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_4_SETTING());
		}
		ObservableList<String> items = gpio4.getItems();
		if (!gpio1.getValue().equals("Not Used") && !gpio1.getValue().equals("LED Brightness Control")) {
			items.remove(gpio1.getValue());
		}
		if (!gpio2.getValue().equals("Not Used") && !gpio2.getValue().equals("LED Brightness Control")) {
			items.remove(gpio2.getValue());
		}
		if (!gpio3.getValue().equals("Not Used")) {
			items.remove(gpio3.getValue());
		}
		if (!gpio5.getValue().equals("Not Used")) {
			items.remove(gpio5.getValue());
		}
		if (!gpio6.getValue().equals("Not Used")) {
			items.remove(gpio6.getValue());
		}
		if (!gpio7.getValue().equals("Not Used")) {
			items.remove(gpio7.getValue());
		}
		for (int j = 0; j < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; j++) {
			if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals("Not Used")
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals("LED Brightness Control")
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio1.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio2.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio3.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio5.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio6.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio7.getValue())
					&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j])) {
				gpio4.getItems().add(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j]);
			}
		}
	}

	/** Log GPIO 5 **/
	@FXML
	public void logGpio5() {
		if (gpio5.getValue() != null) {
			checkFieldEditOrNot = true;
			SX3Manager.getInstance().addLog("GPIO 4 :" + gpio5.getValue() + ".<br>");
			logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

			logDetails1.getEngine().executeScript("window.scrollTo(0, document.body.scrollHeight);");
		}
	}

	@FXML
	public void loadGpio5List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_5_SETTING());
		}
		ObservableList<String> items = gpio5.getItems();
		if (!gpio1.getValue().equals("Not Used") && !gpio1.getValue().equals("LED Brightness Control")) {
			items.remove(gpio1.getValue());
		}
		if (!gpio2.getValue().equals("Not Used") && !gpio2.getValue().equals("LED Brightness Control")) {
			items.remove(gpio2.getValue());
		}
		if (!gpio3.getValue().equals("Not Used")) {
			items.remove(gpio3.getValue());
		}
		if (!gpio4.getValue().equals("Not Used")) {
			items.remove(gpio4.getValue());
		}
		if (!gpio6.getValue().equals("Not Used")) {
			items.remove(gpio6.getValue());
		}
		if (!gpio7.getValue().equals("Not Used")) {
			items.remove(gpio7.getValue());
		}
		for (int j = 0; j < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; j++) {
			if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals("Not Used")
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals("LED Brightness Control")
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio1.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio2.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio3.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio4.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio6.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio7.getValue())
					&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j])) {
				gpio5.getItems().add(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j]);
			}
		}
	}

	/** Log GPIO 6 **/
	@FXML
	public void logGpio6() {
		if (gpio6.getValue() != null) {
			checkFieldEditOrNot = true;
			SX3Manager.getInstance().addLog("GPIO 5 :" + gpio6.getValue() + ".<br>");
			logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

			logDetails1.getEngine().executeScript("window.scrollTo(0, document.body.scrollHeight);");
		}
	}

	@FXML
	public void loadGpio6List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_6_SETTING());
		}
		ObservableList<String> items = gpio6.getItems();
		if (!gpio1.getValue().equals("Not Used") && !gpio1.getValue().equals("LED Brightness Control")) {
			items.remove(gpio1.getValue());
		}
		if (!gpio2.getValue().equals("Not Used") && !gpio2.getValue().equals("LED Brightness Control")) {
			items.remove(gpio2.getValue());
		}
		if (!gpio3.getValue().equals("Not Used")) {
			items.remove(gpio3.getValue());
		}
		if (!gpio4.getValue().equals("Not Used")) {
			items.remove(gpio4.getValue());
		}
		if (!gpio5.getValue().equals("Not Used")) {
			items.remove(gpio5.getValue());
		}
		if (!gpio7.getValue().equals("Not Used")) {
			items.remove(gpio7.getValue());
		}
		for (int j = 0; j < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; j++) {
			if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals("Not Used")
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals("LED Brightness Control")
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio1.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio2.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio3.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio4.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio5.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio7.getValue())
					&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j])) {
				gpio6.getItems().add(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j]);
			}
		}
	}

	/** Log GPIO 7 **/
	@FXML
	public void logGpio7() {
		if (gpio7.getValue() != null) {
			checkFieldEditOrNot = true;
			SX3Manager.getInstance().addLog("GPIO 6 :" + gpio7.getValue() + ".<br>");
			logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

		}
	}

	@FXML
	public void loadGpio7List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_7_SETTING());
		}
		ObservableList<String> items = gpio7.getItems();
		if (!gpio1.getValue().equals("Not Used") && !gpio1.getValue().equals("LED Brightness Control")) {
			items.remove(gpio1.getValue());
		}
		if (!gpio2.getValue().equals("Not Used") && !gpio2.getValue().equals("LED Brightness Control")) {
			items.remove(gpio2.getValue());
		}
		if (!gpio3.getValue().equals("Not Used")) {
			items.remove(gpio3.getValue());
		}
		if (!gpio4.getValue().equals("Not Used")) {
			items.remove(gpio4.getValue());
		}
		if (!gpio5.getValue().equals("Not Used")) {
			items.remove(gpio5.getValue());
		}
		if (!gpio6.getValue().equals("Not Used")) {
			items.remove(gpio6.getValue());
		}
		for (int j = 0; j < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; j++) {
			if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals("Not Used")
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals("LED Brightness Control")
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio1.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio2.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio3.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio4.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio5.getValue())
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j].equals(gpio6.getValue())
					&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j])) {
				gpio7.getItems().add(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[j]);
			}
		}
	}

	@FXML
	public void showDeviceSettingFirmwareHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine().loadContent(
					sx3ConfigurationHelp.getDEVICE_SETTINGS().getAUXILLIARY_INTERFACE().getFIRMWARE_UPDATE_HID());
		}
	}

	@FXML
	public void showDeviceSettingI2CFrequencyHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine().loadContent(
					sx3ConfigurationHelp.getDEVICE_SETTINGS().getAUXILLIARY_INTERFACE().getI2C_FREQUENCY());
		}
	}

	/** Log Enable Disable Debug Level **/
	@FXML
	public void logDebugLevel() {
		checkFieldEditOrNot = true;
		SX3Manager.getInstance().addLog("Debug Level : " + debugValue.getValue() + ".<br>");
		logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

		logDetails1.getEngine().executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	@FXML
	public void logUVCVersion() {
		if (uvcVersion.getValue() != null && !uvcVersion.getValue().equals("")) {
			checkFieldEditOrNot = true;
			SX3Manager.getInstance().addLog("UVC Version : " + uvcVersion.getValue() + ".<br>");
			logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
		}
	}

	@FXML
	public void logUVCHeaderAddition() {
		if (uvcHeaderAddition.getValue() != null && !uvcHeaderAddition.getValue().equals("")) {
			checkFieldEditOrNot = true;
			SX3Manager.getInstance().addLog("UVC Header Addition : " + uvcHeaderAddition.getValue() + ".<br>");
			logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
		}
	}

	/**
	 * ----------------------------------------------- Show Help Content
	 * ------------------------------------------------------
	 **/

	@FXML
	public void showVendorIdHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getVENDOR_ID());
		}
	}

	@FXML
	public void showProductIdHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getPRODUCT_ID());
		}
	}

	@FXML
	void showManufactureHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getMANUFACTURER_STRING());
		}
	}

	@FXML
	public void showProductStringHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getPRODUCT_STRING());
		}
	}

	@FXML
	public void showSerialNoIncrementHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine().loadContent(
					sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getAUTO_INCREMENT_SERIAL_NUMBER());
		}
	}

	@FXML
	public void showAutoGenerateSerialNoHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine().loadContent(
					sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getAUTO_GENERATE_SERIAL_NUMBER());
		}
	}

	@FXML
	public void showSerialNoHelp() {
		if (!serialNumber.getText().equals("")) {
			String substring = serialNumber.getText().substring(serialNumber.getText().length() - 1);
			if (!SX3CommonUIValidations.isValidNumeric(substring)) {
				numericValidator
						.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
				numericValidator.setAutoHide(false);
				numericValidator.getItems().clear();
				numericValidator.getItems().add(new MenuItem("Last character should be numeric."));
				numericValidator.show(serialNumber, Side.RIGHT, 2, 0);
			}
		}
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getSERIALNUMBER_STRING());
		}
	}

	@FXML
	public void hideSerialNumberRichText() {
		numericValidator.hide();
	}

	@FXML
	public void clearSerialNumberHelpContent() {
		numericValidator.hide();
	}

	@FXML
	public void showEnableRemoteWakeupHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getREMOTE_WAKEUP_ENABLE());
		}
	}

	@FXML
	public void showPowerConfigurationHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getPOWER_CONFIGURATION());
		}
	}

	@FXML
	public void showFifoBusWidthHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			if (deviceName.getText().equals("SX3 Data-16 bit(CYUSB3015)")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(
						sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getFIFO_BUS_WIDTH() + "16");
			} else {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(
						sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getFIFO_BUS_WIDTH() + "32");
			}
		}
	}

	@FXML
	public void showFifoClockFrequencyHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getFIFO_CLOCK());
		}
	}

	@FXML
	public void showDebugEnableHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getDEBUG_LEVEL().getDEBUG_ENABLE());
		}
	}

	@FXML
	public void showDebugLevelHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getDEBUG_LEVEL().getDEBUG_LEVEL());
		}
	}

	@FXML
	public void showNoOfEndpointHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getNUM_ENDPOINTS());
		}
	}

	@FXML
	public void showEndpoinOnetHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT1_TYPE());
		}
	}

	@FXML
	public void showEndpoinTwoHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT2_TYPE());
		}
	}

	@FXML
	public void showUVCVersionHelpContent() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getUVC_VERSION());
		}
	}

	@FXML
	public void showUVCHeaderAdditionHelpContent() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getUVC_HEADER_ADDITION());
		}
	}

	/**
	 * -------------------------------- SX3 Slave FIFO Settings
	 * ---------------------------------
	 **/

	@SuppressWarnings("static-access")
	private Tab createSlaveFIFOSettingsUI(SlaveFIFOSettings slaveFIFOSettings2, Tab tab) {
		AnchorPane anchorPane = new AnchorPane();
		anchorPane.setLayoutX(50);
		anchorPane.setLayoutY(20);
		GridPane leftAnchorGridPane = new GridPane();
		leftAnchorGridPane.setLayoutX(3);
		leftAnchorGridPane.setLayoutY(20);
		leftAnchorGridPane.setVgap(5.0);
		if (tab.getText().contains("Endpoint 1")) {
			slaveFIFOSettings2.setENDPOINT(1);
		} else {
			slaveFIFOSettings2.setENDPOINT(2);
		}

		/** Brust Length **/
		Label end1BurstLengthLabel = new Label("Burst Length : ");
		leftAnchorGridPane.setMargin(end1BurstLengthLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(end1BurstLengthLabel, 0, 1);
		TextField end1BurstLengthValue = new TextField();
		end1BurstLengthValue.setId("slaveFifoSettingsBrustLength");
		end1BurstLengthValue.setAlignment(Pos.CENTER_RIGHT);
		end1BurstLengthValue.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("SLAVE_FIFO_SETTINGS.TOOLTIP_BURST_LENGTH")));
		end1BurstLengthValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = end1BurstLengthValue.localToScreen(end1BurstLengthValue.getBoundsInLocal());
			end1BurstLengthValue.getTooltip().setX(bounds.getMaxX());
		});
		end1BurstLengthValue.setText(String.valueOf(slaveFIFOSettings2.getBURST_LENGTH()));
		end1BurstLengthValue.setMaxWidth(40);
		leftAnchorGridPane.add(end1BurstLengthValue, 1, 1);
		SlaveFifoSettingValidation.setupslaveFifoSettingsValidationForNumeric(slaveFifoSettingsTab, tab,
				end1BurstLengthValue, endpointSettingsTabErrorList, performLoadAction,
				tooltipAndErrorProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), 16, "Burst Length");
		end1BurstLengthValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (end1BurstLengthValue.getText().equals("") || end1BurstLengthValue.getText().equals("0")) {
					end1BurstLengthValue.setText("1");
				}
				slaveFIFOSettings2.setBURST_LENGTH(Integer.parseInt(end1BurstLengthValue.getText()));
				SX3Manager.getInstance()
						.addLog("Slave FIFO Burst Length : " + end1BurstLengthValue.getText() + ".<br>");
				logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

			}
		});
		end1BurstLengthValue.setOnMouseClicked(new OnMouseClickHandler());

		/** Buffer Size **/
		Label end1BufferSizeLabel = new Label("Buffer Size (Bytes) : ");
		leftAnchorGridPane.setMargin(end1BufferSizeLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(end1BufferSizeLabel, 0, 2);
		TextField end1BufferSizeValue = new TextField();
		end1BufferSizeValue.setId("slaveFifoSettingsBufferSize");
		end1BufferSizeValue.setAlignment(Pos.CENTER_RIGHT);
		end1BufferSizeValue.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("SLAVE_FIFO_SETTINGS.TOOLTIP_BUFFER_SIZE")));
		end1BufferSizeValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = end1BufferSizeValue.localToScreen(end1BufferSizeValue.getBoundsInLocal());
			end1BufferSizeValue.getTooltip().setX(bounds.getMaxX());
		});
		end1BufferSizeValue.setMaxWidth(60);
		leftAnchorGridPane.add(end1BufferSizeValue, 1, 2);
		end1BufferSizeValue.setText(String.valueOf(slaveFIFOSettings2.getBUFFER_SIZE()));
		SlaveFifoSettingValidation.setupslaveFifoSettingsValidationForNumeric(slaveFifoSettingsTab, tab,
				end1BufferSizeValue, endpointSettingsTabErrorList, performLoadAction,
				tooltipAndErrorProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), 65535, "Buffer Size");
		end1BufferSizeValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (end1BufferSizeValue.getText().equals("") || end1BufferSizeValue.getText().equals("0")) {
					end1BufferSizeValue.setText("16");
					slaveFIFOSettings2.setBUFFER_SIZE(0);
				}
				int bufferSize = Integer.parseInt(end1BufferSizeValue.getText());
				if (bufferSize % 16 != 0) {
					errorListOnSave.put("Slave fifo Buffer Size", true);
					SX3Manager.getInstance().addLog(
							"<span style='color:red;'>Choose the size (in bytes) for each buffer (should be a multiple of 16).</span><br>");
					logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				} else {
					errorListOnSave.put("Slave fifo Buffer Size Buffer Size", false);
					SX3Manager.getInstance().addLog("Buffer Size (Bytes) : " + end1BufferSizeValue.getText() + ".<br>");
					logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				}
			}
		});
		end1BufferSizeValue.setOnMouseClicked(new OnMouseClickHandler());

		/** Buffer Count **/
		Label end1BufferCountLabel = new Label("Buffer Count : ");
		leftAnchorGridPane.setMargin(end1BufferCountLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(end1BufferCountLabel, 0, 3);
		TextField end1BufferCountValue = new TextField();
		end1BufferCountValue.setAlignment(Pos.CENTER_RIGHT);
		end1BufferCountValue.setId("slaveFifoSettingsBufferCount");
		end1BufferCountValue.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("SLAVE_FIFO_SETTINGS.TOOLTIP_BUFFER_COUNT")));
		end1BufferCountValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = end1BufferCountValue.localToScreen(end1BufferCountValue.getBoundsInLocal());
			end1BufferCountValue.getTooltip().setX(bounds.getMaxX());
		});
		end1BufferCountValue.setMaxWidth(40);
		end1BufferCountValue.setText(String.valueOf(slaveFIFOSettings2.getBUFFER_COUNT()));
		leftAnchorGridPane.add(end1BufferCountValue, 1, 3);
		SlaveFifoSettingValidation.setupslaveFifoSettingsValidationForNumeric(slaveFifoSettingsTab, tab,
				end1BufferCountValue, endpointSettingsTabErrorList, performLoadAction,
				tooltipAndErrorProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), 255, "Buffer Count");
		end1BufferCountValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (end1BufferCountValue.getText().equals("") || end1BufferCountValue.getText().equals("0")) {
					end1BufferCountValue.setText("1");
					slaveFIFOSettings2.setBUFFER_COUNT(1);
				}
				SX3Manager.getInstance().addLog("Buffer Count : " + end1BufferCountValue.getText() + ".<br>");
				logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

			}
		});
		end1BufferCountValue.setOnMouseClicked(new OnMouseClickHandler());

		/** Total Used Buffer Space **/
		Label totalUseBufferSpaceLabel = new Label("Total used Buffer Space (Bytes) : ");
		leftAnchorGridPane.setMargin(totalUseBufferSpaceLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(totalUseBufferSpaceLabel, 0, 4);
		TextField totalUseBufferSpaceValue = new TextField();
		totalUseBufferSpaceValue.setAlignment(Pos.CENTER_RIGHT);
		totalUseBufferSpaceValue.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("Endpoint_Settings.TOOLTIP_BUFFER_SPACE")));
		totalUseBufferSpaceValue.setMaxWidth(60);
		totalUseBufferSpaceValue.setText(String.valueOf(slaveFIFOSettings2.getBUFFER_SPACE()));
		totalUseBufferSpaceValue.setDisable(true);

		end1BufferSizeValue.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if (newValue.equals("")) {
				newValue = "0";
			}
			if (tab.getText().contains("Endpoint 1")) {
				if (Endpoint_2.getValue() != null && !Endpoint_2.getValue().equals("Not Enabled")) {
					int bufferSize = sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_SIZE();
					int bufferCount = sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_COUNT();
					sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_SPACE(
							((Integer.parseInt(newValue) * Integer.parseInt(end1BufferCountValue.getText()))
									+ (bufferSize * bufferCount)) * 2);
					sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).setBUFFER_SPACE(
							((Integer.parseInt(newValue) * Integer.parseInt(end1BufferCountValue.getText()))
									+ (bufferSize * bufferCount)) * 2);
					sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_SIZE(Integer.parseInt(newValue));
					totalUseBufferSpaceValue.setText(
							String.valueOf(sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_SPACE()));
					setSlaveFifoSettingsBufferSpace(leftAnchorGridPane, totalUseBufferSpaceValue);
				} else if (Endpoint_2.getValue() == null || Endpoint_2.getValue().equals("Not Enabled")) {
					sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_SPACE(
							(Integer.parseInt(newValue) * Integer.parseInt(end1BufferCountValue.getText())) * 2);
					sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_SIZE(Integer.parseInt(newValue));
					totalUseBufferSpaceValue.setText(
							String.valueOf(sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_SPACE()));
				}
			} else {
				int bufferSize = sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_SIZE();
				int bufferCount = sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_COUNT();
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).setBUFFER_SPACE(
						((Integer.parseInt(newValue) * Integer.parseInt(end1BufferCountValue.getText()))
								+ (bufferSize * bufferCount)) * 2);
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_SPACE(
						((Integer.parseInt(newValue) * Integer.parseInt(end1BufferCountValue.getText()))
								+ (bufferSize * bufferCount)) * 2);
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).setBUFFER_SIZE(Integer.parseInt(newValue));
				totalUseBufferSpaceValue
						.setText(String.valueOf(sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_SPACE()));
				setSlaveFifoSettingsBufferSpace(leftAnchorGridPane, totalUseBufferSpaceValue);
			}
		});

		end1BufferCountValue.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if (newValue.equals("")) {
				newValue = "0";
			}
			if (tab.getText().contains("Endpoint 1")) {
				if (!Endpoint_2.getValue().equals("Not Enabled")) {
					int bufferSize = sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_SIZE();
					int bufferCount = sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_COUNT();
					sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_SPACE(
							((Integer.parseInt(newValue) * Integer.parseInt(end1BufferSizeValue.getText()))
									+ (bufferSize * bufferCount)) * 2);
					sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).setBUFFER_SPACE(
							((Integer.parseInt(newValue) * Integer.parseInt(end1BufferSizeValue.getText()))
									+ (bufferSize * bufferCount)) * 2);
					sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_COUNT(Integer.parseInt(newValue));
					totalUseBufferSpaceValue.setText(
							String.valueOf(sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_SPACE()));
					setSlaveFifoSettingsBufferSpace(leftAnchorGridPane, totalUseBufferSpaceValue);
				} else if (Endpoint_2.getValue().equals("Not Enabled")) {
					sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_SPACE(
							(Integer.parseInt(newValue) * Integer.parseInt(end1BufferSizeValue.getText())) * 2);
					sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).setBUFFER_COUNT(Integer.parseInt(newValue));
					totalUseBufferSpaceValue.setText(
							String.valueOf(sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_SPACE()));
					setSlaveFifoSettingsBufferSpace(leftAnchorGridPane, totalUseBufferSpaceValue);
				}

			} else {
				int bufferSize = sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_SIZE();
				int bufferCount = sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_COUNT();
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1)
						.setBUFFER_SPACE(((Integer.parseInt(newValue) * Integer.parseInt(end1BufferSizeValue.getText()))
								+ (bufferSize * bufferCount)) * 2);
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(0)
						.setBUFFER_SPACE(((Integer.parseInt(newValue) * Integer.parseInt(end1BufferSizeValue.getText()))
								+ (bufferSize * bufferCount)) * 2);
				sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).setBUFFER_COUNT(Integer.parseInt(newValue));
				totalUseBufferSpaceValue
						.setText(String.valueOf(sx3Configuration.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_SPACE()));
				setSlaveFifoSettingsBufferSpace(leftAnchorGridPane, totalUseBufferSpaceValue);
			}
		});
		leftAnchorGridPane.add(totalUseBufferSpaceValue, 1, 4);
		anchorPane.getChildren().add(leftAnchorGridPane);
		tab.setContent(anchorPane);
		return tab;

	}

	private void setSlaveFifoSettingsBufferSpace(GridPane leftAnchorGridPane, TextField totalUseBufferSpaceValue) {
		ObservableList<javafx.scene.Node> children2 = leftAnchorGridPane.getChildren();
		for (javafx.scene.Node node : children2) {
			if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 4) {
				leftAnchorGridPane.getChildren().remove(node);
				leftAnchorGridPane.add(totalUseBufferSpaceValue, 1, 4);
				break;
			}
		}
	}

	/**
	 * ------------------------------------------------------ Global Used methods
	 * -------------------------------------------
	 **/

	/**
	 * Create Configuration
	 * 
	 * @throws IOException
	 **/
	@FXML
	public void createNewConfiguration() throws IOException {
		if (!checkFieldEditOrNot) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("fxmls/CreateConfigurationDialog.fxml"));

			Parent root = loader.load();

			Scene scene = new Scene(root, 400, 200);
			Stage dialogStage = new Stage();
			dialogStage.setResizable(false);
			dialogStage.setTitle(CYPRESS_EZ_USB_SX3_CONFIGURATION_UTILITY_TITLE);
			dialogStage.getIcons().add(new Image("/resources/CyLogo_tree.png"));
			dialogStage.initOwner((Stage) deviceName.getScene().getWindow());
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.setScene(scene);

			NewConfigurationController newConfigurationController = (NewConfigurationController) loader.getController();
			newConfigurationController.setToolTipAndErrorProperties(tooltipAndErrorProperties);

			dialogStage.showAndWait();

			if (newConfigurationController.isOkClicked()) {

				createConfigurationFile(newConfigurationController.getDeviceName(),
						newConfigurationController.getFileName(), newConfigurationController.getFilePath());
			}
		} else {
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setTitle("Warning");
			dialog.setResizable(true);
			dialog.setContentText("Data entered will be lost. Do you want continue?");
			ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
			ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			dialog.getDialogPane().getButtonTypes().add(buttonCancel);
			Optional<ButtonType> showAndWait = dialog.showAndWait();
			if (showAndWait.get() == buttonTypeOk) {
				checkFieldEditOrNot = false;
				createNewConfiguration();
			}
			if (showAndWait.get() == buttonCancel) {
				dialog.close();
			}
		}

	}

	/**
	 * ------------------------------------------------------ Global Used methods
	 * -------------------------------------------
	 **/

	/**
	 * Programming the Device 1. Save Operation to be performed if the Content was
	 * Modified. 2. If there are errors deducted during the same, Programming cannot
	 * be allowed. Error needs to be prompted. 3. If all OK, Programing Dialog to be
	 * opened.
	 * 
	 * @throws IOException
	 **/
	@FXML
	public void programConfiguration() throws IOException {

		if (performSaveAction == false) {
			saveConfiguration();
		}
		if (errorListOnSave.containsValue(true)) {
			showAlertMsg("Some error in JSON file. Please check log or log file.");
			return;
		}

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("fxmls/ProgramDialog.fxml"));

		Parent root = loader.load();

		Scene scene = new Scene(root, 575, 150);
		Stage dialogStage = new Stage();
		dialogStage.setResizable(false);
		dialogStage.setTitle(CYPRESS_EZ_USB_SX3_CONFIGURATION_UTILITY_TITLE);
		dialogStage.getIcons().add(new Image("/resources/CyLogo_tree.png"));
		dialogStage.initOwner((Stage) deviceName.getScene().getWindow());
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.setScene(scene);

		((ProgrammingUtilityController) loader.getController()).setStage(dialogStage);

		dialogStage.showAndWait();

	}

	private void createConfigurationFile(String deviceCombo, String fileName1, String filePath1) {
		try {
			performSaveAction = false;
			slaveFifoSettingsTab.setDisable(true);
			performLoadAction = false;
			File file = new File(filePath1 + File.separator + fileName1);
			file.mkdir();

			if (file.exists()) {
				//Creating the Marker File
				File projectFile = new File(file.getAbsolutePath() + File.separator + ".sx3project");
				projectFile.createNewFile();

				videoSourceType = new HashMap<>();
				videoSourceType.put("Endpoint 1", "Image Sensor");
				videoSourceType.put("Endpoint 2", "Image Sensor");
				programConfigFromMenuBar.setDisable(false);
				programConfigFromToolBar.setDisable(false);
				logDetails3 = new TextArea();
				logDetails2 = new TextArea();
				endpointOneChanges = false;
				endpointTwoChanges = false;
				endpointValues = new HashMap<>();
				endpointOneActionPerformed = false;
				endpointTwoActionPerformed = false;
				welcomeScreen.getChildren().clear();
				deviceSettingsTabSplitpane.setVisible(true);
				welcomeScreen.setVisible(false);
				fifoMasterConfigTab.setDisable(false);
				sx3Configuration = new SX3Configuration();
				List<String> lines = Arrays.asList("Device :" + deviceCombo);
				file.createNewFile();
				sx3ConfigurationFilePath = file.getAbsolutePath() + File.separator + fileName1 + ".json";
				Path path = Paths.get(sx3ConfigurationFilePath);
				Files.write(path, lines, StandardCharsets.UTF_8);

				SX3Manager.getInstance().setSx3Configuration(sx3Configuration);
				deviceName.setText(deviceCombo);
				sx3Configuration.setDEVICE_NAME(deviceCombo);
				Stage primStage = (Stage) deviceName.getScene().getWindow();
				primStage.setTitle("Cypress SX3 Configuration Utility - " + sx3ConfigurationFilePath);
				saveConfigFromToolBar.setDisable(false);
				saveConfigFromMenu.setDisable(false);
				uvcuacSettingsTab.setDisable(true);
				videoSourceConfigTab.setDisable(true);
				uacSettingList = new ArrayList<>();
				uvcSettingList = new ArrayList<>();
				videoSourceConfigList = new ArrayList<>();
				resetDeviceSettings(deviceName.getText());
				if (serialNumber.getText() != null && !serialNumber.getText().equals("")) {
					if (SX3CommonUIValidations
							.isValidNumeric(serialNumber.getText().substring(serialNumber.getText().length() - 1))) {
						int serialNo = Integer
								.parseInt(serialNumber.getText().substring(serialNumber.getText().length() - 1))
								+ Integer.parseInt(serialNumberIncrement.getValue());
						String newStringNumber = serialNumber.getText().substring(0,
								serialNumber.getText().length() - 1) + String.valueOf(serialNo);
						serialNumber.setText(newStringNumber);
					}
				}

				// Set Config File path to preference
				SX3ConfigPreference.setSx3ConfigFilePathPreference(sx3ConfigurationFilePath);
				checkFieldEditOrNot = false;

				/**
				 * -------- Set tooltip on device settings tab and fifo master tab fields
				 * ---------
				 **/
				setAllToolTip();
				logHelpSplitpane.setMaxHeight(470.0);
				if (deviceName.getText().equals("SX3 UVC (CYUSB3017)")) {
					Endpoint_1.setValue("UVC");
					Endpoint_2.setValue("Not Enabled");
				} else {
					Endpoint_1.setValue("IN");
					Endpoint_2.setValue("Not Enabled");
				}
				checkFieldEditOrNot = false;
				logDetails = new StringBuffer();
				SX3Manager.getInstance().setLogDetails(logDetails);
				SX3Manager.getInstance().addLog(new Date() + " EZ-USB SX3 Configuration Utility Launched.<br>");
				SX3Manager.getInstance().addLog("Device Name : " + deviceCombo + ".<br>");
				SX3Manager.getInstance().addLog("Configuration File Name : " + fileName1 + ".<br>");
				SX3Manager.getInstance().addLog("Configuration File Path : " + file.getAbsolutePath() + ".<br>");
				SX3Manager.getInstance().addLog("Configuration file created Successfully.<br>");
				logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				deviceSettingsTabSplitpane.getSelectionModel().select(0);
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText(null);
				a.setContentText("The system cannot find the path specified.");
				Optional<ButtonType> showAndWait = a.showAndWait();
				if (showAndWait.isPresent()) {
					createNewConfiguration();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void resetDeviceSettings(String deviceName) {
		uvcVersion.setDisable(true);
		uvcVersion.setValue(null);
		uvcHeaderAddition.setDisable(true);
		uvcHeaderAddition.setValue(null);
		vendorId.setText("04b4");
		productId.setText("");
		enableRemoteWakeup.setSelected(false);
		powerConfiguration.setValue("Bus Powered");
		fifoClockFrequency.setText("100");
		debugValue.setValue("");
		enableDebugLevel.setSelected(false);
		enableFPGA.setSelected(false);
		chooseBitFile.setText("");
		bitFileSize.setText("");
		fpgaFamily.setValue("Lattice ECP5");
		i2cSlaveAddress.setText("");
		uvcuacTabpane.getTabs().clear();
		uvcuacSettingsTab.setDisable(true);
		videoSourceConfigTabpane.getTabs().clear();
		videoSourceConfigTab.setDisable(true);
		slaveFifoSettingsTabpane.getTabs().clear();
		slaveFifoSettingsTab.setDisable(true);
		deviceSttingI2CFrequency.setItems(DeviceSettingsConstant.DEVICE_I2C_FREQUENCY);
		deviceSttingI2CFrequency.setValue(DeviceSettingsConstant.DEVICE_I2C_FREQUENCY.get(0));
		uvcuacSettingsTab.setStyle("");
		if (deviceName.equals("SX3 Data-16 bit (CYUSB3015)")) {
			interFaceType.getItems().clear();
			interFaceType.setDisable(true);
			slaveFifoSettingsTab.setDisable(false);
			fifoBusWidth.getItems().clear();
			fifoBusWidth.getItems().addAll(DeviceSettingsConstant.FIFO_BUS_WIDTH_16BIT);
			fifoBusWidth.setValue("16");
			Endpoint_1.getItems().clear();
			Endpoint_1.getItems().addAll(DeviceSettingsConstant.ENDPOINTS_SX3_DATA);
			Endpoint_2.getItems().clear();
			Endpoint_2.getItems().addAll("Not Enabled","OUT");
			gpioReset();
		} else if (deviceName.equals("SX3 Data-32 bit (CYUSB3016)")) {
			interFaceType.getItems().clear();
			interFaceType.setDisable(true);
			slaveFifoSettingsTab.setDisable(false);
			fifoBusWidth.getItems().clear();
			fifoBusWidth.getItems().addAll("8", "16", "24", "32");
			fifoBusWidth.setValue("32");
			Endpoint_1.getItems().clear();
			Endpoint_1.getItems().addAll(DeviceSettingsConstant.ENDPOINTS_SX3_DATA);
			Endpoint_2.getItems().clear();
			Endpoint_2.getItems().addAll("Not Enabled","OUT");
			gpioReset();
		} else {
			interFaceType.getItems().clear();
			interFaceType.getItems().addAll(DeviceSettingsConstant.FIFO_INTERFACE_TYPE);
			interFaceType.setValue("Slave FIFO Interface");
			interFaceType.setDisable(false);
			slaveChange = true;
			imageSensorChangeChange = false;
			fifoBusWidth.getItems().clear();
			fifoBusWidth.getItems().addAll("8", "16", "24", "32");
			fifoBusWidth.setValue("32");
			uvcVersion.getItems().clear();
			uvcVersion.getItems().addAll(DeviceSettingsConstant.UVC_VERSION);
			uvcHeaderAddition.getItems().clear();
			uvcHeaderAddition.getItems().addAll(DeviceSettingsConstant.UVC_HEADER_ADDITION);
			Endpoint_1.getItems().clear();
			Endpoint_1.getItems().addAll(DeviceSettingsConstant.ENDPOINTS_SX3_UVC);
			Endpoint_2.getItems().clear();
			Endpoint_2.getItems().addAll("Not Enabled", "UAC");
			gpio1.getItems().clear();
			gpio1.getItems().addAll(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC);
			gpio1.setValue("Not Used");
			gpio2.getItems().clear();
			gpio2.getItems().addAll(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC);
			gpio2.setValue("Not Used");
			gpio3.getItems().clear();
			gpio3.getItems().addAll(DeviceSettingsConstant.GPIO_THREE_TO_SEVEN_IN_SX3UVC);
			gpio3.setValue("Not Used");
			gpio4.getItems().clear();
			gpio4.getItems().addAll(DeviceSettingsConstant.GPIO_THREE_TO_SEVEN_IN_SX3UVC);
			gpio4.setDisable(false);
			gpio4.setValue("Not Used");
			gpio5.getItems().clear();
			gpio5.getItems().addAll(DeviceSettingsConstant.GPIO_THREE_TO_SEVEN_IN_SX3UVC);
			gpio5.setDisable(false);
			gpio5.setValue("Not Used");
			gpio6.setDisable(false);
			gpio6.getItems().addAll(DeviceSettingsConstant.GPIO_THREE_TO_SEVEN_IN_SX3UVC);
			gpio6.setValue("Not Used");
			gpio7.setDisable(false);
			gpio7.getItems().addAll(DeviceSettingsConstant.GPIO_THREE_TO_SEVEN_IN_SX3UVC);
			gpio7.setValue("Not Used");
		}

	}

	private void gpioReset() {
		gpio1.getItems().clear();
		gpio1.getItems().addAll(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA);
		gpio1.setValue(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[0]);
		gpio2.getItems().clear();
		gpio2.getItems().addAll(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA);
		gpio2.setValue(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[0]);
		gpio3.getItems().clear();
		gpio3.getItems().addAll(DeviceSettingsConstant.GPIO_THREE_TO_SEVEN_IN_SX3DATA);
		gpio3.setValue(DeviceSettingsConstant.GPIO_THREE_TO_SEVEN_IN_SX3DATA[0]);
		gpio4.getItems().clear();
		gpio4.setDisable(true);
		gpio4.setValue(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[0]);
		gpio5.getItems().clear();
		gpio5.setDisable(true);
		gpio5.setValue(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[0]);
		gpio6.getItems().clear();
		gpio6.setDisable(true);
		gpio6.setValue(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[0]);
		gpio7.getItems().clear();
		gpio7.setDisable(true);
		gpio7.setValue(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3DATA[0]);

	}

	/** Save Configuration **/
	@FXML
	private void saveConfiguration() throws IOException {

		prepareSX3ConfigurationObjectForSave();
		saveConfigurationToFile(sx3ConfigurationFilePath);
	}

	private void prepareSX3ConfigurationObjectForSave() {
		performSaveAction = true;
		/** Config Table General Data **/
		ConfigTableGeneral configTableGeneral = new ConfigTableGeneral();
		configTableGeneral.setSIGNATURE("CY");
		configTableGeneral.setCONFIG_TABLE_CHECKSUM("0xFFFFFFFF");
		configTableGeneral.setCONFIG_TABLE_LENGTH("0xFFFFFFFF");
		configTableGeneral.setVERSION_MAJOR(1);
		configTableGeneral.setVERSION_MINOR(0);
		configTableGeneral.setVERSION_PATCH(0);
		configTableGeneral.setRESERVED("FF");

		if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("IN")
				&& Endpoint_2.getValue().equals("Not Enabled")) {
			configTableGeneral.setCONFIGURATION_TYPE(1);
		} else if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("OUT")
				&& Endpoint_2.getValue().equals("Not Enabled")) {
			configTableGeneral.setCONFIGURATION_TYPE(2);
		} else if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("IN") && Endpoint_2.getValue() != null
				&& Endpoint_2.getValue().equals("OUT")) {
			configTableGeneral.setCONFIGURATION_TYPE(3);
		} else if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("OUT") && Endpoint_2.getValue() != null
				&& Endpoint_2.getValue().equals("OUT")) {
			configTableGeneral.setCONFIGURATION_TYPE(5);
		} else if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("IN") && Endpoint_2.getValue() != null
				&& Endpoint_2.getValue().equals("IN")) {
			configTableGeneral.setCONFIGURATION_TYPE(6);
		} else if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UVC") && Endpoint_2.getValue() != null
				&& Endpoint_2.getValue().equals("UAC")) {
			configTableGeneral.setCONFIGURATION_TYPE(8);
		} else if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UVC")
				&& Endpoint_2.getValue().equals("Not Enabled")) {
			configTableGeneral.setCONFIGURATION_TYPE(11);
		} else if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UAC")
				&& Endpoint_2.getValue().equals("Not Enabled")) {
			configTableGeneral.setCONFIGURATION_TYPE(12);
		}

		/** Config Table OffSet Table Data **/
		ConfigTableOffSetTable configTableOffSetTable = new ConfigTableOffSetTable();
		configTableOffSetTable.setSX3_DEVICE_SETTINGS_TABLE_OFFSET("0xFFFF");
		configTableOffSetTable.setFIFO_MASTER_CONFIG_OFFSET("0xFFFF");
		configTableOffSetTable.setVIDEO_SOURCE_1_CONFIG_OFFSET("0xFFFF");
		configTableOffSetTable.setVIDEO_SOURCE_2_CONFIG_OFFSET("0xFFFF");
		configTableOffSetTable.setSLAVE_FIFO_1_SETTINGS_CONFIG_TABLE_OFFSET("0xFFFF");
		configTableOffSetTable.setSLAVE_FIFO_2_SETTINGS_CONFIG_TABLE_OFFSET("0xFFFF");
		configTableOffSetTable.setUAC_1_CONFIG_TABLE_OFFSET("0xFFFF");
		configTableOffSetTable.setUAC_2_CONFIG_TABLE_OFFSET("0xFFFF");
		configTableOffSetTable.setUVC_1_CONFIG_TABLE_OFFSET("0xFFFF");
		configTableOffSetTable.setUVC_2_CONFIG_TABLE_OFFSET("0xFFFF");
		configTableOffSetTable.setUVC_1_COLOR_MATCHING_DESCRIPTOR_OFFSET("0xFFFF");
		configTableOffSetTable.setUVC_2_COLOR_MATCHING_DESCRIPTOR_OFFSET("0xFFFF");
		configTableOffSetTable.setUVC_1_CAMERA_CONTROLS_TABLE_OFFSET("0xFFFF");
		configTableOffSetTable.setUVC_2_CAMERA_CONTROLS_TABLE_OFFSET("0xFFFF");
		configTableOffSetTable.setUVC_1_PROCESSING_UNIT_TABLE_OFFSET("0xFFFF");
		configTableOffSetTable.setUVC_2_PROCESSING_UNIT_TABLE_OFFSET("0xFFFF");
		configTableOffSetTable.setUVC_1_EXTENSION_UNIT_TABLE_OFFSET("0xFFFF");
		configTableOffSetTable.setUVC_2_EXTENSION_UNIT_TABLE_OFFSET("0xFFFF");
		configTableOffSetTable.setRESERVED("FF");

		DeviceSettings deviceSettings = new DeviceSettings();
		deviceSettings.setRESERVED("FF");
		/** USB Settings Data **/
		USBSettings usbSetting = new USBSettings();
		if (!vendorId.getText().equals("")) {
			usbSetting.setVENDOR_ID(SX3PropertiesConstants.HEX_PREFIX + vendorId.getText());
			errorListOnSave.put("vendorId", false);
		} else {
			usbSetting.setVENDOR_ID(vendorId.getText());
			SX3Manager.getInstance()
					.addLog("<span style='color:red;'>Error : Vendor ID should not be empty.</span><br>");
			errorListOnSave.put("vendorId", true);
		}
		if (!productId.getText().equals("")) {
			usbSetting.setPRODUCT_ID(SX3PropertiesConstants.HEX_PREFIX + productId.getText());
			errorListOnSave.put("productId", false);
		} else {
			usbSetting.setPRODUCT_ID(productId.getText());
			SX3Manager.getInstance()
					.addLog("<span style='color:red;'>Error : Product ID should not be empty.</span><br>");
			errorListOnSave.put("productId", true);
		}
		if (!manufacture.getText().equals("")) {
			usbSetting.setMANUFACTURER_STRING(manufacture.getText());
			errorListOnSave.put("manufacture", false);
		} else {
			usbSetting.setMANUFACTURER_STRING(manufacture.getText());
			SX3Manager.getInstance()
					.addLog("<span style='color:red;'>Error : Manufacture should not be empty.</span><br>");
			errorListOnSave.put("manufacture", true);
		}
		usbSetting.setLEN_MANUFACTURER_STRING(manufacture.getText().length());
		if (!productString.getText().equals("")) {
			usbSetting.setPRODUCT_STRING(productString.getText());
			errorListOnSave.put("productString", false);
		} else {
			usbSetting.setPRODUCT_STRING(productString.getText());
			SX3Manager.getInstance()
					.addLog("<span style='color:red;'>Error : Product String should not be empty.</span><br>");
			errorListOnSave.put("productString", true);
		}
		usbSetting.setLEN_PRODUCT_STRING(productString.getText().length());
		if (enableRemoteWakeup.isSelected()) {
			usbSetting.setREMOTE_WAKEUP_ENABLE("Enable");
		} else {
			usbSetting.setREMOTE_WAKEUP_ENABLE("Disable");
		}
		usbSetting.setPOWER_CONFIGURATION(powerConfiguration.getValue());
		usbSetting.setENDPOINT1_TYPE(Endpoint_1.getValue());
		if (interFaceType.getValue() != null) {
			usbSetting.setFIFO_INTERFACE_TYPE(interFaceType.getValue());
		} else {
			usbSetting.setFIFO_INTERFACE_TYPE("");
		}
		if (deviceName.getText().equals("SX3 UVC (CYUSB3017)")) {
			if (Endpoint_1.getValue().equals("UVC")
					|| (Endpoint_2.getValue() != null && Endpoint_2.getValue().equals("UVC"))) {
				if (uvcVersion.getValue() != null) {
					usbSetting.setUVC_VERSION(uvcVersion.getValue());
					errorListOnSave.put("uvcVersion", false);
				} else {
					usbSetting.setUVC_VERSION("");
					errorListOnSave.put("uvcVersion", true);
					SX3Manager.getInstance()
							.addLog("<span style='color:red;'>Error : UVC Version should not be empty.</span><br>");
				}
			}
		}
		if (deviceName.getText().equals("SX3 UVC (CYUSB3017)")) {
			if (Endpoint_1.getValue().equals("UVC")
					|| (Endpoint_2.getValue() != null && Endpoint_2.getValue().equals("UVC"))) {
				if (uvcHeaderAddition.getValue() != null) {
					usbSetting.setUVC_HEADER_ADDITION(uvcHeaderAddition.getValue());
					errorListOnSave.put("uvcHeaderAddition", false);
				} else {
					errorListOnSave.put("uvcHeaderAddition", true);
					SX3Manager.getInstance().addLog(
							"<span style='color:red;'>Error : UVC Header Addition should not be empty.</span><br>");
					usbSetting.setUVC_HEADER_ADDITION("");
				}
			}
		}
		if (!Endpoint_2.getValue().equals("Not Enabled")) {
			usbSetting.setENDPOINT2_TYPE(Endpoint_2.getValue());
		} else {
			usbSetting.setENDPOINT2_TYPE("");
		}
		if (fifoBusWidth.getValue() != null && !fifoBusWidth.getValue().equals("")) {
			usbSetting.setFIFO_BUS_WIDTH(Integer.parseInt(fifoBusWidth.getValue()));
		}
		if (!fifoClockFrequency.getText().isEmpty() && fifoClockFrequency.getText() != null
				&& !fifoClockFrequency.getText().equals("")) {
			usbSetting.setFIFO_CLOCK(Integer.parseInt(fifoClockFrequency.getText()));
		}
		usbSetting.setRESERVED("FF");
		deviceSettings.setUSB_SETTINGS(usbSetting);

		/** Debug Data **/
		DebugLevel debugObject = new DebugLevel();
		if (enableDebugLevel.isSelected()) {
			debugObject.setDEBUG_ENABLE("Enable");
		} else {
			debugObject.setDEBUG_ENABLE("Disable");
		}
		if (debugValue.getValue() != null && !debugValue.getValue().equals("")) {
			debugObject.setDEBUG_LEVEL(Integer.parseInt(debugValue.getValue()));
		}
		debugObject.setRESERVED("FF");
		deviceSettings.setDEBUG_LEVEL(debugObject);
		if (autoGenerateSerialNumber.isSelected()) {
			usbSetting.setSERIALNUMBER_STRING("");
			usbSetting.setLEN_SERIALNUMBER(0);
			usbSetting.setAUTO_INCREMENT_SERIAL_NUMBER(0);
			debugObject.setAUTO_GENERATE_SERIAL_NUMBER(1);
		} else {
			debugObject.setAUTO_GENERATE_SERIAL_NUMBER(0);
			if (serialNumber.getText().equals("")) {
				errorListOnSave.put("serialNumber", true);
				SX3Manager.getInstance()
						.addLog("<span style='color:red;'>Error : Serial number should not be empty.</span><br>");
			} else {
				errorListOnSave.put("serialNumber", false);
			}
			usbSetting.setSERIALNUMBER_STRING(serialNumber.getText());
			usbSetting.setLEN_SERIALNUMBER(serialNumber.getText().length());
			usbSetting.setAUTO_INCREMENT_SERIAL_NUMBER(Integer.parseInt(serialNumberIncrement.getValue()));
		}

		/** GPIOs Data **/
		GPIOs gpios = new GPIOs();
		gpios.setGPIO_0_SETTING(gpio1.getValue());
		gpios.setGPIO_1_SETTING(gpio2.getValue());
		gpios.setGPIO_2_SETTING(gpio3.getValue());
		gpios.setGPIO_3_SETTING(gpio4.getValue());
		gpios.setGPIO_4_SETTING(gpio5.getValue());
		gpios.setGPIO_5_SETTING(gpio6.getValue());
		gpios.setGPIO_6_SETTING(gpio7.getValue());
		gpios.setRESERVED("FF");
		deviceSettings.setGPIOS_SETTINGS(gpios);

		AuxilliaryInterface auxInterface = new AuxilliaryInterface();
		if (deviceSttingFirmWare.isSelected()) {
			auxInterface.setFIRMWARE_UPDATE_HID("Enable");
		} else {
			auxInterface.setFIRMWARE_UPDATE_HID("Disable");
		}
		if (deviceSttingI2CFrequency.getValue() != null) {
			auxInterface.setI2C_FREQUENCY(Long.valueOf(deviceSttingI2CFrequency.getValue()));
			errorListOnSave.put("deviceSttingI2CFrequency", false);
		} else {
			auxInterface.setI2C_FREQUENCY(0L);
			errorListOnSave.put("deviceSttingI2CFrequency", true);
			SX3Manager.getInstance()
					.addLog("<span style='color:red;'>Error : I2C Frequency should not be empty.</span><br>");
		}
		deviceSettings.setAUXILLIARY_INTERFACE(auxInterface);

		/** FIFO Master Config **/
		FifoMasterConfig fifoMasterConfig = new FifoMasterConfig();
		if (enableFPGA.isSelected()) {
			errorListOnSave.put("enableFPGA", false);
			fifoMasterConfig.setFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN("Enable");
			fifoMasterConfig.setFPGA_FAMILY(fpgaFamily.getValue());
			if (chooseBitFile.getText().equals("")) {
				SX3Manager.getInstance()
						.addLog("<span style='color:red;'>Error : Bit file should not be empty.</span><br>");
				errorListOnSave.put("chooseBitFile", true);
			} else {
				errorListOnSave.put("chooseBitFile", false);
			}
			fifoMasterConfig.setBIT_FILE_PATH(chooseBitFile.getText());
			if (bitFileSize.getText().equals("")) {
				fifoMasterConfig.setBIT_FILE_SIZE(0);
			} else {
				fifoMasterConfig.setBIT_FILE_SIZE(Integer.parseInt(bitFileSize.getText()));
			}
		} else {
			fifoMasterConfig.setFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN("Disable");
			fifoMasterConfig.setFPGA_FAMILY("");
			fifoMasterConfig.setBIT_FILE_PATH("");
			fifoMasterConfig.setBIT_FILE_SIZE(0);
			errorListOnSave.put("chooseBitFile", false);
		}
		if (!i2cSlaveAddress.getText().equals("")) {
			errorListOnSave.put("i2cSlaveAddress", false);
			String i2cSlaveAddressText = i2cSlaveAddress.getText().isEmpty() ? ""
					: SX3PropertiesConstants.HEX_PREFIX + i2cSlaveAddress.getText();
			fifoMasterConfig.setI2C_SLAVE_ADDRESS(i2cSlaveAddressText);
		} else {
			String i2cSlaveAddressText = i2cSlaveAddress.getText().isEmpty() ? ""
					: SX3PropertiesConstants.HEX_PREFIX + i2cSlaveAddress.getText();
			fifoMasterConfig.setI2C_SLAVE_ADDRESS(i2cSlaveAddressText);
			errorListOnSave.put("i2cSlaveAddress", true);
			SX3Manager.getInstance()
					.addLog("<span style='color:red;'>Error : FPGA I2C slave address should not be empty.</span><br>");
		}
		String bitFileSizeText = bitFileSize.getText();
		int bitFileSizeIntval = bitFileSizeText != null && !bitFileSizeText.isEmpty()
				? Integer.parseInt(bitFileSizeText)
				: 0;
		fifoMasterConfig.setBIT_FILE_SIZE(bitFileSizeIntval);
		fifoMasterConfig.setRESERVED("FF");

		if (sx3Configuration.getVIDEO_SOURCE_CONFIG() != null) {
			if (Endpoint_1.getValue().equals("UAC") && sx3Configuration.getVIDEO_SOURCE_CONFIG().size() > 1) {
				sx3Configuration.getVIDEO_SOURCE_CONFIG().remove(1);
			}
			List<VideoSourceConfig> video_SOURCE_CONFIG = sx3Configuration.getVIDEO_SOURCE_CONFIG();
			for (int i = 0; i < video_SOURCE_CONFIG.size(); i++) {
				if (video_SOURCE_CONFIG.get(i).getHDMI_SOURCE_CONFIGURATION() == null) {
					video_SOURCE_CONFIG.get(i).setHDMI_CONFIG_NUM_ROWS(0);
				} else {
					video_SOURCE_CONFIG.get(i)
							.setHDMI_CONFIG_NUM_ROWS(video_SOURCE_CONFIG.get(i).getHDMI_SOURCE_CONFIGURATION().size());
				}
				if (video_SOURCE_CONFIG.get(i).getI2C_SLAVE_ADDRESS().equals("")) {
					errorListOnSave.put("i2cSlaveAddress", true);
					int n = i + 1;
					SX3Manager.getInstance().addLog("<span style='color:red;'>Error : " + "Endpoint " + n
							+ " Video Source Config I2C slave address should not be empty.</span><br>");
				} else {
					errorListOnSave.put("i2cSlaveAddress", false);
				}
				if (video_SOURCE_CONFIG.get(i).getENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD().equals("Disable")) {
					video_SOURCE_CONFIG.get(i).setVIDEO_SOURCE_TYPE("");
				} else {
					errorListOnSave.put("enableVideoSourceConfig", false);
					if (video_SOURCE_CONFIG.get(i).getVIDEO_SOURCE_CONFIGURATION_SIZE() == 0) {
						errorListOnSave.put("videoSourceSensorConfig", true);
						int n = i + 1;
						SX3Manager.getInstance().addLog("<span style='color:red;'>Error : " + "Endpoint " + n
								+ " Video Source Configuration should not be empty.</span><br>");
					} else {
						errorListOnSave.put("videoSourceSensorConfig", false);
					}
					if (video_SOURCE_CONFIG.get(i).getVIDEO_SOURCE_TYPE().equals("HDMI Source")) {
						if (video_SOURCE_CONFIG.get(i).getVIDEO_SOURCE_SUBTYPE().equals("HDMI RX - Generic")) {
							Collections.sort(video_SOURCE_CONFIG.get(i).getHDMI_SOURCE_CONFIGURATION(), HDMISourceConfiguration.registerAddressComparator);
						}

						if (video_SOURCE_CONFIG.get(i).getVIDEO_SOURCE_SUBTYPE().equals("")) {
							errorListOnSave.put("videoSourceSubType", true);
							int n = i + 1;
							SX3Manager.getInstance().addLog("<span style='color:red;'>Error : " + "Endpoint " + n
									+ " Video Source Sub Type should not be empty.</span><br>");
						} else if (video_SOURCE_CONFIG.get(i).getVIDEO_SOURCE_SUBTYPE().equals("HDMI RX - Generic")) {
							errorListOnSave.put("videoSourceSubType", false);
							if (video_SOURCE_CONFIG.get(i).getHDMI_SOURCE_CONFIGURATION().get(0)
									.getHDMI_SOURCE_REGISTER_ADDRESS().equals("")
									|| video_SOURCE_CONFIG.get(i).getHDMI_SOURCE_CONFIGURATION().get(0)
											.getMASK_PATTERN().equals("")
									|| video_SOURCE_CONFIG.get(i).getHDMI_SOURCE_CONFIGURATION().get(0)
											.getCOMPARE_VALUE().equals("")) {
								errorListOnSave.put("videoSourceHdmiSensorConfig", true);
								int n = i + 1;
								SX3Manager.getInstance().addLog("<span style='color:red;'>Error : " + "Endpoint " + n
										+ " Video Source HDMI Source should not be empty.</span><br>");
							} else {
								errorListOnSave.put("videoSourceHdmiSensorConfig", false);
							}
						}
					}
				}
			}
		}

		if (Endpoint_1.getValue().equals("UAC") && sx3Configuration.getUAC_SETTINGS().size() > 1) {
			sx3Configuration.getUAC_SETTINGS().remove(1);
		}
		if (Endpoint_1.getValue().equals("UAC") && sx3Configuration.getUVC_SETTINGS() != null) {
			sx3Configuration.getUVC_SETTINGS().clear();
		}

		if ((Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UVC"))) {
			LinkedList<FormatAndResolutions> ex = new LinkedList<>();
			Map<String, Map<String, String>> map = new HashMap<>();
			int TOTAL_NUMBER_OF_IMAGE_FORMATS_SS_ENABLED = 0;
			int TOTAL_NUMBER_OF_IMAGE_FORMATS_HS_ENABLED = 0;
			int TOTAL_NUMBER_OF_IMAGE_FORMATS_FS_ENABLED = 0;
			int TOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_SS = 0;
			int TOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_HS = 0;
			int TOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_FS = 0;
			int TOTAL_NUMBER_OF_RESOLUTIONS_SS_ENABLED = 0;
			int TOTAL_NUMBER_OF_RESOLUTIONS_HS_ENABLED = 0;
			int TOTAL_NUMBER_OF_RESOLUTIONS_FS_ENABLED = 0;
			int TOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_FS = 0;
			int TOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_HS = 0;
			int TOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_SS = 0;
			UVCSettings uvcSettings = sx3Configuration.getUVC_SETTINGS().get(0);
			FormatAndResolution format_RESOLUTION = uvcSettings.getFORMAT_RESOLUTION();
			List<FormatAndResolutions> format_RESOLUTIONS = format_RESOLUTION.getFORMAT_RESOLUTIONS();
			if (format_RESOLUTIONS.get(format_RESOLUTIONS.size() - 1).getRESOLUTION_REGISTER_SETTING_LEN() == 0) {
				errorListOnSave.put("endpoin1FormatResolutionSensorConfig", true);
				SX3Manager.getInstance()
						.addLog("<span style='color:red;'>Error : " + "Endpoint 1 " + " S.No. "
								+ format_RESOLUTIONS.get(format_RESOLUTIONS.size() - 1).getS_NO() + " "
								+ format_RESOLUTIONS.get(format_RESOLUTIONS.size() - 1).getIMAGE_FORMAT()
								+ " Sensor config should not be empty in format and resolution.</span><br>");
			} else {
				errorListOnSave.put("endpoin1FormatResolutionSensorConfig", false);
			}
			if (format_RESOLUTIONS.size() == 1 && format_RESOLUTIONS.get(0).getIMAGE_FORMAT().equals("")) {
				errorListOnSave.put("endpoint1FormatResolutionImageFormat", true);
				SX3Manager.getInstance()
						.addLog("<span style='color:red;'>Error : " + "Endpoint 1 " + " S.No. "
								+ format_RESOLUTIONS.get(format_RESOLUTIONS.size() - 1).getS_NO() + " "
								+ " Image Format should not be empty in format and resolution.</span><br>");
				ex.add(format_RESOLUTIONS.get(0));
				format_RESOLUTION.setFORMAT_RESOLUTIONS(ex);
				format_RESOLUTION.setTOTAL_NUMBER_OF_RESOLUTIONS(ex.size());
				format_RESOLUTION.setTOTAL_NUMBER_OF_IMAGE_FORMATS_FS_ENABLED(0);
				format_RESOLUTION.setTOTAL_NUMBER_OF_IMAGE_FORMATS_HS_ENABLED(0);
				format_RESOLUTION.setTOTAL_NUMBER_OF_IMAGE_FORMATS_SS_ENABLED(0);
			} else {
				errorListOnSave.put("endpoint1FormatResolutionImageFormat", false);
				for (String imgFormat : UVCSettingsConstants.IMAGE_FORMAT) {
					Map<String, String> map1 = new HashMap<>();
					for (FormatAndResolutions formatAndResolutions : format_RESOLUTIONS) {
						if (imgFormat.equals(formatAndResolutions.getIMAGE_FORMAT())) {
							ex.add(formatAndResolutions);
							if (formatAndResolutions.getSUPPORTED_IN_FS().equals("Enable")) {
								TOTAL_NUMBER_OF_RESOLUTIONS_FS_ENABLED++;
							}
							if (formatAndResolutions.getSUPPORTED_IN_HS().equals("Enable")) {
								TOTAL_NUMBER_OF_RESOLUTIONS_HS_ENABLED++;
							}
							if (formatAndResolutions.getSUPPORTED_IN_SS().equals("Enable")) {
								TOTAL_NUMBER_OF_RESOLUTIONS_SS_ENABLED++;
							}
							if (formatAndResolutions.getSTILL_CAPTURE().equals("Enable")
									&& formatAndResolutions.getSUPPORTED_IN_FS().equals("Enable")) {
								TOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_FS++;
							}
							if (formatAndResolutions.getSTILL_CAPTURE().equals("Enable")
									&& formatAndResolutions.getSUPPORTED_IN_HS().equals("Enable")) {
								TOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_HS++;
							}
							if (formatAndResolutions.getSTILL_CAPTURE().equals("Enable")
									&& formatAndResolutions.getSUPPORTED_IN_SS().equals("Enable")) {
								TOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_SS++;
							}
							if (formatAndResolutions.getSUPPORTED_IN_FS().equals("Enable")) {
								map1.put("FS", "supportedInFS");
								if (formatAndResolutions.getSTILL_CAPTURE().equals("Enable")) {
									map1.put("SFS", "stillCapSupFS");
								}
								map.put(formatAndResolutions.getIMAGE_FORMAT(), map1);
							}
							if (formatAndResolutions.getSUPPORTED_IN_HS().equals("Enable")) {
								map1.put("HS", "supportedInHS");
								if (formatAndResolutions.getSTILL_CAPTURE().equals("Enable")) {
									map1.put("SHS", "stillCapSupHS");
								}
								map.put(formatAndResolutions.getIMAGE_FORMAT(), map1);
							}
							if (formatAndResolutions.getSUPPORTED_IN_SS().equals("Enable")) {
								map1.put("SS", "supportedInSS");
								if (formatAndResolutions.getSTILL_CAPTURE().equals("Enable")) {
									map1.put("SSS", "stillCapSupSS");
								}
								map.put(formatAndResolutions.getIMAGE_FORMAT(), map1);
							}
						}
						if (formatAndResolutions.getRESOLUTION_REGISTER_SETTING() != null) {
							formatAndResolutions.setRESOLUTION_REGISTER_SETTING_LEN(
									formatAndResolutions.getRESOLUTION_REGISTER_SETTING().size());
						} else {
							List<SensorConfig> sensorConfigList = new ArrayList<>();
							formatAndResolutions.setRESOLUTION_REGISTER_SETTING(sensorConfigList);
							formatAndResolutions.setRESOLUTION_REGISTER_SETTING_LEN(0);
						}

					}
				}

				if (ex.get(ex.size() - 1).getRESOLUTION_REGISTER_SETTING_LEN() == 0) {
					SX3Manager.getInstance()
							.addLog("<span style='color:red;'>Error : " + "Endpoint 2 " + " S.No. "
									+ ex.get(ex.size() - 1).getS_NO() + " " + ex.get(ex.size() - 1).getIMAGE_FORMAT()
									+ " Sensor config should not be empty in format and resolution.</span><br>");
				}
				for (Entry<String, Map<String, String>> entry : map.entrySet()) {
					for (Map.Entry<String, String> entry1 : entry.getValue().entrySet()) {
						if (entry1.getValue().equals("supportedInSS")) {
							TOTAL_NUMBER_OF_IMAGE_FORMATS_SS_ENABLED++;
						}
						if (entry1.getValue().equals("supportedInHS")) {
							TOTAL_NUMBER_OF_IMAGE_FORMATS_HS_ENABLED++;
						}
						if (entry1.getValue().equals("supportedInFS")) {
							TOTAL_NUMBER_OF_IMAGE_FORMATS_FS_ENABLED++;
						}
						if (entry1.getValue().equals("stillCapSupFS")) {
							TOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_FS++;
						}
						if (entry1.getValue().equals("stillCapSupHS")) {
							TOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_HS++;
						}
						if (entry1.getValue().equals("stillCapSupSS")) {
							TOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_SS++;
						}
					}

				}
				format_RESOLUTION.setFORMAT_RESOLUTIONS(ex);
				format_RESOLUTION.setTOTAL_NUMBER_OF_RESOLUTIONS(ex.size());
				format_RESOLUTION.setTOTAL_NUMBER_OF_IMAGE_FORMATS_FS_ENABLED(TOTAL_NUMBER_OF_IMAGE_FORMATS_FS_ENABLED);
				format_RESOLUTION.setTOTAL_NUMBER_OF_IMAGE_FORMATS_HS_ENABLED(TOTAL_NUMBER_OF_IMAGE_FORMATS_HS_ENABLED);
				format_RESOLUTION.setTOTAL_NUMBER_OF_IMAGE_FORMATS_SS_ENABLED(TOTAL_NUMBER_OF_IMAGE_FORMATS_SS_ENABLED);
				format_RESOLUTION.setTOTAL_NUMBER_OF_RESOLUTIONS_FS_ENABLED(TOTAL_NUMBER_OF_RESOLUTIONS_FS_ENABLED);
				format_RESOLUTION.setTOTAL_NUMBER_OF_RESOLUTIONS_HS_ENABLED(TOTAL_NUMBER_OF_RESOLUTIONS_HS_ENABLED);
				format_RESOLUTION.setTOTAL_NUMBER_OF_RESOLUTIONS_SS_ENABLED(TOTAL_NUMBER_OF_RESOLUTIONS_SS_ENABLED);
				format_RESOLUTION.setTOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_FS(
						TOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_FS);
				format_RESOLUTION.setTOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_HS(
						TOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_HS);
				format_RESOLUTION.setTOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_SS(
						TOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_SS);
				format_RESOLUTION.setTOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_FS(
						TOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_FS);
				format_RESOLUTION.setTOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_HS(
						TOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_HS);
				format_RESOLUTION.setTOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_SS(
						TOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_SS);
			}
		} else if ((Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UAC"))) {
			UACSetting uac_SETTING = sx3Configuration.getUAC_SETTINGS().get(0).getUAC_SETTING();
			if (uac_SETTING.getAUDIO_FORMAT() != null) {
				errorListOnSave.put("endpoin1AudioFormat", false);
			} else {
				errorListOnSave.put("endpoin1AudioFormat", true);
				SX3Manager.getInstance().addLog(
						"<span style='color:red;'>Error : Audio Format should not be empty in Endpoint 1 UAC Settings.</span><br>");
			}
			if (uac_SETTING.getBIT_RESOLUTION() != null) {
				errorListOnSave.put("endpoin1BitResolution", false);
			} else {
				errorListOnSave.put("endpoin1BitResolution", true);
				SX3Manager.getInstance().addLog(
						"<span style='color:red;'>Error : Bit Resolution should not be empty in Endpoint 1 UAC Settings.</span><br>");
			}
			if (uac_SETTING.getNUMBER_OF_SAMPLING_FREQUENCIES() != 0) {
				errorListOnSave.put("endpoin1SamplingFrequency", false);
			} else {
				errorListOnSave.put("endpoin1SamplingFrequency", true);
				SX3Manager.getInstance().addLog(
						"<span style='color:red;'>Error : Sampling Frequency should not be empty in Endpoint 1 UAC Settings.</span><br>");
			}
		}
		if ((Endpoint_2.getValue() != null && Endpoint_2.getValue().equals("UAC"))) {
			UACSetting uac_SETTING = new UACSetting();
			if (Endpoint_1.getValue().equals("UAC")) {
				uac_SETTING = sx3Configuration.getUAC_SETTINGS().get(1).getUAC_SETTING();
			} else {
				uac_SETTING = sx3Configuration.getUAC_SETTINGS().get(0).getUAC_SETTING();
			}
			if (uac_SETTING.getAUDIO_FORMAT() != null) {
				errorListOnSave.put("endpoin2AudioFormat", false);
			} else {
				errorListOnSave.put("endpoin2AudioFormat", true);
				SX3Manager.getInstance().addLog(
						"<span style='color:red;'>Error : Audio Format should not be empty in Endpoint 2 UAC Settings.</span><br>");
			}
			if (uac_SETTING.getBIT_RESOLUTION() != null) {
				errorListOnSave.put("endpoin2BitResolution", false);
			} else {
				errorListOnSave.put("endpoin2BitResolution", true);
				SX3Manager.getInstance().addLog(
						"<span style='color:red;'>Error : Bit Resolution should not be empty in Endpoint 2 UAC Settings.</span><br>");
			}
			if (uac_SETTING.getNUMBER_OF_SAMPLING_FREQUENCIES() != 0) {
				errorListOnSave.put("endpoin2SamplingFrequency", false);
			} else {
				errorListOnSave.put("endpoin2SamplingFrequency", true);
				SX3Manager.getInstance().addLog(
						"<span style='color:red;'>Error : Sampling Frequency should not be empty in Endpoint 2 UAC Settings.</span><br>");
			}
		}

		if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UVC")) {
			int lengthSS1 = 160
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_SS_ENABLED() * 27)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_RESOLUTIONS_SS_ENABLED() * 30)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_SS() * 7)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_SS() * 4)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_SS_ENABLED());
			usbSetting.setCONFIG_DSCR_LENGTH_SS(lengthSS1);
		}
		if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UVC")) {
			int lengthHS1 = 160
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_HS_ENABLED() * 27)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_RESOLUTIONS_HS_ENABLED() * 30)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_HS() * 7)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_HS() * 4)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_HS_ENABLED());
			usbSetting.setCONFIG_DSCR_LENGTH_HS(lengthHS1);
		}
		if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UVC")) {
			int lengthFS1 = 160
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_FS_ENABLED() * 27)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_RESOLUTIONS_FS_ENABLED() * 30)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_FS() * 7)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_FS() * 4)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_FS_ENABLED());
			usbSetting.setCONFIG_DSCR_LENGTH_FS(lengthFS1);
		}
		if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UAC")) {
			int lengthSS1 = 120
					+ (sx3Configuration.getUAC_SETTINGS().get(0).getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()
							* 3);
			usbSetting.setCONFIG_DSCR_LENGTH_SS(lengthSS1);
		}
		if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UAC")) {
			int lengthHS1 = 114
					+ (sx3Configuration.getUAC_SETTINGS().get(0).getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()
							* 3);
			usbSetting.setCONFIG_DSCR_LENGTH_HS(lengthHS1);
		}
		if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UAC")) {
			int lengthFS1 = 114
					+ (sx3Configuration.getUAC_SETTINGS().get(0).getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()
							* 3);
			usbSetting.setCONFIG_DSCR_LENGTH_FS(lengthFS1);
		}
		if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UVC") && Endpoint_2.getValue() != null
				&& Endpoint_2.getValue().equals("UAC")) {
			int lengthSS1 = 152
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_SS_ENABLED() * 27)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_RESOLUTIONS_SS_ENABLED() * 30)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_SS() * 7)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_SS() * 4)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_SS_ENABLED());
			int lengthSS2 = 120
					+ (sx3Configuration.getUAC_SETTINGS().get(0).getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()
							* 3);
			int total = lengthSS1 + lengthSS2;
			usbSetting.setCONFIG_DSCR_LENGTH_SS(total);
		}
		if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UVC") && Endpoint_2.getValue() != null
				&& Endpoint_2.getValue().equals("UAC")) {
			int lengthHS1 = 152
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_HS_ENABLED() * 27)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_RESOLUTIONS_HS_ENABLED() * 30)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_HS() * 7)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_HS() * 4)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_HS_ENABLED());
			int lengthHS2 = 114
					+ (sx3Configuration.getUAC_SETTINGS().get(0).getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()
							* 3);
			usbSetting.setCONFIG_DSCR_LENGTH_HS(lengthHS1 + lengthHS2);
		}
		if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UVC") && Endpoint_2.getValue() != null
				&& Endpoint_2.getValue().equals("UAC")) {
			int lengthFS1 = 152
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_FS_ENABLED() * 27)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_RESOLUTIONS_FS_ENABLED() * 30)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_WITH_STILL_CAPTURE_ENABLED_FS() * 7)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_RESOLUTIONS_WITH_STILL_CAPTURE_ENABLED_FS() * 4)
					+ (sx3Configuration.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
							.getTOTAL_NUMBER_OF_IMAGE_FORMATS_FS_ENABLED());
			int lengthFS2 = 114
					+ (sx3Configuration.getUAC_SETTINGS().get(0).getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()
							* 3);
			usbSetting.setCONFIG_DSCR_LENGTH_FS(lengthFS1 + lengthFS2);
		}
		if (Endpoint_1.getValue() != null
				&& (Endpoint_1.getValue().equals("IN") || Endpoint_1.getValue().equals("OUT"))) {
			usbSetting.setCONFIG_DSCR_LENGTH_SS(31);
			usbSetting.setCONFIG_DSCR_LENGTH_HS(25);
			usbSetting.setCONFIG_DSCR_LENGTH_FS(25);
		}

		SX3Manager.getInstance().addLog(logDetails2.getText());
		SX3Manager.getInstance().addLog(logDetails3.getText());

		/** Set SX3Configuration data **/
		sx3Configuration.setTOOL("EZ_USB_SX3_CONFIGURATION_UTILITY");
		sx3Configuration.setTOOL_VERSION("1.0_" + RELEASE_DATE);
		sx3Configuration.setCONFIG_TABLE_GENERAL(configTableGeneral);
		sx3Configuration.setCONFIG_TABLE_OFFSET_TABLE(configTableOffSetTable);
		sx3Configuration.setDEVICE_SETTINGS(deviceSettings);
		sx3Configuration.setFIFO_MASTER_CONFIG(fifoMasterConfig);
		List<UACSettings> uac_SETTINGS = sx3Configuration.getUAC_SETTINGS();
		if (uac_SETTINGS != null && uac_SETTINGS.size() != 0) {
			for (int i = 0; i < uac_SETTINGS.size(); i++) {
				int j = i + 1;
				uac_SETTINGS.get(i).getUAC_SETTING().setSAMPLING_FQ_1_NUM_SENSOR_WRITES(
						uac_SETTINGS.get(i).getUAC_SETTING().getSAMPLING_FREQUENCY_1_SENSOR_CONFIG().size());
				uac_SETTINGS.get(i).getUAC_SETTING().setSAMPLING_FQ_2_NUM_SENSOR_WRITES(
						uac_SETTINGS.get(i).getUAC_SETTING().getSAMPLING_FREQUENCY_2_SENSOR_CONFIG().size());
				uac_SETTINGS.get(i).getUAC_SETTING().setSAMPLING_FQ_3_NUM_SENSOR_WRITES(
						uac_SETTINGS.get(i).getUAC_SETTING().getSAMPLING_FREQUENCY_3_SENSOR_CONFIG().size());
				uac_SETTINGS.get(i).getUAC_SETTING().setSAMPLING_FQ_4_NUM_SENSOR_WRITES(
						uac_SETTINGS.get(i).getUAC_SETTING().getSAMPLING_FREQUENCY_4_SENSOR_CONFIG().size());
				if (uac_SETTINGS.get(i).getUAC_SETTING().getSAMPLING_FREQUENCY_1_SENSOR_CONFIG().size() > 0
						&& uac_SETTINGS.get(i).getUAC_SETTING().getSAMPLING_FREQUENCY_1_SENSOR_CONFIG().get(0)
								.getREGISTER_ADDRESS().equals("")) {
					List<SensorConfig> sensorConfigList = new ArrayList<>();
					uac_SETTINGS.get(i).getUAC_SETTING().setSAMPLING_FREQUENCY_1_SENSOR_CONFIG(sensorConfigList);
					uac_SETTINGS.get(i).getUAC_SETTING().setSAMPLING_FQ_1_NUM_SENSOR_WRITES(0);
					errorListOnSave.put("endpoin" + j + "samplingFrequencyConfig", false);
					SX3Manager.getInstance().addLog(
							"<span style='color:red;'>Error : Sampling Frequency Config should not be empty in Endpoint 1 UAC Settings.</span><br>");
				}
				if (uac_SETTINGS.get(i).getUAC_SETTING().getSAMPLING_FREQUENCY_2_SENSOR_CONFIG().size() > 0
						&& uac_SETTINGS.get(i).getUAC_SETTING().getSAMPLING_FREQUENCY_2_SENSOR_CONFIG().get(0)
								.getREGISTER_ADDRESS().equals("")) {
					List<SensorConfig> sensorConfigList = new ArrayList<>();
					uac_SETTINGS.get(i).getUAC_SETTING().setSAMPLING_FREQUENCY_2_SENSOR_CONFIG(sensorConfigList);
					uac_SETTINGS.get(i).getUAC_SETTING().setSAMPLING_FQ_2_NUM_SENSOR_WRITES(0);
				}
				if (uac_SETTINGS.get(i).getUAC_SETTING().getSAMPLING_FREQUENCY_3_SENSOR_CONFIG().size() > 0
						&& uac_SETTINGS.get(i).getUAC_SETTING().getSAMPLING_FREQUENCY_3_SENSOR_CONFIG().get(0)
								.getREGISTER_ADDRESS().equals("")) {
					List<SensorConfig> sensorConfigList = new ArrayList<>();
					uac_SETTINGS.get(i).getUAC_SETTING().setSAMPLING_FREQUENCY_3_SENSOR_CONFIG(sensorConfigList);
					uac_SETTINGS.get(i).getUAC_SETTING().setSAMPLING_FQ_3_NUM_SENSOR_WRITES(0);
				}
				if (uac_SETTINGS.get(i).getUAC_SETTING().getSAMPLING_FREQUENCY_4_SENSOR_CONFIG().size() > 0
						&& uac_SETTINGS.get(i).getUAC_SETTING().getSAMPLING_FREQUENCY_4_SENSOR_CONFIG().get(0)
								.getREGISTER_ADDRESS().equals("")) {
					List<SensorConfig> sensorConfigList = new ArrayList<>();
					uac_SETTINGS.get(i).getUAC_SETTING().setSAMPLING_FREQUENCY_4_SENSOR_CONFIG(sensorConfigList);
					uac_SETTINGS.get(i).getUAC_SETTING().setSAMPLING_FQ_4_NUM_SENSOR_WRITES(0);
				}
			}
		} else {
			sx3Configuration.setUAC_SETTINGS(null);
		}

		/** Set UVC Bit Mask Value data **/
		List<UVCSettings> uvc_SETTINGS = sx3Configuration.getUVC_SETTINGS();
		if (uvc_SETTINGS != null && uvc_SETTINGS.size() != 0) {
			for (int i = 0; i < uvc_SETTINGS.size(); i++) {
				CameraControl camera_CONTROL = uvc_SETTINGS.get(i).getCAMERA_CONTROL();
				String bitmaskCCVal = SX3ConfigHexFileUtil.getBitMaskBinaryValue(camera_CONTROL,
						SX3PropertiesConstants.UVC_SETTINGS_CAMERA_CONTROL_FIELDS[0]);
				camera_CONTROL.setCAMERA_CONTROLS_ENABLED_BITMASK(SX3PropertiesConstants.BINAARY_PREFIX + bitmaskCCVal);
				ProcessingUnitControl processing_UNIT_CONTROL = uvc_SETTINGS.get(i).getPROCESSING_UNIT_CONTROL();
				String bitmaskPUCVal = SX3ConfigHexFileUtil.getBitMaskBinaryValue(processing_UNIT_CONTROL,
						SX3PropertiesConstants.UVC_SETTINGS_PROCESSING_UNIT_CONTROL_FIELDS[0]);
				processing_UNIT_CONTROL.setPROCESSING_UNIT_ENABLED_CONTROLS_BITMASK(
						SX3PropertiesConstants.BINAARY_PREFIX + bitmaskPUCVal);
			}
		} else {
			sx3Configuration.setUVC_SETTINGS(null);
		}
	}

	private void saveConfigurationToFile(String sx3ConfigurationFilePath) {
		SX3ConfigCommonUtil.createSx3JsonFile(sx3Configuration, sx3ConfigurationFilePath);
		SX3Manager.getInstance().addLog("Configuration saved.<br>");
		SX3Manager.getInstance().addLog(sx3ConfigurationFilePath + ".<br>");
		logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

		if (errorListOnSave.containsValue(true)) {
			logTab.setStyle("-fx-border-color:red;");
		} else {
			logTab.setStyle("");
		}

		/** Convert Json to Hex files **/
		SX3Configuration sx3Obj = SX3Manager.getInstance().getSx3Configuration();
		if (sx3Obj != null) {
			// Convert config json to hex file
			SX3ConfigHexFileUtil.convertJsonToHexFile();

		} else {
			BytesStreamsAndHexFileUtil.log("SX3Configuration Object is not created.");
		}
		checkFieldEditOrNot = false;
	}

	/**
	 * load Configuration
	 * 
	 * @throws IOException
	 **/
	@FXML
	public void loadConfiguration() throws IOException {

		if (!checkFieldEditOrNot) {
			loadEndpoints = new ArrayList<>();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("fxmls/LoadConfigurationDialog.fxml"));

			Parent root = loader.load();

			Scene scene = new Scene(root, 408, 181);
			Stage dialogStage = new Stage();
			dialogStage.setResizable(false);
			dialogStage.setTitle(CYPRESS_EZ_USB_SX3_CONFIGURATION_UTILITY_TITLE);
			dialogStage.getIcons().add(new Image("/resources/CyLogo_tree.png"));
			dialogStage.initOwner((Stage) deviceName.getScene().getWindow());
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.setScene(scene);

			LoadConfigurationController loadConfigurationController = (LoadConfigurationController) loader
					.getController();

			dialogStage.showAndWait();

			if (loadConfigurationController.isOkClicked()) {

				String filePath = loadConfigurationController.getImportFromPath();
				String importToLocation = loadConfigurationController.getImportToPath();
				String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);

				if (fileExtension.equalsIgnoreCase("zip")) {
					String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1,
							filePath.lastIndexOf("."));
					ImportExportUtility importExportUtility = new ImportExportUtility();
					String unzipDestination = importToLocation + File.separator + fileName;

					if (importExportUtility.validateZip(filePath)) {
						importExportUtility.unzip(filePath, unzipDestination);

						File[] listOfJsons = new File(unzipDestination).listFiles(new FilenameFilter() {

							@Override
							public boolean accept(File dir, String name) {
								if (name.endsWith("json")) {
									return true;
								}
								return false;
							}
						});

						if (listOfJsons.length == 1) {
							checkConfigurationFile(listOfJsons[0].getAbsolutePath());
						}
					} else {
						showAlertMsg("Invalid SX3 Project. Please load a valid SX3 Project.");
					}

				} else if (fileExtension.equalsIgnoreCase("json")) {
//					File fileToImport = new File(filePath);// not used
//					String fileName = fileToImport.getName();
//					
//					 InputStream is = null;
//					    OutputStream os = null;
//					    
//					    String folderName = fileName.substring(0, fileName.lastIndexOf("."));
//					    File folder = new File(importToLocation+File.separator+folderName);
//					    if(!folder.exists()) {
//					    	folder.mkdir();
//					    }
//					    
//					    String importJsonPath = folder.getAbsolutePath()+File.separator+fileName;
//						try {
//					        is = new FileInputStream(filePath);
//					        os = new FileOutputStream(importJsonPath);
//					        byte[] buffer = new byte[(int) fileToImport.length()];
//					        int length;
//					        while ((length = is.read(buffer)) > 0) {
//					            os.write(buffer, 0, length);
//					        }
//					    } finally {
//					        is.close();
//					        os.close();
//					    }
					checkConfigurationFile(filePath);
				} else {
					SX3Manager.getInstance()
							.addLog("Attempt to load an invalid Configuration. Only JSON File Types are supported.");
				}
			}
		} else {
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setTitle("Warning");
			dialog.setResizable(true);
			dialog.setContentText("Data entered will be lost. Do you want continue?");
			ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
			ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			dialog.getDialogPane().getButtonTypes().add(buttonCancel);
			Optional<ButtonType> showAndWait = dialog.showAndWait();
			if (showAndWait.get() == buttonTypeOk) {
				checkFieldEditOrNot = false;
				loadConfiguration();
			}
			if (showAndWait.get() == buttonCancel) {
				dialog.close();
			}
		}

	}

	public void checkConfigurationFile(String filePath) {
		Gson gson = new Gson();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			sx3Configuration = gson.fromJson(br, SX3Configuration.class);
			SX3Manager.getInstance().setSx3Configuration(sx3Configuration);

			Stage primStage = (Stage) deviceName.getScene().getWindow();
			String sx3ConfigurationFilePath1 = filePath;
			updateUI(filePath, primStage, sx3ConfigurationFilePath1);
		} catch (Exception e) {
			if (e.getMessage().contains("Expected BEGIN_OBJECT but was STRING")) {
				showAlertMsg("Invalid / Corrupted Configuration File Loaded. Please verify the JSON.");
				SX3Manager.getInstance()
						.addLog("Invalid / Corrupted Configuration File Loaded. Please verify the JSON.");
			} else {
				showAlertMsg("Invalid / Corrupted Configuration File Loaded. Please verify the JSON.");
				SX3Manager.getInstance()
						.addLog("Invalid / Corrupted Configuration File Loaded. Please verify the JSON.");
			}
		}

	}

	private void updateUI(String filePath1, Stage primStage, String sx3ConfigurationFilePath1) {
		deviceName.setText("");
		logDetails = new StringBuffer();
		sx3Configuration = SX3Manager.getInstance().getSx3Configuration();
		if (sx3Configuration.getTOOL() != null
				&& sx3Configuration.getTOOL().equals("EZ_USB_SX3_CONFIGURATION_UTILITY")) {
			if (sx3Configuration.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT1_TYPE().equals("UVC")
					&& sx3Configuration.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT2_TYPE().equals("UVC")) {
				showAlertMsg("Wrong combination of endpoin one and endpoint two.");
			} else if (sx3Configuration.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT1_TYPE().equals("UAC")
					&& sx3Configuration.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT2_TYPE().equals("UAC")) {
				showAlertMsg("Wrong combination of endpoin one and endpoint two.");
			} else if (sx3Configuration.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT1_TYPE().equals("UAC")
					&& sx3Configuration.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT2_TYPE().equals("UVC")) {
				showAlertMsg("Wrong combination of endpoin one and endpoint two.");
			} else if (sx3Configuration.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT1_TYPE().equals("OUT")
					&& sx3Configuration.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT2_TYPE().equals("IN")) {
				showAlertMsg("Wrong combination of endpoin one and endpoint two.");
			} else if (sx3Configuration.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT1_TYPE().equals("")) {
				showAlertMsg("Endpoint one should not empty.");
			} else {
				performSaveAction = false;
				/**
				 * ------------- Load Tooltip and error properties file --------------
				 **/
				tooltipAndErrorProperties = SX3ConfigurationTooltipController.getConfigProperties();

				/**
				 * -------- Set tooltip on device settings tab and fifo master tab fields
				 * ---------
				 **/
				setAllToolTip();
				programConfigFromMenuBar.setDisable(false);
				programConfigFromToolBar.setDisable(false);
				primStage.setTitle(CYPRESS_EZ_USB_SX3_CONFIGURATION_UTILITY_TITLE + "-" + sx3ConfigurationFilePath1);
				sx3ConfigurationFilePath = filePath1;
				// Set Config File path to preference
				SX3ConfigPreference.setSx3ConfigFilePathPreference(sx3ConfigurationFilePath);
				videoSourceType = new HashMap<>();
				videoSourceType.put("Endpoint 1", "Image Sensor");
				videoSourceType.put("Endpoint 2", "Image Sensor");
				logDetails3 = new TextArea();
				logDetails2 = new TextArea();
				deviceName.setText(sx3Configuration.getDEVICE_NAME());
				endpointOneChanges = false;
				endpointTwoChanges = false;
				endpointValues = new HashMap<>();
				endpointOneActionPerformed = false;
				endpointTwoActionPerformed = false;
				deviceSettingTabErrorList = new HashMap<>();
				performLoadAction = true;
				deviceSettingsTabSplitpane.setVisible(true);
				welcomeScreen.getChildren().clear();
				welcomeScreen.setVisible(false);
				saveConfigFromToolBar.setDisable(false);
				saveConfigFromMenu.setDisable(false);
				fifoMasterConfigTab.setDisable(false);
				resetDeviceSettings(deviceName.getText());
				DeviceSettings deviceSetting = sx3Configuration.getDEVICE_SETTINGS();
				videoSourceConfigList = sx3Configuration.getVIDEO_SOURCE_CONFIG();
				if (sx3Configuration.getUVC_SETTINGS() != null && !sx3Configuration.getUVC_SETTINGS().isEmpty()) {
					uvcSettingList = sx3Configuration.getUVC_SETTINGS();
					for (UVCSettings uvcSettings : uvcSettingList) {
						FormatAndResolution format_RESOLUTION = uvcSettings.getFORMAT_RESOLUTION();
						List<FormatAndResolutions> format_RESOLUTIONS = format_RESOLUTION.getFORMAT_RESOLUTIONS();
						Collections.sort(format_RESOLUTIONS, FormatAndResolutions.serialNoComparator);
					}
				}
				if (sx3Configuration.getUAC_SETTINGS() != null && !sx3Configuration.getUAC_SETTINGS().isEmpty()) {
					uacSettingList = sx3Configuration.getUAC_SETTINGS();
					int count = 0;
					for (int i = 0; i < sx3Configuration.getUAC_SETTINGS().size(); i++) {
						UACSettings uacSettings = sx3Configuration.getUAC_SETTINGS().get(i);
						if (uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_1() != 0) {
							count++;
						}
						if (uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_2() != 0) {
							count++;
						}
						if (uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_3() != 0) {
							count++;
						}
						if (uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_4() != 0) {
							count++;
						}
						uacSettings.getUAC_SETTING().setNUMBER_OF_SAMPLING_FREQUENCIES(count);
					}
				}
				USBSettings usbSetting = deviceSetting.getUSB_SETTINGS();
				GPIOs gpios = deviceSetting.getGPIOS_SETTINGS();
				DebugLevel debugLevel = deviceSetting.getDEBUG_LEVEL();
				if (!usbSetting.getVENDOR_ID().equals("")) {
					vendorId.setText(usbSetting.getVENDOR_ID().substring(2));
					validateVendorIdField(true);
				}
				if (!usbSetting.getPRODUCT_ID().equals("")) {
					productId.setText(usbSetting.getPRODUCT_ID().substring(2));
					validateProductIdField(true);
				}
				manufacture.setText(usbSetting.getMANUFACTURER_STRING());
				validateManufactureField(true);
				productString.setText(usbSetting.getPRODUCT_STRING());
				validateProductStringField(true);
				if (debugLevel.getAUTO_GENERATE_SERIAL_NUMBER() == 1) {
					serialNumber.setText("");
					serialNumber.setDisable(true);
					serialNumberIncrement.setValue("0");
					serialNumberIncrement.setDisable(true);
					autoGenerateSerialNumber.setSelected(true);
				} else {
					serialNumber.setText(usbSetting.getSERIALNUMBER_STRING());
					validateSerialNumberField(performLoadAction);
					serialNumberIncrement.setValue(String.valueOf(usbSetting.getAUTO_INCREMENT_SERIAL_NUMBER()));
					serialNumberIncrement.setDisable(false);
					autoGenerateSerialNumber.setSelected(false);
					serialNumber.setDisable(false);
				}
				if (usbSetting.getREMOTE_WAKEUP_ENABLE().equals("Enable")) {
					enableRemoteWakeup.setSelected(true);
				} else {
					enableRemoteWakeup.setSelected(false);
				}
				powerConfiguration.setValue(usbSetting.getPOWER_CONFIGURATION());
				fifoBusWidth.setValue(String.valueOf(usbSetting.getFIFO_BUS_WIDTH()));
				if (usbSetting.getFIFO_CLOCK() == 0) {
					fifoClockFrequency.setText("");
				} else {
					fifoClockFrequency.setText(String.valueOf(usbSetting.getFIFO_CLOCK()));
				}
				validateFifoClockFrequencyField(true);
				gpio1.setValue(gpios.getGPIO_0_SETTING());
				gpio2.setValue(gpios.getGPIO_1_SETTING());
				gpio3.setValue(gpios.getGPIO_2_SETTING());
				gpio4.setValue(gpios.getGPIO_3_SETTING());
				gpio5.setValue(gpios.getGPIO_4_SETTING());
				gpio6.setValue(gpios.getGPIO_5_SETTING());
				gpio7.setValue(gpios.getGPIO_6_SETTING());
				if (debugLevel.getDEBUG_ENABLE().equals("Enable")) {
					enableDebugLevel.setSelected(true);
					debugValue.setDisable(false);
					debugValue.setValue(String.valueOf(debugLevel.getDEBUG_LEVEL()));
				} else {
					enableDebugLevel.setSelected(false);
					debugValue.setDisable(true);
					debugValue.setValue("");
				}
				if (usbSetting.getENDPOINT1_TYPE() != null && !usbSetting.getENDPOINT1_TYPE().isEmpty()
						&& usbSetting.getENDPOINT1_TYPE() != "" && usbSetting.getENDPOINT2_TYPE() != null
						&& !usbSetting.getENDPOINT2_TYPE().isEmpty() && usbSetting.getENDPOINT2_TYPE() != "") {
					loadEndpoints.add(usbSetting.getENDPOINT1_TYPE());
					loadEndpoints.add(usbSetting.getENDPOINT2_TYPE());
					endpointOneChanges = true;
					endpointValues.put("Endpoint1", usbSetting.getENDPOINT1_TYPE());
					Endpoint_1.setValue(usbSetting.getENDPOINT1_TYPE());
					if (usbSetting.getENDPOINT2_TYPE().equals("")) {
						Endpoint_2.setValue("Not Enabled");
					} else {
						Endpoint_2.setValue(usbSetting.getENDPOINT2_TYPE());
						endpointValues.put("Endpoint2", usbSetting.getENDPOINT2_TYPE());
						endpointTwoChanges = true;
					}
					if (usbSetting.getENDPOINT1_TYPE().equals("UVC")) {
						videoSourceType.put("Endpoin 1",
								sx3Configuration.getVIDEO_SOURCE_CONFIG().get(0).getVIDEO_SOURCE_TYPE());
					}
				} else if ((usbSetting.getENDPOINT1_TYPE() != null && !usbSetting.getENDPOINT1_TYPE().isEmpty()
						&& usbSetting.getENDPOINT1_TYPE() != "")
						&& (usbSetting.getENDPOINT2_TYPE().isEmpty() || usbSetting.getENDPOINT2_TYPE() == null
								|| usbSetting.getENDPOINT2_TYPE() == "")) {
					loadEndpoints.add(usbSetting.getENDPOINT1_TYPE());
					endpointOneChanges = true;
					endpointValues.put("Endpoint1", usbSetting.getENDPOINT1_TYPE());
					Endpoint_1.setValue(usbSetting.getENDPOINT1_TYPE());
					if (usbSetting.getENDPOINT1_TYPE().equals("UVC")) {
						videoSourceType.put("Endpoin 1",
								sx3Configuration.getVIDEO_SOURCE_CONFIG().get(0).getVIDEO_SOURCE_TYPE());
					}
					Endpoint_2.setValue("Not Enabled");
				} else {
					performLoadAction = false;
				}

				if (deviceSetting.getAUXILLIARY_INTERFACE().getFIRMWARE_UPDATE_HID().equals("Enable")) {
					deviceSttingFirmWare.setSelected(true);
				} else {
					deviceSttingFirmWare.setSelected(false);
				}
				deviceSttingI2CFrequency
						.setValue(String.valueOf(deviceSetting.getAUXILLIARY_INTERFACE().getI2C_FREQUENCY()));

				interFaceType.setValue(usbSetting.getFIFO_INTERFACE_TYPE());
				if (interFaceType.getValue().equals("Slave FIFO Interface")) {
					slaveChange = true;
					imageSensorChangeChange = false;
				} else {
					slaveChange = false;
					imageSensorChangeChange = true;
				}
				if ((usbSetting.getENDPOINT1_TYPE() != null && usbSetting.getENDPOINT1_TYPE().equals("UVC"))
						|| (usbSetting.getENDPOINT2_TYPE() != null && usbSetting.getENDPOINT2_TYPE().equals("UVC"))) {
					if (usbSetting.getUVC_VERSION() != null && !usbSetting.getUVC_VERSION().equals("")) {
						uvcVersion.setValue(usbSetting.getUVC_VERSION());
						uvcHeaderAddition.setValue(usbSetting.getUVC_HEADER_ADDITION());
					}
				}
				FifoMasterConfig fifoMasterConfig = sx3Configuration.getFIFO_MASTER_CONFIG();
				if (fifoMasterConfig.getFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN().equals("Enable")) {
					enableFPGA.setSelected(true);
					fpgaFamily.setValue(fifoMasterConfig.getFPGA_FAMILY());
					File file = new File(SX3Manager.getInstance().getConfigurationLocation().getAbsoluteFile() + "/"
							+ fifoMasterConfig.getBIT_FILE_PATH());
					if (file.exists()) {
						String fileName = file.getAbsolutePath().toString();
						if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
//							String fileExtention = fileName.substring(fileName.lastIndexOf(".") + 1);
//							if (fileExtention.equals("bit")) {
							chooseBitFile.setText(fifoMasterConfig.getBIT_FILE_PATH());
//							} else {
//								showAlertMsg("Select correct bit file.");
//							}
						}
						bitFileSize.setText(String.valueOf(file.length()));
						SX3Manager.getInstance().addLog(new Date() + " EZ-USB SX3 Configuration Utility Launched.<br>");
					} else {
						SX3Manager.getInstance().addLog(new Date() + " EZ-USB SX3 Configuration Utility Launched.<br>");
						SX3Manager.getInstance()
								.addLog("<span style='color:red;'> Bit file not exist in this location ("
										+ fifoMasterConfig.getBIT_FILE_PATH() + ").</span><br>");
					}
				} else {
					enableFPGA.setSelected(false);
					fpgaFamily.setValue(fifoMasterConfig.getFPGA_FAMILY());
					chooseBitFile.setText(fifoMasterConfig.getBIT_FILE_PATH());
					bitFileSize.setText(String.valueOf(0));
				}
				String i2c_SLAVE_ADDRESS = fifoMasterConfig.getI2C_SLAVE_ADDRESS();
				if (i2c_SLAVE_ADDRESS != null) {
					i2cSlaveAddress.setText(!i2c_SLAVE_ADDRESS.isEmpty() ? i2c_SLAVE_ADDRESS.substring(2) : "");
					validateFifoMasterSlaveAddress(true);
				}

				SX3Manager.getInstance().setLogDetails(logDetails);
				SX3Manager.getInstance().addLog("Device Name : " + sx3Configuration.getDEVICE_NAME() + ".<br>");
				SX3Manager.getInstance().addLog("Configuration File Path : " + sx3ConfigurationFilePath + ".<br>");
				SX3Manager.getInstance().addLog("Configuration file loaded Successfully.<br>");
				logDetails1.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");

				logDetails1.getEngine().executeScript("window.scrollTo(0, document.body.scrollHeight)");
				logHelpSplitpane.setMaxHeight(470.0);
				checkFieldEditOrNot = true;
			}
		} else {
			showAlertMsg("Select correct json file.");
		}
	}

	private void showAlertMsg(String msg) {
		Alert a = new Alert(AlertType.ERROR);
		a.setHeaderText(null);
		a.setContentText(msg);
		a.show();
	}

	/** Save Log **/
	@FXML
	public void saveLog() throws IOException {
//		Stage primaryStage = (Stage) deviceName.getScene().getWindow();
//		FileChooser fileChooser = new FileChooser();
//		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("log file (*.txt)", "*.txt");
//		fileChooser.getExtensionFilters().add(extFilter);
		String sx3ConfigFilePath = SX3ConfigPreference.getSx3ConfigFilePathPreference();
		File logFolder = new File(sx3ConfigFilePath).getParentFile();
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
		File logFile = new File(logFolder.getAbsolutePath() + File.separator + "log_" + sx3ConfigFilePath
				.substring(sx3ConfigFilePath.lastIndexOf(File.separator) + 1, sx3ConfigFilePath.lastIndexOf('.')) + "_"
				+ dateFormat.format(new Date()) + ".txt");

		if (!logFile.exists()) {
			logFile.createNewFile();
		}
//		fileChooser.setInitialDirectory(logFolder);
//		if(!logFileName.getText().equals("")) {
//			fileChooser.setInitialFileName(logFileName.getText().replace("\\s", "").replace("Log File:", ""));
//		}
		// Show save file dialog
//		File file = fileChooser.showSaveDialog(primaryStage);
		if (logFile != null) {
			saveTextToFile(
					SX3Manager.getInstance().getLogDetails().toString().replace("<br>", "\n").replace("<b>", "")
							.replace("<span style='color:red;'>", "").replace("</span>", "").replace("</b>", ""),
					logFile);
			logFileName.setText("Log File: " + logFile.getAbsolutePath());
		}
	}

	private void saveTextToFile(String content, File file) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(file);
			writer.println(content);
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/** clear Log Console **/
	@FXML
	public void clearLogConsole() {
		SX3Manager.getInstance().clearLog();

	}

	/**
	 * -------------------------------------------------------- Load Model at the
	 * time of Apllication Start --------------------------
	 **/

	/** Load Help content from file **/
	private void loadHelpContent() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		BufferedReader br;
		try {
			br = new BufferedReader(
					new UnicodeReader(new FileInputStream(new File(getHelpContentFilePath())), "UTF-8"));
			sx3ConfigurationHelp = gson.fromJson(br, SX3ConfiguartionHelp.class);
			SX3Manager.getInstance().setSx3ConfiguartionHelp(sx3ConfigurationHelp);
		} catch (FileNotFoundException e) {
			sx3ConfigurationHelp = new SX3ConfiguartionHelp();
			SX3Manager.getInstance().addLog("<span style='color:red;'>" + e.getMessage() + ".</span><br>");

		}
	}

	/**
	 * Load UVC Endpoint Settings Tab Data
	 * 
	 * @return
	 **/
	private EndpointSettings loadEndpoint(String type) {
		EndpointSettings endpoint = new EndpointSettings();
		if ("UVC".equals(type)) {
			endpoint.setENDPOINT_TRANSFER_TYPE("Bulk");
			endpoint.setRESERVED("FF");
		}
		endpoint.setBURST_LENGTH(1);
		endpoint.setBUFFER_SIZE(16);
		endpoint.setBUFFER_COUNT(1);
		endpoint.setUSED_BUFFER_SPACE(0);
		return endpoint;
	}

	/** Load Format And Resolutions **/
	private FormatAndResolutions loadFormatResolutions() {
		FormatAndResolutions formatResolutionJson = new FormatAndResolutions();
		formatResolutionJson.setIMAGE_FORMAT("");
		formatResolutionJson.setSTILL_CAPTURE("Disable");
		formatResolutionJson.setSUPPORTED_IN_FS("Disable");
		formatResolutionJson.setSUPPORTED_IN_HS("Disable");
		formatResolutionJson.setSUPPORTED_IN_SS("Disable");
		formatResolutionJson.setRESERVED("FF");
		return formatResolutionJson;
	}

	/** Load Color Matching **/
	private ColorMatching loadColorMatching() {
		ColorMatching colormatching = new ColorMatching();
		colormatching.setCOLOR_PRIMARIES("Unspecified (Image characteristics unknown)");
		colormatching.setTRANSFER_CHARACTERISTICS("Unspecified (Image characteristics unknown)");
		colormatching.setMATRIX_COEFFICIENTS("Unspecified (Image characteristics unknown)");
		colormatching.setRESERVED("FF");
		return colormatching;
	}

	/** Load Chennal Configuration **/
	private LinkedHashMap<String, String> loadChannelConfig() {
		LinkedHashMap<String, String> configChennal = new LinkedHashMap<String, String>();
		for (String propertyField : UACSettingFieldConstants.CHANNEL_CONFIGURATION_FIELDS) {
			configChennal.put(propertyField, "Disable");
		}
		return configChennal;
	}

	/** Load Feature Unit Control **/
	private LinkedHashMap<String, String> loadFeatureUnitControl() {
		LinkedHashMap<String, String> featurUnitControl = new LinkedHashMap<String, String>();
		for (String propertyField : UACSettingFieldConstants.FEATURE_UNIT_CONTROLS_FIELDS) {
			featurUnitControl.put(propertyField, "Disable");
		}
		return featurUnitControl;
	}

	/** Camera Control **/
	private List<LinkedHashMap<String, LinkedHashMap<String, Object>>> loadCameraControl1() {
		List<LinkedHashMap<String, LinkedHashMap<String, Object>>> cameraControlJsonList = new ArrayList<>();
		for (int i = 0; i < UVCSettingsConstants.CAMERA_CONTROL_LABEL.length; i++) {
			LinkedHashMap<String, LinkedHashMap<String, Object>> cameraControlObj = new LinkedHashMap<>();
			LinkedHashMap<String, Object> cameraControls = new LinkedHashMap<>();
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_ENABLE", "Disable");
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_MIN", 0.0);
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_MAX", 0.0);
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_STEP", 0.0);
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_DEFAULT", 0.0);
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_CURRENT_VALUE", 0.0);
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_REGISTER_ADDRESS", "");
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_LENGTH", 0.0);
			cameraControls.put("RESERVED", "FF");
			cameraControlObj.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i], cameraControls);
			cameraControlJsonList.add(cameraControlObj);
		}
		return cameraControlJsonList;
	}

	/** Processing Unit Control **/
	private List<LinkedHashMap<String, LinkedHashMap<String, Object>>> loadProcessingUnitControl() {
		List<LinkedHashMap<String, LinkedHashMap<String, Object>>> processingUnitControlJsonList = new ArrayList<>();
		for (int i = 0; i < UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL.length; i++) {
			LinkedHashMap<String, LinkedHashMap<String, Object>> processingUnitControlObj = new LinkedHashMap<>();
			LinkedHashMap<String, Object> processingUnitControlJson = new LinkedHashMap<>();
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_ENABLE",
					"Disable");
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_MIN", 0.0);
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_MAX", 0.0);
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_STEP", 0.0);
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_DEFAULT", 0.0);
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_CURRENT_VALUE",
					0.0);
			processingUnitControlJson
					.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_REGISTER_ADDRESS", "");
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_LENGTH", 0.0);
			processingUnitControlJson.put("RESERVED", "FF");
			processingUnitControlObj.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i],
					processingUnitControlJson);
			processingUnitControlJsonList.add(processingUnitControlObj);
		}
		return processingUnitControlJsonList;
	}

	/** Extension Unit Control **/
	private ExtensionUnitControl loadExtensionUnitControl() {
		ExtensionUnitControl extensionUnitControl = new ExtensionUnitControl();
		extensionUnitControl.setDEVICE_RESET_VENDOR_COMMAND_ENABLED("Disable");
		extensionUnitControl.setI2C_READ_VENDOR_COMMAND_ENABLED("Disable");
		extensionUnitControl.setI2C_WRITE_VENDOR_COMMAND_ENABLED("Disable");
		extensionUnitControl.setFIRMWARE_VERSION_VENDOR_COMMAND_ENABLED("Disable");
		return extensionUnitControl;
	}

	/** Video Source Config **/
	private VideoSourceConfig loadVideoSourceConfig() {
		VideoSourceConfig videoSourceConfig = new VideoSourceConfig();
		videoSourceConfig.setENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD("Disable");
		videoSourceConfig.setVIDEO_SOURCE_TYPE("Image Sensor");
		videoSourceConfig.setVIDEO_SOURCE_SUBTYPE("");
		videoSourceConfig.setI2C_SLAVE_ADDRESS("");
		videoSourceConfig.setI2C_SLAVE_DATA_SIZE(1);
		videoSourceConfig.setI2C_SLAVE_REGISTER_SIZE(1);
		videoSourceConfig.setRESERVED("FF");
		return videoSourceConfig;
	}

	/** HDMI Source Confiduration **/
	private HDMISourceConfiguration loadHDMISourceConfig() {
		HDMISourceConfiguration hdmiSourceConfig = new HDMISourceConfiguration();
		hdmiSourceConfig.setHDMI_SOURCE_REGISTER_ADDRESS("");
		hdmiSourceConfig.setMASK_PATTERN("");
		hdmiSourceConfig.setCOMPARE_VALUE("");
		List<SensorConfig> sensorConfigList = new ArrayList<>();
		SensorConfig sensorConfig = new SensorConfig("", "", "");
		sensorConfigList.add(sensorConfig);
		hdmiSourceConfig.setRESOLUTION_REGISTER_SETTING(sensorConfigList);
		return hdmiSourceConfig;
	}

	/** Slave FIFO Settings **/
	private SlaveFIFOSettings loadSlaveFifoSetting() {
		SlaveFIFOSettings slaveFifo = new SlaveFIFOSettings();
		slaveFifo.setBUFFER_SIZE(16);
		slaveFifo.setBUFFER_COUNT(1);
		slaveFifo.setBURST_LENGTH(1);
		slaveFifo.setRESERVED("FF");
		return slaveFifo;
	}

	@FXML
	public void OpenHelpContentPage() {
//		Dialog<ButtonType> dialog = new Dialog<>();
//		dialog.setWidth(700);
//		dialog.setHeight(500);
//		dialog.setTitle("Sensor Configuration Help");
//		dialog.setResizable(true);
//		HostServices hostServices = application.getHostServices();
//		hostServices.showDocument("Cypress_EZ-USB_SX3_ConfigurationUtility.pdf");
//		dialog.getDialogPane().setContent(new BrowserHelpContent());
//		ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
//		dialog.getDialogPane().getButtonTypes().add(buttonCancel);
//		dialog.show();

		if (!Desktop.isDesktopSupported()) {
			System.out.println("Desktop not supported");
			return;
		}

		if (!Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
			System.out.println("File opening not supported");
			return;
		}

		final Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				try {
					File sx3InstallLocation = SX3Manager.getInstance().getInstallLocation().getParentFile()
							.getAbsoluteFile();
					Desktop.getDesktop().open(new File(
							sx3InstallLocation + File.separator + "Cypress_EZ-USB_SX3_ConfigurationUtility.pdf"));
				} catch (IOException e) {
					System.err.println(e.toString());
				}
				return null;
			}
		};

		final Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	@FXML
	public void showInformationPage() {

		Alert a = new Alert(AlertType.INFORMATION);
		a.setTitle("About Dialog");
		a.setHeaderText("SX3 Configuration Utility");
		a.setContentText("Cypress EZ-USB SX3 Configuration Utility V1.0 - " + RELEASE_DATE
				+ "\nDeveloped by ANCIT CONSULTING" + "\nCopyright \u00a9 2020 - Cypress Semiconductor Pvt Ltd");
		a.show();

	}

	/**
	 * Returns help content file path
	 */
	public static String getHelpContentFilePath() {
		File jarPath = SX3Manager.getInstance().getInstallLocation();
		String sx3helpContentPath = jarPath.getParentFile().getAbsolutePath() + "/SX3_CONFIGURATION_HELP_CONTENT.json";
		return sx3helpContentPath;
	}

	/** ----- Load welcome page html file ------- **/
	private void loadWelcomeContent() {
		webEngine = startScreen.getEngine();
		File jarPath = SX3Manager.getInstance().getInstallLocation();
		String sx3helpContentPath = jarPath.getParentFile().getAbsolutePath() + "/welcomeContent/startPage.html";
		File f = new File(sx3helpContentPath);
		webEngine.load(f.toURI().toString());
	}

	@FXML
	private void exitApplication() {
		if (!checkFieldEditOrNot) {
			System.exit(0);
		} else {
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setTitle("Warning");
			dialog.setResizable(true);
			dialog.setContentText("Data entered will be lost. Do you want to save before close?");
			ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
			ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			dialog.getDialogPane().getButtonTypes().add(buttonCancel);
			Optional<ButtonType> showAndWait = dialog.showAndWait();
			if (showAndWait.get() == buttonTypeOk) {
				checkFieldEditOrNot = false;
				System.exit(0);
			}
			if (showAndWait.get() == buttonCancel) {
				dialog.close();
			}
		}

	}

	@FXML
	private void setDarkStyle() {
		// Remove from scene the theme1(asumming you added to your scene when
		// your app starts)
		application.getScene().getStylesheets().remove(urlDefaultTheme);

		// Add the new theme
		application.getScene().getStylesheets().add(urlDarkTheme);
	}

	@FXML
	private void setDefaultStyle() {
		application.getScene().getStylesheets().remove(urlDarkTheme);

		// Add the Default theme
		application.getScene().getStylesheets().add(urlDefaultTheme);
	}

	@FXML
	private void saveAsConfiguration() {
		
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("fxmls/SaveAsConfigurationDialog.fxml"));

			root = loader.load();

			Scene scene = new Scene(root, 400, 200);
			Stage dialogStage = new Stage();
			dialogStage.setResizable(false);
			dialogStage.setTitle(CYPRESS_EZ_USB_SX3_CONFIGURATION_UTILITY_TITLE);
			dialogStage.getIcons().add(new Image("/resources/CyLogo_tree.png"));
			dialogStage.initOwner((Stage) deviceName.getScene().getWindow());
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.setScene(scene);

			SaveAsConfigurationController newConfigurationController = (SaveAsConfigurationController) loader.getController();
			newConfigurationController.setToolTipAndErrorProperties(tooltipAndErrorProperties);

			dialogStage.showAndWait();

			if (newConfigurationController.isOkClicked()) {

				String fileName = newConfigurationController.getFileName();
				String filePath = newConfigurationController.getFilePath();
				
				File file = new File(filePath + File.separator + fileName);
				file.mkdir();
		
				if (file.exists()) {
					//Creating the Marker File
					File projectFile = new File(file.getAbsolutePath() + File.separator + ".sx3project");
					projectFile.createNewFile();
					
					String newJsonPath = file.getAbsolutePath() + File.separator + fileName + ".json";
					prepareSX3ConfigurationObjectForSave();
					saveConfigurationToFile(newJsonPath);
					
					Stage primStage = (Stage) deviceName.getScene().getWindow();
					primStage.setTitle(CYPRESS_EZ_USB_SX3_CONFIGURATION_UTILITY_TITLE + "-" + newJsonPath);
					sx3ConfigurationFilePath = newJsonPath;
					
					File configurationLocation = SX3Manager.getInstance().getConfigurationLocation();
					File[] listFiles = configurationLocation.listFiles(new FilenameFilter() {
						
						@Override
						public boolean accept(File dir, String name) {
							if(name.endsWith(".json") || name.endsWith(".csv") || name.endsWith(".hex") || name.endsWith(".sx3project") || name.endsWith(".img")) {
								return false;
							}
							return true;
						}
					});
					
					for (File supportingFile : listFiles) {
						try {
							Files.copy(new FileInputStream(supportingFile), Paths.get(file.getAbsolutePath() + File.separator +supportingFile.getName()),
									StandardCopyOption.REPLACE_EXISTING);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void exportConfiguration() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("fxmls/ExportConfigurationDialog.fxml"));

		ExportConfigurationController exportConfigurationController;
		try {
			Parent root = loader.load();

			Scene scene = new Scene(root, 408, 181);
			Stage dialogStage = new Stage();
			dialogStage.setResizable(false);
			dialogStage.setTitle(CYPRESS_EZ_USB_SX3_CONFIGURATION_UTILITY_TITLE);
			dialogStage.getIcons().add(new Image("/resources/CyLogo_tree.png"));
			dialogStage.initOwner((Stage) deviceName.getScene().getWindow());
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.setScene(scene);

			exportConfigurationController = (ExportConfigurationController) loader.getController();

			dialogStage.showAndWait();

			if (exportConfigurationController.isOkClicked()) {
				File toZipFolder = SX3Manager.getInstance().getConfigurationLocation();

				ImportExportUtility zipFiles = new ImportExportUtility();
				String zipDirName = exportConfigurationController.getExportToPath() + File.separator
						+ toZipFolder.getName() + ".zip";
				zipFiles.zipDirectory(toZipFolder, zipDirName);
				
				SX3Manager.getInstance().addLog("Exported SX3 Configuration to "+zipDirName+"<br>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
