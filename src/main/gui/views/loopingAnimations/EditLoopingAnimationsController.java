package main.gui.views.loopingAnimations;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
	private TextField durationTextField;

	@FXML
	private ListView<AnchorPane> LoopItems;
	
	public static ObservableList<AnchorPane> LoopItemsList = FXCollections.observableArrayList();

	private LoopingAnimations loopingAnimations;

	private Animation selectedAnimation;
	private int lastIndex = 1;

	public void setLoopingAnimations(LoopingAnimations loopingAnimations) {
		this.loopingAnimations = loopingAnimations;
		durationTextField.setText(LoopingAnimations.DEFAULT_DURATION.toString());
		configureLoopItemsListView();
		setListViews();
		displayConfigPanes();
	}

	public void displayConfigPanes() {
		lastIndex = 0;
		LoopItemsList.clear();
		for (AnimationTime animationTime : loopingAnimations.getAnimations().keySet()) {
			setConfigPane(loopingAnimations.getAnimations().get(animationTime), animationTime);
			lastIndex++;
		}
	}
	
	private void configureLoopItemsListView() {
		LoopItems.setItems(LoopItemsList);
		
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

	private int getBeginTime() {
		int time = 0;
		for (AnimationTime animationTime : loopingAnimations.getAnimations().keySet()) {
			time += animationTime.getValue();
		}
		return time;
	}

	@FXML
	private void handleAdd() {
		AnimationTime animationTime = new AnimationTime(getBeginTime(), Integer.parseInt(durationTextField.getText()));

		Animation newAnimation = selectedAnimation.newAnimationInstance();
		setConfigPane(newAnimation, animationTime);
		LoopItems.getSelectionModel().selectLast();
		loopingAnimations.addAnimation(animationTime, newAnimation);
		lastIndex++;
	}

	private void setConfigPane(Animation animation, AnimationTime animationTime) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/main/gui/views/loopingAnimations/LoopItem.fxml"));
		try {
			AnchorPane animationSettingsAnchorPane = loader.load();
			LoopItemsList.add(animationSettingsAnchorPane);
			LoopItems.scrollTo(animationSettingsAnchorPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
		LoopItemController loopItemController = loader.getController();
		loopItemController.setLoopingAnimations(loopingAnimations);
		loopItemController.setEditLoopingAnimationsController(this);
		try {
			loopItemController.setAnimation(animation, lastIndex, animationTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
