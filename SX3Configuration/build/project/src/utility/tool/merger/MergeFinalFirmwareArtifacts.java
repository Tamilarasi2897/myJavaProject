package utility.tool.merger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;

import sx3Configuration.model.SX3Configuration;

/**
 * To create the final firmware file (.img) to program to the connected SX3
 * chip. Merger utility will be run when the user clicks on the Program button
 * in the SX3 Configuration utility
 * 
 * @author mahesh
 *
 */
public class MergeFinalFirmwareArtifacts {

	/**
	 * Merger the following files inputs: <br>
	 * 1. SX3 Base firmware file (.img) <BR>
	 * 2. FPGA Bit File (.bit) <BR>
	 * 3. SX3 Configuration Table Raw Data File (.bin)
	 */
	public static void mergeAllFiles() {

		Properties configProperties = BytesStreamsAndHexFileUtil.getConfigProperties();
		BytesStreamsAndHexFileUtil.log("================================================");
		BytesStreamsAndHexFileUtil.log("Started Programming: 1. Merging input files... ");
		BytesStreamsAndHexFileUtil.log("================================================");
		// Input File#1 SX3 Base firmware file (.img)
		File baseImageFile = BytesStreamsAndHexFileUtil.getBaseImageFile(configProperties);
		byte[] readBaseFirmwareImgFileByteData = BytesStreamsAndHexFileUtil.readBaseFirmwareImgFile(baseImageFile);

		// check foe SX3 Configuration json file, if not exist do not merge.
		File sx3ConfigJsonFile = BytesStreamsAndHexFileUtil.getConfigJsonFile();
		if (!sx3ConfigJsonFile.exists()) {
			BytesStreamsAndHexFileUtil.log("SX3 Configuration Json file not found, Unable to create merge file");
			return;
		}
		// Input File#2 SX3 Configuration Table Raw Data File (Generated hex
		// file)
		File sx3ConfigHexFile = SX3ConfigHexFileUtil.getSX3ConfigHexFile();
		byte[] sx3ConfigUtilityDataOutputByteData = new byte[0];
		if (sx3ConfigHexFile.exists()) {
			sx3ConfigUtilityDataOutputByteData = BytesStreamsAndHexFileUtil.readAlternateImpl(sx3ConfigHexFile);
		} else {
			BytesStreamsAndHexFileUtil.log("Generated Hex file not found. " + SX3PropertiesConstants.NO_PART_OF_DATA);
		}

		// Input File#3 VideoSourceConfig files uploaded to GUI by user.
		// This file should be converted to hex.
		byte[] videoSourceConfigByteData = new byte[0];
		List<File> videoSoureConfigFiles = SX3ConfigHexFileUtil.getVideoSoureConfigFiles(sx3ConfigJsonFile);
		if (videoSoureConfigFiles != null && !videoSoureConfigFiles.isEmpty()) {
			for (File videoSourceConfigFile : videoSoureConfigFiles) {
				if (videoSourceConfigFile != null && videoSourceConfigFile.exists()) {
					File videoSourceHexFile = SX3ConfigHexFileUtil.getVideoSourceConfigHexFile(
							BytesStreamsAndHexFileUtil.getFileNameWithoutExtension(videoSourceConfigFile));
					videoSourceConfigByteData = BytesStreamsAndHexFileUtil.readAlternateImpl(videoSourceHexFile);
				} else {
					BytesStreamsAndHexFileUtil
							.log(SX3PropertiesConstants.VIDEO_SOURCE_CONFIG_FILE_PATH_NOT_ADDED_IN_JSON
									+ SX3PropertiesConstants.NO_PART_OF_DATA);
				}
			}
		} else {
			BytesStreamsAndHexFileUtil.log(SX3PropertiesConstants.NO_FILE_PATHS_ADDED_FOR_VIDEO_SOURCE_CONFIG
					+ SX3PropertiesConstants.NO_PART_OF_DATA);
		}

		// Input File#4 FPGA Bit File (.bit)
		File fifoBitFile = getFifoMasterGitFile(sx3ConfigJsonFile);
			byte[]  fifoMasterByteData = BytesStreamsAndHexFileUtil.readAlternateImpl(fifoBitFile);
		
		// Merge all the byte data's and write it into final .img file
		try {
			byte[] combinedData = addAll(readBaseFirmwareImgFileByteData, sx3ConfigUtilityDataOutputByteData,
					videoSourceConfigByteData, fifoMasterByteData);
			File sx3MergedHexFile = getSX3MergedHexFile();
			BytesStreamsAndHexFileUtil.write(combinedData, sx3MergedHexFile);
		} catch (Exception e) {
			BytesStreamsAndHexFileUtil.log(e);
		}

	}

	public static byte[] addAll(final byte[] array1, byte[] array2, byte[] array3, byte[] array4) {
		byte[] joinedArray = new byte[array1.length + array2.length + array3.length + array4.length];

		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		System.arraycopy(array3, 0, joinedArray, array2.length, array3.length);
		System.arraycopy(array4, 0, joinedArray, array3.length, array4.length);
		return joinedArray;
	}

	private static File getFifoMasterGitFile(File jsonFile) {
		// Gson parser object to parse json file
		Gson gson = new Gson();
		try {
			BufferedReader br = new BufferedReader(new FileReader(jsonFile));
			// convert the json string back to object
			SX3Configuration sx3Obj = gson.fromJson(br, SX3Configuration.class);
			String bitFilePath = sx3Obj.getFIFO_MASTER_CONFIG().getBIT_FILE_PATH();
			if (!bitFilePath.isEmpty()) {
				return new File(bitFilePath);
			} else {
				BytesStreamsAndHexFileUtil.log("FIFO Master(FPGA) Bit File (.bit) not found.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns merged Hex file
	 * 
	 * @param configProperties
	 */
	public static File getSX3MergedHexFile() {
		File jsonFile = BytesStreamsAndHexFileUtil.getConfigJsonFile();
		File mergedHexFile = new File(jsonFile.getParentFile().getAbsolutePath() + File.separator + "sx3MergedFile"
				+ SX3PropertiesConstants.HEX_FILE_EXTENSION);
		return mergedHexFile;
	}
}
