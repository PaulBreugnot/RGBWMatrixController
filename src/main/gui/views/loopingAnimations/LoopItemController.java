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

	private Integer index;
	private Integer time;

	private LoopingAnimations loopingAnimations;

	public void setAnimation(Animation animation, int index, int time) throws IOException {
		ConfigPane.getChildren().clear();
		animation.setAnimationSettings(ConfigPane);
		this.index = index;
		this.time = time;
		setLabels();
	}

	private void setLabels() {
		indexLabel.setText(index.toString());
		timeLabel.setText(time.toString());
	}

	public void setLoopingAnimations(LoopingAnimations loopingAnimations) {
		this.loopingAnimations = loopingAnimations;
	}

	@FXML
	private void handleDelete() {
		loopingAnimations.delete(time);
	}
}
