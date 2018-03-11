package main.gui.views.settings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import main.core.model.animations.circularWave.CircularWave;

public class CircularWaveSettingsController {
	@FXML
	private Slider colorSlider;

	@FXML
	private Slider waveLengthSlider;

	@FXML
	private Slider whiteLevelSlider;

	@FXML
	private Slider contrastSlider;

	@FXML
	private Slider speedSlider;

	private CircularWave circularWave;

	@FXML
	private void initialize() {
		setSliders();

	}

	private void setSliders() {
		colorSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				circularWave.setHueColor((double) new_val);
			}
		});

		waveLengthSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				circularWave.setWaveLength((double) new_val);
			}
		});

		whiteLevelSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				circularWave.setWhiteLevel((int) Math.floor((double) new_val));
			}
		});

		contrastSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				circularWave.setContrast((double) new_val);
			}
		});

		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				circularWave.setSpeed((int) Math.floor((double) new_val));
			}
		});
	}

	public void setCircularWave(CircularWave circularWave) {
		this.circularWave = circularWave;
	}
}
