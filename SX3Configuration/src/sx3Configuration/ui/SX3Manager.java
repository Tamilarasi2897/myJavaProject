package sx3Configuration.ui;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javafx.application.Platform;
import javafx.scene.web.WebView;
import sx3Configuration.mergertool.BytesStreamsAndHexFileUtil;
import sx3Configuration.model.SX3Configuration;
import sx3Configuration.util.SX3ConfiguartionHelp;

public class SX3Manager {

	private static SX3Manager sx3Manager;

	private SX3Configuration sx3Configuration;
	private SX3ConfiguartionHelp sx3ConfiguartionHelp;
	private WebView logView;
	private StringBuffer logDetails;
	private WebView helpView;
	
	private File installLocation;

	public File getInstallLocation() {

		if (installLocation == null) {
			String installPath = new String();
			installPath = SX3Manager.class.getProtectionDomain().getCodeSource().getLocation()
					.getPath();
			try {
				installPath = URLDecoder.decode(installPath, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			installLocation = new File(installPath);
		}
		return installLocation;
	}

	public static SX3Manager getInstance() {
		if (sx3Manager == null) {
			sx3Manager = new SX3Manager();
		}
		return sx3Manager;
	}

	public void setSx3Configuration(SX3Configuration sx3Configuration) {
		this.sx3Configuration = sx3Configuration;
	}

	public SX3Configuration getSx3Configuration() {
		return sx3Configuration;
	}
	
	public void setSx3ConfiguartionHelp(SX3ConfiguartionHelp sx3ConfiguartionHelp) {
		this.sx3ConfiguartionHelp = sx3ConfiguartionHelp;
	}
	
	public SX3ConfiguartionHelp getSx3ConfigurationHelp() {
		return sx3ConfiguartionHelp;
	}
	
	public void setLogView(WebView logView) {
		this.logView = logView;
	}
	
	public WebView getLogView() {
		return logView;
	}
	
	public void addLog(String logContent) {
		logDetails.append(logContent);
		renderLogView();
	}
	
	public void clearLog() {
		logDetails = new StringBuffer();
		logDetails.append(new Date() + " EZ-USB SX3 Configuration Utility Launched.<br>");
		renderLogView();
	}
	
	public void renderLogView() {
		
		Platform.runLater(() -> {
			logView.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
			logView.getEngine().loadContent(logDetails.toString(), "text/html");
		});
		
		
	}
	
	public void setLogDetails(StringBuffer logDetails) {
		this.logDetails = logDetails;
	}
	
	public StringBuffer getLogDetails() {
		if(logDetails == null) {
			logDetails = new StringBuffer();
		}
		return logDetails;
	}
	
	public void setHelpView(WebView helpView) {
		this.helpView = helpView;
	}
	
	public WebView getHelpView() {
		return helpView;
	}
	
	public void showHelpContent(String help) {
		helpView.getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
		helpView.getEngine()
				.loadContent(help);
	}
	
	public File getConfigurationLocation() {
		return BytesStreamsAndHexFileUtil.getConfigJsonFile().getParentFile();
	}

}
