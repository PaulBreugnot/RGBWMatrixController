package main.core.model.animations.diamondWave;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeSet;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import main.core.model.animations.Animation;
import main.core.model.animations.circularWave.CircularWave.WaveMode;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;
import main.core.util.Coordinates;

public class DiamondWave implements Animation {

	private WaveMode waveMode = WaveMode.SATURATION;
	private double hueColor = 0;
	private int whiteLevel = 0;
	private double brightness = 1;
	private double waveLength = 50;
	private double contrast = 1;
	private double speed = 2.7;
	boolean clearPanel = false;
	private double displayProgress = 0;
	private double sinProgress;
	private int frame;

	private double ratio = 1;
	private int xCenter = 16;
	private int yCenter = 8;
	int diamondNum = 10;

	private TreeSet<Diamond> diamonds = new TreeSet<>();

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

	public void setBrightness(double brightness) {
		this.brightness = brightness;
	}

	public void setWaveMode(WaveMode waveMode) {
		this.waveMode = waveMode;
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
		System.out.println("Diamonds size : " + diamonds.size());
		TreeSet<Diamond> newDiamonds = new TreeSet<>();
		for (Diamond diamond : diamonds) {
			int steps = diamond.progress(speed);
			// add diamonds if necessary
			System.out.println("progress : " + diamond.getProgress());
			if (diamond.getProgress() == speed) {
				for (int i = 1; i < steps; i++) {
					Color color = Color.hsb(SinAmp(sinProgress - speed + i * speed / steps) * 360, 1, brightness);
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
		System.out.println("Diamonds to remove : " + diamondsToRemove);
		for (int i = 0; i < diamondsToRemove; i++) {
			diamonds.remove(diamonds.first());
		}
	}

	private void popDiamond(RGBWPixel[][] ledMatrix) {
		Color color = Color.hsb(SinAmp(sinProgress) * 360, 1, brightness);
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
	public void setAnimationSettings(AnchorPane configAnchorPane, LedPanel ledPanel) throws IOException {
		// TODO Auto-generated method stub

	}

}
