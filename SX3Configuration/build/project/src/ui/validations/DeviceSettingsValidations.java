package ui.validations;

import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

public class DeviceSettingsValidations {

	public static final String INVALID_HEX_ERROR_MESSAGE = "Please enter hexadecimal value only (0-F).";

	public static final String INVALID_ALPHANUMERIC_ERROR_MESSAGE = "Please enter alphaNumeric value only (0-9,a-z)";

	public static final String INVALID_NUMERIC_ERROR_MESSAGE = "Please enter numeric value only (0-9)";
	
	public static final String INVALID_FILE_NAME = "A file name can't contain any of the following Characters\n \\ / : * ? \\\" < > |";
	

	public static void setupValidationForTextField(Map<String, Boolean> rootTabErrorList, Tab rootTab, TextField textField, String errorMessage, int length, 
			String labelName, boolean performLoadAction) {
		// Context Menu for error messages
		final ContextMenu vendorIdValidator = new ContextMenu();
		vendorIdValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		vendorIdValidator.setAutoHide(false);
		if(performLoadAction == true) {
			if (!validateHexString(textField.getText())) {
				rootTabErrorList.put(labelName, true);
				rootTab.setStyle("-fx-border-color:red;");
				SX3CommonUIValidations.showTexErrorBorder(textField);
			}
		}

		/** Hex data Text Field Validation **/
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if(newValue.length() > length) {
				textField.setText(oldValue);
				final StringBuffer sb = new StringBuffer(errorMessage); 
				sb.append(" Max length "+length);
				SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, sb.toString());
			}else {
				if (!validateHexString(newValue)) {
					textField.setText(oldValue);
					rootTabErrorList.put(labelName, true);
					rootTab.setStyle("-fx-border-color:red;");
					SX3CommonUIValidations.showTexErrorBorder(textField);
						SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, errorMessage);
				} else {
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
				
				if (newPropertyValue && !validateHexString(textField.getText())) {
					SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, errorMessage);
				} else {
					vendorIdValidator.hide();
				}
			}
		});
	}

	public static boolean validateHexString(String hexVal) {
		if(hexVal == null || hexVal.equals("")) {
			return true;
		}else {
			return SX3CommonUIValidations.isValidHex(hexVal);
		}
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
				final StringBuffer sb = new StringBuffer(invalidAlphanumericErrorMessage); 
				sb.append(" Max length "+maxLength);
				SX3CommonUIValidations.showErrorRichText(textField, alphaNumeriValidator, sb.toString());
			}
//			else {
//				if (!validateAlphaNumericString(newValue)) {
//					textField.setText(oldValue);
//					rootTab.setStyle("-fx-border-color:red;");
//					rootTabErrorList.put(labelName, true);
//					SX3CommonUIValidations.showTexErrorBorder(textField);
//						SX3CommonUIValidations.showErrorRichText(textField, alphaNumeriValidator,
//								invalidAlphanumericErrorMessage);
//				} else {
					// Hiding the error message
//					rootTabErrorList.put(labelName, false);
//					if(!rootTabErrorList.containsValue(true)) {
//						rootTab.setStyle("");
//					}
//					alphaNumeriValidator.hide();
//					SX3CommonUIValidations.removeTextErrorBorder(textField);
//				}
//			}
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

	private static boolean validateAlphaNumericString(String newValue) {
		return SX3CommonUIValidations.isValidAlphaNumeric(newValue);
	}

	public static void validateSerialNumberTextField(Map<String, Boolean> deviceSettingTabErrorList, Tab deviceSettingsTab, TextField textField, String invalidNumericErrorMessage, 
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
			if(newValue.length() > fieldLength ) {
				textField.setText(oldValue);
				final StringBuffer sb = new StringBuffer(invalidNumericErrorMessage); 
				sb.append(" Max length "+fieldLength);
				SX3CommonUIValidations.showErrorRichText(textField, numericValidator, sb.toString());
			}else {
				String substring = textField.getText().substring(textField.getText().length() - 1);
				if (!validateNumeric(substring)) {
					textField.setText(oldValue);
					deviceSettingsTab.setStyle("-fx-border-color:red;");
					deviceSettingTabErrorList.put(labelName, true);
					SX3CommonUIValidations.showTexErrorBorder(textField);
					SX3CommonUIValidations.showErrorRichText(textField, numericValidator, invalidNumericErrorMessage+" Last character should be numeric.");
				} else {
					deviceSettingTabErrorList.put(labelName, false);
					if(!deviceSettingTabErrorList.containsValue(true)) {
						deviceSettingsTab.setStyle("");
					}
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
				String substring = "";
				if(!textField.getText().equals("")) {
					substring = textField.getText().substring(textField.getText().length() - 1);
				}
				if (newPropertyValue || !validateNumeric(substring)) {
					deviceSettingsTab.setStyle("-fx-border-color:red;");
					deviceSettingTabErrorList.put(labelName, true);
					SX3CommonUIValidations.showTexErrorBorder(textField);
				} else {
					deviceSettingTabErrorList.put(labelName, false);
					if(!deviceSettingTabErrorList.containsValue(true)) {
						deviceSettingsTab.setStyle("");
						SX3CommonUIValidations.removeTextErrorBorder(textField);
					}
					numericValidator.hide();
				}
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
			if(!validateNumeric(newValue) || (!newValue.equals("") && Integer.parseInt(newValue) > maxLength)) {
				textField.setText(oldValue);
				final StringBuffer sb = new StringBuffer(invalidNumericErrorMessage); 
				sb.append(" Max value "+maxLength);
				SX3CommonUIValidations.showErrorRichText(textField, numericValidator, sb.toString());
			} else {
					// Hiding the error message
					numericValidator.hide();
					SX3CommonUIValidations.removeTextErrorBorder(textField);
				}
//			}
		});

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue && !validateNumeric(textField.getText())) {
					SX3CommonUIValidations.showErrorRichText(textField, numericValidator, invalidNumericErrorMessage);
				} else {
					numericValidator.hide();
				}
			}
		});

	}

	private static boolean validateNumeric(String newValue) {
		return SX3CommonUIValidations.isValidNumeric(newValue);
	}

	public static void fileNameValidation(TextField textField, String invalidFileName) {
		final ContextMenu fileNameValidator = new ContextMenu();
		fileNameValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		fileNameValidator.setAutoHide(false);
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if(!validateFileName(newValue)) {
				textField.setStyle("-fx-border-color:red;");
				SX3CommonUIValidations.showErrorRichText(textField, fileNameValidator, invalidFileName);
			}else {
				textField.setStyle("");
				fileNameValidator.hide();
			}
		});
				
		
	}

	private static boolean validateFileName(String newValue) {
		if(newValue == null || newValue.equals("")) {
			return true;
		}else {
			return SX3CommonUIValidations.isValidFileName(newValue);
		}
	}

}
