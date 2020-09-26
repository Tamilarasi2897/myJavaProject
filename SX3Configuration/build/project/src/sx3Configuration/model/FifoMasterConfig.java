package sx3Configuration.model;

import java.util.List;

public class FifoMasterConfig {

	private String FIFO_MASTER_CONFIGURATION_DOWNLOAD_EN;
	private String BIT_FILE_PATH;
	private String FPGA_FAMILY;
	private String I2C_SLAVE_ADDRESS;
	private int I2C_FREQUENCY;
	private int BIT_FILE_SIZE;
	private String RESERVED;

	public String getFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN() {
		return FIFO_MASTER_CONFIGURATION_DOWNLOAD_EN;
	}

	public void setFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN(String fIFO_MASTER_CONFIGURATION_DOWNLOAD_EN) {
		FIFO_MASTER_CONFIGURATION_DOWNLOAD_EN = fIFO_MASTER_CONFIGURATION_DOWNLOAD_EN;
	}

	public String getBIT_FILE_PATH() {
		return BIT_FILE_PATH;
	}

	public void setBIT_FILE_PATH(String bIT_FILE_PATH) {
		BIT_FILE_PATH = bIT_FILE_PATH;
	}

	public String getFPGA_FAMILY() {
		return FPGA_FAMILY;
	}

	public void setFPGA_FAMILY(String fPGA_FAMILY) {
		FPGA_FAMILY = fPGA_FAMILY;
	}

	public String getI2C_SLAVE_ADDRESS() {
		return I2C_SLAVE_ADDRESS;
	}

	public void setI2C_SLAVE_ADDRESS(String i2c_SLAVE_ADDRESS) {
		I2C_SLAVE_ADDRESS = i2c_SLAVE_ADDRESS;
	}

	public int getI2C_FREQUENCY() {
		return I2C_FREQUENCY;
	}

	public void setI2C_FREQUENCY(int i2c_FREQUENCY) {
		I2C_FREQUENCY = i2c_FREQUENCY;
	}

	public int getBIT_FILE_SIZE() {
		return BIT_FILE_SIZE;
	}

	public void setBIT_FILE_SIZE(int bIT_FILE_SIZE) {
		BIT_FILE_SIZE = bIT_FILE_SIZE;
	}
	
	public String getRESERVED() {
		return RESERVED;
	}

	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}

}
