package sx3Configuration.ui;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sx3Configuration.model.HDMISourceConfiguration;
import sx3Configuration.tablemodel.FormatAndResolutionTableModel;
import sx3Configuration.tablemodel.HDMISourceConfigurationTable;
import sx3Configuration.ui.validations.DeviceSettingsValidations;
import sx3Configuration.ui.validations.VideoSourceConfigValidation;
import sx3Configuration.util.OnMouseExitedHandler;

public class VideoSourceConfigController {

	private static ContextMenu contextMenu; 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void createHDMITable(AnchorPane anchorPane1, TableView<HDMISourceConfigurationTable> hdmiSourceConfigurationTable) {
		
//		anchorPane1.setPrefWidth(500);
		hdmiSourceConfigurationTable.setPrefHeight(390);
		hdmiSourceConfigurationTable.setPrefWidth(650);
		
		/** S. NO. **/
		TableColumn snoColumn = new TableColumn("S.No.");
		snoColumn.setPrefWidth(40);
		snoColumn.setStyle("-fx-font-size: 12;-fx-alignment: center;");
		snoColumn.setCellValueFactory(new PropertyValueFactory<HDMISourceConfigurationTable, Long>("sno"));
		
		/** Comment **/
		TableColumn commentColumn = new TableColumn("Event Name");
		commentColumn.setPrefWidth(100);
		commentColumn.setStyle("-fx-font-size: 12;-fx-alignment: center;");
		commentColumn.setCellValueFactory(new PropertyValueFactory<HDMISourceConfigurationTable, String>("EventName"));
		
		/** HDMI Source Register Address **/
		TableColumn sourceRegisterAddress = new TableColumn("");
		TableColumn hdmiXLabel = new TableColumn<>("");
		hdmiXLabel.setPrefWidth(20);
		hdmiXLabel.setStyle("-fx-alignment: center;-fx-font-size:12px");
		hdmiXLabel.setCellValueFactory(new PropertyValueFactory<HDMISourceConfigurationTable, Label>("HDMIXLabel"));
		TableColumn registerAddress = new TableColumn<>("HDMI Source\nRegister Address");
		registerAddress.setStyle("-fx-font-size: 12;-fx-alignment: center;");
		registerAddress.setPrefWidth(100);
		registerAddress.setCellValueFactory(new PropertyValueFactory<HDMISourceConfigurationTable, String>("HDMISourceRegisterAddress"));
		sourceRegisterAddress.getColumns().addAll(hdmiXLabel,registerAddress);
		
		/** Mask Value **/
		TableColumn maskValue = new TableColumn("");
		TableColumn maskXLabel = new TableColumn("");
		maskXLabel.setPrefWidth(20);
		maskXLabel.setStyle("-fx-alignment: center;-fx-font-size:12px");
		maskXLabel.setCellValueFactory(new PropertyValueFactory<HDMISourceConfigurationTable, Label>("MaskXLabel"));
		TableColumn maskValue1 = new TableColumn<>("Mask pattern");
		maskValue1.setPrefWidth(100);
		maskValue1.setStyle("-fx-font-size: 12;-fx-alignment: center;");
		maskValue1.setCellValueFactory(new PropertyValueFactory<HDMISourceConfigurationTable, String>("MaskValue"));
		maskValue.getColumns().addAll(maskXLabel,maskValue1);
		
		/** Compare Value **/
		TableColumn compareValue = new TableColumn("");
		TableColumn compareXLabel = new TableColumn("");
		compareXLabel.setPrefWidth(20);
		compareXLabel.setStyle("-fx-alignment: center;-fx-font-size:12px");
		compareXLabel.setCellValueFactory(new PropertyValueFactory<HDMISourceConfigurationTable, Label>("CompareXLabel"));
		TableColumn compareValue1 = new TableColumn<>("Compare Value");
		compareValue1.setPrefWidth(100);
		compareValue1.setStyle("-fx-font-size: 12;-fx-alignment: center;");
		compareValue1.setCellValueFactory(new PropertyValueFactory<HDMISourceConfigurationTable, String>("CompareValue"));
		compareValue.getColumns().addAll(compareXLabel,compareValue1);
		
		/** Sensor Config **/
		TableColumn sensorConfigColumn = new TableColumn("HDMI Source Config");
		sensorConfigColumn.setPrefWidth(140);
		sensorConfigColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		sensorConfigColumn
				.setCellValueFactory(new PropertyValueFactory<FormatAndResolutionTableModel, Button>("button"));

		hdmiSourceConfigurationTable.getColumns().addAll(snoColumn, commentColumn, sourceRegisterAddress, maskValue, 
				compareValue, sensorConfigColumn);
		anchorPane1.getChildren().add(hdmiSourceConfigurationTable);
		
	}

	public static void createAndAddRowInHdmiSensorConfigTable(Tab videoSourceConfigTab, Tab subTab, Map<String, Boolean> videoSourceConfigTabErrorList, 
			boolean performLoadAction, Button addBtn, Button editBtn, HDMISourceConfiguration hdmiSourceConfiguration,
			ObservableList<HDMISourceConfigurationTable> hdmiSourceConfigurationData,
			TableView<HDMISourceConfigurationTable> hdmiSourceConfigurationTable, int index, Properties configProperties, List<String> s1) {
		HDMISourceConfigurationTable hdmiResourceConfigTable = new HDMISourceConfigurationTable();
		
		
		/** S_NO **/
		Label sno = new Label(String.valueOf(index));
		sno.setStyle("-fx-font-size: 12");
		hdmiResourceConfigTable.setSno(sno);
		hdmiSourceConfiguration.setS_NO(index);
		
		/** S_NO **/
		TextField eventName = new TextField();
		eventName.setId("hdmiEvent"+hdmiSourceConfigurationData.size());
		eventName.setStyle("-fx-font-size: 12");
		hdmiResourceConfigTable.setEventName(eventName);
		DeviceSettingsValidations.setupValidationForAlphNumericTextField(videoSourceConfigTabErrorList, subTab, eventName, 
				configProperties.getProperty("INVALID_ALPHANUMERIC_ERROR_MESSAGE"), "EventName", 
				performLoadAction, 32);
		eventName.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				hdmiSourceConfiguration.setEVENT_NAME(eventName.getText());
				SX3Manager.getInstance().addLog("Event Name : "+eventName.getText()+"<br>");
			}
		});
		
		Label extraLabel = new Label("0x");
		hdmiResourceConfigTable.setHDMIXLabel(extraLabel);
		
		/** HDMI SOUURCE REGISTER ADDRESS **/
		TextField hdmiRegisterAddress = new TextField();
		hdmiRegisterAddress.setId("hdmiRegisterAddress"+hdmiSourceConfigurationData.size());
		if(!hdmiSourceConfiguration.getHDMI_SOURCE_REGISTER_ADDRESS().equals("")) {
			hdmiRegisterAddress.setText(hdmiSourceConfiguration.getHDMI_SOURCE_REGISTER_ADDRESS().toString().substring(2));
		}
		hdmiResourceConfigTable.setHDMISourceRegisterAddress(hdmiRegisterAddress);
		hdmiRegisterAddress.setStyle("-fx-font-size: 12");
		contextMenu = VideoSourceConfigValidation.setupValidationForHDMIHexTextField(videoSourceConfigTab, subTab,
				videoSourceConfigTabErrorList, performLoadAction, hdmiRegisterAddress,
				configProperties.getProperty("INVALID_HEX_ERROR_MESSAGE"), 255,"HDMI_Source_Register_Address");
		hdmiRegisterAddress.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				int i = 0; 
				while (i < hdmiRegisterAddress.getText().length() && hdmiRegisterAddress.getText().charAt(i) == '0') {
					i++; 
					StringBuffer sb = new StringBuffer(hdmiRegisterAddress.getText()); 
					sb.replace(0, i, ""); 
					hdmiRegisterAddress.setText(sb.toString());
				}
				hdmiSourceConfiguration.setHDMI_SOURCE_REGISTER_ADDRESS(hdmiRegisterAddress.getText().toString().isEmpty() ? "" : "0x" + hdmiRegisterAddress.getText().toString());
				SX3Manager.getInstance().addLog("HDMI SOURCE REGISTER ADDRESS : "+hdmiRegisterAddress.getText()+"<br>");
				s1.add(hdmiRegisterAddress.getText());
				
			}
		});
		hdmiRegisterAddress.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		
		Label extraLabel2 = new Label("0x");
		hdmiResourceConfigTable.setMaskXLabel(extraLabel2);
		
		/** Mask Value **/
		TextField maskValue = new TextField();
		maskValue.setId("hdmiMaskValue"+hdmiSourceConfigurationData.size());
		if(!hdmiSourceConfiguration.getMASK_PATTERN().equals("")) {
			maskValue.setText(hdmiSourceConfiguration.getMASK_PATTERN().toString().substring(2));
		}
		hdmiResourceConfigTable.setMaskValue(maskValue);
		maskValue.setStyle("-fx-font-size: 12");
		contextMenu = VideoSourceConfigValidation.setupValidationForHDMIHexTextField(videoSourceConfigTab, subTab,
				videoSourceConfigTabErrorList, performLoadAction, maskValue,
				configProperties.getProperty("INVALID_HEX_ERROR_MESSAGE"), 255,"Mask_pattern");
		maskValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				int i = 0; 
				while (i < maskValue.getText().length() && maskValue.getText().charAt(i) == '0') {
					i++; 
					StringBuffer sb = new StringBuffer(maskValue.getText()); 
					sb.replace(0, i, ""); 
					maskValue.setText(sb.toString());
				}
				hdmiSourceConfiguration.setMASK_PATTERN(maskValue.getText().toString().isEmpty() ? "" : "0x" + maskValue.getText().toString());
				SX3Manager.getInstance().addLog("MASK PATTERN : "+maskValue.getText()+"<br>");
				
				
			}
		});
		maskValue.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		
		Label extraLabel3 = new Label("0x");
		hdmiResourceConfigTable.setCompareXLabel(extraLabel3);
		
		/** Compare value **/
		TextField compareValue = new TextField();
		compareValue.setId("hdmiCompareValue"+hdmiSourceConfigurationData.size());
		if(!hdmiSourceConfiguration.getCOMPARE_VALUE().equals("")) {
			compareValue.setText(hdmiSourceConfiguration.getCOMPARE_VALUE().toString().substring(2));
		}
		hdmiResourceConfigTable.setCompareValue(compareValue);
		compareValue.setStyle("-fx-font-size: 12");
		contextMenu = VideoSourceConfigValidation.setupValidationForHDMIHexTextField(videoSourceConfigTab, subTab,
				videoSourceConfigTabErrorList, performLoadAction, compareValue,
				configProperties.getProperty("INVALID_HEX_ERROR_MESSAGE"), 255,"Compare_Value");
		compareValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				int i = 0; 
				while (i < compareValue.getText().length() && compareValue.getText().charAt(i) == '0') {
					i++; 
					StringBuffer sb = new StringBuffer(compareValue.getText()); 
					sb.replace(0, i, ""); 
					compareValue.setText(sb.toString());
				}
				hdmiSourceConfiguration.setCOMPARE_VALUE(compareValue.getText().toString().isEmpty() ? "" : "0x" + compareValue.getText().toString());
				SX3Manager.getInstance().addLog("COMPARE VALUE : "+compareValue.getText()+"<br>");
				
				
			}
		});
		compareValue.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		
		/** Sensor Config Button **/
		Button sensorConfigEditButton = new Button();
		sensorConfigEditButton.setTooltip(new Tooltip("HDMI Source Config Button"));
		sensorConfigEditButton.setId("hdmiSensorConfigButton"+hdmiSourceConfigurationData.size());
		hdmiResourceConfigTable.setButton(sensorConfigEditButton);
		
		hdmiSourceConfigurationData.add(hdmiResourceConfigTable);
		FilteredList<HDMISourceConfigurationTable> filteredData = new FilteredList<>(hdmiSourceConfigurationData,
				hdmiResource -> hdmiSourceConfigurationData.indexOf(hdmiResource) < 16);
		SortedList<HDMISourceConfigurationTable> sortedData = new SortedList<>(filteredData);
		hdmiSourceConfigurationTable.setItems(sortedData);
		
		if(hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING() != null && !hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().isEmpty() && !hdmiSourceConfiguration.getRESOLUTION_REGISTER_SETTING().get(0).getREGISTER_ADDRESS().equals("")) {
			sensorConfigEditButton.setStyle("-fx-background-color:green");
		}
		
		sensorConfigEditButton.setOnAction(new SensorConfigUI(addBtn, editBtn, hdmiSourceConfiguration, 
				hdmiResourceConfigTable, sensorConfigEditButton));
		
		if (!hdmiRegisterAddress.getText().equals("") && !maskValue.getText().equals("") && !compareValue.getText().equals("")) {
			hdmiResourceConfigTable.getHDMISourceRegisterAddress().setDisable(true);
			hdmiResourceConfigTable.getMaskValue().setDisable(true);
			hdmiResourceConfigTable.getCompareValue().setDisable(true);
			addBtn.setDisable(false);
			sensorConfigEditButton.setDisable(true);
		}

		
	}

}
