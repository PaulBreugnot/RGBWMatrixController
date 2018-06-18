package main.gui.views;

import java.io.IOException;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
	private ComboBox<String> ComPortComboBox;

	@FXML
	private Label ErrorLabel;

	@FXML
	private TabPane mainTabPane;

	@FXML
	private Tab AnimationsTab;

	@FXML
	private Spinner<Integer> WidthSpinner;

	@FXML
	private Spinner<Integer> HeightSpinner;

	private EditLoopingAnimationsController editLoopingAnimationController;

	private LoopingAnimations loopingAnimations;

	private ObservableList<String> ListComPort = FXCollections.observableArrayList();

	private LedPanel ledPanel;
	private Rectangle[][] matrixAnchorPaneContent;
	private boolean run;
	private GUIupdater updater;

	@FXML
	private void initialize() {
		initSizeSpinners();
	}

	public void setLedPanel(LedPanel ledPanel) throws IOException {
		this.ledPanel = ledPanel;
		initAnimationsTab();
		initTilePane();
		System.out.println("initTilePane OK");
		initComPort();
		if (!ledPanel.isConnected()) {
			//ledPanel.setWiFiConnection();
		}
		System.out.println("Connection method : " + ledPanel.getConMethod());
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
		editLoopingAnimationController = loader.getController();
		editLoopingAnimationController.setLoopingAnimations(loopingAnimations);
		editLoopingAnimationController.setMainViewController(this);

		mainTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
				if (t1 == AnimationsTab) {
					editLoopingAnimationController.setRunPreview(true);
				} else {
					editLoopingAnimationController.setRunPreview(false);
				}
			}
		});
	}

	private void initTilePane() {
		updater = new GUIupdater(this);
		matrixAnchorPane.getChildren().clear();
		matrixAnchorPaneContent = new Rectangle[LedPanel.MATRIX_HEIGHT][LedPanel.MATRIX_WIDTH];
		double tileSize;
		double xOffset;
		double yOffset;
		if (LedPanel.MATRIX_WIDTH <= 2 * LedPanel.MATRIX_HEIGHT) {
			tileSize = matrixAnchorPane.getPrefHeight() / LedPanel.MATRIX_HEIGHT;
			xOffset = matrixAnchorPane.getPrefWidth() / 2 - tileSize * LedPanel.MATRIX_WIDTH / 2;
			yOffset = 0;
		} else {
			tileSize = matrixAnchorPane.getPrefWidth() / LedPanel.MATRIX_WIDTH;
			xOffset = 0;
			yOffset = matrixAnchorPane.getPrefHeight() / 2 - tileSize * LedPanel.MATRIX_HEIGHT / 2;
		}
		for (int i = LedPanel.MATRIX_HEIGHT - 1; i >= 0; i--) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				Rectangle pixel = new Rectangle(tileSize, tileSize);
				pixel.setStroke(Color.BLACK);
				pixel.setFill(Color.WHITE);
				matrixAnchorPaneContent[i][j] = pixel;
				AnchorPane.setTopAnchor(pixel, yOffset + (LedPanel.MATRIX_HEIGHT - 1 - i) * tileSize);
				AnchorPane.setLeftAnchor(pixel, xOffset + j * tileSize);
				matrixAnchorPane.getChildren().add(pixel);
			}
		}
		updater.start();
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
			// handleConnect();
		}
	}

	public void initSizeSpinners() {
		WidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 128, LedPanel.MATRIX_WIDTH));
		WidthSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> v, Number oldVal, Number newVal) {
				run = false;
				Platform.runLater(() -> {
					try {
						setLedPanel(new LedPanel(ledPanel.getFps(), (int) newVal, LedPanel.MATRIX_HEIGHT));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		});

		HeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 128, 16));
		HeightSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> v, Number oldVal, Number newVal) {
				run = false;
				Platform.runLater(() -> {
					try {
						setLedPanel(new LedPanel(ledPanel.getFps(), LedPanel.MATRIX_WIDTH, (int) newVal));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		});
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
		updater.run();
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
