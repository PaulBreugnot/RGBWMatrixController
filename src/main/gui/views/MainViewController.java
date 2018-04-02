package main.gui.views;

import java.io.IOException;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.core.model.animations.Animation;
import main.core.model.animations.circularWave.CircularWave;
import main.core.model.animations.diamondWave.DiamondWave;
import main.core.model.animations.fan.Fan;
import main.core.model.animations.loopingAnimations.LoopingAnimations;
import main.core.model.animations.pixelRain.PixelRain;
import main.core.model.animations.text.ScrollingText;
import main.core.model.panel.LedPanel;
import main.gui.views.loopingAnimations.EditLoopingAnimationsController;

public class MainViewController {

	@FXML
	private TilePane tilePane;

	@FXML
	private Button PlayButton;

	@FXML
	private Button FrameByFrameButton;

	@FXML
	private AnchorPane ConfigAnchorPane;

	@FXML
	private ListView<Animation> RandomListView;

	@FXML
	private ListView<Animation> GeometricListView;

	@FXML
	private ListView<Animation> TextListView;

	@FXML
	private ListView<Animation> SpecialListView;

	@FXML
	private ComboBox<String> ComPortComboBox;

	@FXML
	private Label ErrorLabel;
	
	@FXML
	private Tab AnimationsTab;
	private LoopingAnimations loopingAnimations;

	private ObservableList<String> ListComPort = FXCollections.observableArrayList();

	public static ObservableList<Animation> ListRandomEffects = FXCollections.observableArrayList();
	public static ObservableList<Animation> ListGeometricEffects = FXCollections.observableArrayList();
	public static ObservableList<Animation> ListTextEffects = FXCollections.observableArrayList();
	public static ObservableList<Animation> ListSpecialEffects = FXCollections.observableArrayList();

	private LedPanel ledPanel;
	private Rectangle[][] tilePaneContent = new Rectangle[LedPanel.MATRIX_HEIGHT][LedPanel.MATRIX_WIDTH];
	private boolean run;

	public void setLedPanel(LedPanel ledPanel) throws IOException {
		this.ledPanel = ledPanel;
		setListViews();
		initAnimationsTab();
		initTilePane();
		initComPort();
	}

	private void setListViews() {

		ListGeometricEffects.add(new PixelRain());
		ListGeometricEffects.add(new CircularWave());
		ListGeometricEffects.add(new DiamondWave());
		ListGeometricEffects.add(new Fan());

		ListTextEffects.add(new ScrollingText());

		ListSpecialEffects.add(new LoopingAnimations());

		RandomListView.setItems(ListRandomEffects);
		GeometricListView.setItems(ListGeometricEffects);
		TextListView.setItems(ListTextEffects);
		SpecialListView.setItems(ListSpecialEffects);

		RandomListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				GeometricListView.getSelectionModel().clearSelection();
				TextListView.getSelectionModel().clearSelection();
				SpecialListView.getSelectionModel().clearSelection();
				handleStop();
				try {
					setAnimation(newSelection);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		GeometricListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				RandomListView.getSelectionModel().clearSelection();
				TextListView.getSelectionModel().clearSelection();
				SpecialListView.getSelectionModel().clearSelection();
				handleStop();
				try {
					setAnimation(newSelection);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		TextListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				GeometricListView.getSelectionModel().clearSelection();
				RandomListView.getSelectionModel().clearSelection();
				SpecialListView.getSelectionModel().clearSelection();
				handleStop();
				try {
					setAnimation(newSelection);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		SpecialListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				GeometricListView.getSelectionModel().clearSelection();
				TextListView.getSelectionModel().clearSelection();
				RandomListView.getSelectionModel().clearSelection();
				handleStop();
				try {
					setAnimation(newSelection);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

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
	}

	private void initTilePane() {
		tilePane.setOrientation(Orientation.HORIZONTAL);
		tilePane.setPrefRows(LedPanel.MATRIX_HEIGHT);
		tilePane.setPrefColumns(LedPanel.MATRIX_WIDTH);
		tilePane.setPrefTileHeight(tilePane.getPrefHeight() / LedPanel.MATRIX_HEIGHT - 1);
		tilePane.setPrefTileWidth(tilePane.getPrefWidth() / LedPanel.MATRIX_WIDTH - 1);

		for (int i = LedPanel.MATRIX_HEIGHT - 1; i >= 0; i--) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				Rectangle pixel = new Rectangle(tilePane.getPrefWidth() / LedPanel.MATRIX_WIDTH,
						tilePane.getPrefHeight() / LedPanel.MATRIX_HEIGHT);
				pixel.setStroke(Color.BLACK);
				pixel.setFill(Color.WHITE);
				tilePaneContent[i][j] = pixel;
				tilePane.getChildren().add(pixel);
			}
		}
		System.out.println("Init TilePane OK");
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

	private void setAnimation(Animation animation) throws IOException {
		ledPanel.setCurrentAnimation(animation);
		ConfigAnchorPane.getChildren().clear();
		animation.setAnimationSettings(ConfigAnchorPane);
	}

	private void displayMatrix() {
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				tilePaneContent[i][j].setFill(ledPanel.getLedMatrix()[i][j].getDisplayColor());
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
