package sx3Configuration.TableModel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

public class HDMISourceConfigurationTable {
	
	private Label sno;
	private TextField HDMISourceRegisterAddress;
	private TextField MaskValue;
	private TextField CompareValue;
	private Button button;
	public Label getSno() {
		return sno;
	}
	public void setSno(Label sno) {
		this.sno = sno;
	}
	public TextField getHDMISourceRegisterAddress() {
		return HDMISourceRegisterAddress;
	}
	public void setHDMISourceRegisterAddress(TextField hDMISourceRegisterAddress) {
		HDMISourceRegisterAddress = hDMISourceRegisterAddress;
	}
	public TextField getMaskValue() {
		return MaskValue;
	}
	public void setMaskValue(TextField maskValue) {
		MaskValue = maskValue;
	}
	public TextField getCompareValue() {
		return CompareValue;
	}
	public void setCompareValue(TextField compareValue) {
		CompareValue = compareValue;
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
