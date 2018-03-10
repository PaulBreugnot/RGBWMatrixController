package main.core.model.animations.fireworks;

import java.util.Random;
import java.util.TreeMap;

import javafx.scene.paint.Color;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;
import main.core.util.Coordinates;

public class FireworkPixel extends RGBWPixel {

	public enum Direction {
		TOP, BOTTOM, LEFT, RIGHT
	};

	private double speed;
	private double progress = 0;
	private int popHeight = 5;
	private int initialHeight;
	private int initialAbsciss;
	private Coordinates coordinates;
	private Direction direction;

	public FireworkPixel(Color color, int white, Direction direction, int initialHeight, int initialAbsciss,
			double speed) {
		super(color, white);
		this.direction = direction;
		System.out.println("Initial height : " + initialHeight);
		this.initialHeight = initialHeight;
		this.initialAbsciss = initialAbsciss;
		this.speed = speed;
	}

	public void progress() {
		progress += speed;
	}

	public Coordinates coordinates() {
		switch (direction) {
		case TOP:
			coordinates = new Coordinates(initialAbsciss, initialHeight + (int) Math.floor(progress));
			break;
		case BOTTOM:
			coordinates = new Coordinates(initialAbsciss, initialHeight - (int) Math.floor(progress));
			break;
		case RIGHT:
			coordinates = new Coordinates(initialAbsciss + (int) Math.floor(progress), initialHeight);
			break;
		case LEFT:
			coordinates = new Coordinates(initialAbsciss - (int) Math.floor(progress), initialHeight);
			break;
		}
		return coordinates;
	}

	public boolean isReadyToPop() {
		return progress >= popHeight;
	}

	public TreeMap<Coordinates, FireworkPixel> pop() {
		TreeMap<Coordinates, FireworkPixel> sons = new TreeMap<>();
		Random random = new Random();
		double randomHue1 = random.nextDouble() * 360;
		Color randomColor1 = Color.hsb(randomHue1, color.getSaturation(), color.getBrightness());
		double randomHue2 = random.nextDouble() * 360;
		Color randomColor2 = Color.hsb(randomHue2, color.getSaturation(), color.getBrightness());
		switch (direction) {
		case TOP:
			if (coordinates.getKey() - 1 >= 0) {
				Coordinates newCoordinates = new Coordinates(coordinates.getKey() - 1, coordinates.getValue());
				sons.put(newCoordinates, new FireworkPixel(randomColor1, white, Direction.LEFT,
						newCoordinates.getValue(), newCoordinates.getKey(), speed));
			}
			if (coordinates.getKey() + 1 < LedPanel.MATRIX_WIDTH) {
				Coordinates newCoordinates = new Coordinates(coordinates.getKey() + 1, coordinates.getValue());
				sons.put(newCoordinates, new FireworkPixel(randomColor2, white, Direction.RIGHT,
						newCoordinates.getValue(), newCoordinates.getKey(), speed));
			}
			break;
		case BOTTOM:
			if (coordinates.getKey() - 1 >= 0) {
				Coordinates newCoordinates = new Coordinates(coordinates.getKey() - 1, coordinates.getValue());
				sons.put(newCoordinates, new FireworkPixel(randomColor1, white, Direction.LEFT,
						newCoordinates.getValue(), newCoordinates.getKey(), speed));
			}
			if (coordinates.getKey() + 1 < LedPanel.MATRIX_WIDTH) {
				Coordinates newCoordinates = new Coordinates(coordinates.getKey() + 1, coordinates.getValue());
				sons.put(newCoordinates, new FireworkPixel(randomColor2, white, Direction.RIGHT,
						newCoordinates.getValue(), newCoordinates.getKey(), speed));
			}
			break;
		case LEFT:
			if (coordinates.getValue() + 1 < LedPanel.MATRIX_HEIGHT) {
				Coordinates newCoordinates = new Coordinates(coordinates.getKey(), coordinates.getValue() + 1);
				sons.put(newCoordinates, new FireworkPixel(randomColor1, white, Direction.TOP,
						newCoordinates.getValue(), newCoordinates.getKey(), speed));
			}
			if (coordinates.getValue() - 1 >= 0) {
				Coordinates newCoordinates = new Coordinates(coordinates.getKey(), coordinates.getValue() - 1);
				sons.put(newCoordinates, new FireworkPixel(randomColor2, white, Direction.BOTTOM,
						newCoordinates.getValue(), newCoordinates.getKey(), speed));
			}
			break;
		case RIGHT:
			if (coordinates.getValue() + 1 < LedPanel.MATRIX_HEIGHT) {
				Coordinates newCoordinates = new Coordinates(coordinates.getKey(), coordinates.getValue() + 1);
				sons.put(newCoordinates, new FireworkPixel(randomColor1, white, Direction.TOP,
						newCoordinates.getValue(), newCoordinates.getKey(), speed));
			}
			if (coordinates.getValue() - 1 >= 0) {
				Coordinates newCoordinates = new Coordinates(coordinates.getKey(), coordinates.getValue() - 1);
				sons.put(newCoordinates, new FireworkPixel(randomColor2, white, Direction.BOTTOM,
						newCoordinates.getValue(), newCoordinates.getKey(), speed));
			}
			break;
		}
		return sons;
	}

}
