package main.gui.views.connectionInterface;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ConnectionModule extends AnchorPane {

	public ConnectionModule() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/gui/views/connectionInterface/ConnectionModule.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(new ConnectionModuleController());
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
