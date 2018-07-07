package main.core.model.animations.diamondWave;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeSet;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;
import main.core.util.Coordinates;
import main.gui.views.settings.diamondWave.DiamondWaveSettingsController;

public class DiamondWave implements Animation {

	public static final String effectName = "Diamond Wave!";

	public enum WaveMode {
		SATURATION, BRIGHTNESS, RAINBOW
	}

	private WaveMode waveMode = WaveMode.SATURATION;
	private double hueColor = 0;
	private int whiteLevel = 0;
	private double intensity = LedPanel.MAX_INTENSITY;
	private double waveLength = 50;
	private double contrast = 1;
	private double speed = 1;
	boolean clearPanel = false;
	private double displayProgress = 0;
	private double sinProgress;

	private double ratio = 1;
	private int xCenter = (LedPanel.MATRIX_WIDTH - 1) / 2;
	private int yCenter = (LedPanel.MATRIX_HEIGHT - 1) / 2;

	private TreeSet<Diamond> diamonds = new TreeSet<>();

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

	public int getXCenter() {
		return xCenter;
	}

	public void setYCenter(int yCenter) {
		this.yCenter = yCenter;
	}

	public int getYCenter() {
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
		if (clearPanel) {
			LedPanel.setBlackPanel(ledMatrix);
			clearPanel = false;
		}
		if (speed <= 1) {
			displayWave(ledMatrix);
			if (displayProgress >= 1) {
				displayProgress -= Math.floor(displayProgress);
				popDiamond(ledMatrix);
			}
			displayProgress += speed;
		} else {
			displayWave(ledMatrix);
			popDiamond(ledMatrix);
		}
		sinProgress += speed;
	}

	public void displayWave(RGBWPixel[][] ledMatrix) {
		int diamondsToRemove = 0;
		TreeSet<Diamond> newDiamonds = new TreeSet<>();
		for (Diamond diamond : diamonds) {
			int steps = diamond.progress(speed);
			// add diamonds if necessary
			if (diamond.getProgress() == speed) {
				for (int i = 1; i < steps; i++) {
					double hue = hueColor;
					double saturation = 1;
					double brightness = intensity;
					switch (waveMode) {
					case SATURATION:
						saturation = SinAmp(sinProgress - speed + i * speed / steps);
						break;
					case BRIGHTNESS:
						brightness = SinAmp(sinProgress - speed + i * speed / steps) * intensity;
						break;
					case RAINBOW:
						hue = SinAmp(sinProgress - speed + i * speed / steps) * 360;
						break;
					}
					Color color = Color.hsb(hue, saturation, brightness);
					Diamond newDiamond = new Diamond(color, whiteLevel, ratio, xCenter, yCenter, diamond.getWidth() - i,
							(int) Math.floor((diamond.getWidth() - i) * ratio), diamond.getProgress() - i);
					newDiamonds.add(newDiamond);
				}
			}
			newDiamonds.add(diamond);
		}
		diamonds.clear();
		diamonds.addAll(newDiamonds);
		for (Diamond diamond : diamonds) {
			HashMap<Coordinates, RGBWPixel> pixels = diamond.getPixels();
			if (pixels.size() == 0) {
				diamondsToRemove++;
			} else {
				for (Coordinates coordinates : pixels.keySet()) {
					ledMatrix[coordinates.getValue()][coordinates.getKey()] = pixels.get(coordinates);
				}
			}
		}
		for (int i = 0; i < diamondsToRemove; i++) {
			diamonds.remove(diamonds.first());
		}
	}

	private void popDiamond(RGBWPixel[][] ledMatrix) {
		double hue = hueColor;
		double saturation = 1;
		double brightness = intensity;
		switch (waveMode) {
		case SATURATION:
			saturation = SinAmp(sinProgress);
			break;
		case BRIGHTNESS:
			brightness = SinAmp(sinProgress) * intensity;
			break;
		case RAINBOW:
			hue = SinAmp(sinProgress) * 360;
			break;
		}
		Color color = Color.hsb(hue, saturation, brightness);
		diamonds.add(new Diamond(color, whiteLevel, ratio, xCenter, yCenter));
		ledMatrix[yCenter][xCenter] = new RGBWPixel(color, whiteLevel);
	}

	public double color(int offset) {
		// return 360 * Math.sin(2 * Math.PI / waveLength * (offset - frame * speed));
		return (offset * 10) % 360;
	}

	private double SinAmp(double x) {
		double pi = Math.PI;
		return (1 - contrast) / 2 + 0.5 * (1 + contrast * Math.sin(2 * pi * x / waveLength));
	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/main/gui/views/settings/diamondWave/DiamondWaveSettings.fxml"));
		configAnchorPane.getChildren().add(loader.load());
		DiamondWaveSettingsController diamondWaveSettingsController = loader.getController();
		diamondWaveSettingsController.setDiamondWave(this);
	}

	@Override
	public String toString() {
		return effectName;
	}

	@Override
	public Animation newAnimationInstance() {
		return new DiamondWave();
	}
}
