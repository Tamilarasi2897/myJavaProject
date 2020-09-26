package ui.validations;

import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import sx3Configuration.model.SlaveFIFOSettings;

public class SlaveFifoSettingValidation {
	
	public static final String INVALID_NUMERIC_ERROR_MESSAGE = "Please enter numeric value only (0-9)";
	
	/** Endpoint Setting Validation 
	 * @param endpointTab 
	 * @param rootTab 
	 * @param uvcuacTabErrorList 
	 * @param endpointTab 
	 * @param rootTab 
	 * @param endpointTab 
	 * @param endpointTab2 
	 * @param performLoadAction 
	 * @param endpointSettingsTabErrorList **/
	public static void setupslaveFifoSettingsValidationForNumeric(Tab rootTab, Tab slaveFifoSabTab,
			TextField textField, Map<String, Boolean> endpointSettingsTabErrorList,
			String invalidNumericErrorMessage, SlaveFIFOSettings slaveFIFOSettings2, int fieldLength, String labelName) {
		final ContextMenu numericValidator = new ContextMenu();
		numericValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		numericValidator.setAutoHide(false);

		/** Decimal data Text Field Validation **/
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if(!newValue.equals("") && Integer.parseInt(newValue) > fieldLength) {
				textField.setText(oldValue);
				final StringBuffer sb = new StringBuffer(invalidNumericErrorMessage); 
				sb.append(" Max length "+fieldLength);
				SX3CommonUIValidations.showErrorRichText(textField, numericValidator, sb.toString());
			}else {
				if (!validateNumeric(newValue)) {
					textField.setText(oldValue);
					SX3CommonUIValidations.showTexErrorBorder(textField);
					SX3CommonUIValidations.showErrorRichText(textField, numericValidator, invalidNumericErrorMessage);
				} else {
					if(!newValue.equals("") && labelName.equals("Buffer Size")) {
						int bufferSize = Integer.parseInt(textField.getText());
						if(bufferSize%16!=0) {
							endpointSettingsTabErrorList.put(labelName, true);
							rootTab.setStyle("-fx-border-color:red;");
							slaveFifoSabTab.setStyle("-fx-border-color:red;");
							SX3CommonUIValidations.showTexErrorBorder(textField);
							SX3CommonUIValidations.showErrorRichText(textField, numericValidator, "Choose the size (in bytes) for each buffer (should be a multiple of 16)");
						}else {
							endpointSettingsTabErrorList.put(labelName, false);
							rootTab.setStyle("");
							slaveFifoSabTab.setStyle("");
							// Hiding the error message
							numericValidator.hide();
							SX3CommonUIValidations.removeTextErrorBorder(textField);
						}
					}else {
						rootTab.setStyle("");
						slaveFifoSabTab.setStyle("");
						// Hiding the error message
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
						if(!textField.getText().equals("") && labelName.equals("Burst Length")) {
							slaveFIFOSettings2.setBURST_LENGTH(Integer.parseInt(textField.getText()));
						}else if(!textField.getText().equals("") && labelName.equals("Buffer Count")) {
							slaveFIFOSettings2.setBUFFER_COUNT(Integer.parseInt(textField.getText()));
						}else if(!textField.getText().equals("") && labelName.equals("Buffer Size")) {
							slaveFIFOSettings2.setBUFFER_SIZE(Integer.parseInt(textField.getText()));
						}
					numericValidator.hide();
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
