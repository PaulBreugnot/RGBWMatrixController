package main.core.model.animations.pixelRain;

import java.io.IOException;
//import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
//import javafx.util.Pair;
import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;
import main.gui.views.settings.RainPixelSettingsController;

public class PixelRain implements Animation {

	public static final String effectName = "Pixel Rain!";

	public enum Source {
		TOP, BOTTOM, LEFT, RIGHT
	};

	private Source source;
	private double hueColor;
	private int whiteLevel;
	private double density; // Average poping pixels number each frame
	private int spreadLength;
	private double speed = 2;

	private int frame = 0;
	private double displayTreshold = 0;

	private TreeMap<Coordinates, FallingPixel> fallingPixels = new TreeMap<>();

	public PixelRain() {
		source = Source.TOP;
	}

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
			if (speed <= 1) {
				if (frame % ((int) Math.floor(1 / speed)) == 0) {
					popTopNewPixels(ledMatrix);
				}
				frame++;
			} else {
				popTopNewPixels(ledMatrix);
				frame++;
				while (frame < displayTreshold) {
					makeTopPixelsFall(ledMatrix);
					popTopNewPixels(ledMatrix);
					frame++;
				}
				displayTreshold += speed;
			}
			break;
		case BOTTOM:
			// makeBottomPixelsFall(ledMatrix);
			// popBottomNewPixels(ledMatrix);
			break;
		}

	}

	public void makeTopPixelsFall(RGBWPixel[][] ledMatrix) {
		// Remove pixels
		if ((speed <= 1 && frame % ((int) Math.floor(1 / speed)) == 0) || speed > 1) {
			for (int column = 0; column < LedPanel.MATRIX_WIDTH; column++) {
				fallingPixels.remove(new Coordinates(0, column));
				ledMatrix[0][column] = RGBWPixel.hsbwPixel(hueColor, 0, 0, 0);
			}
		}
		// Move pixels
		Map<Coordinates, FallingPixel> followingPixels = new TreeMap<>();
		Set<Coordinates> blackPixelsToRemove = new HashSet<>();
		for (Coordinates pixelCoordinates : fallingPixels.keySet()) {
			FallingPixel fallingPixel = fallingPixels.get(pixelCoordinates);
			int currentVerticalProgress = fallingPixel.getDiscreteProgress();
			int constantAbscissValue = pixelCoordinates.getValue();
			fallingPixel.progress();
			System.out.println("current Vertical Progress : ");
			System.out.println(currentVerticalProgress);

			if (fallingPixel.getDiscreteProgress() != currentVerticalProgress) {
				ledMatrix[LedPanel.MATRIX_HEIGHT - 1
						- fallingPixel.getDiscreteProgress()][constantAbscissValue] = fallingPixels
								.get(pixelCoordinates);
				followingPixels.put(new Coordinates(LedPanel.MATRIX_HEIGHT - 1 - fallingPixel.getDiscreteProgress(),
						constantAbscissValue), fallingPixel);
				FallingPixel followingPixel;
				System.out.println("fallingPixel.getDiscreteProgress() : ");
				System.out.println(fallingPixel.getDiscreteProgress());
				System.out.println("Follow num : ");
				System.out.println(fallingPixel.getDiscreteProgress() - currentVerticalProgress);
				int followingPixelNumber = fallingPixel.getDiscreteProgress() - currentVerticalProgress;
				for (int i = 0; i < followingPixelNumber; i++) {
					if (fallingPixels.get(pixelCoordinates).getBrightness() - 1.0 / spreadLength > 0) {
						double lowerBrightness = fallingPixels.get(pixelCoordinates).getBrightness()
								- 1.0 / spreadLength;
						followingPixel = new FallingPixel(Color.hsb(hueColor, 1.0, lowerBrightness), whiteLevel, speed,
								currentVerticalProgress);
						followingPixels.put(new Coordinates(pixelCoordinates.getKey() + i, pixelCoordinates.getValue()),
								followingPixel);
					} else {
						followingPixel = new FallingPixel(Color.hsb(hueColor, 1.0, 0), whiteLevel, speed,
								currentVerticalProgress);
						blackPixelsToRemove.add(pixelCoordinates);
					}
					ledMatrix[pixelCoordinates.getKey() + i][pixelCoordinates.getValue()] = followingPixel;
				}
			}
		}
		// Remove black pixels
		for (Coordinates blackPixel : blackPixelsToRemove) {
			fallingPixels.remove(blackPixel);
		}

		// Add following pixels
		for (Coordinates followingPixel : followingPixels.keySet()) {
			fallingPixels.put(followingPixel, followingPixels.get(followingPixel));
		}
	}

	public void popTopNewPixels(RGBWPixel[][] ledMatrix) {
		Random random = new Random();
		for (int i = 0; i < LedPanel.MATRIX_WIDTH; i++) {
			if (random.nextFloat() < density / LedPanel.MATRIX_WIDTH) {
				FallingPixel newPixel = new FallingPixel(Color.hsb(hueColor, 1, 1), whiteLevel, speed, 0);
				ledMatrix[LedPanel.MATRIX_HEIGHT - 1][i] = newPixel;
				fallingPixels.put(new Coordinates(LedPanel.MATRIX_HEIGHT - 1, i), newPixel);
			}
		}
	}

	/*
	 * public void makeBottomPixelsFall(RGBWPixel[][] ledMatrix) { // Remove pixels
	 * for (int column = 0; column < LedPanel.MATRIX_WIDTH; column++) {
	 * fallingPixels.remove(new Coordinates(LedPanel.MATRIX_HEIGHT - 1, column));
	 * ledMatrix[LedPanel.MATRIX_HEIGHT - 1][column] = RGBWPixel.hsbwPixel(hueColor,
	 * 0, 0, whiteLevel); }
	 * 
	 * // Move pixels TreeMap<Coordinates, RGBWPixel> followingPixels = new
	 * TreeMap<>(); Set<Coordinates> blackPixelsToRemove = new HashSet<>(); for
	 * (Coordinates pixel : fallingPixels.descendingMap().keySet()) {
	 * ledMatrix[pixel.getKey() + 1][pixel.getValue()] = fallingPixels.get(pixel);
	 * followingPixels.put(new Coordinates(pixel.getKey() + 1, pixel.getValue()),
	 * fallingPixels.get(pixel));
	 * 
	 * RGBWPixel followingPixel;
	 * 
	 * if (fallingPixels.get(pixel).getBrightness() - 1.0 / spreadLength > 0) {
	 * followingPixel = RGBWPixel.hsbwPixel(hueColor, 1.0,
	 * fallingPixels.get(pixel).getBrightness() - 1.0 / spreadLength, whiteLevel);
	 * followingPixels.put(new Coordinates(pixel.getKey(), pixel.getValue()),
	 * followingPixel); } else { followingPixel = RGBWPixel.hsbwPixel(hueColor, 0,
	 * 0, whiteLevel); blackPixelsToRemove.add(pixel); }
	 * ledMatrix[pixel.getKey()][pixel.getValue()] = followingPixel; } // Remove
	 * black pixels for (Coordinates blackPixel : blackPixelsToRemove) {
	 * fallingPixels.remove(blackPixel); }
	 * 
	 * // Add following pixels for (Coordinates followingPixel :
	 * followingPixels.keySet()) { fallingPixels.put(followingPixel,
	 * followingPixels.get(followingPixel)); } }
	 * 
	 * public void popBottomNewPixels(RGBWPixel[][] ledMatrix) { Random random = new
	 * Random(); for (int i = 0; i < LedPanel.MATRIX_WIDTH; i++) { if
	 * (random.nextFloat() < density / LedPanel.MATRIX_WIDTH) { RGBWPixel newPixel =
	 * RGBWPixel.hsbwPixel(hueColor, 1, 1, whiteLevel); ledMatrix[0][i] = newPixel;
	 * fallingPixels.put(new Coordinates(0, i), newPixel); } } }
	 */

	@Override
	public void setAnimationSettings(AnchorPane ConfigAnchorPane, LedPanel ledPanel) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/main/gui/views/settings/RainPixelSettings.fxml"));
		ConfigAnchorPane.getChildren().add(loader.load());
		RainPixelSettingsController rainPixelSettingsController = loader.getController();
		rainPixelSettingsController.setPixelRain((PixelRain) ledPanel.getCurrentAnimation());
	}

	@Override
	public String toString() {
		return effectName;
	}
}
