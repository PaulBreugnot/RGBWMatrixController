package main.gui.views.mainView;

import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import main.core.model.animations.loopingAnimations.LoopingAnimations;
import main.core.model.panel.LedPanel;
import main.gui.views.connectionInterface.ConnectionModule;
import main.gui.views.ledMatrix.LedMatrix;
import main.gui.views.loopingAnimations.EditLoopingAnimationsController;

public class MainViewController {

	@FXML
	private LedMatrix ledMatrix;

	@FXML
	private Button PlayButton;

	@FXML
	private Button FrameByFrameButton;

	@FXML
	private TabPane mainTabPane;

	@FXML
	private Tab AnimationsTab;

	@FXML
	private Spinner<Integer> WidthSpinner;

	@FXML
	private Spinner<Integer> HeightSpinner;
	
	@FXML
	private ConnectionModule connectionModule;

	private EditLoopingAnimationsController editLoopingAnimationController;

	private LoopingAnimations loopingAnimations;

	private LedPanel ledPanel;

	@FXML
	private void initialize() {
		initSizeSpinners();
	}

	public void setLedPanel(LedPanel ledPanel) throws IOException {
		this.ledPanel = ledPanel;
		initAnimationsTab();
		initTilePane();
		System.out.println("initTilePane OK");
		initConnectionModule();
		if (!ledPanel.isConnected()) {
			//ledPanel.setWiFiConnection();
		}
		System.out.println("Connection method : " + ledPanel.getConMethod());
	}

	public LedPanel getLedPanel() {
		return ledPanel;
	}
	
	private void initConnectionModule() {
		connectionModule.getController().setLedPanel(ledPanel);
		connectionModule.getController().initComPort();
	}

	private void initAnimationsTab() {
		BorderPane root;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				this.getClass().getResource("/main/gui/views/loopingAnimations/EditLoopingAnimationsView.fxml"));
		try {
			root = (BorderPane) loader.load();
			AnimationsTab.setContent(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		loopingAnimations = new LoopingAnimations();
		editLoopingAnimationController = loader.getController();
		editLoopingAnimationController.setLoopingAnimations(loopingAnimations);
		editLoopingAnimationController.setMainViewController(this);

		mainTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
				if (t1 == AnimationsTab) {
					editLoopingAnimationController.setRunPreview(true);
				} else {
					editLoopingAnimationController.setRunPreview(false);
				}
			}
		});
	}

	private void initTilePane() {
		ledMatrix.getController().setLedPanel(ledPanel);
		ledMatrix.getController().initMatrix();
	}

	public void initSizeSpinners() {
		WidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 128, LedPanel.MATRIX_WIDTH));
		WidthSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> v, Number oldVal, Number newVal) {
				ledMatrix.getController().stopUpdater();
				Platform.runLater(() -> {
					try {
						setLedPanel(new LedPanel(ledPanel.getFps(), (int) newVal, LedPanel.MATRIX_HEIGHT));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		});

		HeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 128, LedPanel.MATRIX_HEIGHT));
		HeightSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> v, Number oldVal, Number newVal) {
				ledMatrix.getController().stopUpdater();
				Platform.runLater(() -> {
					try {
						setLedPanel(new LedPanel(ledPanel.getFps(), LedPanel.MATRIX_WIDTH, (int) newVal));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		});
	}


	@FXML
	private void handlePlay() {
		FrameByFrameButton.setDisable(true);
		ledMatrix.getController().runUpdater();
	}

	@FXML
	private void handleFrameByFrame() {
		ledPanel.updateDisplay();
		Platform.runLater(() -> ledMatrix.getController().displayMatrix());
		try {
			Thread.sleep(1000 / ledPanel.getFps());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleStop() {
		FrameByFrameButton.setDisable(false);
		ledMatrix.getController().stopUpdater();
	}

}
