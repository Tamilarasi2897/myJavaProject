package sx3Configuration.model;

import java.util.LinkedHashMap;
import java.util.List;

public class CameraControl {

	private String CAMERA_CONTROLS_ENABLED_BITMASK;
	private List<LinkedHashMap<String,LinkedHashMap<String, Object>>> CAMERA_CONTROLS;

	public String getCAMERA_CONTROLS_ENABLED_BITMASK() {
		return CAMERA_CONTROLS_ENABLED_BITMASK;
	}

	public void setCAMERA_CONTROLS_ENABLED_BITMASK(String cAMERA_CONTROLS_ENABLED_BITMASK) {
		CAMERA_CONTROLS_ENABLED_BITMASK = cAMERA_CONTROLS_ENABLED_BITMASK;
	}

	public List<LinkedHashMap<String, LinkedHashMap<String, Object>>> getCAMERA_CONTROLS() {
		return CAMERA_CONTROLS;
	}

	public void setCAMERA_CONTROLS(List<LinkedHashMap<String, LinkedHashMap<String, Object>>> cAMERA_CONTROLS) {
		CAMERA_CONTROLS = cAMERA_CONTROLS;
	}

}
