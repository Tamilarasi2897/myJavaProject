package sx3Configuration.model;

import java.util.LinkedList;
import java.util.List;

public class VideoSourceConfig {
	private int ENDPOINT;
	private String VIDEO_SOURCE_TYPE;
	private String VIDEO_SOURCE_SUBTYPE;
	private String ENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD;
	private long VIDEO_SOURCE_CONFIGURATION_LEN;
	private List<SensorConfig> VIDEO_SOURCE_CONFIGURATION;
	private String I2C_SLAVE_ADDRESS;
	private int I2C_SLAVE_DATA_SIZE;
	private int I2C_SLAVE_REGISTER_SIZE;
	private int HDMI_CONFIG_NUM_ROWS;
	private LinkedList<HDMISourceConfiguration> HDMI_SOURCE_CONFIGURATION;
	private String RESERVED;

	public int getENDPOINT() {
		return ENDPOINT;
	}

	public void setENDPOINT(int eNDPOINT) {
		ENDPOINT = eNDPOINT;
	}

	public String getVIDEO_SOURCE_TYPE() {
		return VIDEO_SOURCE_TYPE;
	}

	public void setVIDEO_SOURCE_TYPE(String vIDEO_SOURCE_TYPE) {
		VIDEO_SOURCE_TYPE = vIDEO_SOURCE_TYPE;
	}

	public String getVIDEO_SOURCE_SUBTYPE() {
		return VIDEO_SOURCE_SUBTYPE;
	}

	public void setVIDEO_SOURCE_SUBTYPE(String vIDEO_SOURCE_SUBTYPE) {
		VIDEO_SOURCE_SUBTYPE = vIDEO_SOURCE_SUBTYPE;
	}

	public long getVIDEO_SOURCE_CONFIGURATION_LEN() {
		return VIDEO_SOURCE_CONFIGURATION_LEN;
	}

	public void setVIDEO_SOURCE_CONFIGURATION_LEN(long vIDEO_SOURCE_CONFIGURATION_LEN) {
		VIDEO_SOURCE_CONFIGURATION_LEN = vIDEO_SOURCE_CONFIGURATION_LEN;
	}

	public String getENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD() {
		return ENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD;
	}

	public void setENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD(String eNABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD) {
		ENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD = eNABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD;
	}

	public List<SensorConfig> getVIDEO_SOURCE_CONFIG() {
		return VIDEO_SOURCE_CONFIGURATION;
	}

	public void setVIDEO_SOURCE_CONFIG(List<SensorConfig> vIDEO_SOURCE_CONFIG) {
		VIDEO_SOURCE_CONFIGURATION = vIDEO_SOURCE_CONFIG;
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

	public LinkedList<HDMISourceConfiguration> getHDMI_SOURCE_CONFIGURATION() {
		return HDMI_SOURCE_CONFIGURATION;
	}

	public void setHDMI_SOURCE_CONFIGURATION(LinkedList<HDMISourceConfiguration> hDMI_SOURCE_CONFIGURATION) {
		HDMI_SOURCE_CONFIGURATION = hDMI_SOURCE_CONFIGURATION;
	}

	public long getVIDEO_SOURCE_CONFIGURATION_SIZE() {
		return VIDEO_SOURCE_CONFIGURATION_LEN;
	}

	public void setVIDEO_SOURCE_CONFIGURATION_SIZE(long vIDEO_SOURCE_CONFIGURATION_SIZE) {
		VIDEO_SOURCE_CONFIGURATION_LEN = vIDEO_SOURCE_CONFIGURATION_SIZE;
	}

	public List<SensorConfig> getVIDEO_SOURCE_CONFIGURATION() {
		return VIDEO_SOURCE_CONFIGURATION;
	}

	public void setVIDEO_SOURCE_CONFIGURATION(List<SensorConfig> vIDEO_SOURCE_CONFIGURATION) {
		VIDEO_SOURCE_CONFIGURATION = vIDEO_SOURCE_CONFIGURATION;
	}

	public String getRESERVED() {
		return RESERVED;
	}

	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}

	public int getHDMI_CONFIG_NUM_ROWS() {
		return HDMI_CONFIG_NUM_ROWS;
	}

	public void setHDMI_CONFIG_NUM_ROWS(int hDMI_CONFIG_NUM_ROWS) {
		HDMI_CONFIG_NUM_ROWS = hDMI_CONFIG_NUM_ROWS;
	}
	
}
