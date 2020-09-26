package sx3Configuration.util;

public class DeviceSettingsConstant {

	public static final String[] DEVICE_NAME = { "SX3 UVC", "SX3 Data (16-bit)", "SX3-Data (32-bit)" };

	public static final String[] ENDPOINTS_SX3_UVC = { "UVC", "UAC" };
	
	public static final String[] ENDPOINTS_NUMBER = { "1", "2" };

	public static final String[] ENDPOINTS_SX3_DATA = { "IN", "OUT" };

	public static final String[] FIFO_INTERFACE_TYPE = { "Slave FIFO Interface", "Camera Link Interface" };

	public static final String[] UVC_VERSION = { "1.1", "1.5" };

	public static final String[] UVC_HEADER_ADDITION = { "UVC HEADER BY FIFO MASTER", "UVC HEADER BY SX3" };

	public static final String NEW_COFIGURATION_TOOLTIP = "New Configuration";

	public static final String OPEN_COFIGURATION_TOOLTIP = "Open Configuration";

	public static final String SAVE_COFIGURATION_TOOLTIP = "Save Configuration";

	public static final String UNDO = "Undo";

	public static final String REDO = "Redo";

	public static final String PROGRAM_COFIGURATION_TOOLTIP = "Program Configuration";

	public static final String SAVE_LOG = "Save Log";

	public static final String CLEAR_LOG = "Clear Log";

	public static final String CONFIGURATION_FILE_PATH = "Configuration File Path";

	public static final String LOG_FILE_PATH = "C://Users//Lenovo//Documents//SX3//log.txt";

	public static final String UVC = "UVC";

	public static final String UAC = "UAC";

	public static final String[] FIFO_BUS_WIDTH_16BIT = { "8", "16" };

	public static final String[] GPIO_ONE_TO_TWO_IN_SX3DATA = { "Not Used","Streaming On LED", "Error LED", "Configuration Done" };

	public static final String[] GPIO_THREE_TO_SEVEN_IN_SX3DATA = { "Not Used", "Streaming On LED", "Error LED",
			"Configuration Done" };

	public static final String[] GPIO_ONE_TO_TWO_IN_SX3UVC = { "Not Used", "LED Brightness Control", "Streaming On LED",
			"Error LED", "Still Capture Button", "Focus Button", "Zoom IN Button", "Zoom OUT Button",
			"Flip Image Button", "Configuration Done" };

	public static final String[] GPIO_THREE_TO_SEVEN_IN_SX3UVC = { "Not Used", "Streaming On LED", "Error LED",
			"Still Capture Button", "Focus Button", "Zoom IN Button", "Zoom OUT Button", "Flip Image Button",
			"Configuration Done" };

}
