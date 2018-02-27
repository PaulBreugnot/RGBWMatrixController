package main.core.model.animations.pixelRain;

import javafx.scene.paint.Color;
import main.core.model.pixel.RGBWPixel;

public class FallingPixel extends RGBWPixel {

	private double speed = 1; // In pixel by frame
	private double progress = 0;
	private int currentDiscretePosition = 0;

	public FallingPixel(Color color, int white) {
		super(color, white);
	}

	public FallingPixel(Color color, int white, double speed, double progress) {
		super(color, white);
		this.speed = speed;
		this.progress = progress;
	}

	public void progress() {
		progress += speed;
		currentDiscretePosition = (int) Math.floor(progress);
	}

	public double getProgress() {
		return progress;
	}

	public int getDiscreteProgress() {
		return currentDiscretePosition;
	}
}
