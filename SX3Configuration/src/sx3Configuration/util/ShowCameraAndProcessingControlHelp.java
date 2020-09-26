package sx3Configuration.util;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import sx3Configuration.ui.SX3Manager;

public class ShowCameraAndProcessingControlHelp implements EventHandler<Event>{
	
	Label labelName;
	private SX3ConfiguartionHelp sx3ConfigurationHelp;
	private WebView helpContent;
	
	public ShowCameraAndProcessingControlHelp(Label labelName) {
		this.labelName = labelName;
		this.sx3ConfigurationHelp = SX3Manager.getInstance().getSx3ConfigurationHelp();
		this.helpContent = SX3Manager.getInstance().getHelpView();
	}

	@Override
	public void handle(Event event) {
		if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
			if (labelName.getText().equals("Auto-Exposure Mode")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getAUTO_EXPOSURE_MODE());
			} else if (labelName.getText().equals("Auto-Exposure Priority")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getAUTO_EXPOSURE_PRIORITY());
			} else if (labelName.getText().equals("Exposure Time")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getEXPOSURE_TIME());
			} else if (labelName.getText().equals("Focus")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getFOCUS());
			} else if (labelName.getText().equals("Iris")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getIRIS());
			} else if (labelName.getText().equals("Zoom")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getZOOM());
			} else if (labelName.getText().equals("Pan Tilt")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getPAN_TILT());
			} else if (labelName.getText().equals("Roll")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getROLL());
			} else if (labelName.getText().equals("Region of Interest- Top (in pixels)")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwROI_Top());
			} else if (labelName.getText().equals("Region of Interest  - Left (in pixels)")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwROI_Left());
			} else if (labelName.getText().equals("Region of Interest - Bottom (in pixels)")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwROI_Bottom());
			} else if (labelName.getText().equals("Region of Interest - Right (in pixels)")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwROI_Right());
			} else if (labelName.getText().equals("Region of Interest - Auto Exposure")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD0());
			} else if (labelName.getText().equals("Region of Interest - Auto Iris")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD1());
			} else if (labelName.getText().equals("Region of Interest - Auto White Balance")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD2());
			} else if (labelName.getText().equals("Region of Interest - Auto Focus")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD3());
			} else if (labelName.getText().equals("Region of Interest - Auto Face Detect")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD4());
			} else if (labelName.getText().equals("Region of Interest - Auto Detect and Track")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD5());
			} else if (labelName.getText().equals("Region of Interest - Image Stabilization")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD6());
			} else if (labelName.getText().equals("Region of Interest -Higher Quality")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD7());
			} else if (labelName.getText().equals("Window Control - Top (in pixels)")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwWindow_Top());
			} else if (labelName.getText().equals("Window Control - Left (in pixels)")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwWindow_Left());
			} else if (labelName.getText().equals("Window Control - Bottom (in pixels)")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwWindow_Bottom());
			} else if (labelName.getText().equals("Window Control - Right (in pixels)")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwWindow_Right());
			} else if (labelName.getText().equals("Window Control - NumSteps (in pixels)")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwWindow_NumSteps());
			} else if (labelName.getText().equals("Window Control - NumStepsUnits (in pixels)")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmNumStepsUnits());
			} else if (labelName.getText().equals("Brightness")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getBRIGHTNESS());
			} else if (labelName.getText().equals("Contrast")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getCONTRAST());
			} else if (labelName.getText().equals("Saturation")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getSATURATION());
			} else if (labelName.getText().equals("Hue")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getHUE());
			} else if (labelName.getText().equals("Sharpness")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getSHARPNESS());
			} else if (labelName.getText().equals("Gamma")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getGAMMA());
			} else if (labelName.getText().equals("White Balance Temperature")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getWHITE_BALANCE_TEMPERATURE());
			} else if (labelName.getText().equals("White Balance Component")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getWHITE_BALANCE_COMPONENT());
			} else if (labelName.getText().equals("Backlight Compensation")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getBACKLIGHT_COMPENSATION());
			} else if (labelName.getText().equals("Gain")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getGAIN());
			} else if (labelName.getText().equals("Power Line Frequency")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getPOWER_LINE_FREQUENCY());
			} else if (labelName.getText().equals("Hue Auto")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getHUE_AUTO());
			} else if (labelName.getText().equals("White Balance Temperature Auto")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL()
						.getWHITE_BALANCE_TEMPERATURE_AUTO());
			} else if (labelName.getText().equals("White Balance Component Auto")) {
				helpContent.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL()
						.getWHITE_BALANCE_COMPONENT_AUTO());
			}


		}
		
	}

}
