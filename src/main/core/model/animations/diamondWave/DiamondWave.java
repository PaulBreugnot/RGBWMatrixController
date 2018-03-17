package main.core.model.animations.diamondWave;

import java.io.IOException;

import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.animations.circularWave.CircularWave.WaveMode;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class DiamondWave implements Animation {

	private WaveMode waveMode = WaveMode.SATURATION;
	private double hueColor = 0;
	private int whiteLevel = 0;
	private double brightness = 1;
	private double waveLength = 8;
	private double contrast = 0.4;
	private int speed = 1;
	private int frame = 0;

	private int a0 = 5;
	private int b0 = 5;
	private int xCenter = 16;
	private int yCenter = 8;
	int diamondNum = 10;

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
		displayWave(ledMatrix);
		frame++;
		// TODO Auto-generated method stub

	}

	public void displayWave(RGBWPixel[][] ledMatrix) {
		for (int offset = 0; offset < diamondNum; offset++) {
			for (int x = -(offset + (speed * frame)); x <= offset + (speed * frame); x++) {
				int y = (int) Math.floor(y(x, offset));
				if ((xCenter - x) >= 0 && (xCenter - x) < LedPanel.MATRIX_WIDTH) {
					if ((y + yCenter) >= 0 && (y + yCenter) < LedPanel.MATRIX_HEIGHT) {
						ledMatrix[y + yCenter][xCenter - x] = RGBWPixel.hsbwPixel(color(offset), 1, brightness * 1,
								whiteLevel);
					}
					if ((-y + yCenter) >= 0 && (-y + yCenter) < LedPanel.MATRIX_HEIGHT) {
						ledMatrix[-y + yCenter][xCenter - x] = RGBWPixel.hsbwPixel(color(offset), 1, brightness * 1,
								whiteLevel);
					}
				}
			}
		}
		// diamondNum++;
	}

	public double y(int x, int offset) {
		double b = ((speed * frame) + offset) % diamondNum;
		double a = b * a0 / b0;
		return Math.abs((x * a / b)) - a;
	}

	public double color(int offset) {
		// return 360 * Math.sin(2 * Math.PI / waveLength * (offset - frame * speed));
		return (offset * 10) % 360;
	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane, LedPanel ledPanel) throws IOException {
		// TODO Auto-generated method stub

	}

}
