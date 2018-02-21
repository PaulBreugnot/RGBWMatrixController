package main.core.model.animations.randomEffects;

import java.util.ArrayList;
import java.util.Random;

import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class RandomPop implements Animation {

	private enum PredefinedRandomType {
		fullRange, classicalRGB
	};

	private PredefinedRandomType predefinedRandomType;
	private int lightOnNumber;
	private double hueMainColorTone;
	private int speed;
	private int whiteLevel;

	public RandomPop(int lightOnNumber, double hueMainColorTone, int speed, int whiteLevel) {
		this.lightOnNumber = lightOnNumber;
		this.hueMainColorTone = hueMainColorTone;
		this.speed = speed;
		this.whiteLevel = whiteLevel;
	}

	public RandomPop(int lightOnNumber, int speed, int whiteLevel) {
		this.lightOnNumber = lightOnNumber;
		this.speed = speed;
		this.whiteLevel = whiteLevel;
	}

	public static RandomPop fullRangeColorPop(int lightOnNumber, int speed, int whiteLevel) {
		RandomPop fullRangeColorPop = new RandomPop(lightOnNumber, speed, whiteLevel);
		fullRangeColorPop.setPredefinedRandomType(PredefinedRandomType.fullRange);
		return fullRangeColorPop;
	}

	public static RandomPop classicalRGBColorPop(int lightOnNumber, int speed, int whiteLevel) {
		RandomPop classicalRGBColorPop = new RandomPop(lightOnNumber, speed, whiteLevel);
		classicalRGBColorPop.setPredefinedRandomType(PredefinedRandomType.classicalRGB);
		return classicalRGBColorPop;
	}

	private void setPredefinedRandomType(PredefinedRandomType predefinedRandomType) {
		this.predefinedRandomType = predefinedRandomType;
	}

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		setRandomColors(ledMatrix, matrixWidth, matrixHeight);

	}

	private void setRandomColors(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		LedPanel.setBlackPanel(ledMatrix);
		ArrayList<Integer> ledsIndexes = new ArrayList<>();
		for (int i = 0; i < matrixWidth * matrixHeight; i++) {
			ledsIndexes.add(i);
		}
		// ArrayList<RGBWPixel> leds = LedArrayToList.convert(ledMatrix, matrixWidth,
		// matrixHeight);
		Random random = new Random();
		for (int i = 0; i < lightOnNumber; i++) {
			int randomIndex = random.nextInt(ledsIndexes.size());

			int line = ledsIndexes.get(randomIndex) / matrixWidth;
			int column = ledsIndexes.get(randomIndex) % matrixWidth;

			ledsIndexes.remove(ledsIndexes.get(randomIndex));

			if (predefinedRandomType != null) {
				switch (predefinedRandomType) {
				case classicalRGB: {
					ledMatrix[line][column] = classicalRGBRandomColoredPixel();
				}
				case fullRange: {
					ledMatrix[line][column] = fullRangeRandomColoredPixel();
				}
				}
			} else {

			}
		}
	}

	private RGBWPixel classicalRGBRandomColoredPixel() {
		Random random = new Random();
		int red = random.nextInt(2);
		int green = random.nextInt(2);
		int blue = random.nextInt(2);
		System.out.println("Red" + red * 200);
		System.out.println("Green" + green * 200);
		System.out.println("Blue" + blue * 200);
		return RGBWPixel.rgbwPixel(200 * red, 200 * green, 200 * blue, whiteLevel);
	}

	private RGBWPixel fullRangeRandomColoredPixel() {
		Random random = new Random();
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);
		return RGBWPixel.rgbwPixel(red, green, blue, whiteLevel);
	}
}
