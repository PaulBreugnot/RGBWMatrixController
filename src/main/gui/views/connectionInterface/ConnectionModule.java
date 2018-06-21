package main.gui.views.connectionInterface;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class ConnectionModule extends VBox {
	
	private FXMLLoader fxmlLoader;
	private ConnectionModuleController connectionModuleController;

	public ConnectionModule() {
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource("/main/gui/views/connectionInterface/ConnectionModule.fxml"));
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
