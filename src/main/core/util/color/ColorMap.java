package main.core.util.color;

import java.util.Random;

import javafx.scene.paint.Color;

public class ColorMap {
	// Defines a hue range in which random value could be selected
	private double maxHue;
	private double minHue;
	private double minSaturation;
	private double maxSaturation;
	private double minBrightness;
	private double maxBrightness;
	
	
	public ColorMap(double maxHue, double minHue, double minSaturation, double maxSaturation, double minBrightness,
			double maxBrightness) {
		this.maxHue = maxHue;
		this.minHue = minHue;
		this.minSaturation = minSaturation;
		this.maxSaturation = maxSaturation;
		this.minBrightness = minBrightness;
		this.maxBrightness = maxBrightness;
	}

	public Color random() {
		Random rd = new Random();
		double hue = rd.nextDouble() * (maxHue - minHue) + minHue;
		double saturation = rd.nextDouble() * (maxSaturation - minSaturation) + minSaturation;
		double brightness = rd.nextDouble() * (maxBrightness - minBrightness) + minBrightness;
		return Color.hsb(hue, saturation, brightness);
	}

}
