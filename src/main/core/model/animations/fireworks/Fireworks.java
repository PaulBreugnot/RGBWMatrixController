package main.core.model.animations.fireworks;

import java.io.IOException;
import java.util.Random;
import java.util.TreeMap;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;
import main.core.util.Coordinates;

public class Fireworks implements Animation {

	public static final String effectName = "Pixel Rain!";

	public enum Source {
		TOP, BOTTOM, LEFT, RIGHT
	};

	private Source source = Source.TOP;
	private double hueColor = 0;
	private double brightness = 1;
	private int whiteLevel = 0;
	private double density; // Average poping pixels number each frame
	private int spreadLength = 1;
	private double speed = 1;
	TreeMap<Coordinates, FireworkPixel> fireworks = new TreeMap<>();

	public void setSource(Source source) {
		this.source = source;
	}

	public void setHueColor(double hueColor) {
		this.hueColor = hueColor;
	}

	public void setWhiteLevel(int whiteLevel) {
		this.whiteLevel = whiteLevel;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public void setSpreadLength(int spreadLength) {
		this.spreadLength = spreadLength;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		createNewFireworks(ledMatrix);

	}

	private void createNewFireworks(RGBWPixel[][] ledMatrix) {
		Random random = new Random();
		if (source == Source.TOP || source == Source.BOTTOM) {
			for (int i = 0; i < LedPanel.MATRIX_WIDTH; i++) {
				switch (source) {
				case TOP:
					if (random.nextFloat() < density / LedPanel.MATRIX_WIDTH) {
						Coordinates newCoordinates = new Coordinates(i, LedPanel.MATRIX_HEIGHT - 1);
						FireworkPixel newFireworkPixel = new FireworkPixel(Color.hsb(hueColor, 1, brightness),
								whiteLevel, FireworkPixel.Direction.BOTTOM, LedPanel.MATRIX_HEIGHT - 1, i, speed);
						fireworks.put(newCoordinates, newFireworkPixel);
						ledMatrix[i][LedPanel.MATRIX_HEIGHT - 1] = newFireworkPixel;
					}
				}
			}

		}

	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane, LedPanel ledPanel) throws IOException {
		// TODO Auto-generated method stub

	}

}
