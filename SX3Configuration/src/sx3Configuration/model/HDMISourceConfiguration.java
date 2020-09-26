package sx3Configuration.model;

import java.util.Comparator;
import java.util.List;

public class HDMISourceConfiguration {
	
	private int S_NO;
	private String EVENT_NAME;
	private String HDMI_SOURCE_REGISTER_ADDRESS;
	private String MASK_PATTERN;
	private String COMPARE_VALUE;
	private int HDMI_SOURCE_CONFIG_NUM_ROWS;
	private List<SensorConfig> RESOLUTION_REGISTER_SETTING;
	
	public int getS_NO() {
		return S_NO;
	}
	public void setS_NO(int s_NO) {
		S_NO = s_NO;
	}
	
	public String getEVENT_NAME() {
		return EVENT_NAME;
	}
	public void setEVENT_NAME(String eVENT_NAME) {
		EVENT_NAME = eVENT_NAME;
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
	public List<SensorConfig> getRESOLUTION_REGISTER_SETTING() {
		return RESOLUTION_REGISTER_SETTING;
	}
	public void setRESOLUTION_REGISTER_SETTING(List<SensorConfig> rESOLUTION_REGISTER_SETTING) {
		RESOLUTION_REGISTER_SETTING = rESOLUTION_REGISTER_SETTING;
	}
	public int getHDMI_SOURCE_CONFIG_NUM_ROWS() {
		return HDMI_SOURCE_CONFIG_NUM_ROWS;
	}
	public void setHDMI_SOURCE_CONFIG_NUM_ROWS(int hDMI_SOURCE_CONFIG_NUM_ROWS) {
		HDMI_SOURCE_CONFIG_NUM_ROWS = hDMI_SOURCE_CONFIG_NUM_ROWS;
	}
	
	public static Comparator<HDMISourceConfiguration> serialNoComparator = new Comparator<HDMISourceConfiguration>() {

        @Override
        public int compare(HDMISourceConfiguration o1, HDMISourceConfiguration o2) {
            return o1.getS_NO() - o2.getS_NO();
        }
    };
    
    public static Comparator<HDMISourceConfiguration> registerAddressComparator = new Comparator<HDMISourceConfiguration>() {

        @Override
        public int compare(HDMISourceConfiguration o1, HDMISourceConfiguration o2) {
            return o1.getHDMI_SOURCE_REGISTER_ADDRESS().compareTo(o2.getHDMI_SOURCE_REGISTER_ADDRESS());
        }
    };
}
