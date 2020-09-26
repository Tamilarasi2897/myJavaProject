package ui.validations;

import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import sx3Configuration.model.EndpointSettings;
import sx3Configuration.model.FormatAndResolutions;

public class UVCSettingsValidation {

	public static final String INVALID_NUMERIC_ERROR_MESSAGE = "Please enter numeric value only (0-9)";

	public static final String INVALID_HEX_ERROR_MESSAGE = "Please enter hexadecimal value only (0-F)";

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
	 * @param maxValue **/
	public static void setupEndpointValidationForNumeric(Tab rootTab, Tab endpointTab, Tab endpointTab2, TextField textField,
			boolean performLoadAction, Map<String, Boolean> endpointSettingsTabErrorList, String invalidNumericErrorMessage, 
			EndpointSettings endpointSettingsJson, int maxValue, String labelName) {
		// Context Menu for error messages
				final ContextMenu numericValidator = new ContextMenu();
				numericValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
				numericValidator.setAutoHide(false);
				if(performLoadAction == true) {
					if (!validateHexString(textField.getText())) {
						endpointSettingsTabErrorList.put(labelName, true);
						rootTab.setStyle("-fx-border-color:red;");
						endpointTab.setStyle("-fx-border-color:red;");
						endpointTab2.setStyle("-fx-border-color:red;");
						SX3CommonUIValidations.showTexErrorBorder(textField);
					}
				}

				/** Decimal data Text Field Validation **/
				textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
					if(!newValue.equals("") && Integer.parseInt(newValue) > maxValue) {
						textField.setText(oldValue);
						final StringBuffer sb = new StringBuffer(invalidNumericErrorMessage); 
						sb.append(" Max length "+maxValue+".");
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
									endpointTab.setStyle("-fx-border-color:red;");
									endpointTab2.setStyle("-fx-border-color:red;");
									SX3CommonUIValidations.showTexErrorBorder(textField);
									SX3CommonUIValidations.showErrorRichText(textField, numericValidator, "Choose the size (in bytes) for each buffer (should be a multiple of 16)");
								}else {
									endpointSettingsTabErrorList.put(labelName, false);
									rootTab.setStyle("");
									endpointTab.setStyle("");
									endpointTab2.setStyle("");
									// Hiding the error message
									numericValidator.hide();
									SX3CommonUIValidations.removeTextErrorBorder(textField);
								}
							}else {
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
						numericValidator.hide();
//						SX3CommonUIValidations.removeTextErrorBorder(textField);
								if(!textField.getText().equals("") && labelName.equals("Burst Length")) {
									endpointSettingsJson.setBURST_LENGTH(Integer.parseInt(textField.getText()));
								}else if(!textField.getText().equals("") && labelName.equals("Buffer Count")) {
									endpointSettingsJson.setBUFFER_COUNT(Integer.parseInt(textField.getText()));
								}else if(!textField.getText().equals("") && labelName.equals("Buffer Size")) {
									endpointSettingsJson.setBUFFER_SIZE(Integer.parseInt(textField.getText()));
								}
//							}
							numericValidator.hide();
//						}
					}
				});
	}
	
	public static void setupFormatAndResolutionValidationForNumeric(TextField textField,
			String invalidNumericErrorMessage, FormatAndResolutions formatAndResolutionJson, String labelName) {
			// Context Menu for error messages
					final ContextMenu numericValidator = new ContextMenu();
					numericValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
					numericValidator.setAutoHide(false);

					/** Decimal data Text Field Validation **/
					textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
						if(!newValue.equals("") && Integer.parseInt(newValue) > 65535) {
							textField.setText(oldValue);
							final StringBuffer sb = new StringBuffer(invalidNumericErrorMessage); 
							sb.append(" Max length 65535.");
							SX3CommonUIValidations.showErrorRichText(textField, numericValidator, sb.toString());
						}else {
							if (!validateNumeric(newValue)) {
								textField.setText(oldValue);
								SX3CommonUIValidations.showTexErrorBorder(textField);
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
							SX3CommonUIValidations.removeTextErrorBorder(textField);
							setDataInJson(textField,formatAndResolutionJson,labelName);
							numericValidator.hide();
						}
					});
		
	}
	
	private static void setDataInJson(TextField textField, FormatAndResolutions formatAndResolutionJson, String labelName) {
		if(labelName.equals("HResolution")) {
			if(textField.getText().equals("")) {
    	  		formatAndResolutionJson.setH_RESOLUTION(0);
    	  	}else {
    	  		formatAndResolutionJson.setH_RESOLUTION(Integer.parseInt(textField.getText()));
    	  	}
		}else if(labelName.equals("VResolution")) {
			if(textField.getText().equals("")) {
    	  		formatAndResolutionJson.setV_RESOLUTION(0);
    	  	}else {
    	  		formatAndResolutionJson.setV_RESOLUTION(Integer.parseInt(textField.getText()));
    	  	}
		}else if(labelName.equals("FSFrameRate")) {
			if(textField.getText().equals("")) {
    	  		formatAndResolutionJson.setFRAME_RATE_IN_SS(0);
    	  	}else {
    	  		formatAndResolutionJson.setFRAME_RATE_IN_FS(Integer.parseInt(textField.getText()));
    	  	}
		}else if(labelName.equals("HSFrameRate")) {
			if(textField.getText().equals("")) {
    	  		formatAndResolutionJson.setFRAME_RATE_IN_HS(0);
    	  	}else {
    	  		formatAndResolutionJson.setFRAME_RATE_IN_HS(Integer.parseInt(textField.getText()));
    	  	}
		}else if(labelName.equals("SSFrameRate")) {
			if(textField.getText().equals("")) {
    	  		formatAndResolutionJson.setFRAME_RATE_IN_SS(0);
    	  	}else {
    	  		formatAndResolutionJson.setFRAME_RATE_IN_SS(Integer.parseInt(textField.getText()));
    	  	}
		}
		
	}
	
	public static void setupCameraAnProcessingControlValidationForNumeric(TextField textField,
			String invalidNumericErrorMessage, Map<String, Object> map, String labelName) {
		// Context Menu for error messages
		final ContextMenu numericValidator = new ContextMenu();
		numericValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		numericValidator.setAutoHide(false);

		/** Decimal data Text Field Validation **/
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if(!newValue.equals("") && Long.parseLong(newValue) > 4294967295L) {
				textField.setText(oldValue);
				final StringBuffer sb = new StringBuffer(invalidNumericErrorMessage); 
				sb.append(" Max length 4294967295.");
				SX3CommonUIValidations.showErrorRichText(textField, numericValidator, sb.toString());
			}else {
				if (!validateNumeric(newValue)) {
					textField.setText(oldValue);
					SX3CommonUIValidations.showTexErrorBorder(textField);
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
						setCameraAndProcessingDataInJson(textField,map,labelName);
					numericValidator.hide();
					SX3CommonUIValidations.removeTextErrorBorder(textField);
			}
		});
		
	}
	
	private static void setCameraAndProcessingDataInJson(TextField textField, Map<String, Object> map,
			String labelName) {
		if(labelName.equals("MIN")) {
			for (Map.Entry<String,Object> entry : map.entrySet()) {
				if(entry.getKey().contains("_MIN")) {
					if(textField.getText().equals("")) {
						map.put(entry.getKey(), 0);
					}else {
						Long parseLong = Long.parseLong(textField.getText());
						map.put(entry.getKey(), parseLong);
					}
				}
			}
		}else if(labelName.equals("MAX")) {
			for (Map.Entry<String,Object> entry : map.entrySet()) {
				if(entry.getKey().contains("_MAX")) {
					if(textField.getText().equals("")) {
						map.put(entry.getKey(), 0);
					}else {
						Long parseLong = Long.parseLong(textField.getText());
						map.put(entry.getKey(), parseLong);
					}
				}
			}
		}else if(labelName.equals("DEFAULT")) {
			for (Map.Entry<String,Object> entry : map.entrySet()) {
				if(entry.getKey().contains("_DEFAULT")) {
					if(textField.getText().equals("")) {
						map.put(entry.getKey(), 0);
					}else {
						Long parseLong = Long.parseLong(textField.getText());
						map.put(entry.getKey(), parseLong);
					}
				}
			}
		}else if(labelName.equals("STEP")) {
			for (Map.Entry<String,Object> entry : map.entrySet()) {
				if(entry.getKey().contains("_STEP")) {
					if(textField.getText().equals("")) {
						map.put(entry.getKey(), 0);
					}else {
						Long parseLong = Long.parseLong(textField.getText());
						map.put(entry.getKey(), parseLong);
					}
				}
			}
		}else if(labelName.equals("_REGISTER_ADDRESS")) {
			for (Map.Entry<String,Object> entry : map.entrySet()) {
				if(entry.getKey().contains("REGISTER_ADDRESS")) {
					map.put(entry.getKey(), textField.getText().toString().isEmpty() ? "" : "0x" + textField.getText().toString());
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
	
	public static void setupValidationForHexTextField(Map<String, Boolean> rootTabErrorList,Tab rootTab, Tab subTab, Tab subSubTab, TextField textField,
			String errorMessage, Map<String, Object> map, int fieldLength, String labelName, String labelName1, boolean performLoadAction) {
		// Context Menu for error messages
		final ContextMenu vendorIdValidator = new ContextMenu();
		vendorIdValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		vendorIdValidator.setAutoHide(false);

		if(performLoadAction == true) {
			if (!validateHexString(textField.getText())) {
				rootTabErrorList.put(labelName1, true);
				rootTab.setStyle("-fx-border-color:red;");
				subTab.setStyle("-fx-border-color:red;");
				subSubTab.setStyle("-fx-border-color:red;");
				SX3CommonUIValidations.showTexErrorBorder(textField);
			}
		}
		
		/** Hex data Text Field Validation **/
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if(newValue.length() > fieldLength) {
				textField.setText(oldValue);
				final StringBuffer sb = new StringBuffer(errorMessage); 
				sb.append(" Max length "+fieldLength);
				SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, sb.toString());
			}else {
				if (!validateHexString(newValue)) {
					textField.setText(oldValue);
					rootTabErrorList.put(labelName1, true);
					rootTab.setStyle("-fx-border-color:red;");
					subTab.setStyle("-fx-border-color:red;");
					subSubTab.setStyle("-fx-border-color:red;");
					SX3CommonUIValidations.showTexErrorBorder(textField);
					SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, errorMessage);
				} else {
					// Hiding the error message
					rootTabErrorList.put(labelName1, false);
					if(!rootTabErrorList.containsValue(true)) {
						rootTab.setStyle("");
						subTab.setStyle("");
						subSubTab.setStyle("");
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
					setCameraAndProcessingDataInJson(textField, map, labelName);
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

}
