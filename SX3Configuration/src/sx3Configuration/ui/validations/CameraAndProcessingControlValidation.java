package sx3Configuration.ui.validations;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;

public class CameraAndProcessingControlValidation implements ChangeListener<String>{

	TextField textField;
	String errorMessage;
	String labelName;
	TextField minMaxField;
	ContextMenu contecontextMenu;
	
	public CameraAndProcessingControlValidation(TextField textField,String errorMessage,String labelName,TextField minMaxField,ContextMenu contecontextMenu) {
		this.textField = textField;
		this.errorMessage = errorMessage;
		this.labelName = labelName;
		this.minMaxField = minMaxField;
		this.contecontextMenu = contecontextMenu;
	}
	
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		contecontextMenu.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		contecontextMenu.setAutoHide(false);
		
			if(!newValue.equals("") && validateNumeric(newValue) && Long.parseLong(newValue) > 4294967295L) {
				textField.setText(oldValue);
				final StringBuffer sb = new StringBuffer(); 
				sb.append(" Max size 4 bytes.");
				SX3CommonUIValidations.showErrorRichText(textField, contecontextMenu, sb.toString());
			}else {
				if (!validateNumeric(newValue)) {
					textField.setText(oldValue);
					SX3CommonUIValidations.showTexErrorBorder(textField);
					SX3CommonUIValidations.showErrorRichText(textField, contecontextMenu, errorMessage);
				} else {
					if(labelName.equals("MIN")) {
						if(!newValue.equals("") && Long.parseLong(newValue) > Long.parseLong(minMaxField.getText())) {
							SX3CommonUIValidations.showTexErrorBorder(textField);
							SX3CommonUIValidations.showErrorRichText(textField, contecontextMenu, "Min value should be less than max value(Min<Max)");
						}else {
							SX3CommonUIValidations.removeTextErrorBorder(textField);
							SX3CommonUIValidations.removeTextErrorBorder(minMaxField);
						}
					}else if(labelName.equals("MAX")) {
						if(!newValue.equals("") && Long.parseLong(newValue) < Long.parseLong(minMaxField.getText())) {
							SX3CommonUIValidations.showTexErrorBorder(textField);
							SX3CommonUIValidations.showErrorRichText(textField, contecontextMenu, "Max value should be greater than min value(Max>Min)");
						}else {
							SX3CommonUIValidations.removeTextErrorBorder(textField);
							SX3CommonUIValidations.removeTextErrorBorder(minMaxField);
						}
					}else {
						// Hiding the error message
						contecontextMenu.hide();
						SX3CommonUIValidations.removeTextErrorBorder(textField);
					}
				}
			}
		
		
	}
	
	/** Checck Numeric Validation **/
	private static boolean validateNumeric(String newValue) {
		if(newValue.equals("")) {
			return true;
		}else {
			return SX3CommonUIValidations.isValidNumeric(newValue);
		}
	}
	

}
