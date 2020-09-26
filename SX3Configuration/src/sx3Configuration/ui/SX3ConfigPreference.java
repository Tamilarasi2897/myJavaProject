package sx3Configuration.ui;

import java.util.prefs.Preferences;

public class SX3ConfigPreference {

	private static final String SX3_CONFIG_FILE_PATH = "SX3ConfigFilePath";

	private SX3ConfigPreference() {
		// do not instantiate this pref class
	}

	public static void setSx3ConfigFilePathPreference(String configFilePath) {
		Preferences prefs = Preferences.userNodeForPackage(SX3ConfigPreference.class);
		prefs.put(SX3_CONFIG_FILE_PATH, configFilePath);
	}

	public static String getSx3ConfigFilePathPreference() {
		Preferences prefs = Preferences.userNodeForPackage(SX3ConfigPreference.class);
		return prefs.get(SX3_CONFIG_FILE_PATH, ""); // TODO: Add the default
													// path here..
	}

}
