package main.gui.views;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.core.model.animations.pixelRain.PixelRain;
import main.core.model.panel.LedPanel;
import main.gui.views.settings.RainPixelSettingsController;

public class MainViewController {

	@FXML
	private TilePane tilePane;

	@FXML
	private Button PlayButton;

	@FXML
	private Button FrameByFrameButton;

	@FXML
	private AnchorPane ConfigAnchorPane;

	private LedPanel ledPanel;
	private Rectangle[][] tilePaneContent = new Rectangle[LedPanel.MATRIX_HEIGHT][LedPanel.MATRIX_WIDTH];
	private boolean run;

	public void setLedPanel(LedPanel ledPanel) throws IOException {
		this.ledPanel = ledPanel;
		initTilePane();
		setAnimationSettings();
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

	private void setAnimationSettings() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/main/gui/views/settings/RainPixelSettings.fxml"));
		ConfigAnchorPane.getChildren().add(loader.load());
		RainPixelSettingsController rainPixelSettingsController = loader.getController();
		rainPixelSettingsController.setPixelRain((PixelRain) ledPanel.getCurrentAnimation());
	}

	private void displayMatrix() {
		System.out.println("DisplayingMatrix");
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				System.out.print(".");
				tilePaneContent[i][j].setFill(ledPanel.getLedMatrix()[i][j].getColor());
			}
		}
		System.out.println("");
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
