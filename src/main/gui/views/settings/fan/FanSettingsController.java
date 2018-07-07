package main.gui.views.settings.fan;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import main.core.model.animations.fan.Fan;
import main.core.model.panel.LedPanel;

public class FanSettingsController {
	@FXML
	private Slider colorSlider;

	@FXML
	private Slider fanNumberSlider;

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

	private Fan fan;

	@FXML
	private void initialize() {
		setSliders();

	}

	private void setSliders() {
		colorSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				fan.setHueColor((double) new_val);
			}
		});

		fanNumberSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				fan.setFanNumber((int) Math.floor((double) new_val));
			}
		});

		whiteLevelSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				fan.setWhiteLevel((int) Math.floor((double) new_val));
			}
		});

		xCenterSlider.setMin(-1);
		xCenterSlider.setMax(LedPanel.MATRIX_WIDTH + 1);
		xCenterSlider.setValue(LedPanel.MATRIX_WIDTH / 2);
		xCenterSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				fan.setXCenter((int) Math.floor((double) new_val));
			}
		});

		yCenterSlider.setMin(-1);
		yCenterSlider.setMax(LedPanel.MATRIX_HEIGHT + 1);
		yCenterSlider.setValue(LedPanel.MATRIX_HEIGHT / 2);
		yCenterSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				fan.setYCenter((int) Math.floor((double) new_val));
			}
		});

		contrastSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				fan.setContrast((double) new_val);
			}
		});

		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				fan.setSpeed(Math.floor(((double) new_val) * 10) / 10);
			}
		});
		intensitySlider.setMax(LedPanel.MAX_INTENSITY);
		intensitySlider.setValue(LedPanel.MAX_INTENSITY);
		intensitySlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				fan.setIntensity((double) new_val);
			}
		});
	}

	public void setFan(Fan fan) {
		this.fan = fan;
		setDisplayedParameters();
	}

	private void setDisplayedParameters() {
		colorSlider.setValue(fan.getHueColor());
		fanNumberSlider.setValue(fan.getFanNumber());
		whiteLevelSlider.setValue(fan.getWhiteLevel());
		xCenterSlider.setValue(fan.getXCenter());
		yCenterSlider.setValue(fan.getYCenter());
		contrastSlider.setValue(fan.getContrast());
		speedSlider.setValue(fan.getSpeed());
		intensitySlider.setValue(fan.getIntensity());
		switch (fan.getWaveMode()) {
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
		fan.setWaveMode(Fan.WaveMode.SATURATION);
	}

	@FXML
	private void handleBrightnessButton() {
		saturationButton.setSelected(false);
		rainbowButton.setSelected(false);
		fan.setWaveMode(Fan.WaveMode.BRIGHTNESS);
	}

	@FXML
	private void handleRainbowButton() {
		brightnessButton.setSelected(false);
		saturationButton.setSelected(false);
		fan.setWaveMode(Fan.WaveMode.RAINBOW);
	}
}
