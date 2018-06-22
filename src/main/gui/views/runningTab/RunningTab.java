package main.gui.views.runningTab;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class RunningTab extends BorderPane {

	private FXMLLoader fxmlLoader;
	private RunningTabController runningTabController;

	public RunningTab() {
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource("/main/gui/views/runningTab/RunningTab.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(new RunningTabController());
			runningTabController = fxmlLoader.getController();
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public RunningTabController getController() {
		return runningTabController;
	}
}
