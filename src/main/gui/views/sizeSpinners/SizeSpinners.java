package main.gui.views.sizeSpinners;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class SizeSpinners extends HBox {

	private FXMLLoader fxmlLoader;
	private SizeSpinnersController sizeSpinnersController;

	public SizeSpinners() {
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource("/main/gui/views/sizeSpinners/SizeSpinners.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(new SizeSpinnersController());
			sizeSpinnersController = fxmlLoader.getController();
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public SizeSpinnersController getController() {
		return sizeSpinnersController;
	}
}
