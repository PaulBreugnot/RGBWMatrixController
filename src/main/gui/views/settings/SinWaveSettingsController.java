package main.gui.views.settings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import main.core.model.animations.SinWave.SinWave;

public class SinWaveSettingsController {

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

	private SinWave sinWave;

	@FXML
	private void initialize() {
		setSliders();

	}

	private void setSliders() {
		colorSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				sinWave.setHueColor((double) new_val);
			}
		});

		waveLengthSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				sinWave.setWaveLength((double) new_val);
			}
		});

		whiteLevelSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				sinWave.setWhiteLevel((int) Math.floor((double) new_val));
			}
		});

		contrastSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				sinWave.setContrast((double) new_val);
			}
		});

		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				sinWave.setSpeed((int) Math.floor((double) new_val));
			}
		});
	}

	public void setSinWave(SinWave sinWave) {
		this.sinWave = sinWave;
	}
}
