package main.core.model.animations.circularWave;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;
import main.gui.views.settings.CircularWaveSettingsController;

public class CircularWave implements Animation {

	public static final String effectName = "Circular Wave!";

	private double hueColor = 0;
	private int whiteLevel = 0;
	private double waveLength = 8;
	private double contrast = 0.4;
	private int speed = 1;
	private int offset = 0;

	public void setHueColor(double hueColor) {
		this.hueColor = hueColor;
	}

	public void setWhiteLevel(int whiteLevel) {
		this.whiteLevel = whiteLevel;
	}

	public void setWaveLength(double waveLength) {
		this.waveLength = waveLength;
	}

	public void setContrast(double contrast) {
		this.contrast = contrast;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		displayWave(ledMatrix);
		offset += speed;
		// offset = (offset + speed) % (LedPanel.MATRIX_WIDTH - 1);

	}

	public void displayWave(RGBWPixel[][] ledMatrix) {
		for (int column = 0; column < LedPanel.MATRIX_WIDTH; column++) {
			for (int line = 0; line < LedPanel.MATRIX_HEIGHT; line++) {
				int distanceToCenter = (int) Math
						.floor(Math.sqrt(Math.pow(column - (int) Math.floor(LedPanel.MATRIX_WIDTH / 2), 2)
								+ Math.pow(line - (int) Math.floor(LedPanel.MATRIX_HEIGHT / 2), 2)));
				double saturation = SinAmp(distanceToCenter);
				RGBWPixel pixel = RGBWPixel.hsbwPixel(hueColor, 1, saturation, whiteLevel);
				ledMatrix[line][column] = pixel;
			}
		}

	}

	private double SinAmp(int column) {
		double pi = Math.PI;
		return (1 - contrast) / 2 + 0.5 * (1 + contrast * Math.sin(2 * pi / waveLength * (column - offset)));
	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane, LedPanel ledPanel) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/main/gui/views/settings/CircularWaveSettings.fxml"));
		configAnchorPane.getChildren().add(loader.load());
		CircularWaveSettingsController circularWaveSettingsController = loader.getController();
		circularWaveSettingsController.setCircularWave((CircularWave) ledPanel.getCurrentAnimation());
	}

	@Override
	public String toString() {
		return effectName;
	}
}