package main.core.model.animations.pixelRain;

import javafx.scene.paint.Color;
import main.core.model.pixel.RGBWPixel;

public class FallingPixel extends RGBWPixel {
	
	private double speed = 1; //In pixel by frame
	private double progress = 0;

	public FallingPixel(Color color, int white) {
		super(color, white);
	}
	
	public FallingPixel(Color color, int white, double speed) {
		super(color, white);
		this.speed = speed;
	}
	
	public FallingPixel(RGBWPixel rgbwPixel, double progress) {
		super(rgbwPixel);
		this.progress = progress;
	}

	public void progress() {
		progress += speed;
	}
	
	public int getDiscreteProgress() {
		return (int) Math.floor(progress);
	}
}
