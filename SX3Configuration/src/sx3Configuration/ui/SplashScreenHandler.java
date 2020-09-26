package sx3Configuration.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreenHandler extends Preloader implements Initializable {
	private Stage preloaderStage;

	@FXML
	ProgressBar progressbar;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.preloaderStage = primaryStage;

//		Parent root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));

		Image image = new Image("/resources/cypress-infi-logo-title.jpg");
		ImageView imageView = new ImageView(image);
		
		ProgressBar progressBar = new ProgressBar();
		

		VBox hbox = new VBox();
		hbox.setAlignment(Pos.TOP_CENTER);
		hbox.setSpacing(20);
		
		hbox.getChildren().add(imageView);
		hbox.getChildren().add(progressBar);

		Scene scene = new Scene(hbox, 600, 400);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
		if (stateChangeNotification.getType() == Type.BEFORE_START) {
			preloaderStage.hide();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}