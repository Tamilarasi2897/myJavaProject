package sx3Configuration.ui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import sx3Configuration.mergertool.SX3PropertiesConstants;
import sx3Configuration.model.CameraControlAndProcessingUnitJson;
import sx3Configuration.model.ColorMatching;
import sx3Configuration.model.EndpointSettings;
import sx3Configuration.model.ExtensionUnitControl;
import sx3Configuration.model.FormatAndResolutions;
import sx3Configuration.model.SensorConfig;
import sx3Configuration.model.UACSettings;
import sx3Configuration.model.UVCSettings;
import sx3Configuration.tablemodel.CameraAndProcessingUnitControlsTableModel;
import sx3Configuration.tablemodel.FormatAndResolutionTableModel;
import sx3Configuration.ui.validations.CameraAndProcessingControlValidation;
import sx3Configuration.ui.validations.UVCSettingsValidation;
import sx3Configuration.util.OnMouseClickHandler;
import sx3Configuration.util.OnMouseExitedHandler;
import sx3Configuration.util.SX3ConfiguartionHelp;
import sx3Configuration.util.ShowCameraAndProcessingControlHelp;
import sx3Configuration.util.UACSettingFieldConstants;
import sx3Configuration.util.UVCSettingsConstants;

public class UVCUACSettingsController {

	private static ContextMenu contextMenu;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void createTable(TableView<FormatAndResolutionTableModel> tableView) {

		/** S. NO. **/
		TableColumn snoColumn = new TableColumn("S.No.");
		snoColumn.setPrefWidth(40);
		snoColumn.setStyle("-fx-font-size: 12;-fx-alignment: center;");
		snoColumn.setCellValueFactory(new PropertyValueFactory<FormatAndResolutionTableModel, Long>("sno"));

		/** Image Format **/
		TableColumn imageFormatColumn = new TableColumn("Image Format");
		// imageFormatColumn.setPrefWidth(100);
		imageFormatColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		imageFormatColumn
				.setCellValueFactory(new PropertyValueFactory<FormatAndResolutionTableModel, String>("imageFormat"));

		/** Bits Per pixel **/
		TableColumn bitesPerPixelColumn = new TableColumn("Bits per pixel");
		bitesPerPixelColumn.setPrefWidth(90);
		bitesPerPixelColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		bitesPerPixelColumn.setCellValueFactory(
				new PropertyValueFactory<FormatAndResolutionTableModel, TextField>("bitPerPixcel"));

		/** H Resolution **/
		TableColumn hResolutionColumn = new TableColumn("H resolution");
		hResolutionColumn.setPrefWidth(100);
		hResolutionColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		hResolutionColumn
				.setCellValueFactory(new PropertyValueFactory<FormatAndResolutionTableModel, TextField>("resolutionH"));

		/** V Resolution **/
		TableColumn vResolutionColumn = new TableColumn("V resolution");
		vResolutionColumn.setPrefWidth(100);
		vResolutionColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		vResolutionColumn
				.setCellValueFactory(new PropertyValueFactory<FormatAndResolutionTableModel, TextField>("resolutionV"));

		/** Still Capture **/
		TableColumn stillCaptureColumn = new TableColumn("Still Capture");
		stillCaptureColumn.setPrefWidth(80);
		stillCaptureColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		stillCaptureColumn.setCellValueFactory(
				new PropertyValueFactory<FormatAndResolutionTableModel, TextField>("stillCapture"));

		/** Supported **/
		TableColumn supportedInColumn = new TableColumn("Supported");
		supportedInColumn.setStyle("-fx-font-size: 12;");
		TableColumn supportedInLSColumn = new TableColumn<>("FS");
		supportedInLSColumn.setPrefWidth(30);
		supportedInLSColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		supportedInLSColumn.setCellValueFactory(
				new PropertyValueFactory<FormatAndResolutionTableModel, CheckBox>("supportedInFS"));
		TableColumn supportedInHSColumn = new TableColumn<>("HS");
		supportedInHSColumn.setPrefWidth(30);
		supportedInHSColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		supportedInHSColumn.setCellValueFactory(
				new PropertyValueFactory<FormatAndResolutionTableModel, CheckBox>("supportedInHS"));
		TableColumn supportedInSSColumn = new TableColumn<>("SS");
		supportedInSSColumn.setPrefWidth(30);
		supportedInSSColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		supportedInSSColumn.setCellValueFactory(
				new PropertyValueFactory<FormatAndResolutionTableModel, CheckBox>("supportedInSS"));
		supportedInColumn.getColumns().addAll(supportedInLSColumn, supportedInHSColumn, supportedInSSColumn);

		/** Frame rate **/
		TableColumn frameRateInColumn = new TableColumn("Frame rate");
		frameRateInColumn.setStyle("-fx-font-size: 12;");
		TableColumn frameRateInLSColumn = new TableColumn<>("FS");
		frameRateInLSColumn.setPrefWidth(80);
		frameRateInLSColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		frameRateInLSColumn.setCellValueFactory(
				new PropertyValueFactory<FormatAndResolutionTableModel, TextField>("frameRateInFS"));
		TableColumn frameRateInHSColumn = new TableColumn<>("HS");
		frameRateInHSColumn.setPrefWidth(80);
		frameRateInHSColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		frameRateInHSColumn.setCellValueFactory(
				new PropertyValueFactory<FormatAndResolutionTableModel, TextField>("frameRateInHS"));
		TableColumn frameRateInSSColumn = new TableColumn<>("SS");
		frameRateInSSColumn.setPrefWidth(80);
		frameRateInSSColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		frameRateInSSColumn.setCellValueFactory(
				new PropertyValueFactory<FormatAndResolutionTableModel, TextField>("frameRateInSS"));
		frameRateInColumn.getColumns().addAll(frameRateInLSColumn, frameRateInHSColumn, frameRateInSSColumn);

		/** Sensor Config **/
		TableColumn sensorConfigColumn = new TableColumn("Sensor Config");
		sensorConfigColumn.setPrefWidth(90);
		sensorConfigColumn.setStyle("-fx-alignment: center;-fx-font-size: 12;");
		sensorConfigColumn
				.setCellValueFactory(new PropertyValueFactory<FormatAndResolutionTableModel, Button>("button"));

		tableView.getColumns().addAll(snoColumn, imageFormatColumn, bitesPerPixelColumn, hResolutionColumn,
				vResolutionColumn, stillCaptureColumn, supportedInColumn, frameRateInColumn, sensorConfigColumn);
	}

	public static GridPane createColorMatchingDescriptor(GridPane gridPane1, ColorMatching colorMatching,
			Properties configProperties) {
		gridPane1.setLayoutX(100);
		gridPane1.setHgap(10);
		gridPane1.setVgap(10);
		gridPane1.setHgap(10);
		Label colorPrimariesLabel = new Label("Color Primaries : ");
		gridPane1.add(colorPrimariesLabel, 0, 0);
		ComboBox<String> colorPrimariesValue = new ComboBox<>();
		colorPrimariesValue.setId("colorPrimaries");
		colorPrimariesValue
				.setTooltip(new Tooltip(configProperties.getProperty("COLOR_MATCHING.TOOLTIP_COLOR_PRIMARIES")));
		colorPrimariesValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = colorPrimariesValue.localToScreen(colorPrimariesValue.getBoundsInLocal());
			colorPrimariesValue.getTooltip().setX(bounds.getMaxX());
		});
		colorPrimariesValue.getItems().addAll(UVCSettingsConstants.COLOR_PRIMARIES);
		colorPrimariesValue.setValue(colorMatching.getCOLOR_PRIMARIES());
		gridPane1.add(colorPrimariesValue, 1, 0);
		colorPrimariesValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				colorMatching.setCOLOR_PRIMARIES(colorPrimariesValue.getValue());
				SX3Manager.getInstance().addLog("Color Primaries : " + colorPrimariesValue.getValue() + ".<br>");
				
				
			}
		});
		colorPrimariesValue.setOnMouseClicked(new OnMouseClickHandler());

		Label transferCharacteristicsLabel = new Label("Transfer Characteristics : ");
		gridPane1.add(transferCharacteristicsLabel, 0, 1);
		ComboBox<String> transferCharacteristicsValue = new ComboBox<>();
		transferCharacteristicsValue.setId("transferCharacteristics");
		transferCharacteristicsValue.setTooltip(
				new Tooltip(configProperties.getProperty("COLOR_MATCHING.TOOLTIP_TRANSFER_CHARACTERISTICS")));
		transferCharacteristicsValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = transferCharacteristicsValue.localToScreen(transferCharacteristicsValue.getBoundsInLocal());
			transferCharacteristicsValue.getTooltip().setX(bounds.getMaxX());
		});
		transferCharacteristicsValue.getItems().addAll(UVCSettingsConstants.TRANSFER_CHARACTERISTIC);
		gridPane1.add(transferCharacteristicsValue, 1, 1);
		transferCharacteristicsValue.setValue(colorMatching.getTRANSFER_CHARACTERISTICS());
		transferCharacteristicsValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				colorMatching.setTRANSFER_CHARACTERISTICS(transferCharacteristicsValue.getValue());
				SX3Manager.getInstance().addLog(
						"Transfer Characteristics : " + transferCharacteristicsValue.getValue() + ".<br>");
				
				
			}
		});
		transferCharacteristicsValue.setOnMouseClicked(new OnMouseClickHandler());

		Label matrixCoefficientsLabel = new Label("Matrix Coefficients : ");
		gridPane1.add(matrixCoefficientsLabel, 0, 2);
		ComboBox<String> matrixCoefficientsValue = new ComboBox<>();
		matrixCoefficientsValue.setId("matrixCoefficients");
		matrixCoefficientsValue
				.setTooltip(new Tooltip(configProperties.getProperty("COLOR_MATCHING.TOOLTIP_MATRIX_COEFFICIENTS")));
		matrixCoefficientsValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = matrixCoefficientsValue.localToScreen(matrixCoefficientsValue.getBoundsInLocal());
			matrixCoefficientsValue.getTooltip().setX(bounds.getMaxX());
		});
		matrixCoefficientsValue.getItems().addAll("Unspecified (Image characteristics unknown)", "BT.709", "FCC",
				"BT.470-2 B, G", "SMPTE 170M (BT.601,default)", "SMPTE 240M");
		gridPane1.add(matrixCoefficientsValue, 1, 2);
		matrixCoefficientsValue.setValue(colorMatching.getMATRIX_COEFFICIENTS());
		matrixCoefficientsValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				colorMatching.setMATRIX_COEFFICIENTS(matrixCoefficientsValue.getValue());
				SX3Manager.getInstance().addLog("Matrix Coefficients : " + matrixCoefficientsValue.getValue() + ".<br>");
				
				
			}
		});
		matrixCoefficientsValue.setOnMouseClicked(new OnMouseClickHandler());

		return gridPane1;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static TableView<CameraAndProcessingUnitControlsTableModel> createCameraAndProcessingControlTableUI(
			TableView<CameraAndProcessingUnitControlsTableModel> ControlTableView) {

		/** Label Column **/
		TableColumn<CameraAndProcessingUnitControlsTableModel, String> labelColumn = new TableColumn("Control Name");
		labelColumn.setPrefWidth(300);
		labelColumn.setStyle("-fx-alignment: center-left;-fx-font-size:12px;");
		labelColumn.setCellValueFactory(new PropertyValueFactory<>("labelName"));

		/** Enable Column **/
		TableColumn<CameraAndProcessingUnitControlsTableModel, CheckBox> enableColumn = new TableColumn("Enable");
		enableColumn.setPrefWidth(50);
		enableColumn.setStyle("-fx-alignment: center;-fx-font-size:12px;");
		enableColumn.setCellValueFactory(new PropertyValueFactory<>("enableLabel"));

		/** Min Column **/
		TableColumn<CameraAndProcessingUnitControlsTableModel, TextField> minColumn = new TableColumn("Min");
		minColumn.setPrefWidth(120);
		minColumn.setStyle("-fx-alignment: center;-fx-font-size:12px;");
		minColumn.setCellValueFactory(new PropertyValueFactory<>("min"));

		/** Max Column **/
		TableColumn<CameraAndProcessingUnitControlsTableModel, TextField> maxColumn = new TableColumn("Max");
		maxColumn.setPrefWidth(120);
		maxColumn.setStyle("-fx-alignment: center;-fx-font-size:12px;");
		maxColumn.setCellValueFactory(new PropertyValueFactory<>("max"));

		/** Default Column **/
		TableColumn<CameraAndProcessingUnitControlsTableModel, TextField> defaultColumn = new TableColumn("Default");
		defaultColumn.setPrefWidth(120);
		defaultColumn.setStyle("-fx-alignment: center;-fx-font-size:12px;");
		defaultColumn.setCellValueFactory(new PropertyValueFactory<>("defaultValue"));

		/** Step Column **/
		TableColumn<CameraAndProcessingUnitControlsTableModel, TextField> stepColumn = new TableColumn("Step");
		stepColumn.setPrefWidth(120);
		stepColumn.setStyle("-fx-alignment: center;-fx-font-size:12px;");
		stepColumn.setCellValueFactory(new PropertyValueFactory<>("step"));

		/** Len. Column **/
		TableColumn<CameraAndProcessingUnitControlsTableModel, TextField> lenColumn = new TableColumn("Len");
		lenColumn.setPrefWidth(40);
		lenColumn.setStyle("-fx-alignment: center;-fx-font-size:12px;");
		lenColumn.setCellValueFactory(new PropertyValueFactory<>("len"));

		/** Register Address Column **/
		TableColumn<CameraAndProcessingUnitControlsTableModel, TextField> registerAddressColumn = new TableColumn(
				"Register Address");
		registerAddressColumn.setPrefWidth(100);
		registerAddressColumn.setStyle("-fx-alignment: center;-fx-font-size:12px");
		/** Extra Column **/
		TableColumn<CameraAndProcessingUnitControlsTableModel, TextField> extraColumn = new TableColumn("");
		extraColumn.setCellValueFactory(new PropertyValueFactory<>("extraColumn"));
		extraColumn.setPrefWidth(20);
		extraColumn.setStyle("-fx-alignment: center;-fx-font-size:12px");
		/** Register Address Column **/
		TableColumn<CameraAndProcessingUnitControlsTableModel, TextField> registerAddressColumn1 = new TableColumn("");
		registerAddressColumn1.setCellValueFactory(new PropertyValueFactory<>("registerAddress"));
		registerAddressColumn1.setPrefWidth(85);
		registerAddressColumn.getColumns().addAll(extraColumn, registerAddressColumn1);

		ControlTableView.getColumns().addAll(labelColumn, enableColumn, minColumn, maxColumn, defaultColumn, stepColumn,
				lenColumn, registerAddressColumn);
		return ControlTableView;
	}

	public static ObservableList<CameraAndProcessingUnitControlsTableModel> addRowInCameraContolTable(
			Map<String, Boolean> uvcuacTabErrorList, Tab rootTab, Tab subTab, Tab subSubTab, String cameraControlLabel,
			ObservableList<CameraAndProcessingUnitControlsTableModel> cameraControlsList,
			LinkedHashMap<String, LinkedHashMap<String, Object>> map1,
			TextArea logDetails2,boolean performLoadAction, Properties configProperties) {
		CameraAndProcessingUnitControlsTableModel cameraControlTable = new CameraAndProcessingUnitControlsTableModel();
		CameraControlAndProcessingUnitJson cameraControlJson = new CameraControlAndProcessingUnitJson();
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		for (Map.Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
			map = entry1.getValue();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				Long intVal = null;
				if (entry.getValue() instanceof Double) {
					intVal = ((Double) entry.getValue()).longValue();
				}
				if (entry.getKey().contains("_ENABLE")) {
					cameraControlJson.setENABLE(entry.getValue().toString());
				} else if (entry.getKey().contains("_MIN")) {
					cameraControlJson.setMIN(intVal);
				} else if (entry.getKey().contains("_MAX")) {
					cameraControlJson.setMAX(intVal);
				} else if (entry.getKey().contains("_STEP")) {
					cameraControlJson.setSTEP(intVal);
				} else if (entry.getKey().contains("_DEFAULT")) {
					cameraControlJson.setDEFAULT(intVal);
				} else if (entry.getKey().contains("_LENGTH")) {
					cameraControlJson.setLEN(intVal);
				} else if (entry.getKey().contains("REGISTER_ADDRESS")) {
					if (!entry.getValue().toString().isEmpty() && entry.getValue().toString() != null) {
						cameraControlJson.setREGISTER_ADDRESS(entry.getValue().toString().substring(2));
					} else {
						cameraControlJson.setREGISTER_ADDRESS(entry.getValue().toString());
					}
				}

			}
		}

		map.put("RESERVED", "FF");
		String tooltip = SX3ConfigurationTooltipController.getCameraControlTooltip(cameraControlLabel,
				configProperties);

		/** Label Name **/
		Label labelName = new Label();
		labelName.setText(cameraControlLabel);
		labelName.setTooltip(new Tooltip(tooltip));
		labelName.getTooltip().setOnShowing(s -> {
			Bounds bounds = labelName.localToScreen(labelName.getBoundsInLocal());
			labelName.getTooltip().setX(bounds.getMaxX());
		});
		labelName.setStyle("-fx-font-weight: bold;-fx-font-size:12px;");
		cameraControlTable.setLabelName(labelName);
		labelName.setPadding(new Insets(0, 0, 0, 5));
		labelName.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));
		//
		/** Enable Checkbox **/
		CheckBox enableCheckBox = new CheckBox();
		enableCheckBox.setId("cameraControlEnable"+cameraControlsList.size());
		enableCheckBox.setTooltip(new Tooltip(tooltip + " Enable/Disable"));
		enableCheckBox.getTooltip().setOnShowing(s -> {
			Bounds bounds = enableCheckBox.localToScreen(enableCheckBox.getBoundsInLocal());
			enableCheckBox.getTooltip().setX(bounds.getMaxX());
		});
		if (cameraControlJson.getENABLE().equals("Enable")) {
			enableCheckBox.setSelected(true);
		} else {
			enableCheckBox.setSelected(false);
		}
		cameraControlTable.setEnableLabel(enableCheckBox);
		enableCheckBox.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));

		/** Min Text Field **/
		TextField minTextField = new TextField();
		minTextField.setId("cameraControlMin"+cameraControlsList.size());
		minTextField.setTooltip(new Tooltip(tooltip + " Min Value"));
		minTextField.getTooltip().setOnShowing(s -> {
			Bounds bounds = minTextField.localToScreen(minTextField.getBoundsInLocal());
			minTextField.getTooltip().setX(bounds.getMaxX());
		});
		minTextField.setAlignment(Pos.CENTER_RIGHT);
		if (cameraControlJson.getENABLE().equals("Enable")) {
			minTextField.setDisable(false);
		} else {
			minTextField.setDisable(true);
		}
		minTextField.setText(String.valueOf(cameraControlJson.getMIN()));
		cameraControlTable.setMin(minTextField);
		minTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (minTextField.getText().equals("")) {
					minTextField.setText("0");
				}
				for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
					LinkedHashMap<String, Object> value = entry1.getValue();
					for (Map.Entry<String, Object> entry : value.entrySet()) {
						if (entry.getKey().contains("_MIN")) {
							Long parseLong = Long.parseLong(minTextField.getText());
							entry1.getValue().put(entry.getKey(), parseLong);
						}
					}
				}
				logCameraAndProcessingControlFields(labelName, "MIN", minTextField);
			}
		});
		minTextField.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		minTextField.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));

		/** Max Text Field **/
		TextField maxTextField = new TextField();
		maxTextField.setId("cameraControlMax"+cameraControlsList.size());
		maxTextField.setTooltip(new Tooltip(tooltip + " Max Value"));
		maxTextField.getTooltip().setOnShowing(s -> {
			Bounds bounds = maxTextField.localToScreen(maxTextField.getBoundsInLocal());
			maxTextField.getTooltip().setX(bounds.getMaxX());
		});
		maxTextField.setAlignment(Pos.CENTER_RIGHT);
		if (cameraControlJson.getENABLE().equals("Enable")) {
			maxTextField.setDisable(false);
		} else {
			maxTextField.setDisable(true);
		}
		maxTextField.setText(String.valueOf(cameraControlJson.getMAX()));
		cameraControlTable.setMax(maxTextField);
		maxTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (maxTextField.getText().equals("")) {
					maxTextField.setText("0");
				}
				for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
					LinkedHashMap<String, Object> value = entry1.getValue();
					for (Map.Entry<String, Object> entry : value.entrySet()) {
						if (entry.getKey().contains("_MAX")) {
							Long parseLong = Long.parseLong(maxTextField.getText());
							entry1.getValue().put(entry.getKey(), parseLong);
						}
					}
				}
				logCameraAndProcessingControlFields(labelName, "MAX", maxTextField);
			}
		});

		minTextField.textProperty().addListener(new CameraAndProcessingControlValidation(minTextField,
				configProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), "MIN", maxTextField, contextMenu));

		maxTextField.textProperty().addListener(new CameraAndProcessingControlValidation(maxTextField,
				configProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), "MAX", minTextField, contextMenu));

		maxTextField.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		maxTextField.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));

		/** Default Text Field **/
		TextField defaultTextField = new TextField();
		defaultTextField.setId("cameraControlDefault"+cameraControlsList.size());
		defaultTextField.setTooltip(new Tooltip(tooltip + " Default Value"));
		defaultTextField.getTooltip().setOnShowing(s -> {
			Bounds bounds = defaultTextField.localToScreen(defaultTextField.getBoundsInLocal());
			defaultTextField.getTooltip().setX(bounds.getMaxX());
		});
		defaultTextField.setAlignment(Pos.CENTER_RIGHT);
		if (cameraControlJson.getENABLE().equals("Enable")) {
			defaultTextField.setDisable(false);
		} else {
			defaultTextField.setDisable(true);
		}
		defaultTextField.setText(String.valueOf(cameraControlJson.getDEFAULT()));
		cameraControlTable.setDefaultValue(defaultTextField);
		defaultTextField.textProperty()
				.addListener(new CameraAndProcessingControlValidation(defaultTextField,
						configProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), "DEFAULT", defaultTextField,
						contextMenu));
		defaultTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (defaultTextField.getText().equals("")) {
					defaultTextField.setText("0");
				}
				for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
					LinkedHashMap<String, Object> value = entry1.getValue();
					for (Map.Entry<String, Object> entry : value.entrySet()) {
						if (entry.getKey().contains("_DEFAULT")) {
							Long parseLong = Long.parseLong(defaultTextField.getText());
							entry1.getValue().put(entry.getKey(), parseLong);
						}
					}
				}
				logCameraAndProcessingControlFields(labelName, "DEFAULT", defaultTextField);
			}
		});
		defaultTextField.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		defaultTextField.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));

		/** Step Text Field **/
		TextField StepTextField = new TextField();
		StepTextField.setId("cameraControlStep"+cameraControlsList.size());
		StepTextField.setTooltip(new Tooltip(tooltip + " Step Value"));
		StepTextField.getTooltip().setOnShowing(s -> {
			Bounds bounds = StepTextField.localToScreen(StepTextField.getBoundsInLocal());
			StepTextField.getTooltip().setX(bounds.getMaxX());
		});
		StepTextField.setAlignment(Pos.CENTER_RIGHT);
		if (cameraControlJson.getENABLE().equals("Enable")) {
			StepTextField.setDisable(false);
		} else {
			StepTextField.setDisable(true);
		}
		StepTextField.setText(String.valueOf(cameraControlJson.getSTEP()));
		cameraControlTable.setStep(StepTextField);
		StepTextField.textProperty().addListener(new CameraAndProcessingControlValidation(StepTextField,
				configProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), "STEP", StepTextField, contextMenu));
		StepTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (StepTextField.getText().equals("")) {
					StepTextField.setText("0");
				}
				for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
					LinkedHashMap<String, Object> value = entry1.getValue();
					for (Map.Entry<String, Object> entry : value.entrySet()) {
						if (entry.getKey().contains("_STEP")) {
							Long parseLong = Long.parseLong(StepTextField.getText());
							entry1.getValue().put(entry.getKey(), parseLong);
						}
					}
				}
				logCameraAndProcessingControlFields(labelName, "STEP", StepTextField);
			}
		});
		StepTextField.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		StepTextField.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));

		/** Len Text Field **/
		TextField lenTextField = new TextField();
		lenTextField.setId("cameraControlLength"+cameraControlsList.size());
		lenTextField.setTooltip(new Tooltip(tooltip + " Len"));
		lenTextField.getTooltip().setOnShowing(s -> {
			Bounds bounds = lenTextField.localToScreen(lenTextField.getBoundsInLocal());
			lenTextField.getTooltip().setX(bounds.getMaxX());
		});
		lenTextField.setAlignment(Pos.CENTER_RIGHT);
		lenTextField.setText(String.valueOf(cameraControlJson.getLEN()));
		lenTextField.setDisable(true);
		cameraControlTable.setLen(lenTextField);

		Label extraLabel = new Label("0x");
		cameraControlTable.setExtraColumn(extraLabel);

		/** Register Address Text Field **/
		TextField registerAddressTextField = new TextField();
		registerAddressTextField.setId("cameraControlRegisterAddress"+cameraControlsList.size());
		registerAddressTextField.setTooltip(new Tooltip(tooltip + " Register Address"));
		registerAddressTextField.getTooltip().setOnShowing(s -> {
			Bounds bounds = registerAddressTextField.localToScreen(registerAddressTextField.getBoundsInLocal());
			registerAddressTextField.getTooltip().setX(bounds.getMaxX());
		});
		registerAddressTextField.setAlignment(Pos.CENTER_RIGHT);
		if (cameraControlJson.getENABLE().equals("Enable")) {
			registerAddressTextField.setDisable(false);
		} else {
			registerAddressTextField.setDisable(true);
		}
		registerAddressTextField.setText(cameraControlJson.getREGISTER_ADDRESS());
		if (enableCheckBox.isSelected() && registerAddressTextField.getText().equals("")) {
			registerAddressTextField.setStyle("-fx-border-color:red;");
			logDetails2.setText(
					"<span style='color:red;'>Error : REGISTER_ADDRESS should not be empty in camera control.</span><br>");
		}else {
			uvcuacTabErrorList.put(labelName.getText(), false);
		}
		cameraControlTable.setRegisterAddress(registerAddressTextField);
		contextMenu = UVCSettingsValidation.setupValidationForHexTextField(uvcuacTabErrorList, rootTab, subTab,
				subSubTab, registerAddressTextField, configProperties.getProperty("INVALID_HEX_ERROR_MESSAGE"), map,
				"REGISTER_ADDRESS", labelName.getText(), performLoadAction, cameraControlJson.getENABLE());
		registerAddressTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (!registerAddressTextField.getText().equals("")) {
					int i = 0; 
					while (i < registerAddressTextField.getText().length() 
							&& registerAddressTextField.getText().charAt(i) == '0') {
						StringBuffer sb = new StringBuffer(registerAddressTextField.getText()); 
						sb.replace(0, i, ""); 
						registerAddressTextField.setText(sb.toString());
						i++;
					}
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("REGISTER_ADDRESS")) {
								entry1.getValue().put(entry.getKey(),
										SX3PropertiesConstants.HEX_PREFIX + registerAddressTextField.getText());
							}
						}
					}
					logCameraAndProcessingControlFields(labelName, "REGISTER_ADDRESS", registerAddressTextField);
					registerAddressTextField.setStyle("");
					logDetails2.setText("");
				} else {
					if(cameraControlJson.getENABLE().equals("Enable")) {
						registerAddressTextField.setText("1");
					}
				}
			}
		});
		registerAddressTextField.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		registerAddressTextField.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));

		cameraControlsList.add(cameraControlTable);
		enableCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (enableCheckBox.isSelected()) {
					cameraControlJson.setENABLE("Enable");
					SX3Manager.getInstance().addLog(labelName.getText() + " Enable : " + enableCheckBox.isSelected() + ".<br>");
					
					
					minTextField.setDisable(false);
					maxTextField.setDisable(false);
					defaultTextField.setDisable(false);
					StepTextField.setDisable(false);
					registerAddressTextField.setDisable(false);
					registerAddressTextField.setText("1");
					registerAddressTextField.requestFocus();
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("ENABLE")) {
								entry1.getValue().put(entry.getKey(), "Enable");
							}
						}
					}
					if (labelName.getText().contains("Auto-Exposure Mode")
							|| labelName.getText().contains("Auto-Exposure Priority")) {
						lenTextField.setText("1");
						for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
							LinkedHashMap<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if (entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 1);
								}
							}
						}
					} else if (labelName.getText().contains("Exposure Time")) {
						lenTextField.setText("4");
						for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
							LinkedHashMap<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if (entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 4);
								}
							}
						}
					} else if (labelName.getText().contains("Focus") || labelName.getText().contains("Iris")
							|| labelName.getText().contains("Zoom") || labelName.getText().contains("Roll")) {
						lenTextField.setText("2");
						for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
							LinkedHashMap<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if (entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 2);
								}
							}
						}
					} else if (labelName.getText().contains("Pan Tilt")) {
						lenTextField.setText("8");
						for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
							LinkedHashMap<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if (entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 8);
								}
							}
						}
					} else if (labelName.getText().contains("Window Control")) {
						lenTextField.setText("12");
						for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
							LinkedHashMap<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if (entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 12);
								}
							}
						}
					} else if (labelName.getText().contains("Region of Interest")) {
						lenTextField.setText("10");
						for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
							LinkedHashMap<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if (entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 10);
								}
							}
						}
					}
				} else {
					cameraControlJson.setENABLE("Disable");
					SX3Manager.getInstance().addLog(labelName.getText() + " Enable : " + enableCheckBox.isSelected() + ".<br>");
					
					registerAddressTextField.setStyle("");
					uvcuacTabErrorList.put(labelName.getText(), false);
					if(!uvcuacTabErrorList.containsValue(true)) {
						rootTab.setStyle("");
						subTab.setStyle("");
						subSubTab.setStyle("");
						logDetails2.setText("");
					}
					minTextField.setDisable(true);
					maxTextField.setDisable(true);
					defaultTextField.setDisable(true);
					StepTextField.setDisable(true);
					registerAddressTextField.requestFocus();
					registerAddressTextField.setDisable(true);
					registerAddressTextField.setStyle("");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("ENABLE")) {
								entry1.getValue().put(entry.getKey(), "Disable");
							}
						}
					}
					lenTextField.setText("0");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("LENGTH")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					minTextField.setText("0");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("MIN")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					maxTextField.setText("0");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("MAX")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					defaultTextField.setText("0");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("DEFAULT")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					StepTextField.setText("0");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("STEP")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					registerAddressTextField.setText("");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : map1.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("REGISTER_ADDRESS")) {
								entry1.getValue().put(entry.getKey(), "");
							}
						}
					}
				}

			}
		});
		return cameraControlsList;
	}

	private static void logCameraAndProcessingControlFields(Label labelName, String columnName, TextField minTextField) {
		SX3Manager.getInstance().addLog(labelName.getText() + " " + columnName + " : " + minTextField.getText() + ".<br>");
	}

	public static ObservableList<CameraAndProcessingUnitControlsTableModel> addRowsInProcessingUnitControlTable(
			Map<String, Boolean> uvcuacTabErrorList, Tab rootTab, Tab subTab, Tab subSubTab, String label_Name,
			ObservableList<CameraAndProcessingUnitControlsTableModel> processingUnitControlsList,
			LinkedHashMap<String, LinkedHashMap<String, Object>> linkedHashMap, TextArea logDetails3,
			boolean performLoadAction, Properties configProperties) {
		CameraAndProcessingUnitControlsTableModel processingUnitControlTable = new CameraAndProcessingUnitControlsTableModel();

		CameraControlAndProcessingUnitJson processingUnitControlJson = new CameraControlAndProcessingUnitJson();
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
			map = entry1.getValue();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				Long intVal = null;
				if (entry.getValue() instanceof Double) {
					intVal = ((Double) entry.getValue()).longValue();
				}
				if (entry.getKey().contains("_ENABLE")) {
					processingUnitControlJson.setENABLE(entry.getValue().toString());
				} else if (entry.getKey().contains("_MIN")) {
					processingUnitControlJson.setMIN(intVal);
				} else if (entry.getKey().contains("_MAX")) {
					processingUnitControlJson.setMAX(intVal);
				} else if (entry.getKey().contains("_STEP")) {
					processingUnitControlJson.setSTEP(intVal);
				} else if (entry.getKey().contains("_DEFAULT")) {
					processingUnitControlJson.setDEFAULT(intVal);
				} else if (entry.getKey().contains("_LENGTH")) {
					processingUnitControlJson.setLEN(intVal);
				} else if (entry.getKey().contains("_REGISTER_ADDRESS")) {
					if (!entry.getValue().toString().isEmpty() && entry.getValue().toString() != null) {
						processingUnitControlJson.setREGISTER_ADDRESS(entry.getValue().toString().substring(2));
					} else {
						processingUnitControlJson.setREGISTER_ADDRESS(entry.getValue().toString());
					}
				}
			}
		}

		map.put("RESERVED", "FF");
		String tooltip = SX3ConfigurationTooltipController.getprocessingUnitControlTooltip(label_Name,
				configProperties);

		/** Label Name **/
		Label labelName = new Label();
		labelName.setText(label_Name);
		labelName.setStyle("-fx-font-weight: bold;-fx-font-size:12px;");
		labelName.setPadding(new Insets(0, 0, 0, 5));
		processingUnitControlTable.setLabelName(labelName);
		labelName.setTooltip(new Tooltip(tooltip));
		labelName.getTooltip().setOnShowing(s -> {
			Bounds bounds = labelName.localToScreen(labelName.getBoundsInLocal());
			labelName.getTooltip().setX(bounds.getMaxX());
		});
		labelName.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));

		/** Enable Checkbox **/
		CheckBox enableCheckBox = new CheckBox();
		enableCheckBox.setId("processingControlEnable"+processingUnitControlsList.size());
		enableCheckBox.setTooltip(new Tooltip(tooltip + " Enable/Disable"));
		enableCheckBox.getTooltip().setOnShowing(s -> {
			Bounds bounds = enableCheckBox.localToScreen(enableCheckBox.getBoundsInLocal());
			enableCheckBox.getTooltip().setX(bounds.getMaxX());
		});
		if (processingUnitControlJson.getENABLE().equals("Enable")) {
			enableCheckBox.setSelected(true);
		} else {
			enableCheckBox.setSelected(false);
		}
		processingUnitControlTable.setEnableLabel(enableCheckBox);
		enableCheckBox.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));

		/** Min Text Field **/
		TextField minTextField = new TextField();
		minTextField.setId("processingControlMin"+processingUnitControlsList.size());
		minTextField.setAlignment(Pos.CENTER_RIGHT);
		minTextField.setTooltip(new Tooltip(tooltip + " Min Value"));
		minTextField.getTooltip().setOnShowing(s -> {
			Bounds bounds = minTextField.localToScreen(minTextField.getBoundsInLocal());
			minTextField.getTooltip().setX(bounds.getMaxX());
		});
		if (processingUnitControlJson.getENABLE().equals("Enable")) {
			minTextField.setDisable(false);
		} else {
			minTextField.setDisable(true);
		}
		minTextField.setText(String.valueOf(processingUnitControlJson.getMIN()));
		processingUnitControlTable.setMin(minTextField);
		minTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (minTextField.getText().equals("")) {
					minTextField.setText("0");
				}
				for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
					LinkedHashMap<String, Object> value = entry1.getValue();
					for (Map.Entry<String, Object> entry : value.entrySet()) {
						if (entry.getKey().contains("_MIN")) {
							Long parseLong = Long.parseLong(minTextField.getText());
							entry1.getValue().put(entry.getKey(), parseLong);
						}
					}
				}
				logCameraAndProcessingControlFields(labelName, "MIN", minTextField);
			}
		});
		minTextField.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		minTextField.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));

		/** Max Text Field **/
		TextField maxTextField = new TextField();
		maxTextField.setId("processingControlMax"+processingUnitControlsList.size());
		maxTextField.setAlignment(Pos.CENTER_RIGHT);
		maxTextField.setTooltip(new Tooltip(tooltip + " Max Value"));
		maxTextField.getTooltip().setOnShowing(s -> {
			Bounds bounds = maxTextField.localToScreen(maxTextField.getBoundsInLocal());
			maxTextField.getTooltip().setX(bounds.getMaxX());
		});
		if (processingUnitControlJson.getENABLE().equals("Enable")) {
			maxTextField.setDisable(false);
		} else {
			maxTextField.setDisable(true);
		}
		maxTextField.setText(String.valueOf(processingUnitControlJson.getMAX()));
		processingUnitControlTable.setMax(maxTextField);
		maxTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (maxTextField.getText().equals("")) {
					maxTextField.setText("0");
				}
				for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
					LinkedHashMap<String, Object> value = entry1.getValue();
					for (Map.Entry<String, Object> entry : value.entrySet()) {
						if (entry.getKey().contains("_MAX")) {
							Long parseLong = Long.parseLong(maxTextField.getText());
							entry1.getValue().put(entry.getKey(), parseLong);
						}
					}
				}
				logCameraAndProcessingControlFields(labelName, "MAX", maxTextField);
			}
		});
		minTextField.textProperty().addListener(new CameraAndProcessingControlValidation(minTextField,
				configProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), "MIN", maxTextField, contextMenu));
		maxTextField.textProperty().addListener(new CameraAndProcessingControlValidation(maxTextField,
				configProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), "MAX", minTextField, contextMenu));
		maxTextField.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		maxTextField.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));

		/** Default Text Field **/
		TextField defaultTextField = new TextField();
		defaultTextField.setId("processingControlDefault"+processingUnitControlsList.size());
		defaultTextField.setAlignment(Pos.CENTER_RIGHT);
		defaultTextField.setTooltip(new Tooltip(tooltip + " Default Value"));
		defaultTextField.getTooltip().setOnShowing(s -> {
			Bounds bounds = defaultTextField.localToScreen(defaultTextField.getBoundsInLocal());
			defaultTextField.getTooltip().setX(bounds.getMaxX());
		});
		if (processingUnitControlJson.getENABLE().equals("Enable")) {
			defaultTextField.setDisable(false);
		} else {
			defaultTextField.setDisable(true);
		}
		defaultTextField.setText(String.valueOf(processingUnitControlJson.getDEFAULT()));
		processingUnitControlTable.setDefaultValue(defaultTextField);
		defaultTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (defaultTextField.getText().equals("")) {
					defaultTextField.setText("0");
				}
				for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
					LinkedHashMap<String, Object> value = entry1.getValue();
					for (Map.Entry<String, Object> entry : value.entrySet()) {
						if (entry.getKey().contains("_DEFAULT")) {
							Long parseLong = Long.parseLong(defaultTextField.getText());
							entry1.getValue().put(entry.getKey(), parseLong);
						}
					}
				}
				logCameraAndProcessingControlFields(labelName, "DEFAULT", minTextField);
			}
		});
		defaultTextField.textProperty()
				.addListener(new CameraAndProcessingControlValidation(defaultTextField,
						configProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), "DEFAULT", defaultTextField,
						contextMenu));
		defaultTextField.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		defaultTextField.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));

		/** Step Text Field **/
		TextField StepTextField = new TextField();
		StepTextField.setId("processingControlStep"+processingUnitControlsList.size());
		StepTextField.setAlignment(Pos.CENTER_RIGHT);
		StepTextField.setTooltip(new Tooltip(tooltip + " Step Value"));
		StepTextField.getTooltip().setOnShowing(s -> {
			Bounds bounds = StepTextField.localToScreen(StepTextField.getBoundsInLocal());
			StepTextField.getTooltip().setX(bounds.getMaxX());
		});
		if (processingUnitControlJson.getENABLE().equals("Enable")) {
			StepTextField.setDisable(false);
		} else {
			StepTextField.setDisable(true);
		}
		StepTextField.setText(String.valueOf(processingUnitControlJson.getSTEP()));
		processingUnitControlTable.setStep(StepTextField);
		StepTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (StepTextField.getText().equals("")) {
					StepTextField.setText("0");
				}
				for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
					LinkedHashMap<String, Object> value = entry1.getValue();
					for (Map.Entry<String, Object> entry : value.entrySet()) {
						if (entry.getKey().contains("_STEP")) {
							Long parseLong = Long.parseLong(StepTextField.getText());
							entry1.getValue().put(entry.getKey(), parseLong);
						}
					}
				}
				logCameraAndProcessingControlFields(labelName, "STEP", minTextField);
			}
		});
		StepTextField.textProperty().addListener(new CameraAndProcessingControlValidation(StepTextField,
				configProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), "STEP", StepTextField, contextMenu));
		StepTextField.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		StepTextField.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));

		/** Len Text Field **/
		TextField lenTextField = new TextField();
		lenTextField.setId("processingControlLength"+processingUnitControlsList.size());
		lenTextField.setTooltip(new Tooltip(tooltip + " Len"));
		lenTextField.getTooltip().setOnShowing(s -> {
			Bounds bounds = lenTextField.localToScreen(lenTextField.getBoundsInLocal());
			lenTextField.getTooltip().setX(bounds.getMaxX());
		});
		lenTextField.setAlignment(Pos.CENTER_RIGHT);
		lenTextField.setText(String.valueOf(processingUnitControlJson.getLEN()));
		lenTextField.setDisable(true);
		processingUnitControlTable.setLen(lenTextField);

		Label extraLabel = new Label("0x");
		processingUnitControlTable.setExtraColumn(extraLabel);

		/** Register Address Text Field **/
		TextField registerAddressTextField = new TextField();
		registerAddressTextField.setId("processingControlRegisterAddress"+processingUnitControlsList.size());
		registerAddressTextField.setAlignment(Pos.CENTER_RIGHT);
		registerAddressTextField.setTooltip(new Tooltip(tooltip + " Register Address"));
		registerAddressTextField.getTooltip().setOnShowing(s -> {
			Bounds bounds = registerAddressTextField.localToScreen(registerAddressTextField.getBoundsInLocal());
			registerAddressTextField.getTooltip().setX(bounds.getMaxX());
		});
		if (processingUnitControlJson.getENABLE().equals("Enable")) {
			registerAddressTextField.setDisable(false);
		} else {
			registerAddressTextField.setDisable(true);
		}
		registerAddressTextField.setText(processingUnitControlJson.getREGISTER_ADDRESS());
		if (enableCheckBox.isSelected() && registerAddressTextField.getText().equals("")) {
			rootTab.setStyle("-fx-border-color:red;");
			subTab.setStyle("-fx-border-color:red;");
			subSubTab.setStyle("-fx-border-color:red;");
			registerAddressTextField.setStyle("-fx-border-color:red;");
			logDetails3.setText(
					"<span style='color:red;'>Error : REGISTER_ADDRESS should not be empty in processing unit control.</span><br>");
		}
		contextMenu = UVCSettingsValidation.setupValidationForHexTextField(uvcuacTabErrorList, rootTab, subTab,
				subSubTab, registerAddressTextField, configProperties.getProperty("INVALID_HEX_ERROR_MESSAGE"), map,
				"REGISTER_ADDRESS", labelName.getText(), performLoadAction, processingUnitControlJson.getENABLE());
		processingUnitControlTable.setRegisterAddress(registerAddressTextField);
		registerAddressTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				int i = 0; 
				while (i < registerAddressTextField.getText().length() 
						&& registerAddressTextField.getText().charAt(i) == '0') {
					StringBuffer sb = new StringBuffer(registerAddressTextField.getText()); 
					sb.replace(0, i, ""); 
					registerAddressTextField.setText(sb.toString());
					i++;
				}
				if (!registerAddressTextField.getText().equals("")) {
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("REGISTER_ADDRESS")) {
								entry1.getValue().put(entry.getKey(),
										SX3PropertiesConstants.HEX_PREFIX + registerAddressTextField.getText());
							}
						}
					}
					logCameraAndProcessingControlFields(labelName, "REGISTER_ADDRESS", registerAddressTextField);
					registerAddressTextField.setStyle("");
					logDetails3.setText("");
				} else {
					
					if(enableCheckBox.isSelected()) {
						registerAddressTextField.setText("1");
					}
				}
			}
		});
		registerAddressTextField.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		registerAddressTextField.setOnMouseClicked(
				new ShowCameraAndProcessingControlHelp(labelName));

		processingUnitControlsList.add(processingUnitControlTable);
		enableCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if (enableCheckBox.isSelected()) {
					processingUnitControlJson.setENABLE("Enable");
					minTextField.setDisable(false);
					maxTextField.setDisable(false);
					defaultTextField.setDisable(false);
					StepTextField.setDisable(false);
					registerAddressTextField.setDisable(false);
					registerAddressTextField.setText("1");
					registerAddressTextField.requestFocus();
					SX3Manager.getInstance().addLog(labelName.getText() + " Enable : " + true + ".<br>");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("ENABLE")) {
								entry1.getValue().put(entry.getKey(), "Enable");
							}
						}
					}
					if (labelName.getText().equals("Brightness") || labelName.getText().equals("Contrast")
							|| labelName.getText().equals("Saturation") || labelName.getText().equals("Hue")
							|| labelName.getText().equals("Sharpness") || labelName.getText().equals("Gamma")
							|| labelName.getText().equals("White Balance Temperature")
							|| labelName.getText().equals("Backlight Compensation")
							|| labelName.getText().equals("Gain")) {
						lenTextField.setText("2");
						for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
							LinkedHashMap<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if (entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 2);
								}
							}
						}
					} else if (labelName.getText().contains("White Balance Component")) {
						lenTextField.setText("4");
						for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
							LinkedHashMap<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if (entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 4);
								}
							}
						}
					} else if (labelName.getText().contains("Power Line Frequency")
							|| labelName.getText().contains("Hue Auto")
							|| labelName.getText().contains("White Balance Temperature Auto")
							|| labelName.getText().contains("White Balance Component Auto")) {
						lenTextField.setText("1");
						for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
							LinkedHashMap<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if (entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 1);
								}
							}
						}
					}
				} else {
					registerAddressTextField.setStyle("");
					uvcuacTabErrorList.put(labelName.getText(), false);
					if(!uvcuacTabErrorList.containsValue(true)) {
						rootTab.setStyle("");
						subTab.setStyle("");
						subSubTab.setStyle("");
						logDetails3.setText("");
					}
					processingUnitControlJson.setENABLE("Disable");
					minTextField.setDisable(true);
					maxTextField.setDisable(true);
					defaultTextField.setDisable(true);
					StepTextField.setDisable(true);
					registerAddressTextField.requestFocus();
					registerAddressTextField.setStyle("");
					registerAddressTextField.setDisable(true);
					SX3Manager.getInstance().addLog(labelName.getText() + " Enable : " + false + ".<br>");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("ENABLE")) {
								entry1.getValue().put(entry.getKey(), "Disable");
							}
						}
					}
					lenTextField.setText("0");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("LENGTH")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					minTextField.setText("0");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("MIN")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					maxTextField.setText("0");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("MAX")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					defaultTextField.setText("0");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("DEFAULT")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					StepTextField.setText("0");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("STEP")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					registerAddressTextField.setText("");
					for (Entry<String, LinkedHashMap<String, Object>> entry1 : linkedHashMap.entrySet()) {
						LinkedHashMap<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("REGISTER_ADDRESS")) {
								entry1.getValue().put(entry.getKey(), "");
							}
						}
					}
				}

			}
		});
		return processingUnitControlsList;
	}

	@SuppressWarnings("static-access")
	public static AnchorPane createExtensionUnitControlUI(AnchorPane anchorPane,
			ExtensionUnitControl extensionUnitControl, Properties configProperties) {
		AnchorPane anchorPane1 = new AnchorPane();
		anchorPane1.setLayoutX(50);
		anchorPane1.setLayoutY(20);
		anchorPane1.setStyle("-fx-background-color: #D0D3D4; -fx-border-insets: 4 1 1 1; -fx-border-color: black;");
		Label endpointLabel = new Label("Vendor Commands");
		endpointLabel.setLayoutX(6);
		endpointLabel.setLayoutY(-5);
		endpointLabel.setStyle("-fx-background-color: inherit;");
		GridPane leftAnchorGridPane = new GridPane();
		leftAnchorGridPane.setLayoutX(5);
		leftAnchorGridPane.setLayoutY(20);
		leftAnchorGridPane.setVgap(5.0);

		/** Device Reset **/
		Label deviceResetLabel = new Label("Device Reset : ");
		leftAnchorGridPane.setMargin(deviceResetLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(deviceResetLabel, 0, 0);
		CheckBox deviceResetCheckBox = new CheckBox();
		deviceResetCheckBox.setId("deviceReset");
		deviceResetCheckBox
				.setTooltip(new Tooltip(configProperties.getProperty("EXTENTION_UNIT_CONTROL.TOOLTIP_DEVICE_RESET")));
		deviceResetCheckBox.getTooltip().setOnShowing(s -> {
			Bounds bounds = deviceResetCheckBox.localToScreen(deviceResetCheckBox.getBoundsInLocal());
			deviceResetCheckBox.getTooltip().setX(bounds.getMaxX());
		});
		if (extensionUnitControl.getDEVICE_RESET_VENDOR_COMMAND_ENABLED().equals("Enable")) {
			deviceResetCheckBox.setSelected(true);
		} else {
			deviceResetCheckBox.setSelected(false);
		}
		leftAnchorGridPane.setMargin(deviceResetCheckBox, new Insets(0, 5, 0, 0));
		leftAnchorGridPane.add(deviceResetCheckBox, 1, 0);
		deviceResetCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (deviceResetCheckBox.isSelected()) {
					extensionUnitControl.setDEVICE_RESET_VENDOR_COMMAND_ENABLED("Enable");
					SX3Manager.getInstance().addLog("Device Reset : " + true + ".<br>");
				} else {
					extensionUnitControl.setDEVICE_RESET_VENDOR_COMMAND_ENABLED("Disable");
					SX3Manager.getInstance().addLog("Device Reset : " + false + ".<br>");
					
					
				}
			}
		});
		deviceResetCheckBox.setOnMouseClicked(new OnMouseClickHandler());

		/** I2C Register Read **/
		Label i2cregisterLabel = new Label("I2C Register Read : ");
		leftAnchorGridPane.setMargin(i2cregisterLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(i2cregisterLabel, 0, 1);
		CheckBox i2cRegisterCheckBox = new CheckBox();
		i2cRegisterCheckBox.setId("I2cRegisterRead");
		i2cRegisterCheckBox.setTooltip(
				new Tooltip(configProperties.getProperty("EXTENTION_UNIT_CONTROL.TOOLTIP_I2C_REGISTER_READ")));
		i2cRegisterCheckBox.getTooltip().setOnShowing(s -> {
			Bounds bounds = i2cRegisterCheckBox.localToScreen(i2cRegisterCheckBox.getBoundsInLocal());
			i2cRegisterCheckBox.getTooltip().setX(bounds.getMaxX());
		});
		if (extensionUnitControl.getI2C_READ_VENDOR_COMMAND_ENABLED().equals("Enable")) {
			i2cRegisterCheckBox.setSelected(true);
		} else {
			i2cRegisterCheckBox.setSelected(false);
		}
		leftAnchorGridPane.setMargin(i2cRegisterCheckBox, new Insets(0, 5, 0, 0));
		leftAnchorGridPane.add(i2cRegisterCheckBox, 1, 1);
		i2cRegisterCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (i2cRegisterCheckBox.isSelected()) {
					extensionUnitControl.setI2C_READ_VENDOR_COMMAND_ENABLED("Enable");
					SX3Manager.getInstance().addLog("I2C Register Read : " + true + ".<br>");
					
					
				} else {
					extensionUnitControl.setI2C_READ_VENDOR_COMMAND_ENABLED("Disable");
					SX3Manager.getInstance().addLog("I2C Register Read : " + false + ".<br>");
					
					
				}
			}
		});
		i2cRegisterCheckBox.setOnMouseClicked(new OnMouseClickHandler());

		/** I2C Register Write **/
		Label i2cRegisterWhiteLabel = new Label("I2C Register Write : ");
		leftAnchorGridPane.setMargin(i2cRegisterWhiteLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(i2cRegisterWhiteLabel, 0, 2);
		CheckBox i2cRegisterWriteCheckBox = new CheckBox();
		i2cRegisterWriteCheckBox.setId("I2CRegisterWrite");
		i2cRegisterWriteCheckBox.setTooltip(
				new Tooltip(configProperties.getProperty("EXTENTION_UNIT_CONTROL.TOOLTIP_I2C_REGISTER_WRITE")));
		i2cRegisterWriteCheckBox.getTooltip().setOnShowing(s -> {
			Bounds bounds = i2cRegisterWriteCheckBox.localToScreen(i2cRegisterWriteCheckBox.getBoundsInLocal());
			i2cRegisterWriteCheckBox.getTooltip().setX(bounds.getMaxX());
		});
		if (extensionUnitControl.getI2C_WRITE_VENDOR_COMMAND_ENABLED().equals("Enable")) {
			i2cRegisterWriteCheckBox.setSelected(true);
		} else {
			i2cRegisterWriteCheckBox.setSelected(false);
		}
		leftAnchorGridPane.setMargin(i2cRegisterWriteCheckBox, new Insets(0, 5, 0, 0));
		leftAnchorGridPane.add(i2cRegisterWriteCheckBox, 1, 2);
		i2cRegisterWriteCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (i2cRegisterWriteCheckBox.isSelected()) {
					extensionUnitControl.setI2C_WRITE_VENDOR_COMMAND_ENABLED("Enable");
					SX3Manager.getInstance().addLog("I2C Register Write : " + true + ".<br>");
					
					
				} else {
					extensionUnitControl.setI2C_WRITE_VENDOR_COMMAND_ENABLED("Disable");
					SX3Manager.getInstance().addLog("I2C Register Write : " + false + ".<br>");
					
					
				}
			}
		});
		i2cRegisterWriteCheckBox.setOnMouseClicked(new OnMouseClickHandler());

		/** Firmware Update **/
		Label firmwareUpdateLabel = new Label("Firmware Version : ");
		leftAnchorGridPane.setMargin(firmwareUpdateLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(firmwareUpdateLabel, 0, 3);
		CheckBox firmwareUpdateCheckBox = new CheckBox();
		firmwareUpdateCheckBox.setId("firmWareUpdate");
		firmwareUpdateCheckBox.setTooltip(
				new Tooltip(configProperties.getProperty("EXTENTION_UNIT_CONTROL.TOOLTIP_FIRMWARE_UPDATE")));
		firmwareUpdateCheckBox.getTooltip().setOnShowing(s -> {
			Bounds bounds = firmwareUpdateCheckBox.localToScreen(firmwareUpdateCheckBox.getBoundsInLocal());
			firmwareUpdateCheckBox.getTooltip().setX(bounds.getMaxX());
		});
		if (extensionUnitControl.getFIRMWARE_VERSION_VENDOR_COMMAND_ENABLED().equals("Enable")) {
			firmwareUpdateCheckBox.setSelected(true);
		} else {
			firmwareUpdateCheckBox.setSelected(false);
		}
		leftAnchorGridPane.add(firmwareUpdateCheckBox, 1, 3);
		firmwareUpdateCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (firmwareUpdateCheckBox.isSelected()) {
					extensionUnitControl.setFIRMWARE_VERSION_VENDOR_COMMAND_ENABLED("Enable");
					SX3Manager.getInstance().addLog("Firmware Version : " + true + ".<br>");
					
					
				} else {
					extensionUnitControl.setFIRMWARE_VERSION_VENDOR_COMMAND_ENABLED("Disable");
					SX3Manager.getInstance().addLog("Firmware Version : " + false + ".<br>");
					
					
				}
			}
		});
		firmwareUpdateCheckBox.setOnMouseClicked(new OnMouseClickHandler());

		anchorPane1.getChildren().add(leftAnchorGridPane);
		anchorPane1.getChildren().add(endpointLabel);
		anchorPane.getChildren().addAll(anchorPane1);
		return anchorPane;
	}

	@SuppressWarnings("static-access")
	public static void createUACSettingsTabUI(AnchorPane anchorPane, UACSettings uacSettings,
			Properties tooltipAndErrorProperties) {
		BorderPane borderPane = new BorderPane();
		anchorPane.getChildren().add(borderPane);
		Map<String, String> channelConfiguration = uacSettings.getUAC_SETTING().getCHANNEL_CONFIGURATION();
		Map<String, String> featureUnitControl = uacSettings.getUAC_SETTING().getFEATURE_UNIT_CONTROLS();
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(7);
		borderPane.setTop(gridPane);
		borderPane.setMargin(gridPane, new Insets(10, 50, 20, 0));

		/** Terminal Type **/
		Label terminalTypeLabel = new Label("Terminal Type :");
		gridPane.add(terminalTypeLabel, 0, 0);
		ComboBox<String> terminalTypeValue = new ComboBox<>();
		terminalTypeValue.setId("terminalType");
		terminalTypeValue.getItems().addAll(UACSettingFieldConstants.TERMINAL_TYPE);
		terminalTypeValue
				.setTooltip(new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_TERMINAL_TYPE")));
		terminalTypeValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = terminalTypeValue.localToScreen(terminalTypeValue.getBoundsInLocal());
			terminalTypeValue.getTooltip().setX(bounds.getMaxX());
		});
		terminalTypeValue.setValue(uacSettings.getUAC_SETTING().getTERMINAL_TYPE());
		gridPane.add(terminalTypeValue, 1, 0);
		terminalTypeValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				uacSettings.getUAC_SETTING().setTERMINAL_TYPE(terminalTypeValue.getValue());
				SX3Manager.getInstance().addLog("Terminal Type : " + terminalTypeValue.getValue() + ".<br>");
				
				
			}
		});
		terminalTypeValue.setOnMouseClicked(new OnMouseClickHandler());

		/** Number of Channels **/
		Label numberOfChannelsLabel = new Label("Number of Channels : ");
		gridPane.add(numberOfChannelsLabel, 0, 1);
		TextField numberOfChannelsValue = new TextField();
		numberOfChannelsValue.setId("NoOfChannelsValue");
		numberOfChannelsValue.setPrefWidth(30.0);
		gridPane.add(numberOfChannelsValue, 1, 1);
		numberOfChannelsValue.setDisable(true);
		numberOfChannelsValue.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_NUMBER_OF_CHANNELS")));
		numberOfChannelsValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = numberOfChannelsValue.localToScreen(numberOfChannelsValue.getBoundsInLocal());
			numberOfChannelsValue.getTooltip().setX(bounds.getMaxX());
		});
		numberOfChannelsValue.setOnMouseClicked(new OnMouseClickHandler());
		numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));

		/** Channel Configuration **/
		Label channelConfigurationLabel = new Label("Channel Configuration : ");
		gridPane.add(channelConfigurationLabel, 0, 2);

		/** D0 **/
		CheckBox leftFrontCheckBox = new CheckBox();
		if (channelConfiguration.get("D0: Left Front (L)").equals("Enable")) {
			leftFrontCheckBox.setSelected(true);
		} else {
			leftFrontCheckBox.setSelected(false);
		}
		leftFrontCheckBox.setId("channelConfigurationLeftCheckBox");
		leftFrontCheckBox.setText("D0: Left Front (L)");
		leftFrontCheckBox.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_CHANNELS_CONFIGURATION")));
		leftFrontCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (leftFrontCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D0: Left Front (L)", "Enable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D0: Left Front (L) : " + "Enable" + ".<br>");
					
					
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D0: Left Front (L)", "Disable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D0: Left Front (L) : " + "Disable" + ".<br>");
					
					
				}

			}
		});
		leftFrontCheckBox.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(leftFrontCheckBox, 1, 2);

		/** D1 **/
		CheckBox rightFrontCheckBox = new CheckBox();
		rightFrontCheckBox.setText("D1: Right Front (R)");
		if (channelConfiguration.get("D1: Right Front (R)").equals("Enable")) {
			rightFrontCheckBox.setSelected(true);
		} else {
			rightFrontCheckBox.setSelected(false);
		}
		rightFrontCheckBox.setId("channelConfigurationRightCheckBox");
		rightFrontCheckBox.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_CHANNELS_CONFIGURATION")));
		rightFrontCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (rightFrontCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D1: Right Front (R)", "Enable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D1: Right Front (R) : Enable.<br>");
					
					
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D1: Right Front (R)", "Disable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D1: Right Front (R) : Disable.<br>");
					
					
				}

			}
		});
		rightFrontCheckBox.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(rightFrontCheckBox, 2, 2);

		/** D2 **/
		CheckBox centerFrontCheckBox = new CheckBox();
		centerFrontCheckBox.setText("D2: Center Front (C)");
		centerFrontCheckBox.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_CHANNELS_CONFIGURATION")));
		if (channelConfiguration.get("D2: Center Front (C)").equals("Enable")) {
			centerFrontCheckBox.setSelected(true);
		} else {
			centerFrontCheckBox.setSelected(false);
		}
		centerFrontCheckBox.setId("channelConfigurationCenterCheckBox");
		centerFrontCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (centerFrontCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D2: Center Front (C)", "Enable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D2: Center Front (C) : Enable.<br>");
					
					
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D2: Center Front (C)", "Disable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D2: Center Front (C) : Disable.<br>");
					
					
				}

			}
		});
		centerFrontCheckBox.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(centerFrontCheckBox, 3, 2);

		/** D3 **/
		CheckBox lowFrequencyEnhancementCheckBox = new CheckBox();
		if (channelConfiguration.get("D3: Low Frequency Enhancement (LFE)").equals("Enable")) {
			lowFrequencyEnhancementCheckBox.setSelected(true);
		} else {
			lowFrequencyEnhancementCheckBox.setSelected(false);
		}
		lowFrequencyEnhancementCheckBox.setId("lowFrequencyEnhancementCheckBox");
		lowFrequencyEnhancementCheckBox.setText("D3: Low Frequency Enhancement (LFE)");
		lowFrequencyEnhancementCheckBox.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_CHANNELS_CONFIGURATION")));
		lowFrequencyEnhancementCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (lowFrequencyEnhancementCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D3: Low Frequency Enhancement (LFE)", "Enable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D3: Low Frequency Enhancement (LFE) : Enable.<br>");
					
					
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D3: Low Frequency Enhancement (LFE)", "Disable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D3: Low Frequency Enhancement (LFE) : Disable.<br>");
					
					
				}

			}
		});
		lowFrequencyEnhancementCheckBox.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(lowFrequencyEnhancementCheckBox, 4, 2);

		/** D4 **/
		CheckBox leftSurroundCheckBox = new CheckBox();
		leftSurroundCheckBox.setText("D4: Left Surround (LS)");
		if (channelConfiguration.get("D4: Left Surround (LS)").equals("Enable")) {
			leftSurroundCheckBox.setSelected(true);
		} else {
			leftSurroundCheckBox.setSelected(false);
		}
		leftSurroundCheckBox.setId("leftSurroundCheckBox");
		leftSurroundCheckBox.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_CHANNELS_CONFIGURATION")));
		leftSurroundCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (leftSurroundCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D4: Left Surround (LS)", "Enable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D4: Left Surround (LS) : Enable.<br>");
					
					
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D4: Left Surround (LS)", "Disable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D4: Left Surround (LS) : Disable.<br>");
					
					
				}

			}
		});
		leftSurroundCheckBox.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(leftSurroundCheckBox, 1, 3);

		/** D5 **/
		CheckBox rightSurroundCheckBox = new CheckBox();
		rightSurroundCheckBox.setText("D5: Right Surround (RS)");
		if (channelConfiguration.get("D5: Right Surround (RS)").equals("Enable")) {
			rightSurroundCheckBox.setSelected(true);
		} else {
			rightSurroundCheckBox.setSelected(false);
		}
		rightSurroundCheckBox.setId("rightSurroundCheckBox");
		rightSurroundCheckBox.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_CHANNELS_CONFIGURATION")));
		rightSurroundCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (rightSurroundCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D5: Right Surround (RS)", "Enable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D5: Right Surround (RS) : Enable.<br>");
					
					
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D5: Right Surround (RS)", "Disable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D5: Right Surround (RS) : Disable.<br>");
					
					
				}

			}
		});
		rightSurroundCheckBox.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(rightSurroundCheckBox, 2, 3);

		/** D6 **/
		CheckBox leftOfCenterCheckBox = new CheckBox();
		leftOfCenterCheckBox.setText("D6: Left of Center (LC)");
		if (channelConfiguration.get("D6: Left of Center (LC)").equals("Enable")) {
			leftOfCenterCheckBox.setSelected(true);
		} else {
			leftOfCenterCheckBox.setSelected(false);
		}
		leftOfCenterCheckBox.setId("leftOfCenterCheckBox");
		leftOfCenterCheckBox.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_CHANNELS_CONFIGURATION")));
		leftOfCenterCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (leftOfCenterCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D6: Left of Center (LC)", "Enable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D6: Left of Center (LC) : Enable.<br>");
					
					
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D6: Left of Center (LC)", "Disable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D6: Left of Center (LC) : Disable.<br>");
					
					
				}

			}
		});
		leftOfCenterCheckBox.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(leftOfCenterCheckBox, 3, 3);

		/** D7 **/
		CheckBox rightOfCenterCheckBox = new CheckBox();
		rightOfCenterCheckBox.setText("D7: Right of Center (RC)");
		rightOfCenterCheckBox.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_CHANNELS_CONFIGURATION")));
		if (channelConfiguration.get("D7: Right of Center (RC)").equals("Enable")) {
			rightOfCenterCheckBox.setSelected(true);
		} else {
			rightOfCenterCheckBox.setSelected(false);
		}
		rightOfCenterCheckBox.setId("rightOfCenterCheckBox");
		rightOfCenterCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (rightOfCenterCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D7: Right of Center (RC)", "Enable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D7: Right of Center (RC) : Enable.<br>");
					
					
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D7: Right of Center (RC)", "Disable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D7: Right of Center (RC) : Disable.<br>");
					
					
				}

			}
		});
		rightOfCenterCheckBox.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(rightOfCenterCheckBox, 4, 3);

		/** D8 **/
		CheckBox surroundCheckBox = new CheckBox();
		surroundCheckBox.setText("D8: Surround (S)");
		if (channelConfiguration.get("D8: Surround (S)").equals("Enable")) {
			surroundCheckBox.setSelected(true);
		} else {
			surroundCheckBox.setSelected(false);
		}
		surroundCheckBox.setId("surroundCheckBox");
		surroundCheckBox.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_CHANNELS_CONFIGURATION")));
		surroundCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (surroundCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D8: Surround (S)", "Enable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D8: Surround (S) : Enable.<br>");
					
					
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D8: Surround (S)", "Disable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D8: Surround (S) : Disable.<br>");
					
					
				}

			}
		});
		surroundCheckBox.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(surroundCheckBox, 1, 4);

		/** D9 **/
		CheckBox sideLeftCheckBox = new CheckBox();
		sideLeftCheckBox.setText("D9: Side Left (SL)");
		if (channelConfiguration.get("D9: Side Left (SL)").equals("Enable")) {
			sideLeftCheckBox.setSelected(true);
		} else {
			sideLeftCheckBox.setSelected(false);
		}
		sideLeftCheckBox.setId("sideLeftCheckBox");
		sideLeftCheckBox.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_CHANNELS_CONFIGURATION")));
		sideLeftCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (sideLeftCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D9: Side Left (SL)", "Enable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D9: Side Left (SL) : Enable.<br>");
					
					
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D9: Side Left (SL)", "Disable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D9: Side Left (SL) : Disable.<br>");
					
					
				}

			}
		});
		sideLeftCheckBox.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(sideLeftCheckBox, 2, 4);

		/** D10 **/
		CheckBox sideRightCheckBox = new CheckBox();
		sideRightCheckBox.setText("D10: Side Right (SR)");
		if (channelConfiguration.get("D10: Side Right (SR)").equals("Enable")) {
			sideRightCheckBox.setSelected(true);
		} else {
			sideRightCheckBox.setSelected(false);
		}
		sideRightCheckBox.setId("sideRightCheckBox");
		sideRightCheckBox.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_CHANNELS_CONFIGURATION")));
		sideRightCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (sideRightCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D10: Side Right (SR)", "Enable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D10: Side Right (SR) : Enable.<br>");
					
					
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D10: Side Right (SR)", "Disable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D10: Side Right (SR) : Disable.<br>");
					
					
				}

			}
		});
		sideRightCheckBox.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(sideRightCheckBox, 3, 4);

		/** D11 **/
		CheckBox topCheckBox = new CheckBox();
		topCheckBox.setText("D11: Top (T)");
		if (channelConfiguration.get("D11: Top (T)").equals("Enable")) {
			topCheckBox.setSelected(true);
		} else {
			topCheckBox.setSelected(false);
		}
		topCheckBox.setId("topCheckBox");
		topCheckBox.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_CHANNELS_CONFIGURATION")));
		topCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (topCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D11: Top (T)", "Enable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D11: Top (T) : Enable.<br>");
					
					
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D11: Top (T)", "Disable");
					SX3Manager.getInstance().addLog(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".<br>");
					SX3Manager.getInstance().addLog("D11: Top (T) : Disable.<br>");
					
					
				}

			}
		});
		topCheckBox.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(topCheckBox, 4, 4);

		/** Audio Format **/
		Label audioFormatLabel = new Label("Audio Format : ");
		gridPane.add(audioFormatLabel, 0, 5);
		ComboBox<String> audioFormatValue = new ComboBox<>();
		audioFormatValue.setId("audioFormatValue");
		audioFormatValue
				.setTooltip(new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_AUDIO_FORMAT")));
		audioFormatValue.getTooltip().setOnShowing(s -> {
			Bounds bounds = audioFormatValue.localToScreen(audioFormatValue.getBoundsInLocal());
			audioFormatValue.getTooltip().setX(bounds.getMaxX());
		});
		audioFormatValue.getItems().addAll(UACSettingFieldConstants.AUDIO_FORMAT);
		audioFormatValue.setValue(uacSettings.getUAC_SETTING().getAUDIO_FORMAT());
		audioFormatValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				uacSettings.getUAC_SETTING().setAUDIO_FORMAT(audioFormatValue.getValue());
				SX3Manager.getInstance().addLog("Audio Format : " + audioFormatValue.getValue() + ".<br>");
				
				
			}
		});
		audioFormatValue.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(audioFormatValue, 1, 5);

		/** Bit Resolution **/
		Label bitResolutionLabel = new Label("Bit resolution : ");
		gridPane.add(bitResolutionLabel, 0, 6);
		ComboBox<String> bitResolutionValue = new ComboBox<>();
		bitResolutionValue.setId("bitResolutionValue");
		bitResolutionValue
				.setTooltip(new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_BIT_RESOLUTION")));
		bitResolutionValue.getItems().addAll(UACSettingFieldConstants.BIT_RESOLUTION);
		bitResolutionValue.setValue(uacSettings.getUAC_SETTING().getBIT_RESOLUTION());
		bitResolutionValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				uacSettings.getUAC_SETTING().setBIT_RESOLUTION(bitResolutionValue.getValue());
				SX3Manager.getInstance().addLog("Bit resolution : " + bitResolutionValue.getValue() + ".<br>");
				
				
			}
		});
		bitResolutionValue.setOnMouseClicked(new OnMouseClickHandler());
		gridPane.add(bitResolutionValue, 1, 6);

		/** Feature Unit Controls **/
		Label featureUnitControlLabel = new Label("Feature unit Controls : ");
		gridPane.add(featureUnitControlLabel, 0, 7);
		featureUnitControlLabel.setPadding(new Insets(0, 50, 0, 0));

		/** Mute Checkbox **/
		CheckBox muteCheckBox = new CheckBox();
		muteCheckBox.setText("D0: Mute");
		muteCheckBox.setId("FeatureUnitD0Mute");
		if (featureUnitControl.get("D0: Mute").equals("Enable")) {
			muteCheckBox.setSelected(true);
		} else {
			muteCheckBox.setSelected(false);
		}
		gridPane.add(muteCheckBox, 1, 7);
		muteCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (muteCheckBox.isSelected()) {
					featureUnitControl.put("D0: Mute", "Enable");
					SX3Manager.getInstance().addLog("D0: Mute : Enable.<br>");
					
					
				} else {
					featureUnitControl.put("D0: Mute", "Disable");
					SX3Manager.getInstance().addLog("D0: Mute : Disable.<br>");
					
					
				}
			}
		});

		/** Volume Checkbox **/
		CheckBox volumnCheckBox = new CheckBox();
		volumnCheckBox.setText("D1: Volume");
		volumnCheckBox.setId("FeatureUnitD1Volume");
		if (featureUnitControl.get("D1: Volume").equals("Enable")) {
			volumnCheckBox.setSelected(true);
		} else {
			volumnCheckBox.setSelected(false);
		}
		gridPane.add(volumnCheckBox, 2, 7);
		volumnCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (volumnCheckBox.isSelected()) {
					featureUnitControl.put("D1: Volume", "Enable");
					SX3Manager.getInstance().addLog("D1: Volume : Enable.<br>");
					
					
				} else {
					featureUnitControl.put("D1: Volume", "Disable");
					SX3Manager.getInstance().addLog("D1: Volume : Disable.<br>");
					
					
				}
			}
		});


		GridPane gridPane1 = new GridPane();
		gridPane1.setLayoutX(50);
		gridPane1.setHgap(10);
		gridPane1.setVgap(7);
		borderPane.setBottom(gridPane1);

		/** Number Of Sampling Frequencies **/
		Label numberOfSamplingFrequenciesLabel = new Label("Number of sampling frequencies : ");
		gridPane1.add(numberOfSamplingFrequenciesLabel, 0, 0);
		TextField numberOfSamplingFrequenciesValue = new TextField();
		numberOfSamplingFrequenciesValue.setTooltip(new Tooltip(
				tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_NUMBER_OF_SAMPLING_FREQUENCY")));
		numberOfSamplingFrequenciesValue.setPrefWidth(30.0);
		numberOfSamplingFrequenciesValue
				.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()));
		numberOfSamplingFrequenciesValue.setDisable(true);
		gridPane1.add(numberOfSamplingFrequenciesValue, 1, 0);

		/** Sampling Frequency 1 **/
		Label samplingFrequency1Label = new Label("Sampling Frequency 1 (Hz) : ");
		gridPane1.add(samplingFrequency1Label, 0, 1);
		ComboBox<String> samplingFrequency1Value = new ComboBox<>();
		samplingFrequency1Value.setId("samplingFrequency1Value");
		samplingFrequency1Value.setPrefWidth(100.0);
		samplingFrequency1Value.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_SAMPLING_FREQUENCY")));
		samplingFrequency1Value.getTooltip().setOnShowing(s -> {
			Bounds bounds = samplingFrequency1Value.localToScreen(samplingFrequency1Value.getBoundsInLocal());
			samplingFrequency1Value.getTooltip().setX(bounds.getMaxX());
		});
		samplingFrequency1Value.getItems().addAll(UACSettingFieldConstants.SAMPLING_FREQUENCIES);
		samplingFrequency1Value.getItems().remove("Not Used");
		if (uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_1() == 0) {
			samplingFrequency1Value.setValue("44100");
		} else {
			samplingFrequency1Value.setValue(String.valueOf(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_1()));
		}

		gridPane1.add(samplingFrequency1Value, 1, 1);
		Button samplingFrequency1SensorConfig = new Button();
		samplingFrequency1SensorConfig.setId("samplingFrequencySensorConfig");
		ImageView sensorConfigImage1 = new ImageView("/resources/sensorConfig.png");
		sensorConfigImage1.setFitHeight(15);
		sensorConfigImage1.setFitWidth(15);
		samplingFrequency1SensorConfig.setGraphic(sensorConfigImage1);
		gridPane1.add(samplingFrequency1SensorConfig, 2, 1);
		samplingFrequency1SensorConfig.setTooltip(new Tooltip("Sampling Frequency Config"));
		samplingFrequency1SensorConfig
				.setOnAction(new SensorConfigUI(samplingFrequency1SensorConfig, uacSettings.getUAC_SETTING(), "FQ1"));
		if (uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_1_SENSOR_CONFIG() != null
				&& !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_1_SENSOR_CONFIG().isEmpty()
				&& !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_1_SENSOR_CONFIG().get(0).getREGISTER_ADDRESS()
						.equals("")) {
			samplingFrequency1SensorConfig.setStyle("-fx-background-color:green");
		}
		/** Sampling Frequency **/
		Label samplingFrequency2Label = new Label("Sampling Frequency 2 (Hz) : ");
		samplingFrequency2Label.setPadding(new Insets(0, 0, 0, 100));
		gridPane1.add(samplingFrequency2Label, 3, 1);
		ComboBox<String> samplingFrequency2Value = new ComboBox<>();
		samplingFrequency2Value.setId("samplingFrequency2Value");
		samplingFrequency2Value.setPrefWidth(100.0);
		samplingFrequency2Value.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_SAMPLING_FREQUENCY")));
		samplingFrequency2Value.getTooltip().setOnShowing(s -> {
			Bounds bounds = samplingFrequency2Value.localToScreen(samplingFrequency2Value.getBoundsInLocal());
			samplingFrequency2Value.getTooltip().setX(bounds.getMaxX());
		});
		if (uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_2() == 0) {
			samplingFrequency2Value.setValue("Not Used");
		} else {
			samplingFrequency2Value.setValue(String.valueOf(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_2()));
		}
		samplingFrequency2Value.getItems().addAll(UACSettingFieldConstants.SAMPLING_FREQUENCIES);
		gridPane1.add(samplingFrequency2Value, 4, 1);
		Button samplingFrequency2SensorConfig = new Button();
		samplingFrequency2SensorConfig.setTooltip(new Tooltip("Sampling Frequency Config"));
		samplingFrequency2SensorConfig.setId("samplingFrequencySensorConfig");
		ImageView sensorConfigImage2 = new ImageView("/resources/sensorConfig.png");
		sensorConfigImage2.setFitHeight(15);
		sensorConfigImage2.setFitWidth(15);
		samplingFrequency2SensorConfig.setGraphic(sensorConfigImage2);
		gridPane1.add(samplingFrequency2SensorConfig, 5, 1);
		samplingFrequency2SensorConfig
				.setOnAction(new SensorConfigUI(samplingFrequency2SensorConfig, uacSettings.getUAC_SETTING(), "FQ2"));
		if (uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_2_SENSOR_CONFIG() != null
				&& !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_2_SENSOR_CONFIG().isEmpty()
				&& !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_2_SENSOR_CONFIG().get(0).getREGISTER_ADDRESS()
						.equals("")) {
			samplingFrequency2SensorConfig.setStyle("-fx-background-color:green");
		}

		Label samplingFrequency3Label = new Label("Sampling Frequency 3 (Hz) : ");
		gridPane1.add(samplingFrequency3Label, 0, 2);
		ComboBox<String> samplingFrequency3Value = new ComboBox<>();
		samplingFrequency3Value.setId("samplingFrequency3Value");
		samplingFrequency3Value.setPrefWidth(100.0);
		samplingFrequency3Value.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_SAMPLING_FREQUENCY")));
		samplingFrequency3Value.getTooltip().setOnShowing(s -> {
			Bounds bounds = samplingFrequency3Value.localToScreen(samplingFrequency3Value.getBoundsInLocal());
			samplingFrequency3Value.getTooltip().setX(bounds.getMaxX());
		});
		if (uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_3() == 0) {
			samplingFrequency3Value.setValue("Not Used");
		} else {
			samplingFrequency3Value.setValue(String.valueOf(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_3()));
		}
		samplingFrequency3Value.getItems().addAll(UACSettingFieldConstants.SAMPLING_FREQUENCIES);
		gridPane1.add(samplingFrequency3Value, 1, 2);
		Button samplingFrequency3SensorConfig = new Button();
		samplingFrequency3SensorConfig.setTooltip(new Tooltip("Sampling Frequency Config"));
		samplingFrequency3SensorConfig.setId("samplingFrequencySensorConfig");
		ImageView sensorConfigImage3 = new ImageView("/resources/sensorConfig.png");
		sensorConfigImage3.setFitHeight(15);
		sensorConfigImage3.setFitWidth(15);
		samplingFrequency3SensorConfig.setGraphic(sensorConfigImage3);
		gridPane1.add(samplingFrequency3SensorConfig, 2, 2);
		samplingFrequency3SensorConfig
				.setOnAction(new SensorConfigUI(samplingFrequency3SensorConfig, uacSettings.getUAC_SETTING(), "FQ3"));
		if (uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_3_SENSOR_CONFIG() != null
				&& !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_3_SENSOR_CONFIG().isEmpty()
				&& !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_3_SENSOR_CONFIG().get(0).getREGISTER_ADDRESS()
						.equals("")) {
			samplingFrequency3SensorConfig.setStyle("-fx-background-color:green");
		}

		Label samplingFrequency4Label = new Label("Sampling Frequency 4 (Hz) : ");
		samplingFrequency4Label.setPadding(new Insets(0, 0, 0, 100));
		gridPane1.add(samplingFrequency4Label, 3, 2);
		ComboBox<String> samplingFrequency4Value = new ComboBox<>();
		samplingFrequency4Value.setId("samplingFrequency4Value");
		samplingFrequency4Value.setPrefWidth(100.0);
		samplingFrequency4Value.setTooltip(
				new Tooltip(tooltipAndErrorProperties.getProperty("UAC_SETTINGS.TOOLTIP_SAMPLING_FREQUENCY")));
		samplingFrequency4Value.getTooltip().setOnShowing(s -> {
			Bounds bounds = samplingFrequency4Value.localToScreen(samplingFrequency4Value.getBoundsInLocal());
			samplingFrequency4Value.getTooltip().setX(bounds.getMaxX());
		});
		if (uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_4() == 0) {
			samplingFrequency4Value.setValue("Not Used");
		} else {
			samplingFrequency4Value.setValue(String.valueOf(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_4()));
		}
		samplingFrequency4Value.getItems().addAll(UACSettingFieldConstants.SAMPLING_FREQUENCIES);
		gridPane1.add(samplingFrequency4Value, 4, 2);
		Button samplingFrequency4SensorConfig = new Button();
		samplingFrequency4SensorConfig.setTooltip(new Tooltip("Sampling Frequency Config"));
		samplingFrequency4SensorConfig.setId("samplingFrequencySensorConfig");
		ImageView sensorConfigImage4 = new ImageView("/resources/sensorConfig.png");
		sensorConfigImage4.setFitHeight(15);
		sensorConfigImage4.setFitWidth(15);
		samplingFrequency4SensorConfig.setGraphic(sensorConfigImage4);
		gridPane1.add(samplingFrequency4SensorConfig, 5, 2);
		samplingFrequency4SensorConfig
				.setOnAction(new SensorConfigUI(samplingFrequency4SensorConfig, uacSettings.getUAC_SETTING(), "FQ4"));
		if (uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_4_SENSOR_CONFIG() != null
				&& !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_4_SENSOR_CONFIG().isEmpty()
				&& !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_4_SENSOR_CONFIG().get(0).getREGISTER_ADDRESS()
						.equals("")) {
			samplingFrequency4SensorConfig.setStyle("-fx-background-color:green");
		}

		samplingFrequency1Value.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				SX3ConfiguartionHelp sx3ConfigurationHelp = SX3Manager.getInstance().getSx3ConfigurationHelp();
				if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
					SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getSAMPLING_FREQUENCY_1());
				}
				ObservableList<String> items = samplingFrequency1Value.getItems();
				if (!samplingFrequency2Value.getValue().equals("Not Used")) {
					items.remove(samplingFrequency2Value.getValue());
				}
				if (!samplingFrequency3Value.getValue().equals("Not Used")) {
					items.remove(samplingFrequency3Value.getValue());
				}
				if (!samplingFrequency4Value.getValue().equals("Not Used")) {
					items.remove(samplingFrequency4Value.getValue());
				}
				for (int j = 0; j < UACSettingFieldConstants.SAMPLING_FREQUENCIES.length; j++) {
					if (!UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals("Not Used")
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]
									.equals(samplingFrequency2Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]
									.equals(samplingFrequency3Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]
									.equals(samplingFrequency4Value.getValue())
							&& !items.contains(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j])) {
						samplingFrequency1Value.getItems().add(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]);
					}
				}
			}
		});
		samplingFrequency2Value.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				SX3ConfiguartionHelp sx3ConfiguartionHelp = SX3Manager.getInstance().getSx3ConfigurationHelp();
				if (sx3ConfiguartionHelp.getENDPOINT_SETTINGS() != null) {
					SX3Manager.getInstance().showHelpContent(sx3ConfiguartionHelp.getUAC_SETTINGS().getSAMPLING_FREQUENCY_2());
				}
				ObservableList<String> items = samplingFrequency2Value.getItems();
				if (!samplingFrequency1Value.getValue().equals("Not Used")) {
					items.remove(samplingFrequency1Value.getValue());
				}
				if (!samplingFrequency3Value.getValue().equals("Not Used")) {
					items.remove(samplingFrequency3Value.getValue());
				}
				if (!samplingFrequency4Value.getValue().equals("Not Used")) {
					items.remove(samplingFrequency4Value.getValue());
				}
				for (int j = 0; j < UACSettingFieldConstants.SAMPLING_FREQUENCIES.length; j++) {
					if (!UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals("Not Used")
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]
									.equals(samplingFrequency1Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]
									.equals(samplingFrequency3Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]
									.equals(samplingFrequency4Value.getValue())
							&& !items.contains(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j])) {
						samplingFrequency2Value.getItems().add(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]);
					}
				}
			}
		});

		samplingFrequency3Value.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				SX3ConfiguartionHelp sx3ConfigurationHelp = SX3Manager.getInstance().getSx3ConfigurationHelp();
				if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
					SX3Manager.getInstance().getHelpView().getEngine().setUserStyleSheetLocation("data:,body{font: 12px Arial;}");
					SX3Manager.getInstance().getHelpView().getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getSAMPLING_FREQUENCY_3());
				}
				ObservableList<String> items = samplingFrequency3Value.getItems();
				if (!samplingFrequency1Value.getValue().equals("Not Used")) {
					items.remove(samplingFrequency1Value.getValue());
				}
				if (!samplingFrequency2Value.getValue().equals("Not Used")) {
					items.remove(samplingFrequency2Value.getValue());
				}
				if (!samplingFrequency4Value.getValue().equals("Not Used")) {
					items.remove(samplingFrequency4Value.getValue());
				}
				for (int j = 0; j < UACSettingFieldConstants.SAMPLING_FREQUENCIES.length; j++) {
					if (!UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals("Not Used")
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]
									.equals(samplingFrequency1Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]
									.equals(samplingFrequency2Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]
									.equals(samplingFrequency4Value.getValue())
							&& !items.contains(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j])) {
						samplingFrequency3Value.getItems().add(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]);
					}
				}
			}
		});
		samplingFrequency4Value.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				SX3ConfiguartionHelp sx3ConfigurationHelp = SX3Manager.getInstance().getSx3ConfigurationHelp();
				if (sx3ConfigurationHelp.getENDPOINT_SETTINGS() != null) {
					SX3Manager.getInstance().showHelpContent(sx3ConfigurationHelp.getUAC_SETTINGS().getSAMPLING_FREQUENCY_4());
				}
				ObservableList<String> items = samplingFrequency4Value.getItems();
				if (!samplingFrequency1Value.getValue().equals("Not Used")) {
					items.remove(samplingFrequency1Value.getValue());
				}
				if (!samplingFrequency2Value.getValue().equals("Not Used")) {
					items.remove(samplingFrequency2Value.getValue());
				}
				if (!samplingFrequency3Value.getValue().equals("Not Used")) {
					items.remove(samplingFrequency3Value.getValue());
				}
				for (int j = 0; j < UACSettingFieldConstants.SAMPLING_FREQUENCIES.length; j++) {
					if (!UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals("Not Used")
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]
									.equals(samplingFrequency1Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]
									.equals(samplingFrequency2Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]
									.equals(samplingFrequency3Value.getValue())
							&& !items.contains(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j])) {
						samplingFrequency4Value.getItems().add(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]);
					}
				}
			}
		});

		samplingFrequency1Value.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int count = 0;
				uacSettings.getUAC_SETTING()
						.setSAMPLING_FREQUENCY_1(Integer.parseInt(samplingFrequency1Value.getValue()));
				SX3Manager.getInstance().addLog("Sampling Frequency 1 (Hz) : " + samplingFrequency1Value.getValue() + ".<br>");
				
				
				if (!samplingFrequency2Value.getValue().equals("Not Used")) {
					count = count + 1;
				}
				if (!samplingFrequency3Value.getValue().equals("Not Used")) {
					count = count + 1;
				}
				if (!samplingFrequency4Value.getValue().equals("Not Used")) {
					count = count + 1;
				}
				if (!samplingFrequency1Value.getValue().equals("")) {
					count = count + 1;
				}
				uacSettings.getUAC_SETTING().setNUMBER_OF_SAMPLING_FREQUENCIES(count);
				numberOfSamplingFrequenciesValue
						.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()));
			}
		});

		samplingFrequency2Value.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int count = 0;
				if (samplingFrequency2Value.getValue().equals("Not Used")) {
					uacSettings.getUAC_SETTING().setSAMPLING_FREQUENCY_2(0);
				} else {
					uacSettings.getUAC_SETTING()
							.setSAMPLING_FREQUENCY_2(Integer.parseInt(samplingFrequency2Value.getValue()));
				}
				SX3Manager.getInstance().addLog("Sampling Frequency 2 (Hz) : " + samplingFrequency2Value.getValue() + ".<br>");
				
				
				if (!samplingFrequency1Value.getValue().equals("")) {
					count = count + 1;
				}
				if (!samplingFrequency3Value.getValue().equals("Not Used")) {
					count = count + 1;
				}
				if (!samplingFrequency4Value.getValue().equals("Not Used")) {
					count = count + 1;
				}
				if (!samplingFrequency2Value.getValue().equals("Not Used")) {
					count = count + 1;
				}
				uacSettings.getUAC_SETTING().setNUMBER_OF_SAMPLING_FREQUENCIES(count);
				numberOfSamplingFrequenciesValue
						.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()));
			}
		});

		samplingFrequency3Value.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int count = 0;
				if (samplingFrequency3Value.getValue().equals("Not Used")) {
					uacSettings.getUAC_SETTING().setSAMPLING_FREQUENCY_3(0);
				} else {
					uacSettings.getUAC_SETTING()
							.setSAMPLING_FREQUENCY_3(Integer.parseInt(samplingFrequency3Value.getValue()));
				}
				if (!samplingFrequency1Value.getValue().equals("")) {
					count = count + 1;
				}
				if (!samplingFrequency2Value.getValue().equals("Not Used")) {
					count = count + 1;
				}
				if (!samplingFrequency4Value.getValue().equals("Not Used")) {
					count = count + 1;
				}
				if (!samplingFrequency3Value.getValue().equals("Not Used")) {
					count = count + 1;
				}
				uacSettings.getUAC_SETTING().setNUMBER_OF_SAMPLING_FREQUENCIES(count);
				numberOfSamplingFrequenciesValue
						.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()));
				SX3Manager.getInstance().addLog("Sampling Frequency 3 (Hz) : " + samplingFrequency3Value.getValue() + ".<br>");
				
				
			}
		});

		samplingFrequency4Value.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int count = 0;
				if (samplingFrequency4Value.getValue().equals("Not Used")) {
					uacSettings.getUAC_SETTING().setSAMPLING_FREQUENCY_4(0);
				} else {
					uacSettings.getUAC_SETTING()
							.setSAMPLING_FREQUENCY_4(Integer.parseInt(samplingFrequency4Value.getValue()));
				}
				if (!samplingFrequency1Value.getValue().equals("")) {
					count = count + 1;
				}
				if (!samplingFrequency2Value.getValue().equals("Not Used")) {
					count = count + 1;
				}
				if (!samplingFrequency3Value.getValue().equals("Not Used")) {
					count = count + 1;
				}
				if (!samplingFrequency4Value.getValue().equals("Not Used")) {
					count = count + 1;
				}
				uacSettings.getUAC_SETTING().setNUMBER_OF_SAMPLING_FREQUENCIES(count);
				numberOfSamplingFrequenciesValue
						.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()));
				SX3Manager.getInstance().addLog("Sampling Frequency 4 (Hz) : " + samplingFrequency4Value.getValue() + ".<br>");
				
				
			}
		});

	}

	@SuppressWarnings("static-access")
	public static Map<String, UVCSettings> createEndpointSettings(String keyName,
			Map<String, UVCSettings> uvcSettingsList, UVCSettings uvcSetting, Tab endpointTab,
			EndpointSettings endpointSettingsJson, AnchorPane anchorPane2, String tabName) {
		AnchorPane anchorPane = new AnchorPane();
		AnchorPane anchorPane1 = new AnchorPane();
		anchorPane1.setLayoutX(50);
		anchorPane1.setLayoutY(20);
		anchorPane1.setStyle("-fx-background-color: #D0D3D4; -fx-border-insets: 4 1 1 1; -fx-border-color: black;");
		Label endpointLabel = new Label("Endpoint Settings");
		endpointLabel.setLayoutX(6);
		endpointLabel.setLayoutY(-5);
		endpointLabel.setStyle("-fx-background-color: inherit;");
		GridPane leftAnchorGridPane = new GridPane();
		leftAnchorGridPane.setLayoutX(3);
		leftAnchorGridPane.setLayoutY(20);
		leftAnchorGridPane.setVgap(5.0);

		/** Brust Length **/
		Label end1BurstLengthLabel = new Label(tabName + " Burst Length : ");
		leftAnchorGridPane.setMargin(end1BurstLengthLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(end1BurstLengthLabel, 0, 1);
		
		TextField end1BurstLengthValue = new TextField();
		end1BurstLengthValue.setId("end1BurstLengthValue");
		end1BurstLengthValue.setText(String.valueOf(endpointSettingsJson.getBURST_LENGTH()));
		end1BurstLengthValue.setMaxWidth(40);
		end1BurstLengthValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {

				endpointSettingsJson.setBURST_LENGTH(Integer.parseInt(end1BurstLengthValue.getText()));
			}
		});
		leftAnchorGridPane.add(end1BurstLengthValue, 1, 1);

		/** Buffer Count **/
		Label end1BufferCountLabel = new Label(tabName + "  Buffer Count : ");
		leftAnchorGridPane.setMargin(end1BufferCountLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(end1BufferCountLabel, 0, 2);
		TextField end1BufferCountValue = new TextField();
		end1BufferCountValue.setMaxWidth(40);
		end1BufferCountValue.setText(String.valueOf(endpointSettingsJson.getBUFFER_COUNT()));
		end1BufferCountValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				endpointSettingsJson.setBUFFER_COUNT(Integer.parseInt(end1BufferCountValue.getText()));
			}
		});
		leftAnchorGridPane.add(end1BufferCountValue, 1, 2);

		/** Buffer Count **/
		Label end1BufferSizeLabel = new Label(tabName + " Buffer Size (Bytes) : ");
		leftAnchorGridPane.setMargin(end1BufferSizeLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(end1BufferSizeLabel, 0, 3);
		TextField end1BufferSizeValue = new TextField();
		end1BufferSizeValue.setMaxWidth(80);
		end1BufferSizeValue.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				endpointSettingsJson.setBUFFER_SIZE(Integer.parseInt(end1BufferSizeValue.getText()));
				uvcSetting.setENDPOINT_SETTINGS(endpointSettingsJson);
				uvcSettingsList.put(keyName, uvcSetting);
			}
		});
		leftAnchorGridPane.add(end1BufferSizeValue, 1, 3);
		end1BufferSizeValue.setText(String.valueOf(endpointSettingsJson.getBUFFER_SIZE()));

		/** Total Used Buffer Space **/
		Label totalUseBufferSpaceLabel = new Label("Total used Buffer Space (KB) : ");
		leftAnchorGridPane.setMargin(totalUseBufferSpaceLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(totalUseBufferSpaceLabel, 0, 4);
		TextField totalUseBufferSpaceValue = new TextField();
		totalUseBufferSpaceValue.setMaxWidth(40);
		totalUseBufferSpaceValue.setText(String.valueOf(endpointSettingsJson.getUSED_BUFFER_SPACE()));
		endpointSettingsJson.setUSED_BUFFER_SPACE(Integer.parseInt(totalUseBufferSpaceValue.getText()));
		totalUseBufferSpaceValue.setDisable(true);

		leftAnchorGridPane.add(totalUseBufferSpaceValue, 1, 4);
		anchorPane1.getChildren().add(leftAnchorGridPane);
		anchorPane1.getChildren().add(endpointLabel);
		anchorPane.getChildren().add(anchorPane1);
		endpointTab.setContent(anchorPane);
		return uvcSettingsList;
	}

	public static void createAndAddRowInTable(Button addBtn, Button editBtn,
			FormatAndResolutions formatAndResolutionJson,
			ObservableList<FormatAndResolutionTableModel> formateAndResolutionData,
			TableView<FormatAndResolutionTableModel> formatResolutionTable, int index,
			Map<String, Boolean> uvcuacTabErrorList, Tab subTab, Tab subSubTab, Tab uvcuacSettingsTab,
			Properties configProperties, boolean performLoadAction, int busWidth, Map<String, Boolean> errorListOnSave) {
		FormatAndResolutionTableModel formatAndResolution = new FormatAndResolutionTableModel();
		Label sno = new Label(String.valueOf(index));
		sno.setStyle("-fx-font-size: 12");
		formatAndResolution.setSno(sno);
		formatAndResolutionJson.setS_NO(index);

		/** Image Format **/
		ComboBox<String> imageFormat = new ComboBox<>();
		imageFormat.setId("imageFormat"+formateAndResolutionData.size());
		imageFormat.setValue(formatAndResolutionJson.getIMAGE_FORMAT());
		formatAndResolution.setImageFormat(imageFormat);
		imageFormat.setStyle("-fx-font-size: 12");
		imageFormat.getItems().addAll(UVCSettingsConstants.IMAGE_FORMAT);
		imageFormat
				.setTooltip(new Tooltip(configProperties.getProperty("FORMAT_AND_RESOLUTION.TOOLTIP_IMAGE_FORMATS")));
		imageFormat.getTooltip().setOnShowing(s -> {
			Bounds bounds = imageFormat.localToScreen(imageFormat.getBoundsInLocal());
			imageFormat.getTooltip().setX(bounds.getMaxX());
		});
		imageFormat.setOnMouseClicked(new OnMouseClickHandler());

		/** Bit Per Pixel **/
		TextField bitPerPixel = new TextField();
		bitPerPixel.setDisable(true);
		bitPerPixel.setStyle("-fx-font-size: 12");
		bitPerPixel.setText(String.valueOf(formatAndResolutionJson.getBITS_PER_PIXEL()));
		formatAndResolution.setBitPerPixcel(bitPerPixel);

		/** H Resolution **/
		TextField hResolution = new TextField();
		hResolution.setId("hResolution"+formateAndResolutionData.size());
		hResolution.setDisable(true);
		hResolution.setAlignment(Pos.CENTER_RIGHT);
		hResolution.setStyle("-fx-font-size: 12");
		if (formatAndResolutionJson.getH_RESOLUTION() == 0) {
			hResolution.setText("");
		} else {
			hResolution.setText(String.valueOf(formatAndResolutionJson.getH_RESOLUTION()));
		}
		hResolution.setMaxWidth(100);
		formatAndResolution.setResolutionH(hResolution);
		hResolution.setPromptText("H Resolution");
		hResolution.setTooltip(new Tooltip(configProperties.getProperty("FORMAT_AND_RESOLUTION.TOOLTIP_H_RESOLUTION")));
		hResolution.getTooltip().setOnShowing(s -> {
			Bounds bounds = hResolution.localToScreen(hResolution.getBoundsInLocal());
			hResolution.getTooltip().setX(bounds.getMaxX());
		});
		contextMenu = UVCSettingsValidation.setupFormatAndResolutionValidationForNumeric(uvcuacTabErrorList,
				uvcuacSettingsTab, subTab, subSubTab, hResolution,
				configProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), formatAndResolutionJson, "HResolution",
				performLoadAction,busWidth,bitPerPixel);
				hResolution.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (!hResolution.getText().equals("")) {
					int resolution = Integer.parseInt(hResolution.getText());
					int busWidth1 = busWidth/8;
					int bitPerPixel1 = Integer.parseInt(bitPerPixel.getText())/8;
					if((resolution*bitPerPixel1)%busWidth1 == 0) {
						errorListOnSave.put("HResolution", false);
						SX3Manager.getInstance().addLog("H Resolution : " + hResolution.getText() + ".<br>");
					}else {
						errorListOnSave.put("HResolution", true);
					}
				}
			}
		});
		hResolution.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		hResolution.setOnMouseClicked(new OnMouseClickHandler());
		
		imageFormat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				formatAndResolutionJson.setIMAGE_FORMAT(imageFormat.getValue());
				hResolution.setDisable(false);
				if (imageFormat.getValue().equals("YUY2")) {
					bitPerPixel.setText("16");
					SX3Manager.getInstance().addLog("Image format : " + imageFormat.getValue() + ".<br>");
					SX3Manager.getInstance().addLog("Bit Per Pixel : " + bitPerPixel.getText() + ".<br>");
					
					
				} else if (imageFormat.getValue().equals("YUYV")) {
					bitPerPixel.setText("16");
					SX3Manager.getInstance().addLog("Image format : " + imageFormat.getValue() + ".<br>");
					SX3Manager.getInstance().addLog("Bit Per Pixel : " + bitPerPixel.getText() + ".<br>");
					
					
				} else if (imageFormat.getValue().equals("Y41P")) {
					bitPerPixel.setText("12");
					SX3Manager.getInstance().addLog("Image format : " + imageFormat.getValue() + ".<br>");
					SX3Manager.getInstance().addLog("Bit Per Pixel : " + bitPerPixel.getText() + ".<br>");
					
					
				} else if (imageFormat.getValue().equals("YUVP")) {
					bitPerPixel.setText("24");
					SX3Manager.getInstance().addLog("Image format : " + imageFormat.getValue() + ".<br>");
					SX3Manager.getInstance().addLog("Bit Per Pixel : " + bitPerPixel.getText() + ".<br>");
					
					
				} else if (imageFormat.getValue().equals("YUV4")) {
					bitPerPixel.setText("32");
					SX3Manager.getInstance().addLog("Image format : " + imageFormat.getValue() + ".<br>");
					SX3Manager.getInstance().addLog("Bit Per Pixel : " + bitPerPixel.getText() + ".<br>");
					
					
				} else if (imageFormat.getValue().equals("IYU2")) {
					bitPerPixel.setText("24");
					SX3Manager.getInstance().addLog("Image format : " + imageFormat.getValue() + ".<br>");
					SX3Manager.getInstance().addLog("Bit Per Pixel : " + bitPerPixel.getText() + ".<br>");
					
					
				} else if (imageFormat.getValue().equals("AYUV")) {
					bitPerPixel.setText("32");
					SX3Manager.getInstance().addLog("Image format : " + imageFormat.getValue() + ".<br>");
					SX3Manager.getInstance().addLog("Bit Per Pixel : " + bitPerPixel.getText() + ".<br>");
					
					
				} else if (imageFormat.getValue().equals("NV12")) {
					bitPerPixel.setText("12");
					SX3Manager.getInstance().addLog("Image format : " + imageFormat.getValue() + ".<br>");
					SX3Manager.getInstance().addLog("Bit Per Pixel : " + bitPerPixel.getText() + ".<br>");
					
					
				} else if (imageFormat.getValue().equals("NV16")) {
					bitPerPixel.setText("16");
					SX3Manager.getInstance().addLog("Image format : " + imageFormat.getValue() + ".<br>");
					SX3Manager.getInstance().addLog("Bit Per Pixel : " + bitPerPixel.getText() + ".<br>");
					
					
				} else if (imageFormat.getValue().equals("YV12") || imageFormat.getValue().equals("I420")) {
					bitPerPixel.setText("12");
					SX3Manager.getInstance().addLog("Image format : " + imageFormat.getValue() + ".<br>");
					SX3Manager.getInstance().addLog("Bit Per Pixel : " + bitPerPixel.getText() + ".<br>");
					
					
				} else if (imageFormat.getValue().equals("GREY")) {
					bitPerPixel.setText("8");
					SX3Manager.getInstance().addLog("Image format : " + imageFormat.getValue() + ".<br>");
					SX3Manager.getInstance().addLog("Bit Per Pixel : " + bitPerPixel.getText() + ".<br>");
					
					
				} else if (imageFormat.getValue().equals("Y16") || imageFormat.getValue().equals("MJPG")
						|| imageFormat.getValue().equals("H264")) {
					bitPerPixel.setText("16");
					SX3Manager.getInstance().addLog("Image format : " + imageFormat.getValue() + ".<br>");
					SX3Manager.getInstance().addLog("Bit Per Pixel : " + bitPerPixel.getText() + ".<br>");
					
					
				}
				formatAndResolutionJson.setBITS_PER_PIXEL(Integer.parseInt(bitPerPixel.getText()));
			}
		});

		/** V Resolution **/
		TextField vResolution = new TextField();
		vResolution.setId("vResolution"+formateAndResolutionData.size());
		vResolution.setAlignment(Pos.CENTER_RIGHT);
		vResolution.setStyle("-fx-font-size: 12");
		if (formatAndResolutionJson.getV_RESOLUTION() == 0) {
			vResolution.setText("");
		} else {
			vResolution.setText(String.valueOf(formatAndResolutionJson.getV_RESOLUTION()));
		}
		vResolution.setMaxWidth(100);
		formatAndResolution.setResolutionV(vResolution);
		vResolution.setPromptText("V Resolution");
		vResolution.setTooltip(new Tooltip(configProperties.getProperty("FORMAT_AND_RESOLUTION.TOOLTIP_V_RESOLUTION")));
		vResolution.getTooltip().setOnShowing(s -> {
			Bounds bounds = vResolution.localToScreen(vResolution.getBoundsInLocal());
			vResolution.getTooltip().setX(bounds.getMaxX());
		});
		contextMenu = UVCSettingsValidation.setupFormatAndResolutionValidationForNumeric(uvcuacTabErrorList,
				uvcuacSettingsTab, subTab, subSubTab, vResolution,
				configProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), formatAndResolutionJson, "VResolution",
				performLoadAction,busWidth,bitPerPixel);
		vResolution.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if (!vResolution.getText().equals("")) {
					SX3Manager.getInstance().addLog("V Resolution : " + vResolution.getText() + ".<br>");
					
					
				}
			}
		});
		vResolution.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		vResolution.setOnMouseClicked(new OnMouseClickHandler());

		/** Still Captured **/
		CheckBox stillCaptured = new CheckBox();
		stillCaptured.setId("stillCaptured"+formateAndResolutionData.size());
		stillCaptured.setStyle("-fx-font-size: 12");
		stillCaptured
				.setTooltip(new Tooltip(configProperties.getProperty("FORMAT_AND_RESOLUTION.TOOLTIP_STILL_CAPTURE")));
		stillCaptured.getTooltip().setOnShowing(s -> {
			Bounds bounds = stillCaptured.localToScreen(stillCaptured.getBoundsInLocal());
			stillCaptured.getTooltip().setX(bounds.getMaxX());
		});
		stillCaptured.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (stillCaptured.isSelected()) {
					formatAndResolutionJson.setSTILL_CAPTURE("Enable");
				} else {
					formatAndResolutionJson.setSTILL_CAPTURE("Disable");
				}
				SX3Manager.getInstance().addLog("Still Captured : " + stillCaptured.isSelected() + ".<br>");
				
				
			}
		});
		stillCaptured.setOnMouseClicked(new OnMouseClickHandler());
		if (formatAndResolutionJson.getSTILL_CAPTURE().equals("Enable")) {
			stillCaptured.setSelected(true);
		} else {
			stillCaptured.setSelected(false);
		}
		formatAndResolution.setStillCapture(stillCaptured);

		/** Supported In FS **/
		CheckBox supportedInLS = new CheckBox();
		supportedInLS.setId("supportedInFS"+formateAndResolutionData.size());
			supportedInLS.setDisable(true);
			formatAndResolutionJson.setSUPPORTED_IN_FS("Disable");
		supportedInLS.setStyle("-fx-font-size: 12");
		supportedInLS
				.setTooltip(new Tooltip(configProperties.getProperty("FORMAT_AND_RESOLUTION.TOOLTIP_SUPPORTED_IN_FS")));
		supportedInLS.getTooltip().setOnShowing(s -> {
			Bounds bounds = supportedInLS.localToScreen(supportedInLS.getBoundsInLocal());
			supportedInLS.getTooltip().setX(bounds.getMaxX());
		});
		if (formatAndResolutionJson.getSUPPORTED_IN_FS().equals("Enable")) {
			supportedInLS.setSelected(true);
		} else {
			supportedInLS.setSelected(false);
		}
		formatAndResolution.setSupportedInFS(supportedInLS);
		supportedInLS.setOnMouseClicked(new OnMouseClickHandler());

		/** Supported In HS **/
		CheckBox supportedInHS = new CheckBox();
		supportedInHS.setId("supportedInHS"+formateAndResolutionData.size());
		if(SX3Manager.getInstance().getSx3Configuration().getUVC_SETTINGS().get(0).getENDPOINT_SETTINGS().getENDPOINT_TRANSFER_TYPE().equals("Isochronous")) {
			supportedInHS.setDisable(true);
			formatAndResolutionJson.setSUPPORTED_IN_HS("Disable");
		}else {
			supportedInHS.setDisable(false);
		}
		supportedInHS.setStyle("-fx-font-size: 12");
		supportedInHS
				.setTooltip(new Tooltip(configProperties.getProperty("FORMAT_AND_RESOLUTION.TOOLTIP_SUPPORTED_IN_HS")));
		supportedInHS.getTooltip().setOnShowing(s -> {
			Bounds bounds = supportedInHS.localToScreen(supportedInHS.getBoundsInLocal());
			supportedInHS.getTooltip().setX(bounds.getMaxX());
		});
		if (formatAndResolutionJson.getSUPPORTED_IN_HS().equals("Enable")) {
			supportedInHS.setSelected(true);
		} else {
			supportedInHS.setSelected(false);
		}
		formatAndResolution.setSupportedInHS(supportedInHS);
		supportedInHS.setOnMouseClicked(new OnMouseClickHandler());

		/** Supported In SS **/
		CheckBox supportedInSS = new CheckBox();
		supportedInSS.setId("supportedInSS"+formateAndResolutionData.size());
		supportedInSS.setStyle("-fx-font-size: 12");
		supportedInSS
				.setTooltip(new Tooltip(configProperties.getProperty("FORMAT_AND_RESOLUTION.TOOLTIP_SUPPORTED_IN_SS")));
		supportedInSS.getTooltip().setOnShowing(s -> {
			Bounds bounds = supportedInSS.localToScreen(supportedInSS.getBoundsInLocal());
			supportedInSS.getTooltip().setX(bounds.getMaxX());
		});
		if (formatAndResolutionJson.getSUPPORTED_IN_SS().equals("Enable")) {
			supportedInSS.setSelected(true);
		} else {
			supportedInSS.setSelected(false);
		}
		formatAndResolution.setSupportedInSS(supportedInSS);
		supportedInSS.setOnMouseClicked(new OnMouseClickHandler());

		/** Frame Rate In LS **/
		TextField frameRateInLS = new TextField();
		frameRateInLS.setId("frameRateInFS"+formateAndResolutionData.size());
		frameRateInLS.setAlignment(Pos.CENTER_RIGHT);
		frameRateInLS.setStyle("-fx-font-size: 12");
		frameRateInLS
				.setTooltip(new Tooltip(configProperties.getProperty("FORMAT_AND_RESOLUTION.TOOLTIP_FRAMERATE_IN_FS")));
		frameRateInLS.getTooltip().setOnShowing(s -> {
			Bounds bounds = frameRateInLS.localToScreen(frameRateInLS.getBoundsInLocal());
			frameRateInLS.getTooltip().setX(bounds.getMaxX());
		});
//		if (formatAndResolutionJson.getFRAME_RATE_IN_FS() == 0) {
//			frameRateInLS.setText("");
//		} else {
//		}
		frameRateInLS.setText(String.valueOf(formatAndResolutionJson.getFRAME_RATE_IN_FS()));
		formatAndResolution.setFrameRateInFS(frameRateInLS);
		frameRateInLS.setPromptText("FS Frame Rate");
		frameRateInLS.setDisable(true);
		frameRateInLS.setMaxWidth(100);
		contextMenu = UVCSettingsValidation.setupFormatAndResolutionValidationForNumeric(uvcuacTabErrorList,
				uvcuacSettingsTab, subTab, subSubTab, frameRateInLS,
				configProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), formatAndResolutionJson, "FSFrameRate",
				performLoadAction,busWidth,bitPerPixel);
		frameRateInLS.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(frameRateInLS.getText().equals("")) {
					frameRateInLS.setText("1");
				}
//				if (!frameRateInLS.getText().equals("") && Integer.parseInt(frameRateInLS.getText()) != 0) {
//					framRateEmptyCheck.put("framRateFS", false);
//				} else if (frameRateInLS.getText().equals("")) {
//					framRateEmptyCheck.put("framRateFS", true);
//				}
				if (!frameRateInLS.getText().equals("")) {
					SX3Manager.getInstance().addLog("FS Frame Rate : " + frameRateInLS.getText() + ".<br>");
					
					
				}
			}
		});
		frameRateInLS.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		frameRateInLS.setOnMouseClicked(new OnMouseClickHandler());

		/** Frame Rate In HS **/
		TextField frameRateInHS = new TextField();
		frameRateInHS.setId("frameRateInHS"+formateAndResolutionData.size());
		frameRateInHS.setAlignment(Pos.CENTER_RIGHT);
		frameRateInHS.setStyle("-fx-font-size: 12");
		frameRateInHS
				.setTooltip(new Tooltip(configProperties.getProperty("FORMAT_AND_RESOLUTION.TOOLTIP_FRAMERATE_IN_HS")));
		frameRateInHS.getTooltip().setOnShowing(s -> {
			Bounds bounds = frameRateInHS.localToScreen(frameRateInHS.getBoundsInLocal());
			frameRateInHS.getTooltip().setX(bounds.getMaxX());
		});
		frameRateInHS.setText(String.valueOf(formatAndResolutionJson.getFRAME_RATE_IN_HS()));
		formatAndResolution.setFrameRateInHS(frameRateInHS);
		frameRateInHS.setPromptText("HS Frame Rate"+formateAndResolutionData.size());
		frameRateInHS.setDisable(true);
		frameRateInHS.setMaxWidth(100);
		contextMenu = UVCSettingsValidation.setupFormatAndResolutionValidationForNumeric(uvcuacTabErrorList,
				uvcuacSettingsTab, subTab, subSubTab, frameRateInHS,
				configProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), formatAndResolutionJson, "HSFrameRate",
				performLoadAction,busWidth,bitPerPixel);
		frameRateInHS.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(frameRateInHS.getText().equals("")) {
					frameRateInHS.setText("1");
				}
//				if (!frameRateInHS.getText().equals("") && Integer.parseInt(frameRateInHS.getText()) != 0) {
//					framRateEmptyCheck.put("framRateHS", false);
//				} else if (frameRateInHS.getText().equals("")) {
//					framRateEmptyCheck.put("framRateHS", true);
//				}
				if (!frameRateInHS.getText().equals("")) {
					SX3Manager.getInstance().addLog("HS Frame Rate : " + frameRateInHS.getText() + ".<br>");
					
					
				}
			}
		});
		frameRateInHS.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		frameRateInHS.setOnMouseClicked(new OnMouseClickHandler());

		/** Frame Rate In SS **/
		TextField frameRateInSS = new TextField();
		frameRateInSS.setId("frameRateInSS"+formateAndResolutionData.size());
		frameRateInSS.setAlignment(Pos.CENTER_RIGHT);
		frameRateInSS.setStyle("-fx-font-size: 12");
		frameRateInSS
				.setTooltip(new Tooltip(configProperties.getProperty("FORMAT_AND_RESOLUTION.TOOLTIP_FRAMERATE_IN_SS")));
		frameRateInSS.getTooltip().setOnShowing(s -> {
			Bounds bounds = frameRateInSS.localToScreen(frameRateInSS.getBoundsInLocal());
			frameRateInSS.getTooltip().setX(bounds.getMaxX());
		});
		frameRateInSS.setText(String.valueOf(formatAndResolutionJson.getFRAME_RATE_IN_SS()));
		formatAndResolution.setFrameRateInSS(frameRateInSS);
		frameRateInSS.setPromptText("SS Frame Rate");
		frameRateInSS.setDisable(true);
		frameRateInSS.setMaxWidth(100);
		contextMenu = UVCSettingsValidation.setupFormatAndResolutionValidationForNumeric(uvcuacTabErrorList,
				uvcuacSettingsTab, subTab, subSubTab, frameRateInSS,
				configProperties.getProperty("INVALID_NUMERIC_ERROR_MESSAGE"), formatAndResolutionJson, "SSFrameRate",
				performLoadAction,busWidth,bitPerPixel);
		frameRateInSS.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(frameRateInSS.getText().equals("")) {
					frameRateInSS.setText("1");
				}
//				if (!frameRateInSS.getText().equals("") && Integer.parseInt(frameRateInSS.getText()) != 0) {
//					framRateEmptyCheck.put("framRateSS", false);
//				} else if (frameRateInSS.getText().equals("")) {
//					framRateEmptyCheck.put("framRateSS", true);
//					
//				}
				if (!frameRateInSS.getText().equals("")) {
					SX3Manager.getInstance().addLog("SS Frame Rate : " + frameRateInSS.getText() + ".<br>");
				}
			}
		});
		frameRateInSS.setOnMouseExited(new OnMouseExitedHandler(contextMenu));
		frameRateInSS.setOnMouseClicked(new OnMouseClickHandler());

		/** Enable/Disable FS Frame Rate **/
		supportedInLS.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (supportedInLS.isSelected()) {
					frameRateInLS.setDisable(false);
					frameRateInLS.setText("1");
					SX3Manager.getInstance().addLog("Supported In FS : " + true + ".<br>");
					formatAndResolutionJson.setSUPPORTED_IN_FS("Enable");
//					framRateEmptyCheck.put("framRateFS", true);
				} else {
					frameRateInLS.setDisable(true);
					SX3Manager.getInstance().addLog("Supported In FS : " + false + ".<br>");
					formatAndResolutionJson.setSUPPORTED_IN_FS("Disable");
//					framRateEmptyCheck.put("framRateFS", false);
					frameRateInLS.setText("0");
				}

			}
		});

		/** Enable/Disable HS Frame Rate **/
		supportedInHS.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (supportedInHS.isSelected()) {
					frameRateInHS.setDisable(false);
					frameRateInHS.setText("1");
					SX3Manager.getInstance().addLog("Supported In HS : " + true + ".<br>");
					formatAndResolutionJson.setSUPPORTED_IN_HS("Enable");
//					framRateEmptyCheck.put("framRateHS", true);
				} else {
					frameRateInHS.setDisable(true);
					SX3Manager.getInstance().addLog("Supported In HS : " + false + ".<br>");
					formatAndResolutionJson.setSUPPORTED_IN_HS("Disable");
//					framRateEmptyCheck.put("framRateHS", false);
					frameRateInHS.setText("0");
				}

			}
		});

		/** Enable/Disable HS Frame Rate **/
		supportedInSS.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (supportedInSS.isSelected()) {
					frameRateInSS.setDisable(false);
					SX3Manager.getInstance().addLog("Supported In SS : " + true + ".<br>");
					frameRateInSS.setText("1");
					formatAndResolutionJson.setSUPPORTED_IN_SS("Enable");
//					framRateEmptyCheck.put("framRateSS", true);
				} else {
					frameRateInSS.setDisable(true);
					SX3Manager.getInstance().addLog("Supported In SS : " + false + ".<br>");
					formatAndResolutionJson.setSUPPORTED_IN_SS("Disable");
//					framRateEmptyCheck.put("framRateSS", false);
					frameRateInSS.setText("0");
				}

			}
		});

		Button sensorConfigEditButton = new Button();
		sensorConfigEditButton.setId("formatResolutionSensorConfig"+formateAndResolutionData.size());
		sensorConfigEditButton
				.setTooltip(new Tooltip(configProperties.getProperty("FORMAT_AND_RESOLUTION.TOOLTIP_SENSOR_CONFIG")));
		formatAndResolution.setButton(sensorConfigEditButton);
		sensorConfigEditButton.setOnAction(new SensorConfigUI(addBtn, editBtn, formatAndResolution,
				formatAndResolutionJson, sensorConfigEditButton, formatResolutionTable));
		if (formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING_LEN() != 0) {
			List<SensorConfig> resolution_REGISTER_SETTING = formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING();
			if (resolution_REGISTER_SETTING != null
					&& !resolution_REGISTER_SETTING.get(0).getREGISTER_ADDRESS().equals("")) {
				sensorConfigEditButton.setStyle("-fx-background-color:green");
			}
		}

		if (!formatAndResolutionJson.getIMAGE_FORMAT().equals("") && formatAndResolutionJson.getH_RESOLUTION() != 0
				&& formatAndResolutionJson.getV_RESOLUTION() != 0
				&& ((formatAndResolutionJson.getSUPPORTED_IN_FS().equals("Enable"))
						|| (formatAndResolutionJson.getSUPPORTED_IN_HS().equals("Enable"))
						|| (formatAndResolutionJson.getSUPPORTED_IN_SS().equals("Enable")))) {
			formatAndResolution.getImageFormat().setDisable(true);
			formatAndResolution.getResolutionH().setDisable(true);
			formatAndResolution.getResolutionV().setDisable(true);
			formatAndResolution.getStillCapture().setDisable(true);
			formatAndResolution.getSupportedInFS().setDisable(true);
			formatAndResolution.getSupportedInHS().setDisable(true);
			formatAndResolution.getSupportedInSS().setDisable(true);
			formatAndResolution.getFrameRateInFS().setDisable(true);
			formatAndResolution.getFrameRateInHS().setDisable(true);
			formatAndResolution.getFrameRateInSS().setDisable(true);
			formatAndResolution.getButton().setDisable(true);
			addBtn.setDisable(false);
		} else {
			addBtn.setDisable(true);
		}

		formateAndResolutionData.add(formatAndResolution);
		FilteredList<FormatAndResolutionTableModel> filteredData = new FilteredList<>(formateAndResolutionData,
				formatAndResol -> formateAndResolutionData.indexOf(formatAndResol) < 16);
		SortedList<FormatAndResolutionTableModel> sortedData = new SortedList<>(filteredData);
		formatResolutionTable.setItems(sortedData);

	}

}
