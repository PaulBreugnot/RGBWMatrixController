package main.core.model.animations.sinWave;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;
import main.gui.views.settings.sinWave.SinWaveSettingsController;

public class SinWave implements Animation {

	public static final String effectName = "Sinus Wave!";

	public enum Source {
		TOP, BOTTOM, LEFT, RIGHT
	};

	private Source source;
	private double hueColor = 0;
	private int whiteLevel = 0;
	private double waveLength = 50;
	private double contrast = 0.4;
	private int speed = 1;
	private int offset = 0;

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

	public void setContrast(double contrast) {
		this.contrast = contrast;
	}

	public double getContrast() {
		return contrast;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		displayWave(ledMatrix);
		offset += speed;
		// offset = (offset + speed) % (LedPanel.MATRIX_WIDTH - 1);

	}

	@Override
	public void setAnimationSettings(AnchorPane ConfigAnchorPane) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/main/gui/views/settings/sinWave/SinWaveSettings.fxml"));
		ConfigAnchorPane.getChildren().add(loader.load());
		SinWaveSettingsController sinWaveSettingsController = loader.getController();
		sinWaveSettingsController.setSinWave(this);
	}

	public void displayWave(RGBWPixel[][] ledMatrix) {
		for (int column = 0; column < LedPanel.MATRIX_WIDTH; column++) {
			double saturation = SinAmp(column);
			for (int line = 0; line < LedPanel.MATRIX_HEIGHT; line++) {
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
	public String toString() {
		return effectName;
	}

	@Override
	public Animation newAnimationInstance() {
		return new SinWave();
	}

}
