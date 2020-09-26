package sx3Configuration.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import sx3Configuration.TableModel.CameraAndProcessingUnitControlsTableModel;
import sx3Configuration.TableModel.FormatAndResolutionTableModel;
import sx3Configuration.TableModel.SensorConfigurationTable;
import sx3Configuration.model.CameraControlAndProcessingUnitJson;
import sx3Configuration.model.ColorMatching;
import sx3Configuration.model.EndpointSettings;
import sx3Configuration.model.ExtensionUnitControl;
import sx3Configuration.model.FormatAndResolutions;
import sx3Configuration.model.SensorConfig;
import sx3Configuration.model.UACSettings;
import sx3Configuration.model.UVCSettings;
import sx3Configuration.util.SX3ConfiguartionHelp;
import sx3Configuration.util.UACSettingFieldConstants;
import sx3Configuration.util.UVCSettingsConstants;
import ui.validations.UVCSettingsValidation;

public class UVCUACSettingsController {

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

		tableView.getColumns().addAll(snoColumn, imageFormatColumn, bitesPerPixelColumn, 
				hResolutionColumn, vResolutionColumn, stillCaptureColumn, supportedInColumn, frameRateInColumn,
				sensorConfigColumn);
	}

	public static GridPane createColorMatchingDescriptor(GridPane gridPane1, ColorMatching colorMatching,
			WebView logDetails1, TextArea logDetails, WebView helpContent, SX3ConfiguartionHelp sx3ConfigurationHelp) {
		gridPane1.setLayoutX(100);
		gridPane1.setHgap(10);
		gridPane1.setVgap(10);
		gridPane1.setHgap(10);
		Label colorPrimariesLabel = new Label("Color Primaries : ");
		gridPane1.add(colorPrimariesLabel, 0, 0);
		ComboBox<String> colorPrimariesValue = new ComboBox<>();
		colorPrimariesValue.setTooltip(new Tooltip("bColorPrimaries for the color matching descriptors"));
		colorPrimariesValue.getTooltip().setOnShowing(s->{
		    Bounds bounds = colorPrimariesValue.localToScreen(colorPrimariesValue.getBoundsInLocal());
		    colorPrimariesValue.getTooltip().setX(bounds.getMaxX());
		});
		colorPrimariesValue.getItems().addAll("Unspecified (Image\n" + "characteristics unknown)",
				"BT.709, sRGB (default)", "BT.470-2 (M)", " BT.470-2 (B, G)", "SMPTE 170M", "SMPTE 240M");
		colorPrimariesValue.setValue(colorMatching.getCOLOR_PRIMARIES());
		gridPane1.add(colorPrimariesValue, 1, 0);
		colorPrimariesValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				colorMatching.setCOLOR_PRIMARIES(colorPrimariesValue.getValue());
				logDetails.appendText("Color Primaries : " + colorPrimariesValue.getValue() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		colorPrimariesValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCOLOR_MATCHING().getCOLOR_PRIMARIES());
				}
			}
		});
//		colorPrimariesValue.setOnMouseExited(new EventHandler<Event>() {
//			@Override
//			public void handle(Event event) {
//				helpContent.getEngine().loadContent("");
//			}
//		});

		Label transferCharacteristicsLabel = new Label("Transfer Characteristics : ");
		gridPane1.add(transferCharacteristicsLabel, 0, 1);
		ComboBox<String> transferCharacteristicsValue = new ComboBox<>();
		transferCharacteristicsValue.setTooltip(new Tooltip("bTransferCharacteristics for color matching descriptor"));
		transferCharacteristicsValue.getTooltip().setOnShowing(s->{
		    Bounds bounds = transferCharacteristicsValue.localToScreen(transferCharacteristicsValue.getBoundsInLocal());
		    transferCharacteristicsValue.getTooltip().setX(bounds.getMaxX());
		});
		transferCharacteristicsValue.getItems().addAll("Unspecified (Image\n" + "characteristics unknown)",
				"BT.709 (default)", "BT.470-2 (M)", " BT.470-2 (B, G)", "SMPTE 170M", "SMPTE 240M", "Linear (V = Lc)",
				"sRGB (very similar to BT.709)");
		gridPane1.add(transferCharacteristicsValue, 1, 1);
		transferCharacteristicsValue.setValue(colorMatching.getTRANSFER_CHARACTERISTICS());
		transferCharacteristicsValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				colorMatching.setTRANSFER_CHARACTERISTICS(transferCharacteristicsValue.getValue());
				logDetails.appendText("Transfer Characteristics : " + transferCharacteristicsValue.getValue() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		transferCharacteristicsValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getCOLOR_MATCHING().getTRANSFER_CHARACTERISTICS());
				}
			}
		});
//		transferCharacteristicsValue.setOnMouseExited(new EventHandler<Event>() {
//			@Override
//			public void handle(Event event) {
//				helpContent.getEngine().loadContent("");
//			}
//		});

		Label matrixCoefficientsLabel = new Label("Matrix Coefficients : ");
		gridPane1.add(matrixCoefficientsLabel, 0, 2);
		ComboBox<String> matrixCoefficientsValue = new ComboBox<>();
		matrixCoefficientsValue.setTooltip(new Tooltip("bMatrixCoefficients for the color matching descriptor"));
		matrixCoefficientsValue.getTooltip().setOnShowing(s->{
		    Bounds bounds = matrixCoefficientsValue.localToScreen(matrixCoefficientsValue.getBoundsInLocal());
		    matrixCoefficientsValue.getTooltip().setX(bounds.getMaxX());
		});
		matrixCoefficientsValue.getItems().addAll("Unspecified (Image\n" + "characteristics unknown)", "BT.709", "FCC",
				"BT.470-2 B, G", "SMPTE 170M (BT.601,default)", "SMPTE 240M");
		gridPane1.add(matrixCoefficientsValue, 1, 2);
		matrixCoefficientsValue.setValue(colorMatching.getMATRIX_COEFFICIENTS());
		matrixCoefficientsValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				colorMatching.setMATRIX_COEFFICIENTS(matrixCoefficientsValue.getValue());
				logDetails.appendText("Matrix Coefficients : " + matrixCoefficientsValue.getValue() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		matrixCoefficientsValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent
						.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCOLOR_MATCHING().getMATRIX_COEFFICIENTS());
				}
			}
		});
		matrixCoefficientsValue.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

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

		ControlTableView.getColumns().addAll(labelColumn, enableColumn, minColumn, maxColumn, defaultColumn, stepColumn,lenColumn,
				registerAddressColumn);
		return ControlTableView;
	}

	public static ObservableList<CameraAndProcessingUnitControlsTableModel> addRowInCameraContolTable(
			Map<String, Boolean> uvcuacTabErrorList, Tab rootTab, Tab subTab, Tab subSubTab, String cameraControlLabel,
			ObservableList<CameraAndProcessingUnitControlsTableModel> cameraControlsList,
			Map<String, Map<String, Object>> map1, WebView logDetails1, TextArea logDetails, TextArea logDetails2, WebView helpContent,
			SX3ConfiguartionHelp sx3ConfigurationHelp, boolean performLoadAction) {
		CameraAndProcessingUnitControlsTableModel cameraControlTable = new CameraAndProcessingUnitControlsTableModel();
		CameraControlAndProcessingUnitJson cameraControlJson = new CameraControlAndProcessingUnitJson();
		Map<String, Object> map = new HashMap<>();
		for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
			map = entry1.getValue();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				Long intVal = null ;
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

		/** Label Name **/
		Label labelName = new Label();
		labelName.setTooltip(new Tooltip(labelName.getText()));
		labelName.getTooltip().setOnShowing(s->{
		    Bounds bounds = labelName.localToScreen(labelName.getBoundsInLocal());
		    labelName.getTooltip().setX(bounds.getMaxX());
		});
		labelName.setText(cameraControlLabel);
		labelName.setStyle("-fx-font-weight: bold;-fx-font-size:12px;");
		cameraControlTable.setLabelName(labelName);
		labelName.setPadding(new Insets(0, 0, 0, 5));
		labelName.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showHelpContent(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		labelName.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Enable Checkbox **/
		CheckBox enableCheckBox = new CheckBox();
		enableCheckBox.setTooltip(new Tooltip(labelName.getText()+" Enable/Disable"));
		enableCheckBox.getTooltip().setOnShowing(s->{
		    Bounds bounds = enableCheckBox.localToScreen(enableCheckBox.getBoundsInLocal());
		    enableCheckBox.getTooltip().setX(bounds.getMaxX());
		});
		if (cameraControlJson.getENABLE().equals("Enable")) {
			enableCheckBox.setSelected(true);
		} else {
			enableCheckBox.setSelected(false);
		}
		cameraControlTable.setEnableLabel(enableCheckBox);
		enableCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showHelpContent(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		enableCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Min Text Field **/
		TextField minTextField = new TextField();
		minTextField.setTooltip(new Tooltip(labelName.getText()+" Min Value"));
		minTextField.getTooltip().setOnShowing(s->{
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
		UVCSettingsValidation.setupCameraAnProcessingControlValidationForNumeric(minTextField,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, map, "MIN");
		minTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText(labelName.getText()+" MIN : " + minTextField.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		minTextField.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showHelpContent(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		minTextField.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Max Text Field **/
		TextField maxTextField = new TextField();
		maxTextField.setTooltip(new Tooltip(labelName.getText()+" Max Value"));
		maxTextField.getTooltip().setOnShowing(s->{
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
		UVCSettingsValidation.setupCameraAnProcessingControlValidationForNumeric(maxTextField,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, map, "MAX");
		maxTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText(labelName.getText()+" MAX : " + maxTextField.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		maxTextField.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showHelpContent(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		maxTextField.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Default Text Field **/
		TextField defaultTextField = new TextField();
		defaultTextField.setTooltip(new Tooltip(labelName.getText()+" Default Value"));
		defaultTextField.getTooltip().setOnShowing(s->{
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
		UVCSettingsValidation.setupCameraAnProcessingControlValidationForNumeric(defaultTextField,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, map, "DEFAULT");
		defaultTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText(labelName.getText()+" DEFAULT : " + defaultTextField.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		defaultTextField.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showHelpContent(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		defaultTextField.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Step Text Field **/
		TextField StepTextField = new TextField();
		StepTextField.setTooltip(new Tooltip(labelName.getText()+" Step Value"));
		StepTextField.getTooltip().setOnShowing(s->{
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
		UVCSettingsValidation.setupCameraAnProcessingControlValidationForNumeric(StepTextField,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, map, "STEP");
		StepTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText(labelName.getText()+" STEP : " + StepTextField.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		StepTextField.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showHelpContent(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		StepTextField.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		
		/** Len Text Field **/
		TextField lenTextField = new TextField();
		lenTextField.setTooltip(new Tooltip(labelName.getText()+" Len"));
		lenTextField.getTooltip().setOnShowing(s->{
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
		registerAddressTextField.setTooltip(new Tooltip(labelName.getText()+" Register Address"));
		registerAddressTextField.getTooltip().setOnShowing(s->{
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
		cameraControlTable.setRegisterAddress(registerAddressTextField);
		UVCSettingsValidation.setupValidationForHexTextField(uvcuacTabErrorList, rootTab, subTab, subSubTab,
				registerAddressTextField, UVCSettingsValidation.INVALID_HEX_ERROR_MESSAGE, map, 4, "REGISTER_ADDRESS",
				labelName.getText(), performLoadAction);
		registerAddressTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(cameraControlJson.getENABLE().equals("Enable") && !registerAddressTextField.getText().equals("")) {
					logDetails.appendText(labelName.getText()+" REGISTER_ADDRESS : " + registerAddressTextField.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					uvcuacTabErrorList.put(labelName.getText()+"REGISTER_ADDRESS", false);
					if(uvcuacTabErrorList.containsValue(false)) {
						registerAddressTextField.setStyle("");
					}
				}else {
					uvcuacTabErrorList.put(labelName.getText()+"REGISTER_ADDRESS", true);
					if(cameraControlJson.getENABLE().equals("Enable") && uvcuacTabErrorList.containsValue(true)) {
						registerAddressTextField.setStyle("-fx-border-color:red;");
					}
					logDetails2.appendText("<span style='color:red;'>"+labelName.getText()+" REGISTER_ADDRESS should not be empty.\n<span<br>");
				}
			}
		});
		registerAddressTextField.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showHelpContent(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		registerAddressTextField.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		cameraControlsList.add(cameraControlTable);
		enableCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (enableCheckBox.isSelected()) {
					cameraControlJson.setENABLE("Enable");
					logDetails.appendText(labelName.getText() + " Enable : " + enableCheckBox.isSelected() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					minTextField.setDisable(false);
					maxTextField.setDisable(false);
					defaultTextField.setDisable(false);
					StepTextField.setDisable(false);
					registerAddressTextField.setDisable(false);
					registerAddressTextField.requestFocus();
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("ENABLE")) {
								entry1.getValue().put(entry.getKey(), "Enable");
							}
						}
					}
					if(labelName.getText().contains("Auto-Exposure Mode") || labelName.getText().contains("Auto-Exposure Priority")) {
						lenTextField.setText("1");
						for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
							Map<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if(entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 1);
								}
							}
						}
					}else if(labelName.getText().contains("Exposure Time")) {
						lenTextField.setText("4");
						for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
							Map<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if(entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 4);
								}
							}
						}
					}else if(labelName.getText().contains("Focus") || labelName.getText().contains("Iris") || labelName.getText().contains("Zoom")
							|| labelName.getText().contains("Roll")) {
						lenTextField.setText("2");
						for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
							Map<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if(entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 2);
								}
							}
						}
					}else if(labelName.getText().contains("Pan Tilt")) {
						lenTextField.setText("8");
						for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
							Map<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if(entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 8);
								}
							}
						}
					}else if(labelName.getText().contains("Window Control")) {
						lenTextField.setText("12");
						for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
							Map<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if(entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 12);
								}
							}
						}
					}else if(labelName.getText().contains("Region of Interest")) {
						lenTextField.setText("10");
						for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
							Map<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if(entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 10);
								}
							}
						}
					}
				} else {
					cameraControlJson.setENABLE("Disable");
					logDetails.appendText(labelName.getText() + " Enable : " + enableCheckBox.isSelected() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					minTextField.setDisable(true);
					maxTextField.setDisable(true);
					defaultTextField.setDisable(true);
					StepTextField.setDisable(true);
					registerAddressTextField.requestFocus();
					registerAddressTextField.setDisable(true);
					registerAddressTextField.setStyle("");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("ENABLE")) {
								entry1.getValue().put(entry.getKey(), "Disable");
							}
						}
					}
					lenTextField.setText("0");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if(entry.getKey().contains("LENGTH")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					minTextField.setText("0");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if(entry.getKey().contains("MIN")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					maxTextField.setText("0");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if(entry.getKey().contains("MAX")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					defaultTextField.setText("0");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if(entry.getKey().contains("DEFAULT")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					StepTextField.setText("0");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if(entry.getKey().contains("STEP")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					registerAddressTextField.setText("");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if(entry.getKey().contains("REGISTER_ADDRESS")) {
								entry1.getValue().put(entry.getKey(), "");
							}
						}
					}
				}

			}
		});
		return cameraControlsList;
	}

	private static void showHelpContent(Label labelName, WebView helpContent,
			SX3ConfiguartionHelp sx3ConfigurationHelp) {
		if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
			if (labelName.getText().equals("Auto-Exposure Mode")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getAUTO_EXPOSURE_MODE());
			} else if (labelName.getText().equals("Auto-Exposure Priority")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getAUTO_EXPOSURE_PRIORITY());
			} else if (labelName.getText().equals("Exposure Time")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getEXPOSURE_TIME());
			} else if (labelName.getText().equals("Focus")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getFOCUS());
			} else if (labelName.getText().equals("Iris")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getIRIS());
			} else if (labelName.getText().equals("Zoom")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getZOOM());
			} else if (labelName.getText().equals("Pan Tilt")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getPAN_TILT());
			} else if (labelName.getText().equals("Roll")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getROLL());
			} else if (labelName.getText().equals("Region of Interest- Top (in pixels)")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwROI_Top());
			} else if (labelName.getText().equals("Region of Interest  - Left (in pixels)")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwROI_Left());
			} else if (labelName.getText().equals("Region of Interest - Bottom (in pixels)")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwROI_Bottom());
			} else if (labelName.getText().equals("Region of Interest - Right (in pixels)")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwROI_Right());
			} else if (labelName.getText().equals("Region of Interest - Auto Exposure")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD0());
			} else if (labelName.getText().equals("Region of Interest - Auto Iris")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD1());
			} else if (labelName.getText().equals("Region of Interest - Auto White Balance")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD2());
			} else if (labelName.getText().equals("Region of Interest - Auto Focus")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD3());
			} else if (labelName.getText().equals("Region of Interest - Auto Face Detect")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD4());
			} else if (labelName.getText().equals("Region of Interest - Auto Detect and Track")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD5());
			} else if (labelName.getText().equals("Region of Interest - Image Stabilization")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD6());
			} else if (labelName.getText().equals("Region of Interest -Higher Quality")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmAutoControlsD7());
			} else if (labelName.getText().equals("Window Control - Top (in pixels)")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwWindow_Top());
			} else if (labelName.getText().equals("Window Control - Left (in pixels)")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwWindow_Left());
			} else if (labelName.getText().equals("Window Control - Bottom (in pixels)")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwWindow_Bottom());
			} else if (labelName.getText().equals("Window Control - Right (in pixels)")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwWindow_Right());
			} else if (labelName.getText().equals("Window Control - NumSteps (in pixels)")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getwWindow_NumSteps());
			} else if (labelName.getText().equals("Window Control - NumStepsUnits (in pixels)")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getCAMERA_CONTROL().getBmNumStepsUnits());
			}

		}
	}

	public static ObservableList<CameraAndProcessingUnitControlsTableModel> addRowsInProcessingUnitControlTable(
			Map<String, Boolean> uvcuacTabErrorList, Tab rootTab, Tab subTab, Tab subSubTab, String label_Name,
			ObservableList<CameraAndProcessingUnitControlsTableModel> processingUnitControlsList,
			Map<String, Map<String, Object>> map1, TextArea logDetails, WebView logDetails1, TextArea logDetails2, WebView helpContent,
			SX3ConfiguartionHelp sx3ConfigurationHelp, boolean performLoadAction) {
		CameraAndProcessingUnitControlsTableModel processingUnitControlTable = new CameraAndProcessingUnitControlsTableModel();

		CameraControlAndProcessingUnitJson processingUnitControlJson = new CameraControlAndProcessingUnitJson();
		Map<String, Object> map = new HashMap<>();
		for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
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

		/** Label Name **/
		Label labelName = new Label();
		labelName.setText(label_Name);
		labelName.setStyle("-fx-font-weight: bold;-fx-font-size:12px;");
		labelName.setPadding(new Insets(0, 0, 0, 5));
		processingUnitControlTable.setLabelName(labelName);
		labelName.setTooltip(new Tooltip(labelName.getText()));
		labelName.getTooltip().setOnShowing(s->{
		    Bounds bounds = labelName.localToScreen(labelName.getBoundsInLocal());
		    labelName.getTooltip().setX(bounds.getMaxX());
		});
		labelName.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showProcessingUnitControlHelp(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		labelName.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Enable Checkbox **/
		CheckBox enableCheckBox = new CheckBox();
		enableCheckBox.setTooltip(new Tooltip(labelName.getText()+" Enable/Disable"));
		enableCheckBox.getTooltip().setOnShowing(s->{
		    Bounds bounds = enableCheckBox.localToScreen(enableCheckBox.getBoundsInLocal());
		    enableCheckBox.getTooltip().setX(bounds.getMaxX());
		});
		if (processingUnitControlJson.getENABLE().equals("Enable")) {
			enableCheckBox.setSelected(true);
		} else {
			enableCheckBox.setSelected(false);
		}
		processingUnitControlTable.setEnableLabel(enableCheckBox);
		enableCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showProcessingUnitControlHelp(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		enableCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Min Text Field **/
		TextField minTextField = new TextField();
		minTextField.setAlignment(Pos.CENTER_RIGHT);
		minTextField.setTooltip(new Tooltip(labelName.getText() + " Min Value"));
		minTextField.getTooltip().setOnShowing(s->{
		    Bounds bounds = minTextField.localToScreen(minTextField.getBoundsInLocal());
		    minTextField.getTooltip().setX(bounds.getMaxX());
		});
		if (processingUnitControlJson.getENABLE().equals("Enable")) {
			minTextField.setDisable(false);
		} else {
			minTextField.setDisable(true);
		}
		minTextField.setText(String.valueOf(processingUnitControlJson.getMIN()));
		UVCSettingsValidation.setupCameraAnProcessingControlValidationForNumeric(minTextField,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, map, "MIN");
		processingUnitControlTable.setMin(minTextField);
		minTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText(labelName.getText()+" MIN : " + minTextField.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		minTextField.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showProcessingUnitControlHelp(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		minTextField.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Max Text Field **/
		TextField maxTextField = new TextField();
		maxTextField.setAlignment(Pos.CENTER_RIGHT);
		maxTextField.setTooltip(new Tooltip(labelName.getText() + " Max Value"));
		maxTextField.getTooltip().setOnShowing(s->{
		    Bounds bounds = maxTextField.localToScreen(maxTextField.getBoundsInLocal());
		    maxTextField.getTooltip().setX(bounds.getMaxX());
		});
		if (processingUnitControlJson.getENABLE().equals("Enable")) {
			maxTextField.setDisable(false);
		} else {
			maxTextField.setDisable(true);
		}
		maxTextField.setText(String.valueOf(processingUnitControlJson.getMAX()));
		UVCSettingsValidation.setupCameraAnProcessingControlValidationForNumeric(maxTextField,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, map, "MAX");
		processingUnitControlTable.setMax(maxTextField);
		maxTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText(labelName.getText()+" MAX : " + maxTextField.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		maxTextField.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showProcessingUnitControlHelp(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		maxTextField.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Default Text Field **/
		TextField defaultTextField = new TextField();
		defaultTextField.setAlignment(Pos.CENTER_RIGHT);
		defaultTextField.setTooltip(new Tooltip(labelName.getText() + " Default Value"));
		defaultTextField.getTooltip().setOnShowing(s->{
		    Bounds bounds = defaultTextField.localToScreen(defaultTextField.getBoundsInLocal());
		    defaultTextField.getTooltip().setX(bounds.getMaxX());
		});
		if (processingUnitControlJson.getENABLE().equals("Enable")) {
			defaultTextField.setDisable(false);
		} else {
			defaultTextField.setDisable(true);
		}
		defaultTextField.setText(String.valueOf(processingUnitControlJson.getDEFAULT()));
		UVCSettingsValidation.setupCameraAnProcessingControlValidationForNumeric(defaultTextField,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, map, "DEFAULT");
		processingUnitControlTable.setDefaultValue(defaultTextField);
		defaultTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText(labelName.getText()+" Default : " + defaultTextField.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		defaultTextField.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showProcessingUnitControlHelp(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		defaultTextField.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Step Text Field **/
		TextField StepTextField = new TextField();
		StepTextField.setAlignment(Pos.CENTER_RIGHT);
		StepTextField.setTooltip(new Tooltip(labelName.getText() + " Step Value"));
		StepTextField.getTooltip().setOnShowing(s->{
		    Bounds bounds = StepTextField.localToScreen(StepTextField.getBoundsInLocal());
		    StepTextField.getTooltip().setX(bounds.getMaxX());
		});
		if (processingUnitControlJson.getENABLE().equals("Enable")) {
			StepTextField.setDisable(false);
		} else {
			StepTextField.setDisable(true);
		}
		StepTextField.setText(String.valueOf(processingUnitControlJson.getSTEP()));
		UVCSettingsValidation.setupCameraAnProcessingControlValidationForNumeric(StepTextField,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, map, "STEP");
		processingUnitControlTable.setStep(StepTextField);
		StepTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				logDetails.appendText(labelName.getText()+" Step : " + StepTextField.getText() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		StepTextField.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showProcessingUnitControlHelp(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		StepTextField.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		
		/** Len Text Field **/
		TextField lenTextField = new TextField();
		lenTextField.setTooltip(new Tooltip(labelName.getText()+" Len"));
		lenTextField.getTooltip().setOnShowing(s->{
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
		registerAddressTextField.setAlignment(Pos.CENTER_RIGHT);
		registerAddressTextField.setTooltip(new Tooltip(labelName.getText() + " Register Address"));
		registerAddressTextField.getTooltip().setOnShowing(s->{
		    Bounds bounds = registerAddressTextField.localToScreen(registerAddressTextField.getBoundsInLocal());
		    registerAddressTextField.getTooltip().setX(bounds.getMaxX());
		});
		if (processingUnitControlJson.getENABLE().equals("Enable")) {
			registerAddressTextField.setDisable(false);
		} else {
			registerAddressTextField.setDisable(true);
		}
		registerAddressTextField.setText(processingUnitControlJson.getREGISTER_ADDRESS());
		UVCSettingsValidation.setupValidationForHexTextField(uvcuacTabErrorList, rootTab, subTab, subSubTab,
				registerAddressTextField, UVCSettingsValidation.INVALID_HEX_ERROR_MESSAGE, map, 4, "REGISTER_ADDRESS",
				labelName.getText(), performLoadAction);
		processingUnitControlTable.setRegisterAddress(registerAddressTextField);
		registerAddressTextField.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(processingUnitControlJson.getENABLE().equals("Enable") && !registerAddressTextField.getText().equals("")) {
					logDetails.appendText(labelName.getText()+" REGISTER_ADDRESS : " + registerAddressTextField.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					uvcuacTabErrorList.put(labelName.getText()+"REGISTER_ADDRESS", false);
					if(uvcuacTabErrorList.containsValue(false)) {
						registerAddressTextField.setStyle("");
					}
				}else {
					uvcuacTabErrorList.put(labelName.getText()+"REGISTER_ADDRESS", true);
					if(processingUnitControlJson.getENABLE().equals("Enable") && uvcuacTabErrorList.containsValue(true)) {
						registerAddressTextField.setStyle("-fx-border-color:red;");
					}
					logDetails2.appendText("<span style='color:red;'>"+labelName.getText()+" REGISTER_ADDRESS should not be empty.\n<span<br>");
				}
			}
		});
		registerAddressTextField.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showProcessingUnitControlHelp(labelName, helpContent, sx3ConfigurationHelp);
			}
		});
		registerAddressTextField.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

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
					registerAddressTextField.requestFocus();
					logDetails.appendText(labelName.getText() + " Enable : " + true + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("ENABLE")) {
								entry1.getValue().put(entry.getKey(), "Enable");
							}
						}
					}
					if(labelName.getText().equals("Brightness") || labelName.getText().equals("Contrast") || labelName.getText().equals("Saturation")
							 || labelName.getText().equals("Hue") || labelName.getText().equals("Sharpness")
							 || labelName.getText().equals("Gamma") || labelName.getText().equals("White Balance Temperature")
							 || labelName.getText().equals("Backlight Compensation") || labelName.getText().equals("Gain")) {
						lenTextField.setText("2");
						for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
							Map<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if(entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 2);
								}
							}
						}
					}else if(labelName.getText().contains("White Balance Component")) {
						lenTextField.setText("4");
						for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
							Map<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if(entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 4);
								}
							}
						}
					}else if(labelName.getText().contains("Power Line Frequency") || labelName.getText().contains("Hue Auto") 
							|| labelName.getText().contains("White Balance Temperature Auto")
							|| labelName.getText().contains("White Balance Component Auto")) {
						lenTextField.setText("1");
						for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
							Map<String, Object> value = entry1.getValue();
							for (Map.Entry<String, Object> entry : value.entrySet()) {
								if(entry.getKey().contains("LENGTH")) {
									entry1.getValue().put(entry.getKey(), 1);
								}
							}
						}
					}
				} else {
					processingUnitControlJson.setENABLE("Disable");
					minTextField.setDisable(true);
					maxTextField.setDisable(true);
					defaultTextField.setDisable(true);
					StepTextField.setDisable(true);
					registerAddressTextField.requestFocus();
					registerAddressTextField.setStyle("");
					registerAddressTextField.setDisable(true);
					logDetails.appendText(labelName.getText() + " Enable : " + false + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if (entry.getKey().contains("ENABLE")) {
								entry1.getValue().put(entry.getKey(), "Disable");
							}
						}
					}
					lenTextField.setText("0");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if(entry.getKey().contains("LENGTH")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					minTextField.setText("0");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if(entry.getKey().contains("MIN")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					maxTextField.setText("0");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if(entry.getKey().contains("MAX")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					defaultTextField.setText("0");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if(entry.getKey().contains("DEFAULT")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					StepTextField.setText("0");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if(entry.getKey().contains("STEP")) {
								entry1.getValue().put(entry.getKey(), 0);
							}
						}
					}
					registerAddressTextField.setText("");
					for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
						Map<String, Object> value = entry1.getValue();
						for (Map.Entry<String, Object> entry : value.entrySet()) {
							if(entry.getKey().contains("REGISTER_ADDRESS")) {
								entry1.getValue().put(entry.getKey(), "");
							}
						}
					}
				}

			}
		});
		return processingUnitControlsList;
	}

	protected static void showProcessingUnitControlHelp(Label labelName, WebView helpContent,
			SX3ConfiguartionHelp sx3ConfigurationHelp) {
		if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
			if (labelName.getText().equals("Brightness")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getBRIGHTNESS());
			} else if (labelName.getText().equals("Contrast")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getCONTRAST());
			} else if (labelName.getText().equals("Saturation")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getSATURATION());
			} else if (labelName.getText().equals("Hue")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getHUE());
			} else if (labelName.getText().equals("Sharpness")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getSHARPNESS());
			} else if (labelName.getText().equals("Gamma")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getGAMMA());
			} else if (labelName.getText().equals("White Balance Temperature")) {
				helpContent.getEngine().loadContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getWHITE_BALANCE_TEMPERATURE());
			} else if (labelName.getText().equals("White Balance Component")) {
				helpContent.getEngine().loadContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getWHITE_BALANCE_COMPONENT());
			} else if (labelName.getText().equals("Backlight Compensation")) {
				helpContent.getEngine().loadContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getBACKLIGHT_COMPENSATION());
			} else if (labelName.getText().equals("Gain")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getGAIN());
			} else if (labelName.getText().equals("Power Line Frequency")) {
				helpContent.getEngine().loadContent(
						sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getPOWER_LINE_FREQUENCY());
			} else if (labelName.getText().equals("Hue Auto")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL().getHUE_AUTO());
			} else if (labelName.getText().equals("White Balance Temperature Auto")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL()
						.getWHITE_BALANCE_TEMPERATURE_AUTO());
			} else if (labelName.getText().equals("White Balance Component Auto")) {
				helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getPROCESSING_UNIT_CONTROL()
						.getWHITE_BALANCE_COMPONENT_AUTO());
			}

		}
	}

	@SuppressWarnings("static-access")
	public static AnchorPane createExtensionUnitControlUI(AnchorPane anchorPane,
			ExtensionUnitControl extensionUnitControl, TextArea logDetails,WebView logDetails1, WebView helpContent,
			SX3ConfiguartionHelp sx3ConfigurationHelp) {
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
		// Label vendorCommandsLabel = new Label("Enable");
		// leftAnchorGridPane.setMargin(vendorCommandsLabel, new Insets(0, 5, 0,
		// 0));
		// leftAnchorGridPane.add(vendorCommandsLabel, 1, 0);

		/** Device Reset **/
		Label deviceResetLabel = new Label("Device Reset : ");
		leftAnchorGridPane.setMargin(deviceResetLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(deviceResetLabel, 0, 0);
		CheckBox deviceResetCheckBox = new CheckBox();
		deviceResetCheckBox.setTooltip(new Tooltip("Support vendor command for Device Reset"));
		deviceResetCheckBox.getTooltip().setOnShowing(s->{
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
					logDetails.appendText("Device Reset : " + true + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					extensionUnitControl.setDEVICE_RESET_VENDOR_COMMAND_ENABLED("Disable");
					logDetails.appendText("Device Reset : " + false + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
		deviceResetCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getEXTENSION_UNIT_CONTROL()
							.getDEVICE_RESET_VENDOR_COMMAND_ENABLED());
				}
			}
		});
		deviceResetCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** I2C Register Read **/
		Label i2cregisterLabel = new Label("I2C Register Read : ");
		leftAnchorGridPane.setMargin(i2cregisterLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(i2cregisterLabel, 0, 1);
		CheckBox i2cRegisterCheckBox = new CheckBox();
		i2cRegisterCheckBox.setTooltip(new Tooltip("Support vendor command for I2C Register Read"));
		i2cRegisterCheckBox.getTooltip().setOnShowing(s->{
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
					logDetails.appendText("I2C Register Read : " + true + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					extensionUnitControl.setI2C_READ_VENDOR_COMMAND_ENABLED("Disable");
					logDetails.appendText("I2C Register Read : " + false + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
		i2cRegisterCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getEXTENSION_UNIT_CONTROL()
						.getI2C_READ_VENDOR_COMMAND_ENABLED());
				}
			}
		});
		i2cRegisterCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** I2C Register Write **/
		Label i2cRegisterWhiteLabel = new Label("I2C Register Write : ");
		leftAnchorGridPane.setMargin(i2cRegisterWhiteLabel, new Insets(0, 0, 0, 5));
		leftAnchorGridPane.add(i2cRegisterWhiteLabel, 0, 2);
		CheckBox i2cRegisterWriteCheckBox = new CheckBox();
		i2cRegisterWriteCheckBox.setTooltip(new Tooltip("Support vendor command for I2C Register Write"));
		i2cRegisterWriteCheckBox.getTooltip().setOnShowing(s->{
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
					logDetails.appendText("I2C Register Write : " + true + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					extensionUnitControl.setI2C_WRITE_VENDOR_COMMAND_ENABLED("Disable");
					logDetails.appendText("I2C Register Write : " + false + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
		i2cRegisterWriteCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getEXTENSION_UNIT_CONTROL()
						.getI2C_WRITE_VENDOR_COMMAND_ENABLED());
				}
			}
		});
		i2cRegisterWriteCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		GridPane gridPane = new GridPane();
		gridPane.setLayoutX(50);
		gridPane.setLayoutY(150);
		gridPane.setVgap(5.0);

		/** Firmware Update **/
		Label firmwareUpdateLabel = new Label("Firmware Update : ");
		gridPane.setMargin(firmwareUpdateLabel, new Insets(0, 0, 0, 5));
		gridPane.add(firmwareUpdateLabel, 0, 0);
		CheckBox firmwareUpdateCheckBox = new CheckBox();
		firmwareUpdateCheckBox.setTooltip(new Tooltip("Support firmware update via extension unit"));
		firmwareUpdateCheckBox.getTooltip().setOnShowing(s->{
		    Bounds bounds = firmwareUpdateCheckBox.localToScreen(firmwareUpdateCheckBox.getBoundsInLocal());
		    firmwareUpdateCheckBox.getTooltip().setX(bounds.getMaxX());
		});
		if (extensionUnitControl.getFIRMWARE_UPDATE_ENABLED().equals("Enable")) {
			firmwareUpdateCheckBox.setSelected(true);
		} else {
			firmwareUpdateCheckBox.setSelected(false);
		}
		firmwareUpdateCheckBox.setText("Enable");
		gridPane.add(firmwareUpdateCheckBox, 1, 0);
		firmwareUpdateCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (firmwareUpdateCheckBox.isSelected()) {
					extensionUnitControl.setFIRMWARE_UPDATE_ENABLED("Enable");
					logDetails.appendText("Firmware Update : " + true + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					extensionUnitControl.setFIRMWARE_UPDATE_ENABLED("Disable");
					logDetails.appendText("Firmware Update : " + false + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
		firmwareUpdateCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getEXTENSION_UNIT_CONTROL()
						.getFIRMWARE_UPDATE_ENABLED());
				}
			}
		});
		firmwareUpdateCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		anchorPane1.getChildren().add(leftAnchorGridPane);
		anchorPane1.getChildren().add(endpointLabel);
		anchorPane.getChildren().addAll(anchorPane1, gridPane);
		return anchorPane;
	}

	@SuppressWarnings("static-access")
	public static void createUACSettingsTabUI(AnchorPane anchorPane, UACSettings uacSettings, TextArea logDetails, WebView logDetails1,
			WebView helpContent, SX3ConfiguartionHelp sx3ConfigurationHelp) {
		BorderPane borderPane = new BorderPane();
		anchorPane.getChildren().add(borderPane);
		Map<String, String> channelConfiguration = uacSettings.getUAC_SETTING().getCHANNEL_CONFIGURATION();
		Map<String, String> featureUnitControl = uacSettings.getUAC_SETTING().getFEATURE_UNIT_CONTROLS();
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(7);
		borderPane.setTop(gridPane);
		borderPane.setMargin(gridPane, new Insets(10,50,20,0));

		/** Terminal Type **/
		Label terminalTypeLabel = new Label("Terminal Type :");
		gridPane.add(terminalTypeLabel, 0, 0);
		ComboBox<String> terminalTypeValue = new ComboBox<>();
		terminalTypeValue.getItems().addAll(UACSettingFieldConstants.TERMINAL_TYPE);
		terminalTypeValue.setTooltip(new Tooltip("wTerminalType"));
		terminalTypeValue.getTooltip().setOnShowing(s->{
		    Bounds bounds = terminalTypeValue.localToScreen(terminalTypeValue.getBoundsInLocal());
		    terminalTypeValue.getTooltip().setX(bounds.getMaxX());
		});
		terminalTypeValue.setValue(uacSettings.getUAC_SETTING().getTERMINAL_TYPE());
		gridPane.add(terminalTypeValue, 1, 0);
		terminalTypeValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				uacSettings.getUAC_SETTING().setTERMINAL_TYPE(terminalTypeValue.getValue());
				logDetails.appendText("Terminal Type : " + terminalTypeValue.getValue() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		terminalTypeValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getTERMINAL_TYPE());
				}
			}
		});
		terminalTypeValue.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Number of Channels **/
		Label numberOfChannelsLabel = new Label("Number of Channels : ");
		gridPane.add(numberOfChannelsLabel, 0, 1);
		TextField numberOfChannelsValue = new TextField();
		numberOfChannelsValue.setPrefWidth(30.0);
		gridPane.add(numberOfChannelsValue, 1, 1);
		numberOfChannelsValue.setDisable(true);
		numberOfChannelsValue.setTooltip(new Tooltip("bNrChannels"));
		numberOfChannelsValue.getTooltip().setOnShowing(s->{
		    Bounds bounds = numberOfChannelsValue.localToScreen(numberOfChannelsValue.getBoundsInLocal());
		    numberOfChannelsValue.getTooltip().setX(bounds.getMaxX());
		});
		numberOfChannelsValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getNUMBER_OF_CHANNELS());
				}
			}
		});
		numberOfChannelsValue.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
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
		leftFrontCheckBox.setText("D0: Left Front (L)");
		leftFrontCheckBox.setTooltip(new Tooltip("wChannelConfig"));
		leftFrontCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (leftFrontCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D0: Left Front (L)", "Enable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D0: Left Front (L) : " + "Enable" + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D0: Left Front (L)", "Disable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D0: Left Front (L) : " + "Disable" + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}

			}
		});
		leftFrontCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
				}
			}
		});
		leftFrontCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(leftFrontCheckBox, 1, 2);

		/** D1 **/
		CheckBox rightFrontCheckBox = new CheckBox();
		rightFrontCheckBox.setText("D1: Right Front (R)");
		if (channelConfiguration.get("D1: Right Front (R)").equals("Enable")) {
			rightFrontCheckBox.setSelected(true);
		} else {
			rightFrontCheckBox.setSelected(false);
		}
		rightFrontCheckBox.setTooltip(new Tooltip("wChannelConfig"));
		rightFrontCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (rightFrontCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D1: Right Front (R)", "Enable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D1: Right Front (R) : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D1: Right Front (R)", "Disable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D1: Right Front (R) : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}

			}
		});
		rightFrontCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
				}
			}
		});
		rightFrontCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(rightFrontCheckBox, 2, 2);

		/** D2 **/
		CheckBox centerFrontCheckBox = new CheckBox();
		centerFrontCheckBox.setText("D2: Center Front (C)");
		centerFrontCheckBox.setTooltip(new Tooltip("wChannelConfig"));
		if (channelConfiguration.get("D2: Center Front (C)").equals("Enable")) {
			centerFrontCheckBox.setSelected(true);
		} else {
			centerFrontCheckBox.setSelected(false);
		}
		centerFrontCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (centerFrontCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D2: Center Front (C)", "Enable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D2: Center Front (C) : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D2: Center Front (C)", "Disable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D2: Center Front (C) : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}

			}
		});
		centerFrontCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
				}
			}
		});
		centerFrontCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(centerFrontCheckBox, 3, 2);

		/** D3 **/
		CheckBox lowFrequencyEnhancementCheckBox = new CheckBox();
		if (channelConfiguration.get("D3: Low Frequency Enhancement (LFE)").equals("Enable")) {
			lowFrequencyEnhancementCheckBox.setSelected(true);
		} else {
			lowFrequencyEnhancementCheckBox.setSelected(false);
		}
		lowFrequencyEnhancementCheckBox.setText("D3: Low Frequency Enhancement (LFE)");
		lowFrequencyEnhancementCheckBox.setTooltip(new Tooltip("wChannelConfig"));
		lowFrequencyEnhancementCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (lowFrequencyEnhancementCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D3: Low Frequency Enhancement (LFE)", "Enable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D3: Low Frequency Enhancement (LFE) : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D3: Low Frequency Enhancement (LFE)", "Disable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D3: Low Frequency Enhancement (LFE) : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}

			}
		});
		lowFrequencyEnhancementCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
				}
			}
		});
		lowFrequencyEnhancementCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(lowFrequencyEnhancementCheckBox, 4, 2);

		/** D4 **/
		CheckBox leftSurroundCheckBox = new CheckBox();
		leftSurroundCheckBox.setText("D4: Left Surround (LS)");
		if (channelConfiguration.get("D4: Left Surround (LS)").equals("Enable")) {
			leftSurroundCheckBox.setSelected(true);
		} else {
			leftSurroundCheckBox.setSelected(false);
		}
		leftSurroundCheckBox.setTooltip(new Tooltip("wChannelConfig"));
		leftSurroundCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (leftSurroundCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D4: Left Surround (LS)", "Enable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D4: Left Surround (LS) : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D4: Left Surround (LS)", "Disable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D4: Left Surround (LS) : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}

			}
		});
		leftSurroundCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
				}
			}
		});
		leftSurroundCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(leftSurroundCheckBox, 1, 3);

		/** D5 **/
		CheckBox rightSurroundCheckBox = new CheckBox();
		rightSurroundCheckBox.setText("D5: Right Surround (RS)");
		if (channelConfiguration.get("D5: Right Surround (RS)").equals("Enable")) {
			rightSurroundCheckBox.setSelected(true);
		} else {
			rightSurroundCheckBox.setSelected(false);
		}
		rightSurroundCheckBox.setTooltip(new Tooltip("wChannelConfig"));
		rightSurroundCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (rightSurroundCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D5: Right Surround (RS)", "Enable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D5: Right Surround (RS) : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D5: Right Surround (RS)", "Disable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D5: Right Surround (RS) : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}

			}
		});
		rightSurroundCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
				}
			}
		});
		rightSurroundCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(rightSurroundCheckBox, 2, 3);

		/** D6 **/
		CheckBox leftOfCenterCheckBox = new CheckBox();
		leftOfCenterCheckBox.setText("D6: Left of Center (LC)");
		if (channelConfiguration.get("D6: Left of Center (LC)").equals("Enable")) {
			leftOfCenterCheckBox.setSelected(true);
		} else {
			leftOfCenterCheckBox.setSelected(false);
		}
		leftOfCenterCheckBox.setTooltip(new Tooltip("wChannelConfig"));
		leftOfCenterCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (leftOfCenterCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D6: Left of Center (LC)", "Enable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D6: Left of Center (LC) : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D6: Left of Center (LC)", "Disable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D6: Left of Center (LC) : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}

			}
		});
		leftOfCenterCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
				}
			}
		});
		leftOfCenterCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(leftOfCenterCheckBox, 3, 3);

		/** D7 **/
		CheckBox rightOfCenterCheckBox = new CheckBox();
		rightOfCenterCheckBox.setText("D7: Right of Center (RC)");
		rightOfCenterCheckBox.setTooltip(new Tooltip("wChannelConfig"));
		if (channelConfiguration.get("D7: Right of Center (RC)").equals("Enable")) {
			rightOfCenterCheckBox.setSelected(true);
		} else {
			rightOfCenterCheckBox.setSelected(false);
		}
		rightOfCenterCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (rightOfCenterCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D7: Right of Center (RC)", "Enable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D7: Right of Center (RC) : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D7: Right of Center (RC)", "Disable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D7: Right of Center (RC) : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}

			}
		});
		rightOfCenterCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
				}
			}
		});
		rightOfCenterCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(rightOfCenterCheckBox, 4, 3);

		/** D8 **/
		CheckBox surroundCheckBox = new CheckBox();
		surroundCheckBox.setText("D8: Surround (S)");
		if (channelConfiguration.get("D8: Surround (S)").equals("Enable")) {
			surroundCheckBox.setSelected(true);
		} else {
			surroundCheckBox.setSelected(false);
		}
		surroundCheckBox.setTooltip(new Tooltip("wChannelConfig"));
		surroundCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (surroundCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D8: Surround (S)", "Enable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D8: Surround (S) : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D8: Surround (S)", "Disable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D8: Surround (S) : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}

			}
		});
		surroundCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
				}
			}
		});
		surroundCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(surroundCheckBox, 1, 4);

		/** D9 **/
		CheckBox sideLeftCheckBox = new CheckBox();
		sideLeftCheckBox.setText("D9: Side Left (SL)");
		if (channelConfiguration.get("D9: Side Left (SL)").equals("Enable")) {
			sideLeftCheckBox.setSelected(true);
		} else {
			sideLeftCheckBox.setSelected(false);
		}
		sideLeftCheckBox.setTooltip(new Tooltip("wChannelConfig"));
		sideLeftCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (sideLeftCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D9: Side Left (SL)", "Enable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D9: Side Left (SL) : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D9: Side Left (SL)", "Disable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D9: Side Left (SL) : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}

			}
		});
		sideLeftCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
				}
			}
		});
		sideLeftCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(sideLeftCheckBox, 2, 4);

		/** D10 **/
		CheckBox sideRightCheckBox = new CheckBox();
		sideRightCheckBox.setText("D10: Side Right (SR)");
		if (channelConfiguration.get("D10: Side Right (SR)").equals("Enable")) {
			sideRightCheckBox.setSelected(true);
		} else {
			sideRightCheckBox.setSelected(false);
		}
		sideRightCheckBox.setTooltip(new Tooltip("wChannelConfig"));
		sideRightCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (sideRightCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D10: Side Right (SR)", "Enable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D10: Side Right (SR) : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D10: Side Right (SR)", "Disable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D10: Side Right (SR) : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}

			}
		});
		sideRightCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
				}
			}
		});
		sideRightCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(sideRightCheckBox, 3, 4);

		/** D11 **/
		CheckBox topCheckBox = new CheckBox();
		topCheckBox.setText("D11: Top (T)");
		if (channelConfiguration.get("D11: Top (T)").equals("Enable")) {
			topCheckBox.setSelected(true);
		} else {
			topCheckBox.setSelected(false);
		}
		topCheckBox.setTooltip(new Tooltip("wChannelConfig"));
		topCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (topCheckBox.isSelected()) {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D11: Top (T)", "Enable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D11: Top (T) : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					uacSettings.getUAC_SETTING()
							.setNUMBER_OF_CHANNELS(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() - 1);
					numberOfChannelsValue.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS()));
					channelConfiguration.put("D11: Top (T)", "Disable");
					logDetails.appendText(
							"Number of Channels : " + uacSettings.getUAC_SETTING().getNUMBER_OF_CHANNELS() + ".\n<br>");
					logDetails.appendText("D11: Top (T) : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}

			}
		});
		topCheckBox.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getCHANNEL_CONFIGURATION());
				}
			}
		});
		topCheckBox.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(topCheckBox, 4, 4);

		/** Audio Format **/
		Label audioFormatLabel = new Label("Audio Format : ");
		gridPane.add(audioFormatLabel, 0, 5);
		ComboBox<String> audioFormatValue = new ComboBox<>();
		audioFormatValue.setTooltip(new Tooltip("wFormatTag"));
		audioFormatValue.getTooltip().setOnShowing(s->{
		    Bounds bounds = audioFormatValue.localToScreen(audioFormatValue.getBoundsInLocal());
		    audioFormatValue.getTooltip().setX(bounds.getMaxX());
		});
		audioFormatValue.getItems().addAll(UACSettingFieldConstants.AUDIO_FORMAT);
		audioFormatValue.setValue(uacSettings.getUAC_SETTING().getAUDIO_FORMAT());
		audioFormatValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				uacSettings.getUAC_SETTING().setAUDIO_FORMAT(audioFormatValue.getValue());
				logDetails.appendText("Audio Format : " + audioFormatValue.getValue() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		audioFormatValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getAUDIO_FORMAT());
				}
			}
		});
		audioFormatValue.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(audioFormatValue, 1, 5);

		/** Bit Resolution **/
		Label bitResolutionLabel = new Label("Bit resolution : ");
		gridPane.add(bitResolutionLabel, 0, 6);
		ComboBox<String> bitResolutionValue = new ComboBox<>();
		bitResolutionValue.getItems().addAll(UACSettingFieldConstants.BIT_RESOLUTION);
		bitResolutionValue.setValue(uacSettings.getUAC_SETTING().getBIT_RESOLUTION());
		bitResolutionValue.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				uacSettings.getUAC_SETTING().setBIT_RESOLUTION(bitResolutionValue.getValue());
				logDetails.appendText("Bit resolution : " + bitResolutionValue.getValue() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		bitResolutionValue.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getBIT_RESOLUTION());
				}
			}
		});
		bitResolutionValue.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane.add(bitResolutionValue, 1, 6);

		/** Feature Unit Controls **/
		Label featureUnitControlLabel = new Label("Feature unit Controls : ");
		gridPane.add(featureUnitControlLabel, 0, 7);
		featureUnitControlLabel.setPadding(new Insets(0,50,0,0));

		/** Mute Checkbox **/
		CheckBox muteCheckBox = new CheckBox();
		muteCheckBox.setText("D0: Mute");
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
					logDetails.appendText("D0: Mute : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					featureUnitControl.put("D0: Mute", "Disable");
					logDetails.appendText("D0: Mute : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});

		/** Volume Checkbox **/
		CheckBox volumnCheckBox = new CheckBox();
		volumnCheckBox.setText("D1: Volume");
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
					logDetails.appendText("D1: Volume : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					featureUnitControl.put("D1: Volume", "Disable");
					logDetails.appendText("D1: Volume : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});

		/** Bass Checkbox **/
		CheckBox bassCheckBox = new CheckBox();
		bassCheckBox.setText("D2: Bass");
		if (featureUnitControl.get("D2: Bass").equals("Enable")) {
			bassCheckBox.setSelected(true);
		} else {
			bassCheckBox.setSelected(false);
		}
		gridPane.add(bassCheckBox, 3, 7);
		bassCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (bassCheckBox.isSelected()) {
					featureUnitControl.put("D2: Bass", "Enable");
					logDetails.appendText("D2: Bass : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					featureUnitControl.put("D2: Bass", "Disable");
					logDetails.appendText("D2: Bass : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});

		/** Mid Checkbox **/
		CheckBox midCheckBox = new CheckBox();
		midCheckBox.setText("D3: Mid");
		if (featureUnitControl.get("D3: Mid").equals("Enable")) {
			midCheckBox.setSelected(true);
		} else {
			midCheckBox.setSelected(false);
		}
		gridPane.add(midCheckBox, 4, 7);
		midCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (midCheckBox.isSelected()) {
					featureUnitControl.put("D3: Mid", "Enable");
					logDetails.appendText("D3: Mid : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					featureUnitControl.put("D3: Mid", "Disable");
					logDetails.appendText("D3: Mid : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});

		/** Treble Checkbox **/
		CheckBox trebleCheckBox = new CheckBox();
		trebleCheckBox.setText("D4: Treble");
		if (featureUnitControl.get("D4: Treble").equals("Enable")) {
			trebleCheckBox.setSelected(true);
		} else {
			trebleCheckBox.setSelected(false);
		}
		gridPane.add(trebleCheckBox, 5, 7);
		trebleCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (trebleCheckBox.isSelected()) {
					featureUnitControl.put("D4: Treble", "Enable");
					logDetails.appendText("D4: Treble : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					featureUnitControl.put("D4: Treble", "Disable");
					logDetails.appendText("D4: Treble : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});

		/** Graphic Equalizer Checkbox **/
		CheckBox graphicEqualizerCheckBox = new CheckBox();
		graphicEqualizerCheckBox.setText("D5: Graphic Equalizer");
		if (featureUnitControl.get("D5: Graphic Equalizer").equals("Enable")) {
			graphicEqualizerCheckBox.setSelected(true);
		} else {
			graphicEqualizerCheckBox.setSelected(false);
		}
		gridPane.add(graphicEqualizerCheckBox, 1, 8);
		graphicEqualizerCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (graphicEqualizerCheckBox.isSelected()) {
					featureUnitControl.put("D5: Graphic Equalizer", "Enable");
					logDetails.appendText("D5: Graphic Equalizer : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					featureUnitControl.put("D5: Graphic Equalizer", "Disable");
					logDetails.appendText("D5: Graphic Equalizer : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});

		/** Automatic Gain Checkbox **/
		CheckBox automaticGainCheckBox = new CheckBox();
		automaticGainCheckBox.setText("D6: Automatic Gain");
		if (featureUnitControl.get("D6: Automatic Gain").equals("Enable")) {
			automaticGainCheckBox.setSelected(true);
		} else {
			automaticGainCheckBox.setSelected(false);
		}
		gridPane.add(automaticGainCheckBox, 2, 8);
		automaticGainCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (automaticGainCheckBox.isSelected()) {
					featureUnitControl.put("D6: Automatic Gain", "Enable");
					logDetails.appendText("D6: Automatic Gain : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					featureUnitControl.put("D6: Automatic Gain", "Disable");
					logDetails.appendText("D6: Automatic Gain : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});

		/** Delay Checkbox **/
		CheckBox delayCheckBox = new CheckBox();
		delayCheckBox.setText("D7: Delay");
		if (featureUnitControl.get("D7: Delay").equals("Enable")) {
			delayCheckBox.setSelected(true);
		} else {
			delayCheckBox.setSelected(false);
		}
		gridPane.add(delayCheckBox, 3, 8);
		delayCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (delayCheckBox.isSelected()) {
					featureUnitControl.put("D7: Delay", "Enable");
					logDetails.appendText("D7: Delay : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					featureUnitControl.put("D7: Delay", "Disable");
					logDetails.appendText("D7: Delay : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});

		/** Bass Boost Checkbox **/
		CheckBox bassBoostCheckBox = new CheckBox();
		bassBoostCheckBox.setText("D8: Bass Boost");
		if (featureUnitControl.get("D8: Bass Boost").equals("Enable")) {
			bassBoostCheckBox.setSelected(true);
		} else {
			bassBoostCheckBox.setSelected(false);
		}
		gridPane.add(bassBoostCheckBox, 4, 8);
		bassBoostCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (bassBoostCheckBox.isSelected()) {
					featureUnitControl.put("D8: Bass Boost", "Enable");
					logDetails.appendText("D8: Bass Boost : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					featureUnitControl.put("D8: Bass Boost", "Disable");
					logDetails.appendText("D8: Bass Boost : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});

		/** Loudness Checkbox **/
		CheckBox loudnessCheckBox = new CheckBox();
		loudnessCheckBox.setText("D9: Loudness");
		if (featureUnitControl.get("D9: Loudness").equals("Enable")) {
			loudnessCheckBox.setSelected(true);
		} else {
			loudnessCheckBox.setSelected(false);
		}
		gridPane.add(loudnessCheckBox, 5, 8);
		loudnessCheckBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (loudnessCheckBox.isSelected()) {
					featureUnitControl.put("D9: Loudness", "Enable");
					logDetails.appendText("D9: Loudness : Enable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else {
					featureUnitControl.put("D9: Loudness", "Disable");
					logDetails.appendText("D9: Loudness : Disable.\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
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
		numberOfSamplingFrequenciesValue.setPrefWidth(30.0);
		numberOfSamplingFrequenciesValue
				.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()));
		numberOfSamplingFrequenciesValue.setDisable(true);
		gridPane1.add(numberOfSamplingFrequenciesValue, 1, 0);
		
		/** Sampling Frequency 1 **/
		Label samplingFrequency1Label = new Label("Sampling Frequency 1 (Hz) : ");
		gridPane1.add(samplingFrequency1Label, 0, 1);
		ComboBox<String> samplingFrequency1Value = new ComboBox<>();
		samplingFrequency1Value.setTooltip(new Tooltip("tSamFreq"));
		samplingFrequency1Value.getTooltip().setOnShowing(s->{
		    Bounds bounds = samplingFrequency1Value.localToScreen(samplingFrequency1Value.getBoundsInLocal());
		    samplingFrequency1Value.getTooltip().setX(bounds.getMaxX());
		});
		samplingFrequency1Value.getItems().addAll(UACSettingFieldConstants.SAMPLING_FREQUENCIES1_LIST);
		samplingFrequency1Value.getItems().remove("Not Used");
		if(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_1() == 0) {
			samplingFrequency1Value.setValue("");
		}else {
			samplingFrequency1Value.setValue(String.valueOf(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_1()));
		}
		
		samplingFrequency1Value.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getSAMPLING_FREQUENCY_1());
				}
			}
		});
		samplingFrequency1Value.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		gridPane1.add(samplingFrequency1Value, 1, 1);
		Button samplingFrequency1SensorConfig = new Button();
		ImageView sensorConfigImage1 = new ImageView("/resources/sensorConfig.png");
		sensorConfigImage1.setFitHeight(15);
		sensorConfigImage1.setFitWidth(15);
		samplingFrequency1SensorConfig.setGraphic(sensorConfigImage1);
		gridPane1.add(samplingFrequency1SensorConfig, 2, 1);
		samplingFrequency1SensorConfig.setTooltip(new Tooltip("Sensor Config"));
		samplingFrequency1SensorConfig.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				openSampleFreqSensorConfig(samplingFrequency1SensorConfig,uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_1_SENSOR_CONFIG());
			}
		});
		if(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_1_SENSOR_CONFIG() != null && !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_1_SENSOR_CONFIG().isEmpty() 
				&& !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_1_SENSOR_CONFIG().get(0).getREGISTER_ADDRESS().equals("")) {
			samplingFrequency1SensorConfig.setStyle("-fx-background-color:green");
		}
		/** Sampling Frequency **/
		Label samplingFrequency2Label = new Label("Sampling Frequency 2 (Hz) : ");
		samplingFrequency2Label.setPadding(new Insets(0,0,0,100));
		gridPane1.add(samplingFrequency2Label, 3, 1);
		ComboBox<String> samplingFrequency2Value = new ComboBox<>();
		samplingFrequency2Value.setTooltip(new Tooltip("tSamFreq"));
		samplingFrequency2Value.getTooltip().setOnShowing(s->{
		    Bounds bounds = samplingFrequency2Value.localToScreen(samplingFrequency2Value.getBoundsInLocal());
		    samplingFrequency2Value.getTooltip().setX(bounds.getMaxX());
		});
		if(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_2() == 0) {
			samplingFrequency2Value.setValue("Not Used");
		}else {
			samplingFrequency2Value.setValue(String.valueOf(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_2()));
		}
		samplingFrequency2Value.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getSAMPLING_FREQUENCY_2());
				}
			}
		});
		samplingFrequency2Value.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		samplingFrequency2Value.getItems().addAll(UACSettingFieldConstants.SAMPLING_FREQUENCIES2_LIST);
		gridPane1.add(samplingFrequency2Value, 4, 1);
		Button samplingFrequency2SensorConfig = new Button();
		ImageView sensorConfigImage2 = new ImageView("/resources/sensorConfig.png");
		sensorConfigImage2.setFitHeight(15);
		sensorConfigImage2.setFitWidth(15);
		samplingFrequency2SensorConfig.setGraphic(sensorConfigImage2);
		gridPane1.add(samplingFrequency2SensorConfig, 5, 1);
		samplingFrequency2SensorConfig.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				openSampleFreqSensorConfig(samplingFrequency2SensorConfig,uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_2_SENSOR_CONFIG());
			}
		});
		if(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_2_SENSOR_CONFIG() != null && !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_2_SENSOR_CONFIG().isEmpty() 
				&& !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_2_SENSOR_CONFIG().get(0).getREGISTER_ADDRESS().equals("")) {
			samplingFrequency2SensorConfig.setStyle("-fx-background-color:green");
		}
		
		Label samplingFrequency3Label = new Label("Sampling Frequency 3 (Hz) : ");
		gridPane1.add(samplingFrequency3Label, 0, 2);
		ComboBox<String> samplingFrequency3Value = new ComboBox<>();
		samplingFrequency3Value.setTooltip(new Tooltip("tSamFreq"));
		samplingFrequency3Value.getTooltip().setOnShowing(s->{
		    Bounds bounds = samplingFrequency3Value.localToScreen(samplingFrequency3Value.getBoundsInLocal());
		    samplingFrequency3Value.getTooltip().setX(bounds.getMaxX());
		});
		if(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_3() == 0) {
			samplingFrequency3Value.setValue("Not Used");
		}else {
			samplingFrequency3Value.setValue(String.valueOf(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_3()));
		}
		samplingFrequency3Value.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getSAMPLING_FREQUENCY_3());
				}
			}
		});
		samplingFrequency3Value.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		samplingFrequency3Value.getItems().addAll(UACSettingFieldConstants.SAMPLING_FREQUENCIES3_LIST);
		gridPane1.add(samplingFrequency3Value, 1, 2);
		Button samplingFrequency3SensorConfig = new Button();
		ImageView sensorConfigImage3 = new ImageView("/resources/sensorConfig.png");
		sensorConfigImage3.setFitHeight(15);
		sensorConfigImage3.setFitWidth(15);
		samplingFrequency3SensorConfig.setGraphic(sensorConfigImage3);
		gridPane1.add(samplingFrequency3SensorConfig, 2, 2);
		samplingFrequency3SensorConfig.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				openSampleFreqSensorConfig(samplingFrequency3SensorConfig,uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_3_SENSOR_CONFIG());
			}
		});
		if(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_3_SENSOR_CONFIG() != null && !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_3_SENSOR_CONFIG().isEmpty() 
				&& !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_3_SENSOR_CONFIG().get(0).getREGISTER_ADDRESS().equals("")) {
			samplingFrequency3SensorConfig.setStyle("-fx-background-color:green");
		}

		
		Label samplingFrequency4Label = new Label("Sampling Frequency 4 (Hz) : ");
		samplingFrequency4Label.setPadding(new Insets(0,0,0,100));
		gridPane1.add(samplingFrequency4Label, 3, 2);
		ComboBox<String> samplingFrequency4Value = new ComboBox<>();
		samplingFrequency4Value.setTooltip(new Tooltip("tSamFreq"));
		samplingFrequency4Value.getTooltip().setOnShowing(s->{
		    Bounds bounds = samplingFrequency4Value.localToScreen(samplingFrequency4Value.getBoundsInLocal());
		    samplingFrequency4Value.getTooltip().setX(bounds.getMaxX());
		});
		if(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_4() == 0) {
			samplingFrequency4Value.setValue("Not Used");
		}else {
			samplingFrequency4Value.setValue(String.valueOf(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_4()));
		}
		samplingFrequency4Value.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUAC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUAC_SETTINGS().getSAMPLING_FREQUENCY_4());
				}
			}
		});
		samplingFrequency4Value.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		samplingFrequency4Value.getItems().addAll(UACSettingFieldConstants.SAMPLING_FREQUENCIES4_LIST);
		gridPane1.add(samplingFrequency4Value, 4, 2);
		Button samplingFrequency4SensorConfig = new Button();
		ImageView sensorConfigImage4 = new ImageView("/resources/sensorConfig.png");
		sensorConfigImage4.setFitHeight(15);
		sensorConfigImage4.setFitWidth(15);
		samplingFrequency4SensorConfig.setGraphic(sensorConfigImage4);
		gridPane1.add(samplingFrequency4SensorConfig, 5, 2);
		samplingFrequency4SensorConfig.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				openSampleFreqSensorConfig(samplingFrequency4SensorConfig,uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_4_SENSOR_CONFIG());
			}
		});
		if(uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_4_SENSOR_CONFIG() != null && !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_4_SENSOR_CONFIG().isEmpty() 
				&& !uacSettings.getUAC_SETTING().getSAMPLING_FREQUENCY_4_SENSOR_CONFIG().get(0).getREGISTER_ADDRESS().equals("")) {
			samplingFrequency4SensorConfig.setStyle("-fx-background-color:green");
		}
		
		
		samplingFrequency1Value.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				ObservableList<String> items = samplingFrequency1Value.getItems();
				for(int i=0;i<items.size();i++) {
					if(samplingFrequency2Value.getValue().equals(items.get(i))
							|| samplingFrequency3Value.getValue().equals(items.get(i))
							|| samplingFrequency4Value.getValue().equals(items.get(i))) {
						samplingFrequency1Value.getItems().remove(items.get(i));
					}
				}
				for(int j=0;j<UACSettingFieldConstants.SAMPLING_FREQUENCIES.length;j++) {
					if(!UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals("Not Used") && !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals(samplingFrequency2Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals(samplingFrequency3Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals(samplingFrequency4Value.getValue())
							&& !items.contains(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j])) {
						samplingFrequency1Value.getItems().add(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]);
					}
				}
			}
		});
		samplingFrequency2Value.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				ObservableList<String> items = samplingFrequency2Value.getItems();
				for(int i=0;i<items.size();i++) {
					if(!items.get(i).equals("Not Used") 
							&& (samplingFrequency1Value.getValue().equals(items.get(i))
							|| samplingFrequency3Value.getValue().equals(items.get(i))
							|| samplingFrequency4Value.getValue().equals(items.get(i)))) {
						samplingFrequency2Value.getItems().remove(items.get(i));
					}
				}
				for(int j=0;j<UACSettingFieldConstants.SAMPLING_FREQUENCIES.length;j++) {
					if(!UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals("Not Used") && !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals(samplingFrequency1Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals(samplingFrequency3Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals(samplingFrequency4Value.getValue())
							&& !items.contains(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j])) {
						samplingFrequency2Value.getItems().add(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]);
					}
				}
			}
		});
		
		samplingFrequency3Value.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				ObservableList<String> items = samplingFrequency3Value.getItems();
				for(int i=0;i<items.size();i++) {
					if(!items.get(i).equals("Not Used") 
							&& (samplingFrequency1Value.getValue().equals(items.get(i))
							|| samplingFrequency2Value.getValue().equals(items.get(i))
							|| samplingFrequency4Value.getValue().equals(items.get(i)))) {
						samplingFrequency3Value.getItems().remove(items.get(i));
					}
				}
				for(int j=0;j<UACSettingFieldConstants.SAMPLING_FREQUENCIES.length;j++) {
					if(!UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals("Not Used") && !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals(samplingFrequency1Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals(samplingFrequency2Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals(samplingFrequency4Value.getValue())
							&& !items.contains(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j])) {
						samplingFrequency3Value.getItems().add(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]);
					}
				}
			}
		});
		samplingFrequency4Value.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				ObservableList<String> items = samplingFrequency4Value.getItems();
				for(int i=0;i<items.size();i++) {
					if(!items.get(i).equals("Not Used") 
							&& (samplingFrequency1Value.getValue().equals(items.get(i))
							|| samplingFrequency2Value.getValue().equals(items.get(i))
							|| samplingFrequency3Value.getValue().equals(items.get(i)))) {
						samplingFrequency4Value.getItems().remove(items.get(i));
					}
				}
				for(int j=0;j<UACSettingFieldConstants.SAMPLING_FREQUENCIES.length;j++) {
					if(!UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals("Not Used") && !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals(samplingFrequency1Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals(samplingFrequency2Value.getValue())
							&& !UACSettingFieldConstants.SAMPLING_FREQUENCIES[j].equals(samplingFrequency3Value.getValue())
							&& !items.contains(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j])) {
						samplingFrequency4Value.getItems().add(UACSettingFieldConstants.SAMPLING_FREQUENCIES[j]);
					}
				}
			}
		});		
		
		Map<String,Boolean> countSampleFrequency = new HashMap<>();
		
		samplingFrequency1Value.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int count = 0;
				if(samplingFrequency1Value.getValue().equals("Not Used")) {
					uacSettings.getUAC_SETTING().setSAMPLING_FREQUENCY_1(0);
					countSampleFrequency.put("sample1", false);
				}else {
					uacSettings.getUAC_SETTING().setSAMPLING_FREQUENCY_1(Integer.parseInt(samplingFrequency1Value.getValue()));
					countSampleFrequency.put("sample1", true);
				}
				logDetails.appendText("Sampling Frequency 1 (Hz) : " + samplingFrequency1Value.getValue() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				for (Map.Entry<String,Boolean> entry : countSampleFrequency.entrySet()) {
					if(entry.getValue().equals(true)) {
						count++;
					}
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
				if(samplingFrequency2Value.getValue().equals("Not Used")) {
					uacSettings.getUAC_SETTING().setSAMPLING_FREQUENCY_2(0);
					countSampleFrequency.put("sample2", false);
				}else {
					uacSettings.getUAC_SETTING().setSAMPLING_FREQUENCY_2(Integer.parseInt(samplingFrequency2Value.getValue()));
					countSampleFrequency.put("sample2", true);
				}
				logDetails.appendText("Sampling Frequency 2 (Hz) : " + samplingFrequency2Value.getValue() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				for (Map.Entry<String,Boolean> entry : countSampleFrequency.entrySet()) {
					if(entry.getValue().equals(true)) {
						count++;
					}
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
				if(samplingFrequency3Value.getValue().equals("Not Used")) {
					uacSettings.getUAC_SETTING().setSAMPLING_FREQUENCY_3(0);
					countSampleFrequency.put("sample3", false);
				}else {
					uacSettings.getUAC_SETTING().setSAMPLING_FREQUENCY_3(Integer.parseInt(samplingFrequency3Value.getValue()));
					countSampleFrequency.put("sample3", true);
				}
				for (Map.Entry<String,Boolean> entry : countSampleFrequency.entrySet()) {
					if(entry.getValue().equals(true)) {
						count++;
					}
				}
				uacSettings.getUAC_SETTING().setNUMBER_OF_SAMPLING_FREQUENCIES(count);
				numberOfSamplingFrequenciesValue
						.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()));
				logDetails.appendText("Sampling Frequency 3 (Hz) : " + samplingFrequency3Value.getValue() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		
		samplingFrequency4Value.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int count = 0;
				if(samplingFrequency4Value.getValue().equals("Not Used")) {
					uacSettings.getUAC_SETTING().setSAMPLING_FREQUENCY_4(0);
					countSampleFrequency.put("sample4", false);
				}else {
					uacSettings.getUAC_SETTING().setSAMPLING_FREQUENCY_4(Integer.parseInt(samplingFrequency4Value.getValue()));
					countSampleFrequency.put("sample4", true);
				}
				
				for (Map.Entry<String,Boolean> entry : countSampleFrequency.entrySet()) {
					if(entry.getValue().equals(true)) {
						count++;
					}
				}
				uacSettings.getUAC_SETTING().setNUMBER_OF_SAMPLING_FREQUENCIES(count);
				numberOfSamplingFrequenciesValue
						.setText(String.valueOf(uacSettings.getUAC_SETTING().getNUMBER_OF_SAMPLING_FREQUENCIES()));
				logDetails.appendText("Sampling Frequency 4 (Hz) : " + samplingFrequency4Value.getValue() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		
		
		
		
	}

	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	public static void openSampleFreqSensorConfig(Button samplingFrequencySensorConfig, List<SensorConfig> sensorConfigList) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText(null);
		dialog.setGraphic(null);
//		dialog.setWidth(500);
		dialog.setTitle("Sensor Configuration");
		dialog.setResizable(true);
		ObservableList<SensorConfigurationTable> sensorConfigTableRows = FXCollections.observableArrayList();
		BorderPane borderPane = new BorderPane();
		AnchorPane anchorPane = new AnchorPane();
		TableView<SensorConfigurationTable> sensorTable = new TableView<>();
		sensorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		sensorTable.setMaxHeight(200);
		anchorPane.setTopAnchor(sensorTable, 0.0);
		anchorPane.setLeftAnchor(sensorTable, 0.0);
		anchorPane.setRightAnchor(sensorTable, 0.0);
		anchorPane.setBottomAnchor(sensorTable, 0.0);
		sensorTable.setLayoutX(5);
		borderPane.setTop(anchorPane);
		
		/** Register Address **/
		TableColumn firstColumn = new TableColumn("Register Address");
		firstColumn.setCellValueFactory(new PropertyValueFactory<SensorConfigurationTable, String>("registerAddress"));

		/** Register Value **/
		TableColumn secondColumn = new TableColumn("Register Value");
		secondColumn.setCellValueFactory(new PropertyValueFactory<SensorConfigurationTable, String>("registerValue"));

		/** Slave Address **/
		TableColumn threeColumn = new TableColumn("Slave Address");
		threeColumn.setCellValueFactory(new PropertyValueFactory<SensorConfigurationTable, String>("slaveAddress"));
		sensorTable.getColumns().addAll(firstColumn, secondColumn, threeColumn);

		sensorTable.setItems(sensorConfigTableRows);
		sensorTable.setPrefWidth(500.0);
		sensorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		/** Remove Button **/
		Button removeButton = new Button();
		removeButton.setTooltip(new Tooltip("Remove table"));
		ImageView removeImage = new ImageView("/resources/deleteRow.png");
		removeImage.setFitHeight(15);
		removeImage.setFitWidth(15);
		removeButton.setGraphic(removeImage);
		
		
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10.0);
		gridPane.add(sensorTable, 0, 0);
		gridPane.add(removeButton, 1, 0);
		anchorPane.getChildren().addAll(gridPane);
		dialog.getDialogPane().setContent(borderPane);
		
		AnchorPane anchorPane2 = new AnchorPane();
		GridPane gridPane1 = new GridPane();
		gridPane1.setHgap(10);
		TextField registerAddress = new TextField();
		registerAddress.setDisable(true);
		registerAddress.setPrefWidth(160.0);
		registerAddress.setPromptText("0x6000");
		gridPane1.add(registerAddress, 0, 0);
		TextField registerValue = new TextField();
		registerValue.setDisable(true);
		registerValue.setPrefWidth(160.0);
		registerValue.setPromptText("0x1");
		gridPane1.add(registerValue, 1, 0);
		TextField slaveAddress = new TextField();
		slaveAddress.setDisable(true);
		slaveAddress.setPrefWidth(160.0);
		slaveAddress.setPromptText("0x1");
		gridPane1.add(slaveAddress, 2, 0);

		/** Edit Button **/
		Button editButton = new Button();
		editButton.setDisable(true);
		editButton.setTooltip(new Tooltip("Update table"));
		ImageView editImage = new ImageView("/resources/editRow.png");
		editImage.setFitHeight(15);
		editImage.setFitWidth(15);
		editButton.setGraphic(editImage);
		
		gridPane1.add(editButton, 3, 0);
		
		anchorPane2.getChildren().addAll(gridPane1);
		anchorPane2.setTopAnchor(gridPane1, 0.0);
		anchorPane2.setLeftAnchor(gridPane1, 0.0);
		anchorPane2.setRightAnchor(gridPane1, 0.0);
		anchorPane2.setBottomAnchor(gridPane1, 0.0);
		borderPane.setCenter(anchorPane2);
		borderPane.setMargin(anchorPane2, new Insets(5,0,5,0));
		
		AnchorPane anchorPane3 = new AnchorPane();
		GridPane gridPane2 = new GridPane();
		gridPane2.setHgap(10.0);
		TextArea textArea = new TextArea();
		textArea.setPrefWidth(500.0);
		gridPane2.add(textArea, 0, 0);
		/** Immport Button **/
		Button importButton = new Button();
		importButton.setDisable(true);
		importButton.setTooltip(new Tooltip("Convert To JSON"));
		ImageView importImage = new ImageView("/resources/import.png");
		importImage.setFitHeight(15);
		importImage.setFitWidth(15);
		importButton.setGraphic(importImage);
		gridPane2.add(importButton, 1, 0);
		
		anchorPane3.setTopAnchor(gridPane2, 0.0);
		anchorPane3.setLeftAnchor(gridPane2, 0.0);
		anchorPane3.setRightAnchor(gridPane2, 0.0);
		anchorPane3.setBottomAnchor(gridPane2, 0.0);
		anchorPane3.getChildren().add(gridPane2);
		borderPane.setBottom(anchorPane3);
		
		textArea.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				importButton.setDisable(false);
			}
		});
		
		importButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String sensorConfigText = textArea.getText();
				String[] splitString = sensorConfigText.split("},");
				sensorConfigList.clear();
				sensorConfigTableRows.clear();
				for (int i = 0; i < splitString.length; i++) {
					splitString[i] = splitString[i].replace("{", "");
					splitString[i] = splitString[i].replace("}", "");
					String[] splitString1 = splitString[i].split(",");
					SensorConfig sensorConfigJson = new SensorConfig();
					String registerAddress = splitString1[0];
					sensorConfigJson.setREGISTER_ADDRESS(registerAddress.replaceAll("\\s", ""));
					String registerValue = splitString1[1];
					sensorConfigJson.setREGISTER_VALUE(registerValue);
					if (splitString1.length > 2) {
						String slaveAddress = splitString1[2];
						sensorConfigJson.setSLAVE_ADDRESS(slaveAddress);
					} else {
						sensorConfigJson.setSLAVE_ADDRESS("");
					}
					sensorConfigList.add(sensorConfigJson);
//					formatAndResolutionJson.setRESOLUTION_REGISTER_SETTING(list);
				}
				if (sensorConfigList.size() > 5) {
					Alert a = new Alert(AlertType.ERROR);
					a.setHeaderText(null);
					a.setContentText("Maximum 5 rows allowed");
					a.show();
				} else {
//					List<SensorConfig> resRegSettings = formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING();
					for (int i = 0; i < sensorConfigList.size(); i++) {
						if (sensorConfigList.get(i) != null) {
							SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
							sensorConfigTable.setRegisterAddress(sensorConfigList.get(i).getREGISTER_ADDRESS());
							sensorConfigTable.setRegisterValue(sensorConfigList.get(i).getREGISTER_VALUE());
							sensorConfigTable.setSlaveAddress(sensorConfigList.get(i).getSLAVE_ADDRESS());
							sensorConfigTableRows.add(sensorConfigTable);
						}

					}
					dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				}
			}
		});
		
		for (int i = 0; i < sensorConfigList.size(); i++) {
			if (sensorConfigList.get(i) != null) {
				SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
				sensorConfigTable.setRegisterAddress(sensorConfigList.get(i).getREGISTER_ADDRESS());
				sensorConfigTable.setRegisterValue(sensorConfigList.get(i).getREGISTER_VALUE());
				sensorConfigTable.setSlaveAddress(sensorConfigList.get(i).getSLAVE_ADDRESS());
				sensorConfigTableRows.add(sensorConfigTable);
			}
		}
		
		sensorTable.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) {
				SensorConfigurationTable selectedItem = sensorTable.getSelectionModel().getSelectedItem();
				registerAddress.setText(selectedItem.getRegisterAddress());
				registerAddress.setDisable(false);
				registerValue.setText(selectedItem.getRegisterValue());
				registerValue.setDisable(false);
				slaveAddress.setText(selectedItem.getSlaveAddress());
				slaveAddress.setDisable(false);
				}
			});
		
		registerAddress.textProperty().addListener((observable, oldValue, newValue) -> {
			registerValue.textProperty().addListener((observable1, oldValue1, newValue1) -> {
				slaveAddress.textProperty().addListener((observable2, oldValue2, newValue2) -> {
					editButton.setDisable(newValue == null || newValue1 == null || newValue2 == null);
				});
			});
		});
		
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SensorConfigurationTable selectedItem = sensorTable.getSelectionModel().getSelectedItem();
				selectedItem.setRegisterAddress(registerAddress.getText());
				registerAddress.setText("");
				registerAddress.setDisable(true);
				selectedItem.setRegisterValue(registerValue.getText());
				registerValue.setText("");
				registerValue.setDisable(true);
				selectedItem.setSlaveAddress(slaveAddress.getText());
				slaveAddress.setText("");
				slaveAddress.setDisable(true);
				sensorTable.refresh();
				dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
			}
		});
		
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sensorTable.getItems().clear();
				sensorConfigList.clear();
				SensorConfig sensorConfig = new SensorConfig();
				sensorConfig.setREGISTER_ADDRESS("");
				sensorConfig.setREGISTER_VALUE("");
				sensorConfig.setSLAVE_ADDRESS("");
				sensorConfigList.add(sensorConfig);
				dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
				sensorConfigTable.setRegisterAddress("");
				sensorConfigTable.setRegisterValue("");
				sensorConfigTable.setSlaveAddress("");
				sensorConfigTableRows.add(sensorConfigTable);
			}
		});

		/** OK/Cancel Button **/
		dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
		Optional<String> showAndWait = dialog.showAndWait();
		if(showAndWait.isPresent()) {
			ObservableList<SensorConfigurationTable> items = sensorTable.getItems();
			for(int i = 0;i < sensorConfigList.size();i++) {
				sensorConfigList.get(i).setREGISTER_ADDRESS(items.get(i).getRegisterAddress());
				sensorConfigList.get(i).setREGISTER_VALUE(items.get(i).getRegisterValue());
				sensorConfigList.get(i).setSLAVE_ADDRESS(items.get(i).getSlaveAddress());
			}
			if(!sensorConfigList.get(0).getREGISTER_ADDRESS().equals("")
				|| !sensorConfigList.get(0).getREGISTER_VALUE().equals("")
				|| !sensorConfigList.get(0).getSLAVE_ADDRESS().equals("")) {
				samplingFrequencySensorConfig.setStyle("-fx-background-color:green");
			}else {
				samplingFrequencySensorConfig.setStyle("");
			}
		}
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
			TableView<FormatAndResolutionTableModel> formatResolutionTable, int index, WebView helpContent,
			WebView logDetails1, TextArea logDetails, SX3ConfiguartionHelp sx3ConfigurationHelp, Map<String, Boolean> uvcuacTabErrorList, Tab subTab, Tab subSubTab, Tab uvcuacSettingsTab) {
		Map<String,Boolean> framRateEmptyCheck = new HashMap<>();
		FormatAndResolutionTableModel formatAndResolution = new FormatAndResolutionTableModel();
		Label sno = new Label(String.valueOf(index));
		sno.setStyle("-fx-font-size: 12");
		formatAndResolution.setSno(sno);
		formatAndResolutionJson.setS_NO(index);

		/** Image Format **/
		ComboBox<String> imageFormat = new ComboBox<>();
		imageFormat.setValue(formatAndResolutionJson.getIMAGE_FORMAT());
		formatAndResolution.setImageFormat(imageFormat);
		imageFormat.setStyle("-fx-font-size: 12");
		imageFormat.getItems().addAll(UVCSettingsConstants.IMAGE_FORMAT);
		imageFormat.setTooltip(new Tooltip("Image format type"));
		imageFormat.getTooltip().setOnShowing(s->{
		    Bounds bounds = imageFormat.localToScreen(imageFormat.getBoundsInLocal());
		    imageFormat.getTooltip().setX(bounds.getMaxX());
		});
		imageFormat.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getIMAGE_FORMAT());
				}
			}
		});
		imageFormat.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Bit Per Pixel **/
		TextField bitPerPixel = new TextField();
		bitPerPixel.setDisable(true);
		bitPerPixel.setStyle("-fx-font-size: 12");
		bitPerPixel.setText(String.valueOf(formatAndResolutionJson.getBITS_PER_PIXEL()));
		formatAndResolution.setBitPerPixcel(bitPerPixel);
		imageFormat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				formatAndResolutionJson.setIMAGE_FORMAT(imageFormat.getValue());
				if (imageFormat.getValue().equals("YUY2")) {
					bitPerPixel.setText("16");
					logDetails.appendText("Image format : " + imageFormat.getValue() + ".\n<br>");
					logDetails.appendText("Bit Per Pixel : " + bitPerPixel.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else if (imageFormat.getValue().equals("YUYV")) {
					bitPerPixel.setText("16");
					logDetails.appendText("Image format : " + imageFormat.getValue() + ".\n<br>");
					logDetails.appendText("Bit Per Pixel : " + bitPerPixel.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else if (imageFormat.getValue().equals("Y41P")) {
					bitPerPixel.setText("12");
					logDetails.appendText("Image format : " + imageFormat.getValue() + ".\n<br>");
					logDetails.appendText("Bit Per Pixel : " + bitPerPixel.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else if (imageFormat.getValue().equals("YUVP")) {
					bitPerPixel.setText("24");
					logDetails.appendText("Image format : " + imageFormat.getValue() + ".\n<br>");
					logDetails.appendText("Bit Per Pixel : " + bitPerPixel.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else if (imageFormat.getValue().equals("YUV4")) {
					bitPerPixel.setText("32");
					logDetails.appendText("Image format : " + imageFormat.getValue() + ".\n<br>");
					logDetails.appendText("Bit Per Pixel : " + bitPerPixel.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else if (imageFormat.getValue().equals("IYU2")) {
					bitPerPixel.setText("24");
					logDetails.appendText("Image format : " + imageFormat.getValue() + ".\n<br>");
					logDetails.appendText("Bit Per Pixel : " + bitPerPixel.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else if (imageFormat.getValue().equals("AYUV")) {
					bitPerPixel.setText("32");
					logDetails.appendText("Image format : " + imageFormat.getValue() + ".\n<br>");
					logDetails.appendText("Bit Per Pixel : " + bitPerPixel.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else if (imageFormat.getValue().equals("NV12")) {
					bitPerPixel.setText("12");
					logDetails.appendText("Image format : " + imageFormat.getValue() + ".\n<br>");
					logDetails.appendText("Bit Per Pixel : " + bitPerPixel.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else if (imageFormat.getValue().equals("NV16")) {
					bitPerPixel.setText("16");
					logDetails.appendText("Image format : " + imageFormat.getValue() + ".\n<br>");
					logDetails.appendText("Bit Per Pixel : " + bitPerPixel.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else if (imageFormat.getValue().equals("YV12")) {
					bitPerPixel.setText("12");
					logDetails.appendText("Image format : " + imageFormat.getValue() + ".\n<br>");
					logDetails.appendText("Bit Per Pixel : " + bitPerPixel.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else if (imageFormat.getValue().equals("GREY")) {
					bitPerPixel.setText("8");
					logDetails.appendText("Image format : " + imageFormat.getValue() + ".\n<br>");
					logDetails.appendText("Bit Per Pixel : " + bitPerPixel.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				} else if (imageFormat.getValue().equals("Y16")) {
					bitPerPixel.setText("16");
					logDetails.appendText("Image format : " + imageFormat.getValue() + ".\n<br>");
					logDetails.appendText("Bit Per Pixel : " + bitPerPixel.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
				formatAndResolutionJson.setBITS_PER_PIXEL(Integer.parseInt(bitPerPixel.getText()));
			}
		});

		/** H Resolution **/
		TextField hResolution = new TextField();
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
		hResolution.setTooltip(new Tooltip("Horizontal length (in pixels)"));
		hResolution.getTooltip().setOnShowing(s->{
		    Bounds bounds = hResolution.localToScreen(hResolution.getBoundsInLocal());
		    hResolution.getTooltip().setX(bounds.getMaxX());
		});
		UVCSettingsValidation.setupFormatAndResolutionValidationForNumeric(hResolution,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, formatAndResolutionJson, "HResolution");
		hResolution.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!hResolution.getText().equals("")) {
					logDetails.appendText("H Resolution : " + hResolution.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					uvcuacTabErrorList.put("hResolution", false);
					if(uvcuacTabErrorList.containsValue(false)) {
						subTab.setStyle("");
						subSubTab.setStyle("");
						uvcuacSettingsTab.setStyle("");
						hResolution.setStyle("");
					}
				}else {
					uvcuacTabErrorList.put("hResolution", true);
					if(uvcuacTabErrorList.containsValue(true)) {
						subTab.setStyle("-fx-border-color:red;");
						subSubTab.setStyle("-fx-border-color:red;");
						uvcuacSettingsTab.setStyle("-fx-border-color:red;");
						hResolution.setStyle("-fx-border-color:red;");
					}
//					logDetails.appendText("<b><span style='color:red;'>Error : H Resolution should not empty.\n</span></b><br>");
//					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
		hResolution.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getH_RESOLUTION());
				}
			}
		});
		hResolution.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** V Resolution **/
		TextField vResolution = new TextField();
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
		vResolution.setTooltip(new Tooltip("Vertical Height (in pixels)"));
		vResolution.getTooltip().setOnShowing(s->{
		    Bounds bounds = vResolution.localToScreen(vResolution.getBoundsInLocal());
		    vResolution.getTooltip().setX(bounds.getMaxX());
		});
		UVCSettingsValidation.setupFormatAndResolutionValidationForNumeric(vResolution,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, formatAndResolutionJson, "VResolution");
		vResolution.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!vResolution.getText().equals("")) {
					logDetails.appendText("V Resolution : " + vResolution.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					uvcuacTabErrorList.put("vResolution", false);
					if(uvcuacTabErrorList.containsValue(false)) {
						subTab.setStyle("");
						subSubTab.setStyle("");
						uvcuacSettingsTab.setStyle("");
						vResolution.setStyle("");
					}
				}else {
					uvcuacTabErrorList.put("vResolution", true);
					if(uvcuacTabErrorList.containsValue(true)) {
						subTab.setStyle("-fx-border-color:red;");
						subSubTab.setStyle("-fx-border-color:red;");
						uvcuacSettingsTab.setStyle("-fx-border-color:red;");
						vResolution.setStyle("-fx-border-color:red;");
					}
//					logDetails.appendText("<b><span style='color:red;'>Error : V Resolution should not empty.\n</span></b><br>");
//					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
		vResolution.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getV_RESOLUTION());
				}
			}
		});
		vResolution.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Still Captured **/
		CheckBox stillCaptured = new CheckBox();
		stillCaptured.setStyle("-fx-font-size: 12");
		stillCaptured.setTooltip(new Tooltip("Enable still capture support"));
		stillCaptured.getTooltip().setOnShowing(s->{
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
				logDetails.appendText("Still Captured : " + stillCaptured.isSelected() + ".\n<br>");
				logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
			}
		});
		stillCaptured.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getSTILL_CAPTURE());
				}
			}
		});
		stillCaptured.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});
		if (formatAndResolutionJson.getSTILL_CAPTURE().equals("Enable")) {
			stillCaptured.setSelected(true);
		} else {
			stillCaptured.setSelected(false);
		}
		formatAndResolution.setStillCapture(stillCaptured);

		/** Supported In FS **/
		CheckBox supportedInLS = new CheckBox();
		supportedInLS.setStyle("-fx-font-size: 12");
		supportedInLS.setTooltip(new Tooltip("Resolution supported in LS"));
		supportedInLS.getTooltip().setOnShowing(s->{
		    Bounds bounds = supportedInLS.localToScreen(supportedInLS.getBoundsInLocal());
		    supportedInLS.getTooltip().setX(bounds.getMaxX());
		});
		if (formatAndResolutionJson.getSUPPORTED_IN_FS().equals("Enable")) {
			supportedInLS.setSelected(true);
		} else {
			supportedInLS.setSelected(false);
		}
		formatAndResolution.setSupportedInFS(supportedInLS);
		supportedInLS.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getSUPPORTED_IN_LS());
				}
			}
		});
		supportedInLS.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Supported In HS **/
		CheckBox supportedInHS = new CheckBox();
		supportedInHS.setStyle("-fx-font-size: 12");
		supportedInHS.setTooltip(new Tooltip("Resolution supported in HS"));
		supportedInHS.getTooltip().setOnShowing(s->{
		    Bounds bounds = supportedInHS.localToScreen(supportedInHS.getBoundsInLocal());
		    supportedInHS.getTooltip().setX(bounds.getMaxX());
		});
		if (formatAndResolutionJson.getSUPPORTED_IN_HS().equals("Enable")) {
			supportedInHS.setSelected(true);
		} else {
			supportedInHS.setSelected(false);
		}
		formatAndResolution.setSupportedInHS(supportedInHS);
		supportedInHS.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getSUPPORTED_IN_HS());
				}
			}
		});
		supportedInHS.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Supported In SS **/
		CheckBox supportedInSS = new CheckBox();
		supportedInSS.setStyle("-fx-font-size: 12");
		supportedInSS.setTooltip(new Tooltip("Resolution supported in SS"));
		supportedInSS.getTooltip().setOnShowing(s->{
		    Bounds bounds = supportedInSS.localToScreen(supportedInSS.getBoundsInLocal());
		    supportedInSS.getTooltip().setX(bounds.getMaxX());
		});
		if (formatAndResolutionJson.getSUPPORTED_IN_SS().equals("Enable")) {
			supportedInSS.setSelected(true);
		} else {
			supportedInSS.setSelected(false);
		}
		formatAndResolution.setSupportedInSS(supportedInSS);
		supportedInSS.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getSUPPORTED_IN_SS());
				}
			}
		});
		supportedInSS.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Frame Rate In LS **/
		TextField frameRateInLS = new TextField();
		frameRateInLS.setAlignment(Pos.CENTER_RIGHT);
		frameRateInLS.setStyle("-fx-font-size: 12");
		frameRateInLS.setTooltip(new Tooltip("FS Frame Rate"));
		frameRateInLS.getTooltip().setOnShowing(s->{
		    Bounds bounds = frameRateInLS.localToScreen(frameRateInLS.getBoundsInLocal());
		    frameRateInLS.getTooltip().setX(bounds.getMaxX());
		});
		if (formatAndResolutionJson.getFRAME_RATE_IN_FS() == 0) {
			frameRateInLS.setText("");
		} else {
			frameRateInLS.setText(String.valueOf(formatAndResolutionJson.getFRAME_RATE_IN_FS()));
		}
		formatAndResolution.setFrameRateInFS(frameRateInLS);
		frameRateInLS.setPromptText("FS Frame Rate");
		frameRateInLS.setDisable(true);
		frameRateInLS.setMaxWidth(100);
		UVCSettingsValidation.setupFormatAndResolutionValidationForNumeric(frameRateInLS,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, formatAndResolutionJson,"FSFrameRate");
		frameRateInLS.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!frameRateInLS.getText().equals("") && Integer.parseInt(frameRateInLS.getText()) != 0) {
					framRateEmptyCheck.put("framRateFS", false);
				}else if(frameRateInLS.getText().equals("")) {
					framRateEmptyCheck.put("framRateFS", true);
				}
				if(!frameRateInLS.getText().equals("")) {
					logDetails.appendText("FS Frame Rate : " + frameRateInLS.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					uvcuacTabErrorList.put("framRateFS", false);
					if(uvcuacTabErrorList.containsValue(false)) {
						subTab.setStyle("");
						subSubTab.setStyle("");
						uvcuacSettingsTab.setStyle("");
						frameRateInLS.setStyle("");
					}
				}else {
					uvcuacTabErrorList.put("framRateFS", true);
					if(uvcuacTabErrorList.containsValue(true)) {
						subTab.setStyle("-fx-border-color:red;");
						subSubTab.setStyle("-fx-border-color:red;");
						uvcuacSettingsTab.setStyle("-fx-border-color:red;");
						frameRateInLS.setStyle("-fx-border-color:red;");
					}
//					logDetails.appendText("<b><span style='color:red;'>Error : FS Frame Rate should not empty.\n</span></b><br>");
//					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
		frameRateInLS.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getFRAME_RATE_IN_LS());
				}
			}
		});
		frameRateInLS.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Frame Rate In HS **/
		TextField frameRateInHS = new TextField();
		frameRateInHS.setAlignment(Pos.CENTER_RIGHT);
		frameRateInHS.setStyle("-fx-font-size: 12");
		frameRateInHS.setTooltip(new Tooltip("HS Frame Rate"));
		frameRateInHS.getTooltip().setOnShowing(s->{
		    Bounds bounds = frameRateInHS.localToScreen(frameRateInHS.getBoundsInLocal());
		    frameRateInHS.getTooltip().setX(bounds.getMaxX());
		});
		if (formatAndResolutionJson.getFRAME_RATE_IN_HS() == 0) {
			frameRateInHS.setText("");
		} else {
			frameRateInHS.setText(String.valueOf(formatAndResolutionJson.getFRAME_RATE_IN_HS()));
		}
		formatAndResolution.setFrameRateInHS(frameRateInHS);
		frameRateInHS.setPromptText("HS Frame Rate");
		frameRateInHS.setDisable(true);
		frameRateInHS.setMaxWidth(100);
		UVCSettingsValidation.setupFormatAndResolutionValidationForNumeric(frameRateInHS,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, formatAndResolutionJson,"HSFrameRate");
		frameRateInHS.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!frameRateInHS.getText().equals("") && Integer.parseInt(frameRateInHS.getText()) != 0) {
					framRateEmptyCheck.put("framRateHS", false);
				}else if(frameRateInHS.getText().equals("")) {
					framRateEmptyCheck.put("framRateHS", true);
				}
				if(!frameRateInHS.getText().equals("")) {
					logDetails.appendText("HS Frame Rate : " + frameRateInHS.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					uvcuacTabErrorList.put("framRateHS", false);
					if(uvcuacTabErrorList.containsValue(false)) {
						subTab.setStyle("");
						subSubTab.setStyle("");
						uvcuacSettingsTab.setStyle("");
						frameRateInHS.setStyle("");
					}
				}else {
					uvcuacTabErrorList.put("framRateHS", true);
					if(uvcuacTabErrorList.containsValue(true)) {
						subTab.setStyle("-fx-border-color:red;");
						subSubTab.setStyle("-fx-border-color:red;");
						uvcuacSettingsTab.setStyle("-fx-border-color:red;");
						frameRateInHS.setStyle("-fx-border-color:red;");
					}
//					logDetails.appendText("<b><span style='color:red;'>Error : HS Frame Rate should not empty.\n</span></b><br>");
//					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
		frameRateInHS.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getFRAME_RATE_IN_HS());
				}
			}
		});
		frameRateInHS.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				helpContent.getEngine().loadContent("");
			}
		});

		/** Frame Rate In SS **/
		TextField frameRateInSS = new TextField();
		frameRateInSS.setAlignment(Pos.CENTER_RIGHT);
		frameRateInSS.setStyle("-fx-font-size: 12");
		frameRateInSS.setTooltip(new Tooltip("SS Frame Rate"));
		frameRateInSS.getTooltip().setOnShowing(s->{
		    Bounds bounds = frameRateInSS.localToScreen(frameRateInSS.getBoundsInLocal());
		    frameRateInSS.getTooltip().setX(bounds.getMaxX());
		});
		if (formatAndResolutionJson.getFRAME_RATE_IN_SS() == 0) {
			frameRateInSS.setText("");
		} else {
			frameRateInSS.setText(String.valueOf(formatAndResolutionJson.getFRAME_RATE_IN_SS()));
		}
		formatAndResolution.setFrameRateInSS(frameRateInSS);
		frameRateInSS.setPromptText("SS Frame Rate");
		frameRateInSS.setDisable(true);
		frameRateInSS.setMaxWidth(100);
		UVCSettingsValidation.setupFormatAndResolutionValidationForNumeric(frameRateInSS,
				UVCSettingsValidation.INVALID_NUMERIC_ERROR_MESSAGE, formatAndResolutionJson, "SSFrameRate");
		frameRateInSS.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				if(!frameRateInSS.getText().equals("") && Integer.parseInt(frameRateInSS.getText()) != 0) {
					framRateEmptyCheck.put("framRateSS", false);
				}else if(frameRateInSS.getText().equals("")) {
					framRateEmptyCheck.put("framRateSS", true);
				}
				if(!frameRateInSS.getText().equals("")) {
					logDetails.appendText("SS Frame Rate : " + frameRateInSS.getText() + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					uvcuacTabErrorList.put("framRateSS", false);
					if(uvcuacTabErrorList.containsValue(false)) {
						subTab.setStyle("");
						subSubTab.setStyle("");
						uvcuacSettingsTab.setStyle("");
						frameRateInSS.setStyle("");
					}
				}else {
					uvcuacTabErrorList.put("framRateSS", true);
					if(uvcuacTabErrorList.containsValue(true)) {
						subTab.setStyle("-fx-border-color:red;");
						subSubTab.setStyle("-fx-border-color:red;");
						uvcuacSettingsTab.setStyle("-fx-border-color:red;");
						frameRateInSS.setStyle("-fx-border-color:red;");
					}
//					logDetails.appendText("<b><span style='color:red;'>Error : SS Frame Rate should not empty.\n</span></b><br>");
//					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
				}
			}
		});
		frameRateInSS.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(sx3ConfigurationHelp.getUVC_SETTINGS() != null) {
					helpContent.getEngine().loadContent(sx3ConfigurationHelp.getUVC_SETTINGS().getFORMAT_RESOLUTION().getFRAME_RATE_IN_SS());
				}
			}
		});
//		frameRateInSS.setOnMouseExited(new EventHandler<Event>() {
//			@Override
//			public void handle(Event event) {
//				helpContent.getEngine().loadContent("");
//			}
//		});
		
		

		/** Enable/Disable FS Frame Rate **/
		supportedInLS.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (supportedInLS.isSelected()) {
					frameRateInLS.setDisable(false);
					logDetails.appendText("Supported In FS : " + true + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					formatAndResolutionJson.setSUPPORTED_IN_FS("Enable");
					framRateEmptyCheck.put("framRateFS", true);
				} else {
					frameRateInLS.setDisable(true);
					logDetails.appendText("Supported In FS : " + false + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					formatAndResolutionJson.setSUPPORTED_IN_FS("Disable");
					framRateEmptyCheck.put("framRateFS", false);
					frameRateInLS.setText("");
				}

			}
		});

		/** Enable/Disable HS Frame Rate **/
		supportedInHS.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (supportedInHS.isSelected()) {
					frameRateInHS.setDisable(false);
					logDetails.appendText("Supported In HS : " + true + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					formatAndResolutionJson.setSUPPORTED_IN_HS("Enable");
					framRateEmptyCheck.put("framRateHS", true);
				} else {
					frameRateInHS.setDisable(true);
					logDetails.appendText("Supported In HS : " + false + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					formatAndResolutionJson.setSUPPORTED_IN_HS("Disable");
					framRateEmptyCheck.put("framRateHS", false);
					frameRateInHS.setText("");
				}

			}
		});

		/** Enable/Disable HS Frame Rate **/
		supportedInSS.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (supportedInSS.isSelected()) {
					frameRateInSS.setDisable(false);
					logDetails.appendText("Supported In SS : " + true + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					formatAndResolutionJson.setSUPPORTED_IN_SS("Enable");
					framRateEmptyCheck.put("framRateSS", true);
				} else {
					frameRateInSS.setDisable(true);
					logDetails.appendText("Supported In SS : " + false + ".\n<br>");
					logDetails1.getEngine().loadContent(logDetails.getText(), "text/html");
					formatAndResolutionJson.setSUPPORTED_IN_SS("Disable");
					framRateEmptyCheck.put("framRateSS", false);
					frameRateInSS.setText("");
				}

			}
		});
		
		
		Button sensorConfigEditButton = new Button();
		formatAndResolution.setButton(sensorConfigEditButton);
		sensorConfigEditButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (formatAndResolutionJson.getIMAGE_FORMAT() != null
						&& formatAndResolutionJson.getH_RESOLUTION() != 0
						&& formatAndResolutionJson.getV_RESOLUTION() != 0
						&& (formatAndResolutionJson.getSUPPORTED_IN_FS().equals("Enable") 
								|| formatAndResolutionJson.getSUPPORTED_IN_HS().equals("Enable")
								|| formatAndResolutionJson.getSUPPORTED_IN_SS().equals("Enable"))
						&& !framRateEmptyCheck.containsValue(true)) {
					openSensorConfigurationDialog(addBtn, editBtn, formatAndResolution, formatAndResolutionJson,
							formatResolutionTable,sensorConfigEditButton);
				}else {
					Alert a = new Alert(AlertType.ERROR);
					a.setHeaderText(null);
					a.setContentText("fill all columns");
					a.show();
				}

			}
		});
		
		List<SensorConfig> resolution_REGISTER_SETTING = formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING();
		if(resolution_REGISTER_SETTING != null && !resolution_REGISTER_SETTING.get(0).getREGISTER_ADDRESS().equals("")) {
			sensorConfigEditButton.setStyle("-fx-background-color:green");
		}

		if (!formatAndResolutionJson.getIMAGE_FORMAT().equals("")) {
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
		}

		formateAndResolutionData.add(formatAndResolution);
		FilteredList<FormatAndResolutionTableModel> filteredData = new FilteredList<>(formateAndResolutionData,
				formatAndResol -> formateAndResolutionData.indexOf(formatAndResol) < 16);
		SortedList<FormatAndResolutionTableModel> sortedData = new SortedList<>(filteredData);
		formatResolutionTable.setItems(sortedData);

	}

	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	public static void openSensorConfigurationDialog(Button addBtn, Button editBtn,
			FormatAndResolutionTableModel formatAndResolution, FormatAndResolutions formatAndResolutionJson,
			TableView<FormatAndResolutionTableModel> formatResolutionTable, Button sensorConfigEditButton) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText(null);
		dialog.setGraphic(null);
//		dialog.setWidth(500);
		dialog.setTitle("Sensor Configuration");
		dialog.setResizable(true);
		ObservableList<SensorConfigurationTable> sensorConfigTableRows = FXCollections.observableArrayList();
		BorderPane borderPane = new BorderPane();
		AnchorPane anchorPane = new AnchorPane();
		TableView<SensorConfigurationTable> sensorTable = new TableView<>();
		sensorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		sensorTable.setMaxHeight(200);
		anchorPane.setTopAnchor(sensorTable, 0.0);
		anchorPane.setLeftAnchor(sensorTable, 0.0);
		anchorPane.setRightAnchor(sensorTable, 0.0);
		anchorPane.setBottomAnchor(sensorTable, 0.0);
		sensorTable.setLayoutX(5);
		borderPane.setTop(anchorPane);
		
		/** Register Address **/
		TableColumn firstColumn = new TableColumn("Register Address");
		firstColumn.setCellValueFactory(new PropertyValueFactory<SensorConfigurationTable, String>("registerAddress"));

		/** Register Value **/
		TableColumn secondColumn = new TableColumn("Register Value");
		secondColumn.setCellValueFactory(new PropertyValueFactory<SensorConfigurationTable, String>("registerValue"));

		/** Slave Address **/
		TableColumn threeColumn = new TableColumn("Slave Address");
		threeColumn.setCellValueFactory(new PropertyValueFactory<SensorConfigurationTable, String>("slaveAddress"));
		sensorTable.getColumns().addAll(firstColumn, secondColumn, threeColumn);

		sensorTable.setItems(sensorConfigTableRows);
		sensorTable.setPrefWidth(500.0);
		sensorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		/** Remove Button **/
		Button removeButton = new Button();
		removeButton.setTooltip(new Tooltip("Remove table"));
		ImageView removeImage = new ImageView("/resources/deleteRow.png");
		removeImage.setFitHeight(15);
		removeImage.setFitWidth(15);
		removeButton.setGraphic(removeImage);
		
		
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10.0);
		gridPane.add(sensorTable, 0, 0);
		gridPane.add(removeButton, 1, 0);
		anchorPane.getChildren().addAll(gridPane);
		dialog.getDialogPane().setContent(borderPane);
		
		AnchorPane anchorPane2 = new AnchorPane();
		GridPane gridPane1 = new GridPane();
		gridPane1.setHgap(10);
		TextField registerAddress = new TextField();
		registerAddress.setDisable(true);
		registerAddress.setPrefWidth(160.0);
		registerAddress.setPromptText("0x6000");
		gridPane1.add(registerAddress, 0, 0);
		TextField registerValue = new TextField();
		registerValue.setDisable(true);
		registerValue.setPrefWidth(160.0);
		registerValue.setPromptText("0x1");
		gridPane1.add(registerValue, 1, 0);
		TextField slaveAddress = new TextField();
		slaveAddress.setDisable(true);
		slaveAddress.setPrefWidth(160.0);
		slaveAddress.setPromptText("0x1");
		gridPane1.add(slaveAddress, 2, 0);

		/** Edit Button **/
		Button editButton = new Button();
		editButton.setDisable(true);
		editButton.setTooltip(new Tooltip("Update table"));
		ImageView editImage = new ImageView("/resources/editRow.png");
		editImage.setFitHeight(15);
		editImage.setFitWidth(15);
		editButton.setGraphic(editImage);
		
		gridPane1.add(editButton, 3, 0);
		
		anchorPane2.getChildren().addAll(gridPane1);
		anchorPane2.setTopAnchor(gridPane1, 0.0);
		anchorPane2.setLeftAnchor(gridPane1, 0.0);
		anchorPane2.setRightAnchor(gridPane1, 0.0);
		anchorPane2.setBottomAnchor(gridPane1, 0.0);
		borderPane.setCenter(anchorPane2);
		borderPane.setMargin(anchorPane2, new Insets(5,0,5,0));
		
		AnchorPane anchorPane3 = new AnchorPane();
		GridPane gridPane2 = new GridPane();
		gridPane2.setHgap(10.0);
		TextArea textArea = new TextArea();
		textArea.setPrefWidth(500.0);
		gridPane2.add(textArea, 0, 0);
		/** Immport Button **/
		Button importButton = new Button();
		importButton.setDisable(true);
		importButton.setTooltip(new Tooltip("Convert To JSON"));
		ImageView importImage = new ImageView("/resources/import.png");
		importImage.setFitHeight(15);
		importImage.setFitWidth(15);
		importButton.setGraphic(importImage);
		gridPane2.add(importButton, 1, 0);
		
		anchorPane3.setTopAnchor(gridPane2, 0.0);
		anchorPane3.setLeftAnchor(gridPane2, 0.0);
		anchorPane3.setRightAnchor(gridPane2, 0.0);
		anchorPane3.setBottomAnchor(gridPane2, 0.0);
		anchorPane3.getChildren().add(gridPane2);
		borderPane.setBottom(anchorPane3);
		
		textArea.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				importButton.setDisable(false);
			}
		});
		
		importButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String sensorConfigText = textArea.getText();
				String[] splitString = sensorConfigText.split("},");
				if(formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING() != null) {
					formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().clear();
				}
				sensorConfigTableRows.clear();
				List<SensorConfig> list = new ArrayList<>();
				for (int i = 0; i < splitString.length; i++) {
					splitString[i] = splitString[i].replace("{", "");
					splitString[i] = splitString[i].replace("}", "");
					String[] splitString1 = splitString[i].split(",");
					SensorConfig sensorConfigJson = new SensorConfig();
					String registerAddress = splitString1[0];
					sensorConfigJson.setREGISTER_ADDRESS(registerAddress.replaceAll("\\s", ""));
					String registerValue = splitString1[1];
					sensorConfigJson.setREGISTER_VALUE(registerValue);
					if (splitString1.length > 2) {
						String slaveAddress = splitString1[2];
						sensorConfigJson.setSLAVE_ADDRESS(slaveAddress);
					} else {
						sensorConfigJson.setSLAVE_ADDRESS("");
					}
					list.add(sensorConfigJson);
				}
				formatAndResolutionJson.setRESOLUTION_REGISTER_SETTING(list);
				formatAndResolutionJson.setRESOLUTION_REGISTER_SETTING_LEN(list.size());
				if (formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().size() > 100) {
					Alert a = new Alert(AlertType.ERROR);
					a.setHeaderText(null);
					a.setContentText("Maximum 100 rows allowed");
					a.show();
				} else {
					List<SensorConfig> resRegSettings = formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING();
					for (int i = 0; i < resRegSettings.size(); i++) {
						if (resRegSettings.get(i) != null) {
							SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
							sensorConfigTable.setRegisterAddress(resRegSettings.get(i).getREGISTER_ADDRESS());
							sensorConfigTable.setRegisterValue(resRegSettings.get(i).getREGISTER_VALUE());
							sensorConfigTable.setSlaveAddress(resRegSettings.get(i).getSLAVE_ADDRESS());
							sensorConfigTableRows.add(sensorConfigTable);
						}

					}
					dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				}
			}
		});
		
		List<SensorConfig> resRegSettings = formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING();
		if(resRegSettings != null) {
			for (int i = 0; i < resRegSettings.size(); i++) {
				SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
				sensorConfigTable.setRegisterAddress(resRegSettings.get(i).getREGISTER_ADDRESS());
				sensorConfigTable.setRegisterValue(resRegSettings.get(i).getREGISTER_VALUE());
				sensorConfigTable.setSlaveAddress(resRegSettings.get(i).getSLAVE_ADDRESS());
				sensorConfigTableRows.add(sensorConfigTable);
			}
		}
		
		sensorTable.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) {
				SensorConfigurationTable selectedItem = sensorTable.getSelectionModel().getSelectedItem();
				registerAddress.setText(selectedItem.getRegisterAddress());
				registerAddress.setDisable(false);
				registerValue.setText(selectedItem.getRegisterValue());
				registerValue.setDisable(false);
				slaveAddress.setText(selectedItem.getSlaveAddress());
				slaveAddress.setDisable(false);
				}
			});
		
		registerAddress.textProperty().addListener((observable, oldValue, newValue) -> {
			registerValue.textProperty().addListener((observable1, oldValue1, newValue1) -> {
				slaveAddress.textProperty().addListener((observable2, oldValue2, newValue2) -> {
					editButton.setDisable(newValue == null || newValue1 == null || newValue2 == null);
				});
			});
		});
		
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SensorConfigurationTable selectedItem = sensorTable.getSelectionModel().getSelectedItem();
				selectedItem.setRegisterAddress(registerAddress.getText());
				registerAddress.setText("");
				registerAddress.setDisable(true);
				selectedItem.setRegisterValue(registerValue.getText());
				registerValue.setText("");
				registerValue.setDisable(true);
				selectedItem.setSlaveAddress(slaveAddress.getText());
				slaveAddress.setText("");
				slaveAddress.setDisable(true);
				sensorTable.refresh();
			}
		});
		
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sensorTable.getItems().clear();
				formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().clear();
				List<SensorConfig> sensorConfigList = new ArrayList<>();
				SensorConfig sensorConfig = new SensorConfig();
				sensorConfig.setREGISTER_ADDRESS("");
				sensorConfig.setREGISTER_VALUE("");
				sensorConfig.setSLAVE_ADDRESS("");
				sensorConfigList.add(sensorConfig);
				formatAndResolutionJson.setRESOLUTION_REGISTER_SETTING(sensorConfigList);
				dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				SensorConfigurationTable sensorConfigTable = new SensorConfigurationTable();
				sensorConfigTable.setRegisterAddress("");
				sensorConfigTable.setRegisterValue("");
				sensorConfigTable.setSlaveAddress("");
				sensorConfigTableRows.add(sensorConfigTable);
			}
		});

		/** OK/Cancel Button **/
		dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
		Optional<String> showAndWait = dialog.showAndWait();
		if (showAndWait.isPresent()) {
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
			editBtn.setDisable(true);
			ObservableList<SensorConfigurationTable> items = sensorTable.getItems();
			for(int i = 0;i < formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().size();i++) {
				formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(i).setREGISTER_ADDRESS(items.get(i).getRegisterAddress());
				formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(i).setREGISTER_VALUE(items.get(i).getRegisterValue());
				formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(i).setSLAVE_ADDRESS(items.get(i).getSlaveAddress());
			}
			if(!formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(0).getREGISTER_ADDRESS().equals("")
				|| !formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(0).getREGISTER_VALUE().equals("")
				|| !formatAndResolutionJson.getRESOLUTION_REGISTER_SETTING().get(0).getSLAVE_ADDRESS().equals("")) {
				sensorConfigEditButton.setStyle("-fx-background-color:green");
			}else {
				sensorConfigEditButton.setStyle("");
				addBtn.setDisable(true);
			}
		}
	}

}
