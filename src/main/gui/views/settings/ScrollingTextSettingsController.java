package main.gui.views.settings;

import java.io.InputStream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.core.model.animations.text.ScrollingText;
import main.core.model.panel.LedPanel;

public class ScrollingTextSettingsController {

	@FXML
	private TextField TextToDisplayField;
	@FXML
	private Slider TextHueSlider;
	@FXML
	private Slider TextSaturationSlider;
	@FXML
	private Slider TextBrightnessSlider;
	@FXML
	private Slider whiteSlider;
	@FXML
	private Slider BackgroundHueSlider;
	@FXML
	private Slider BackgroundSaturationSlider;
	@FXML
	private Slider BackgroundBrightnessSlider;
	@FXML
	private ComboBox<String> DefaultFontsComboBox;
	@FXML
	private ComboBox<String> EmbeddedFontsComboBox;
	@FXML
	private Slider SizeSlider;
	@FXML
	private Slider SpeedSlider;

	private ScrollingText scrollingText;
	private String[] embeddedFonts = { "04B_30__", "Perfect DOS VGA 437 Win", "Minecraft", "I-pixel-u", "pixelart" };

	private ObservableList<String> ListDefaultFonts = FXCollections.observableArrayList();
	private ObservableList<String> ListEmbeddedFonts = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		setTextSliders();
		setBackgroundSliders();
		setOtherSliders();
		setComboBoxes();
	}

	public void setScrollingText(ScrollingText scrollingText) {
		this.scrollingText = scrollingText;
		// DefaultFontsComboBox.getSelectionModel().select("Times New Roman");
		setDisplayedParameters();
	}

	private void setTextSliders() {
		TextHueSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				double saturation = scrollingText.getTextColor().getSaturation();
				double brightness = scrollingText.getTextColor().getBrightness();
				scrollingText.setTextColor(Color.hsb((double) new_val, saturation, brightness));
				scrollingText.setTextArray();
			}
		});
		TextSaturationSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				double hue = scrollingText.getTextColor().getHue();
				double brightness = scrollingText.getTextColor().getBrightness();
				scrollingText.setTextColor(Color.hsb(hue, (double) new_val, brightness));
				scrollingText.setTextArray();
			}
		});

		TextBrightnessSlider.setMax(LedPanel.MAX_INTENSITY);
		TextBrightnessSlider.setMin(0.01 * LedPanel.MAX_INTENSITY); // Don't like 0...
		TextBrightnessSlider.setValue(LedPanel.MAX_INTENSITY);
		TextBrightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				double hue = scrollingText.getTextColor().getHue();
				double saturation = scrollingText.getTextColor().getSaturation();
				scrollingText.setTextColor(Color.hsb(hue, saturation, (double) new_val));
				scrollingText.setTextArray();
			}
		});

		whiteSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				scrollingText.setWhiteLevel((int) Math.floor((double) new_val));
				scrollingText.setTextArray();
			}
		});
	}

	private void setBackgroundSliders() {
		BackgroundHueSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				double saturation = scrollingText.getBackgroundColor().getSaturation();
				double brightness = scrollingText.getBackgroundColor().getBrightness();
				scrollingText.setBackgroundColor(Color.hsb((double) new_val, saturation, brightness));
				scrollingText.setTextArray();
			}
		});
		BackgroundSaturationSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				double hue = scrollingText.getBackgroundColor().getHue();
				double brightness = scrollingText.getBackgroundColor().getBrightness();
				scrollingText.setBackgroundColor(Color.hsb(hue, (double) new_val, brightness));
				scrollingText.setTextArray();
			}
		});

		BackgroundBrightnessSlider.setMax(LedPanel.MAX_INTENSITY);
		BackgroundBrightnessSlider.setMin(0.01 * LedPanel.MAX_INTENSITY); // Don't like 0...
		BackgroundBrightnessSlider.setValue(0.01 * LedPanel.MAX_INTENSITY);
		BackgroundBrightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				double hue = scrollingText.getBackgroundColor().getHue();
				double saturation = scrollingText.getBackgroundColor().getSaturation();
				scrollingText.setBackgroundColor(Color.hsb(hue, saturation, (double) new_val));
				scrollingText.setTextArray();
			}
		});
	}

	private void setOtherSliders() {

		SpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				scrollingText.setSpeed((int) Math.floor((double) new_val));
			}
		});

		SizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (DefaultFontsComboBox.getSelectionModel().getSelectedItem() != null) {
					scrollingText.setFont(
							new Font(DefaultFontsComboBox.getSelectionModel().getSelectedItem(), (double) new_val));
					scrollingText.setTextArray();
				} else if (EmbeddedFontsComboBox.getSelectionModel().getSelectedItem() != null) {
					Font font = null;
					InputStream is = this.getClass().getResourceAsStream(
							"/fonts/" + EmbeddedFontsComboBox.getSelectionModel().getSelectedItem() + ".ttf");
					font = Font.loadFont(is, SizeSlider.getValue());
					scrollingText.setFont(font);
					scrollingText.setTextArray();
				}
			}
		});

	}

	private void setComboBoxes() {
		ListDefaultFonts.addAll(Font.getFontNames());
		DefaultFontsComboBox.setItems(ListDefaultFonts);
		ListEmbeddedFonts.addAll(embeddedFonts);
		EmbeddedFontsComboBox.setItems(ListEmbeddedFonts);

		DefaultFontsComboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				if (t1 != null) {
					EmbeddedFontsComboBox.getSelectionModel().clearSelection();
					scrollingText.setFont(new Font(t1, SizeSlider.getValue()));
					scrollingText.setSelectedDefaultFontName(t1);
					scrollingText.setSelectedEmbeddedFontName(null);
					scrollingText.setTextArray();
				}
			}
		});

		EmbeddedFontsComboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				if (t1 != null) {
					DefaultFontsComboBox.getSelectionModel().clearSelection();
					Font font = null;
					InputStream is = this.getClass().getResourceAsStream(
							"/fonts/" + EmbeddedFontsComboBox.getSelectionModel().getSelectedItem() + ".ttf");
					font = Font.loadFont(is, SizeSlider.getValue());
					scrollingText.setFont(font);
					scrollingText.setSelectedDefaultFontName(null);
					scrollingText.setSelectedEmbeddedFontName(t1);
					scrollingText.setTextArray();
				}
			}
		});

	}

	private void setDisplayedParameters() {
		TextHueSlider.setValue(scrollingText.getTextColor().getHue());
		TextSaturationSlider.setValue(scrollingText.getTextColor().getSaturation());
		TextBrightnessSlider.setValue(scrollingText.getTextColor().getBrightness());
		BackgroundHueSlider.setValue(scrollingText.getBackgroundColor().getHue());
		BackgroundSaturationSlider.setValue(scrollingText.getBackgroundColor().getSaturation());
		BackgroundBrightnessSlider.setValue(scrollingText.getBackgroundColor().getBrightness());
		SizeSlider.setValue(scrollingText.getFont().getSize());
		SpeedSlider.setValue(scrollingText.getSpeed());
		DefaultFontsComboBox.getSelectionModel().select(scrollingText.getSelectedDefaultFontName());
		EmbeddedFontsComboBox.getSelectionModel().select(scrollingText.getSelectedEmbeddedFontName());
	}

	@FXML
	private void handleGo() {
		scrollingText.setTextToDisplay(TextToDisplayField.getText());
		scrollingText.setTextArray();
	}
}
