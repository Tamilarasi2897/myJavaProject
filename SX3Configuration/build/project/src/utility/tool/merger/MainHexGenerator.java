package utility.tool.merger;

import java.io.File;

import sx3Configuration.ui.SX3ConfigPreference;

public class MainHexGenerator {

	public static void main(String[] args) {
		String jsonFilePath;
		if (args.length == 1) {
			try {
				jsonFilePath = args[0].trim();
				// Set Config File path to preference
				SX3ConfigPreference.setSx3ConfigFilePathPreference(jsonFilePath);
				File configJsonFile = new File(jsonFilePath);
				if (configJsonFile.exists()) {
					// Convert config json to hex file
					SX3ConfigHexFileUtil.convertJsonToHexFile(configJsonFile);
					BytesStreamsAndHexFileUtil.log("Successfully generated confic table hex file");
					// Convert video source config txt to hex file
					SX3ConfigHexFileUtil.convertVideoSourceConfigToHexFile(configJsonFile);
					BytesStreamsAndHexFileUtil.log("Successfully generated video source config hex file");
				} else {
					BytesStreamsAndHexFileUtil.log("SX3Configuration Json file not found");
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
		}else{
			BytesStreamsAndHexFileUtil.log("Please pass only one argument. i.e = Json file path");
		}
	}

}
