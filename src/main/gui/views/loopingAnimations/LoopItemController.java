package main.gui.views.loopingAnimations;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.animations.loopingAnimations.LoopingAnimations;

public class LoopItemController {

	@FXML
	private Label indexLabel;

	@FXML
	private Label timeLabel;

	@FXML
	private AnchorPane ConfigPane;

	private int time;
	private Animation animation;

	private LoopingAnimations loopingAnimations;

	public void setAnimation(Animation animation) throws IOException {
		ConfigPane.getChildren().clear();
		animation.setAnimationSettings(ConfigPane);
	}

	public void setLoopingAnimations(LoopingAnimations loopingAnimations) {
		this.loopingAnimations = loopingAnimations;
	}

	@FXML
	private void handleDelete() {
		loopingAnimations.delete(time);
	}
}
