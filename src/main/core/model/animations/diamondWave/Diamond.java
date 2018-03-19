package main.core.model.animations.diamondWave;

import java.util.HashMap;

import javafx.scene.paint.Color;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;
import main.core.util.Coordinates;

public class Diamond implements Comparable<Diamond> {

	private Color color;
	private int white;
	private int width;
	private int height;
	private double ratio;
	private double progress = 0;
	private int xCenter;
	private int yCenter;

	private HashMap<Coordinates, RGBWPixel> pixels = new HashMap<>();

	public Diamond(Color color, int white, double ratio, int xCenter, int yCenter) {
		this.color = color;
		this.ratio = ratio;
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		pixels.put(new Coordinates(xCenter, yCenter), new RGBWPixel(color, white));
	}

	public Diamond(Color color, int white, double ratio, int xCenter, int yCenter, int width, int height,
			double progress) {
		this.color = color;
		this.ratio = ratio;
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.width = width;
		this.height = height;
		this.progress = progress;
		updatePixels();
	}

	public double getProgress() {
		return progress;
	}

	public int getWidth() {
		return width;
	}

	public int progress(double speed) {
		progress += speed;
		int steps = (int) Math.floor(progress) - width;
		if (width != (int) Math.floor(progress)) {
			width = (int) Math.floor(progress);
			height = (int) Math.floor(ratio * width);
			updatePixels();
		}
		return steps;
	}

	private void updatePixels() {
		pixels.clear();
		for (int x = -width; x <= width; x++) {
			int y = (int) Math.floor(y(x));
			if ((xCenter - x) >= 0 && (xCenter - x) < LedPanel.MATRIX_WIDTH) {
				if ((y + yCenter) >= 0 && (y + yCenter) < LedPanel.MATRIX_HEIGHT) {
					pixels.put(new Coordinates(xCenter - x, y + yCenter), new RGBWPixel(color, white));
				}
				if ((-y + yCenter) >= 0 && (-y + yCenter) < LedPanel.MATRIX_HEIGHT) {
					pixels.put(new Coordinates(xCenter - x, -y + yCenter), new RGBWPixel(color, white));
				}
			}

		}
	}

	public HashMap<Coordinates, RGBWPixel> getPixels() {
		return pixels;
	}

	public double y(int x) {
		return Math.abs((x * ratio)) - height;
	}

	@Override
	public int compareTo(Diamond o) {
		if (getProgress() >= o.getProgress()) {
			if (getProgress() == o.getProgress()) {
				return 0;
			}
			return -1;
		}
		return 1;
	}

}
