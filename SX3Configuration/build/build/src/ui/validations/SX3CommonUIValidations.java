package ui.validations;

import java.util.Collections;

import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class SX3CommonUIValidations {
	
	private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};

	public static void showTexErrorBorder(TextField tf) {
		ObservableList<String> styleClass = tf.getStyleClass();
		if (!styleClass.contains("error")) {
			styleClass.add("error");
		}
	}

	public static void removeTextErrorBorder(TextField tf) {
		ObservableList<String> styleClass = tf.getStyleClass();
		// remove all occurrences:
		styleClass.removeAll(Collections.singleton("error"));
	}

	public static void showTabPaneErrorBorder(TextField tf) {
		ObservableList<String> styleClass = tf.getStyleClass();
		if (!styleClass.contains("error")) {
			styleClass.add("error");
		}
	}

	public static void removeTabPanetErrorBorder(TextField tf) {
		ObservableList<String> styleClass = tf.getStyleClass();
		// remove all occurrences:
		styleClass.removeAll(Collections.singleton("error"));
	}

	static void showErrorRichText(TextField vendorId, final ContextMenu contextRichText, String message) {
		contextRichText.getItems().clear();
		contextRichText.getItems().add(new MenuItem(message));
		contextRichText.show(vendorId, Side.RIGHT, 2, 0);
	}

	public static boolean isValidHex(String value) {
		try {
			// valid hex
			Integer.parseInt(value, 16);
			return true;
		} catch (NumberFormatException nfe) {
			// not a valid hex
		}
		return false;
	}

	public static boolean isValidAlphaNumeric(String newValue) {
		for (int i = 0; i < newValue.length(); i++) {
			char c = newValue.charAt(i);
			if (!Character.isDigit(c) && !Character.isLetter(c) && !Character.isSpaceChar(c)) 
				return false;
		}
		return true;
	}

	public static boolean isValidNumeric(String newValue) {
		for (char c : newValue.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public static boolean isValidFileName(String newValue) {
		char[] copy = newValue.toCharArray();
		for(int i=0;i<copy.length;i++) {
			for(int j=0;j<ILLEGAL_CHARACTERS.length;j++) {
				if(copy[i] == ILLEGAL_CHARACTERS[j]) {
					return false;
				}
			}
		}
		return true;
	}

}
