package sx3configuration.tests;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxRobotInterface;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import sx3Configuration.mergertool.HexConversionErrors;
import sx3Configuration.mergertool.MergeFinalFirmwareArtifacts;
import sx3Configuration.model.EndpointSettings;
import sx3Configuration.model.ExtensionUnitControl;
import sx3Configuration.model.FormatAndResolutions;
import sx3Configuration.model.HDMISourceConfiguration;
import sx3Configuration.model.SX3Configuration;
import sx3Configuration.model.SensorConfig;
import sx3Configuration.model.VideoSourceConfig;
import sx3Configuration.programming.OSValidator;
import sx3Configuration.programming.SX3ConfigurationProgrammingUtility;
import sx3Configuration.programming.OSValidator.Level;
import sx3Configuration.tablemodel.FormatAndResolutionTableModel;
import sx3Configuration.ui.SX3Manager;

//Sorts by method name
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Scenario5_Validations extends TestFXBase{




	@Before
	public void setup() throws FileNotFoundException {

	}

	@Test
	public void test1_scenario_New_Export_Import_program() throws InterruptedException {

		createNewConfiguration();
		updateDeviceSettings();

		if (sx3Obj.getDEVICE_NAME().contains("Data")) {
			updateFifoMasterConfig();
			updateSlaveFifoSettings();
		} else {
			updateVideoSourceConfig();
			updateUvcUacSettings();
			updateFifoMasterConfig();
		}
		clickOn("Device Settings");
		saveConfiguration();sleep(1000);
		exportAction(); sleep(1000);
		loadConfiguration(); sleep(1000);
		clickOn("Utility");sleep(1000);
		clickOn("Program");sleep(2000);
		//if device connected then pop window setting like button should enable otherwise disable
		SX3ConfigurationProgrammingUtility programmingUtility = new SX3ConfigurationProgrammingUtility();
		List<String> deviceList = programmingUtility.getDeviceList();
		if(deviceList.size()>0) {
			clickOn("#btnProgram");
			sleep(500000);
		}else {
			System.out.println("No device connected");
		}
		HexConversionErrors result = MergeFinalFirmwareArtifacts.mergeAllFiles();
		File jarPath = SX3Manager.getInstance().getInstallLocation();
		if(result !=HexConversionErrors.BASE_IMAGE_FILE_MISSING) {
			clickOn("#btnProgram");
			System.out.println("Base Image file Missing");
		}
		else if(result !=HexConversionErrors.BIT_FILE_MISSING) {
			clickOn("#btnProgram");
			System.out.println("Bit File Missing");
		}
		else if(result !=HexConversionErrors.MERGE_SUCCESS) {
			clickOn("#btnProgram");
			System.out.println("Merge is not Success ");
		}
		else if(!new File(jarPath.getParentFile().getAbsolutePath() + "/cyfwprog/cyfwprog.exe").exists()) {
			clickOn("#btnProgram");
			System.out.println("Program Utility is Missing");
		}
        clickOn("#prgclose");sleep(2000);
		SaveAsProgramConfig();
		clickOn("#saveLog");

	}

	private void updateUvcUacSettings() {
		clickOn("UVC/UAC Settings");

		updateUvcUacEndPoint1Tab();

		updateUvcUacEndPoint2Tab();
	}

	private void updateUvcUacEndPoint1Tab() {
		String endPoint1TabName = "Endpoint 1 -" + sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT1_TYPE();
		clickOn(endPoint1TabName);
		clickOn("Endpoint Settings");
		if (sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT1_TYPE().equals("UVC")) {

			handle_combo_with_string("#endpointTransferType",
					sx3Obj.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getENDPOINT_TRANSFER_TYPE());
			updateUvcUacEndPointSettings("UVC", sx3Obj.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS());

			updateFormatResolutionTable();

			updateCameraControl();

			updateProcessingUnitControl();

			updateExtensionUnitControl();

		} else if (sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT1_TYPE().equals("UAC")) {

			updateUacSettings();
		}
	}

	private void updateUvcUacEndPoint2Tab() {
		if(sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT2_TYPE().isEmpty()) {
			return;
		}
		String endPoint2TabName = "Endpoint 2 -" + sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT2_TYPE();
		clickOn(endPoint2TabName);
		clickOn("Endpoint Settings");
		if (sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT2_TYPE().equals("UVC")) {

			handle_combo_with_string("#endpointTransferType",
					sx3Obj.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getENDPOINT_TRANSFER_TYPE());
			updateUvcUacEndPointSettings("UVC", sx3Obj.getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS());

			updateFormatResolutionTable();

			updateCameraControl();

			updateProcessingUnitControl();

			updateExtensionUnitControl();

		} else if (sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT2_TYPE().equals("UAC")) {

			updateUacSettings();
		}
	}

	private void updateExtensionUnitControl() {
		clickOn("Extension Unit Controls");

		ExtensionUnitControl extension_UNIT_CONTROL = sx3Obj.getUVC_SETTINGS().get(0).getEXTENSION_UNIT_CONTROL();
		handle_checkbox_with_string("#deviceReset", extension_UNIT_CONTROL.getDEVICE_RESET_VENDOR_COMMAND_ENABLED());
		handle_checkbox_with_string("#I2cRegisterRead", extension_UNIT_CONTROL.getI2C_READ_VENDOR_COMMAND_ENABLED());
		handle_checkbox_with_string("#I2CRegisterWrite", extension_UNIT_CONTROL.getI2C_WRITE_VENDOR_COMMAND_ENABLED());
		handle_checkbox_with_string("#firmWareUpdate",
				extension_UNIT_CONTROL.getFIRMWARE_VERSION_VENDOR_COMMAND_ENABLED());

	}

	private void updateCameraControl() {
		clickOn("Camera Control");

		List<LinkedHashMap<String, LinkedHashMap<String, Object>>> camera_CONTROLS = sx3Obj.getUVC_SETTINGS().get(0)
				.getCAMERA_CONTROL().getCAMERA_CONTROLS();

		int i = 0;
		for (LinkedHashMap<String, LinkedHashMap<String, Object>> linkedHashMap : camera_CONTROLS) {

			for (Map.Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
				LinkedHashMap<String, Object> map = entry1.getValue();
				System.out.println(entry1.getKey());
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					Long intVal = null;
					System.out.println("\t" + entry.getKey());
					if (entry.getValue() instanceof Double) {
						intVal = ((Double) entry.getValue()).longValue();
					}
					if (entry.getKey().contains("_ENABLE")) {
						handle_checkbox_with_string("#cameraControlEnable" + i, entry.getValue().toString());
						if (entry.getValue().toString().equals("Disable")) {
							break;
						}
					} else if (entry.getKey().contains("_MIN")) {
						update_text_field("#cameraControlMin" + i, Long.toString(intVal));
					} else if (entry.getKey().contains("_MAX")) {
						update_text_field("#cameraControlMax" + i, Long.toString(intVal));
					} else if (entry.getKey().contains("_STEP")) {
						update_text_field("#cameraControlStep" + i, Long.toString(intVal));
					} else if (entry.getKey().contains("_DEFAULT")) {
						update_text_field("#cameraControlDefault" + i, Long.toString(intVal));
					} else if (entry.getKey().contains("_LENGTH")) {
						update_text_field("#cameraControlLength" + i, Long.toString(intVal));
					} else if (entry.getKey().contains("REGISTER_ADDRESS")) {
						update_text_field("#cameraControlRegisterAddress" + i, entry.getValue().toString());
					}

				}

			}
			i++;

		}
	}

	private void updateProcessingUnitControl() {
		clickOn("Processing Unit Control");

		List<LinkedHashMap<String, LinkedHashMap<String, Object>>> camera_CONTROLS = sx3Obj.getUVC_SETTINGS().get(0)
				.getPROCESSING_UNIT_CONTROL().getPROCESSING_UNIT_CONTROLS();

		int i = 0;
		for (LinkedHashMap<String, LinkedHashMap<String, Object>> linkedHashMap : camera_CONTROLS) {

			for (Map.Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
				LinkedHashMap<String, Object> map = entry1.getValue();
				System.out.println(entry1.getKey());
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					Long intVal = null;
					System.out.println("\t" + entry.getKey());
					if (entry.getValue() instanceof Double) {
						intVal = ((Double) entry.getValue()).longValue();
					}
					if (entry.getKey().contains("_ENABLE")) {
						handle_checkbox_with_string("#processingControlEnable" + i, entry.getValue().toString());
						if (entry.getValue().toString().equals("Disable")) {
							break;
						}
					} else if (entry.getKey().contains("_MIN")) {
						update_text_field("#processingControlMin" + i, Long.toString(intVal));
					} else if (entry.getKey().contains("_MAX")) {
						update_text_field("#processingControlMax" + i, Long.toString(intVal));
					} else if (entry.getKey().contains("_STEP")) {
						update_text_field("#processingControlStep" + i, Long.toString(intVal));
					} else if (entry.getKey().contains("_DEFAULT")) {
						update_text_field("#processingControlDefault" + i, Long.toString(intVal));
					} else if (entry.getKey().contains("_LENGTH")) {
						update_text_field("#processingControlLength" + i, Long.toString(intVal));
					} else if (entry.getKey().contains("REGISTER_ADDRESS")) {
						update_text_field("#processingControlRegisterAddress" + i, entry.getValue().toString());
					}

				}

			}
			i++;

		}
	}

	private void updateFormatResolutionTable() {
		clickOn("Format And Resolution");
		List<FormatAndResolutions> format_RESOLUTIONS = sx3Obj.getUVC_SETTINGS().get(0).getFORMAT_RESOLUTION()
				.getFORMAT_RESOLUTIONS();

		TableView<FormatAndResolutionTableModel> tableView = lookupControl("#formatResolutionTable");

		int i = 0;
		for (FormatAndResolutions formatAndResolutions : format_RESOLUTIONS) {

			if (i > 0) {
				clickOn("#formatResolutionAddBtn");

			}

			user_selects_combo_item("#imageFormat" + i, formatAndResolutions.getIMAGE_FORMAT());
			//			handle_combo_with_string("#imageFormat" + i, formatAndResolutions.getIMAGE_FORMAT());
			clickOn("#hResolution" + i);
			String hresolution = Integer.toString(formatAndResolutions.getH_RESOLUTION());
			write(hresolution).push(KeyCode.TAB);
			clickOn("#vResolution" + i);
			String vresolution = Integer.toString(formatAndResolutions.getV_RESOLUTION());
			write(vresolution).push(KeyCode.TAB);
			handle_checkbox_with_string("#stillCaptured" + i, formatAndResolutions.getSTILL_CAPTURE());
			handle_checkbox_with_string("#supportedInFS" + i, formatAndResolutions.getSUPPORTED_IN_FS());
			handle_checkbox_with_string("#supportedInHS" + i, formatAndResolutions.getSUPPORTED_IN_HS());
			handle_checkbox_with_string("#supportedInSS" + i, formatAndResolutions.getSUPPORTED_IN_SS());
			update_text_field("#frameRateInFS" + i, Integer.toString(formatAndResolutions.getFRAME_RATE_IN_FS()));
			update_text_field("#frameRateInHS" + i, Integer.toString(formatAndResolutions.getFRAME_RATE_IN_HS()));
			update_text_field("#frameRateInSS" + i, Integer.toString(formatAndResolutions.getFRAME_RATE_IN_SS()));

			clickOn("#formatResolutionSensorConfig" + i);
			List<SensorConfig> resolution_REGISTER_SETTING = formatAndResolutions.getRESOLUTION_REGISTER_SETTING();
			fillSensorConfigData(resolution_REGISTER_SETTING);
			clickOn("#formatResolutionSaveBtn");

			i++;
		}
	}

	private void updateUacSettings() {
		updateUvcUacEndPointSettings("UAC",sx3Obj.getUAC_SETTINGS().get(0).getEndpointSettings());

		clickOn("UAC Setting");
		handle_combo_with_string("#audioFormatValue",
				sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getAUDIO_FORMAT());
		handle_combo_with_string("#bitResolutionValue",
				sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getBIT_RESOLUTION());

		handle_checkbox_with_string("#topCheckBox",sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getCHANNEL_CONFIGURATION().get("D11: Top (T)"));
		handle_checkbox_with_string("#sideRightCheckBox",sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getCHANNEL_CONFIGURATION().get("D10: Side Right (SR)"));
		handle_checkbox_with_string("#sideLeftCheckBox",sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getCHANNEL_CONFIGURATION().get("D9: Side Left (SL)"));
		handle_checkbox_with_string("#surroundCheckBox",sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getCHANNEL_CONFIGURATION().get("D8: Surround (S)"));
		handle_checkbox_with_string("#rightOfCenterCheckBox",sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getCHANNEL_CONFIGURATION().get("D7: Right of Center (RC)"));
		handle_checkbox_with_string("#leftOfCenterCheckBox",sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getCHANNEL_CONFIGURATION().get("D6: Left of Center (LC)"));
		handle_checkbox_with_string("#rightSurroundCheckBox",sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getCHANNEL_CONFIGURATION().get("D5: Right Surround (RS)"));
		handle_checkbox_with_string("#leftSurroundCheckBox",sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getCHANNEL_CONFIGURATION().get("D4: Left Surround (LS)"));
		handle_checkbox_with_string("#lowFrequencyEnhancementCheckBox",sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getCHANNEL_CONFIGURATION().get("D3: Low Frequency Enhancement (LFE)"));
		handle_checkbox_with_string("#channelConfigurationCenterCheckBox",sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getCHANNEL_CONFIGURATION().get("D2: Center Front (C)"));
		handle_checkbox_with_string("#channelConfigurationRightCheckBox",sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getCHANNEL_CONFIGURATION().get("D1: Right Front (R)"));

		handle_checkbox_with_string("#FeatureUnitD0Mute",
				sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getFEATURE_UNIT_CONTROLS().get("D0: Mute"));
		handle_checkbox_with_string("#FeatureUnitD1Volume",
				sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getFEATURE_UNIT_CONTROLS().get("D1: Volume"));

		handle_combo_with_integer("#samplingFrequency1Value",
				sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getSAMPLING_FREQUENCY_1());
		clickOn("#samplingFrequencySensorConfig");
		if (((SX3Configuration) sx3Obj).getUAC_SETTINGS().get(0).getUAC_SETTING()
				.getSAMPLING_FREQUENCY_1_SENSOR_CONFIG() != null) {
			System.out.println("aaa");

			List<SensorConfig> getVIDEO_SOURCE_CONFIGURATION = ((SX3Configuration) sx3Obj).getUAC_SETTINGS().get(0)
					.getUAC_SETTING().getSAMPLING_FREQUENCY_1_SENSOR_CONFIG();
			System.out.println("getVIDEO_SOURCE_CONFIGURATION" + getVIDEO_SOURCE_CONFIGURATION.size());

			for (SensorConfig name : getVIDEO_SOURCE_CONFIGURATION) {
				System.out.println("1==>" + name.getREGISTER_ADDRESS());
				System.out.println("2==>" + name.getREGISTER_VALUE());
				System.out.println("3==>" + name.getSLAVE_ADDRESS());
				clickOn("#registerTextArea");
				write("{" + name.getREGISTER_ADDRESS() + ",").push(KeyCode.TAB);
				write(name.getREGISTER_VALUE() + ",").push(KeyCode.TAB);
				write(name.getSLAVE_ADDRESS() + "},").push(KeyCode.TAB);
			}

			clickOn("#ConvertToJson");

		}

		type(KeyCode.ENTER);

		if (sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getSAMPLING_FREQUENCY_2() > 0) {
			handle_combo_with_integer("#samplingFrequency2Value",
					sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getSAMPLING_FREQUENCY_2());

			clickOn("#samplingFrequency2SensorConfig");
			if (((SX3Configuration) sx3Obj).getUAC_SETTINGS().get(0).getUAC_SETTING()
					.getSAMPLING_FREQUENCY_2_SENSOR_CONFIG() != null) {
				System.out.println("aaa");

				List<SensorConfig> getVIDEO_SOURCE_CONFIGURATION = ((SX3Configuration) sx3Obj).getUAC_SETTINGS().get(0)
						.getUAC_SETTING().getSAMPLING_FREQUENCY_2_SENSOR_CONFIG();
				System.out.println("getVIDEO_SOURCE_CONFIGURATION" + getVIDEO_SOURCE_CONFIGURATION.size());

				for (SensorConfig name : getVIDEO_SOURCE_CONFIGURATION) {
					System.out.println("1==>" + name.getREGISTER_ADDRESS());
					System.out.println("2==>" + name.getREGISTER_VALUE());
					System.out.println("3==>" + name.getSLAVE_ADDRESS());
					clickOn("#registerTextArea");
					write("{" + name.getREGISTER_ADDRESS() + ",").push(KeyCode.TAB);
					write(name.getREGISTER_VALUE() + ",").push(KeyCode.TAB);
					write(name.getSLAVE_ADDRESS() + "},").push(KeyCode.TAB);
				}

				clickOn("#ConvertToJson");

			}
			type(KeyCode.ENTER);
		}

		if (sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getSAMPLING_FREQUENCY_3() > 0) {
			handle_combo_with_integer("#samplingFrequency3Value",
					sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getSAMPLING_FREQUENCY_3());
			clickOn("samplingFrequency3SensorConfig");
			if (((SX3Configuration) sx3Obj).getUAC_SETTINGS().get(0).getUAC_SETTING()
					.getSAMPLING_FREQUENCY_3_SENSOR_CONFIG() != null) {
				System.out.println("aaa");

				List<SensorConfig> getVIDEO_SOURCE_CONFIGURATION = ((SX3Configuration) sx3Obj).getUAC_SETTINGS().get(0)
						.getUAC_SETTING().getSAMPLING_FREQUENCY_3_SENSOR_CONFIG();
				System.out.println("getVIDEO_SOURCE_CONFIGURATION" + getVIDEO_SOURCE_CONFIGURATION.size());

				for (SensorConfig name : getVIDEO_SOURCE_CONFIGURATION) {
					System.out.println("1==>" + name.getREGISTER_ADDRESS());
					System.out.println("2==>" + name.getREGISTER_VALUE());
					System.out.println("3==>" + name.getSLAVE_ADDRESS());
					clickOn("#registerTextArea");
					write("{" + name.getREGISTER_ADDRESS() + ",").push(KeyCode.TAB);
					write(name.getREGISTER_VALUE() + ",").push(KeyCode.TAB);
					write(name.getSLAVE_ADDRESS() + "},").push(KeyCode.TAB);
				}

			}
			type(KeyCode.ENTER);
		}

		if (sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getSAMPLING_FREQUENCY_4() > 0) {
			handle_combo_with_integer("#samplingFrequency4Value",
					sx3Obj.getUAC_SETTINGS().get(0).getUAC_SETTING().getSAMPLING_FREQUENCY_4());
			clickOn("samplingFrequency4SensorConfig");
			if (((SX3Configuration) sx3Obj).getUAC_SETTINGS().get(0).getUAC_SETTING()
					.getSAMPLING_FREQUENCY_3_SENSOR_CONFIG() != null) {
				System.out.println("aaa");

				List<SensorConfig> getVIDEO_SOURCE_CONFIGURATION = ((SX3Configuration) sx3Obj).getUAC_SETTINGS().get(0)
						.getUAC_SETTING().getSAMPLING_FREQUENCY_3_SENSOR_CONFIG();
				System.out.println("getVIDEO_SOURCE_CONFIGURATION" + getVIDEO_SOURCE_CONFIGURATION.size());

				for (SensorConfig name : getVIDEO_SOURCE_CONFIGURATION) {
					System.out.println("1==>" + name.getREGISTER_ADDRESS());
					System.out.println("2==>" + name.getREGISTER_VALUE());
					System.out.println("3==>" + name.getSLAVE_ADDRESS());
					clickOn("#registerTextArea");
					write("{" + name.getREGISTER_ADDRESS() + ",").push(KeyCode.TAB);
					write(name.getREGISTER_VALUE() + ",").push(KeyCode.TAB);
					write(name.getSLAVE_ADDRESS() + "},").push(KeyCode.TAB);
				}

			}
			type(KeyCode.ENTER);
		}
	}

	private void updateUvcUacEndPointSettings(String tabType, EndpointSettings endpointSettings) {
		update_text_field("#end"+tabType+"BurstLengthValue", Integer.toString(endpointSettings.getBURST_LENGTH()));
		update_text_field("#end"+tabType+"BufferSizeValue", Integer.toString(endpointSettings.getBUFFER_SIZE()));
		update_text_field("#end"+tabType+"BufferCountValue", Integer.toString(endpointSettings.getBUFFER_COUNT()));
	}

	private void updateVideoSourceConfig() {
		clickOn("Video/Audio Source Config");

		String endPoint1TabName = "Endpoint 1 -" + sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT1_TYPE();
		clickOn(endPoint1TabName);
		VideoSourceConfig videoSourceConfig1 = sx3Obj.getVIDEO_SOURCE_CONFIG().get(0);
		handle_checkbox_with_string("#enableVideoSourceConfig",
				videoSourceConfig1.getENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD());
		if (videoSourceConfig1.getENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD().equals("Enable")) {
			handle_combo_with_string("#videoSorceTypeValue", videoSourceConfig1.getVIDEO_SOURCE_TYPE());
			handle_combo_with_string("#videoSorceSubTypeValue", videoSourceConfig1.getVIDEO_SOURCE_SUBTYPE());

			clickOn("#videoSourceSensorConfig");


			List<SensorConfig> getVIDEO_SOURCE_CONFIGURATION = videoSourceConfig1.getVIDEO_SOURCE_CONFIGURATION();
			fillSensorConfigData(getVIDEO_SOURCE_CONFIGURATION);

			if(videoSourceConfig1.getVIDEO_SOURCE_SUBTYPE().equals("HDMI RX - Generic")) {
				LinkedList<HDMISourceConfiguration> hdmi_SOURCE_CONFIGURATION = videoSourceConfig1.getHDMI_SOURCE_CONFIGURATION();
				int i = 0;
				for (HDMISourceConfiguration hdmiSourceConfiguration : hdmi_SOURCE_CONFIGURATION) {
					if(i > 0) {
						clickOn("#hdmiConfigAddBtn");
					}
					update_text_field("#hdmiEvent"+i, hdmiSourceConfiguration.getEVENT_NAME());
					update_text_field("#hdmiRegisterAddress"+i,hdmiSourceConfiguration.getHDMI_SOURCE_REGISTER_ADDRESS());
					update_text_field("#hdmiMaskValue"+i, hdmiSourceConfiguration.getMASK_PATTERN());
					update_text_field("#hdmiCompareValue"+i, hdmiSourceConfiguration.getCOMPARE_VALUE());
					clickOn("#hdmiSensorConfigButton"+i);

					String hdmiSensorConfigData = new String();
					clickOn("#registerTextArea");	
					List<SensorConfig> resolution_REGISTER_SETTING = hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING();
					for (SensorConfig hdmiSensorConfig : resolution_REGISTER_SETTING) {
						System.out.println("1==>" + hdmiSensorConfig.getREGISTER_ADDRESS());
						System.out.println("2==>" + hdmiSensorConfig.getREGISTER_VALUE());
						System.out.println("3==>" + hdmiSensorConfig.getSLAVE_ADDRESS());

						hdmiSensorConfigData += "{" + hdmiSensorConfig.getREGISTER_ADDRESS() + ",\t";
						hdmiSensorConfigData += hdmiSensorConfig.getREGISTER_VALUE() + ",\t";
						hdmiSensorConfigData += hdmiSensorConfig.getSLAVE_ADDRESS() + "},\n";

					}

					copyAndPaste("#registerTextArea", hdmiSensorConfigData);

					clickOn("#ConvertToJson");
					type(KeyCode.ENTER);

					i++;
				}
			}
		}

		update_text_field("#videoSourceI2CSlaveAddressValue", videoSourceConfig1.getI2C_SLAVE_ADDRESS());
		update_text_field("#videoSourceI2CSlaveDataSize",
				Integer.toString(videoSourceConfig1.getI2C_SLAVE_DATA_SIZE()));
		update_text_field("#videoSourceI2CSlaveRegisterSize",
				Integer.toString(videoSourceConfig1.getI2C_SLAVE_REGISTER_SIZE()));

		// HDMI

		if (sx3Obj.getVIDEO_SOURCE_CONFIG().size() > 1) {

			String endPoint2TabName = "Endpoint 2 -"
					+ sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT2_TYPE();
			clickOn(endPoint2TabName);
			VideoSourceConfig videoSourceConfig2 = sx3Obj.getVIDEO_SOURCE_CONFIG().get(1);
			handle_checkbox_with_string("#enableVideoSourceConfig",
					videoSourceConfig2.getENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD());
			if (videoSourceConfig2.getENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD().equals("Enable")) {
				handle_combo_with_string("#videoSorceTypeValue", videoSourceConfig2.getVIDEO_SOURCE_TYPE());
				handle_combo_with_string("#videoSorceSubTypeValue", videoSourceConfig2.getVIDEO_SOURCE_SUBTYPE());

				clickOn("#videoSourceSensorConfig");

				List<SensorConfig> getVIDEO_SOURCE_CONFIGURATION = videoSourceConfig2.getVIDEO_SOURCE_CONFIGURATION();
				System.out.println("getVIDEO_SOURCE_CONFIGURATION" + getVIDEO_SOURCE_CONFIGURATION.size());

				for (SensorConfig name : getVIDEO_SOURCE_CONFIGURATION) {
					System.out.println("1==>" + name.getREGISTER_ADDRESS());
					System.out.println("2==>" + name.getREGISTER_VALUE());
					System.out.println("3==>" + name.getSLAVE_ADDRESS());
					clickOn("#registerTextArea");
					write("{" + name.getREGISTER_ADDRESS() + ",").push(KeyCode.TAB);
					write(name.getREGISTER_VALUE() + ",").push(KeyCode.TAB);
					write(name.getSLAVE_ADDRESS() + "},").push(KeyCode.TAB);

				}

				clickOn("#ConvertToJson");
				type(KeyCode.ENTER);
			}

			update_text_field("#videoSourceI2CSlaveAddressValue", videoSourceConfig2.getI2C_SLAVE_ADDRESS());
			update_text_field("#videoSourceI2CSlaveDataSize",
					Integer.toString(videoSourceConfig2.getI2C_SLAVE_DATA_SIZE()));
			update_text_field("#videoSourceI2CSlaveRegisterSize",
					Integer.toString(videoSourceConfig2.getI2C_SLAVE_REGISTER_SIZE()));
		}
	}

	private void fillSensorConfigData(List<SensorConfig> getVIDEO_SOURCE_CONFIGURATION) {
		String videoSourceSensorConfigData = new String();
		clickOn("#registerTextArea");
		System.out.println("getVIDEO_SOURCE_CONFIGURATION" + getVIDEO_SOURCE_CONFIGURATION.size());
		for (SensorConfig name : getVIDEO_SOURCE_CONFIGURATION) {
			System.out.println("1==>" + name.getREGISTER_ADDRESS());
			System.out.println("2==>" + name.getREGISTER_VALUE());
			System.out.println("3==>" + name.getSLAVE_ADDRESS());

			videoSourceSensorConfigData += "{" + name.getREGISTER_ADDRESS() + ",\t";
			videoSourceSensorConfigData += name.getREGISTER_VALUE() + ",\t";
			videoSourceSensorConfigData += name.getSLAVE_ADDRESS() + "},\n";
		}

		copyAndPaste("#registerTextArea", videoSourceSensorConfigData);

		clickOn("#ConvertToJson");
		type(KeyCode.ENTER);
	}

	private void updateSlaveFifoSettings() {
		// Slave FIFO Settings
		clickOn("Slave FIFO Settings");
		String endPoint1TabName = "Endpoint 1 -" + sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT1_TYPE();
		clickOn(endPoint1TabName);
		String burst = Integer.toString(sx3Obj.getSLAVE_FIFO_SETTINGS().get(0).getBURST_LENGTH());
		update_text_field("#slaveFifoSettingsBrustLength", burst);
		String buffer = Integer.toString(sx3Obj.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_SIZE());
		update_text_field("#slaveFifoSettingsBufferSize", buffer);
		String count = Integer.toString(sx3Obj.getSLAVE_FIFO_SETTINGS().get(0).getBUFFER_COUNT());
		write(count).push(KeyCode.TAB);

		if(sx3Obj.getSLAVE_FIFO_SETTINGS().size() > 1) {
			String endPoint2TabName = "Endpoint 2 -" + sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT2_TYPE();
			clickOn(endPoint2TabName);
			String burst2 = Integer.toString(sx3Obj.getSLAVE_FIFO_SETTINGS().get(1).getBURST_LENGTH());
			update_text_field("#slaveFifoSettingsBrustLength", burst2);
			String buffer2 = Integer.toString(sx3Obj.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_SIZE());
			update_text_field("#slaveFifoSettingsBufferSize", buffer2);
			String count2 = Integer.toString(sx3Obj.getSLAVE_FIFO_SETTINGS().get(1).getBUFFER_COUNT());
			write(count2).push(KeyCode.TAB);
		}
	}

	private void updateFifoMasterConfig() {
		clickOn("FIFO Master Config");

		handle_checkbox_with_string("#enableFPGA",
				sx3Obj.getFIFO_MASTER_CONFIG().getFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN());

		if (sx3Obj.getFIFO_MASTER_CONFIG().getFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN().equals("Enable")) {
			handle_combo_with_string("#fpgaFamily", sx3Obj.getFIFO_MASTER_CONFIG().getFPGA_FAMILY());sleep(5000);

			if (!sx3Obj.getFIFO_MASTER_CONFIG().getBIT_FILE_PATH().isEmpty()) {
				((TextField) lookupControl("#chooseBitFile"))
				.setText(sx3Obj.getFIFO_MASTER_CONFIG().getBIT_FILE_PATH());sleep(9000);
				File loadedConfigurationPath = new File(PREDEFINED_CONFIGURATION_PATH);
				List<String> textFiles = new ArrayList<String>();
				File dir = new File(loadedConfigurationPath.getParent());
				for (File file : dir.listFiles()) {
					if (file.getName().endsWith((".bit"))) {
						textFiles.add(file.getName());

					}
				}
				String bitFileName=textFiles.get(0);
				System.out.println(bitFileName);
				String detinationPastPath = new String();
				File source;
                if(OPERATING_SYSTEM.equalsIgnoreCase("WINDOWS")) {
				 source=new File(loadedConfigurationPath.getParent()+"\\"+bitFileName);
                }else {
    			 source=new File(loadedConfigurationPath.getParent()+"/"+bitFileName);
                }
				System.out.println("source path===>"+source);
				detinationPastPath = "toolgenerated_"
						+ loadedConfigurationPath.getName().substring(0, loadedConfigurationPath.getName().lastIndexOf("."));

				File dest=new File(loadedConfigurationPath.getParent()+"/"+detinationPastPath+"/"+bitFileName);
				System.out.println("dest path==>"+dest);
				try {
					Files.copy(source.toPath(), dest.toPath());
					System.out.println("Success");
				} catch (IOException e) {
					System.out.println("Not copied");
					e.printStackTrace();
				}

				System.out.println("bit file size"+String.valueOf((sx3Obj.getFIFO_MASTER_CONFIG().getBIT_FILE_SIZE())));
				((TextField) lookupControl("#bitFileSize")).setText(String.valueOf((sx3Obj.getFIFO_MASTER_CONFIG().getBIT_FILE_SIZE()))); sleep(7000);
				//tamil ends
			}
		}

		update_text_field("#i2cSlaveAddress", sx3Obj.getFIFO_MASTER_CONFIG().getI2C_SLAVE_ADDRESS());
	}

	private void updateDeviceSettings() {
		updateUSBSettings();
		updateFifoSettings();
		updateGPIOSettings();
		updateAuxillaryInterface();
		updateDebugLevel();
		updateEndPoints();
	}

	private void updateEndPoints() {
		handle_combo_with_string("#Endpoint_1", sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT1_TYPE());
		handle_combo_with_string("#Endpoint_2", sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getENDPOINT2_TYPE());
		handle_combo_with_string("#uvcVersion", sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getUVC_VERSION());
		handle_combo_with_string("#uvcHeaderAddition",
				sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getUVC_HEADER_ADDITION());
	}

	private void updateDebugLevel() {
		handle_checkbox_with_string("#enableDebugLevel",
				sx3Obj.getDEVICE_SETTINGS().getDEBUG_LEVEL().getDEBUG_ENABLE());
		if (sx3Obj.getDEVICE_SETTINGS().getDEBUG_LEVEL().getDEBUG_ENABLE().equals("Enable")) {
			handle_combo_with_integer("#debugValue", sx3Obj.getDEVICE_SETTINGS().getDEBUG_LEVEL().getDEBUG_LEVEL());
		}
	}

	private void updateAuxillaryInterface() {
		handle_checkbox_with_string("#deviceSttingFirmWare",
				sx3Obj.getDEVICE_SETTINGS().getAUXILLIARY_INTERFACE().getFIRMWARE_UPDATE_HID());
		// handle_combo_with_integer("#deviceSttingI2CFrequency",sx3Obj.getDEVICE_SETTINGS().getAUXILLIARY_INTERFACE().getI2C_FREQUENCY());
	}

	private void updateGPIOSettings() {
		handle_combo_with_string("#gpio1", sx3Obj.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_0_SETTING());
		handle_combo_with_string("#gpio2", sx3Obj.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_1_SETTING());
		handle_combo_with_string("#gpio3", sx3Obj.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_2_SETTING());
		handle_combo_with_string("#gpio4", sx3Obj.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_3_SETTING());
		handle_combo_with_string("#gpio5", sx3Obj.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_4_SETTING());
		handle_combo_with_string("#gpio6", sx3Obj.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_5_SETTING());
		handle_combo_with_string("#gpio7", sx3Obj.getDEVICE_SETTINGS().getGPIOS_SETTINGS().getGPIO_6_SETTING());
	}

	private void updateFifoSettings() {
		handle_combo_with_string("#interFaceType",
				sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getFIFO_INTERFACE_TYPE());
		handle_combo_with_integer("#fifoClockFrequency", sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getFIFO_CLOCK());
		handle_combo_with_integer("#fifoBusWidth", sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getFIFO_BUS_WIDTH());
	}

	private void updateUSBSettings() {
		update_text_field("#vendorId", sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getVENDOR_ID());
		update_text_field("#productId", sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getPRODUCT_ID());
		update_text_field("#manufacture", sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getMANUFACTURER_STRING());
		update_text_field("#productString", sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getPRODUCT_STRING());

		handle_checkbox_with_integer("#autoGenerateSerialNumber",
				sx3Obj.getDEVICE_SETTINGS().getDEBUG_LEVEL().getAUTO_GENERATE_SERIAL_NUMBER());
		handle_combo_with_integer("#serialNumberIncrement",
				sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getAUTO_INCREMENT_SERIAL_NUMBER());

		update_text_field("#serialNumber", sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getSERIALNUMBER_STRING());
		handle_checkbox_with_string("#enableRemoteWakeup",
				sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getREMOTE_WAKEUP_ENABLE());
		handle_combo_with_string("#powerConfiguration",
				sx3Obj.getDEVICE_SETTINGS().getUSB_SETTINGS().getPOWER_CONFIGURATION());
	}

	private void createNewConfiguration() {
		clickOn("File");

		sleep(1000);
		FxRobotInterface clickOn = clickOn("New Configuration");
		clickOn(".combo-box-base");
		write(sx3Obj.getDEVICE_NAME()).clickOn(sx3Obj.getDEVICE_NAME()).push(KeyCode.TAB);sleep(1000);
		write(newConfigurationFileName).push(KeyCode.TAB);
		write(newConfigurationFilePath).push(KeyCode.ENTER);
		clickOn("Ok");
	}

	/*private void write(int buffer_SIZE) {
		// TODO Auto-generated method stub

	}*/


	private <T extends Node> T lookupControl(String controlId) {
		T actualControl = lookup(controlId).query();
		assertNotNull("Could not find a control by id = " + controlId, actualControl);

		return actualControl;
	}

	private void handle_combo_with_integer(String id, int value) {
		clickOn(id);
		String comboValue = Integer.toString(value);
		write(comboValue).clickOn(comboValue).push(KeyCode.TAB);
	}

	private void handle_combo_with_string(String id, String value) {

		if (value != null && !value.isEmpty()) {
			clickOn(id).write(value.charAt(0)).clickOn(value).push(KeyCode.TAB);
		}
		sleep(1000);
	}

	private void handle_checkbox_with_integer(String id, int value) {
		if (value == 1) {
			clickOn(id);
		}
	}

	private void handle_checkbox_with_string(String id, String value) {
		if (value != null && value.equals("Enable")) {
			clickOn(id);
		}
	}

	void user_selects_combo_item(String comboBoxId, String itemToSelect) {
		ComboBox<String> actualComboBox = lookupControl(comboBoxId);
		Platform.runLater(() -> {
			actualComboBox.setValue(itemToSelect.toString());
		});

	}


	private void update_text_field(String id, String value) {

		clickOn(id);

		OSValidator osValidator = new OSValidator();
		Level os = osValidator.getOS();
		System.out.println(os);
		if(os.equals(OSValidator.Level.MAC)) {
			push(KeyCode.COMMAND, KeyCode.A);
		} else {
			push(KeyCode.CONTROL, KeyCode.A);
		}

		if (value == null || value.isEmpty()) {
			return;
		}
		if (value.startsWith("0x")) {
			write(value.substring(2)).push(KeyCode.TAB);
		} else {
			write(value).push(KeyCode.TAB);
		}
		sleep(1000);
	}

	private void saveConfiguration() {
		clickOn("File");
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		clickOn("Save Configuration");
	}
	private void loadConfiguration() {
		clickOn("#openConfig");sleep(1000);
		clickOn(LOADCONFIG_SELECTION); sleep(1000);
		if(PREDEFINED_IMPORT_FROM_TYPE.equalsIgnoreCase(".json")){
			System.out.println("Iam json");
			if(OPERATING_SYSTEM.equalsIgnoreCase("WINDOWS")) {
			((TextField) lookupControl("#sx3ConfigurationToImport"))
			.setText(newConfigurationFilePath + "\\" + newConfigurationFileName+"\\"+newConfigurationFileName+".json"); sleep(2000);
			//.setText(PREDEFINED_CONFIGURATION_PATH);
			System.out.println("load json file name===>"+newConfigurationFilePath + "\\" + newConfigurationFileName+"\\"+newConfigurationFileName+".json");
			}else
			{
				((TextField) lookupControl("#sx3ConfigurationToImport"))
				.setText(newConfigurationFilePath + "/" + newConfigurationFileName+"/"+newConfigurationFileName+".json"); sleep(2000);
				//.setText(PREDEFINED_CONFIGURATION_PATH);
				System.out.println("load json file name===>"+newConfigurationFilePath + "/" + newConfigurationFileName+"/"+newConfigurationFileName+".json");

			}
			}
		else if(PREDEFINED_IMPORT_FROM_TYPE.equalsIgnoreCase(".zip")){
			System.out.println("Iam zip");
			File loadedConfigurationPath = new File(PREDEFINED_CONFIGURATION_PATH);
			if(OPERATING_SYSTEM.equalsIgnoreCase("WINDOWS")) {
			((TextField) lookupControl("#sx3ConfigurationToImport"))
			.setText(newConfigurationFilePath + "\\" + "toolgenerated_"+loadedConfigurationPath.getName().substring(0, loadedConfigurationPath.getName().lastIndexOf("."))+".zip"); sleep(2000);
			System.out.println("load zip file name===>"+newConfigurationFilePath + "\\" + loadedConfigurationPath.getName().substring(0, loadedConfigurationPath.getName().lastIndexOf("."))+".zip");
			}else {
				((TextField) lookupControl("#sx3ConfigurationToImport"))
				.setText(newConfigurationFilePath + "/" + "toolgenerated_"+loadedConfigurationPath.getName().substring(0, loadedConfigurationPath.getName().lastIndexOf("."))+".zip"); sleep(2000);
				
			}
			((TextField) lookupControl("#jsonFilePath"))
			.setText(PREDEFINED_IMPORT_TO);sleep(2000);
			System.out.println("PREDEFINED_IMPORT_TO===>"+PREDEFINED_IMPORT_TO);
		}
		clickOn("#loadConfigurationOkBtn");
	}
	private void exportAction() {
		clickOn("#exportConfiguration");sleep(1000);
		((TextField) lookupControl("#exportPath"))
		.setText(newConfigurationFilePath);
		System.out.println("clicking Export Path Saving===>"+newConfigurationFilePath);
		clickOn("#exportConfigurationOkBtn");sleep(1000);
	}
	
	private void SaveAsProgramConfig() {
		clickOn("File");
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		clickOn("#saveAsConfigFromMenu");
		File loadedConfigurationPath = new File(PREDEFINED_CONFIGURATION_PATH);
		String saveProgramConfig="Imported_Program_File_"
				+ loadedConfigurationPath.getName().substring(0, loadedConfigurationPath.getName().lastIndexOf("."));		
		write(saveProgramConfig).push(KeyCode.TAB);
		String savePath=loadedConfigurationPath.getParent();
		((TextField) lookupControl("#txtFilePath"))
		.setText(savePath);
		clickOn("Ok");
		File file = new File( saveProgramConfig+ "/" + savePath);

	}


}