package main.core.model.animations.diamondWave;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
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
	private double speed = 1;
	private int frame = 0;
	boolean clearPanel = false;
	private double displayProgress = 0;

	private double ratio = 1;
	private int xCenter = 8;
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
		frame++;
	}

	public void displayWave(RGBWPixel[][] ledMatrix) {
		HashSet<Diamond> diamondsToRemove = new HashSet<>();
		for (Diamond diamond : diamonds) {
			diamond.progress(speed);
			HashMap<Coordinates, RGBWPixel> pixels = diamond.getPixels();
			if (pixels.keySet().size() == 0) {
				diamondsToRemove.add(diamond);
			} else {
				for (Coordinates coordinates : pixels.keySet()) {
					ledMatrix[coordinates.getValue()][coordinates.getKey()] = pixels.get(coordinates);
				}
			}
		}

	}

	private void popDiamond(RGBWPixel[][] ledMatrix) {
		Color color = Color.hsb(SinAmp() * 360, 1, brightness);
		diamonds.add(new Diamond(color, whiteLevel, ratio, xCenter, yCenter));
		ledMatrix[yCenter][xCenter] = new RGBWPixel(color, whiteLevel);
	}

	public double color(int offset) {
		// return 360 * Math.sin(2 * Math.PI / waveLength * (offset - frame * speed));
		return (offset * 10) % 360;
	}

	private double SinAmp() {
		double pi = Math.PI;
		return (1 - contrast) / 2 + 0.5 * (1 + contrast * Math.sin(2 * pi * frame / waveLength));
	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane, LedPanel ledPanel) throws IOException {
		// TODO Auto-generated method stub

	}

}
