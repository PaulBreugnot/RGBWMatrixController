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

	private int a0;
	private int b0;
	private int xCenter;
	private int yCenter;
	int diamondNum = (LedPanel.MATRIX_WIDTH - xCenter) + b0;

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
			for (int x = -offset; x <= offset; x++) {
				int y = (int) Math.floor(y(x, offset));
				if ((x - xCenter) >= 0 && (x - xCenter) < LedPanel.MATRIX_WIDTH) {
					if ((y - yCenter) >= 0) {
						ledMatrix[x - xCenter][y - yCenter] = RGBWPixel.hsbwPixel(color(offset), 1, brightness * 1,
								whiteLevel);
					}
					if ((y - yCenter) < LedPanel.MATRIX_HEIGHT) {
						ledMatrix[x - xCenter][-y + yCenter] = RGBWPixel.hsbwPixel(color(offset), 1, brightness * 1,
								whiteLevel);
					}
				}
			}
		}
	}

	public double y(int x, int offset) {
		double b = b0 + speed * frame + offset;
		double a = b * a0 / b0;
		return Math.abs((x * a / b) - a);
	}

	public double color(int offset) {
		return 360 * offset / diamondNum;
	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane, LedPanel ledPanel) throws IOException {
		// TODO Auto-generated method stub

	}

}
