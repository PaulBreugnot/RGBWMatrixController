package main.core.model.animations.fan;

import java.io.IOException;

import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class Fan implements Animation {

	public static final String effectName = "Fan!";

	public enum WaveMode {
		SATURATION, BRIGHTNESS, RAINBOW
	}

	private WaveMode waveMode = WaveMode.BRIGHTNESS;
	private double hueColor = 0;
	private int whiteLevel = 0;
	private int xCenter = -1;
	private int yCenter = -1;
	private double intensity = LedPanel.MAX_INTENSITY;
	private double fanNumber = 6;
	private double contrast = 1;
	private double speed = 5; // degree per frame
	private double alphaT = 0;

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		rotate(ledMatrix);
		alphaT += speed * 2 * Math.PI / 360;
	}

	public WaveMode getWaveMode() {
		return waveMode;
	}

	public void setWaveMode(WaveMode waveMode) {
		this.waveMode = waveMode;
	}

	public double getHueColor() {
		return hueColor;
	}

	public void setHueColor(double hueColor) {
		this.hueColor = hueColor;
	}

	public int getWhiteLevel() {
		return whiteLevel;
	}

	public void setWhiteLevel(int whiteLevel) {
		this.whiteLevel = whiteLevel;
	}

	public int getXCenter() {
		return xCenter;
	}

	public void setXCenter(int xCenter) {
		this.xCenter = xCenter;
	}

	public int getYCenter() {
		return yCenter;
	}

	public void setYCenter(int yCenter) {
		this.yCenter = yCenter;
	}

	public double getIntensity() {
		return intensity;
	}

	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}

	public double getFanNumber() {
		return fanNumber;
	}

	public void setFanNumber(double fanNumber) {
		this.fanNumber = fanNumber;
	}

	public double getContrast() {
		return contrast;
	}

	public void setContrast(double contrast) {
		this.contrast = contrast;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	private void rotate(RGBWPixel[][] ledMatrix) {
		for (int column = 0; column < LedPanel.MATRIX_WIDTH; column++) {
			for (int line = 0; line < LedPanel.MATRIX_HEIGHT; line++) {
				double alpha;
				if ((column - xCenter) > 0) {
					alpha = Math.atan(((double) (line - yCenter)) / ((double) (column - xCenter)));
				} else if ((column - xCenter) < 0) {
					alpha = Math.atan(((double) (line - yCenter)) / ((double) (column - xCenter))) + Math.PI;
				} else {
					if ((line - yCenter) > 0) {
						alpha = Math.PI / 2;
					} else {
						alpha = -Math.PI / 2;
					}
				}
				RGBWPixel pixel = RGBWPixel.BLACK_PIXEL;
				double angle = Math.PI / 2 + alpha + alphaT;
				while (angle > 2 * Math.PI) {
					angle -= (2 * Math.PI);
				}
				switch (waveMode) {
				case SATURATION:
					double saturation = Amp(angle);
					pixel = RGBWPixel.hsbwPixel(hueColor, saturation, intensity, whiteLevel);
					break;
				case BRIGHTNESS:
					// double brightness = SinAmp(alpha);
					double brightness = Amp(angle);
					pixel = RGBWPixel.hsbwPixel(hueColor, 1, intensity * brightness, whiteLevel);
					break;
				case RAINBOW:
					double hue = Amp(angle) * 360;
					pixel = RGBWPixel.hsbwPixel(hue, 1, intensity, whiteLevel);
					break;
				}
				ledMatrix[line][column] = pixel;
			}
			if (!(waveMode == WaveMode.RAINBOW)) {
				if (0 < yCenter && yCenter < LedPanel.MATRIX_HEIGHT && 0 < xCenter && xCenter < LedPanel.MATRIX_WIDTH) {
					ledMatrix[yCenter][xCenter] = RGBWPixel.hsbwPixel(hueColor, 1, intensity, whiteLevel);
				}
			}
		}
	}

	private double Amp(double angle) {
		while (angle > 2 * Math.PI / fanNumber) {
			angle -= 2 * Math.PI / fanNumber;
		}
		return (angle / (2 * Math.PI / fanNumber));
	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Animation newAnimationInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
