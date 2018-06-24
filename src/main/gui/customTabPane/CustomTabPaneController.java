package main.gui.customTabPane;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;

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
