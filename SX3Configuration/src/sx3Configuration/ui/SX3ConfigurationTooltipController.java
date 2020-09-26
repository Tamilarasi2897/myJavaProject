package sx3Configuration.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import sx3Configuration.mergertool.SX3PropertiesConstants;


public class SX3ConfigurationTooltipController {
	
	public static Properties getConfigProperties() {
		try {
			File jarPath = SX3Manager.getInstance().getInstallLocation();
			// to load application's properties, we use this class
			Properties mainProperties = new Properties();
			FileInputStream file;
			// the base folder is ./, the root of the main.properties file
			String path = jarPath.getParentFile().getAbsolutePath()
					+ SX3PropertiesConstants.DEVICE_SETTINGS_TOOLTIP_PROPERTIES;
			// load the file handle for main.properties
			file = new FileInputStream(path);
			// load all the properties from this file
			mainProperties.load(file);
			// we have loaded the properties, so close the file handle
			file.close();

			return mainProperties;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getCameraControlTooltip(String cameraControlLabel, Properties configProperties) {
		if(cameraControlLabel.equals("Auto-Exposure Mode")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_AUTO_EXPOSURE_MODE");
			return tootltip;
		}else if(cameraControlLabel.equals("Auto-Exposure Priority")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_AUTO_EXPOSURE_PRIORITY_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Exposure Time")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_EXPOSURE_TIME_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Focus")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_FOCUS_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Iris")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_IRIS_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Zoom")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_ZOOM_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Pan Tilt")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_PANTILT_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Roll")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_ROLL_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Region of Interest - Top (in pixels)")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_REGION_OF_INTEREST_TOP_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Region of Interest  - Left (in pixels)")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_REGION_OF_INTEREST_LEFT_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Region of Interest - Bottom (in pixels)")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_REGION_OF_INTEREST_BOTTOM_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Region of Interest - Right (in pixels)")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_REGION_OF_INTEREST_RIGHT_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Region of Interest - Auto Exposure")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_REGION_OF_INTEREST_AUTO_EXPOSURE_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Region of Interest - Auto Iris")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_REGION_OF_INTEREST_AUTO_IRIS_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Region of Interest - Auto White Balance")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_REGION_OF_INTEREST_AUTO_WHITE_BALANCE_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Region of Interest - Auto Focus")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_REGION_OF_INTEREST_AUTO_FOCUS_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Region of Interest - Auto Face Detect")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_REGION_OF_INTEREST_AUTO_FACE_DETECT_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Region of Interest - Auto Detect and Track")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_REGION_OF_INTEREST_AUTO_DETECT_AND_TRACK_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Region of Interest - Image Stabilization")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_REGION_OF_INTEREST_IMAGE_STABILIZATION_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Region of Interest - Higher Quality")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_REGION_OF_INTEREST_HIGHER_QUALITY_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Window Control - Top (in pixels)")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_WINDOWS_CONTROL_TOP_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Window Control - Left (in pixels)")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_WINDOWS_CONTROL_LEFT_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Window Control - Bottom (in pixels)")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_WINDOWS_CONTROL_BOTTOM_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Window Control - Right (in pixels)")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_WINDOWS_CONTROL_RIGHT_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Window Control - NumSteps (in pixels)")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_WINDOWS_CONTROL_NUMSTEPS_CONTROL");
			return tootltip;
		}else if(cameraControlLabel.equals("Window Control - NumStepsUnits (in pixels)")) {
			String tootltip = configProperties.getProperty("CAMERA_CONTROL.TOOLTIP_WINDOWS_CONTROL_NUMSTEPS_UNITES_CONTROL");
			return tootltip;
		}
		
		return null;
	}

	public static String getprocessingUnitControlTooltip(String label_Name, Properties configProperties) {
		if(label_Name.equals("Brightness")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_BRIGHTNESS");
			return tootltip;
		}else if(label_Name.equals("Contrast")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_CONTRAST");
			return tootltip;
		}else if(label_Name.equals("Saturation")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_SATURATION");
			return tootltip;
		}else if(label_Name.equals("Hue")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_HUE");
			return tootltip;
		}else if(label_Name.equals("Sharpness")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_SHARPNESS");
			return tootltip;
		}else if(label_Name.equals("Gamma")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_GAMMA");
			return tootltip;
		}else if(label_Name.equals("White Balance Temperature")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_WHITE_BALANCE");
			return tootltip;
		}else if(label_Name.equals("White Balance Component")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_WHITE_BALANCE_COMPONENT");
			return tootltip;
		}else if(label_Name.equals("Backlight Compensation")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_BACKLIGHT_COMPENSATION");
			return tootltip;
		}else if(label_Name.equals("Gain")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_GAIN");
			return tootltip;
		}else if(label_Name.equals("Power Line Frequency")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_POWER_LINE_FREQUENCY");
			return tootltip;
		}else if(label_Name.equals("Hue Auto")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_HUE_AUTO");
			return tootltip;
		}else if(label_Name.equals("White Balance Temperature Auto")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_WHITE_BALANCE_TEMPERATURE");
			return tootltip;
		}else if(label_Name.equals("White Balance Component Auto")) {
			String tootltip = configProperties.getProperty("PROCESSING_UNIT_CONTROL.TOOLTIP_AUTO_WHITE_BALANCE_COMPONENT");
			return tootltip;
		}
		return null;
	}
	
	
	

}
