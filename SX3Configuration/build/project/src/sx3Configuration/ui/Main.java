package sx3Configuration.ui;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application { 
	@Override
	public void start(Stage primaryStage) { 
		try {
			SplitPane root = (SplitPane)FXMLLoader.load(getClass().getResource("SX3Configuration.fxml"));
//			root.setStyle("-fx-base: rgba(60, 60, 60, 255);");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("/resources/CyLogo_tree.png"));
			primaryStage.setTitle("Cypress EZ-USB SX3 Configuration Utility");
			primaryStage.setMaximized(true); 
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
