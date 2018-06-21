package main.gui.views.playBar;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.core.model.panel.LedPanel;
import main.gui.views.ledMatrix.LedMatrix;

public class PlayBarController {

	@FXML
	private Button PlayButton;

	@FXML
	private Button FrameByFrameButton;
	
	private LedPanel ledPanel; //Core item
	private LedMatrix ledMatrix; //Graphic item
	
	public void setLedPanel(LedPanel ledPanel) {
		this.ledPanel = ledPanel;
	}
	
	public void setLedMatrix(LedMatrix ledMatrix) {
		this.ledMatrix = ledMatrix;
	}

	@FXML
	private void handlePlay() {
		FrameByFrameButton.setDisable(true);
		ledMatrix.getController().runUpdater();
	}

	@FXML
	private void handleFrameByFrame() {
		ledPanel.updateDisplay();
		Platform.runLater(() -> ledMatrix.getController().displayMatrix());
		try {
			Thread.sleep(1000 / ledPanel.getFps());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleStop() {
		FrameByFrameButton.setDisable(false);
		ledMatrix.getController().stopUpdater();
	}
}
