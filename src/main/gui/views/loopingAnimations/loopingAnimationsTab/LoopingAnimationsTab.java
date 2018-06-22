package main.gui.views.loopingAnimations.loopingAnimationsTab;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class LoopingAnimationsTab extends BorderPane {
	
	private FXMLLoader fxmlLoader;
	private LoopingAnimationsTabController loopingAnimationsTabController;

	public LoopingAnimationsTab() {
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource("/main/gui/views/loopingAnimations/loopingAnimationsTab/LoopingAnimationsTab.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(new LoopingAnimationsTabController());
			loopingAnimationsTabController = fxmlLoader.getController();
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public LoopingAnimationsTabController getController() {
		return loopingAnimationsTabController;
	}
}
