package sx3Configuration.util;

public class SX3ConfiguartionHelp {
	private String DEVICE_NAME;
	private DEVICE_SETTINGS_HELP DEVICE_SETTINGS;
	private UVC_SETTING_HELP UVC_SETTINGS;
	private UAC_SETTING_HELP UAC_SETTINGS;
	private FIFO_MASTER_CONFIG_HELP FIFO_MASTER_CONFIG;
	private VIDEO_SOURCE_CONFIG_HELP VIDEO_SOURCE_CONFIG;
	private ENDPOINT_SETTINGS_HELP ENDPOINT_SETTINGS;
	
	private SLAVE_FIFO_SETTINGS_HELP SLAVE_FIFO_SETTINGS;
	
	public ENDPOINT_SETTINGS_HELP getENDPOINT_SETTINGS() {
		return ENDPOINT_SETTINGS;
	}
	public void setENDPOINT_SETTINGS(ENDPOINT_SETTINGS_HELP eNDPOINT_SETTINGS) {
		ENDPOINT_SETTINGS = eNDPOINT_SETTINGS;
	}
	public String getDEVICE_NAME() {
		return DEVICE_NAME;
	}
	public void setDEVICE_NAME(String dEVICE_NAME) {
		DEVICE_NAME = dEVICE_NAME;
	}
	public DEVICE_SETTINGS_HELP getDEVICE_SETTINGS() {
		return DEVICE_SETTINGS;
	}
	public void setDEVICE_SETTINGS(DEVICE_SETTINGS_HELP dEVICE_SETTINGS) {
		DEVICE_SETTINGS = dEVICE_SETTINGS;
	}
	public UVC_SETTING_HELP getUVC_SETTINGS() {
		return UVC_SETTINGS;
	}
	public void setUVC_SETTINGS(UVC_SETTING_HELP uVC_SETTINGS) {
		UVC_SETTINGS = uVC_SETTINGS;
	}
	public UAC_SETTING_HELP getUAC_SETTINGS() {
		return UAC_SETTINGS;
	}
	public void setUAC_SETTINGS(UAC_SETTING_HELP uAC_SETTINGS) {
		UAC_SETTINGS = uAC_SETTINGS;
	}
	public FIFO_MASTER_CONFIG_HELP getFIFO_MASTER_CONFIG() {
		return FIFO_MASTER_CONFIG;
	}
	public void setFIFO_MASTER_CONFIG(FIFO_MASTER_CONFIG_HELP fIFO_MASTER_CONFIG) {
		FIFO_MASTER_CONFIG = fIFO_MASTER_CONFIG;
	}
	public VIDEO_SOURCE_CONFIG_HELP getVIDEO_SOURCE_CONFIG() {
		return VIDEO_SOURCE_CONFIG;
	}
	public void setVIDEO_SOURCE_CONFIG(VIDEO_SOURCE_CONFIG_HELP vIDEO_SOURCE_CONFIG) {
		VIDEO_SOURCE_CONFIG = vIDEO_SOURCE_CONFIG;
	}
	public SLAVE_FIFO_SETTINGS_HELP getSLAVE_FIFO_SETTINGS() {
		return SLAVE_FIFO_SETTINGS;
	}
	public void setSLAVE_FIFO_SETTINGS(SLAVE_FIFO_SETTINGS_HELP sLAVE_FIFO_SETTINGS) {
		SLAVE_FIFO_SETTINGS = sLAVE_FIFO_SETTINGS;
	}
	
}
