package main.gui.views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.core.model.panel.LedPanel;

public class MainViewController {

	@FXML
	private TilePane tilePane;

	@FXML
	private Button PlayButton;

	@FXML
	private Button FrameByFrameButton;

	private LedPanel ledPanel;
	private Rectangle[][] tilePaneContent = new Rectangle[LedPanel.MATRIX_HEIGHT][LedPanel.MATRIX_WIDTH];
	private boolean run;

	public void setLedPanel(LedPanel ledPanel) {
		this.ledPanel = ledPanel;
		initTilePane();
	}

	private void initTilePane() {
		tilePane.setOrientation(Orientation.HORIZONTAL);
		tilePane.setPrefRows(LedPanel.MATRIX_HEIGHT);
		tilePane.setPrefColumns(LedPanel.MATRIX_WIDTH);
		tilePane.setPrefTileHeight(tilePane.getPrefHeight() / LedPanel.MATRIX_HEIGHT);
		tilePane.setPrefTileWidth(tilePane.getPrefWidth() / LedPanel.MATRIX_WIDTH);

		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
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
			Thread.sleep(1000);
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
				Thread.sleep(500);
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
