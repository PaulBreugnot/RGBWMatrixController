package main.gui.views.connectionInterface.connectionModule;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.gui.views.connectionInterface.usb.UsbConnectionTab;

public class ConnectionModuleController {
	
	@FXML
	UsbConnectionTab usbConnectionTab;

	@FXML
	private void initialize() {
		ImageView icon = new ImageView(new Image(this.getClass().getResourceAsStream("/img/usb.png")));
		icon.setFitHeight(25);
		icon.setFitWidth(25);
		usbConnectionTab.setGraphic(icon);
	}
	
	public UsbConnectionTab getUsbConnectionTab() {
		return usbConnectionTab;
	}
}
