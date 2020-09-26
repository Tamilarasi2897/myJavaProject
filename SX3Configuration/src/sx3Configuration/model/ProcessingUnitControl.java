package sx3Configuration.model;

import java.util.LinkedHashMap;
import java.util.List;

public class ProcessingUnitControl {
	
	private String PROCESSING_UNIT_ENABLED_CONTROLS_BITMASK;
	private List<LinkedHashMap<String,LinkedHashMap<String,Object>>> PROCESSING_UNIT_CONTROLS;
	
	public String getPROCESSING_UNIT_ENABLED_CONTROLS_BITMASK() {
		return PROCESSING_UNIT_ENABLED_CONTROLS_BITMASK;
	}
	public void setPROCESSING_UNIT_ENABLED_CONTROLS_BITMASK(String pROCESSING_UNIT_ENABLED_CONTROLS_BITMASK) {
		PROCESSING_UNIT_ENABLED_CONTROLS_BITMASK = pROCESSING_UNIT_ENABLED_CONTROLS_BITMASK;
	}
	public List<LinkedHashMap<String, LinkedHashMap<String, Object>>> getPROCESSING_UNIT_CONTROLS() {
		return PROCESSING_UNIT_CONTROLS;
	}
	public void setPROCESSING_UNIT_CONTROLS(List<LinkedHashMap<String, LinkedHashMap<String, Object>>> pROCESSING_UNIT_CONTROLS) {
		PROCESSING_UNIT_CONTROLS = pROCESSING_UNIT_CONTROLS;
	}

}
