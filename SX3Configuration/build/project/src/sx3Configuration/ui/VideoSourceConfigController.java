package sx3Configuration.ui;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sx3Configuration.TableModel.FormatAndResolutionTableModel;
import sx3Configuration.TableModel.HDMISourceConfigurationTable;
import sx3Configuration.model.HDMISourceConfiguration;

public class VideoSourceConfigController {


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void createHDMITable(AnchorPane anchorPane1, TableView<HDMISourceConfigurationTable> hdmiSourceConfigurationTable) {
		
//		anchorPane1.setPrefWidth(500);
		hdmiSourceConfigurationTable.setPrefHeight(390);
		hdmiSourceConfigurationTable.setPrefWidth(530);
		
		/** S. NO. **/
		TableColumn snoColumn = new TableColumn("S.No.");
		snoColumn.setPrefWidth(40);
		snoColumn.setStyle("-fx-font-size: 12;-fx-alignment: center;");
		snoColumn.setCellValueFactory(new PropertyValueFactory<HDMISourceConfigurationTable, Long>("sno"));
		
		/** HDMI Source Register Address **/
		TableColumn sourceRegisterAddress = new TableColumn("HDMI Source Register Address");
		sourceRegisterAddress.setPrefWidth(180);
		sourceRegisterAddress.setStyle("-fx-font-size: 12;-fx-alignment: center;");
		sourceRegisterAddress.setCellValueFactory(new PropertyValueFactory<HDMISourceConfigurationTable, String>("HDMISourceRegisterAddress"));
		
		/** Mask Value **/
		TableColumn maskValue = new TableColumn("Mask pattern");
		maskValue.setPrefWidth(100);
		maskValue.setStyle("-fx-font-size: 12;-fx-alignment: center;");
		maskValue.setCellValueFactory(new PropertyValueFactory<HDMISourceConfigurationTable, String>("MaskValue"));
		
		/** Compare Value **/
		TableColumn compareValue = new TableColumn("Compare Value");
		compareValue.setPrefWidth(100);
		compareValue.setStyle("-fx-font-size: 12;-fx-alignment: center;");
		compareValue.setCellValueFactory(new PropertyValueFactory<HDMISourceConfigurationTable, String>("CompareValue"));
		
		/** Sensor Config **/
		TableColumn sensorConfigColumn = new TableColumn("Sensor Config");
		sensorConfigColumn.setPrefWidth(90);
		sensorConfigColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		sensorConfigColumn
				.setCellValueFactory(new PropertyValueFactory<FormatAndResolutionTableModel, Button>("button"));

		hdmiSourceConfigurationTable.getColumns().addAll(snoColumn, sourceRegisterAddress, maskValue, 
				compareValue, sensorConfigColumn);
		anchorPane1.getChildren().add(hdmiSourceConfigurationTable);
		
	}

	public static void createAndAddRowInTable(HDMISourceConfiguration hdmiSourceConfiguration,
			ObservableList<HDMISourceConfigurationTable> hdmiSourceConfigurationData,
			TableView<HDMISourceConfigurationTable> hdmiSourceConfigurationTable, int index) {
		HDMISourceConfigurationTable hdmiResourceConfigTable = new HDMISourceConfigurationTable();
		
		/** S_NO **/
		Label sno = new Label(String.valueOf(index));
		sno.setStyle("-fx-font-size: 12");
		hdmiResourceConfigTable.setSno(sno);
		hdmiSourceConfiguration.setS_NO(index);
		
		/** HDMI SOUURCE REGISTER ADDRESS **/
		TextField hdmiRegisterAddress = new TextField();
		hdmiRegisterAddress.setText(hdmiSourceConfiguration.getHDMI_SOURCE_REGISTER_ADDRESS());
		hdmiResourceConfigTable.setHDMISourceRegisterAddress(hdmiRegisterAddress);
		hdmiRegisterAddress.setStyle("-fx-font-size: 12");
		
		/** Mask Value **/
		TextField maskValue = new TextField();
		maskValue.setText(hdmiSourceConfiguration.getMASK_PATTERN());
		hdmiResourceConfigTable.setMaskValue(maskValue);
		maskValue.setStyle("-fx-font-size: 12");
		
		/** Compare value **/
		TextField compareValue = new TextField();
		compareValue.setText(hdmiSourceConfiguration.getCOMPARE_VALUE());
		hdmiResourceConfigTable.setCompareValue(compareValue);
		compareValue.setStyle("-fx-font-size: 12");
		
		/** Sensor Config Button **/
		Button sensorConfigEditButton = new Button();
		hdmiResourceConfigTable.setButton(sensorConfigEditButton);
		
		hdmiSourceConfigurationData.add(hdmiResourceConfigTable);
		FilteredList<HDMISourceConfigurationTable> filteredData = new FilteredList<>(hdmiSourceConfigurationData,
				hdmiResource -> hdmiSourceConfigurationData.indexOf(hdmiResource) < 16);
		SortedList<HDMISourceConfigurationTable> sortedData = new SortedList<>(filteredData);
		hdmiSourceConfigurationTable.setItems(sortedData);

		
	}

}
