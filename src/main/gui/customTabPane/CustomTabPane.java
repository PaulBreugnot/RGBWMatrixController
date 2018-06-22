package main.gui.customTabPane;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

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
            	((BorderPane) customTabPaneController.getRunningTab().getContent()).setPrefWidth((Double) newWidth);
            	((BorderPane) customTabPaneController.getAnimationsTab().getContent()).setPrefWidth((Double) newWidth);
            }
        });

        heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> value, Number oldHeight, Number newHeight) {
            	((BorderPane) customTabPaneController.getRunningTab().getContent()).setPrefHeight(((Double) newHeight) - 40);
            	((BorderPane) customTabPaneController.getAnimationsTab().getContent()).setPrefHeight(((Double) newHeight) - 40);
           }
        });
	}
}
