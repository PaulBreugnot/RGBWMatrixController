package main.gui.views;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.core.model.animations.Animation;
import main.core.model.animations.pixelRain.PixelRain;
import main.core.model.animations.randomEffects.RandomPop;
import main.core.model.animations.text.ScrollingText;
import main.core.model.panel.LedPanel;

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

	private ObservableList<Animation> ListRandomEffects = FXCollections.observableArrayList();
	private ObservableList<Animation> ListGeometricEffects = FXCollections.observableArrayList();
	private ObservableList<Animation> ListTextEffects = FXCollections.observableArrayList();
	private ObservableList<Animation> ListSpecialEffects = FXCollections.observableArrayList();

	private LedPanel ledPanel;
	private Rectangle[][] tilePaneContent = new Rectangle[LedPanel.MATRIX_HEIGHT][LedPanel.MATRIX_WIDTH];
	private boolean run;

	public void setLedPanel(LedPanel ledPanel) throws IOException {
		this.ledPanel = ledPanel;
		setListViews();
		initTilePane();
	}

	private void setListViews() {

		ListRandomEffects.add(new RandomPop());

		ListGeometricEffects.add(new PixelRain());

		ListTextEffects.add(new ScrollingText());

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

	private void setAnimation(Animation animation) throws IOException {
		ledPanel.setCurrentAnimation(animation);
		ConfigAnchorPane.getChildren().clear();
		animation.setAnimationSettings(ConfigAnchorPane, ledPanel);
	}

	private void displayMatrix() {
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				tilePaneContent[i][j].setFill(ledPanel.getLedMatrix()[i][j].getColor());
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
			// mainViewController.run = false;
			Platform.runLater(() -> {
				displayMatrix();
				if (mainViewController.run) {
					run();
				}
			});

		}
	}

}
