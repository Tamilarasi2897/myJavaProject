package sx3Configuration.ui.validations;

import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

public class SlaveFifoSettingValidation {
	
	/** Endpoint Setting Validation 
	 * @param endpointTab 
	 * @param rootTab 
	 * @param uvcuacTabErrorList 
	 * @param endpointTab 
	 * @param rootTab 
	 * @param endpointTab 
	 * @param endpointTab2 
	 * @param performLoadAction 
	 * @param endpointSettingsTabErrorList 
	 * @param performLoadAction 
	 * @param i 
	 * @param string **/
	public static void setupslaveFifoSettingsValidationForNumeric(Tab rootTab, Tab slaveFifoSabTab,
			TextField textField, Map<String, Boolean> endpointSettingsTabErrorList,
			boolean performLoadAction, String invalidNumericErrorMessage, int fieldLength, String labelName) {
		final ContextMenu numericValidator = new ContextMenu();
		numericValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		numericValidator.setAutoHide(false);

		if(performLoadAction) {
			if(!textField.getText().equals("") && !textField.getText().equals("0") && labelName.equals("Buffer Size")) {
				int bufferSize = Integer.parseInt(textField.getText());
				if(bufferSize%16!=0) {
					endpointSettingsTabErrorList.put(labelName, false);
					rootTab.setStyle("-fx-border-color:red;");
					slaveFifoSabTab.setStyle("-fx-border-color:red;");
					// Hiding the error message
					SX3CommonUIValidations.showTexErrorBorder(textField);
				}
		}}
		
		/** Decimal data Text Field Validation **/
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if(validateNumeric(newValue) && !newValue.equals("") && Integer.parseInt(newValue) > fieldLength) {
				textField.setText(oldValue);
				final StringBuffer sb = new StringBuffer(invalidNumericErrorMessage); 
				sb.append("Max size 2 bytes");
				SX3CommonUIValidations.showErrorRichText(textField, numericValidator, sb.toString());
			}else {
				if (!validateNumeric(newValue)) {
					textField.setText(oldValue);
					SX3CommonUIValidations.showTexErrorBorder(textField);
					SX3CommonUIValidations.showErrorRichText(textField, numericValidator, invalidNumericErrorMessage);
				} else {
					if(!newValue.equals("") && !newValue.equals("0") && labelName.equals("Buffer Size")) {
						int bufferSize = Integer.parseInt(textField.getText());
						if(bufferSize%16==0) {
							endpointSettingsTabErrorList.put(labelName, false);
							rootTab.setStyle("");
							slaveFifoSabTab.setStyle("");
							// Hiding the error message
							numericValidator.hide();
							SX3CommonUIValidations.removeTextErrorBorder(textField);
						}else {
							endpointSettingsTabErrorList.put(labelName, true);
							rootTab.setStyle("-fx-border-color:red;");
							slaveFifoSabTab.setStyle("-fx-border-color:red;");
							SX3CommonUIValidations.showTexErrorBorder(textField);
							SX3CommonUIValidations.showErrorRichText(textField, numericValidator, "Choose the size (in bytes) for each buffer (should be a multiple of 16)");
						}
					}else {
						rootTab.setStyle("");
						slaveFifoSabTab.setStyle("");
						numericValidator.hide();
						SX3CommonUIValidations.removeTextErrorBorder(textField);
					}
				}
			}
		});

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
					numericValidator.hide();
					if(!textField.getText().equals("")) {
						int bufferSize = Integer.parseInt(textField.getText());
						if (validateNumeric(textField.getText()) && bufferSize%16==0) {
							rootTab.setStyle("");
							slaveFifoSabTab.setStyle("");
							SX3CommonUIValidations.removeTextErrorBorder(textField);
						}
					}
			}
		});
		
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
