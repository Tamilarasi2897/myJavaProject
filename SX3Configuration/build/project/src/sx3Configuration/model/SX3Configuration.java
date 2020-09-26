package sx3Configuration.model;

import java.util.List;

public class SX3Configuration {
	
	private String TOOL;
	private String TOOL_VERSION;
	private ConfigTableGeneral CONFIG_TABLE_GENERAL;
	private ConfigTableOffSetTable CONFIG_TABLE_OFFSET_TABLE;
	private String DEVICE_NAME;
	private DeviceSettings DEVICE_SETTINGS;
	private FifoMasterConfig FIFO_MASTER_CONFIG;
	private List<VideoSourceConfig> VIDEO_SOURCE_CONFIG;
	private List<SlaveFIFOSettings> SLAVE_FIFO_SETTINGS;
	private List<UACSettings> UAC_SETTINGS;
	private List<UVCSettings> UVC_SETTINGS;
	
	public SX3Configuration() {
	
	}
	
	public String getTOOL() {
		return TOOL;
	}


	public void setTOOL(String tOOL) {
		TOOL = tOOL;
	}


	public String getTOOL_VERSION() {
		return TOOL_VERSION;
	}


	public void setTOOL_VERSION(String tOOL_VERSION) {
		TOOL_VERSION = tOOL_VERSION;
	}


	public ConfigTableGeneral getCONFIG_TABLE_GENERAL() {
		return CONFIG_TABLE_GENERAL;
	}

	public void setCONFIG_TABLE_GENERAL(ConfigTableGeneral cONFIG_TABLE_GENERAL) {
		CONFIG_TABLE_GENERAL = cONFIG_TABLE_GENERAL;
	}

	public ConfigTableOffSetTable getCONFIG_TABLE_OFFSET_TABLE() {
		return CONFIG_TABLE_OFFSET_TABLE;
	}

	public void setCONFIG_TABLE_OFFSET_TABLE(ConfigTableOffSetTable cONFIG_TABLE_OFFSET_TABLE) {
		CONFIG_TABLE_OFFSET_TABLE = cONFIG_TABLE_OFFSET_TABLE;
	}
	
	public String getDEVICE_NAME() {
		return DEVICE_NAME;
	}

	public void setDEVICE_NAME(String dEVICE_NAME) {
		DEVICE_NAME = dEVICE_NAME;
	}

	public DeviceSettings getDEVICE_SETTINGS() {
		return DEVICE_SETTINGS;
	}

	public void setDEVICE_SETTINGS(DeviceSettings dEVICE_SETTINGS) {
		DEVICE_SETTINGS = dEVICE_SETTINGS;
	}

	public List<UVCSettings> getUVC_SETTINGS() {
		return UVC_SETTINGS;
	}

	public void setUVC_SETTINGS(List<UVCSettings> uVC_SETTINGS) {
		UVC_SETTINGS = uVC_SETTINGS;
	}

	public List<UACSettings> getUAC_SETTINGS() {
		return UAC_SETTINGS;
	}

	public void setUAC_SETTINGS(List<UACSettings> uAC_SETTINGS) {
		UAC_SETTINGS = uAC_SETTINGS;
	}

	public FifoMasterConfig getFIFO_MASTER_CONFIG() {
		return FIFO_MASTER_CONFIG;
	}

	public void setFIFO_MASTER_CONFIG(FifoMasterConfig fIFO_MASTER_CONFIG) {
		FIFO_MASTER_CONFIG = fIFO_MASTER_CONFIG;
	}

	public List<VideoSourceConfig> getVIDEO_SOURCE_CONFIG() {
		return VIDEO_SOURCE_CONFIG;
	}

	public void setVIDEO_SOURCE_CONFIG(List<VideoSourceConfig> vIDEO_SOURCE_CONFIG) {
		VIDEO_SOURCE_CONFIG = vIDEO_SOURCE_CONFIG;
	}

	public List<SlaveFIFOSettings> getSLAVE_FIFO_SETTINGS() {
		return SLAVE_FIFO_SETTINGS;
	}

	public void setSLAVE_FIFO_SETTINGS(List<SlaveFIFOSettings> sLAVE_FIFO_SETTINGS) {
		SLAVE_FIFO_SETTINGS = sLAVE_FIFO_SETTINGS;
	}

}
