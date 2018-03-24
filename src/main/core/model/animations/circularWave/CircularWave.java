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
	private double intensity = LedPanel.MAX_INTENSITY;
	private double waveLength = 50;
	private double contrast = 1;
	private double speed = 1;
	private double offset = 0;

	public void setHueColor(double hueColor) {
		this.hueColor = hueColor;
	}

	public double getHueColor() {
		return hueColor;
	}

	public void setWhiteLevel(int whiteLevel) {
		this.whiteLevel = whiteLevel;
	}

	public int getWhiteLevel() {
		return whiteLevel;
	}

	public void setWaveLength(double waveLength) {
		this.waveLength = waveLength;
	}

	public double getWaveLength() {
		return waveLength;
	}

	public void setXCenter(int xCenter) {
		this.xCenter = xCenter;
	}

	public int getxCenter() {
		return xCenter;
	}

	public void setYCenter(int yCenter) {
		this.yCenter = yCenter;
	}

	public int getyCenter() {
		return yCenter;
	}

	public void setContrast(double contrast) {
		this.contrast = contrast;
	}

	public double getContrast() {
		return contrast;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getSpeed() {
		return speed;
	}

	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}

	public double getIntensity() {
		return intensity;
	}

	public void setWaveMode(WaveMode waveMode) {
		this.waveMode = waveMode;
	}

	public WaveMode getWaveMode() {
		return waveMode;
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
					pixel = RGBWPixel.hsbwPixel(hueColor, saturation, intensity, whiteLevel);
					break;
				case BRIGHTNESS:
					double brightness = SinAmp(distanceToCenter);
					pixel = RGBWPixel.hsbwPixel(hueColor, 1, intensity * brightness, whiteLevel);
					break;
				case RAINBOW:
					double hue = SinAmp(distanceToCenter) * 360;
					pixel = RGBWPixel.hsbwPixel(hue, 1, intensity, whiteLevel);
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
	public void setAnimationSettings(AnchorPane configAnchorPane) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/main/gui/views/settings/CircularWaveSettings.fxml"));
		configAnchorPane.getChildren().add(loader.load());
		CircularWaveSettingsController circularWaveSettingsController = loader.getController();
		circularWaveSettingsController.setCircularWave(this);
	}

	@Override
	public String toString() {
		return effectName;
	}
}
