package main.core.model.animations.fireworks;

import java.util.HashSet;
import java.util.Random;

import javafx.scene.paint.Color;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;
import main.core.util.Coordinates;

public class FireworkPixel extends RGBWPixel {

	public enum Direction {
		TOP, BOTTOM, LEFT, RIGHT
	};

	private double speed;
	private double progress;
	private int popHeight = 10;
	private int initialHeight;
	private int initialAbsciss;
	private Coordinates coordinates;
	private Direction direction;

	public FireworkPixel(Color color, int white, Direction direction, int initialHeight, int initialAbsciss,
			double speed) {
		super(color, white);
		this.direction = direction;
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
			coordinates = new Coordinates(initialAbsciss,
					LedPanel.MATRIX_HEIGHT - initialHeight - (int) Math.floor(progress) - 1);
			break;
		case RIGHT:
			coordinates = new Coordinates(initialAbsciss + (int) Math.floor(progress), initialHeight);
			break;
		case LEFT:
			coordinates = new Coordinates(LedPanel.MATRIX_WIDTH - initialAbsciss - (int) Math.floor(progress) - 1,
					initialHeight);
			break;
		}
		return coordinates;
	}

	public boolean isReadyToPop() {
		return progress >= popHeight;
	}

	public HashSet<FireworkPixel> pop() {
		HashSet<FireworkPixel> sons = new HashSet<>();
		Random random = new Random();
		double randomHue1 = random.nextDouble() * 360;
		Color randomColor1 = Color.hsb(randomHue1, color.getSaturation(), color.getBrightness());
		double randomHue2 = random.nextDouble() * 360;
		Color randomColor2 = Color.hsb(randomHue2, color.getSaturation(), color.getBrightness());
		switch (direction) {
		case TOP:
			if (coordinates.getKey() - 1 >= 0) {
				sons.add(new FireworkPixel(randomColor1, white, Direction.LEFT, coordinates.getValue(),
						coordinates.getKey() - 1, speed));
			}
			if (coordinates.getKey() + 1 < LedPanel.MATRIX_WIDTH) {
				sons.add(new FireworkPixel(randomColor2, white, Direction.RIGHT, coordinates.getValue(),
						coordinates.getKey() + 1, speed));
			}
			break;
		case BOTTOM:
			if (coordinates.getKey() - 1 >= 0) {
				sons.add(new FireworkPixel(randomColor1, white, Direction.LEFT, coordinates.getValue(),
						coordinates.getKey() - 1, speed));
			}
			if (coordinates.getKey() + 1 < LedPanel.MATRIX_WIDTH) {
				sons.add(new FireworkPixel(randomColor2, white, Direction.RIGHT, coordinates.getValue(),
						coordinates.getKey() + 1, speed));
			}
			break;
		case LEFT:
			if (coordinates.getValue() + 1 < LedPanel.MATRIX_HEIGHT) {
				sons.add(new FireworkPixel(randomColor1, white, Direction.TOP, coordinates.getValue() + 1,
						coordinates.getKey(), speed));
			}
			if (coordinates.getValue() - 1 >= 0) {
				sons.add(new FireworkPixel(randomColor2, white, Direction.BOTTOM, coordinates.getValue() - 1,
						coordinates.getKey(), speed));
			}
			break;
		case RIGHT:
			if (coordinates.getValue() + 1 < LedPanel.MATRIX_HEIGHT) {
				sons.add(new FireworkPixel(randomColor1, white, Direction.TOP, coordinates.getValue() + 1,
						coordinates.getKey(), speed));
			}
			if (coordinates.getValue() - 1 >= 0) {
				sons.add(new FireworkPixel(randomColor2, white, Direction.BOTTOM, coordinates.getValue() - 1,
						coordinates.getKey(), speed));
			}
			break;
		}
		return sons;
	}

}
