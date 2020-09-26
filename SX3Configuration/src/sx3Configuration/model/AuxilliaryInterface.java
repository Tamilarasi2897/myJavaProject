package sx3Configuration.model;

public class AuxilliaryInterface {
	private String FIRMWARE_UPDATE_HID;
	private Long I2C_FREQUENCY;
	public String getFIRMWARE_UPDATE_HID() {
		return FIRMWARE_UPDATE_HID;
	}
	public void setFIRMWARE_UPDATE_HID(String fIRMWARE_UPDATE_HID) {
		FIRMWARE_UPDATE_HID = fIRMWARE_UPDATE_HID;
	}
	public Long getI2C_FREQUENCY() {
		return I2C_FREQUENCY;
	}
	public void setI2C_FREQUENCY(Long i2c_FREQUENCY) {
		I2C_FREQUENCY = i2c_FREQUENCY;
	}
	
	
	

}
