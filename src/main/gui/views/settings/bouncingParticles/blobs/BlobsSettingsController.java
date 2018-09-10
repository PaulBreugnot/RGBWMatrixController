package main.gui.views.settings.bouncingParticles.blobs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import main.core.model.animations.bouncingParticles.blobs.BouncingBlobs;
import main.gui.views.settings.bouncingParticles.BouncingParticlesSettingsController;

public class BlobsSettingsController extends BouncingParticlesSettingsController {

	@FXML
	private Slider TresholdSlider;
	
	@Override
	protected void setChildDisplayedParameters() {
		TresholdSlider.setValue(((BouncingBlobs) particleAnimation).getMinTreshold());
	}
	
	@Override
	protected void initializeChild() {
		initTresholdSlider();
	}
	
	protected void initTresholdSlider() {
		MinSpeedSlider.setMin(0.05);
		MinSpeedSlider.setMax(0.2);
		MinSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				((BouncingBlobs) particleAnimation).setMinTreshold((double) new_val); 
			}
		});
	}

}
