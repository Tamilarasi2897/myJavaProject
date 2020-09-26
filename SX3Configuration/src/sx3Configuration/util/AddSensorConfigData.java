package sx3Configuration.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import sx3Configuration.mergertool.BytesStreamsAndHexFileUtil;
import sx3Configuration.model.SensorConfig;
import sx3Configuration.tablemodel.SensorConfigurationTable;
import sx3Configuration.ui.validations.SX3CommonUIValidations;

public class AddSensorConfigData {
	

	public static List<SensorConfig> AddSensorConfigDataInTable(TextArea textArea,
			ObservableList<SensorConfigurationTable> sensorConfigTableRows) {
		String sensorConfigText = textArea.getText();
		String[] splitString = sensorConfigText.split("},");
		sensorConfigTableRows.clear();
		List<SensorConfig> list = addToSensorConfigList(splitString);
		return list;
	}

	private static List<SensorConfig> addToSensorConfigList(String[] splitString) {
		List<SensorConfig> list = new ArrayList<>();

		for (int i = 0; i < splitString.length; i++) {
				splitString[i] = splitString[i].replace("{", "");
				splitString[i] = splitString[i].replace("}", "");
				String[] splitString1 = splitString[i].split(",");
				if(splitString1.length > 1 && !splitString1[0].isEmpty()) {
					SensorConfig sensorConfigJson = new SensorConfig();
					/** Register Address **/
					String registerAddress = splitString1[0];
						registerAddress = registerAddress.replaceAll("\\s", "").replaceAll("[^a-zA-Z0-9]", "");
						int lastIndexOf = registerAddress.lastIndexOf("0x");
						if(lastIndexOf != -1) {
							String registerAddress1 = registerAddress.substring(0, lastIndexOf);
							registerAddress = registerAddress.replaceAll(registerAddress1, "");
						}
						
					if(registerAddress.length() > 0 && !registerAddress.equals("") && !registerAddress.isEmpty()){
						String registerAddress2 = registerAddress.substring(0, 2);
						if(!registerAddress2.equals("0x") && !registerAddress2.equals("0X")) {
							return showErrorMessage();
						}
						
						/** Register Address **/
						try {
							if(!validateHexString(registerAddress.substring(2))) {
								return showErrorMessage2();
							}else if(!registerAddress.substring(2).equals("") && Long.parseLong(registerAddress.substring(2),16) > 65535) {
								return showErrorMessage1();
							}
						}catch(Exception e) {
							return showErrorMessage2();
						}
						sensorConfigJson
								.setREGISTER_ADDRESS("0x" + StringUtils.leftPad(registerAddress.substring(2), 4, "0"));
					}else {
						sensorConfigJson
						.setREGISTER_ADDRESS("0x0000");
					}
					
					/** Register Value **/
					String registerValue = splitString1[1];
						registerValue = registerValue.replaceAll("\\s", "").replaceAll("[^a-zA-Z0-9]", "");
					if(registerValue.length() > 0 && !registerValue.equals("") && !registerValue.isEmpty()) {
						String registerValue1 = registerValue.substring(0, 2);
						if(!registerValue1.equals("0x") && !registerValue1.equals("0X")) {
							return showErrorMessage();
						}
						
						/** Register Value **/
						try {
							if(!validateHexString1(registerValue.substring(2))) {
								return showErrorMessage2();
							}else if(!registerValue.substring(2).equals("") && Long.parseLong(registerValue.substring(2),16) > 4294967295L) {
								return showSizeErrorMessage1();
							}
						}catch(Exception e) {
							return showErrorMessage2();
						}
						
						sensorConfigJson
								.setREGISTER_VALUE("0x" + StringUtils.leftPad(registerValue.substring(2), 4, "0"));
					}else {
						sensorConfigJson
						.setREGISTER_VALUE("0x0000");
					}
					
					
					/** Slave Address **/
					if (splitString1.length > 2) {
						String slaveAddress = splitString1[2];
						slaveAddress = slaveAddress.replaceAll("\\s", "").replaceAll("[^a-zA-Z0-9]", "");
						if (!slaveAddress.equals(" ") && !slaveAddress.equals("")) {
							String slaveAddress1 = slaveAddress.substring(0, 2);
							if(!slaveAddress1.equals("0x") && !slaveAddress1.equals("0X")) {
								return showErrorMessage();
							}
							try {
								if(!validateHexString(slaveAddress1.substring(2))) {
									return showErrorMessage2();
								}else if(!slaveAddress.substring(2).equals("") && Long.parseLong(slaveAddress.substring(2),16) > 65535) {
									return showErrorMessage1();
								}
							}catch(Exception e) {
								return showErrorMessage2();
							}
							sensorConfigJson
									.setSLAVE_ADDRESS("0x" + StringUtils.leftPad(slaveAddress.substring(2), 4, "0"));
						} else {
							sensorConfigJson.setSLAVE_ADDRESS("0x0000");
						}
					} else {
						sensorConfigJson.setSLAVE_ADDRESS("0x0000");
					}
					list.add(sensorConfigJson);
				}
		}
		return list;
	}


	private static List<SensorConfig> showErrorMessage1() {
		Alert a = new Alert(AlertType.ERROR);
		a.setHeaderText(null);
		a.setContentText("Max size 2 bytes");
		a.show();
		List<SensorConfig> list = new ArrayList<>();
		return list;
	}
	
	private static List<SensorConfig> showSizeErrorMessage1() {
		Alert a = new Alert(AlertType.ERROR);
		a.setHeaderText(null);
		a.setContentText("Max size 4 bytes");
		a.show();
		List<SensorConfig> list = new ArrayList<>();
		return list;
	}
	
	private static List<SensorConfig> showErrorMessage() {
		Alert a = new Alert(AlertType.ERROR);
		a.setHeaderText(null);
		a.setContentText("Value should be start with 0x");
		a.show();
		List<SensorConfig> list = new ArrayList<>();
		return list;
	}
	
	private static List<SensorConfig> showErrorMessage2() {
		Alert a = new Alert(AlertType.ERROR);
		a.setHeaderText(null);
		a.setContentText("Format not correct.");
		a.show();
		List<SensorConfig> list = new ArrayList<>();
		return list;
	}

	public static List<SensorConfig> getSensorConfigDatafromFile(File vsFile) {

		List<SensorConfig> result = null;
		if (vsFile != null && vsFile.exists()) {
			String videoSourceTextdata = BytesStreamsAndHexFileUtil.readAllTextAsString(vsFile)
					.replaceAll("[{|/\r|/\r/\n|/\n]", "");

			String[] videoSourceList = videoSourceTextdata.split("};");
			for (String listVal : videoSourceList) {
				if (listVal.length() == 1 || listVal.length() == 2) {
					BytesStreamsAndHexFileUtil.log(
							"Video Source Config file is not properly formatted -> Video Source hex file not created");
				} else {
					for (int i = 0; i < videoSourceList.length; i++) {
						String string = videoSourceList[i];
						String[] splitString1 = string.split("},");
						result = addToSensorConfigList(splitString1);
					}
//					result = addToSensorConfigList(videoSourceList);
				}
			}
		}
		return result;
	}
	
	public static boolean validateHexString(String hexVal) {
		if(hexVal == null || hexVal.equals("")) {
			return true;
		}else {
			return SX3CommonUIValidations.isValidHex(hexVal);
		}
	}
	
	private static boolean validateHexString1(String hexVal) {
		if(hexVal == null || hexVal.equals("")) {
			return true;
		}else {
			return SX3CommonUIValidations.isValidHex1(hexVal);
		}
	}
}
