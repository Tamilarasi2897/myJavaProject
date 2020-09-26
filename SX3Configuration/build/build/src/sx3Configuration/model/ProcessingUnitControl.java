package sx3Configuration.model;

import java.util.List;
import java.util.Map;

public class ProcessingUnitControl {
	
	private String PROCESSING_UNIT_ENABLED_CONTROLS_BITMASK;
	private List<Map<String,Map<String,Object>>> PROCESSING_UNIT_CONTROLS;
	
	public String getPROCESSING_UNIT_ENABLED_CONTROLS_BITMASK() {
		return PROCESSING_UNIT_ENABLED_CONTROLS_BITMASK;
	}
	public void setPROCESSING_UNIT_ENABLED_CONTROLS_BITMASK(String pROCESSING_UNIT_ENABLED_CONTROLS_BITMASK) {
		PROCESSING_UNIT_ENABLED_CONTROLS_BITMASK = pROCESSING_UNIT_ENABLED_CONTROLS_BITMASK;
	}
	public List<Map<String, Map<String, Object>>> getPROCESSING_UNIT_CONTROLS() {
		return PROCESSING_UNIT_CONTROLS;
	}
	public void setPROCESSING_UNIT_CONTROLS(List<Map<String, Map<String, Object>>> pROCESSING_UNIT_CONTROLS) {
		PROCESSING_UNIT_CONTROLS = pROCESSING_UNIT_CONTROLS;
	}

}