package main.gui.views.loopingAnimations;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;

public class LoopItemController {

	@FXML
	private Label indexLabel;

	@FXML
	private Label timeLabel;

	@FXML
	private AnchorPane ConfigPane;

	private Animation animation;

	public void setAnimation(Animation animation) throws IOException {
		ConfigPane.getChildren().clear();
		animation.setAnimationSettings(ConfigPane);
	}
}
