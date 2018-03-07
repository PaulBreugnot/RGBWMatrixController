package main.core.model.animations.fireworks;

import java.util.HashSet;

import javafx.scene.paint.Color;
import main.core.model.pixel.RGBWPixel;

public class FireworkPixel extends RGBWPixel {

	private double speed;
	private double progress;
	private int popHeight;
	private int sonsNumber;
	private double direction;
	
	public FireworkPixel(Color color, int white) {
		super(color, white);
		// TODO Auto-generated constructor stub
	}
	
	private HashSet<FireworkPixel> sons;
	
	private void progress() {
		progress += speed;
	}
	
	private boolean isReadyToPop() {
		return progress >= popHeight;
	}
	
	private HashSet<FireworkPixel> pop() {
		for (int i = 0; i < sonsNumber; i ++) {
			
		}
		return null;
	}

}
