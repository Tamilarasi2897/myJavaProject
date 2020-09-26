package sx3Configuration.TableModel;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

public class FormatAndResolutionTableModel {
	
	private Label sno;
	private ComboBox<String> imageFormat;
	private TextField bitPerPixcel;
	private CheckBox stillCapture;
	private CheckBox supportedInFS;
	private CheckBox supportedInHS;
	private CheckBox supportedInSS;
	private TextField frameRateInFS;
	private TextField frameRateInHS;
	private TextField frameRateInSS;
	private TextField resolutionH;
	private TextField resolutionV;
	private Button button;
	
	
//	public FormatAndResolutions(int sno, String imageFormat, String bitPerPixcel,
//			String pixcelPerPCLK, String hResolution, String vResolution,
//			String frameRate, String stillCapture, String supportedInLS,
//			String supportedInHS, String supportedInSS, String frameRateInLS,
//			String frameRateInHS, String frameRateInSS, Button button) {
//		super();
//		this.sno = sno;
//		this.imageFormat = imageFormat;
//		this.bitPerPixcel = bitPerPixcel;
//		this.pixcelPerPCLK = pixcelPerPCLK;
//		this.hResolution = hResolution;
//		this.vResolution = vResolution;
//		this.frameRate = frameRate;
//		this.stillCapture = stillCapture;
//		this.supportedInLS = supportedInLS;
//		this.supportedInHS = supportedInHS;
//		this.supportedInSS = supportedInSS;
//		this.frameRateInLS = frameRateInLS;
//		this.frameRateInHS = frameRateInHS;
//		this.frameRateInSS = frameRateInSS;
//		this.button = new Button();
//	}
	

	public Label getSno() {
		return sno;
	}

	public void setSno(Label sno) {
		this.sno = sno;
	}

	public ComboBox<String> getImageFormat() {
		return imageFormat;
	}

	public void setImageFormat(ComboBox<String> imageFormat) {
		this.imageFormat = imageFormat;
	}

	public TextField getBitPerPixcel() {
		return bitPerPixcel;
	}

	public void setBitPerPixcel(TextField bitPerPixcel) {
		this.bitPerPixcel = bitPerPixcel;
	}

	public CheckBox getStillCapture() {
		return stillCapture;
	}

	public void setStillCapture(CheckBox stillCapture) {
		this.stillCapture = stillCapture;
	}

	public CheckBox getSupportedInFS() {
		return supportedInFS;
	}

	public void setSupportedInFS(CheckBox supportedInFS) {
		this.supportedInFS = supportedInFS;
	}

	public CheckBox getSupportedInHS() {
		return supportedInHS;
	}

	public void setSupportedInHS(CheckBox supportedInHS) {
		this.supportedInHS = supportedInHS;
	}

	public CheckBox getSupportedInSS() {
		return supportedInSS;
	}

	public void setSupportedInSS(CheckBox supportedInSS) {
		this.supportedInSS = supportedInSS;
	}

	public TextField getFrameRateInFS() {
		return frameRateInFS;
	}

	public void setFrameRateInFS(TextField frameRateInFS) {
		this.frameRateInFS = frameRateInFS;
	}

	public TextField getFrameRateInHS() {
		return frameRateInHS;
	}

	public void setFrameRateInHS(TextField frameRateInHS) {
		this.frameRateInHS = frameRateInHS;
	}

	public TextField getFrameRateInSS() {
		return frameRateInSS;
	}

	public void setFrameRateInSS(TextField frameRateInSS) {
		this.frameRateInSS = frameRateInSS;
	}
	
	public TextField getResolutionH() {
		return resolutionH;
	}

	public void setResolutionH(TextField resolutionH) {
		this.resolutionH = resolutionH;
	}

	public TextField getResolutionV() {
		return resolutionV;
	}

	public void setResolutionV(TextField resolutionV) {
		this.resolutionV = resolutionV;
	}

	public Button getButton() {
		ImageView sensorConfigImage = new ImageView(getClass().getResource("/resources/sensorConfig.png").toString());
		sensorConfigImage.setFitHeight(15);
		sensorConfigImage.setFitWidth(15);
		button.setGraphic(sensorConfigImage);
		button.setTooltip(new Tooltip("Sensor Config"));
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}
	
}
