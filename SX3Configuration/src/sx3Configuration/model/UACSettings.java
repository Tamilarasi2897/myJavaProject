package sx3Configuration.model;

public class UACSettings {

	private EndpointSettings ENDPOINT_SETTINGS;
	private UACSetting UAC_SETTING;
	private String RESERVED;

	public EndpointSettings getEndpointSettings() {
		return ENDPOINT_SETTINGS;
	}

	public void setEndpointSettings(EndpointSettings endpointSettings) {
		this.ENDPOINT_SETTINGS = endpointSettings;
	}


	public UACSetting getUAC_SETTING() {
		return UAC_SETTING;
	}

	public void setUAC_SETTING(UACSetting uAC_SETTING) {
		UAC_SETTING = uAC_SETTING;
	}
	
	public String getRESERVED() {
		return RESERVED;
	}

	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}


}
