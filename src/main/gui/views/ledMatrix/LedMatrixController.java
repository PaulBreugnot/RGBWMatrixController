package main.gui.views.ledMatrix;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.core.model.panel.LedPanel;

public class LedMatrixController {
	
	@FXML
	private LedMatrix ledMatrix;
	
	private LedPanel ledPanel;
	private Rectangle[][] matrixAnchorPaneContent;
	
	private GUIupdater updater;
	private boolean run;
	
	
	public void setLedPanel(LedPanel ledPanel) {
		this.ledPanel = ledPanel;
	}
	
	public void setComponent(LedMatrix ledMatrix) {
		this.ledMatrix = ledMatrix;
	}
	
	public void initMatrix() {
		updater = new GUIupdater(this);
		ledMatrix.getChildren().clear();
		matrixAnchorPaneContent = new Rectangle[LedPanel.MATRIX_HEIGHT][LedPanel.MATRIX_WIDTH];
		double tileSize;
		double xOffset;
		double yOffset;
		if (LedPanel.MATRIX_WIDTH <= 2 * LedPanel.MATRIX_HEIGHT) {
			tileSize = ledMatrix.getPrefHeight() / LedPanel.MATRIX_HEIGHT;
			xOffset = ledMatrix.getPrefWidth() / 2 - tileSize * LedPanel.MATRIX_WIDTH / 2;
			yOffset = 0;
		} else {
			tileSize = ledMatrix.getPrefWidth() / LedPanel.MATRIX_WIDTH;
			xOffset = 0;
			yOffset = ledMatrix.getPrefHeight() / 2 - tileSize * LedPanel.MATRIX_HEIGHT / 2;
		}
		for (int i = LedPanel.MATRIX_HEIGHT - 1; i >= 0; i--) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				Rectangle pixel = new Rectangle(tileSize, tileSize);
				pixel.setStroke(Color.BLACK);
				pixel.setFill(Color.BLACK);
				matrixAnchorPaneContent[i][j] = pixel;
				AnchorPane.setTopAnchor(pixel, yOffset + (LedPanel.MATRIX_HEIGHT - 1 - i) * tileSize);
				AnchorPane.setLeftAnchor(pixel, xOffset + j * tileSize);
				ledMatrix.getChildren().add(pixel);
			}
		}
		updater.start();
	}

	public void displayMatrix() {
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				matrixAnchorPaneContent[i][j].setFill(ledPanel.getLedMatrix()[i][j].getDisplayColor());
			}
		}
	}
	
	public void runUpdater() {
		if(!run) {
			System.out.println("RUN");
			run = true;
			updater.run();
		}
	}
	
	public void stopUpdater() {
		System.out.println("STOP");
		run = false;
	}

	private class GUIupdater extends Thread {
		private LedMatrixController ledMatrixController;

		public GUIupdater(LedMatrixController ledMatrixController) {
			this.ledMatrixController = ledMatrixController;
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
				if (ledMatrixController.run) {
					run();
				}
			});

		}
	}
}
