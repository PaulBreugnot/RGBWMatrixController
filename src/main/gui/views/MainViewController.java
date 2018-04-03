package main.gui.views;

import java.io.IOException;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.core.model.animations.loopingAnimations.LoopingAnimations;
import main.core.model.panel.LedPanel;
import main.gui.views.loopingAnimations.EditLoopingAnimationsController;

public class MainViewController {

	@FXML
	private AnchorPane matrixAnchorPane;

	@FXML
	private Button PlayButton;

	@FXML
	private Button FrameByFrameButton;

	@FXML
	private AnchorPane ConfigAnchorPane;

	@FXML
	private ComboBox<String> ComPortComboBox;

	@FXML
	private Label ErrorLabel;

	@FXML
	private Tab AnimationsTab;
	private LoopingAnimations loopingAnimations;

	private ObservableList<String> ListComPort = FXCollections.observableArrayList();

	private LedPanel ledPanel;
	private Rectangle[][] matrixAnchorPaneContent = new Rectangle[LedPanel.MATRIX_HEIGHT][LedPanel.MATRIX_WIDTH];
	private boolean run;

	public void setLedPanel(LedPanel ledPanel) throws IOException {
		this.ledPanel = ledPanel;
		initAnimationsTab();
		initTilePane();
		initComPort();
	}

	public LedPanel getLedPanel() {
		return ledPanel;
	}

	private void initAnimationsTab() {
		BorderPane root;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				this.getClass().getResource("/main/gui/views/loopingAnimations/EditLoopingAnimationsView.fxml"));
		try {
			root = (BorderPane) loader.load();
			AnimationsTab.setContent(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		loopingAnimations = new LoopingAnimations();
		EditLoopingAnimationsController editLoopingAnimationController = loader.getController();
		editLoopingAnimationController.setLoopingAnimations(loopingAnimations);
		editLoopingAnimationController.setMainViewController(this);
	}

	private void initTilePane() {
		double tileWidth = matrixAnchorPane.getPrefWidth() / LedPanel.MATRIX_WIDTH;
		double tileHeight = matrixAnchorPane.getPrefHeight() / LedPanel.MATRIX_HEIGHT;
		for (int i = LedPanel.MATRIX_HEIGHT - 1; i >= 0; i--) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				Rectangle pixel = new Rectangle(tileWidth, tileHeight);
				pixel.setStroke(Color.BLACK);
				pixel.setFill(Color.WHITE);
				matrixAnchorPaneContent[i][j] = pixel;
				AnchorPane.setTopAnchor(pixel, (LedPanel.MATRIX_HEIGHT - 1 - i) * tileHeight);
				AnchorPane.setLeftAnchor(pixel, j * tileWidth);
				matrixAnchorPane.getChildren().add(pixel);
			}
		}
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

	private void displayMatrix() {
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				matrixAnchorPaneContent[i][j].setFill(ledPanel.getLedMatrix()[i][j].getDisplayColor());
			}
		}
	}

	@FXML
	private void handlePlay() {
		FrameByFrameButton.setDisable(true);
		run = true;
		GUIupdater updater = new GUIupdater(this);
		updater.start();
	}

	@FXML
	private void handleFrameByFrame() {
		ledPanel.updateDisplay();
		Platform.runLater(() -> displayMatrix());
		try {
			Thread.sleep(1000 / ledPanel.getFps());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleStop() {
		FrameByFrameButton.setDisable(false);
		run = false;
	}

	@FXML
	private void handleConnect() {
		String comName = ComPortComboBox.getSelectionModel().getSelectedItem();
		if (comName != null) {
			ledPanel.setConnection(comName);
			if (ledPanel.isConnected()) {
				ErrorLabel.setText("Connected to " + comName);
			} else {
				ErrorLabel.setText("Fail to connect " + comName);
			}
		} else {
			ErrorLabel.setText("No port selected");
		}
	}

	private class GUIupdater extends Thread {
		private MainViewController mainViewController;

		public GUIupdater(MainViewController mainViewController) {
			this.mainViewController = mainViewController;
		}

		@Override
		public void run() {
			ledPanel.updateDisplay();
			try {
				Thread.sleep(1000 / ledPanel.getFps());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Platform.runLater(() -> {
				displayMatrix();
				if (mainViewController.run) {
					run();
				}
			});

		}
	}

}
