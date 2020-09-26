package sx3Configuration.model;

import java.util.List;

public class HDMISourceConfiguration {
	
	private int S_NO;
	private String HDMI_SOURCE_REGISTER_ADDRESS;
	private String MASK_PATTERN;
	private String COMPARE_VALUE;
	private int RESOLUTION_REGISTER_SETTING_LEN;
	private List<SensorConfig> RESOLUTION_REGISTER_SETTING;
	private String RESERVED;
	
	public int getS_NO() {
		return S_NO;
	}
	public void setS_NO(int s_NO) {
		S_NO = s_NO;
	}
	public String getHDMI_SOURCE_REGISTER_ADDRESS() {
		return HDMI_SOURCE_REGISTER_ADDRESS;
	}
	public void setHDMI_SOURCE_REGISTER_ADDRESS(String hDMI_SOURCE_REGISTER_ADDRESS) {
		HDMI_SOURCE_REGISTER_ADDRESS = hDMI_SOURCE_REGISTER_ADDRESS;
	}
	public String getMASK_PATTERN() {
		return MASK_PATTERN;
	}
	public void setMASK_PATTERN(String mASK_PATTERN) {
		MASK_PATTERN = mASK_PATTERN;
	}
	public String getCOMPARE_VALUE() {
		return COMPARE_VALUE;
	}
	public void setCOMPARE_VALUE(String cOMPARE_VALUE) {
		COMPARE_VALUE = cOMPARE_VALUE;
	}
	public int getRESOLUTION_REGISTER_SETTING_LEN() {
		return RESOLUTION_REGISTER_SETTING_LEN;
	}
	public void setRESOLUTION_REGISTER_SETTING_LEN(int rESOLUTION_REGISTER_SETTING_LEN) {
		RESOLUTION_REGISTER_SETTING_LEN = rESOLUTION_REGISTER_SETTING_LEN;
	}
	public List<SensorConfig> getRESOLUTION_REGISTER_SETTING() {
		return RESOLUTION_REGISTER_SETTING;
	}
	public void setRESOLUTION_REGISTER_SETTING(List<SensorConfig> rESOLUTION_REGISTER_SETTING) {
		RESOLUTION_REGISTER_SETTING = rESOLUTION_REGISTER_SETTING;
	}
	public String getRESERVED() {
		return RESERVED;
	}
	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}
	
}
