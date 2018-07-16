package main.gui.views.ledMatrix;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

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
	private boolean run = false;

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
		for (int i = LedPanel.MATRIX_HEIGHT - 1; i >= 0; i--) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				Rectangle pixel = new Rectangle();
				pixel.setStroke(Color.BLACK);
				pixel.setFill(Color.BLACK);
				matrixAnchorPaneContent[i][j] = pixel;
				ledMatrix.getChildren().add(pixel);
			}
		}
		updater.start();
	}

	public void renderMatrix(double width, double height) {
		double tileSize;
		double xOffset;
		double yOffset;
		if (LedPanel.MATRIX_WIDTH <= 2 * LedPanel.MATRIX_HEIGHT) {
			if (width * LedPanel.MATRIX_HEIGHT >= LedPanel.MATRIX_WIDTH * height) {
				tileSize = height / LedPanel.MATRIX_HEIGHT;
				xOffset = width / 2 - tileSize * LedPanel.MATRIX_WIDTH / 2;
				yOffset = 0;
			} else {
				tileSize = width / LedPanel.MATRIX_WIDTH;
				xOffset = 0;
				yOffset = height / 2 - tileSize * LedPanel.MATRIX_HEIGHT / 2;
			}
		} else {
			if (LedPanel.MATRIX_WIDTH * height >= width * LedPanel.MATRIX_HEIGHT) {
				tileSize = width / LedPanel.MATRIX_WIDTH;
				xOffset = 0;
				yOffset = height / 2 - tileSize * LedPanel.MATRIX_HEIGHT / 2;
			} else {
				tileSize = height / LedPanel.MATRIX_HEIGHT;
				xOffset = width / 2 - tileSize * LedPanel.MATRIX_WIDTH / 2;
				yOffset = 0;
			}
		}

		for (int i = LedPanel.MATRIX_HEIGHT - 1; i >= 0; i--) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				Rectangle pixel = matrixAnchorPaneContent[i][j];
				pixel.setHeight(tileSize);
				pixel.setWidth(tileSize);
				AnchorPane.setTopAnchor(pixel, yOffset + (LedPanel.MATRIX_HEIGHT - 1 - i) * tileSize);
				AnchorPane.setBottomAnchor(pixel,
						height - yOffset + (LedPanel.MATRIX_HEIGHT - 1 - i) * tileSize - tileSize);
				AnchorPane.setLeftAnchor(pixel, xOffset + j * tileSize);
				AnchorPane.setRightAnchor(pixel, width - xOffset + j * tileSize - tileSize);
			}
		}

	}

	public void displayMatrix() {
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				matrixAnchorPaneContent[i][j].setFill(ledPanel.getLedMatrix()[i][j].getDisplayColor());
			}
		}
	}

	public void runUpdater() {
		if (!run) {
			run = true;
			updater.run();
		}
	}

	public void stopUpdater() {
		run = false;
	}

	private class GUIupdater extends Thread {
		private LedMatrixController ledMatrixController;

		public GUIupdater(LedMatrixController ledMatrixController) {
			this.ledMatrixController = ledMatrixController;
		}

		@Override
		public void run() {
			Instant end = Instant.now().plus(1000 / ledPanel.getFps(), ChronoUnit.MILLIS);
			if (ledMatrixController.run) {
				ledPanel.updateDisplay();
				long delay = Duration.between(Instant.now(), end).toMillis();
				if(delay>0) {
				try {
						Thread.sleep(delay);
					}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
				else {
					System.out.println("Overflow");
				}
				Platform.runLater(() -> {
					displayMatrix();
					run();
				});
			}
		}
	}
}
