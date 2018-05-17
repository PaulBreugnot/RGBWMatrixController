package main.gui.views.settings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import main.core.model.animations.diamondWave.DiamondWave;
import main.core.model.panel.LedPanel;

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

		xCenterSlider.setMin(0);
		xCenterSlider.setMax(LedPanel.MATRIX_WIDTH - 1);
		xCenterSlider.setValue((LedPanel.MATRIX_WIDTH - 1) / 2);
		xCenterSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				diamondWave.setXCenter((int) Math.floor((double) new_val));
			}
		});

		yCenterSlider.setMin(0);
		yCenterSlider.setMax(LedPanel.MATRIX_HEIGHT - 1);
		yCenterSlider.setValue((LedPanel.MATRIX_HEIGHT - 1) / 2);
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

		intensitySlider.setMax(LedPanel.MAX_INTENSITY);
		intensitySlider.setValue(LedPanel.MAX_INTENSITY);
		intensitySlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				diamondWave.setIntensity((double) new_val);
			}
		});
	}

	public void setDiamondWave(DiamondWave diamondWave) {
		this.diamondWave = diamondWave;
		setDisplayedParameters();
	}

	private void setDisplayedParameters() {
		colorSlider.setValue(diamondWave.getHueColor());
		waveLengthSlider.setValue(diamondWave.getWaveLength());
		whiteLevelSlider.setValue(diamondWave.getWhiteLevel());
		xCenterSlider.setValue(diamondWave.getXCenter());
		yCenterSlider.setValue(diamondWave.getYCenter());
		contrastSlider.setValue(diamondWave.getContrast());
		speedSlider.setValue(diamondWave.getSpeed());
		intensitySlider.setValue(diamondWave.getIntensity());
		switch (diamondWave.getWaveMode()) {
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
