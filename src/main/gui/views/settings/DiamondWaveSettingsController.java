package main.gui.views.settings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import main.core.model.animations.diamondWave.DiamondWave;

public class DiamondWaveSettingsController {
	@FXML
	private Slider colorSlider;

	@FXML
	private Slider whiteLevelSlider;

	@FXML
	private Slider waveLengthSlider;

	@FXML
	private Slider xCenterSlider;

	@FXML
	private Slider yCenterSlider;

	@FXML
	private Slider contrastSlider;

	@FXML
	private Slider speedSlider;

	@FXML
	private Slider intensitySlider;

	@FXML
	private RadioButton saturationButton;

	@FXML
	private RadioButton brightnessButton;

	@FXML
	private RadioButton rainbowButton;

	private DiamondWave diamondWave;

	@FXML
	private void initialize() {
		setSliders();

	}

	private void setSliders() {
		colorSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				diamondWave.setHueColor((double) new_val);
			}
		});

		whiteLevelSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				diamondWave.setWhiteLevel((int) Math.floor((double) new_val));
			}
		});

		waveLengthSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				diamondWave.setWaveLength((double) new_val);
			}
		});

		xCenterSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				diamondWave.setXCenter((int) Math.floor((double) new_val));
			}
		});

		yCenterSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				diamondWave.setYCenter((int) Math.floor((double) new_val));
			}
		});

		contrastSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				diamondWave.setContrast((double) new_val);
			}
		});

		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				diamondWave.setSpeed(Math.floor(((double) new_val) * 10) / 10);
			}
		});

		intensitySlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				diamondWave.setIntensity((double) new_val);
			}
		});
	}

	public void setDiamondWave(DiamondWave diamondWave) {
		this.diamondWave = diamondWave;
	}

	@FXML
	private void handleSaturationButton() {
		brightnessButton.setSelected(false);
		rainbowButton.setSelected(false);
		diamondWave.setWaveMode(DiamondWave.WaveMode.SATURATION);
	}

	@FXML
	private void handleBrightnessButton() {
		saturationButton.setSelected(false);
		rainbowButton.setSelected(false);
		diamondWave.setWaveMode(DiamondWave.WaveMode.BRIGHTNESS);
	}

	@FXML
	private void handleRainbowButton() {
		brightnessButton.setSelected(false);
		saturationButton.setSelected(false);
		diamondWave.setWaveMode(DiamondWave.WaveMode.RAINBOW);
	}
}