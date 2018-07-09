package main.gui.views.settings.bouncingParticles;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import main.core.model.animations.bouncingParticles.simpleBouncingParticles.SimpleBouncingParticles;

public class BouncingParticlesSettingsController {

	// Particles menu
	@FXML
	private RadioButton RandomColorType;
	@FXML
	private RadioButton RawColorType;
	@FXML
	private RadioButton ShadedColorType;
	@FXML
	private Slider HueSlider;
	@FXML
	private Slider BrightnessSlider;
	@FXML
	private Slider SaturationSlider;
	@FXML
	private CheckBox FixedRadiusBox;
	@FXML
	private Spinner<Double> MinRadiusSpinner;
	@FXML
	private Spinner<Double> MaxRadiusSpinner;
	@FXML
	private Spinner<Integer> ParticleNumberSpinner;

	private SimpleBouncingParticles simpleBouncingParticles;

	public void setSimpleBouncingParticles(SimpleBouncingParticles simpleBouncingParticles) {
		this.simpleBouncingParticles = simpleBouncingParticles;
		setDisplayedParameters();
	}
	
	private void setDisplayedParameters() {
		HueSlider.setValue(simpleBouncingParticles.getColor().getHue());
		BrightnessSlider.setValue(simpleBouncingParticles.getColor().getBrightness());
		SaturationSlider.setValue(simpleBouncingParticles.getColor().getSaturation());
		MinRadiusSpinner.getValueFactory().setValue(simpleBouncingParticles.getMinRadius());
		MaxRadiusSpinner.getValueFactory().setValue(simpleBouncingParticles.getMaxRadius());
		ParticleNumberSpinner.getValueFactory().setValue(simpleBouncingParticles.getParticleNumber());
		switch (simpleBouncingParticles.getColorType()) {
		case Random:
			RandomColorType.setSelected(true);
			RawColorType.setSelected(false);
			ShadedColorType.setSelected(false);
			break;
		case Raw:
			RandomColorType.setSelected(false);
			RawColorType.setSelected(true);
			ShadedColorType.setSelected(false);
			break;
		case Shaded:
			RandomColorType.setSelected(false);
			RawColorType.setSelected(false);
			ShadedColorType.setSelected(true);
			break;
		}
	}
	
	@FXML
	private void initialize() {
		initColorSliders();
		initRadiusSpinners();
		initParticleNumberSpinner();
	}

	private void initColorSliders() {
		HueSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				Color newColor = Color.hsb((double) new_val, SaturationSlider.getValue(), BrightnessSlider.getValue());
				simpleBouncingParticles.setColor(newColor);
			}
		});

		BrightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				Color newColor = Color.hsb(HueSlider.getValue(), SaturationSlider.getValue(), (double) new_val);
				simpleBouncingParticles.setColor(newColor);
			}
		});

		SaturationSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				Color newColor = Color.hsb(HueSlider.getValue(), (double) new_val, BrightnessSlider.getValue());
				simpleBouncingParticles.setColor(newColor);
			}
		});
	}

	private void initRadiusSpinners() {
		MinRadiusSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 5, 1, 0.5));
		MinRadiusSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (FixedRadiusBox.isSelected()) {
					MaxRadiusSpinner.getValueFactory().setValue((double) new_val);
					simpleBouncingParticles.setInitialize(true);
				} else {
					if ((double) new_val > MaxRadiusSpinner.getValue()) {
						MinRadiusSpinner.getValueFactory().setValue((double) old_val);
					} else {
						simpleBouncingParticles.setMinRadius((double) new_val);
						simpleBouncingParticles.setInitialize(true);
					}
				}
			}
		});
		
		MaxRadiusSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 5, 1, 0.5));
		MaxRadiusSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (FixedRadiusBox.isSelected()) {
					MinRadiusSpinner.getValueFactory().setValue((double) new_val);
					simpleBouncingParticles.setInitialize(true);
				} else {
					if ((double) new_val < MinRadiusSpinner.getValue()) {
						MaxRadiusSpinner.getValueFactory().setValue((double) old_val);
					} else {
						simpleBouncingParticles.setMaxRadius((double) new_val);
						simpleBouncingParticles.setInitialize(true);
					}
				}
			}
		});
	}
	
	@FXML
	private void initParticleNumberSpinner() {
		ParticleNumberSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 5, 1));
		ParticleNumberSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				simpleBouncingParticles.setParticleNumber((int) new_val);
				simpleBouncingParticles.setInitialize(true);
			}
		});
	}

	@FXML
	private void handleFixedRadius() {
		if(FixedRadiusBox.isSelected()) {
			MaxRadiusSpinner.getValueFactory().setValue(MinRadiusSpinner.getValue());
		}
	}

	@FXML
	private void handleRandomButton() {
		RawColorType.setSelected(false);
		ShadedColorType.setSelected(false);
		simpleBouncingParticles.setColorType(SimpleBouncingParticles.ColorType.Random);
	}

	@FXML
	private void handleRawButton() {
		RandomColorType.setSelected(false);
		ShadedColorType.setSelected(false);
		simpleBouncingParticles.setColorType(SimpleBouncingParticles.ColorType.Raw);
	}

	@FXML
	private void handleShadedButton() {
		RawColorType.setSelected(false);
		RandomColorType.setSelected(false);
		simpleBouncingParticles.setColorType(SimpleBouncingParticles.ColorType.Shaded);
	}

}
