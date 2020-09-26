package sx3Configuration.mergertool;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;

import sx3Configuration.model.AuxilliaryInterface;
import sx3Configuration.model.CSVKeys;
import sx3Configuration.model.CSVValues;
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
import sx3Configuration.model.HDMISourceConfiguration;
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
import sx3Configuration.ui.SX3Manager;
import sx3Configuration.util.SX3ConfigCommonUtil;
import sx3Configuration.util.UACSettingFieldConstants;

public class SX3ConfigHexFileUtil {

	private static final String OBJECT_BLOCK_NOT_FOUND_IN_JSON_FILE = " object block not found in Json file";

	/**
	 * Read the given Json file, and convert its contents as a HEX/ASCII data.
	 */
	public static void convertJsonToHexFile() {
		StringBuilder result = new StringBuilder();
		BytesStreamsAndHexFileUtil.log("Fetching SX3 Configuration Object from Manager.");
		try {
			Properties hexMapProperties = BytesStreamsAndHexFileUtil.gethexValueMappingProperties();
			// get the Sx3 object from the manager
			SX3Configuration sx3Obj = SX3Manager.getInstance().getSx3Configuration();

			// create an list of json key obj and value obj for CSV generation
			LinkedHashMap<CSVKeys, CSVValues> csvMapObj = new LinkedHashMap<>();

			/**
			 * CONFIGURATION TABLE SETTINGS => Checksum, Version Information,
			 * Size
			 **/
			// CONFIG_TABLE_GENERAL data conversion
			ConfigTableGeneral configTableGen = sx3Obj.getCONFIG_TABLE_GENERAL();
			if (configTableGen != null) {
				result.append(getHexStringValueFromJsonObject(configTableGen,
						SX3PropertiesConstants.CONFIG_TABLE_GENERAL_FIELDS, SX3PropertiesConstants.CONFIG_TABLE_GENERAL,
						hexMapProperties, csvMapObj, false, 0));
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
					SX3PropertiesConstants.CONFIG_TABLE_OFFSET_TABLE, hexMapProperties, csvMapObj, false, 0));

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
					SX3PropertiesConstants.DEVICE_SETTINGS_USB_SETTINGS, hexMapProperties, csvMapObj, false, 0));

			// DEBUG_LEVEL data Conversion
			DebugLevel debugLevel = deviceSettings.getDEBUG_LEVEL();
			result.append(getHexStringValueFromJsonObject(debugLevel,
					SX3PropertiesConstants.DEVICE_SETTINGS_DEBUG_LEVEL_FIELDS,
					SX3PropertiesConstants.DEVICE_SETTINGS_DEBUG_LEVEL, hexMapProperties, csvMapObj, false, 0));

			// GPIOs GPIOS_SETTINGS Conversion
			GPIOs gpiosSettings = deviceSettings.getGPIOS_SETTINGS();
			result.append(getHexStringValueFromJsonObject(gpiosSettings,
					SX3PropertiesConstants.DEVICE_SETTINGS_GPIOS_SETTINGS_FIELDS,
					SX3PropertiesConstants.DEVICE_SETTINGS_GPIOS_SETTINGS, hexMapProperties, csvMapObj, false, 0));

			// Auxilliary Interface Conversion
			AuxilliaryInterface auxInf = deviceSettings.getAUXILLIARY_INTERFACE();
			result.append(getHexStringValueFromJsonObject(auxInf,
					SX3PropertiesConstants.DEVICE_SETTINGS_AUXILLIARY_INTERFACE_FIELDS,
					SX3PropertiesConstants.DEVICE_SETTINGS_AUXILLIARY_INTERFACE, hexMapProperties, csvMapObj, false,
					0));

			// append DEVICE_SETTINGS RESERVED data conversion at the end
			result.append(getHexStringValueFromJsonObject(deviceSettings,
					SX3PropertiesConstants.RESERVED, SX3PropertiesConstants.DEVICE_SETTINGS
							+ SX3PropertiesConstants.UNDER_SCORE + SX3PropertiesConstants.RESERVED_KEY,
					hexMapProperties, csvMapObj, false, 0));

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
							SX3PropertiesConstants.FIFO_MASTER_CONFIG, hexMapProperties, csvMapObj, false, 0));

			/**
			 * SX3 VIDEO SOURCE CONFIGURATION TABLE => Parameters listed in the
			 * Video Source Settings tab of the Config utility
			 **/
			// VIDEO_SOURCE_CONFIG data conversion
			List<VideoSourceConfig> videoSourceConfigs = sx3Obj.getVIDEO_SOURCE_CONFIG();
			if (videoSourceConfigs != null) {
				// Set VIDEO_SOURCE_CONFIG OffSet
				int size = videoSourceConfigs.size();
				configTableOffSetTable
						.setVIDEO_SOURCE_1_CONFIG_OFFSET(SX3PropertiesConstants.CONFIG_TABLE_OFFSET_DEFAULT_VALUE);
				configTableOffSetTable
						.setVIDEO_SOURCE_2_CONFIG_OFFSET(SX3PropertiesConstants.CONFIG_TABLE_OFFSET_DEFAULT_VALUE);
				for (int i = 0; i < size; i++) {
					if (videoSourceConfigs.get(i).getENDPOINT() == 1) {

						configTableOffSetTable.setVIDEO_SOURCE_1_CONFIG_OFFSET(
								getOffSet(videoSourceConfigs.get(i), result.length() / 2));
					} else {
						configTableOffSetTable.setVIDEO_SOURCE_2_CONFIG_OFFSET(
								getOffSet(videoSourceConfigs.get(i), result.length() / 2));
					}

					String hexStringValueFromJsonObject = getHexStringValueFromJsonObject(videoSourceConfigs.get(i),
							SX3PropertiesConstants.VIDEO_SOURCE_CONFIG_FIELDS,
							SX3PropertiesConstants.VIDEO_SOURCE_CONFIG, hexMapProperties, csvMapObj, true, i);
					result.append(hexStringValueFromJsonObject);

					// Show HDMI config table if Video Source Subtype is "HDMI
					// RX - Generic"
					if ("HDMI RX - Generic".equals(videoSourceConfigs.get(i).getVIDEO_SOURCE_SUBTYPE())) {
						// VIDEO_SOURCE_CONFIG HDMI data conversion
						if ("HDMI Source".equals(videoSourceConfigs.get(i).getVIDEO_SOURCE_TYPE())) {
							List<HDMISourceConfiguration> hdmi_config = videoSourceConfigs.get(i)
									.getHDMI_SOURCE_CONFIGURATION();
							if (hdmi_config != null && !hdmi_config.isEmpty()) {
								for (int h = 0; h < hdmi_config.size(); h++) {
									result.append(getHexStringValueFromJsonObject(hdmi_config.get(h),
											SX3PropertiesConstants.HDMI_SOURCE_CONFIGURATION_FIELDS,
											SX3PropertiesConstants.VIDEO_TYPE_HDMI_SOURCE_CONFIGURATION,
											hexMapProperties, csvMapObj, true, h));
								}
							}
						}
					}
					// append VIDEO_SOURCE_CONFIG RESERVED data conversion at
					// the end
					result.append(getHexStringValueFromJsonObject(deviceSettings,
							SX3PropertiesConstants.RESERVED, SX3PropertiesConstants.VIDEO_SOURCE_CONFIG
									+ SX3PropertiesConstants.UNDER_SCORE + SX3PropertiesConstants.RESERVED_KEY,
							hexMapProperties, csvMapObj, false, 0));
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
							SX3PropertiesConstants.SLAVE_FIFO_SETTINGS, hexMapProperties, csvMapObj, true, i));
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
					if ("UAC".equals(usbSettings.getENDPOINT2_TYPE())) {
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
							SX3PropertiesConstants.UAC_SETTINGS_ENDPOINT_SETTINGS, hexMapProperties, csvMapObj, true,
							i));
					// UAC_Settings -> UAC Setting data conversion
					UACSetting uacSetting = uacSettings.get(i).getUAC_SETTING();
					result.append(getHexStringValueFromJsonObject(uacSetting,
							SX3PropertiesConstants.UAC_SETTINGS_UAC_SETTING_FIELDS,
							SX3PropertiesConstants.UAC_SETTINGS_UAC_SETTING, hexMapProperties, csvMapObj, true, i));

					// append UAC_Settings RESERVED data conversion at the end
					result.append(getHexStringValueFromJsonObject(uacSettings.get(i),
							SX3PropertiesConstants.RESERVED, SX3PropertiesConstants.UAC_SETTINGS
									+ SX3PropertiesConstants.UNDER_SCORE + SX3PropertiesConstants.RESERVED_KEY,
							hexMapProperties, csvMapObj, true, i));
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
					if ("UVC".equals(usbSettings.getENDPOINT2_TYPE())) {
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
							SX3PropertiesConstants.UVC_SETTINGS_ENDPOINT_SETTINGS, hexMapProperties, csvMapObj, true,
							i));

					// UVC_SETTINGS -> FORMAT_RESOLUTIONS data Conversion
					FormatAndResolution formatResolution = uvcSettings.get(i).getFORMAT_RESOLUTION();
					result.append(getHexStringValueFromJsonObject(formatResolution,
							SX3PropertiesConstants.UVC_SETTINGS_FORMAT_RESOLUTION_FIELDS,
							SX3PropertiesConstants.UVC_SETTINGS_FORMAT_RESOLUTION, hexMapProperties, csvMapObj, true,
							i));
					// UVC_SETTINGS -> FORMAT_RESOLUTION -> FORMAT_RESOLUTIONS
					// data Conversion
					List<FormatAndResolutions> formatResolutions = uvcSettings.get(i).getFORMAT_RESOLUTION()
							.getFORMAT_RESOLUTIONS();
					if (formatResolutions != null) {
						for (int j = 0; j < formatResolutions.size(); j++) {
							result.append(getHexStringValueFromJsonObject(formatResolutions.get(j),
									SX3PropertiesConstants.UVC_SETTINGS_FORMAT_RESOLUTIONS_FIELDS,
									SX3PropertiesConstants.UVC_SETTINGS_FORMAT_RESOLUTIONS, hexMapProperties, csvMapObj,
									true, j));
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
					if ("UVC".equals(usbSettings.getENDPOINT2_TYPE())) {
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
							SX3PropertiesConstants.UVC_SETTINGS_COLOR_MATCHING, hexMapProperties, csvMapObj, true, i));

					// UVC_SETTINGS -> CAMERA_CONTROL data Conversion
					CameraControl cameraControl = uvcSettings.get(i).getCAMERA_CONTROL();
					// Set CAMERA_CONTROL OffSet
					if (i == 0 && "UVC".equals(usbSettings.getENDPOINT1_TYPE())) {
						configTableOffSetTable
								.setUVC_1_CAMERA_CONTROLS_TABLE_OFFSET(getOffSet(cameraControl, result.length() / 2));
					}
					if ("UVC".equals(usbSettings.getENDPOINT2_TYPE())) {
						if (!"UVC".equals(usbSettings.getENDPOINT1_TYPE()) && uvcSettings.size() == 1 && i == 0) {
							configTableOffSetTable.setUVC_2_CAMERA_CONTROLS_TABLE_OFFSET(
									getOffSet(cameraControl, result.length() / 2));
						} else if (i == 1) {
							configTableOffSetTable.setUVC_2_CAMERA_CONTROLS_TABLE_OFFSET(
									getOffSet(cameraControl, result.length() / 2));
						}
					}

					List<LinkedHashMap<String, LinkedHashMap<String, Object>>> cameraControls = cameraControl
							.getCAMERA_CONTROLS();
					result.append(getHexStringValueFromJsonObject(cameraControl,
							SX3PropertiesConstants.UVC_SETTINGS_CAMERA_CONTROL_FIELDS,
							SX3PropertiesConstants.UVC_SETTINGS_CAMERA_CONTROL, hexMapProperties, csvMapObj, true, i));

					// CAMERA_CONTROL -> CAMERA_CONTROLS data Conversion
					if (cameraControls != null) {
						for (int k = 0; k < cameraControls.size(); k++) {
							Map<String, LinkedHashMap<String, Object>> camControlsMap = cameraControls.get(k);
							String mainField = "";
							Map<String, Object> jsnObj = null;
							for (Entry<String, LinkedHashMap<String, Object>> entry : camControlsMap.entrySet()) {
								mainField = entry.getKey();
								jsnObj = entry.getValue();
							}

							result.append(
									getHexStringValueFromJsonObject(jsnObj,
											SX3PropertiesConstants.getMainFieldAppendedWithExtendedFields(mainField),
											SX3PropertiesConstants.UVC_SETTINGS_CAMERA_CONTROLS
													+ SX3PropertiesConstants.DOT + mainField,
											hexMapProperties, csvMapObj, true, k));
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
					if ("UVC".equals(usbSettings.getENDPOINT2_TYPE())) {
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
							SX3PropertiesConstants.UVC_SETTINGS_PROCESSING_UNIT_CONTROL, hexMapProperties, csvMapObj,
							true, i));

					// PROCESSING_UNIT_CONTROL -> PROCESSING_UNIT_CONTROLS data
					// Conversion
					List<LinkedHashMap<String, LinkedHashMap<String, Object>>> processingUnitControls = processingUnitControl
							.getPROCESSING_UNIT_CONTROLS();
					if (processingUnitControls != null) {
						for (int l = 0; l < processingUnitControls.size(); l++) {
							Map<String, LinkedHashMap<String, Object>> processControlMap = processingUnitControls
									.get(l);
							String mainField = "";
							Map<String, Object> jsnObj = null;
							for (Entry<String, LinkedHashMap<String, Object>> entry : processControlMap.entrySet()) {
								mainField = entry.getKey();
								jsnObj = entry.getValue();
							}
							result.append(getHexStringValueFromJsonObject(jsnObj,
									SX3PropertiesConstants.getMainFieldAppendedWithExtendedFields(mainField),
									SX3PropertiesConstants.UVC_SETTINGS_PROCESSING_UNIT_CONTROLS
											+ SX3PropertiesConstants.DOT + mainField,
									hexMapProperties, csvMapObj, true, l));
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
					if ("UVC".equals(usbSettings.getENDPOINT2_TYPE())) {
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
							SX3PropertiesConstants.UVC_SETTINGS_EXTENSION_UNIT_CONTROL, hexMapProperties, csvMapObj,
							true, i));

					// append UVC_Settings RESERVED data conversion at the end
					result.append(getHexStringValueFromJsonObject(uvcSettings.get(i),
							SX3PropertiesConstants.RESERVED, SX3PropertiesConstants.UVC_SETTINGS
									+ SX3PropertiesConstants.UNDER_SCORE + SX3PropertiesConstants.RESERVED_KEY,
							hexMapProperties, csvMapObj, true, i));

				}
			} else {
				BytesStreamsAndHexFileUtil
						.log(SX3PropertiesConstants.UVC_SETTINGS + OBJECT_BLOCK_NOT_FOUND_IN_JSON_FILE);
				result.append("");
			}

			csvMapObj.remove(null);

			// update the modified config offset table values
			updateHexStringForModifiedOffSet(configTableOffSetTable, configOffsetHexStringStartIndex, result,
					csvMapObj);

			// update coonfig table length
			updateConfigTableLength(result, configTableGen, csvMapObj);
			updateConfigTableCheckSum(result, configTableGen, csvMapObj);

			File congigHexFile = getSX3ConfigHexFile();
			// DatatypeConverter.parseHexBinary(result.toString())
			BytesStreamsAndHexFileUtil.write(BytesStreamsAndHexFileUtil.hexStringToByteArray(result.toString()),
					congigHexFile);

			// Update the Config Table OffSet Table
			SX3ConfigCommonUtil.createSx3JsonFile(sx3Obj, SX3ConfigPreference.getSx3ConfigFilePathPreference());

			if (BytesStreamsAndHexFileUtil.needCSVFileGeneration()) { // only
																		// require
																		// for
																		// development
																		// env
				// create csv file
				BytesStreamsAndHexFileUtil.convertJSONToCSV(csvMapObj);
			}

		} catch (Exception e) {
			e.printStackTrace();
			BytesStreamsAndHexFileUtil.log(e);
		}
	}

	private static void updateConfigTableCheckSum(StringBuilder result, ConfigTableGeneral configTableGen,
			Map<CSVKeys, CSVValues> csvMapObj) {
		/** start after signature(2 bytes) + checksum (4 bytes) * */
		Map<String, Integer> configByteLengthMap = new SX3ParametersByteSizeBuilder().getParametersByteSizeMap(
				SX3PropertiesConstants.CONFIG_TABLE_GENERAL_FIELDS, SX3PropertiesConstants.CONFIG_TABLE_GENERAL);
		int configSigByteSize = configByteLengthMap.get("SIGNATURE");
		int configCheckkSumByteSize = configByteLengthMap.get("CONFIG_TABLE_CHECKSUM");
		int startIndex = (configSigByteSize)
				* 2; /* each byte is 2 bit size for hex */
		int fieldLength = configCheckkSumByteSize * 2;

		String allHexString = result.substring((configSigByteSize + configCheckkSumByteSize) * 2);
		String[] hexStringToHexStringArray = BytesStreamsAndHexFileUtil.hexStringToHexStringArray(allHexString);

		int configTblCheckSumval = 0;
		for (String val : hexStringToHexStringArray) {
			configTblCheckSumval += Integer.parseInt(val, 16);
		}

		String configTblCSHexString = Integer.toHexString(configTblCheckSumval);
		String hexVal = BytesStreamsAndHexFileUtil.leftPad(configTblCSHexString, fieldLength);
		String jsnValue = SX3PropertiesConstants.HEX_PREFIX + configTblCSHexString.toUpperCase();
		updateCSVData(SX3PropertiesConstants.CONFIG_TABLE_GENERAL, csvMapObj, "CONFIG_TABLE_CHECKSUM", jsnValue,
				hexVal);
		configTableGen.setCONFIG_TABLE_CHECKSUM(jsnValue);
		result.replace(startIndex, startIndex + fieldLength, hexVal);
	}

	private static void updateConfigTableLength(StringBuilder result, ConfigTableGeneral configTableGen,
			Map<CSVKeys, CSVValues> csvMapObj) {

		/** start after signature(2 bytes) + checksum (4 bytes) * */
		Map<String, Integer> configByteLengthMap = new SX3ParametersByteSizeBuilder().getParametersByteSizeMap(
				SX3PropertiesConstants.CONFIG_TABLE_GENERAL_FIELDS, SX3PropertiesConstants.CONFIG_TABLE_GENERAL);
		int configSigByteSize = configByteLengthMap.get("SIGNATURE");
		int configCheckkSumByteSize = configByteLengthMap.get("CONFIG_TABLE_CHECKSUM");
		int configLenByteSize = configByteLengthMap.get("CONFIG_TABLE_LENGTH");
		int startIndex = (configSigByteSize + configCheckkSumByteSize)
				* 2; /* each byte is 2 bit size for hex */
		int fieldLength = configLenByteSize * 2;
		String configTblLengthHexString = BytesStreamsAndHexFileUtil.leftPad(Integer.toHexString(result.length() / 2),
				fieldLength);
		String jsnValue = SX3PropertiesConstants.HEX_PREFIX + configTblLengthHexString.toUpperCase();
		updateCSVData(SX3PropertiesConstants.CONFIG_TABLE_GENERAL, csvMapObj, "CONFIG_TABLE_LENGTH", jsnValue,
				configTblLengthHexString);
		configTableGen.setCONFIG_TABLE_LENGTH(jsnValue);

		result.replace(startIndex, startIndex + fieldLength, configTblLengthHexString);
	}

	private static String bitmaskValToHexVal(String bitmaskVal, Integer byteSize) {
		long num = Long.parseLong(bitmaskVal, 2);
		String hexString = Long.toHexString(num);
		return BytesStreamsAndHexFileUtil.leftPad(hexString, byteSize * 2);
	}

	private static void updateHexStringForModifiedOffSet(ConfigTableOffSetTable configTableOffSetTable,
			int configOffsetHexStringStartIndex, StringBuilder result, Map<CSVKeys, CSVValues> csvMapObj) {
		int index = configOffsetHexStringStartIndex;
		for (String fieldName : SX3PropertiesConstants.CONFIG_TABLE_OFFSET_TABLE_FIELDS) {
			Object invokedValue = invokeMethodByFieldName(fieldName, configTableOffSetTable);
			if (!SX3PropertiesConstants.RESERVED_KEY.equals(fieldName) && invokedValue != null) {
				String hexString = new String((String) invokedValue).substring(2, ((String) invokedValue).length())
						.toLowerCase().trim();
				updateCSVData(SX3PropertiesConstants.CONFIG_TABLE_OFFSET_TABLE, csvMapObj, fieldName,
						(String) invokedValue, hexString);
				result.replace(index, index += 4, hexString.toUpperCase());
			}
		}

	}

	private static void updateCSVData(String parentKey, Map<CSVKeys, CSVValues> csvMapObj, String fieldName,
			String jsnVal, String hexString) {
		CSVKeys ck = new CSVKeys();
		ck.setParentKey(parentKey);
		ck.setActualKey(fieldName);
		CSVValues cv = csvMapObj.get(ck);
		cv.setJsonValue(jsnVal);
		cv.setHexValue(hexString);
		csvMapObj.replace(ck, cv);
	}

	@SuppressWarnings("unchecked")
	public static String getHexStringValueFromJsonObject(Object jsnObj, String[] modelFields, String key,
			Properties hexMapProperties, LinkedHashMap<CSVKeys, CSVValues> csvMapObj, boolean isList,
			int indxForLists) {
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
						SX3PropertiesConstants.RESERVED_KEY, sx3ConfigPropertiesMap, hexValueMapPropertiesMap,
						csvMapObj, isList, indxForLists);
			} else if (jsnObj instanceof LinkedHashMap<?, ?>) {
				LinkedHashMap<String, String> treeMap = ((LinkedHashMap<String, String>) jsnObj);
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
							sx3ConfigPropertiesMap, hexValueMapPropertiesMap, csvMapObj, isList, indxForLists));
				}
				return sb.toString();
			} else {
				return getSX3HexData(jsnObj, modelFields, key, sx3ConfigPropertiesMap, hexValueMapPropertiesMap,
						csvMapObj, isList, indxForLists);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return SX3PropertiesConstants.NO_HEX_VALUE;
		}

	}

	@SuppressWarnings("unchecked")
	protected static String getSX3HexData(Object modelObject, String[] modelFields, String modelKey,
			Map<String, Integer> sx3ConfigPropertiesMap, Map<String, String> hexValueMapPropertiesMap,
			LinkedHashMap<CSVKeys, CSVValues> csvMapObj, boolean isList, int indxForLists)
			throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		for (String propertyName : modelFields) {
			Object invokedValue = invokeMethodByFieldName(propertyName, modelObject);
			if (invokedValue != null) {
				if (invokedValue instanceof LinkedHashMap<?, ?>) {
					if (SX3PropertiesConstants.UAC_SETTINGS_UAC_SETTING.equals(modelKey)) {
						String[] subFields = {};
						if ("CHANNEL_CONFIGURATION".equals(propertyName)) {
							subFields = UACSettingFieldConstants.CHANNEL_CONFIGURATION_FIELDS;
						} else if ("FEATURE_UNIT_CONTROLS".equals(propertyName)) {
							subFields = UACSettingFieldConstants.FEATURE_UNIT_CONTROLS_FIELDS;
						}
						LinkedHashMap<String, String> hashMap = ((LinkedHashMap<String, String>) invokedValue);
						StringBuilder sb = new StringBuilder();
						for (String key : subFields) {
							sb.append("Enable".equals(hashMap.get(key)) ? "1" : "0");
						}

						String bitMaskVal = propertyName.equals("CHANNEL_CONFIGURATION")
								? sb.append("0000").reverse().toString() : sb.reverse().toString();
						int byteSize = sx3ConfigPropertiesMap.get(propertyName);
						String hexval = bitmaskValToHexVal(bitMaskVal, byteSize);
						BytesStreamsAndHexFileUtil.setCSVData(modelKey, propertyName, bitMaskVal, "NA", byteSize,
								hexval, csvMapObj, isList, indxForLists);
						result.append(hexval);
					}
				} else if (invokedValue instanceof ArrayList<?>) {
					if (((ArrayList<?>) invokedValue).size() > 0) {
						Object invObj = ((ArrayList<?>) invokedValue).get(0);
						String[] subModelFields = SX3PropertiesConstants.SENSOR_CONFIG_FIELDS;
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
							int slNo = 0;
							while (iterator.hasNext()) {
								SensorConfig sensorConfig = iterator.next();
								for (String subModelField : subModelFields) {
									Object invokedValue1 = invokeMethodByFieldName(subModelField, sensorConfig);
									sensorConfigHexSb.append(BytesStreamsAndHexFileUtil.convertJsonValuesToHex(
											invokedValue1.toString().trim(), modelKey + "." + propertyName,
											subModelField, subModelConfigPropertiesMap, hexValueMapPropertiesMap,
											csvMapObj, true, slNo));
								}
								slNo++;
							}
							String oveAllResolutionRegisterSettings = sensorConfigHexSb.toString();
							result.append(oveAllResolutionRegisterSettings);
						}
					}
				} else {
					if ((modelObject instanceof CameraControl && propertyName.equals("CAMERA_CONTROLS_ENABLED_BITMASK"))
							|| (modelObject instanceof ProcessingUnitControl
									&& propertyName.equals("PROCESSING_UNIT_ENABLED_CONTROLS_BITMASK"))) {

						String bitmaskVal = invokedValue.toString().substring(2);
						String binaryVal = !bitmaskVal.isEmpty() ? bitmaskVal : SX3PropertiesConstants.NO_HEX_VALUE;
						int byteSize = sx3ConfigPropertiesMap.get(propertyName);
						String bitMaskHexval = bitmaskValToHexVal(binaryVal, byteSize);
						BytesStreamsAndHexFileUtil.setCSVData(modelKey, propertyName, binaryVal, "NA", byteSize,
								bitMaskHexval, csvMapObj, isList, indxForLists);
						return bitMaskHexval;
					} else if (modelObject instanceof ConfigTableGeneral
							|| modelObject instanceof ConfigTableOffSetTable) {
						if (invokedValue.equals(SX3PropertiesConstants.SIGNATURE)) {
							String hexVal = BytesStreamsAndHexFileUtil
									.convertByteToHexString((SX3PropertiesConstants.SIGNATURE.getBytes()), 1);
							BytesStreamsAndHexFileUtil.setCSVData(modelKey, propertyName, invokedValue, "NA", 1, hexVal,
									csvMapObj, isList, indxForLists);
							result.append(hexVal);
						} else {
							result.append(BytesStreamsAndHexFileUtil.convertJsonValuesToHex(invokedValue, modelKey,
									propertyName, sx3ConfigPropertiesMap, hexValueMapPropertiesMap, csvMapObj, false,
									0));
						}
					} else if (modelObject instanceof USBSettings
							&& Arrays.stream(SX3PropertiesConstants.DEVICE_SETTINGS_USB_SETTINGS_LENGTH_FIELDS)
									.parallel().anyMatch(propertyName::contains)) {
						int intVal = ((int) invokedValue) * 2;
						result.append(BytesStreamsAndHexFileUtil.convertJsonValuesToHex(intVal, modelKey, propertyName,
								sx3ConfigPropertiesMap, hexValueMapPropertiesMap, csvMapObj, false, 0));
						// result.append(String.format("%02d", intVal));
					} else {
						result.append(BytesStreamsAndHexFileUtil.convertJsonValuesToHex(invokedValue, modelKey,
								propertyName, sx3ConfigPropertiesMap, hexValueMapPropertiesMap, csvMapObj, isList,
								indxForLists));
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
			List<LinkedHashMap<String, LinkedHashMap<String, Object>>> listMapObject = (modelObject instanceof CameraControl)
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

	protected static List<Map<Integer, List<SensorConfig>>> getVideoSoureSensorConfigList() {
		List<Map<Integer, List<SensorConfig>>> videoConfigList = new ArrayList<>();
		try {
			// get the Sx3 object from the manager
			SX3Configuration sx3Obj = SX3Manager.getInstance().getSx3Configuration();
			List<VideoSourceConfig> videoSourceConfigList = sx3Obj.getVIDEO_SOURCE_CONFIG();
			if (videoSourceConfigList != null && !videoSourceConfigList.isEmpty()) {
				for (int j = 0; j < videoSourceConfigList.size(); j++) {
					Map<Integer, List<SensorConfig>> sensorConfigMap = new HashMap<>();
					if (videoSourceConfigList.get(j).getENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD()
							.equals("Disable")) {
						continue;
					}
					sensorConfigMap.put(videoSourceConfigList.get(j).getENDPOINT(),
							videoSourceConfigList.get(j).getVIDEO_SOURCE_CONFIG());
					videoConfigList.add(sensorConfigMap);
				}
			} else {
				BytesStreamsAndHexFileUtil.log("VIDEO_SOURCE_CONFIG object not created");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return videoConfigList;
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
			return SX3PropertiesConstants.HEX_PREFIX + BytesStreamsAndHexFileUtil.leftPad(hexOffSet, 4);
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
