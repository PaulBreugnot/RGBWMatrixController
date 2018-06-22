package main.gui.customTabPane;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import main.gui.views.loopingAnimations.loopItem.LoopItemController;
import main.gui.views.runningTab.RunningTab;

public class CustomTabPaneController {
	
	@FXML
	private Tab RunningTab;

	@FXML
	private Tab LoopingAnimationsTab;
	
	public Tab getRunningTab() {
		return RunningTab;
	}
	
	public Tab getAnimationsTab() {
		return LoopingAnimationsTab;
	}
	

}
