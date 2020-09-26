package sx3Configuration.ui.validations;

import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import sx3Configuration.model.FormatAndResolutions;

public class UVCSettingsValidation {

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
	 * @param maxValue 
	 * @param busWidth **/
	public static void setupEndpointValidationForNumeric(Tab rootTab, Tab endpointTab, Tab endpointTab2, TextField textField,
			boolean performLoadAction, Map<String, Boolean> endpointSettingsTabErrorList, String invalidNumericErrorMessage, 
			int maxValue, String labelName) {
		// Context Menu for error messages
				final ContextMenu numericValidator = new ContextMenu();
				numericValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
				numericValidator.setAutoHide(false);
				if(performLoadAction == true) {
					if (!validateNumeric(textField.getText())) {
						endpointSettingsTabErrorList.put(labelName, true);
						rootTab.setStyle("-fx-border-color:red;");
						endpointTab.setStyle("-fx-border-color:red;");
						endpointTab2.setStyle("-fx-border-color:red;");
						SX3CommonUIValidations.showTexErrorBorder(textField);
					}
				}

				/** Decimal data Text Field Validation **/
				textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
					if(validateNumeric(newValue) && !newValue.equals("") && Integer.parseInt(newValue) > maxValue) {
						textField.setText(oldValue);
						String sb = "";
						if(Integer.parseInt(newValue) > maxValue) {
							if(maxValue == 65535) {
								sb = "Max size 2 bytes.";
							}else if(maxValue == 255) {
								sb = "Max size 1 bytes.";
							}else {
								sb = "Max value "+maxValue+".";
							}
							SX3CommonUIValidations.showErrorRichText(textField, numericValidator, sb.toString());
						}
					}else {
						if (!validateNumeric(newValue)) {
							textField.setText(oldValue);
							SX3CommonUIValidations.showTexErrorBorder(textField);
							SX3CommonUIValidations.showErrorRichText(textField, numericValidator, invalidNumericErrorMessage);
						} else {
							if(labelName.equals("Burst Length")) {
								if(newValue.length() > 5) {
									textField.setText(oldValue);
									SX3CommonUIValidations.showErrorRichText(textField, numericValidator, "Max length 5 character.");
								}
							} else if(labelName.equals("Buffer Count")) {
								if(newValue.length() > 2) {
									textField.setText(oldValue);
									SX3CommonUIValidations.showErrorRichText(textField, numericValidator, "Max length 2 character.");
								}
							}
							if(!newValue.equals("") && !newValue.equals("0") && labelName.equals("Buffer Size")) {
								if(newValue.length() > 5) {
									textField.setText(oldValue);
									SX3CommonUIValidations.showErrorRichText(textField, numericValidator, "Max length 5 character.");
								}
								int bufferSize = Integer.parseInt(textField.getText());
								if(bufferSize%16 != 0) {
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
								rootTab.setStyle("");
								endpointTab.setStyle("");
								endpointTab2.setStyle("");
								SX3CommonUIValidations.showTexErrorBorder(textField);
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
							numericValidator.hide();
							if (validateNumeric(textField.getText()) && !endpointSettingsTabErrorList.containsValue(true)) {
								SX3CommonUIValidations.removeTextErrorBorder(textField);
							}
					}
				});
	}
	
	public static ContextMenu setupFormatAndResolutionValidationForNumeric(Map<String, Boolean> uvcuacTabErrorList, Tab uvcuacSettingsTab, Tab subTab, Tab subSubTab, TextField textField,
			String invalidNumericErrorMessage, FormatAndResolutions formatAndResolutionJson, String labelName, boolean performLoadAction, int busWidth, TextField bitPerPixel) {
			// Context Menu for error messages
					final ContextMenu numericValidator = new ContextMenu();
					numericValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
					numericValidator.setAutoHide(false);
					if(performLoadAction) {
						if(!textField.getText().equals("") && (labelName.equals("HResolution"))) {
							int resolution = Integer.parseInt(textField.getText());
							int busWidth1 = busWidth/8;
							if(resolution%busWidth1!=0) {
								uvcuacSettingsTab.setStyle("-fx-border-color:red;");
								subSubTab.setStyle("-fx-border-color:red;");
								subTab.setStyle("-fx-border-color:red;");
								SX3CommonUIValidations.showTexErrorBorder(textField);
							}
						}
					}
					
					/** Decimal data Text Field Validation **/
					textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
						if(validateNumeric(newValue) && !newValue.equals("") && Integer.parseInt(newValue) > 65535) {
							textField.setText(oldValue);
							final StringBuffer sb = new StringBuffer(); 
							sb.append("Max size 2 bytes.");
							SX3CommonUIValidations.showErrorRichText(textField, numericValidator, sb.toString());
						}else {
							if (!validateNumeric(newValue)) {
								textField.setText(oldValue);
								SX3CommonUIValidations.showTexErrorBorder(textField);
								SX3CommonUIValidations.showErrorRichText(textField, numericValidator, invalidNumericErrorMessage);
							} else {
								if(!newValue.equals("") && (labelName.equals("HResolution"))) {
									int resolution = Integer.parseInt(textField.getText());
									int busWidth1 = busWidth/8;
									int bitPerPixel1 = Integer.parseInt(bitPerPixel.getText())/8;
									if((resolution*bitPerPixel1)%busWidth1 == 0) {
										uvcuacTabErrorList.put(labelName, false);
										if(!uvcuacTabErrorList.containsValue(true)) {
											uvcuacSettingsTab.setStyle("");
											subSubTab.setStyle("");
											subTab.setStyle("");
										}
										// Hiding the error message
										numericValidator.hide();
										SX3CommonUIValidations.removeTextErrorBorder(textField);
									}else {
										uvcuacTabErrorList.put(labelName, true);
										uvcuacSettingsTab.setStyle("-fx-border-color:red;");
										subSubTab.setStyle("-fx-border-color:red;");
										subTab.setStyle("-fx-border-color:red;");
										SX3CommonUIValidations.showTexErrorBorder(textField);
										SX3CommonUIValidations.showErrorRichText(textField, numericValidator, "Choose the size (in bytes) for each buffer (("+labelName+" * (Bits Per Pixel/8)) should be divisible by (FIFO_BUS_WIDTH/8).)");
									}
								}
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
							if (!textField.getText().equals("") && validateNumeric(textField.getText())) {
								if(labelName.equals("HResolution")) {
									int resolution = Integer.parseInt(textField.getText());
									int busWidth1 = busWidth/8;
									int bitPerPixel1 = Integer.parseInt(bitPerPixel.getText())/8;
									if((resolution*bitPerPixel1)%busWidth1 == 0) {
										uvcuacTabErrorList.put(labelName, false);
										if(!uvcuacTabErrorList.containsValue(true)) {
											uvcuacSettingsTab.setStyle("");
											subSubTab.setStyle("");
											subTab.setStyle("");
										}
										// Hiding the error message
										numericValidator.hide();
										SX3CommonUIValidations.removeTextErrorBorder(textField);
									}else {
										uvcuacTabErrorList.put(labelName, true);
										uvcuacSettingsTab.setStyle("-fx-border-color:red;");
										subSubTab.setStyle("-fx-border-color:red;");
										subTab.setStyle("-fx-border-color:red;");
										SX3CommonUIValidations.showTexErrorBorder(textField);
									}
								}else {
									SX3CommonUIValidations.removeTextErrorBorder(textField);
								}
							}
						}
					});
					return numericValidator;
		
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
    	  		formatAndResolutionJson.setFRAME_RATE_IN_FS(1);
    	  	}else {
    	  		formatAndResolutionJson.setFRAME_RATE_IN_FS(Integer.parseInt(textField.getText()));
    	  	}
		}else if(labelName.equals("HSFrameRate")) {
			if(textField.getText().equals("")) {
    	  		formatAndResolutionJson.setFRAME_RATE_IN_HS(1);
    	  	}else {
    	  		formatAndResolutionJson.setFRAME_RATE_IN_HS(Integer.parseInt(textField.getText()));
    	  	}
		}else if(labelName.equals("SSFrameRate")) {
			if(textField.getText().equals("")) {
    	  		formatAndResolutionJson.setFRAME_RATE_IN_SS(1);
    	  	}else {
    	  		formatAndResolutionJson.setFRAME_RATE_IN_SS(Integer.parseInt(textField.getText()));
    	  	}
		}
		
	}
	
	
	public static ContextMenu setupValidationForHexTextField(Map<String, Boolean> rootTabErrorList,Tab rootTab, Tab subTab, Tab subSubTab, TextField textField,
			String errorMessage, Map<String, Object> map, String labelName, String labelName1, boolean performLoadAction, String enableCheck) {
		// Context Menu for error messages
		final ContextMenu vendorIdValidator = new ContextMenu();
		vendorIdValidator.setStyle("-fx-effect:null;-fx-border-color:red;-fx-border-width:1px;-fx-border-insets:0;");
		vendorIdValidator.setAutoHide(false);

		if(performLoadAction == true) {
			if ((!validateHexString(textField.getText()) || textField.getText().equals("")) && enableCheck.equals("Enable")) {
				rootTabErrorList.put(labelName1, true);
				rootTab.setStyle("-fx-border-color:red;");
				subTab.setStyle("-fx-border-color:red;");
				subSubTab.setStyle("-fx-border-color:red;");
				SX3CommonUIValidations.showTexErrorBorder(textField);
			}
		}
		
		/** Hex data Text Field Validation **/
		textField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if (!validateHexString(newValue)) {
				textField.setText(oldValue);
				SX3CommonUIValidations.showTexErrorBorder(textField);
				SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, errorMessage);
			}else {
				if(newValue.length() > 4) {
					textField.setText(oldValue);
					final StringBuffer sb = new StringBuffer(); 
					sb.append(" Max length 4 character.");
					SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, sb.toString());
				}else if(!newValue.equals("") && Integer.parseInt(newValue, 16)>65535) {
					textField.setText(oldValue);
					final StringBuffer sb = new StringBuffer(); 
					sb.append(" Max value 2 bytes.");
					SX3CommonUIValidations.showErrorRichText(textField, vendorIdValidator, sb.toString());
				}else {
					if(newValue.equals("") && enableCheck.equals("Enable")) {
						rootTabErrorList.put(labelName1, true);
						rootTab.setStyle("-fx-border-color:red;");
						subTab.setStyle("-fx-border-color:red;");
						subSubTab.setStyle("-fx-border-color:red;");
						SX3CommonUIValidations.showTexErrorBorder(textField);
					}else {
//						 Hiding the error message
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

	public static boolean validateHexString(String hexVal) {
		if(hexVal == null || hexVal.equals("")) {
			return true;
		}else {
			return SX3CommonUIValidations.isValidHex(hexVal);
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
