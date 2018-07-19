package main.gui.views.connectionInterface;

import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import main.core.model.panel.LedPanel;

public class ConnectionModuleController {

	@FXML
	private ComboBox<String> ComPortComboBox;

	@FXML
	private Label ErrorLabel;
	
	private LedPanel ledPanel;

	private ObservableList<String> ListComPort = FXCollections.observableArrayList();
	
	public void setLedPanel(LedPanel ledPanel) {
		this.ledPanel = ledPanel;
	}
	
	public void initComPort() {
		ListComPort.clear();
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			ListComPort.add(((CommPortIdentifier) portList.nextElement()).getName());
		}
		ComPortComboBox.setItems(ListComPort);
		if (ListComPort.size() == 1) {
			ComPortComboBox.getSelectionModel().selectFirst();
			handleConnect();
		}
	}
	
	@FXML
	private void handleConnect() {
		String comName = ComPortComboBox.getSelectionModel().getSelectedItem();
		if (comName != null) {
			ledPanel.setUSBConnection(comName);
			if (ledPanel.isConnected()) {
				ErrorLabel.setText("Connected to " + comName);
			} else {
				ErrorLabel.setText("Fail to connect " + comName);
			}
		} else {
			ErrorLabel.setText("No port selected");
		}
	}
}
