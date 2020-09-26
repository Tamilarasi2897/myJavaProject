package sx3Configuration.TableModel;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CameraAndProcessingUnitControlsTableModel {
	
	private Label labelName;
	private CheckBox enableLabel;
	private TextField min;
	private TextField max;
	private TextField defaultValue;
	private TextField step;
	private TextField len;
	private Label extraColumn;
	private TextField registerAddress;
	
	public Label getLabelName() {
		return labelName;
	}
	public void setLabelName(Label labelName) {
		this.labelName = labelName;
	}
	public CheckBox getEnableLabel() {
		return enableLabel;
	}
	public void setEnableLabel(CheckBox enableLabel) {
		this.enableLabel = enableLabel;
	}
	public TextField getMin() {
		return min;
	}
	public void setMin(TextField min) {
		this.min = min;
	}
	public TextField getMax() {
		return max;
	}
	public void setMax(TextField max) {
		this.max = max;
	}
	public TextField getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(TextField defaultValue) {
		this.defaultValue = defaultValue;
	}
	public TextField getStep() {
		return step;
	}
	public void setStep(TextField step) {
		this.step = step;
	}
	public Label getExtraColumn() {
		return extraColumn;
	}
	public void setExtraColumn(Label extraColumn) {
		this.extraColumn = extraColumn;
	}
	public TextField getRegisterAddress() {
		return registerAddress;
	}
	public void setRegisterAddress(TextField registerAddress) {
		this.registerAddress = registerAddress;
	}
	public TextField getLen() {
		return len;
	}
	public void setLen(TextField len) {
		this.len = len;
	}

}
