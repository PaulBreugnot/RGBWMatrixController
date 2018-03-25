package main.gui.views.loopingAnimations;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import main.core.model.animations.Animation;
import main.core.model.animations.loopingAnimations.LoopingAnimations;
import main.core.util.AnimationTime;
import main.gui.views.MainViewController;

public class EditLoopingAnimationsController {

	@FXML
	private ListView<Animation> RandomListView;

	@FXML
	private ListView<Animation> GeometricListView;

	@FXML
	private ListView<Animation> TextListView;

	@FXML
	private ListView<Animation> SpecialListView;

	@FXML
	private TextField timeTextField;

	@FXML
	private HBox LoopItems;

	private LoopingAnimations loopingAnimations;

	private Animation selectedAnimation;
	private int lastIndex = 1;

	public void setLoopingAnimations(LoopingAnimations loopingAnimations) {
		this.loopingAnimations = loopingAnimations;
		timeTextField.setText("0");
		setListViews();
	}

	private void setListViews() {
		RandomListView.setItems(MainViewController.ListRandomEffects);
		GeometricListView.setItems(MainViewController.ListGeometricEffects);
		TextListView.setItems(MainViewController.ListTextEffects);
		SpecialListView.setItems(MainViewController.ListSpecialEffects);

		RandomListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				GeometricListView.getSelectionModel().clearSelection();
				TextListView.getSelectionModel().clearSelection();
				SpecialListView.getSelectionModel().clearSelection();
				selectedAnimation = newSelection;
			}
		});

		GeometricListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				RandomListView.getSelectionModel().clearSelection();
				TextListView.getSelectionModel().clearSelection();
				SpecialListView.getSelectionModel().clearSelection();
				selectedAnimation = newSelection;
			}
		});

		TextListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				GeometricListView.getSelectionModel().clearSelection();
				RandomListView.getSelectionModel().clearSelection();
				SpecialListView.getSelectionModel().clearSelection();
				selectedAnimation = newSelection;
			}
		});

		SpecialListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				GeometricListView.getSelectionModel().clearSelection();
				TextListView.getSelectionModel().clearSelection();
				RandomListView.getSelectionModel().clearSelection();
				selectedAnimation = newSelection;
			}
		});
	}

	@FXML
	private void handleAdd() {
		AnimationTime animationTime = new AnimationTime(Integer.parseInt(timeTextField.getText()),
				LoopingAnimations.DEFAULT_DURATION);

		setConfigPane(selectedAnimation, animationTime);
		loopingAnimations.addAnimation(animationTime, selectedAnimation.newAnimationInstance());
		lastIndex++;
	}

	private void setConfigPane(Animation animation, AnimationTime animationTime) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/main/gui/views/loopingAnimations/LoopItem.fxml"));
		try {
			LoopItems.getChildren().add(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		LoopItemController loopItemController = loader.getController();
		loopItemController.setLoopingAnimations(loopingAnimations);
		try {
			loopItemController.setAnimation(animation, lastIndex, animationTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
