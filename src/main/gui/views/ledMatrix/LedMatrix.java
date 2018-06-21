package main.gui.views.ledMatrix;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class LedMatrix extends AnchorPane{
		
		private FXMLLoader fxmlLoader;
		private LedMatrixController ledMatrixController;

		public LedMatrix() {
			try {
				fxmlLoader = new FXMLLoader(getClass().getResource("/main/gui/views/ledMatrix/LedMatrix.fxml"));
				fxmlLoader.setRoot(this);
				fxmlLoader.setController(new LedMatrixController());
				ledMatrixController = fxmlLoader.getController();
				ledMatrixController.setComponent(this);
				fxmlLoader.load();
			} catch (IOException exception) {
				throw new RuntimeException(exception);
			}
		}
		
		public LedMatrixController getController() {
			return ledMatrixController;
		}

}
