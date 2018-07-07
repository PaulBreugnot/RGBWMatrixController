package main.gui.views.settings.loopingAnimations;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.core.model.animations.loopingAnimations.LoopingAnimations;
import main.gui.views.loopingAnimations.loopingAnimationsTab.LoopingAnimationsTabController;

public class LoopingAnimationsSettingsController {

	private LoopingAnimations loopingAnimations;

	public void setLoopingAnimations(LoopingAnimations loopingAnimations) {
		this.loopingAnimations = loopingAnimations;
	}

	@FXML
	private void handleEdit() {
		Stage editStage = new Stage();
		editStage.setTitle("Looping Animation");
		editStage.setResizable(false);

		BorderPane root;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				this.getClass().getResource("/main/gui/views/loopingAnimations/EditLoopingAnimationsView.fxml"));
		try {
			root = (BorderPane) loader.load();
			editStage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
		LoopingAnimationsTabController editLoopingAnimationController = loader.getController();
		editLoopingAnimationController.setLoopingAnimations(loopingAnimations);

		editStage.showAndWait();
	}

}
