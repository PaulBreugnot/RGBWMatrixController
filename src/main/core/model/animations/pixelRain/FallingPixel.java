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
		System.out.print("Old Progress : ");
		System.out.println(progress);
		progress += speed;
		System.out.print("New Progress : ");
		System.out.println(progress);
		currentDiscretePosition = (int) Math.floor(progress);
	}

	public double getProgress() {
		return progress;
	}

	public int getDiscreteProgress() {
		return currentDiscretePosition;
	}
}
