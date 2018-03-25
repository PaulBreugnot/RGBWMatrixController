package main.core.model.animations.randomEffects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class RandomPop implements Animation {

	public static final String effectName = "Random Pop";

	private enum PredefinedRandomType {
		fullRange, classicalRGB
	};

	private PredefinedRandomType predefinedRandomType;
	private int lightOnNumber;
	private double hueMainColorTone;
	private int fps;
	private int whiteLevel;

	public RandomPop() {

	}

	public RandomPop(int lightOnNumber, double hueMainColorTone, int fps, int whiteLevel) {
		this.lightOnNumber = lightOnNumber;
		this.hueMainColorTone = hueMainColorTone;
		this.fps = fps;
		this.whiteLevel = whiteLevel;
	}

	public RandomPop(int lightOnNumber, int fps, int whiteLevel) {
		this.lightOnNumber = lightOnNumber;
		this.fps = fps;
		this.whiteLevel = whiteLevel;
	}

	public static RandomPop fullRangeColorPop(int lightOnNumber, int fps, int whiteLevel) {
		RandomPop fullRangeColorPop = new RandomPop(lightOnNumber, fps, whiteLevel);
		fullRangeColorPop.setPredefinedRandomType(PredefinedRandomType.fullRange);
		return fullRangeColorPop;
	}

	public static RandomPop classicalRGBColorPop(int lightOnNumber, int fps, int whiteLevel) {
		RandomPop classicalRGBColorPop = new RandomPop(lightOnNumber, fps, whiteLevel);
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

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return effectName;
	}

	@Override
	public Animation newAnimationInstance() {
		return new RandomPop();
	}

}
