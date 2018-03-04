package main.core.model.animations.SinWave;

import java.io.IOException;

import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class SinWave implements Animation {

	public static final String effectName = "Sinus Wave!";

	public enum Source {
		TOP, BOTTOM, LEFT, RIGHT
	};

	private Source source;
	private double hueColor = 0;
	private int whiteLevel = 0;
	private double waveLength = 10;
	private double contrast = 0.4;
	private int speed = 1;
	private int offset = 0;

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		displayWave(ledMatrix);
		offset++;
		// offset = (offset + speed) % (LedPanel.MATRIX_WIDTH - 1);

	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane, LedPanel ledPanel) throws IOException {
		// TODO Auto-generated method stub

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

}
