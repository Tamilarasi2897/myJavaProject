package sx3Configuration.model;

public class ExtensionUnitControl {
	private String DEVICE_RESET_VENDOR_COMMAND_ENABLED;
	private String I2C_READ_VENDOR_COMMAND_ENABLED;
	private String I2C_WRITE_VENDOR_COMMAND_ENABLED;
	private String FIRMWARE_VERSION_VENDOR_COMMAND_ENABLED;

	public String getDEVICE_RESET_VENDOR_COMMAND_ENABLED() {
		return DEVICE_RESET_VENDOR_COMMAND_ENABLED;
	}

	public void setDEVICE_RESET_VENDOR_COMMAND_ENABLED(String dEVICE_RESET_VENDOR_COMMAND_ENABLED) {
		DEVICE_RESET_VENDOR_COMMAND_ENABLED = dEVICE_RESET_VENDOR_COMMAND_ENABLED;
	}

	public String getI2C_READ_VENDOR_COMMAND_ENABLED() {
		return I2C_READ_VENDOR_COMMAND_ENABLED;
	}

	public void setI2C_READ_VENDOR_COMMAND_ENABLED(String i2c_READ_VENDOR_COMMAND_ENABLED) {
		I2C_READ_VENDOR_COMMAND_ENABLED = i2c_READ_VENDOR_COMMAND_ENABLED;
	}

	public String getI2C_WRITE_VENDOR_COMMAND_ENABLED() {
		return I2C_WRITE_VENDOR_COMMAND_ENABLED;
	}

	public void setI2C_WRITE_VENDOR_COMMAND_ENABLED(String i2c_WRITE_VENDOR_COMMAND_ENABLED) {
		I2C_WRITE_VENDOR_COMMAND_ENABLED = i2c_WRITE_VENDOR_COMMAND_ENABLED;
	}

	public String getFIRMWARE_VERSION_VENDOR_COMMAND_ENABLED() {
		return FIRMWARE_VERSION_VENDOR_COMMAND_ENABLED;
	}

	public void setFIRMWARE_VERSION_VENDOR_COMMAND_ENABLED(String fIRMWARE_VERSION_ENABLED) {
		FIRMWARE_VERSION_VENDOR_COMMAND_ENABLED = fIRMWARE_VERSION_ENABLED;
	}

}
