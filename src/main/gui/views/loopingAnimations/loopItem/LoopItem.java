package main.gui.views.loopingAnimations.loopItem;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class LoopItem extends AnchorPane {

	private FXMLLoader fxmlLoader;
	private LoopItemController loopItemController;

	public LoopItem() {
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource("/main/gui/views/loopingAnimations/loopItem/LoopItem.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(new LoopItemController());
			loopItemController = fxmlLoader.getController();
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public LoopItemController getController() {
		return loopItemController;
	}
}
