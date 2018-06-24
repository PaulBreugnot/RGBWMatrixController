package main.gui.customTabPane;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import main.gui.views.ledMatrix.LedMatrix;

public class CustomTabPane extends TabPane {

	private FXMLLoader fxmlLoader;
	private CustomTabPaneController customTabPaneController;

	public CustomTabPane() {
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource("/main/gui/customTabPane/CustomTabPane.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(new CustomTabPaneController());
			customTabPaneController = fxmlLoader.getController();
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		setUpChangeListeners();
	}
	
	public CustomTabPaneController getController() {
		return customTabPaneController;
	}
	
	private void setUpChangeListeners() {

        widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> value, Number oldWidth, Number newWidth) {
            	setContentSize((double) newWidth, getHeight() - 40);
            	ForceLedMatrixResize((double) newWidth, getHeight() - 40);
            }
        });

        heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> value, Number oldHeight, Number newHeight) {
            	setContentSize(getWidth(), ((double) newHeight) - 40);
            	ForceLedMatrixResize(getWidth(), ((double) newHeight) - 40);
           }
        });
	}
	
	public void setContentSize(double tabPaneWidth, double tabPaneHeight) {
		((BorderPane) customTabPaneController.getRunningTab().getContent()).setPrefSize(tabPaneWidth, tabPaneHeight);
    	((BorderPane) customTabPaneController.getAnimationsTab().getContent()).setPrefSize(tabPaneWidth, tabPaneHeight);
	}
	
	public void ForceLedMatrixResize(double tabPaneWidth, double tabPaneHeight) {
		double height = tabPaneHeight
				- ((Region)((BorderPane) customTabPaneController.getRunningTab().getContent()).getTop()).getHeight()
				- ((Region)((BorderPane) customTabPaneController.getRunningTab().getContent()).getBottom()).getHeight()
				- 50;
		double width = tabPaneWidth - 20; //insets
		LedMatrix ledMatrix = (LedMatrix)((BorderPane) customTabPaneController.getRunningTab().getContent()).getCenter();
		ledMatrix.setPrefSize(width, height);
		ledMatrix.getController().renderMatrix(width, height);
		((BorderPane) customTabPaneController.getRunningTab().getContent()).setCenter(ledMatrix);
	}
}
