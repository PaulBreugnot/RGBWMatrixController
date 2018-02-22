package main.core.model.animations.pixelRain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javafx.util.Pair;
import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class PixelRain implements Animation {

	public enum Source {
		TOP, BOTTOM, LEFT, RIGHT
	};

	private Source source;
	private double hueColor;
	private int whiteLevel;
	private double density; // Average poping pixels number each frame
	private int spreadLength;

	private Map<Pair<Integer, Integer>, RGBWPixel> fallingPixels = new HashMap<>();

	public PixelRain(Source source, double hueColor, double density, int spreadLength, int whiteLevel) {
		this.source = source;
		this.hueColor = hueColor;
		this.density = density;
		this.spreadLength = spreadLength;
		this.whiteLevel = whiteLevel;
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

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public int getSpreadLength() {
		return spreadLength;
	}

	public void setSpreadLength(int spreadLength) {
		this.spreadLength = spreadLength;
	}

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		switch (source) {
		case TOP:
			makeTopPixelsFall(ledMatrix);
			popTopNewPixels(ledMatrix);
			break;
		case BOTTOM:
			makeBottomPixelsFall(ledMatrix);
			popBottomNewPixels(ledMatrix);
			break;
		}

	}

	public void makeTopPixelsFall(RGBWPixel[][] ledMatrix) {
		// Remove pixels
		for (int column = 0; column < LedPanel.MATRIX_WIDTH; column++) {
			fallingPixels.remove(new Pair<Integer, Integer>(0, column));
			ledMatrix[0][column] = RGBWPixel.hsbwPixel(hueColor, 0, 0, whiteLevel);
		}

		// Move pixels
		Map<Pair<Integer, Integer>, RGBWPixel> followingPixels = new HashMap<>();
		Set<Pair<Integer, Integer>> blackPixelsToRemove = new HashSet<>();
		for (Pair<Integer, Integer> pixel : fallingPixels.keySet()) {
			ledMatrix[pixel.getKey() - 1][pixel.getValue()] = fallingPixels.get(pixel);
			followingPixels.put(new Pair<Integer, Integer>(pixel.getKey() - 1, pixel.getValue()),
					fallingPixels.get(pixel));

			RGBWPixel followingPixel;
			if (fallingPixels.get(pixel).getBrightness() - 1.0 / spreadLength > 0) {
				followingPixel = RGBWPixel.hsbwPixel(hueColor, 1.0,
						fallingPixels.get(pixel).getBrightness() - 1.0 / spreadLength, whiteLevel);
				followingPixels.put(new Pair<Integer, Integer>(pixel.getKey(), pixel.getValue()), followingPixel);
			} else {
				followingPixel = RGBWPixel.hsbwPixel(hueColor, 0, 0, whiteLevel);
				blackPixelsToRemove.add(pixel);
			}
			ledMatrix[pixel.getKey()][pixel.getValue()] = followingPixel;
		}
		// Remove black pixels
		for (Pair<Integer, Integer> blackPixel : blackPixelsToRemove) {
			fallingPixels.remove(blackPixel);
		}

		// Add following pixels
		for (Pair<Integer, Integer> followingPixel : followingPixels.keySet()) {
			fallingPixels.put(followingPixel, followingPixels.get(followingPixel));
		}
	}

	public void popTopNewPixels(RGBWPixel[][] ledMatrix) {
		Random random = new Random();
		for (int i = 0; i < LedPanel.MATRIX_WIDTH; i++) {
			if (random.nextFloat() < density / LedPanel.MATRIX_WIDTH) {
				RGBWPixel newPixel = RGBWPixel.hsbwPixel(hueColor, 1, 1, whiteLevel);
				ledMatrix[LedPanel.MATRIX_HEIGHT - 1][i] = newPixel;
				fallingPixels.put(new Pair<Integer, Integer>(LedPanel.MATRIX_HEIGHT - 1, i), newPixel);
			}
		}
	}

	public void makeBottomPixelsFall(RGBWPixel[][] ledMatrix) {
		// Remove pixels
		for (int column = 0; column < LedPanel.MATRIX_WIDTH; column++) {
			fallingPixels.remove(new Pair<Integer, Integer>(LedPanel.MATRIX_HEIGHT - 1, column));
			ledMatrix[LedPanel.MATRIX_HEIGHT - 1][column] = RGBWPixel.hsbwPixel(hueColor, 0, 0, whiteLevel);
		}

		// Move pixels
		Map<Pair<Integer, Integer>, RGBWPixel> followingPixels = new HashMap<>();
		Set<Pair<Integer, Integer>> blackPixelsToRemove = new HashSet<>();
		for (Pair<Integer, Integer> pixel : fallingPixels.keySet()) {
			ledMatrix[pixel.getKey() + 1][pixel.getValue()] = fallingPixels.get(pixel);
			followingPixels.put(new Pair<Integer, Integer>(pixel.getKey() + 1, pixel.getValue()),
					fallingPixels.get(pixel));

			RGBWPixel followingPixel;

			if (fallingPixels.get(pixel).getBrightness() - 1.0 / spreadLength > 0) {
				followingPixel = RGBWPixel.hsbwPixel(hueColor, 1.0,
						fallingPixels.get(pixel).getBrightness() - 1.0 / spreadLength, whiteLevel);
				followingPixels.put(new Pair<Integer, Integer>(pixel.getKey(), pixel.getValue()), followingPixel);
			} else {
				followingPixel = RGBWPixel.hsbwPixel(hueColor, 0, 0, whiteLevel);
				blackPixelsToRemove.add(pixel);
			}
			ledMatrix[pixel.getKey()][pixel.getValue()] = followingPixel;
		}
		// Remove black pixels
		for (Pair<Integer, Integer> blackPixel : blackPixelsToRemove) {
			fallingPixels.remove(blackPixel);
		}

		// Add following pixels
		for (Pair<Integer, Integer> followingPixel : followingPixels.keySet()) {
			fallingPixels.put(followingPixel, followingPixels.get(followingPixel));
		}
	}

	public void popBottomNewPixels(RGBWPixel[][] ledMatrix) {
		Random random = new Random();
		for (int i = 0; i < LedPanel.MATRIX_WIDTH; i++) {
			if (random.nextFloat() < density / LedPanel.MATRIX_WIDTH) {
				RGBWPixel newPixel = RGBWPixel.hsbwPixel(hueColor, 1, 1, whiteLevel);
				ledMatrix[0][i] = newPixel;
				fallingPixels.put(new Pair<Integer, Integer>(0, i), newPixel);
			}
		}
	}
}
