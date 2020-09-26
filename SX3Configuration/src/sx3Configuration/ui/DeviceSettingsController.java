package sx3Configuration.ui;

import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class DeviceSettingsController {

	public static void setAllToolTip(Properties configProperties, String deviceName, TextField vendorId, TextField productId, TextField manufacture,
			TextField productString, CheckBox autoGenerateSerialNumber, TextField serialNumber, ComboBox<String> serialNumberIncrement,
			CheckBox enableRemoteWakeup, ComboBox<String> powerConfiguration, ChoiceBox<String> endpoint_1,
			ComboBox<String> fifoBusWidth, TextField fifoClockFrequency,
			CheckBox enableDebugLevel, ComboBox<String> debugValue, ComboBox<String> gpio1, ComboBox<String> gpio2,
			ComboBox<String> gpio3, ComboBox<String> gpio4, ComboBox<String> gpio5, ComboBox<String> gpio6,
			ComboBox<String> gpio7, ChoiceBox<String> interFaceType, ComboBox<String> uvcVersion, ComboBox<String> uvcHeaderAddition,
			CheckBox enableFPGA, ComboBox<String> fpgaFamily, Button browseBitFile, TextField i2cSlaveAddress, CheckBox deviceSttingFirmWare, ComboBox<String> deviceSttingI2CFrequency) {
		
		vendorId.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_VID"))); //$NON-NLS-1$
		vendorId.getTooltip().setOnShowing(s->{
		    Bounds bounds = vendorId.localToScreen(vendorId.getBoundsInLocal());
		    vendorId.getTooltip().setX(bounds.getMaxX());
		});
		productId.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_PID"))); //$NON-NLS-1$
		productId.getTooltip().setOnShowing(s->{
		    Bounds bounds = productId.localToScreen(productId.getBoundsInLocal());
		    productId.getTooltip().setX(bounds.getMaxX());
		});
		manufacture.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_MANUFACTURER"))); //$NON-NLS-1$
		manufacture.getTooltip().setOnShowing(s->{
		    Bounds bounds = manufacture.localToScreen(manufacture.getBoundsInLocal());
		    manufacture.getTooltip().setX(bounds.getMaxX());
		});
		productString.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_PRODUCT_STRING"))); //$NON-NLS-1$
		productString.getTooltip().setOnShowing(s->{
		    Bounds bounds = productString.localToScreen(productString.getBoundsInLocal());
		    productString.getTooltip().setX(bounds.getMaxX());
		});
		autoGenerateSerialNumber.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_AUTO_GENERATE_SERIAL_NUMBER"))); //$NON-NLS-1$
		autoGenerateSerialNumber.getTooltip().setOnShowing(s->{
		    Bounds bounds = autoGenerateSerialNumber.localToScreen(autoGenerateSerialNumber.getBoundsInLocal());
		    autoGenerateSerialNumber.getTooltip().setX(bounds.getMaxX());
		});
		serialNumber.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_SERIAL_NUMBER"))); //$NON-NLS-1$
		serialNumber.getTooltip().setOnShowing(s->{
		    Bounds bounds = serialNumber.localToScreen(serialNumber.getBoundsInLocal());
		    serialNumber.getTooltip().setX(bounds.getMaxX());
		});
		serialNumberIncrement.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_SERIAL_NUMBER_INCREMENT"))); //$NON-NLS-1$
		serialNumberIncrement.getTooltip().setOnShowing(s->{
		    Bounds bounds = serialNumberIncrement.localToScreen(serialNumberIncrement.getBoundsInLocal());
		    serialNumberIncrement.getTooltip().setX(bounds.getMaxX());
		});
		enableRemoteWakeup.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_ENABLE_REMOTE_WAKEUP"))); //$NON-NLS-1$
		enableRemoteWakeup.getTooltip().setOnShowing(s->{
		    Bounds bounds = enableRemoteWakeup.localToScreen(enableRemoteWakeup.getBoundsInLocal());
		    enableRemoteWakeup.getTooltip().setX(bounds.getMaxX());
		});
		powerConfiguration.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_POWER_CONFIGURATION"))); //$NON-NLS-1$
		powerConfiguration.getTooltip().setOnShowing(s->{
		    Bounds bounds = powerConfiguration.localToScreen(powerConfiguration.getBoundsInLocal());
		    powerConfiguration.getTooltip().setX(bounds.getMaxX());
		});
		if(deviceName.equals("SX3 UVC (CYUSB3017)")) {
			endpoint_1.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_DATA_STREAM_TYPE"))); //$NON-NLS-1$
		}else {
			endpoint_1.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_ENDPOINT_DIRECTION"))); //$NON-NLS-1$
		}
		endpoint_1.getTooltip().setOnShowing(s->{
		    Bounds bounds = endpoint_1.localToScreen(endpoint_1.getBoundsInLocal());
		    endpoint_1.getTooltip().setX(bounds.getMaxX());
		});
		if (deviceName.equals("SX3 Data-16 bit(CYUSB3015)")) { //$NON-NLS-1$
			fifoBusWidth.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_PARALLEL_BUS_WIDTH_16"))); //$NON-NLS-1$
		} else {
			fifoBusWidth.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_PARALLEL_BUS_WIDTH_32"))); //$NON-NLS-1$
		}
		fifoBusWidth.getTooltip().setOnShowing(s->{
		    Bounds bounds = fifoBusWidth.localToScreen(fifoBusWidth.getBoundsInLocal());
		    fifoBusWidth.getTooltip().setX(bounds.getMaxX());
		});
		fifoClockFrequency.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_FIFO_CLOCK_100"))); //$NON-NLS-1$
		fifoClockFrequency.getTooltip().setOnShowing(s->{
		    Bounds bounds = fifoClockFrequency.localToScreen(fifoClockFrequency.getBoundsInLocal());
		    fifoClockFrequency.getTooltip().setX(bounds.getMaxX());
		});
		enableDebugLevel.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_ENABLE_DEBUG"))); //$NON-NLS-1$
		enableDebugLevel.getTooltip().setOnShowing(s->{
		    Bounds bounds = enableDebugLevel.localToScreen(enableDebugLevel.getBoundsInLocal());
		    enableDebugLevel.getTooltip().setX(bounds.getMaxX());
		});
		debugValue.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_ENABLE_DEBUG_LEVEL"))); //$NON-NLS-1$
		debugValue.getTooltip().setOnShowing(s->{
		    Bounds bounds = debugValue.localToScreen(debugValue.getBoundsInLocal());
		    debugValue.getTooltip().setX(bounds.getMaxX());
		});
		gpio1.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_GPIO1"))); //$NON-NLS-1$
		gpio1.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio1.localToScreen(gpio1.getBoundsInLocal());
		    gpio1.getTooltip().setX(bounds.getMaxX());
		});
		gpio2.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_GPIO2"))); //$NON-NLS-1$
		gpio2.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio2.localToScreen(gpio2.getBoundsInLocal());
		    gpio2.getTooltip().setX(bounds.getMaxX());
		});
		gpio3.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_GPIO3"))); //$NON-NLS-1$
		gpio3.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio3.localToScreen(gpio3.getBoundsInLocal());
		    gpio3.getTooltip().setX(bounds.getMaxX());
		});
		gpio4.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_GPIO4"))); //$NON-NLS-1$
		gpio4.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio4.localToScreen(gpio4.getBoundsInLocal());
		    gpio4.getTooltip().setX(bounds.getMaxX());
		});
		gpio5.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_GPIO5"))); //$NON-NLS-1$
		gpio5.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio5.localToScreen(gpio5.getBoundsInLocal());
		    gpio5.getTooltip().setX(bounds.getMaxX());
		});
		gpio6.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_GPIO6"))); //$NON-NLS-1$
		gpio6.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio6.localToScreen(gpio6.getBoundsInLocal());
		    gpio6.getTooltip().setX(bounds.getMaxX());
		});
		gpio7.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_GPIO7"))); //$NON-NLS-1$
		gpio7.getTooltip().setOnShowing(s->{
		    Bounds bounds = gpio7.localToScreen(gpio7.getBoundsInLocal());
		    gpio7.getTooltip().setX(bounds.getMaxX());
		});
		interFaceType.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_INTERFACE_TYPE"))); //$NON-NLS-1$
		interFaceType.getTooltip().setOnShowing(s->{
		    Bounds bounds = interFaceType.localToScreen(interFaceType.getBoundsInLocal());
		    interFaceType.getTooltip().setX(bounds.getMaxX());
		});
		uvcVersion.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_UVC_VERSION"))); //$NON-NLS-1$
		uvcVersion.getTooltip().setOnShowing(s->{
		    Bounds bounds = uvcVersion.localToScreen(uvcVersion.getBoundsInLocal());
		    uvcVersion.getTooltip().setX(bounds.getMaxX());
		});
		uvcHeaderAddition.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_UVC_HEADER_ADDITION"))); //$NON-NLS-1$
		uvcHeaderAddition.getTooltip().setOnShowing(s->{
		    Bounds bounds = uvcHeaderAddition.localToScreen(uvcHeaderAddition.getBoundsInLocal());
		    uvcHeaderAddition.getTooltip().setX(bounds.getMaxX());
		});
		enableFPGA.setTooltip(new Tooltip(configProperties.getProperty("FIFO_MASTER_CONFIG.TOOLTIP_ENABLE_FIFO_MASTER_CONFIGURATION"))); //$NON-NLS-1$
		enableFPGA.getTooltip().setOnShowing(s->{
		    Bounds bounds = enableFPGA.localToScreen(enableFPGA.getBoundsInLocal());
		    enableFPGA.getTooltip().setX(bounds.getMaxX());
		});
		fpgaFamily.setTooltip(new Tooltip(configProperties.getProperty("FIFO_MASTER_CONFIG.TOOLTIP_CHOOSE_FPGA_FAMILY"))); //$NON-NLS-1$
		fpgaFamily.getTooltip().setOnShowing(s->{
		    Bounds bounds = fpgaFamily.localToScreen(fpgaFamily.getBoundsInLocal());
		    fpgaFamily.getTooltip().setX(bounds.getMaxX());
		});
		browseBitFile.setTooltip(new Tooltip(configProperties.getProperty("FIFO_MASTER_CONFIG.TOOLTIP_CONFIGURATION_BIT_FILE"))); //$NON-NLS-1$
		browseBitFile.getTooltip().setOnShowing(s->{
		    Bounds bounds = browseBitFile.localToScreen(browseBitFile.getBoundsInLocal());
		    browseBitFile.getTooltip().setX(bounds.getMaxX());
		});
		i2cSlaveAddress.setTooltip(new Tooltip(configProperties.getProperty("FIFO_MASTER_CONFIG.TOOLTIP_I2C_SLAVE_ADDRESS"))); //$NON-NLS-1$
		i2cSlaveAddress.getTooltip().setOnShowing(s->{
		    Bounds bounds = i2cSlaveAddress.localToScreen(i2cSlaveAddress.getBoundsInLocal());
		    i2cSlaveAddress.getTooltip().setX(bounds.getMaxX());
		});
		deviceSttingFirmWare.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_FIRMWARE"))); //$NON-NLS-1$
		deviceSttingFirmWare.getTooltip().setOnShowing(s->{
		    Bounds bounds = deviceSttingFirmWare.localToScreen(deviceSttingFirmWare.getBoundsInLocal());
		    deviceSttingFirmWare.getTooltip().setX(bounds.getMaxX());
		});
		deviceSttingI2CFrequency.setTooltip(new Tooltip(configProperties.getProperty("DEVICE_SETTINGS.TOOLTIP_I2CFREQUENCY"))); //$NON-NLS-1$
		deviceSttingI2CFrequency.getTooltip().setOnShowing(s->{
		    Bounds bounds = deviceSttingI2CFrequency.localToScreen(deviceSttingI2CFrequency.getBoundsInLocal());
		    deviceSttingI2CFrequency.getTooltip().setX(bounds.getMaxX());
		});
	}
	
	
	public static void logVendorID(TextField vendorId) {
		vendorId.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!vendorId.getText().equals("")) {
					SX3Manager.getInstance().addLog("Vendor ID : " + vendorId.getText() + ".<br>");
				}
			}
		});
	}

	public static void logProductID(TextField productId) {
		productId.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!productId.getText().equals("")) {
					SX3Manager.getInstance().addLog("Product ID : " + productId.getText() + ".<br>");
					
				}
			}
		});
	}

	public static void logManufacture(TextField manufacture) {
		manufacture.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!manufacture.getText().equals("")) {
					SX3Manager.getInstance().addLog("Manufacture : " + manufacture.getText() + ".<br>");
				}
			}
		});
	}

	public static void logProductString(TextField productString) {
		productString.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!productString.getText().equals("")) {
					SX3Manager.getInstance().addLog("Product String : " + productString.getText() + ".<br>");
				}
			}
		});
	}

	public static void logSerialNumber(TextField serialNumber) {
		serialNumber.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!serialNumber.getText().equals("")) {
					SX3Manager.getInstance().addLog("Serial Number : " + serialNumber.getText() + ".<br>");
				}
			}
		});
	}

	public static void logFifoClockFrequency(TextField fifoClockFrequency) {
		fifoClockFrequency.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!fifoClockFrequency.getText().equals("")) {
					SX3Manager.getInstance().addLog("FIFO Clock Frequency (MHz) : " + fifoClockFrequency.getText() + ".<br>");
				}
			}
		});
	}

	public static void logFPGAI2CSlaveAddress(TextField i2cSlaveAddress) {
		i2cSlaveAddress.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!i2cSlaveAddress.getText().equals("")) {
					SX3Manager.getInstance().addLog("FPGA I2C Slave Address : " + i2cSlaveAddress.getText() + ".<br>");
				}
			}
		});
		
	}


	public static void logDeviceSettingFirmware(CheckBox deviceSttingFirmWare) {
		deviceSttingFirmWare.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(deviceSttingFirmWare.isSelected()) {
					SX3Manager.getInstance().addLog("Device Settings Firmware : Enable.<br>");
				}else {
					SX3Manager.getInstance().addLog("Device Settings Firmware : Disable.<br>");
				}
				
			}
		});
		
	}


	public static void logDeviceSettingI2CFrequency(ComboBox<String> deviceSttingI2CFrequency) {
		deviceSttingI2CFrequency.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SX3Manager.getInstance().addLog("Device Settings I2C Frequency : "+deviceSttingI2CFrequency.getValue()+".<br>");				
			}
		});
		
	}

}
