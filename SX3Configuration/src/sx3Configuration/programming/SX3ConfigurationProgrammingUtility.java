package sx3Configuration.programming;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import sx3Configuration.programming.OSValidator.Level;
import sx3Configuration.ui.SX3Manager;

public class SX3ConfigurationProgrammingUtility {

	public String program(String imagePath) {
		// creating list of process
		try {

			OSValidator osValidator = new OSValidator();
			Level os = osValidator.getOS();

			String lastMessage = new String();

			switch (os) {
			case WINDOWS:

				lastMessage = flashOnWindows(imagePath);
				return lastMessage;

			case MAC:
			case LINUX:
				lastMessage = flashOnMacLinux(imagePath, os);
				return lastMessage;

			default:
				return "OS Not Supported";
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String flashOnMacLinux(String imagePath, Level os) throws IOException {
		File jarPath = SX3Manager.getInstance().getInstallLocation();
		String utilityPath = null;
		switch (os) {
		case MAC:
			utilityPath = "/cyfwprog/macos";
			break;
		case LINUX:
			utilityPath = "/cyfwprog/linux";
			break;

		default:
			break;
		}
		
		File downloadFile = new File(jarPath.getParentFile().getAbsolutePath() + utilityPath + "/download_fx3");
		if(!downloadFile.exists()) {
			return "Error : Firmware Download Utility Missing.";
		} else {
			downloadFile.setExecutable(true);
		}

		List<String> list = new ArrayList<String>();
		list.add("./download_fx3");
		list.add("-t");
		list.add("SPI");
		list.add("-i");
		list.add(imagePath);

		// create the process
		ProcessBuilder build = new ProcessBuilder(list);
		build.directory(new File(jarPath.getParentFile().getAbsolutePath() + utilityPath));

		// checking the command in list
		System.out.println("command: " + build.command());
		Process process = build.start();

		// for reading the ouput from stream
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String s = null;
		String lastMessage = null;
		while ((s = stdInput.readLine()) != null) {
			System.out.println(s);
			lastMessage = s;
		}

		stdInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		s = null;
		while ((s = stdInput.readLine()) != null) {
			System.out.println(s);
			lastMessage = s;
		}

		System.out.println("Verdict >> " + lastMessage);
		return lastMessage;
	}

	private String flashOnWindows(String imagePath) throws IOException {
		File jarPath = SX3Manager.getInstance().getInstallLocation();
		String cyfwprog = jarPath.getParentFile().getAbsolutePath() + "/cyfwprog/cyfwprog.exe";
		
		if(!new File(cyfwprog).exists()) {
			return "Error : Programming Utility Missing";
		}

		List<String> list = new ArrayList<String>();
		list.add(cyfwprog);
		list.add("-fw");
		list.add(imagePath);
		list.add("-dest");
		list.add("SPI_FLASH");
		list.add("-v");

		// create the process
		ProcessBuilder build = new ProcessBuilder(list);

		// checking the command in list
		System.out.println("command: " + build.command());
		Process process = build.start();

		// for reading the ouput from stream
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String s = null;
		String lastMessage = null;
		while ((s = stdInput.readLine()) != null) {
			System.out.println(s);
			lastMessage = s;
		}
		System.out.println("Verdict >> " + lastMessage);
		return lastMessage;
	}

	public List<String> getDeviceList() {
		List<String> deviceList = new ArrayList<String>();

		try {

			OSValidator osValidator = new OSValidator();
			Level os = osValidator.getOS();

			File jarPath = SX3Manager.getInstance().getInstallLocation();

			switch (os) {
			case WINDOWS:

				String cyfwfinddevice = jarPath.getParentFile().getAbsolutePath() + "/cyfwfinddevice/ConsoleApp3.exe";

				if (new File(cyfwfinddevice).exists()) {

					List<String> list = new ArrayList<String>();
					list.add(cyfwfinddevice);

					// create the process
					ProcessBuilder build = new ProcessBuilder(list);

					// checking the command in list
					System.out.println("command: " + build.command());
					Process process = build.start();

					// for reading the ouput from stream
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String s = null;
					int count = 0;
					while ((s = stdInput.readLine()) != null) {
//						if (count % 4 == 0) {
							if (s.contains("BootLoader")) {
								System.out.println(s);
								deviceList.add(s);
							}
							count++;
//						}
					}
					System.out.println("Verdict >> " + deviceList.size());
					break;
				} else {
					deviceList.add("Error : Device Detection Utility Missing.");
					break;
				}

			case LINUX:
			case MAC:
				LibUSBProgrammingUtility utility = new LibUSBProgrammingUtility();
				deviceList.addAll(utility.getDeviceList());
				break;

			default:
				break;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return deviceList;
	}

	public static void main(String[] args) {
		SX3ConfigurationProgrammingUtility programmingUtility = new SX3ConfigurationProgrammingUtility();
		String program = programmingUtility
				.program("C:\\Program Files (x86)\\Cypress\\EZ-USB FX3 SDK\\1.3\\util\\cyfwprog\\CyBootProgrammer.img");
		System.out.println(program);
	}
}
