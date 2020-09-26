package sx3Configuration.ui;

import java.util.Map;

import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.web.WebView;

public class DeviceSettingsController {

	public static void setAllToolTip(String deviceName, TextField vendorId, TextField productId, TextField manufacture,
			TextField productString, CheckBox autoGenerateSerialNumber, TextField serialNumber, ComboBox<String> serialNumberIncrement,
			CheckBox enableRemoteWakeup, ComboBox<String> powerConfiguration, ChoiceBox<String> noOfEndpoint,
			ChoiceBox<String> endpoint_1, ComboBox<String> fifoBusWidth, TextField fifoClockFrequency,
			CheckBox enableDebugLevel, ComboBox<String> debugValue, ComboBox<String> gpio1, ComboBox<String> gpio2,
			ComboBox<String> gpio3, ComboBox<String> gpio4, ComboBox<String> gpio5, ComboBox<String> gpio6,
			ComboBox<String> gpio7, ComboBox<String> interFaceType, ComboBox<String> uvcVersion, ComboBox<String> uvcHeaderAddition,
			CheckBox enableFPGA, ComboBox<String> fpgaFamily, Button browseBitFile, TextField i2cSlaveAddress, ComboBox<String> i2cFrequency) {
		vendorId.setTooltip(new Tooltip("VID"));
		vendorId.getTooltip().setOnShowing(s->{
		    Bounds bounds = vendorId.localToScreen(vendorId.getBoundsInLocal());
		    vendorId.getTooltip().setX(bounds.getMaxX());
		});
		productId.setTooltip(new Tooltip("PID"));
		productId.getTooltip().setOnShowing(s->{
		    Bounds bounds = productId.localToScreen(productId.getBoundsInLocal());
		    productId.getTooltip().setX(bounds.getMaxX());
		});
		manufacture.setTooltip(new Tooltip("Manufacturer string index"));
		manufacture.getTooltip().setOnShowing(s->{
		    Bounds bounds = manufacture.localToScreen(manufacture.getBoundsInLocal());
		    manufacture.getTooltip().setX(bounds.getMaxX());
		});
		productString.setTooltip(new Tooltip("Product string index"));
		productString.getTooltip().setOnShowing(s->{
		    Bounds bounds = productString.localToScreen(productString.getBoundsInLocal());
		    productString.getTooltip().setX(bounds.getMaxX());
		});
		autoGenerateSerialNumber.setTooltip(new Tooltip("Auto-Generate Serial Number"));
		autoGenerateSerialNumber.getTooltip().setOnShowing(s->{
		    Bounds bounds = autoGenerateSerialNumber.localToScreen(autoGenerateSerialNumber.getBoundsInLocal());
		    autoGenerateSerialNumber.getTooltip().setX(bounds.getMaxX());
		});
		serialNumber.setTooltip(new Tooltip("Serial Number index"));
		serialNumber.getTooltip().setOnShowing(s->{
		    Bounds bounds = serialNumber.localToScreen(serialNumber.getBoundsInLocal());
		    serialNumber.getTooltip().setX(bounds.getMaxX());
		});
		serialNumberIncrement.setTooltip(new Tooltip("Choose increment value"));
		serialNumberIncrement.getTooltip().setOnShowing(s->{
		    Bounds bounds = serialNumberIncrement.localToScreen(serialNumberIncrement.getBoundsInLocal());
		    serialNumberIncrement.getTooltip().setX(bounds.getMaxX());
		});
		enableRemoteWakeup.setTooltip(new Tooltip("Enable remote wakeup option"));
		enableRemoteWakeup.getTooltip().setOnShowing(s->{
		    Bounds bounds = enableRemoteWakeup.localToScreen(enableRemoteWakeup.getBoundsInLocal());
		    enableRemoteWakeup.getTooltip().setX(bounds.getMaxX());
		});
		powerConfiguration.setTooltip(new Tooltip("Power configuration"));
		powerConfiguration.getTooltip().setOnShowing(s->{
		    Bounds bounds = powerConfiguration.localToScreen(powerConfiguration.getBoundsInLocal());
		    powerConfiguration.getTooltip().setX(bounds.getMaxX());
		});
		noOfEndpoint.setTooltip(new Tooltip("Choose number of data streams"));
		noOfEndpoint.getTooltip().setOnShowing(s->{
		    Bounds bounds = noOfEndpoint.localToScreen(noOfEndpoint.getBoundsInLocal());
		    noOfEndpoint.getTooltip().setX(bounds.getMaxX());
		});
		endpoint_1.setTooltip(new Tooltip("Data stream type"));
		endpoint_1.getTooltip().setOnShowing(s->{
		    Bounds bounds = endpoint_1.localToScreen(endpoint_1.getBoundsInLocal());
		    endpoint_1.getTooltip().setX(bounds.getMaxX());
		});
		if (deviceName.equals("SX3 Data (16-bit)")) {
			fifoBusWidth.setTooltip(new Tooltip("FIFO parallel bus width. Max = 16"));
		} else {
			fifoBusWidth.setTooltip(new Tooltip("FIFO parallel bus width. Max = 32"));
		}
		fifoBusWidth.getTooltip().setOnShowing(s->{
		    Bounds bounds = fifoBusWidth.localToScreen(fifoBusWidth.getBoundsInLocal());
		    fifoBusWidth.getTooltip().setX(bounds.getMaxX());
		});
		fifoClockFrequency.setTooltip(new Tooltip("FIFO Clock. Max = 100"));
		fifoClockFrequency.getTooltip().setOnShowing(s->{
		    Bounds bounds = fifoClockFrequency.localToScreen(fifoClockFrequency.getBoundsInLocal());
		    fifoClockFrequency.getTooltip().setX(bounds.getMaxX());
		});
		enableDebugLevel.setTooltip(new Tooltip("Enable Debug via USB COM port"));
		enableDebugLevel.getTooltip().setOnShowing(s->{
		    Bounds bounds = enableDebugLevel.localToScreen(enableDebugLevel.getBoundsInLocal());
		    enableDebugLevel.getTooltip().setX(bounds.getMaxX());
		});
		debugValue.setTooltip(new Tooltip("Debug level"));
		debugValue.getTooltip().setOnShowing(s->{
		    Bounds bounds = debugValue.localToScreen(debugValue.getBoundsInLocal());
		    debugValue.getTooltip().setX(bounds.getMaxX());
		});
		gpio1.setTooltip(new Tooltip("GPIO 1 setting"));
		gpio1.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio1.localToScreen(gpio1.getBoundsInLocal());
		    gpio1.getTooltip().setX(bounds.getMaxX());
		});
		gpio2.setTooltip(new Tooltip("GPIO 2 setting"));
		gpio2.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio2.localToScreen(gpio2.getBoundsInLocal());
		    gpio2.getTooltip().setX(bounds.getMaxX());
		});
		gpio3.setTooltip(new Tooltip("GPIO 3 setting"));
		gpio3.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio3.localToScreen(gpio3.getBoundsInLocal());
		    gpio3.getTooltip().setX(bounds.getMaxX());
		});
		gpio4.setTooltip(new Tooltip("GPIO 4 setting"));
		gpio4.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio4.localToScreen(gpio4.getBoundsInLocal());
		    gpio4.getTooltip().setX(bounds.getMaxX());
		});
		gpio5.setTooltip(new Tooltip("GPIO 5 setting"));
		gpio5.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio5.localToScreen(gpio5.getBoundsInLocal());
		    gpio5.getTooltip().setX(bounds.getMaxX());
		});
		gpio6.setTooltip(new Tooltip("GPIO 6 setting"));
		gpio6.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio6.localToScreen(gpio6.getBoundsInLocal());
		    gpio6.getTooltip().setX(bounds.getMaxX());
		});
		gpio7.setTooltip(new Tooltip("GPIO 7 setting"));
		gpio7.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio7.localToScreen(gpio7.getBoundsInLocal());
		    gpio7.getTooltip().setX(bounds.getMaxX());
		});
		interFaceType.setTooltip(new Tooltip("Interface Type"));
		interFaceType.getTooltip().setOnShowing(s->{
		    Bounds bounds = interFaceType.localToScreen(interFaceType.getBoundsInLocal());
		    interFaceType.getTooltip().setX(bounds.getMaxX());
		});
		uvcVersion.setTooltip(new Tooltip("UVC Version"));
		uvcVersion.getTooltip().setOnShowing(s->{
		    Bounds bounds = uvcVersion.localToScreen(uvcVersion.getBoundsInLocal());
		    uvcVersion.getTooltip().setX(bounds.getMaxX());
		});
		uvcHeaderAddition.setTooltip(new Tooltip("UVC Header Addition"));
		uvcHeaderAddition.getTooltip().setOnShowing(s->{
		    Bounds bounds = uvcHeaderAddition.localToScreen(uvcHeaderAddition.getBoundsInLocal());
		    uvcHeaderAddition.getTooltip().setX(bounds.getMaxX());
		});
		enableFPGA.setTooltip(new Tooltip("Enable FIFO master configuration from SX3"));
		enableFPGA.getTooltip().setOnShowing(s->{
		    Bounds bounds = enableFPGA.localToScreen(enableFPGA.getBoundsInLocal());
		    enableFPGA.getTooltip().setX(bounds.getMaxX());
		});
		fpgaFamily.setTooltip(new Tooltip("Choose the FPGA Family being used in the design"));
		fpgaFamily.getTooltip().setOnShowing(s->{
		    Bounds bounds = fpgaFamily.localToScreen(fpgaFamily.getBoundsInLocal());
		    fpgaFamily.getTooltip().setX(bounds.getMaxX());
		});
		browseBitFile.setTooltip(new Tooltip("Browse to configuration file (.bit)"));
		browseBitFile.getTooltip().setOnShowing(s->{
		    Bounds bounds = browseBitFile.localToScreen(browseBitFile.getBoundsInLocal());
		    browseBitFile.getTooltip().setX(bounds.getMaxX());
		});
		i2cSlaveAddress.setTooltip(new Tooltip("I2C Slave address for the FIFO Master"));
		i2cSlaveAddress.getTooltip().setOnShowing(s->{
		    Bounds bounds = i2cSlaveAddress.localToScreen(i2cSlaveAddress.getBoundsInLocal());
		    i2cSlaveAddress.getTooltip().setX(bounds.getMaxX());
		});
		i2cFrequency.setTooltip(new Tooltip("I2C Clock frequency"));
		i2cFrequency.getTooltip().setOnShowing(s->{
		    Bounds bounds = i2cFrequency.localToScreen(i2cFrequency.getBoundsInLocal());
		    i2cFrequency.getTooltip().setX(bounds.getMaxX());
		});
	}

	public static void logVendorID(Map<String, Boolean> deviceSettingTabErrorList, Tab deviceSettingsTab, TextField vendorId, TextArea logDetails, WebView logDetails1) {
		vendorId.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!vendorId.getText().equals("")) {
					logDetails.appendText("Vendor ID : " + vendorId.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					deviceSettingTabErrorList.put("vendorId", false);
					if(deviceSettingTabErrorList.containsValue(false)) {
						deviceSettingsTab.setStyle("");
						vendorId.setStyle("");
					}
				}else {
					deviceSettingTabErrorList.put("vendorId", true);
					if(deviceSettingTabErrorList.containsValue(true)) {
						deviceSettingsTab.setStyle("-fx-border-color:red;");
						vendorId.setStyle("-fx-border-color:red;");
					}
//					logDetails.appendText("<b><span style='color:red;'>Error : Vendor Id should not empty.\n</span></b><br>");
//					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
	}

	public static void logProductID(Map<String, Boolean> deviceSettingTabErrorList, Tab deviceSettingsTab, TextField productId, TextArea logDetails, WebView logDetails1) {
		productId.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!productId.getText().equals("")) {
					logDetails.appendText("Product ID : " + productId.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					deviceSettingTabErrorList.put("productId", false);
					if(deviceSettingTabErrorList.containsValue(false)) {
						deviceSettingsTab.setStyle("");
						productId.setStyle("");
					}
				}else {
					deviceSettingTabErrorList.put("productId", true);
					if(deviceSettingTabErrorList.containsValue(true)) {
						deviceSettingsTab.setStyle("-fx-border-color:red;");
						productId.setStyle("-fx-border-color:red;");
					}
//					logDetails.appendText("<b><span style='color:red;'>Error : Product Id should not empty.\n</span></b><br>");
//					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
	}

	public static void logManufacture(Map<String, Boolean> deviceSettingTabErrorList, Tab deviceSettingsTab, TextField manufacture, TextArea logDetails, WebView logDetails1) {
		manufacture.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!manufacture.getText().equals("")) {
					logDetails.appendText("Manufacture : " + manufacture.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					deviceSettingTabErrorList.put("manufacture", false);
					if(deviceSettingTabErrorList.containsValue(false)) {
						deviceSettingsTab.setStyle("");
						manufacture.setStyle("");
					}
				}else {
					deviceSettingTabErrorList.put("manufacture", true);
					if(deviceSettingTabErrorList.containsValue(true)) {
						deviceSettingsTab.setStyle("-fx-border-color:red;");
						manufacture.setStyle("-fx-border-color:red;");
					}
//					logDetails.appendText("<b><span style='color:red;'>Error : Manufacture should not empty.\n</span></b><br>");
//					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
	}

	public static void logProductString(Map<String, Boolean> deviceSettingTabErrorList, Tab deviceSettingsTab, TextField productString, TextArea logDetails, WebView logDetails1) {
		productString.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!productString.getText().equals("")) {
					logDetails.appendText("Product String : " + productString.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					deviceSettingTabErrorList.put("productString", false);
					if(deviceSettingTabErrorList.containsValue(false)) {
						deviceSettingsTab.setStyle("");
						productString.setStyle("");
					}
				}else {
					deviceSettingTabErrorList.put("productString", true);
					if(deviceSettingTabErrorList.containsValue(true)) {
						deviceSettingsTab.setStyle("-fx-border-color:red;");
						productString.setStyle("-fx-border-color:red;");
					}
//					logDetails.appendText("<b><span style='color:red;'>Error : Product String should not empty.\n</span></b><br>");
//					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
	}

	public static void logSerialNumber(Map<String, Boolean> deviceSettingTabErrorList, Tab deviceSettingsTab, TextField serialNumber, TextArea logDetails, WebView logDetails1) {
		serialNumber.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!serialNumber.getText().equals("")) {
					logDetails.appendText("Serial Number : " + serialNumber.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					deviceSettingTabErrorList.put("serialNumber", false);
					if(deviceSettingTabErrorList.containsValue(false)) {
						deviceSettingsTab.setStyle("");
						serialNumber.setStyle("");
					}
				}else {
					deviceSettingTabErrorList.put("serialNumber", true);
					if(deviceSettingTabErrorList.containsValue(true)) {
						deviceSettingsTab.setStyle("-fx-border-color:red;");
						serialNumber.setStyle("-fx-border-color:red;");
					}
//					logDetails.appendText("<b><span style='color:red;'>Error : Serial Number should not empty.\n</span></b><br>");
//					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
	}

	public static void logFifoClockFrequency(Map<String, Boolean> deviceSettingTabErrorList, Tab deviceSettingsTab, TextField fifoClockFrequency, TextArea logDetails, WebView logDetails1) {
		fifoClockFrequency.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!fifoClockFrequency.getText().equals("")) {
					logDetails.appendText("FIFO Clock Frequency (MHz) : " + fifoClockFrequency.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					deviceSettingTabErrorList.put("fifoClockFrequency", false);
					if(deviceSettingTabErrorList.containsValue(false)) {
						deviceSettingsTab.setStyle("");
						fifoClockFrequency.setStyle("");
					}
				}else {
					deviceSettingTabErrorList.put("serialNumber", true);
					if(deviceSettingTabErrorList.containsValue(true)) {
						deviceSettingsTab.setStyle("-fx-border-color:red;");
						fifoClockFrequency.setStyle("-fx-border-color:red;");
					}
//					logDetails.appendText("<b><span style='color:red;'>Error : FIFO Clock Frequency (MHz) should not empty.\n</span></b><br>");
//					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
	}

	public static void logFPGAI2CSlaveAddress(Map<String, Boolean> deviceSettingTabErrorList, Tab deviceSettingsTab, TextField i2cSlaveAddress, TextArea logDetails, WebView logDetails1) {
		i2cSlaveAddress.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!i2cSlaveAddress.getText().equals("")) {
					logDetails.appendText("FPGA I2C Slave Address : " + i2cSlaveAddress.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					deviceSettingTabErrorList.put("i2cSlaveAddress", false);
					if(deviceSettingTabErrorList.containsValue(false)) {
						deviceSettingsTab.setStyle("");
						i2cSlaveAddress.setStyle("");
					}
				}else {
					deviceSettingTabErrorList.put("i2cSlaveAddress", true);
					if(deviceSettingTabErrorList.containsValue(true)) {
						deviceSettingsTab.setStyle("-fx-border-color:red;");
						i2cSlaveAddress.setStyle("-fx-border-color:red;");
					}
//					logDetails.appendText("<b><span style='color:red;'>Error : FPGA I2C Slave Address should not empty.\n</span></b><br>");
//					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
		
	}

}
