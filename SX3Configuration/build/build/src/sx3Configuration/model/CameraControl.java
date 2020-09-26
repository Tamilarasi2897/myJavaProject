package sx3Configuration.model;

import java.util.List;
import java.util.Map;

public class CameraControl {

	private String CAMERA_CONTROLS_ENABLED_BITMASK;
	private List<Map<String,Map<String, Object>>> CAMERA_CONTROLS;

	public String getCAMERA_CONTROLS_ENABLED_BITMASK() {
		return CAMERA_CONTROLS_ENABLED_BITMASK;
	}

	public void setCAMERA_CONTROLS_ENABLED_BITMASK(String cAMERA_CONTROLS_ENABLED_BITMASK) {
		CAMERA_CONTROLS_ENABLED_BITMASK = cAMERA_CONTROLS_ENABLED_BITMASK;
	}

	public List<Map<String, Map<String, Object>>> getCAMERA_CONTROLS() {
		return CAMERA_CONTROLS;
	}

	public void setCAMERA_CONTROLS(List<Map<String, Map<String, Object>>> cAMERA_CONTROLS) {
		CAMERA_CONTROLS = cAMERA_CONTROLS;
	}

}
