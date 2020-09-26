package utility.tool.merger;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import sx3Configuration.ui.SX3ConfigPreference;

public class BytesStreamsAndHexFileUtil {

	private static final int BUFFER_SIZE = 256 * 1000; // 256 KB

	private BytesStreamsAndHexFileUtil() {
		// don't instantiate this util class
	}

	/**
	 * Read the content of .img file in firmware folder into the final firmware
	 * file, max limit will be 256KB, if it is less than max size just append FF
	 * for rest of the size.
	 */
	protected static byte[] readBaseFirmwareImgFile(File file) {
		log("Reading in (.img) file named : " + file.getName());
		byte[] result = new byte[(int) BUFFER_SIZE];
		try {
			try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
				// Reads up to certain bytes of data from this input stream into
				// an array of bytes.
				input.read(result);
			}
		} catch (FileNotFoundException ex) {
			log("Base Firmware (.img) file not found.");
		} catch (IOException ex) {
			log(ex);
		}
		return result;
	}

	protected static File getBaseImageFile(Properties configProperties) {
		File imgFile = null;
		try {
			String sx3ImgPath = configProperties.getProperty(SX3PropertiesConstants.SX3_BASE_IMG_PATH_KEY).trim();
			String sx3ImgFileName = configProperties.getProperty(SX3PropertiesConstants.SX3_BASE_IMG_FILE_NAME_KEY)
					.trim();
			imgFile = new File(
					sx3ImgPath + File.separator + sx3ImgFileName + SX3PropertiesConstants.IMAGE_FILE_EXTENSION);
		} catch (Exception e) {
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

	public static String convertJsonValuesToHex(Object value, String objName, String key,
			Map<String, Integer> configProperitesMap, Map<String, String> hexValPropertiesMap)
			throws UnsupportedEncodingException {
		String defValue = key.contains(SX3PropertiesConstants.RESERVED_KEY) ? SX3PropertiesConstants.RESERVED_HEX_VALUE
				: SX3PropertiesConstants.NO_HEX_VALUE;
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
				return appendExtraBytes(new byte[0], byteSize, defValue, 1);
			}
			// If the value is hex then don't convert
			if (((String) value).startsWith(SX3PropertiesConstants.HEX_PREFIX)) {
				String hexString = new String((String) value).substring(2, ((String) value).length()).toLowerCase()
						.trim();
				return StringUtils.leftPad(hexString, byteSize * 2,
						"0"); /* each byte is 2 bit size for hex */
				// return convertByteToHexString(fromHexString(hexString), 1);
			}
			// if the json value has hex mapped value, then return mapped value
			else if (hexValPropertiesMap.containsKey(fullyQualifiedKeyForMapValue)) {
				return StringUtils.leftPad(hexValPropertiesMap.get(fullyQualifiedKeyForMapValue), byteSize * 2, "0");
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
				sb.append(
						StringUtils.leftPad(hexValPropertiesMap.get(fullyQualifiedKeyForMapValue), byteSize * 2, "0"));
			} else {
				sb.append(StringUtils.leftPad(Integer.toHexString(intVal), byteSize * 2,
						"0")); /* each byte is 2 bit size for hex */
			}

			return sb.toString();
		}else if (value instanceof Long) {
			long longVal = (long) value;
			String fullyQualifiedKeyForMapValue = objName + "." + key + "."
					+ (Long.toString(longVal)).replaceAll(" ", "_");
			StringBuilder sb = new StringBuilder();
			if (hexValPropertiesMap.containsKey(fullyQualifiedKeyForMapValue)) {
				sb.append(
						StringUtils.leftPad(hexValPropertiesMap.get(fullyQualifiedKeyForMapValue), byteSize * 2, "0"));
			} else {
				sb.append(StringUtils.leftPad(Long.toHexString(longVal), byteSize * 2,
						"0")); /* each byte is 2 bit size for hex */
			}

			return sb.toString();
		}

		if (bytes.length == byteSize) {
			return convertByteToHexString(bytes, 2);
		}

		return convertByteToHexString(bytes, 2) + appendExtraBytes(bytes, byteSize, defValue, 2);
	}

	public static String convertByteToHexString(byte[] bytes, int eachCharByteSize) {
		StringBuilder result = new StringBuilder();
		for (byte temp : bytes) {
			// bytes widen to int, need mask,prevent sign extension
			int decimal = (int) temp & 0xff;
			String hex = Integer.toHexString(decimal);

			if (hex.length() < 2) {
				// pad with leading zero if needed
				result.append(StringUtils.leftPad(hex, 2, "0"));
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

	public static byte[] hexStringToByteArray(String hexString) {
		byte[] val = new byte[hexString.length() / 2];
		for (int i = 0; i < val.length; i++) {
			int index = i * 2;
			int j = Integer.parseInt(hexString.substring(index, index + 2), 16);
			val[i] = (byte) j;
		}
		return val;
	}

	public static Properties getConfigProperties() {
		try {
			File jarPath = new File(
					BytesStreamsAndHexFileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			// to load application's properties, we use this class
			Properties mainProperties = new Properties();
			FileInputStream file;
			// the base folder is ./, the root of the main.properties file
			String path = jarPath.getParentFile().getAbsolutePath()
					+ SX3PropertiesConstants.SX3_CONFIG_MAPPING_PROPERTIES;
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

	protected static Properties gethexValueMappingProperties() {
		try {
			File jarPath = new File(
					BytesStreamsAndHexFileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath());
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

	public static void log(Object thing) {
		System.out.println(String.valueOf(thing));
	}

}
