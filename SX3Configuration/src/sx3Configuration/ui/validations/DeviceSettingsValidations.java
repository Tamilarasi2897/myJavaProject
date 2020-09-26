package sx3Configuration.ui.validations;

import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

public class DeviceSettingsValidations {

	public static void setupValidationForTextField(Map<String, Boolean> rootTabErrorList, Tab rootTab, TextField textField, String errorMessage, 
			int byteSize, String labelName, boolean performLoadAction) {
		// Context Menu for error messages
		final ContextMenu vendorIdValidator = new ContextMenu();
		vendorIdValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		vendorIdValidator.setAutoHide(false);
		if(performLoadAction == true) {
			if (!textField.getText().equals("") && !validateHexString(textField.getText())) {
				rootTabErrorList.put(labelName, true);
				rootTab.setStyle("-fx-border-color:red;");
				SX3CommonUIValidations.showTexErrorBorder(textField);
			}
		}

		/** Hex data Text Field Validation **/
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if (!validateHexString(newValue)) {
				textField.setText(oldValue);
				rootTabErrorList.put(labelName, true);
				SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, errorMessage);
			}else {
				if(newValue.length() > 4) {
					textField.setText(oldValue);
					SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, "Max length 4 character.");
				}else if(!newValue.equals("") && Integer.parseInt(newValue, 16)>byteSize) {
					textField.setText(oldValue);
					if(byteSize == 65535) {
						SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, "Max size 2 bytes.");
					}else if(byteSize == 255) {
						SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, "Max size 1 bytes.");
					}
				}else {
				// Hiding the error message
					rootTabErrorList.put(labelName, false);
					vendorIdValidator.hide();
					if(!rootTabErrorList.containsValue(true)) {
						rootTab.setStyle("");
					}
					SX3CommonUIValidations.removeTextErrorBorder(textField);
				}
			}
		});

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				SX3CommonUIValidations.removeTextErrorBorder(textField);
					vendorIdValidator.hide();
			}
		});
	}

	public static void setupValidationForAlphNumericTextField(Map<String, Boolean> rootTabErrorList, Tab rootTab, TextField textField,
			String invalidAlphanumericErrorMessage, String labelName, boolean performLoadAction, int maxLength) {
		// Context Menu for error messages
		final ContextMenu alphaNumeriValidator = new ContextMenu();
		alphaNumeriValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		alphaNumeriValidator.setAutoHide(false);
		
		if(performLoadAction == true) {
			if (!validateAlphaNumericString(textField.getText())) {
				rootTabErrorList.put(labelName, true);
				rootTab.setStyle("-fx-border-color:red;");
				SX3CommonUIValidations.showTexErrorBorder(textField);
			}
		}


		/** Hex data Text Field Validation **/
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if(newValue.length() > maxLength) {
				textField.setText(oldValue);
				final StringBuffer sb = new StringBuffer(); 
				sb.append(" Max length "+maxLength);
				SX3CommonUIValidations.showErrorRichText(textField, alphaNumeriValidator, sb.toString());
			}
		});

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue && !validateAlphaNumericString(textField.getText())) {
					SX3CommonUIValidations.showErrorRichText(textField, alphaNumeriValidator,
							invalidAlphanumericErrorMessage);
				} else {
					alphaNumeriValidator.hide();
				}
			}
		});
	}

	public static void validateSerialNumberTextField(ContextMenu numericValidator2, Map<String, Boolean> deviceSettingTabErrorList, Tab deviceSettingsTab, TextField textField, String invalidNumericErrorMessage, 
			String labelName, int fieldLength, boolean performLoadAction) {
		// Context Menu for error messages
		final ContextMenu numericValidator = new ContextMenu();
		numericValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		numericValidator.setAutoHide(false);
		
		if(performLoadAction == true) {
			String substring = "";
			if(!textField.getText().equals("")) {
				substring = textField.getText().substring(textField.getText().length() - 1);
			}
			if (!validateAlphaNumericString(textField.getText()) || !validateNumeric(substring)) {
				deviceSettingTabErrorList.put(labelName, true);
				deviceSettingsTab.setStyle("-fx-border-color:red;");
				SX3CommonUIValidations.showTexErrorBorder(textField);
			}
		}

		/** Decimal data Text Field Validation **/
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			numericValidator2.hide();
			if(newValue.length() > fieldLength ) {
				textField.setText(oldValue);
				final StringBuffer sb = new StringBuffer(); 
				sb.append(" Max length "+fieldLength);
				SX3CommonUIValidations.showErrorRichText(textField, numericValidator, sb.toString());
			}else {
				if (!validateAlphaNumericString(newValue)) {
					textField.setText(oldValue);
//					deviceSettingsTab.setStyle("-fx-border-color:red;");
					deviceSettingTabErrorList.put(labelName, true);
					SX3CommonUIValidations.showTexErrorBorder(textField);
					SX3CommonUIValidations.showErrorRichText(textField, numericValidator, invalidNumericErrorMessage+" .");
				} else {
					deviceSettingTabErrorList.put(labelName, false);
					if(!deviceSettingTabErrorList.containsValue(true)) {
						deviceSettingsTab.setStyle("");
					}
					// Hiding the error message
					numericValidator.hide();
					SX3CommonUIValidations.removeTextErrorBorder(textField);
				}
				numericValidator.hide();
				SX3CommonUIValidations.removeTextErrorBorder(textField);
			}
		});

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				String substring = "";
				if(!textField.getText().equals("")) {
					substring = textField.getText().substring(textField.getText().length() - 1);
				}
				if (!validateNumeric(substring)) {
					deviceSettingsTab.setStyle("-fx-border-color:red;");
					deviceSettingTabErrorList.put(labelName, true);
					SX3CommonUIValidations.showTexErrorBorder(textField);
				} else {
					deviceSettingTabErrorList.put(labelName, false);
					if(!deviceSettingTabErrorList.containsValue(true)) {
						deviceSettingsTab.setStyle("");
						SX3CommonUIValidations.removeTextErrorBorder(textField);
					}
				}
				numericValidator.hide();
			}
		});

	}
	
	public static void validateFifoClockFrequencyTextField(TextField textField, String invalidNumericErrorMessage, 
			int maxLength) {
		// Context Menu for error messages
		final ContextMenu numericValidator = new ContextMenu();
		numericValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		numericValidator.setAutoHide(false);

		/** Decimal data Text Field Validation **/
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			SX3CommonUIValidations.removeTextErrorBorder(textField);
			if(validateNumeric(newValue) && !newValue.equals("") && Integer.parseInt(newValue) > maxLength) {
				textField.setText(oldValue);
				SX3CommonUIValidations.showErrorRichText(textField, numericValidator, " Max value "+maxLength);
			}else {
				if(!validateNumeric(newValue)) {
					textField.setText(oldValue);
					SX3CommonUIValidations.showErrorRichText(textField, numericValidator, invalidNumericErrorMessage);
				} else {
						// Hiding the error message
						numericValidator.hide();
						SX3CommonUIValidations.removeTextErrorBorder(textField);
					}
			}
		});

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if(textField.getText().equals("")) {
					textField.setText("100");
				}
				SX3CommonUIValidations.removeTextErrorBorder(textField);
					numericValidator.hide();
			}
		});

	}

	public static void fileNameValidation(TextField textField, String invalidFileName) {
		final ContextMenu fileNameValidator = new ContextMenu();
		fileNameValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		fileNameValidator.setAutoHide(false);
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if(!validateFileName(newValue)) {
				textField.setText(oldValue);
				textField.setStyle("-fx-border-color:red;");
				SX3CommonUIValidations.showErrorRichText(textField, fileNameValidator, invalidFileName);
			}else {
				textField.setStyle("");
				fileNameValidator.hide();
			}
		});
				
		
	}
	
	private static boolean validateNumeric(String newValue) {
		if(newValue == null || newValue.equals("")) {
			return true;
		}else {
			return SX3CommonUIValidations.isValidNumeric(newValue);
		}
	}

	private static boolean validateFileName(String newValue) {
		if(newValue == null || newValue.equals("")) {
			return true;
		}else {
			return SX3CommonUIValidations.isValidFileName(newValue);
		}
	}
	
	private static boolean validateAlphaNumericString(String newValue) {
		if(newValue == null || newValue.equals("")) {
			return true;
		}else {
			return SX3CommonUIValidations.isValidAlphaNumeric(newValue);
		}
	}
	
	public static boolean validateHexString(String hexVal) {
		if(hexVal == null || hexVal.equals("")) {
			return true;
		}else {
			return SX3CommonUIValidations.isValidHex(hexVal);
		}
	}

}
