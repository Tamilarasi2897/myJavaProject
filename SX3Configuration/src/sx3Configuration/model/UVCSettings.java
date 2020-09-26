package sx3Configuration.model;

public class UVCSettings {
	private EndpointSettings ENDPOINT_SETTINGS;
	private FormatAndResolution FORMAT_RESOLUTION;
	private ColorMatching COLOR_MATCHING;
	private CameraControl CAMERA_CONTROL;
	private ProcessingUnitControl PROCESSING_UNIT_CONTROL;
	private ExtensionUnitControl EXTENSION_UNIT_CONTROL;
	private String RESERVED;
	
	public EndpointSettings getENDPOINT_SETTINGS() {
		return ENDPOINT_SETTINGS;
	}
	public void setENDPOINT_SETTINGS(EndpointSettings eNDPOINT_SETTINGS) {
		ENDPOINT_SETTINGS = eNDPOINT_SETTINGS;
	}
	public FormatAndResolution getFORMAT_RESOLUTION() {
		return FORMAT_RESOLUTION;
	}
	public void setFORMAT_RESOLUTION(FormatAndResolution fORMAT_RESOLUTION) {
		FORMAT_RESOLUTION = fORMAT_RESOLUTION;
	}
	public ColorMatching getCOLOR_MATCHING() {
		return COLOR_MATCHING;
	}
	public void setCOLOR_MATCHING(ColorMatching cOLOR_MATCHING) {
		COLOR_MATCHING = cOLOR_MATCHING;
	}
	
	public CameraControl getCAMERA_CONTROL() {
		return CAMERA_CONTROL;
	}
	public void setCAMERA_CONTROL(CameraControl cAMERA_CONTROL) {
		CAMERA_CONTROL = cAMERA_CONTROL;
	}
	public ProcessingUnitControl getPROCESSING_UNIT_CONTROL() {
		return PROCESSING_UNIT_CONTROL;
	}
	public void setPROCESSING_UNIT_CONTROL(ProcessingUnitControl pROCESSING_UNIT_CONTROL) {
		PROCESSING_UNIT_CONTROL = pROCESSING_UNIT_CONTROL;
	}
	public ExtensionUnitControl getEXTENSION_UNIT_CONTROL() {
		return EXTENSION_UNIT_CONTROL;
	}
	public void setEXTENSION_UNIT_CONTROL(ExtensionUnitControl eXTENSION_UNIT_CONTROL) {
		EXTENSION_UNIT_CONTROL = eXTENSION_UNIT_CONTROL;
	}
	public String getRESERVED() {
		return RESERVED;
	}
	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}
	
	
}
