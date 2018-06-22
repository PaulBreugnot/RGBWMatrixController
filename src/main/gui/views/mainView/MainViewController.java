package main.gui.views.mainView;

import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import main.core.model.animations.loopingAnimations.LoopingAnimations;
import main.core.model.panel.LedPanel;
import main.gui.customTabPane.CustomTabPane;
import main.gui.views.loopingAnimations.loopingAnimationsTab.LoopingAnimationsTab;
import main.gui.views.runningTab.RunningTab;

public class MainViewController {

	@FXML
	private CustomTabPane mainTabPane;

	private RunningTab runningTabContent;

	private LoopingAnimationsTab animationsTabContent;

	private LoopingAnimations loopingAnimations;

	private LedPanel ledPanel;


	public void setLedPanel(LedPanel ledPanel) throws IOException {
		this.ledPanel = ledPanel;
		initRunningTab();
		initAnimationsTab();
		initMainTabPane();
		if (!ledPanel.isConnected()) {
			//ledPanel.setWiFiConnection();
		}
		System.out.println("Connection method : " + ledPanel.getConMethod());
	}

	public LedPanel getLedPanel() {
		return ledPanel;
	}
	
	private void initMainTabPane() {
		mainTabPane.getController().getRunningTab().setContent(runningTabContent);
		mainTabPane.getController().getAnimationsTab().setContent(animationsTabContent);
	}
	
	private void initRunningTab() {
		runningTabContent = new RunningTab();
		runningTabContent.getController().setLedPanel(ledPanel);
		runningTabContent.getController().setMainViewController(this);
		runningTabContent.getController().initRunningTab();
	}

	private void initAnimationsTab() {
		animationsTabContent = new LoopingAnimationsTab();
		loopingAnimations = new LoopingAnimations();
		animationsTabContent.getController().setLoopingAnimations(loopingAnimations);
		animationsTabContent.getController().setMainViewController(this);

		mainTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
				if (t1.getContent() == animationsTabContent) {
					animationsTabContent.getController().setRunPreview(true);
				} else {
					animationsTabContent.getController().setRunPreview(false);
				}
			}
		});
	}

}
