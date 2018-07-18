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
import main.core.model.panel.LedPanel;

public class BouncingParticlesSettingsController {
	
	// Color menu
	@FXML
	private RadioButton FullRangeColorType;
	@FXML
	private RadioButton RawColorType;
	@FXML
	private RadioButton ShadedColorType;
	@FXML
	private Slider HueSlider;
	@FXML
	private Spinner<Double> HueWidthSpinner;
	@FXML
	private Slider BrightnessSlider;
	@FXML
	private Spinner<Double> BrightnessWidthSpinner;
	@FXML
	private Slider SaturationSlider;
	@FXML
	private Spinner<Double> SaturationWidthSpinner;

	// Particles menu
	@FXML
	private Spinner<Integer> ParticleNumberSpinner;
	@FXML
	private CheckBox FixedRadiusBox;
	@FXML
	private Spinner<Double> MinRadiusSpinner;
	@FXML
	private Spinner<Double> MaxRadiusSpinner;
	@FXML
	private CheckBox FixedSpeedBox;
	@FXML
	private Slider MinSpeedSlider;
	@FXML
	private Slider MaxSpeedSlider;
	
	// Collision Area menu
	@FXML
	private RadioButton EdgeCollisions;
	@FXML
	private RadioButton ParticlesCollisions;
	@FXML
	private Spinner<Integer> WidthSpinner;
	@FXML
	private Spinner<Integer> HeightSpinner;
	@FXML
	private CheckBox CenteredCheckBox;
	@FXML
	private Spinner<Integer> HorizontalOffsetSpinner;
	@FXML
	private Spinner<Integer> VerticalOffsetSpinner;

	private SimpleBouncingParticles simpleBouncingParticles;

	public void setSimpleBouncingParticles(SimpleBouncingParticles simpleBouncingParticles) {
		this.simpleBouncingParticles = simpleBouncingParticles;
		setDisplayedParameters();
	}
	
	private void setDisplayedParameters() {
		// Color
		HueSlider.setValue(simpleBouncingParticles.getHue());
		HueWidthSpinner.getValueFactory().setValue(simpleBouncingParticles.getHueWidth());
		BrightnessSlider.setValue(simpleBouncingParticles.getBrightness());
		BrightnessWidthSpinner.getValueFactory().setValue(simpleBouncingParticles.getBrightWidth());
		SaturationSlider.setValue(simpleBouncingParticles.getSaturation());
		SaturationWidthSpinner.getValueFactory().setValue(simpleBouncingParticles.getSatWidth());
		
		// Particle
		MinRadiusSpinner.getValueFactory().setValue(simpleBouncingParticles.getMinRadius());
		MaxRadiusSpinner.getValueFactory().setValue(simpleBouncingParticles.getMaxRadius());
		MinSpeedSlider.setValue(simpleBouncingParticles.getvMin());
		MaxSpeedSlider.setValue(simpleBouncingParticles.getvMax());
		ParticleNumberSpinner.getValueFactory().setValue(simpleBouncingParticles.getParticleNumber());
		WidthSpinner.getValueFactory().setValue(simpleBouncingParticles.getAreaWidth());
		HeightSpinner.getValueFactory().setValue(simpleBouncingParticles.getAreaHeight());
		HorizontalOffsetSpinner.getValueFactory().setValue(simpleBouncingParticles.getHorizontalOffset());
		VerticalOffsetSpinner.getValueFactory().setValue(simpleBouncingParticles.getVerticalOffset());
		EdgeCollisions.setSelected(!simpleBouncingParticles.getParticleCollision());
		ParticlesCollisions.setSelected(simpleBouncingParticles.getParticleCollision());
		initCheckBoxes();
	}
	
	@FXML
	private void initialize() {
		// Color
		initColorSliders();
		initColorWidthSpinners();
		// Particles
		initRadiusSpinners();
		initSpeedSliders();
		initParticleNumberSpinner();
		// Area
		initSizeSpinners();
	}
	
	// Color
	private void initColorSliders() {
		HueSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				simpleBouncingParticles.setHue((double) new_val);
				simpleBouncingParticles.setInitColor(true);
			}
		});

		BrightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				simpleBouncingParticles.setBrightness((double) new_val);
				simpleBouncingParticles.setInitColor(true);
			}
		});

		SaturationSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				simpleBouncingParticles.setSaturation((double) new_val);
				simpleBouncingParticles.setInitColor(true);
			}
		});
	}
	
	private void initColorWidthSpinners() {
		HueWidthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 180, 0, 10));
		HueWidthSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				simpleBouncingParticles.setHueWidth((double) new_val);
				simpleBouncingParticles.setInitColor(true);
			}
		});
		
		SaturationWidthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0, 0.1));
		SaturationWidthSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				simpleBouncingParticles.setSatWidth((double) new_val);
				simpleBouncingParticles.setInitColor(true);
			}
		});
		
		BrightnessWidthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0, 0.1));
		BrightnessWidthSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				simpleBouncingParticles.setBrightWidth((double) new_val);
				simpleBouncingParticles.setInitColor(true);
			}
		});
	}

	// Particles
	private void initRadiusSpinners() {
		MinRadiusSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 5, 1, 0.5));
		MinRadiusSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (FixedRadiusBox.isSelected()) {
					MaxRadiusSpinner.getValueFactory().setValue((double) new_val);
					simpleBouncingParticles.setMinRadius((double) new_val);
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
					simpleBouncingParticles.setMaxRadius((double) new_val);
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
	
	private void initSpeedSliders() {
		MinSpeedSlider.setMin(0.1);
		MinSpeedSlider.setMax(10);
		MinSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (FixedSpeedBox.isSelected()) {
					MaxSpeedSlider.setValue((double) new_val);
					simpleBouncingParticles.setvMin((double) new_val);
					simpleBouncingParticles.setInitialize(true);
				} else {
					if ((double) new_val > MaxSpeedSlider.getValue()) {
						MinSpeedSlider.setValue((double) old_val);
					} else {
						simpleBouncingParticles.setvMin((double) new_val);
						simpleBouncingParticles.setInitialize(true);
					}
				}
			}
		});
		
		MaxSpeedSlider.setMin(0.1);
		MaxSpeedSlider.setMax(10);
		MaxSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (FixedSpeedBox.isSelected()) {
					MinSpeedSlider.setValue((double) new_val);
					simpleBouncingParticles.setvMax((double) new_val);
					simpleBouncingParticles.setInitialize(true);
				} else {
					if ((double) new_val < MinSpeedSlider.getValue()) {
						MaxSpeedSlider.setValue((double) old_val);
					} else {
						simpleBouncingParticles.setvMax((double) new_val);
						simpleBouncingParticles.setInitialize(true);
					}
				}
			}
		});
	}

	@FXML
	private void handleFixedSpeed() {
		if(FixedSpeedBox.isSelected()) {
			MaxSpeedSlider.setValue(MinSpeedSlider.getValue());
		}
	}
	
	@FXML
	private void initParticleNumberSpinner() {
		ParticleNumberSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 5, 1));
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
	private void handleFullRangeButton() {
		if (!FullRangeColorType.isSelected() && !RawColorType.isSelected() && !ShadedColorType.isSelected()) {
			FullRangeColorType.setSelected(true);
		}
		RawColorType.setSelected(false);
		ShadedColorType.setSelected(false);
		HueWidthSpinner.getValueFactory().setValue(180.0);;
		HueWidthSpinner.setDisable(true);
		simpleBouncingParticles.setInitColor(true);
	}

	@FXML
	private void handleRawButton() {
		if (!FullRangeColorType.isSelected() && !RawColorType.isSelected() && !ShadedColorType.isSelected()) {
			RawColorType.setSelected(true);
		}
		FullRangeColorType.setSelected(false);
		ShadedColorType.setSelected(false);
		HueWidthSpinner.getValueFactory().setValue(0.0);;
		HueWidthSpinner.setDisable(true);
		simpleBouncingParticles.setInitColor(true);
	}

	@FXML
	private void handleShadedButton() {
		if (!FullRangeColorType.isSelected() && !RawColorType.isSelected() && !ShadedColorType.isSelected()) {
			ShadedColorType.setSelected(true);
		}
		RawColorType.setSelected(false);
		FullRangeColorType.setSelected(false);
		HueWidthSpinner.setDisable(false);
		simpleBouncingParticles.setInitColor(true);
	}
	
	// Area
	private void initSizeSpinners() {
		WidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 256, 32, 1));
		WidthSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				simpleBouncingParticles.setAreaWidth((int) new_val);
				if(CenteredCheckBox.isSelected()) {
					centerArea();
				}
				simpleBouncingParticles.setInitialize(true);
			}
		});
		
		HeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 256, 16, 1));
		HeightSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				simpleBouncingParticles.setAreaHeight((int) new_val);
				if(CenteredCheckBox.isSelected()) {
					centerArea();
				}
				simpleBouncingParticles.setInitialize(true);
			}
		});
		
		HorizontalOffsetSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-128, 128, 0, 1));
		HorizontalOffsetSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				simpleBouncingParticles.setHorizontalOffset((int) new_val);
				simpleBouncingParticles.setInitialize(true);
			}
		});
		
		VerticalOffsetSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-128, 128, 0, 1));
		VerticalOffsetSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				simpleBouncingParticles.setVerticalOffset((int) new_val);
				simpleBouncingParticles.setInitialize(true);
			}
		});
	}
	
	private void initCheckBoxes() {
		if(CenteredCheckBox.isSelected()) {
			HorizontalOffsetSpinner.setDisable(true);
			VerticalOffsetSpinner.setDisable(true);
			centerArea();
		}
		else {
			HorizontalOffsetSpinner.setDisable(false);
			VerticalOffsetSpinner.setDisable(false);
		}	
	}
	
	@FXML
	private void handleEdgeCollisionsButton() {
		ParticlesCollisions.setSelected(false);
		if(simpleBouncingParticles.getParticleCollision()) {
			simpleBouncingParticles.setParticleCollision(false);
			simpleBouncingParticles.setInitialize(true);
		}
	}
	
	@FXML
	private void handleParticleCollisionsButton() {
		EdgeCollisions.setSelected(false);
		if(!simpleBouncingParticles.getParticleCollision()) {
			simpleBouncingParticles.setParticleCollision(true);
			simpleBouncingParticles.setInitialize(true);
		}
	}
	
	@FXML
	private void handleCenteredCheckBox() {
		if(CenteredCheckBox.isSelected()) {
			HorizontalOffsetSpinner.setDisable(true);
			VerticalOffsetSpinner.setDisable(true);
			centerArea();
		}
		else {
			HorizontalOffsetSpinner.setDisable(false);
			VerticalOffsetSpinner.setDisable(false);
		}
	}
	
	private void centerArea() {
		int xOffset = (int) Math.floor((LedPanel.MATRIX_WIDTH - WidthSpinner.getValue()) / 2);
		int yOffset = (int) Math.floor((LedPanel.MATRIX_HEIGHT - HeightSpinner.getValue()) / 2);
		HorizontalOffsetSpinner.getValueFactory().setValue(xOffset);
		VerticalOffsetSpinner.getValueFactory().setValue(yOffset);
	}

}
