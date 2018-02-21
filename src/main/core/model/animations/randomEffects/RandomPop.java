package main.core.model.animations.randomEffects;

import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;

public class RandomPop implements Animation {

	private enum PredefinedRandomType {
		fullRange, classicalRGB
	};

	private LedPanel ledPanel;
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
	public void showNextPicture() {
		// TODO Auto-generated method stub

	}

}
