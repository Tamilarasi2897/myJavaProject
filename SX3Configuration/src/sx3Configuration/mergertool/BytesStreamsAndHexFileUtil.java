package sx3Configuration.mergertool;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import sx3Configuration.model.CSVKeys;
import sx3Configuration.model.CSVValues;
import sx3Configuration.model.SX3Configuration;
import sx3Configuration.model.VideoSourceConfig;
import sx3Configuration.ui.SX3ConfigPreference;
import sx3Configuration.ui.SX3Manager;

public class BytesStreamsAndHexFileUtil {

	public static final int BUFFER_SIZE = 512 * 1024; // 512 KB

	// Delimiter used in CSV file
	private static final String CSV_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String DOUBLE_QUOTE = "\"";

	private BytesStreamsAndHexFileUtil() {
		// don't instantiate this util class
	}

	/**
	 * Read the content of .img file in firmware folder into the final firmware
	 * file, max limit will be 256KB, if it is less than max size just append FF
	 * for rest of the size.
	 */
	protected static byte[] readBaseFirmwareImgFile(File file) {
		SX3Manager.getInstance().addLog("Reading in (.img) file named : " + file.getName() + "<br>");
		log("Reading in (.img) file named : " + file.getName());
		byte[] result = new byte[(int) BUFFER_SIZE];
		try {
			try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
				// Reads up to certain bytes of data from this input stream into
				// an array of bytes.
				input.read(result);
				ByteBuffer buffer = ByteBuffer.wrap(result);

				int fileSize = (int) file.length();

				if (fileSize < BUFFER_SIZE) {
					int extraByte = BUFFER_SIZE - fileSize;
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < extraByte; i++) {
						sb.append("FF");
					}
					byte[] newArray = hexStringToByteArray(sb.toString());
					buffer.position(fileSize);
					buffer.put(newArray);
				}

			}
		} catch (FileNotFoundException ex) {
			log("Base Firmware (.img) file not found.");
		} catch (IOException ex) {
			log(ex);
		}
		return result;
	}

	protected static File getBaseImageFile() {
		File imgFile = null;
		try {

			File jarPath = SX3Manager.getInstance().getInstallLocation();

			String sx3BaseImgPath = jarPath.getParentFile().getAbsolutePath() + File.separator
					+ SX3PropertiesConstants.FIRMWARE_FOLDER;

			SX3Configuration sx3Obj = SX3Manager.getInstance().getSx3Configuration();
			// Choose for SX3-Data device
			if (sx3Obj.getDEVICE_NAME().startsWith(SX3PropertiesConstants.SX3_DATA_DEVICE_NAME)) {
				String sx3ImgFileName = SX3PropertiesConstants.SX3_DATA_IMG_FILE_NAME;
				return new File(
						sx3BaseImgPath + File.separator + sx3ImgFileName + SX3PropertiesConstants.IMAGE_FILE_EXTENSION);
			}
			List<VideoSourceConfig> videoSourceConfigList = sx3Obj.getVIDEO_SOURCE_CONFIG();
			if (videoSourceConfigList != null && !videoSourceConfigList.isEmpty()) {
				for (int j = 0; j < videoSourceConfigList.size(); j++) {
					if (videoSourceConfigList.get(j).getENABLE_VIDEO_SOURCE_CONFIGURATION_DOWNLOAD()
							.equals("Disable")) {
						continue;
					}
					// If there are two endpoints, consider Video Source/Subtype
					// values of the first endpoint only.
					if (videoSourceConfigList.get(j).getENDPOINT() == 1) {
						if ("Image Sensor".equals(videoSourceConfigList.get(j).getVIDEO_SOURCE_TYPE())) {
							String sx3ImgFileName = SX3PropertiesConstants.SX3_IS_IMG_FILE_NAME;
							imgFile = new File(sx3BaseImgPath + File.separator + sx3ImgFileName
									+ SX3PropertiesConstants.IMAGE_FILE_EXTENSION);
						} else if ("HDMI Source".equals(videoSourceConfigList.get(j).getVIDEO_SOURCE_TYPE())) {
							if ("HDMI RX - ITE6801".equals(videoSourceConfigList.get(j).getVIDEO_SOURCE_SUBTYPE())) {
								String sx3ImgFileName = SX3PropertiesConstants.SX3_HDMI_ITE_IMG_FILE_NAME;
								imgFile = new File(sx3BaseImgPath + File.separator + sx3ImgFileName
										+ SX3PropertiesConstants.IMAGE_FILE_EXTENSION);
							} else if ("HDMI RX - Generic"
									.equals(videoSourceConfigList.get(j).getVIDEO_SOURCE_SUBTYPE())) {
								String sx3ImgFileName = SX3PropertiesConstants.SX3_HDMI_GENERIC_IMG_FILE_NAME;
								imgFile = new File(sx3BaseImgPath + File.separator + sx3ImgFileName
										+ SX3PropertiesConstants.IMAGE_FILE_EXTENSION);
							}
						}

					}
				}
				
				if(imgFile == null) {
					String sx3ImgFileName = SX3PropertiesConstants.SX3_UVC_DEFAULT_IMG_FILE_NAME;
					imgFile = new File(
							sx3BaseImgPath + File.separator + sx3ImgFileName + SX3PropertiesConstants.IMAGE_FILE_EXTENSION);
				}
			} else {
				String sx3ImgFileName = SX3PropertiesConstants.SX3_UVC_DEFAULT_IMG_FILE_NAME;
				return new File(
						sx3BaseImgPath + File.separator + sx3ImgFileName + SX3PropertiesConstants.IMAGE_FILE_EXTENSION);
			}

		} catch (Exception e) {
			SX3Manager.getInstance().addLog("Unable to get the base firmware (.img) file properties <br>");
			BytesStreamsAndHexFileUtil.log("Unable to get the base firmware (.img) file properties");
		}
		return imgFile;
	}

	public static File getConfigJsonFile() {
		File configJsonFile = new File(SX3ConfigPreference.getSx3ConfigFilePathPreference());
		return configJsonFile;
	}

	/**
	 * Write a byte array to the given file. Writing binary data is
	 * significantly simpler than reading it.
	 */
	protected static void write(byte[] input, File outPutFile) {
		SX3Manager.getInstance().addLog("Writing binary file...<br>");
		log("Writing binary file...");
		try {
			FileOutputStream output = null;
			try {
				if (outPutFile.exists()) {
					outPutFile.delete(); // you might want to check if delete
											// was successfull
				}
				outPutFile.createNewFile();
				output = new FileOutputStream(outPutFile);
				output.write(input);
			} finally {
				output.close();
			}
		} catch (FileNotFoundException ex) {
			log("File not found.");
		} catch (IOException ex) {
			log(ex);
		}
	}

	/**
	 * Write a byte array to the given file. Writing binary data is
	 * significantly simpler than reading it.
	 */
	protected static void write(BufferedReader input, File outPutFile) {
		log("Writing Hex file...");
		try {
			BufferedWriter output = null;
			String line;
			try {
				if (outPutFile.exists()) {
					outPutFile.delete(); // you might want to check if delete
											// was successfull
				}
				outPutFile.createNewFile();
				output = Files.newBufferedWriter(outPutFile.toPath(), StandardCharsets.UTF_8);
				while ((line = input.readLine()) != null) {
					output.write(line);
					output.newLine(); // must do this: readLine() will have
										// stripped line endings

				}
			} finally {
				output.close();
			}
		} catch (FileNotFoundException ex) {
			log("File not found.");
		} catch (IOException ex) {
			log(ex.getMessage());
		}
	}

	/** Read the given binary file, and return its contents as a byte array. */
	protected static byte[] readAlternateImpl(File file) {
		SX3Manager.getInstance().addLog("Reading the file named : " + file.getName() +"<br>");
		log("Reading the file named : " + file.getName());
		byte[] result = new byte[0];
		try {
			result = Files.readAllBytes(file.toPath());
		} catch (IOException ex) {
			log(ex.getMessage());
		}
		return result;
	}

	/** Read the given file, and return its contents as a array list. */
	protected static List<String> readAsListImpl(File file) {
		SX3Manager.getInstance().addLog("Reading the file named :"+file.getName());
		log("Reading the file named : " + file.getName());
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
			reader.close();

		} catch (IOException ex) {
			log(ex);
		}
		return list;
	}

	public static String readAllTextAsString(File file) {
		SX3Manager.getInstance().addLog("Reading the file named : " + file.getName()+"<BR>");
		log("Reading the file named : " + file.getName());
		try {
			byte[] encoded = Files.readAllBytes(file.toPath());
			return new String(encoded, StandardCharsets.US_ASCII);
		} catch (IOException ex) {
			log(ex);
			return "";
		}

	}

	public static String convertJsonValuesToHex(Object value, String objName, String key,
			Map<String, Integer> configProperitesMap, Map<String, String> hexValPropertiesMap,
			LinkedHashMap<CSVKeys, CSVValues> csvMapObj, boolean isList, int indxForLists)
			throws UnsupportedEncodingException {
		String defValue = key.contains(SX3PropertiesConstants.RESERVED_KEY) ? SX3PropertiesConstants.RESERVED_HEX_VALUE
				: SX3PropertiesConstants.NO_HEX_VALUE;
		String mappedValue = "NA";
		int byteSize = 32;
		if (configProperitesMap.containsKey(key)) {
			byteSize = configProperitesMap.get(key);
		} else {
			BytesStreamsAndHexFileUtil.log("Size of the Parameter is not defined in properties file for the key: " + key
					+ "\n" + "Default value set to 32 Bytes");
		}
		byte[] bytes = new byte[byteSize];
		if (value instanceof String) {
			String fullyQualifiedKeyForMapValue = objName + "." + key + "." + ((String) value).replaceAll(" ", "_");
			// if the key is reserved then return x times "FF"
			if (key.contains(SX3PropertiesConstants.RESERVED_KEY)) {
				String hexval = appendExtraBytes(new byte[0], byteSize, defValue, 1);
				setCSVData(objName, key, value, mappedValue, byteSize, hexval, csvMapObj, isList, indxForLists);
				return hexval;
			}
			// If the value is hex then don't convert
			if (((String) value).toLowerCase().startsWith(SX3PropertiesConstants.HEX_PREFIX)) {
				String hexString = new String((String) value).substring(2, ((String) value).length()).toLowerCase()
						.trim();
				String hexval = leftPad(hexString,
						byteSize * 2); /* each byte is 2 bit size for hex */
				// return convertByteToHexString(fromHexString(hexString), 1);
				setCSVData(objName, key, value, mappedValue, byteSize, hexval, csvMapObj, isList, indxForLists);
				return hexval;
			}
			// if the json value has hex mapped value, then return mapped value
			else if (hexValPropertiesMap.containsKey(fullyQualifiedKeyForMapValue)) {
				mappedValue = hexValPropertiesMap.get(fullyQualifiedKeyForMapValue);
				String hexval = leftPad(mappedValue, byteSize * 2);
				setCSVData(objName, key, value, mappedValue, byteSize, hexval, csvMapObj, isList, indxForLists);
				return hexval;
			} else {
				bytes = ((String) value).trim().getBytes("UTF-8");
			}
		} else // if the value is integer then return 2 digit hex value
		if (value instanceof Integer) {
			int intVal = (int) value;
			String fullyQualifiedKeyForMapValue = objName + "." + key + "."
					+ (Integer.toString(intVal)).replaceAll(" ", "_");
			StringBuilder sb = new StringBuilder();
			if (hexValPropertiesMap.containsKey(fullyQualifiedKeyForMapValue)) {
				mappedValue = hexValPropertiesMap.get(fullyQualifiedKeyForMapValue);
				sb.append(leftPad(mappedValue, byteSize * 2));
			} else {
				sb.append(leftPad(Integer.toHexString(intVal),
						byteSize * 2)); /* each byte is 2 bit size for hex */
			}
			String hexval = sb.toString();
			setCSVData(objName, key, value, mappedValue, byteSize, hexval, csvMapObj, isList, indxForLists);
			return hexval;
		} else if (value instanceof Long) {
			long longVal = (long) value;
			String fullyQualifiedKeyForMapValue = objName + "." + key + "."
					+ (Long.toString(longVal)).replaceAll(" ", "_");
			StringBuilder sb = new StringBuilder();
			if (hexValPropertiesMap.containsKey(fullyQualifiedKeyForMapValue)) {
				mappedValue = hexValPropertiesMap.get(fullyQualifiedKeyForMapValue);
				sb.append(leftPad(mappedValue, byteSize * 2));
			} else {
				sb.append(leftPad(Long.toHexString(longVal),
						byteSize * 2)); /* each byte is 2 bit size for hex */
			}
			String hexval = sb.toString();
			setCSVData(objName, key, value, mappedValue, byteSize, hexval, csvMapObj, isList, indxForLists);
			return hexval;
		}

		if (bytes.length == byteSize) {
			String hexval = convertByteToHexString(bytes, 2);
			setCSVData(objName, key, value, mappedValue, byteSize, hexval, csvMapObj, isList, indxForLists);
			return hexval;
		}

		String hexval = convertByteToHexString(bytes, 2) + appendExtraBytes(bytes, byteSize, defValue, 2);
		setCSVData(objName, key, value, mappedValue, byteSize, hexval, csvMapObj, isList, indxForLists);
		return hexval;
	}

	// set the key value pair for the json object
	public static void setCSVData(String objName, String key, Object value, String mapValue, int byteSize,
			String hexval, Map<CSVKeys, CSVValues> csvMapObj, boolean isList, int indxForLists) {
		CSVKeys newKeys = new CSVKeys();
		String newObjName = objName;
		if (isList) {
			newObjName = objName + "." + Integer.toString((int) indxForLists);
		}
		newKeys.setParentKey(newObjName);
		newKeys.setActualKey(key);

		CSVValues newValues = new CSVValues();
		newValues.setJsonValue(value);
		newValues.setMappedValue(mapValue);
		newValues.setByteSize(byteSize);
		newValues.setHexValue(hexval);

		csvMapObj.put(newKeys, newValues);
	}

	public static String convertByteToHexString(byte[] bytes, int eachCharByteSize) {
		StringBuilder result = new StringBuilder();
		for (byte temp : bytes) {
			// bytes widen to int, need mask,prevent sign extension
			int decimal = (int) temp & 0xff;
			String hex = Integer.toHexString(decimal);

			if (hex.length() < 2) {
				// pad with leading zero if needed
				result.append(leftPad(hex, 2));
			} else {
				result.append(hex);
			}
			// if char size is 2 bytes long then append "00"for alternate hex
			// string
			if (eachCharByteSize == 2) {
				result.append("00");
			}
		}
		return result.toString();
	}

	public static String appendExtraBytes(byte[] bytes, int byteSize, String dummyValue, int eachCharByteSize) {
		int extraByteData = byteSize - (bytes.length * eachCharByteSize);
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < extraByteData; i++) {
			result.append(dummyValue);
		}
		return result.toString();
	}

	public static void convertJSONToCSV(Map<CSVKeys, CSVValues> csvMapObj) {

		// CSV file header
		final String FILE_HEADER = "Parent Key,Json Key,Json Value,Mapped Value,Byte Size,Hex Value,Hex Value(By Formula),Expected Hex Value(Text),Result";

		final String[] bitMakFields = { "CHANNEL_CONFIGURATION", "FEATURE_UNIT_CONTROLS",
				"CAMERA_CONTROLS_ENABLED_BITMASK", "PROCESSING_UNIT_ENABLED_CONTROLS_BITMASK" };

		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(getCSVFile());

			// Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());

			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			// Iterate through LinkedHashMap entries
			log("Start creating CSV : ");
			int rowInd = 2; // Row index start from 1 and Already header row
							// added
			for (Map.Entry<CSVKeys, CSVValues> entry : csvMapObj.entrySet()) {
				// now work with key and value...
				CSVKeys key = entry.getKey();
				CSVValues value = entry.getValue();

				fileWriter.append(formatCSVString(key.getParentKey()));
				fileWriter.append(CSV_DELIMITER);

				fileWriter.append(formatCSVString(key.getActualKey()));
				fileWriter.append(CSV_DELIMITER);

				Object jsonValue = value.getJsonValue();
				String jsnoStringVal = StringUtils.EMPTY;
				if (jsonValue instanceof String) {
					fileWriter.append(jsnoStringVal = (String) jsonValue);
				} else if (jsonValue instanceof Integer) {
					fileWriter.append(jsnoStringVal = Integer.toString((int) jsonValue));
				} else if (jsonValue instanceof Long) {
					fileWriter.append(jsnoStringVal = Long.toString((long) jsonValue));
				}
				fileWriter.append(CSV_DELIMITER);

				fileWriter.append(value.getMappedValue());
				fileWriter.append(CSV_DELIMITER);

				fileWriter.append(Integer.toString(value.getByteSize()));
				fileWriter.append(CSV_DELIMITER);

				fileWriter.append(formatCSVStringWithleadngTab(value.getHexValue()));
				fileWriter.append(CSV_DELIMITER);

				// populate formula for expected column
				String formula = StringUtils.EMPTY;
				String rowNo = Integer.toString((int) rowInd);
				if (jsonValue instanceof String && "SIGNATURE".equals(key.getActualKey())) {
					formula = "=DEC2HEX(CODE(C" + rowNo + "))&DEC2HEX(CODE(RIGHT(C" + rowNo + ")))";
				} else if (jsonValue instanceof String && StringUtils.EMPTY.equals(jsnoStringVal)) {
					formula = "=REPT(" + DOUBLE_QUOTE + SX3PropertiesConstants.NO_HEX_VALUE + DOUBLE_QUOTE + ",E"
							+ rowNo + ")";
				} else if (jsonValue instanceof String
						&& SX3PropertiesConstants.RESERVED_HEX_VALUE.equals(jsnoStringVal)
						&& key.getActualKey().contains(SX3PropertiesConstants.RESERVED_KEY)) {
					formula = "=REPT(" + DOUBLE_QUOTE + SX3PropertiesConstants.RESERVED_HEX_VALUE + DOUBLE_QUOTE + ",E"
							+ rowNo + ")";
				} else if (jsonValue instanceof String
						&& StringUtils.startsWithIgnoreCase(jsnoStringVal, SX3PropertiesConstants.HEX_PREFIX)) {
					formula = "=REPT(\"0\",(E" + rowNo + "*2 )- (LEN(C" + rowNo + ")-FIND(\"x\",C" + rowNo
							+ ")) ) & RIGHT(C" + rowNo + ",LEN(C" + rowNo + ")-FIND(\"x\",C" + rowNo + "))";
				} else if (Arrays.stream(bitMakFields).parallel().anyMatch(key.getActualKey()::contains)) {
					formula = "=REPT(\"0\",(E" + rowNo + "*2 )- LEN(BIN2HEX(C" + rowNo + "))) & BIN2HEX(C" + rowNo
							+ ")";
				} else if ((jsonValue instanceof String || jsonValue instanceof Integer || jsonValue instanceof Long)
						&& !"NA".equals(value.getMappedValue())) {
					formula = "=TEXT(D" + rowNo + ",REPT(\"0\",E" + rowNo + "*2))";
				} else if (jsonValue instanceof String) {
					StringBuilder sb = new StringBuilder();
					sb.append("=");
					for (int i = 1; i <= ((String) jsonValue).length(); i++) {
						sb.append("DEC2HEX(CODE(MID(C" + rowNo + "," + i + "," + i + ")))");
						sb.append("&\"00\"&");
					}
					sb.append("REPT(\"0\",((E" + rowNo + "*2)-(LEN(C" + rowNo + ")*4)))");
					formula = sb.toString();
				} else if (jsonValue instanceof Integer || jsonValue instanceof Long) {
					formula = "=REPT(\"0\",(E" + rowNo + "*2 )- LEN(DEC2HEX(C" + rowNo + "))) &DEC2HEX(C" + rowNo + ")";
				}

				fileWriter.append(formatCSVString(formula));
				fileWriter.append(CSV_DELIMITER);

				fileWriter.append(formatCSVString("=\"'\"&G" + rowNo));
				fileWriter.append(CSV_DELIMITER);

				fileWriter.append(formatCSVString("=IF(F" + rowNo + "=H" + rowNo + ",\"Pass\",\"Fail\")"));
				fileWriter.append(NEW_LINE_SEPARATOR);
				rowInd++;
			}

			log("CSV file was created successfully !!!");

		} catch (Exception e) {
			log("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				log("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}

	private static String formatCSVString(String formula) {

		// if a quote appears in a string, replace all by doubling the quotes.
		String replacedDataValue = ((String) formula).replaceAll(DOUBLE_QUOTE, "\"\"");
		// All the string data's should be enclosed with double quotes(").
		return DOUBLE_QUOTE + StringUtils.trim((String) replacedDataValue) + DOUBLE_QUOTE;

	}

	private static String formatCSVStringWithleadngTab(String dataValue) {

		// if a quote appears in a string, replace all by doubling the quotes.
		String replacedDataValue = ((String) dataValue).replaceAll(DOUBLE_QUOTE, "\"\"");
		// All the string data's should be enclosed with double quotes(").
		return DOUBLE_QUOTE + "'" + StringUtils.trim((String) replacedDataValue) + DOUBLE_QUOTE;

	}

	/**
	 * Returns csv file
	 * 
	 */
	public static File getCSVFile() {
		File jsonFile = BytesStreamsAndHexFileUtil.getConfigJsonFile();
		String configurationFileName = jsonFile.getName().substring(0, jsonFile.getName().lastIndexOf("."));
		File csvFile = new File(jsonFile.getParentFile().getAbsolutePath() + File.separator + configurationFileName
				+ SX3PropertiesConstants.CSV_FILE_EXTENSION);
		return csvFile;
	}

	public static byte[] hexStringToByteArray(String hexString) {
		byte[] val = new byte[hexString.length() / 2];
		for (int i = 0; i < val.length; i++) {
			int index = i * 2;
			int j = Integer.parseInt(hexString.substring(index, index + 2), 16);
			val[i] = (byte) j;
		}
		return val;
	}

	public static String[] hexStringToHexStringArray(String hexString) {
		String[] val = new String[hexString.length() / 2];
		for (int i = 0; i < val.length; i++) {
			int index = i * 2;
			val[i] = hexString.substring(index, index + 2);
		}
		return val;
	}

	protected static Properties gethexValueMappingProperties() {
		try {
			File jarPath = SX3Manager.getInstance().getInstallLocation();
			// to load application's properties, we use this class
			Properties mainProperties = new Properties();
			FileInputStream file;
			// the base folder is ./, the root of the main.properties file
			String path = jarPath.getParentFile().getAbsolutePath()
					+ SX3PropertiesConstants.HEX_VALUE_MAPPING_PROPERTIES;
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

	protected static boolean needCSVFileGeneration() {
		boolean result = false;
		try {
			result = Boolean.parseBoolean(
					gethexValueMappingProperties().getProperty(SX3PropertiesConstants.CSV_FILE_GENERATION_FLAG));
		} catch (Exception e) {
			log("Error while gtting CSV File Generation Property, Set value to false");
		}
		return result;
	}

	protected static String getFileNameWithoutExtension(File file) {
		String fileName = "";
		try {
			if (file != null && file.exists()) {
				String name = file.getName();
				fileName = name.replaceFirst("[.][^.]+$", "");
			}

		} catch (Exception e) {
			// ignore
		}
		return fileName;
	}

	protected static String leftPad(String val, int len) {
		return StringUtils.right(StringUtils.leftPad(val, len, "0"), len);
	}

	public static void log(Object thing) {
		System.out.println(String.valueOf(thing));
		
	}

}
