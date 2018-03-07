package main.core.model.animations.fireworks;

import java.io.IOException;

import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class Fireworks implements Animation {
	
	public static final String effectName = "Pixel Rain!";

	public enum Source {
		TOP, BOTTOM, LEFT, RIGHT
	};

	private Source source;
	private double hueColor;
	private int whiteLevel;
	private double density; // Average poping pixels number each frame
	private int spreadLength = 1;
	private double speed = 1;
	
	

	public void setSource(Source source) {
		this.source = source;
	}

	public void setHueColor(double hueColor) {
		this.hueColor = hueColor;
	}

	public void setWhiteLevel(int whiteLevel) {
		this.whiteLevel = whiteLevel;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public void setSpreadLength(int spreadLength) {
		this.spreadLength = spreadLength;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane, LedPanel ledPanel) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	

	
}
