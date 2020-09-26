package sx3configuration.tests;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

import javafx.application.Platform;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import sx3Configuration.model.SX3Configuration;
import sx3Configuration.programming.OSValidator;
import sx3Configuration.programming.OSValidator.Level;

public class TestFXBase extends ApplicationTest {
	protected Stage stage;

	protected SX3Configuration sx3Obj;
	protected String PREDEFINED_CONFIGURATION_PATH;
	protected String newConfigurationFileName = new String();
	protected String newConfigurationFilePath = new String();
	//tamil starts
	protected String PREDEFINED_IMPORT_FROM_TYPE;
	protected String PREDEFINED_IMPORT_TO;
	protected String LOADCONFIG_SELECTION;
	protected String PREDEFINED_IMPORT_JSON;
	protected String OPERATING_SYSTEM;
	//tamil ends


	@Before
	public void setUpclass() throws Exception {

		FileReader reader = new FileReader("testdata.properties");
		Properties p = new Properties();
		p.load(reader);

		PREDEFINED_CONFIGURATION_PATH = p.getProperty("configured_json");

		//tamil starts
		PREDEFINED_IMPORT_FROM_TYPE=p.getProperty("import_from_type");
		PREDEFINED_IMPORT_TO=p.getProperty("import_to");
		LOADCONFIG_SELECTION=p.getProperty("load_config_selection");
		PREDEFINED_IMPORT_JSON=p.getProperty("import_from_type_json");
		OPERATING_SYSTEM=p.getProperty("operating_system");
		//tamil ends
		Gson gson = new Gson();
		JsonReader jsonReader = new JsonReader(new FileReader(PREDEFINED_CONFIGURATION_PATH));
		sx3Obj = gson.fromJson(jsonReader, SX3Configuration.class);

		File loadedConfigurationPath = new File(PREDEFINED_CONFIGURATION_PATH);
		newConfigurationFilePath = loadedConfigurationPath.getParent();
		newConfigurationFileName = "toolgenerated_"
				+ loadedConfigurationPath.getName().substring(0, loadedConfigurationPath.getName().lastIndexOf("."));

		System.out.println(newConfigurationFileName);
		System.out.println(newConfigurationFilePath);

		File file = new File(newConfigurationFilePath + "/" + newConfigurationFileName);
		/*if (file.exists() && file.isDirectory()) {
			deleteDirectory(file);
		}*/

		ApplicationTest.launch(sx3Configuration.ui.Main.class);
	}

	boolean deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file);
			}
		}
		return directoryToBeDeleted.delete();
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		stage.show();

	}

	public Stage getStage() {
		return stage;
	}

	@After
	public void afterEachTest() throws TimeoutException, java.util.concurrent.TimeoutException {
		FxToolkit.hideStage();
		release(new KeyCode[] {});
		release(new MouseButton[] {});
	}

	public void copyAndPaste(String query, String string) {
		clickOn(query);
		copy(string);
		paste();
	}

	private void copy(String string) {

		Platform.runLater(() -> {
			final Clipboard clipboard = Clipboard.getSystemClipboard();
			final ClipboardContent content = new ClipboardContent();
			content.putString(string);
			clipboard.setContent(content);
		});

	}

	private void paste() {
		Platform.runLater(() -> {
			OSValidator osValidator = new OSValidator();
			Level os = osValidator.getOS();
			System.out.println(os);
			switch (os) {
			case MAC:
				push(KeyCode.COMMAND, KeyCode.V);
				break;
			default:
				push(KeyCode.CONTROL, KeyCode.V);
				break;
			}
		});
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	
}