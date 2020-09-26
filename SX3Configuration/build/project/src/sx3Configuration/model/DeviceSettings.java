package sx3Configuration.model;

public class DeviceSettings {

	private USBSettings USB_SETTINGS;
	private DebugLevel DEBUG_LEVEL;
	private GPIOs GPIOS_SETTINGS;
	private String RESERVED;

	public USBSettings getUSB_SETTINGS() {
		return USB_SETTINGS;
	}

	public void setUSB_SETTINGS(USBSettings uSB_SETTINGS) {
		USB_SETTINGS = uSB_SETTINGS;
	}

	public GPIOs getGPIOS_SETTINGS() {
		return GPIOS_SETTINGS;
	}

	public void setGPIOS_SETTINGS(GPIOs gPIOS_SETTINGS) {
		GPIOS_SETTINGS = gPIOS_SETTINGS;
	}

	public DebugLevel getDEBUG_LEVEL() {
		return DEBUG_LEVEL;
	}

	public void setDEBUG_LEVEL(DebugLevel dEBUG_LEVEL) {
		DEBUG_LEVEL = dEBUG_LEVEL;
	}

	public String getRESERVED() {
		return RESERVED;
	}

	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}

}
