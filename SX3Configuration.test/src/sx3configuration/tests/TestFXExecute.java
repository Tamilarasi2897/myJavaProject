package sx3configuration.tests;
import static org.junit.Assert.assertArrayEquals;

import java.awt.Checkbox;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.testfx.api.FxRobotInterface;
import org.testfx.assertions.api.Assertions;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotInterface;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import sun.rmi.runtime.Log;

import org.junit.runners.MethodSorters;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestFXExecute extends TestFXBase{
	
	
	@Test
	public void clickondevicesettings() {
		clickOn("File");
		sleep(1000);
		FxRobotInterface clickOn = clickOn("New Configuration");
		clickOn(".combo-box-base");
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(2000);
		//C:\Users\Sridhar\Documents\SX3FIVE
		write("test03").push(KeyCode.TAB);
		write("C:/Users/Sridhar/Documents/SX3FIVE").push(KeyCode.ENTER);
		sleep(2000);
		sleep(2000);
		clickOn("Ok");
		FxAssert.verifyThat("#deviceName", LabeledMatchers.hasText("SX3 UVC (CYUSB3017)"));
		
		FxAssert.verifyThat("File", LabeledMatchers.hasText("File"));
		FxAssert.verifyThat("Utility", LabeledMatchers.hasText("Utility"));
		FxAssert.verifyThat("Help", LabeledMatchers.hasText("Help"));
		
		clickOn("#vendorId");
		String tooltip = TestFXExecute.getAttribute("#vendorId", "title");
		print(tooltip);
		sleep(1000);
		clickOn("#productId");
		write("5678").push(KeyCode.TAB);
		sleep(2000);
		clickOn("#manufacture");
		
		sleep(2000);
		clickOn("#productString");
		
		sleep(2000);
		//clickOn("#autoGenerateSerialNumber");
		sleep(2000);
		clickOn("#serialNumber");
		write("1111111111111").push(KeyCode.TAB);
		sleep(2000);
		clickOn("#serialNumberIncrement");
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		write("4").push(KeyCode.TAB);
		
		clickOn("#fifoBusWidth");
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		clickOn("#fifoClockFrequency");
		write("4").push(KeyCode.TAB);
		clickOn("#interFaceType");
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		clickOn("#gpio1");
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		clickOn("#gpio2");
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		clickOn("#gpio3");
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		clickOn("#gpio4");
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		clickOn("#gpio5");
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		clickOn("#gpio6");
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		clickOn("#gpio7");
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		clickOn("#deviceSttingFirmWare");
		sleep(1000);
		clickOn("#deviceSttingI2CFrequency");
		type(KeyCode.DOWN);
		sleep(1000);
		clickOn("#enableDebugLevel");
		sleep(1000);
		clickOn("#debugValue");
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		
		sleep(4000);
		clickOn("#noOfEndpoint");
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		clickOn("#Endpoint_1");
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		clickOn("#Endpoint_2");
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		
		//type(KeyCode.TAB);
		sleep(1000);
		//clickOn("Ok");
		clickOn("#uvcVersion");
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		clickOn("#uvcHeaderAddition");
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		clickOn("UVC/UAC Settings");
		sleep(3000);
		clickOn("#endpointBrustLength");
		write("16").push(KeyCode.TAB);
		sleep(1000);
		clickOn("#endPointBufferSize");
		write("16").push(KeyCode.TAB);
		sleep(1000);
		clickOn("#endPointBufferCount");
		write("2").push(KeyCode.TAB);
		sleep(1000);
		//clickOn("Endpoint Settings");
		sleep(1000);
		clickOn("Format And Resolution");
		sleep(1000);
		clickOn("#imageFormat");
		type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		clickOn("#hResolution");
		write("4").push(KeyCode.TAB);
		clickOn("#vResolution");
		write("16").push(KeyCode.TAB);
		clickOn("#stillCaptured");
		sleep(1000);
		clickOn("#supportedInFS");
		sleep(1000);
		clickOn("#supportedInHS");
		sleep(1000);
		clickOn("#supportedInSS");
		sleep(1000);
		clickOn("#frameRateInFS");
		write("16").push(KeyCode.TAB);
		clickOn("#frameRateInHS");
		write("16").push(KeyCode.TAB);
		clickOn("#frameRateInSS");
		write("16").push(KeyCode.TAB);
		
		//clickOn("#formatResolutionSensorConfig");
		//type(KeyCode.DOWN);
		//write("{0x6000, 0x1, }").push(KeyCode.TAB);
		clickOn("Camera Control");
		sleep(1000);
		//clickOn("#minTextField");
		//write("16").push(KeyCode.TAB);
		clickOn("Processing Unit Control");
		sleep(1000);
		clickOn("Extension Unit Controls");
		sleep(1000);
		clickOn("#deviceReset");
		 type(KeyCode.TAB);
		 sleep(1000);
		 clickOn("#I2cRegisterRead");
		 type(KeyCode.TAB);
		 sleep(1000);
		 clickOn("#I2CRegisterWrite");
		 type(KeyCode.TAB);
		 sleep(1000);
		 clickOn("#firmWareUpdate");
		 type(KeyCode.TAB);
		 sleep(1000);
		
		//clickOn("#end1BurstLengthValue");
		//write("4").push(KeyCode.TAB);
		 //sleep(2000);
		//clickOn("#end1BufferCountValue");
		//write("4").push(KeyCode.TAB);
			//sleep(2000);
		//clickOn("#end1BufferSizeValue");
		//write("4").push(KeyCode.TAB);
	     clickOn("FIFO Master Config");
	     sleep(1000);
	     clickOn("#enableFPGA");
	     sleep(1000);
	     clickOn("#fpgaFamily");
	     type(KeyCode.DOWN);
		type(KeyCode.TAB);
		sleep(1000);
		 clickOn("#i2cSlaveAddress");
		 write("1").push(KeyCode.TAB);
		 sleep(1000);
		 clickOn("Video Source Config");
		 sleep(2000);
		 clickOn("#enableVideoSourceConfig");
		 sleep(2000);
		 clickOn("#videoSorceTypeValue");
		 type(KeyCode.DOWN);
		 type(KeyCode.TAB);
		 clickOn("#videoSorceSubTypeValue");
		 type(KeyCode.DOWN);
		 sleep(2000);
		 clickOn("#videoSourceI2CSlaveAddressValue");
		 write("5").push(KeyCode.TAB);
		 sleep(2000);
		 clickOn("#videoSourceI2CSlaveDataSize");
		 write("5").push(KeyCode.TAB);
		 clickOn("#videoSourceI2CFrequency");
		 type(KeyCode.DOWN);
		 sleep(2000);
		
			
		 
		 clickOn("File");
		 type(KeyCode.DOWN);
		 type(KeyCode.DOWN);
		 clickOn("Save Configuration");
		 
		 
}
	
		
		
		private void print(String tooltip) {
		// TODO Auto-generated method stub
		
	}



		//FxAssert.verifyThat("#gpio1", TextMatchers.hasText("Not Used"));
		//FxAssert.verifyThat("#vendorId", LabeledMatchers.hasText("04b4"));
		//FxAssert.verifyThat("#productString", LabeledMatchers.hasText("SX3"));
		
	private static String getAttribute(String myinputtestobject2, String string) {
		// TODO Auto-generated method stub
		return null;
	}
	


	/*@Test
	 public void device() throws FileNotFoundException {
		clickOn("File");
		sleep(1000);
		FxRobotInterface clickOn = clickOn("Load Configuration");
		sleep(1000);
		//FxRobotInterface clickOn = clickOn("#openConfig");
		//clickOn("#openConfig");
		//sleep(4000);
		//type(KeyCode.TAB);
		write("C:/Users/Sridhar/Documents/SX3FIVE/sampleConfiguration/sampleConfiguration.json");
		
		//C:\Users\Sridhar\Documents\SX3FIVE
		sleep(1000);
		clickOn("Ok");
		sleep(3000);
		clickOn("UVC/UAC Settings");
		sleep(1000);
		clickOn("FIFO Master Config");
		sleep(1000);
		clickOn("Video Source Config");
		sleep(1000);
		//FxRobotInterface clickOn1 = clickOn("Save");
		//clickOn("Save");
		
		type(KeyCode.TAB);
		type(KeyCode.TAB);
		type(KeyCode.TAB);
		
		




		
		 //write("test").push(KeyCode.ENTER);
		//clickOn("Save");
		
		//DirectoryChooser dc = new DirectoryChooser();
		//File file = dc.showDialog(null);
		//if (file != null) {
		//file = new File(file.getAbsolutePath() + "/test.txt");}
		
		//MenuItem cmItem2 = new MenuItem("Save Image");
	    //cmItem2.setOnAction(new EventHandler<ActionEvent>() {
	        
	           
		
		
		

	    }
	
/*@Test
public void uacscenario() throws FileNotFoundException {
clickOn("File");
sleep(1000);
FxRobotInterface clickOn = clickOn("New Configuration");
clickOn(".combo-box-base");
type(KeyCode.DOWN);
type(KeyCode.TAB);
sleep(2000);
write("sampleConfiguration").push(KeyCode.TAB);
write("C:/Users/Sridhar/Documents/SX3").push(KeyCode.ENTER);
sleep(2000);
clickOn("Ok");
sleep(2000);
sleep(4000);
clickOn("#noOfEndpoint");
type(KeyCode.DOWN);
type(KeyCode.TAB);
sleep(1000);
clickOn("#Endpoint_1");
type(KeyCode.DOWN);
type(KeyCode.DOWN);
type(KeyCode.TAB);
sleep(1000);
clickOn("#Endpoint_2");
type(KeyCode.DOWN);
type(KeyCode.DOWN);

//type(KeyCode.TAB);
sleep(1000);

//type(KeyCode.TAB);

}*/


}




		
		


		
		