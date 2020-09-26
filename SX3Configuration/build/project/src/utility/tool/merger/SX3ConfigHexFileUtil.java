package utility.tool.merger;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;

import sx3Configuration.model.CameraControl;
import sx3Configuration.model.ColorMatching;
import sx3Configuration.model.ConfigTableGeneral;
import sx3Configuration.model.ConfigTableOffSetTable;
import sx3Configuration.model.DebugLevel;
import sx3Configuration.model.DeviceSettings;
import sx3Configuration.model.EndpointSettings;
import sx3Configuration.model.ExtensionUnitControl;
import sx3Configuration.model.FifoMasterConfig;
import sx3Configuration.model.FormatAndResolution;
import sx3Configuration.model.FormatAndResolutions;
import sx3Configuration.model.GPIOs;
import sx3Configuration.model.ProcessingUnitControl;
import sx3Configuration.model.SX3Configuration;
import sx3Configuration.model.SensorConfig;
import sx3Configuration.model.SlaveFIFOSettings;
import sx3Configuration.model.UACSetting;
import sx3Configuration.model.UACSettings;
import sx3Configuration.model.USBSettings;
import sx3Configuration.model.UVCSettings;
import sx3Configuration.model.VideoSourceConfig;
import sx3Configuration.ui.SX3ConfigPreference;
import sx3Configuration.util.SX3ConfigCommonUtil;
import sx3Configuration.util.UACSettingFieldConstants;

public class SX3ConfigHexFileUtil {

	private static final String OBJECT_BLOCK_NOT_FOUND_IN_JSON_FILE = " object block not found in Json file";

	/**
	 * Read the given Json file, and convert its contents as a HEX/ASCII data.
	 */
	public static void convertJsonToHexFile(File jsonFile) {
		jsonFile.exists();
		StringBuilder result = new StringBuilder();
		BytesStreamsAndHexFileUtil.log("Reading in SX3 Config Json file named : " + jsonFile.getName());
		// Gson parser object to parse json file
		Gson gson = new Gson();
		try {
			Properties hexMapProperties = BytesStreamsAndHexFileUtil.gethexValueMappingProperties();
			JsonReader reader = new JsonReader(new FileReader(jsonFile));
			// convert the json string back to object
			SX3Configuration sx3Obj = gson.fromJson(reader, SX3Configuration.class);

			/**
			 * CONFIGURATION TABLE SETTINGS => Checksum, Version Information,
			 * Size
			 **/
			// CONFIG_TABLE_GENERAL data conversion
			ConfigTableGeneral configTableGen = sx3Obj.getCONFIG_TABLE_GENERAL();
			if (configTableGen != null) {
				result.append(getHexStringValueFromJsonObject(configTableGen,
						SX3PropertiesConstants.CONFIG_TABLE_GENERAL_FIELDS, SX3PropertiesConstants.CONFIG_TABLE_GENERAL,
						hexMapProperties));
			} else {
				BytesStreamsAndHexFileUtil
						.log(SX3PropertiesConstants.CONFIG_TABLE_GENERAL + OBJECT_BLOCK_NOT_FOUND_IN_JSON_FILE);
			}

			/**
			 * SX3 OFFSET LOOKUP TABLE => Contains the offset value of the SX3
			 * Device Settings, UVC, UAC, FIFO_Master, Video_Source and SX3_Data
			 * Config tables
			 **/
			int configOffsetHexStringStartIndex = result.length();
			// CONFIG_TABLE_OFFSET_TABLE data conversion
			ConfigTableOffSetTable configTableOffSetTable = sx3Obj.getCONFIG_TABLE_OFFSET_TABLE();
			result.append(getHexStringValueFromJsonObject(configTableOffSetTable,
					SX3PropertiesConstants.CONFIG_TABLE_OFFSET_TABLE_FIELDS,
					SX3PropertiesConstants.CONFIG_TABLE_OFFSET_TABLE, hexMapProperties));

			/**
			 * SX3 DEVICE SETTINGS => Parameters listed in the Device Settings
			 * tab of the Config utility
			 **/
			// DEVICE_SETTINGS data conversion
			DeviceSettings deviceSettings = sx3Obj.getDEVICE_SETTINGS();
			// Set DEVICE_SETTINGS OffSet
			String deviceSettingsoffSet = getOffSet(deviceSettings, result.length() / 2);
			configTableOffSetTable.setSX3_DEVICE_SETTINGS_TABLE_OFFSET(deviceSettingsoffSet);

			// USB_Settings data Conversion
			USBSettings usbSettings = deviceSettings.getUSB_SETTINGS();
			result.append(getHexStringValueFromJsonObject(usbSettings,
					SX3PropertiesConstants.DEVICE_SETTINGS_USB_SETTINGS_FIELDS,
					SX3PropertiesConstants.DEVICE_SETTINGS_USB_SETTINGS, hexMapProperties));

			// DEBUG_LEVEL data Conversion
			DebugLevel debugLevel = deviceSettings.getDEBUG_LEVEL();
			result.append(getHexStringValueFromJsonObject(debugLevel,
					SX3PropertiesConstants.DEVICE_SETTINGS_DEBUG_LEVEL_FIELDS,
					SX3PropertiesConstants.DEVICE_SETTINGS_DEBUG_LEVEL, hexMapProperties));

			// GPIOs GPIOS_SETTINGS Conversion
			GPIOs gpiosSettings = deviceSettings.getGPIOS_SETTINGS();
			result.append(getHexStringValueFromJsonObject(gpiosSettings,
					SX3PropertiesConstants.DEVICE_SETTINGS_GPIOS_SETTINGS_FIELDS,
					SX3PropertiesConstants.DEVICE_SETTINGS_GPIOS_SETTINGS, hexMapProperties));

			// append DEVICE_SETTINGS RESERVED data conversion at the end
			result.append(getHexStringValueFromJsonObject(deviceSettings,
					SX3PropertiesConstants.RESERVED, SX3PropertiesConstants.DEVICE_SETTINGS
							+ SX3PropertiesConstants.UNDER_SCORE + SX3PropertiesConstants.RESERVED_KEY,
					hexMapProperties));

			/**
			 * SX3 FIFO MASTER CONFIGURATION TABLE => Parameters listed in the
			 * FIFO Master Settings tab of the Config utility
			 **/
			// FIFO_MASTER_CONFIG data conversion
			FifoMasterConfig fifoMasterConfig = sx3Obj.getFIFO_MASTER_CONFIG();
			// Set FIFO_MASTER_CONFIG OffSet
			configTableOffSetTable.setFIFO_MASTER_CONFIG_OFFSET(getOffSet(fifoMasterConfig, result.length() / 2));
			result.append(
					getHexStringValueFromJsonObject(fifoMasterConfig, SX3PropertiesConstants.FIFO_MASTER_CONFIG_FIELDS,
							SX3PropertiesConstants.FIFO_MASTER_CONFIG, hexMapProperties));

			/**
			 * SX3 VIDEO SOURCE CONFIGURATION TABLE => Parameters listed in the
			 * Video Source Settings tab of the Config utility
			 **/
			// VIDEO_SOURCE_CONFIG data conversion
			List<VideoSourceConfig> videoSourceConfigs = sx3Obj.getVIDEO_SOURCE_CONFIG();
			if (videoSourceConfigs != null) {
				for (int i = 0; i < videoSourceConfigs.size(); i++) {
					// Set VIDEO_SOURCE_CONFIG OffSet
					if (i == 0 && "UVC".equals(usbSettings.getENDPOINT1_TYPE())) {
						configTableOffSetTable.setVIDEO_SOURCE_1_CONFIG_OFFSET(
								getOffSet(videoSourceConfigs.get(0), result.length() / 2));
					}
					if (usbSettings.getNUM_ENDPOINTS() == 2 && "UVC".equals(usbSettings.getENDPOINT2_TYPE())) {
						if (!"UVC".equals(usbSettings.getENDPOINT1_TYPE()) && videoSourceConfigs.size() == 1
								&& i == 0) {
							configTableOffSetTable.setVIDEO_SOURCE_2_CONFIG_OFFSET(
									getOffSet(videoSourceConfigs.get(i), result.length() / 2));
						} else if (i == 1) {
							configTableOffSetTable.setVIDEO_SOURCE_2_CONFIG_OFFSET(
									getOffSet(videoSourceConfigs.get(i), result.length() / 2));
						}

					}
					String hexStringValueFromJsonObject = getHexStringValueFromJsonObject(videoSourceConfigs.get(i),
							SX3PropertiesConstants.VIDEO_SOURCE_CONFIG_FIELDS,
							SX3PropertiesConstants.VIDEO_SOURCE_CONFIG, hexMapProperties);
					result.append(hexStringValueFromJsonObject);
				}
			} else {
				BytesStreamsAndHexFileUtil
						.log(SX3PropertiesConstants.VIDEO_SOURCE_CONFIG + OBJECT_BLOCK_NOT_FOUND_IN_JSON_FILE);
				result.append("");
			}

			/**
			 * SX3 SLAVE FIFO CONFIGURATION TABLE => Parameters listed in the
			 * SX3 Slave FIFO Settings tab of the Config utility
			 **/
			// SLAVE_FIFO_SETTINGS data conversion
			List<SlaveFIFOSettings> slaveFiFoSettings = sx3Obj.getSLAVE_FIFO_SETTINGS();
			if (slaveFiFoSettings != null) {
				for (int i = 0; i < slaveFiFoSettings.size(); i++) {
					// Set SLAVE_FIFO_SETTINGS OffSet
					if (i == 0) {
						configTableOffSetTable.setSLAVE_FIFO_1_SETTINGS_CONFIG_TABLE_OFFSET(
								getOffSet(slaveFiFoSettings.get(i), result.length() / 2));
					} else if (i == 1) {
						configTableOffSetTable.setSLAVE_FIFO_2_SETTINGS_CONFIG_TABLE_OFFSET(
								getOffSet(slaveFiFoSettings.get(i), result.length() / 2));
					}

					result.append(getHexStringValueFromJsonObject(slaveFiFoSettings.get(i),
							SX3PropertiesConstants.SLAVE_FIFO_SETTINGS_FIELDS,
							SX3PropertiesConstants.SLAVE_FIFO_SETTINGS, hexMapProperties));
				}
			} else {
				BytesStreamsAndHexFileUtil
						.log(SX3PropertiesConstants.SLAVE_FIFO_SETTINGS + OBJECT_BLOCK_NOT_FOUND_IN_JSON_FILE);
				result.append("");
			}

			/**
			 * SX3 UAC CONFIGURATION TABLE => Parameters listed in the UAC
			 * Settings tab of the Config utility
			 **/
			// UAC_Settings data conversion
			List<UACSettings> uacSettings = sx3Obj.getUAC_SETTINGS();
			if (uacSettings != null) {
				for (int i = 0; i < uacSettings.size(); i++) {
					// Set UAC_Settings OffSet
					if (i == 0 && "UAC".equals(usbSettings.getENDPOINT1_TYPE())) {
						configTableOffSetTable
								.setUAC_1_CONFIG_TABLE_OFFSET(getOffSet(uacSettings.get(0), result.length() / 2));
					}
					if (usbSettings.getNUM_ENDPOINTS() == 2 && "UAC".equals(usbSettings.getENDPOINT2_TYPE())) {
						if (!"UAC".equals(usbSettings.getENDPOINT1_TYPE()) && uacSettings.size() == 1 && i == 0) {
							configTableOffSetTable
									.setUAC_2_CONFIG_TABLE_OFFSET(getOffSet(uacSettings.get(i), result.length() / 2));
						} else if (i == 1) {
							configTableOffSetTable
									.setUAC_2_CONFIG_TABLE_OFFSET(getOffSet(uacSettings.get(i), result.length() / 2));
						}

					}

					// UAC_Settings -> EndPoint Settings data conversion
					EndpointSettings endpointSettings = uacSettings.get(i).getEndpointSettings();
					result.append(getHexStringValueFromJsonObject(endpointSettings,
							SX3PropertiesConstants.UAC_SETTINGS_ENDPOINT_SETTINGS_FIELDS,
							SX3PropertiesConstants.UAC_SETTINGS_ENDPOINT_SETTINGS, hexMapProperties));
					// UAC_Settings -> UAC Setting data conversion
					UACSetting uacSetting = uacSettings.get(i).getUAC_SETTING();
					result.append(getHexStringValueFromJsonObject(uacSetting,
							SX3PropertiesConstants.UAC_SETTINGS_UAC_SETTING_FIELDS,
							SX3PropertiesConstants.UAC_SETTINGS_UAC_SETTING, hexMapProperties));

					// append UAC_Settings RESERVED data conversion at the end
					result.append(getHexStringValueFromJsonObject(uacSettings.get(i),
							SX3PropertiesConstants.RESERVED, SX3PropertiesConstants.UAC_SETTINGS
									+ SX3PropertiesConstants.UNDER_SCORE + SX3PropertiesConstants.RESERVED_KEY,
							hexMapProperties));
				}
			} else {
				BytesStreamsAndHexFileUtil
						.log(SX3PropertiesConstants.UAC_SETTINGS + OBJECT_BLOCK_NOT_FOUND_IN_JSON_FILE);
				result.append("");
			}

			/**
			 * SX3 UVC CONFIGURATION TABLE => Parameters listed in the UVC
			 * Settings tab of the Config utility
			 **/
			// UVC_SETTINGS data conversion
			List<UVCSettings> uvcSettings = sx3Obj.getUVC_SETTINGS();
			if (uvcSettings != null) {
				for (int i = 0; i < uvcSettings.size(); i++) {
					// Set UVC_SETTINGS OffSet
					if (i == 0 && "UVC".equals(usbSettings.getENDPOINT1_TYPE())) {
						configTableOffSetTable
								.setUVC_1_CONFIG_TABLE_OFFSET(getOffSet(uvcSettings.get(0), result.length() / 2));
					}
					if (usbSettings.getNUM_ENDPOINTS() == 2 && "UVC".equals(usbSettings.getENDPOINT2_TYPE())) {
						if (!"UVC".equals(usbSettings.getENDPOINT1_TYPE()) && uvcSettings.size() == 1 && i == 0) {
							configTableOffSetTable
									.setUVC_2_CONFIG_TABLE_OFFSET(getOffSet(uvcSettings.get(i), result.length() / 2));
						} else if (i == 1) {
							configTableOffSetTable
									.setUVC_2_CONFIG_TABLE_OFFSET(getOffSet(uvcSettings.get(i), result.length() / 2));
						}
					}

					// UVC_SETTINGS -> ENDPOINT_SETTINGS data Conversion
					EndpointSettings endpointSettings = uvcSettings.get(i).getENDPOINT_SETTINGS();
					result.append(getHexStringValueFromJsonObject(endpointSettings,
							SX3PropertiesConstants.UVC_SETTINGS_ENDPOINT_SETTINGS_FIELDS,
							SX3PropertiesConstants.UVC_SETTINGS_ENDPOINT_SETTINGS, hexMapProperties));

					// UVC_SETTINGS -> FORMAT_RESOLUTIONS data Conversion
					FormatAndResolution formatResolution = uvcSettings.get(i).getFORMAT_RESOLUTION();
					result.append(getHexStringValueFromJsonObject(formatResolution,
							SX3PropertiesConstants.UVC_SETTINGS_FORMAT_RESOLUTION_FIELDS,
							SX3PropertiesConstants.UVC_SETTINGS_FORMAT_RESOLUTION, hexMapProperties));
					// UVC_SETTINGS -> FORMAT_RESOLUTION -> FORMAT_RESOLUTIONS
					// data Conversion
					List<FormatAndResolutions> formatResolutions = uvcSettings.get(i).getFORMAT_RESOLUTION()
							.getFORMAT_RESOLUTIONS();
					if (formatResolutions != null) {
						for (int j = 0; j < formatResolutions.size(); j++) {
							result.append(getHexStringValueFromJsonObject(formatResolutions.get(j),
									SX3PropertiesConstants.UVC_SETTINGS_FORMAT_RESOLUTIONS_FIELDS,
									SX3PropertiesConstants.UVC_SETTINGS_FORMAT_RESOLUTIONS, hexMapProperties));
						}
					} else {
						BytesStreamsAndHexFileUtil.log(SX3PropertiesConstants.UVC_SETTINGS_FORMAT_RESOLUTIONS
								+ OBJECT_BLOCK_NOT_FOUND_IN_JSON_FILE);
						result.append("");
					}

					// UVC_SETTINGS -> COLOR_MATCHING data Conversion
					ColorMatching colorMatching = uvcSettings.get(i).getCOLOR_MATCHING();
					// Set COLOR_MATCHING OffSet
					if (i == 0 && "UVC".equals(usbSettings.getENDPOINT1_TYPE())) {
						configTableOffSetTable.setUVC_1_COLOR_MATCHING_DESCRIPTOR_OFFSET(
								getOffSet(colorMatching, result.length() / 2));
					}
					if (usbSettings.getNUM_ENDPOINTS() == 2 && "UVC".equals(usbSettings.getENDPOINT2_TYPE())) {
						if (!"UVC".equals(usbSettings.getENDPOINT1_TYPE()) && uvcSettings.size() == 1 && i == 0) {
							configTableOffSetTable.setUVC_2_COLOR_MATCHING_DESCRIPTOR_OFFSET(
									getOffSet(colorMatching, result.length() / 2));
						} else if (i == 1) {
							configTableOffSetTable.setUVC_2_COLOR_MATCHING_DESCRIPTOR_OFFSET(
									getOffSet(colorMatching, result.length() / 2));
						}
					}

					result.append(getHexStringValueFromJsonObject(colorMatching,
							SX3PropertiesConstants.UVC_SETTINGS_COLOR_MATCHING_FIELDS,
							SX3PropertiesConstants.UVC_SETTINGS_COLOR_MATCHING, hexMapProperties));

					// UVC_SETTINGS -> CAMERA_CONTROL data Conversion
					CameraControl cameraControl = uvcSettings.get(i).getCAMERA_CONTROL();
					// Set CAMERA_CONTROL OffSet
					if ("UVC".equals(usbSettings.getENDPOINT1_TYPE())) {
						configTableOffSetTable
								.setUVC_1_CAMERA_CONTROLS_TABLE_OFFSET(getOffSet(cameraControl, result.length() / 2));
					}
					if (usbSettings.getNUM_ENDPOINTS() == 2 && "UVC".equals(usbSettings.getENDPOINT2_TYPE())) {
						if (!"UVC".equals(usbSettings.getENDPOINT1_TYPE()) && uvcSettings.size() == 1 && i == 0) {
							configTableOffSetTable.setUVC_2_CAMERA_CONTROLS_TABLE_OFFSET(
									getOffSet(cameraControl, result.length() / 2));
						} else if (i == 1) {
							configTableOffSetTable.setUVC_2_CAMERA_CONTROLS_TABLE_OFFSET(
									getOffSet(cameraControl, result.length() / 2));
						}
					}

					List<Map<String, Map<String, Object>>> cameraControls = cameraControl.getCAMERA_CONTROLS();
					result.append(getHexStringValueFromJsonObject(cameraControl,
							SX3PropertiesConstants.UVC_SETTINGS_CAMERA_CONTROL_FIELDS,
							SX3PropertiesConstants.UVC_SETTINGS_CAMERA_CONTROL, hexMapProperties));

					// CAMERA_CONTROL -> CAMERA_CONTROLS data Conversion
					if (cameraControls != null) {
						for (int k = 0; k < cameraControls.size(); k++) {
							Map<String, Map<String, Object>> camControlsMap = cameraControls.get(k);
							String mainField = "";
							Map<String, Object> jsnObj = null;
							for (Entry<String, Map<String, Object>> entry : camControlsMap.entrySet()) {
								mainField = entry.getKey();
								jsnObj = entry.getValue();
							}

							result.append(getHexStringValueFromJsonObject(jsnObj,
									SX3PropertiesConstants.getMainFieldAppendedWithExtendedFields(mainField),
									SX3PropertiesConstants.UVC_SETTINGS_CAMERA_CONTROLS + SX3PropertiesConstants.DOT
											+ mainField,
									hexMapProperties));
						}
					} else {
						BytesStreamsAndHexFileUtil.log(SX3PropertiesConstants.UVC_SETTINGS_CAMERA_CONTROLS
								+ OBJECT_BLOCK_NOT_FOUND_IN_JSON_FILE);
						result.append("");
					}

					// UVC_SETTINGS -> PROCESSING_UNIT_CONTROL data Conversion
					ProcessingUnitControl processingUnitControl = uvcSettings.get(i).getPROCESSING_UNIT_CONTROL();
					// Set PROCESSING_UNIT_CONTROL OffSet
					if (i == 0 && "UVC".equals(usbSettings.getENDPOINT1_TYPE())) {
						configTableOffSetTable.setUVC_1_PROCESSING_UNIT_TABLE_OFFSET(
								getOffSet(processingUnitControl, result.length() / 2));
					}
					if (usbSettings.getNUM_ENDPOINTS() == 2 && "UVC".equals(usbSettings.getENDPOINT2_TYPE())) {
						if (!"UVC".equals(usbSettings.getENDPOINT1_TYPE()) && uvcSettings.size() == 1 && i == 0) {
							configTableOffSetTable.setUVC_2_PROCESSING_UNIT_TABLE_OFFSET(
									getOffSet(processingUnitControl, result.length() / 2));
						} else if (i == 1) {
							configTableOffSetTable.setUVC_2_PROCESSING_UNIT_TABLE_OFFSET(
									getOffSet(processingUnitControl, result.length() / 2));
						}
					}

					result.append(getHexStringValueFromJsonObject(processingUnitControl,
							SX3PropertiesConstants.UVC_SETTINGS_PROCESSING_UNIT_CONTROL_FIELDS,
							SX3PropertiesConstants.UVC_SETTINGS_PROCESSING_UNIT_CONTROL, hexMapProperties));

					// PROCESSING_UNIT_CONTROL -> PROCESSING_UNIT_CONTROLS data
					// Conversion
					List<Map<String, Map<String, Object>>> processingUnitControls = processingUnitControl
							.getPROCESSING_UNIT_CONTROLS();
					if (processingUnitControls != null) {
						for (int l = 0; l < processingUnitControls.size(); l++) {
							Map<String, Map<String, Object>> processControlMap = processingUnitControls.get(l);
							String mainField = "";
							Map<String, Object> jsnObj = null;
							for (Entry<String, Map<String, Object>> entry : processControlMap.entrySet()) {
								mainField = entry.getKey();
								jsnObj = entry.getValue();
							}
							result.append(getHexStringValueFromJsonObject(jsnObj,
									SX3PropertiesConstants.getMainFieldAppendedWithExtendedFields(mainField),
									SX3PropertiesConstants.UVC_SETTINGS_PROCESSING_UNIT_CONTROLS
											+ SX3PropertiesConstants.DOT + mainField,
									hexMapProperties));
						}
					} else {
						BytesStreamsAndHexFileUtil.log(SX3PropertiesConstants.UVC_SETTINGS_PROCESSING_UNIT_CONTROLS
								+ OBJECT_BLOCK_NOT_FOUND_IN_JSON_FILE);
						result.append("");
					}

					// UVC_SETTINGS -> EXTENSION_UNIT_CONTROL data Conversion
					ExtensionUnitControl extUnitControl = uvcSettings.get(i).getEXTENSION_UNIT_CONTROL();
					// Set EXTENSION_UNIT_CONTROL OffSet
					if (i == 0 && "UVC".equals(usbSettings.getENDPOINT1_TYPE())) {
						configTableOffSetTable
								.setUVC_1_EXTENSION_UNIT_TABLE_OFFSET(getOffSet(extUnitControl, result.length() / 2));
					}
					if (usbSettings.getNUM_ENDPOINTS() == 2 && "UVC".equals(usbSettings.getENDPOINT2_TYPE())) {
						if (!"UVC".equals(usbSettings.getENDPOINT1_TYPE()) && uvcSettings.size() == 1 && i == 0) {
							configTableOffSetTable.setUVC_2_EXTENSION_UNIT_TABLE_OFFSET(
									getOffSet(extUnitControl, result.length() / 2));
						} else if (i == 1) {
							configTableOffSetTable.setUVC_2_EXTENSION_UNIT_TABLE_OFFSET(
									getOffSet(extUnitControl, result.length() / 2));
						}
					}

					result.append(getHexStringValueFromJsonObject(extUnitControl,
							SX3PropertiesConstants.UVC_SETTINGS_EXTENSION_UNIT_CONTROL_FIELDS,
							SX3PropertiesConstants.UVC_SETTINGS_EXTENSION_UNIT_CONTROL, hexMapProperties));

					// append UVC_Settings RESERVED data conversion at the end
					result.append(getHexStringValueFromJsonObject(uvcSettings.get(i),
							SX3PropertiesConstants.RESERVED, SX3PropertiesConstants.UVC_SETTINGS
									+ SX3PropertiesConstants.UNDER_SCORE + SX3PropertiesConstants.RESERVED_KEY,
							hexMapProperties));

				}
			} else {
				BytesStreamsAndHexFileUtil
						.log(SX3PropertiesConstants.UVC_SETTINGS + OBJECT_BLOCK_NOT_FOUND_IN_JSON_FILE);
				result.append("");
			}
			// update the modified config offset table values
			updateHexStringForModifiedOffSet(configTableOffSetTable, configOffsetHexStringStartIndex, result);

			// update coonfig table length
			updateConfigTableLength(result, configTableGen);

			File congigHexFile = getSX3ConfigHexFile();
			// DatatypeConverter.parseHexBinary(result.toString())
			BytesStreamsAndHexFileUtil.write(BytesStreamsAndHexFileUtil.hexStringToByteArray(result.toString()),
					congigHexFile);

			// Update the Config Table OffSet Table
			SX3ConfigCommonUtil.createSx3JsonFile(sx3Obj, SX3ConfigPreference.getSx3ConfigFilePathPreference());

		} catch (Exception e) {
			e.printStackTrace();
			BytesStreamsAndHexFileUtil.log(e);
		}
	}

	private static void updateConfigTableLength(StringBuilder result, ConfigTableGeneral configTableGen) {
		/* each byte is 2 bit size for hex */
		/** start after signature + checksum (4 bytes * 2 WCHAR) * */
		Map<String, Integer> configByteLengthMap = new SX3ParametersByteSizeBuilder().getParametersByteSizeMap(
				SX3PropertiesConstants.CONFIG_TABLE_GENERAL_FIELDS, SX3PropertiesConstants.CONFIG_TABLE_GENERAL);
		int configLenByteSize = configByteLengthMap.get("CONFIG_TABLE_LENGTH");
		int startIndex = 4 * 2;
		int fieldLength = configLenByteSize * 2;
		String configTblLengthHexString = StringUtils.leftPad(Integer.toHexString(result.length() / 2), fieldLength,
				"0");
		configTableGen
				.setCONFIG_TABLE_LENGTH(SX3PropertiesConstants.HEX_PREFIX + configTblLengthHexString.toUpperCase());
		result.replace(startIndex, startIndex + fieldLength, configTblLengthHexString);
	}

	private static String bitmaskValToHexVal(String bitmaskVal, Integer byteSize) {
		int num = Integer.parseInt(bitmaskVal, 2);
		String hexString = Integer.toHexString(num);
		return StringUtils.leftPad(hexString, byteSize * 2, "0");
	}

	private static void updateHexStringForModifiedOffSet(ConfigTableOffSetTable configTableOffSetTable,
			int configOffsetHexStringStartIndex, StringBuilder result) {
		int index = configOffsetHexStringStartIndex;
		for (String fieldName : SX3PropertiesConstants.CONFIG_TABLE_OFFSET_TABLE_FIELDS) {
			Object invokedValue = invokeMethodByFieldName(fieldName, configTableOffSetTable);
			if (!SX3PropertiesConstants.RESERVED_KEY.equals(fieldName) && invokedValue != null) {
				String hexString = new String((String) invokedValue).substring(2, ((String) invokedValue).length())
						.toLowerCase().trim();
				result.replace(index, index += 4, hexString.toUpperCase());
			}
		}

	}

	@SuppressWarnings("unchecked")
	public static String getHexStringValueFromJsonObject(Object jsnObj, String[] modelFields, String key,
			Properties hexMapProperties) {
		BytesStreamsAndHexFileUtil.log("Started to convert Hex data for the json object: " + key);
		// add device setting properties here
		Map<String, Integer> sx3ConfigPropertiesMap = new SX3ParametersByteSizeBuilder()
				.getParametersByteSizeMap(modelFields, key);
		// getPropertiesMapForSize(configProperties, key);
		Map<String, String> hexValueMapPropertiesMap = getPropertiesMapForHexValue(hexMapProperties, key);
		if (jsnObj == null) {
			BytesStreamsAndHexFileUtil.log(key + OBJECT_BLOCK_NOT_FOUND_IN_JSON_FILE);
			return SX3PropertiesConstants.NO_HEX_VALUE;
		}

		try {
			if (key.contains(SX3PropertiesConstants.RESERVED_KEY)) {
				return BytesStreamsAndHexFileUtil.convertJsonValuesToHex(SX3PropertiesConstants.RESERVED_HEX_VALUE, key,
						SX3PropertiesConstants.RESERVED_KEY, sx3ConfigPropertiesMap, hexValueMapPropertiesMap);
			} else if (jsnObj instanceof LinkedTreeMap<?, ?>) {
				LinkedTreeMap<String, String> treeMap = ((LinkedTreeMap<String, String>) jsnObj);
				StringBuilder sb = new StringBuilder();
				for (String modelField : modelFields) {
					Object val = modelField.contains(SX3PropertiesConstants.RESERVED_KEY)
							? SX3PropertiesConstants.RESERVED_HEX_VALUE : treeMap.get(modelField);
					val = val != null ? val : SX3PropertiesConstants.NO_HEX_VALUE;
					Object modelFieldValue = val instanceof Double ? ((Double) val).longValue() : val;

					if (modelField.contains("ENABLE")) {
						modelFieldValue = modelFieldValue.equals("Enable") ? 1 : 0;
					}
					sb.append(BytesStreamsAndHexFileUtil.convertJsonValuesToHex(modelFieldValue, key, modelField,
							sx3ConfigPropertiesMap, hexValueMapPropertiesMap));
				}
				return sb.toString();
			} else {
				return getSX3HexData(jsnObj, modelFields, key, sx3ConfigPropertiesMap, hexValueMapPropertiesMap);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return SX3PropertiesConstants.NO_HEX_VALUE;
		}

	}

	@SuppressWarnings("unchecked")
	protected static String getSX3HexData(Object modelObject, String[] modelFields, String modelKey,
			Map<String, Integer> sx3ConfigPropertiesMap, Map<String, String> hexValueMapPropertiesMap)
			throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		for (String propertyName : modelFields) {
			Object invokedValue = invokeMethodByFieldName(propertyName, modelObject);
			if (invokedValue != null) {
				if (invokedValue instanceof LinkedTreeMap<?, ?>) {
					if (SX3PropertiesConstants.UAC_SETTINGS_UAC_SETTING.equals(modelKey)) {
						String[] subFields = {};
						if ("CHANNEL_CONFIGURATION".equals(propertyName)) {
							subFields = UACSettingFieldConstants.CHANNEL_CONFIGURATION_FIELDS;
						} else if ("FEATURE_UNIT_CONTROLS".equals(propertyName)) {
							subFields = UACSettingFieldConstants.FEATURE_UNIT_CONTROLS_FIELDS;
						}
						LinkedTreeMap<String, String> treeMap = ((LinkedTreeMap<String, String>) invokedValue);
						StringBuilder sb = new StringBuilder();
						for (String key : subFields) {
							sb.append("Enable".equals(treeMap.get(key)) ? "1" : "0");
						}

						String bitMaskVal = propertyName.equals("CHANNEL_CONFIGURATION")
								? sb.append("0000").reverse().toString() : sb.reverse().toString();

						result.append(bitmaskValToHexVal(bitMaskVal, sx3ConfigPropertiesMap.get(propertyName)));
					}
				} else if (invokedValue instanceof ArrayList<?>) {
					if (((ArrayList) invokedValue).size() > 0) {
						Object invObj = ((ArrayList<?>) invokedValue).get(0);
						String[] subModelFields = SX3PropertiesConstants.UVC_SETTINGS_REGISTER_SETTING_FIELDS;
						if (invObj != null && invObj instanceof SensorConfig) {

							Map<String, Integer> subModelConfigPropertiesMap = new SX3ParametersByteSizeBuilder()
									.getParametersByteSizeMap(subModelFields, propertyName);

							List<SensorConfig> sensorConfigList = new ArrayList<>();
							for (Object sensorConfig : (List<?>) invokedValue) {
								if (sensorConfig != null) {
									sensorConfigList.add((SensorConfig) sensorConfig);
								}
							}

							Iterator<SensorConfig> iterator = sensorConfigList.iterator();
							StringBuilder sensorConfigHexSb = new StringBuilder();
							while (iterator.hasNext()) {
								SensorConfig sensorConfig = iterator.next();
								for (String subModelField : subModelFields) {
									Object invokedValue1 = invokeMethodByFieldName(subModelField, sensorConfig);
									sensorConfigHexSb.append(BytesStreamsAndHexFileUtil.convertJsonValuesToHex(
											invokedValue1.toString().trim(), modelKey, subModelField,
											subModelConfigPropertiesMap, hexValueMapPropertiesMap));
								}
							}
							String oveAllResolutionRegisterSettings = sensorConfigHexSb.toString();
							result.append(oveAllResolutionRegisterSettings);
							// .append(BytesStreamsAndHexFileUtil.appendExtraBytes(
							// BytesStreamsAndHexFileUtil
							// .hexStringToByteArray(oveAllResolutionRegisterSettings),
							// sx3ConfigPropertiesMap.get(propertyName),
							// SX3PropertiesConstants.NO_HEX_VALUE,
							// 1));
						}
					}
					} else {
						if ((modelObject instanceof CameraControl
								&& propertyName.equals("CAMERA_CONTROLS_ENABLED_BITMASK"))
								|| (modelObject instanceof ProcessingUnitControl
										&& propertyName.equals("PROCESSING_UNIT_ENABLED_CONTROLS_BITMASK"))) {

							String bitmaskVal = invokedValue.toString().substring(2);
							String binaryVal = !bitmaskVal.isEmpty() ? SX3PropertiesConstants.NO_HEX_VALUE : bitmaskVal;
							String bitMaskHexval = bitmaskValToHexVal(binaryVal,
									sx3ConfigPropertiesMap.get(propertyName));
							return bitMaskHexval;
						} else if (modelObject instanceof ConfigTableGeneral
								|| modelObject instanceof ConfigTableOffSetTable) {
							if (invokedValue.equals(SX3PropertiesConstants.SIGNATURE)) {
								result.append(BytesStreamsAndHexFileUtil
										.convertByteToHexString((SX3PropertiesConstants.SIGNATURE.getBytes()), 1));
							} else {
								result.append(BytesStreamsAndHexFileUtil.convertJsonValuesToHex(invokedValue, modelKey,
										propertyName, sx3ConfigPropertiesMap, hexValueMapPropertiesMap));
							}
						} else if (modelObject instanceof USBSettings
								&& Arrays.stream(SX3PropertiesConstants.DEVICE_SETTINGS_USB_SETTINGS_LENGTH_FIELDS)
										.parallel().anyMatch(propertyName::contains)) {
							int intVal = ((int) invokedValue) * 2;
							result.append(BytesStreamsAndHexFileUtil.convertJsonValuesToHex(intVal, modelKey,
									propertyName, sx3ConfigPropertiesMap, hexValueMapPropertiesMap));
							// result.append(String.format("%02d", intVal));
						} else {
							result.append(BytesStreamsAndHexFileUtil.convertJsonValuesToHex(invokedValue, modelKey,
									propertyName, sx3ConfigPropertiesMap, hexValueMapPropertiesMap));
						}
				}

			} else {
				result.append(SX3PropertiesConstants.NO_HEX_VALUE);
			}
		}
		return result.toString();
	}

	public static String getBitMaskBinaryValue(Object modelObject, String propertyName) {
		StringBuilder sb = new StringBuilder();
		if ((modelObject instanceof CameraControl && propertyName.equals("CAMERA_CONTROLS_ENABLED_BITMASK"))
				|| (modelObject instanceof ProcessingUnitControl
						&& propertyName.equals("PROCESSING_UNIT_ENABLED_CONTROLS_BITMASK"))) {
			List<Map<String, Map<String, Object>>> listMapObject = (modelObject instanceof CameraControl)
					? ((CameraControl) modelObject).getCAMERA_CONTROLS()
					: ((ProcessingUnitControl) modelObject).getPROCESSING_UNIT_CONTROLS();
			// Convert List of Maps to single Map via streams
			Map<String, Map<String, Object>> mapObj = new HashMap<>();
			listMapObject.stream().forEach(map1 -> {
				mapObj.putAll(map1.entrySet().stream().collect(Collectors.toMap(entry -> (String) entry.getKey(),
						entry -> (Map<String, Object>) entry.getValue())));
			});

			// update the bit mask

			String[] bitmaskFields = propertyName.equals("CAMERA_CONTROLS_ENABLED_BITMASK")
					? SX3PropertiesConstants.CAMERA_CONTROLS_ENABLED_BITMASK_FIELDS
					: SX3PropertiesConstants.PROCESSING_UNIT_ENABLED_CONTROLS_BITMASK_FIELDS;
			for (String key : bitmaskFields) {
				String actualParam = key.split(":")[1].trim();
				if (SX3PropertiesConstants.RESERVED_KEY.equals(actualParam)) {
					sb.append("0");
				} else {
					Map<String, Object> actualMap = mapObj.get(actualParam);
					if (actualMap != null && !actualMap.isEmpty()) {
						sb.append("Enable".equals((String) actualMap.get(actualParam + "_ENABLE")) ? "1" : "0");
					} else {
						sb.append("0");
					}
				}
			}

			sb.reverse();
		}
		return sb.toString();
	}

	static File getSX3ConfigHexFile() {
		File jsonFile = BytesStreamsAndHexFileUtil.getConfigJsonFile();
		File congigHexFile = new File(jsonFile.getParentFile().getAbsolutePath() + File.separator
				+ jsonFile.getName().substring(0, jsonFile.getName().lastIndexOf('.'))
				+ SX3PropertiesConstants.HEX_FILE_EXTENSION);
		return congigHexFile;
	}

	protected static List<File> getVideoSoureConfigFiles(File jsonFile) {
		// Gson parser object to parse json file
		BytesStreamsAndHexFileUtil.log(
				"Getting the video source config file path from : " + jsonFile.getName() + " => VIDEO_SOURCE_CONFIG");
		// Gson parser object to parse json file
		List<File> fileList = new ArrayList<>();
		Gson gson = new Gson();
		try {
			JsonReader reader = new JsonReader(new FileReader(jsonFile));
			// convert the json string back to object
			SX3Configuration sx3Obj = gson.fromJson(reader, SX3Configuration.class);
			List<VideoSourceConfig> videoSourceConfigList = sx3Obj.getVIDEO_SOURCE_CONFIG();
			if (videoSourceConfigList != null && !videoSourceConfigList.isEmpty()) {
				for (VideoSourceConfig videoSourceConfig : videoSourceConfigList) {
					String videoSourceConfigFilePath = videoSourceConfig.getVIDEO_SOURCE_CONFIG_FILE_PATH();
					if (videoSourceConfigFilePath != null) {
						fileList.add(new File(videoSourceConfigFilePath));
					}
				}
			} else {
				BytesStreamsAndHexFileUtil.log("VIDEO_SOURCE_CONFIG object not created");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileList;
	}

	/**
	 * Returns video source Hex file
	 * 
	 * @param configProperties
	 * @param fileName
	 */
	public static File getVideoSourceConfigHexFile(String fileName) {
		File jsonFile = BytesStreamsAndHexFileUtil.getConfigJsonFile();
		File congigHexFile = new File(jsonFile.getParentFile().getAbsolutePath() + File.separator + fileName
				+ SX3PropertiesConstants.HEX_FILE_EXTENSION);
		return congigHexFile;
	}

	public static void convertVideoSourceConfigToHexFile(File jsonFile) {
		List<File> videoSoureConfigFiles = getVideoSoureConfigFiles(jsonFile);
		if (videoSoureConfigFiles != null && !videoSoureConfigFiles.isEmpty()) {
			for (File videoSourceConfigFile : videoSoureConfigFiles) {
				if (videoSourceConfigFile != null && videoSourceConfigFile.exists()) {
					List<String> videoSourceList = BytesStreamsAndHexFileUtil.readAsListImpl(videoSourceConfigFile);
					StringBuilder hexValueString = new StringBuilder();
					for (String listVal : videoSourceList) {
						String replaceString = listVal.replaceAll("[{}]", " ");
						final int secondLast = replaceString.length() - 2;
						String subString = replaceString.substring(0, replaceString.lastIndexOf(',', secondLast));
						String[] hexValues = subString.split(",");
						for (String hexValue : hexValues) {
							if (!hexValue.isEmpty()) {
								String replace0xString = hexValue.replace(SX3PropertiesConstants.HEX_PREFIX, "").trim();
								if (replace0xString.length() == 1) {
									hexValueString.append("0" + replace0xString);
								} else {
									hexValueString.append(replace0xString);
								}
							}
						}
					}
					byte[] convertHexStringToHexBytes = BytesStreamsAndHexFileUtil
							.hexStringToByteArray(hexValueString.toString());
					BytesStreamsAndHexFileUtil.write(convertHexStringToHexBytes, getVideoSourceConfigHexFile(
							BytesStreamsAndHexFileUtil.getFileNameWithoutExtension(videoSourceConfigFile)));
				} else {
					BytesStreamsAndHexFileUtil
							.log(SX3PropertiesConstants.VIDEO_SOURCE_CONFIG_FILE_PATH_NOT_ADDED_IN_JSON);
				}
			}
		} else {
			BytesStreamsAndHexFileUtil.log(SX3PropertiesConstants.NO_FILE_PATHS_ADDED_FOR_VIDEO_SOURCE_CONFIG);
		}
	}

	private static Map<String, String> getPropertiesMapForHexValue(Properties chexMapProperties, String key) {
		Map<String, String> propertiesMap = new HashMap<>();
		propertiesMap.putAll(chexMapProperties.entrySet().stream().filter(map -> map.getKey().toString().contains(key))
				.collect(Collectors.toMap(e -> e.getKey().toString(), e -> (String) e.getValue())));
		return propertiesMap;
	}

	private static String getOffSet(Object jsonOnj, int intVal) {
		String result = SX3PropertiesConstants.CONFIG_TABLE_OFFSET_DEFAULT_VALUE;
		if (jsonOnj != null) {
			String hexOffSet = Integer.toHexString(intVal);
			return SX3PropertiesConstants.HEX_PREFIX + StringUtils.leftPad(hexOffSet, 4, "0");
		}
		return result;
	}

	public static Object invokeMethodByFieldName(String propertyName, Object obj) {
		Object getVal = null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(propertyName, obj.getClass());
			Method getter = pd.getReadMethod();
			getVal = getter.invoke(obj);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| IntrospectionException e) {
			BytesStreamsAndHexFileUtil.log(e);
		}
		return getVal;
	}

}
