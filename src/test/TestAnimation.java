package test;

import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class TestAnimation implements Animation {

	private int counter = 0;

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		if (counter == 0) {
			Red(ledMatrix);
		} else if (counter == 1) {
			Green(ledMatrix);
		} else if (counter == 2) {
			Blue(ledMatrix);
		}
		if (counter == 2) {
			counter = 0;
		} else {
			counter++;
		}

	}

	private void Red(RGBWPixel[][] ledMatrix) {
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				ledMatrix[i][j] = RGBWPixel.rgbPixel(255, 0, 0);
			}
		}
	}

	private void Green(RGBWPixel[][] ledMatrix) {
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				ledMatrix[i][j] = RGBWPixel.rgbPixel(0, 255, 0);
			}
		}
	}

	private void Blue(RGBWPixel[][] ledMatrix) {
		for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				ledMatrix[i][j] = RGBWPixel.rgbPixel(0, 0, 255);
			}
		}
	}
}
