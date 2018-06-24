package main.gui.views.connectionInterface.usb;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;

public class UsbConnectionTab extends Tab {
	
	private FXMLLoader fxmlLoader;
	private UsbConnectionTabController connectionModuleController;

	public UsbConnectionTab() {
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource("/main/gui/views/connectionInterface/usb/UsbConnectionTab.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(new UsbConnectionTabController());
			connectionModuleController = fxmlLoader.getController();
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public UsbConnectionTabController getController() {
		return connectionModuleController;
	}

}
