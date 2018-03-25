package main.core.model.animations.loopingAnimations;

import java.io.IOException;
import java.util.TreeMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.pixel.RGBWPixel;
import main.core.util.AnimationTime;
import main.gui.views.settings.LoopingAnimationsSettingsController;

public class LoopingAnimations implements Animation {

	public static final String effectName = "LoopingAnimation";
	public static final int DEFAULT_DURATION = 125;

	private TreeMap<AnimationTime, Animation> animations = new TreeMap<>();
	private int end;
	private int time = 0;

	@Override
	public void setNextPicture(RGBWPixel[][] ledMatrix, int matrixWidth, int matrixHeight) {
		getAnimation(new AnimationTime(time, 0)).setNextPicture(ledMatrix, matrixWidth, matrixHeight);
		if (time >= end) {
			time = 0;
		} else {
			time++;
		}
	}

	public void addAnimation(AnimationTime animationTime, Animation animation) {
		animations.put(animationTime, animation);
	}

	public Animation getAnimation(AnimationTime animationTime) {
		return animations.get(animations.floorKey(animationTime));
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void delete(AnimationTime animationTime) {
		animations.remove(animationTime);
	}

	@Override
	public void setAnimationSettings(AnchorPane configAnchorPane) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/main/gui/views/settings/LoopingAnimationsSettings.fxml"));
		configAnchorPane.getChildren().add(loader.load());
		LoopingAnimationsSettingsController loopingAnimationsSettingsController = loader.getController();
		loopingAnimationsSettingsController.setLoopingAnimations(this);

	}

	@Override
	public Animation newAnimationInstance() {
		return new LoopingAnimations();
	}

	@Override
	public String toString() {
		return effectName;
	}

}
