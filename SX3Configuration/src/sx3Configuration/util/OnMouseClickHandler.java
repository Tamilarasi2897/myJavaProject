package sx3Configuration.util;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.web.WebView;
import sx3Configuration.ui.SX3Manager;

public class OnMouseClickHandler implements EventHandler<Event> {

	private SX3ConfiguartionHelp sx3ConfigurationHelp;
	private WebView helpContent;

	public OnMouseClickHandler() {
		this.sx3ConfigurationHelp = SX3Manager.getInstance().getSx3ConfigurationHelp();
		this.helpContent = SX3Manager.getInstance().getHelpView();
	}

	@Override
	public void handle(Event event) {
		String textField = ((Control) event.getSource()).getId();
		if(textField.contains("imageFormat")) {
			textField = "imageFormat";
		}else if(textField.contains("hResolution")) {
			textField = "hResolution";
		}else if(textField.contains("vResolution")) {
			textField = "vResolution";
		}else if(textField.contains("stillCaptured")) {
			textField = "stillCaptured";
		}else if(textField.contains("supportedInFS")) {
			textField = "supportedInFS";
		}else if(textField.contains("supportedInHS")) {
			textField = "supportedInHS";
		}else if(textField.contains("supportedInSS")) {
			textField = "supportedInSS";
		}else if(textField.contains("frameRateInFS")) {
			textField = "frameRateInFS";
		}else if(textField.contains("frameRateInHS")) {
			textField = "frameRateInHS";
		}else if(textField.contains("frameRateInSS")) {
			textField = "frameRateInSS";
		}else if(textField.contains("BurstLengthValue")) {
			textField = "endpointBrustLength";
		}else if(textField.contains("BufferSizeValue")) {
			textField = "endPointBufferSize";
		}else if(textField.contains("BufferCountValue")) {
			textField = "endPointBufferCount";
		}

		switch (textField) {
		case "slaveFifoSettingsBrustLength":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getSLAVE_FIFO_SETTINGS().getBURST_LENGTH());
			}
			break;
		case "slaveFifoSettingsBufferSize":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getSLAVE_FIFO_SETTINGS().getBUFFER_SIZE());
			}
			break;

		case "slaveFifoSettingsBufferCount":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getSLAVE_FIFO_SETTINGS().getBUFFER_COUNT());

			}
			break;

		case "endpointTransferType":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getENDPOINT_SETTINGS().getENDPOINT_TRANSFER_TYPE());

			}
			break;
			
		case "endpointBrustLength":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getENDPOINT_SETTINGS().getBURST_LENGTH());

			}
			break;

		case "endPointBufferSize":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getENDPOINT_SETTINGS().getBUFFER_SIZE());

			}
			break;

		case "endPointBufferCount":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getENDPOINT_SETTINGS().getBUFFER_COUNT());

			}
			break;

		case "endPointBufferSpace":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getSLAVE_FIFO_SETTINGS().getBUFFER_SPACE());

			}
			break;
			
		case "enableVideoSourceConfig":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				helpContent.getEngine()
						.loadContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD());
			}
			break;

		case "videoSorceTypeValue":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				helpContent.getEngine()
						.loadContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getVIDEO_SOURCE_TYPE());
			}
			break;
			
		case "videoSorceSubTypeValue":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				helpContent.getEngine()
						.loadContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getVIDEO_SOURCE_SUB_TYPE());
			}
			break;

		case "VideoSourceConfigFile":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				helpContent.getEngine()
						.loadContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getVIDEO_SOURCE_CONFIG_FILE_PATH());

			}
			break;
		case "videoSourceI2CSlaveAddressValue":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				helpContent.getEngine()
						.loadContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getI2C_SLAVE_ADDRESS());

			}
			break;

		case "videoSourceI2CSlaveDataSize":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				helpContent.getEngine()
						.loadContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getI2C_SLAVE_DATA_SIZE());

			}
			break;

		case "videoSourceI2CSlaveRegisterSize":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				helpContent.getEngine()
						.loadContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getI2C_SLAVE_REGISTER_SIZE());

			}
			break;

		case "videoSourceI2CFrequency":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getVIDEO_SOURCE_CONFIG().getI2C_FREQUENCY());

			}
			break;

		case "colorPrimaries":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				helpContent.getEngine()
						.loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCOLOR_MATCHING().getCOLOR_PRIMARIES());

			}
			break;

		case "transferCharacteristics":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getCOLOR_MATCHING().getTRANSFER_CHARACTERISTICS());

			}
			break;

		case "matrixCoefficients":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getCOLOR_MATCHING().getMATRIX_COEFFICIENTS());

			}
			break;

		case "deviceReset":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUVC_SETTINGS().getEXTENSION_UNIT_CONTROL()
						.getDEVICE_RESET_VENDOR_COMMAND_ENABLED());

			}
			break;

		case "I2cRegisterRead":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUVC_SETTINGS().getEXTENSION_UNIT_CONTROL()
						.getI2C_READ_VENDOR_COMMAND_ENABLED());

			}
			break;

		case "I2CRegisterWrite":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUVC_SETTINGS().getEXTENSION_UNIT_CONTROL()
						.getI2C_WRITE_VENDOR_COMMAND_ENABLED());

			}
			break;
		case "firmWareUpdate":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUVC_SETTINGS().getEXTENSION_UNIT_CONTROL()
						.getFIRMWARE_UPDATE_ENABLED());

			}
			break;
		case "terminalType":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getTERMINAL_TYPE());
			}
			break;

		case "NoOfChannelsValue":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getNUMBER_OF_CHANNELS());
			}
			break;

		case "channelConfigurationLeftCheckBox":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
			}
			break;

		case "channelConfigurationRightCheckBox":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
			}
			break;

		case "channelConfigurationCenterCheckBox":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
			}
			break;
		case "lowFrequencyEnhancementCheckBox":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
			}
			break;
		case "leftSurroundCheckBox":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
			}
			break;

		case "rightSurroundCheckBox":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
			}
			break;
		case "leftOfCenterCheckBox":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
			}
			break;
		case "rightOfCenterCheckBox":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
			}
			break;
		case "surroundCheckBox":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
			}
			break;

		case "sideLeftCheckBox":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
			}
			break;
		case "sideRightCheckBox":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
			}
			break;

		case "topCheckBox":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
			}
			break;

		case "audioFormatValue":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getAUDIO_FORMAT());
			}
			break;

		case "bitResolutionValue":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getBIT_RESOLUTION());
			}
			break;
		case "imageFormat":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				helpContent.getEngine()
						.loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getIMAGE_FORMAT());

			}
			break;
		case "hResolution":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				helpContent.getEngine()
						.loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getH_RESOLUTION());

			}
			break;
		case "vResolution":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				helpContent.getEngine()
						.loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getV_RESOLUTION());
			}
			break;
		case "stillCaptured":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				helpContent.getEngine()
						.loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getSTILL_CAPTURE());
			}
			break;
		case "supportedInFS":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getSUPPORTED_IN_LS());
			}
			break;
		case "supportedInHS":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getSUPPORTED_IN_HS());
			}
			break;
		case "supportedInSS":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getSUPPORTED_IN_SS());
			}
			break;
		case "frameRateInFS":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getFRAME_RATE_IN_LS());
			}
			break;
		case "frameRateInHS":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getFRAME_RATE_IN_HS());
			}
			break;
		case "frameRateInSS":
			if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
				
				SX3Manager.getInstance().showHelpContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getFRAME_RATE_IN_SS());
			}
			break;
		default:

			break;
		}

	}
	
}
