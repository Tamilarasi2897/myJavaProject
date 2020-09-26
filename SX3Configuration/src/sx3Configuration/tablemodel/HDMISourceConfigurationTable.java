package sx3Configuration.tablemodel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

public class HDMISourceConfigurationTable {
	
	private Label sno;
	private TextField EventName;
	private Label HDMIXLabel;
	private TextField HDMISourceRegisterAddress;
	private Label MaskXLabel;
	private TextField MaskValue;
	private Label CompareXLabel;
	private TextField CompareValue;
	private Button button;
	
	
	public Label getSno() {
		return sno;
	}
	public void setSno(Label sno) {
		this.sno = sno;
	}
	
	public TextField getEventName() {
		return EventName;
	}
	public void setEventName(TextField eventName) {
		EventName = eventName;
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
	public Label getHDMIXLabel() {
		return HDMIXLabel;
	}
	public void setHDMIXLabel(Label hDMIXLabel) {
		HDMIXLabel = hDMIXLabel;
	}
	public Label getMaskXLabel() {
		return MaskXLabel;
	}
	public void setMaskXLabel(Label maskXLabel) {
		MaskXLabel = maskXLabel;
	}
	public Label getCompareXLabel() {
		return CompareXLabel;
	}
	public void setCompareXLabel(Label compareXLabel) {
		CompareXLabel = compareXLabel;
	}

}
