package main.core.model.animations.loopingAnimations;

import java.io.IOException;
import java.util.NavigableMap;
import java.util.TreeMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.pixel.RGBWPixel;
import main.core.util.AnimationTime;
import main.gui.views.settings.LoopingAnimationsSettingsController;

public class LoopingAnimations implements Animation {

	public static final String effectName = "LoopingAnimation";
	public static final Integer DEFAULT_DURATION = 125;

	private TreeMap<AnimationTime, Animation> animations = new TreeMap<>();
	private int end = 0;
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
		end += animationTime.getValue();
	}

	public Animation getAnimation(AnimationTime animationTime) {
		return animations.get(animations.floorKey(animationTime));
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public TreeMap<AnimationTime, Animation> getAnimations() {
		return animations;
	}

	public void delete(AnimationTime animationTimeToDelete) {
		end -= animationTimeToDelete.getValue();
		TreeMap<AnimationTime, Animation> shiftedAnimations = new TreeMap<>();
		NavigableMap<AnimationTime, Animation> tail = animations.tailMap(animationTimeToDelete, false);
		for (AnimationTime animationTime : tail.keySet()) {
			shiftedAnimations.put(new AnimationTime(animationTime.getKey() - animationTimeToDelete.getValue(),
					animationTime.getValue()), animations.get(animationTime));
		}
		tail.clear();
		animations.remove(animationTimeToDelete);
		animations.putAll(shiftedAnimations);
	}

	public void modifyDuration(AnimationTime animationTimeToModify, Integer newDuration) {
		end -= animationTimeToModify.getValue();
		end += newDuration;
		TreeMap<AnimationTime, Animation> shiftedAnimations = new TreeMap<>();
		NavigableMap<AnimationTime, Animation> tail = animations.tailMap(animationTimeToModify, false);
		for (AnimationTime animationTime : tail.keySet()) {
			shiftedAnimations
					.put(new AnimationTime(animationTime.getKey() - animationTimeToModify.getValue() + newDuration,
							animationTime.getValue()), animations.get(animationTime));
		}
		tail.clear();
		Animation a = animations.get(animationTimeToModify);
		animations.remove(animationTimeToModify);
		animations.put(new AnimationTime(animationTimeToModify.getKey(), newDuration), a);
		animations.putAll(shiftedAnimations);
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
