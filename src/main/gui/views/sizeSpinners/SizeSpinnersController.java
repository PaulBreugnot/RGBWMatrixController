package main.gui.views.sizeSpinners;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import main.core.model.panel.LedPanel;
import main.gui.views.ledMatrix.LedMatrix;
import main.gui.views.mainView.MainViewController;

public class SizeSpinnersController {

	@FXML
	private Spinner<Integer> WidthSpinner;

	@FXML
	private Spinner<Integer> HeightSpinner;
	
	private MainViewController mainViewController;
	private LedMatrix ledMatrix;
	
	public void setMainViewController(MainViewController mainViewController) {
		this.mainViewController = mainViewController;
	}
	
	public void setLedMatrix(LedMatrix ledMatrix) {
		this.ledMatrix = ledMatrix;
	}
	
	public void initSizeSpinners() {
		WidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 128, LedPanel.MATRIX_WIDTH));
		WidthSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> v, Number oldVal, Number newVal) {
				ledMatrix.getController().stopUpdater();
				Platform.runLater(() -> {
				mainViewController.updateLedPanel(
						new LedPanel(mainViewController.getLedPanel().getFps(), (int) newVal, LedPanel.MATRIX_HEIGHT));

				});
			}
		});

		HeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 128, LedPanel.MATRIX_HEIGHT));
		HeightSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> v, Number oldVal, Number newVal) {
				ledMatrix.getController().stopUpdater();
				Platform.runLater(() -> {
				mainViewController.updateLedPanel(
						new LedPanel(mainViewController.getLedPanel().getFps(), LedPanel.MATRIX_WIDTH, (int) newVal));

				});
			}
		});
	}
}
