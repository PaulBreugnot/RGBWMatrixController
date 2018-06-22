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
import main.gui.views.runningTab.RunningTab;
import main.gui.views.sizeSpinners.SizeSpinners;

public class MainViewController {

	@FXML
	private TabPane mainTabPane;
	
	@FXML
	private RunningTab runningTab;

	@FXML
	private Tab AnimationsTab;

	private EditLoopingAnimationsController editLoopingAnimationController;

	private LoopingAnimations loopingAnimations;

	private LedPanel ledPanel;


	public void setLedPanel(LedPanel ledPanel) throws IOException {
		this.ledPanel = ledPanel;
		initRunningTab();
		initAnimationsTab();
		if (!ledPanel.isConnected()) {
			//ledPanel.setWiFiConnection();
		}
		System.out.println("Connection method : " + ledPanel.getConMethod());
	}

	public LedPanel getLedPanel() {
		return ledPanel;
	}
	
	private void initRunningTab() {
		runningTab.getController().setLedPanel(ledPanel);
		runningTab.getController().setMainViewController(this);
		runningTab.getController().initRunningTab();
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

}
