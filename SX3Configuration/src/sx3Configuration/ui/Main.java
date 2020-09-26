package sx3Configuration.ui;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	private Scene scene;
	
	@Override
	public void init() throws Exception {
		super.init();
		Thread.sleep(5000);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			ConfigurationController configurationController = new ConfigurationController(this);
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setController(configurationController);
			fxmlLoader.setLocation(getClass().getResource("fxmls/SX3Configuration.fxml"));
			SplitPane root = (SplitPane) fxmlLoader.load();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("fxmls/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("/resources/CyLogo_tree.png"));
			primaryStage.setTitle("Cypress EZ-USB SX3 Configuration Utility V1.0");
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		LauncherImpl.launchApplication(Main.class, SplashScreenHandler.class,args);
	}

	protected Scene getScene() {
		return scene;

	}

}
