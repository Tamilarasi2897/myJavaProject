package sx3Configuration.mergertool;

import java.util.HashMap;
import java.util.Map;

public class SX3ParametersByteSizeBuilder {

	private Map<String, Integer> parmByteSizeMap = new HashMap<>();
	private Map<String, int[]> byteSizeMap = new HashMap<>();

	public SX3ParametersByteSizeBuilder() {
		byteSizeMap.put(SX3PropertiesConstants.CONFIG_TABLE_GENERAL,
				SX3PropertiesConstants.CONFIG_TABLE_GENERAL_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.CONFIG_TABLE_OFFSET_TABLE,
				SX3PropertiesConstants.CONFIG_TABLE_OFFSET_TABLE_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.DEVICE_SETTINGS_USB_SETTINGS,
				SX3PropertiesConstants.DEVICE_SETTINGS_USB_SETTINGS_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.DEVICE_SETTINGS_DEBUG_LEVEL,
				SX3PropertiesConstants.DEVICE_SETTINGS_DEBUG_LEVEL_BYTE_SIZES);
		byteSizeMap.put(SX3PropertiesConstants.DEVICE_SETTINGS_GPIOS_SETTINGS,
				SX3PropertiesConstants.DEVICE_SETTINGS_GPIOS_SETTINGS_BYTE_SIZES);
		byteSizeMap.put(SX3PropertiesConstants.DEVICE_SETTINGS_AUXILLIARY_INTERFACE,
				SX3PropertiesConstants.DEVICE_SETTINGS_AUXILLIARY_INTERFACE_BYTE_SIZES);
		byteSizeMap.put(
				SX3PropertiesConstants.DEVICE_SETTINGS + SX3PropertiesConstants.UNDER_SCORE
						+ SX3PropertiesConstants.RESERVED_KEY,
				SX3PropertiesConstants.DEVICE_SETTINGS_RESERVED_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.FIFO_MASTER_CONFIG,
				SX3PropertiesConstants.FIFO_MASTER_CONFIG_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.VIDEO_SOURCE_CONFIG,
				SX3PropertiesConstants.VIDEO_SOURCE_CONFIG_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.VIDEO_TYPE_HDMI_SOURCE_CONFIGURATION,
				SX3PropertiesConstants.HDMI_SOURCE_CONFIGURATION_BYTE_SIZES);

		byteSizeMap.put(
				SX3PropertiesConstants.VIDEO_SOURCE_CONFIG + SX3PropertiesConstants.UNDER_SCORE
						+ SX3PropertiesConstants.RESERVED_KEY,
				SX3PropertiesConstants.VIDEO_SOURCE_CONFIG_RESERVED_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.SLAVE_FIFO_SETTINGS,
				SX3PropertiesConstants.SLAVE_FIFO_SETTINGS_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.UAC_SETTINGS_ENDPOINT_SETTINGS,
				SX3PropertiesConstants.UAC_SETTINGS_ENDPOINT_SETTINGS_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.UAC_SETTINGS_UAC_SETTING,
				SX3PropertiesConstants.UAC_SETTINGS_UAC_SETTING_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.SAMPLING_FREQUENCY_1_SENSOR_CONFIG,
				SX3PropertiesConstants.SENSOR_CONIG_BYTE_SIZES);
		byteSizeMap.put(SX3PropertiesConstants.SAMPLING_FREQUENCY_2_SENSOR_CONFIG,
				SX3PropertiesConstants.SENSOR_CONIG_BYTE_SIZES);
		byteSizeMap.put(SX3PropertiesConstants.SAMPLING_FREQUENCY_3_SENSOR_CONFIG,
				SX3PropertiesConstants.SENSOR_CONIG_BYTE_SIZES);
		byteSizeMap.put(SX3PropertiesConstants.SAMPLING_FREQUENCY_4_SENSOR_CONFIG,
				SX3PropertiesConstants.SENSOR_CONIG_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.UAC_SETTINGS + SX3PropertiesConstants.UNDER_SCORE
				+ SX3PropertiesConstants.RESERVED_KEY, SX3PropertiesConstants.UAC_SETTINGS_RESERVED_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.UVC_SETTINGS_ENDPOINT_SETTINGS,
				SX3PropertiesConstants.UVC_SETTINGS_ENDPOINT_SETTINGS_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.UVC_SETTINGS_FORMAT_RESOLUTION,
				SX3PropertiesConstants.UVC_SETTINGS_FORMAT_RESOLUTION_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.UVC_SETTINGS_FORMAT_RESOLUTIONS,
				SX3PropertiesConstants.UVC_SETTINGS_FORMAT_RESOLUTIONS_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.SENSOR_CONFIG, SX3PropertiesConstants.SENSOR_CONIG_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.UVC_SETTINGS_COLOR_MATCHING,
				SX3PropertiesConstants.UVC_SETTINGS_COLOR_MATCHING_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.UVC_SETTINGS_CAMERA_CONTROL,
				SX3PropertiesConstants.UVC_SETTINGS_CAMERA_CONTROL_BYTE_SIZES);

		for (String mainField : SX3PropertiesConstants.UVC_SETTINGS_CAMERA_CONTROLS_FIELDS) {
			byteSizeMap.put(
					SX3PropertiesConstants.UVC_SETTINGS_CAMERA_CONTROLS + SX3PropertiesConstants.DOT + mainField,
					SX3PropertiesConstants.getMainFieldByteSizesForExtendedFields(mainField));
		}

		byteSizeMap.put(SX3PropertiesConstants.UVC_SETTINGS_PROCESSING_UNIT_CONTROL,
				SX3PropertiesConstants.UVC_SETTINGS_PROCESSING_UNIT_CONTROL_BYTE_SIZES);

		for (String mainField : SX3PropertiesConstants.UVC_SETTINGS_PROCESSING_UNIT_CONTROLS_FIELDS) {
			byteSizeMap.put(SX3PropertiesConstants.UVC_SETTINGS_PROCESSING_UNIT_CONTROLS + SX3PropertiesConstants.DOT
					+ mainField, SX3PropertiesConstants.getMainFieldByteSizesForExtendedFields(mainField));
		}

		byteSizeMap.put(SX3PropertiesConstants.UVC_SETTINGS_EXTENSION_UNIT_CONTROL,
				SX3PropertiesConstants.UVC_SETTINGS_EXTENSION_UNIT_CONTROL_BYTE_SIZES);

		byteSizeMap.put(SX3PropertiesConstants.UVC_SETTINGS + SX3PropertiesConstants.UNDER_SCORE
				+ SX3PropertiesConstants.RESERVED_KEY, SX3PropertiesConstants.UVC_SETTINGS_RESERVED_BYTE_SIZES);
	}

	protected Map<String, Integer> getParametersByteSizeMap(String[] parms, String key) {
		int[] fieldByteSizes = byteSizeMap.get(key);
		if (fieldByteSizes == null) {
			BytesStreamsAndHexFileUtil.log("Size of parameter (Bytes) is not added to the map for the key -> " + key);
		}
		if (parms != null && parms.length != fieldByteSizes.length)
			throw new IllegalArgumentException(
					"Input parameters string array length  is not matching with byte size integer array length ");
		int i = 0;
		for (String parmField : parms) {
			parmByteSizeMap.put(parmField, byteSizeMap.get(key)[i]);
			i++;
		}

		return parmByteSizeMap;
	}

}
