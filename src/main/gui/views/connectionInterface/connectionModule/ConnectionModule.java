package main.gui.views.connectionInterface.connectionModule;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

public class ConnectionModule extends TabPane {

	private FXMLLoader fxmlLoader;
	private ConnectionModuleController connectionModuleController;

	public ConnectionModule() {
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource("/main/gui/views/connectionInterface/connectionModule/ConnectionModule.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(new ConnectionModuleController());
			connectionModuleController = fxmlLoader.getController();
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public ConnectionModuleController getController() {
		return connectionModuleController;
	}
}
