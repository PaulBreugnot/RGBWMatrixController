package main.gui.views.settings.pixelRain;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import main.core.model.animations.pixelRain.PixelRain;

public class RainPixelSettingsController {

	@FXML
	private Slider colorSlider;

	@FXML
	private Slider densitySlider;

	@FXML
	private Slider lengthSlider;

	@FXML
	private Slider speedSlider;

	private PixelRain pixelRain;

	@FXML
	private void initialize() {
		setSliders();

	}

	private void setSliders() {
		colorSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				pixelRain.setHueColor((double) new_val);
			}
		});

		densitySlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				pixelRain.setDensity((double) new_val);
			}
		});

		lengthSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				pixelRain.setSpreadLength((int) Math.floor((double) new_val));
			}
		});

		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				pixelRain.clearMatrix();
				double roundValue = Math.floor(((double) new_val) * 100) / 100;
				System.out.println(roundValue);
				pixelRain.setSpeed(roundValue);
			}
		});
	}

	public void setPixelRain(PixelRain pixelRain) {
		this.pixelRain = pixelRain;
		setDisplayedParameters();
	}

	private void setDisplayedParameters() {
		colorSlider.setValue(pixelRain.getHueColor());
		densitySlider.setValue(pixelRain.getDensity());
		lengthSlider.setValue(pixelRain.getSpreadLength());
		speedSlider.setValue(pixelRain.getSpeed());
	}
}
