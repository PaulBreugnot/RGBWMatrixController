package main.gui.views.mainView;

import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import main.core.model.animations.loopingAnimations.LoopingAnimations;
import main.core.model.panel.LedPanel;
import main.gui.views.connectionInterface.ConnectionModule;
import main.gui.views.ledMatrix.LedMatrix;
import main.gui.views.loopingAnimations.editAnimationsPane.EditLoopingAnimationsController;
import main.gui.views.playBar.PlayBar;
import main.gui.views.sizeSpinners.SizeSpinners;

public class MainViewController {

	@FXML
	private LedMatrix ledMatrix;
	
	@FXML
	private SizeSpinners sizeSpinners;

	@FXML
	private TabPane mainTabPane;

	@FXML
	private Tab AnimationsTab;
	
	@FXML
	private ConnectionModule connectionModule;
	
	@FXML
	private PlayBar playBar;

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
		initPlayBar();
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
	
	private void initPlayBar() {
		playBar.getController().setLedPanel(ledPanel);
		playBar.getController().setLedMatrix(ledMatrix);
	}

	private void initAnimationsTab() {
		BorderPane root;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				this.getClass().getResource("/main/gui/views/loopingAnimations/editAnimationsPane/EditLoopingAnimationsView.fxml"));
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
		sizeSpinners.getController().setMainViewController(this);
		sizeSpinners.getController().setLedMatrix(ledMatrix);
		sizeSpinners.getController().initSizeSpinners();
	}

}
