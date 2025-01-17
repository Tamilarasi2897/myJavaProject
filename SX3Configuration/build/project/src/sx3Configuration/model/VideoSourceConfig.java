package sx3Configuration.model;

import java.util.List;

public class VideoSourceConfig {
	private String VIDEO_SOURCE_TYPE;
	private String ENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD;
	private String VIDEO_SOURCE_CONFIG_FILE_PATH;
	private String I2C_SLAVE_ADDRESS;
	private int I2C_SLAVE_DATA_SIZE;
	private int I2C_SLAVE_REGISTER_SIZE;
	private int I2C_FREQUENCY;
//	private List<HDMISourceConfiguration> HDMI_SOURCE_CONFIGURATION;
	private String RESERVED;

	public String getVIDEO_SOURCE_TYPE() {
		return VIDEO_SOURCE_TYPE;
	}

	public void setVIDEO_SOURCE_TYPE(String vIDEO_SOURCE_TYPE) {
		VIDEO_SOURCE_TYPE = vIDEO_SOURCE_TYPE;
	}

	public String getENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD() {
		return ENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD;
	}

	public void setENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD(String eNABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD) {
		ENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD = eNABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD;
	}

	public String getVIDEO_SOURCE_CONFIG_FILE_PATH() {
		return VIDEO_SOURCE_CONFIG_FILE_PATH;
	}

	public void setVIDEO_SOURCE_CONFIG_FILE_PATH(String jsonFilePath) {
		this.VIDEO_SOURCE_CONFIG_FILE_PATH = jsonFilePath;
	}

	public String getI2C_SLAVE_ADDRESS() {
		return I2C_SLAVE_ADDRESS;
	}

	public void setI2C_SLAVE_ADDRESS(String i2c_SLAVE_ADDRESS) {
		I2C_SLAVE_ADDRESS = i2c_SLAVE_ADDRESS;
	}

	public int getI2C_SLAVE_DATA_SIZE() {
		return I2C_SLAVE_DATA_SIZE;
	}

	public void setI2C_SLAVE_DATA_SIZE(int i2c_SLAVE_DATA_SIZE) {
		I2C_SLAVE_DATA_SIZE = i2c_SLAVE_DATA_SIZE;
	}

	public int getI2C_SLAVE_REGISTER_SIZE() {
		return I2C_SLAVE_REGISTER_SIZE;
	}

	public void setI2C_SLAVE_REGISTER_SIZE(int i2c_SLAVE_REGISTER_SIZE) {
		I2C_SLAVE_REGISTER_SIZE = i2c_SLAVE_REGISTER_SIZE;
	}

	public int getI2C_FREQUENCY() {
		return I2C_FREQUENCY;
	}

	public void setI2C_FREQUENCY(int i2c_FREQUENCY) {
		I2C_FREQUENCY = i2c_FREQUENCY;
	}

//	public List<HDMISourceConfiguration> getHDMI_SOURCE_CONFIGURATION() {
//		return HDMI_SOURCE_CONFIGURATION;
//	}
//
//	public void setHDMI_SOURCE_CONFIGURATION(List<HDMISourceConfiguration> hDMI_SOURCE_CONFIGURATION) {
//		HDMI_SOURCE_CONFIGURATION = hDMI_SOURCE_CONFIGURATION;
//	}

	public String getRESERVED() {
		return RESERVED;
	}

	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}

}
