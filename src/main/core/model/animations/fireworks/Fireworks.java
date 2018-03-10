package main.core.model.animations.fireworks;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
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
	private double density = 0.1; // Average poping pixels number each frame
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
		updateDisplay(ledMatrix);
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
						ledMatrix[LedPanel.MATRIX_HEIGHT - 1][i] = newFireworkPixel;
					}
				}
			}

		}

	}

	private void updateDisplay(RGBWPixel[][] ledMatrix) {
		Set<Coordinates> availableCoordinates = new HashSet<>();
		availableCoordinates.addAll(fireworks.keySet());
		TreeMap<Coordinates, FireworkPixel> updatedPixels = new TreeMap<>();
		for (Coordinates coordinates : availableCoordinates) {
			FireworkPixel pixel = fireworks.get(coordinates);
			pixel.progress();
			int newAbsciss = pixel.coordinates().getKey();
			int newHeight = pixel.coordinates().getValue();
			if (newAbsciss >= 0 && newAbsciss < LedPanel.MATRIX_WIDTH && newHeight >= 0
					&& newHeight < LedPanel.MATRIX_HEIGHT) {
				updatedPixels.put(pixel.coordinates(), pixel);
				if (pixel.isReadyToPop()) {
					updatedPixels.putAll(pixel.pop());
				}
			}
		}
		for (Coordinates coordinatesToRemove : availableCoordinates) {
			fireworks.remove(coordinatesToRemove);
			ledMatrix[coordinatesToRemove.getValue()][coordinatesToRemove.getKey()] = RGBWPixel.BLACK_PIXEL;
		}
		fireworks.putAll(updatedPixels);
		updateMatrix(ledMatrix);
	}

	private void updateMatrix(RGBWPixel[][] ledMatrix) {
		for (Coordinates coordinates : fireworks.keySet()) {
			ledMatrix[coordinates.getValue()][coordinates.getKey()] = fireworks.get(coordinates);
		}
	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane, LedPanel ledPanel) throws IOException {
		// TODO Auto-generated method stub

	}

}
