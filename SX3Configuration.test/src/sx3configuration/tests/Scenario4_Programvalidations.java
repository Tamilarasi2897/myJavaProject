package sx3configuration.tests;



import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxRobotInterface;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import sx3Configuration.mergertool.HexConversionErrors;
import sx3Configuration.mergertool.MergeFinalFirmwareArtifacts;
import sx3Configuration.programming.SX3ConfigurationProgrammingUtility;
import sx3Configuration.ui.SX3Manager;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Scenario4_Programvalidations extends TestFXBase {

	@Before
	public void setup() throws FileNotFoundException {

	}
	@Test
	public void test1_scenario4_NewConfigurationwithvalidvalues() throws InterruptedException {

		loadConfiguration(); sleep(2000);
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



	private <T extends Node> T lookupControl(String controlId) {
		T actualControl = lookup(controlId).query();
		assertNotNull("Could not find a control by id = " + controlId, actualControl);

		return actualControl;
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
