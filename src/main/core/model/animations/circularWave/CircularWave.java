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

	public enum WaveMode {
		SATURATION, BRIGHTNESS, RAINBOW
	}

	private WaveMode waveMode = WaveMode.SATURATION;
	private double hueColor = 0;
	private int whiteLevel = 0;
	private int xCenter = 16;
	private int yCenter = 8;
	private double intensity = 1;
	private double waveLength = 8;
	private double contrast = 0.4;
	private double speed = 1;
	private double offset = 0;

	public void setHueColor(double hueColor) {
		this.hueColor = hueColor;
	}

	public void setWhiteLevel(int whiteLevel) {
		this.whiteLevel = whiteLevel;
	}

	public void setWaveLength(double waveLength) {
		this.waveLength = waveLength;
	}

	public void setXCenter(int xCenter) {
		this.xCenter = xCenter;
	}

	public void setYCenter(int yCenter) {
		this.yCenter = yCenter;
	}

	public void setContrast(double contrast) {
		this.contrast = contrast;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}

	public void setWaveMode(WaveMode waveMode) {
		this.waveMode = waveMode;
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
						.floor(Math.sqrt(Math.pow(column - xCenter, 2) + Math.pow(line - yCenter, 2)));
				RGBWPixel pixel = RGBWPixel.BLACK_PIXEL;
				switch (waveMode) {
				case SATURATION:
					double saturation = SinAmp(distanceToCenter);
					pixel = RGBWPixel.hsbwPixel(hueColor, intensity, saturation * intensity, whiteLevel);
					break;
				case BRIGHTNESS:
					double brightness = SinAmp(distanceToCenter);
					pixel = RGBWPixel.hsbwPixel(hueColor, intensity * brightness, intensity, whiteLevel);
					break;
				case RAINBOW:
					double hue = SinAmp(distanceToCenter) * 360;
					pixel = RGBWPixel.hsbwPixel(hue, intensity, 1, whiteLevel);
					break;
				}
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
