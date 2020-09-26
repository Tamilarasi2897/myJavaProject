package sx3Configuration.ui.validations;

import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import sx3Configuration.mergertool.SX3PropertiesConstants;
import sx3Configuration.model.VideoSourceConfig;

public class VideoSourceConfigValidation {
	
	public static void setupNumericFieldValidation(TextField textField,
			String invalidNumericErrorMessage, VideoSourceConfig videoSourceConfig, int fieldLength, String labelName) {
			// Context Menu for error messages
					final ContextMenu numericValidator = new ContextMenu();
					numericValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
					numericValidator.setAutoHide(false);

					/** Decimal data Text Field Validation **/
					textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
						if (!newValue.equals("") && !validateNumeric(newValue)) {
							textField.setText(oldValue);
							SX3CommonUIValidations.showTexErrorBorder(textField);
							SX3CommonUIValidations.showErrorRichText(textField, numericValidator, invalidNumericErrorMessage);
						} else {
							if(labelName.equals("DataSize")) {
								if(!newValue.equals("") && Integer.parseInt(newValue) > 4) {
									textField.setText(oldValue);
									final StringBuffer sb = new StringBuffer(); 
									sb.append(" Max Value 4");
									SX3CommonUIValidations.showErrorRichText(textField, numericValidator, sb.toString());
								}else {
									// Hiding the error message
									numericValidator.hide();
									SX3CommonUIValidations.removeTextErrorBorder(textField);
								}
							} else if(labelName.equals("RegisterSize")) {
								if(!newValue.equals("") && Integer.parseInt(newValue) > 2) {
									textField.setText(oldValue);
									final StringBuffer sb = new StringBuffer(); 
									sb.append(" Max Value 2");
									SX3CommonUIValidations.showErrorRichText(textField, numericValidator, sb.toString());
								}else {
									// Hiding the error message
									numericValidator.hide();
									SX3CommonUIValidations.removeTextErrorBorder(textField);
								}
							}
						}
//						if(labelName.equals("DataSize")) {
//							if(!newValue.equals("") && Integer.parseInt(newValue) > 4) {
//								textField.setText(oldValue);
//								final StringBuffer sb = new StringBuffer(); 
//								sb.append(" Max Value 4");
//								SX3CommonUIValidations.showErrorRichText(textField, numericValidator, sb.toString());
//							}else {
//								if (!validateNumeric(newValue)) {
//									textField.setText(oldValue);
//									SX3CommonUIValidations.showTexErrorBorder(textField);
//									SX3CommonUIValidations.showErrorRichText(textField, numericValidator, invalidNumericErrorMessage);
//								} else {
//									// Hiding the error message
//									numericValidator.hide();
//									SX3CommonUIValidations.removeTextErrorBorder(textField);
//								}
//							}
//						} else if(labelName.equals("RegisterSize")) {
//							if(!newValue.equals("") && Integer.parseInt(newValue) > 2) {
//								textField.setText(oldValue);
//								final StringBuffer sb = new StringBuffer(); 
//								sb.append(" Max Value 2");
//								SX3CommonUIValidations.showErrorRichText(textField, numericValidator, sb.toString());
//							}else {
//								if (!validateNumeric(newValue)) {
//									textField.setText(oldValue);
//									SX3CommonUIValidations.showTexErrorBorder(textField);
//									SX3CommonUIValidations.showErrorRichText(textField, numericValidator, invalidNumericErrorMessage);
//								} else {
//									// Hiding the error message
//									numericValidator.hide();
//									SX3CommonUIValidations.removeTextErrorBorder(textField);
//								}
//							}
//						}
					});

					textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
						@Override
						public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
								Boolean newPropertyValue) {
							if (newPropertyValue && !validateNumeric(textField.getText())) {
								SX3CommonUIValidations.showErrorRichText(textField, numericValidator, invalidNumericErrorMessage);
							} else {
								if(validateNumeric(textField.getText())) {
									setVideoSourceConfigDataInJson(textField,videoSourceConfig,labelName);
								}
								SX3CommonUIValidations.removeTextErrorBorder(textField);
								numericValidator.hide();
							}
						}
					});
	}
	
	private static void setVideoSourceConfigDataInJson(TextField textField,
			VideoSourceConfig videoSourceConfig, String labelName) {
		if(labelName.equals("DataSize")) {
			if(textField.getText().equals("")) {
				videoSourceConfig.setI2C_SLAVE_DATA_SIZE(0);
			}else {
				videoSourceConfig.setI2C_SLAVE_DATA_SIZE(Integer.parseInt(textField.getText()));
			}
		} else if (labelName.equals("RegisterSize")) {
			if(textField.getText().equals("")) {
				videoSourceConfig.setI2C_SLAVE_REGISTER_SIZE(0);
			}else {
				videoSourceConfig.setI2C_SLAVE_REGISTER_SIZE(Integer.parseInt(textField.getText()));
			}
		} else if (labelName.equals("Slave_Address")) {
			videoSourceConfig.setI2C_SLAVE_ADDRESS(textField.getText().toString().isEmpty() ? "" : SX3PropertiesConstants.HEX_PREFIX + textField.getText().toString());
		}

	}

	/** Checck Numeric Validation **/
	private static boolean validateNumeric(String newValue) {
		if (newValue.equals("")) {
			return true;
		} else {
			return SX3CommonUIValidations.isValidNumeric(newValue);
		}
	}

	public static void setupValidationForHexTextField(Tab tab, Tab rootTab, Map<String, Boolean> videoSourceConfigTabErrorList, 
			boolean performLoadAction, TextField textField, String errorMessage,
			VideoSourceConfig videoSourceConfig, int fieldLength, String labelName) {
		// Context Menu for error messages
		final ContextMenu vendorIdValidator = new ContextMenu();
		vendorIdValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		vendorIdValidator.setAutoHide(false);
		
		if(performLoadAction == true) {
			if (!validateHexString(textField.getText())) {
				videoSourceConfigTabErrorList.put(labelName, true);
				rootTab.setStyle("-fx-border-color:red;");
				tab.setStyle("-fx-border-color:red;");
				SX3CommonUIValidations.showTexErrorBorder(textField);
			}
		}

		/** Hex data Text Field Validation **/
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if (!validateHexString(newValue)) {
				textField.setText(oldValue);
				SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, errorMessage);
			}else {
				if(newValue.length() > 2) {
					textField.setText(oldValue);
					SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, "Max length 2 character.");
				}
				if(!newValue.equals("") && Integer.parseInt(newValue, 16) > fieldLength) {
					textField.setText(oldValue);
					if(fieldLength == 65535) {
						SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, "Max value 2 bytes.");
					}else if(fieldLength == 255) {
						SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, "Max value 1 bytes.");
					}
				}else {
//					 Hiding the error message
					videoSourceConfigTabErrorList.put(labelName, false);
					if(!videoSourceConfigTabErrorList.containsValue(true)) {
						tab.setStyle("");
						rootTab.setStyle("");
					}
					vendorIdValidator.hide();
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
						setVideoSourceConfigDataInJson(textField, videoSourceConfig, labelName);
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

	public static ContextMenu setupValidationForHDMIHexTextField(Tab videoSourceConfigTab, Tab subTab,
			Map<String, Boolean> videoSourceConfigTabErrorList, boolean performLoadAction,
			TextField textField, String errorMessage, int fieldLength, String labelName) {
		final ContextMenu vendorIdValidator = new ContextMenu();
		vendorIdValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		vendorIdValidator.setAutoHide(false);
		
		if(performLoadAction == true) {
			if (!validateHexString(textField.getText())) {
				videoSourceConfigTabErrorList.put(labelName, true);
				videoSourceConfigTab.setStyle("-fx-border-color:red;");
				subTab.setStyle("-fx-border-color:red;");
				SX3CommonUIValidations.showTexErrorBorder(textField);
			}
		}

		/** Hex data Text Field Validation **/
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if (!validateHexString(newValue)) {
				textField.setText(oldValue);
				SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, errorMessage);
			}else {
				if(!newValue.equals("") && Integer.parseInt(newValue, 16) > fieldLength) {
					textField.setText(oldValue);
					if(fieldLength == 65535) {
						SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, "Max size 2 bytes.");
					}else if(fieldLength == 255) {
						SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, "Max size 1 bytes.");
					}
				}else {
					// Hiding the error message
					videoSourceConfigTabErrorList.put(labelName, false);
					if(!videoSourceConfigTabErrorList.containsValue(true)) {
						videoSourceConfigTab.setStyle("");
						subTab.setStyle("");
					}
					vendorIdValidator.hide();
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
		return vendorIdValidator;
		
	}

}
