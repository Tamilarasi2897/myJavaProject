package sx3configuration.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;
import org.testfx.api.FxRobotInterface;

import javafx.scene.input.KeyCode;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


import javafx.scene.Node;
import javafx.scene.control.TextField;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Scenario2_LoadConfiguration extends TestFXBase {
	@Before
	public void setup() throws FileNotFoundException {

	}
	@Test
	public void test1_scenario2_LoadConfig_Zip() throws InterruptedException {

		loadConfigurationZip(); sleep(6000);
		saveAsConfigurationZip();sleep(2000);
		clickOn("#saveLog");
	}
	@Test
	public void test1_scenario2_LoadConfig_json() throws InterruptedException {

		loadConfigurationJson(); sleep(6000);
		saveAsConfigurationJson();sleep(2000);
		clickOn("#saveLog");
	}
	private void loadConfigurationZip() {
		clickOn("File");
		sleep(2000);
		FxRobotInterface clickOn = clickOn("Load Configuration"); sleep(3000);
		clickOn(LOADCONFIG_SELECTION); sleep(3000);
		if(PREDEFINED_IMPORT_FROM_TYPE.equalsIgnoreCase(".zip")){
			System.out.println("Iam zip");
			File loadedConfigurationPath = new File(PREDEFINED_CONFIGURATION_PATH);
			if(OPERATING_SYSTEM.equalsIgnoreCase("WINDOWS")) {
				((TextField) lookupControl("#sx3ConfigurationToImport"))
				.setText(newConfigurationFilePath + "\\" + "toolgenerated_"+loadedConfigurationPath.getName().substring(0, loadedConfigurationPath.getName().lastIndexOf("."))+".zip"); sleep(2000);
				System.out.println("load zip file name===>"+newConfigurationFilePath + "\\" + loadedConfigurationPath.getName().substring(0, loadedConfigurationPath.getName().lastIndexOf("."))+".zip");
			}else
			{
				((TextField) lookupControl("#sx3ConfigurationToImport"))
				.setText(newConfigurationFilePath + "/" + "toolgenerated_"+loadedConfigurationPath.getName().substring(0, loadedConfigurationPath.getName().lastIndexOf("."))+".zip"); sleep(2000);
				System.out.println("load zip file name===>"+newConfigurationFilePath + "/" + loadedConfigurationPath.getName().substring(0, loadedConfigurationPath.getName().lastIndexOf("."))+".zip");
			}
			((TextField) lookupControl("#jsonFilePath"))
			.setText(PREDEFINED_IMPORT_TO);sleep(2000);
			System.out.println("PREDEFINED_IMPORT_TO===>"+PREDEFINED_IMPORT_TO);
		}
		clickOn("#loadConfigurationOkBtn");
	}
	private void loadConfigurationJson() {
		clickOn("File");
		sleep(2000);
		FxRobotInterface clickOn = clickOn("Load Configuration"); sleep(3000);
		clickOn(LOADCONFIG_SELECTION); sleep(3000);
		if(PREDEFINED_IMPORT_JSON.equalsIgnoreCase(".json")){
			System.out.println("Iam json");
			((TextField) lookupControl("#sx3ConfigurationToImport"))
			//.setText(newConfigurationFilePath + "\\" + newConfigurationFileName+"\\"+newConfigurationFileName+".json"); sleep(2000);
			.setText(PREDEFINED_CONFIGURATION_PATH);
			//System.out.println("load json file name===>"+newConfigurationFilePath + "\\" + newConfigurationFileName+"\\"+newConfigurationFileName+".json");
			System.out.println("load json file name===>"+PREDEFINED_CONFIGURATION_PATH);
		}
		clickOn("#loadConfigurationOkBtn");
	}
	private <T extends Node> T lookupControl(String controlId) {
		T actualControl = lookup(controlId).query();
		assertNotNull("Could not find a control by id = " + controlId, actualControl);

		return actualControl;
	}
	private void saveAsConfigurationZip() {
		clickOn("File");
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		clickOn("#saveAsConfigFromMenu");
		File loadedConfigurationPath = new File(PREDEFINED_CONFIGURATION_PATH);
		String zipFileName="Imported_Zip_"
				+ loadedConfigurationPath.getName().substring(0, loadedConfigurationPath.getName().lastIndexOf("."));
		write(zipFileName).push(KeyCode.TAB);
		String savePath=loadedConfigurationPath.getParent();
		write(savePath).push(KeyCode.ENTER);
		((TextField) lookupControl("#txtFilePath"))
		.setText(savePath);
		clickOn("Ok");
		File file = new File( zipFileName+ "/" + savePath);

	}
	private void saveAsConfigurationJson() {
		clickOn("File");
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		clickOn("#saveAsConfigFromMenu");
		File loadedConfigurationPath = new File(PREDEFINED_CONFIGURATION_PATH);
		String jsonFileName="Imported_Json_"
				+ loadedConfigurationPath.getName().substring(0, loadedConfigurationPath.getName().lastIndexOf("."));
		write(jsonFileName).push(KeyCode.TAB);
		String savePath=loadedConfigurationPath.getParent();
		((TextField) lookupControl("#txtFilePath"))
		.setText(savePath);
		clickOn("Ok");
		File file = new File( jsonFileName+ "/" + savePath);

	}
}