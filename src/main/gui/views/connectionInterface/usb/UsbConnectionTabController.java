package main.gui.views.connectionInterface.usb;

import gnu.io.CommPortIdentifier;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.core.model.panel.LedPanel;

public class UsbConnectionTabController {

	@FXML
	private ComboBox<CommPortIdentifier> ComPortComboBox;

	@FXML
	private Label ErrorLabel;

	@FXML
	private Button RefreshButton;

	private LedPanel ledPanel;

	@FXML
	private void initialize() {
		ImageView icon = new ImageView(new Image(this.getClass().getResourceAsStream("/img/refresh.png")));
		icon.setFitHeight(20);
		icon.setFitWidth(20);
		RefreshButton.setGraphic(icon);

	}

	public void setLedPanel(LedPanel ledPanel) {
		this.ledPanel = ledPanel;
	}

	public void initComPort() {
		ledPanel.initUsbConnector();
		ComPortComboBox.setItems(
				FXCollections.observableArrayList(ledPanel.getUsbConnector().getConnectionChecker().getPortList()));

		SimpleObjectProperty<CommPortIdentifier> commPortProperty = new SimpleObjectProperty<>(
				ledPanel.getUsbConnector().getConnectedPort());
		commPortProperty.addListener(new ChangeListener<CommPortIdentifier>() {
			@Override
			public void changed(ObservableValue<? extends CommPortIdentifier> v, CommPortIdentifier oldVal,
					CommPortIdentifier newVal) {
				if (newVal != null) {
					ComPortComboBox.getSelectionModel().select(newVal);
					ErrorLabel.setText("Connected to " + newVal.getName());
				}
				else {
					ComPortComboBox.getSelectionModel().clearSelection();
					ErrorLabel.setText("Unconnected");
				}
			}
		});
	}

	@FXML
	private void handleConnect() {
		ledPanel.getUsbConnector().tryToConnect(ComPortComboBox.getSelectionModel().getSelectedItem().getName());
		/*
		 * String comName =
		 * ComPortComboBox.getSelectionModel().getSelectedItem().getName(); if (comName
		 * != null) { ledPanel.getUsbConnector().tryToConnect(comName); if
		 * (ledPanel.isConnected()) { ErrorLabel.setText("Connected to " + comName); }
		 * else { ErrorLabel.setText("Fail to connect " + comName); } } else {
		 * ErrorLabel.setText("No port selected"); }
		 */
	}
}
