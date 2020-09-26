package sx3Configuration.model;

import java.util.Comparator;
import java.util.List;

public class FormatAndResolutions {

	private int S_NO;
	private String IMAGE_FORMAT;
	private int BITS_PER_PIXEL;
	private int H_RESOLUTION;
	private int V_RESOLUTION;
	private String STILL_CAPTURE;
	private String SUPPORTED_IN_FS;
	private String SUPPORTED_IN_HS;
	private String SUPPORTED_IN_SS;
	private int FRAME_RATE_IN_FS;
	private int FRAME_RATE_IN_HS;
	private int FRAME_RATE_IN_SS;
	private int RESOLUTION_REGISTER_SETTING_LEN;
	private List<SensorConfig> RESOLUTION_REGISTER_SETTING;
	private String RESERVED;
	public int getS_NO() {
		return S_NO;
	}
	public void setS_NO(int s_NO) {
		S_NO = s_NO;
	}
	public String getIMAGE_FORMAT() {
		return IMAGE_FORMAT;
	}
	public void setIMAGE_FORMAT(String iMAGE_FORMAT) {
		IMAGE_FORMAT = iMAGE_FORMAT;
	}
	public int getBITS_PER_PIXEL() {
		return BITS_PER_PIXEL;
	}
	public void setBITS_PER_PIXEL(int bITS_PER_PIXEL) {
		BITS_PER_PIXEL = bITS_PER_PIXEL;
	}
	public int getH_RESOLUTION() {
		return H_RESOLUTION;
	}
	public void setH_RESOLUTION(int h_RESOLUTION) {
		H_RESOLUTION = h_RESOLUTION;
	}
	public int getV_RESOLUTION() {
		return V_RESOLUTION;
	}
	public void setV_RESOLUTION(int v_RESOLUTION) {
		V_RESOLUTION = v_RESOLUTION;
	}
	public String getSTILL_CAPTURE() {
		return STILL_CAPTURE;
	}
	public void setSTILL_CAPTURE(String sTILL_CAPTURE) {
		STILL_CAPTURE = sTILL_CAPTURE;
	}
	public String getSUPPORTED_IN_FS() {
		return SUPPORTED_IN_FS;
	}
	public void setSUPPORTED_IN_FS(String sUPPORTED_IN_FS) {
		SUPPORTED_IN_FS = sUPPORTED_IN_FS;
	}
	public String getSUPPORTED_IN_HS() {
		return SUPPORTED_IN_HS;
	}
	public void setSUPPORTED_IN_HS(String sUPPORTED_IN_HS) {
		SUPPORTED_IN_HS = sUPPORTED_IN_HS;
	}
	public String getSUPPORTED_IN_SS() {
		return SUPPORTED_IN_SS;
	}
	public void setSUPPORTED_IN_SS(String sUPPORTED_IN_SS) {
		SUPPORTED_IN_SS = sUPPORTED_IN_SS;
	}
	public int getFRAME_RATE_IN_FS() {
		return FRAME_RATE_IN_FS;
	}
	public void setFRAME_RATE_IN_FS(int fRAME_RATE_IN_FS) {
		FRAME_RATE_IN_FS = fRAME_RATE_IN_FS;
	}
	public int getFRAME_RATE_IN_HS() {
		return FRAME_RATE_IN_HS;
	}
	public void setFRAME_RATE_IN_HS(int fRAME_RATE_IN_HS) {
		FRAME_RATE_IN_HS = fRAME_RATE_IN_HS;
	}
	public int getFRAME_RATE_IN_SS() {
		return FRAME_RATE_IN_SS;
	}
	public void setFRAME_RATE_IN_SS(int fRAME_RATE_IN_SS) {
		FRAME_RATE_IN_SS = fRAME_RATE_IN_SS;
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

	public static Comparator<FormatAndResolutions> serialNoComparator = new Comparator<FormatAndResolutions>() {

        @Override
        public int compare(FormatAndResolutions o1, FormatAndResolutions o2) {
            return o1.getS_NO() - o2.getS_NO();
        }
    };
}
