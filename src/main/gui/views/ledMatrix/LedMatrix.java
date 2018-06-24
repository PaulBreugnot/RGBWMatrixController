package main.gui.views.ledMatrix;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
			setUpChangeListeners();
		}
		
		public LedMatrixController getController() {
			return ledMatrixController;
		}
		
		private void setUpChangeListeners() {

	        widthProperty().addListener(new ChangeListener<Number>() {
	            @Override
	            public void changed(ObservableValue<? extends Number> value, Number oldWidth, Number newWidth) {
	            	ledMatrixController.renderMatrix((double) newWidth, getHeight());
	            }
	        });

	        heightProperty().addListener(new ChangeListener<Number>() {
	            @Override public void changed(ObservableValue<? extends Number> value, Number oldHeight, Number newHeight) {
	            	ledMatrixController.renderMatrix(getWidth(), (double) newHeight);
	           }
	        });
		}

}
