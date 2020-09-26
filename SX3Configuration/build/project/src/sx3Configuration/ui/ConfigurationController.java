package sx3Configuration.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;
import javax.usb.UsbException;

import org.configureme.util.UnicodeReader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sx3Configuration.TableModel.CameraAndProcessingUnitControlsTableModel;
import sx3Configuration.TableModel.FormatAndResolutionTableModel;
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
import sx3Configuration.model.ProcessingUnitControl;
import sx3Configuration.model.SX3Configuration;
import sx3Configuration.model.SensorConfig;
import sx3Configuration.model.SlaveFIFOSettings;
import sx3Configuration.model.UACSetting;
import sx3Configuration.model.UACSettings;
import sx3Configuration.model.USBSettings;
import sx3Configuration.model.UVCSettings;
import sx3Configuration.model.VideoSourceConfig;
import sx3Configuration.util.DeviceSettingsConstant;
import sx3Configuration.util.SX3ConfigCommonUtil;
import sx3Configuration.util.SX3ConfiguartionHelp;
import sx3Configuration.util.UACSettingFieldConstants;
import sx3Configuration.util.UVCSettingsConstants;
import ui.validations.DeviceSettingsValidations;
import ui.validations.SX3CommonUIValidations;
import ui.validations.SlaveFifoSettingValidation;
import ui.validations.UVCSettingsValidation;
import ui.validations.VideoSourceConfigValidation;
import utility.tool.merger.BytesStreamsAndHexFileUtil;
import utility.tool.merger.MergeFinalFirmwareArtifacts;
import utility.tool.merger.SX3ConfigHexFileUtil;
import utility.tool.merger.SX3PropertiesConstants;

public class ConfigurationController implements Initializable {

	@FXML
	private ComboBox<String> debugValue, fpgaFamily, i2cFrequency, powerConfiguration, fifoBusWidth, gpio1, gpio2,
			gpio3, gpio4, gpio5, gpio6, gpio7, serialNumberIncrement, interFaceType, uvcVersion, uvcHeaderAddition;

	@FXML
	private ChoiceBox<String> Endpoint_1, Endpoint_2, noOfEndpoint;

	@FXML
	private CheckBox enableDebugLevel, enableFPGA, enableRemoteWakeup, autoGenerateSerialNumber;

	@FXML
	private Label deviceName, logFileName;

	@FXML
	private AnchorPane welcomeScreen;

	// @FXML
	// private WebView helpDetails1;

	@FXML
	private Button newConfig, openConfig, saveConfigFromToolBar, undo, redo, programConfigFromToolBar, browseBitFile,
			saveLog, clearLog;

	@FXML
	private MenuItem saveConfigFromMenu;

	@FXML
	private TextField chooseBitFile, i2cSlaveAddress, vendorId, productId, manufacture, productString, serialNumber,
			fifoClockFrequency, bitFileSize;

	@FXML
	private TabPane videoSourceConfigEndpoint, uvcuacTabpane, videoSourceConfigTabpane, slaveFifoSettings,
			slaveFifoSettingsTabpane;

	@FXML
	private Tab helpTab, deviceSettingsTab, uvcuacSettingsTab, videoSourceConfigTab, fifoMasterConfigTab,
			slaveFifoSettingsTab;

	// @FXML
	// private TextArea helpContent;

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

	private List<UVCSettings> uvcSettingList = new ArrayList<>();

	private List<VideoSourceConfig> videoSourceConfigList = new ArrayList<VideoSourceConfig>(2);

	private List<UACSettings> uacSettingList = new ArrayList<>();

	private boolean performLoadAction = false;

	private List<SlaveFIFOSettings> slaveFifoSettingList = new ArrayList<>();

	private SX3ConfiguartionHelp sx3ConfigurationHelp = new SX3ConfiguartionHelp();

	private List<String> loadEndpoints = new ArrayList<String>();

	private Map<String, Boolean> uvcuacTabErrorList = new HashMap<>();

	private Map<String, Boolean> deviceSettingTabErrorList = new HashMap<>();

	private Map<String, Boolean> fifoMasterTabErrorList = new HashMap<>();

	private Map<String, Boolean> videoSourceConfigTabErrorList = new HashMap<>();

	private Map<String, Boolean> endpointSettingsTabErrorList = new HashMap<>();

	private static USBManager instance;

	private boolean noOfEndpointChanges = false;
	private boolean endpointOneChanges = false;
	private boolean endpointTwoChanges = false;
	private Map<String, String> endpointValues = new HashMap<>();
	private boolean endpointOneActionPerformed = false;
	private boolean endpointTwoActionPerformed = false;
	final ContextMenu numericValidator = new ContextMenu();
	private TextArea logDetails = new TextArea();
	private TextArea logDetails2 = new TextArea();
	private boolean checkFieldEditOrNot = false;
	
	private int bs = 10;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		checkFieldEditOrNot = false;
		endpointOneChanges = false;
		endpointTwoChanges = false;
		noOfEndpointChanges = false;
		deviceSettingsTabSplitpane.setVisible(false);
		welcomeScreen.setVisible(true);
		sx3Configuration = new SX3Configuration();
		uvcuacSettingsTab.setDisable(true);
		videoSourceConfigTab.setDisable(true);
		logDetails.setText(new Date() + " EZ-USB SX3 Configuration Utility Launched.\n<br>");
		logDetails.appendText("Create new EZ-USB SX3 Configuration : Click File &gt; New Configuration.\n<br>");
		logDetails.appendText("Load existing EZ-USB SX3 Configuration : Click File &gt;Load Configuration.\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		Tooltip newConfigToolTip = new Tooltip(DeviceSettingsConstant.NEW_COFIGURATION_TOOLTIP);
		newConfig.setTooltip(newConfigToolTip);
		newConfig.getTooltip().setOnShowing(s -> {
			Bounds bounds = newConfig.localToScreen(newConfig.getBoundsInLocal());
			newConfig.getTooltip().setX(bounds.getMaxX());
		});

		Tooltip openConfigToolTip = new Tooltip(DeviceSettingsConstant.OPEN_COFIGURATION_TOOLTIP);
		openConfig.setTooltip(openConfigToolTip);
		openConfig.getTooltip().setOnShowing(s -> {
			Bounds bounds = openConfig.localToScreen(openConfig.getBoundsInLocal());
			openConfig.getTooltip().setX(bounds.getMaxX());
		});

		Tooltip saveConfigToolTip = new Tooltip(DeviceSettingsConstant.SAVE_COFIGURATION_TOOLTIP);
		saveConfigFromToolBar.setTooltip(saveConfigToolTip);
		saveConfigFromToolBar.getTooltip().setOnShowing(s -> {
			Bounds bounds = saveConfigFromToolBar.localToScreen(saveConfigFromToolBar.getBoundsInLocal());
			saveConfigFromToolBar.getTooltip().setX(bounds.getMaxX());
		});

		Tooltip undoToolTip = new Tooltip(DeviceSettingsConstant.UNDO);
		undo.setTooltip(undoToolTip);
		undo.getTooltip().setOnShowing(s -> {
			Bounds bounds = undo.localToScreen(undo.getBoundsInLocal());
			undo.getTooltip().setX(bounds.getMaxX());
		});

		Tooltip redoToolTip = new Tooltip(DeviceSettingsConstant.REDO);
		redo.setTooltip(redoToolTip);
		redo.getTooltip().setOnShowing(s -> {
			Bounds bounds = redo.localToScreen(redo.getBoundsInLocal());
			redo.getTooltip().setX(bounds.getMaxX());
		});

		Tooltip programConfigToolTip = new Tooltip(DeviceSettingsConstant.PROGRAM_COFIGURATION_TOOLTIP);
		programConfigFromToolBar.setTooltip(programConfigToolTip);
		programConfigFromToolBar.getTooltip().setOnShowing(s -> {
			Bounds bounds = programConfigFromToolBar.localToScreen(programConfigFromToolBar.getBoundsInLocal());
			programConfigFromToolBar.getTooltip().setX(bounds.getMaxX());
		});

		Tooltip saveLogToolTip = new Tooltip(DeviceSettingsConstant.SAVE_LOG);
		saveLog.setTooltip(saveLogToolTip);
		saveLog.getTooltip().setOnShowing(s -> {
			Bounds bounds = saveLog.localToScreen(saveLog.getBoundsInLocal());
			saveLog.getTooltip().setX(bounds.getMaxX());
		});

		Tooltip clearLogToolTip = new Tooltip(DeviceSettingsConstant.CLEAR_LOG);
		clearLog.setTooltip(clearLogToolTip);
		clearLog.getTooltip().setOnShowing(s -> {
			Bounds bounds = clearLog.localToScreen(clearLog.getBoundsInLocal());
			clearLog.getTooltip().setX(bounds.getMaxX());
		});

		setAllToolTip();

		logDeviceSettingTabField();

		loadWelcomeContent();

		addEndpoints();

		selectEndpoint();

		selectEndpointTwo();
	}

	private void logDeviceSettingTabField() {

		DeviceSettingsController.logVendorID(deviceSettingTabErrorList, deviceSettingsTab, vendorId, logDetails,
				logDetails1);
		DeviceSettingsController.logProductID(deviceSettingTabErrorList, deviceSettingsTab, productId, logDetails,
				logDetails1);
		DeviceSettingsController.logManufacture(deviceSettingTabErrorList, deviceSettingsTab, manufacture, logDetails,
				logDetails1);
		DeviceSettingsController.logProductString(deviceSettingTabErrorList, deviceSettingsTab, productString,
				logDetails, logDetails1);
		DeviceSettingsController.logSerialNumber(deviceSettingTabErrorList, deviceSettingsTab, serialNumber, logDetails,
				logDetails1);
		DeviceSettingsController.logFifoClockFrequency(deviceSettingTabErrorList, deviceSettingsTab, fifoClockFrequency,
				logDetails, logDetails1);
		DeviceSettingsController.logFPGAI2CSlaveAddress(deviceSettingTabErrorList, deviceSettingsTab, i2cSlaveAddress,
				logDetails, logDetails1);
	}

	@FXML
	public void vendorIdValidation() {
		validateVendorIdField(false);
	}

	public void validateVendorIdField(boolean performLoadAction) {
		/** Vendor ID **/
		if (vendorId.getText() != null && !vendorId.getText().isEmpty()) {
			checkFieldEditOrNot = true;
			DeviceSettingsValidations.setupValidationForTextField(deviceSettingTabErrorList, deviceSettingsTab,
					vendorId, DeviceSettingsValidations.INVALID_HEX_ERROR_MESSAGE, 4, "VendorID", performLoadAction);
		}
	}

	@FXML
	public void productIdValidation() {
		validateProductIdField(false);
	}

	public void validateProductIdField(boolean performLoadAction) {
		if (productId.getText() != null && !productId.getText().isEmpty()) {
			checkFieldEditOrNot = true;
		}
		DeviceSettingsValidations.setupValidationForTextField(deviceSettingTabErrorList, deviceSettingsTab, productId,
				DeviceSettingsValidations.INVALID_HEX_ERROR_MESSAGE, 4, "ProductID", performLoadAction);
	}

	@FXML
	public void manufactureValidation() {
		validateManufactureField(false);
	}

	public void validateManufactureField(boolean performLoadAction) {
		checkFieldEditOrNot = true;
		/** Manufacture **/
		DeviceSettingsValidations.setupValidationForAlphNumericTextField(deviceSettingTabErrorList, deviceSettingsTab,
				manufacture, DeviceSettingsValidations.INVALID_ALPHANUMERIC_ERROR_MESSAGE, "Manufacture",
				performLoadAction, 16);
	}

	@FXML
	public void productStringValidation() {
		validateProductStringField(false);
	}

	public void validateProductStringField(boolean performLoadAction) {
		checkFieldEditOrNot = true;
		/** Product String **/
		DeviceSettingsValidations.setupValidationForAlphNumericTextField(deviceSettingTabErrorList, deviceSettingsTab,
				productString, DeviceSettingsValidations.INVALID_ALPHANUMERIC_ERROR_MESSAGE, "ProductString",
				performLoadAction, 16);
	}

	@FXML
	public void serialNumberValidation() {
		validateSerialNumberField(false);
	}

	public void validateSerialNumberField(boolean performLoadAction) {
		checkFieldEditOrNot = true;
		/** Serial Number **/
		DeviceSettingsValidations.validateSerialNumberTextField(deviceSettingTabErrorList, deviceSettingsTab,
				serialNumber, DeviceSettingsValidations.INVALID_ALPHANUMERIC_ERROR_MESSAGE, "SerialNumber", 16,
				performLoadAction);
	}

	@FXML
	public void fifoClockFrequencyValidation() {
		validateFifoClockFrequencyField(false);
	}

	public void validateFifoClockFrequencyField(boolean performLoadAction) {
		checkFieldEditOrNot = true;
		/** FIFO Clock Frequency **/
		DeviceSettingsValidations.validateFifoClockFrequencyTextField(fifoClockFrequency,
				DeviceSettingsValidations.INVALID_NUMERIC_ERROR_MESSAGE, 100);
	}

	@FXML
	public void fifoMasterSlaveAddressValidation() {
		validateFifoMasterSlaveAddress(false);
	}

	public void validateFifoMasterSlaveAddress(boolean b) {
		checkFieldEditOrNot = true;
		/** I2C Slave Address **/
		DeviceSettingsValidations.setupValidationForTextField(fifoMasterTabErrorList, fifoMasterConfigTab,
				i2cSlaveAddress, DeviceSettingsValidations.INVALID_HEX_ERROR_MESSAGE, 2, "I2CSlaveAddress",
				performLoadAction);
	}

	/**
	 * ------------------------------------ Device Settings Tab
	 * ------------------------------------------------
	 **/

	/** Set Tooltip On all fields **/
	private void setAllToolTip() {
		DeviceSettingsController.setAllToolTip(deviceName.getText(), vendorId, productId, manufacture, productString,
				autoGenerateSerialNumber, serialNumber, serialNumberIncrement, enableRemoteWakeup, powerConfiguration,
				noOfEndpoint, Endpoint_1, fifoBusWidth, fifoClockFrequency, enableDebugLevel, debugValue, gpio1, gpio2,
				gpio3, gpio4, gpio5, gpio6, gpio7, interFaceType, uvcVersion, uvcHeaderAddition, enableFPGA, fpgaFamily,
				browseBitFile, i2cSlaveAddress, i2cFrequency);
	}

	/** Enable Disable Remote Wakeup **/
	@FXML
	public void logRemoteWakeup() {
		checkFieldEditOrNot = true;
		if (enableRemoteWakeup.isSelected()) {
			logDetails.appendText("Enable Remote Wakeup : Enable.\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		} else {
			logDetails.appendText("Enable Remote Wakeup : Disable.\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		}
	}

	/** Enable Disable Debug Level **/
	@FXML
	public void enableDebugLevel() {
		checkFieldEditOrNot = true;
		if (enableDebugLevel.isSelected()) {
			debugValue.setDisable(false);
			logDetails.appendText("Debug Level : Enable.\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		} else {
			debugValue.setDisable(true);
			logDetails.appendText("Debug Level : Disable.\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		}
	}

	@FXML
	public void logAutoGenerateSerialNo() {
		checkFieldEditOrNot = true;
		if (autoGenerateSerialNumber.isSelected()) {
			logDetails.appendText("Auto-Generate Serial Number : Enable.\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			serialNumber.setDisable(true);
			serialNumber.setText("");
			serialNumber.setStyle("");
			serialNumberIncrement.setDisable(true);
			serialNumberIncrement.setValue(String.valueOf(0));
		} else {
			logDetails.appendText("Auto-Generate Serial Number : Disable.\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			serialNumber.setDisable(false);
			serialNumberIncrement.setDisable(false);
		}
	}

	@FXML
	public void selectUVCUACTab() {
		if (uvcuacSettingsTab.isSelected()) {
			if (noOfEndpoint.getValue().equals("2")) {
				endpointOneChanges = true;
				noOfEndpointChanges = true;
				endpointValues.put("Endpoint1", Endpoint_1.getValue());
				endpointValues.put("Endpoint2", Endpoint_2.getValue());
				endpointTwoChanges = true;

			} else {
				endpointOneChanges = true;
				endpointValues.put("Endpoint1", Endpoint_1.getValue());
			}
		}
	}

	@FXML
	public void selectSlaveFifoSettingsTab() {
		if (slaveFifoSettingsTab.isSelected()) {
			if (noOfEndpoint.getValue().equals("2")) {
				endpointOneChanges = true;
				noOfEndpointChanges = true;
				endpointValues.put("Endpoint1", Endpoint_1.getValue());
				endpointValues.put("Endpoint2", Endpoint_2.getValue());
				endpointTwoChanges = true;
			} else {
				endpointOneChanges = true;
				endpointValues.put("Endpoint1", Endpoint_1.getValue());
			}
		}
	}

	private void addEndpoints() {
		ChangeListener<String> changeListener = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				logDetails.appendText("No. of Endpoint : " + newValue + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				if (sx3Configuration.getDEVICE_NAME().equals("SX3 UVC")) {
					if (!noOfEndpoint.getValue().equals("1") && Endpoint_1.getValue() != null) {
						uvcuacSettingsTab.setDisable(true);
						Endpoint_2.setDisable(false);
						endpointValues.put("NoOfEndpoints", noOfEndpoint.getValue());
					} else if (noOfEndpointChanges && noOfEndpoint.getValue().equals("1")
							&& Endpoint_1.getValue() != null) {
						Dialog<ButtonType> dialog = new Dialog<>();
						dialog.setTitle("Warning");
						dialog.setResizable(true);
						dialog.setContentText("Data entered in Endpoint 2 - " + endpointValues.get("Endpoint2")
								+ " tab will be lost. Do you want continue?");
						ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
						dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
						ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
						dialog.getDialogPane().getButtonTypes().add(buttonCancel);
						Optional<ButtonType> showAndWait = dialog.showAndWait();
						if (showAndWait.get() == buttonTypeOk) {
							loadOnEndpointNumberChange();
							endpointValues.put("NoOfEndpoints", noOfEndpoint.getValue());
							endpointTwoChanges = false;
						}
						if (showAndWait.get() == buttonCancel) {
							dialog.close();
							noOfEndpoint.setValue(oldValue);
						}
					} else {
						if (noOfEndpoint.getValue().equals("1")) {
							Endpoint_2.setDisable(true);
							loadOnEndpointNumberChange();
						}
					}
				} else {
					int noOfEndpoints = Integer.parseInt(noOfEndpoint.getValue());
					if (noOfEndpoints > 1 && Endpoint_1.getValue() != null) {
						slaveFifoSettingsTab.setDisable(false);
						Endpoint_2.setDisable(false);
						if (Endpoint_2.getValue() == null) {
							slaveFifoSettingsTab.setDisable(true);
						}
					} else if (noOfEndpointChanges && noOfEndpoints == 1 && Endpoint_1.getValue() != null) {
						Dialog<ButtonType> dialog = new Dialog<>();
						dialog.setTitle("Warning");
						dialog.setResizable(true);
						dialog.setContentText("Data entered in Endpoint 2 - " + endpointValues.get("Endpoint2")
								+ " tab will be lost. Do you want continue?");
						ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
						dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
						ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
						dialog.getDialogPane().getButtonTypes().add(buttonCancel);
						Optional<ButtonType> showAndWait = dialog.showAndWait();
						if (showAndWait.get() == buttonTypeOk) {
							loadSlaveFifoSettingsOnEndpointNumberChange();
							endpointValues.put("NoOfEndpoints", noOfEndpoint.getValue());
						}
						if (showAndWait.get() == buttonCancel) {
							dialog.close();
							noOfEndpoint.setValue(oldValue);
						}
					} else {
						if (noOfEndpoint.getValue().equals("1")) {
							Endpoint_2.setDisable(true);
							loadSlaveFifoSettingsOnEndpointNumberChange();
						}
					}
				}
			}
		};
		noOfEndpoint.getSelectionModel().selectedItemProperty().addListener(changeListener);

	}

	protected void loadSlaveFifoSettingsOnEndpointNumberChange() {
		checkFieldEditOrNot = true;
		slaveFifoSettingsTab.setDisable(false);
		Endpoint_2.setDisable(true);
		if (slaveFifoSettingList.size() > 1) {
			slaveFifoSettingList.remove(1);
			slaveFifoSettingsTabpane.getTabs().remove(1);
			Endpoint_2.setValue(null);
		} else if (slaveFifoSettingList.size() == 1) {
			slaveFifoSettingList.remove(0);
			slaveFifoSettingsTabpane.getTabs().remove(0);
			Endpoint_2.setValue(null);
		}

	}

	private void loadOnEndpointNumberChange() {
		checkFieldEditOrNot = true;
		uvcuacSettingsTab.setDisable(false);
		Endpoint_2.setValue(null);
		Endpoint_2.setDisable(true);
		if (uvcSettingList.size() > 1) {
			uvcSettingList.remove(1);
			videoSourceConfigList.remove(1);
			uvcuacTabpane.getTabs().remove(1);
			videoSourceConfigTabpane.getTabs().remove(1);
		} else if (uvcSettingList.size() == 1) {
			uvcSettingList.remove(0);
			videoSourceConfigList.remove(0);
			uvcuacTabpane.getTabs().remove(1);
			videoSourceConfigTabpane.getTabs().remove(0);
		} else if (uacSettingList.size() > 1) {
			uacSettingList.remove(1);
			uvcuacTabpane.getTabs().remove(1);
		} else if (uacSettingList.size() == 1) {
			uacSettingList.remove(0);
			uvcuacTabpane.getTabs().remove(1);
		}
		if ((Endpoint_1.getValue() != null && Endpoint_1.getValue().equals(DeviceSettingsConstant.UAC))
				&& (Endpoint_2.getValue() != null && Endpoint_2.getValue().equals(DeviceSettingsConstant.UAC))) {
			videoSourceConfigTab.setDisable(true);
			uvcHeaderAddition.setValue("");
			uvcHeaderAddition.setDisable(true);
			uvcVersion.setValue("");
			uvcVersion.setDisable(true);
		} else if ((Endpoint_1.getValue() != null && Endpoint_1.getValue().equals(DeviceSettingsConstant.UAC))
				&& Endpoint_2.getValue() == null) {
			videoSourceConfigTab.setDisable(true);
			uvcHeaderAddition.setValue("");
			uvcHeaderAddition.setDisable(true);
			uvcVersion.setValue("");
			uvcVersion.setDisable(true);
		}

	}

	/** Endpoint One **/
	public void selectEndpoint() {
		ChangeListener<String> changeListener = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (Endpoint_1.getValue() != null) {
					if (endpointOneChanges && !endpointValues.get("Endpoint1").equals(Endpoint_1.getValue())) {
						Dialog<ButtonType> dialog = new Dialog<>();
						dialog.setTitle("Warning");
						dialog.setResizable(true);
						dialog.setContentText("Data entered in Endpoint 1 - " + endpointValues.get("Endpoint1")
								+ " tab will be lost. Do you want continue?");
						ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
						dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
						ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
						dialog.getDialogPane().getButtonTypes().add(buttonCancel);
						Optional<ButtonType> showAndWait = dialog.showAndWait();
						if (showAndWait.get() == buttonTypeOk) {
							loadNewEndPoint1();
						}
						if (showAndWait.get() == buttonCancel) {
							dialog.close();
							endpointOneActionPerformed = true;
							Endpoint_1.setValue(oldValue);
						}
					} else {
						if (!endpointOneActionPerformed) {
							loadNewEndPoint1();
						}
					}
				}
			}
		};
		Endpoint_1.getSelectionModel().selectedItemProperty().addListener(changeListener);
	}

	private void loadNewEndPoint1() {
		if (loadEndpoints != null && !loadEndpoints.isEmpty() && !loadEndpoints.get(0).equals(Endpoint_1.getValue())) {
			performLoadAction = false;
			if (loadEndpoints.get(0).equals("UVC")) {
				sx3Configuration.getUVC_SETTINGS().remove(0);
				sx3Configuration.getVIDEO_SOURCE_CONFIG().remove(0);
				loadEndpoints.remove(0);
			} else if (loadEndpoints.get(0).equals("UAC")) {
				sx3Configuration.getUAC_SETTINGS().remove(0);
				loadEndpoints.remove(0);
			} else if (loadEndpoints.get(0).equals("IN")) {
				sx3Configuration.getSLAVE_FIFO_SETTINGS().remove(0);
				loadEndpoints.remove(0);
			} else if (loadEndpoints.get(0).equals("OUT")) {
				sx3Configuration.getSLAVE_FIFO_SETTINGS().remove(0);
				loadEndpoints.remove(0);
			}
		}
		checkFieldEditOrNot = true;
		logDetails.appendText("Endpoint 1 : " + Endpoint_1.getValue() + ".\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		if (deviceName.getText().equals("SX3 UVC")) {
			if (!noOfEndpoint.getValue().equals("1") && !Endpoint_1.getValue().equals("")) {
				Endpoint_2.setDisable(false);
			}
			uvcuacSettingsTab.setDisable(false);
			Tab tabOne = new Tab();
			tabOne.setClosable(false);
			tabOne.setText("Endpoint 1 -" + Endpoint_1.getValue());
			if (!uvcuacTabpane.getTabs().isEmpty()) {
				uvcuacTabpane.getTabs().set(0, tabOne);
			} else {
				uvcuacTabpane.getTabs().add(tabOne);
			}
			if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals(DeviceSettingsConstant.UVC)) {
				createUVCUI(tabOne, uvcSettingList, 0);
				videoSourceConfigTab.setDisable(false);
				Tab videoConfigTab = new Tab();
				videoConfigTab.setText("Endpoint 1 -" + Endpoint_1.getValue());
				videoSourceConfigTabpane.getTabs().add(videoConfigTab);
				createVideoSourceConfig(videoConfigTab, videoSourceConfigList, 0);
				// if (videoSourceConfigList.isEmpty()) {
				// createVideoSourceConfig(videoConfigTab,
				// videoSourceConfigList, 0);
				// } else {
				// createVideoSourceConfig(videoConfigTab,
				// videoSourceConfigList, 1);
				// }
				uvcVersion.setDisable(false);
				uvcHeaderAddition.setDisable(false);
				if (uacSettingList.size() > 1) {
					uacSettingList.remove(0);
				}
			} else {
				createUACUI(tabOne, uacSettingList, 0);
				if (!videoSourceConfigTabpane.getTabs().isEmpty()) {
					videoSourceConfigTabpane.getTabs().remove(0);
					if (!videoSourceConfigList.isEmpty()) {
						videoSourceConfigList.remove(0);
						uvcSettingList.remove(0);
					}
				}
				if (Endpoint_2.getValue() == null || Endpoint_2.getValue().equals("")
						|| Endpoint_2.getValue().equals(DeviceSettingsConstant.UAC)) {
					videoSourceConfigTab.setDisable(true);
					uvcVersion.setValue(null);
					uvcVersion.setDisable(true);
					uvcHeaderAddition.setValue(null);
					uvcHeaderAddition.setDisable(true);
				}
			}
		} else {
			Tab tabOne = new Tab();
			tabOne.setClosable(false);
			tabOne.setText("Endpoint 1 -" + Endpoint_1.getValue());
			slaveFifoSettingsTab.setDisable(false);
			if (performLoadAction == false) {
				if (!slaveFifoSettingsTabpane.getTabs().isEmpty()) {
					slaveFifoSettingList.remove(0);
					slaveFifoSettingsTabpane.getTabs().set(0, tabOne);
				} else {
					slaveFifoSettingsTabpane.getTabs().add(tabOne);
				}
				SlaveFIFOSettings slaveFifo = loadSlaveFifoSetting();
				slaveFifoSettingList.add(slaveFifo);
				sx3Configuration.setSLAVE_FIFO_SETTINGS(slaveFifoSettingList);
				if (slaveFifoSettingList.size() == 1) {
					createSlaveFIFOSettingsUI(slaveFifoSettingList.get(0), tabOne);
				} else {
					createSlaveFIFOSettingsUI(slaveFifoSettingList.get(1), tabOne);
				}
			} else {
				slaveFifoSettingsTabpane.getTabs().add(tabOne);
				slaveFifoSettingList = sx3Configuration.getSLAVE_FIFO_SETTINGS();
				createSlaveFIFOSettingsUI(slaveFifoSettingList.get(0), tabOne);
			}
			if (!noOfEndpoint.getValue().equals("1") && !Endpoint_1.getValue().equals("")) {
				Endpoint_2.setDisable(false);
			}
		}

	}

	/** Endpoint Two **/
	public void selectEndpointTwo() {
		ChangeListener<String> changeListener = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (Endpoint_2.getValue() != null) {
					if (endpointTwoChanges && !endpointValues.get("Endpoint2").equals(Endpoint_2.getValue())) {
						Dialog<ButtonType> dialog = new Dialog<>();
						dialog.setTitle("Warning");
						dialog.setResizable(true);
						dialog.setContentText("Data entered in Endpoint 2 - " + endpointValues.get("Endpoint2")
								+ " tab will be lost. Do you want continue?");
						ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
						dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
						ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
						dialog.getDialogPane().getButtonTypes().add(buttonCancel);
						Optional<ButtonType> showAndWait = dialog.showAndWait();
						if (showAndWait.get() == buttonTypeOk) {
							loadNewEndPoint2();
						}
						if (showAndWait.get() == buttonCancel) {
							dialog.close();
							endpointTwoActionPerformed = true;
							Endpoint_2.setValue(oldValue);
						}
					} else {
						if (!endpointTwoActionPerformed) {
							loadNewEndPoint2();
						}
					}
				}
			}
		};
		Endpoint_2.getSelectionModel().selectedItemProperty().addListener(changeListener);
	}

	private void loadNewEndPoint2() {
		if (loadEndpoints.size() > 1 && loadEndpoints != null && !loadEndpoints.isEmpty()
				&& !loadEndpoints.get(1).equals(Endpoint_2.getValue())) {
			performLoadAction = false;
			if (loadEndpoints.get(1).equals("UVC")) {
				loadEndpoints.remove(1);
				if (sx3Configuration.getUVC_SETTINGS() != null && sx3Configuration.getUVC_SETTINGS().size() > 1) {
					sx3Configuration.getUVC_SETTINGS().remove(1);
					sx3Configuration.getVIDEO_SOURCE_CONFIG().remove(1);
				} else if (sx3Configuration.getUVC_SETTINGS() != null
						&& sx3Configuration.getUVC_SETTINGS().size() == 1) {
					sx3Configuration.getUVC_SETTINGS().remove(0);
					sx3Configuration.getVIDEO_SOURCE_CONFIG().remove(0);
				}
			} else if (loadEndpoints.get(1).equals("UAC")) {
				loadEndpoints.remove(1);
				if (sx3Configuration.getUAC_SETTINGS() != null && sx3Configuration.getUAC_SETTINGS().size() > 1) {
					sx3Configuration.getUAC_SETTINGS().remove(1);
				} else if (sx3Configuration.getUAC_SETTINGS() != null
						&& sx3Configuration.getUAC_SETTINGS().size() == 1) {
					sx3Configuration.getUAC_SETTINGS().remove(0);
				}
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

		} else if (loadEndpoints.size() == 1) {
			performLoadAction = false;
		}
		checkFieldEditOrNot = true;
		logDetails.appendText("Endpoint 2 : " + Endpoint_2.getValue() + ".\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		if (deviceName.getText().equals("SX3 UVC")) {
			uvcuacSettingsTab.setDisable(false);
			Tab tabTwo = new Tab();
			tabTwo.setClosable(false);
			tabTwo.setText("Endpoint 2 -" + Endpoint_2.getValue());
			Tab videoConfigTab = new Tab();
			if (uvcuacTabpane.getTabs().size() > 1) {
				uvcuacTabpane.getTabs().set(1, tabTwo);
			} else {
				uvcuacTabpane.getTabs().add(tabTwo);
			}
			if (Endpoint_2.getValue() != null && Endpoint_2.getValue().equals(DeviceSettingsConstant.UVC)) {
				createUVCUI(tabTwo, uvcSettingList, 1);
				videoSourceConfigTab.setDisable(false);
				videoConfigTab.setText("Endpoint 2 -" + Endpoint_2.getValue());
				videoSourceConfigTabpane.getTabs().add(videoConfigTab);
				createVideoSourceConfig(videoConfigTab, videoSourceConfigList, 1);
				// if (videoSourceConfigList.isEmpty()) {
				// createVideoSourceConfig(videoConfigTab,
				// videoSourceConfigList, 0);
				// } else {
				// createVideoSourceConfig(videoConfigTab,
				// videoSourceConfigList, 1);
				// }
				uvcVersion.setDisable(false);
				uvcHeaderAddition.setDisable(false);
				if (uacSettingList.size() > 1) {
					uacSettingList.remove(1);
				}
			} else {
				createUACUI(tabTwo, uacSettingList, 1);
				if (videoSourceConfigTabpane.getTabs().size() > 1) {
					videoSourceConfigTabpane.getTabs().remove(1);
					videoSourceConfigList.remove(1);
					uvcSettingList.remove(1);
				}
				if (Endpoint_1.getValue() != null && Endpoint_1.getValue().equals(DeviceSettingsConstant.UAC)) {
					videoSourceConfigTab.setDisable(true);
					uvcVersion.setValue(null);
					uvcVersion.setDisable(true);
					uvcHeaderAddition.setValue(null);
					uvcHeaderAddition.setDisable(true);
				}
			}
		} else {
			slaveFifoSettingsTab.setDisable(false);
			Tab tabTwo = new Tab();
			tabTwo.setClosable(false);
			tabTwo.setText("Endpoint 2 -" + Endpoint_2.getValue());
			if (performLoadAction == false) {
				if (slaveFifoSettingsTabpane.getTabs().size() > 1) {
					slaveFifoSettingsTabpane.getTabs().set(1, tabTwo);
				} else {
					slaveFifoSettingsTabpane.getTabs().add(tabTwo);
				}
				SlaveFIFOSettings slaveFifo = loadSlaveFifoSetting();
				slaveFifoSettingList.add(slaveFifo);
				sx3Configuration.setSLAVE_FIFO_SETTINGS(slaveFifoSettingList);
				if (slaveFifoSettingList.size() == 1) {
					createSlaveFIFOSettingsUI(slaveFifoSettingList.get(0), tabTwo);
				} else {
					createSlaveFIFOSettingsUI(slaveFifoSettingList.get(1), tabTwo);
				}
			} else {
				slaveFifoSettingsTabpane.getTabs().add(tabTwo);
				slaveFifoSettingList = sx3Configuration.getSLAVE_FIFO_SETTINGS();
				createSlaveFIFOSettingsUI(slaveFifoSettingList.get(1), tabTwo);
			}

		}

	}

	/**
	 * ------------------------------------------------------- UVC Settings Tab
	 * --------------------------------------------
	 * 
	 * @param endpointOneChanges2
	 **/

	private void createUVCUI(Tab tab, List<UVCSettings> uvcSettingList, int i) {
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
			List<Map<String, Map<String, Object>>> cameraControlList = loadCameraControl1();
			cameraControl.setCAMERA_CONTROLS(cameraControlList);
			uvcSettings.setCAMERA_CONTROL(cameraControl);
			ProcessingUnitControl processingUnnitControl = new ProcessingUnitControl();
			List<Map<String, Map<String, Object>>> processingUnitControlJson = loadProcessingUnitControl();
			processingUnnitControl.setPROCESSING_UNIT_CONTROLS(processingUnitControlJson);
			uvcSettings.setPROCESSING_UNIT_CONTROL(processingUnnitControl);
			ExtensionUnitControl extensionUnitControl = loadExtensionUnitControl();
			uvcSettings.setEXTENSION_UNIT_CONTROL(extensionUnitControl);
			uvcSettings.setRESERVED("FF");
			uvcSettingList.add(uvcSettings);
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
//		endpointTab.setOnSelectionChanged(event -> {
//	        if (endpointTab.isSelected()) {
//	        	TextField tx = new TextField(String.valueOf(bs));
//	            AnchorPane content1 = (AnchorPane) endpointTab.getContent();
//	            AnchorPane node1 = (AnchorPane) content1.getChildren().get(0);
//	            GridPane node2 = (GridPane) node1.getChildren().get(0);
//	            ObservableList<javafx.scene.Node> children2 = node2.getChildren();
//				for (javafx.scene.Node node : children2) {
//					if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 5) {
//						node2.getChildren().remove(node);
//						node2.add(tx, 1, 5);
//						break;
//					}
//				}
//	        }
//	    });

		/** UVC Format And Resolution **/
		Tab formationAndResolutionTab = new Tab();
		formationAndResolutionTab.setClosable(false);
		formationAndResolutionTab.setText("Format And Resolution");
		createFormatAndResolution(tab, formationAndResolutionTab,
				uvcSettings.getFORMAT_RESOLUTION().getFORMAT_RESOLUTIONS(), uvcSettings.getCOLOR_MATCHING());

		/** UVC Camera Control **/
		Tab cameraControlTab = new Tab();
		cameraControlTab.setClosable(false);
		cameraControlTab.setText("Camera Control");
		createCameraControl(tab, cameraControlTab, uvcSettings.getCAMERA_CONTROL().getCAMERA_CONTROLS());

		/** UVC Processing Unit Control **/
		Tab processingUnitControlTab = new Tab();
		processingUnitControlTab.setClosable(false);
		processingUnitControlTab.setText("Processing Unit Control");
		createProcessingUnitControl(tab, processingUnitControlTab,
				uvcSettings.getPROCESSING_UNIT_CONTROL().getPROCESSING_UNIT_CONTROLS());

		/** UVC Processing Unit Control **/
		Tab extensionUnitControlTab = new Tab();
		extensionUnitControlTab.setClosable(false);
		extensionUnitControlTab.setText("Extension Unit Controls");
		createExtensionUnitControl(extensionUnitControlTab, uvcSettings.getEXTENSION_UNIT_CONTROL());

		tabPane.getTabs().addAll(endpointTab, formationAndResolutionTab, cameraControlTab, processingUnitControlTab,
				extensionUnitControlTab);
	}

	@SuppressWarnings("static-access")
	private void createFormatAndResolution(Tab subTab, Tab subSubTab,
			List<FormatAndResolutions> formatResolutionJsonList, ColorMatching colorMatching) {
		TableView<FormatAndResolutionTableModel> formatResolutionTable = new TableView<>();
		ObservableList<FormatAndResolutionTableModel> formateAndResolutionData = FXCollections.observableArrayList();
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
		gridPane1 = UVCUACSettingsController.createColorMatchingDescriptor(gridPane1, colorMatching, logDetails1,
				logDetails, helpContent, sx3ConfigurationHelp);
		borderPane.setBottom(gridPane1);
		borderPane.setMargin(gridPane1, new Insets(5));

		/** Separator **/
		final Separator sepHor = new Separator();
		sepHor.setPadding(new Insets(0));
		sepHor.setLayoutY(410);
		sepHor.setPrefWidth(1060);

		/** Add Row Button **/
		Button addBtn = new Button();
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
		editBtn.setLayoutY(40);
		// editBtn.setDisable(true);
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
		for (int i = 0; i < formatResolutionJsonList.size(); i++) {
			UVCUACSettingsController.createAndAddRowInTable(addBtn, editBtn, formatResolutionJsonList.get(i),
					formateAndResolutionData, formatResolutionTable, i+1, helpContent,
					logDetails1, logDetails, sx3ConfigurationHelp, uvcuacTabErrorList, subTab, subSubTab,
					uvcuacSettingsTab);
		}

		/** Add Row in Table **/
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FormatAndResolutions formatResolutionJson = loadFormatResolutions();
				formatResolutionJsonList.add(formatResolutionJson);
				UVCUACSettingsController.createAndAddRowInTable(addBtn, editBtn, formatResolutionJson,
						formateAndResolutionData, formatResolutionTable, formateAndResolutionData.size() + 1,
						helpContent, logDetails1, logDetails, sx3ConfigurationHelp, uvcuacTabErrorList, subTab,
						subSubTab, uvcuacSettingsTab);
				addBtn.setDisable(true);
				editBtn.setDisable(false);
			}

		});

		editBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FormatAndResolutionTableModel editedRow = formatResolutionTable.getSelectionModel().getSelectedItem();
				if (editedRow != null && editedRow.getImageFormat().getValue() != null
						&& !editedRow.getResolutionH().getText().equals("")
						&& !editedRow.getResolutionV().getText().equals("") && editedRow.getStillCapture().isSelected()
						&& editedRow.getSupportedInFS().isSelected() && editedRow.getSupportedInHS().isSelected()
						&& editedRow.getSupportedInSS().isSelected()
						&& !editedRow.getFrameRateInFS().getText().equals("")
						&& !editedRow.getFrameRateInHS().getText().equals("")
						&& !editedRow.getFrameRateInSS().getText().equals("")) {
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
					editBtn.setDisable(true);
					addBtn.setDisable(false);
				} else {
					Alert a = new Alert(AlertType.ERROR);
					a.setContentText("Please fill all columns");
					a.show();
				}
			}
		});

		removeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FormatAndResolutionTableModel editedRow = formatResolutionTable.getSelectionModel().getSelectedItem();
				formateAndResolutionData.remove(editedRow);
				int index = Integer.parseInt(editedRow.getSno().getText());
				formatResolutionJsonList.remove(index-1);
				for (int i = 0; i < formateAndResolutionData.size(); i++) {
					Label sno = new Label(String.valueOf(i + 1));
					formateAndResolutionData.get(i).setSno(sno);
				}
				removeBtn.setDisable(true);
				addBtn.setDisable(false);
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
						formatResolution.getSupportedInFS().setDisable(false);
						formatResolution.getSupportedInHS().setDisable(false);
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
								&& formatResolution.getStillCapture().isSelected()
								&& formatResolution.getSupportedInFS().isSelected()
								&& formatResolution.getSupportedInHS().isSelected()
								&& formatResolution.getSupportedInSS().isSelected()
								&& !formatResolution.getFrameRateInFS().getText().equals("")
								&& !formatResolution.getFrameRateInHS().getText().equals("")
								&& !formatResolution.getFrameRateInSS().getText().equals("")) {
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

	private void createCameraControl(Tab rootTab, Tab cameraControlTab,
			List<Map<String, Map<String, Object>>> cameraControlList) {
		ScrollPane rootScrollPane = new ScrollPane();
		rootScrollPane.getStyleClass().add("scrollpane");
		BorderPane borderPane = new BorderPane();
		rootScrollPane.setContent(borderPane);
		cameraControlTab.setContent(rootScrollPane);

		TableView<CameraAndProcessingUnitControlsTableModel> cameraControlTableView = new TableView<>();
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
					cameraControlList.get(i), logDetails1, logDetails, logDetails2,helpContent, sx3ConfigurationHelp,
					performLoadAction);
		}
		cameraControlTableView.setItems(cameraControlsList);

		anchorPane1.getChildren().addAll(cameraControlLabel, cameraControlTableView);
		anchorPane.getChildren().add(anchorPane1);
		rootScrollPane.setContent(anchorPane);

	}

	private void createProcessingUnitControl(Tab rootTab, Tab processingUnitControlTab,
			List<Map<String, Map<String, Object>>> processingUnitControlList) {
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
					processingUnitControlsList, processingUnitControlList.get(i), logDetails, logDetails1, logDetails2, helpContent,
					sx3ConfigurationHelp, performLoadAction);
		}

		processingUnitControlTableView.setItems(processingUnitControlsList);
		anchorPane1.getChildren().addAll(processingUnitControlLabel, processingUnitControlTableView);
		anchorPane.getChildren().add(anchorPane1);
		rootScrollPane.setContent(anchorPane);

	}

	private void createExtensionUnitControl(Tab extensionUnitControlTab, ExtensionUnitControl extensionUnitControl) {
		AnchorPane anchorPane = new AnchorPane();
		anchorPane = UVCUACSettingsController.createExtensionUnitControlUI(anchorPane, extensionUnitControl, logDetails,logDetails1,
				helpContent, sx3ConfigurationHelp);

		extensionUnitControlTab.setContent(anchorPane);
	}

	/**
	 * --------------------------------- UAC Settings Tab
	 * --------------------------------
	 * 
	 * @param endpointOneChanges
	 **/

	private void createUACUI(Tab tab, List<UACSettings> uacSettingList, int number) {
		TabPane tabPane = new TabPane();
		tab.setContent(tabPane);
		UACSettings uacSettings = new UACSettings();
		uacSettings.setRESERVED("FF");
		if (performLoadAction == false) {
			int channelCount = 0;
			int samplingFrequenciesCount = 0;
			EndpointSettings endpointSettings = loadEndpoint("UAC");
			uacSettings.setEndpointSettings(endpointSettings);
			Map<String, String> chennalConfig = loadChannelConfig();
			UACSetting uacSetting = new UACSetting();
			uacSetting.setCHANNEL_CONFIGURATION(chennalConfig);
			uacSetting.setNUMBER_OF_CHANNELS(channelCount);
			uacSetting.setNUMBER_OF_SAMPLING_FREQUENCIES(samplingFrequenciesCount);
			uacSetting.setSAMPLING_FREQUENCY_1_SENSOR_CONFIG(loadSampleFreqSensorConfig());
			uacSetting.setSAMPLING_FREQUENCY_2_SENSOR_CONFIG(loadSampleFreqSensorConfig());
			uacSetting.setSAMPLING_FREQUENCY_3_SENSOR_CONFIG(loadSampleFreqSensorConfig());
			uacSetting.setSAMPLING_FREQUENCY_4_SENSOR_CONFIG(loadSampleFreqSensorConfig());
			Map<String, String> featureUnitControl = loadFeatureUnitControl();
			uacSetting.setFEATURE_UNIT_CONTROLS(featureUnitControl);
			// sx3Configuration.getUAC_SETTINGS().add(uacSettings);
			uacSettings.setUAC_SETTING(uacSetting);
			uacSettingList.add(uacSettings);
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

		// GridPane gridPane = new GridPane();
		UVCUACSettingsController.createUACSettingsTabUI(topAnchor, uacSettings, logDetails, logDetails1, helpContent,
				sx3ConfigurationHelp);

		// topAnchor.getChildren().add(gridPane);
		uacSettingTab.setContent(rootScrollPane);

	}

	/**
	 * ------------------------------- Endpoint Setting Tab For UVC And UAC
	 * ----------------------------------------
	 * 
	 * @param rootTab
	 * @param tabType
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
			end1TransferTypeValue.getItems().addAll("Bulk", "Isochronous");
			end1TransferTypeValue.setTooltip(new Tooltip("Endpoint Type"));
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
					logDetails.appendText(
							endpointName + " Transfer Type : " + end1TransferTypeValue.getValue() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}

			});
			end1TransferTypeValue.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
						helpContent.getEngine()
								.loadContent(sx3ConfigurationHelp.getENDPOINT_SETTINGS().getBURST_LENGTH());
					}
				}
			});
			// end1TransferTypeValue.setOnMouseExited(new EventHandler<Event>()
			// {
			// @Override
			// public void handle(Event event) {
			// helpContent.setText("");
			// }
			// });
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
		end1BurstLengthValue.setTooltip(new Tooltip("Bursts per transfer"));
		end1BurstLengthValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = end1BurstLengthValue.localToScreen(end1BurstLengthValue.getBoundsInLocal());
			end1BurstLengthValue.getTooltip().setX(bounds.getMaxX());
		});
		end1BurstLengthValue.setText(String.valueOf(endpointSettingsJson.getBURST_LENGTH()));
		end1BurstLengthValue.setMaxWidth(40);
		UVCSettingsValidation.setupEndpointValidationForNumeric(uvcuacSettingsTab, rootTab, endpointTab,
				end1BurstLengthValue, performLoadAction, endpointSettingsTabErrorList,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, endpointSettingsJson, 16, "Burst Length");
		end1BurstLengthValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText(endpointName + " Burst Length : " + end1BurstLengthValue.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		if (tabType.equals("UVC")) {
			leftAnchorGridPane.add(end1BurstLengthValue, 1, 2);
		} else {
			leftAnchorGridPane.add(end1BurstLengthValue, 1, 1);
		}
		end1BurstLengthValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getENDPOINT_SETTINGS().getBURST_LENGTH());
				}
			}
		});
		// end1BurstLengthValue.setOnMouseExited(new EventHandler<Event>() {
		// @Override
		// public void handle(Event event) {
		// helpContent.setText("");
		// }
		// });

		/** Buffer Size **/
		Label end1BufferSizeLabel = new Label(tabName + " Buffer Size (Bytes) : ");
		leftAnchorGridPane.setMargin(end1BufferSizeLabel, new Insets(0, 0, 0, 5));
		if (tabType.equals("UVC")) {
			leftAnchorGridPane.add(end1BufferSizeLabel, 0, 3);
		} else {
			leftAnchorGridPane.add(end1BufferSizeLabel, 0, 2);
		}
		TextField end1BufferSizeValue = new TextField();
		end1BufferSizeValue.setAlignment(Pos.CENTER_RIGHT);
		end1BufferSizeValue.setMaxWidth(60);
		end1BufferSizeValue
				.setTooltip(new Tooltip("Choose the size (in bytes) for each buffer (should be a multiple of 16)"));
		end1BufferSizeValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = end1BufferSizeValue.localToScreen(end1BufferSizeValue.getBoundsInLocal());
			end1BufferSizeValue.getTooltip().setX(bounds.getMaxX());
		});
		UVCSettingsValidation.setupEndpointValidationForNumeric(uvcuacSettingsTab, rootTab, endpointTab,
				end1BufferSizeValue, performLoadAction, endpointSettingsTabErrorList,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, endpointSettingsJson, 999984, "Buffer Size");
		end1BufferSizeValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText(
						endpointName + " Buffer Size (Bytes) : " + end1BufferSizeValue.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		if (tabType.equals("UVC")) {
			leftAnchorGridPane.add(end1BufferSizeValue, 1, 3);
		} else {
			leftAnchorGridPane.add(end1BufferSizeValue, 1, 2);
		}
		end1BufferSizeValue.setText(String.valueOf(endpointSettingsJson.getBUFFER_SIZE()));
		end1BufferSizeValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getENDPOINT_SETTINGS().getBUFFER_SIZE());
				}
			}
		});
		// end1BufferSizeValue.setOnMouseExited(new EventHandler<Event>() {
		// @Override
		// public void handle(Event event) {
		// helpContent.setText("");
		// }
		// });

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
		end1BufferCountValue.setMaxWidth(40);
		end1BufferCountValue.setTooltip(new Tooltip("Choose the number of buffers per endpoint"));
		end1BufferCountValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = end1BufferCountValue.localToScreen(end1BufferCountValue.getBoundsInLocal());
			end1BufferCountValue.getTooltip().setX(bounds.getMaxX());
		});
		end1BufferCountValue.setText(String.valueOf(endpointSettingsJson.getBUFFER_COUNT()));
		UVCSettingsValidation.setupEndpointValidationForNumeric(uvcuacSettingsTab, rootTab, endpointTab,
				end1BufferCountValue, performLoadAction, endpointSettingsTabErrorList,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, endpointSettingsJson, 999, "Buffer Count");
		end1BufferCountValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText(endpointName + " Buffer Count : " + end1BufferCountValue.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		if (tabType.equals("UVC")) {
			leftAnchorGridPane.add(end1BufferCountValue, 1, 4);
		} else {
			leftAnchorGridPane.add(end1BufferCountValue, 1, 3);
		}
		end1BufferCountValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getENDPOINT_SETTINGS().getBUFFER_COUNT());
				}
			}
		});
		// end1BufferCountValue.setOnMouseExited(new EventHandler<Event>() {
		// @Override
		// public void handle(Event event) {
		// helpContent.setText("");
		// }
		// });

		/** Total Used Buffer Space **/
		Label totalUseBufferSpaceLabel = new Label("Total used Buffer Space (KB) : ");
		leftAnchorGridPane.setMargin(totalUseBufferSpaceLabel, new Insets(0, 0, 0, 5));
		if (tabType.equals("UVC")) {
			leftAnchorGridPane.add(totalUseBufferSpaceLabel, 0, 5);
		} else {
			leftAnchorGridPane.add(totalUseBufferSpaceLabel, 0, 4);
		}
		TextField totalUseBufferSpaceValue = new TextField();
		totalUseBufferSpaceValue.setAlignment(Pos.CENTER_RIGHT);
		totalUseBufferSpaceValue.setMaxWidth(40);
		totalUseBufferSpaceValue.setTooltip(new Tooltip("Amount of buffer size used by active endpoints"));
		totalUseBufferSpaceValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = totalUseBufferSpaceValue.localToScreen(totalUseBufferSpaceValue.getBoundsInLocal());
			totalUseBufferSpaceValue.getTooltip().setX(bounds.getMaxX());
		});
		totalUseBufferSpaceValue.setText(String.valueOf(endpointSettingsJson.getUSED_BUFFER_SPACE()));
		totalUseBufferSpaceValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
					helpContent.getEngine()
							.loadContent(sx3ConfigurationHelp.getENDPOINT_SETTINGS().getUSED_BUFFER_SPACE());
				}
			}
		});
		// totalUseBufferSpaceValue.setOnMouseExited(new EventHandler<Event>() {
		// @Override
		// public void handle(Event event) {
		// helpContent.setText("");
		// }
		// });
		endpointSettingsJson.setUSED_BUFFER_SPACE(Integer.parseInt(totalUseBufferSpaceValue.getText()));
		totalUseBufferSpaceValue.setDisable(true);

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

	/**
	 * ------------------------------------ FIFO Master Config Tab
	 * ------------------------------------------------
	 **/

	@FXML
	private void enableFPGA() {
		if (enableFPGA.isSelected()) {
			chooseBitFile.setDisable(false);
			fpgaFamily.setDisable(false);
			browseBitFile.setDisable(false);
			logDetails.appendText("Enable FIFO Master Configuration Download : " + true + ".\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		} else {
			chooseBitFile.setDisable(true);
			fpgaFamily.setDisable(true);
			browseBitFile.setDisable(true);
			logDetails.appendText("Enable FIFO Master Configuration Download : " + false + ".\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		}
	}

	@FXML
	private void selectFPGAFamily() {
		logDetails.appendText("FPGA Family : " + fpgaFamily.getValue() + ".\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
	}

	@FXML
	private void selectFPGAFrequency() {
		logDetails.appendText("FPGA I2C Frequency : " + i2cFrequency.getValue() + ".\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
	}

	@FXML
	private void selectBitFile() {
		final Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		configureBitFileChooser(fileChooser);
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			chooseBitFile.setText(selectedFile.getAbsolutePath().toString());
			bitFileSize.setText(String.valueOf(selectedFile.length()));
			logDetails.appendText("Bit File Path : " + selectedFile.getAbsolutePath().toString() + ".\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		}

	}

	protected void configureBitFileChooser(FileChooser fileChooser) {
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BIT", "*.bit"));

	}

	@FXML
	public void selectFifoMasterTab() {
		if (fifoMasterConfigTab.isSelected()) {
			if (enableFPGA.isSelected()) {
				chooseBitFile.setDisable(false);
				fpgaFamily.setDisable(false);
				browseBitFile.setDisable(false);
			} else {
				chooseBitFile.setDisable(true);
				fpgaFamily.setDisable(true);
				browseBitFile.setDisable(true);
			}
		}
	}

	@FXML
	private void showChooseBitFile() {
		if (sx3ConfigurationHelp.getFIFO_MASTER_CONFIG() != null) {
			helpContent.getEngine().loadContent(sx3ConfigurationHelp.getFIFO_MASTER_CONFIG().getBIT_FILE_PATH());
		}
	}

	// @FXML
	// private void hideChooseBitFile() {
	// helpContent.setText("");
	// }

	@FXML
	private void showEnableFPGAHelp() {
		if (sx3ConfigurationHelp.getFIFO_MASTER_CONFIG() != null) {
			helpContent.getEngine().loadContent(
					sx3ConfigurationHelp.getFIFO_MASTER_CONFIG().getFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN());
		}
	}

	// @FXML
	// private void hideEnableFPGAHelp() {
	// helpContent.setText("");
	// }

	@FXML
	private void showFPGAFamilyHelp() {
		if (sx3ConfigurationHelp.getFIFO_MASTER_CONFIG() != null) {
			helpContent.getEngine().loadContent(sx3ConfigurationHelp.getFIFO_MASTER_CONFIG().getFPGA_FAMILY());
		}
	}

	// @FXML
	// private void hideFPGAFamilyHelp() {
	// helpContent.setText("");
	// }

	@FXML
	private void showFPGASlaveAddressHelp() {
		if (sx3ConfigurationHelp.getFIFO_MASTER_CONFIG() != null) {
			helpContent.getEngine().loadContent(sx3ConfigurationHelp.getFIFO_MASTER_CONFIG().getI2C_SLAVE_ADDRESS());
		}
	}

	// @FXML
	// private void hideFPGASlaveAddressHelp() {
	// helpContent.setText("");
	// }

	@FXML
	private void showFPGAI2CFrequency() {
		if (sx3ConfigurationHelp.getFIFO_MASTER_CONFIG() != null) {
			helpContent.getEngine().loadContent(sx3ConfigurationHelp.getFIFO_MASTER_CONFIG().getI2C_FREQUENCY());
		}
	}

	// @FXML
	// private void hideFPGAI2CFrequency() {
	// helpContent.setText("");
	// }

	/**
	 * ----------------------------------------- Video Source Config
	 * -----------------------------
	 **/
	private void createVideoSourceConfig(Tab tab, List<VideoSourceConfig> videoSourceConfigList, int i) {
		if (performLoadAction == false) {
			VideoSourceConfig videoSourceConfig = loadVideoSourceConfig();
//			List<HDMISourceConfiguration> hdmiSourceConfigList = new ArrayList<>();
//			HDMISourceConfiguration hdmiSourceConfig = loadHDMISourceConfig();
//			hdmiSourceConfigList.add(hdmiSourceConfig);
//			videoSourceConfig.setHDMI_SOURCE_CONFIGURATION(hdmiSourceConfigList);
			videoSourceConfigList.add(videoSourceConfig);
			sx3Configuration.setVIDEO_SOURCE_CONFIG(videoSourceConfigList);
			if (tab.getText().contains("Endpoint 2") && sx3Configuration.getVIDEO_SOURCE_CONFIG().size() == 1) {
				createVideoSourceConfigUI(tab, sx3Configuration.getVIDEO_SOURCE_CONFIG().get(i - 1));
			} else {
				createVideoSourceConfigUI(tab, sx3Configuration.getVIDEO_SOURCE_CONFIG().get(i));
			}
		} else {
			if (tab.getText().contains("Endpoint 2") && sx3Configuration.getVIDEO_SOURCE_CONFIG().size() == 1) {
				createVideoSourceConfigUI(tab, sx3Configuration.getVIDEO_SOURCE_CONFIG().get(i - 1));
			} else {
				createVideoSourceConfigUI(tab, sx3Configuration.getVIDEO_SOURCE_CONFIG().get(i));
			}

		}
	}

	@SuppressWarnings("static-access")
	private void createVideoSourceConfigUI(Tab tab, VideoSourceConfig videoSourceConfig) {
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setStyle("-fx-background : #D0D3D4");
		AnchorPane anchorPane = new AnchorPane();
		AnchorPane anchorPane1 = new AnchorPane();
		anchorPane1.setLayoutX(50);
		anchorPane1.setLayoutY(20);
		anchorPane1.setStyle("-fx-background-color: #D0D3D4; -fx-border-insets: 4 1 1 1; -fx-border-color: black;");
		AnchorPane anchorPane3 = new AnchorPane();
		anchorPane3.setLayoutX(50);
		anchorPane3.setLayoutY(150);
		anchorPane3.setStyle("-fx-background-color: #D0D3D4; -fx-border-insets: 4 1 1 1; -fx-border-color: black;");
		Label endpointLabel = new Label("Configuration Download");
		endpointLabel.setLayoutX(6);
		endpointLabel.setLayoutY(-5);
		endpointLabel.setStyle("-fx-background-color: inherit;");
		GridPane leftAnchorGridPane = new GridPane();
		leftAnchorGridPane.setLayoutX(5);
		leftAnchorGridPane.setLayoutY(20);
		leftAnchorGridPane.setVgap(5.0);
		GridPane leftAnchorGridPane1 = new GridPane();
		leftAnchorGridPane1.setLayoutX(5);
		leftAnchorGridPane1.setLayoutY(10);
		leftAnchorGridPane1.setVgap(5.0);
		anchorPane1.getChildren().add(leftAnchorGridPane);
		anchorPane3.getChildren().add(leftAnchorGridPane1);
		anchorPane1.getChildren().add(endpointLabel);
		anchorPane.getChildren().addAll(anchorPane1, anchorPane3);
		scrollPane.setContent(anchorPane);
		tab.setContent(scrollPane);
		AnchorPane anchorPane2 = new AnchorPane();
		anchorPane2.setLayoutX(800);
		anchorPane2.setLayoutY(20);
		anchorPane2.setStyle("-fx-background-color: #D0D3D4;");// -fx-border-insets:
																// 4 1 1 1;
																// -fx-border-color:
																// black;
		anchorPane.getChildren().add(anchorPane2);

		/** Enable Video Source Configuration Download **/
		Label enableVideoSourceLabel = new Label("Enable Video Source Configuration Download : ");
		leftAnchorGridPane.setMargin(enableVideoSourceLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(enableVideoSourceLabel, 0, 0);
		CheckBox enableVideoSourceCheckBox = new CheckBox();
		enableVideoSourceCheckBox.setId("enableVideoSourceCheckBox");
		enableVideoSourceCheckBox.setTooltip(new Tooltip("Configure Video Source from SX3"));
		enableVideoSourceCheckBox.getTooltip().setOnShowing(s -> {
			Bounds bounds = enableVideoSourceCheckBox.localToScreen(enableVideoSourceCheckBox.getBoundsInLocal());
			enableVideoSourceCheckBox.getTooltip().setX(bounds.getMaxX());
		});
		if (videoSourceConfig.getENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD().equals("Enable")) {
			enableVideoSourceCheckBox.setSelected(true);
		} else {
			enableVideoSourceCheckBox.setSelected(false);
		}
		enableVideoSourceCheckBox.setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG()
							.getENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD());
				}
			}
		});
		leftAnchorGridPane.setMargin(enableVideoSourceCheckBox, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(enableVideoSourceCheckBox, 2, 0);

		/** Video Source Type **/
		Label videoSorceTypeLabel = new Label("Video Source Type : ");
		leftAnchorGridPane.setMargin(videoSorceTypeLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(videoSorceTypeLabel, 0, 1);
		ComboBox<String> videoSorceTypeValue = new ComboBox<>();
		videoSorceTypeValue.setId("videoSorceTypeValue");
		videoSorceTypeValue.setTooltip(new Tooltip("Select Video Source Type"));
		videoSorceTypeValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = videoSorceTypeValue.localToScreen(videoSorceTypeValue.getBoundsInLocal());
			videoSorceTypeValue.getTooltip().setX(bounds.getMaxX());
		});
		videoSorceTypeValue.setDisable(true);
		videoSorceTypeValue.getItems().addAll("Image Sensor", "HDMI Source");
		videoSorceTypeValue.setValue(videoSourceConfig.getVIDEO_SOURCE_TYPE());
		leftAnchorGridPane.setMargin(videoSorceTypeValue, new Insets(0, 5, 0, 0));
		leftAnchorGridPane.add(videoSorceTypeValue, 2, 1);
		videoSorceTypeValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				videoSourceConfig.setVIDEO_SOURCE_TYPE(videoSorceTypeValue.getValue());
				logDetails.appendText("Video source type : " + videoSorceTypeValue.getValue() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				// if(videoSorceTypeValue.getValue().equals("HDMI Source")) {
				// createHDMISourceUI(anchorPane2,videoSourceConfig.getHDMI_SOURCE_CONFIGURATION());
				// }else {
				// List<HDMISourceConfiguration> confiList = new ArrayList<>();
				// HDMISourceConfiguration hdmiConfig = loadHDMISourceConfig();
				// confiList.add(hdmiConfig);
				// videoSourceConfig.setHDMI_SOURCE_CONFIGURATION(confiList);
				// anchorPane2.getChildren().clear();
				// }
			}
		});
		videoSorceTypeValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG() != null) {
					helpContent.getEngine()
							.loadContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getVIDEO_SOURCE_TYPE());
				}
			}
		});

		/** Choose Video Source Config File **/
		TextField videoSourceConfifFilePath = new TextField();
		videoSourceConfifFilePath.setPromptText("Choose Video source configuration file");
		videoSourceConfifFilePath.setTooltip(new Tooltip("Choose .txt file"));
		videoSourceConfifFilePath.getTooltip().setOnShowing(s -> {
			Bounds bounds = videoSourceConfifFilePath.localToScreen(videoSourceConfifFilePath.getBoundsInLocal());
			videoSourceConfifFilePath.getTooltip().setX(bounds.getMaxX());
		});
		videoSourceConfifFilePath.setText(videoSourceConfig.getVIDEO_SOURCE_CONFIG_FILE_PATH());
		videoSourceConfifFilePath.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG() != null) {
					helpContent.getEngine().loadContent(
							sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getVIDEO_SOURCE_CONFIG_FILE_PATH());
				}
			}
		});
		videoSourceConfifFilePath.setPrefWidth(300);
		videoSourceConfifFilePath.setLayoutX(5);
		videoSourceConfifFilePath.setDisable(true);
		leftAnchorGridPane.setMargin(videoSourceConfifFilePath, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(videoSourceConfifFilePath, 0, 2);
		Button chooseJsonFileButton = new Button("Browse to Choose Video source configuration file");
		chooseJsonFileButton.setTooltip(new Tooltip("Browse to Choose Video source configuration file"));
		chooseJsonFileButton.getTooltip().setOnShowing(s -> {
			Bounds bounds = chooseJsonFileButton.localToScreen(chooseJsonFileButton.getBoundsInLocal());
			chooseJsonFileButton.getTooltip().setX(bounds.getMaxX());
		});
		chooseJsonFileButton.setDisable(true);
		leftAnchorGridPane.setMargin(videoSourceConfifFilePath, new Insets(0, 5, 0, 0));
		leftAnchorGridPane.add(chooseJsonFileButton, 2, 2);
		final Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		// fileChooser.setInitialDirectory(new File("src"));
		chooseJsonFileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				configureTextFileChooser(fileChooser);
				File selectedFile = fileChooser.showOpenDialog(stage);
				if (selectedFile != null) {
					videoSourceConfifFilePath.setText(selectedFile.getAbsolutePath().toString());
					videoSourceConfig.setVIDEO_SOURCE_CONFIG_FILE_PATH(selectedFile.getAbsolutePath().toString());
					logDetails.appendText(
							"Video Source Config File Path : " + selectedFile.getAbsolutePath().toString() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
		chooseJsonFileButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG() != null) {
					helpContent.getEngine().loadContent(
							sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getVIDEO_SOURCE_CONFIG_FILE_PATH());
				}
			}
		});
		// chooseJsonFileButton.setOnMouseExited(new EventHandler<Event>() {
		// @Override
		// public void handle(Event event) {
		// helpContent.setText("");
		// }
		// });

		/** I2C Slave address (Video Source) **/
		Label videoSourceI2CSlaveAddressLabel = new Label("I2C Slave address (Video Source) : ");
		leftAnchorGridPane1.setMargin(videoSourceI2CSlaveAddressLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane1.add(videoSourceI2CSlaveAddressLabel, 0, 3);
		Label videoSourceI2CSlaveAddressLabel1 = new Label("0x");
		leftAnchorGridPane1.add(videoSourceI2CSlaveAddressLabel1, 1, 3);
		TextField videoSourceI2CSlaveAddressValue = new TextField();
		videoSourceI2CSlaveAddressValue.setAlignment(Pos.CENTER_RIGHT);
		videoSourceI2CSlaveAddressValue.setId("videoSourceI2CSlaveAddressValue");
		videoSourceI2CSlaveAddressValue.setTooltip(new Tooltip("I2C Slave address for Video Source"));
		videoSourceI2CSlaveAddressValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = videoSourceI2CSlaveAddressValue
					.localToScreen(videoSourceI2CSlaveAddressValue.getBoundsInLocal());
			videoSourceI2CSlaveAddressValue.getTooltip().setX(bounds.getMaxX());
		});
		videoSourceI2CSlaveAddressValue.setMaxWidth(40);
		videoSourceI2CSlaveAddressValue.setText(!videoSourceConfig.getI2C_SLAVE_ADDRESS().isEmpty()
				? videoSourceConfig.getI2C_SLAVE_ADDRESS().substring(2) : "");
		leftAnchorGridPane1.setMargin(videoSourceI2CSlaveAddressValue, new Insets(0, 5, 0, 0));
		leftAnchorGridPane1.add(videoSourceI2CSlaveAddressValue, 2, 3);
		VideoSourceConfigValidation.setupValidationForHexTextField(videoSourceConfigTab, tab,
				videoSourceConfigTabErrorList, performLoadAction, videoSourceI2CSlaveAddressValue,
				VideoSourceConfigValidation.INVALID_HEX_ERROR_MESSAGE, videoSourceConfig, 2, "Slave_Address");
		videoSourceI2CSlaveAddressValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText("I2C Slave Address : " + videoSourceI2CSlaveAddressValue.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		videoSourceI2CSlaveAddressValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG() != null) {
					helpContent.getEngine()
							.loadContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getI2C_SLAVE_ADDRESS());
				}
			}
		});
		// videoSourceI2CSlaveAddressValue.setOnMouseExited(new
		// EventHandler<Event>() {
		// @Override
		// public void handle(Event event) {
		// helpContent.setText("");
		// }
		// });

		/** I2C Slave Data size **/
		Label videoSourceI2CSlaveDataSizeLabel = new Label("I2C Slave Data size (Bytes) : ");
		leftAnchorGridPane1.setMargin(videoSourceI2CSlaveDataSizeLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane1.add(videoSourceI2CSlaveDataSizeLabel, 0, 4);
		TextField videoSourceI2CSlaveDataSizeValue = new TextField();
		videoSourceI2CSlaveDataSizeValue.setAlignment(Pos.CENTER_RIGHT);
		videoSourceI2CSlaveDataSizeValue.setTooltip(new Tooltip("Data Size for I2C Registers in Video Source"));
		videoSourceI2CSlaveDataSizeValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = videoSourceI2CSlaveDataSizeValue
					.localToScreen(videoSourceI2CSlaveDataSizeValue.getBoundsInLocal());
			videoSourceI2CSlaveDataSizeValue.getTooltip().setX(bounds.getMaxX());
		});
		videoSourceI2CSlaveDataSizeValue.setMaxWidth(40);
		videoSourceI2CSlaveDataSizeValue.setText(String.valueOf(videoSourceConfig.getI2C_SLAVE_DATA_SIZE()));
		leftAnchorGridPane1.setMargin(videoSourceI2CSlaveDataSizeValue, new Insets(0, 5, 0, 0));
		leftAnchorGridPane1.add(videoSourceI2CSlaveDataSizeValue, 2, 4);
		VideoSourceConfigValidation.setupNumericFieldValidation(videoSourceI2CSlaveDataSizeValue,
				VideoSourceConfigValidation.INVALID_NUMERIC_ERROR_MESSAGE, videoSourceConfig, 2, "DataSize");
		videoSourceI2CSlaveDataSizeValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText(
						"I2C Slave Data size (Bytes) : " + videoSourceI2CSlaveDataSizeValue.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		videoSourceI2CSlaveDataSizeValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG() != null) {
					helpContent.getEngine()
							.loadContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getI2C_SLAVE_DATA_SIZE());
				}
			}
		});
		// videoSourceI2CSlaveDataSizeValue.setOnMouseExited(new
		// EventHandler<Event>() {
		// @Override
		// public void handle(Event event) {
		// helpContent.setText("");
		// }
		// });

		/** I2C Slave register size **/
		Label videoSourceI2CSlaveregisterSizeLabel = new Label("I2C Slave register size (Bytes) : ");
		leftAnchorGridPane1.setMargin(videoSourceI2CSlaveregisterSizeLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane1.add(videoSourceI2CSlaveregisterSizeLabel, 0, 5);
		TextField videoSourceI2CSlaveregisterSizeValue = new TextField();
		videoSourceI2CSlaveregisterSizeValue.setDisable(true);
		videoSourceI2CSlaveregisterSizeValue.setAlignment(Pos.CENTER_RIGHT);
		videoSourceI2CSlaveregisterSizeValue.setId("TestId");
		videoSourceI2CSlaveregisterSizeValue
				.setTooltip(new Tooltip("Register Address size for I2C Registers in Video Source"));
		videoSourceI2CSlaveregisterSizeValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = videoSourceI2CSlaveregisterSizeValue
					.localToScreen(videoSourceI2CSlaveregisterSizeValue.getBoundsInLocal());
			videoSourceI2CSlaveregisterSizeValue.getTooltip().setX(bounds.getMaxX());
		});
		videoSourceI2CSlaveregisterSizeValue.setMaxWidth(40);
		leftAnchorGridPane1.setMargin(videoSourceI2CSlaveregisterSizeValue, new Insets(0, 5, 0, 0));
		leftAnchorGridPane1.add(videoSourceI2CSlaveregisterSizeValue, 2, 5);
		videoSourceI2CSlaveregisterSizeValue.setText(String.valueOf(videoSourceConfig.getI2C_SLAVE_REGISTER_SIZE()));
		videoSourceI2CSlaveregisterSizeValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG() != null) {
					helpContent.getEngine()
							.loadContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getI2C_SLAVE_REGISTER_SIZE());
				}
			}
		});
		// videoSourceI2CSlaveregisterSizeValue.setOnMouseExited(new
		// EventHandler<Event>() {
		// @Override
		// public void handle(Event event) {
		// helpContent.setText("");
		// }
		// });

		/** I2C Frequency **/
		Label videoSourceI2CFrequencyLabel = new Label("I2C Frequency (Hz) : ");
		leftAnchorGridPane1.setMargin(videoSourceI2CFrequencyLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane1.add(videoSourceI2CFrequencyLabel, 0, 6);
		ComboBox<Integer> videoSourceI2CFrequencyCombo = new ComboBox<>();
		videoSourceI2CFrequencyCombo.setTooltip(new Tooltip("I2C Clock frequency for Video Source"));
		videoSourceI2CFrequencyCombo.getTooltip().setOnShowing(s -> {
			Bounds bounds = videoSourceI2CFrequencyCombo.localToScreen(videoSourceI2CFrequencyCombo.getBoundsInLocal());
			videoSourceI2CFrequencyCombo.getTooltip().setX(bounds.getMaxX());
		});
		videoSourceI2CFrequencyCombo.getItems().addAll(100000, 400000, 1000000);
		videoSourceI2CFrequencyCombo.setValue(videoSourceConfig.getI2C_FREQUENCY());
		leftAnchorGridPane1.setMargin(videoSourceI2CFrequencyCombo, new Insets(0, 5, 0, 0));
		leftAnchorGridPane1.add(videoSourceI2CFrequencyCombo, 2, 6);
		videoSourceI2CFrequencyCombo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				videoSourceConfig.setI2C_FREQUENCY(videoSourceI2CFrequencyCombo.getValue());
				logDetails.appendText("I2C Frequency : " + videoSourceI2CFrequencyCombo.getValue() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		videoSourceI2CFrequencyCombo.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG() != null) {
					helpContent.getEngine()
							.loadContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getI2C_FREQUENCY());
				}
			}
		});
		// videoSourceI2CFrequencyCombo.setOnMouseExited(new
		// EventHandler<Event>() {
		// @Override
		// public void handle(Event event) {
		// helpContent.setText("");
		// }
		// });

		if (enableVideoSourceCheckBox.isSelected()) {
			videoSorceTypeValue.setDisable(false);
			videoSourceConfifFilePath.setDisable(false);
			chooseJsonFileButton.setDisable(false);
			videoSourceConfig.setENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD("Enable");
		} else {
			videoSorceTypeValue.setDisable(true);
			videoSourceConfifFilePath.setDisable(true);
			chooseJsonFileButton.setDisable(true);
			videoSourceConfig.setENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD("Disable");
		}

		enableVideoSourceCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (enableVideoSourceCheckBox.isSelected()) {
					videoSorceTypeValue.setDisable(false);
					videoSourceConfifFilePath.setDisable(false);
					chooseJsonFileButton.setDisable(false);
					videoSourceConfig.setENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD("Enable");
					logDetails.appendText("Enable Video Source Configuration Download : " + true + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					videoSorceTypeValue.setDisable(true);
					videoSourceConfifFilePath.setDisable(true);
					chooseJsonFileButton.setDisable(true);
					videoSourceConfig.setENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD("Disable");
					logDetails.appendText("Enable Video Source Configuration Download : " + false + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}

		});
	}

	protected void configureTextFileChooser(FileChooser fileChooser) {
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt", "*.txt"));

	}

//	private void createHDMISourceUI(AnchorPane anchorPane1, List<HDMISourceConfiguration> hdmiConfigList) {
//		/** Add Row Button **/
//		Button addBtn = new Button();
//		// addBtn.setDisable(true);
//		addBtn.setLayoutY(10);
//		addBtn.setLayoutX(540);
//		addBtn.setTooltip(new Tooltip("Add Row"));
//		addBtn.getTooltip().setOnShowing(s -> {
//			Bounds bounds = addBtn.localToScreen(addBtn.getBoundsInLocal());
//			addBtn.getTooltip().setX(bounds.getMaxX());
//		});
//		ImageView addImage = new ImageView(getClass().getResource("/resources/add.png").toString());
//		addImage.setFitHeight(15);
//		addImage.setFitWidth(15);
//		addBtn.setGraphic(addImage);
//		/** Edit Row Button **/
//		Button editBtn = new Button();
//		editBtn.setLayoutY(40);
//		editBtn.setLayoutX(540);
//		editBtn.setTooltip(new Tooltip("Save Row"));
//		editBtn.getTooltip().setOnShowing(s -> {
//			Bounds bounds = editBtn.localToScreen(editBtn.getBoundsInLocal());
//			editBtn.getTooltip().setX(bounds.getMaxX());
//		});
//		ImageView editImage = new ImageView(getClass().getResource("/resources/saveRow.png").toString());
//		editImage.setFitHeight(15);
//		editImage.setFitWidth(15);
//		editBtn.setGraphic(editImage);
//
//		/** Remove Row Button **/
//		Button removeBtn = new Button();
//		removeBtn.setLayoutY(76);
//		// removeBtn.setDisable(true);
//		removeBtn.setLayoutX(540);
//		removeBtn.setTooltip(new Tooltip("Remove Row"));
//		removeBtn.getTooltip().setOnShowing(s -> {
//			Bounds bounds = removeBtn.localToScreen(removeBtn.getBoundsInLocal());
//			removeBtn.getTooltip().setX(bounds.getMaxX());
//		});
//		ImageView removeImage = new ImageView(getClass().getResource("/resources/deleteRow.png").toString());
//		removeImage.setFitHeight(15);
//		removeImage.setFitWidth(15);
//		removeBtn.setGraphic(removeImage);
//		anchorPane1.getChildren().addAll(addBtn, removeBtn, editBtn);
//		TableView<HDMISourceConfigurationTable> hdmiSourceConfigurationTable = new TableView<>();
//		ObservableList<HDMISourceConfigurationTable> hdmiSourceConfigurationData = FXCollections.observableArrayList();
//		VideoSourceConfigController.createHDMITable(anchorPane1, hdmiSourceConfigurationTable);
//		for (int i = 0; i < hdmiConfigList.size(); i++) {
//			VideoSourceConfigController.createAndAddRowInTable(hdmiConfigList.get(i), hdmiSourceConfigurationData,
//					hdmiSourceConfigurationTable, i + 1);
//		}
//
//		/** Add Row in Table **/
//		addBtn.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				HDMISourceConfiguration hdmiSourceConfigJson = loadHDMISourceConfig();
//				hdmiConfigList.add(hdmiSourceConfigJson);
//				VideoSourceConfigController.createAndAddRowInTable(hdmiSourceConfigJson, hdmiSourceConfigurationData,
//						hdmiSourceConfigurationTable, hdmiConfigList.size());
//			}
//
//		});
//
//	}

	@SuppressWarnings("static-access")
	@FXML
	public void selectvideoSourceConfigTab() {
		if (videoSourceConfigTab.isSelected()) {
			ObservableList<Tab> tabs = videoSourceConfigTabpane.getTabs();
			for (Tab tab : tabs) {
				ScrollPane scp = (ScrollPane) tab.getContent();
				AnchorPane anchorPane = (AnchorPane) scp.getContent();
				AnchorPane children = (AnchorPane) anchorPane.getChildren().get(0);
				GridPane gridPane = (GridPane) children.getChildren().get(0);
				TextField videoSourceI2CSlaveregisterSizeValue = new TextField();
				videoSourceI2CSlaveregisterSizeValue.setAlignment(Pos.CENTER_RIGHT);
				videoSourceI2CSlaveregisterSizeValue.setId("TestId");
				videoSourceI2CSlaveregisterSizeValue
						.setTooltip(new Tooltip("Register Address size for I2C Registers in Video Source"));
				videoSourceI2CSlaveregisterSizeValue.getTooltip().setOnShowing(s -> {
					Bounds bounds = videoSourceI2CSlaveregisterSizeValue
							.localToScreen(videoSourceI2CSlaveregisterSizeValue.getBoundsInLocal());
					videoSourceI2CSlaveregisterSizeValue.getTooltip().setX(bounds.getMaxX());
				});
				videoSourceI2CSlaveregisterSizeValue.setDisable(true);
				videoSourceI2CSlaveregisterSizeValue.setMaxWidth(40);
				gridPane.setMargin(videoSourceI2CSlaveregisterSizeValue, new Insets(0, 5, 0, 0));
				if (tab.getText().contains("Endpoint 1")) {
					List<Map<String, Map<String, Object>>> camera_CONTROLS = sx3Configuration.getUVC_SETTINGS().get(0)
							.getCAMERA_CONTROL().getCAMERA_CONTROLS();
					for (int i = 0; i < camera_CONTROLS.size(); i++) {
						for (Map.Entry<String, Map<String, Object>> entry1 : camera_CONTROLS.get(i).entrySet()) {
							for (Map.Entry<String, Object> entry : entry1.getValue().entrySet()) {
								if (entry.getKey().contains("_REGISTER_ADDRESS")) {
									String slaveAddress = !entry.getValue().toString().isEmpty()
											? entry.getValue().toString().substring(2) : "";
									if (slaveAddress.length() > 2) {
										videoSourceI2CSlaveregisterSizeValue.setText(String.valueOf(2));
										sx3Configuration.getVIDEO_SOURCE_CONFIG().get(0).setI2C_SLAVE_REGISTER_SIZE(2);
									} else if (slaveAddress.length() > 0) {
										videoSourceI2CSlaveregisterSizeValue.setText(String.valueOf(1));
										sx3Configuration.getVIDEO_SOURCE_CONFIG().get(0).setI2C_SLAVE_REGISTER_SIZE(1);
									}
								}
							}
						}
					}
					if (videoSourceI2CSlaveregisterSizeValue.getText().equals("0")
							|| !videoSourceI2CSlaveregisterSizeValue.getText().equals("2")) {
						List<Map<String, Map<String, Object>>> processing_CONTROLS = sx3Configuration.getUVC_SETTINGS()
								.get(0).getPROCESSING_UNIT_CONTROL().getPROCESSING_UNIT_CONTROLS();

						for (int i = 0; i < processing_CONTROLS.size(); i++) {
							for (Map.Entry<String, Map<String, Object>> entry1 : processing_CONTROLS.get(i)
									.entrySet()) {
								for (Map.Entry<String, Object> entry : entry1.getValue().entrySet()) {
									if (entry.getKey().contains("REGISTER_ADDRESS")) {
										String slaveAddress = !entry.getValue().toString().isEmpty()
												? entry.getValue().toString().substring(2) : "";
										if (slaveAddress.length() > 2) {
											videoSourceI2CSlaveregisterSizeValue.setText(String.valueOf(2));
											sx3Configuration.getVIDEO_SOURCE_CONFIG().get(0)
													.setI2C_SLAVE_REGISTER_SIZE(2);
										} else if (slaveAddress.length() > 0) {
											videoSourceI2CSlaveregisterSizeValue.setText(String.valueOf(1));
											sx3Configuration.getVIDEO_SOURCE_CONFIG().get(0)
													.setI2C_SLAVE_REGISTER_SIZE(1);
										}
									}
								}
							}
						}
					}

				} else {
					List<Map<String, Map<String, Object>>> camera_CONTROLS = new ArrayList<>();
					if (sx3Configuration.getUVC_SETTINGS().size() > 1) {
						camera_CONTROLS = sx3Configuration.getUVC_SETTINGS().get(1).getCAMERA_CONTROL()
								.getCAMERA_CONTROLS();
					} else {
						camera_CONTROLS = sx3Configuration.getUVC_SETTINGS().get(0).getCAMERA_CONTROL()
								.getCAMERA_CONTROLS();
					}
					for (int i = 0; i < camera_CONTROLS.size(); i++) {
						for (Map.Entry<String, Map<String, Object>> entry1 : camera_CONTROLS.get(i).entrySet()) {
							for (Map.Entry<String, Object> entry : entry1.getValue().entrySet()) {
								if (entry.getKey().contains("REGISTER_ADDRESS")) {
									String slaveAddress = !entry.getValue().toString().isEmpty()
											? entry.getValue().toString().substring(2) : "";
									if (slaveAddress.length() > 2) {
										videoSourceI2CSlaveregisterSizeValue.setText("");
										videoSourceI2CSlaveregisterSizeValue.setText(String.valueOf(2));
										if(sx3Configuration.getVIDEO_SOURCE_CONFIG().size()>1) {
											sx3Configuration.getVIDEO_SOURCE_CONFIG().get(1).setI2C_SLAVE_REGISTER_SIZE(2);
										}else {
											sx3Configuration.getVIDEO_SOURCE_CONFIG().get(0).setI2C_SLAVE_REGISTER_SIZE(2);
										}
									} else if (slaveAddress.length() > 0) {
										videoSourceI2CSlaveregisterSizeValue.setText("");
										videoSourceI2CSlaveregisterSizeValue.setText(String.valueOf(1));
										if(sx3Configuration.getVIDEO_SOURCE_CONFIG().size()>1) {
											sx3Configuration.getVIDEO_SOURCE_CONFIG().get(1).setI2C_SLAVE_REGISTER_SIZE(1);
										}else {
											sx3Configuration.getVIDEO_SOURCE_CONFIG().get(0).setI2C_SLAVE_REGISTER_SIZE(1);
										}
									}
								}
							}
						}
					}
					if (videoSourceI2CSlaveregisterSizeValue.getText().equals("0")) {
						List<Map<String, Map<String, Object>>> processing_CONTROLS = sx3Configuration.getUVC_SETTINGS()
								.get(1).getPROCESSING_UNIT_CONTROL().getPROCESSING_UNIT_CONTROLS();
						for (int i = 0; i < processing_CONTROLS.size(); i++) {
							for (Map.Entry<String, Map<String, Object>> entry1 : processing_CONTROLS.get(i)
									.entrySet()) {
								for (Map.Entry<String, Object> entry : entry1.getValue().entrySet()) {
									if (entry.getKey().contains("REGISTER_ADDRESS")) {
										String slaveAddress = !entry.getValue().toString().isEmpty()
												? entry.getValue().toString().substring(2) : "";
										if (slaveAddress.length() > 2) {
											videoSourceI2CSlaveregisterSizeValue.setText("");
											videoSourceI2CSlaveregisterSizeValue.setText(String.valueOf(2));
											if(sx3Configuration.getVIDEO_SOURCE_CONFIG().size()>1) {
												sx3Configuration.getVIDEO_SOURCE_CONFIG().get(1)
												.setI2C_SLAVE_REGISTER_SIZE(2);
											}else {
												sx3Configuration.getVIDEO_SOURCE_CONFIG().get(0)
												.setI2C_SLAVE_REGISTER_SIZE(2);
											}
										} else if (slaveAddress.length() > 0) {
											videoSourceI2CSlaveregisterSizeValue.setText("");
											videoSourceI2CSlaveregisterSizeValue.setText(String.valueOf(1));
											if(sx3Configuration.getVIDEO_SOURCE_CONFIG().size()>1) {
												sx3Configuration.getVIDEO_SOURCE_CONFIG().get(1)
												.setI2C_SLAVE_REGISTER_SIZE(1);
											}else {
												sx3Configuration.getVIDEO_SOURCE_CONFIG().get(0)
												.setI2C_SLAVE_REGISTER_SIZE(1);
											}
										}
									}
								}
							}
						}
					}
				}
				ObservableList<javafx.scene.Node> children2 = gridPane.getChildren();
				for (javafx.scene.Node node : children2) {
					if (GridPane.getColumnIndex(node) == 2 && GridPane.getRowIndex(node) == 5) {
						gridPane.getChildren().remove(node);
						gridPane.add(videoSourceI2CSlaveregisterSizeValue, 2, 5);
						break;
					}
				}
			}
		}
	}

	/**
	 * ----------------------------------------------- Log Data
	 * --------------------------------------------------------------
	 **/

	/** Log Serial Number increment **/
	@FXML
	private void logSerialNumberIncrement() {
		checkFieldEditOrNot = true;
		logDetails.appendText("Serial Number Increment : " + serialNumberIncrement.getValue() + ".\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
	}

	/** Log Power Configuration **/
	@FXML
	private void logPowerConfiguration() {
		checkFieldEditOrNot = true;
		logDetails.appendText("Power Configuration : " + powerConfiguration.getValue() + ".\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
	}

	/** Log FIFO BusWidth **/
	@FXML
	public void logFifoBusWidth() {
		checkFieldEditOrNot = true;
		logDetails.appendText("FIFO Bus Width : " + fifoBusWidth.getValue() + ".\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
	}

	/** Log Interface Type **/
	@FXML
	public void logInterfaceType() {
		checkFieldEditOrNot = true;
		logDetails.appendText("FIFO Interface Type : " + interFaceType.getValue() + ".\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
	}

	/** Log GPIO 1 **/
	@FXML
	public void logGpio1() {
		if (gpio1.getValue() != null) {
			checkFieldEditOrNot = true;
			logDetails.appendText("GPIO 1 :" + gpio1.getValue() + ".\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		}
	}

	@FXML
	public void loadGpio1List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_1_SETTING());
		}
		ObservableList<String> items = gpio1.getItems();
		if (deviceName.getText().equals("SX3 UVC")) {
			for (int i = 0; i < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; i++) {
				if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
						&& gpio2.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])) {
					gpio1.getItems().remove(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
				} else if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
						&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])) {
					gpio1.getItems().add(i, DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
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
			logDetails.appendText("GPIO 2 :" + gpio2.getValue() + ".\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		}
	}

	@FXML
	public void loadGpio2List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_2_SETTING());
		}
		ObservableList<String> items = gpio2.getItems();
		if (deviceName.getText().equals("SX3 UVC")) {
			for (int i = 0; i < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; i++) {
				if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
						&& gpio1.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])) {
					gpio2.getItems().remove(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
				} else if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
						&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])) {
					gpio2.getItems().add(i, DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
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
			logDetails.appendText("GPIO 3 :" + gpio3.getValue() + ".\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		}
	}

	@FXML
	public void loadGpio3List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_3_SETTING());
		}
		ObservableList<String> items = gpio3.getItems();
		if (deviceName.getText().equals("SX3 UVC")) {
			for (int i = 0; i < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; i++) {
				if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("LED Brightness Control")
						&& (gpio4.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
								|| gpio5.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
								|| gpio6.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
								|| gpio7.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
								|| gpio1.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
								|| gpio2.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]))) {
					gpio3.getItems().remove(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
				} else if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
						&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
						&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("LED Brightness Control")) {
					gpio3.getItems().add(i, DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
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
			logDetails.appendText("GPIO 4 :" + gpio4.getValue() + ".\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		}
	}

	@FXML
	public void loadGpio4List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_4_SETTING());
		}
		ObservableList<String> items = gpio4.getItems();
		for (int i = 0; i < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; i++) {
			if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("LED Brightness Control")
					&& (gpio3.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio5.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio6.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio7.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio1.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio2.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]))) {
				gpio4.getItems().remove(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
			} else if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
					&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("LED Brightness Control")) {
				gpio4.getItems().add(i, DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
			}
		}
	}

	/** Log GPIO 5 **/
	@FXML
	public void logGpio5() {
		if (gpio5.getValue() != null) {
			checkFieldEditOrNot = true;
			logDetails.appendText("GPIO 5 :" + gpio5.getValue() + ".\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		}
	}

	@FXML
	public void loadGpio5List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_5_SETTING());
		}
		ObservableList<String> items = gpio5.getItems();
		for (int i = 0; i < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; i++) {
			if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("LED Brightness Control")
					&& (gpio3.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio4.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio6.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio7.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio1.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio2.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]))) {
				gpio5.getItems().remove(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
			} else if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
					&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("LED Brightness Control")) {
				gpio5.getItems().add(i, DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
			}
		}
	}

	/** Log GPIO 6 **/
	@FXML
	public void logGpio6() {
		if (gpio6.getValue() != null) {
			checkFieldEditOrNot = true;
			logDetails.appendText("GPIO 6 :" + gpio6.getValue() + ".\n<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		}
	}

	@FXML
	public void loadGpio6List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_6_SETTING());
		}
		ObservableList<String> items = gpio6.getItems();
		for (int i = 0; i < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; i++) {
			if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("LED Brightness Control")
					&& (gpio3.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio4.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio5.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio7.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio1.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio2.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]))) {
				gpio6.getItems().remove(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
			} else if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
					&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("LED Brightness Control")) {
				gpio6.getItems().add(i, DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
			}
		}
	}

	/** Log GPIO 7 **/
	@FXML
	public void logGpio7() {
		if (gpio7.getValue() != null) {
			checkFieldEditOrNot = true;
			logDetails.appendText("GPIO 7 :" + gpio7.getValue() + ".<br>");
			logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		}
	}

	@FXML
	public void loadGpio7List() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_7_SETTING());
		}
		ObservableList<String> items = gpio7.getItems();
		for (int i = 0; i < DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC.length; i++) {
			if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("LED Brightness Control")
					&& (gpio3.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio4.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio5.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio6.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio1.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
							|| gpio2.getValue().equals(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]))) {
				gpio7.getItems().remove(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
			} else if (!DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("Not Used")
					&& !items.contains(DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i])
					&& !DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i].equals("LED Brightness Control")) {
				gpio6.getItems().add(i, DeviceSettingsConstant.GPIO_ONE_TO_TWO_IN_SX3UVC[i]);
			}
		}
	}

	/** Log Enable Disable Debug Level **/
	@FXML
	public void logDebugLevel() {
		checkFieldEditOrNot = true;
		logDetails.appendText("Debug Level : " + debugValue.getValue() + ".\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
	}

	@FXML
	public void logUVCVersion() {
		checkFieldEditOrNot = true;
		logDetails.appendText("UVC Version : " + uvcVersion.getValue() + ".\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
	}

	@FXML
	public void logUVCHeaderAddition() {
		checkFieldEditOrNot = true;
		logDetails.appendText("UVC Header Addition : " + uvcHeaderAddition.getValue() + ".\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
	}

	/**
	 * ----------------------------------------------- Show Help Content
	 * ------------------------------------------------------
	 **/

	@FXML
	public void showVendorIdHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getVENDOR_ID());
		}
		// helpDetails1.getEngine()
		// .loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getVENDOR_ID());
	}

	@FXML
	public void showProductIdHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getPRODUCT_ID());
		}
		// helpDetails1.getEngine()
		// .loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getPRODUCT_ID());
	}

	@FXML
	void showManufactureHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getMANUFACTURER_STRING());
		}
	}

	@FXML
	public void showProductStringHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getPRODUCT_STRING());
		}
	}

	@FXML
	public void showSerialNoIncrementHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().loadContent(
					sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getAUTO_INCREMENT_SERIAL_NUMBER());
		}
	}

	@FXML
	public void showAutoGenerateSerialNoHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine().loadContent(
					sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getAUTO_GENERATE_SERIAL_NUMBER());
		}
	}

	@FXML
	public void showSerialNoHelp() {
		if(!serialNumber.getText().equals("")) {
			String substring = serialNumber.getText().substring(serialNumber.getText().length() - 1);
			if (!SX3CommonUIValidations.isValidNumeric(substring)) {
				numericValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
				numericValidator.setAutoHide(false);
				numericValidator.getItems().clear();
				numericValidator.getItems().add(new MenuItem("Last character should be numeric."));
				numericValidator.show(serialNumber, Side.RIGHT, 2, 0);
			}
		}
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getSERIALNUMBER_STRING());
		}
	}

	@FXML
	public void clearSerialNumberHelpContent() {
		numericValidator.hide();
	}

	@FXML
	public void showEnableRemoteWakeupHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getREMOTE_WAKEUP_ENABLE());
		}
	}

	@FXML
	public void showPowerConfigurationHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getPOWER_CONFIGURATION());
		}
	}

	@FXML
	public void showFifoBusWidthHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			if (deviceName.getText().equals("SX3 Data (16-bit)")) {
				helpContent.getEngine().loadContent(
						sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getFIFO_BUS_WIDTH() + "16");
			} else {
				helpContent.getEngine().loadContent(
						sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getFIFO_BUS_WIDTH() + "32");
			}
		}
	}

	@FXML
	public void showFifoClockFrequencyHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getFIFO_CLOCK());
		}
	}

	@FXML
	public void showDebugEnableHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getDEBUG_LEVEL().getDEBUG_ENABLE());
		}
	}

	@FXML
	public void showDebugLevelHelp() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getDEBUG_LEVEL().getDEBUG_LEVEL());
		}
	}

	// @FXML
	// public void showNoOfEndpointsHelp() {
	// if(sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
	// helpContent.setText(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getNUM_ENDPOINTS());
	// }
	// }

	// @FXML
	// public void showEndpoint1Help() {
	// if(sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
	// helpContent.setText(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT1_TYPE());
	// }
	// }
	//
	// @FXML
	// public void showEndpoint2Help() {
	// if(sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
	// helpContent.setText(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT2_TYPE());
	// }
	// }

	// @FXML
	// public void clearHelpContent() {
	// helpContent.clear();
	// }

	@FXML
	public void showUVCVersionHelpContent() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getUVC_VERSION());
		}
	}

	@FXML
	public void showUVCHeaderAdditionHelpContent() {
		if (sx3ConfigurationHelp.getDEVICE_SETTINGS() != null) {
			helpContent.getEngine()
					.loadContent(sx3ConfigurationHelp.getDEVICE_SETTINGS().getUSB_SETTINGS().getUVC_HEADER_ADDITION());
		}
	}

	@FXML
	public void openBrowser() throws IOException, URISyntaxException {
		Document document = helpContent.getEngine().getDocument();
		NodeList elementsByTagName = document.getElementsByTagName("a");
		for (int i = 0; i < elementsByTagName.getLength(); i++) {
			Node item = elementsByTagName.item(0);
			String url = item.toString();
			Runtime rt = Runtime.getRuntime();
			rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
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

		/** Brust Length **/
		Label end1BurstLengthLabel = new Label("Burst Length : ");
		leftAnchorGridPane.setMargin(end1BurstLengthLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(end1BurstLengthLabel, 0, 1);
		TextField end1BurstLengthValue = new TextField();
		end1BurstLengthValue.setTooltip(new Tooltip("Bursts per transfer"));
		end1BurstLengthValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = end1BurstLengthValue.localToScreen(end1BurstLengthValue.getBoundsInLocal());
			end1BurstLengthValue.getTooltip().setX(bounds.getMaxX());
		});
		end1BurstLengthValue.setText(String.valueOf(slaveFIFOSettings2.getBURST_LENGTH()));
		end1BurstLengthValue.setMaxWidth(40);
		leftAnchorGridPane.add(end1BurstLengthValue, 1, 1);
		SlaveFifoSettingValidation.setupslaveFifoSettingsValidationForNumeric(slaveFifoSettingsTab, tab,
				end1BurstLengthValue, endpointSettingsTabErrorList,
				SlaveFifoSettingValidation.INVALID_NUMERIC_ERROR_MESSAGE, slaveFIFOSettings2, 16, "Burst Length");
		end1BurstLengthValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText("Slave FIFO Burst Length : " + end1BurstLengthValue.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		end1BurstLengthValue.setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getSLAVE_FIFO_SETTINGS() != null) {
					helpContent.getEngine()
							.loadContent(sx3ConfigurationHelp.getSLAVE_FIFO_SETTINGS().getBURST_LENGTH());
				}
			}
		});
		// end1BurstLengthValue.setOnMouseExited(new EventHandler<Event>() {
		// @Override
		// public void handle(Event event) {
		// helpContent.setText("");
		// }
		// });

		/** Buffer Size **/
		Label end1BufferSizeLabel = new Label("Buffer Size (Bytes) : ");
		leftAnchorGridPane.setMargin(end1BufferSizeLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(end1BufferSizeLabel, 0, 2);
		TextField end1BufferSizeValue = new TextField();
		end1BufferSizeValue.setTooltip(new Tooltip("Choose the size (in bytes) for each buffer"));
		end1BufferSizeValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = end1BufferSizeValue.localToScreen(end1BufferSizeValue.getBoundsInLocal());
			end1BufferSizeValue.getTooltip().setX(bounds.getMaxX());
		});
		end1BufferSizeValue.setMaxWidth(80);
		leftAnchorGridPane.add(end1BufferSizeValue, 1, 2);
		end1BufferSizeValue.setText(String.valueOf(slaveFIFOSettings2.getBUFFER_SIZE()));
		SlaveFifoSettingValidation.setupslaveFifoSettingsValidationForNumeric(slaveFifoSettingsTab, tab,
				end1BufferSizeValue, endpointSettingsTabErrorList,
				SlaveFifoSettingValidation.INVALID_NUMERIC_ERROR_MESSAGE, slaveFIFOSettings2, 999984, "Buffer Size");
		end1BufferSizeValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText("Buffer Size (Bytes) : " + end1BufferSizeValue.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		end1BufferSizeValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getSLAVE_FIFO_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getSLAVE_FIFO_SETTINGS().getBUFFER_SIZE());
				}
			}
		});
		// end1BufferSizeValue.setOnMouseExited(new EventHandler<Event>() {
		// @Override
		// public void handle(Event event) {
		// helpContent.setText("");
		// }
		// });

		/** Buffer Count **/
		Label end1BufferCountLabel = new Label("Buffer Count : ");
		leftAnchorGridPane.setMargin(end1BufferCountLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(end1BufferCountLabel, 0, 3);
		TextField end1BufferCountValue = new TextField();
		end1BufferCountValue.setTooltip(new Tooltip("Choose the number of buffers per endpoint"));
		end1BufferCountValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = end1BufferCountValue.localToScreen(end1BufferCountValue.getBoundsInLocal());
			end1BufferCountValue.getTooltip().setX(bounds.getMaxX());
		});
		end1BufferCountValue.setMaxWidth(40);
		end1BufferCountValue.setText(String.valueOf(slaveFIFOSettings2.getBUFFER_COUNT()));
		leftAnchorGridPane.add(end1BufferCountValue, 1, 3);
		SlaveFifoSettingValidation.setupslaveFifoSettingsValidationForNumeric(slaveFifoSettingsTab, tab,
				end1BufferCountValue, endpointSettingsTabErrorList,
				SlaveFifoSettingValidation.INVALID_NUMERIC_ERROR_MESSAGE, slaveFIFOSettings2, 8, "Buffer Count");
		end1BufferCountValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText("Buffer Count : " + end1BufferCountValue.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		end1BufferCountValue.setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (sx3ConfigurationHelp.getSLAVE_FIFO_SETTINGS() != null) {
					helpContent.getEngine()
							.loadContent(sx3ConfigurationHelp.getSLAVE_FIFO_SETTINGS().getBUFFER_COUNT());
				}
			}
		});
		// end1BufferCountValue.setOnMouseExited(new EventHandler<Event>() {
		// @Override
		// public void handle(Event event) {
		// helpContent.setText("");
		// }
		// });

		/** Total Used Buffer Space **/
		Label totalUseBufferSpaceLabel = new Label("Total used Buffer Space (KB) : ");
		leftAnchorGridPane.setMargin(totalUseBufferSpaceLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(totalUseBufferSpaceLabel, 0, 4);
		TextField totalUseBufferSpaceValue = new TextField();
		totalUseBufferSpaceValue.setMaxWidth(40);
		totalUseBufferSpaceValue.setText(String.valueOf(slaveFIFOSettings2.getBUFFER_SPACE()));
		totalUseBufferSpaceValue.setDisable(true);

		leftAnchorGridPane.add(totalUseBufferSpaceValue, 1, 4);
		anchorPane.getChildren().add(leftAnchorGridPane);
		tab.setContent(anchorPane);
		return tab;

	}

	/**
	 * ------------------------------------------------------ Global Used
	 * methods -------------------------------------------
	 **/

	/**
	 * Create Configuration
	 * 
	 * @throws IOException
	 **/
	@FXML
	public void createNewConfiguration() throws IOException {
		if (!checkFieldEditOrNot) {
			final Stage stage = new Stage();
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setTitle("Device Details");
			dialog.setResizable(true);
			Label device = new Label("Select Device ");
			ComboBox<String> deviceCombo = new ComboBox<String>();
			deviceCombo.getItems().addAll(DeviceSettingsConstant.DEVICE_NAME);
			Label fileName = new Label("Configuration File Name");
			TextField fileName1 = new TextField();
			DeviceSettingsValidations.fileNameValidation(fileName1, DeviceSettingsValidations.INVALID_FILE_NAME);
			Label filePath = new Label(DeviceSettingsConstant.CONFIGURATION_FILE_PATH);
			TextField filePath1 = new TextField();
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

			Button button = new Button("...");
			button.setOnAction(e -> {
				File selectedDirectory = directoryChooser.showDialog(stage);
				if (selectedDirectory != null) {
					filePath1.setText(selectedDirectory.getAbsolutePath());
				}
			});
			GridPane grid = new GridPane();
			grid.add(device, 1, 1);
			grid.add(deviceCombo, 2, 1);
			grid.add(fileName, 1, 2);
			grid.add(fileName1, 2, 2);
			grid.add(filePath, 1, 3);
			grid.add(filePath1, 2, 3);
			grid.add(button, 3, 3);
			grid.setHgap(10);
			grid.setVgap(10);
			dialog.getDialogPane().setContent(grid);
			ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
			ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			dialog.getDialogPane().getButtonTypes().add(buttonCancel);
			final Button okButton = (Button) dialog.getDialogPane().lookupButton(buttonTypeOk);
			okButton.setDisable(true);
			deviceCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
				if ((fileName1.getText() != null && !fileName1.getText().equals(""))
						&& (filePath1.getText() != null && !filePath1.getText().equals("")) && newValue != null) {
					okButton.setDisable(false);
				} else {
					okButton.setDisable(true);
				}
			});
			fileName1.textProperty().addListener((observable1, oldValue1, newValue1) -> {
				if ((deviceCombo.getValue() != null && !deviceCombo.getValue().isEmpty())
						&& (filePath1.getText() != null && !filePath1.getText().isEmpty())) {
					okButton.setDisable(false);
				}
				if (newValue1 == null || newValue1.equals("")) {
					okButton.setDisable(true);
				}
			});
			filePath1.textProperty().addListener((observable2, oldValue2, newValue2) -> {
				if ((deviceCombo.getValue() != null && !deviceCombo.getValue().isEmpty())
						&& (fileName1.getText() != null && !fileName1.getText().isEmpty()) && newValue2 != null) {
					okButton.setDisable(false);
				}
				if (newValue2 == null || newValue2.equals("")) {
					okButton.setDisable(true);
				}
			});
			Optional<ButtonType> showAndWait = dialog.showAndWait();
			if (showAndWait.get() == buttonTypeOk) {

				createConfigurationFile(deviceCombo, fileName1, filePath1);
			}
			if (showAndWait.get() == buttonCancel) {
				dialog.close();
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
	 * ------------------------------------------------------ Global Used
	 * methods -------------------------------------------
	 **/

	/**
	 * Program Configurations
	 * 
	 * @throws IOException
	 **/
	@FXML
	public void programConfiguration() throws IOException {
		// Process process = new
		// ProcessBuilder("C:\\PathToExe\\MyExe.exe","param1","param2").start();
//		Process process = new ProcessBuilder("C:\\Users\\Lenovo\\Documents\\SX3\\CyUSB (2)\\CyUSB (2)\\CyUSB\\ConsoleApp3\\bin\\Release\\ConsoleApp3.exe").start();
//		InputStream processInputStream = process.getInputStream();
//		OutputStream processOutputStream = process.getOutputStream();
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(processOutputStream));
//		BufferedReader reader = new BufferedReader(new InputStreamReader(processInputStream));
//
//		System.err.println(process.getInputStream().getClass().getName());

		// Before programming we need to merge the list of files
		MergeFinalFirmwareArtifacts.mergeAllFiles();

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Select Device");
		dialog.setWidth(200);
		dialog.setResizable(false);
		Label device = new Label("Device :");
		ComboBox<String> deviceCombo = new ComboBox<String>();
		ArrayList<UsbDevice> deviceList;
		try {
			deviceList = getInstance().getDeviceList();

			for (UsbDevice usbDevice : deviceList) {
				UsbDeviceDescriptor usbDeviceDescriptor = usbDevice.getUsbDeviceDescriptor();
				String vendorId = String.format("%04X", usbDeviceDescriptor.idVendor());
				if (vendorId.equalsIgnoreCase("04b4")) {
					String productId = String.format("%04x%n", usbDeviceDescriptor.idProduct());
					deviceCombo.getItems().add(productId);
				}
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (UsbException e) {
			e.printStackTrace();
		}

		GridPane grid = new GridPane();
		grid.add(device, 1, 1);
		grid.add(deviceCombo, 2, 1);
		grid.setHgap(10);
		grid.setVgap(10);
		dialog.getDialogPane().setContent(grid);
		ButtonType buttonTypeOk = new ButtonType("Connect", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().add(buttonCancel);
		final Button okButton = (Button) dialog.getDialogPane().lookupButton(buttonTypeOk);
		okButton.setDisable(true);
		deviceCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
			okButton.setDisable(newValue == null);
		});
		Optional<ButtonType> showAndWait = dialog.showAndWait();
		if (showAndWait.get() == buttonTypeOk) {

		}
		if (showAndWait.get() == buttonCancel) {
			dialog.close();
		}

		// MergeFinalFirmwareArtifacts.mergeAllFiles();
	}

	private USBManager getInstance() throws SecurityException, UsbException {
		if (instance == null) {
			instance = new USBManager();
		}
		return instance;
	}

	private void createConfigurationFile(ComboBox<String> deviceCombo, TextField fileName1, TextField filePath1) {
		try {
			slaveFifoSettingsTab.setDisable(true);
			Endpoint_1.setDisable(false);
			Endpoint_2.setDisable(true);
			performLoadAction = false;
			File file = new File(filePath1.getText() + File.separator + fileName1.getText());
			file.mkdir();
			if (file.exists()) {
				noOfEndpointChanges = false;
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
				List<String> lines = Arrays.asList("Device :" + deviceCombo.getValue());
				file.createNewFile();
				sx3ConfigurationFilePath = file.getAbsolutePath() + File.separator + fileName1.getText() + ".json";
				Path path = Paths.get(sx3ConfigurationFilePath);
				Files.write(path, lines, StandardCharsets.UTF_8);
				deviceName.setText(deviceCombo.getValue());
				sx3Configuration.setDEVICE_NAME(deviceCombo.getValue());
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

				logDetails.setText(new Date() + " EZ-USB SX3 Configuration Utility Launched.\n<br>");
				logDetails.appendText("Device Name : " + deviceCombo.getValue() + ".\n<br>");
				logDetails.appendText("Configuration File Name : " + fileName1.getText() + ".\n<br>");
				logDetails.appendText("Configuration File Path : " + file.getAbsolutePath() + ".\n<br>");
				logDetails.appendText("Configuration file created Successfully.\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				checkFieldEditOrNot = false;
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
		uvcHeaderAddition.setDisable(true);
		vendorId.setText("04b4");
		productId.setText("");
		enableRemoteWakeup.setSelected(false);
		powerConfiguration.setValue("Bus Powered");
		fifoClockFrequency.setText("");
		interFaceType.getItems().clear();
		interFaceType.getItems().addAll(DeviceSettingsConstant.FIFO_INTERFACE_TYPE);
		debugValue.setValue("");
		enableDebugLevel.setSelected(false);
		noOfEndpoint.setValue("1");
		Endpoint_1.getItems().clear();
		Endpoint_1.setValue(null);
		Endpoint_2.getItems().clear();
		Endpoint_2.setValue(null);
		enableFPGA.setSelected(false);
		chooseBitFile.setText("");
		fpgaFamily.setValue("Lattice ECP5");
		fifoBusWidth.setValue("");
		i2cSlaveAddress.setText("");
		i2cFrequency.setValue("100000");
		uvcuacTabpane.getTabs().clear();
		uvcuacSettingsTab.setDisable(true);
		videoSourceConfigTabpane.getTabs().clear();
		videoSourceConfigTab.setDisable(true);
		slaveFifoSettingsTabpane.getTabs().clear();
		slaveFifoSettingsTab.setDisable(true);
		if (deviceName.equals("SX3 Data (16-bit)")) {
			Endpoint_1.setDisable(false);
			Endpoint_2.setDisable(true);
			fifoBusWidth.getItems().clear();
			fifoBusWidth.getItems().addAll(DeviceSettingsConstant.FIFO_BUS_WIDTH_16BIT);
			Endpoint_1.getItems().addAll(DeviceSettingsConstant.ENDPOINTS_SX3_DATA);
			Endpoint_2.getItems().addAll(DeviceSettingsConstant.ENDPOINTS_SX3_DATA);
			gpioReset();
			slaveFifoSettingsTab.setDisable(true);
		} else if (deviceName.equals("SX3-Data (32-bit)")) {
			Endpoint_1.setDisable(false);
			Endpoint_2.setDisable(true);
			fifoBusWidth.getItems().clear();
			fifoBusWidth.getItems().addAll("8", "16", "24", "32");
			fifoBusWidth.setValue("");
			Endpoint_1.getItems().addAll(DeviceSettingsConstant.ENDPOINTS_SX3_DATA);
			Endpoint_2.getItems().addAll(DeviceSettingsConstant.ENDPOINTS_SX3_DATA);
			gpioReset();
			slaveFifoSettingsTab.setDisable(true);
		} else {
			uvcVersion.getItems().clear();
			uvcVersion.getItems().addAll(DeviceSettingsConstant.UVC_VERSION);
			uvcHeaderAddition.getItems().clear();
			uvcHeaderAddition.getItems().addAll(DeviceSettingsConstant.UVC_HEADER_ADDITION);
			Endpoint_1.getItems().addAll(DeviceSettingsConstant.ENDPOINTS_SX3_UVC);
			Endpoint_2.getItems().addAll(DeviceSettingsConstant.ENDPOINTS_SX3_UVC);
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

		/** Config Table General Data **/
		ConfigTableGeneral configTableGeneral = new ConfigTableGeneral();
		configTableGeneral.setSIGNATURE("CY");
		configTableGeneral.setCONFIG_TABLE_CHECKSUM("0xCCCC");
		configTableGeneral.setCONFIG_TABLE_LENGTH("0xFFFFFFFF");
		configTableGeneral.setVERSION_MAJOR(1);
		configTableGeneral.setVERSION_MINOR(0);
		configTableGeneral.setVERSION_PATCH(0);
		configTableGeneral.setCONFIGURATION_TYPE(4);
		configTableGeneral.setRESERVED("FF");

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
		if(!vendorId.getText().equals("")) {
			usbSetting.setVENDOR_ID("0x" + vendorId.getText());
		}else {
			logDetails.appendText("<span style='color:red;'>Error : Vendor ID should not be empty.\n</span><br>");
		}
		if(!productId.getText().equals("")) {
			usbSetting.setPRODUCT_ID("0x" + productId.getText());
		}else {
			logDetails.appendText("<span style='color:red;'>Error : Product ID should not be empty.\n</span><br>");
		}
		if(!manufacture.getText().equals("")) {
			usbSetting.setMANUFACTURER_STRING(manufacture.getText());
		}else {
			logDetails.appendText("<span style='color:red;'>Error : Manufacture should not be empty.\n</span><br>");
		}
		usbSetting.setLEN_MANUFACTURER_STRING(manufacture.getText().length());
		if(!productString.getText().equals("")) {
			usbSetting.setPRODUCT_STRING(productString.getText());
		}else {
			logDetails.appendText("<span style='color:red;'>Error : Product String should not be empty.\n</span><br>");
		}
		usbSetting.setLEN_PRODUCT_STRING(productString.getText().length());
		if (autoGenerateSerialNumber.isSelected()) {
			usbSetting.setSERIALNUMBER_STRING("");
			usbSetting.setLEN_SERIALNUMBER(0);
			usbSetting.setAUTO_INCREMENT_SERIAL_NUMBER(0);
			usbSetting.setAUTO_GENERATE_SERIAL_NUMBER("Enable");
		} else {
			usbSetting.setAUTO_GENERATE_SERIAL_NUMBER("Disable");
			usbSetting.setSERIALNUMBER_STRING(serialNumber.getText());
			usbSetting.setLEN_SERIALNUMBER(serialNumber.getText().length());
			usbSetting.setAUTO_INCREMENT_SERIAL_NUMBER(Integer.parseInt(serialNumberIncrement.getValue()));
		}
		if (enableRemoteWakeup.isSelected()) {
			usbSetting.setREMOTE_WAKEUP_ENABLE("Enable");
		} else {
			usbSetting.setREMOTE_WAKEUP_ENABLE("Disable");
		}
		usbSetting.setPOWER_CONFIGURATION(powerConfiguration.getValue());
		usbSetting.setNUM_ENDPOINTS(Integer.parseInt(noOfEndpoint.getValue()));
		usbSetting.setENDPOINT1_TYPE(Endpoint_1.getValue());
		if (interFaceType.getValue() != null) {
			usbSetting.setFIFO_INTERFACE_TYPE(interFaceType.getValue());
		} else {
			usbSetting.setFIFO_INTERFACE_TYPE("");
		}
		if (deviceName.getText().equals("SX3 UVC")) {
			if (uvcVersion.getValue() != null) {
				usbSetting.setUVC_VERSION(uvcVersion.getValue());
			} else {
				usbSetting.setUVC_VERSION("");
			}
		}
		if (deviceName.getText().equals("SX3 UVC")) {
			if (uvcHeaderAddition.getValue() != null) {
				usbSetting.setUVC_HEADER_ADDITION(uvcHeaderAddition.getValue());
			} else {
				usbSetting.setUVC_HEADER_ADDITION("");
			}
		}
		usbSetting.setCONFIG_DSCR_LENGTH_SS("0xFF");
		usbSetting.setCONFIG_DSCR_LENGTH_HS("0xFF");
		usbSetting.setCONFIG_DSCR_LENGTH_FS("0xFF");
		if (noOfEndpoint.getValue().equals("2") && Endpoint_2.getValue() != null) {
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
		}else {
			logDetails.appendText("<span style='color:red;'>Error : FIFO Clock Frequency should not be empty.\n</span><br>");
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

		/** GPIOs Data **/
		GPIOs gpios = new GPIOs();
		gpios.setGPIO_1_SETTING(gpio1.getValue());
		gpios.setGPIO_2_SETTING(gpio2.getValue());
		gpios.setGPIO_3_SETTING(gpio3.getValue());
		gpios.setGPIO_4_SETTING(gpio4.getValue());
		gpios.setGPIO_5_SETTING(gpio5.getValue());
		gpios.setGPIO_6_SETTING(gpio6.getValue());
		gpios.setGPIO_7_SETTING(gpio7.getValue());
		gpios.setRESERVED("FF");
		deviceSettings.setGPIOS_SETTINGS(gpios);

		/** FIFO Master Config **/
		FifoMasterConfig fifoMasterConfig = new FifoMasterConfig();
		if (enableFPGA.isSelected()) {
			fifoMasterConfig.setFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN("Enable");
			fifoMasterConfig.setFPGA_FAMILY(fpgaFamily.getValue());
			fifoMasterConfig.setBIT_FILE_PATH(chooseBitFile.getText());
		} else {
			fifoMasterConfig.setFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN("Disable");
			fifoMasterConfig.setFPGA_FAMILY("");
			fifoMasterConfig.setBIT_FILE_PATH("");
		}
		if(!i2cSlaveAddress.getText().equals("")) {
			String i2cSlaveAddressText = i2cSlaveAddress.getText().isEmpty() ? "" : "0x" + i2cSlaveAddress.getText();
			fifoMasterConfig.setI2C_SLAVE_ADDRESS(i2cSlaveAddressText);
		}else {
			logDetails.appendText("<span style='color:red;'>Error : I2C slave address should not be empty.\n</span><br>");
		}
		fifoMasterConfig.setI2C_FREQUENCY(Integer.parseInt(i2cFrequency.getValue()));
		String bitFileSizeText = bitFileSize.getText();
		int bitFileSizeIntval = bitFileSizeText != null && !bitFileSizeText.isEmpty()
				? Integer.parseInt(bitFileSizeText) : 0;
		fifoMasterConfig.setBIT_FILE_SIZE(bitFileSizeIntval);
		fifoMasterConfig.setRESERVED("FF");
		if ((Endpoint_1.getValue() != null && Endpoint_1.getValue().equals("UVC"))
				|| (Endpoint_2.getValue() != null && Endpoint_2.getValue().equals("UVC"))) {
			LinkedList<FormatAndResolutions> ex = new LinkedList<>();
			List<UVCSettings> uvc_SETTINGS = sx3Configuration.getUVC_SETTINGS();
			Set<String> countImageFormat = new HashSet<>();
			for (UVCSettings uvcSettings : uvc_SETTINGS) {
				FormatAndResolution format_RESOLUTION = uvcSettings.getFORMAT_RESOLUTION();
				List<FormatAndResolutions> format_RESOLUTIONS = format_RESOLUTION.getFORMAT_RESOLUTIONS();
				if (format_RESOLUTIONS.size() == 1 && format_RESOLUTIONS.get(0).getIMAGE_FORMAT().equals("")) {
					ex.add(format_RESOLUTIONS.get(0));
					format_RESOLUTION.setFORMAT_RESOLUTIONS(ex);
					format_RESOLUTION.setTOTAL_NUMBER_OF_RESOLUTIONS(ex.size());
					format_RESOLUTION.setTOTAL_NUMBER_OF_IMAGE_FORMATS(0);
				} else {
					for (String imgFormat : UVCSettingsConstants.IMAGE_FORMAT) {
						for (FormatAndResolutions formatAndResolutions : format_RESOLUTIONS) {
							countImageFormat.add(formatAndResolutions.getIMAGE_FORMAT());
							if (imgFormat.equals(formatAndResolutions.getIMAGE_FORMAT())) {
								ex.add(formatAndResolutions);
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
					format_RESOLUTION.setFORMAT_RESOLUTIONS(ex);
					format_RESOLUTION.setTOTAL_NUMBER_OF_RESOLUTIONS(ex.size());
					format_RESOLUTION.setTOTAL_NUMBER_OF_IMAGE_FORMATS(countImageFormat.size());
				}
			}

		}
		
		logDetails.appendText(logDetails2.getText());
		if(sx3Configuration.getVIDEO_SOURCE_CONFIG() != null) {
			List<VideoSourceConfig> video_SOURCE_CONFIG = sx3Configuration.getVIDEO_SOURCE_CONFIG();
			for(int i=0;i<video_SOURCE_CONFIG.size();i++) {
				if(video_SOURCE_CONFIG.get(i).getI2C_SLAVE_ADDRESS().equals("")) {
					logDetails2.appendText("<span style='color:red;'> I2C slave address should not be empty.\n<span<br>");
				}
			}
		}

		/** Set SX3Configuration data **/
		sx3Configuration.setTOOL("EZ_USB_SX3_CONFIGURATION_UTILITY");
		sx3Configuration.setTOOL_VERSION("1.0");
		sx3Configuration.setCONFIG_TABLE_GENERAL(configTableGeneral);
		sx3Configuration.setCONFIG_TABLE_OFFSET_TABLE(configTableOffSetTable);
		sx3Configuration.setDEVICE_SETTINGS(deviceSettings);
		sx3Configuration.setFIFO_MASTER_CONFIG(fifoMasterConfig);
		List<UACSettings> uac_SETTINGS = sx3Configuration.getUAC_SETTINGS();
		if (uac_SETTINGS != null) {
			for (int i = 0; i < uac_SETTINGS.size(); i++) {
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
		}

		/** Set UVC Bit Mask Value data **/
		List<UVCSettings> uvc_SETTINGS = sx3Configuration.getUVC_SETTINGS();
		if (uvc_SETTINGS != null) {
			for (int i = 0; i < uvc_SETTINGS.size(); i++) {
				// set Camera control bit mask
				CameraControl camera_CONTROL = uvc_SETTINGS.get(i).getCAMERA_CONTROL();
				String bitmaskCCVal = SX3ConfigHexFileUtil.getBitMaskBinaryValue(camera_CONTROL,
						SX3PropertiesConstants.UVC_SETTINGS_CAMERA_CONTROL_FIELDS[0]);
				camera_CONTROL.setCAMERA_CONTROLS_ENABLED_BITMASK(SX3PropertiesConstants.BINAARY_PREFIX + bitmaskCCVal);
				// set processing unit control bit mask
				ProcessingUnitControl processing_UNIT_CONTROL = uvc_SETTINGS.get(i).getPROCESSING_UNIT_CONTROL();
				String bitmaskPUCVal = SX3ConfigHexFileUtil.getBitMaskBinaryValue(processing_UNIT_CONTROL,
						SX3PropertiesConstants.UVC_SETTINGS_PROCESSING_UNIT_CONTROL_FIELDS[0]);
				processing_UNIT_CONTROL.setPROCESSING_UNIT_ENABLED_CONTROLS_BITMASK(
						SX3PropertiesConstants.BINAARY_PREFIX + bitmaskPUCVal);
			}
		}

		SX3ConfigCommonUtil.createSx3JsonFile(sx3Configuration, sx3ConfigurationFilePath);
		logDetails.appendText("Configuration saved.\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
		/** Convert Json to Hex files **/
		File configJsonFile = BytesStreamsAndHexFileUtil.getConfigJsonFile();
		if (configJsonFile.exists()) {
			// Convert config json to hex file
			SX3ConfigHexFileUtil.convertJsonToHexFile(configJsonFile);
			// Convert video source config txt to hex file
			SX3ConfigHexFileUtil.convertVideoSourceConfigToHexFile(configJsonFile);

		} else {
			BytesStreamsAndHexFileUtil.log("SX3Configuration Json file not found");
		}
		// serialNumber.setDisable(true);
	}

	/** load Configuration **/
	@FXML
	public void loadConfiguration() {
		if (!checkFieldEditOrNot) {
			loadEndpoints = new ArrayList<>();
			final Stage stage = new Stage();
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setTitle("Configuration File");
			dialog.setResizable(true);
			Label filePath2 = new Label(DeviceSettingsConstant.CONFIGURATION_FILE_PATH);
			TextField filePath1 = new TextField();
			FileChooser fileChooser = new FileChooser();
			// fileChooser.setInitialDirectory(new File("src"));

			Button button = new Button("...");
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					configureFileChooser(fileChooser);
					File selectedFile = fileChooser.showOpenDialog(stage);
					if (selectedFile != null) {
						filePath1.setText(selectedFile.getAbsolutePath());
					}
				}
			});
			GridPane grid = new GridPane();
			grid.add(filePath2, 1, 1);
			grid.add(filePath1, 2, 1);
			grid.add(button, 3, 1);
			grid.setHgap(10);
			grid.setVgap(10);
			dialog.getDialogPane().setContent(grid);
			ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
			ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			dialog.getDialogPane().getButtonTypes().add(buttonCancel);
			final Button okButton = (Button) dialog.getDialogPane().lookupButton(buttonTypeOk);
			okButton.setDisable(true);
			filePath1.textProperty().addListener((observable, oldValue, newValue) -> {
				okButton.setDisable(newValue == null);
			});
			Optional<ButtonType> showAndWait = dialog.showAndWait();
			if (showAndWait.get() == buttonTypeOk) {
				loadConfigurationFile(filePath1);
			}
			if (showAndWait.get() == buttonCancel) {
				dialog.close();
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

	private void configureFileChooser(FileChooser fileChooser) {
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));

	}

	public void loadConfigurationFile(TextField filePath1) {
		noOfEndpointChanges = false;
		endpointOneChanges = false;
		endpointTwoChanges = false;
		endpointValues = new HashMap<>();
		endpointOneActionPerformed = false;
		endpointTwoActionPerformed = false;
		deviceName.setText("");
		sx3Configuration = new SX3Configuration();
		deviceSettingTabErrorList = new HashMap<>();
		performLoadAction = true;
		deviceSettingsTabSplitpane.setVisible(true);
		welcomeScreen.getChildren().clear();
		welcomeScreen.setVisible(false);
		saveConfigFromToolBar.setDisable(false);
		saveConfigFromMenu.setDisable(false);
		fifoMasterConfigTab.setDisable(false);
		Stage primStage = (Stage) deviceName.getScene().getWindow();
		String sx3ConfigurationFilePath1 = filePath1.getText();
		primStage.setTitle("Cypress SX3 Configuration Utility - " + sx3ConfigurationFilePath1);
		sx3ConfigurationFilePath = filePath1.getText();
		// Set Config File path to preference
		SX3ConfigPreference.setSx3ConfigFilePathPreference(sx3ConfigurationFilePath);
		Gson gson = new Gson();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath1.getText()));
			sx3Configuration = gson.fromJson(br, SX3Configuration.class);
			if (sx3Configuration.getTOOL() != null
					&& sx3Configuration.getTOOL().equals("EZ_USB_SX3_CONFIGURATION_UTILITY")
					&& sx3Configuration.getTOOL_VERSION().equals("1.0")) {
				deviceName.setText(sx3Configuration.getDEVICE_NAME());
				resetDeviceSettings(deviceName.getText());
				DeviceSettings deviceSetting = sx3Configuration.getDEVICE_SETTINGS();
				if (sx3Configuration.getUVC_SETTINGS() != null && !sx3Configuration.getUVC_SETTINGS().isEmpty()) {
					uvcSettingList = sx3Configuration.getUVC_SETTINGS();
					videoSourceConfigList = sx3Configuration.getVIDEO_SOURCE_CONFIG();
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
				vendorId.setText(usbSetting.getVENDOR_ID().substring(2));
				validateVendorIdField(true);
				productId.setText(usbSetting.getPRODUCT_ID().substring(2));
				validateProductIdField(true);
				manufacture.setText(usbSetting.getMANUFACTURER_STRING());
				validateManufactureField(true);
				productString.setText(usbSetting.getPRODUCT_STRING());
				validateProductStringField(true);
				if (usbSetting.getAUTO_GENERATE_SERIAL_NUMBER().equals("Enable")) {
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
				noOfEndpoint.setValue(String.valueOf(usbSetting.getNUM_ENDPOINTS()));
				fifoBusWidth.setValue(String.valueOf(usbSetting.getFIFO_BUS_WIDTH()));
				if (usbSetting.getFIFO_CLOCK() == 0) {
					fifoClockFrequency.setText("");
				} else {
					fifoClockFrequency.setText(String.valueOf(usbSetting.getFIFO_CLOCK()));
				}
				validateFifoClockFrequencyField(true);
				gpio1.setValue(gpios.getGPIO_1_SETTING());
				gpio2.setValue(gpios.getGPIO_2_SETTING());
				gpio3.setValue(gpios.getGPIO_3_SETTING());
				gpio4.setValue(gpios.getGPIO_4_SETTING());
				gpio5.setValue(gpios.getGPIO_5_SETTING());
				gpio6.setValue(gpios.getGPIO_6_SETTING());
				gpio7.setValue(gpios.getGPIO_7_SETTING());
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
					noOfEndpointChanges = true;
					endpointValues.put("Endpoint1", usbSetting.getENDPOINT1_TYPE());
					endpointValues.put("Endpoint2", usbSetting.getENDPOINT2_TYPE());
					endpointTwoChanges = true;
					Endpoint_1.setValue(usbSetting.getENDPOINT1_TYPE());
					Endpoint_2.setValue(usbSetting.getENDPOINT2_TYPE());
				} else if ((usbSetting.getENDPOINT1_TYPE() != null && !usbSetting.getENDPOINT1_TYPE().isEmpty()
						&& usbSetting.getENDPOINT1_TYPE() != "")
						&& (usbSetting.getENDPOINT2_TYPE().isEmpty() || usbSetting.getENDPOINT2_TYPE() == null
								|| usbSetting.getENDPOINT2_TYPE() == "")) {
					loadEndpoints.add(usbSetting.getENDPOINT1_TYPE());
					endpointOneChanges = true;
					endpointValues.put("Endpoint1", usbSetting.getENDPOINT1_TYPE());
					Endpoint_1.setValue(usbSetting.getENDPOINT1_TYPE());
				} else {
					performLoadAction = false;
				}

				interFaceType.setValue(usbSetting.getFIFO_INTERFACE_TYPE());
				if ((usbSetting.getENDPOINT1_TYPE() != null && usbSetting.getENDPOINT1_TYPE().equals("UVC"))
						|| (usbSetting.getENDPOINT2_TYPE() != null && usbSetting.getENDPOINT2_TYPE().equals("UVC"))) {
					if (!usbSetting.getUVC_VERSION().equals("")) {
						uvcVersion.setValue(usbSetting.getUVC_VERSION());
						uvcHeaderAddition.setValue(usbSetting.getUVC_HEADER_ADDITION());
					}
				}
				FifoMasterConfig fifoMasterConfig = sx3Configuration.getFIFO_MASTER_CONFIG();
				if (fifoMasterConfig.getFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN().equals("Enable")) {
					enableFPGA.setSelected(true);
					fpgaFamily.setValue(fifoMasterConfig.getFPGA_FAMILY());
					chooseBitFile.setText(fifoMasterConfig.getBIT_FILE_PATH());
					File file = new File(fifoMasterConfig.getBIT_FILE_PATH());
					bitFileSize.setText(String.valueOf(file.length()));
				} else {
					enableFPGA.setSelected(false);
					fpgaFamily.setValue(fifoMasterConfig.getFPGA_FAMILY());
					chooseBitFile.setText(fifoMasterConfig.getBIT_FILE_PATH());
				}
				String i2c_SLAVE_ADDRESS = fifoMasterConfig.getI2C_SLAVE_ADDRESS();
				i2cSlaveAddress.setText(!i2c_SLAVE_ADDRESS.isEmpty() ? i2c_SLAVE_ADDRESS.substring(2) : "");
				validateFifoMasterSlaveAddress(true);
				i2cFrequency.setValue(String.valueOf(fifoMasterConfig.getI2C_FREQUENCY()));
				logDetails.setText(new Date() + " EZ-USB SX3 Configuration Utility Launched.\n<br>");
				logDetails.appendText("Device Name : " + sx3Configuration.getDEVICE_NAME() + ".\n<br>");
				logDetails.appendText("Configuration File Path : " + sx3ConfigurationFilePath + ".\n<br>");
				logDetails.appendText("Configuration file loaded Successfully.\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				checkFieldEditOrNot = false;
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText(null);
				a.setContentText("Select correct json file.");
				a.show();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Save Log **/
	@FXML
	public void saveLog() throws IOException {
		Stage primaryStage = (Stage) deviceName.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setInitialFileName(logFileName.getText());
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		// Show save file dialog
		File file = fileChooser.showSaveDialog(primaryStage);
		if (file != null) {
			String name = file.getName();
			saveTextToFile(logDetails.getText().replace("<br>", "").replace("<b>", "")
					.replace("<span style='color:red;'>", "").replace("</span>", "").replace("</b>", ""), file);
			logFileName.setText(name);
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
		logDetails.clear();
		logDetails.setText(new Date() + " EZ-USB SX3 Configuration Utility Launched.\n<br>");
		logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
	}

	/**
	 * -------------------------------------------------------- Load Model at
	 * the time of Apllication Start --------------------------
	 **/

	/** Load Help content from file **/
	@FXML
	private void loadHelpContent() {
		if (helpTab.isSelected()) {
			sx3ConfigurationHelp = new SX3ConfiguartionHelp();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			BufferedReader br;
			try {
				br = new BufferedReader(
						new UnicodeReader(new FileInputStream(new File(getHelpContentFilePath())), "UTF-8"));
				sx3ConfigurationHelp = gson.fromJson(br, SX3ConfiguartionHelp.class);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
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
		endpoint.setBUFFER_SIZE(0);
		endpoint.setBUFFER_COUNT(0);
		endpoint.setUSED_BUFFER_SPACE(20);
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
		// List<SensorConfig> sensorConfigList = new ArrayList<>();
		// SensorConfig sensorConfig = new SensorConfig("", "", "");
		// sensorConfigList.add(sensorConfig);
		// formatResolutionJson.setRESOLUTION_REGISTER_SETTING(sensorConfigList);
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
	private Map<String, String> loadChannelConfig() {
		Map<String, String> configChennal = new HashMap<String, String>();
		for (String propertyField : UACSettingFieldConstants.CHANNEL_CONFIGURATION_FIELDS) {
			configChennal.put(propertyField, "Disable");
		}
		return configChennal;
	}

	/** Load Feature Unit Control **/
	private Map<String, String> loadFeatureUnitControl() {
		Map<String, String> featurUnitControl = new HashMap<String, String>();
		for (String propertyField : UACSettingFieldConstants.FEATURE_UNIT_CONTROLS_FIELDS) {
			featurUnitControl.put(propertyField, "Disable");
		}
		return featurUnitControl;
	}

	/** Camera Control **/
	private List<Map<String, Map<String, Object>>> loadCameraControl1() {
		List<Map<String, Map<String, Object>>> cameraControlJsonList = new ArrayList<>();
		for (int i = 0; i < UVCSettingsConstants.CAMERA_CONTROL_LABEL.length; i++) {
			Map<String, Map<String, Object>> cameraControlObj = new HashMap<>();
			Map<String, Object> cameraControls = new HashMap<>();
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_ENABLE", "Disable");
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_MIN", 0.0);
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_MAX", 0.0);
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_STEP", 0.0);
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_DEFAULT", 0.0);
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_LENGTH", 0.0);
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_CURRENT_VALUE", 0.0);
			cameraControls.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i] + "_REGISTER_ADDRESS", "");
			cameraControls.put("RESERVED", "FF");
			cameraControlObj.put(UVCSettingsConstants.CAMERA_CONTROL_LABEL_NAME[i], cameraControls);
			cameraControlJsonList.add(cameraControlObj);
		}
		return cameraControlJsonList;
	}

	/** Processing Unit Control **/
	private List<Map<String, Map<String, Object>>> loadProcessingUnitControl() {
		List<Map<String, Map<String, Object>>> processingUnitControlJsonList = new ArrayList<>();
		for (int i = 0; i < UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL.length; i++) {
			Map<String, Map<String, Object>> processingUnitControlObj = new HashMap<>();
			Map<String, Object> processingUnitControlJson = new HashMap<>();
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_ENABLE",
					"Disable");
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_MIN", 0.0);
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_MAX", 0.0);
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_STEP", 0.0);
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_LENGTH", 0.0);
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_CURRENT_VALUE", 0.0);
			processingUnitControlJson.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_DEFAULT", 0.0);
			processingUnitControlJson
					.put(UVCSettingsConstants.PROCESSING_UNIT_CONTROL_LABEL_NAME[i] + "_REGISTER_ADDRESS", "");
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
		extensionUnitControl.setFIRMWARE_UPDATE_ENABLED("Disable");
		return extensionUnitControl;
	}

	/** Video Source Config **/
	private VideoSourceConfig loadVideoSourceConfig() {
		VideoSourceConfig videoSourceConfig = new VideoSourceConfig();
		videoSourceConfig.setENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD("Disable");
		videoSourceConfig.setVIDEO_SOURCE_CONFIG_FILE_PATH("");
		videoSourceConfig.setVIDEO_SOURCE_TYPE("");
		videoSourceConfig.setI2C_SLAVE_ADDRESS("");
		videoSourceConfig.setI2C_SLAVE_DATA_SIZE(0);
		videoSourceConfig.setI2C_SLAVE_REGISTER_SIZE(0);
		videoSourceConfig.setI2C_FREQUENCY(100000);
		videoSourceConfig.setRESERVED("FF");
		return videoSourceConfig;
	}

//	/** HDMI Source Confiduration **/
//	private HDMISourceConfiguration loadHDMISourceConfig() {
//		HDMISourceConfiguration hdmiSourceConfig = new HDMISourceConfiguration();
//		hdmiSourceConfig.setHDMI_SOURCE_REGISTER_ADDRESS("");
//		hdmiSourceConfig.setMASK_PATTERN("");
//		hdmiSourceConfig.setCOMPARE_VALUE("");
//		List<SensorConfig> sensorConfigList = new ArrayList<>();
//		SensorConfig sensorConfig = new SensorConfig("", "", "");
//		sensorConfigList.add(sensorConfig);
//		hdmiSourceConfig.setRESOLUTION_REGISTER_SETTING(sensorConfigList);
//		hdmiSourceConfig.setRESERVED("FF");
//		// TODO Auto-generated method stub
//		return hdmiSourceConfig;
//	}

	/** Slave FIFO Settings **/
	private SlaveFIFOSettings loadSlaveFifoSetting() {
		SlaveFIFOSettings slaveFifo = new SlaveFIFOSettings();
		slaveFifo.setBURST_LENGTH(1);
		slaveFifo.setRESERVED("FF");
		return slaveFifo;
	}

	@FXML
	public void OpenHelpContentPage() {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setWidth(700);
		dialog.setHeight(500);
		dialog.setTitle("Sensor Configuration Help");
		dialog.setResizable(true);
		dialog.getDialogPane().setContent(new BrowserHelpContent());
		ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().add(buttonCancel);
		dialog.show();
	}

	/**
	 * Returns help content file path
	 */
	public static String getHelpContentFilePath() {
		Properties configProperties = BytesStreamsAndHexFileUtil.getConfigProperties();
		String sx3helpContentPath = configProperties.getProperty(SX3PropertiesConstants.SX3_HELP_CONTENT_FILE_PATH_KEY);
		return sx3helpContentPath;
	}

	private void loadWelcomeContent() {
		final WebEngine webEngine = startScreen.getEngine();
		Properties configProperties = BytesStreamsAndHexFileUtil.getConfigProperties();
		String sx3helpContentPath = configProperties.getProperty(SX3PropertiesConstants.SX3_START_PAGE_FILE_NAME_KEY);
		File f = new File(sx3helpContentPath);
		webEngine.load(f.toURI().toString());
	}

}
