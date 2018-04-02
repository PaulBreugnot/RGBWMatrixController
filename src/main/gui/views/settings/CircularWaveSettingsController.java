package main.gui.views.settings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import main.core.model.animations.Animation;
import main.core.model.animations.circularWave.CircularWave;
import main.core.model.panel.LedPanel;

public class CircularWaveSettingsController {
	@FXML
	private Slider colorSlider;

	@FXML
	private Slider waveLengthSlider;

	@FXML
	private Slider whiteLevelSlider;

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

		xCenterSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				circularWave.setXCenter((int) Math.floor((double) new_val));
			}
		});

		yCenterSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				circularWave.setYCenter((int) Math.floor((double) new_val));
			}
		});

		contrastSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				circularWave.setContrast((double) new_val);
			}
		});

		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				circularWave.setSpeed(Math.floor(((double) new_val) * 10) / 10);
			}
		});
		intensitySlider.setMax(LedPanel.MAX_INTENSITY);
		intensitySlider.setValue(LedPanel.MAX_INTENSITY);
		intensitySlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				circularWave.setIntensity((double) new_val);
			}
		});
	}

	public void setCircularWave(CircularWave circularWave) {
		this.circularWave = circularWave;
		setDisplayedParameters();
	}

	private void setDisplayedParameters() {
		colorSlider.setValue(circularWave.getHueColor());
		waveLengthSlider.setValue(circularWave.getWaveLength());
		whiteLevelSlider.setValue(circularWave.getWhiteLevel());
		xCenterSlider.setValue(circularWave.getxCenter());
		yCenterSlider.setValue(circularWave.getyCenter());
		contrastSlider.setValue(circularWave.getContrast());
		speedSlider.setValue(circularWave.getSpeed());
		intensitySlider.setValue(circularWave.getIntensity());
		switch (circularWave.getWaveMode()) {
		case SATURATION:
			saturationButton.setSelected(true);
			brightnessButton.setSelected(false);
			rainbowButton.setSelected(false);
			break;
		case BRIGHTNESS:
			saturationButton.setSelected(false);
			brightnessButton.setSelected(true);
			rainbowButton.setSelected(false);
			break;
		case RAINBOW:
			saturationButton.setSelected(false);
			brightnessButton.setSelected(false);
			rainbowButton.setSelected(true);
			break;
		}
	}

	@FXML
	private void handleSaturationButton() {
		brightnessButton.setSelected(false);
		rainbowButton.setSelected(false);
		circularWave.setWaveMode(CircularWave.WaveMode.SATURATION);
	}

	@FXML
	private void handleBrightnessButton() {
		saturationButton.setSelected(false);
		rainbowButton.setSelected(false);
		circularWave.setWaveMode(CircularWave.WaveMode.BRIGHTNESS);
	}

	@FXML
	private void handleRainbowButton() {
		brightnessButton.setSelected(false);
		saturationButton.setSelected(false);
		circularWave.setWaveMode(CircularWave.WaveMode.RAINBOW);
	}
}
