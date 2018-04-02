package main.gui.views.loopingAnimations;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.core.model.animations.Animation;
import main.core.model.animations.loopingAnimations.LoopingAnimations;
import main.core.model.panel.LedPanel;
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
	private AnchorPane previewAnchorPane;

	@FXML
	private ListView<AnchorPane> LoopItems;
	
	public static ObservableList<AnchorPane> LoopItemsList = FXCollections.observableArrayList();
	
	private HashMap<AnchorPane, Animation> SettingsControllersMap = new HashMap<>();
	
	private LedPanel ledPanel;
	private Rectangle[][] previewAnchorPaneContent = new Rectangle[LedPanel.MATRIX_HEIGHT][LedPanel.MATRIX_WIDTH];
	
	private LoopingAnimations loopingAnimations;

	private Animation selectedAnimation;
	private int lastIndex = 1;
	private GUIupdater updater;
	private boolean updaterInitialized = false;

	public void setLoopingAnimations(LoopingAnimations loopingAnimations) {
		this.loopingAnimations = loopingAnimations;
		ledPanel = new LedPanel(25);
		durationTextField.setText(LoopingAnimations.DEFAULT_DURATION.toString());
		configureLoopItemsListView();
		setListViews();
		displayConfigPanes();
		initPreviewAnchorPane();
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
		LoopItems.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				ledPanel.setCurrentAnimation(SettingsControllersMap.get(newSelection));
				if(!updaterInitialized) {
					updaterInitialized = true;
					updater = new GUIupdater(this);
					updater.start();
				}
			}
		});
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
			SettingsControllersMap.put(animationSettingsAnchorPane, animation);
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
	
	private void initPreviewAnchorPane() {
		double tileWidth = previewAnchorPane.getPrefWidth()/LedPanel.MATRIX_WIDTH;
		double tileHeight = previewAnchorPane.getPrefHeight()/LedPanel.MATRIX_HEIGHT;
		for (int i = LedPanel.MATRIX_HEIGHT - 1; i >= 0; i--) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				Rectangle pixel = new Rectangle(tileWidth, tileHeight);
				pixel.setStroke(Color.BLACK);
				pixel.setFill(Color.WHITE);
				previewAnchorPaneContent[i][j] = pixel;
				AnchorPane.setTopAnchor(pixel, i * tileHeight);
				AnchorPane.setLeftAnchor(pixel, j * tileWidth);
				previewAnchorPane.getChildren().add(pixel);
			}
		}
	}
	
	private class GUIupdater extends Thread {
		private EditLoopingAnimationsController editLoopingAnimationsController;

		public GUIupdater(EditLoopingAnimationsController editLoopingAnimationsController) {
			this.editLoopingAnimationsController = editLoopingAnimationsController;
		}
		
		private void displayMatrix() {
			for (int i = 0; i < LedPanel.MATRIX_HEIGHT; i++) {
				for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
					previewAnchorPaneContent[i][j].setFill(ledPanel.getLedMatrix()[i][j].getDisplayColor());
				}
			}
		}

		@Override
		public void run() {
			if (ledPanel.getCurrentAnimation() != null) {
			ledPanel.updateDisplay();
			}
			try {
				Thread.sleep(1000 / ledPanel.getFps());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Platform.runLater(() -> {
				displayMatrix();
				run();
			});

		}
	}
}
