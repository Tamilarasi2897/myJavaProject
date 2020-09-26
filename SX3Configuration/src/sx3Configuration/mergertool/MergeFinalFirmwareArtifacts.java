package sx3Configuration.mergertool;

import java.io.File;
import java.util.List;
import java.util.Map;

import sx3Configuration.model.SX3Configuration;
import sx3Configuration.model.SensorConfig;
import sx3Configuration.ui.SX3Manager;

/**
 * To create the final firmware file (.img) to program to the connected SX3
 * chip. Merger utility will be run when the user clicks on the Program button
 * in the SX3 Configuration utility
 * 
 * @author mahesh
 *
 */
public class MergeFinalFirmwareArtifacts {

	private static final String OFFSET_PLUS_SIZE = "FFFFFFFFFFFFFFFF";
	public static final int OFFSET_RESERVED_SIZE = 32; // 4 bytes for size and
														// address for 4
														// inputs(2 video, 1
														// fifo, 1 hex)

	/**
	 * Merger the following files inputs: <br>
	 * 1. SX3 Base firmware file (.img) <BR>
	 * 2. FPGA Bit File (.bit) <BR>
	 * 3. SX3 Configuration Table Raw Data File (.bin)
	 */
	public static HexConversionErrors mergeAllFiles() {

		StringBuilder offSetSb = new StringBuilder();
		BytesStreamsAndHexFileUtil.log("================================================");
		BytesStreamsAndHexFileUtil.log("Started Programming: 1. Merging input files... ");
		BytesStreamsAndHexFileUtil.log("================================================");
		// Input File#1 SX3 Base firmware file (.img)
		File baseImageFile = BytesStreamsAndHexFileUtil.getBaseImageFile();

		if (baseImageFile == null || !baseImageFile.exists()) {
			return HexConversionErrors.BASE_IMAGE_FILE_MISSING;
		}

		byte[] readBaseFirmwareImgFileByteData = BytesStreamsAndHexFileUtil.readBaseFirmwareImgFile(baseImageFile);

		// check for SX3 Configuration Object, if not exist do not merge.
		SX3Configuration sx3Obj = SX3Manager.getInstance().getSx3Configuration();
		if (sx3Obj == null) {
			SX3Manager.getInstance().addLog("SX3 Configuration Object not created, Unable to create merge file <br>");
			BytesStreamsAndHexFileUtil.log("SX3 Configuration Object not created, Unable to create merge file");
			return HexConversionErrors.HEX_CONVERSION_FAILED;
		}
		int destLendth = OFFSET_RESERVED_SIZE;
		// Input File#2 SX3 Configuration Table Raw Data File (Generated hex
		// file)
		File sx3ConfigHexFile = SX3ConfigHexFileUtil.getSX3ConfigHexFile();
		byte[] sx3ConfigUtilityDataOutputByteData = new byte[0];
		if (sx3ConfigHexFile.exists()) {
			sx3ConfigUtilityDataOutputByteData = BytesStreamsAndHexFileUtil.readAlternateImpl(sx3ConfigHexFile);
			destLendth += readBaseFirmwareImgFileByteData.length;
			offSetSb.append(getOffSetDetails(sx3ConfigUtilityDataOutputByteData.length, destLendth));
		} else {
			SX3Manager.getInstance().addLog("Generated Hex file not found. " + SX3PropertiesConstants.NO_PART_OF_DATA +"<br>");
			BytesStreamsAndHexFileUtil.log("Generated Hex file not found. " + SX3PropertiesConstants.NO_PART_OF_DATA);
			return HexConversionErrors.HEX_FILE_MISSING;
		}

		// Input File#3 VideoSourceConfig files uploaded to GUI by user.
		// This data should be converted to hex.
		Map<String, Integer> sp = new SX3ParametersByteSizeBuilder().getParametersByteSizeMap(
				SX3PropertiesConstants.SENSOR_CONFIG_FIELDS, SX3PropertiesConstants.SENSOR_CONFIG);
		int ras = sp.get("REGISTER_ADDRESS").intValue() * 2;
		int rvs = sp.get("REGISTER_VALUE").intValue() * 2;
		int sas = sp.get("SLAVE_ADDRESS").intValue() * 2;

		byte[] vidSrcByteArray_1 = new byte[0];
		byte[] vidSrcByteArray_2 = new byte[0];
		List<Map<Integer, List<SensorConfig>>> videoSoureConfigFiles = SX3ConfigHexFileUtil
				.getVideoSoureSensorConfigList();
		StringBuilder vsb = new StringBuilder();
		if (videoSoureConfigFiles != null && !videoSoureConfigFiles.isEmpty()) {

			if (videoSoureConfigFiles.size() == 1 && videoSoureConfigFiles.get(0).keySet().iterator().next() == 1) {
				List<SensorConfig> firstSenConfg = videoSoureConfigFiles.get(0).values().iterator().next();
				if (!firstSenConfg.isEmpty()) {
					for (SensorConfig sensorConfig : firstSenConfg) {
						vsb.append(BytesStreamsAndHexFileUtil.leftPad(
								sensorConfig.getREGISTER_ADDRESS().replaceAll(SX3PropertiesConstants.HEX_PREFIX, ""),
								ras));
						vsb.append(BytesStreamsAndHexFileUtil.leftPad(
								sensorConfig.getREGISTER_VALUE().replaceAll(SX3PropertiesConstants.HEX_PREFIX, ""),
								rvs));
						vsb.append(BytesStreamsAndHexFileUtil.leftPad(
								sensorConfig.getSLAVE_ADDRESS().replaceAll(SX3PropertiesConstants.HEX_PREFIX, ""),
								sas));
					}
					vidSrcByteArray_1 = BytesStreamsAndHexFileUtil.hexStringToByteArray(vsb.toString());
					destLendth += sx3ConfigUtilityDataOutputByteData.length;
					offSetSb.append(getOffSetDetails(vidSrcByteArray_1.length, destLendth)); // 1st
																								// endpoint
					offSetSb.append(OFFSET_PLUS_SIZE); // 2nd endpoint
				} else {
					destLendth += sx3ConfigUtilityDataOutputByteData.length;
					offSetSb.append(OFFSET_PLUS_SIZE); // 1st endpoint
					offSetSb.append(OFFSET_PLUS_SIZE); // 2nd endpoint
				}
			} else if (videoSoureConfigFiles.size() == 1
					&& videoSoureConfigFiles.get(0).keySet().iterator().next() == 2) {
				List<SensorConfig> secondSenConfg = videoSoureConfigFiles.get(0).values().iterator().next();
				if (!secondSenConfg.isEmpty()) {
					for (SensorConfig sensorConfig : secondSenConfg) {
						vsb.append(BytesStreamsAndHexFileUtil.leftPad(
								sensorConfig.getREGISTER_ADDRESS().replaceAll(SX3PropertiesConstants.HEX_PREFIX, ""),
								ras));
						vsb.append(BytesStreamsAndHexFileUtil.leftPad(
								sensorConfig.getREGISTER_VALUE().replaceAll(SX3PropertiesConstants.HEX_PREFIX, ""),
								rvs));
						vsb.append(BytesStreamsAndHexFileUtil.leftPad(
								sensorConfig.getSLAVE_ADDRESS().replaceAll(SX3PropertiesConstants.HEX_PREFIX, ""),
								sas));
					}
					vidSrcByteArray_1 = BytesStreamsAndHexFileUtil.hexStringToByteArray(vsb.toString());
					destLendth += sx3ConfigUtilityDataOutputByteData.length;
					offSetSb.append(OFFSET_PLUS_SIZE); // 1st endpoint
					offSetSb.append(getOffSetDetails(vidSrcByteArray_1.length, destLendth)); // 2nd
																								// endpoint
				} else {
					destLendth += sx3ConfigUtilityDataOutputByteData.length;
					offSetSb.append(OFFSET_PLUS_SIZE); // 1st endpoint
					offSetSb.append(OFFSET_PLUS_SIZE); // 2nd endpoint
				}
			} else if (videoSoureConfigFiles.size() == 2) {
				for (Map<Integer, List<SensorConfig>> map : videoSoureConfigFiles) {
					List<SensorConfig> senConfgs = map.values().iterator().next();
					if (!senConfgs.isEmpty()) {
						StringBuilder vsb2 = new StringBuilder();
						for (SensorConfig sensorConfig : senConfgs) {
							vsb2.append(BytesStreamsAndHexFileUtil.leftPad(sensorConfig.getREGISTER_ADDRESS()
									.replaceAll(SX3PropertiesConstants.HEX_PREFIX, ""), ras));
							vsb2.append(BytesStreamsAndHexFileUtil.leftPad(
									sensorConfig.getREGISTER_VALUE().replaceAll(SX3PropertiesConstants.HEX_PREFIX, ""),
									rvs));
							vsb2.append(BytesStreamsAndHexFileUtil.leftPad(
									sensorConfig.getSLAVE_ADDRESS().replaceAll(SX3PropertiesConstants.HEX_PREFIX, ""),
									sas));
						}
						if (map.keySet().iterator().next() == 1) {
							destLendth += sx3ConfigUtilityDataOutputByteData.length;
							vidSrcByteArray_1 = BytesStreamsAndHexFileUtil.hexStringToByteArray(vsb2.toString());
							offSetSb.append(getOffSetDetails(vidSrcByteArray_1.length, destLendth)); // 1st
																										// endpoint
						}
						if (map.keySet().iterator().next() == 2) {
							int internDestLen = destLendth + vidSrcByteArray_1.length;
							vidSrcByteArray_2 = BytesStreamsAndHexFileUtil.hexStringToByteArray(vsb2.toString());
							offSetSb.append(getOffSetDetails(vidSrcByteArray_2.length, internDestLen)); // 2nd

						}

					} else {
						destLendth += sx3ConfigUtilityDataOutputByteData.length;
						offSetSb.append(OFFSET_PLUS_SIZE); // 1st endpoint
						offSetSb.append(OFFSET_PLUS_SIZE); // 2nd endpoint
					}
				}
			}

		} else {
			destLendth += sx3ConfigUtilityDataOutputByteData.length;
			offSetSb.append(OFFSET_PLUS_SIZE);
			offSetSb.append(OFFSET_PLUS_SIZE);
			BytesStreamsAndHexFileUtil.log(SX3PropertiesConstants.NO_FILE_PATHS_ADDED_FOR_VIDEO_SOURCE_CONFIG
					+ SX3PropertiesConstants.NO_PART_OF_DATA);
		}

		byte[] combinedVidSrcBytedata = new byte[vidSrcByteArray_1.length + vidSrcByteArray_2.length];
		System.arraycopy(vidSrcByteArray_1, 0, combinedVidSrcBytedata, 0, vidSrcByteArray_1.length);
		System.arraycopy(vidSrcByteArray_2, 0, combinedVidSrcBytedata, vidSrcByteArray_1.length,
				vidSrcByteArray_2.length);

		// Input File#4 FPGA Bit File (.bit)
		byte[] fifoMasterByteData = new byte[0];
		if (sx3Obj.getFIFO_MASTER_CONFIG().getFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN().equals("Enable")) {
			File fifoBitFile = getFifoMasterGitFile();
			if (fifoBitFile != null) {
				fifoMasterByteData = BytesStreamsAndHexFileUtil.readAlternateImpl(fifoBitFile);
				destLendth += combinedVidSrcBytedata.length;
				offSetSb.append(getOffSetDetails(fifoMasterByteData.length, destLendth));
			} else {
				return HexConversionErrors.BIT_FILE_MISSING;
			}
		} else {
			offSetSb.append(OFFSET_PLUS_SIZE);// if in case bit file not
			// uploaded, set the offset to
			// dummy hex value i.e.FF..
		}

		// Merge all the byte data's and write it into final .img file
		try {
			byte[] combinedData = addAll(readBaseFirmwareImgFileByteData, sx3ConfigUtilityDataOutputByteData,
					combinedVidSrcBytedata, fifoMasterByteData, offSetSb.toString());
			File sx3MergedHexFile = getSX3MergedHexFile();
			BytesStreamsAndHexFileUtil.write(combinedData, sx3MergedHexFile);
		} catch (Exception e) {
			BytesStreamsAndHexFileUtil.log(e);
			return HexConversionErrors.IMAGE_CONVERSION_FAILED;
		}

		return HexConversionErrors.MERGE_SUCCESS;

	}

	public static byte[] addAll(final byte[] array1, byte[] array2, byte[] array3, byte[] array4, String offsetDet) {

		byte[] joinedArray = new byte[array1.length + OFFSET_RESERVED_SIZE + array2.length + array3.length
				+ array4.length];
		// ** Fix the length of the img to 256kb, do it like this **//
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		// get offset details for 3 inputs
		System.arraycopy(BytesStreamsAndHexFileUtil.hexStringToByteArray(offsetDet), 0, joinedArray, array1.length,
				OFFSET_RESERVED_SIZE);
		System.arraycopy(array2, 0, joinedArray, array1.length + OFFSET_RESERVED_SIZE, array2.length);
		System.arraycopy(array3, 0, joinedArray, array1.length + OFFSET_RESERVED_SIZE + array2.length, array3.length);
		if (array4 != null) {
			System.arraycopy(array4, 0, joinedArray,
					array1.length + OFFSET_RESERVED_SIZE + array2.length + array3.length, array4.length);
		}
		return joinedArray;
	}

	private static String getOffSetDetails(int byteArrayLength, int prevEndTotSize) {
		StringBuilder result = new StringBuilder();
		try {
			// Byte size
			result.append(BytesStreamsAndHexFileUtil.leftPad(Integer.toHexString(byteArrayLength),
					4 * 2)); /* each byte is 2 bit size for hex */
			// Starting Position in hex
			result.append(BytesStreamsAndHexFileUtil.leftPad(Integer.toHexString(prevEndTotSize),
					4 * 2)); /* each byte is 2 bit size for hex */
		} catch (Exception e) {
			BytesStreamsAndHexFileUtil.log(e);
		}

		return result.toString();

	}

	private static File getFifoMasterGitFile() {
		try {
			// get sx3 config object from the manager
			SX3Configuration sx3Obj = SX3Manager.getInstance().getSx3Configuration();

			if (sx3Obj.getFIFO_MASTER_CONFIG().getFIFO_MASTER_CONFIGURATION_DOWNLOAD_EN().equals("Enable")) {
				String bitFilePath =BytesStreamsAndHexFileUtil.getConfigJsonFile().getParentFile().getAbsolutePath() +"/"+sx3Obj.getFIFO_MASTER_CONFIG().getBIT_FILE_PATH();
				if (!bitFilePath.isEmpty()) {
					File file = new File(bitFilePath);
					if (file.exists()) {
						return file;
					} else {
						BytesStreamsAndHexFileUtil.log("FIFO Master(FPGA) not found.");
						SX3Manager.getInstance().getLogDetails().append("FIFO Master(FPGA) not found.");
					}
				} else {
					BytesStreamsAndHexFileUtil.log("FIFO Master(FPGA) not found.");
					SX3Manager.getInstance().getLogDetails().append("FIFO Master(FPGA) not found.");
				}
			} else {
				BytesStreamsAndHexFileUtil.log("FIFO Configuration Download is not Enabled so BIT File omitted.");
				SX3Manager.getInstance().getLogDetails()
						.append("FIFO Configuration Download is not Enabled so BIT File omitted.");
			}
		} catch (Exception e) {
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
		String configurationFileName = jsonFile.getName().substring(0, jsonFile.getName().lastIndexOf("."));
		File mergedHexFile = new File(jsonFile.getParentFile().getAbsolutePath() + File.separator
				+ configurationFileName + SX3PropertiesConstants.IMAGE_FILE_EXTENSION);
		return mergedHexFile;
	}
}
