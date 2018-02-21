package main.core.model.animations.randomEffects;

import java.util.ArrayList;
import java.util.Random;

import main.core.model.animations.Animation;
import main.core.model.pixel.RGBWPixel;
import main.core.util.LedArrayToList;

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
		ArrayList<RGBWPixel> leds = LedArrayToList.convert(ledMatrix, matrixWidth, matrixHeight);
		Random random = new Random();
		for (int i = 0; i < lightOnNumber; i++) {
			int randomIndex = random.nextInt(leds.size());
			leds.remove(leds.get(randomIndex));

			int line = randomIndex / matrixWidth;
			int column = randomIndex % matrixWidth;

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
		return RGBWPixel.rgbwPixel(255 * red, 255 * green, 255 * blue, whiteLevel);
	}

	private RGBWPixel fullRangeRandomColoredPixel() {
		Random random = new Random();
		int red = random.nextInt(255);
		int green = random.nextInt(255);
		int blue = random.nextInt(255);
		return RGBWPixel.rgbwPixel(red, green, blue, whiteLevel);
	}
}
