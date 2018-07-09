package main.gui.views.loopingAnimations.loopingAnimationsTab;

import java.io.IOException;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.core.model.animations.Animation;
import main.core.model.animations.bouncingParticles.simpleBouncingParticles.SimpleBouncingParticles;
import main.core.model.animations.circularWave.CircularWave;
import main.core.model.animations.diamondWave.DiamondWave;
import main.core.model.animations.fan.Fan;
import main.core.model.animations.loopingAnimations.LoopingAnimations;
import main.core.model.animations.pixelRain.PixelRain;
import main.core.model.animations.text.ScrollingText;
import main.core.model.panel.LedPanel;
import main.core.util.AnimationTime;
import main.gui.views.ledMatrix.LedMatrix;
import main.gui.views.loopingAnimations.loopItem.LoopItem;
import main.gui.views.mainView.MainViewController;

public class LoopingAnimationsTabController {

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
	private LedMatrix previewLedMatrix;

	@FXML
	private ListView<LoopItem> LoopItems;

	public static ObservableList<LoopItem> LoopItemsList = FXCollections.observableArrayList();

	public static ObservableList<Animation> ListRandomEffects = FXCollections.observableArrayList();
	public static ObservableList<Animation> ListGeometricEffects = FXCollections.observableArrayList();
	public static ObservableList<Animation> ListTextEffects = FXCollections.observableArrayList();
	public static ObservableList<Animation> ListSpecialEffects = FXCollections.observableArrayList();

	private HashMap<LoopItem, Animation> SettingsControllersMap = new HashMap<>();

	private LedPanel ledPanel;

	private MainViewController mainViewController;
	private LoopingAnimations loopingAnimations;

	private Animation selectedAnimation;
	private int lastIndex = 1;

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
		if (run && !LoopItems.getSelectionModel().isEmpty()) {
			previewLedMatrix.getController().runUpdater();
		}
		else {
			previewLedMatrix.getController().stopUpdater();
		}
	}

	public void displayConfigPanes() {
		previewLedMatrix.getController().stopUpdater();
		lastIndex = 0;
		LoopItemsList.clear();
		for (AnimationTime animationTime : loopingAnimations.getAnimations().keySet()) {
			setConfigPane(loopingAnimations.getAnimations().get(animationTime), animationTime);
			lastIndex++;
		}
		/*if(!LoopItems.getSelectionModel().isEmpty()) {
			previewLedMatrix.getController().runUpdater();
		}*/
	}

	private void configureLoopItemsListView() {
		LoopItems.setItems(LoopItemsList);
		LoopItems.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				ledPanel.setCurrentAnimation(SettingsControllersMap.get(newSelection));
				previewLedMatrix.getController().runUpdater();
			} else {
				previewLedMatrix.getController().stopUpdater();
				ledPanel.setCurrentAnimation(null);
				LedPanel.setBlackPanel(ledPanel.getLedMatrix());
			}
		});
	}

	private void setListViews() {

		ListGeometricEffects.clear();
		ListGeometricEffects.add(new PixelRain());
		ListGeometricEffects.add(new CircularWave());
		ListGeometricEffects.add(new DiamondWave());
		ListGeometricEffects.add(new Fan());

		ListTextEffects.clear();
		ListTextEffects.add(new ScrollingText());

		ListSpecialEffects.clear();
		ListSpecialEffects.add(new SimpleBouncingParticles());

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

		LoopItem loopItem = new LoopItem();
		LoopItemsList.add(loopItem);
		SettingsControllersMap.put(loopItem, animation);
		LoopItems.scrollTo(loopItem);
		loopItem.getController().setLoopingAnimations(loopingAnimations);
		loopItem.getController().setEditLoopingAnimationsController(this);
		try {
			loopItem.getController().setAnimation(animation, lastIndex, animationTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initPreviewAnchorPane() {
		previewLedMatrix.getController().setLedPanel(ledPanel);
		previewLedMatrix.getController().initMatrix();
	}

	@FXML
	private void handleLaunch() {
		// TODO : animationsHardCopy
		LoopingAnimations displayedAnimations = loopingAnimations;
		mainViewController.getLedPanel().setCurrentAnimation(displayedAnimations);

	}
}
