package sx3Configuration.mergertool;

import java.io.File;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import sx3Configuration.model.SX3Configuration;
import sx3Configuration.ui.SX3ConfigPreference;
import sx3Configuration.ui.SX3Manager;

public class MainHexGenerator {

	public static void main(String[] args) {
		String jsonFilePath;
		if (args.length == 1) {
			try {
				// Gson parser object to parse json file
				Gson gson = new Gson();
				jsonFilePath = args[0].trim();
				// Set Config File path to preference
				SX3ConfigPreference.setSx3ConfigFilePathPreference(jsonFilePath);
				File configJsonFile = new File(jsonFilePath);
				
				if (configJsonFile.exists()) {
					JsonReader reader = new JsonReader(new FileReader(configJsonFile));
					// convert the json string back to object
					SX3Configuration sx3Obj = gson.fromJson(reader, SX3Configuration.class);
					// Set the Sx3 object to the manager
					SX3Manager.getInstance().setSx3Configuration(sx3Obj);
					// Convert config json to hex file
					SX3ConfigHexFileUtil.convertJsonToHexFile();
					BytesStreamsAndHexFileUtil.log("Successfully generated sx3 config hex file");
				} else {
					BytesStreamsAndHexFileUtil.log("SX3Configuration Json file not found");
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
		}else{
			BytesStreamsAndHexFileUtil.log("Please pass only one argument. (arg = Json file path)");
		}
	}

}
