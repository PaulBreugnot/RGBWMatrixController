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
import main.core.model.animations.bouncingParticles.animation.ParticleAnimation;
import main.core.model.panel.LedPanel;

public class BouncingParticlesSettingsController {

	// Color menu
	@FXML
	protected RadioButton FullRangeColorType;
	@FXML
	protected RadioButton RawColorType;
	@FXML
	protected RadioButton ShadedColorType;
	@FXML
	protected Slider HueSlider;
	@FXML
	protected Spinner<Double> HueWidthSpinner;
	@FXML
	protected Slider BrightnessSlider;
	@FXML
	protected Spinner<Double> BrightnessWidthSpinner;
	@FXML
	protected Slider SaturationSlider;
	@FXML
	protected Spinner<Double> SaturationWidthSpinner;

	// Particles menu
	@FXML
	protected Spinner<Integer> ParticleNumberSpinner;
	@FXML
	protected CheckBox FixedRadiusBox;
	@FXML
	protected Spinner<Double> MinRadiusSpinner;
	@FXML
	protected Spinner<Double> MaxRadiusSpinner;
	@FXML
	protected CheckBox FixedSpeedBox;
	@FXML
	protected Slider MinSpeedSlider;
	@FXML
	protected Slider MaxSpeedSlider;

	// Collision Area menu
	@FXML
	protected RadioButton EdgeCollisions;
	@FXML
	protected RadioButton ParticlesCollisions;
	@FXML
	protected Spinner<Integer> WidthSpinner;
	@FXML
	protected Spinner<Integer> HeightSpinner;
	@FXML
	protected CheckBox CenteredCheckBox;
	@FXML
	protected Spinner<Integer> HorizontalOffsetSpinner;
	@FXML
	protected Spinner<Integer> VerticalOffsetSpinner;

	protected ParticleAnimation particleAnimation;

	public void setParticleAnimation(ParticleAnimation particleAnimation) {
		this.particleAnimation = particleAnimation;
		setDisplayedParameters();
	}

	protected void setDisplayedParameters() {
		// Color
		HueSlider.setValue(particleAnimation.getHue());
		HueWidthSpinner.getValueFactory().setValue(particleAnimation.getHueWidth());
		BrightnessSlider.setValue(particleAnimation.getBrightness());
		BrightnessWidthSpinner.getValueFactory().setValue(particleAnimation.getBrightWidth());
		SaturationSlider.setValue(particleAnimation.getSaturation());
		SaturationWidthSpinner.getValueFactory().setValue(particleAnimation.getSatWidth());

		// Particle
		MinRadiusSpinner.getValueFactory().setValue(particleAnimation.getMinRadius());
		MaxRadiusSpinner.getValueFactory().setValue(particleAnimation.getMaxRadius());
		MinSpeedSlider.setValue(particleAnimation.getvMin());
		MaxSpeedSlider.setValue(particleAnimation.getvMax());
		ParticleNumberSpinner.getValueFactory().setValue(particleAnimation.getParticleNumber());
		WidthSpinner.getValueFactory().setValue(particleAnimation.getAreaWidth());
		HeightSpinner.getValueFactory().setValue(particleAnimation.getAreaHeight());
		HorizontalOffsetSpinner.getValueFactory().setValue(particleAnimation.getHorizontalOffset());
		VerticalOffsetSpinner.getValueFactory().setValue(particleAnimation.getVerticalOffset());
		EdgeCollisions.setSelected(!particleAnimation.getParticleCollision());
		ParticlesCollisions.setSelected(particleAnimation.getParticleCollision());
		initCheckBoxes();
		setChildDisplayedParameters();
	}
	
	protected void setChildDisplayedParameters() {
		//To override
	}

	@FXML
	protected void initialize() {
		// Color
		initColorSliders();
		initColorWidthSpinners();
		// Particles
		initRadiusSpinners();
		initSpeedSliders();
		initParticleNumberSpinner();
		// Area
		initSizeSpinners();
		initializeChild();
	}
	
	protected void initializeChild() {
		// To override
	}

	// Color
	protected void initColorSliders() {
		HueSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				particleAnimation.setHue((double) new_val);
				particleAnimation.setInitColor(true);
			}
		});

		BrightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				particleAnimation.setBrightness((double) new_val);
				particleAnimation.setInitColor(true);
			}
		});

		SaturationSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				particleAnimation.setSaturation((double) new_val);
				particleAnimation.setInitColor(true);
			}
		});
	}

	protected void initColorWidthSpinners() {
		HueWidthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 360, 0, 10));
		HueWidthSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				particleAnimation.setHueWidth((double) new_val);
				particleAnimation.setInitColor(true);
			}
		});

		SaturationWidthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0, 0.1));
		SaturationWidthSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				particleAnimation.setSatWidth((double) new_val);
				particleAnimation.setInitColor(true);
			}
		});

		BrightnessWidthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0, 0.1));
		BrightnessWidthSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				particleAnimation.setBrightWidth((double) new_val);
				particleAnimation.setInitColor(true);
			}
		});
	}

	@FXML
	protected void handleFullRangeButton() {
		if (!FullRangeColorType.isSelected() && !RawColorType.isSelected() && !ShadedColorType.isSelected()) {
			FullRangeColorType.setSelected(true);
		}
		RawColorType.setSelected(false);
		ShadedColorType.setSelected(false);
		HueWidthSpinner.getValueFactory().setValue(360.0);
		;
		HueWidthSpinner.setDisable(true);
		particleAnimation.setInitColor(true);
	}

	@FXML
	protected void handleRawButton() {
		if (!FullRangeColorType.isSelected() && !RawColorType.isSelected() && !ShadedColorType.isSelected()) {
			RawColorType.setSelected(true);
		}
		FullRangeColorType.setSelected(false);
		ShadedColorType.setSelected(false);
		HueWidthSpinner.getValueFactory().setValue(0.0);
		;
		HueWidthSpinner.setDisable(true);
		particleAnimation.setInitColor(true);
	}

	@FXML
	protected void handleShadedButton() {
		if (!FullRangeColorType.isSelected() && !RawColorType.isSelected() && !ShadedColorType.isSelected()) {
			ShadedColorType.setSelected(true);
		}
		RawColorType.setSelected(false);
		FullRangeColorType.setSelected(false);
		HueWidthSpinner.setDisable(false);
		particleAnimation.setInitColor(true);
	}

	// Particles
	protected void initRadiusSpinners() {
		MinRadiusSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 5, 1, 0.5));
		MinRadiusSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (FixedRadiusBox.isSelected()) {
					MaxRadiusSpinner.getValueFactory().setValue((double) new_val);
					particleAnimation.setMinRadius((double) new_val);
					particleAnimation.setInitialize(true);
				} else {
					if ((double) new_val > MaxRadiusSpinner.getValue()) {
						MinRadiusSpinner.getValueFactory().setValue((double) old_val);
					} else {
						particleAnimation.setMinRadius((double) new_val);
						particleAnimation.setInitialize(true);
					}
				}
			}
		});

		MaxRadiusSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 5, 1, 0.5));
		MaxRadiusSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (FixedRadiusBox.isSelected()) {
					MinRadiusSpinner.getValueFactory().setValue((double) new_val);
					particleAnimation.setMaxRadius((double) new_val);
					particleAnimation.setInitialize(true);
				} else {
					if ((double) new_val < MinRadiusSpinner.getValue()) {
						MaxRadiusSpinner.getValueFactory().setValue((double) old_val);
					} else {
						particleAnimation.setMaxRadius((double) new_val);
						particleAnimation.setInitialize(true);
					}
				}
			}
		});
	}

	protected void initSpeedSliders() {
		MinSpeedSlider.setMin(0.1);
		MinSpeedSlider.setMax(10);
		MinSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (FixedSpeedBox.isSelected()) {
					MaxSpeedSlider.setValue((double) new_val);
					particleAnimation.setvMin((double) new_val);
					particleAnimation.setInitialize(true);
				} else {
					if ((double) new_val > MaxSpeedSlider.getValue()) {
						MinSpeedSlider.setValue((double) old_val);
					} else {
						particleAnimation.setvMin((double) new_val);
						particleAnimation.setInitialize(true);
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
					particleAnimation.setvMax((double) new_val);
					particleAnimation.setInitialize(true);
				} else {
					if ((double) new_val < MinSpeedSlider.getValue()) {
						MaxSpeedSlider.setValue((double) old_val);
					} else {
						particleAnimation.setvMax((double) new_val);
						particleAnimation.setInitialize(true);
					}
				}
			}
		});
	}

	@FXML
	protected void handleFixedSpeed() {
		if (FixedSpeedBox.isSelected()) {
			MaxSpeedSlider.setValue(MinSpeedSlider.getValue());
		}
	}

	@FXML
	protected void initParticleNumberSpinner() {
		ParticleNumberSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, LedPanel.MATRIX_HEIGHT * LedPanel.MATRIX_WIDTH / 2, 5, 1));
		ParticleNumberSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				particleAnimation.setParticleNumber((int) new_val);
				particleAnimation.setInitialize(true);
			}
		});
	}

	@FXML
	protected void handleFixedRadius() {
		if (FixedRadiusBox.isSelected()) {
			MaxRadiusSpinner.getValueFactory().setValue(MinRadiusSpinner.getValue());
		}
	}

	// Area
	protected void initSizeSpinners() {
		WidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 256, 32, 1));
		WidthSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				particleAnimation.setAreaWidth((int) new_val);
				if (CenteredCheckBox.isSelected()) {
					centerArea();
				}
				particleAnimation.setInitialize(true);
			}
		});

		HeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 256, 16, 1));
		HeightSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				particleAnimation.setAreaHeight((int) new_val);
				if (CenteredCheckBox.isSelected()) {
					centerArea();
				}
				particleAnimation.setInitialize(true);
			}
		});

		HorizontalOffsetSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-128, 128, 0, 1));
		HorizontalOffsetSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				particleAnimation.setHorizontalOffset((int) new_val);
				particleAnimation.setInitialize(true);
			}
		});

		VerticalOffsetSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-128, 128, 0, 1));
		VerticalOffsetSpinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				particleAnimation.setVerticalOffset((int) new_val);
				particleAnimation.setInitialize(true);
			}
		});
	}

	protected void initCheckBoxes() {
		if (CenteredCheckBox.isSelected()) {
			HorizontalOffsetSpinner.setDisable(true);
			VerticalOffsetSpinner.setDisable(true);
			centerArea();
		} else {
			HorizontalOffsetSpinner.setDisable(false);
			VerticalOffsetSpinner.setDisable(false);
		}
	}

	@FXML
	protected void handleEdgeCollisionsButton() {
		ParticlesCollisions.setSelected(false);
		if (particleAnimation.getParticleCollision()) {
			particleAnimation.setParticleCollision(false);
			particleAnimation.setInitialize(true);
		}
	}

	@FXML
	protected void handleParticleCollisionsButton() {
		EdgeCollisions.setSelected(false);
		if (!particleAnimation.getParticleCollision()) {
			particleAnimation.setParticleCollision(true);
			particleAnimation.setInitialize(true);
		}
	}

	@FXML
	protected void handleCenteredCheckBox() {
		if (CenteredCheckBox.isSelected()) {
			HorizontalOffsetSpinner.setDisable(true);
			VerticalOffsetSpinner.setDisable(true);
			centerArea();
		} else {
			HorizontalOffsetSpinner.setDisable(false);
			VerticalOffsetSpinner.setDisable(false);
		}
	}

	protected void centerArea() {
		int xOffset = (int) Math.floor((LedPanel.MATRIX_WIDTH - WidthSpinner.getValue()) / 2);
		int yOffset = (int) Math.floor((LedPanel.MATRIX_HEIGHT - HeightSpinner.getValue()) / 2);
		HorizontalOffsetSpinner.getValueFactory().setValue(xOffset);
		VerticalOffsetSpinner.getValueFactory().setValue(yOffset);
	}

}
