package main.gui.views.connectionInterface.usb;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class UsbConnectionModule extends VBox {
	
	private FXMLLoader fxmlLoader;
	private UsbConnectionModuleController connectionModuleController;

	public UsbConnectionModule() {
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource("/main/gui/views/connectionInterface/usb/UsbConnectionModule.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(new UsbConnectionModuleController());
			connectionModuleController = fxmlLoader.getController();
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public UsbConnectionModuleController getController() {
		return connectionModuleController;
	}

}
