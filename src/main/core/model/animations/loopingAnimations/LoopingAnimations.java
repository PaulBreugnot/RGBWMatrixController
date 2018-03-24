package main.core.model.animations.loopingAnimations;

import java.io.IOException;
import java.util.TreeMap;

import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.panel.LedPanel;
import main.core.model.pixel.RGBWPixel;

public class LoopingAnimations implements Animation {

	private TreeMap<Integer, Animation> animations = new TreeMap<>();
	private int time = 0;

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		getAnimation(time).setNextPicture(ledMatrix, matrixWidth, matrixHeight);
		time++;
	}

	public void addAnimation(int time, Animation animation) {
		animations.put(time, animation);
	}

	public Animation getAnimation(int time) {
		return animations.get(animations.floorKey(time));
	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane, LedPanel ledPanel) throws IOException {
		// TODO Auto-generated method stub

	}

}
