package main.gui.views.playBar;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class PlayBar extends HBox {

	private FXMLLoader fxmlLoader;
	private PlayBarController playBarController;

	public PlayBar() {
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource("/main/gui/views/playBar/PlayBar.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(new PlayBarController());
			playBarController = fxmlLoader.getController();
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public PlayBarController getController() {
		return playBarController;
	}
}
