package main.gui.views.loopingAnimations;

import java.io.IOException;
import java.util.HashMap;

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
import main.core.model.animations.circularWave.CircularWave;
import main.core.model.animations.diamondWave.DiamondWave;
import main.core.model.animations.fan.Fan;
import main.core.model.animations.loopingAnimations.LoopingAnimations;
import main.core.model.animations.pixelRain.PixelRain;
import main.core.model.animations.text.ScrollingText;
import main.core.model.panel.LedPanel;
import main.core.util.AnimationTime;
import main.gui.views.mainView.MainViewController;

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

	public static ObservableList<Animation> ListRandomEffects = FXCollections.observableArrayList();
	public static ObservableList<Animation> ListGeometricEffects = FXCollections.observableArrayList();
	public static ObservableList<Animation> ListTextEffects = FXCollections.observableArrayList();
	public static ObservableList<Animation> ListSpecialEffects = FXCollections.observableArrayList();

	private HashMap<AnchorPane, Animation> SettingsControllersMap = new HashMap<>();

	private LedPanel ledPanel;
	private Rectangle[][] previewAnchorPaneContent;
	private boolean run;

	private MainViewController mainViewController;
	private LoopingAnimations loopingAnimations;

	private Animation selectedAnimation;
	private int lastIndex = 1;
	private GUIupdater updater = new GUIupdater(this);

	public void setLoopingAnimations(LoopingAnimations loopingAnimations) {
		this.loopingAnimations = loopingAnimations;
		ledPanel = new LedPanel(25);
		LedPanel.setBlackPanel(ledPanel.getLedMatrix());
		durationTextField.setText(LoopingAnimations.DEFAULT_DURATION.toString());
		configureLoopItemsListView();
		setListViews();
		displayConfigPanes();
		initPreviewAnchorPane();
	}

	public void setMainViewController(MainViewController mainViewController) {
		this.mainViewController = mainViewController;
		handleLaunch();
	}

	public void setRunPreview(boolean run) {
		if (!this.run && run) {
			updater.run();
		}
		this.run = run;
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
			} else {
				ledPanel.setCurrentAnimation(null);
				LedPanel.setBlackPanel(ledPanel.getLedMatrix());
			}
		});
	}

	private void setListViews() {

		ListGeometricEffects.add(new PixelRain());
		ListGeometricEffects.add(new CircularWave());
		ListGeometricEffects.add(new DiamondWave());
		ListGeometricEffects.add(new Fan());

		ListTextEffects.add(new ScrollingText());

		ListSpecialEffects.add(new LoopingAnimations());

		RandomListView.setItems(ListRandomEffects);
		GeometricListView.setItems(ListGeometricEffects);
		TextListView.setItems(ListTextEffects);
		SpecialListView.setItems(ListSpecialEffects);

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
		previewAnchorPaneContent = new Rectangle[LedPanel.MATRIX_HEIGHT][LedPanel.MATRIX_WIDTH];
		double tileSize;
		double xOffset;
		double yOffset;
		if (LedPanel.MATRIX_WIDTH <= 2 * LedPanel.MATRIX_HEIGHT) {
			tileSize = previewAnchorPane.getPrefHeight() / LedPanel.MATRIX_HEIGHT;
			xOffset = previewAnchorPane.getPrefWidth() / 2 - tileSize * LedPanel.MATRIX_WIDTH / 2;
			yOffset = 0;
		} else {
			tileSize = previewAnchorPane.getPrefWidth() / LedPanel.MATRIX_WIDTH;
			xOffset = 0;
			yOffset = previewAnchorPane.getPrefHeight() / 2 - tileSize * LedPanel.MATRIX_HEIGHT / 2;
		}
		for (int i = LedPanel.MATRIX_HEIGHT - 1; i >= 0; i--) {
			for (int j = 0; j < LedPanel.MATRIX_WIDTH; j++) {
				Rectangle pixel = new Rectangle(tileSize, tileSize);
				pixel.setStroke(Color.BLACK);
				pixel.setFill(Color.WHITE);
				previewAnchorPaneContent[i][j] = pixel;
				AnchorPane.setTopAnchor(pixel, yOffset + (LedPanel.MATRIX_HEIGHT - 1 - i) * tileSize);
				AnchorPane.setLeftAnchor(pixel, xOffset + j * tileSize);
				previewAnchorPane.getChildren().add(pixel);
			}
		}
		updater.start();
	}

	@FXML
	private void handleLaunch() {
		// TODO : animationsHardCopy
		LoopingAnimations displayedAnimations = loopingAnimations;
		mainViewController.getLedPanel().setCurrentAnimation(displayedAnimations);

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
				if (editLoopingAnimationsController.run) {
					run();
				}
			});

		}
	}
}
