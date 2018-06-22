package main.gui.views.loopingAnimations.loopItem;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import main.core.model.animations.Animation;
import main.core.model.animations.loopingAnimations.LoopingAnimations;
import main.core.util.AnimationTime;
import main.gui.views.loopingAnimations.loopingAnimationsTab.LoopingAnimationsTabController;

public class LoopItemController {

	@FXML
	private Label indexLabel;

	@FXML
	private Label timeLabel;

	@FXML
	private AnchorPane ConfigPane;

	@FXML
	private TextField durationTextField;

	private Integer index;
	private AnimationTime animationTime;

	private LoopingAnimations loopingAnimations;
	private LoopingAnimationsTabController editLoopingAnimationsController;

	public void setAnimation(Animation animation, int index, AnimationTime animationTime) throws IOException {
		ConfigPane.getChildren().clear();
		animation.setAnimationSettings(ConfigPane);
		this.index = index;
		this.animationTime = animationTime;
		setLabels();
	}

	private void setLabels() {
		indexLabel.setText(index.toString());
		timeLabel.setText(animationTime.getKey().toString());
		durationTextField.setText(animationTime.getValue().toString());
	}

	public void setLoopingAnimations(LoopingAnimations loopingAnimations) {
		this.loopingAnimations = loopingAnimations;
	}

	public void setEditLoopingAnimationsController(LoopingAnimationsTabController editLoopingAnimationsController) {
		this.editLoopingAnimationsController = editLoopingAnimationsController;
	}

	@FXML
	private void handleDelete() {
		loopingAnimations.delete(animationTime);
		editLoopingAnimationsController.displayConfigPanes();
	}

	@FXML
	private void handleSave() {
		loopingAnimations.modifyDuration(animationTime, Integer.parseInt(durationTextField.getText()));
		editLoopingAnimationsController.displayConfigPanes();
	}
}
