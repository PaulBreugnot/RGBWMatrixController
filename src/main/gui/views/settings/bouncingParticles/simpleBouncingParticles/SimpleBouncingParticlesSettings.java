package main.gui.views.settings.bouncingParticles.simpleBouncingParticles;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import main.core.model.panel.LedPanel;
import main.gui.views.settings.bouncingParticles.BouncingParticlesSettingsController;

public class SimpleBouncingParticlesSettings extends BouncingParticlesSettingsController {
	
	@FXML
	private CheckBox BlinkyCheckBox;
	
	@FXML
	private void handleBlinky() {
		particleAnimation.setBlinky(BlinkyCheckBox.isSelected());
	}
	
	// Patterns
		@FXML
		private void handleBigBalls() {
			FixedRadiusBox.setSelected(true);
			MinRadiusSpinner.getValueFactory().setValue((double) Math.max(LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT) / 8);
			MaxRadiusSpinner.getValueFactory().setValue((double) Math.max(LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT) / 8);
			FixedSpeedBox.setSelected(true);
			MinSpeedSlider.setValue((double) Math.max(LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT) / 30);
			MaxSpeedSlider.setValue((double) Math.max(LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT) / 30);
			CenteredCheckBox.setSelected(true);
			WidthSpinner.getValueFactory().setValue(2 * LedPanel.MATRIX_WIDTH);
			HeightSpinner.getValueFactory().setValue(2 * LedPanel.MATRIX_HEIGHT);
			ParticleNumberSpinner.getValueFactory()
					.setValue((int) Math.floor(Math.max(LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT) / 4));
		}
		
		@FXML
		private void handleTinyParticles() {
			FixedRadiusBox.setSelected(true);
			MinRadiusSpinner.getValueFactory().setValue(0.5);
			MaxRadiusSpinner.getValueFactory().setValue(0.5);
			FixedSpeedBox.setSelected(true);
			MinSpeedSlider.setValue((double) Math.max(LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT) / 30);
			MaxSpeedSlider.setValue((double) Math.max(LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT) / 30);
			CenteredCheckBox.setSelected(true);
			WidthSpinner.getValueFactory().setValue(LedPanel.MATRIX_WIDTH);
			HeightSpinner.getValueFactory().setValue(LedPanel.MATRIX_HEIGHT);
			ParticleNumberSpinner.getValueFactory()
					.setValue(LedPanel.MATRIX_HEIGHT * LedPanel.MATRIX_WIDTH / 4);
		}
		
		@FXML
		private void handleChaos() {
			FixedRadiusBox.setSelected(false);
			MinRadiusSpinner.getValueFactory().setValue(0.5);
			MaxRadiusSpinner.getValueFactory().setValue((double) Math.max(LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT) / 16);
			FixedSpeedBox.setSelected(false);
			MinSpeedSlider.setValue((double) Math.max(LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT) / 50);
			MaxSpeedSlider.setValue((double) Math.max(LedPanel.MATRIX_WIDTH, LedPanel.MATRIX_HEIGHT) / 10);
			CenteredCheckBox.setSelected(true);
			WidthSpinner.getValueFactory().setValue(LedPanel.MATRIX_WIDTH * 3);
			HeightSpinner.getValueFactory().setValue(LedPanel.MATRIX_HEIGHT * 3);
			ParticleNumberSpinner.getValueFactory()
					.setValue(LedPanel.MATRIX_HEIGHT * LedPanel.MATRIX_WIDTH / 4);
			//RawColorType.setSelected(false);
			//ShadedColorType.setSelected(false);
			FullRangeColorType.setSelected(true);
			handleFullRangeButton();
			BlinkyCheckBox.setSelected(true);
			handleBlinky();
		}
}
