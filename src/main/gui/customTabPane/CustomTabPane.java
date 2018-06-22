package main.gui.customTabPane;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

public class CustomTabPane extends TabPane {

	private FXMLLoader fxmlLoader;
	private CustomTabPaneController customTabPaneController;

	public CustomTabPane() {
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource("/main/gui/customTabPane/CustomTabPane.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(new CustomTabPaneController());
			customTabPaneController = fxmlLoader.getController();
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public CustomTabPaneController getController() {
		return customTabPaneController;
	}

}
